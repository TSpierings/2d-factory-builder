package com.tchaka.factorybuilder.systems

import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.SortedIteratingSystem
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.math.Vector3
import com.tchaka.factorybuilder.components.CellComponent
import com.tchaka.factorybuilder.components.TransformComponent
import java.util.*
import kotlin.system.measureTimeMillis

class CellRenderingSystem(private val camera: Camera) : SortedIteratingSystem(
  Family.all(
    TransformComponent::class.java,
    CellComponent::class.java
  ).get(),
  ZComparator()
) {
  private val comparator = ZComparator()
  private val renderMap = mutableMapOf<Int, MutableList<Entity>>()

  private val transformMapper = ComponentMapper.getFor(TransformComponent::class.java)
  private val cellMapper = ComponentMapper.getFor(CellComponent::class.java)
  private val spriteBatch = SpriteBatch()

  private var frames = 0
  private var frameTime = 0L

  private val tex = Texture("test_tex_2.png")

  init {
    Timer().scheduleAtFixedRate(object : TimerTask() {
      override fun run() {
        println("Frames: $frames Frametime: $frameTime")
        frames = 0
      }
    }, 0, 1000)
  }

  override fun update(deltaTime: Float) {
    super.update(deltaTime)

    camera.update()
    spriteBatch.projectionMatrix = camera.combined
    spriteBatch.begin()
    spriteBatch.enableBlending()

    frameTime = measureTimeMillis {
      renderMap.forEach {
        it.value.sortWith(comparator)
        val transform = transformMapper.get(it.value[0])
        spriteBatch.transformMatrix = Matrix4()
          .translate(transform.position)
          .rotate(
            Vector3(0f, 0f, 1f),
            transform.rotation
          )

        it.value.forEach {
          val cell = cellMapper.get(it)
          spriteBatch.draw(tex, cell.vertices, 0, cell.vertices.count())
        }
      }
    }

    spriteBatch.end()
    renderMap.clear()

    frames++
  }

  override fun processEntity(entity: Entity, deltaTime: Float) {
//    entity.getComponent(TransformComponent::class.java).rotation += 0.01f

    val planetId = entity.getComponent(CellComponent::class.java).planetId

    renderMap.putIfAbsent(planetId, mutableListOf())
    renderMap[entity.getComponent(CellComponent::class.java).planetId]?.add(entity)
  }
}
package com.tchaka.factorybuilder.systems

import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.SortedIteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.tchaka.factorybuilder.components.TextureComponent
import com.tchaka.factorybuilder.components.TransformComponent
import java.io.Console

class RenderingSystem :
  SortedIteratingSystem(
    Family.all(
      TransformComponent::class.java
    ).get(),
    ZComparator()
  ) {
  private val pixelsPerMetre = 1.0f
  private var frustumHeight = 0.0f
  private var frustumWidth = 0.0f

  private val textureMapper = ComponentMapper.getFor(TextureComponent::class.java)
  private val transformMapper = ComponentMapper.getFor(TransformComponent::class.java)
  private val renderQueue = mutableListOf<Entity>()
  private val comparator = ZComparator()
  private var camera: OrthographicCamera

  private var shapeRenderer: ShapeRenderer = ShapeRenderer()

  val pixelsToMetres = 1 / pixelsPerMetre

  init {
    frustumHeight = Gdx.graphics.height / pixelsPerMetre
    frustumWidth = Gdx.graphics.width / pixelsPerMetre

    camera = OrthographicCamera(frustumWidth, frustumHeight)
    camera.position.set(0f, 0f, 0f)
  }

  override fun update(deltaTime: Float) {
    super.update(deltaTime)

    renderQueue.sortWith(comparator)

    camera.update()
//    spriteBatch.projectionMatrix = camera.combined
//    spriteBatch.enableBlending()
//    spriteBatch.begin()

    shapeRenderer.projectionMatrix = camera.combined
    shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)

//    println("Renderqueue depth: ${renderQueue.size}")

    renderQueue.forEach {
      val texture = textureMapper.get(it)
      val transform = transformMapper.get(it)

//      if (texture.region == null) return

      shapeRenderer.setColor(1f, 0f, 0f, 1f)
      shapeRenderer.circle(transform.position.x, transform.position.y, 50f)
    }

    shapeRenderer.end()
    renderQueue.clear()
  }

  override fun processEntity(entity: Entity, deltaTime: Float) {
    renderQueue.add(entity)
  }

  val Camera: OrthographicCamera
    get() {
      return camera
    }
}
package com.tchaka.factorybuilder.systems

import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.SortedIteratingSystem
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.tchaka.factorybuilder.components.CellComponent
import com.tchaka.factorybuilder.components.TransformComponent

class CellRenderingSystem(private val camera: Camera) : SortedIteratingSystem(
  Family.all(
    TransformComponent::class.java,
    CellComponent::class.java
  ).get(),
  ZComparator()
) {
  private val comparator = ZComparator()
  private val renderQueue = mutableListOf<Entity>()

  //  private val transformMapper = ComponentMapper.getFor(TransformComponent::class.java)
  private val cellMapper = ComponentMapper.getFor(CellComponent::class.java)
  private val spriteBatch = SpriteBatch()

  override fun update(deltaTime: Float) {
    super.update(deltaTime)

    renderQueue.sortWith(comparator)

    camera.update()
    spriteBatch.projectionMatrix = camera.combined
    spriteBatch.begin()
    spriteBatch.enableBlending()

    renderQueue.forEach {
//      val transform = transformMapper.get(it)
      val cell = cellMapper.get(it)

      spriteBatch.draw(cell.texture, cell.vertices, 0, cell.vertices.count())
    }

    spriteBatch.end()
    renderQueue.clear()
  }

  override fun processEntity(entity: Entity, deltaTime: Float) {
    renderQueue.add(entity)
  }

//  private fun translateVertices(vertices: FloatArray, transform: Vector3): FloatArray {
//    val translatedVertices = vertices.clone()
//    translatedVertices[0] += transform.x
//    translatedVertices[1] += transform.y
//
//    translatedVertices[5] += transform.x
//    translatedVertices[6] += transform.y
//
//    translatedVertices[10] += transform.x
//    translatedVertices[11] += transform.y
//
//    translatedVertices[15] += transform.x
//    translatedVertices[16] += transform.y
//
//    return translatedVertices
//  }
}
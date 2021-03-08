package com.tchaka.factorybuilder.systems

import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.tchaka.factorybuilder.components.PlanetComponent
import com.tchaka.factorybuilder.components.TransformComponent

class PlanetRenderingSystem(private val camera: Camera) : IteratingSystem(
  Family.all(
    TransformComponent::class.java,
    PlanetComponent::class.java
  ).get()
) {
  private val renderQueue = mutableListOf<Entity>()
  private val shapeRenderer = ShapeRenderer()
  private val transformMapper = ComponentMapper.getFor(TransformComponent::class.java)
  private val planetMapper = ComponentMapper.getFor(PlanetComponent::class.java)

  override fun update(deltaTime: Float) {
    super.update(deltaTime)

    camera.update()
    shapeRenderer.projectionMatrix = camera.combined
    shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)

    renderQueue.forEach {
      val planet = planetMapper.get(it)
      val transform = transformMapper.get(it)

      shapeRenderer.color = planet.color
      val segments = planet.size * 10
      shapeRenderer.circle(transform.position.x, transform.position.y, planet.size, segments.toInt())
    }

    shapeRenderer.end()

    renderQueue.forEach {
      val planet = planetMapper.get(it)
      CellRenderingSystem.renderCells(planet.cells, camera)
    }


    renderQueue.clear()
  }

  override fun processEntity(entity: Entity, deltaTime: Float) {
    renderQueue.add(entity)
  }
}
package com.tchaka.factorybuilder.factories

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector3
import com.tchaka.factorybuilder.components.PlanetComponent
import com.tchaka.factorybuilder.components.TransformComponent

class PlanetFactory {
  companion object {
    fun create(engine: PooledEngine, distance: Float): Entity {
      val entity = engine.createEntity()
      val transformComponent = engine.createComponent(TransformComponent::class.java)
      val planetComponent = engine.createComponent(PlanetComponent::class.java)

      transformComponent.position = Vector3(
        distance,
        0f,
        0f
      )

//      transformComponent.position = Vector3.Zero

      planetComponent.color = Color(
        Math.random().toFloat(),
        Math.random().toFloat(),
        Math.random().toFloat(),
        1f
      )

      planetComponent.size = 100f
      planetComponent.orbitalDistance = distance
      planetComponent.cells = ArrayList(planetComponent.size.toInt() * 10)

      entity.add(transformComponent)
      entity.add(planetComponent)

      return entity
    }
  }
}
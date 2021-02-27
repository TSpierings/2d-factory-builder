package com.tchaka.factorybuilder.factories

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector3
import com.tchaka.factorybuilder.components.PlanetComponent
import com.tchaka.factorybuilder.components.PlanetaryBodyComponent
import com.tchaka.factorybuilder.components.TransformComponent

class PlanetFactory {
  companion object {
    fun create(engine: PooledEngine, planetId: Int): Entity {
      val entity = engine.createEntity()
      val transformComponent = engine.createComponent(TransformComponent::class.java)
      val planetaryBodyComponent = engine.createComponent(PlanetaryBodyComponent::class.java)
      val planetComponent = engine.createComponent(PlanetComponent::class.java)

      transformComponent.position = Vector3(
        (Math.random().toFloat() - 0.5f) * 1500,
        (Math.random().toFloat() - 0.5f) * 1500,
        0f
      )

      transformComponent.position = Vector3.Zero

      planetaryBodyComponent.color = Color(
        Math.random().toFloat(),
        Math.random().toFloat(),
        Math.random().toFloat(),
        1f
      )

      planetaryBodyComponent.size = 100f
      planetComponent.planetId = planetId

      entity.add(transformComponent)
      entity.add(planetaryBodyComponent)
      entity.add(planetComponent)

      return entity
    }
  }
}
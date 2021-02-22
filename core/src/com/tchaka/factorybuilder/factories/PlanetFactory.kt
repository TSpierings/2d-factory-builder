package com.tchaka.factorybuilder.factories

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector3
import com.tchaka.factorybuilder.components.PlanetaryBodyComponent
import com.tchaka.factorybuilder.components.TransformComponent

class PlanetFactory {
  companion object {
    fun create(engine: PooledEngine): Entity {
      val entity = engine.createEntity()
      val transformComponent = engine.createComponent(TransformComponent::class.java)
      val planetaryBodyComponent = engine.createComponent(PlanetaryBodyComponent::class.java)

//      transformComponent.position = Vector3(
//        (Math.random().toFloat() - 0.5f) * 500,
//        (Math.random().toFloat() - 0.5f) * 500,
//        0f
//      )
      transformComponent.position = Vector3.Zero

      planetaryBodyComponent.color = Color(
        Math.random().toFloat(),
        Math.random().toFloat(),
        Math.random().toFloat(),
        1f
      )

      planetaryBodyComponent.size = 100f

      entity.add(transformComponent)
      entity.add(planetaryBodyComponent)

      return entity
    }
  }
}
package com.tchaka.factorybuilder

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.math.Vector3
import com.tchaka.factorybuilder.components.TransformComponent

class PlanetFactory {
//  private var colors: Vector3 = Vector3(
//      Math.random().toFloat(), Math.random().toFloat(), Math.random().toFloat())

  companion object {
    fun create(engine: PooledEngine): Entity {
      val entity = engine.createEntity()
      val transformComponent = engine.createComponent(TransformComponent::class.java)

      transformComponent.position = Vector3(
        (Math.random().toFloat() - 0.5f) * 500,
        (Math.random().toFloat() - 0.5f) * 500,
        0f)

      entity.add(transformComponent)

      return entity
    }
  }
}
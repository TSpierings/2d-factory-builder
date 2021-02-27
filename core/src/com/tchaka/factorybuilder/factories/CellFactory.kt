package com.tchaka.factorybuilder.factories

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.tchaka.factorybuilder.CoordinateUtils
import com.tchaka.factorybuilder.components.CellComponent
import com.tchaka.factorybuilder.components.PlanetComponent
import com.tchaka.factorybuilder.components.PlanetaryBodyComponent
import com.tchaka.factorybuilder.components.TransformComponent
import java.lang.Math.PI

class CellFactory {
  companion object {
    fun create(engine: PooledEngine, height: Int, radial: Int, planet: Entity): Entity {
      val entity = engine.createEntity()
      val transformComponent = engine.createComponent(TransformComponent::class.java)
      val cellComponent = engine.createComponent(CellComponent::class.java)
      val planetSize = planet.getComponent(PlanetaryBodyComponent::class.java).size

      transformComponent.position = planet.getComponent(TransformComponent::class.java).position
      transformComponent.rotation = 0.0f

      val color = Color(
        1f, 1f, 1f,
        1f
      ).toFloatBits()

      val radialStep = (PI * 2).toFloat() / (planetSize * 10)
      val radialPercentage = radialStep * radial

      val a = CoordinateUtils.sectionToWorld(0f, planetSize)
      val b = CoordinateUtils.sectionToWorld(radialStep, planetSize)
      val dist = Vector2.dst(a.x, a.y, b.x, b.y)
      val totalHeight = planetSize + (height * dist)

      val leftLower = CoordinateUtils.sectionToWorld(radialPercentage, totalHeight)
      val rightLower = CoordinateUtils.sectionToWorld(radialPercentage + radialStep, totalHeight)
      val leftUpper = CoordinateUtils.sectionToWorld(radialPercentage, totalHeight + dist)
      val rightUpper = CoordinateUtils.sectionToWorld(radialPercentage + radialStep, totalHeight + dist)

      cellComponent.vertices = floatArrayOf(
        leftLower.x, leftLower.y, color, 0f, 1f,
        rightLower.x, rightLower.y, color, 1f, 1f,
        rightUpper.x, rightUpper.y, color, 1f, 0f,
        leftUpper.x, leftUpper.y, color, 0f, 0f
      )

      cellComponent.planetId = planet.getComponent(PlanetComponent::class.java).planetId

      entity.add(transformComponent)
      entity.add(cellComponent)

      return entity
    }
  }
}
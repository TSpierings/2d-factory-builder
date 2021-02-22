package com.tchaka.factorybuilder.factories

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Vector3
import com.tchaka.factorybuilder.CoordinateUtils
import com.tchaka.factorybuilder.components.CellComponent
import com.tchaka.factorybuilder.components.TransformComponent

class CellFactory {
  companion object {
    fun create(engine: PooledEngine, height: Float): Entity {
      val entity = engine.createEntity()
      val transformComponent = engine.createComponent(TransformComponent::class.java)
      val cellComponent = engine.createComponent(CellComponent::class.java)

      transformComponent.position = Vector3.Zero
      transformComponent.rotation = 0.0f

      val color = Color(
        1f,1f,1f,
        1f
      ).toFloatBits()

      val leftUpper = CoordinateUtils.sectionToWorld(0f, 101f)
      val rightUpper = CoordinateUtils.sectionToWorld(0.01f, 101f)
      val leftLower = CoordinateUtils.sectionToWorld(0f, 100f)
      val rightLower = CoordinateUtils.sectionToWorld(0.01f, 100f)

      cellComponent.vertices = floatArrayOf(
        leftLower.x, leftLower.y, color, 0f, 1f,
        rightLower.x, rightLower.y, color, 1f, 1f,
        rightUpper.x, rightUpper.y, color, 1f, 0f,
        leftUpper.x, leftUpper.y, color, 0f, 0f
      )

      println("${leftLower.x}, ${leftLower.y} - ${rightUpper.x}, ${rightUpper.y}")

      cellComponent.texture = Texture("badlogic.jpg")

      entity.add(transformComponent)
      entity.add(cellComponent)

      return entity
    }
  }
}
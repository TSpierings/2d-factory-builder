package com.tchaka.factorybuilder.flat_test

import com.badlogic.gdx.ai.pfa.GraphPath
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.tchaka.factorybuilder.graph.Vertex

class Order(
  private val path: GraphPath<Vertex>,
  val type: Int,
  val action: () -> Unit
) {
  private val speed = 100f
  private var index = 0
  private val location = Vector2(path[0].x * 100f + 50f, path[0].y * 100f + 50f)
  private var deltaX = 0f
  private var deltaY = 0f

  fun update(delta: Float): Boolean {
    val target = Vector2(path[index].x * 100f + 50f, path[index].y * 100f + 50f)
    val distance = Vector2.dst2(target.x, target.y, location.x, location.y)

    if (distance < 10f) {
      index++

      if (index >= path.count()) {
        action()
        return true
      }

      setDeltaSpeed()
    }

    location.x += deltaX * delta
    location.y += deltaY * delta

    return false
  }

  private fun setDeltaSpeed() {
    val target = Vector2(path[index].x * 100f + 50f, path[index].y * 100f + 50f)
    val angle = MathUtils.atan2(target.y - location.y, target.x - location.x)
    deltaX = MathUtils.cos(angle) * speed
    deltaY = MathUtils.sin(angle) * speed
  }

  fun render(shapeRenderer: ShapeRenderer) {
    shapeRenderer.circle(location.x, location.y, 10f, 10)
  }
}
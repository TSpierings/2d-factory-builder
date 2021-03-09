package com.tchaka.factorybuilder.flat_test

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import kotlin.math.abs
import kotlin.random.Random

class Order(
  val origin: Float,
  val destination: Float,
  val type: Int,
  val action: () -> Unit
) {
  private val speed = 100f
  private val goal = abs(destination * 100f - origin * 100f)
  private val direction = if (destination < origin) -1 else 1
  private val height = 10 + Random.nextFloat() * 80

  private var distance = 0f

  fun update(delta: Float): Boolean {
    distance += delta * speed
    if (distance >= goal) action()
    return  distance >= goal
  }

  fun render(shapeRenderer: ShapeRenderer) {
    shapeRenderer.circle(origin * 100f + distance * direction + 50f, height, 10f, 10)
  }
}
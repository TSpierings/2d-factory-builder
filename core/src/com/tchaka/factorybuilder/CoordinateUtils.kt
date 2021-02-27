package com.tchaka.factorybuilder

import com.badlogic.gdx.math.Vector2
import kotlin.math.cos
import kotlin.math.sin

class CoordinateUtils {
  companion object {
    private const val TAU = Math.PI * 2

    fun sectionToWorld(radial: Float, distance: Float): Vector2 {
      val radians = TAU * radial
      return Vector2(
        (distance * sin(radians)).toFloat(), (distance * cos(radians)).toFloat()
      )
    }
  }
}
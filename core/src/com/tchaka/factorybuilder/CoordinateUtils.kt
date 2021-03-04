package com.tchaka.factorybuilder

import com.badlogic.gdx.math.Vector2
import kotlin.math.cos
import kotlin.math.sin

class CoordinateUtils {
  companion object {
    fun sectionToWorld(radians: Float, distance: Float): Vector2 {
      return Vector2(
        (distance * sin(radians)), (distance * cos(radians))
      )
    }
  }
}
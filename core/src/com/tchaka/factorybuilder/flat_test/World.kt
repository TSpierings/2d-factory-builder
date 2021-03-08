package com.tchaka.factorybuilder.flat_test

import com.badlogic.gdx.Gdx

class World {
  private val stride = 100
  private val cells = mutableMapOf<Int, Int>()

  fun getCells() = cells

  fun hasBuilding(x: Int, y: Int): Boolean {
    return  cells.containsKey(x + y * stride)
  }

  fun addBuilding(x: Int, y: Int, building: Int): Int? {
    Gdx.app.log("Build", "x: $x, y: $y, int: ${x + y * stride}")
    return cells.putIfAbsent(x + y * stride, building)
  }
}
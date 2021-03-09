package com.tchaka.factorybuilder.flat_test

import com.badlogic.gdx.Gdx

class World {
  private val stride = 100
  private var buildingIds = 0
  val cells = mutableMapOf<Int, Core>()
  val orders = mutableListOf<Order>()

  fun hasBuilding(x: Int, y: Int): Boolean {
    return  cells.containsKey(x + y * stride)
  }

  fun addBuilding(x: Int, y: Int, type: Int): Core? {
    Gdx.app.log("Build", "x: $x, y: $y, int: ${x + y * stride}")
    val location = x + y * stride
    return cells.putIfAbsent(location, Core(buildingIds++, type, location))
  }
}
package com.tchaka.factorybuilder.flat_test

import com.tchaka.factorybuilder.graph.Graph

class World {
  private val stride = 100
  private var buildingIds = 0
  val cells = mutableMapOf<Int, Core>()
  val orders = mutableListOf<Order>()
  val graph = Graph()

  fun hasBuilding(x: Int, y: Int): Boolean {
    return  cells.containsKey(x + y * stride)
  }

  fun addBuilding(x: Int, y: Int, type: Int): Core? {
    val location = x + y * stride

    val building = cells.putIfAbsent(location, Core(buildingIds++, type, location))

    if (building == null) {
      graph.addNode(x, y)
    }

    return building
  }

  fun getBuilding(x: Int, y: Int): Core? {
    return cells[ x + y * stride]
  }
}
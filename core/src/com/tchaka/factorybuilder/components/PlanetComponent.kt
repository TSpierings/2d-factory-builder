package com.tchaka.factorybuilder.components

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.graphics.Color

class PlanetComponent: Component {
  var id = getNewId()
  var size = 100f
  var orbitalDistance = 10000f
  lateinit var color: Color
  lateinit var cells: ArrayList<Entity>

  companion object {
    private var idIterator = 0

    fun getNewId(): Int {
      return idIterator++
    }
  }
}
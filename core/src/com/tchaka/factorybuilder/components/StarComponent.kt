package com.tchaka.factorybuilder.components

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity

class StarComponent: Component {
  var id = getNewId()
  var size = 1000f
  lateinit var planets: ArrayList<Entity>

  companion object {
    private var idIterator = 0

    fun getNewId(): Int {
      return idIterator++
    }
  }
}
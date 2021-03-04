package com.tchaka.factorybuilder.components

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity

class PlanetComponent: Component {
  var planetId = 0
  lateinit var slots: ArrayList<Entity>
}
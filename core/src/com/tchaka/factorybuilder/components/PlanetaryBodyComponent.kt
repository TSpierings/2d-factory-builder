package com.tchaka.factorybuilder.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.Color

class PlanetaryBodyComponent : Component {
  var size = 0.0f
  var color: Color = Color(0f, 0f, 0f, 1f)
}
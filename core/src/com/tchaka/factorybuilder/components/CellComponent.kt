package com.tchaka.factorybuilder.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.Texture

class CellComponent: Component {
  lateinit var vertices: FloatArray
  lateinit var texture: Texture
  var planetId = 0
}
package com.tchaka.factorybuilder.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.math.Vector3

class TransformComponent: Component {
  var position: Vector3 = Vector3.Zero
  var rotation = 0.0f
}
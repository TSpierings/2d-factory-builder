package com.tchaka.factorybuilder

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3

class Player {
  private var shapeRenderer: ShapeRenderer = ShapeRenderer()
  private var colors: Vector3 = Vector3(
      Math.random().toFloat(), Math.random().toFloat(), Math.random().toFloat())
  private var position: Vector2 = Vector2.Zero

  fun render(camera: OrthographicCamera) {
    shapeRenderer.projectionMatrix = camera.combined
    shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
    shapeRenderer.setColor(colors.x, colors.y, colors.z, 1f)
    shapeRenderer.circle(position.x, position.y, 25f)
    shapeRenderer.end()
  }

  fun dispose() {
    shapeRenderer.dispose()
  }
}
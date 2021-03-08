package com.tchaka.factorybuilder.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.tchaka.factorybuilder.FactoryBuilder
import com.tchaka.factorybuilder.flat_test.Builder
import com.tchaka.factorybuilder.flat_test.UI

class TestScreen(private val game: FactoryBuilder) : ScreenAdapter() {
  private lateinit var camera: OrthographicCamera
  private val builder = Builder(game)
  private val userInterface = UI(builder)

  override fun show() {
    super.show()

    camera = OrthographicCamera()
    userInterface.create()
  }

  override fun resize(width: Int, height: Int) {
    super.resize(width, height)

    camera.viewportHeight = height.toFloat()
    camera.viewportWidth = width.toFloat()
    camera.position.set(
      width / 2f,
      height / 2f,
      0f)

    userInterface.resize(width, height)
  }

  override fun render(delta: Float) {
    super.render(delta)

    Gdx.gl.glClearColor(1f, 1f, 1f, 1f)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

    camera.update()
    game.shapeRenderer.projectionMatrix = camera.combined

    game.shapeRenderer.setAutoShapeType(true)
    game.shapeRenderer.begin()
    game.shapeRenderer.color = Color.BLUE
    game.shapeRenderer.set(ShapeRenderer.ShapeType.Filled)
    game.shapeRenderer.rect(0f, 0f, 100f, 100f)

    builder.render()

    game.shapeRenderer.end()

    userInterface.render(delta)
  }

  override fun hide() {
    super.hide()
  }
}
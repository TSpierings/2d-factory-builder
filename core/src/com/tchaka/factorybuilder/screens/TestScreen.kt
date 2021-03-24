package com.tchaka.factorybuilder.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.MathUtils
import com.tchaka.factorybuilder.FactoryBuilder
import com.tchaka.factorybuilder.flat_test.*
import kotlin.math.round
import kotlin.system.measureTimeMillis

class TestScreen(private val game: FactoryBuilder) : ScreenAdapter() {
  private lateinit var camera: OrthographicCamera
  private val world = World()
  private val builder = Builder(game, world)
  private val planner = Planner(game, world)
  private val userInterface = UI(builder, planner)

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
    camera.zoom = 1f

    userInterface.resize(width, height)

    for (x in 0 until 100) {
      for(y in 0 until 100) {
        val newBuilding = world.addBuilding(x, y, round(Math.random() * 5).toInt())

        if (y > 0) {
          newBuilding?.target = world.getBuilding(0, 0)
        }
      }
    }
  }

  override fun render(delta: Float) {
    super.render(delta)

    UI.debugLog["updateTime"]?.setText("Update: ${measureTimeMillis {
      update(delta)
    }} ms")

    Gdx.gl.glClearColor(1f, 1f, 1f, 1f)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

    camera.update()
    game.shapeRenderer.projectionMatrix = camera.combined

    game.shapeRenderer.setAutoShapeType(true)
    game.shapeRenderer.begin()

    world.cells.forEach { (key, value) ->
      game.shapeRenderer.color = value.color
      game.shapeRenderer.set(ShapeRenderer.ShapeType.Filled)
      val x = key % 100
      val y = MathUtils.floor(key / 100f)
      game.shapeRenderer.rect(x * 100f, y * 100f, 100f, 100f)
    }

    world.orders.forEach {
      game.shapeRenderer.color = Core.makeColor(it.type)
      it.render(game.shapeRenderer)
    }

    builder.render()
    world.graph.render(game.shapeRenderer)
    planner.render()

    game.shapeRenderer.end()

    userInterface.render(delta)
  }

  private fun update(delta: Float) {
    world.cells.values.forEach { it.update(delta) }
    val newOrders = world.cells.values.sumBy { it.resolveOrdersManual(world) }
    world.orders.removeIf { it.update(delta) }

    UI.debugLog["orderCount"]?.setText("New: $newOrders total: ${world.orders.size}")
  }
}
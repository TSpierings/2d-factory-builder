package com.tchaka.factorybuilder.flat_test

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.tchaka.factorybuilder.FactoryBuilder
import kotlin.math.floor

class Planner(
  private val game: FactoryBuilder,
  private val world: World
) : InputAdapter() {
  private val cellSize = 100f

  private var planStart: Core? = null

  private fun getCellPosition(x: Int, y: Int): Pair<Int, Int> {
    return Pair(
      MathUtils.floor(x / cellSize),
      MathUtils.floor(y / cellSize)
    )
  }

  fun render() {
    if (planStart == null) return

    val startPosition = Vector2(
      planStart!!.location % 100f * cellSize + cellSize / 2,
      floor(planStart!!.location / 100f) * cellSize + cellSize / 2
    )

    game.shapeRenderer.color = Color.RED
    game.shapeRenderer.set(ShapeRenderer.ShapeType.Filled)
    game.shapeRenderer.rectLine(startPosition, Vector2(Gdx.input.x.toFloat(), Gdx.graphics.height - Gdx.input.y.toFloat()), 5f)
  }

  override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
    val coord = getCellPosition(screenX, Gdx.graphics.height - screenY)

    val building = world.getBuilding(coord.first, coord.second) ?: return false

    if (planStart == null)
      planStart = building
    else {
      planStart!!.target = building
      planStart = null
    }

    return true
  }
}
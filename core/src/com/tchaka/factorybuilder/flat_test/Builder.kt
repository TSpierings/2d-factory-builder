package com.tchaka.factorybuilder.flat_test

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.MathUtils
import com.tchaka.factorybuilder.FactoryBuilder
import kotlin.random.Random

class Builder(
  private val game: FactoryBuilder,
  private val world: World
) : InputAdapter() {
  private var toBuild = -1
  private val cellSize = 100f
  private var color = Color.RED

  fun render() {
    if (toBuild >= 0) {
      val position = getCellPosition(Gdx.input.x, Gdx.graphics.height - Gdx.input.y)

      if (world.hasBuilding(position.first, position.second)) {
        game.shapeRenderer.color = Color.RED
      } else {
        game.shapeRenderer.color = color
      }

      game.shapeRenderer.set(ShapeRenderer.ShapeType.Filled)
      game.shapeRenderer.rect(position.first * cellSize, position.second * cellSize, 100f, 100f)
    }
  }

  fun setToBuild(building: Int) {
    if (toBuild == building) {
      toBuild = -1
      return
    }

    toBuild = building
    color = makeColor(building)
  }

  private fun getCellPosition(x: Int, y: Int): Pair<Int, Int> {
    return Pair(
      MathUtils.floor(x / cellSize),
      MathUtils.floor(y / cellSize)
    )
  }

  private fun makeColor(seed: Int): Color {
    val random = Random(seed)

    return Color(random.nextFloat(), random.nextFloat(), random.nextFloat(), 1f)
  }

  override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
    val coord = getCellPosition(screenX, Gdx.graphics.height - screenY)
    if (world.addBuilding(coord.first, coord.second, toBuild) == null) {
      toBuild = -1
    }

    return true
  }
}
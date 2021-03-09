package com.tchaka.factorybuilder.flat_test

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.MathUtils
import com.tchaka.factorybuilder.FactoryBuilder

class Builder(
  private val game: FactoryBuilder,
  private val world: World
) : InputAdapter() {
  private var toBuild = -1
  private val cellSize = 100f
  private var color = Color.RED

  fun render() {
    if (toBuild >= 0) {
      val position = getCellPosition(Gdx.input.x, 0)

      if (world.hasBuilding(position.first, position.second))
        game.shapeRenderer.color = Color.RED
      else
        game.shapeRenderer.color = color

      game.shapeRenderer.set(ShapeRenderer.ShapeType.Filled)
      game.shapeRenderer.rect(position.first * cellSize, position.second * cellSize, 100f, 100f)
    }
  }

  fun setToBuild(type: Int) {
    if (toBuild == type) {
      toBuild = -1
      return
    }

    toBuild = type
    color = Core.makeColor(type)
  }

  private fun getCellPosition(x: Int, y: Int): Pair<Int, Int> {
    return Pair(
      MathUtils.floor(x / cellSize),
      MathUtils.floor(y / cellSize)
    )
  }

  override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
    if (toBuild < 0) return false

    val coord = getCellPosition(screenX, 0)
    if (world.addBuilding(coord.first, coord.second, toBuild) == null)
      toBuild = -1

    return true
  }
}
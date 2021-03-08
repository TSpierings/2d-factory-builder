package com.tchaka.factorybuilder.flat_test

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.tchaka.factorybuilder.FactoryBuilder
import kotlin.random.Random

class Builder(private val game: FactoryBuilder): InputAdapter() {
  private var toBuild = -1
  private val cellSize = 100f
  private var color = Color.RED

  fun render() {
   if (toBuild >= 0) {
     val position = Vector2(Gdx.input.x.toFloat(), Gdx.graphics.height - Gdx.input.y.toFloat())

     position.x = MathUtils.floor(position.x / cellSize) * cellSize
     position.y = MathUtils.floor(position.y / cellSize) * cellSize

     game.shapeRenderer.color = color
     game.shapeRenderer.set(ShapeRenderer.ShapeType.Filled)
     game.shapeRenderer.rect(position.x, position.y, 100f, 100f)
   }
  }

  fun setToBuild(building: Int) {
    if(toBuild == building) {
      toBuild = -1
      return
    }

    toBuild = building
    color = makeColor(building)
  }

  private fun makeColor(seed: Int): Color {
    val random = Random(seed)

    return Color(random.nextFloat(), random.nextFloat(), random.nextFloat(), 1f)
  }

  override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
    toBuild = -1
    return true
  }
}
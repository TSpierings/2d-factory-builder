package com.tchaka.factorybuilder.flat_test

import com.badlogic.gdx.graphics.Color
import kotlin.math.floor
import kotlin.random.Random

class Core(
  private val id: Int,
  private val type: Int,
  private val location: Int
) {
  private val productionSpeed = 1f
  private val bufferSize = 10

  private var productionProgress = 0f
  var outputBuffer = 0
  private var inputBuffer = 0
  private var reserved = 0

  val color = makeColor(type)

  fun update(delta: Float) {
    if (type == 0) return

    if (type == 1) inputBuffer = 1

    if (outputBuffer < bufferSize && inputBuffer > 0)
      productionProgress += delta

    while (productionProgress > productionSpeed) {
      productionProgress -= productionSpeed
      outputBuffer += 1
      inputBuffer -= 1
    }
  }

  fun resolveOrders(world: World) {
    if (inputBuffer + reserved >= bufferSize) return

    world.cells.forEach {
      if (it.value.type == type - 1 && it.value.outputBuffer > 0) {
        it.value.outputBuffer--
        reserved++

        world.orders.add(Order(world.graph.findPath(
          world.graph.getVertex(it.value.location % 100, floor(it.value.location / 100f).toInt())!!,
          world.graph.getVertex(location % 100, floor(location / 100f).toInt())!!
        ), it.value.type) {
          inputBuffer++
          reserved--
        })

        return
      }
    }
  }

  companion object {
    fun makeColor(seed: Int): Color {
      val random = Random(seed)

      return Color(random.nextFloat(), random.nextFloat(), random.nextFloat(), 1f)
    }
  }
}
package com.tchaka.factorybuilder.flat_test

import com.badlogic.gdx.ai.pfa.GraphPath
import com.badlogic.gdx.graphics.Color
import com.tchaka.factorybuilder.graph.Vertex
import kotlin.math.floor
import kotlin.random.Random

class Core(
  private val id: Int,
  val type: Int,
  val location: Int
) {
  private val productionSpeed = 1f
  private val bufferSize = 10

  private var productionProgress = 0f
  var outputBuffer = 0
  private var inputBuffer = 0
  private var reserved = 0

  var target: Core? = null
  var path: GraphPath<Vertex>? = null

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

  fun resolveOrdersManual(world: World): Int {
    if (target == null ||
        outputBuffer <= 0
//      target!!.type != type + 1 ||
//      target!!.inputBuffer + target!!.reserved >= target!!.bufferSize
    ) return 0

    outputBuffer--
    target!!.reserved++

    if (path == null) {
      val startVertex = world.graph.getVertex(location % 100, floor(location / 100f).toInt())!!
      val targetVertex = world.graph.getVertex(target!!.location % 100, floor(target!!.location / 100f).toInt())!!
      path = world.graph.findPath(startVertex, targetVertex)
    }

    world.orders.add(Order(path!!, type) {
      target!!.inputBuffer++
      target!!.reserved--
    })

    return 1
  }

  fun resolveOrdersAuto(world: World): Int {
    if (type < 2 || inputBuffer + reserved >= bufferSize) return 0

    val startVertex = world.graph.getVertex(location % 100, floor(location / 100f).toInt())!!

    val target = world.graph.findClosestOfType(
      world,
      startVertex,
      type - 1,
      5f
    ) ?: return 0

    if (target.outputBuffer <= 0) return 0

    target.outputBuffer--
    reserved++

    val targetVertex = world.graph.getVertex(target.location % 100, floor(target.location / 100f).toInt())!!
    world.orders.add(Order(world.graph.findPath(targetVertex, startVertex), type) {
      inputBuffer++
      reserved--
    })

    return 1
  }

  companion object {
    fun makeColor(seed: Int): Color {
      val random = Random(seed)

      return Color(random.nextFloat(), random.nextFloat(), random.nextFloat(), 1f)
    }
  }
}
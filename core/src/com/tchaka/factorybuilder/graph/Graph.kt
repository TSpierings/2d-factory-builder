package com.tchaka.factorybuilder.graph

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.MathUtils.floor
import com.badlogic.gdx.math.Vector2
import kotlin.system.measureTimeMillis

data class Edge(
  val a: Int,
  val b: Int,
  val cost: Float
)

class Graph {
  private var edges = mutableListOf<Edge>()
  private val indexedNodes = Array(100 * 100) { 0f }

  fun addNode(x: Int, y: Int) {
    indexedNodes[x + y * 100] = 1f
  }

  fun render(shapeRenderer: ShapeRenderer) {
    shapeRenderer.color = Color.BLACK

    indexedNodes.forEachIndexed loop@{ index, value ->
      if (value <= 0) return@loop

      shapeRenderer.circle(
        index % 100 * 100f + 50f,
        floor(index / 100f) * 100f + 50f, 10f,
        10
      )
    }

    edges.forEach {
      shapeRenderer.rectLine(
        Vector2(it.a % 100 * 100f + 50f, floor(it.a / 100f) * 100f + 50f),
        Vector2(it.b % 100 * 100f + 50f, floor(it.b / 100f) * 100f + 50f),
        2f
      )
    }
  }

  fun recalculateEdges() {
    val time = measureTimeMillis {
      edges = mutableListOf()

      var lastNodeIndex = -1
      for (x in 0 until 100) {
        for (y in 0 until 100) {
          if (indexedNodes[x + y * 100] <= 0) continue

          // Right neighbour
          if (x < 99 && indexedNodes[x + 1 + y * 100] > 0)
            edges.add(Edge(x + y * 100, x + 1 + y * 100, 1f))

          // Top neighbour
          if (y < 99 && indexedNodes[x + (y + 1) * 100] > 0)
            edges.add(Edge(x + y * 100, x + (y + 1) * 100, 1f))
        }

        if (lastNodeIndex >= 0 && lastNodeIndex != x && indexedNodes[x] > 0 && x - lastNodeIndex > 1) {
          edges.add(Edge(x, lastNodeIndex, 1f))
        }

        if (indexedNodes[x] > 0) lastNodeIndex = x
      }
    }
    Gdx.app.log("Graph", "Recalculate ${edges.size} edges in $time ms")
  }
}
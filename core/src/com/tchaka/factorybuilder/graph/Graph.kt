package com.tchaka.factorybuilder.graph

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.MathUtils.floor
import com.badlogic.gdx.math.Vector2
import kotlin.math.abs
import kotlin.system.measureTimeMillis

data class Edge(
  val a: Int,
  val b: Int,
  val cost: Float
)

data class Node(
  val x: Int,
  val y: Int
) {
  val edges = mutableListOf<Edge>()
}

class Graph {
  private val indexedNodes = arrayOfNulls<Node>(100 * 100)

  fun addNode(x: Int, y: Int) {
    indexedNodes[x + y * 100] = Node(x, y)
  }

  fun render(shapeRenderer: ShapeRenderer) {
    shapeRenderer.color = Color.BLACK

    indexedNodes.filterNotNull().forEach {
      shapeRenderer.circle(
        it.x * 100f + 50f,
        it.y * 100f + 50f, 10f,
        10
      )

      it.edges.forEach { edge ->
        shapeRenderer.rectLine(
          Vector2(edge.a % 100 * 100f + 50f, floor(edge.a / 100f) * 100f + 50f),
          Vector2(edge.b % 100 * 100f + 50f, floor(edge.b / 100f) * 100f + 50f),
          2f
        )
      }
    }
  }

  fun recalculateEdges() {
    val time = measureTimeMillis {
      var lastNodeIndex = -1
      for (x in 0 until 100) {
        for (y in 0 until 100) {
          if (indexedNodes[x + y * 100] == null) continue

          indexedNodes[x + y * 100]!!.edges.clear()

          // Right neighbour
          if (x < 99 && indexedNodes[x + 1 + y * 100] != null) {
            indexedNodes[x + y * 100]!!.edges += Edge(x + y * 100, x + 1 + y * 100, 1f)
          }

          // Top neighbour
          if (y < 99 && indexedNodes[x + (y + 1) * 100] != null) {
            indexedNodes[x + y * 100]!!.edges += Edge(x + y * 100, x + (y + 1) * 100, 1f)
          }
        }

        if (lastNodeIndex >= 0 && lastNodeIndex != x && indexedNodes[x] != null && x - lastNodeIndex > 1) {
          indexedNodes[x]!!.edges += Edge(x, lastNodeIndex, abs(x - lastNodeIndex).toFloat())
        }

        if (indexedNodes[x] != null) lastNodeIndex = x
      }
    }
    val edges = indexedNodes.filterNotNull().sumBy { it.edges.size }
    Gdx.app.log("Graph", "Recalculate ${edges} edges in $time ms")
  }

  fun findNearestType(x: Int, y: Int, type: Int) {

  }
}
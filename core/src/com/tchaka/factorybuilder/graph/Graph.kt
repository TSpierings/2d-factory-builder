package com.tchaka.factorybuilder.graph

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ai.pfa.Connection
import com.badlogic.gdx.ai.pfa.DefaultGraphPath
import com.badlogic.gdx.ai.pfa.GraphPath
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder
import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Array
import kotlin.math.abs
import kotlin.system.measureTimeMillis

class Graph: IndexedGraph<Vertex> {
  private val heuristic = VertexHeuristic()
  private val vertices = mutableListOf<Vertex>()
  private val edges = mutableListOf<Edge>()

  private val edgeMap = mutableMapOf<Vertex, Array<Connection<Vertex>>>()

  private var lastNodeIndex = 0
  private var lastPath: GraphPath<Vertex> = DefaultGraphPath()

  fun addNode(x: Int, y: Int) {
    vertices += Vertex(x, y, lastNodeIndex)
    lastNodeIndex++

    vertices.filter {
      abs((x - it.x) + (y - it.y)) < 2
    }.forEach {
      val distance = Vector2.dst2(it.x.toFloat(), it.y.toFloat(), x.toFloat(), y.toFloat())

      if (distance == 1f) {
        addConnection(vertices.last(), it)
        addConnection(it, vertices.last())
      }
    }

    println(measureTimeMillis {
      lastPath = findPath(vertices[0], vertices.last())
    })
  }

  fun addConnection(from: Vertex, to: Vertex) {
    val edge = Edge(from, to, 1f)

    if(edgeMap.putIfAbsent(from, Array.with(edge)) != null) {
      edgeMap[from]!!.add(edge)
    }

    edges += edge
  }

  fun findPath(start: Vertex, goal: Vertex): GraphPath<Vertex> {
    val vertexPath = DefaultGraphPath<Vertex>()
    IndexedAStarPathFinder(this).searchNodePath(start, goal, heuristic, vertexPath)
    return vertexPath
  }

  fun render(shapeRenderer: ShapeRenderer) {
    shapeRenderer.color = Color.BLACK

    vertices.forEach {
      shapeRenderer.circle(
        it.x * 100f + 50f,
        it.y * 100f + 50f, 10f,
        10
      )
    }

    edges.forEach {
      shapeRenderer.rectLine(
        Vector2(it.from.x * 100f + 50f, it.from.y * 100f + 50f),
        Vector2(it.to.x * 100f + 50f, it.to.y * 100f + 50f),
        2f
      )
    }

    shapeRenderer.color = Color.RED
    lastPath.forEach {
      shapeRenderer.circle(
        it.x * 100f + 50f,
        it.y * 100f + 50f, 10f,
        10
      )
    }
  }

  fun getVertex(x: Int, y: Int): Vertex? {
    return vertices.find { it.x == x && it.y == y }
  }

  override fun getIndex(node: Vertex): Int {
    return node.index
  }

  override fun getNodeCount(): Int {
    return vertices.count()
  }

  override fun getConnections(fromNode: Vertex): Array<Connection<Vertex>> {
    return edgeMap.getOrDefault(fromNode, Array())
  }
}
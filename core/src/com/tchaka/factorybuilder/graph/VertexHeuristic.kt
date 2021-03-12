package com.tchaka.factorybuilder.graph

import com.badlogic.gdx.ai.pfa.Heuristic
import com.badlogic.gdx.math.Vector2

class VertexHeuristic: Heuristic<Vertex> {
  override fun estimate(node: Vertex, endNode: Vertex): Float {
    return Vector2.dst2(node.x.toFloat(), node.y.toFloat(), endNode.x.toFloat(), endNode.y.toFloat())
  }
}
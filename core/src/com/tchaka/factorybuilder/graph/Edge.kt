package com.tchaka.factorybuilder.graph

import com.badlogic.gdx.ai.pfa.Connection

class Edge(
  val from: Vertex,
  val to: Vertex,
  val weight: Float
): Connection<Vertex> {
  override fun getCost(): Float {
    return weight
  }

  override fun getFromNode(): Vertex {
    return from
  }

  override fun getToNode(): Vertex {
    return to
  }
}
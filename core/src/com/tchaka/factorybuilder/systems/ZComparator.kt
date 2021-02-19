package com.tchaka.factorybuilder.systems

import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.tchaka.factorybuilder.components.TransformComponent

class ZComparator : Comparator<Entity> {
  private val transformComponentMapper = ComponentMapper.getFor(TransformComponent::class.java)

  override fun compare(o1: Entity?, o2: Entity?): Int {
    val az = transformComponentMapper.get(o1).position.z
    val bz = transformComponentMapper.get(o2).position.z

    return when {
      az > bz -> 1
      az < bz -> -1
      else -> 0
    }
  }
}
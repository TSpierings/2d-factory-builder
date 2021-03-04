package com.tchaka.factorybuilder.systems

import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.math.Vector3
import com.tchaka.factorybuilder.components.CellComponent
import com.tchaka.factorybuilder.components.TransformComponent

class CellRenderingSystem : IteratingSystem(
  Family.all(
    CellComponent::class.java
  ).get()
) {
  override fun processEntity(entity: Entity, deltaTime: Float) {
    entity.getComponent(TransformComponent::class.java).rotation += 0.01f
  }

  companion object {
    private val transformMapper = ComponentMapper.getFor(TransformComponent::class.java)
    private val cellMapper = ComponentMapper.getFor(CellComponent::class.java)
    private val spriteBatch = SpriteBatch()

    private val tex = Texture("test_tex_2.png")

    fun renderCells(cells: ArrayList<Entity>, camera: Camera) {
      camera.update()
      spriteBatch.projectionMatrix = camera.combined
      spriteBatch.begin()
      spriteBatch.enableBlending()

      val transform = transformMapper.get(cells[0])
      spriteBatch.transformMatrix = Matrix4()
        .translate(transform.position)
        .rotate(
          Vector3(0f, 0f, 1f),
          transform.rotation
        )

      cells.forEach {
        val cell = cellMapper.get(it)
        spriteBatch.draw(tex, cell.vertices, 0, cell.vertices.count())
      }

      spriteBatch.end()
    }
  }
}
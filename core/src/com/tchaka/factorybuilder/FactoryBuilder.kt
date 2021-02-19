package com.tchaka.factorybuilder

import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.ScreenUtils
import com.tchaka.factorybuilder.systems.RenderingSystem

class FactoryBuilder : ApplicationAdapter() {
  private lateinit var renderingSystem: RenderingSystem
  private lateinit var camera: OrthographicCamera
  private val moveSpeed = 100f
  private val engine = PooledEngine()

  override fun create() {
    println("create..")
    renderingSystem = RenderingSystem()
    camera = renderingSystem.Camera
    engine.addSystem(renderingSystem)

    engine.addEntity(PlanetFactory.create(engine))
  }

  override fun render() {
    if (Gdx.input.isKeyPressed(Input.Keys.W)) {
      camera.translate(0f, moveSpeed * Gdx.graphics.deltaTime)
    } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
      camera.translate(0f, -moveSpeed * Gdx.graphics.deltaTime)
    }

    if (Gdx.input.isKeyPressed(Input.Keys.A)) {
      camera.translate(-moveSpeed * Gdx.graphics.deltaTime, 0f)
    } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
      camera.translate(moveSpeed * Gdx.graphics.deltaTime, 0f)
    }

    Gdx.gl.glClearColor(1f, 1f, 1f, 1f)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

    engine.update(Gdx.graphics.deltaTime)
  }

  override fun dispose() {
    super.dispose()
  }
}
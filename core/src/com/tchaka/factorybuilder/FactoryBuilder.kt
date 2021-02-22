package com.tchaka.factorybuilder

import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.tchaka.factorybuilder.factories.CellFactory
import com.tchaka.factorybuilder.factories.PlanetFactory
import com.tchaka.factorybuilder.systems.CellRenderingSystem
import com.tchaka.factorybuilder.systems.PlanetRenderingSystem
import com.tchaka.factorybuilder.systems.RenderingSystem

class FactoryBuilder : ApplicationAdapter() {
  private lateinit var renderingSystem: RenderingSystem
  private lateinit var planetRenderingSystem: PlanetRenderingSystem
  private lateinit var celLRenderingSystem: CellRenderingSystem
  private lateinit var camera: OrthographicCamera
  private val moveSpeed = 25f
  private val engine = PooledEngine()

  override fun create() {
    println("create..")
    renderingSystem = RenderingSystem()
    camera = renderingSystem.Camera
    camera.zoom = 0.01f
    camera.translate(0f, 100f)
//    engine.addSystem(renderingSystem)

    planetRenderingSystem = PlanetRenderingSystem(camera)
    celLRenderingSystem = CellRenderingSystem(camera)

    engine.addSystem(planetRenderingSystem)
    engine.addSystem(celLRenderingSystem)

    engine.addEntity(PlanetFactory.create(engine))
    engine.addEntity(CellFactory.create(engine, 0f))
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

    if (Gdx.input.isKeyPressed(Input.Keys.NUMPAD_ADD)) {
      camera.zoom = 0.01f.coerceAtLeast(camera.zoom - 0.005f)
    } else if (Gdx.input.isKeyPressed(Input.Keys.NUMPAD_SUBTRACT)) {
      camera.zoom += 0.005f
    }

    Gdx.gl.glClearColor(1f, 1f, 1f, 1f)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

    engine.update(Gdx.graphics.deltaTime)
  }

  override fun dispose() {
    super.dispose()
  }
}
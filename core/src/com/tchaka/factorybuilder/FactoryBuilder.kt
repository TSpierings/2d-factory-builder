package com.tchaka.factorybuilder

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.tchaka.factorybuilder.components.PlanetComponent
import com.tchaka.factorybuilder.components.PlanetaryBodyComponent
import com.tchaka.factorybuilder.factories.CellFactory
import com.tchaka.factorybuilder.factories.PlanetFactory
import com.tchaka.factorybuilder.systems.CellRenderingSystem
import com.tchaka.factorybuilder.systems.PlanetRenderingSystem
import java.util.*
import kotlin.system.measureTimeMillis

class FactoryBuilder : ApplicationAdapter() {
  private lateinit var planetRenderingSystem: PlanetRenderingSystem
  private lateinit var celLRenderingSystem: CellRenderingSystem
  private lateinit var camera: OrthographicCamera
  private val moveSpeed = 25f
  private val engine = PooledEngine()
  private lateinit var font: BitmapFont
  private lateinit var batch: SpriteBatch

  private val pixelsPerMetre = 1.0f
  private var frustumHeight = 0.0f
  private var frustumWidth = 0.0f

  override fun create() {
    font = BitmapFont()
    batch = SpriteBatch()

    createCamera()

    println("create..")
    camera.zoom = 0.01f
    camera.translate(0f, 100f)

    planetRenderingSystem = PlanetRenderingSystem(camera)
    celLRenderingSystem = CellRenderingSystem()

    engine.addSystem(planetRenderingSystem)
    engine.addSystem(celLRenderingSystem)

    for (i in 0 until 1) {
      val planet = PlanetFactory.create(engine, i)
      engine.addEntity(planet)
      fillPlanetWithCells(planet)
    }
  }

  fun createCamera() {
    frustumHeight = Gdx.graphics.height / pixelsPerMetre
    frustumWidth = Gdx.graphics.width / pixelsPerMetre

    camera = OrthographicCamera(frustumWidth, frustumHeight)
    camera.position.set(0f, 0f, 0f)
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

    val elapsed = measureTimeMillis {
      engine.update(Gdx.graphics.deltaTime)
    }

    batch.begin()
    font.draw(batch, Gdx.graphics.framesPerSecond.toString() + " fps", 10f, Gdx.graphics.height - 10f)
    font.draw(batch, "$elapsed ms", 10f, Gdx.graphics.height - 30f)
    batch.end()
  }

  private fun fillPlanetWithCells(planet: Entity) {
    val data = planet.getComponent(PlanetaryBodyComponent::class.java)

    for (i in 0 until (data.size * 10).toInt()) {
      for(h in 0 until 50) {
        val cell = CellFactory.create(engine, h, i, planet)
        engine.addEntity(cell)
        planet.getComponent(PlanetComponent::class.java).slots.add(cell)
      }
    }
  }

  override fun dispose() {
    super.dispose()
  }
}
package com.tchaka.factorybuilder

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.tchaka.factorybuilder.components.PlanetComponent
import com.tchaka.factorybuilder.components.StarComponent
import com.tchaka.factorybuilder.factories.CellFactory
import com.tchaka.factorybuilder.factories.PlanetFactory
import com.tchaka.factorybuilder.systems.CellRenderingSystem
import com.tchaka.factorybuilder.systems.PlanetRenderingSystem
import kotlin.system.measureTimeMillis

class FactoryBuilder : ApplicationAdapter() {
  private lateinit var planetRenderingSystem: PlanetRenderingSystem
  private lateinit var celLRenderingSystem: CellRenderingSystem
  private lateinit var camera: OrthographicCamera
  private val moveSpeed = 250f
  private val engine = PooledEngine()
  private lateinit var batch: SpriteBatch

  private val pixelsPerMetre = 32.0f
  private var frustumHeight = 0.0f
  private var frustumWidth = 0.0f

  private lateinit var stage: Stage
  private lateinit var fpsCounter: Label
  private lateinit var renderTime: Label

  private lateinit var table: Table

  override fun create() {
    batch = SpriteBatch()

    createCamera()

    println("create..")
    camera.zoom = 10f
    camera.translate(0f, 0f)

    planetRenderingSystem = PlanetRenderingSystem(camera)
    celLRenderingSystem = CellRenderingSystem()

    engine.addSystem(planetRenderingSystem)
    engine.addSystem(celLRenderingSystem)

    createStar()

//    for (i in 0 until 1) {
//      val planet = PlanetFactory.create(engine)
//      engine.addEntity(planet)
//      fillPlanetWithCells(planet)
//    }

    stage = Stage(ScreenViewport())
    Gdx.input.inputProcessor = stage
    val skin = Skin(Gdx.files.internal("skin/uiskin.json"))

    table = Table(skin)
    table.align(Align.topLeft)
    table.pad(10f)

    fpsCounter = Label("0 fps", skin)
    renderTime = Label("0 ms", skin)

    val debugButton = TextButton("Debug", skin)
    debugButton.addListener(object : ClickListener() {
      override fun clicked(event: InputEvent?, x: Float, y: Float) {
        super.clicked(event, x, y)
        table.setDebug(!table.debug, true)
      }
    })

    table.add(debugButton).align(Align.topLeft)
    table.row()
    table.add(fpsCounter).align(Align.topLeft)
    table.row()
    table.add(renderTime).align(Align.topLeft)

    stage.addActor(table)
  }

  private fun createCamera() {
    frustumHeight = Gdx.graphics.height / pixelsPerMetre
    frustumWidth = Gdx.graphics.width / pixelsPerMetre

    camera = OrthographicCamera(frustumWidth, frustumHeight)
    camera.position.set(0f, 0f, 0f)
  }

  override fun resize(width: Int, height: Int) {
    super.resize(width, height)

    stage.viewport.update(width, height, true)
    table.width = Gdx.graphics.width.toFloat()
    table.height = Gdx.graphics.height.toFloat()

    frustumHeight = Gdx.graphics.height / pixelsPerMetre
    frustumWidth = Gdx.graphics.width / pixelsPerMetre
    camera.viewportHeight = frustumHeight
    camera.viewportWidth = frustumWidth
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
      camera.zoom = 0.01f.coerceAtLeast(camera.zoom - 0.5f)
    } else if (Gdx.input.isKeyPressed(Input.Keys.NUMPAD_SUBTRACT)) {
      camera.zoom += 0.5f
    }

    Gdx.gl.glClearColor(1f, 1f, 1f, 1f)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

    val elapsed = measureTimeMillis {
      engine.update(Gdx.graphics.deltaTime)
    }

    fpsCounter.setText("${Gdx.graphics.framesPerSecond} fps")
    renderTime.setText("$elapsed ms")

    stage.act(Gdx.graphics.deltaTime)
    stage.draw()
  }

  private fun createStar() {
    for (i in 0 until 1) {
      val star = engine.createEntity()
      val starComponent = engine.createComponent(StarComponent::class.java)
      starComponent.size = 200f
      val planetCount = 3
      starComponent.planets = ArrayList(planetCount)

      for (p in 0 until planetCount) {
        val distance = 500f * p
        val planet = PlanetFactory.create(engine, distance)
        starComponent.planets.add(planet)
        engine.addEntity(planet)
//         fillPlanetWithCells(planet)
      }

      star.add(starComponent)
      engine.addEntity(star)
    }
  }

  private fun fillPlanetWithCells(planet: Entity) {
    val data = planet.getComponent(PlanetComponent::class.java)

    for (i in 0 until (data.size * 10).toInt()) {
      for (h in 0 until 10) {
        val cell = CellFactory.create(engine, h, i, planet)
        engine.addEntity(cell)
        planet.getComponent(PlanetComponent::class.java).cells.add(cell)
      }
    }
  }

  override fun dispose() {
    super.dispose()
    stage.dispose()
  }
}
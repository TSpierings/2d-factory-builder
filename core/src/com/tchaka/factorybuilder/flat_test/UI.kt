package com.tchaka.factorybuilder.flat_test

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.viewport.ScreenViewport

class UI(
  private val builder: Builder,
  private val planner: Planner) {
  private lateinit var stage: Stage
  private lateinit var table: Table
  private val skin = Skin(Gdx.files.internal("skin/uiskin.json"))
  private var fpsLabel = Label("", skin)

  fun create() {
    stage = Stage(ScreenViewport())
    Gdx.input.inputProcessor = InputMultiplexer(stage, builder, planner)

    table = Table(skin)
    table.align(Align.topLeft)
    table.pad(10f)

    table.add(fpsLabel).align(Align.topLeft)
    table.row()

    val debugButton = TextButton("Debug", skin)
    debugButton.addListener(object : ClickListener() {
      override fun clicked(event: InputEvent?, x: Float, y: Float) {
        super.clicked(event, x, y)
        table.setDebug(!table.debug, true)
      }
    })

    table.add(debugButton).align(Align.topLeft)

    for (i in 0 until 5) {
      table.row()
      table.add(createBuildingButton(i)).align(Align.topLeft)
    }

    debugLog.forEach {
      table.row()
      val label = Label("", skin)
      debugLog[it.key] = label
      table.add(label).align(Align.topLeft)
    }

    stage.addActor(table)
  }

  fun resize(width: Int, height: Int) {
    stage.viewport.update(width, height, true)
    table.width = Gdx.graphics.width.toFloat()
    table.height = Gdx.graphics.height.toFloat()
  }

  fun render(delta: Float) {
    fpsLabel.setText(Gdx.graphics.framesPerSecond)

    stage.act(delta)
    stage.draw()
  }

  private fun createBuildingButton(index: Int): TextButton {
    val building = TextButton("Building $index", skin)
    building.addListener(object : ClickListener() {
      override fun clicked(event: InputEvent?, x: Float, y: Float) {
        super.clicked(event, x, y)
        builder.setToBuild(index)
      }
    })

    return building
  }

  companion object {
    val debugLog = mutableMapOf<String, Label?>(
      Pair("updateTime", null),
      Pair("orderCount", null)
    )
  }
}
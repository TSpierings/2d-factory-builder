package com.tchaka.factorybuilder

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
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
import com.tchaka.factorybuilder.screens.GameScreen
import com.tchaka.factorybuilder.screens.TestScreen
import com.tchaka.factorybuilder.systems.CellRenderingSystem
import com.tchaka.factorybuilder.systems.PlanetRenderingSystem
import kotlin.system.measureTimeMillis

class FactoryBuilder : Game() {
  lateinit var batch: SpriteBatch
  lateinit var shapeRenderer: ShapeRenderer

  override fun create() {
    batch = SpriteBatch()
    shapeRenderer = ShapeRenderer()
    setScreen(TestScreen(this))
  }
}
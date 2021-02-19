package com.tchaka.factorybuilder.desktop

import kotlin.jvm.JvmStatic
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.tchaka.factorybuilder.FactoryBuilder

object DesktopLauncher {
  @JvmStatic
  fun main(arg: Array<String>) {
    val config = LwjglApplicationConfiguration()
    config.width = 1920
    config.height = 1080
    LwjglApplication(FactoryBuilder(), config)
  }
}
package com.cout970.fira.gui.config

abstract class ConfigOption {
    abstract val height: Int
    abstract val name: String
    abstract val description: String

    abstract fun draw()

    open fun click(mouseX: Int, mouseY: Int, mouseButton: Int) {}
    open fun outsideClick(mouseButton: Int) {}
    open fun hover(mouseX: Int, mouseY: Int): List<String> = listOf(name, description)
    open fun keyTyped(typedChar: Char, keyCode: Int): Boolean = false
}
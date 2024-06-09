package com.cout970.fira.gui.config

import com.cout970.fira.util.Utils.color
import kotlin.math.max

class ConfigOptionButton(
        override val name: String,
        override val description: String,
        val function: () -> Unit
) : ConfigOption() {
    override val height: Int = CELL_HEIGHT
    var animation = 1f

    override fun draw() {
        animation = max(animation - ModuleConfig.delta * 0.1f, 0f)

        val color = color((0xFF * animation).toInt() shl 8, animation / 2f)

        drawBox(0, 0, CELL_WIDTH, CELL_HEIGHT, color)
        drawString(name, 4, 4, color(0xFFFFFF))
    }

    override fun click(mouseX: Int, mouseY: Int, mouseButton: Int) {
        if (mouseButton == 0) {
            animation = 1f
            function()
        }
    }
}
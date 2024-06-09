package com.cout970.fira.gui.config

import com.cout970.fira.util.Proxy
import com.cout970.fira.util.Utils
import org.lwjgl.input.Mouse

class ConfigOptionSlider(
        override val name: String,
        override val description: String,
        val min: Float,
        val max: Float,
        proxy: Proxy<Float>
) : ConfigOption() {
    override val height: Int = CELL_HEIGHT
    var value: Float by proxy
    var selected = false
    val slideWidth = CELL_WIDTH - 8

    override fun draw() {
        if (!Mouse.isButtonDown(0)) {
            selected = false
        }

        val percent = (value - min) / (max - min)
        val sizeX = (CELL_WIDTH * percent).toInt()

        drawBox(0, 0, sizeX, CELL_HEIGHT, Utils.color(0x007F00, 0.5f))

        if (selected) {
            val inverseSizeX = CELL_WIDTH - sizeX
            drawBox(sizeX, 0, inverseSizeX, CELL_HEIGHT, Utils.color(0x7F0000, 0.5f))
        }

        val valueLen = getStringWidth("%.1f".format(value))
        var title = name

        if (getStringWidth(name) + valueLen > CELL_WIDTH - 10) {
            var nameLength = name.length
            while (getStringWidth(title) + valueLen > CELL_WIDTH - 10 && nameLength > 2) {
                nameLength -= 2
                title = name.substring(0, nameLength) + "..."
            }
        }

        drawString(title, 4, 4, Utils.color(0xFFFFFF))
        drawString("%.1f".format(value), CELL_WIDTH - valueLen - 4, 4, Utils.color(0xFFFFFF))
    }

    override fun click(mouseX: Int, mouseY: Int, mouseButton: Int) {
        val percent = ((mouseX - 4f) / slideWidth).coerceIn(0f, 1f)
        value = min + (max - min) * percent
        selected = mouseButton == 0
    }

    override fun hover(mouseX: Int, mouseY: Int): List<String> {
        return if (selected) {
            val percent = ((mouseX - 4f) / slideWidth).coerceIn(0f, 1f)
            value = min + (max - min) * percent
            emptyList()
        } else {
            super.hover(mouseX, mouseY)
        }
    }
}
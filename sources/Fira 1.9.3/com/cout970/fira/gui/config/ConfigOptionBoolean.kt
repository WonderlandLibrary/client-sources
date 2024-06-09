package com.cout970.fira.gui.config

import com.cout970.fira.util.Proxy
import com.cout970.fira.util.Utils.color
import net.minecraft.client.resources.I18n

class ConfigOptionBoolean(
        override val name: String,
        override val description: String,
        proxy: Proxy<Boolean>
) : ConfigOption() {
    override val height: Int = CELL_HEIGHT
    var value: Boolean by proxy

    override fun draw() {
        val color = if (value) color(0x007F00, 0.5f) else 0x00
        val text = if (value) {
            I18n.format("text.fira_client.boolean_options.on")
        } else {
            I18n.format("text.fira_client.boolean_options.off")
        }

        drawBox(0, 0, CELL_WIDTH, CELL_HEIGHT, color)
        drawString(name, 4, 4, color(0xFFFFFF))
        drawString(text, CELL_WIDTH - 4 - getStringWidth(text), 4, color(0xFFFFFF))
    }

    override fun click(mouseX: Int, mouseY: Int, mouseButton: Int) {
        value = !value
    }
}
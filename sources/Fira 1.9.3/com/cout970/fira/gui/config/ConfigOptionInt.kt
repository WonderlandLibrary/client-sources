package com.cout970.fira.gui.config

import com.cout970.fira.util.Proxy
import com.cout970.fira.util.Utils
import com.cout970.fira.util.Utils.color
import net.minecraft.client.gui.GuiTextField

class ConfigOptionInt(
        override val name: String,
        override val description: String,
        proxy: Proxy<Int>
) : ConfigOption() {
    override val height: Int = CELL_HEIGHT
    var value: Int by proxy
    var selected = false
    val text: GuiTextField by lazy {
        GuiTextField(0, Utils.font(), CELL_WIDTH / 2, 0, CELL_WIDTH / 2, CELL_HEIGHT).also {
            it.enableBackgroundDrawing = false
        }
    }

    override fun draw() {
        var title = name
        var valueLen = getStringWidth(value.toString()) + 4

        if (selected) {
            drawBox(CELL_WIDTH * 3 / 4, 0, CELL_WIDTH / 4, CELL_HEIGHT, color(0x007F00, 0.5f))
            text.y = 4
            text.x = CELL_WIDTH * 3 / 4 + 4
            text.width = CELL_WIDTH / 4 - 8
            text.drawTextBox()
            valueLen = text.width
        }

        if (getStringWidth(name) + valueLen > CELL_WIDTH - 10) {
            var nameLength = name.length
            while (getStringWidth(title) + valueLen > CELL_WIDTH - 10 && nameLength > 2) {
                nameLength -= 2
                title = name.substring(0, nameLength) + "..."
            }
        }

        drawString(title, 4, 4, color(0xFFFFFF))
        if (!selected) {
            drawString(value.toString(), CELL_WIDTH - getStringWidth(value.toString()) - 4, 4, color(0xFFFFFF))
        }
    }

    fun unSelect() {
        value = text.text.toIntOrNull() ?: value
        selected = false
    }

    override fun click(mouseX: Int, mouseY: Int, mouseButton: Int) {
        if (!selected) {
            selected = true
            text.text = value.toString()
        }
        text.mouseClicked(mouseX, mouseY, mouseButton)
        super.click(mouseX, mouseY, mouseButton)
    }

    override fun outsideClick(mouseButton: Int) {
        unSelect()
    }

    override fun keyTyped(typedChar: Char, keyCode: Int): Boolean {
        if (!selected) return false

        if (keyCode == 28 /*ENTER*/ || keyCode == 1 /*ESC*/) {
            unSelect()
        } else {
            text.textboxKeyTyped(typedChar, keyCode)
            return true
        }
        return false
    }

    override fun hover(mouseX: Int, mouseY: Int): List<String> {
        if (selected) return emptyList()
        return super.hover(mouseX, mouseY)
    }
}
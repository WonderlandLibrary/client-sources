package com.cout970.fira.gui.config

import com.cout970.fira.util.Proxy
import com.cout970.fira.util.Utils
import net.minecraft.client.resources.I18n
import net.minecraft.client.settings.KeyBinding
import org.lwjgl.input.Keyboard

class ConfigOptionKeybind(
        override val name: String,
        override val description: String,
        proxy: Proxy<KeyBinding>
) : ConfigOption() {
    override val height: Int = CELL_HEIGHT
    var value: KeyBinding by proxy
    var selected = false

    override fun draw() {
        var title = name
        val text = when {
            selected -> " Press key "
            value.keyCode == Keyboard.KEY_ESCAPE -> I18n.format("text.key_none.name")
            else -> value.displayName
        }

        val textLen = getStringWidth(text) + 4

        if (selected) {
            drawBox(CELL_WIDTH - textLen, 0, textLen, CELL_HEIGHT, Utils.color(0x007F00, 0.5f))
        }

        if (getStringWidth(title) > CELL_WIDTH - textLen) {
            var nameLength = name.length
            val padding = getStringWidth("...")
            while (getStringWidth(title) + padding > CELL_WIDTH - textLen && nameLength > 2) {
                nameLength -= 2
                title = name.substring(0, nameLength) + "..."
            }
        }

        drawString(title, 4, 4, Utils.color(0xFFFFFF))
        drawRightString(text, CELL_WIDTH, -4, 4, Utils.color(0xFFFFFF))
    }

    override fun click(mouseX: Int, mouseY: Int, mouseButton: Int) {
        if (!selected) {
            selected = true
        }

        // Disable keybind
        if (mouseButton == 1) {
            selected = false
            value.keyCode = Keyboard.KEY_ESCAPE
            Utils.mc.gameSettings.saveOptions()
        }

        super.click(mouseX, mouseY, mouseButton)
    }

    override fun outsideClick(mouseButton: Int) {
        selected = false
    }

    override fun keyTyped(typedChar: Char, keyCode: Int): Boolean {
        if (!selected) return false

        if (keyCode == 28 /*ENTER*/) {
            selected = false
        } else {
            value.keyCode = keyCode
            Utils.mc.gameSettings.saveOptions()
            selected = false
            return true
        }

        return false
    }

    override fun hover(mouseX: Int, mouseY: Int): List<String> {
        if (selected) return emptyList()
        return super.hover(mouseX, mouseY)
    }
}
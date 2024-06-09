package com.cout970.fira.gui

import com.cout970.fira.Config
import com.cout970.fira.gui.config.ModuleConfig
import com.cout970.fira.util.Utils
import net.minecraft.client.gui.GuiScreen
import net.minecraft.client.renderer.GlStateManager.*
import org.lwjgl.input.Keyboard


class GuiModuleConfig : GuiScreen() {
    var animationTimer = 0f

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        ModuleConfig.mouseX = mouseX
        ModuleConfig.mouseY = mouseY

        val now = System.nanoTime() / 50_000_000.0f
        ModuleConfig.delta = now - animationTimer
        animationTimer = now

        ModuleConfig.config.forEach { column ->
            pushMatrix()
            translate(column.posX, column.posY, 0f)
            column.draw(width, height)
            popMatrix()
        }

        // Tooltips
        ModuleConfig.getColumnUnderCursor(mouseX, mouseY)?.let { (mX, mY, col) ->
            col.getModuleUnderCursor(mX, mY)?.let { (mX, mY, mod) ->
                mod.getOptionUnderMouse(mX, mY)?.let { (mX, mY, option) ->
                    val lines = option.hover(mX, mY)

                    if (lines.isNotEmpty()) {
                        this.drawHoveringText(lines, mouseX, mouseY)
                    }
                }
            }
        }

        super.drawScreen(mouseX, mouseY, partialTicks)
    }

    override fun mouseClicked(mouseX: Int, mouseY: Int, mouseButton: Int) {
        ModuleConfig.mouseX = mouseX
        ModuleConfig.mouseY = mouseY

        val colSelection = ModuleConfig.getColumnUnderCursor(mouseX, mouseY)
        val modSelection = colSelection?.let { (x, y, col) -> col.getModuleUnderCursor(x, y) }
        val optSelection = modSelection?.let { (x, y, mod) -> mod.getOptionUnderMouse(x, y) }

        // Notify options that a click happened elsewhere
        ModuleConfig.config.forEach { col ->
            col.modules.forEach { mods ->
                mods.options.forEach {
                    if (optSelection?.third != it) it.outsideClick(mouseButton)
                }
            }
        }

        // Actual click
        val (x, y, col) = colSelection ?: return
        col.click(x, y, mouseButton)
    }

    override fun doesGuiPauseGame(): Boolean {
        return false
    }

    override fun keyTyped(typedChar: Char, keyCode: Int) {
        ModuleConfig.config.forEach { col ->
            col.modules.forEach { mods ->
                mods.options.forEach {
                    if (it.keyTyped(typedChar, keyCode)) {
                        return
                    }
                }
            }
        }
        if (keyCode == Config.HudColors.toggleModuleConfig.keyCode) {
            mc.displayGuiScreen(null)
            if (mc.currentScreen == null) {
                mc.setIngameFocus()
            }
            return
        }

        if (keyCode == Keyboard.KEY_F7) {
            Utils.mc.textureManager.onResourceManagerReload(Utils.mc.resourceManager)
        }
        super.keyTyped(typedChar, keyCode)
    }
}
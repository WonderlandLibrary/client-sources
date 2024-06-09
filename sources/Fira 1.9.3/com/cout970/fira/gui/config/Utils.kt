package com.cout970.fira.gui.config

import com.cout970.fira.MOD_ID
import com.cout970.fira.util.Utils
import net.minecraft.client.gui.Gui
import net.minecraft.util.ResourceLocation

const val COLUMN_WIDTH: Int = 150
const val CELL_WIDTH: Int = 148
const val CELL_HEIGHT: Int = 15

fun drawString(txt: String, x: Int, y: Int, color: Int) {
    Utils.font().drawString(txt, x, y, color)
}

fun drawCenteredString(text: String, x: Int, y: Int, color: Int) {
    Utils.font().drawStringWithShadow(text, (x - getStringWidth(text) / 2).toFloat(), y.toFloat(), color)
}

fun drawRightString(text: String, max: Int, x: Int, y: Int, color: Int) {
    Utils.font().drawStringWithShadow(text, max.toFloat() - getStringWidth(text) + x, y.toFloat(), color)
}

fun getStringWidth(txt: String): Int = Utils.font().getStringWidth(txt)

fun drawBorder(sizeX: Int, sizeY: Int, color: Int) {
    Gui.drawRect(0, 0, sizeX, 1, color)
    Gui.drawRect(0, sizeY - 1, sizeX, sizeY, color)
    Gui.drawRect(0, 0, 1, sizeY, color)
    Gui.drawRect(sizeX - 1, 1, sizeX, sizeY, color)
}

fun drawBox(posX: Int, posY: Int, sizeX: Int, sizeY: Int, color: Int) {
    Gui.drawRect(posX, posY, posX + sizeX, posY + sizeY, color)
}

fun drawTexturedBox(posX: Int, posY: Int, sizeX: Int, sizeY: Int, texture: String, scale: Float = 16f) {
    Utils.mc.textureManager.bindTexture(ResourceLocation(MOD_ID, texture))
    Gui.drawScaledCustomSizeModalRect(posX, posY, 0f, 0f, scale.toInt(), scale.toInt(), posX + sizeX, posY + sizeY, scale, scale)
}
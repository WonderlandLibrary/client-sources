package com.cout970.fira.gui.config

import com.cout970.fira.util.Utils
import net.minecraft.client.renderer.GlStateManager.*
import net.minecraft.nbt.NBTTagCompound
import org.lwjgl.input.Keyboard
import org.lwjgl.input.Mouse
import kotlin.math.max
import kotlin.math.min

class ModuleConfigColumn(val name: String, val modules: List<ModuleConfigModule>) {

    var open: Boolean = true
    var openPercent: Float = 100f
    var posX: Float = 0f
    var posY: Float = 0f

    val width: Int = COLUMN_WIDTH
    val height: Int get() = CELL_HEIGHT + if (open) modules.sumBy { it.height } else 0
    var dragStart: Pair<Float, Float>? = null
    var dragStartClick: Pair<Int, Int> = 0 to 0

    fun draw(screenWidth: Int, screenHeight: Int) {
        val headerSizeY = CELL_HEIGHT
        val padding = (headerSizeY - Utils.font().FONT_HEIGHT) / 2

        dragStart?.let { dragStart ->
            if (!Mouse.isButtonDown(0)) {
                this.dragStart = null
                ModuleConfig.saveConfig()
            } else {
                posX = dragStart.first + (ModuleConfig.mouseX - this.dragStartClick.first)
                posY = dragStart.second + (ModuleConfig.mouseY - this.dragStartClick.second)

                if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
                    val xMod = 4
                    val yMod = 4
                    posX = (posX.toInt() / xMod * xMod).toFloat()
                    posY = (posY.toInt() / yMod * yMod).toFloat()
                }

                posX = posX.coerceIn(0f, screenWidth.toFloat() - width)
                posY = posY.coerceIn(0f, screenHeight.toFloat() - height)
            }
        }

        // Column header background
        drawBox(0, 0, width, headerSizeY, Utils.color(0x000000, 0.75f))
        // Column header name
        drawCenteredString(name, width / 2, padding, Utils.color(0xFFFFFF))

        if (open) {
            // Update animation
            if (openPercent < 100f) {
                openPercent = min(openPercent + 20f * ModuleConfig.delta, 100f)
            }
        } else {
            // Update animation
            if (openPercent > 0f) {
                openPercent = max(openPercent - 20f * ModuleConfig.delta, 0f)
            }
        }

        if (openPercent > 0) {
            // Render Dropdown
            translate(0f, headerSizeY.toFloat(), 0f)
            // Selection mark
            drawBox(0, -2, width, 2, Utils.color(0xFF0000))
            // Rest of the dropdown
            drawDropdown(openPercent / 100f, width, modules)
        }
    }

    private fun drawDropdown(percent: Float, sizeX: Int, modules: List<ModuleConfigModule>) {
        val sizeY = (modules.sumBy { it.height } * percent).toInt()
        var pos = 0

        // Background
        drawBox(0, 0, sizeX, sizeY, Utils.color(0x000000, 0.5f))

        pushMatrix()
        for (mod in modules) {
            val modSizeY = mod.height
            if (sizeY < pos + modSizeY) break

            // Background
            val enable = mod.enableOption?.value != false
            val backgroundColor = if (enable) Utils.color(0xFF0000, 0.6f) else Utils.color(0xFF0000, 0.15f)
            drawBox(0, 0, sizeX, CELL_HEIGHT, backgroundColor)

            // Module name
            val textColor = if (enable) Utils.color(0xFFFFFF) else Utils.color(0x777777)
            drawCenteredString(mod.name, sizeX / 2, 4, textColor)

            // Open/Close icon
            val tex = if (mod.open) "textures/open.png" else "textures/close.png"
            drawTexturedBox(0, 0, 15, 15, tex, 15f)

            // Top line
            drawBox(0, 0, sizeX, 1, Utils.color(0x222222))

            // Module options
            if (mod.open) {
                pushMatrix()
                translate(0f, CELL_HEIGHT.toFloat(), 0f)
                // left vertical bar
                drawBox(0, 0, 2, mod.height - CELL_HEIGHT, Utils.color(0xFFFFFF))

                mod.options.forEachIndexed { index, opt ->
                    pushMatrix()
                    translate((COLUMN_WIDTH - CELL_WIDTH).toFloat(), 0f, 0f)
                    opt.draw()
                    popMatrix()

                    // option separator bar
                    if (index != 0) {
                        drawBox(COLUMN_WIDTH - CELL_WIDTH, 0, CELL_WIDTH, 1, Utils.color(0x0, 0.15f))
                    }

                    translate(0f, opt.height.toFloat(), 0f)
                }
                popMatrix()

            }
            // module header bottom bar
            drawBox(0, CELL_HEIGHT, COLUMN_WIDTH, 1, Utils.color(0x0, 1f))

            translate(0f, modSizeY.toFloat(), 0f)
            pos += modSizeY
        }
        popMatrix()
    }

    fun getModuleUnderCursor(posX: Int, posY: Int): Triple<Int, Int, ModuleConfigModule>? {
        var pos = CELL_HEIGHT

        for (mod in modules) {
            val sizeY = mod.height

            // Module click
            if (posY in pos..pos + sizeY) {
                val offset = posY - pos

                return Triple(posX, offset, mod)
            }
            pos += sizeY
        }
        return null
    }

    fun click(posX: Int, posY: Int, mouseButton: Int) {
        val triple = getModuleUnderCursor(posX, posY)

        // Header click
        if (triple == null) {
            if (mouseButton == 0) {
                dragStart = this.posX to this.posY
                dragStartClick = ModuleConfig.mouseX to ModuleConfig.mouseY
            } else {
                open = !open
                ModuleConfig.saveConfig()
            }
            return
        }

        triple.third.click(triple.first, triple.second, mouseButton)
    }

    fun save(nbt: NBTTagCompound) {
        nbt.setTag(name, NBTTagCompound().also { tag ->
            tag.setFloat("posX", posX)
            tag.setFloat("posY", posY)
            tag.setBoolean("open", open)
            tag.setTag("modules", NBTTagCompound().also { list ->
                modules.forEach { mod ->
                    list.setTag(mod.name, NBTTagCompound().also { nbt -> mod.save(nbt) })
                }
            })
        })
    }

    fun load(nbt: NBTTagCompound) {
        val tag = nbt.getCompoundTag(name)
        if (tag.size == 0) return
        posX = tag.getFloat("posX")
        posY = tag.getFloat("posY")
        open = tag.getBoolean("open")
        tag.getCompoundTag("modules").also { list ->
            modules.forEach { mod ->
                mod.load(list.getCompoundTag(mod.name))
            }
        }
    }
}
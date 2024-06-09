package com.cout970.fira.gui.config

import net.minecraft.nbt.NBTTagCompound

class ModuleConfigModule(val name: String, val options: List<ConfigOption>, val enableOption: ConfigOptionBoolean?) {
    var open = false
    val height get() = CELL_HEIGHT + if (open) options.sumBy { it.height } else 0

    fun getOptionUnderMouse(posX: Int, posY: Int): Triple<Int, Int, ConfigOption>? {
        var pos = CELL_HEIGHT
        val padd = (COLUMN_WIDTH - CELL_WIDTH) / 2

        for (option in options) {
            if (posY in pos..pos + option.height && posX >= padd && posX <= COLUMN_WIDTH - padd) {
                val offset = posY - pos

                return Triple(posX - padd, offset, option)
            }
            pos += option.height
        }

        return null
    }

    fun click(posX: Int, posY: Int, mouseButton: Int) {
        val triple = getOptionUnderMouse(posX, posY)

        // Header click
        if (triple == null) {
            if (mouseButton == 0) {
                if (enableOption != null && posX > CELL_HEIGHT) {
                    enableOption.value = !enableOption.value
                    return
                }

                open = !open
                ModuleConfig.saveConfig()

            } else if (mouseButton == 1) {
                open = !open
                ModuleConfig.saveConfig()
            }
            return
        }

        triple.third.click(triple.first, triple.second, mouseButton)
    }

    fun save(nbt: NBTTagCompound) {
        nbt.setBoolean("open", open)
    }

    fun load(nbt: NBTTagCompound) {
        open = nbt.getBoolean("open")
    }
}
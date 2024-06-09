package com.cout970.fira.util

import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiMainMenu
import net.minecraft.client.gui.GuiMultiplayer
import kotlin.concurrent.thread

object Misc {
    fun autoConnect(timeout: Long) {
        thread(start = true) {
            Thread.sleep(timeout)
            Minecraft.getMinecraft().addScheduledTask {
                val gui = GuiMultiplayer(GuiMainMenu())
                Minecraft.getMinecraft().displayGuiScreen(gui)
                gui.selectServer(0)
                gui.connectToSelected()
            }
        }
    }
}
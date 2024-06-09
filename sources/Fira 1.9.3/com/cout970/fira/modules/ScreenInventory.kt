package com.cout970.fira.modules

import com.cout970.fira.Config
import com.cout970.fira.util.Utils
import net.minecraft.client.gui.Gui
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.renderer.RenderHelper
import net.minecraft.item.ItemStack
import net.minecraftforge.client.event.RenderGameOverlayEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

object ScreenInventory {

    fun hud() = "ScreenInventory"

    @SubscribeEvent
    fun onTick(e: RenderGameOverlayEvent.Post) {
        if (e.type != RenderGameOverlayEvent.ElementType.HOTBAR) return
        if (!Config.ScreenInventory.enable) return

        val width = e.resolution.scaledWidth.toFloat() - 162f
        val height = e.resolution.scaledHeight.toFloat() - 54f
        val x = (Config.ScreenInventory.offsetX / 100.0f * width).toInt()
        val y = (Config.ScreenInventory.offsetY / 100.0f * height).toInt()
        if (Config.ScreenInventory.background) {
            Gui.drawRect(x, y, x + 162, y + 54, Utils.color(0, 0.2f))
        }
        drawInventory(x, y)
    }

    fun drawInventory(offsetX: Int, offsetY: Int, hotbar: Boolean = false) {
        val inv = Utils.mc.player.inventory

        for (row in 0..2) {
            for (col in 0..8) {
                drawStack(inv.getStackInSlot(col + (row + 1) * 9), offsetX + col * 18, offsetY + row * 18)
            }
        }

        if (hotbar) {
            for (col in 0..8) {
                drawStack(inv.getStackInSlot(col), offsetX + col * 18, offsetY + 58)
            }
        }
    }

    fun drawStack(stack: ItemStack, i: Int, j: Int) {
        GlStateManager.enableDepth()
        RenderHelper.enableGUIStandardItemLighting()
        Utils.mc.renderItem.renderItemAndEffectIntoGUI(Utils.mc.player, stack, i, j)
        Utils.mc.renderItem.renderItemOverlayIntoGUI(Utils.font(), stack, i, j, null)
        Utils.mc.renderItem.zLevel = 0.0f
        GlStateManager.enableLighting()
        GlStateManager.enableDepth()
        RenderHelper.disableStandardItemLighting()
        GlStateManager.disableRescaleNormal()
        GlStateManager.disableBlend()
    }
}
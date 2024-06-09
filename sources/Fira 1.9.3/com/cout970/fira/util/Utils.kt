package com.cout970.fira.util

import com.cout970.fira.Log
import com.cout970.fira.Manager
import net.minecraft.client.Minecraft
import net.minecraft.client.audio.PositionedSoundRecord
import net.minecraft.client.gui.FontRenderer
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import net.minecraft.init.SoundEvents
import net.minecraft.util.text.ChatType
import net.minecraft.util.text.TextComponentString
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent

object Utils {

    val mc: Minecraft get() = Minecraft.getMinecraft()

    private val debugData = mutableMapOf<String, Any?>()
    private val futureTasks = mutableListOf<Pair<Long, () -> Unit>>()
    private val cooldowns = mutableMapOf<String, () -> Unit>()

    fun debug(any: Any?): Boolean {
        if (any == null) return false
        val clazz = any.javaClass.simpleName

        Log.info("$clazz ${Manager.GSON.toJson(any)}")
        return false
    }

    fun runLater(ticks: Int, action: () -> Unit) {
        val world = Minecraft.getMinecraft().world ?: return
        val now = world.totalWorldTime
        futureTasks += (now + ticks) to action
    }

    @SubscribeEvent
    fun onTick(e: TickEvent.ClientTickEvent) {
        if (e.phase != TickEvent.Phase.START) return
        val world = Minecraft.getMinecraft().world ?: return
        val now = world.totalWorldTime

        futureTasks.removeIf { (time, action) ->
            (time <= now).also { remove ->
                if (remove) {
                    try {
                        action()
                    } catch (e: Throwable) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }

    fun withCooldown(name: String, cooldown: Int, action: () -> Unit) {
        if (name !in cooldowns) {
            action()
            cooldowns[name] = action
            runLater(cooldown) { cooldowns.remove(name) }
        }
    }

    fun color(hex: Int, alpha: Float = 1f): Int {
        val lastBytes = (alpha * 255).toInt() shl 24
        return hex or lastBytes
    }

    fun chatMsg(str: String) {
        mc.ingameGUI.addChatMessage(ChatType.SYSTEM, TextComponentString(str))
    }

    fun ping() {
        // Sonido de mensaje
        val sound = PositionedSoundRecord.getMasterRecord(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f)
        mc.soundHandler.playSound(sound)
    }

    fun worldTime(): Float {
        val world = mc.world ?: return 0f
        return (world.totalWorldTime and 0xFFFF).toInt().toFloat() + partialTicks()
    }

    fun partialTicks(): Float {
        return mc.renderPartialTicks
    }

    fun font(): FontRenderer {
        return mc.fontRenderer
    }

    fun drawTexturedModalRect(xCoord: Int, yCoord: Int, textureSprite: TextureAtlasSprite, widthIn: Int, heightIn: Int, zLevel: Double = 0.0) {
        val tessellator = Tessellator.getInstance()
        val bufferbuilder = tessellator.buffer
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX)
        bufferbuilder.pos((xCoord + 0).toDouble(), (yCoord + heightIn).toDouble(), zLevel).tex(textureSprite.minU.toDouble(), textureSprite.maxV.toDouble()).endVertex()
        bufferbuilder.pos((xCoord + widthIn).toDouble(), (yCoord + heightIn).toDouble(), zLevel).tex(textureSprite.maxU.toDouble(), textureSprite.maxV.toDouble()).endVertex()
        bufferbuilder.pos((xCoord + widthIn).toDouble(), (yCoord + 0).toDouble(), zLevel).tex(textureSprite.maxU.toDouble(), textureSprite.minV.toDouble()).endVertex()
        bufferbuilder.pos((xCoord + 0).toDouble(), (yCoord + 0).toDouble(), zLevel).tex(textureSprite.minU.toDouble(), textureSprite.minV.toDouble()).endVertex()
        tessellator.draw()
    }
}
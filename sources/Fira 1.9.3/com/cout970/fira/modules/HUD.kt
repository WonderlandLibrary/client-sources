package com.cout970.fira.modules

import com.cout970.fira.Config
import com.cout970.fira.MOD_ID
import com.cout970.fira.MOD_NAME
import com.cout970.fira.MOD_VERSION
import com.cout970.fira.util.Utils
import net.minecraft.client.gui.Gui
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.Vec3d
import net.minecraftforge.client.event.RenderGameOverlayEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import java.awt.Color
import java.text.DecimalFormat
import kotlin.math.sqrt

object HUD {

    @SubscribeEvent
    fun onTick(e: RenderGameOverlayEvent.Post) {
        if (e.type != RenderGameOverlayEvent.ElementType.HOTBAR) return
        if (!Config.HudColors.enable) return
        var count = 1

        val p = Utils.mc.player
        val speed = sqrt(p.motionX * p.motionX + p.motionZ * p.motionZ) * 20

        val current = Vec3d(p.posX, p.posY, p.posZ)
        val other = if (p.dimension != 0) Vec3d(p.posX, p.posY, p.posZ).scale(8.0) else Vec3d(p.posX, p.posY, p.posZ).scale(1.0 / 8.0)

        if (Config.HudColors.showLogo) {
            drawLogo(count)
            count += 2
        }

        draw("$MOD_NAME $MOD_VERSION", -1, count++)

        if (Config.HudColors.showX)
            draw("X ${format("#,##0.0", current.x)} (${format("#,##0.0", other.x)})", 0, count++)

        if (Config.HudColors.showY)
            draw("Y ${format("#,##0.00000", current.y)}", 1, count++)

        if (Config.HudColors.showZ)
            draw("Z ${format("#,##0.0", current.z)} (${format("#,##0.0", other.z)})", 2, count++)

        if (Config.HudColors.showSpeed)
            draw("Speed %.2f m/s (%.2f Km/h)".format(speed, speed * 3600f / 1000f), 3, count++)

        val list = mutableListOf<String>()

        if (Config.Debug.packetFlood)
            list += "PacketFood"

        if (Config.ScreenInventory.enable)
            list += ScreenInventory.hud()

        if (Config.Chat.enableChatFilter)
            list += ChatFilter.hud()

        if (Config.AutoPilot.enable)
            list += AutoPilot.hud()

        if (Config.ElytraFly.enable)
            list += ElytraFly.hud()

        if (Config.ElytraTweaks.autoEnableElytras)
            list += ElytraTweaks.hud()

        if (Config.PacketFly.enable)
            list += PacketFly.hud()

        if (Config.CrystalPvP.surround)
            list += CrystalPvP.hud()

        if (Config.AutoFly.enable)
            list += "AutoFly"

        if (Config.YawLock.enable)
            list += YawLock.hud()

        list.sortByDescending { it.length }
        list.forEachIndexed { index, s ->
            draw(s, 3 + index, count++)
        }
    }

    private fun format(pattern: String, value: Number): String {
        return DecimalFormat(pattern).format(value.toFloat())
    }

    private fun draw(line: String, colorIndex: Int, index: Int) {
        val black = 0xFF000000.toInt()
        val color = Color.HSBtoRGB(
                (Config.HudColors.hudColorScale / 100f * colorIndex) % 1f + Config.HudColors.hudColorOffset / 360f,
                Config.HudColors.hudColorSaturation / 100f,
                Config.HudColors.hudColorBright / 100f
        )

        val offsetX = Config.HudColors.offsetX
        val offsetY = Config.HudColors.offsetY

        // Shadow
        Utils.mc.fontRenderer.drawString(line, offsetX + 0.5f, offsetY + 0.5f + index * 10f, black, false)
        // Color
        Utils.mc.fontRenderer.drawString(line, offsetX, offsetY + index * 10f, color, false)
    }

    private fun drawLogo(index: Int) {
        val offsetX = Config.HudColors.offsetX.toInt()
        val offsetY = Config.HudColors.offsetY.toInt()

        val scale = 18f / 72f
        val sizeX = 192
        val sizeY = 72

        GlStateManager.enableBlend()
        Utils.mc.textureManager.bindTexture(ResourceLocation(MOD_ID, "logo-mini.png"))
        Gui.drawScaledCustomSizeModalRect(
                offsetX, offsetY + index * 10,
                0f, 0f,
                sizeX, sizeY,
                (sizeX * scale).toInt(), (sizeY * scale).toInt(),
                sizeX.toFloat(), sizeY.toFloat()
        )
        GlStateManager.disableBlend()
    }
}
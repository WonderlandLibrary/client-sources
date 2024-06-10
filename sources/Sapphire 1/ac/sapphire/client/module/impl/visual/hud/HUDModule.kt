package ac.sapphire.client.module.impl.visual.hud

import ac.sapphire.client.Sapphire
import ac.sapphire.client.event.annotation.Subscribe
import ac.sapphire.client.event.events.render.RenderOverlayEvent
import ac.sapphire.client.ext.mc
import ac.sapphire.client.module.AbstractModule
import ac.sapphire.client.module.ModuleCategory
import net.minecraft.client.gui.ScaledResolution
import org.lwjgl.opengl.GL11
import java.awt.Color

class HUDModule : AbstractModule("hud", "HUD", "mew", ModuleCategory.VISUALS) {
    init {
        toggle(true)
    }

    @Subscribe
    fun onOverlay(event: RenderOverlayEvent) {
        mc.fontRendererObj.drawStringWithShadow("Sapphire Lite", 2F, 2F, Color.WHITE.rgb)

        var y = 2
        Sapphire.moduleManager.moduleMap.values.filter { it.isToggled }
            .sortedByDescending { mc.fontRendererObj.getStringWidth(it.displayName) }.forEach {
                val width = mc.fontRendererObj.getStringWidth(it.displayName)
                val x = ScaledResolution(mc).scaledWidth - width - 2
                mc.fontRendererObj.drawStringWithShadow(it.displayName, x.toFloat(), y.toFloat(), Color.WHITE.rgb)
                y += mc.fontRendererObj.FONT_HEIGHT + 2
            }
    }
}
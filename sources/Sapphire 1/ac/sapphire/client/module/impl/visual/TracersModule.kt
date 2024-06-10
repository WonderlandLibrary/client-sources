package ac.sapphire.client.module.impl.visual

import ac.sapphire.client.event.annotation.Subscribe
import ac.sapphire.client.event.events.render.RenderWorldPassEvent
import ac.sapphire.client.ext.mc
import ac.sapphire.client.ext.player
import ac.sapphire.client.ext.requireMixin
import ac.sapphire.client.mixin.entity.IEntityRendererAccessor
import ac.sapphire.client.mixin.entity.IRenderManagerAccessor
import ac.sapphire.client.module.AbstractModule
import ac.sapphire.client.module.ModuleCategory
import net.minecraft.client.renderer.GlStateManager
import org.lwjgl.opengl.GL11.*
import java.awt.Color
import kotlin.contracts.ExperimentalContracts

class TracersModule : AbstractModule("tracers", "Tracers", "", ModuleCategory.VISUALS) {
    @Subscribe
    @ExperimentalContracts
    fun onRenderWorld(event: RenderWorldPassEvent) {
        for (entity in mc.theWorld.playerEntities) {
            if (entity === player) {
                continue
            }

            val rm = mc.renderManager
            requireMixin<IRenderManagerAccessor>(rm)

            val x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * event.tickDelta - rm.renderPosX
            val y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * event.tickDelta - rm.renderPosY
            val z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * event.tickDelta - rm.renderPosZ

            val er = mc.entityRenderer
            requireMixin<IEntityRendererAccessor>(er)

            glEnable(GL_BLEND)
            glEnable(GL_LINE_SMOOTH)

            glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA)

            glDisable(GL_TEXTURE_2D)
            glDisable(GL_DEPTH_TEST)

            er.callSetupCameraTransform(mc.timer.renderPartialTicks, 0)

            val bobbing: Boolean = mc.gameSettings.viewBobbing
            mc.gameSettings.viewBobbing = false
            er.callSetupCameraTransform(mc.timer.renderPartialTicks, 2)
            mc.gameSettings.viewBobbing = bobbing

            val color = Color.RED.rgb
            val red: Float = (color shr 16 and 255) / 255.0f
            val green: Float = (color shr 8 and 255) / 255.0f
            val blue: Float = (color and 255) / 255.0f
            val alpha: Float = (color shr 24 and 255) / 255.0f

            glColor4f(red, green, blue, alpha)
            glLineWidth(1.5f)
            glBegin(1)
            glVertex3d(0.0, player.getEyeHeight().toDouble(), 0.0)
            glVertex3d(x, y + entity.eyeHeight, z)
            glEnd()

            glDisable(GL_BLEND)
            glEnable(GL_TEXTURE_2D)
            glDisable(GL_LINE_SMOOTH)
            glEnable(GL_DEPTH_TEST)
            GlStateManager.disableBlend()
        }
    }
}
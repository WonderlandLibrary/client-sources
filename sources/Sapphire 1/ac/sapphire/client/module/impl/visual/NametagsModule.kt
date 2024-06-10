package ac.sapphire.client.module.impl.visual

import ac.sapphire.client.event.annotation.Subscribe
import ac.sapphire.client.event.events.render.NametagRenderEvent
import ac.sapphire.client.ext.mc
import ac.sapphire.client.ext.requireMixin
import ac.sapphire.client.mixin.entity.IRenderManagerAccessor
import ac.sapphire.client.module.AbstractModule
import ac.sapphire.client.module.ModuleCategory
import net.minecraft.client.entity.EntityOtherPlayerMP
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import org.lwjgl.opengl.GL11.*
import java.awt.Color
import kotlin.contracts.ExperimentalContracts

class NametagsModule : AbstractModule("nametags", "Nametags", "", ModuleCategory.VISUALS) {
    @ExperimentalContracts
    @Subscribe
    fun onNametagRender(event: NametagRenderEvent) {
        val player: EntityPlayer = event.entity as? EntityOtherPlayerMP ?: return
        if (player.isInvisible) {
            return
        }

        event.cancelled = true

        val name: String = event.entity.name
        renderNameTag(event.entity, "$name [${event.entity.health}]")
    }

    @ExperimentalContracts
    private fun renderNameTag(entity: EntityLivingBase, tag: String) {
        glPushMatrix()

        val renderManager = mc.renderManager
        val timer = mc.timer

        requireMixin<IRenderManagerAccessor>(renderManager)
        glTranslated(
            entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * timer.renderPartialTicks - renderManager.renderPosX,
            entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * timer.renderPartialTicks - renderManager.renderPosY + entity.eyeHeight.toDouble() + 0.55,
            entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * timer.renderPartialTicks - renderManager.renderPosZ
        )

        glRotatef(-mc.renderManager.playerViewY, 0F, 1F, 0F)
        glRotatef(mc.renderManager.playerViewX, 1F, 0F, 0F)

        var distance = mc.thePlayer.getDistanceToEntity(entity) / 4F

        if (distance < 1F) {
            distance = 1F
        }

        val scale = distance / 120

        glDisable(GL_LIGHTING)
        glDisable(GL_DEPTH_TEST)

        glEnable(GL_BLEND)
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA)

        val fontRenderer = mc.fontRendererObj
        val width = fontRenderer.getStringWidth(tag).coerceAtLeast(30) / 2

        glScalef(-scale * 2, -scale * 2, scale * 2)
        drawRect(-width - 6F, -fontRenderer.FONT_HEIGHT * 1.7F, width + 6F, -2F, Color(0, 0, 0, 150))
        fontRenderer.drawString(
            tag,
            (-fontRenderer.getStringWidth(tag) * 0.5F).toInt(),
            (-fontRenderer.FONT_HEIGHT * 1.4F).toInt(),
            Color.WHITE.rgb
        )

        // Reset caps
        glEnable(GL_LIGHTING)
        glEnable(GL_DEPTH_TEST)
        glDisable(GL_BLEND)

        // Reset color
        glColor4f(1F, 1F, 1F, 1F)

        // Pop
        glPopMatrix()
    }

    private fun drawRect(x: Float, y: Float, x2: Float, y2: Float, color: Color) {
        glEnable(GL_BLEND)
        glDisable(GL_TEXTURE_2D)
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA)
        glEnable(GL_LINE_SMOOTH)
        glColor(color)
        glBegin(GL_QUADS)
        glVertex2f(x2, y)
        glVertex2f(x, y)
        glVertex2f(x, y2)
        glVertex2f(x2, y2)
        glEnd()
        glEnable(GL_TEXTURE_2D)
        glDisable(GL_BLEND)
        glDisable(GL_LINE_SMOOTH)
    }

    private fun glColor(color: Color) {
        val red: Float = color.red / 255f
        val green: Float = color.green / 255f
        val blue: Float = color.blue / 255f
        val alpha: Float = color.alpha / 255f
        GlStateManager.color(red, green, blue, alpha)
    }
}
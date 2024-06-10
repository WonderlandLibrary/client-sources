package ac.sapphire.client.module.impl.visual

import ac.sapphire.client.event.annotation.Subscribe
import ac.sapphire.client.event.events.render.RenderWorldPassEvent
import ac.sapphire.client.ext.mc
import ac.sapphire.client.ext.player
import ac.sapphire.client.ext.requireMixin
import ac.sapphire.client.mixin.entity.IRenderManagerAccessor
import ac.sapphire.client.module.AbstractModule
import ac.sapphire.client.module.ModuleCategory
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityLivingBase
import net.minecraft.util.AxisAlignedBB
import org.lwjgl.opengl.GL11.*
import java.awt.Color
import kotlin.contracts.ExperimentalContracts

class ESPModule : AbstractModule("esp", "ESP", "", ModuleCategory.VISUALS) {
    @Subscribe
    @ExperimentalContracts
    fun onRenderWorld(event: RenderWorldPassEvent) {
        for (entity in mc.theWorld.playerEntities) {
            if (entity.entityId == player.entityId) {
                continue
            }

            if (entity is EntityLivingBase) {
                drawEntityBox(entity, Color(104, 113, 185, 45))
            }
        }
    }

    @ExperimentalContracts
    private fun drawEntityBox(entity: Entity, color: Color) {
        val renderManager = mc.renderManager
        val timer = mc.timer
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA)

        GlStateManager.enableBlend()
        GlStateManager.disableTexture2D()
        GlStateManager.disableDepth()
        glDepthMask(false)

        requireMixin<IRenderManagerAccessor>(renderManager)
        val x =
            (entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * timer.renderPartialTicks - renderManager.renderPosX)
        val y =
            (entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * timer.renderPartialTicks - renderManager.renderPosY)
        val z =
            (entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * timer.renderPartialTicks - renderManager.renderPosZ)

        val entityBox = entity.entityBoundingBox
        val axisAlignedBB = AxisAlignedBB(
            entityBox.minX - entity.posX + x - 0.15,
            entityBox.minY - entity.posY + y,
            entityBox.minZ - entity.posZ + z - 0.15,
            entityBox.maxX - entity.posX + x + 0.15,
            entityBox.maxY - entity.posY + y + 0.15,
            entityBox.maxZ - entity.posZ + z + 0.15
        )

        glColor4f(color.red / 255F, color.green / 255F, color.blue / 255F, color.alpha / 255F)
        drawAABB(axisAlignedBB)
        GlStateManager.resetColor()

        glDepthMask(true)
        GlStateManager.disableBlend()
        GlStateManager.enableTexture2D()
        GlStateManager.disableDepth()
    }

    private fun drawAABB(axisAlignedBB: AxisAlignedBB) {
        val tess = Tessellator.getInstance()
        val world = tess.worldRenderer

        world.begin(7, DefaultVertexFormats.POSITION)
        world.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex()
        world.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex()
        world.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex()
        world.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex()
        world.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex()
        world.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex()
        world.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex()
        world.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex()
        world.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex()
        world.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex()
        world.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex()
        world.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex()
        world.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex()
        world.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex()
        world.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex()
        world.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex()
        world.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex()
        world.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex()
        world.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex()
        world.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex()
        world.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex()
        world.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex()
        world.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex()
        world.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex()
        world.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex()
        world.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex()
        world.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex()
        world.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex()
        world.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex()
        world.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex()
        world.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex()
        world.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex()
        world.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex()
        world.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex()
        world.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex()
        world.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex()
        world.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex()
        world.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex()
        world.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex()
        world.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex()
        world.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex()
        world.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex()
        world.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex()
        world.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex()
        world.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex()
        world.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex()
        world.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex()
        world.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex()
        tess.draw()
    }
}
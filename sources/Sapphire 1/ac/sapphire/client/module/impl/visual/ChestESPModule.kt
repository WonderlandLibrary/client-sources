package ac.sapphire.client.module.impl.visual

import ac.sapphire.client.event.annotation.Subscribe
import ac.sapphire.client.event.events.render.RenderWorldPassEvent
import ac.sapphire.client.ext.mc
import ac.sapphire.client.ext.requireMixin
import ac.sapphire.client.mixin.entity.IRenderManagerAccessor
import ac.sapphire.client.module.AbstractModule
import ac.sapphire.client.module.ModuleCategory
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import net.minecraft.tileentity.TileEntityChest
import net.minecraft.tileentity.TileEntityEnderChest
import net.minecraft.util.AxisAlignedBB
import org.lwjgl.opengl.GL11
import java.awt.Color
import kotlin.contracts.ExperimentalContracts

class ChestESPModule : AbstractModule("chestesp", "Chest ESP", "", ModuleCategory.VISUALS) {
    private val REGULAR_CHEST = Color.ORANGE
    private val ENDER_CHEST = Color.PINK
    private val TRAPPED_CHEST = Color.RED

    @Subscribe
    @ExperimentalContracts
    fun onRender3D(event: RenderWorldPassEvent) {
        for (entity in mc.theWorld.loadedTileEntityList) {
            val renderManager = mc.renderManager
            requireMixin<IRenderManagerAccessor>(renderManager)

            val posX = entity.pos.x - renderManager.renderPosX
            val posY = entity.pos.y - renderManager.renderPosY
            val posZ = entity.pos.z - renderManager.renderPosZ

            if (entity is TileEntityChest) {
                var box = AxisAlignedBB(0.0625, 0.0, 0.0625, 0.94, 0.875, 0.94).offset(posX, posY, posZ)
                val adjacent = if (entity.adjacentChestXNeg != null) {
                    entity.adjacentChestXNeg
                } else if (entity.adjacentChestXPos != null) {
                    entity.adjacentChestXPos
                } else if (entity.adjacentChestZNeg != null) {
                    entity.adjacentChestZNeg
                } else if (entity.adjacentChestZPos != null) {
                    entity.adjacentChestZPos
                } else {
                    null
                }

                if (adjacent != null) {
                    box = box.union(
                        AxisAlignedBB(
                            0.0625, 0.0, 0.0625, 0.94, 0.875, 0.94
                        ).offset(
                            adjacent.pos.x - renderManager.renderPosX,
                            adjacent.pos.y - renderManager.renderPosY,
                            adjacent.pos.z - renderManager.renderPosZ
                        )
                    )
                }

                if (entity.chestType == 1) {
                    drawBlockESP(box, TRAPPED_CHEST, 1f)
                } else {
                    drawBlockESP(box, REGULAR_CHEST, 1f)
                }
            } else if (entity is TileEntityEnderChest) {
                drawBlockESP(
                    AxisAlignedBB(0.0625, 0.0, 0.0625, 0.94, 0.875, 0.94).offset(posX, posY, posZ), ENDER_CHEST, 1f
                )
            }
        }
    }

    private fun drawBlockESP(bb: AxisAlignedBB, color: Color, width: Float) {
        GL11.glPushMatrix()
        GL11.glEnable(3042)
        GL11.glBlendFunc(770, 771)
        GL11.glDisable(3553)
        GL11.glEnable(2848)
        GL11.glDisable(2929)
        GL11.glDepthMask(false)
        GL11.glLineWidth(width)
        GL11.glColor4f(color.red / 255f, color.green / 255f, color.blue / 255f, color.alpha / 255f)
        drawOutlinedBoundingBox(bb)
        GL11.glDisable(2848)
        GL11.glEnable(3553)
        GL11.glEnable(2929)
        GL11.glDepthMask(true)
        GL11.glDisable(3042)
        GL11.glPopMatrix()
        GL11.glColor4f(1f, 1f, 1f, 1f)
    }

    private fun drawOutlinedBoundingBox(bb: AxisAlignedBB) {
        val tessellator = Tessellator.getInstance()
        val worldRenderer = tessellator.worldRenderer
        worldRenderer.begin(3, DefaultVertexFormats.POSITION)
        worldRenderer.pos(bb.minX, bb.minY, bb.minZ).endVertex()
        worldRenderer.pos(bb.maxX, bb.minY, bb.minZ).endVertex()
        worldRenderer.pos(bb.maxX, bb.minY, bb.maxZ).endVertex()
        worldRenderer.pos(bb.minX, bb.minY, bb.maxZ).endVertex()
        worldRenderer.pos(bb.minX, bb.minY, bb.minZ).endVertex()
        tessellator.draw()
        worldRenderer.begin(3, DefaultVertexFormats.POSITION)
        worldRenderer.pos(bb.minX, bb.maxY, bb.minZ).endVertex()
        worldRenderer.pos(bb.maxX, bb.maxY, bb.minZ).endVertex()
        worldRenderer.pos(bb.maxX, bb.maxY, bb.maxZ).endVertex()
        worldRenderer.pos(bb.minX, bb.maxY, bb.maxZ).endVertex()
        worldRenderer.pos(bb.minX, bb.maxY, bb.minZ).endVertex()
        tessellator.draw()
        worldRenderer.begin(1, DefaultVertexFormats.POSITION)
        worldRenderer.pos(bb.minX, bb.minY, bb.minZ).endVertex()
        worldRenderer.pos(bb.minX, bb.maxY, bb.minZ).endVertex()
        worldRenderer.pos(bb.maxX, bb.minY, bb.minZ).endVertex()
        worldRenderer.pos(bb.maxX, bb.maxY, bb.minZ).endVertex()
        worldRenderer.pos(bb.maxX, bb.minY, bb.maxZ).endVertex()
        worldRenderer.pos(bb.maxX, bb.maxY, bb.maxZ).endVertex()
        worldRenderer.pos(bb.minX, bb.minY, bb.maxZ).endVertex()
        worldRenderer.pos(bb.minX, bb.maxY, bb.maxZ).endVertex()
        tessellator.draw()
    }
}
package com.leafclient.leaf.mod.visual

import com.leafclient.leaf.management.mod.ToggleableMod
import com.leafclient.leaf.management.mod.category.Category
import com.leafclient.leaf.management.setting.asSetting
import com.leafclient.leaf.management.setting.setting
import com.leafclient.leaf.utils.render.shader.default.OutlineShader
import fr.shyrogan.publisher4k.subscription.Listener
import fr.shyrogan.publisher4k.subscription.Subscribe
import net.minecraft.block.BlockChest
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher
import net.minecraft.tileentity.TileEntity
import net.minecraft.tileentity.TileEntityChest
import net.minecraft.tileentity.TileEntityEnderChest
import net.minecraft.tileentity.TileEntityShulkerBox
import java.awt.Color

class StorageESP: ToggleableMod("StorageESP", Category.VISUAL) {

    private var chests by setting("Chests", true)
    private var trappedChests by setting("Trapped chests", true)
    private var chestsColor by ::chests.asSetting
        .setting("Color", Color(220, 120, 20))
    private var trappedChestsColor by ::chests.asSetting
        .setting("Color", Color(220, 60, 0))

    private var enderChests by setting("Enderchests", true)
    private var enderChestsColor by ::enderChests.asSetting
        .setting("Color", Color(160, 90, 240))

    private var shulkers by setting("Shulkers", true)
    private var shulkersColor by ::shulkers.asSetting
        .setting("Color", Color(10, 0, 240))

    @Subscribe
    val onRenderOutline = Listener { e: OutlineShader.RenderEvent ->
        mc.world.loadedTileEntityList
            .filter(this::shouldRenderEntity)
            .forEach {
                GlStateManager.enableOutlineMode(getRenderColor(it).rgb)
                mc.entityRenderer.disableLightmap()
                TileEntityRendererDispatcher.instance.render(it, e.partialTicks, -1)
                mc.entityRenderer.enableLightmap()
                GlStateManager.disableOutlineMode()
            }
    }

    /**
     * Returns true whether [e] should be rendered by the ESP
     */
    private fun shouldRenderEntity(e: TileEntity): Boolean {
        return OutlineShader.frustum.isBoundingBoxInFrustum(e.renderBoundingBox) && when(e) {
            is TileEntityChest      -> if(e.chestType == BlockChest.Type.TRAP) chests else trappedChests
            is TileEntityEnderChest -> enderChests
            is TileEntityShulkerBox -> shulkers
            else -> false
        }
    }

    /**
     * Returns the color of rendered entity
     */
    private fun getRenderColor(e: TileEntity): Color = when(e) {
        is TileEntityChest      -> if(e.chestType == BlockChest.Type.TRAP) chestsColor else trappedChestsColor
        is TileEntityEnderChest -> enderChestsColor
        is TileEntityShulkerBox -> shulkersColor
        else -> Color.white
    }

    init {
        isRunning = true
    }

}
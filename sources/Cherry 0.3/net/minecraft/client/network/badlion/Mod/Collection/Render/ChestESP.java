// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.network.badlion.Mod.Collection.Render;

import net.minecraft.client.network.badlion.memes.EventTarget;
import java.util.Iterator;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.client.network.badlion.Utils.RenderUtils;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.client.network.badlion.Events.EventRender3D;
import net.minecraft.client.network.badlion.memes.EventManager;
import net.minecraft.client.network.badlion.Mod.Category;
import net.minecraft.client.network.badlion.Mod.Mod;

public class ChestESP extends Mod
{
    public ChestESP() {
        super("Chest ESP", Category.RENDER);
    }
    
    @Override
    public void onEnable() {
        EventManager.register(this);
    }
    
    @Override
    public void onDisable() {
        EventManager.unregister(this);
    }
    
    @EventTarget
    public void onRender3D(final EventRender3D event) {
        for (final Object obj : this.mc.theWorld.loadedTileEntityList) {
            if (obj instanceof TileEntityChest) {
                final TileEntityChest chest = (TileEntityChest)obj;
                this.mc.getRenderManager();
                final double posX = chest.getPos().getX() - RenderManager.renderPosX;
                this.mc.getRenderManager();
                final double posY = chest.getPos().getY() - RenderManager.renderPosY;
                this.mc.getRenderManager();
                final double posZ = chest.getPos().getZ() - RenderManager.renderPosZ;
                RenderUtils.drawBlockESP(posX, posY, posZ, 5.0f, 5.0f, 5.0f, 0.15f, 0.3f, 0.0f, 0.0f, 0.0f, 0.2f);
            }
            else {
                if (!(obj instanceof TileEntityEnderChest)) {
                    continue;
                }
                final TileEntityEnderChest enderchest = (TileEntityEnderChest)obj;
                this.mc.getRenderManager();
                final double posX = enderchest.getPos().getX() - RenderManager.renderPosX;
                this.mc.getRenderManager();
                final double posY = enderchest.getPos().getY() - RenderManager.renderPosY;
                this.mc.getRenderManager();
                final double posZ = enderchest.getPos().getZ() - RenderManager.renderPosZ;
                RenderUtils.drawBlockESP(posX - 0.025, posY - 0.025, posZ - 0.025, 2.9f, 0.0f, 2.9f, 0.15f, 0.9f, 0.0f, 0.0f, 0.0f, 1.0f);
            }
        }
    }
}

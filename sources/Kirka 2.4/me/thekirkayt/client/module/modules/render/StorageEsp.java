/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.module.modules.render;

import java.awt.Color;
import java.util.List;
import me.thekirkayt.client.module.Module;
import me.thekirkayt.client.option.Option;
import me.thekirkayt.event.EventTarget;
import me.thekirkayt.event.events.Render3DEvent;
import me.thekirkayt.utils.ClientUtils;
import me.thekirkayt.utils.RenderUtils;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;

@Module.Mod(displayName="StorageEsp", shown=BADBOOL 11141290)
public class StorageEsp
extends Module {
    @Option.Op
    public boolean chest = true;
    @Option.Op
    public boolean dispenser;
    @Option.Op
    public boolean enderChest;

    @EventTarget
    private void onRender(Render3DEvent event) {
        GlStateManager.pushMatrix();
        for (Object o : ClientUtils.world().loadedTileEntityList) {
            TileEntity ent = (TileEntity)o;
            if (!(ent instanceof TileEntityChest) && !(ent instanceof TileEntityDispenser) && !(ent instanceof TileEntityEnderChest) || ent instanceof TileEntityChest && !this.chest || ent instanceof TileEntityDispenser && !this.dispenser || ent instanceof TileEntityEnderChest && !this.enderChest) continue;
            this.drawEsp(ent, event.getPartialTicks());
        }
        GlStateManager.popMatrix();
    }

    private void drawEsp(TileEntity ent, float pTicks) {
        double x1 = (double)ent.getPos().getX() - RenderManager.renderPosX;
        double y1 = (double)ent.getPos().getY() - RenderManager.renderPosY;
        double z1 = (double)ent.getPos().getZ() - RenderManager.renderPosZ;
        float[] color = this.getColor(ent);
        AxisAlignedBB box = new AxisAlignedBB(x1, y1, z1, x1 + 1.0, y1 + 1.0, z1 + 1.0);
        if (ent instanceof TileEntityChest) {
            TileEntityChest chest = (TileEntityChest)TileEntityChest.class.cast(ent);
            if (chest.adjacentChestZPos != null) {
                box = new AxisAlignedBB(x1 + 0.0625, y1, z1 + 0.0625, x1 + 0.9375, y1 + 0.875, z1 + 1.9375);
            } else if (chest.adjacentChestXPos != null) {
                box = new AxisAlignedBB(x1 + 0.0625, y1, z1 + 0.0625, x1 + 1.9375, y1 + 0.875, z1 + 0.9375);
            } else {
                if (chest.adjacentChestZPos != null || chest.adjacentChestXPos != null || chest.adjacentChestZNeg != null || chest.adjacentChestXNeg != null) {
                    return;
                }
                box = new AxisAlignedBB(x1 + 0.0625, y1, z1 + 0.0625, x1 + 0.9375, y1 + 0.875, z1 + 0.9375);
            }
        } else if (ent instanceof TileEntityEnderChest) {
            box = new AxisAlignedBB(x1 + 0.0625, y1, z1 + 0.0625, x1 + 0.9375, y1 + 0.875, z1 + 0.9375);
        }
        RenderUtils.filledBox(box, new Color(color[0], color[1], color[2]).getRGB() & 11141290, true);
        RenderGlobal.drawOutlinedBoundingBox(box, Color.lightGray.hashCode());
    }

    private float[] getColor(TileEntity ent) {
        if (ent instanceof TileEntityChest) {
            return new float[]{0.0f, 0.9f, 0.9f};
        }
        if (ent instanceof TileEntityDispenser) {
            return new float[]{0.5f, 0.5f, 0.5f};
        }
        if (ent instanceof TileEntityEnderChest) {
            return new float[]{0.3f, 0.0f, 0.3f};
        }
        return new float[]{1.0f, 1.0f, 1.0f};
    }
}


/*
 * Decompiled with CFR 0_132.
 *
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package me.superskidder.lune.modules.render;

import java.util.Iterator;
import java.awt.Color;

import me.superskidder.lune.modules.Mod;
import me.superskidder.lune.modules.ModCategory;
import me.superskidder.lune.events.EventRender3D;
import me.superskidder.lune.manager.event.EventTarget;
import me.superskidder.lune.utils.render.RenderUtil;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.util.AxisAlignedBB;

import org.lwjgl.opengl.GL11;

public class ChestESP
        extends Mod {
    public ChestESP() {
        super("ChestESP", ModCategory.Render,"Draw a box on chests");
    }

    @EventTarget
    public void onRender(EventRender3D eventRender) {
        Iterator var3;
        var3 = this.mc.theWorld.loadedTileEntityList.iterator();

        while (true) {
            TileEntity ent;
            do {
                if (!var3.hasNext()) {
                    return;
                }

                ent = (TileEntity) var3.next();
            } while (!(ent instanceof TileEntityChest) && !(ent instanceof TileEntityEnderChest));

            this.mc.getRenderManager();
            double x = (double) ent.getPos().getX() - RenderManager.renderPosX;
            this.mc.getRenderManager();
            double y = (double) ent.getPos().getY() - RenderManager.renderPosY;
            this.mc.getRenderManager();
            double z = (double) ent.getPos().getZ() - RenderManager.renderPosZ;
            GL11.glPushMatrix();
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glDisable(3553);
            GL11.glEnable(2848);
            GL11.glDisable(2929);
            GL11.glDepthMask(false);
            Color rainbow = Gui.rainbow(System.nanoTime(), 1.0F, 1.0F);
            GL11.glColor4f(1, 1, 1, 1);
//            if (((Boolean) this.boundingBox.getValue()).booleanValue()) {
//                RenderUtil.drawBoundingBox(new AxisAlignedBB(x + ent.getBlockType().getBlockBoundsMinX(), y + ent.getBlockType().getBlockBoundsMinY(), z + ent.getBlockType().getBlockBoundsMinZ(), x + ent.getBlockType().getBlockBoundsMaxX(), y + ent.getBlockType().getBlockBoundsMaxY(), z + ent.getBlockType().getBlockBoundsMaxZ()));
//            }
//            if (((Boolean) this.outlinedboundingBox.getValue()).booleanValue()) {
//                RenderUtil.drawOutlinedBoundingBox(new AxisAlignedBB(x + ent.getBlockType().getBlockBoundsMinX(), y + ent.getBlockType().getBlockBoundsMinY(), z + ent.getBlockType().getBlockBoundsMinZ(), x + ent.getBlockType().getBlockBoundsMaxX(), y + ent.getBlockType().getBlockBoundsMaxY(), z + ent.getBlockType().getBlockBoundsMaxZ()));
//            }
            RenderUtil.drawOutlinedBoundingBox(new AxisAlignedBB(x + ent.getBlockType().getBlockBoundsMinX(), y + ent.getBlockType().getBlockBoundsMinY(), z + ent.getBlockType().getBlockBoundsMinZ(), x + ent.getBlockType().getBlockBoundsMaxX(), y + ent.getBlockType().getBlockBoundsMaxY(), z + ent.getBlockType().getBlockBoundsMaxZ()));

            GL11.glDisable(2848);
            GL11.glEnable(3553);
            GL11.glEnable(2929);
            GL11.glDepthMask(true);
            GL11.glDisable(3042);
            GL11.glPopMatrix();
        }
    }
}

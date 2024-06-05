/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package drunkclient.beta.IMPL.Module.impl.render;

import drunkclient.beta.API.EventHandler;
import drunkclient.beta.API.events.render.EventRender3D;
import drunkclient.beta.IMPL.Module.Module;
import drunkclient.beta.IMPL.Module.Type;
import drunkclient.beta.UTILS.render.RenderUtil;
import java.util.Iterator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.util.AxisAlignedBB;
import org.lwjgl.opengl.GL11;

public class ChestESP
extends Module {
    public ChestESP() {
        super("ChestEsp", new String[0], Type.RENDER, "No");
    }

    @EventHandler
    public void onRender(EventRender3D e) {
        Iterator iterator = ChestESP.mc.theWorld.loadedTileEntityList.iterator();
        while (iterator.hasNext()) {
            TileEntity tile = (TileEntity)iterator.next();
            double d = tile.getPos().getX();
            mc.getRenderManager();
            double posX = d - RenderManager.getRenderPosX();
            double d2 = tile.getPos().getY();
            mc.getRenderManager();
            double posY = d2 - RenderManager.getRenderPosY();
            double d3 = tile.getPos().getZ();
            mc.getRenderManager();
            double posZ = d3 - RenderManager.getRenderPosZ();
            if (tile instanceof TileEntityChest) {
                AxisAlignedBB bb = new AxisAlignedBB(0.0625, 0.0, 0.0625, 0.94, 0.875, 0.94).offset(posX, posY, posZ);
                TileEntityChest adjacent = null;
                if (((TileEntityChest)tile).adjacentChestXNeg != null) {
                    adjacent = ((TileEntityChest)tile).adjacentChestXNeg;
                }
                if (((TileEntityChest)tile).adjacentChestXPos != null) {
                    adjacent = ((TileEntityChest)tile).adjacentChestXPos;
                }
                if (((TileEntityChest)tile).adjacentChestZNeg != null) {
                    adjacent = ((TileEntityChest)tile).adjacentChestZNeg;
                }
                if (((TileEntityChest)tile).adjacentChestZPos != null) {
                    adjacent = ((TileEntityChest)tile).adjacentChestZPos;
                }
                if (adjacent != null) {
                    AxisAlignedBB axisAlignedBB = new AxisAlignedBB(0.0625, 0.0, 0.0625, 0.94, 0.875, 0.94);
                    double d4 = adjacent.getPos().getX();
                    mc.getRenderManager();
                    double d5 = d4 - RenderManager.getRenderPosX();
                    double d6 = adjacent.getPos().getY();
                    mc.getRenderManager();
                    double d7 = d6 - RenderManager.getRenderPosY();
                    double d8 = adjacent.getPos().getZ();
                    mc.getRenderManager();
                    bb = bb.union(axisAlignedBB.offset(d5, d7, d8 - RenderManager.getRenderPosZ()));
                }
                if (((TileEntityChest)tile).getChestType() == 1) {
                    this.drawBlockESP(bb, 255.0f, 91.0f, 86.0f, 255.0f, 1.0f);
                } else {
                    this.drawBlockESP(bb, 130.0f, 132.0f, 255.0f, 255.0f, 1.0f);
                }
            }
            if (!(tile instanceof TileEntityEnderChest)) continue;
            this.drawBlockESP(new AxisAlignedBB(0.0625, 0.0, 0.0625, 0.94, 0.875, 0.94).offset(posX, posY, posZ), 165.0f, 84.0f, 209.0f, 255.0f, 1.0f);
        }
    }

    private void drawBlockESP(AxisAlignedBB bb, float red, float green, float blue, float alpha, float width) {
        GL11.glPushMatrix();
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glDisable((int)3553);
        GL11.glEnable((int)2848);
        GL11.glDisable((int)2929);
        GL11.glDepthMask((boolean)false);
        GL11.glColor4f((float)(red / 255.0f), (float)(green / 255.0f), (float)(blue / 255.0f), (float)0.2f);
        RenderUtil.drawBoundingBox(bb);
        GL11.glLineWidth((float)width);
        GL11.glColor4f((float)(red / 255.0f), (float)(green / 255.0f), (float)(blue / 255.0f), (float)(alpha / 255.0f));
        RenderUtil.drawOutlinedBoundingBox(bb);
        GL11.glDisable((int)2848);
        GL11.glEnable((int)3553);
        GL11.glEnable((int)2929);
        GL11.glDepthMask((boolean)true);
        GL11.glDisable((int)3042);
        GL11.glPopMatrix();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
    }
}


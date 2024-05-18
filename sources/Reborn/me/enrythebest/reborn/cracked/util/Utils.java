package me.enrythebest.reborn.cracked.util;

import org.lwjgl.opengl.*;
import me.enrythebest.reborn.cracked.*;
import net.minecraft.client.*;
import java.util.*;
import net.minecraft.src.*;

public class Utils
{
    public static void drawBoundingBox(final AxisAlignedBB var0) {
        final Tessellator var = Tessellator.instance;
        var.startDrawingQuads();
        var.addVertex(var0.minX, var0.minY, var0.minZ);
        var.addVertex(var0.minX, var0.maxY, var0.minZ);
        var.addVertex(var0.maxX, var0.minY, var0.minZ);
        var.addVertex(var0.maxX, var0.maxY, var0.minZ);
        var.addVertex(var0.maxX, var0.minY, var0.maxZ);
        var.addVertex(var0.maxX, var0.maxY, var0.maxZ);
        var.addVertex(var0.minX, var0.minY, var0.maxZ);
        var.addVertex(var0.minX, var0.maxY, var0.maxZ);
        var.draw();
        var.startDrawingQuads();
        var.addVertex(var0.maxX, var0.maxY, var0.minZ);
        var.addVertex(var0.maxX, var0.minY, var0.minZ);
        var.addVertex(var0.minX, var0.maxY, var0.minZ);
        var.addVertex(var0.minX, var0.minY, var0.minZ);
        var.addVertex(var0.minX, var0.maxY, var0.maxZ);
        var.addVertex(var0.minX, var0.minY, var0.maxZ);
        var.addVertex(var0.maxX, var0.maxY, var0.maxZ);
        var.addVertex(var0.maxX, var0.minY, var0.maxZ);
        var.draw();
        var.startDrawingQuads();
        var.addVertex(var0.minX, var0.maxY, var0.minZ);
        var.addVertex(var0.maxX, var0.maxY, var0.minZ);
        var.addVertex(var0.maxX, var0.maxY, var0.maxZ);
        var.addVertex(var0.minX, var0.maxY, var0.maxZ);
        var.addVertex(var0.minX, var0.maxY, var0.minZ);
        var.addVertex(var0.minX, var0.maxY, var0.maxZ);
        var.addVertex(var0.maxX, var0.maxY, var0.maxZ);
        var.addVertex(var0.maxX, var0.maxY, var0.minZ);
        var.draw();
        var.startDrawingQuads();
        var.addVertex(var0.minX, var0.minY, var0.minZ);
        var.addVertex(var0.maxX, var0.minY, var0.minZ);
        var.addVertex(var0.maxX, var0.minY, var0.maxZ);
        var.addVertex(var0.minX, var0.minY, var0.maxZ);
        var.addVertex(var0.minX, var0.minY, var0.minZ);
        var.addVertex(var0.minX, var0.minY, var0.maxZ);
        var.addVertex(var0.maxX, var0.minY, var0.maxZ);
        var.addVertex(var0.maxX, var0.minY, var0.minZ);
        var.draw();
        var.startDrawingQuads();
        var.addVertex(var0.minX, var0.minY, var0.minZ);
        var.addVertex(var0.minX, var0.maxY, var0.minZ);
        var.addVertex(var0.minX, var0.minY, var0.maxZ);
        var.addVertex(var0.minX, var0.maxY, var0.maxZ);
        var.addVertex(var0.maxX, var0.minY, var0.maxZ);
        var.addVertex(var0.maxX, var0.maxY, var0.maxZ);
        var.addVertex(var0.maxX, var0.minY, var0.minZ);
        var.addVertex(var0.maxX, var0.maxY, var0.minZ);
        var.draw();
        var.startDrawingQuads();
        var.addVertex(var0.minX, var0.maxY, var0.maxZ);
        var.addVertex(var0.minX, var0.minY, var0.maxZ);
        var.addVertex(var0.minX, var0.maxY, var0.minZ);
        var.addVertex(var0.minX, var0.minY, var0.minZ);
        var.addVertex(var0.maxX, var0.maxY, var0.minZ);
        var.addVertex(var0.maxX, var0.minY, var0.minZ);
        var.addVertex(var0.maxX, var0.maxY, var0.maxZ);
        var.addVertex(var0.maxX, var0.minY, var0.maxZ);
        var.draw();
    }
    
    public static void drawOutlinedBoundingBox(final AxisAlignedBB var0) {
        final Tessellator var = Tessellator.instance;
        var.startDrawing(3);
        var.addVertex(var0.minX, var0.minY, var0.minZ);
        var.addVertex(var0.maxX, var0.minY, var0.minZ);
        var.addVertex(var0.maxX, var0.minY, var0.maxZ);
        var.addVertex(var0.minX, var0.minY, var0.maxZ);
        var.addVertex(var0.minX, var0.minY, var0.minZ);
        var.draw();
        var.startDrawing(3);
        var.addVertex(var0.minX, var0.maxY, var0.minZ);
        var.addVertex(var0.maxX, var0.maxY, var0.minZ);
        var.addVertex(var0.maxX, var0.maxY, var0.maxZ);
        var.addVertex(var0.minX, var0.maxY, var0.maxZ);
        var.addVertex(var0.minX, var0.maxY, var0.minZ);
        var.draw();
        var.startDrawing(1);
        var.addVertex(var0.minX, var0.minY, var0.minZ);
        var.addVertex(var0.minX, var0.maxY, var0.minZ);
        var.addVertex(var0.maxX, var0.minY, var0.minZ);
        var.addVertex(var0.maxX, var0.maxY, var0.minZ);
        var.addVertex(var0.maxX, var0.minY, var0.maxZ);
        var.addVertex(var0.maxX, var0.maxY, var0.maxZ);
        var.addVertex(var0.minX, var0.minY, var0.maxZ);
        var.addVertex(var0.minX, var0.maxY, var0.maxZ);
        var.draw();
    }
    
    public static void drawESP(final double var0, final double var2, final double var4, final double var6, final double var8, final double var10) {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glLineWidth(1.5f);
        GL11.glDisable(2896);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glColor4d(var6, var10, var8, 0.18250000476837158);
        drawBoundingBox(new AxisAlignedBB(var0, var2, var4, var0 + 1.0, var2 + 1.0, var4 + 1.0));
        GL11.glColor4d(var6, var10, var8, 1.0);
        drawOutlinedBoundingBox(new AxisAlignedBB(var0, var2, var4, var0 + 1.0, var2 + 1.0, var4 + 1.0));
        GL11.glLineWidth(2.0f);
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glEnable(2896);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }
    
    public static double getDistance(final double var0, final double var2, final double var4) {
        MorbidWrapper.mcObj();
        final double var5 = var0 - Minecraft.thePlayer.posX;
        MorbidWrapper.mcObj();
        final double var6 = var2 - Minecraft.thePlayer.posY;
        MorbidWrapper.mcObj();
        final double var7 = var4 - Minecraft.thePlayer.posZ;
        return Math.abs(Math.sqrt(var5 * var5 + var6 * var6 + var7 * var7));
    }
    
    public static EntityPlayer getClosestPlayer() {
        double var0 = 9.99999999999E11;
        EntityPlayer var2 = null;
        MorbidWrapper.mcObj();
        for (final Object var4 : Minecraft.theWorld.playerEntities) {
            final EntityPlayer var5 = (EntityPlayer)var4;
            if (var5 instanceof EntityPlayer) {
                final EntityPlayer entityPlayer = var5;
                MorbidWrapper.mcObj();
                if (entityPlayer == Minecraft.thePlayer || getDistance(var5.posX, var5.posY, var5.posZ) >= var0) {
                    continue;
                }
                var0 = getDistance(var5.posX, var5.posY, var5.posZ);
                var2 = var5;
            }
        }
        return var2;
    }
    
    public static void drawItem(final int var0, final int var1, final ItemStack var2) {
        RenderItem.renderItemIntoGUI(Minecraft.getMinecraft().fontRenderer, Minecraft.getMinecraft().renderEngine, var2, var0, var1);
        RenderItem.renderItemAndEffectIntoGUI(Minecraft.getMinecraft().fontRenderer, Minecraft.getMinecraft().renderEngine, var2, var0, var1);
        GL11.glDisable(2884);
        GL11.glEnable(3008);
        GL11.glDisable(3042);
        GL11.glDisable(2896);
        GL11.glDisable(2884);
        GL11.glClear(256);
    }
}

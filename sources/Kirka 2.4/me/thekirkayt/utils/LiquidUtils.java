/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.utils;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class LiquidUtils {
    public static boolean isOnLiquid() {
        Minecraft.getMinecraft();
        AxisAlignedBB par1AxisAlignedBB = Minecraft.thePlayer.boundingBox.offset(0.0, -0.01, 0.0).contract(0.001, 0.001, 0.001);
        int var4 = MathHelper.floor_double(par1AxisAlignedBB.minX);
        int var5 = MathHelper.floor_double(par1AxisAlignedBB.maxX + 1.0);
        int var6 = MathHelper.floor_double(par1AxisAlignedBB.minY);
        int var7 = MathHelper.floor_double(par1AxisAlignedBB.maxY + 1.0);
        int var8 = MathHelper.floor_double(par1AxisAlignedBB.minZ);
        int var9 = MathHelper.floor_double(par1AxisAlignedBB.maxZ + 1.0);
        Vec3 var11 = new Vec3(0.0, 0.0, 0.0);
        for (int var12 = var4; var12 < var5; ++var12) {
            for (int var13 = var6; var13 < var7; ++var13) {
                for (int var14 = var8; var14 < var9; ++var14) {
                    Minecraft.getMinecraft();
                    Block var15 = Minecraft.theWorld.getBlock(var12, var13, var14);
                    if (var15 instanceof BlockAir || var15 instanceof BlockLiquid) continue;
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isInLiquid() {
        Minecraft.getMinecraft();
        AxisAlignedBB par1AxisAlignedBB = Minecraft.thePlayer.boundingBox.contract(0.001, 0.001, 0.001);
        int var4 = MathHelper.floor_double(par1AxisAlignedBB.minX);
        int var5 = MathHelper.floor_double(par1AxisAlignedBB.maxX + 1.0);
        int var6 = MathHelper.floor_double(par1AxisAlignedBB.minY);
        int var7 = MathHelper.floor_double(par1AxisAlignedBB.maxY + 1.0);
        int var8 = MathHelper.floor_double(par1AxisAlignedBB.minZ);
        int var9 = MathHelper.floor_double(par1AxisAlignedBB.maxZ + 1.0);
        Vec3 var11 = new Vec3(0.0, 0.0, 0.0);
        for (int var12 = var4; var12 < var5; ++var12) {
            for (int var13 = var6; var13 < var7; ++var13) {
                for (int var14 = var8; var14 < var9; ++var14) {
                    Minecraft.getMinecraft();
                    Block var15 = Minecraft.theWorld.getBlock(var12, var13, var14);
                    if (!(var15 instanceof BlockLiquid)) continue;
                    return true;
                }
            }
        }
        return false;
    }
}


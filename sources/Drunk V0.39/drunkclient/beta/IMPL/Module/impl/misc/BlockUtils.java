/*
 * Decompiled with CFR 0.152.
 */
package drunkclient.beta.IMPL.Module.impl.misc;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;

class BlockUtils {
    private static Minecraft mc = Minecraft.getMinecraft();

    BlockUtils() {
    }

    public static float[] getDirectionToBlock(int var0, int var1, int var2, EnumFacing var3) {
        EntityEgg var4 = new EntityEgg(BlockUtils.mc.theWorld);
        var4.posX = (double)var0 + 0.5;
        var4.posY = (double)var1 + 0.5;
        var4.posZ = (double)var2 + 0.5;
        var4.posX += (double)var3.getDirectionVec().getX() * 0.25;
        var4.posY += (double)var3.getDirectionVec().getY() * 0.25;
        var4.posZ += (double)var3.getDirectionVec().getZ() * 0.25;
        return BlockUtils.getDirectionToEntity(var4);
    }

    private static float[] getDirectionToEntity(Entity var0) {
        float[] fArray = new float[2];
        fArray[0] = BlockUtils.getYaw(var0) + Minecraft.thePlayer.rotationYaw;
        fArray[1] = BlockUtils.getPitch(var0) + Minecraft.thePlayer.rotationPitch;
        return fArray;
    }

    public static float[] getRotationNeededForBlock(EntityPlayer ep, BlockPos pos) {
        double d1 = (double)pos.getX() - ep.posX;
        double d2 = (double)pos.getY() + 0.5 - ep.posY + (double)ep.getEyeHeight();
        double d3 = (double)pos.getZ() - ep.posZ;
        double d4 = Math.sqrt(d1 * d2 * d3);
        float f1 = (float)(Math.atan2(d3, d1) * 180.0 / Math.PI) - 90.0f;
        float f2 = (float)(-(Math.atan2(d2, d4) * 180.0 / Math.PI));
        return new float[]{f1, f2};
    }

    public static float getYaw(Entity var0) {
        double var5;
        double var1 = var0.posX - Minecraft.thePlayer.posX;
        double var3 = var0.posZ - Minecraft.thePlayer.posZ;
        if (var3 < 0.0 && var1 < 0.0) {
            var5 = 90.0 + Math.toDegrees(Math.atan(var3 / var1));
            return MathHelper.wrapAngleTo180_float(-(Minecraft.thePlayer.rotationYaw - (float)var5));
        }
        if (var3 < 0.0 && var1 > 0.0) {
            var5 = -90.0 + Math.toDegrees(Math.atan(var3 / var1));
            return MathHelper.wrapAngleTo180_float(-(Minecraft.thePlayer.rotationYaw - (float)var5));
        }
        var5 = Math.toDegrees(-Math.atan(var1 / var3));
        return MathHelper.wrapAngleTo180_float(-(Minecraft.thePlayer.rotationYaw - (float)var5));
    }

    public static float getPitch(Entity var0) {
        double var1 = var0.posX - Minecraft.thePlayer.posX;
        double var3 = var0.posZ - Minecraft.thePlayer.posZ;
        double var5 = var0.posY - 1.6 + (double)var0.getEyeHeight() - Minecraft.thePlayer.posY;
        double var7 = MathHelper.sqrt_double(var1 * var1 + var3 * var3);
        double var9 = -Math.toDegrees(Math.atan(var5 / var7));
        return -MathHelper.wrapAngleTo180_float(Minecraft.thePlayer.rotationPitch - (float)var9);
    }
}


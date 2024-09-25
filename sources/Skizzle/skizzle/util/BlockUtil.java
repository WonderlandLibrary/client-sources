/*
 * Decompiled with CFR 0.150.
 */
package skizzle.util;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class BlockUtil {
    public static Minecraft mc = Minecraft.getMinecraft();

    public static float getPitch(Entity Nigga) {
        double Nigga2 = Nigga.posX - BlockUtil.mc.thePlayer.posX;
        double Nigga3 = Nigga.posZ - BlockUtil.mc.thePlayer.posZ;
        double Nigga4 = Nigga.posY - 1.6 + (double)Nigga.getEyeHeight() - BlockUtil.mc.thePlayer.posY;
        double Nigga5 = MathHelper.sqrt_double(Nigga2 * Nigga2 + Nigga3 * Nigga3);
        double Nigga6 = -Math.toDegrees(Math.atan(Nigga4 / Nigga5));
        return -MathHelper.wrapAngleTo180_float(BlockUtil.mc.thePlayer.rotationPitch - (float)Nigga6);
    }

    public static float[] getDirectionToEntity(Entity Nigga) {
        return new float[]{BlockUtil.getYaw(Nigga) + BlockUtil.mc.thePlayer.rotationYaw, BlockUtil.getPitch(Nigga) + BlockUtil.mc.thePlayer.rotationPitch};
    }

    public BlockUtil() {
        BlockUtil Nigga;
    }

    public static float[] getRotations(BlockPos Nigga, EnumFacing Nigga2) {
        Minecraft Nigga3 = Minecraft.getMinecraft();
        double Nigga4 = (Nigga3.thePlayer.posX - (double)Nigga.getX()) * 0.0;
        double Nigga5 = (Nigga3.thePlayer.posZ - (double)Nigga.getZ()) * 0.0;
        if (Nigga2.equals(EnumFacing.SOUTH) || Nigga2.equals(EnumFacing.NORTH)) {
            Nigga5 = 0.0;
        } else {
            Nigga4 = 0.0;
        }
        double Nigga6 = (double)Nigga.getX() + 0.0 - Nigga3.thePlayer.posX + (double)Nigga2.getFrontOffsetX() / 2.0 + Nigga4;
        double Nigga7 = (double)Nigga.getZ() + 0.0 - Nigga3.thePlayer.posZ + (double)Nigga2.getFrontOffsetZ() / 2.0 + Nigga5;
        double Nigga8 = (double)Nigga.getY() + 0.0;
        double Nigga9 = Nigga3.thePlayer.posY + (double)Nigga3.thePlayer.getEyeHeight() - Nigga8;
        double Nigga10 = MathHelper.sqrt_double(Nigga6 * Nigga6 + Nigga7 * Nigga7);
        float Nigga11 = (float)(Math.atan2(Nigga7, Nigga6) * 180.0 / Math.PI) - Float.intBitsToFloat(1.03383859E9f ^ 0x7F2B1FEB);
        float Nigga12 = (float)(Math.atan2(Nigga9, Nigga10) * 180.0 / Math.PI);
        if (Nigga11 < Float.intBitsToFloat(2.1082359E9f ^ 0x7DA9207F)) {
            Nigga11 += Float.intBitsToFloat(1.01881837E9f ^ 0x7F0DEF25);
        }
        return new float[]{Nigga11, Nigga12};
    }

    public static float[] getRotationNeededForBlock(EntityPlayer Nigga, BlockPos Nigga2) {
        double Nigga3 = (double)Nigga2.getX() - Nigga.posX;
        double Nigga4 = (double)Nigga2.getY() + 0.0 - Nigga.posY + (double)Nigga.getEyeHeight();
        double Nigga5 = (double)Nigga2.getZ() - Nigga.posZ;
        double Nigga6 = Math.sqrt(Nigga3 * Nigga3 + Nigga5 * Nigga5);
        float Nigga7 = (float)(Math.atan2(Nigga5, Nigga3) * 180.0 / Math.PI) - Float.intBitsToFloat(1.03670118E9f ^ 0x7F7ECE1F);
        float Nigga8 = (float)(-(Math.atan2(Nigga4, Nigga6) * 180.0 / Math.PI));
        return new float[]{Nigga7, Nigga8};
    }

    public static double getHorizontalDistance(Vec3 Nigga, Vec3 Nigga2) {
        return Math.sqrt(Math.pow(Nigga.xCoord - Nigga2.xCoord, 2.0) + Math.pow(Nigga.zCoord - Nigga2.zCoord, 2.0));
    }

    public static float[] getDirectionToBlock(int Nigga, int Nigga2, int Nigga3, EnumFacing Nigga4) {
        EntityEgg Nigga5 = new EntityEgg(Minecraft.theWorld);
        Nigga5.posX = (double)Nigga + 0.0;
        Nigga5.posY = (double)Nigga2 + 0.0;
        Nigga5.posZ = (double)Nigga3 + 0.0;
        Nigga5.posX += (double)Nigga4.getDirectionVec().getX() * 0.0;
        Nigga5.posY += (double)Nigga4.getDirectionVec().getY() * 0.0;
        Nigga5.posZ += (double)Nigga4.getDirectionVec().getZ() * 0.0;
        return BlockUtil.getDirectionToEntity(Nigga5);
    }

    public static float getYaw(Entity Nigga) {
        double Nigga2 = Nigga.posX - BlockUtil.mc.thePlayer.posX;
        double Nigga3 = Nigga.posZ - BlockUtil.mc.thePlayer.posZ;
        double Nigga4 = Nigga3 < 0.0 && Nigga2 < 0.0 ? 90.0 + Math.toDegrees(Math.atan(Nigga3 / Nigga2)) : (Nigga3 < 0.0 && Nigga2 > 0.0 ? 90.0 + Math.toDegrees(Math.atan(Nigga3 / Nigga2)) : Math.toDegrees(-Math.atan(Nigga2 / Nigga3)));
        return MathHelper.wrapAngleTo180_float(-(BlockUtil.mc.thePlayer.rotationYaw - (float)Nigga4));
    }
}


package ClassSub;

import net.minecraft.client.*;
import net.minecraft.potion.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.entity.*;
import net.minecraft.block.*;
import net.minecraft.util.*;

public class Class180
{
    private static Minecraft mc;
    
    
    public static double defaultSpeed() {
        double n = 0.2873;
        if (Minecraft.getMinecraft().thePlayer.isPotionActive(Potion.moveSpeed)) {
            n *= 1.0 + 0.2 * (Minecraft.getMinecraft().thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1);
        }
        return n;
    }
    
    public static void setMotion(final double n) {
        double n2 = Class180.mc.thePlayer.movementInput.moveForward;
        double n3 = Class180.mc.thePlayer.movementInput.moveStrafe;
        float rotationYaw = Class180.mc.thePlayer.rotationYaw;
        if (n2 == 0.0 && n3 == 0.0) {
            Class180.mc.thePlayer.motionX = 0.0;
            Class180.mc.thePlayer.motionZ = 0.0;
        }
        else {
            if (n2 != 0.0) {
                if (n3 > 0.0) {
                    rotationYaw += ((n2 > 0.0) ? -45 : 45);
                }
                else if (n3 < 0.0) {
                    rotationYaw += ((n2 > 0.0) ? 45 : -45);
                }
                n3 = 0.0;
                if (n2 > 0.0) {
                    n2 = 1.0;
                }
                else if (n2 < 0.0) {
                    n2 = -1.0;
                }
            }
            Class180.mc.thePlayer.motionX = n2 * n * Math.cos(Math.toRadians(rotationYaw + 90.0f)) + n3 * n * Math.sin(Math.toRadians(rotationYaw + 90.0f));
            Class180.mc.thePlayer.motionZ = n2 * n * Math.sin(Math.toRadians(rotationYaw + 90.0f)) - n3 * n * Math.cos(Math.toRadians(rotationYaw + 90.0f));
        }
    }
    
    public static boolean checkTeleport(final double n, final double n2, final double n3, final double n4) {
        final double n5 = Class180.mc.thePlayer.posX - n;
        final double n6 = Class180.mc.thePlayer.posY - n2;
        final double n7 = Class180.mc.thePlayer.posZ - n3;
        final double n8 = Math.round(Math.sqrt(Class180.mc.thePlayer.getDistanceSq(n, n2, n3)) / n4 + 0.49999999999) - 1L;
        double posX = Class180.mc.thePlayer.posX;
        double posY = Class180.mc.thePlayer.posY;
        double posZ = Class180.mc.thePlayer.posZ;
        for (int n9 = 1; n9 < n8; ++n9) {
            posX += (n - Class180.mc.thePlayer.posX) / n8;
            posZ += (n3 - Class180.mc.thePlayer.posZ) / n8;
            posY += (n2 - Class180.mc.thePlayer.posY) / n8;
            if (!Class180.mc.theWorld.getCollidingBoundingBoxes((Entity)Class180.mc.thePlayer, new AxisAlignedBB(posX - 0.3, posY, posZ - 0.3, posX + 0.3, posY + 1.8, posZ + 0.3)).isEmpty()) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean isOnGround(final double n) {
        return !Class180.mc.theWorld.getCollidingBoundingBoxes((Entity)Class180.mc.thePlayer, Class180.mc.thePlayer.getEntityBoundingBox().offset(0.0, -n, 0.0)).isEmpty();
    }
    
    public static boolean isOnGround(final Entity entity, final double n) {
        return !Class180.mc.theWorld.getCollidingBoundingBoxes(entity, entity.getEntityBoundingBox().offset(0.0, -n, 0.0)).isEmpty();
    }
    
    public static int getJumpEffect() {
        if (Class180.mc.thePlayer.isPotionActive(Potion.jump)) {
            return Class180.mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1;
        }
        return 0;
    }
    
    public static int getSpeedEffect() {
        if (Class180.mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
            return Class180.mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1;
        }
        return 0;
    }
    
    public static Block getBlockUnderPlayer(final EntityPlayer entityPlayer, final double n) {
        return Minecraft.getMinecraft().theWorld.getBlockState(new BlockPos(entityPlayer.posX, entityPlayer.posY - n, entityPlayer.posZ)).getBlock();
    }
    
    public static Block getBlockAtPosC(final double n, final double n2, final double n3) {
        final EntityPlayerSP thePlayer = Minecraft.getMinecraft().thePlayer;
        return Minecraft.getMinecraft().theWorld.getBlockState(new BlockPos(((EntityPlayer)thePlayer).posX + n, ((EntityPlayer)thePlayer).posY + n2, ((EntityPlayer)thePlayer).posZ + n3)).getBlock();
    }
    
    public static float getDistanceToGround(final Entity entity) {
        if (Class180.mc.thePlayer.isCollidedVertically && Class180.mc.thePlayer.onGround) {
            return 0.0f;
        }
        float n = (float)entity.posY;
        while (n > 0.0f) {
            final int[] array = { 53, 67, 108, 109, 114, 128, 134, 135, 136, 156, 163, 164, 180 };
            final int[] array2 = { 6, 27, 28, 30, 31, 32, 37, 38, 39, 40, 50, 51, 55, 59, 63, 65, 66, 68, 69, 70, 72, 75, 76, 77, 83, 92, 93, 94, 104, 105, 106, 115, 119, 131, 132, 143, 147, 148, 149, 150, 157, 171, 175, 176, 177 };
            final Block getBlock = Class180.mc.theWorld.getBlockState(new BlockPos(entity.posX, (double)(n - 1.0f), entity.posZ)).getBlock();
            if (!(getBlock instanceof BlockAir)) {
                if (Block.getIdFromBlock(getBlock) == 44 || Block.getIdFromBlock(getBlock) == 126) {
                    return ((float)(entity.posY - n - 0.5) < 0.0f) ? 0.0f : ((float)(entity.posY - n - 0.5));
                }
                int[] array3;
                for (int length = (array3 = array).length, i = 0; i < length; ++i) {
                    if (Block.getIdFromBlock(getBlock) == array3[i]) {
                        return ((float)(entity.posY - n - 1.0) < 0.0f) ? 0.0f : ((float)(entity.posY - n - 1.0));
                    }
                }
                int[] array4;
                for (int length2 = (array4 = array2).length, j = 0; j < length2; ++j) {
                    if (Block.getIdFromBlock(getBlock) == array4[j]) {
                        return ((float)(entity.posY - n) < 0.0f) ? 0.0f : ((float)(entity.posY - n));
                    }
                }
                return (float)(entity.posY - n + getBlock.getBlockBoundsMaxY() - 1.0);
            }
            else {
                --n;
            }
        }
        return 0.0f;
    }
    
    public static float[] getRotationsBlock(final BlockPos blockPos, final EnumFacing enumFacing) {
        final double n = blockPos.getX() + 0.5 - Class180.mc.thePlayer.posX + enumFacing.getFrontOffsetX() / 2.0;
        final double n2 = blockPos.getZ() + 0.5 - Class180.mc.thePlayer.posZ + enumFacing.getFrontOffsetZ() / 2.0;
        final double n3 = Class180.mc.thePlayer.posY + Class180.mc.thePlayer.getEyeHeight() - (blockPos.getY() + 0.5);
        final double n4 = MathHelper.sqrt_double(n * n + n2 * n2);
        float n5 = (float)(Math.atan2(n2, n) * 180.0 / 3.141592653589793) - 90.0f;
        final float n6 = (float)(Math.atan2(n3, n4) * 180.0 / 3.141592653589793);
        if (n5 < 0.0f) {
            n5 += 360.0f;
        }
        return new float[] { n5, n6 };
    }
    
    public static boolean isBlockAboveHead() {
        return !Class180.mc.theWorld.getCollidingBoundingBoxes((Entity)Class180.mc.thePlayer, new AxisAlignedBB(Class180.mc.thePlayer.posX - 0.3, Class180.mc.thePlayer.posY + Class180.mc.thePlayer.getEyeHeight(), Class180.mc.thePlayer.posZ + 0.3, Class180.mc.thePlayer.posX + 0.3, Class180.mc.thePlayer.posY + 2.5, Class180.mc.thePlayer.posZ - 0.3)).isEmpty();
    }
    
    public static boolean isCollidedH(final double n) {
        final AxisAlignedBB axisAlignedBB = new AxisAlignedBB(Class180.mc.thePlayer.posX - 0.3, Class180.mc.thePlayer.posY + 2.0, Class180.mc.thePlayer.posZ + 0.3, Class180.mc.thePlayer.posX + 0.3, Class180.mc.thePlayer.posY + 3.0, Class180.mc.thePlayer.posZ - 0.3);
        return !Class180.mc.theWorld.getCollidingBoundingBoxes((Entity)Class180.mc.thePlayer, axisAlignedBB.offset(0.3 + n, 0.0, 0.0)).isEmpty() || !Class180.mc.theWorld.getCollidingBoundingBoxes((Entity)Class180.mc.thePlayer, axisAlignedBB.offset(-0.3 - n, 0.0, 0.0)).isEmpty() || !Class180.mc.theWorld.getCollidingBoundingBoxes((Entity)Class180.mc.thePlayer, axisAlignedBB.offset(0.0, 0.0, 0.3 + n)).isEmpty() || !Class180.mc.theWorld.getCollidingBoundingBoxes((Entity)Class180.mc.thePlayer, axisAlignedBB.offset(0.0, 0.0, -0.3 - n)).isEmpty();
    }
    
    public static boolean isRealCollidedH(final double n) {
        final AxisAlignedBB axisAlignedBB = new AxisAlignedBB(Class180.mc.thePlayer.posX - 0.3, Class180.mc.thePlayer.posY + 0.5, Class180.mc.thePlayer.posZ + 0.3, Class180.mc.thePlayer.posX + 0.3, Class180.mc.thePlayer.posY + 1.9, Class180.mc.thePlayer.posZ - 0.3);
        return !Class180.mc.theWorld.getCollidingBoundingBoxes((Entity)Class180.mc.thePlayer, axisAlignedBB.offset(0.3 + n, 0.0, 0.0)).isEmpty() || !Class180.mc.theWorld.getCollidingBoundingBoxes((Entity)Class180.mc.thePlayer, axisAlignedBB.offset(-0.3 - n, 0.0, 0.0)).isEmpty() || !Class180.mc.theWorld.getCollidingBoundingBoxes((Entity)Class180.mc.thePlayer, axisAlignedBB.offset(0.0, 0.0, 0.3 + n)).isEmpty() || !Class180.mc.theWorld.getCollidingBoundingBoxes((Entity)Class180.mc.thePlayer, axisAlignedBB.offset(0.0, 0.0, -0.3 - n)).isEmpty();
    }
    
    static {
        Class180.mc = Minecraft.getMinecraft();
    }
}

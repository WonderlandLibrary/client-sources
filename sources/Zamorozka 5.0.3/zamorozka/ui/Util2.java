package zamorozka.ui;

import net.minecraft.client.Minecraft;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import java.util.Random;


public class Util2 {
    private static final Random RANDOM = new Random();


    public static double random(double max, double min) {
        return (Math.random() * (max - min)) + min;
    }

    public static Vec3d getRandomCenter(AxisAlignedBB bb) {
        return new Vec3d(bb.minX + (bb.maxX - bb.minX) * 0.8 * Math.random(), bb.minY + (bb.maxY - bb.minY) * 1 * Math.random(), bb.minZ + (bb.maxZ - bb.minZ) * 0.8 * Math.random());
    }

    public static float[] getNeededRotations(final Vec3d vec) {
        final Vec3d eyesPos = new Vec3d(Minecraft.player.posX, Minecraft.player.posY + Minecraft.player.getEyeHeight(), Minecraft.player.posZ);
        final double diffX = vec.xCoord - eyesPos.xCoord;
        final double diffY = vec.yCoord - eyesPos.yCoord;
        final double diffZ = vec.zCoord - eyesPos.zCoord;
        final double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        final float yaw = (float) Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
        final float pitch = (float) (-Math.toDegrees(Math.atan2(diffY, diffXZ)));
        return new float[]{MathHelper.wrapDegrees(yaw), MathHelper.wrapDegrees(pitch)};
    }

    public static float getDirection() {
        Minecraft mc = Minecraft.getMinecraft();
        float var1 = Minecraft.player.rotationYaw;

        if (Minecraft.player.moveForward < 0.0F) {
            var1 += 180.0F;
        }

        float forward = 1.0F;

        if (Minecraft.player.moveForward < 0.0F) {
            forward = -0.5F;
        } else if (Minecraft.player.moveForward > 0.0F) {
            forward = 0.5F;
        }

        if (Minecraft.player.moveStrafing > 0.0F) {
            var1 -= 90.0F * forward;
        }

        if (Minecraft.player.moveStrafing < 0.0F) {
            var1 += 90.0F * forward;
        }

        var1 *= 0.017453292F;
        return var1;
    }

    public static BlockPos getHypixelBlockpos(String str) {
        int val = 89;
        if (str != null && str.length() > 1) {
            char[] chs = str.toCharArray();

            int lenght = chs.length;
            for (int i = 0; i < lenght; i++)
                val += (int) chs[i] * str.length() * str.length() + (int) str.charAt(0) + (int) str.charAt(1);
            val /= str.length();
        }
        return new BlockPos(val, -val % 255, val);
    }
}


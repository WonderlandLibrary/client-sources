package club.strifeclient.util.player;

import club.strifeclient.util.misc.MinecraftUtil;
import club.strifeclient.util.rendering.InterpolateUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public final class RotationUtil extends MinecraftUtil {

    public static float getYawDifference(float f, float i) {
        return MathHelper.wrapAngleTo180_float(f - i);
    }

    public static float[] getRotationFromPosition(double x, double y, double z) {
        double diffX = (x - mc.thePlayer.posX);
        double diffY = (y - mc.thePlayer.posY);
        double diffZ = (z - mc.thePlayer.posZ);
        double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);;
        float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90F;
        float pitch = (float)-Math.toDegrees(Math.atan2(diffY, dist));
        return new float[] {MathHelper.wrapAngleTo180_float(yaw), pitch};
    }

    public static float[] getRotationFromPosition(BlockPos pos) {
        return getRotationFromPosition(pos.getX(), pos.getY(), pos.getZ());
    }

    public static float[] getRotationFromVector(Vec3 vec) {
        return getRotationFromPosition(vec.xCoord, vec.yCoord, vec.zCoord);
    }

    public static float[] getRotationFromEntity(EntityLivingBase entity) {
        double diffX = entity.posX - mc.thePlayer.posX;
        double diffY = (entity.posY - entity.getEyeHeight()) - (mc.thePlayer.posY + mc.thePlayer.getEyeHeight());
        double diffZ = entity.posZ - mc.thePlayer.posZ;
        double dist = MathHelper.hypot_double(diffZ, diffX);
        float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90F;
        float pitch = (float)-Math.toDegrees(Math.atan2(diffY, dist));
        return new float[] {MathHelper.wrapAngleTo180_float(yaw), pitch};
    }

    public static float[] getScaffoldRotations(BlockPos pos, EnumFacing face) {
        BlockPos newPos = pos.add(0.5, -0.5, 0.5);
//                .add(MovementUtil.getRandomHypixelValues(),
//                        MovementUtil.getRandomHypixelValues(),
//                        MovementUtil.getRandomHypixelValues())
//                .add(0, -mc.thePlayer.getEyeHeight() - 12, 0);
        return getRotationFromPosition(newPos.getX(), newPos.getY(), newPos.getZ());
    }

    public static Vec3 getLookVec(float yaw, float pitch, float prevYaw, float prevPitch, float partialTicks) {
        if (partialTicks == 1.0F) {
            return getVectorForRotation(yaw, pitch);
        } else {
            float y = InterpolateUtil.interpolateFloat(yaw, prevYaw, partialTicks);
            float p = InterpolateUtil.interpolateFloat(pitch, prevPitch, partialTicks);
            return getVectorForRotation(y, p);
        }
    }

    public static Vec3 getVectorForRotation(float yaw, float pitch) {
        float f = MathHelper.cos(-yaw * MathHelper.deg2Rad - MathHelper.PI);
        float f1 = MathHelper.sin(-yaw * MathHelper.deg2Rad - MathHelper.PI);
        float f2 = -MathHelper.cos(-pitch * MathHelper.deg2Rad);
        float f3 = MathHelper.sin(-pitch * MathHelper.deg2Rad);
        return new Vec3(f1 * f2, f3, f * f2);
    }

    public static float getSensitivityMultiplier() {
        float f = mc.gameSettings.mouseSensitivity * 0.6F + 0.2F;
        return (f * f * f * 8.0F) * 0.15F;
    }

    public static float clampRotationDelta(final float delta, final float cap) {
//        if(delta > cap)
//            return cap;
//        else if(delta < -cap)
//            return -cap;
//        else return delta;
        return MathHelper.clamp_float(delta, -cap, cap);
    }
}

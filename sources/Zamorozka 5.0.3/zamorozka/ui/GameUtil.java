package zamorozka.ui;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class GameUtil extends Wrapper {

    public static IBlockState getBlock(int x, int y, int z) {
        return getWorld().getChunkFromBlockCoords(new BlockPos(x, y, z)).getBlockState(new BlockPos(x, y, z));
    }

    public static IBlockState getBlock(BlockPos pos) {
        return getWorld().getChunkFromBlockCoords(pos).getBlockState(pos);
    }

    public static IBlockState getBlock(double x, double y, double z) {
        x = MathHelper.sqrt_double(x);
        y = MathHelper.sqrt_double(y);
        z = MathHelper.sqrt_double(z);
        return getWorld().getChunkFromBlockCoords(new BlockPos(x, y, z)).getBlockState(new BlockPos(x, y, z));
    }

    public static float[] getAngles(Entity targetentity, Entity forentity) {
        double x = targetentity.posX - forentity.posX;
        double z = targetentity.posZ - forentity.posZ;
        double y = (targetentity.getEntityBoundingBox().maxY - 0.4) - (forentity.getEntityBoundingBox().maxY - 0.4);
        double dist = MathHelper.sqrt_double(x * x + z * z);
        float yaw = (float) (Math.atan2(z, x) * 180.0D / Math.PI) - 90.0F;
        float pitch = (float) -(Math.atan2(y, dist) * 180.0D / Math.PI);
        return (new float[]{yaw, pitch});
    }

    public static float[] getAngles(double bx, double by, double bz, Entity forentity) {
        double x = bx - forentity.posX;
        double y = by - (forentity.getEntityBoundingBox().maxY - 0.4);
        double z = bz - forentity.posZ;
        double dist = MathHelper.sqrt_double(x * x + z * z);
        float yaw = (float) (Math.atan2(z, x) * 180.0D / Math.PI) - 90.0F;
        float pitch = (float) -(Math.atan2(y, dist) * 180.0D / Math.PI);
        return (new float[]{yaw, pitch});
    }

    public static float getDistanceBetweenAngle(float clientyaw, float otheryaw) {
        float angle = Math.abs(otheryaw - clientyaw) % 360.0F;
        if (angle > 180.0F) {
            angle = 360.0F - angle;
        }
        return Math.abs(angle);
    }

    public static float[] getYawAndPitch(Entity paramEntityPlayer) {
        double d1 = paramEntityPlayer.posX - getPlayer().posX;
        double d2 = paramEntityPlayer.posZ - getPlayer().posZ;
        double d3 = getPlayer().posY + 0.12D - (paramEntityPlayer.posY + 1.82D);
        double d4 = MathHelper.sqrt_double(d1 + d2 + d3);
        float f1 = (float) (Math.atan2(d2, d1) * 180.0D / Math.PI) - 90.0F;
        float f2 = (float) (Math.atan2(d3, d4) * 180.0D / Math.PI);

        return new float[]{f1, f2};
    }

    public static boolean entityInFOV(Entity e, double fov) {
        float[] arrayOfFloat = getYawAndPitch(e);
        double d2g = getDistanceBetweenAngle(arrayOfFloat[0], getPlayer().rotationYaw);
        return d2g <= fov;
    }
}
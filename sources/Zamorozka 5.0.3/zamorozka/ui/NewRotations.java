package zamorozka.ui;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class NewRotations implements MCUtil {
    public final Vec3d getVectorForRotation(float pitch, float yaw) {
        float f = MathHelper.cos(-yaw * 0.017453292F - 3.1415927F);
        float f1 = MathHelper.sin(-yaw * 0.017453292F - 3.1415927F);
        float f2 = -MathHelper.cos(-pitch * 0.017453292F);
        float f3 = MathHelper.sin(-pitch * 0.017453292F);
        return new Vec3d((f1 * f2), f3, (f * f2));
    }

    public static float[] getIntaveRots(BlockPos bp, EnumFacing enumface) {
        double x = bp.getX() + 0.5D, y = bp.getY() + 0.5D, z = bp.getZ() + 0.5D;
        if (enumface != null)
            if (EnumFacing.UP != null) {
                y += 0.5D;
            } else if (EnumFacing.DOWN != null) {
                y -= 0.5D;
            } else if (EnumFacing.WEST != null) {
                x += 0.5D;
            } else if (EnumFacing.EAST != null) {
                x -= 0.5D;
            } else if (EnumFacing.NORTH != null) {
                z += 0.5D;
            } else if (EnumFacing.SOUTH != null) {
                z -= 0.5D;
            }
        double dX = x - mc.player.posX, dY = y - mc.player.posY + mc.player.getEyeHeight();
        double dZ = z - mc.player.posZ;
        float yaw = (float) (Math.atan2(dZ, dX) * 180.0D / Math.PI) - 90.0F;
        float pitch = (float) -(Math.atan2(dY, Math.sqrt(dX * dX + dZ * dZ)) * 180.0D / Math.PI);
        yaw = MathHelper.wrapDegrees(yaw);
        pitch = MathHelper.wrapDegrees(pitch);
        return new float[]{yaw, pitch};
    }

    public static float[] getBlockRotations(final double x, final double y, final double z) {
        final double var4 = x - mc.player.posX + 0.5;
        final double var5 = z - mc.player.posZ + 0.5;
        final double var6 = y - (mc.player.posY + mc.player.getEyeHeight() - 1.0);
        final double var7 = MathHelper.sqrt(var4 * var4 + var5 * var5);
        final float var8 = (float)(Math.atan2(var5, var4) * 180.0 / 3.141592653589793) - 90.0f;
        return new float[] { var8, (float)(-(Math.atan2(var6, var7) * 180.0 / 3.141592653589793)) };
    }

    public static float[] getRotations(double posX, double posY, double posZ) {
        EntityPlayerSP player = mc.player;
        double x = posX - player.posX;
        double y = posY - player.posY + player.getEyeHeight();
        double z = posZ - player.posZ;
        double dist = MathHelper.sqrt(x * x + z * z);
        float yaw = (float)(Math.atan2(z, x) * 180.0D / Math.PI) - 90.0F;
        float pitch = (float)-(Math.atan2(y, dist) * 180.0D / Math.PI);
        return new float[] { yaw, pitch };
    }

    public static float[] getRotationsEntity(EntityLivingBase entity) {
        return getRotations(entity.posX + MathUtil.randomNumber(0.03D, -0.03D), entity.posY + entity.getEyeHeight() - 0.4D + MathUtil.randomNumber(0.07D, -0.07D), entity.posZ + MathUtil.randomNumber(0.03D, -0.03D));
    }

    public static float[] getRotations(EntityLivingBase ent) {
        double x = ent.posX;
        double z = ent.posZ;
        double y = ent.posY + ent.getEyeHeight() / 2.0F;
        return getRotationFromPosition(x, z, y);
    }

    public static float[] getAngles(Entity e) {
        return new float[]{getYawChangeToEntity(e) + mc.player.rotationYaw, getPitchChangeToEntity(e) + mc.player.rotationPitch};
    }

    public static float getPitchChangeToEntity(Entity entity) {
        double deltaX = entity.posX - mc.player.posX;
        double deltaZ = entity.posZ - mc.player.posZ;
        double deltaY = entity.posY - 1.6D + entity.getEyeHeight() - 0.4D - mc.player.posY;
        double distanceXZ = MathHelper.sqrt(deltaX * deltaX + deltaZ * deltaZ);
        double pitchToEntity = -Math.toDegrees(Math.atan(deltaY / distanceXZ));
        return -MathHelper.wrapDegrees(mc.player.rotationPitch - (float) pitchToEntity);
    }

    public static float getYawChangeToEntity(Entity entity) {
        double deltaX = entity.posX - mc.player.posX;
        double deltaZ = entity.posZ - mc.player.posZ;
        double yawToEntity;
        if ((deltaZ < 0.0D) && (deltaX < 0.0D)) {
            yawToEntity = 90.0D + Math.toDegrees(Math.atan(deltaZ / deltaX));
        } else {
            if ((deltaZ < 0.0D) && (deltaX > 0.0D)) {
                yawToEntity = -90.0D + Math.toDegrees(Math.atan(deltaZ / deltaX));
            } else
                yawToEntity = Math.toDegrees(-Math.atan(deltaX / deltaZ));
        }
        return MathHelper.wrapDegrees(-(mc.player.rotationYaw - (float) yawToEntity));
    }

    public static float normalizeAngle(float angle) {
        return MathHelper.wrapDegrees((angle + 180.0F) % 360.0F - 180.0F);
    }

    public static boolean canEntityBeSeen(Entity e) {
        Vec3d vec1 = new Vec3d(mc.player.posX, mc.player.posY + mc.player.getEyeHeight(), mc.player.posZ);

        AxisAlignedBB box = e.getEntityBoundingBox();
        Vec3d vec2 = new Vec3d(e.posX, e.posY + (e.getEyeHeight() / 1.32F), e.posZ);
        double minx = e.posX - 0.25;
        double maxx = e.posX + 0.25;
        double miny = e.posY;
        double maxy = e.posY + Math.abs(e.posY - box.maxY);
        double minz = e.posZ - 0.25;
        double maxz = e.posZ + 0.25;
        boolean see = mc.world.rayTraceBlocks(vec1, vec2) == null ? true : false;
        if (see)
            return true;
        vec2 = new Vec3d(maxx, miny, minz);
        see = mc.world.rayTraceBlocks(vec1, vec2) == null ? true : false;
        if (see)
            return true;
        vec2 = new Vec3d(minx, miny, minz);
        see = mc.world.rayTraceBlocks(vec1, vec2) == null ? true : false;

        if (see)
            return true;
        vec2 = new Vec3d(minx, miny, maxz);
        see = mc.world.rayTraceBlocks(vec1, vec2) == null ? true : false;
        if (see)
            return true;
        vec2 = new Vec3d(maxx, miny, maxz);
        see = mc.world.rayTraceBlocks(vec1, vec2) == null ? true : false;
        if (see)
            return true;

        vec2 = new Vec3d(maxx, maxy, minz);
        see = mc.world.rayTraceBlocks(vec1, vec2) == null ? true : false;

        if (see)
            return true;
        vec2 = new Vec3d(minx, maxy, minz);

        see = mc.world.rayTraceBlocks(vec1, vec2) == null ? true : false;
        if (see)
            return true;
        vec2 = new Vec3d(minx, maxy, maxz - 0.1);
        see = mc.world.rayTraceBlocks(vec1, vec2) == null ? true : false;
        if (see)
            return true;
        vec2 = new Vec3d(maxx, maxy, maxz);
        see = mc.world.rayTraceBlocks(vec1, vec2) == null ? true : false;
        if (see)
            return true;
        return false;
    }

    public static float[] getNeededFacing(final Vec3d target, final Vec3d from) {
        final double diffX = target.xCoord - from.xCoord;
        final double diffY = target.yCoord - from.yCoord;
        final double diffZ = target.zCoord - from.zCoord;
        final double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        final float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
        final float pitch = (float)(-Math.toDegrees(Math.atan2(diffY, diffXZ)));
        return new float[] { MathHelper.wrapDegrees(yaw), MathHelper.wrapDegrees(pitch) };
    }

    public static float[] getRotationFromPosition(double x, double z, double y) {
        double xDiff = x - mc.player.posX;
        double zDiff = z - mc.player.posZ;
        double yDiff = y - mc.player.posY - 1.2;

        double dist = MathHelper.sqrt(xDiff * xDiff + zDiff * zDiff);
        float yaw = (float) (Math.atan2(zDiff, xDiff) * 180.0D / 3.141592653589793D) - 90.0F;
        float pitch = (float) -(Math.atan2(yDiff, dist) * 180.0D / 3.141592653589793D);
        return new float[]{yaw, pitch};
    }

    public static Vec3d getEyesPos() {
        return new Vec3d(mc.player.posX, mc.player.posY + mc.player.getEyeHeight(), mc.player.posZ);
    }
}
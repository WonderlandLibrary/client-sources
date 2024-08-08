package lol.point.returnclient.util.minecraft;

import lol.point.returnclient.util.MinecraftInstance;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public final class RotationUtil implements MinecraftInstance {

    public static double calculateAngleToEntity(Entity entity) {
        Vec3 playerPos = new Vec3(mc.thePlayer.posX, mc.thePlayer.posY + mc.thePlayer.getEyeHeight(), mc.thePlayer.posZ);
        Vec3 targetPos = new Vec3(entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ);
        Vec3 playerLookVec = mc.thePlayer.getLook(1.0f);
        Vec3 playerToTarget = targetPos.subtract(playerPos).normalize();

        double dotProduct = playerToTarget.dotProduct(playerLookVec);
        return Math.toDegrees(Math.acos(dotProduct));
    }

    public static float[] getRotationsToEntity(Vec3 aimVector) {
        if (aimVector == null) {
            return null;
        }

        double x = aimVector.xCoord - mc.thePlayer.posX;
        double y = aimVector.yCoord - (mc.thePlayer.posY + mc.thePlayer.getEyeHeight());
        double z = aimVector.zCoord - mc.thePlayer.posZ;
        double theta = MathHelper.sqrt_double(x * x + z * z);
        float yaw = (float) (Math.atan2(z, x) * 180 / Math.PI) - 90;
        float pitch = (float) (-(Math.atan2(y, theta) * 180 / Math.PI));
        return new float[]{(mc.thePlayer.rotationYaw + MathHelper.wrapAngleTo180_float(yaw - mc.thePlayer.rotationYaw)) % 360, (mc.thePlayer.rotationPitch + MathHelper.wrapAngleTo180_float(pitch - mc.thePlayer.rotationPitch)) % 360.0f};
    }

    public static Vec3 closestPointToBox(Vec3 start, net.minecraft.util.AxisAlignedBB box) {
        double x = Math.min(Math.max(start.xCoord, box.minX), box.maxX);
        double y = Math.min(Math.max(start.yCoord, box.minY), box.maxY);
        double z = Math.min(Math.max(start.zCoord, box.minZ), box.maxZ);
        return new Vec3(x, y, z);
    }

    public static Vec3 getBestAimPoint(net.minecraft.util.AxisAlignedBB box) {
        assert mc.thePlayer != null;

        Vec3 start = mc.thePlayer.getPositionEyes(1.0F);
        if (box.minX < start.xCoord && start.xCoord < box.maxX &&
                box.minZ < start.zCoord && start.zCoord < box.maxZ) {
            return new Vec3(
                    box.minX + (box.maxX - box.minX) / 2.0,
                    Math.min(Math.max(start.yCoord, box.minY), box.maxY),
                    box.minZ + (box.maxZ - box.minZ) / 2.0
            );
        }
        return closestPointToBox(start, box);
    }

    public static Vec3 getLegs(AxisAlignedBB boundingBox) {
        // Calculate the approximate leg position (bottom center of the bounding box)
        double legY = boundingBox.minY + (boundingBox.maxY - boundingBox.minY) / 4.0; // Assuming legs are in the lower 1/4th of the bounding box
        double centerX = (boundingBox.minX + boundingBox.maxX) / 2.0;
        double centerZ = (boundingBox.minZ + boundingBox.maxZ) / 2.0;
        return new Vec3(centerX, legY, centerZ);
    }

    public static Vec3 getCenter(AxisAlignedBB boundingBox) {
        double centerX = (boundingBox.minX + boundingBox.maxX) / 2.0;
        double centerY = (boundingBox.minY + boundingBox.maxY) / 2.0;
        double centerZ = (boundingBox.minZ + boundingBox.maxZ) / 2.0;
        return new Vec3(centerX, centerY, centerZ);
    }

}

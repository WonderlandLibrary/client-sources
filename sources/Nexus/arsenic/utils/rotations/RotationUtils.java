package arsenic.utils.rotations;

import arsenic.utils.java.JavaUtils;
import arsenic.utils.java.UtilityClass;
import arsenic.utils.minecraft.MathUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.util.*;

import javax.vecmath.Vector2f;
import java.security.SecureRandom;

import static arsenic.utils.java.JavaUtils.getRandom;

public class RotationUtils extends UtilityClass {

    private static final Minecraft mc = Minecraft.getMinecraft();

    public static float[] getRotationsToPosition(double x, double y, double z) {
        double deltaX = x - mc.thePlayer.posX;
        double deltaY = y - mc.thePlayer.posY - mc.thePlayer.getEyeHeight();
        double deltaZ = z - mc.thePlayer.posZ;

        double horizontalDistance = Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);

        float yaw = (float) Math.toDegrees(-Math.atan2(deltaX, deltaZ));
        float pitch = (float) Math.toDegrees(-Math.atan2(deltaY, horizontalDistance));

        return new float[]{yaw, pitch};
    }

    public static float[] getRotationsToEntity(EntityLivingBase entity, boolean usePartialTicks) {
        float partialTicks = mc.timer.renderPartialTicks;

        double entityX = usePartialTicks ? entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks : entity.posX;
        double entityY = usePartialTicks ? entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks : entity.posY;
        double entityZ = usePartialTicks ? entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks : entity.posZ;

        double yDiff = mc.thePlayer.posY - entityY;

        double finalEntityY = yDiff >= 0 ? entityY + entity.getEyeHeight() : -yDiff < mc.thePlayer.getEyeHeight() ? mc.thePlayer.posY + mc.thePlayer.getEyeHeight() : entityY;

        return getRotationsToPosition(entityX, finalEntityY, entityZ);
    }

    public static float[] getTargetRotations(Entity q) {
        if (q == null) {
            return null;
        }
        double diffX = q.posX - mc.thePlayer.posX;
        double diffY;
        if (q instanceof EntityLivingBase) {
            EntityLivingBase en = (EntityLivingBase) q;
            diffY = (en.posY + ((double) en.getEyeHeight() * 0.9D))
                    - (mc.thePlayer.posY + (double) mc.thePlayer.getEyeHeight());
        } else {
            diffY = (((q.getEntityBoundingBox().minY + q.getEntityBoundingBox().maxY) / 2.0D))
                    - (mc.thePlayer.posY + (double) mc.thePlayer.getEyeHeight());
        }

        double diffZ = q.posZ - mc.thePlayer.posZ;
        double dist = MathHelper.sqrt_double((diffX * diffX) + (diffZ * diffZ));
        float yaw = (float) ((Math.atan2(diffZ, diffX) * 180.0D) / 3.141592653589793D) - 90.0F;
        float pitch = (float) (-((Math.atan2(diffY, dist) * 180.0D) / 3.141592653589793D));
        float correctYaw = mc.thePlayer.rotationYaw + MathHelper.wrapAngleTo180_float(yaw - mc.thePlayer.rotationYaw);
        float correctPitch = mc.thePlayer.rotationPitch + MathHelper.wrapAngleTo180_float(pitch - mc.thePlayer.rotationPitch);
        correctPitch = MathHelper.clamp_float(correctPitch, -90, 90);
        correctPitch += getRandom(-1, 1);
        return new float[]{correctYaw, correctPitch};
        // tf is happening here
    }

    public static float getYawDifference(float yaw1, float yaw2) {
        float yawDiff = MathHelper.wrapAngleTo180_float(yaw1) - MathHelper.wrapAngleTo180_float(yaw2);
        if (Math.abs(yawDiff) > 180) {
            yawDiff = yawDiff + 360;
        }
        return MathHelper.wrapAngleTo180_float(yawDiff);
    }

    public static double getDistanceToBlockPos(BlockPos blockPos) {
        return mc.thePlayer.getPositionVector().distanceTo(new Vec3(blockPos));
    }

    //haven't tested if this works
    public static float[] getPlayerRotationsToBlock(BlockPos pos, EnumFacing face) {
        return getPlayerRotationsToVec(getVec3FromBlockPosAndEnumFacing(pos, face));
    }

    public static float[] getPlayerRotationsToVec(Vec3 to) {
        return getRotations(mc.thePlayer.getPositionVector().addVector(0, 1.5, 0), to);
    }
    public static float[] getRotations(final double posX, final double posY, final double posZ) {
        final EntityPlayerSP player = mc.thePlayer;
        final double x = posX - player.posX;
        final double y = posY - (player.posY + (double) player.getEyeHeight());
        final double z = posZ - player.posZ;
        final double dist = MathHelper.sqrt_double(x * x + z * z);
        final float yaw = (float) (Math.atan2(z, x) * 180.0D / Math.PI) - 90.0F;
        final float pitch = (float) (-(Math.atan2(y, dist) * 180.0D / Math.PI));
        return new float[]{yaw, pitch};
    }
    public static float[] getRotations(Vec3 from, Vec3 to) {
        final float diffY = (float) (from.yCoord - to.yCoord);
        final float diffX = (float) (from.xCoord - to.xCoord);
        final float diffZ = (float) (from.zCoord - to.zCoord);
        final float dist = MathHelper.sqrt_double((diffX * diffX) + (diffZ * diffZ));
        float pitch = (float) Math.toDegrees(Math.atan2(diffY, dist));
        pitch += JavaUtils.getRandom(-1, 1);
        final float yaw = (float) MathHelper.wrapAngleTo180_double(Math.toDegrees(Math.atan2(diffZ, diffX)) + 90f);
        return new float[]{yaw, pitch};
    }
    public static float[] getDirectionToBlock(final double x, final double y, final double z, final EnumFacing enumfacing) {
        final EntityEgg var4 = new EntityEgg(mc.theWorld);
        var4.posX = x + 0.5D;
        var4.posY = y + 0.5D;
        var4.posZ = z + 0.5D;
        var4.posX += (double) enumfacing.getDirectionVec().getX() * 0.5D;
        var4.posY += (double) enumfacing.getDirectionVec().getY() * 0.5D;
        var4.posZ += (double) enumfacing.getDirectionVec().getZ() * 0.5D;
        return getRotations(var4.posX, var4.posY, var4.posZ);
    }
    public static Vec3 getVec3FromBlockPosAndEnumFacing(BlockPos blockPos, EnumFacing face) {
        final Vec3 blockVec = new Vec3(blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5);
        return blockVec.addVector(face.getFrontOffsetX() / 2d, face.getFrontOffsetY() / 2d, face.getFrontOffsetZ() / 2d);
    }

    private static float nextSecureFloat(final double origin, final double bound) {
        if (origin == bound) {
            return (float) origin;
        }
        final SecureRandom secureRandom = new SecureRandom();
        final float difference = (float) (bound - origin);
        return (float) (origin + secureRandom.nextFloat() * difference);
    }

    public static float[] randomize(float yaw, float pitch) {
        final float random1 = nextSecureFloat(-MathUtils.getRandomInRange(2.3F, 4F), MathUtils.getRandomInRange(2.3F, 4F));
        final float random2 = nextSecureFloat(-MathUtils.getRandomInRange(2.3F, 4F), MathUtils.getRandomInRange(2.3F, 4F));
        final float random3 = nextSecureFloat(-MathUtils.getRandomInRange(2.3F, 4F), MathUtils.getRandomInRange(2.3F, 4F));
        final float random4 = nextSecureFloat(-MathUtils.getRandomInRange(2.3F, 4F), MathUtils.getRandomInRange(2.3F, 4F));
        yaw += nextSecureFloat(Math.min(random1, random2), Math.max(random1, random2));
        pitch += nextSecureFloat(Math.min(random3, random4), Math.max(random3, random4));
        return new float[]{yaw, pitch};
    }
}
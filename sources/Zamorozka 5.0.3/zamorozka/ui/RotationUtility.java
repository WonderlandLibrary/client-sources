package zamorozka.ui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import zamorozka.event.EventTarget;
import zamorozka.event.events.EventReceivePacket;
import zamorozka.event.events.EventTick;

public class RotationUtility implements MCUtil {
    public static boolean lookChanged;
    public static float targetYaw;
    public static float targetPitch;
    private static int keepLength;
    public static boolean keepRotation;
    public static float[] lastLook;
    private static double x;
    private static double y;
    private static double z;
    
    public static void faceBlockPacket(final BlockPos blockPos) {
        if (blockPos == null) {
            return;
        }
        final double diffX = blockPos.getX() + 0.5 - mc.player.posX;
        final double diffY = blockPos.getY() + 0.5 - (mc.player.getEntityBoundingBox().minY + mc.player.getEyeHeight());
        final double diffZ = blockPos.getZ() + 0.5 - mc.player.posZ;
        final double sqrt = Math.sqrt(diffX * diffX + diffZ * diffZ);
        final float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0 / 3.141592653589793) - 90.0f;
        final float pitch = (float)(-(Math.atan2(diffY, sqrt) * 180.0 / 3.141592653589793));
        setTargetRotation(mc.player.rotationYaw + MathHelper.wrapAngleTo180_float(yaw - mc.player.rotationYaw), mc.player.rotationPitch + MathHelper.wrapAngleTo180_float(pitch - mc.player.rotationPitch));
    }
    
    public static void faceBow(final Entity target, final boolean silent, final boolean predict, final float predictSize) {
        final EntityPlayerSP player = mc.player;
        final double posX = target.posX + (predict ? ((target.posX - target.prevPosX) * predictSize) : 0.0) - (player.posX + (predict ? (player.posX - player.prevPosX) : 0.0));
        final double posY = target.getEntityBoundingBox().minY + (predict ? ((target.getEntityBoundingBox().minY - target.prevPosY) * predictSize) : 0.0) + target.getEyeHeight() - 0.15 - (player.getEntityBoundingBox().minY + (predict ? (player.posY - player.prevPosY) : 0.0)) - player.getEyeHeight();
        final double posZ = target.posZ + (predict ? ((target.posZ - target.prevPosZ) * predictSize) : 0.0) - (player.posZ + (predict ? (player.posZ - player.prevPosZ) : 0.0));
        final double sqrt = Math.sqrt(posX * posX + posZ * posZ);
        float velocity = player.getItemInUseCount() / 20.0f;
        velocity = (velocity * velocity + velocity * 2.0f) / 3.0f;
        if (velocity > 1.0f) {
            velocity = 1.0f;
        }
        float yaw = (float)(Math.atan2(posZ, posX) * 180.0 / 3.141592653589793) - 90.0f;
        float pitch = (float)(-Math.toDegrees(Math.atan((velocity * velocity - Math.sqrt(velocity * velocity * velocity * velocity - 0.006000000052154064 * (0.006000000052154064 * (sqrt * sqrt) + 2.0 * posY * (velocity * velocity)))) / (0.006000000052154064 * sqrt))));
        if (velocity < 0.1f) {
            final float[] rotations = getNeededRotations(getCenter(target.getEntityBoundingBox()), true);
            float sens = getSensitivityMultiplier();
            yaw = (Math.round(rotations[0] / sens) * sens);
            pitch = (Math.round(rotations[1] / sens) * sens);
        }
        if (silent) {
            setTargetRotation(yaw, pitch);
        }
        else {
            final float[] rotations = limitAngleChange(new float[] { player.rotationYaw, player.rotationPitch }, new float[] { yaw, pitch }, (float)(10 + RandomUtils.getRandom().nextInt(6)));
            if (rotations == null) {
                return;
            }
            float sens = getSensitivityMultiplier();
            player.rotationYaw = (Math.round(rotations[0] / sens) * sens);
            player.rotationPitch = (Math.round(rotations[1] / sens) * sens);
        }
    }
    
    private static float getSensitivityMultiplier() {
        float f = mc.gameSettings.mouseSensitivity * 0.6f + 0.2f;
        return (f * f * f * 8.0F) * 0.15F;
    }
    
    public static float[] getTargetRotation(final Entity entity) {
        if (entity == null || mc.player == null) {
            return null;
        }
        return getNeededRotations(getRandomCenter(entity.getEntityBoundingBox(), false), true);
    }
    
    public static float[] getNeededRotations(final Vec3d vec, final boolean predict) {
        final Vec3d eyesPos = getEyesPos();
        if (predict) {
            eyesPos.addVector(mc.player.motionX, mc.player.motionY, mc.player.motionZ);
        }
        final double diffX = vec.xCoord - eyesPos.xCoord;
        final double diffY = vec.yCoord - eyesPos.yCoord;
        final double diffZ = vec.zCoord - eyesPos.zCoord;
        final double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        final float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
        final float pitch = (float)(-Math.toDegrees(Math.atan2(diffY, diffXZ)));
        return new float[] { MathHelper.wrapAngleTo180_float(yaw), MathHelper.wrapAngleTo180_float(pitch) };
    }
    
    public static Vec3d getCenter(final AxisAlignedBB bb) {
        return new Vec3d(bb.minX + (bb.maxX - bb.minX) * 0.5, bb.minY + (bb.maxY - bb.minY) * 0.5, bb.minZ + (bb.maxZ - bb.minZ) * 0.5);
    }
    
    public static Vec3d getRandomCenter(final AxisAlignedBB bb, final boolean outborder) {
        if (outborder) {
            return new Vec3d(bb.minX + (bb.maxX - bb.minX) * (x * 0.3 + 1.0), bb.minY + (bb.maxY - bb.minY) * (y * 0.3 + 1.0), bb.minZ + (bb.maxZ - bb.minZ) * (z * 0.3 + 1.0));
        }
        return new Vec3d(bb.minX + (bb.maxX - bb.minX) * x * 0.8, bb.minY + (bb.maxY - bb.minY) * y * 0.8, bb.minZ + (bb.maxZ - bb.minZ) * z * 0.8);
    }
    
    public static double getRotationDifference(final Entity entity) {
        final float[] rotations = getTargetRotation(entity);
        if (rotations == null) {
            return 0.0;
        }
        return getRotationDifference(rotations[0], rotations[1]);
    }
    
    public static double getRotationDifference(final float yaw, final float pitch) {
        return Math.sqrt(Math.pow(Math.abs(angleDifference(lastLook[0] % 360.0f, yaw)), 2.0) + Math.pow(Math.abs(angleDifference(lastLook[1], pitch)), 2.0));
    }
    
    public static float[] limitAngleChange(final float[] current, final float[] target, final float turnSpeed) {
        final double yawDifference = angleDifference(target[0], current[0]);
        final double pitchDifference = angleDifference(target[1], current[1]);
        final int n = 0;
        current[n] += (float)((yawDifference > turnSpeed) ? turnSpeed : ((yawDifference < -turnSpeed) ? (-turnSpeed) : yawDifference));
        final int n2 = 1;
        current[n2] += (float)((pitchDifference > turnSpeed) ? turnSpeed : ((pitchDifference < -turnSpeed) ? (-turnSpeed) : pitchDifference));
        return current;
    }
    
    private static double angleDifference(final double a, final double b) {
        return ((a - b) % 360.0 + 540.0) % 360.0 - 180.0;
    }
    
    public static Vec3d getEyesPos() {
        return new Vec3d(mc.player.posX, mc.player.getEntityBoundingBox().minY + mc.player.getEyeHeight(), mc.player.posZ);
    }
    
    public static boolean isFaced(final Entity targetEntity, final double blockReachDistance) {
        return RayCastUtil.raycastEntities(blockReachDistance).contains(targetEntity);
    }
    
    @EventTarget
    public void onTick(EventTick event) {
        if (lookChanged) {
            ++keepLength;
            if (keepLength > 15) {
                reset();
            }
        }
        if (RandomUtils.getRandom().nextGaussian() * 100.0 > 80.0) {
            x = Math.random();
        }
        if (RandomUtils.getRandom().nextGaussian() * 100.0 > 80.0) {
            y = Math.random();
        }
        if (RandomUtils.getRandom().nextGaussian() * 100.0 > 80.0) {
            z = Math.random();
        }
    }
    
    public static Vec3d getVectorForRotation(final float pitch, final float yaw) {
        final float f = MathHelper.cos(-yaw * 0.017453292f - 3.1415927f);
        final float f2 = MathHelper.sin(-yaw * 0.017453292f - 3.1415927f);
        final float f3 = -MathHelper.cos(-pitch * 0.017453292f);
        final float f4 = MathHelper.sin(-pitch * 0.017453292f);
        return new Vec3d((double)(f2 * f3), (double)f4, (double)(f * f3));
    }
    
    @EventTarget
    public void onPacket(EventReceivePacket event) {
        Packet packet = event.getPacket();
        if (packet instanceof CPacketPlayer) {
            final CPacketPlayer packetPlayer = (CPacketPlayer)packet;
            if (lookChanged && !keepRotation && (targetYaw != lastLook[0] || targetPitch != lastLook[1])) {
                packetPlayer.yaw = targetYaw;
                packetPlayer.pitch = targetPitch;
                packetPlayer.rotating = true;
            }
            if (packetPlayer.rotating) {
                lastLook = new float[] { packetPlayer.yaw, packetPlayer.pitch };
            }
        }
    }
    
    public static void setTargetRotation(final float yaw, final float pitch) {
        if (Double.isNaN(yaw) || Double.isNaN(pitch)) {
            return;
        }
        targetYaw = yaw;
        targetPitch = pitch;
        lookChanged = true;
        keepLength = 0;
    }
    
    public static void reset() {
        lookChanged = false;
        keepLength = 0;
        targetYaw = 0.0f;
        targetPitch = 0.0f;
    }
    
    static {
        keepRotation = false;
        lastLook = new float[] { 0.0f, 0.0f };
        x = Math.random();
        y = Math.random();
        z = Math.random();
    }
}
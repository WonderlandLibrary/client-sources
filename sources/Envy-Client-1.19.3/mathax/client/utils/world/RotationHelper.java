package mathax.client.utils.world;

import mathax.client.utils.misc.Formatter;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;

import static mathax.client.MatHax.mc;

public class RotationHelper {


    public static Vec3d getEyePos(Entity entity) {
        if (entity.equals(mc.player)) return getSelfEye();
        return new Vec3d(entity.getX(), entity.getEyeY(), entity.getZ());
    }

    public static Vec3d getSelfEye() {
        return new Vec3d(mc.player.getX(), mc.player.getY() + mc.player.getEyeHeight(mc.player.getPose()), mc.player.getZ());
    }

    public static Direction getDirection(BlockPos pos) {
        Vec3d eyesPos = new Vec3d(mc.player.getX(), mc.player.getY() + mc.player.getEyeHeight(mc.player.getPose()), mc.player.getZ());
        for (Direction direction : Direction.values()) {
            RaycastContext raycastContext = generateRaycast(eyesPos, pos.offset(direction), direction);
            BlockHitResult result = mc.world.raycast(raycastContext);
            if (result != null && result.getType() == HitResult.Type.BLOCK && result.getBlockPos().equals(pos)) return direction;
        }
        if ((double) pos.getY() > eyesPos.y) return Direction.DOWN; // The player can never see the top of a block if they are under it
        return Direction.UP;
    }

    public static RaycastContext generateRaycast(Vec3d start, BlockPos end, Direction direction) {
        return generateRaycast(start, getFixedEnd(end, direction));
    }

    public static RaycastContext generateRaycast(Vec3d start, Vec3d end) {
        return new RaycastContext(start, end, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, mc.player);
    }

    public static Vec3d getFixedEnd(BlockPos pos, Direction direction) {
        return new Vec3d(pos.getX() + 0.5 + direction.getVector().getX() * 0.5, pos.getY() + 0.5 + direction.getVector().getY() * 0.5, pos.getZ() + 0.5 + direction.getVector().getZ() * 0.5);
    }






    public static float getGCD() {
        float f = (float) (0.2112676 * 0.6D + 0.2D);
        return (f * f * f * 8.0f) * 0.15f;
    }

    public static float getFixedRotation(float value) {
        float gcd = MathHelper.wrapDegrees(getGCD());
        return Math.round((value - (value % gcd)) / getGCD()) * getGCD();
    }

    public static float[] lookAtEntity(Entity targetEntity) {
        double diffX = targetEntity.getX() - mc.player.getX();
        double diffZ = targetEntity.getZ() - mc.player.getZ();

        double diffY;
        if (targetEntity instanceof LivingEntity livingEntity) diffY = livingEntity.getEyeY() - mc.player.getEyeY() - 0.5;
        else diffY = (targetEntity.getBoundingBox().minY + targetEntity.getBoundingBox().maxY) / 2.0D - mc.player.getY() + mc.player.getEyeY() - 0.5;

        double dist = MathHelper.sqrt((float) (diffX * diffX + diffZ * diffZ));
        float yaw = (float) (((Math.atan2(diffZ, diffX) * 180.0 / Math.PI) - 90.0f)) + Formatter.random(-2, 2);
        float pitch = (float) (-(Math.atan2(diffY, dist) * 180.0 / Math.PI)) + Formatter.random(-2, 2);
        yaw = mc.player.getYaw() + getFixedRotation(MathHelper.wrapDegrees(yaw - mc.player.getYaw()));
        pitch = mc.player.getPitch() + getFixedRotation(MathHelper.wrapDegrees(pitch - mc.player.getPitch()));
        return new float[] { yaw, pitch };
    }

}

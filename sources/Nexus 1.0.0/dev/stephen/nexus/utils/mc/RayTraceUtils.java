package dev.stephen.nexus.utils.mc;

import dev.stephen.nexus.utils.Utils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.*;
import net.minecraft.world.RaycastContext;

public class RayTraceUtils implements Utils {

    public static boolean isLookingAtPlayer(PlayerEntity target, double reach, float yaw, float pitch) {
        Box box = getScaledEntityHitBox(target, 1, 1);
        Vec3d look = getVectorForRotation(yaw, pitch);
        Vec3d start = mc.player.getPos().add(new Vec3d(0, mc.player.getEyeHeight(mc.player.getPose()), 0));
        Vec3d current;
        double multi = 0;
        while (true) {
            current = start.add(new Vec3d(look.x * multi, look.y * multi, look.z * multi));
            double d = start.distanceTo(current);
            if (d > reach) return false;
            if (box.contains(current)) return true;
            multi += 0.1d;
        }
    }

    public static boolean isLookingAtBlock(Direction facing, BlockPos position, boolean strict, float reach, float yaw, float pitch) {
        BlockHitResult blockHitResult = rayTrace(reach, yaw, pitch);
        if (blockHitResult == null) {
            return false;
        }

        if (blockHitResult.getBlockPos().getX() == position.getX() && blockHitResult.getBlockPos().getY() == position.getY() && blockHitResult.getBlockPos().getZ() == position.getZ()) {
            if (strict) {
                return blockHitResult.getSide() == facing;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public static BlockHitResult rayTrace(float reach, float yaw, float pitch) {
        final Vec3d vec3 = mc.cameraEntity.getCameraPosVec(1.0f);
        final Vec3d vec4 = getVectorForRotation(yaw, pitch);
        final Vec3d vec5 = vec3.add(vec4.x * reach, vec4.y * reach, vec4.z * reach);

        return mc.world.raycast(new RaycastContext(vec3, vec5, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, mc.cameraEntity));
    }

    public static EntityHitResult rayTrace(double range, float yaw, float pitch) {
        Entity entity = mc.getCameraEntity();
        if (entity == null) {
            return null;
        }

        Vec3d cameraVec = entity.getEyePos();
        Vec3d rotationVec = getVectorForRotation(yaw, pitch);

        Vec3d vec3d3 = cameraVec.add(rotationVec.x * range, rotationVec.y * range, rotationVec.z * range);
        Box box = entity.getBoundingBox().stretch(rotationVec.multiply(range)).expand(1.0, 1.0, 1.0);

        return ProjectileUtil.raycast(
                entity,
                cameraVec,
                vec3d3,
                box,
                (hitEntity) -> !hitEntity.isSpectator() && hitEntity.canHit(),
                range * range
        );
    }

    public static Vec3d getVectorForRotation(float yaw, float pitch) {
        final float f = MathHelper.cos(-yaw * 0.017453292f - 3.1415927f);
        final float f2 = MathHelper.sin(-yaw * 0.017453292f - 3.1415927f);
        final float f3 = -MathHelper.cos(-pitch * 0.017453292f);
        final float f4 = MathHelper.sin(-pitch * 0.017453292f);
        return new Vec3d((f2 * f3), f4, (f * f3));
    }

    public static Box getScaledEntityHitBox(Entity ent, double horizontalMulti, double verticalMulti) {
        Vec3d hb = getEntityHitBoxSize(ent);
        double xPart = ((1 - horizontalMulti) / 2) * hb.x;
        double yPart = ((1 - verticalMulti) / 2) * hb.y;
        double zPart = ((1 - horizontalMulti) / 2) * hb.z;

        Box box = ent.getBoundingBox();
        Vec3d s = new Vec3d(box.minX, box.minY, box.minZ);
        return new Box(s.x + xPart, s.y + yPart, s.z + zPart, s.x + hb.x - xPart, s.y + hb.y + yPart, s.z + hb.z + zPart);
    }

    private static Vec3d getEntityHitBoxSize(Entity ent) {
        Box box = ent.getBoundingBox();
        double hbX = box.maxX - box.minX;
        double hbY = box.maxY - box.minY;
        double hbZ = box.maxZ - box.minZ;
        return new Vec3d(hbX, hbY, hbZ);
    }
}

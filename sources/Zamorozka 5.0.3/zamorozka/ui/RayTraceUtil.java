package zamorozka.ui;

import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;

import java.util.List;

public class RayTraceUtil implements MCUtil {
    public static Entity entity;
    public static RayTraceResult result;

    public static boolean rayTrace(Vec3d origin, Vec3d position) {
        Vec3d difference = position.subtract(origin);
        int steps = 10;
        double x = difference.xCoord / (double) steps;
        double y = difference.yCoord / (double) steps;
        double z = difference.zCoord / (double) steps;
        Vec3d point = origin;

        for (int i = 0; i < steps; ++i) {
            BlockPos blockPosition = new BlockPos(point = point.addVector(x, y, z));
            IBlockState blockState = mc.world.getBlockState(blockPosition);

            if (blockState.getBlock() instanceof BlockLiquid || blockState.getBlock() instanceof BlockAir) {
                continue;
            }

            AxisAlignedBB boundingBox = blockState.getBlock().getCollisionBoundingBox(blockState, mc.world, blockPosition);
            if (boundingBox == null) {
                boundingBox = new AxisAlignedBB(0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
            }
            if (!boundingBox.offset(blockPosition.getX(), blockPosition.getY(), blockPosition.getZ()).isVecInside(point)) {
                continue;
            }
            return true;
        }
        return false;
    }
    public static RayTraceResult rayTrace(final double blockReachDistance, final float yaw, final float pitch) {
        final Vec3d vec3 = mc.player.getPositionEyes(1.0f);
        final Vec3d vec4 = getLook(yaw, pitch);
        final Vec3d vec5 = vec3.addVector(vec4.xCoord * blockReachDistance, vec4.yCoord * blockReachDistance, vec4.zCoord * blockReachDistance);
        return mc.player.world.rayTraceBlocks(vec3, vec5, false, false, true);
    }
    public static RayTraceResult rayTrace(final float n, final double n2, final double n3, final double n4, final float n5, final float n6) {
        final Minecraft minecraft = Minecraft.getMinecraft();
        final Entity func_175606_aa = minecraft.getRenderViewEntity();
        if (func_175606_aa != null && minecraft.world != null) {
            RayTraceUtil.entity = null;
            final double n7 = minecraft.playerController.getBlockReachDistance();
            final Vec3d vec3 = new Vec3d(n2, n3 + minecraft.player.getEyeHeight(), n4);
            final Vec3d vectorForRotation = minecraft.player.getVectorForRotation(n5, n6);
            RayTraceUtil.result = var24(minecraft.playerController.getBlockReachDistance(), minecraft.timer.renderPartialTicks, vec3, vectorForRotation);
            double var2 = n7;
            final Vec3d vec4 = vec3;
            if (RayTraceUtil.result != null) {
                var2 = RayTraceUtil.result.hitVec.distanceTo(vec4);
            }
            final Vec3d vec5 = vectorForRotation;
            final Vec3d addVector = vec4.addVector(vec5.xCoord * n7, vec5.yCoord * n7, vec5.zCoord * n7);
            RayTraceUtil.entity = null;
            Vec3d vec6 = null;
            final float n8 = 1.0f;
            final List<Entity> var3 = minecraft.world.getEntitiesWithinAABBExcludingEntity(func_175606_aa, func_175606_aa.getEntityBoundingBox().expand(vec5.xCoord * n7, vec5.yCoord * n7, vec5.zCoord * n7).sub(n8, n8, n8));
            double n9 = var2;
            for (int i = 0; i < var3.size(); ++i) {
                final Entity entity = var3.get(i);
                final float collisionBorderSize = entity.getCollisionBorderSize();
                final AxisAlignedBB sub = entity.getEntityBoundingBox().sub(collisionBorderSize, collisionBorderSize, collisionBorderSize);
                final RayTraceResult calculateIntercept = sub.calculateIntercept(vec4, addVector);
                if (sub.isVecInside(vec4)) {
                    if (n9 >= 0.0) {
                        RayTraceUtil.entity = entity;
                        vec6 = ((calculateIntercept == null) ? vec4 : calculateIntercept.hitVec);
                        n9 = 0.0;
                    }
                } else if (calculateIntercept != null) {
                    final double var1 = vec4.distanceTo(calculateIntercept.hitVec);
                    if (var1 < n9 || n9 == 0.0) {
                        if (entity == func_175606_aa.ridingEntity) {
                            if (n9 == 0.0) {
                                RayTraceUtil.entity = entity;
                                vec6 = calculateIntercept.hitVec;
                            }
                        } else {
                            RayTraceUtil.entity = entity;
                            vec6 = calculateIntercept.hitVec;
                            n9 = var1;
                        }
                    }
                }
            }
            if (RayTraceUtil.entity != null && (n9 < var2 || RayTraceUtil.result == null)) {
                RayTraceUtil.result = new RayTraceResult(RayTraceUtil.entity, vec6);
                if (RayTraceUtil.entity instanceof EntityLivingBase || RayTraceUtil.entity instanceof EntityItemFrame) {
                    RayTraceUtil.entity = RayTraceUtil.entity;
                }
            }
        }
        return RayTraceUtil.result;
    }

    private static RayTraceResult var24(double n, final float n2, final Vec3d vec3, final Vec3d vec4) {
        Vec3d addVector = null;
        for (double n3 = 0.0; n3 < 1.0; n3 += 0.05) {
            n = 0.0 + (n - 0.0) * n3;
            addVector = vec3.addVector(vec4.xCoord * n, vec4.yCoord * n, vec4.zCoord * n);
        }
        return Minecraft.getMinecraft().player.world.rayTraceBlocks(vec3, addVector, false, false, true);
    }
    public static Vec3d getLook(final float yaw, final float pitch) {
        return mc.player.getVectorForRotation(pitch, yaw);
    }
}
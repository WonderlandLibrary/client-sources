package mathax.client.utils.player;

import com.google.common.collect.ImmutableList;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static mathax.client.MatHax.mc;

public class CanTeleport {


    public static double searchGoodYandTeleport(Vec3d from, Vec3d to) {
        if (Checkifcantteleport(from, to)) {
            double fromy = from.y;
            double ytotest = 0;
            boolean validTeleportFound = false;
            int searchOffset = 0;
            while (!validTeleportFound) {
                ytotest = (fromy + searchOffset);
                Vec3d fromWithOffset = new Vec3d(from.x, ytotest, from.z);
                Vec3d toWithOffset = new Vec3d(to.x, ytotest, to.z);
                validTeleportFound = !Checkifcantteleport(fromWithOffset, toWithOffset);
                if (!validTeleportFound) {
                    searchOffset = (searchOffset <= 0) ? 1 - searchOffset : -searchOffset;
                }
            }
            return ytotest;
        } else {
            return from.y;
        }
    }

    public static boolean Checkifcantteleport(Vec3d from, Vec3d to) {
        Box box = new Box(from.x - mc.player.getWidth() / 2, from.y, from.z - mc.player.getWidth() / 2, from.x + mc.player.getWidth() / 2, from.y + mc.player.getHeight(), from.z + mc.player.getWidth() / 2);
        double d6 = to.x - from.x;
        double d7 = to.y - from.y;
        double d8 = to.z - from.z;
        Vec3d test = adjustMovementForCollisions(box, new Vec3d(d6, d7, d8));
        Vec3d wentto = new Vec3d(from.x + test.x, from.y + test.y, from.z + test.z);
        d6 = to.x - wentto.x;
        d7 = to.y - wentto.y;
        if (d7 > -0.5D || d7 < 0.5D) {
            d7 = 0.0D;
        }
        d8 = to.z - wentto.z;
        double d10 = d6 * d6 + d7 * d7 + d8 * d8;
        if (d10 > 0.0625 && !mc.player.isSleeping() && !mc.player.isCreative()) {
            return true;
        }
        return false;
    }

    private static Vec3d adjustMovementForCollisions(Box box, Vec3d movement) {
        List<VoxelShape> list = mc.player.getWorld().getEntityCollisions(mc.player, box.stretch(movement));
        Vec3d vec3d = movement.lengthSquared() == 0.0 ? movement : adjustMovementForCollisions(mc.player, movement, box, mc.player.getWorld(), list);
        return vec3d;
    }


    private static Vec3d adjustMovementForCollisions(@Nullable Entity entity, Vec3d movement, Box entityBoundingBox, World world, List<VoxelShape> collisions) {
        ImmutableList.Builder<VoxelShape> builder = ImmutableList.builderWithExpectedSize(collisions.size() + 1);
        if (!collisions.isEmpty()) {
            builder.addAll(collisions);
        }
        builder.addAll(world.getBlockCollisions(entity, entityBoundingBox.stretch(movement)));
        return adjustMovementForCollisions(movement, entityBoundingBox, builder.build());
    }

    private static Vec3d adjustMovementForCollisions(Vec3d movement, Box entityBoundingBox, List<VoxelShape> collisions) {
        if (collisions.isEmpty()) {
            return movement;
        } else {
            double d = movement.x;
            double e = movement.y;
            double f = movement.z;
            if (e != 0.0) {
                e = VoxelShapes.calculateMaxOffset(Direction.Axis.Y, entityBoundingBox, collisions, e);
                if (e != 0.0) {
                    entityBoundingBox = entityBoundingBox.offset(0.0, e, 0.0);
                }
            }

            boolean bl = Math.abs(d) < Math.abs(f);
            if (bl && f != 0.0) {
                f = VoxelShapes.calculateMaxOffset(Direction.Axis.Z, entityBoundingBox, collisions, f);
                if (f != 0.0) {
                    entityBoundingBox = entityBoundingBox.offset(0.0, 0.0, f);
                }
            }

            if (d != 0.0) {
                d = VoxelShapes.calculateMaxOffset(Direction.Axis.X, entityBoundingBox, collisions, d);
                if (!bl && d != 0.0) {
                    entityBoundingBox = entityBoundingBox.offset(d, 0.0, 0.0);
                }
            }

            if (!bl && f != 0.0) {
                f = VoxelShapes.calculateMaxOffset(Direction.Axis.Z, entityBoundingBox, collisions, f);
            }

            return new Vec3d(d, e, f);
        }
    }
}

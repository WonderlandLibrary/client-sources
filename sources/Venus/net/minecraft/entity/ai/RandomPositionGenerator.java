/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai;

import java.util.Random;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;
import javax.annotation.Nullable;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.pathfinding.WalkNodeProcessor;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;

public class RandomPositionGenerator {
    @Nullable
    public static Vector3d findRandomTarget(CreatureEntity creatureEntity, int n, int n2) {
        return RandomPositionGenerator.func_226339_a_(creatureEntity, n, n2, 0, null, true, 1.5707963705062866, creatureEntity::getBlockPathWeight, false, 0, 0, true);
    }

    @Nullable
    public static Vector3d findGroundTarget(CreatureEntity creatureEntity, int n, int n2, int n3, @Nullable Vector3d vector3d, double d) {
        return RandomPositionGenerator.func_226339_a_(creatureEntity, n, n2, n3, vector3d, true, d, creatureEntity::getBlockPathWeight, true, 0, 0, false);
    }

    @Nullable
    public static Vector3d getLandPos(CreatureEntity creatureEntity, int n, int n2) {
        return RandomPositionGenerator.func_221024_a(creatureEntity, n, n2, creatureEntity::getBlockPathWeight);
    }

    @Nullable
    public static Vector3d func_221024_a(CreatureEntity creatureEntity, int n, int n2, ToDoubleFunction<BlockPos> toDoubleFunction) {
        return RandomPositionGenerator.func_226339_a_(creatureEntity, n, n2, 0, null, false, 0.0, toDoubleFunction, true, 0, 0, true);
    }

    @Nullable
    public static Vector3d findAirTarget(CreatureEntity creatureEntity, int n, int n2, Vector3d vector3d, float f, int n3, int n4) {
        return RandomPositionGenerator.func_226339_a_(creatureEntity, n, n2, 0, vector3d, false, f, creatureEntity::getBlockPathWeight, true, n3, n4, true);
    }

    @Nullable
    public static Vector3d func_234133_a_(CreatureEntity creatureEntity, int n, int n2, Vector3d vector3d) {
        Vector3d vector3d2 = vector3d.subtract(creatureEntity.getPosX(), creatureEntity.getPosY(), creatureEntity.getPosZ());
        return RandomPositionGenerator.func_226339_a_(creatureEntity, n, n2, 0, vector3d2, false, 1.5707963705062866, creatureEntity::getBlockPathWeight, true, 0, 0, true);
    }

    @Nullable
    public static Vector3d findRandomTargetBlockTowards(CreatureEntity creatureEntity, int n, int n2, Vector3d vector3d) {
        Vector3d vector3d2 = vector3d.subtract(creatureEntity.getPosX(), creatureEntity.getPosY(), creatureEntity.getPosZ());
        return RandomPositionGenerator.func_226339_a_(creatureEntity, n, n2, 0, vector3d2, true, 1.5707963705062866, creatureEntity::getBlockPathWeight, false, 0, 0, true);
    }

    @Nullable
    public static Vector3d findRandomTargetTowardsScaled(CreatureEntity creatureEntity, int n, int n2, Vector3d vector3d, double d) {
        Vector3d vector3d2 = vector3d.subtract(creatureEntity.getPosX(), creatureEntity.getPosY(), creatureEntity.getPosZ());
        return RandomPositionGenerator.func_226339_a_(creatureEntity, n, n2, 0, vector3d2, true, d, creatureEntity::getBlockPathWeight, false, 0, 0, true);
    }

    @Nullable
    public static Vector3d func_226344_b_(CreatureEntity creatureEntity, int n, int n2, int n3, Vector3d vector3d, double d) {
        Vector3d vector3d2 = vector3d.subtract(creatureEntity.getPosX(), creatureEntity.getPosY(), creatureEntity.getPosZ());
        return RandomPositionGenerator.func_226339_a_(creatureEntity, n, n2, n3, vector3d2, false, d, creatureEntity::getBlockPathWeight, true, 0, 0, false);
    }

    @Nullable
    public static Vector3d findRandomTargetBlockAwayFrom(CreatureEntity creatureEntity, int n, int n2, Vector3d vector3d) {
        Vector3d vector3d2 = creatureEntity.getPositionVec().subtract(vector3d);
        return RandomPositionGenerator.func_226339_a_(creatureEntity, n, n2, 0, vector3d2, true, 1.5707963705062866, creatureEntity::getBlockPathWeight, false, 0, 0, true);
    }

    @Nullable
    public static Vector3d func_223548_b(CreatureEntity creatureEntity, int n, int n2, Vector3d vector3d) {
        Vector3d vector3d2 = creatureEntity.getPositionVec().subtract(vector3d);
        return RandomPositionGenerator.func_226339_a_(creatureEntity, n, n2, 0, vector3d2, false, 1.5707963705062866, creatureEntity::getBlockPathWeight, true, 0, 0, true);
    }

    @Nullable
    private static Vector3d func_226339_a_(CreatureEntity creatureEntity, int n, int n2, int n3, @Nullable Vector3d vector3d, boolean bl, double d, ToDoubleFunction<BlockPos> toDoubleFunction, boolean bl2, int n4, int n5, boolean bl3) {
        PathNavigator pathNavigator = creatureEntity.getNavigator();
        Random random2 = creatureEntity.getRNG();
        boolean bl4 = creatureEntity.detachHome() ? creatureEntity.getHomePosition().withinDistance(creatureEntity.getPositionVec(), (double)(creatureEntity.getMaximumHomeDistance() + (float)n) + 1.0) : false;
        boolean bl5 = false;
        double d2 = Double.NEGATIVE_INFINITY;
        BlockPos blockPos = creatureEntity.getPosition();
        for (int i = 0; i < 10; ++i) {
            double d3;
            PathNodeType pathNodeType;
            BlockPos blockPos2;
            BlockPos blockPos3 = RandomPositionGenerator.func_226343_a_(random2, n, n2, n3, vector3d, d);
            if (blockPos3 == null) continue;
            int n6 = blockPos3.getX();
            int n7 = blockPos3.getY();
            int n8 = blockPos3.getZ();
            if (creatureEntity.detachHome() && n > 1) {
                blockPos2 = creatureEntity.getHomePosition();
                n6 = creatureEntity.getPosX() > (double)blockPos2.getX() ? (n6 -= random2.nextInt(n / 2)) : (n6 += random2.nextInt(n / 2));
                n8 = creatureEntity.getPosZ() > (double)blockPos2.getZ() ? (n8 -= random2.nextInt(n / 2)) : (n8 += random2.nextInt(n / 2));
            }
            if ((blockPos2 = new BlockPos((double)n6 + creatureEntity.getPosX(), (double)n7 + creatureEntity.getPosY(), (double)n8 + creatureEntity.getPosZ())).getY() < 0 || blockPos2.getY() > creatureEntity.world.getHeight() || bl4 && !creatureEntity.isWithinHomeDistanceFromPosition(blockPos2) || bl3 && !pathNavigator.canEntityStandOnPos(blockPos2)) continue;
            if (bl2) {
                blockPos2 = RandomPositionGenerator.func_226342_a_(blockPos2, random2.nextInt(n4 + 1) + n5, creatureEntity.world.getHeight(), arg_0 -> RandomPositionGenerator.lambda$func_226339_a_$0(creatureEntity, arg_0));
            }
            if (!bl && creatureEntity.world.getFluidState(blockPos2).isTagged(FluidTags.WATER) || creatureEntity.getPathPriority(pathNodeType = WalkNodeProcessor.func_237231_a_(creatureEntity.world, blockPos2.toMutable())) != 0.0f || !((d3 = toDoubleFunction.applyAsDouble(blockPos2)) > d2)) continue;
            d2 = d3;
            blockPos = blockPos2;
            bl5 = true;
        }
        return bl5 ? Vector3d.copyCenteredHorizontally(blockPos) : null;
    }

    @Nullable
    private static BlockPos func_226343_a_(Random random2, int n, int n2, int n3, @Nullable Vector3d vector3d, double d) {
        if (vector3d != null && !(d >= Math.PI)) {
            double d2 = MathHelper.atan2(vector3d.z, vector3d.x) - 1.5707963705062866;
            double d3 = d2 + (double)(2.0f * random2.nextFloat() - 1.0f) * d;
            double d4 = Math.sqrt(random2.nextDouble()) * (double)MathHelper.SQRT_2 * (double)n;
            double d5 = -d4 * Math.sin(d3);
            double d6 = d4 * Math.cos(d3);
            if (!(Math.abs(d5) > (double)n) && !(Math.abs(d6) > (double)n)) {
                int n4 = random2.nextInt(2 * n2 + 1) - n2 + n3;
                return new BlockPos(d5, (double)n4, d6);
            }
            return null;
        }
        int n5 = random2.nextInt(2 * n + 1) - n;
        int n6 = random2.nextInt(2 * n2 + 1) - n2 + n3;
        int n7 = random2.nextInt(2 * n + 1) - n;
        return new BlockPos(n5, n6, n7);
    }

    static BlockPos func_226342_a_(BlockPos blockPos, int n, int n2, Predicate<BlockPos> predicate) {
        BlockPos blockPos2;
        if (n < 0) {
            throw new IllegalArgumentException("aboveSolidAmount was " + n + ", expected >= 0");
        }
        if (!predicate.test(blockPos)) {
            return blockPos;
        }
        BlockPos blockPos3 = blockPos.up();
        while (blockPos3.getY() < n2 && predicate.test(blockPos3)) {
            blockPos3 = blockPos3.up();
        }
        BlockPos blockPos4 = blockPos3;
        while (blockPos4.getY() < n2 && blockPos4.getY() - blockPos3.getY() < n && !predicate.test(blockPos2 = blockPos4.up())) {
            blockPos4 = blockPos2;
        }
        return blockPos4;
    }

    private static boolean lambda$func_226339_a_$0(CreatureEntity creatureEntity, BlockPos blockPos) {
        return creatureEntity.world.getBlockState(blockPos).getMaterial().isSolid();
    }
}


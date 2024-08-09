/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.DoorBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.world.server.ServerWorld;

public class InteractWithDoorTask
extends Task<LivingEntity> {
    @Nullable
    private PathPoint field_242292_b;
    private int field_242293_c;

    public InteractWithDoorTask() {
        super(ImmutableMap.of(MemoryModuleType.PATH, MemoryModuleStatus.VALUE_PRESENT, MemoryModuleType.OPENED_DOORS, MemoryModuleStatus.REGISTERED));
    }

    @Override
    protected boolean shouldExecute(ServerWorld serverWorld, LivingEntity livingEntity) {
        Path path = livingEntity.getBrain().getMemory(MemoryModuleType.PATH).get();
        if (!path.func_242945_b() && !path.isFinished()) {
            if (!Objects.equals(this.field_242292_b, path.func_237225_h_())) {
                this.field_242293_c = 20;
                return false;
            }
            if (this.field_242293_c > 0) {
                --this.field_242293_c;
            }
            return this.field_242293_c == 0;
        }
        return true;
    }

    @Override
    protected void startExecuting(ServerWorld serverWorld, LivingEntity livingEntity, long l) {
        DoorBlock doorBlock;
        BlockState blockState;
        Object object;
        Path path = livingEntity.getBrain().getMemory(MemoryModuleType.PATH).get();
        this.field_242292_b = path.func_237225_h_();
        PathPoint pathPoint = path.func_242950_i();
        PathPoint pathPoint2 = path.func_237225_h_();
        BlockPos blockPos = pathPoint.func_224759_a();
        BlockState blockState2 = serverWorld.getBlockState(blockPos);
        if (blockState2.isIn(BlockTags.WOODEN_DOORS)) {
            object = (DoorBlock)blockState2.getBlock();
            if (!((DoorBlock)object).isOpen(blockState2)) {
                ((DoorBlock)object).openDoor(serverWorld, blockState2, blockPos, false);
            }
            this.func_242301_c(serverWorld, livingEntity, blockPos);
        }
        if ((blockState = serverWorld.getBlockState((BlockPos)(object = pathPoint2.func_224759_a()))).isIn(BlockTags.WOODEN_DOORS) && !(doorBlock = (DoorBlock)blockState.getBlock()).isOpen(blockState)) {
            doorBlock.openDoor(serverWorld, blockState, (BlockPos)object, false);
            this.func_242301_c(serverWorld, livingEntity, (BlockPos)object);
        }
        InteractWithDoorTask.func_242294_a(serverWorld, livingEntity, pathPoint, pathPoint2);
    }

    public static void func_242294_a(ServerWorld serverWorld, LivingEntity livingEntity, @Nullable PathPoint pathPoint, @Nullable PathPoint pathPoint2) {
        Brain<Set<GlobalPos>> brain = livingEntity.getBrain();
        if (brain.hasMemory(MemoryModuleType.OPENED_DOORS)) {
            Iterator<GlobalPos> iterator2 = brain.getMemory(MemoryModuleType.OPENED_DOORS).get().iterator();
            while (iterator2.hasNext()) {
                GlobalPos globalPos = iterator2.next();
                BlockPos blockPos = globalPos.getPos();
                if (pathPoint != null && pathPoint.func_224759_a().equals(blockPos) || pathPoint2 != null && pathPoint2.func_224759_a().equals(blockPos)) continue;
                if (InteractWithDoorTask.func_242296_a(serverWorld, livingEntity, globalPos)) {
                    iterator2.remove();
                    continue;
                }
                BlockState blockState = serverWorld.getBlockState(blockPos);
                if (!blockState.isIn(BlockTags.WOODEN_DOORS)) {
                    iterator2.remove();
                    continue;
                }
                DoorBlock doorBlock = (DoorBlock)blockState.getBlock();
                if (!doorBlock.isOpen(blockState)) {
                    iterator2.remove();
                    continue;
                }
                if (InteractWithDoorTask.func_242295_a(serverWorld, livingEntity, blockPos)) {
                    iterator2.remove();
                    continue;
                }
                doorBlock.openDoor(serverWorld, blockState, blockPos, true);
                iterator2.remove();
            }
        }
    }

    private static boolean func_242295_a(ServerWorld serverWorld, LivingEntity livingEntity, BlockPos blockPos) {
        Brain<List<LivingEntity>> brain = livingEntity.getBrain();
        return !brain.hasMemory(MemoryModuleType.MOBS) ? false : brain.getMemory(MemoryModuleType.MOBS).get().stream().filter(arg_0 -> InteractWithDoorTask.lambda$func_242295_a$0(livingEntity, arg_0)).filter(arg_0 -> InteractWithDoorTask.lambda$func_242295_a$1(blockPos, arg_0)).anyMatch(arg_0 -> InteractWithDoorTask.lambda$func_242295_a$2(serverWorld, blockPos, arg_0));
    }

    private static boolean func_242300_b(ServerWorld serverWorld, LivingEntity livingEntity, BlockPos blockPos) {
        if (!livingEntity.getBrain().hasMemory(MemoryModuleType.PATH)) {
            return true;
        }
        Path path = livingEntity.getBrain().getMemory(MemoryModuleType.PATH).get();
        if (path.isFinished()) {
            return true;
        }
        PathPoint pathPoint = path.func_242950_i();
        if (pathPoint == null) {
            return true;
        }
        PathPoint pathPoint2 = path.func_237225_h_();
        return blockPos.equals(pathPoint.func_224759_a()) || blockPos.equals(pathPoint2.func_224759_a());
    }

    private static boolean func_242296_a(ServerWorld serverWorld, LivingEntity livingEntity, GlobalPos globalPos) {
        return globalPos.getDimension() != serverWorld.getDimensionKey() || !globalPos.getPos().withinDistance(livingEntity.getPositionVec(), 2.0);
    }

    private void func_242301_c(ServerWorld serverWorld, LivingEntity livingEntity, BlockPos blockPos) {
        Brain<?> brain = livingEntity.getBrain();
        GlobalPos globalPos = GlobalPos.getPosition(serverWorld.getDimensionKey(), blockPos);
        if (brain.getMemory(MemoryModuleType.OPENED_DOORS).isPresent()) {
            brain.getMemory(MemoryModuleType.OPENED_DOORS).get().add(globalPos);
        } else {
            brain.setMemory(MemoryModuleType.OPENED_DOORS, Sets.newHashSet(globalPos));
        }
    }

    private static boolean lambda$func_242295_a$2(ServerWorld serverWorld, BlockPos blockPos, LivingEntity livingEntity) {
        return InteractWithDoorTask.func_242300_b(serverWorld, livingEntity, blockPos);
    }

    private static boolean lambda$func_242295_a$1(BlockPos blockPos, LivingEntity livingEntity) {
        return blockPos.withinDistance(livingEntity.getPositionVec(), 2.0);
    }

    private static boolean lambda$func_242295_a$0(LivingEntity livingEntity, LivingEntity livingEntity2) {
        return livingEntity2.getType() == livingEntity.getType();
    }
}


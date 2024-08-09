/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.network.DebugPacketSender;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.village.PointOfInterestManager;
import net.minecraft.village.PointOfInterestType;
import net.minecraft.world.server.ServerWorld;

public class GatherPOITask
extends Task<CreatureEntity> {
    private final PointOfInterestType field_220604_a;
    private final MemoryModuleType<GlobalPos> field_220605_b;
    private final boolean field_220606_c;
    private final Optional<Byte> field_242290_e;
    private long field_220607_d;
    private final Long2ObjectMap<RetryMarker> field_223013_e = new Long2ObjectOpenHashMap<RetryMarker>();

    public GatherPOITask(PointOfInterestType pointOfInterestType, MemoryModuleType<GlobalPos> memoryModuleType, MemoryModuleType<GlobalPos> memoryModuleType2, boolean bl, Optional<Byte> optional) {
        super(GatherPOITask.func_233841_a_(memoryModuleType, memoryModuleType2));
        this.field_220604_a = pointOfInterestType;
        this.field_220605_b = memoryModuleType2;
        this.field_220606_c = bl;
        this.field_242290_e = optional;
    }

    public GatherPOITask(PointOfInterestType pointOfInterestType, MemoryModuleType<GlobalPos> memoryModuleType, boolean bl, Optional<Byte> optional) {
        this(pointOfInterestType, memoryModuleType, memoryModuleType, bl, optional);
    }

    private static ImmutableMap<MemoryModuleType<?>, MemoryModuleStatus> func_233841_a_(MemoryModuleType<GlobalPos> memoryModuleType, MemoryModuleType<GlobalPos> memoryModuleType2) {
        ImmutableMap.Builder<MemoryModuleType<GlobalPos>, MemoryModuleStatus> builder = ImmutableMap.builder();
        builder.put(memoryModuleType, MemoryModuleStatus.VALUE_ABSENT);
        if (memoryModuleType2 != memoryModuleType) {
            builder.put(memoryModuleType2, MemoryModuleStatus.VALUE_ABSENT);
        }
        return builder.build();
    }

    @Override
    protected boolean shouldExecute(ServerWorld serverWorld, CreatureEntity creatureEntity) {
        if (this.field_220606_c && creatureEntity.isChild()) {
            return true;
        }
        if (this.field_220607_d == 0L) {
            this.field_220607_d = creatureEntity.world.getGameTime() + (long)serverWorld.rand.nextInt(20);
            return true;
        }
        return serverWorld.getGameTime() >= this.field_220607_d;
    }

    @Override
    protected void startExecuting(ServerWorld serverWorld, CreatureEntity creatureEntity, long l) {
        this.field_220607_d = l + 20L + (long)serverWorld.getRandom().nextInt(20);
        PointOfInterestManager pointOfInterestManager = serverWorld.getPointOfInterestManager();
        this.field_223013_e.long2ObjectEntrySet().removeIf(arg_0 -> GatherPOITask.lambda$startExecuting$0(l, arg_0));
        Predicate<BlockPos> predicate = arg_0 -> this.lambda$startExecuting$1(l, arg_0);
        Set<BlockPos> set = pointOfInterestManager.func_242324_b(this.field_220604_a.getPredicate(), predicate, creatureEntity.getPosition(), 48, PointOfInterestManager.Status.HAS_SPACE).limit(5L).collect(Collectors.toSet());
        Path path = creatureEntity.getNavigator().pathfind(set, this.field_220604_a.getValidRange());
        if (path != null && path.reachesTarget()) {
            BlockPos blockPos = path.getTarget();
            pointOfInterestManager.getType(blockPos).ifPresent(arg_0 -> this.lambda$startExecuting$4(pointOfInterestManager, blockPos, creatureEntity, serverWorld, arg_0));
        } else {
            for (BlockPos blockPos : set) {
                this.field_223013_e.computeIfAbsent(blockPos.toLong(), arg_0 -> GatherPOITask.lambda$startExecuting$5(creatureEntity, l, arg_0));
            }
        }
    }

    @Override
    protected boolean shouldExecute(ServerWorld serverWorld, LivingEntity livingEntity) {
        return this.shouldExecute(serverWorld, (CreatureEntity)livingEntity);
    }

    @Override
    protected void startExecuting(ServerWorld serverWorld, LivingEntity livingEntity, long l) {
        this.startExecuting(serverWorld, (CreatureEntity)livingEntity, l);
    }

    private static RetryMarker lambda$startExecuting$5(CreatureEntity creatureEntity, long l, long l2) {
        return new RetryMarker(creatureEntity.world.rand, l);
    }

    private void lambda$startExecuting$4(PointOfInterestManager pointOfInterestManager, BlockPos blockPos, CreatureEntity creatureEntity, ServerWorld serverWorld, PointOfInterestType pointOfInterestType) {
        pointOfInterestManager.take(this.field_220604_a.getPredicate(), arg_0 -> GatherPOITask.lambda$startExecuting$2(blockPos, arg_0), blockPos, 1);
        creatureEntity.getBrain().setMemory(this.field_220605_b, GlobalPos.getPosition(serverWorld.getDimensionKey(), blockPos));
        this.field_242290_e.ifPresent(arg_0 -> GatherPOITask.lambda$startExecuting$3(serverWorld, creatureEntity, arg_0));
        this.field_223013_e.clear();
        DebugPacketSender.func_218801_c(serverWorld, blockPos);
    }

    private static void lambda$startExecuting$3(ServerWorld serverWorld, CreatureEntity creatureEntity, Byte by) {
        serverWorld.setEntityState(creatureEntity, by);
    }

    private static boolean lambda$startExecuting$2(BlockPos blockPos, BlockPos blockPos2) {
        return blockPos2.equals(blockPos);
    }

    private boolean lambda$startExecuting$1(long l, BlockPos blockPos) {
        RetryMarker retryMarker = (RetryMarker)this.field_223013_e.get(blockPos.toLong());
        if (retryMarker == null) {
            return false;
        }
        if (!retryMarker.func_241372_c_(l)) {
            return true;
        }
        retryMarker.func_241370_a_(l);
        return false;
    }

    private static boolean lambda$startExecuting$0(long l, Long2ObjectMap.Entry entry) {
        return !((RetryMarker)entry.getValue()).func_241371_b_(l);
    }

    static class RetryMarker {
        private final Random field_241366_a_;
        private long field_241367_b_;
        private long field_241368_c_;
        private int field_241369_d_;

        RetryMarker(Random random2, long l) {
            this.field_241366_a_ = random2;
            this.func_241370_a_(l);
        }

        public void func_241370_a_(long l) {
            this.field_241367_b_ = l;
            int n = this.field_241369_d_ + this.field_241366_a_.nextInt(40) + 40;
            this.field_241369_d_ = Math.min(n, 400);
            this.field_241368_c_ = l + (long)this.field_241369_d_;
        }

        public boolean func_241371_b_(long l) {
            return l - this.field_241367_b_ < 400L;
        }

        public boolean func_241372_c_(long l) {
            return l >= this.field_241368_c_;
        }

        public String toString() {
            return "RetryMarker{, previousAttemptAt=" + this.field_241367_b_ + ", nextScheduledAttemptAt=" + this.field_241368_c_ + ", currentDelay=" + this.field_241369_d_ + "}";
        }
    }
}


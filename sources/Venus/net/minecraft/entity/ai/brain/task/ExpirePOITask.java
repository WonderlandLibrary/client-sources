/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import java.util.function.Predicate;
import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.network.DebugPacketSender;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.village.PointOfInterestType;
import net.minecraft.world.server.ServerWorld;

public class ExpirePOITask
extends Task<LivingEntity> {
    private final MemoryModuleType<GlobalPos> field_220591_a;
    private final Predicate<PointOfInterestType> poiType;

    public ExpirePOITask(PointOfInterestType pointOfInterestType, MemoryModuleType<GlobalPos> memoryModuleType) {
        super(ImmutableMap.of(memoryModuleType, MemoryModuleStatus.VALUE_PRESENT));
        this.poiType = pointOfInterestType.getPredicate();
        this.field_220591_a = memoryModuleType;
    }

    @Override
    protected boolean shouldExecute(ServerWorld serverWorld, LivingEntity livingEntity) {
        GlobalPos globalPos = livingEntity.getBrain().getMemory(this.field_220591_a).get();
        return serverWorld.getDimensionKey() == globalPos.getDimension() && globalPos.getPos().withinDistance(livingEntity.getPositionVec(), 16.0);
    }

    @Override
    protected void startExecuting(ServerWorld serverWorld, LivingEntity livingEntity, long l) {
        Brain<?> brain = livingEntity.getBrain();
        GlobalPos globalPos = brain.getMemory(this.field_220591_a).get();
        BlockPos blockPos = globalPos.getPos();
        ServerWorld serverWorld2 = serverWorld.getServer().getWorld(globalPos.getDimension());
        if (serverWorld2 != null && !this.func_223020_a(serverWorld2, blockPos)) {
            if (this.func_223019_a(serverWorld2, blockPos, livingEntity)) {
                brain.removeMemory(this.field_220591_a);
                serverWorld.getPointOfInterestManager().release(blockPos);
                DebugPacketSender.func_218801_c(serverWorld, blockPos);
            }
        } else {
            brain.removeMemory(this.field_220591_a);
        }
    }

    private boolean func_223019_a(ServerWorld serverWorld, BlockPos blockPos, LivingEntity livingEntity) {
        BlockState blockState = serverWorld.getBlockState(blockPos);
        return blockState.getBlock().isIn(BlockTags.BEDS) && blockState.get(BedBlock.OCCUPIED) != false && !livingEntity.isSleeping();
    }

    private boolean func_223020_a(ServerWorld serverWorld, BlockPos blockPos) {
        return !serverWorld.getPointOfInterestManager().exists(blockPos, this.poiType);
    }
}


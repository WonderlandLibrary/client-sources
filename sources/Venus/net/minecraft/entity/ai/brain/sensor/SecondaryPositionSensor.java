/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.brain.sensor;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Set;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class SecondaryPositionSensor
extends Sensor<VillagerEntity> {
    public SecondaryPositionSensor() {
        super(40);
    }

    @Override
    protected void update(ServerWorld serverWorld, VillagerEntity villagerEntity) {
        RegistryKey<World> registryKey = serverWorld.getDimensionKey();
        BlockPos blockPos = villagerEntity.getPosition();
        ArrayList<GlobalPos> arrayList = Lists.newArrayList();
        int n = 4;
        for (int i = -4; i <= 4; ++i) {
            for (int j = -2; j <= 2; ++j) {
                for (int k = -4; k <= 4; ++k) {
                    BlockPos blockPos2 = blockPos.add(i, j, k);
                    if (!villagerEntity.getVillagerData().getProfession().getRelatedWorldBlocks().contains(serverWorld.getBlockState(blockPos2).getBlock())) continue;
                    arrayList.add(GlobalPos.getPosition(registryKey, blockPos2));
                }
            }
        }
        Brain<VillagerEntity> brain = villagerEntity.getBrain();
        if (!arrayList.isEmpty()) {
            brain.setMemory(MemoryModuleType.SECONDARY_JOB_SITE, arrayList);
        } else {
            brain.removeMemory(MemoryModuleType.SECONDARY_JOB_SITE);
        }
    }

    @Override
    public Set<MemoryModuleType<?>> getUsedMemories() {
        return ImmutableSet.of(MemoryModuleType.SECONDARY_JOB_SITE);
    }

    @Override
    protected void update(ServerWorld serverWorld, LivingEntity livingEntity) {
        this.update(serverWorld, (VillagerEntity)livingEntity);
    }
}


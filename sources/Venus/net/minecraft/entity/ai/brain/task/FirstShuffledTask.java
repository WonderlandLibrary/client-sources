/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.Map;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.MultiTask;
import net.minecraft.entity.ai.brain.task.Task;

public class FirstShuffledTask<E extends LivingEntity>
extends MultiTask<E> {
    public FirstShuffledTask(List<Pair<Task<? super E>, Integer>> list) {
        this(ImmutableMap.of(), list);
    }

    public FirstShuffledTask(Map<MemoryModuleType<?>, MemoryModuleStatus> map, List<Pair<Task<? super E>, Integer>> list) {
        super(map, ImmutableSet.of(), MultiTask.Ordering.SHUFFLED, MultiTask.RunType.RUN_ONE, list);
    }
}


/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.memory.WalkTarget;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.EntityPosWrapper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.server.ServerWorld;

public class WalkToVillagerBabiesTask
extends Task<CreatureEntity> {
    public WalkToVillagerBabiesTask() {
        super(ImmutableMap.of(MemoryModuleType.VISIBLE_VILLAGER_BABIES, MemoryModuleStatus.VALUE_PRESENT, MemoryModuleType.WALK_TARGET, MemoryModuleStatus.VALUE_ABSENT, MemoryModuleType.LOOK_TARGET, MemoryModuleStatus.REGISTERED, MemoryModuleType.INTERACTION_TARGET, MemoryModuleStatus.REGISTERED));
    }

    @Override
    protected boolean shouldExecute(ServerWorld serverWorld, CreatureEntity creatureEntity) {
        return serverWorld.getRandom().nextInt(10) == 0 && this.func_220501_e(creatureEntity);
    }

    @Override
    protected void startExecuting(ServerWorld serverWorld, CreatureEntity creatureEntity, long l) {
        LivingEntity livingEntity = this.func_220500_b(creatureEntity);
        if (livingEntity != null) {
            this.func_220508_a(serverWorld, creatureEntity, livingEntity);
        } else {
            Optional<LivingEntity> optional = this.func_220497_b(creatureEntity);
            if (optional.isPresent()) {
                WalkToVillagerBabiesTask.func_220498_a(creatureEntity, optional.get());
            } else {
                this.func_220510_a(creatureEntity).ifPresent(arg_0 -> WalkToVillagerBabiesTask.lambda$startExecuting$0(creatureEntity, arg_0));
            }
        }
    }

    private void func_220508_a(ServerWorld serverWorld, CreatureEntity creatureEntity, LivingEntity livingEntity) {
        for (int i = 0; i < 10; ++i) {
            Vector3d vector3d = RandomPositionGenerator.getLandPos(creatureEntity, 20, 8);
            if (vector3d == null || !serverWorld.isVillage(new BlockPos(vector3d))) continue;
            creatureEntity.getBrain().setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(vector3d, 0.6f, 0));
            return;
        }
    }

    private static void func_220498_a(CreatureEntity creatureEntity, LivingEntity livingEntity) {
        Brain<?> brain = creatureEntity.getBrain();
        brain.setMemory(MemoryModuleType.INTERACTION_TARGET, livingEntity);
        brain.setMemory(MemoryModuleType.LOOK_TARGET, new EntityPosWrapper(livingEntity, true));
        brain.setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(new EntityPosWrapper(livingEntity, false), 0.6f, 1));
    }

    private Optional<LivingEntity> func_220510_a(CreatureEntity creatureEntity) {
        return this.func_220503_d(creatureEntity).stream().findAny();
    }

    private Optional<LivingEntity> func_220497_b(CreatureEntity creatureEntity) {
        Map<LivingEntity, Integer> map = this.func_220505_c(creatureEntity);
        return map.entrySet().stream().sorted(Comparator.comparingInt(Map.Entry::getValue)).filter(WalkToVillagerBabiesTask::lambda$func_220497_b$1).map(Map.Entry::getKey).findFirst();
    }

    private Map<LivingEntity, Integer> func_220505_c(CreatureEntity creatureEntity) {
        HashMap<LivingEntity, Integer> hashMap = Maps.newHashMap();
        this.func_220503_d(creatureEntity).stream().filter(this::func_220502_c).forEach(arg_0 -> this.lambda$func_220505_c$3(hashMap, arg_0));
        return hashMap;
    }

    private List<LivingEntity> func_220503_d(CreatureEntity creatureEntity) {
        return creatureEntity.getBrain().getMemory(MemoryModuleType.VISIBLE_VILLAGER_BABIES).get();
    }

    private LivingEntity func_220495_a(LivingEntity livingEntity) {
        return livingEntity.getBrain().getMemory(MemoryModuleType.INTERACTION_TARGET).get();
    }

    @Nullable
    private LivingEntity func_220500_b(LivingEntity livingEntity) {
        return livingEntity.getBrain().getMemory(MemoryModuleType.VISIBLE_VILLAGER_BABIES).get().stream().filter(arg_0 -> this.lambda$func_220500_b$4(livingEntity, arg_0)).findAny().orElse(null);
    }

    private boolean func_220502_c(LivingEntity livingEntity) {
        return livingEntity.getBrain().getMemory(MemoryModuleType.INTERACTION_TARGET).isPresent();
    }

    private boolean func_220499_a(LivingEntity livingEntity, LivingEntity livingEntity2) {
        return livingEntity2.getBrain().getMemory(MemoryModuleType.INTERACTION_TARGET).filter(arg_0 -> WalkToVillagerBabiesTask.lambda$func_220499_a$5(livingEntity, arg_0)).isPresent();
    }

    private boolean func_220501_e(CreatureEntity creatureEntity) {
        return creatureEntity.getBrain().hasMemory(MemoryModuleType.VISIBLE_VILLAGER_BABIES);
    }

    @Override
    protected boolean shouldExecute(ServerWorld serverWorld, LivingEntity livingEntity) {
        return this.shouldExecute(serverWorld, (CreatureEntity)livingEntity);
    }

    @Override
    protected void startExecuting(ServerWorld serverWorld, LivingEntity livingEntity, long l) {
        this.startExecuting(serverWorld, (CreatureEntity)livingEntity, l);
    }

    private static boolean lambda$func_220499_a$5(LivingEntity livingEntity, LivingEntity livingEntity2) {
        return livingEntity2 == livingEntity;
    }

    private boolean lambda$func_220500_b$4(LivingEntity livingEntity, LivingEntity livingEntity2) {
        return this.func_220499_a(livingEntity, livingEntity2);
    }

    private void lambda$func_220505_c$3(Map map, LivingEntity livingEntity) {
        Integer n = map.compute(this.func_220495_a(livingEntity), WalkToVillagerBabiesTask::lambda$func_220505_c$2);
    }

    private static Integer lambda$func_220505_c$2(LivingEntity livingEntity, Integer n) {
        return n == null ? 1 : n + 1;
    }

    private static boolean lambda$func_220497_b$1(Map.Entry entry) {
        return (Integer)entry.getValue() > 0 && (Integer)entry.getValue() <= 5;
    }

    private static void lambda$startExecuting$0(CreatureEntity creatureEntity, LivingEntity livingEntity) {
        WalkToVillagerBabiesTask.func_220498_a(creatureEntity, livingEntity);
    }
}


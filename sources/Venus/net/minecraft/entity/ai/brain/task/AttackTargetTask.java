/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.brain.BrainUtil;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.item.Item;
import net.minecraft.item.ShootableItem;
import net.minecraft.util.Hand;
import net.minecraft.world.server.ServerWorld;

public class AttackTargetTask
extends Task<MobEntity> {
    private final int cooldown;

    public AttackTargetTask(int n) {
        super(ImmutableMap.of(MemoryModuleType.LOOK_TARGET, MemoryModuleStatus.REGISTERED, MemoryModuleType.ATTACK_TARGET, MemoryModuleStatus.VALUE_PRESENT, MemoryModuleType.ATTACK_COOLING_DOWN, MemoryModuleStatus.VALUE_ABSENT));
        this.cooldown = n;
    }

    @Override
    protected boolean shouldExecute(ServerWorld serverWorld, MobEntity mobEntity) {
        LivingEntity livingEntity = this.getAttackTarget(mobEntity);
        return !this.isRanged(mobEntity) && BrainUtil.isMobVisible(mobEntity, livingEntity) && BrainUtil.canAttackTarget(mobEntity, livingEntity);
    }

    private boolean isRanged(MobEntity mobEntity) {
        return mobEntity.func_233634_a_(arg_0 -> AttackTargetTask.lambda$isRanged$0(mobEntity, arg_0));
    }

    @Override
    protected void startExecuting(ServerWorld serverWorld, MobEntity mobEntity, long l) {
        LivingEntity livingEntity = this.getAttackTarget(mobEntity);
        BrainUtil.lookAt(mobEntity, livingEntity);
        mobEntity.swingArm(Hand.MAIN_HAND);
        mobEntity.attackEntityAsMob(livingEntity);
        mobEntity.getBrain().replaceMemory(MemoryModuleType.ATTACK_COOLING_DOWN, true, this.cooldown);
    }

    private LivingEntity getAttackTarget(MobEntity mobEntity) {
        return mobEntity.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).get();
    }

    @Override
    protected boolean shouldExecute(ServerWorld serverWorld, LivingEntity livingEntity) {
        return this.shouldExecute(serverWorld, (MobEntity)livingEntity);
    }

    @Override
    protected void startExecuting(ServerWorld serverWorld, LivingEntity livingEntity, long l) {
        this.startExecuting(serverWorld, (MobEntity)livingEntity, l);
    }

    private static boolean lambda$isRanged$0(MobEntity mobEntity, Item item) {
        return item instanceof ShootableItem && mobEntity.func_230280_a_((ShootableItem)item);
    }
}


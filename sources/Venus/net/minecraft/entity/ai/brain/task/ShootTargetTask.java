/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.ICrossbowUser;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.brain.BrainUtil;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.EntityPosWrapper;
import net.minecraft.world.server.ServerWorld;

public class ShootTargetTask<E extends MobEntity, T extends LivingEntity>
extends Task<E> {
    private int field_233885_b_;
    private Status field_233886_c_ = Status.UNCHARGED;

    public ShootTargetTask() {
        super(ImmutableMap.of(MemoryModuleType.LOOK_TARGET, MemoryModuleStatus.REGISTERED, MemoryModuleType.ATTACK_TARGET, MemoryModuleStatus.VALUE_PRESENT), 1200);
    }

    @Override
    protected boolean shouldExecute(ServerWorld serverWorld, E e) {
        LivingEntity livingEntity = ShootTargetTask.func_233887_a_(e);
        return ((LivingEntity)e).canEquip(Items.CROSSBOW) && BrainUtil.isMobVisible(e, livingEntity) && BrainUtil.canFireAtTarget(e, livingEntity, 0);
    }

    @Override
    protected boolean shouldContinueExecuting(ServerWorld serverWorld, E e, long l) {
        return ((LivingEntity)e).getBrain().hasMemory(MemoryModuleType.ATTACK_TARGET) && this.shouldExecute(serverWorld, e);
    }

    @Override
    protected void updateTask(ServerWorld serverWorld, E e, long l) {
        LivingEntity livingEntity = ShootTargetTask.func_233887_a_(e);
        this.func_233889_b_((MobEntity)e, livingEntity);
        this.func_233888_a_(e, livingEntity);
    }

    @Override
    protected void resetTask(ServerWorld serverWorld, E e, long l) {
        if (((LivingEntity)e).isHandActive()) {
            ((LivingEntity)e).resetActiveHand();
        }
        if (((LivingEntity)e).canEquip(Items.CROSSBOW)) {
            ((ICrossbowUser)e).setCharging(false);
            CrossbowItem.setCharged(((LivingEntity)e).getActiveItemStack(), false);
        }
    }

    private void func_233888_a_(E e, LivingEntity livingEntity) {
        if (this.field_233886_c_ == Status.UNCHARGED) {
            ((LivingEntity)e).setActiveHand(ProjectileHelper.getHandWith(e, Items.CROSSBOW));
            this.field_233886_c_ = Status.CHARGING;
            ((ICrossbowUser)e).setCharging(true);
        } else if (this.field_233886_c_ == Status.CHARGING) {
            ItemStack itemStack;
            int n;
            if (!((LivingEntity)e).isHandActive()) {
                this.field_233886_c_ = Status.UNCHARGED;
            }
            if ((n = ((LivingEntity)e).getItemInUseMaxCount()) >= CrossbowItem.getChargeTime(itemStack = ((LivingEntity)e).getActiveItemStack())) {
                ((LivingEntity)e).stopActiveHand();
                this.field_233886_c_ = Status.CHARGED;
                this.field_233885_b_ = 20 + ((LivingEntity)e).getRNG().nextInt(20);
                ((ICrossbowUser)e).setCharging(false);
            }
        } else if (this.field_233886_c_ == Status.CHARGED) {
            --this.field_233885_b_;
            if (this.field_233885_b_ == 0) {
                this.field_233886_c_ = Status.READY_TO_ATTACK;
            }
        } else if (this.field_233886_c_ == Status.READY_TO_ATTACK) {
            ((IRangedAttackMob)e).attackEntityWithRangedAttack(livingEntity, 1.0f);
            ItemStack itemStack = ((LivingEntity)e).getHeldItem(ProjectileHelper.getHandWith(e, Items.CROSSBOW));
            CrossbowItem.setCharged(itemStack, false);
            this.field_233886_c_ = Status.UNCHARGED;
        }
    }

    private void func_233889_b_(MobEntity mobEntity, LivingEntity livingEntity) {
        mobEntity.getBrain().setMemory(MemoryModuleType.LOOK_TARGET, new EntityPosWrapper(livingEntity, true));
    }

    private static LivingEntity func_233887_a_(LivingEntity livingEntity) {
        return livingEntity.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).get();
    }

    @Override
    protected boolean shouldExecute(ServerWorld serverWorld, LivingEntity livingEntity) {
        return this.shouldExecute(serverWorld, (E)((MobEntity)livingEntity));
    }

    @Override
    protected boolean shouldContinueExecuting(ServerWorld serverWorld, LivingEntity livingEntity, long l) {
        return this.shouldContinueExecuting(serverWorld, (E)((MobEntity)livingEntity), l);
    }

    @Override
    protected void resetTask(ServerWorld serverWorld, LivingEntity livingEntity, long l) {
        this.resetTask(serverWorld, (E)((MobEntity)livingEntity), l);
    }

    @Override
    protected void updateTask(ServerWorld serverWorld, LivingEntity livingEntity, long l) {
        this.updateTask(serverWorld, (E)((MobEntity)livingEntity), l);
    }

    static enum Status {
        UNCHARGED,
        CHARGING,
        CHARGED,
        READY_TO_ATTACK;

    }
}


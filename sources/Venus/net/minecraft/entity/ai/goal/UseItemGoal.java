/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.goal;

import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;

public class UseItemGoal<T extends MobEntity>
extends Goal {
    private final T user;
    private final ItemStack stack;
    private final Predicate<? super T> field_220768_c;
    private final SoundEvent field_220769_d;

    public UseItemGoal(T t, ItemStack itemStack, @Nullable SoundEvent soundEvent, Predicate<? super T> predicate) {
        this.user = t;
        this.stack = itemStack;
        this.field_220769_d = soundEvent;
        this.field_220768_c = predicate;
    }

    @Override
    public boolean shouldExecute() {
        return this.field_220768_c.test(this.user);
    }

    @Override
    public boolean shouldContinueExecuting() {
        return ((LivingEntity)this.user).isHandActive();
    }

    @Override
    public void startExecuting() {
        ((MobEntity)this.user).setItemStackToSlot(EquipmentSlotType.MAINHAND, this.stack.copy());
        ((LivingEntity)this.user).setActiveHand(Hand.MAIN_HAND);
    }

    @Override
    public void resetTask() {
        ((MobEntity)this.user).setItemStackToSlot(EquipmentSlotType.MAINHAND, ItemStack.EMPTY);
        if (this.field_220769_d != null) {
            ((Entity)this.user).playSound(this.field_220769_d, 1.0f, ((LivingEntity)this.user).getRNG().nextFloat() * 0.2f + 0.9f);
        }
    }
}


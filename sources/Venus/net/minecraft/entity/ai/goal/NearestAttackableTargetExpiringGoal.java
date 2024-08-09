/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.goal;

import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.monster.AbstractRaiderEntity;

public class NearestAttackableTargetExpiringGoal<T extends LivingEntity>
extends NearestAttackableTargetGoal<T> {
    private int cooldown = 0;

    public NearestAttackableTargetExpiringGoal(AbstractRaiderEntity abstractRaiderEntity, Class<T> clazz, boolean bl, @Nullable Predicate<LivingEntity> predicate) {
        super(abstractRaiderEntity, clazz, 500, bl, false, predicate);
    }

    public int getCooldown() {
        return this.cooldown;
    }

    public void tickCooldown() {
        --this.cooldown;
    }

    @Override
    public boolean shouldExecute() {
        if (this.cooldown <= 0 && this.goalOwner.getRNG().nextBoolean()) {
            if (!((AbstractRaiderEntity)this.goalOwner).isRaidActive()) {
                return true;
            }
            this.findNearestTarget();
            return this.nearestTarget != null;
        }
        return true;
    }

    @Override
    public void startExecuting() {
        this.cooldown = 200;
        super.startExecuting();
    }
}


/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.goal;

import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.monster.AbstractRaiderEntity;

public class ToggleableNearestAttackableTargetGoal<T extends LivingEntity>
extends NearestAttackableTargetGoal<T> {
    private boolean field_220784_i = true;

    public ToggleableNearestAttackableTargetGoal(AbstractRaiderEntity abstractRaiderEntity, Class<T> clazz, int n, boolean bl, boolean bl2, @Nullable Predicate<LivingEntity> predicate) {
        super(abstractRaiderEntity, clazz, n, bl, bl2, predicate);
    }

    public void func_220783_a(boolean bl) {
        this.field_220784_i = bl;
    }

    @Override
    public boolean shouldExecute() {
        return this.field_220784_i && super.shouldExecute();
    }
}


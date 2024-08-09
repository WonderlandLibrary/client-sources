/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.goal;

import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IAngerable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.GameRules;

public class ResetAngerGoal<T extends MobEntity>
extends Goal {
    private final T field_241383_a_;
    private final boolean field_241384_b_;
    private int revengeTimer;

    public ResetAngerGoal(T t, boolean bl) {
        this.field_241383_a_ = t;
        this.field_241384_b_ = bl;
    }

    @Override
    public boolean shouldExecute() {
        return ((MobEntity)this.field_241383_a_).world.getGameRules().getBoolean(GameRules.UNIVERSAL_ANGER) && this.shouldGetRevengeOnPlayer();
    }

    private boolean shouldGetRevengeOnPlayer() {
        return ((LivingEntity)this.field_241383_a_).getRevengeTarget() != null && ((LivingEntity)this.field_241383_a_).getRevengeTarget().getType() == EntityType.PLAYER && ((LivingEntity)this.field_241383_a_).getRevengeTimer() > this.revengeTimer;
    }

    @Override
    public void startExecuting() {
        this.revengeTimer = ((LivingEntity)this.field_241383_a_).getRevengeTimer();
        ((IAngerable)this.field_241383_a_).func_241355_J__();
        if (this.field_241384_b_) {
            this.func_241389_h_().stream().filter(this::lambda$startExecuting$0).map(ResetAngerGoal::lambda$startExecuting$1).forEach(IAngerable::func_241355_J__);
        }
        super.startExecuting();
    }

    private List<MobEntity> func_241389_h_() {
        double d = ((LivingEntity)this.field_241383_a_).getAttributeValue(Attributes.FOLLOW_RANGE);
        AxisAlignedBB axisAlignedBB = AxisAlignedBB.fromVector(((Entity)this.field_241383_a_).getPositionVec()).grow(d, 10.0, d);
        return ((MobEntity)this.field_241383_a_).world.getLoadedEntitiesWithinAABB(this.field_241383_a_.getClass(), axisAlignedBB);
    }

    private static IAngerable lambda$startExecuting$1(MobEntity mobEntity) {
        return (IAngerable)((Object)mobEntity);
    }

    private boolean lambda$startExecuting$0(MobEntity mobEntity) {
        return mobEntity != this.field_241383_a_;
    }
}


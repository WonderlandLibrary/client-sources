/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ICrossbowUser;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.RangedInteger;

public class RangedCrossbowAttackGoal<T extends MonsterEntity & ICrossbowUser>
extends Goal {
    public static final RangedInteger field_241381_a_ = new RangedInteger(20, 40);
    private final T field_220748_a;
    private CrossbowState field_220749_b = CrossbowState.UNCHARGED;
    private final double field_220750_c;
    private final float field_220751_d;
    private int field_220752_e;
    private int field_220753_f;
    private int field_241382_h_;

    public RangedCrossbowAttackGoal(T t, double d, float f) {
        this.field_220748_a = t;
        this.field_220750_c = d;
        this.field_220751_d = f * f;
        this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean shouldExecute() {
        return this.func_220746_h() && this.func_220745_g();
    }

    private boolean func_220745_g() {
        return ((LivingEntity)this.field_220748_a).canEquip(Items.CROSSBOW);
    }

    @Override
    public boolean shouldContinueExecuting() {
        return this.func_220746_h() && (this.shouldExecute() || !((MobEntity)this.field_220748_a).getNavigator().noPath()) && this.func_220745_g();
    }

    private boolean func_220746_h() {
        return ((MobEntity)this.field_220748_a).getAttackTarget() != null && ((MobEntity)this.field_220748_a).getAttackTarget().isAlive();
    }

    @Override
    public void resetTask() {
        super.resetTask();
        ((MobEntity)this.field_220748_a).setAggroed(true);
        ((MobEntity)this.field_220748_a).setAttackTarget(null);
        this.field_220752_e = 0;
        if (((LivingEntity)this.field_220748_a).isHandActive()) {
            ((LivingEntity)this.field_220748_a).resetActiveHand();
            ((ICrossbowUser)this.field_220748_a).setCharging(false);
            CrossbowItem.setCharged(((LivingEntity)this.field_220748_a).getActiveItemStack(), false);
        }
    }

    @Override
    public void tick() {
        LivingEntity livingEntity = ((MobEntity)this.field_220748_a).getAttackTarget();
        if (livingEntity != null) {
            boolean bl;
            boolean bl2;
            boolean bl3 = ((MobEntity)this.field_220748_a).getEntitySenses().canSee(livingEntity);
            boolean bl4 = bl2 = this.field_220752_e > 0;
            if (bl3 != bl2) {
                this.field_220752_e = 0;
            }
            this.field_220752_e = bl3 ? ++this.field_220752_e : --this.field_220752_e;
            double d = ((Entity)this.field_220748_a).getDistanceSq(livingEntity);
            boolean bl5 = bl = (d > (double)this.field_220751_d || this.field_220752_e < 5) && this.field_220753_f == 0;
            if (bl) {
                --this.field_241382_h_;
                if (this.field_241382_h_ <= 0) {
                    ((MobEntity)this.field_220748_a).getNavigator().tryMoveToEntityLiving(livingEntity, this.func_220747_j() ? this.field_220750_c : this.field_220750_c * 0.5);
                    this.field_241382_h_ = field_241381_a_.getRandomWithinRange(((LivingEntity)this.field_220748_a).getRNG());
                }
            } else {
                this.field_241382_h_ = 0;
                ((MobEntity)this.field_220748_a).getNavigator().clearPath();
            }
            ((MobEntity)this.field_220748_a).getLookController().setLookPositionWithEntity(livingEntity, 30.0f, 30.0f);
            if (this.field_220749_b == CrossbowState.UNCHARGED) {
                if (!bl) {
                    ((LivingEntity)this.field_220748_a).setActiveHand(ProjectileHelper.getHandWith(this.field_220748_a, Items.CROSSBOW));
                    this.field_220749_b = CrossbowState.CHARGING;
                    ((ICrossbowUser)this.field_220748_a).setCharging(true);
                }
            } else if (this.field_220749_b == CrossbowState.CHARGING) {
                ItemStack itemStack;
                int n;
                if (!((LivingEntity)this.field_220748_a).isHandActive()) {
                    this.field_220749_b = CrossbowState.UNCHARGED;
                }
                if ((n = ((LivingEntity)this.field_220748_a).getItemInUseMaxCount()) >= CrossbowItem.getChargeTime(itemStack = ((LivingEntity)this.field_220748_a).getActiveItemStack())) {
                    ((LivingEntity)this.field_220748_a).stopActiveHand();
                    this.field_220749_b = CrossbowState.CHARGED;
                    this.field_220753_f = 20 + ((LivingEntity)this.field_220748_a).getRNG().nextInt(20);
                    ((ICrossbowUser)this.field_220748_a).setCharging(false);
                }
            } else if (this.field_220749_b == CrossbowState.CHARGED) {
                --this.field_220753_f;
                if (this.field_220753_f == 0) {
                    this.field_220749_b = CrossbowState.READY_TO_ATTACK;
                }
            } else if (this.field_220749_b == CrossbowState.READY_TO_ATTACK && bl3) {
                ((IRangedAttackMob)this.field_220748_a).attackEntityWithRangedAttack(livingEntity, 1.0f);
                ItemStack itemStack = ((LivingEntity)this.field_220748_a).getHeldItem(ProjectileHelper.getHandWith(this.field_220748_a, Items.CROSSBOW));
                CrossbowItem.setCharged(itemStack, false);
                this.field_220749_b = CrossbowState.UNCHARGED;
            }
        }
    }

    private boolean func_220747_j() {
        return this.field_220749_b == CrossbowState.UNCHARGED;
    }

    static enum CrossbowState {
        UNCHARGED,
        CHARGING,
        CHARGED,
        READY_TO_ATTACK;

    }
}


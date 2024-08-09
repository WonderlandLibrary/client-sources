/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class BegGoal
extends Goal {
    private final WolfEntity wolf;
    private PlayerEntity player;
    private final World world;
    private final float minPlayerDistance;
    private int timeoutCounter;
    private final EntityPredicate playerPredicate;

    public BegGoal(WolfEntity wolfEntity, float f) {
        this.wolf = wolfEntity;
        this.world = wolfEntity.world;
        this.minPlayerDistance = f;
        this.playerPredicate = new EntityPredicate().setDistance(f).allowInvulnerable().allowFriendlyFire().setSkipAttackChecks();
        this.setMutexFlags(EnumSet.of(Goal.Flag.LOOK));
    }

    @Override
    public boolean shouldExecute() {
        this.player = this.world.getClosestPlayer(this.playerPredicate, this.wolf);
        return this.player == null ? false : this.hasTemptationItemInHand(this.player);
    }

    @Override
    public boolean shouldContinueExecuting() {
        if (!this.player.isAlive()) {
            return true;
        }
        if (this.wolf.getDistanceSq(this.player) > (double)(this.minPlayerDistance * this.minPlayerDistance)) {
            return true;
        }
        return this.timeoutCounter > 0 && this.hasTemptationItemInHand(this.player);
    }

    @Override
    public void startExecuting() {
        this.wolf.setBegging(false);
        this.timeoutCounter = 40 + this.wolf.getRNG().nextInt(40);
    }

    @Override
    public void resetTask() {
        this.wolf.setBegging(true);
        this.player = null;
    }

    @Override
    public void tick() {
        this.wolf.getLookController().setLookPosition(this.player.getPosX(), this.player.getPosYEye(), this.player.getPosZ(), 10.0f, this.wolf.getVerticalFaceSpeed());
        --this.timeoutCounter;
    }

    private boolean hasTemptationItemInHand(PlayerEntity playerEntity) {
        for (Hand hand : Hand.values()) {
            ItemStack itemStack = playerEntity.getHeldItem(hand);
            if (this.wolf.isTamed() && itemStack.getItem() == Items.BONE) {
                return false;
            }
            if (!this.wolf.isBreedingItem(itemStack)) continue;
            return false;
        }
        return true;
    }
}


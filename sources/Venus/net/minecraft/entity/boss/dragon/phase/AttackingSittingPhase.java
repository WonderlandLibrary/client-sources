/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.boss.dragon.phase;

import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.phase.PhaseType;
import net.minecraft.entity.boss.dragon.phase.SittingPhase;
import net.minecraft.util.SoundEvents;

public class AttackingSittingPhase
extends SittingPhase {
    private int attackingTicks;

    public AttackingSittingPhase(EnderDragonEntity enderDragonEntity) {
        super(enderDragonEntity);
    }

    @Override
    public void clientTick() {
        this.dragon.world.playSound(this.dragon.getPosX(), this.dragon.getPosY(), this.dragon.getPosZ(), SoundEvents.ENTITY_ENDER_DRAGON_GROWL, this.dragon.getSoundCategory(), 2.5f, 0.8f + this.dragon.getRNG().nextFloat() * 0.3f, true);
    }

    @Override
    public void serverTick() {
        if (this.attackingTicks++ >= 40) {
            this.dragon.getPhaseManager().setPhase(PhaseType.SITTING_FLAMING);
        }
    }

    @Override
    public void initPhase() {
        this.attackingTicks = 0;
    }

    public PhaseType<AttackingSittingPhase> getType() {
        return PhaseType.SITTING_ATTACKING;
    }
}


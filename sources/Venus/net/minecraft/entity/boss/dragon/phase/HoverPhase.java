/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.boss.dragon.phase;

import javax.annotation.Nullable;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.phase.Phase;
import net.minecraft.entity.boss.dragon.phase.PhaseType;
import net.minecraft.util.math.vector.Vector3d;

public class HoverPhase
extends Phase {
    private Vector3d targetLocation;

    public HoverPhase(EnderDragonEntity enderDragonEntity) {
        super(enderDragonEntity);
    }

    @Override
    public void serverTick() {
        if (this.targetLocation == null) {
            this.targetLocation = this.dragon.getPositionVec();
        }
    }

    @Override
    public boolean getIsStationary() {
        return false;
    }

    @Override
    public void initPhase() {
        this.targetLocation = null;
    }

    @Override
    public float getMaxRiseOrFall() {
        return 1.0f;
    }

    @Override
    @Nullable
    public Vector3d getTargetLocation() {
        return this.targetLocation;
    }

    public PhaseType<HoverPhase> getType() {
        return PhaseType.HOVER;
    }
}


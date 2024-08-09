/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.boss.dragon.phase;

import javax.annotation.Nullable;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.phase.Phase;
import net.minecraft.entity.boss.dragon.phase.PhaseType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.EndPodiumFeature;

public class DyingPhase
extends Phase {
    private Vector3d targetLocation;
    private int time;

    public DyingPhase(EnderDragonEntity enderDragonEntity) {
        super(enderDragonEntity);
    }

    @Override
    public void clientTick() {
        if (this.time++ % 10 == 0) {
            float f = (this.dragon.getRNG().nextFloat() - 0.5f) * 8.0f;
            float f2 = (this.dragon.getRNG().nextFloat() - 0.5f) * 4.0f;
            float f3 = (this.dragon.getRNG().nextFloat() - 0.5f) * 8.0f;
            this.dragon.world.addParticle(ParticleTypes.EXPLOSION_EMITTER, this.dragon.getPosX() + (double)f, this.dragon.getPosY() + 2.0 + (double)f2, this.dragon.getPosZ() + (double)f3, 0.0, 0.0, 0.0);
        }
    }

    @Override
    public void serverTick() {
        double d;
        ++this.time;
        if (this.targetLocation == null) {
            BlockPos blockPos = this.dragon.world.getHeight(Heightmap.Type.MOTION_BLOCKING, EndPodiumFeature.END_PODIUM_LOCATION);
            this.targetLocation = Vector3d.copyCenteredHorizontally(blockPos);
        }
        if (!((d = this.targetLocation.squareDistanceTo(this.dragon.getPosX(), this.dragon.getPosY(), this.dragon.getPosZ())) < 100.0 || d > 22500.0 || this.dragon.collidedHorizontally || this.dragon.collidedVertically)) {
            this.dragon.setHealth(1.0f);
        } else {
            this.dragon.setHealth(0.0f);
        }
    }

    @Override
    public void initPhase() {
        this.targetLocation = null;
        this.time = 0;
    }

    @Override
    public float getMaxRiseOrFall() {
        return 3.0f;
    }

    @Override
    @Nullable
    public Vector3d getTargetLocation() {
        return this.targetLocation;
    }

    public PhaseType<DyingPhase> getType() {
        return PhaseType.DYING;
    }
}


/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.boss.dragon.phase;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.phase.Phase;
import net.minecraft.entity.boss.dragon.phase.PhaseType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.EndPodiumFeature;

public class LandingPhase
extends Phase {
    private Vector3d targetLocation;

    public LandingPhase(EnderDragonEntity enderDragonEntity) {
        super(enderDragonEntity);
    }

    @Override
    public void clientTick() {
        Vector3d vector3d = this.dragon.getHeadLookVec(1.0f).normalize();
        vector3d.rotateYaw(-0.7853982f);
        double d = this.dragon.dragonPartHead.getPosX();
        double d2 = this.dragon.dragonPartHead.getPosYHeight(0.5);
        double d3 = this.dragon.dragonPartHead.getPosZ();
        for (int i = 0; i < 8; ++i) {
            Random random2 = this.dragon.getRNG();
            double d4 = d + random2.nextGaussian() / 2.0;
            double d5 = d2 + random2.nextGaussian() / 2.0;
            double d6 = d3 + random2.nextGaussian() / 2.0;
            Vector3d vector3d2 = this.dragon.getMotion();
            this.dragon.world.addParticle(ParticleTypes.DRAGON_BREATH, d4, d5, d6, -vector3d.x * (double)0.08f + vector3d2.x, -vector3d.y * (double)0.3f + vector3d2.y, -vector3d.z * (double)0.08f + vector3d2.z);
            vector3d.rotateYaw(0.19634955f);
        }
    }

    @Override
    public void serverTick() {
        if (this.targetLocation == null) {
            this.targetLocation = Vector3d.copyCenteredHorizontally(this.dragon.world.getHeight(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, EndPodiumFeature.END_PODIUM_LOCATION));
        }
        if (this.targetLocation.squareDistanceTo(this.dragon.getPosX(), this.dragon.getPosY(), this.dragon.getPosZ()) < 1.0) {
            this.dragon.getPhaseManager().getPhase(PhaseType.SITTING_FLAMING).resetFlameCount();
            this.dragon.getPhaseManager().setPhase(PhaseType.SITTING_SCANNING);
        }
    }

    @Override
    public float getMaxRiseOrFall() {
        return 1.5f;
    }

    @Override
    public float getYawFactor() {
        float f = MathHelper.sqrt(Entity.horizontalMag(this.dragon.getMotion())) + 1.0f;
        float f2 = Math.min(f, 40.0f);
        return f2 / f;
    }

    @Override
    public void initPhase() {
        this.targetLocation = null;
    }

    @Override
    @Nullable
    public Vector3d getTargetLocation() {
        return this.targetLocation;
    }

    public PhaseType<LandingPhase> getType() {
        return PhaseType.LANDING;
    }
}


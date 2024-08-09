/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.boss.dragon.phase;

import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.phase.PhaseType;
import net.minecraft.entity.boss.dragon.phase.SittingPhase;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;

public class FlamingSittingPhase
extends SittingPhase {
    private int flameTicks;
    private int flameCount;
    private AreaEffectCloudEntity areaEffectCloud;

    public FlamingSittingPhase(EnderDragonEntity enderDragonEntity) {
        super(enderDragonEntity);
    }

    @Override
    public void clientTick() {
        ++this.flameTicks;
        if (this.flameTicks % 2 == 0 && this.flameTicks < 10) {
            Vector3d vector3d = this.dragon.getHeadLookVec(1.0f).normalize();
            vector3d.rotateYaw(-0.7853982f);
            double d = this.dragon.dragonPartHead.getPosX();
            double d2 = this.dragon.dragonPartHead.getPosYHeight(0.5);
            double d3 = this.dragon.dragonPartHead.getPosZ();
            for (int i = 0; i < 8; ++i) {
                double d4 = d + this.dragon.getRNG().nextGaussian() / 2.0;
                double d5 = d2 + this.dragon.getRNG().nextGaussian() / 2.0;
                double d6 = d3 + this.dragon.getRNG().nextGaussian() / 2.0;
                for (int j = 0; j < 6; ++j) {
                    this.dragon.world.addParticle(ParticleTypes.DRAGON_BREATH, d4, d5, d6, -vector3d.x * (double)0.08f * (double)j, -vector3d.y * (double)0.6f, -vector3d.z * (double)0.08f * (double)j);
                }
                vector3d.rotateYaw(0.19634955f);
            }
        }
    }

    @Override
    public void serverTick() {
        ++this.flameTicks;
        if (this.flameTicks >= 200) {
            if (this.flameCount >= 4) {
                this.dragon.getPhaseManager().setPhase(PhaseType.TAKEOFF);
            } else {
                this.dragon.getPhaseManager().setPhase(PhaseType.SITTING_SCANNING);
            }
        } else if (this.flameTicks == 10) {
            double d;
            Vector3d vector3d = new Vector3d(this.dragon.dragonPartHead.getPosX() - this.dragon.getPosX(), 0.0, this.dragon.dragonPartHead.getPosZ() - this.dragon.getPosZ()).normalize();
            float f = 5.0f;
            double d2 = this.dragon.dragonPartHead.getPosX() + vector3d.x * 5.0 / 2.0;
            double d3 = this.dragon.dragonPartHead.getPosZ() + vector3d.z * 5.0 / 2.0;
            double d4 = d = this.dragon.dragonPartHead.getPosYHeight(0.5);
            BlockPos.Mutable mutable = new BlockPos.Mutable(d2, d, d3);
            while (this.dragon.world.isAirBlock(mutable)) {
                if ((d4 -= 1.0) < 0.0) {
                    d4 = d;
                    break;
                }
                mutable.setPos(d2, d4, d3);
            }
            d4 = MathHelper.floor(d4) + 1;
            this.areaEffectCloud = new AreaEffectCloudEntity(this.dragon.world, d2, d4, d3);
            this.areaEffectCloud.setOwner(this.dragon);
            this.areaEffectCloud.setRadius(5.0f);
            this.areaEffectCloud.setDuration(200);
            this.areaEffectCloud.setParticleData(ParticleTypes.DRAGON_BREATH);
            this.areaEffectCloud.addEffect(new EffectInstance(Effects.INSTANT_DAMAGE));
            this.dragon.world.addEntity(this.areaEffectCloud);
        }
    }

    @Override
    public void initPhase() {
        this.flameTicks = 0;
        ++this.flameCount;
    }

    @Override
    public void removeAreaEffect() {
        if (this.areaEffectCloud != null) {
            this.areaEffectCloud.remove();
            this.areaEffectCloud = null;
        }
    }

    public PhaseType<FlamingSittingPhase> getType() {
        return PhaseType.SITTING_FLAMING;
    }

    public void resetFlameCount() {
        this.flameCount = 0;
    }
}


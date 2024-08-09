/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.particle;

import java.util.Objects;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.MetaParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;

public class HugeExplosionParticle
extends MetaParticle {
    private int timeSinceStart;
    private final int maximumTime = 8;

    private HugeExplosionParticle(ClientWorld clientWorld, double d, double d2, double d3) {
        super(clientWorld, d, d2, d3, 0.0, 0.0, 0.0);
    }

    @Override
    public void tick() {
        for (int i = 0; i < 6; ++i) {
            double d = this.posX + (this.rand.nextDouble() - this.rand.nextDouble()) * 4.0;
            double d2 = this.posY + (this.rand.nextDouble() - this.rand.nextDouble()) * 4.0;
            double d3 = this.posZ + (this.rand.nextDouble() - this.rand.nextDouble()) * 4.0;
            float f = this.timeSinceStart;
            Objects.requireNonNull(this);
            this.world.addParticle(ParticleTypes.EXPLOSION, d, d2, d3, f / 8.0f, 0.0, 0.0);
        }
        ++this.timeSinceStart;
        if (this.timeSinceStart == this.maximumTime) {
            this.setExpired();
        }
    }

    public static class Factory
    implements IParticleFactory<BasicParticleType> {
        @Override
        public Particle makeParticle(BasicParticleType basicParticleType, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            return new HugeExplosionParticle(clientWorld, d, d2, d3);
        }

        @Override
        public Particle makeParticle(IParticleData iParticleData, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            return this.makeParticle((BasicParticleType)iParticleData, clientWorld, d, d2, d3, d4, d5, d6);
        }
    }
}


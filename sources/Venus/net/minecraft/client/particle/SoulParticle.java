/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.particle;

import net.minecraft.client.particle.DeceleratingParticle;
import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.IParticleData;

public class SoulParticle
extends DeceleratingParticle {
    private final IAnimatedSprite spriteWithAge;

    private SoulParticle(ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6, IAnimatedSprite iAnimatedSprite) {
        super(clientWorld, d, d2, d3, d4, d5, d6);
        this.spriteWithAge = iAnimatedSprite;
        this.multiplyParticleScaleBy(1.5f);
        this.selectSpriteWithAge(iAnimatedSprite);
    }

    @Override
    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.isExpired) {
            this.selectSpriteWithAge(this.spriteWithAge);
        }
    }

    public static class Factory
    implements IParticleFactory<BasicParticleType> {
        private final IAnimatedSprite spriteSet;

        public Factory(IAnimatedSprite iAnimatedSprite) {
            this.spriteSet = iAnimatedSprite;
        }

        @Override
        public Particle makeParticle(BasicParticleType basicParticleType, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            SoulParticle soulParticle = new SoulParticle(clientWorld, d, d2, d3, d4, d5, d6, this.spriteSet);
            soulParticle.setAlphaF(1.0f);
            return soulParticle;
        }

        @Override
        public Particle makeParticle(IParticleData iParticleData, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            return this.makeParticle((BasicParticleType)iParticleData, clientWorld, d, d2, d3, d4, d5, d6);
        }
    }
}


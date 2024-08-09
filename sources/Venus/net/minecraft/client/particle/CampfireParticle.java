/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.particle;

import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SpriteTexturedParticle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.IParticleData;

public class CampfireParticle
extends SpriteTexturedParticle {
    private CampfireParticle(ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6, boolean bl) {
        super(clientWorld, d, d2, d3);
        this.multiplyParticleScaleBy(3.0f);
        this.setSize(0.25f, 0.25f);
        this.maxAge = bl ? this.rand.nextInt(50) + 280 : this.rand.nextInt(50) + 80;
        this.particleGravity = 3.0E-6f;
        this.motionX = d4;
        this.motionY = d5 + (double)(this.rand.nextFloat() / 500.0f);
        this.motionZ = d6;
    }

    @Override
    public void tick() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (this.age++ < this.maxAge && !(this.particleAlpha <= 0.0f)) {
            this.motionX += (double)(this.rand.nextFloat() / 5000.0f * (float)(this.rand.nextBoolean() ? 1 : -1));
            this.motionZ += (double)(this.rand.nextFloat() / 5000.0f * (float)(this.rand.nextBoolean() ? 1 : -1));
            this.motionY -= (double)this.particleGravity;
            this.move(this.motionX, this.motionY, this.motionZ);
            if (this.age >= this.maxAge - 60 && this.particleAlpha > 0.01f) {
                this.particleAlpha -= 0.015f;
            }
        } else {
            this.setExpired();
        }
    }

    @Override
    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public static class SignalSmokeFactory
    implements IParticleFactory<BasicParticleType> {
        private final IAnimatedSprite spriteSet;

        public SignalSmokeFactory(IAnimatedSprite iAnimatedSprite) {
            this.spriteSet = iAnimatedSprite;
        }

        @Override
        public Particle makeParticle(BasicParticleType basicParticleType, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            CampfireParticle campfireParticle = new CampfireParticle(clientWorld, d, d2, d3, d4, d5, d6, true);
            campfireParticle.setAlphaF(0.95f);
            campfireParticle.selectSpriteRandomly(this.spriteSet);
            return campfireParticle;
        }

        @Override
        public Particle makeParticle(IParticleData iParticleData, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            return this.makeParticle((BasicParticleType)iParticleData, clientWorld, d, d2, d3, d4, d5, d6);
        }
    }

    public static class CozySmokeFactory
    implements IParticleFactory<BasicParticleType> {
        private final IAnimatedSprite spriteSet;

        public CozySmokeFactory(IAnimatedSprite iAnimatedSprite) {
            this.spriteSet = iAnimatedSprite;
        }

        @Override
        public Particle makeParticle(BasicParticleType basicParticleType, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            CampfireParticle campfireParticle = new CampfireParticle(clientWorld, d, d2, d3, d4, d5, d6, false);
            campfireParticle.setAlphaF(0.9f);
            campfireParticle.selectSpriteRandomly(this.spriteSet);
            return campfireParticle;
        }

        @Override
        public Particle makeParticle(IParticleData iParticleData, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            return this.makeParticle((BasicParticleType)iParticleData, clientWorld, d, d2, d3, d4, d5, d6);
        }
    }
}


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

public class LargeExplosionParticle
extends SpriteTexturedParticle {
    private final IAnimatedSprite spriteWithAge;

    private LargeExplosionParticle(ClientWorld clientWorld, double d, double d2, double d3, double d4, IAnimatedSprite iAnimatedSprite) {
        super(clientWorld, d, d2, d3, 0.0, 0.0, 0.0);
        float f;
        this.maxAge = 6 + this.rand.nextInt(4);
        this.particleRed = f = this.rand.nextFloat() * 0.6f + 0.4f;
        this.particleGreen = f;
        this.particleBlue = f;
        this.particleScale = 2.0f * (1.0f - (float)d4 * 0.5f);
        this.spriteWithAge = iAnimatedSprite;
        this.selectSpriteWithAge(iAnimatedSprite);
    }

    @Override
    public int getBrightnessForRender(float f) {
        return 1;
    }

    @Override
    public void tick() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (this.age++ >= this.maxAge) {
            this.setExpired();
        } else {
            this.selectSpriteWithAge(this.spriteWithAge);
        }
    }

    @Override
    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_LIT;
    }

    public static class Factory
    implements IParticleFactory<BasicParticleType> {
        private final IAnimatedSprite spriteSet;

        public Factory(IAnimatedSprite iAnimatedSprite) {
            this.spriteSet = iAnimatedSprite;
        }

        @Override
        public Particle makeParticle(BasicParticleType basicParticleType, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            return new LargeExplosionParticle(clientWorld, d, d2, d3, d4, this.spriteSet);
        }

        @Override
        public Particle makeParticle(IParticleData iParticleData, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            return this.makeParticle((BasicParticleType)iParticleData, clientWorld, d, d2, d3, d4, d5, d6);
        }
    }
}


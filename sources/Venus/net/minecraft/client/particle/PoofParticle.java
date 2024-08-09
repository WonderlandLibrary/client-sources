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

public class PoofParticle
extends SpriteTexturedParticle {
    private final IAnimatedSprite spriteWithAge;

    protected PoofParticle(ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6, IAnimatedSprite iAnimatedSprite) {
        super(clientWorld, d, d2, d3);
        float f;
        this.spriteWithAge = iAnimatedSprite;
        this.motionX = d4 + (Math.random() * 2.0 - 1.0) * (double)0.05f;
        this.motionY = d5 + (Math.random() * 2.0 - 1.0) * (double)0.05f;
        this.motionZ = d6 + (Math.random() * 2.0 - 1.0) * (double)0.05f;
        this.particleRed = f = this.rand.nextFloat() * 0.3f + 0.7f;
        this.particleGreen = f;
        this.particleBlue = f;
        this.particleScale = 0.1f * (this.rand.nextFloat() * this.rand.nextFloat() * 6.0f + 1.0f);
        this.maxAge = (int)(16.0 / ((double)this.rand.nextFloat() * 0.8 + 0.2)) + 2;
        this.selectSpriteWithAge(iAnimatedSprite);
    }

    @Override
    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_OPAQUE;
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
            this.motionY += 0.004;
            this.move(this.motionX, this.motionY, this.motionZ);
            this.motionX *= (double)0.9f;
            this.motionY *= (double)0.9f;
            this.motionZ *= (double)0.9f;
            if (this.onGround) {
                this.motionX *= (double)0.7f;
                this.motionZ *= (double)0.7f;
            }
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
            return new PoofParticle(clientWorld, d, d2, d3, d4, d5, d6, this.spriteSet);
        }

        @Override
        public Particle makeParticle(IParticleData iParticleData, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            return this.makeParticle((BasicParticleType)iParticleData, clientWorld, d, d2, d3, d4, d5, d6);
        }
    }
}


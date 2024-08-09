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
import net.minecraft.particles.ParticleTypes;

public class LavaParticle
extends SpriteTexturedParticle {
    private LavaParticle(ClientWorld clientWorld, double d, double d2, double d3) {
        super(clientWorld, d, d2, d3, 0.0, 0.0, 0.0);
        this.motionX *= (double)0.8f;
        this.motionY *= (double)0.8f;
        this.motionZ *= (double)0.8f;
        this.motionY = this.rand.nextFloat() * 0.4f + 0.05f;
        this.particleScale *= this.rand.nextFloat() * 2.0f + 0.2f;
        this.maxAge = (int)(16.0 / (Math.random() * 0.8 + 0.2));
    }

    @Override
    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public int getBrightnessForRender(float f) {
        int n = super.getBrightnessForRender(f);
        int n2 = 240;
        int n3 = n >> 16 & 0xFF;
        return 0xF0 | n3 << 16;
    }

    @Override
    public float getScale(float f) {
        float f2 = ((float)this.age + f) / (float)this.maxAge;
        return this.particleScale * (1.0f - f2 * f2);
    }

    @Override
    public void tick() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        float f = (float)this.age / (float)this.maxAge;
        if (this.rand.nextFloat() > f) {
            this.world.addParticle(ParticleTypes.SMOKE, this.posX, this.posY, this.posZ, this.motionX, this.motionY, this.motionZ);
        }
        if (this.age++ >= this.maxAge) {
            this.setExpired();
        } else {
            this.motionY -= 0.03;
            this.move(this.motionX, this.motionY, this.motionZ);
            this.motionX *= (double)0.999f;
            this.motionY *= (double)0.999f;
            this.motionZ *= (double)0.999f;
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
            LavaParticle lavaParticle = new LavaParticle(clientWorld, d, d2, d3);
            lavaParticle.selectSpriteRandomly(this.spriteSet);
            return lavaParticle;
        }

        @Override
        public Particle makeParticle(IParticleData iParticleData, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            return this.makeParticle((BasicParticleType)iParticleData, clientWorld, d, d2, d3, d4, d5, d6);
        }
    }
}


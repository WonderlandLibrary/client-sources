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

public class PortalParticle
extends SpriteTexturedParticle {
    private final double portalPosX;
    private final double portalPosY;
    private final double portalPosZ;

    protected PortalParticle(ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
        super(clientWorld, d, d2, d3);
        this.motionX = d4;
        this.motionY = d5;
        this.motionZ = d6;
        this.posX = d;
        this.posY = d2;
        this.posZ = d3;
        this.portalPosX = this.posX;
        this.portalPosY = this.posY;
        this.portalPosZ = this.posZ;
        this.particleScale = 0.1f * (this.rand.nextFloat() * 0.2f + 0.5f);
        float f = this.rand.nextFloat() * 0.6f + 0.4f;
        this.particleRed = f * 0.9f;
        this.particleGreen = f * 0.3f;
        this.particleBlue = f;
        this.maxAge = (int)(Math.random() * 10.0) + 40;
    }

    @Override
    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public void move(double d, double d2, double d3) {
        this.setBoundingBox(this.getBoundingBox().offset(d, d2, d3));
        this.resetPositionToBB();
    }

    @Override
    public float getScale(float f) {
        float f2 = ((float)this.age + f) / (float)this.maxAge;
        f2 = 1.0f - f2;
        f2 *= f2;
        f2 = 1.0f - f2;
        return this.particleScale * f2;
    }

    @Override
    public int getBrightnessForRender(float f) {
        int n = super.getBrightnessForRender(f);
        float f2 = (float)this.age / (float)this.maxAge;
        f2 *= f2;
        f2 *= f2;
        int n2 = n & 0xFF;
        int n3 = n >> 16 & 0xFF;
        if ((n3 += (int)(f2 * 15.0f * 16.0f)) > 240) {
            n3 = 240;
        }
        return n2 | n3 << 16;
    }

    @Override
    public void tick() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (this.age++ >= this.maxAge) {
            this.setExpired();
        } else {
            float f = (float)this.age / (float)this.maxAge;
            float f2 = -f + f * f * 2.0f;
            float f3 = 1.0f - f2;
            this.posX = this.portalPosX + this.motionX * (double)f3;
            this.posY = this.portalPosY + this.motionY * (double)f3 + (double)(1.0f - f);
            this.posZ = this.portalPosZ + this.motionZ * (double)f3;
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
            PortalParticle portalParticle = new PortalParticle(clientWorld, d, d2, d3, d4, d5, d6);
            portalParticle.selectSpriteRandomly(this.spriteSet);
            return portalParticle;
        }

        @Override
        public Particle makeParticle(IParticleData iParticleData, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            return this.makeParticle((BasicParticleType)iParticleData, clientWorld, d, d2, d3, d4, d5, d6);
        }
    }
}


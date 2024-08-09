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

public class WaterWakeParticle
extends SpriteTexturedParticle {
    private final IAnimatedSprite spriteWithAge;

    private WaterWakeParticle(ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6, IAnimatedSprite iAnimatedSprite) {
        super(clientWorld, d, d2, d3, 0.0, 0.0, 0.0);
        this.spriteWithAge = iAnimatedSprite;
        this.motionX *= (double)0.3f;
        this.motionY = Math.random() * (double)0.2f + (double)0.1f;
        this.motionZ *= (double)0.3f;
        this.setSize(0.01f, 0.01f);
        this.maxAge = (int)(8.0 / (Math.random() * 0.8 + 0.2));
        this.selectSpriteWithAge(iAnimatedSprite);
        this.particleGravity = 0.0f;
        this.motionX = d4;
        this.motionY = d5;
        this.motionZ = d6;
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
        int n = 60 - this.maxAge;
        if (this.maxAge-- <= 0) {
            this.setExpired();
        } else {
            this.motionY -= (double)this.particleGravity;
            this.move(this.motionX, this.motionY, this.motionZ);
            this.motionX *= (double)0.98f;
            this.motionY *= (double)0.98f;
            this.motionZ *= (double)0.98f;
            float f = (float)n * 0.001f;
            this.setSize(f, f);
            this.setSprite(this.spriteWithAge.get(n % 4, 4));
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
            return new WaterWakeParticle(clientWorld, d, d2, d3, d4, d5, d6, this.spriteSet);
        }

        @Override
        public Particle makeParticle(IParticleData iParticleData, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            return this.makeParticle((BasicParticleType)iParticleData, clientWorld, d, d2, d3, d4, d5, d6);
        }
    }
}


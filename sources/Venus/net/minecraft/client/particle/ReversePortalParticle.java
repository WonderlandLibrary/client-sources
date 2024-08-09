/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.particle;

import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.PortalParticle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.IParticleData;

public class ReversePortalParticle
extends PortalParticle {
    private ReversePortalParticle(ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
        super(clientWorld, d, d2, d3, d4, d5, d6);
        this.particleScale = (float)((double)this.particleScale * 1.5);
        this.maxAge = (int)(Math.random() * 2.0) + 60;
    }

    @Override
    public float getScale(float f) {
        float f2 = 1.0f - ((float)this.age + f) / ((float)this.maxAge * 1.5f);
        return this.particleScale * f2;
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
            this.posX += this.motionX * (double)f;
            this.posY += this.motionY * (double)f;
            this.posZ += this.motionZ * (double)f;
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
            ReversePortalParticle reversePortalParticle = new ReversePortalParticle(clientWorld, d, d2, d3, d4, d5, d6);
            reversePortalParticle.selectSpriteRandomly(this.spriteSet);
            return reversePortalParticle;
        }

        @Override
        public Particle makeParticle(IParticleData iParticleData, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            return this.makeParticle((BasicParticleType)iParticleData, clientWorld, d, d2, d3, d4, d5, d6);
        }
    }
}


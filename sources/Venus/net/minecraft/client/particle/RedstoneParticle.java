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
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.util.math.MathHelper;

public class RedstoneParticle
extends SpriteTexturedParticle {
    private final IAnimatedSprite spriteWithAge;

    private RedstoneParticle(ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6, RedstoneParticleData redstoneParticleData, IAnimatedSprite iAnimatedSprite) {
        super(clientWorld, d, d2, d3, d4, d5, d6);
        this.spriteWithAge = iAnimatedSprite;
        this.motionX *= (double)0.1f;
        this.motionY *= (double)0.1f;
        this.motionZ *= (double)0.1f;
        float f = (float)Math.random() * 0.4f + 0.6f;
        this.particleRed = ((float)(Math.random() * (double)0.2f) + 0.8f) * redstoneParticleData.getRed() * f;
        this.particleGreen = ((float)(Math.random() * (double)0.2f) + 0.8f) * redstoneParticleData.getGreen() * f;
        this.particleBlue = ((float)(Math.random() * (double)0.2f) + 0.8f) * redstoneParticleData.getBlue() * f;
        this.particleScale *= 0.75f * redstoneParticleData.getAlpha();
        int n = (int)(8.0 / (Math.random() * 0.8 + 0.2));
        this.maxAge = (int)Math.max((float)n * redstoneParticleData.getAlpha(), 1.0f);
        this.selectSpriteWithAge(iAnimatedSprite);
    }

    @Override
    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public float getScale(float f) {
        return this.particleScale * MathHelper.clamp(((float)this.age + f) / (float)this.maxAge * 32.0f, 0.0f, 1.0f);
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
            this.move(this.motionX, this.motionY, this.motionZ);
            if (this.posY == this.prevPosY) {
                this.motionX *= 1.1;
                this.motionZ *= 1.1;
            }
            this.motionX *= (double)0.96f;
            this.motionY *= (double)0.96f;
            this.motionZ *= (double)0.96f;
            if (this.onGround) {
                this.motionX *= (double)0.7f;
                this.motionZ *= (double)0.7f;
            }
        }
    }

    public static class Factory
    implements IParticleFactory<RedstoneParticleData> {
        private final IAnimatedSprite spriteSet;

        public Factory(IAnimatedSprite iAnimatedSprite) {
            this.spriteSet = iAnimatedSprite;
        }

        @Override
        public Particle makeParticle(RedstoneParticleData redstoneParticleData, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            return new RedstoneParticle(clientWorld, d, d2, d3, d4, d5, d6, redstoneParticleData, this.spriteSet);
        }

        @Override
        public Particle makeParticle(IParticleData iParticleData, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            return this.makeParticle((RedstoneParticleData)iParticleData, clientWorld, d, d2, d3, d4, d5, d6);
        }
    }
}


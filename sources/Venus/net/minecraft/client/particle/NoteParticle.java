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
import net.minecraft.util.math.MathHelper;

public class NoteParticle
extends SpriteTexturedParticle {
    private NoteParticle(ClientWorld clientWorld, double d, double d2, double d3, double d4) {
        super(clientWorld, d, d2, d3, 0.0, 0.0, 0.0);
        this.motionX *= (double)0.01f;
        this.motionY *= (double)0.01f;
        this.motionZ *= (double)0.01f;
        this.motionY += 0.2;
        this.particleRed = Math.max(0.0f, MathHelper.sin(((float)d4 + 0.0f) * ((float)Math.PI * 2)) * 0.65f + 0.35f);
        this.particleGreen = Math.max(0.0f, MathHelper.sin(((float)d4 + 0.33333334f) * ((float)Math.PI * 2)) * 0.65f + 0.35f);
        this.particleBlue = Math.max(0.0f, MathHelper.sin(((float)d4 + 0.6666667f) * ((float)Math.PI * 2)) * 0.65f + 0.35f);
        this.particleScale *= 1.5f;
        this.maxAge = 6;
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
            this.move(this.motionX, this.motionY, this.motionZ);
            if (this.posY == this.prevPosY) {
                this.motionX *= 1.1;
                this.motionZ *= 1.1;
            }
            this.motionX *= (double)0.66f;
            this.motionY *= (double)0.66f;
            this.motionZ *= (double)0.66f;
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
            NoteParticle noteParticle = new NoteParticle(clientWorld, d, d2, d3, d4);
            noteParticle.selectSpriteRandomly(this.spriteSet);
            return noteParticle;
        }

        @Override
        public Particle makeParticle(IParticleData iParticleData, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            return this.makeParticle((BasicParticleType)iParticleData, clientWorld, d, d2, d3, d4, d5, d6);
        }
    }
}


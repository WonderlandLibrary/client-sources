/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.particle;

import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SimpleAnimatedParticle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.IParticleData;
import net.minecraft.util.math.BlockPos;

public class SquidInkParticle
extends SimpleAnimatedParticle {
    private SquidInkParticle(ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6, IAnimatedSprite iAnimatedSprite) {
        super(clientWorld, d, d2, d3, iAnimatedSprite, 0.0f);
        this.particleScale = 0.5f;
        this.setAlphaF(1.0f);
        this.setColor(0.0f, 0.0f, 0.0f);
        this.maxAge = (int)((double)(this.particleScale * 12.0f) / (Math.random() * (double)0.8f + (double)0.2f));
        this.selectSpriteWithAge(iAnimatedSprite);
        this.canCollide = false;
        this.motionX = d4;
        this.motionY = d5;
        this.motionZ = d6;
        this.setBaseAirFriction(0.0f);
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
            if (this.age > this.maxAge / 2) {
                this.setAlphaF(1.0f - ((float)this.age - (float)(this.maxAge / 2)) / (float)this.maxAge);
            }
            this.move(this.motionX, this.motionY, this.motionZ);
            if (this.world.getBlockState(new BlockPos(this.posX, this.posY, this.posZ)).isAir()) {
                this.motionY -= (double)0.008f;
            }
            this.motionX *= (double)0.92f;
            this.motionY *= (double)0.92f;
            this.motionZ *= (double)0.92f;
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
            return new SquidInkParticle(clientWorld, d, d2, d3, d4, d5, d6, this.spriteSet);
        }

        @Override
        public Particle makeParticle(IParticleData iParticleData, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            return this.makeParticle((BasicParticleType)iParticleData, clientWorld, d, d2, d3, d4, d5, d6);
        }
    }
}


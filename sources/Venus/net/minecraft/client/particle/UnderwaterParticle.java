/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.particle;

import java.util.Random;
import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SpriteTexturedParticle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.IParticleData;

public class UnderwaterParticle
extends SpriteTexturedParticle {
    private UnderwaterParticle(ClientWorld clientWorld, double d, double d2, double d3) {
        super(clientWorld, d, d2 - 0.125, d3);
        this.particleRed = 0.4f;
        this.particleGreen = 0.4f;
        this.particleBlue = 0.7f;
        this.setSize(0.01f, 0.01f);
        this.particleScale *= this.rand.nextFloat() * 0.6f + 0.2f;
        this.maxAge = (int)(16.0 / (Math.random() * 0.8 + 0.2));
        this.canCollide = false;
    }

    private UnderwaterParticle(ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
        super(clientWorld, d, d2 - 0.125, d3, d4, d5, d6);
        this.setSize(0.01f, 0.01f);
        this.particleScale *= this.rand.nextFloat() * 0.6f + 0.6f;
        this.maxAge = (int)(16.0 / (Math.random() * 0.8 + 0.2));
        this.canCollide = false;
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
        if (this.maxAge-- <= 0) {
            this.setExpired();
        } else {
            this.move(this.motionX, this.motionY, this.motionZ);
        }
    }

    public static class WarpedSporeFactory
    implements IParticleFactory<BasicParticleType> {
        private final IAnimatedSprite spriteSet;

        public WarpedSporeFactory(IAnimatedSprite iAnimatedSprite) {
            this.spriteSet = iAnimatedSprite;
        }

        @Override
        public Particle makeParticle(BasicParticleType basicParticleType, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            double d7 = (double)clientWorld.rand.nextFloat() * -1.9 * (double)clientWorld.rand.nextFloat() * 0.1;
            UnderwaterParticle underwaterParticle = new UnderwaterParticle(clientWorld, d, d2, d3, 0.0, d7, 0.0);
            underwaterParticle.selectSpriteRandomly(this.spriteSet);
            underwaterParticle.setColor(0.1f, 0.1f, 0.3f);
            underwaterParticle.setSize(0.001f, 0.001f);
            return underwaterParticle;
        }

        @Override
        public Particle makeParticle(IParticleData iParticleData, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            return this.makeParticle((BasicParticleType)iParticleData, clientWorld, d, d2, d3, d4, d5, d6);
        }
    }

    public static class UnderwaterFactory
    implements IParticleFactory<BasicParticleType> {
        private final IAnimatedSprite spriteSet;

        public UnderwaterFactory(IAnimatedSprite iAnimatedSprite) {
            this.spriteSet = iAnimatedSprite;
        }

        @Override
        public Particle makeParticle(BasicParticleType basicParticleType, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            UnderwaterParticle underwaterParticle = new UnderwaterParticle(clientWorld, d, d2, d3);
            underwaterParticle.selectSpriteRandomly(this.spriteSet);
            return underwaterParticle;
        }

        @Override
        public Particle makeParticle(IParticleData iParticleData, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            return this.makeParticle((BasicParticleType)iParticleData, clientWorld, d, d2, d3, d4, d5, d6);
        }
    }

    public static class CrimsonSporeFactory
    implements IParticleFactory<BasicParticleType> {
        private final IAnimatedSprite spriteSet;

        public CrimsonSporeFactory(IAnimatedSprite iAnimatedSprite) {
            this.spriteSet = iAnimatedSprite;
        }

        @Override
        public Particle makeParticle(BasicParticleType basicParticleType, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            Random random2 = clientWorld.rand;
            double d7 = random2.nextGaussian() * (double)1.0E-6f;
            double d8 = random2.nextGaussian() * (double)1.0E-4f;
            double d9 = random2.nextGaussian() * (double)1.0E-6f;
            UnderwaterParticle underwaterParticle = new UnderwaterParticle(clientWorld, d, d2, d3, d7, d8, d9);
            underwaterParticle.selectSpriteRandomly(this.spriteSet);
            underwaterParticle.setColor(0.9f, 0.4f, 0.5f);
            return underwaterParticle;
        }

        @Override
        public Particle makeParticle(IParticleData iParticleData, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            return this.makeParticle((BasicParticleType)iParticleData, clientWorld, d, d2, d3, d4, d5, d6);
        }
    }
}


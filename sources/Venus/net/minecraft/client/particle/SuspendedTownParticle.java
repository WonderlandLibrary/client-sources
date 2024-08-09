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

public class SuspendedTownParticle
extends SpriteTexturedParticle {
    private SuspendedTownParticle(ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
        super(clientWorld, d, d2, d3, d4, d5, d6);
        float f;
        this.particleRed = f = this.rand.nextFloat() * 0.1f + 0.2f;
        this.particleGreen = f;
        this.particleBlue = f;
        this.setSize(0.02f, 0.02f);
        this.particleScale *= this.rand.nextFloat() * 0.6f + 0.5f;
        this.motionX *= (double)0.02f;
        this.motionY *= (double)0.02f;
        this.motionZ *= (double)0.02f;
        this.maxAge = (int)(20.0 / (Math.random() * 0.8 + 0.2));
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
    public void tick() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (this.maxAge-- <= 0) {
            this.setExpired();
        } else {
            this.move(this.motionX, this.motionY, this.motionZ);
            this.motionX *= 0.99;
            this.motionY *= 0.99;
            this.motionZ *= 0.99;
        }
    }

    public static class HappyVillagerFactory
    implements IParticleFactory<BasicParticleType> {
        private final IAnimatedSprite spriteSet;

        public HappyVillagerFactory(IAnimatedSprite iAnimatedSprite) {
            this.spriteSet = iAnimatedSprite;
        }

        @Override
        public Particle makeParticle(BasicParticleType basicParticleType, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            SuspendedTownParticle suspendedTownParticle = new SuspendedTownParticle(clientWorld, d, d2, d3, d4, d5, d6);
            suspendedTownParticle.selectSpriteRandomly(this.spriteSet);
            suspendedTownParticle.setColor(1.0f, 1.0f, 1.0f);
            return suspendedTownParticle;
        }

        @Override
        public Particle makeParticle(IParticleData iParticleData, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            return this.makeParticle((BasicParticleType)iParticleData, clientWorld, d, d2, d3, d4, d5, d6);
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
            SuspendedTownParticle suspendedTownParticle = new SuspendedTownParticle(clientWorld, d, d2, d3, d4, d5, d6);
            suspendedTownParticle.selectSpriteRandomly(this.spriteSet);
            return suspendedTownParticle;
        }

        @Override
        public Particle makeParticle(IParticleData iParticleData, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            return this.makeParticle((BasicParticleType)iParticleData, clientWorld, d, d2, d3, d4, d5, d6);
        }
    }

    public static class DolphinSpeedFactory
    implements IParticleFactory<BasicParticleType> {
        private final IAnimatedSprite spriteSet;

        public DolphinSpeedFactory(IAnimatedSprite iAnimatedSprite) {
            this.spriteSet = iAnimatedSprite;
        }

        @Override
        public Particle makeParticle(BasicParticleType basicParticleType, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            SuspendedTownParticle suspendedTownParticle = new SuspendedTownParticle(clientWorld, d, d2, d3, d4, d5, d6);
            suspendedTownParticle.setColor(0.3f, 0.5f, 1.0f);
            suspendedTownParticle.selectSpriteRandomly(this.spriteSet);
            suspendedTownParticle.setAlphaF(1.0f - clientWorld.rand.nextFloat() * 0.7f);
            suspendedTownParticle.setMaxAge(suspendedTownParticle.getMaxAge() / 2);
            return suspendedTownParticle;
        }

        @Override
        public Particle makeParticle(IParticleData iParticleData, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            return this.makeParticle((BasicParticleType)iParticleData, clientWorld, d, d2, d3, d4, d5, d6);
        }
    }

    public static class ComposterFactory
    implements IParticleFactory<BasicParticleType> {
        private final IAnimatedSprite spriteSet;

        public ComposterFactory(IAnimatedSprite iAnimatedSprite) {
            this.spriteSet = iAnimatedSprite;
        }

        @Override
        public Particle makeParticle(BasicParticleType basicParticleType, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            SuspendedTownParticle suspendedTownParticle = new SuspendedTownParticle(clientWorld, d, d2, d3, d4, d5, d6);
            suspendedTownParticle.selectSpriteRandomly(this.spriteSet);
            suspendedTownParticle.setColor(1.0f, 1.0f, 1.0f);
            suspendedTownParticle.setMaxAge(3 + clientWorld.getRandom().nextInt(5));
            return suspendedTownParticle;
        }

        @Override
        public Particle makeParticle(IParticleData iParticleData, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            return this.makeParticle((BasicParticleType)iParticleData, clientWorld, d, d2, d3, d4, d5, d6);
        }
    }
}


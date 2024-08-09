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

public class EnchantmentTableParticle
extends SpriteTexturedParticle {
    private final double coordX;
    private final double coordY;
    private final double coordZ;

    private EnchantmentTableParticle(ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
        super(clientWorld, d, d2, d3);
        this.motionX = d4;
        this.motionY = d5;
        this.motionZ = d6;
        this.coordX = d;
        this.coordY = d2;
        this.coordZ = d3;
        this.prevPosX = d + d4;
        this.prevPosY = d2 + d5;
        this.prevPosZ = d3 + d6;
        this.posX = this.prevPosX;
        this.posY = this.prevPosY;
        this.posZ = this.prevPosZ;
        this.particleScale = 0.1f * (this.rand.nextFloat() * 0.5f + 0.2f);
        float f = this.rand.nextFloat() * 0.6f + 0.4f;
        this.particleRed = 0.9f * f;
        this.particleGreen = 0.9f * f;
        this.particleBlue = f;
        this.canCollide = false;
        this.maxAge = (int)(Math.random() * 10.0) + 30;
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
            f = 1.0f - f;
            float f2 = 1.0f - f;
            f2 *= f2;
            f2 *= f2;
            this.posX = this.coordX + this.motionX * (double)f;
            this.posY = this.coordY + this.motionY * (double)f - (double)(f2 * 1.2f);
            this.posZ = this.coordZ + this.motionZ * (double)f;
        }
    }

    public static class NautilusFactory
    implements IParticleFactory<BasicParticleType> {
        private final IAnimatedSprite spriteSet;

        public NautilusFactory(IAnimatedSprite iAnimatedSprite) {
            this.spriteSet = iAnimatedSprite;
        }

        @Override
        public Particle makeParticle(BasicParticleType basicParticleType, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            EnchantmentTableParticle enchantmentTableParticle = new EnchantmentTableParticle(clientWorld, d, d2, d3, d4, d5, d6);
            enchantmentTableParticle.selectSpriteRandomly(this.spriteSet);
            return enchantmentTableParticle;
        }

        @Override
        public Particle makeParticle(IParticleData iParticleData, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            return this.makeParticle((BasicParticleType)iParticleData, clientWorld, d, d2, d3, d4, d5, d6);
        }
    }

    public static class EnchantmentTable
    implements IParticleFactory<BasicParticleType> {
        private final IAnimatedSprite spriteSet;

        public EnchantmentTable(IAnimatedSprite iAnimatedSprite) {
            this.spriteSet = iAnimatedSprite;
        }

        @Override
        public Particle makeParticle(BasicParticleType basicParticleType, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            EnchantmentTableParticle enchantmentTableParticle = new EnchantmentTableParticle(clientWorld, d, d2, d3, d4, d5, d6);
            enchantmentTableParticle.selectSpriteRandomly(this.spriteSet);
            return enchantmentTableParticle;
        }

        @Override
        public Particle makeParticle(IParticleData iParticleData, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            return this.makeParticle((BasicParticleType)iParticleData, clientWorld, d, d2, d3, d4, d5, d6);
        }
    }
}


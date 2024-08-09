/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.particle;

import net.minecraft.client.particle.DeceleratingParticle;
import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.IParticleData;
import net.minecraft.util.math.MathHelper;

public class FlameParticle
extends DeceleratingParticle {
    private FlameParticle(ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
        super(clientWorld, d, d2, d3, d4, d5, d6);
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
        return this.particleScale * (1.0f - f2 * f2 * 0.5f);
    }

    @Override
    public int getBrightnessForRender(float f) {
        float f2 = ((float)this.age + f) / (float)this.maxAge;
        f2 = MathHelper.clamp(f2, 0.0f, 1.0f);
        int n = super.getBrightnessForRender(f);
        int n2 = n & 0xFF;
        int n3 = n >> 16 & 0xFF;
        if ((n2 += (int)(f2 * 15.0f * 16.0f)) > 240) {
            n2 = 240;
        }
        return n2 | n3 << 16;
    }

    public static class Factory
    implements IParticleFactory<BasicParticleType> {
        private final IAnimatedSprite spriteSet;

        public Factory(IAnimatedSprite iAnimatedSprite) {
            this.spriteSet = iAnimatedSprite;
        }

        @Override
        public Particle makeParticle(BasicParticleType basicParticleType, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            FlameParticle flameParticle = new FlameParticle(clientWorld, d, d2, d3, d4, d5, d6);
            flameParticle.selectSpriteRandomly(this.spriteSet);
            return flameParticle;
        }

        @Override
        public Particle makeParticle(IParticleData iParticleData, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            return this.makeParticle((BasicParticleType)iParticleData, clientWorld, d, d2, d3, d4, d5, d6);
        }
    }
}


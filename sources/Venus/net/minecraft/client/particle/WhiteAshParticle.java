/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.particle;

import java.util.Random;
import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.RisingParticle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.IParticleData;

public class WhiteAshParticle
extends RisingParticle {
    protected WhiteAshParticle(ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6, float f, IAnimatedSprite iAnimatedSprite) {
        super(clientWorld, d, d2, d3, 0.1f, -0.1f, 0.1f, d4, d5, d6, f, iAnimatedSprite, 0.0f, 20, -5.0E-4, false);
        this.particleRed = 0.7294118f;
        this.particleGreen = 0.69411767f;
        this.particleBlue = 0.7607843f;
    }

    public static class Factory
    implements IParticleFactory<BasicParticleType> {
        private final IAnimatedSprite spriteSet;

        public Factory(IAnimatedSprite iAnimatedSprite) {
            this.spriteSet = iAnimatedSprite;
        }

        @Override
        public Particle makeParticle(BasicParticleType basicParticleType, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            Random random2 = clientWorld.rand;
            double d7 = (double)random2.nextFloat() * -1.9 * (double)random2.nextFloat() * 0.1;
            double d8 = (double)random2.nextFloat() * -0.5 * (double)random2.nextFloat() * 0.1 * 5.0;
            double d9 = (double)random2.nextFloat() * -1.9 * (double)random2.nextFloat() * 0.1;
            return new WhiteAshParticle(clientWorld, d, d2, d3, d7, d8, d9, 1.0f, this.spriteSet);
        }

        @Override
        public Particle makeParticle(IParticleData iParticleData, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            return this.makeParticle((BasicParticleType)iParticleData, clientWorld, d, d2, d3, d4, d5, d6);
        }
    }
}


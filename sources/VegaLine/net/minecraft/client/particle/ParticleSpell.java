/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.particle;

import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.world.World;

public class ParticleSpell
extends Particle {
    private static final Random RANDOM = new Random();
    private int baseSpellTextureIndex = 128;

    protected ParticleSpell(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double p_i1229_8_, double ySpeed, double p_i1229_12_) {
        super(worldIn, xCoordIn, yCoordIn, zCoordIn, 0.5 - RANDOM.nextDouble(), ySpeed, 0.5 - RANDOM.nextDouble());
        this.motionY *= (double)0.2f;
        if (p_i1229_8_ == 0.0 && p_i1229_12_ == 0.0) {
            this.motionX *= (double)0.1f;
            this.motionZ *= (double)0.1f;
        }
        this.particleScale *= 0.75f;
        this.particleMaxAge = (int)(8.0 / (Math.random() * 0.8 + 0.2));
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.particleAge += Minecraft.getMinecraft().particlesSpeed();
        if (this.particleAge >= (float)this.particleMaxAge) {
            this.setExpired();
        }
        this.setParticleTextureIndex((int)((float)this.baseSpellTextureIndex + (7.0f - this.particleAge * 8.0f / (float)this.particleMaxAge)));
        this.motionY += 0.004;
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        if (this.posY == this.prevPosY) {
            this.motionX *= 1.1;
            this.motionZ *= 1.1;
        }
        this.motionX *= (double)0.96f;
        this.motionY *= (double)0.96f;
        this.motionZ *= (double)0.96f;
        if (this.isCollided) {
            this.motionX *= (double)0.7f;
            this.motionZ *= (double)0.7f;
        }
    }

    public void setBaseSpellTextureIndex(int baseSpellTextureIndexIn) {
        this.baseSpellTextureIndex = baseSpellTextureIndexIn;
    }

    public static class WitchFactory
    implements IParticleFactory {
        @Override
        public Particle createParticle(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int ... p_178902_15_) {
            ParticleSpell particle2 = new ParticleSpell(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
            particle2.setBaseSpellTextureIndex(144);
            float f = worldIn.rand.nextFloat() * 0.5f + 0.35f;
            particle2.setRBGColorF(1.0f * f, 0.0f * f, 1.0f * f);
            return particle2;
        }
    }

    public static class MobFactory
    implements IParticleFactory {
        @Override
        public Particle createParticle(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int ... p_178902_15_) {
            ParticleSpell particle2 = new ParticleSpell(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
            particle2.setRBGColorF((float)xSpeedIn, (float)ySpeedIn, (float)zSpeedIn);
            return particle2;
        }
    }

    public static class InstantFactory
    implements IParticleFactory {
        @Override
        public Particle createParticle(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int ... p_178902_15_) {
            ParticleSpell particle2 = new ParticleSpell(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
            particle2.setBaseSpellTextureIndex(144);
            return particle2;
        }
    }

    public static class Factory
    implements IParticleFactory {
        @Override
        public Particle createParticle(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int ... p_178902_15_) {
            return new ParticleSpell(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
        }
    }

    public static class AmbientMobFactory
    implements IParticleFactory {
        @Override
        public Particle createParticle(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int ... p_178902_15_) {
            ParticleSpell particle2 = new ParticleSpell(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
            particle2.setAlphaF(0.15f);
            particle2.setRBGColorF((float)xSpeedIn, (float)ySpeedIn, (float)zSpeedIn);
            return particle2;
        }
    }
}


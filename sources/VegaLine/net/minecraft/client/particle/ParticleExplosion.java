/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.world.World;

public class ParticleExplosion
extends Particle {
    protected ParticleExplosion(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn) {
        super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
        float f;
        this.motionX = xSpeedIn + (Math.random() * 2.0 - 1.0) * (double)0.05f;
        this.motionY = ySpeedIn + (Math.random() * 2.0 - 1.0) * (double)0.05f;
        this.motionZ = zSpeedIn + (Math.random() * 2.0 - 1.0) * (double)0.05f;
        this.particleRed = f = this.rand.nextFloat() * 0.3f + 0.7f;
        this.particleGreen = f;
        this.particleBlue = f;
        this.particleScale = this.rand.nextFloat() * this.rand.nextFloat() * 6.0f + 1.0f;
        this.particleMaxAge = (int)(16.0 / ((double)this.rand.nextFloat() * 0.8 + 0.2)) + 2;
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
        this.setParticleTextureIndex((int)(7.0f - this.particleAge * 8.0f / (float)this.particleMaxAge));
        this.motionY += 0.004;
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionX *= (double)0.9f;
        this.motionY *= (double)0.9f;
        this.motionZ *= (double)0.9f;
        if (this.isCollided) {
            this.motionX *= (double)0.7f;
            this.motionZ *= (double)0.7f;
        }
    }

    public static class Factory
    implements IParticleFactory {
        @Override
        public Particle createParticle(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int ... p_178902_15_) {
            return new ParticleExplosion(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
        }
    }
}


/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.particle;

import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.SpriteTexturedParticle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.MathHelper;

public class RisingParticle
extends SpriteTexturedParticle {
    private final IAnimatedSprite spriteWithAge;
    private final double yAccel;

    protected RisingParticle(ClientWorld clientWorld, double d, double d2, double d3, float f, float f2, float f3, double d4, double d5, double d6, float f4, IAnimatedSprite iAnimatedSprite, float f5, int n, double d7, boolean bl) {
        super(clientWorld, d, d2, d3, 0.0, 0.0, 0.0);
        float f6;
        this.yAccel = d7;
        this.spriteWithAge = iAnimatedSprite;
        this.motionX *= (double)f;
        this.motionY *= (double)f2;
        this.motionZ *= (double)f3;
        this.motionX += d4;
        this.motionY += d5;
        this.motionZ += d6;
        this.particleRed = f6 = clientWorld.rand.nextFloat() * f5;
        this.particleGreen = f6;
        this.particleBlue = f6;
        this.particleScale *= 0.75f * f4;
        this.maxAge = (int)((double)n / ((double)clientWorld.rand.nextFloat() * 0.8 + 0.2));
        this.maxAge = (int)((float)this.maxAge * f4);
        this.maxAge = Math.max(this.maxAge, 1);
        this.selectSpriteWithAge(iAnimatedSprite);
        this.canCollide = bl;
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
            this.motionY += this.yAccel;
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
}


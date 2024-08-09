/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.particle;

import net.minecraft.client.particle.SpriteTexturedParticle;
import net.minecraft.client.world.ClientWorld;

public abstract class DeceleratingParticle
extends SpriteTexturedParticle {
    protected DeceleratingParticle(ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
        super(clientWorld, d, d2, d3, d4, d5, d6);
        this.motionX = this.motionX * (double)0.01f + d4;
        this.motionY = this.motionY * (double)0.01f + d5;
        this.motionZ = this.motionZ * (double)0.01f + d6;
        this.posX += (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.05f);
        this.posY += (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.05f);
        this.posZ += (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.05f);
        this.maxAge = (int)(8.0 / (Math.random() * 0.8 + 0.2)) + 4;
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


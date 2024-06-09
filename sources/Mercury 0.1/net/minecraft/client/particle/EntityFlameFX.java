/*
 * Decompiled with CFR 0.145.
 */
package net.minecraft.client.particle;

import java.util.Random;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityFlameFX
extends EntityFX {
    private float flameScale;
    private static final String __OBFID = "CL_00000907";

    protected EntityFlameFX(World worldIn, double p_i1209_2_, double p_i1209_4_, double p_i1209_6_, double p_i1209_8_, double p_i1209_10_, double p_i1209_12_) {
        super(worldIn, p_i1209_2_, p_i1209_4_, p_i1209_6_, p_i1209_8_, p_i1209_10_, p_i1209_12_);
        this.motionX = this.motionX * 0.009999999776482582 + p_i1209_8_;
        this.motionY = this.motionY * 0.009999999776482582 + p_i1209_10_;
        this.motionZ = this.motionZ * 0.009999999776482582 + p_i1209_12_;
        double var10000 = p_i1209_2_ + (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.05f);
        var10000 = p_i1209_4_ + (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.05f);
        var10000 = p_i1209_6_ + (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.05f);
        this.flameScale = this.particleScale;
        this.particleBlue = 1.0f;
        this.particleGreen = 1.0f;
        this.particleRed = 1.0f;
        this.particleMaxAge = (int)(8.0 / (Math.random() * 0.8 + 0.2)) + 4;
        this.noClip = true;
        this.setParticleTextureIndex(48);
    }

    @Override
    public void func_180434_a(WorldRenderer p_180434_1_, Entity p_180434_2_, float p_180434_3_, float p_180434_4_, float p_180434_5_, float p_180434_6_, float p_180434_7_, float p_180434_8_) {
        float var9 = ((float)this.particleAge + p_180434_3_) / (float)this.particleMaxAge;
        this.particleScale = this.flameScale * (1.0f - var9 * var9 * 0.5f);
        super.func_180434_a(p_180434_1_, p_180434_2_, p_180434_3_, p_180434_4_, p_180434_5_, p_180434_6_, p_180434_7_, p_180434_8_);
    }

    @Override
    public int getBrightnessForRender(float p_70070_1_) {
        float var2 = ((float)this.particleAge + p_70070_1_) / (float)this.particleMaxAge;
        var2 = MathHelper.clamp_float(var2, 0.0f, 1.0f);
        int var3 = super.getBrightnessForRender(p_70070_1_);
        int var4 = var3 & 255;
        int var5 = var3 >> 16 & 255;
        if ((var4 += (int)(var2 * 15.0f * 16.0f)) > 240) {
            var4 = 240;
        }
        return var4 | var5 << 16;
    }

    @Override
    public float getBrightness(float p_70013_1_) {
        float var2 = ((float)this.particleAge + p_70013_1_) / (float)this.particleMaxAge;
        var2 = MathHelper.clamp_float(var2, 0.0f, 1.0f);
        float var3 = super.getBrightness(p_70013_1_);
        return var3 * var2 + (1.0f - var2);
    }

    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (this.particleAge++ >= this.particleMaxAge) {
            this.setDead();
        }
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.9599999785423279;
        this.motionY *= 0.9599999785423279;
        this.motionZ *= 0.9599999785423279;
        if (this.onGround) {
            this.motionX *= 0.699999988079071;
            this.motionZ *= 0.699999988079071;
        }
    }

    public static class Factory
    implements IParticleFactory {
        private static final String __OBFID = "CL_00002602";

        @Override
        public EntityFX func_178902_a(int p_178902_1_, World worldIn, double p_178902_3_, double p_178902_5_, double p_178902_7_, double p_178902_9_, double p_178902_11_, double p_178902_13_, int ... p_178902_15_) {
            return new EntityFlameFX(worldIn, p_178902_3_, p_178902_5_, p_178902_7_, p_178902_9_, p_178902_11_, p_178902_13_);
        }
    }

}


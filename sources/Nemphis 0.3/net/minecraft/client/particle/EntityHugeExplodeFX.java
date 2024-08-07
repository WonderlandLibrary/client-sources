/*
 * Decompiled with CFR 0_118.
 */
package net.minecraft.client.particle;

import java.util.Random;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class EntityHugeExplodeFX
extends EntityFX {
    private int timeSinceStart;
    private int maximumTime = 8;
    private static final String __OBFID = "CL_00000911";

    protected EntityHugeExplodeFX(World worldIn, double p_i1214_2_, double p_i1214_4_, double p_i1214_6_, double p_i1214_8_, double p_i1214_10_, double p_i1214_12_) {
        super(worldIn, p_i1214_2_, p_i1214_4_, p_i1214_6_, 0.0, 0.0, 0.0);
    }

    @Override
    public void func_180434_a(WorldRenderer p_180434_1_, Entity p_180434_2_, float p_180434_3_, float p_180434_4_, float p_180434_5_, float p_180434_6_, float p_180434_7_, float p_180434_8_) {
    }

    @Override
    public void onUpdate() {
        int var1 = 0;
        while (var1 < 6) {
            double var2 = this.posX + (this.rand.nextDouble() - this.rand.nextDouble()) * 4.0;
            double var4 = this.posY + (this.rand.nextDouble() - this.rand.nextDouble()) * 4.0;
            double var6 = this.posZ + (this.rand.nextDouble() - this.rand.nextDouble()) * 4.0;
            this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, var2, var4, var6, (double)((float)this.timeSinceStart / (float)this.maximumTime), 0.0, 0.0, new int[0]);
            ++var1;
        }
        ++this.timeSinceStart;
        if (this.timeSinceStart == this.maximumTime) {
            this.setDead();
        }
    }

    @Override
    public int getFXLayer() {
        return 1;
    }

    public static class Factory
    implements IParticleFactory {
        private static final String __OBFID = "CL_00002597";

        @Override
        public /* varargs */ EntityFX func_178902_a(int p_178902_1_, World worldIn, double p_178902_3_, double p_178902_5_, double p_178902_7_, double p_178902_9_, double p_178902_11_, double p_178902_13_, int ... p_178902_15_) {
            return new EntityHugeExplodeFX(worldIn, p_178902_3_, p_178902_5_, p_178902_7_, p_178902_9_, p_178902_11_, p_178902_13_);
        }
    }

}


/*
 * Decompiled with CFR 0.143.
 */
package net.minecraft.client.particle;

import java.util.Random;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class EntityParticleEmitter
extends EntityFX {
    private Entity field_174851_a;
    private int field_174852_ax;
    private int field_174850_ay;
    private EnumParticleTypes field_174849_az;
    private static final String __OBFID = "CL_00002574";

    public EntityParticleEmitter(World worldIn, Entity p_i46279_2_, EnumParticleTypes p_i46279_3_) {
        super(worldIn, p_i46279_2_.posX, p_i46279_2_.getEntityBoundingBox().minY + (double)(p_i46279_2_.height / 2.0f), p_i46279_2_.posZ, p_i46279_2_.motionX, p_i46279_2_.motionY, p_i46279_2_.motionZ);
        this.field_174851_a = p_i46279_2_;
        this.field_174850_ay = 3;
        this.field_174849_az = p_i46279_3_;
        this.onUpdate();
    }

    @Override
    public void func_180434_a(WorldRenderer p_180434_1_, Entity p_180434_2_, float p_180434_3_, float p_180434_4_, float p_180434_5_, float p_180434_6_, float p_180434_7_, float p_180434_8_) {
    }

    @Override
    public void onUpdate() {
        for (int var1 = 0; var1 < 16; ++var1) {
            double var6;
            double var4;
            double var2 = this.rand.nextFloat() * 2.0f - 1.0f;
            if (!(var2 * var2 + (var4 = (double)(this.rand.nextFloat() * 2.0f - 1.0f)) * var4 + (var6 = (double)(this.rand.nextFloat() * 2.0f - 1.0f)) * var6 <= 1.0)) continue;
            double var8 = this.field_174851_a.posX + var2 * (double)this.field_174851_a.width / 4.0;
            double var10 = this.field_174851_a.getEntityBoundingBox().minY + (double)(this.field_174851_a.height / 2.0f) + var4 * (double)this.field_174851_a.height / 4.0;
            double var12 = this.field_174851_a.posZ + var6 * (double)this.field_174851_a.width / 4.0;
            this.worldObj.spawnParticle(this.field_174849_az, false, var8, var10, var12, var2, var4 + 0.2, var6, new int[0]);
        }
        ++this.field_174852_ax;
        if (this.field_174852_ax >= this.field_174850_ay) {
            this.setDead();
        }
    }

    @Override
    public int getFXLayer() {
        return 3;
    }
}


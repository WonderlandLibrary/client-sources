/*
 * Decompiled with CFR 0.145.
 */
package net.minecraft.entity.item;

import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityEnderEye
extends Entity {
    private double targetX;
    private double targetY;
    private double targetZ;
    private int despawnTimer;
    private boolean shatterOrDrop;
    private static final String __OBFID = "CL_00001716";

    public EntityEnderEye(World worldIn) {
        super(worldIn);
        this.setSize(0.25f, 0.25f);
    }

    @Override
    protected void entityInit() {
    }

    @Override
    public boolean isInRangeToRenderDist(double distance) {
        double var3 = this.getEntityBoundingBox().getAverageEdgeLength() * 4.0;
        return distance < (var3 *= 64.0) * var3;
    }

    public EntityEnderEye(World worldIn, double p_i1758_2_, double p_i1758_4_, double p_i1758_6_) {
        super(worldIn);
        this.despawnTimer = 0;
        this.setSize(0.25f, 0.25f);
        this.setPosition(p_i1758_2_, p_i1758_4_, p_i1758_6_);
    }

    public void func_180465_a(BlockPos p_180465_1_) {
        double var2 = p_180465_1_.getX();
        int var4 = p_180465_1_.getY();
        double var7 = var2 - this.posX;
        double var5 = p_180465_1_.getZ();
        double var9 = var5 - this.posZ;
        float var11 = MathHelper.sqrt_double(var7 * var7 + var9 * var9);
        if (var11 > 12.0f) {
            this.targetX = this.posX + var7 / (double)var11 * 12.0;
            this.targetZ = this.posZ + var9 / (double)var11 * 12.0;
            this.targetY = this.posY + 8.0;
        } else {
            this.targetX = var2;
            this.targetY = var4;
            this.targetZ = var5;
        }
        this.despawnTimer = 0;
        this.shatterOrDrop = this.rand.nextInt(5) > 0;
    }

    @Override
    public void setVelocity(double x2, double y2, double z2) {
        this.motionX = x2;
        this.motionY = y2;
        this.motionZ = z2;
        if (this.prevRotationPitch == 0.0f && this.prevRotationYaw == 0.0f) {
            float var7 = MathHelper.sqrt_double(x2 * x2 + z2 * z2);
            this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(x2, z2) * 180.0 / 3.141592653589793);
            this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(y2, var7) * 180.0 / 3.141592653589793);
        }
    }

    @Override
    public void onUpdate() {
        this.lastTickPosX = this.posX;
        this.lastTickPosY = this.posY;
        this.lastTickPosZ = this.posZ;
        super.onUpdate();
        this.posX += this.motionX;
        this.posY += this.motionY;
        this.posZ += this.motionZ;
        float var1 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
        this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0 / 3.141592653589793);
        this.rotationPitch = (float)(Math.atan2(this.motionY, var1) * 180.0 / 3.141592653589793);
        while (this.rotationPitch - this.prevRotationPitch < -180.0f) {
            this.prevRotationPitch -= 360.0f;
        }
        while (this.rotationPitch - this.prevRotationPitch >= 180.0f) {
            this.prevRotationPitch += 360.0f;
        }
        while (this.rotationYaw - this.prevRotationYaw < -180.0f) {
            this.prevRotationYaw -= 360.0f;
        }
        while (this.rotationYaw - this.prevRotationYaw >= 180.0f) {
            this.prevRotationYaw += 360.0f;
        }
        this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2f;
        this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2f;
        if (!this.worldObj.isRemote) {
            double var2 = this.targetX - this.posX;
            double var4 = this.targetZ - this.posZ;
            float var6 = (float)Math.sqrt(var2 * var2 + var4 * var4);
            float var7 = (float)Math.atan2(var4, var2);
            double var8 = (double)var1 + (double)(var6 - var1) * 0.0025;
            if (var6 < 1.0f) {
                var8 *= 0.8;
                this.motionY *= 0.8;
            }
            this.motionX = Math.cos(var7) * var8;
            this.motionZ = Math.sin(var7) * var8;
            this.motionY = this.posY < this.targetY ? (this.motionY += (1.0 - this.motionY) * 0.014999999664723873) : (this.motionY += (-1.0 - this.motionY) * 0.014999999664723873);
        }
        float var10 = 0.25f;
        if (this.isInWater()) {
            for (int var3 = 0; var3 < 4; ++var3) {
                this.worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX - this.motionX * (double)var10, this.posY - this.motionY * (double)var10, this.posZ - this.motionZ * (double)var10, this.motionX, this.motionY, this.motionZ, new int[0]);
            }
        } else {
            this.worldObj.spawnParticle(EnumParticleTypes.PORTAL, this.posX - this.motionX * (double)var10 + this.rand.nextDouble() * 0.6 - 0.3, this.posY - this.motionY * (double)var10 - 0.5, this.posZ - this.motionZ * (double)var10 + this.rand.nextDouble() * 0.6 - 0.3, this.motionX, this.motionY, this.motionZ, new int[0]);
        }
        if (!this.worldObj.isRemote) {
            this.setPosition(this.posX, this.posY, this.posZ);
            ++this.despawnTimer;
            if (this.despawnTimer > 80 && !this.worldObj.isRemote) {
                this.setDead();
                if (this.shatterOrDrop) {
                    this.worldObj.spawnEntityInWorld(new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, new ItemStack(Items.ender_eye)));
                } else {
                    this.worldObj.playAuxSFX(2003, new BlockPos(this), 0);
                }
            }
        }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound tagCompound) {
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound tagCompund) {
    }

    @Override
    public float getBrightness(float p_70013_1_) {
        return 1.0f;
    }

    @Override
    public int getBrightnessForRender(float p_70070_1_) {
        return 15728880;
    }

    @Override
    public boolean canAttackWithItem() {
        return false;
    }
}


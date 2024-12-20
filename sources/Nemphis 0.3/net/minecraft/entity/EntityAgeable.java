/*
 * Decompiled with CFR 0_118.
 */
package net.minecraft.entity;

import java.util.Random;
import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public abstract class EntityAgeable
extends EntityCreature {
    protected int field_175504_a;
    protected int field_175502_b;
    protected int field_175503_c;
    private float field_98056_d = -1.0f;
    private float field_98057_e;
    private static final String __OBFID = "CL_00001530";

    public EntityAgeable(World worldIn) {
        super(worldIn);
    }

    public abstract EntityAgeable createChild(EntityAgeable var1);

    @Override
    public boolean interact(EntityPlayer p_70085_1_) {
        ItemStack var2 = p_70085_1_.inventory.getCurrentItem();
        if (var2 != null && var2.getItem() == Items.spawn_egg) {
            EntityAgeable var4;
            Class var3;
            if (!this.worldObj.isRemote && (var3 = EntityList.getClassFromID(var2.getMetadata())) != null && this.getClass() == var3 && (var4 = this.createChild(this)) != null) {
                var4.setGrowingAge(-24000);
                var4.setLocationAndAngles(this.posX, this.posY, this.posZ, 0.0f, 0.0f);
                this.worldObj.spawnEntityInWorld(var4);
                if (var2.hasDisplayName()) {
                    var4.setCustomNameTag(var2.getDisplayName());
                }
                if (!p_70085_1_.capabilities.isCreativeMode) {
                    --var2.stackSize;
                    if (var2.stackSize <= 0) {
                        p_70085_1_.inventory.setInventorySlotContents(p_70085_1_.inventory.currentItem, null);
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(12, Byte.valueOf(0));
    }

    public int getGrowingAge() {
        return this.worldObj.isRemote ? (int)this.dataWatcher.getWatchableObjectByte(12) : this.field_175504_a;
    }

    public void func_175501_a(int p_175501_1_, boolean p_175501_2_) {
        int var3;
        int var4 = var3 = this.getGrowingAge();
        if ((var3 += p_175501_1_ * 20) > 0) {
            var3 = 0;
            if (var4 < 0) {
                this.func_175500_n();
            }
        }
        int var5 = var3 - var4;
        this.setGrowingAge(var3);
        if (p_175501_2_) {
            this.field_175502_b += var5;
            if (this.field_175503_c == 0) {
                this.field_175503_c = 40;
            }
        }
        if (this.getGrowingAge() == 0) {
            this.setGrowingAge(this.field_175502_b);
        }
    }

    public void addGrowth(int p_110195_1_) {
        this.func_175501_a(p_110195_1_, false);
    }

    public void setGrowingAge(int p_70873_1_) {
        this.dataWatcher.updateObject(12, Byte.valueOf((byte)MathHelper.clamp_int(p_70873_1_, -1, 1)));
        this.field_175504_a = p_70873_1_;
        this.setScaleForAge(this.isChild());
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound tagCompound) {
        super.writeEntityToNBT(tagCompound);
        tagCompound.setInteger("Age", this.getGrowingAge());
        tagCompound.setInteger("ForcedAge", this.field_175502_b);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound tagCompund) {
        super.readEntityFromNBT(tagCompund);
        this.setGrowingAge(tagCompund.getInteger("Age"));
        this.field_175502_b = tagCompund.getInteger("ForcedAge");
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (this.worldObj.isRemote) {
            if (this.field_175503_c > 0) {
                if (this.field_175503_c % 4 == 0) {
                    this.worldObj.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, this.posX + (double)(this.rand.nextFloat() * this.width * 2.0f) - (double)this.width, this.posY + 0.5 + (double)(this.rand.nextFloat() * this.height), this.posZ + (double)(this.rand.nextFloat() * this.width * 2.0f) - (double)this.width, 0.0, 0.0, 0.0, new int[0]);
                }
                --this.field_175503_c;
            }
            this.setScaleForAge(this.isChild());
        } else {
            int var1 = this.getGrowingAge();
            if (var1 < 0) {
                this.setGrowingAge(++var1);
                if (var1 == 0) {
                    this.func_175500_n();
                }
            } else if (var1 > 0) {
                this.setGrowingAge(--var1);
            }
        }
    }

    protected void func_175500_n() {
    }

    @Override
    public boolean isChild() {
        if (this.getGrowingAge() < 0) {
            return true;
        }
        return false;
    }

    public void setScaleForAge(boolean p_98054_1_) {
        this.setScale(p_98054_1_ ? 0.5f : 1.0f);
    }

    @Override
    protected final void setSize(float width, float height) {
        boolean var3 = this.field_98056_d > 0.0f;
        this.field_98056_d = width;
        this.field_98057_e = height;
        if (!var3) {
            this.setScale(1.0f);
        }
    }

    protected final void setScale(float p_98055_1_) {
        super.setSize(this.field_98056_d * p_98055_1_, this.field_98057_e * p_98055_1_);
    }
}


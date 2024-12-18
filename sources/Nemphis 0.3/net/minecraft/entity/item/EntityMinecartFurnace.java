/*
 * Decompiled with CFR 0_118.
 */
package net.minecraft.entity.item;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFurnace;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityMinecartFurnace
extends EntityMinecart {
    private int fuel;
    public double pushX;
    public double pushZ;
    private static final String __OBFID = "CL_00001675";

    public EntityMinecartFurnace(World worldIn) {
        super(worldIn);
    }

    public EntityMinecartFurnace(World worldIn, double p_i1719_2_, double p_i1719_4_, double p_i1719_6_) {
        super(worldIn, p_i1719_2_, p_i1719_4_, p_i1719_6_);
    }

    @Override
    public EntityMinecart.EnumMinecartType func_180456_s() {
        return EntityMinecart.EnumMinecartType.FURNACE;
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, new Byte(0));
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (this.fuel > 0) {
            --this.fuel;
        }
        if (this.fuel <= 0) {
            this.pushZ = 0.0;
            this.pushX = 0.0;
        }
        this.setMinecartPowered(this.fuel > 0);
        if (this.isMinecartPowered() && this.rand.nextInt(4) == 0) {
            this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_LARGE, this.posX, this.posY + 0.8, this.posZ, 0.0, 0.0, 0.0, new int[0]);
        }
    }

    @Override
    protected double func_174898_m() {
        return 0.2;
    }

    @Override
    public void killMinecart(DamageSource p_94095_1_) {
        super.killMinecart(p_94095_1_);
        if (!p_94095_1_.isExplosion()) {
            this.entityDropItem(new ItemStack(Blocks.furnace, 1), 0.0f);
        }
    }

    @Override
    protected void func_180460_a(BlockPos p_180460_1_, IBlockState p_180460_2_) {
        super.func_180460_a(p_180460_1_, p_180460_2_);
        double var3 = this.pushX * this.pushX + this.pushZ * this.pushZ;
        if (var3 > 1.0E-4 && this.motionX * this.motionX + this.motionZ * this.motionZ > 0.001) {
            var3 = MathHelper.sqrt_double(var3);
            this.pushX /= var3;
            this.pushZ /= var3;
            if (this.pushX * this.motionX + this.pushZ * this.motionZ < 0.0) {
                this.pushX = 0.0;
                this.pushZ = 0.0;
            } else {
                double var5 = var3 / this.func_174898_m();
                this.pushX *= var5;
                this.pushZ *= var5;
            }
        }
    }

    @Override
    protected void applyDrag() {
        double var1 = this.pushX * this.pushX + this.pushZ * this.pushZ;
        if (var1 > 1.0E-4) {
            var1 = MathHelper.sqrt_double(var1);
            this.pushX /= var1;
            this.pushZ /= var1;
            double var3 = 1.0;
            this.motionX *= 0.800000011920929;
            this.motionY *= 0.0;
            this.motionZ *= 0.800000011920929;
            this.motionX += this.pushX * var3;
            this.motionZ += this.pushZ * var3;
        } else {
            this.motionX *= 0.9800000190734863;
            this.motionY *= 0.0;
            this.motionZ *= 0.9800000190734863;
        }
        super.applyDrag();
    }

    @Override
    public boolean interactFirst(EntityPlayer playerIn) {
        ItemStack var2 = playerIn.inventory.getCurrentItem();
        if (var2 != null && var2.getItem() == Items.coal) {
            if (!playerIn.capabilities.isCreativeMode && --var2.stackSize == 0) {
                playerIn.inventory.setInventorySlotContents(playerIn.inventory.currentItem, null);
            }
            this.fuel += 3600;
        }
        this.pushX = this.posX - playerIn.posX;
        this.pushZ = this.posZ - playerIn.posZ;
        return true;
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound tagCompound) {
        super.writeEntityToNBT(tagCompound);
        tagCompound.setDouble("PushX", this.pushX);
        tagCompound.setDouble("PushZ", this.pushZ);
        tagCompound.setShort("Fuel", (short)this.fuel);
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound tagCompund) {
        super.readEntityFromNBT(tagCompund);
        this.pushX = tagCompund.getDouble("PushX");
        this.pushZ = tagCompund.getDouble("PushZ");
        this.fuel = tagCompund.getShort("Fuel");
    }

    protected boolean isMinecartPowered() {
        if ((this.dataWatcher.getWatchableObjectByte(16) & 1) != 0) {
            return true;
        }
        return false;
    }

    protected void setMinecartPowered(boolean p_94107_1_) {
        if (p_94107_1_) {
            this.dataWatcher.updateObject(16, Byte.valueOf((byte)(this.dataWatcher.getWatchableObjectByte(16) | 1)));
        } else {
            this.dataWatcher.updateObject(16, Byte.valueOf((byte)(this.dataWatcher.getWatchableObjectByte(16) & -2)));
        }
    }

    @Override
    public IBlockState func_180457_u() {
        return (this.isMinecartPowered() ? Blocks.lit_furnace : Blocks.furnace).getDefaultState().withProperty(BlockFurnace.FACING, (Comparable)((Object)EnumFacing.NORTH));
    }
}


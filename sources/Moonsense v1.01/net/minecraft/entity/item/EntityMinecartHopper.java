// 
// Decompiled by Procyon v0.5.36
// 

package net.minecraft.entity.item;

import net.minecraft.inventory.ContainerHopper;
import net.minecraft.inventory.Container;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import java.util.List;
import net.minecraft.command.IEntitySelector;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.IInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.World;
import net.minecraft.util.BlockPos;
import net.minecraft.tileentity.IHopper;

public class EntityMinecartHopper extends EntityMinecartContainer implements IHopper
{
    private boolean isBlocked;
    private int transferTicker;
    private BlockPos field_174900_c;
    private static final String __OBFID = "CL_00001676";
    
    public EntityMinecartHopper(final World worldIn) {
        super(worldIn);
        this.isBlocked = true;
        this.transferTicker = -1;
        this.field_174900_c = BlockPos.ORIGIN;
    }
    
    public EntityMinecartHopper(final World worldIn, final double p_i1721_2_, final double p_i1721_4_, final double p_i1721_6_) {
        super(worldIn, p_i1721_2_, p_i1721_4_, p_i1721_6_);
        this.isBlocked = true;
        this.transferTicker = -1;
        this.field_174900_c = BlockPos.ORIGIN;
    }
    
    @Override
    public EnumMinecartType func_180456_s() {
        return EnumMinecartType.HOPPER;
    }
    
    @Override
    public IBlockState func_180457_u() {
        return Blocks.hopper.getDefaultState();
    }
    
    @Override
    public int getDefaultDisplayTileOffset() {
        return 1;
    }
    
    @Override
    public int getSizeInventory() {
        return 5;
    }
    
    @Override
    public boolean interactFirst(final EntityPlayer playerIn) {
        if (!this.worldObj.isRemote) {
            playerIn.displayGUIChest(this);
        }
        return true;
    }
    
    @Override
    public void onActivatorRailPass(final int p_96095_1_, final int p_96095_2_, final int p_96095_3_, final boolean p_96095_4_) {
        final boolean var5 = !p_96095_4_;
        if (var5 != this.getBlocked()) {
            this.setBlocked(var5);
        }
    }
    
    public boolean getBlocked() {
        return this.isBlocked;
    }
    
    public void setBlocked(final boolean p_96110_1_) {
        this.isBlocked = p_96110_1_;
    }
    
    @Override
    public World getWorld() {
        return this.worldObj;
    }
    
    @Override
    public double getXPos() {
        return this.posX;
    }
    
    @Override
    public double getYPos() {
        return this.posY;
    }
    
    @Override
    public double getZPos() {
        return this.posZ;
    }
    
    @Override
    public void onUpdate() {
        super.onUpdate();
        if (!this.worldObj.isRemote && this.isEntityAlive() && this.getBlocked()) {
            final BlockPos var1 = new BlockPos(this);
            if (var1.equals(this.field_174900_c)) {
                --this.transferTicker;
            }
            else {
                this.setTransferTicker(0);
            }
            if (!this.canTransfer()) {
                this.setTransferTicker(0);
                if (this.func_96112_aD()) {
                    this.setTransferTicker(4);
                    this.markDirty();
                }
            }
        }
    }
    
    public boolean func_96112_aD() {
        if (TileEntityHopper.func_145891_a(this)) {
            return true;
        }
        final List var1 = this.worldObj.func_175647_a(EntityItem.class, this.getEntityBoundingBox().expand(0.25, 0.0, 0.25), IEntitySelector.selectAnything);
        if (var1.size() > 0) {
            TileEntityHopper.func_145898_a(this, var1.get(0));
        }
        return false;
    }
    
    @Override
    public void killMinecart(final DamageSource p_94095_1_) {
        super.killMinecart(p_94095_1_);
        this.dropItemWithOffset(Item.getItemFromBlock(Blocks.hopper), 1, 0.0f);
    }
    
    @Override
    protected void writeEntityToNBT(final NBTTagCompound tagCompound) {
        super.writeEntityToNBT(tagCompound);
        tagCompound.setInteger("TransferCooldown", this.transferTicker);
    }
    
    @Override
    protected void readEntityFromNBT(final NBTTagCompound tagCompund) {
        super.readEntityFromNBT(tagCompund);
        this.transferTicker = tagCompund.getInteger("TransferCooldown");
    }
    
    public void setTransferTicker(final int p_98042_1_) {
        this.transferTicker = p_98042_1_;
    }
    
    public boolean canTransfer() {
        return this.transferTicker > 0;
    }
    
    @Override
    public String getGuiID() {
        return "minecraft:hopper";
    }
    
    @Override
    public Container createContainer(final InventoryPlayer playerInventory, final EntityPlayer playerIn) {
        return new ContainerHopper(playerInventory, this, playerIn);
    }
}

package net.minecraft.src;

import java.util.*;

public class EntityMinecartHopper extends EntityMinecartContainer implements Hopper
{
    private boolean isBlocked;
    private int transferTicker;
    
    public EntityMinecartHopper(final World par1World) {
        super(par1World);
        this.isBlocked = true;
        this.transferTicker = -1;
    }
    
    public EntityMinecartHopper(final World par1World, final double par2, final double par4, final double par6) {
        super(par1World, par2, par4, par6);
        this.isBlocked = true;
        this.transferTicker = -1;
    }
    
    @Override
    public int getMinecartType() {
        return 5;
    }
    
    @Override
    public Block getDefaultDisplayTile() {
        return Block.hopperBlock;
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
    public boolean interact(final EntityPlayer par1EntityPlayer) {
        if (!this.worldObj.isRemote) {
            par1EntityPlayer.displayGUIHopperMinecart(this);
        }
        return true;
    }
    
    @Override
    public void onActivatorRailPass(final int par1, final int par2, final int par3, final boolean par4) {
        final boolean var5 = !par4;
        if (var5 != this.getBlocked()) {
            this.setBlocked(var5);
        }
    }
    
    public boolean getBlocked() {
        return this.isBlocked;
    }
    
    public void setBlocked(final boolean par1) {
        this.isBlocked = par1;
    }
    
    @Override
    public World getWorldObj() {
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
            --this.transferTicker;
            if (!this.canTransfer()) {
                this.setTransferTicker(0);
                if (this.func_96112_aD()) {
                    this.setTransferTicker(4);
                    this.onInventoryChanged();
                }
            }
        }
    }
    
    public boolean func_96112_aD() {
        if (TileEntityHopper.suckItemsIntoHopper(this)) {
            return true;
        }
        final List var1 = this.worldObj.selectEntitiesWithinAABB(EntityItem.class, this.boundingBox.expand(0.25, 0.0, 0.25), IEntitySelector.selectAnything);
        if (var1.size() > 0) {
            TileEntityHopper.func_96114_a(this, var1.get(0));
        }
        return false;
    }
    
    @Override
    public void killMinecart(final DamageSource par1DamageSource) {
        super.killMinecart(par1DamageSource);
        this.dropItemWithOffset(Block.hopperBlock.blockID, 1, 0.0f);
    }
    
    @Override
    protected void writeEntityToNBT(final NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setInteger("TransferCooldown", this.transferTicker);
    }
    
    @Override
    protected void readEntityFromNBT(final NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.transferTicker = par1NBTTagCompound.getInteger("TransferCooldown");
    }
    
    public void setTransferTicker(final int par1) {
        this.transferTicker = par1;
    }
    
    public boolean canTransfer() {
        return this.transferTicker > 0;
    }
}

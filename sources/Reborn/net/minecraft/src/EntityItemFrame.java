package net.minecraft.src;

public class EntityItemFrame extends EntityHanging
{
    private float itemDropChance;
    
    public EntityItemFrame(final World par1World) {
        super(par1World);
        this.itemDropChance = 1.0f;
    }
    
    public EntityItemFrame(final World par1World, final int par2, final int par3, final int par4, final int par5) {
        super(par1World, par2, par3, par4, par5);
        this.itemDropChance = 1.0f;
        this.setDirection(par5);
    }
    
    @Override
    protected void entityInit() {
        this.getDataWatcher().addObjectByDataType(2, 5);
        this.getDataWatcher().addObject(3, (byte)0);
    }
    
    @Override
    public int func_82329_d() {
        return 9;
    }
    
    @Override
    public int func_82330_g() {
        return 9;
    }
    
    @Override
    public boolean isInRangeToRenderDist(final double par1) {
        double var3 = 16.0;
        var3 *= 64.0 * this.renderDistanceWeight;
        return par1 < var3 * var3;
    }
    
    @Override
    public void dropItemStack() {
        this.entityDropItem(new ItemStack(Item.itemFrame), 0.0f);
        ItemStack var1 = this.getDisplayedItem();
        if (var1 != null && this.rand.nextFloat() < this.itemDropChance) {
            var1 = var1.copy();
            var1.setItemFrame(null);
            this.entityDropItem(var1, 0.0f);
        }
    }
    
    public ItemStack getDisplayedItem() {
        return this.getDataWatcher().getWatchableObjectItemStack(2);
    }
    
    public void setDisplayedItem(ItemStack par1ItemStack) {
        par1ItemStack = par1ItemStack.copy();
        par1ItemStack.stackSize = 1;
        par1ItemStack.setItemFrame(this);
        this.getDataWatcher().updateObject(2, par1ItemStack);
        this.getDataWatcher().setObjectWatched(2);
    }
    
    public int getRotation() {
        return this.getDataWatcher().getWatchableObjectByte(3);
    }
    
    public void setItemRotation(final int par1) {
        this.getDataWatcher().updateObject(3, (byte)(par1 % 4));
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound par1NBTTagCompound) {
        if (this.getDisplayedItem() != null) {
            par1NBTTagCompound.setCompoundTag("Item", this.getDisplayedItem().writeToNBT(new NBTTagCompound()));
            par1NBTTagCompound.setByte("ItemRotation", (byte)this.getRotation());
            par1NBTTagCompound.setFloat("ItemDropChance", this.itemDropChance);
        }
        super.writeEntityToNBT(par1NBTTagCompound);
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound par1NBTTagCompound) {
        final NBTTagCompound var2 = par1NBTTagCompound.getCompoundTag("Item");
        if (var2 != null && !var2.hasNoTags()) {
            this.setDisplayedItem(ItemStack.loadItemStackFromNBT(var2));
            this.setItemRotation(par1NBTTagCompound.getByte("ItemRotation"));
            if (par1NBTTagCompound.hasKey("ItemDropChance")) {
                this.itemDropChance = par1NBTTagCompound.getFloat("ItemDropChance");
            }
        }
        super.readEntityFromNBT(par1NBTTagCompound);
    }
    
    @Override
    public boolean interact(final EntityPlayer par1EntityPlayer) {
        if (this.getDisplayedItem() == null) {
            final ItemStack var2 = par1EntityPlayer.getHeldItem();
            if (var2 != null && !this.worldObj.isRemote) {
                this.setDisplayedItem(var2);
                if (!par1EntityPlayer.capabilities.isCreativeMode) {
                    final ItemStack itemStack = var2;
                    if (--itemStack.stackSize <= 0) {
                        par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, null);
                    }
                }
            }
        }
        else if (!this.worldObj.isRemote) {
            this.setItemRotation(this.getRotation() + 1);
        }
        return true;
    }
}

package net.minecraft.src;

public abstract class EntityMinecartContainer extends EntityMinecart implements IInventory
{
    private ItemStack[] minecartContainerItems;
    private boolean dropContentsWhenDead;
    
    public EntityMinecartContainer(final World par1World) {
        super(par1World);
        this.minecartContainerItems = new ItemStack[36];
        this.dropContentsWhenDead = true;
    }
    
    public EntityMinecartContainer(final World par1World, final double par2, final double par4, final double par6) {
        super(par1World, par2, par4, par6);
        this.minecartContainerItems = new ItemStack[36];
        this.dropContentsWhenDead = true;
    }
    
    @Override
    public void killMinecart(final DamageSource par1DamageSource) {
        super.killMinecart(par1DamageSource);
        for (int var2 = 0; var2 < this.getSizeInventory(); ++var2) {
            final ItemStack var3 = this.getStackInSlot(var2);
            if (var3 != null) {
                final float var4 = this.rand.nextFloat() * 0.8f + 0.1f;
                final float var5 = this.rand.nextFloat() * 0.8f + 0.1f;
                final float var6 = this.rand.nextFloat() * 0.8f + 0.1f;
                while (var3.stackSize > 0) {
                    int var7 = this.rand.nextInt(21) + 10;
                    if (var7 > var3.stackSize) {
                        var7 = var3.stackSize;
                    }
                    final ItemStack itemStack = var3;
                    itemStack.stackSize -= var7;
                    final EntityItem var8 = new EntityItem(this.worldObj, this.posX + var4, this.posY + var5, this.posZ + var6, new ItemStack(var3.itemID, var7, var3.getItemDamage()));
                    final float var9 = 0.05f;
                    var8.motionX = (float)this.rand.nextGaussian() * var9;
                    var8.motionY = (float)this.rand.nextGaussian() * var9 + 0.2f;
                    var8.motionZ = (float)this.rand.nextGaussian() * var9;
                    this.worldObj.spawnEntityInWorld(var8);
                }
            }
        }
    }
    
    @Override
    public ItemStack getStackInSlot(final int par1) {
        return this.minecartContainerItems[par1];
    }
    
    @Override
    public ItemStack decrStackSize(final int par1, final int par2) {
        if (this.minecartContainerItems[par1] == null) {
            return null;
        }
        if (this.minecartContainerItems[par1].stackSize <= par2) {
            final ItemStack var3 = this.minecartContainerItems[par1];
            this.minecartContainerItems[par1] = null;
            return var3;
        }
        final ItemStack var3 = this.minecartContainerItems[par1].splitStack(par2);
        if (this.minecartContainerItems[par1].stackSize == 0) {
            this.minecartContainerItems[par1] = null;
        }
        return var3;
    }
    
    @Override
    public ItemStack getStackInSlotOnClosing(final int par1) {
        if (this.minecartContainerItems[par1] != null) {
            final ItemStack var2 = this.minecartContainerItems[par1];
            this.minecartContainerItems[par1] = null;
            return var2;
        }
        return null;
    }
    
    @Override
    public void setInventorySlotContents(final int par1, final ItemStack par2ItemStack) {
        this.minecartContainerItems[par1] = par2ItemStack;
        if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit()) {
            par2ItemStack.stackSize = this.getInventoryStackLimit();
        }
    }
    
    @Override
    public void onInventoryChanged() {
    }
    
    @Override
    public boolean isUseableByPlayer(final EntityPlayer par1EntityPlayer) {
        return !this.isDead && par1EntityPlayer.getDistanceSqToEntity(this) <= 64.0;
    }
    
    @Override
    public void openChest() {
    }
    
    @Override
    public void closeChest() {
    }
    
    @Override
    public boolean isStackValidForSlot(final int par1, final ItemStack par2ItemStack) {
        return true;
    }
    
    @Override
    public String getInvName() {
        return this.isInvNameLocalized() ? this.func_95999_t() : "container.minecart";
    }
    
    @Override
    public int getInventoryStackLimit() {
        return 64;
    }
    
    @Override
    public void travelToDimension(final int par1) {
        this.dropContentsWhenDead = false;
        super.travelToDimension(par1);
    }
    
    @Override
    public void setDead() {
        if (this.dropContentsWhenDead) {
            for (int var1 = 0; var1 < this.getSizeInventory(); ++var1) {
                final ItemStack var2 = this.getStackInSlot(var1);
                if (var2 != null) {
                    final float var3 = this.rand.nextFloat() * 0.8f + 0.1f;
                    final float var4 = this.rand.nextFloat() * 0.8f + 0.1f;
                    final float var5 = this.rand.nextFloat() * 0.8f + 0.1f;
                    while (var2.stackSize > 0) {
                        int var6 = this.rand.nextInt(21) + 10;
                        if (var6 > var2.stackSize) {
                            var6 = var2.stackSize;
                        }
                        final ItemStack itemStack = var2;
                        itemStack.stackSize -= var6;
                        final EntityItem var7 = new EntityItem(this.worldObj, this.posX + var3, this.posY + var4, this.posZ + var5, new ItemStack(var2.itemID, var6, var2.getItemDamage()));
                        if (var2.hasTagCompound()) {
                            var7.getEntityItem().setTagCompound((NBTTagCompound)var2.getTagCompound().copy());
                        }
                        final float var8 = 0.05f;
                        var7.motionX = (float)this.rand.nextGaussian() * var8;
                        var7.motionY = (float)this.rand.nextGaussian() * var8 + 0.2f;
                        var7.motionZ = (float)this.rand.nextGaussian() * var8;
                        this.worldObj.spawnEntityInWorld(var7);
                    }
                }
            }
        }
        super.setDead();
    }
    
    @Override
    protected void writeEntityToNBT(final NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
        final NBTTagList var2 = new NBTTagList();
        for (int var3 = 0; var3 < this.minecartContainerItems.length; ++var3) {
            if (this.minecartContainerItems[var3] != null) {
                final NBTTagCompound var4 = new NBTTagCompound();
                var4.setByte("Slot", (byte)var3);
                this.minecartContainerItems[var3].writeToNBT(var4);
                var2.appendTag(var4);
            }
        }
        par1NBTTagCompound.setTag("Items", var2);
    }
    
    @Override
    protected void readEntityFromNBT(final NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
        final NBTTagList var2 = par1NBTTagCompound.getTagList("Items");
        this.minecartContainerItems = new ItemStack[this.getSizeInventory()];
        for (int var3 = 0; var3 < var2.tagCount(); ++var3) {
            final NBTTagCompound var4 = (NBTTagCompound)var2.tagAt(var3);
            final int var5 = var4.getByte("Slot") & 0xFF;
            if (var5 >= 0 && var5 < this.minecartContainerItems.length) {
                this.minecartContainerItems[var5] = ItemStack.loadItemStackFromNBT(var4);
            }
        }
    }
    
    @Override
    public boolean interact(final EntityPlayer par1EntityPlayer) {
        if (!this.worldObj.isRemote) {
            par1EntityPlayer.displayGUIChest(this);
        }
        return true;
    }
    
    @Override
    protected void applyDrag() {
        final int var1 = 15 - Container.calcRedstoneFromInventory(this);
        final float var2 = 0.98f + var1 * 0.001f;
        this.motionX *= var2;
        this.motionY *= 0.0;
        this.motionZ *= var2;
    }
}

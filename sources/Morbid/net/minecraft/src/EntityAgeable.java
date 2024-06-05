package net.minecraft.src;

public abstract class EntityAgeable extends EntityCreature
{
    private float field_98056_d;
    private float field_98057_e;
    
    public EntityAgeable(final World par1World) {
        super(par1World);
        this.field_98056_d = -1.0f;
    }
    
    public abstract EntityAgeable createChild(final EntityAgeable p0);
    
    @Override
    public boolean interact(final EntityPlayer par1EntityPlayer) {
        final ItemStack var2 = par1EntityPlayer.inventory.getCurrentItem();
        if (var2 != null && var2.itemID == Item.monsterPlacer.itemID && !this.worldObj.isRemote) {
            final Class var3 = EntityList.getClassFromID(var2.getItemDamage());
            if (var3 != null && var3.isAssignableFrom(this.getClass())) {
                final EntityAgeable var4 = this.createChild(this);
                if (var4 != null) {
                    var4.setGrowingAge(-24000);
                    var4.setLocationAndAngles(this.posX, this.posY, this.posZ, 0.0f, 0.0f);
                    this.worldObj.spawnEntityInWorld(var4);
                    if (var2.hasDisplayName()) {
                        var4.func_94058_c(var2.getDisplayName());
                    }
                    if (!par1EntityPlayer.capabilities.isCreativeMode) {
                        final ItemStack itemStack = var2;
                        --itemStack.stackSize;
                        if (var2.stackSize <= 0) {
                            par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, null);
                        }
                    }
                }
            }
        }
        return super.interact(par1EntityPlayer);
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(12, new Integer(0));
    }
    
    public int getGrowingAge() {
        return this.dataWatcher.getWatchableObjectInt(12);
    }
    
    public void setGrowingAge(final int par1) {
        this.dataWatcher.updateObject(12, par1);
        this.func_98054_a(this.isChild());
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setInteger("Age", this.getGrowingAge());
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.setGrowingAge(par1NBTTagCompound.getInteger("Age"));
    }
    
    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (this.worldObj.isRemote) {
            this.func_98054_a(this.isChild());
        }
        else {
            int var1 = this.getGrowingAge();
            if (var1 < 0) {
                ++var1;
                this.setGrowingAge(var1);
            }
            else if (var1 > 0) {
                --var1;
                this.setGrowingAge(var1);
            }
        }
    }
    
    @Override
    public boolean isChild() {
        return this.getGrowingAge() < 0;
    }
    
    public void func_98054_a(final boolean par1) {
        this.func_98055_j(par1 ? 0.5f : 1.0f);
    }
    
    @Override
    protected final void setSize(final float par1, final float par2) {
        final boolean var3 = this.field_98056_d > 0.0f;
        this.field_98056_d = par1;
        this.field_98057_e = par2;
        if (!var3) {
            this.func_98055_j(1.0f);
        }
    }
    
    private void func_98055_j(final float par1) {
        super.setSize(this.field_98056_d * par1, this.field_98057_e * par1);
    }
}

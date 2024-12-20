package net.minecraft.src;

public class EntityMinecartFurnace extends EntityMinecart
{
    private int fuel;
    public double pushX;
    public double pushZ;
    
    public EntityMinecartFurnace(final World par1World) {
        super(par1World);
        this.fuel = 0;
    }
    
    public EntityMinecartFurnace(final World par1World, final double par2, final double par4, final double par6) {
        super(par1World, par2, par4, par6);
        this.fuel = 0;
    }
    
    @Override
    public int getMinecartType() {
        return 2;
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, new Byte((byte)0));
    }
    
    @Override
    public void onUpdate() {
        super.onUpdate();
        if (this.fuel > 0) {
            --this.fuel;
        }
        if (this.fuel <= 0) {
            final double n = 0.0;
            this.pushZ = n;
            this.pushX = n;
        }
        this.setMinecartPowered(this.fuel > 0);
        if (this.isMinecartPowered() && this.rand.nextInt(4) == 0) {
            this.worldObj.spawnParticle("largesmoke", this.posX, this.posY + 0.8, this.posZ, 0.0, 0.0, 0.0);
        }
    }
    
    @Override
    public void killMinecart(final DamageSource par1DamageSource) {
        super.killMinecart(par1DamageSource);
        if (!par1DamageSource.isExplosion()) {
            this.entityDropItem(new ItemStack(Block.furnaceIdle, 1), 0.0f);
        }
    }
    
    @Override
    protected void updateOnTrack(final int par1, final int par2, final int par3, final double par4, final double par6, final int par8, final int par9) {
        super.updateOnTrack(par1, par2, par3, par4, par6, par8, par9);
        double var10 = this.pushX * this.pushX + this.pushZ * this.pushZ;
        if (var10 > 1.0E-4 && this.motionX * this.motionX + this.motionZ * this.motionZ > 0.001) {
            var10 = MathHelper.sqrt_double(var10);
            this.pushX /= var10;
            this.pushZ /= var10;
            if (this.pushX * this.motionX + this.pushZ * this.motionZ < 0.0) {
                this.pushX = 0.0;
                this.pushZ = 0.0;
            }
            else {
                this.pushX = this.motionX;
                this.pushZ = this.motionZ;
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
            final double var2 = 0.05;
            this.motionX *= 0.800000011920929;
            this.motionY *= 0.0;
            this.motionZ *= 0.800000011920929;
            this.motionX += this.pushX * var2;
            this.motionZ += this.pushZ * var2;
        }
        else {
            this.motionX *= 0.9800000190734863;
            this.motionY *= 0.0;
            this.motionZ *= 0.9800000190734863;
        }
        super.applyDrag();
    }
    
    @Override
    public boolean interact(final EntityPlayer par1EntityPlayer) {
        final ItemStack var2 = par1EntityPlayer.inventory.getCurrentItem();
        if (var2 != null && var2.itemID == Item.coal.itemID) {
            final ItemStack itemStack = var2;
            if (--itemStack.stackSize == 0) {
                par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, null);
            }
            this.fuel += 3600;
        }
        this.pushX = this.posX - par1EntityPlayer.posX;
        this.pushZ = this.posZ - par1EntityPlayer.posZ;
        return true;
    }
    
    @Override
    protected void writeEntityToNBT(final NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setDouble("PushX", this.pushX);
        par1NBTTagCompound.setDouble("PushZ", this.pushZ);
        par1NBTTagCompound.setShort("Fuel", (short)this.fuel);
    }
    
    @Override
    protected void readEntityFromNBT(final NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.pushX = par1NBTTagCompound.getDouble("PushX");
        this.pushZ = par1NBTTagCompound.getDouble("PushZ");
        this.fuel = par1NBTTagCompound.getShort("Fuel");
    }
    
    protected boolean isMinecartPowered() {
        return (this.dataWatcher.getWatchableObjectByte(16) & 0x1) != 0x0;
    }
    
    protected void setMinecartPowered(final boolean par1) {
        if (par1) {
            this.dataWatcher.updateObject(16, (byte)(this.dataWatcher.getWatchableObjectByte(16) | 0x1));
        }
        else {
            this.dataWatcher.updateObject(16, (byte)(this.dataWatcher.getWatchableObjectByte(16) & 0xFFFFFFFE));
        }
    }
    
    @Override
    public Block getDefaultDisplayTile() {
        return Block.furnaceBurning;
    }
    
    @Override
    public int getDefaultDisplayTileData() {
        return 2;
    }
}

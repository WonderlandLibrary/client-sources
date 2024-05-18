package net.minecraft.src;

public abstract class EntityTameable extends EntityAnimal
{
    protected EntityAISit aiSit;
    
    public EntityTameable(final World par1World) {
        super(par1World);
        this.aiSit = new EntityAISit(this);
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, (byte)0);
        this.dataWatcher.addObject(17, "");
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
        if (this.getOwnerName() == null) {
            par1NBTTagCompound.setString("Owner", "");
        }
        else {
            par1NBTTagCompound.setString("Owner", this.getOwnerName());
        }
        par1NBTTagCompound.setBoolean("Sitting", this.isSitting());
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
        final String var2 = par1NBTTagCompound.getString("Owner");
        if (var2.length() > 0) {
            this.setOwner(var2);
            this.setTamed(true);
        }
        this.aiSit.setSitting(par1NBTTagCompound.getBoolean("Sitting"));
        this.setSitting(par1NBTTagCompound.getBoolean("Sitting"));
    }
    
    protected void playTameEffect(final boolean par1) {
        String var2 = "heart";
        if (!par1) {
            var2 = "smoke";
        }
        for (int var3 = 0; var3 < 7; ++var3) {
            final double var4 = this.rand.nextGaussian() * 0.02;
            final double var5 = this.rand.nextGaussian() * 0.02;
            final double var6 = this.rand.nextGaussian() * 0.02;
            this.worldObj.spawnParticle(var2, this.posX + this.rand.nextFloat() * this.width * 2.0f - this.width, this.posY + 0.5 + this.rand.nextFloat() * this.height, this.posZ + this.rand.nextFloat() * this.width * 2.0f - this.width, var4, var5, var6);
        }
    }
    
    @Override
    public void handleHealthUpdate(final byte par1) {
        if (par1 == 7) {
            this.playTameEffect(true);
        }
        else if (par1 == 6) {
            this.playTameEffect(false);
        }
        else {
            super.handleHealthUpdate(par1);
        }
    }
    
    public boolean isTamed() {
        return (this.dataWatcher.getWatchableObjectByte(16) & 0x4) != 0x0;
    }
    
    public void setTamed(final boolean par1) {
        final byte var2 = this.dataWatcher.getWatchableObjectByte(16);
        if (par1) {
            this.dataWatcher.updateObject(16, (byte)(var2 | 0x4));
        }
        else {
            this.dataWatcher.updateObject(16, (byte)(var2 & 0xFFFFFFFB));
        }
    }
    
    public boolean isSitting() {
        return (this.dataWatcher.getWatchableObjectByte(16) & 0x1) != 0x0;
    }
    
    public void setSitting(final boolean par1) {
        final byte var2 = this.dataWatcher.getWatchableObjectByte(16);
        if (par1) {
            this.dataWatcher.updateObject(16, (byte)(var2 | 0x1));
        }
        else {
            this.dataWatcher.updateObject(16, (byte)(var2 & 0xFFFFFFFE));
        }
    }
    
    public String getOwnerName() {
        return this.dataWatcher.getWatchableObjectString(17);
    }
    
    public void setOwner(final String par1Str) {
        this.dataWatcher.updateObject(17, par1Str);
    }
    
    public EntityLiving getOwner() {
        return this.worldObj.getPlayerEntityByName(this.getOwnerName());
    }
    
    public EntityAISit func_70907_r() {
        return this.aiSit;
    }
}

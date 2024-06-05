package net.minecraft.src;

public class EntityFireworkRocket extends Entity
{
    private int fireworkAge;
    private int lifetime;
    
    public EntityFireworkRocket(final World par1World) {
        super(par1World);
        this.setSize(0.25f, 0.25f);
    }
    
    @Override
    protected void entityInit() {
        this.dataWatcher.addObjectByDataType(8, 5);
    }
    
    @Override
    public boolean isInRangeToRenderDist(final double par1) {
        return par1 < 4096.0;
    }
    
    public EntityFireworkRocket(final World par1World, final double par2, final double par4, final double par6, final ItemStack par8ItemStack) {
        super(par1World);
        this.fireworkAge = 0;
        this.setSize(0.25f, 0.25f);
        this.setPosition(par2, par4, par6);
        this.yOffset = 0.0f;
        int var9 = 1;
        if (par8ItemStack != null && par8ItemStack.hasTagCompound()) {
            this.dataWatcher.updateObject(8, par8ItemStack);
            final NBTTagCompound var10 = par8ItemStack.getTagCompound();
            final NBTTagCompound var11 = var10.getCompoundTag("Fireworks");
            if (var11 != null) {
                var9 += var11.getByte("Flight");
            }
        }
        this.motionX = this.rand.nextGaussian() * 0.001;
        this.motionZ = this.rand.nextGaussian() * 0.001;
        this.motionY = 0.05;
        this.lifetime = 10 * var9 + this.rand.nextInt(6) + this.rand.nextInt(7);
    }
    
    @Override
    public void setVelocity(final double par1, final double par3, final double par5) {
        this.motionX = par1;
        this.motionY = par3;
        this.motionZ = par5;
        if (this.prevRotationPitch == 0.0f && this.prevRotationYaw == 0.0f) {
            final float var7 = MathHelper.sqrt_double(par1 * par1 + par5 * par5);
            final float n = (float)(Math.atan2(par1, par5) * 180.0 / 3.141592653589793);
            this.rotationYaw = n;
            this.prevRotationYaw = n;
            final float n2 = (float)(Math.atan2(par3, var7) * 180.0 / 3.141592653589793);
            this.rotationPitch = n2;
            this.prevRotationPitch = n2;
        }
    }
    
    @Override
    public void onUpdate() {
        this.lastTickPosX = this.posX;
        this.lastTickPosY = this.posY;
        this.lastTickPosZ = this.posZ;
        super.onUpdate();
        this.motionX *= 1.15;
        this.motionZ *= 1.15;
        this.motionY += 0.04;
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        final float var1 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
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
        if (this.fireworkAge == 0) {
            this.worldObj.playSoundAtEntity(this, "fireworks.launch", 3.0f, 1.0f);
        }
        ++this.fireworkAge;
        if (this.worldObj.isRemote && this.fireworkAge % 2 < 2) {
            this.worldObj.spawnParticle("fireworksSpark", this.posX, this.posY - 0.3, this.posZ, this.rand.nextGaussian() * 0.05, -this.motionY * 0.5, this.rand.nextGaussian() * 0.05);
        }
        if (!this.worldObj.isRemote && this.fireworkAge > this.lifetime) {
            this.worldObj.setEntityState(this, (byte)17);
            this.setDead();
        }
    }
    
    @Override
    public void handleHealthUpdate(final byte par1) {
        if (par1 == 17 && this.worldObj.isRemote) {
            final ItemStack var2 = this.dataWatcher.getWatchableObjectItemStack(8);
            NBTTagCompound var3 = null;
            if (var2 != null && var2.hasTagCompound()) {
                var3 = var2.getTagCompound().getCompoundTag("Fireworks");
            }
            this.worldObj.func_92088_a(this.posX, this.posY, this.posZ, this.motionX, this.motionY, this.motionZ, var3);
        }
        super.handleHealthUpdate(par1);
    }
    
    public void writeEntityToNBT(final NBTTagCompound par1NBTTagCompound) {
        par1NBTTagCompound.setInteger("Life", this.fireworkAge);
        par1NBTTagCompound.setInteger("LifeTime", this.lifetime);
        final ItemStack var2 = this.dataWatcher.getWatchableObjectItemStack(8);
        if (var2 != null) {
            final NBTTagCompound var3 = new NBTTagCompound();
            var2.writeToNBT(var3);
            par1NBTTagCompound.setCompoundTag("FireworksItem", var3);
        }
    }
    
    public void readEntityFromNBT(final NBTTagCompound par1NBTTagCompound) {
        this.fireworkAge = par1NBTTagCompound.getInteger("Life");
        this.lifetime = par1NBTTagCompound.getInteger("LifeTime");
        final NBTTagCompound var2 = par1NBTTagCompound.getCompoundTag("FireworksItem");
        if (var2 != null) {
            final ItemStack var3 = ItemStack.loadItemStackFromNBT(var2);
            if (var3 != null) {
                this.dataWatcher.updateObject(8, var3);
            }
        }
    }
    
    @Override
    public float getShadowSize() {
        return 0.0f;
    }
    
    @Override
    public float getBrightness(final float par1) {
        return super.getBrightness(par1);
    }
    
    @Override
    public int getBrightnessForRender(final float par1) {
        return super.getBrightnessForRender(par1);
    }
    
    @Override
    public boolean canAttackWithItem() {
        return false;
    }
}

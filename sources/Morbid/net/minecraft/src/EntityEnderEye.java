package net.minecraft.src;

public class EntityEnderEye extends Entity
{
    public int field_70226_a;
    private double targetX;
    private double targetY;
    private double targetZ;
    private int despawnTimer;
    private boolean shatterOrDrop;
    
    public EntityEnderEye(final World par1World) {
        super(par1World);
        this.field_70226_a = 0;
        this.setSize(0.25f, 0.25f);
    }
    
    @Override
    protected void entityInit() {
    }
    
    @Override
    public boolean isInRangeToRenderDist(final double par1) {
        double var3 = this.boundingBox.getAverageEdgeLength() * 4.0;
        var3 *= 64.0;
        return par1 < var3 * var3;
    }
    
    public EntityEnderEye(final World par1World, final double par2, final double par4, final double par6) {
        super(par1World);
        this.field_70226_a = 0;
        this.despawnTimer = 0;
        this.setSize(0.25f, 0.25f);
        this.setPosition(par2, par4, par6);
        this.yOffset = 0.0f;
    }
    
    public void moveTowards(final double par1, final int par3, final double par4) {
        final double var6 = par1 - this.posX;
        final double var7 = par4 - this.posZ;
        final float var8 = MathHelper.sqrt_double(var6 * var6 + var7 * var7);
        if (var8 > 12.0f) {
            this.targetX = this.posX + var6 / var8 * 12.0;
            this.targetZ = this.posZ + var7 / var8 * 12.0;
            this.targetY = this.posY + 8.0;
        }
        else {
            this.targetX = par1;
            this.targetY = par3;
            this.targetZ = par4;
        }
        this.despawnTimer = 0;
        this.shatterOrDrop = (this.rand.nextInt(5) > 0);
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
        this.posX += this.motionX;
        this.posY += this.motionY;
        this.posZ += this.motionZ;
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
        if (!this.worldObj.isRemote) {
            final double var2 = this.targetX - this.posX;
            final double var3 = this.targetZ - this.posZ;
            final float var4 = (float)Math.sqrt(var2 * var2 + var3 * var3);
            final float var5 = (float)Math.atan2(var3, var2);
            double var6 = var1 + (var4 - var1) * 0.0025;
            if (var4 < 1.0f) {
                var6 *= 0.8;
                this.motionY *= 0.8;
            }
            this.motionX = Math.cos(var5) * var6;
            this.motionZ = Math.sin(var5) * var6;
            if (this.posY < this.targetY) {
                this.motionY += (1.0 - this.motionY) * 0.014999999664723873;
            }
            else {
                this.motionY += (-1.0 - this.motionY) * 0.014999999664723873;
            }
        }
        final float var7 = 0.25f;
        if (this.isInWater()) {
            for (int var8 = 0; var8 < 4; ++var8) {
                this.worldObj.spawnParticle("bubble", this.posX - this.motionX * var7, this.posY - this.motionY * var7, this.posZ - this.motionZ * var7, this.motionX, this.motionY, this.motionZ);
            }
        }
        else {
            this.worldObj.spawnParticle("portal", this.posX - this.motionX * var7 + this.rand.nextDouble() * 0.6 - 0.3, this.posY - this.motionY * var7 - 0.5, this.posZ - this.motionZ * var7 + this.rand.nextDouble() * 0.6 - 0.3, this.motionX, this.motionY, this.motionZ);
        }
        if (!this.worldObj.isRemote) {
            this.setPosition(this.posX, this.posY, this.posZ);
            ++this.despawnTimer;
            if (this.despawnTimer > 80 && !this.worldObj.isRemote) {
                this.setDead();
                if (this.shatterOrDrop) {
                    this.worldObj.spawnEntityInWorld(new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, new ItemStack(Item.eyeOfEnder)));
                }
                else {
                    this.worldObj.playAuxSFX(2003, (int)Math.round(this.posX), (int)Math.round(this.posY), (int)Math.round(this.posZ), 0);
                }
            }
        }
    }
    
    public void writeEntityToNBT(final NBTTagCompound par1NBTTagCompound) {
    }
    
    public void readEntityFromNBT(final NBTTagCompound par1NBTTagCompound) {
    }
    
    @Override
    public float getShadowSize() {
        return 0.0f;
    }
    
    @Override
    public float getBrightness(final float par1) {
        return 1.0f;
    }
    
    @Override
    public int getBrightnessForRender(final float par1) {
        return 15728880;
    }
    
    @Override
    public boolean canAttackWithItem() {
        return false;
    }
}

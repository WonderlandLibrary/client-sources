package net.minecraft.src;

public class EntityXPOrb extends Entity
{
    public int xpColor;
    public int xpOrbAge;
    public int field_70532_c;
    private int xpOrbHealth;
    private int xpValue;
    private EntityPlayer closestPlayer;
    private int xpTargetColor;
    
    public EntityXPOrb(final World par1World, final double par2, final double par4, final double par6, final int par8) {
        super(par1World);
        this.xpOrbAge = 0;
        this.xpOrbHealth = 5;
        this.setSize(0.5f, 0.5f);
        this.yOffset = this.height / 2.0f;
        this.setPosition(par2, par4, par6);
        this.rotationYaw = (float)(Math.random() * 360.0);
        this.motionX = (float)(Math.random() * 0.20000000298023224 - 0.10000000149011612) * 2.0f;
        this.motionY = (float)(Math.random() * 0.2) * 2.0f;
        this.motionZ = (float)(Math.random() * 0.20000000298023224 - 0.10000000149011612) * 2.0f;
        this.xpValue = par8;
    }
    
    @Override
    protected boolean canTriggerWalking() {
        return false;
    }
    
    public EntityXPOrb(final World par1World) {
        super(par1World);
        this.xpOrbAge = 0;
        this.xpOrbHealth = 5;
        this.setSize(0.25f, 0.25f);
        this.yOffset = this.height / 2.0f;
    }
    
    @Override
    protected void entityInit() {
    }
    
    @Override
    public int getBrightnessForRender(final float par1) {
        float var2 = 0.5f;
        if (var2 < 0.0f) {
            var2 = 0.0f;
        }
        if (var2 > 1.0f) {
            var2 = 1.0f;
        }
        final int var3 = super.getBrightnessForRender(par1);
        int var4 = var3 & 0xFF;
        final int var5 = var3 >> 16 & 0xFF;
        var4 += (int)(var2 * 15.0f * 16.0f);
        if (var4 > 240) {
            var4 = 240;
        }
        return var4 | var5 << 16;
    }
    
    @Override
    public void onUpdate() {
        super.onUpdate();
        if (this.field_70532_c > 0) {
            --this.field_70532_c;
        }
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.motionY -= 0.029999999329447746;
        if (this.worldObj.getBlockMaterial(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)) == Material.lava) {
            this.motionY = 0.20000000298023224;
            this.motionX = (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f;
            this.motionZ = (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f;
            this.playSound("random.fizz", 0.4f, 2.0f + this.rand.nextFloat() * 0.4f);
        }
        this.pushOutOfBlocks(this.posX, (this.boundingBox.minY + this.boundingBox.maxY) / 2.0, this.posZ);
        final double var1 = 8.0;
        if (this.xpTargetColor < this.xpColor - 20 + this.entityId % 100) {
            if (this.closestPlayer == null || this.closestPlayer.getDistanceSqToEntity(this) > var1 * var1) {
                this.closestPlayer = this.worldObj.getClosestPlayerToEntity(this, var1);
            }
            this.xpTargetColor = this.xpColor;
        }
        if (this.closestPlayer != null) {
            final double var2 = (this.closestPlayer.posX - this.posX) / var1;
            final double var3 = (this.closestPlayer.posY + this.closestPlayer.getEyeHeight() - this.posY) / var1;
            final double var4 = (this.closestPlayer.posZ - this.posZ) / var1;
            final double var5 = Math.sqrt(var2 * var2 + var3 * var3 + var4 * var4);
            double var6 = 1.0 - var5;
            if (var6 > 0.0) {
                var6 *= var6;
                this.motionX += var2 / var5 * var6 * 0.1;
                this.motionY += var3 / var5 * var6 * 0.1;
                this.motionZ += var4 / var5 * var6 * 0.1;
            }
        }
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        float var7 = 0.98f;
        if (this.onGround) {
            var7 = 0.58800006f;
            final int var8 = this.worldObj.getBlockId(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.boundingBox.minY) - 1, MathHelper.floor_double(this.posZ));
            if (var8 > 0) {
                var7 = Block.blocksList[var8].slipperiness * 0.98f;
            }
        }
        this.motionX *= var7;
        this.motionY *= 0.9800000190734863;
        this.motionZ *= var7;
        if (this.onGround) {
            this.motionY *= -0.8999999761581421;
        }
        ++this.xpColor;
        ++this.xpOrbAge;
        if (this.xpOrbAge >= 6000) {
            this.setDead();
        }
    }
    
    @Override
    public boolean handleWaterMovement() {
        return this.worldObj.handleMaterialAcceleration(this.boundingBox, Material.water, this);
    }
    
    @Override
    protected void dealFireDamage(final int par1) {
        this.attackEntityFrom(DamageSource.inFire, par1);
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource par1DamageSource, final int par2) {
        if (this.isEntityInvulnerable()) {
            return false;
        }
        this.setBeenAttacked();
        this.xpOrbHealth -= par2;
        if (this.xpOrbHealth <= 0) {
            this.setDead();
        }
        return false;
    }
    
    public void writeEntityToNBT(final NBTTagCompound par1NBTTagCompound) {
        par1NBTTagCompound.setShort("Health", (byte)this.xpOrbHealth);
        par1NBTTagCompound.setShort("Age", (short)this.xpOrbAge);
        par1NBTTagCompound.setShort("Value", (short)this.xpValue);
    }
    
    public void readEntityFromNBT(final NBTTagCompound par1NBTTagCompound) {
        this.xpOrbHealth = (par1NBTTagCompound.getShort("Health") & 0xFF);
        this.xpOrbAge = par1NBTTagCompound.getShort("Age");
        this.xpValue = par1NBTTagCompound.getShort("Value");
    }
    
    @Override
    public void onCollideWithPlayer(final EntityPlayer par1EntityPlayer) {
        if (!this.worldObj.isRemote && this.field_70532_c == 0 && par1EntityPlayer.xpCooldown == 0) {
            par1EntityPlayer.xpCooldown = 2;
            this.playSound("random.orb", 0.1f, 0.5f * ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7f + 1.8f));
            par1EntityPlayer.onItemPickup(this, 1);
            par1EntityPlayer.addExperience(this.xpValue);
            this.setDead();
        }
    }
    
    public int getXpValue() {
        return this.xpValue;
    }
    
    public int getTextureByXP() {
        return (this.xpValue >= 2477) ? 10 : ((this.xpValue >= 1237) ? 9 : ((this.xpValue >= 617) ? 8 : ((this.xpValue >= 307) ? 7 : ((this.xpValue >= 149) ? 6 : ((this.xpValue >= 73) ? 5 : ((this.xpValue >= 37) ? 4 : ((this.xpValue >= 17) ? 3 : ((this.xpValue >= 7) ? 2 : ((this.xpValue >= 3) ? 1 : 0)))))))));
    }
    
    public static int getXPSplit(final int par0) {
        return (par0 >= 2477) ? 2477 : ((par0 >= 1237) ? 1237 : ((par0 >= 617) ? 617 : ((par0 >= 307) ? 307 : ((par0 >= 149) ? 149 : ((par0 >= 73) ? 73 : ((par0 >= 37) ? 37 : ((par0 >= 17) ? 17 : ((par0 >= 7) ? 7 : ((par0 >= 3) ? 3 : 1)))))))));
    }
    
    @Override
    public boolean canAttackWithItem() {
        return false;
    }
}

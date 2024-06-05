package net.minecraft.src;

public class EntityBlaze extends EntityMob
{
    private float heightOffset;
    private int heightOffsetUpdateTime;
    private int field_70846_g;
    
    public EntityBlaze(final World par1World) {
        super(par1World);
        this.heightOffset = 0.5f;
        this.texture = "/mob/fire.png";
        this.isImmuneToFire = true;
        this.experienceValue = 10;
    }
    
    @Override
    public int getMaxHealth() {
        return 20;
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, new Byte((byte)0));
    }
    
    @Override
    protected String getLivingSound() {
        return "mob.blaze.breathe";
    }
    
    @Override
    protected String getHurtSound() {
        return "mob.blaze.hit";
    }
    
    @Override
    protected String getDeathSound() {
        return "mob.blaze.death";
    }
    
    @Override
    public int getBrightnessForRender(final float par1) {
        return 15728880;
    }
    
    @Override
    public float getBrightness(final float par1) {
        return 1.0f;
    }
    
    @Override
    public void onLivingUpdate() {
        if (!this.worldObj.isRemote) {
            if (this.isWet()) {
                this.attackEntityFrom(DamageSource.drown, 1);
            }
            --this.heightOffsetUpdateTime;
            if (this.heightOffsetUpdateTime <= 0) {
                this.heightOffsetUpdateTime = 100;
                this.heightOffset = 0.5f + (float)this.rand.nextGaussian() * 3.0f;
            }
            if (this.getEntityToAttack() != null && this.getEntityToAttack().posY + this.getEntityToAttack().getEyeHeight() > this.posY + this.getEyeHeight() + this.heightOffset) {
                this.motionY += (0.30000001192092896 - this.motionY) * 0.30000001192092896;
            }
        }
        if (this.rand.nextInt(24) == 0) {
            this.worldObj.playSoundEffect(this.posX + 0.5, this.posY + 0.5, this.posZ + 0.5, "fire.fire", 1.0f + this.rand.nextFloat(), this.rand.nextFloat() * 0.7f + 0.3f);
        }
        if (!this.onGround && this.motionY < 0.0) {
            this.motionY *= 0.6;
        }
        for (int var1 = 0; var1 < 2; ++var1) {
            this.worldObj.spawnParticle("largesmoke", this.posX + (this.rand.nextDouble() - 0.5) * this.width, this.posY + this.rand.nextDouble() * this.height, this.posZ + (this.rand.nextDouble() - 0.5) * this.width, 0.0, 0.0, 0.0);
        }
        super.onLivingUpdate();
    }
    
    @Override
    protected void attackEntity(final Entity par1Entity, final float par2) {
        if (this.attackTime <= 0 && par2 < 2.0f && par1Entity.boundingBox.maxY > this.boundingBox.minY && par1Entity.boundingBox.minY < this.boundingBox.maxY) {
            this.attackTime = 20;
            this.attackEntityAsMob(par1Entity);
        }
        else if (par2 < 30.0f) {
            final double var3 = par1Entity.posX - this.posX;
            final double var4 = par1Entity.boundingBox.minY + par1Entity.height / 2.0f - (this.posY + this.height / 2.0f);
            final double var5 = par1Entity.posZ - this.posZ;
            if (this.attackTime == 0) {
                ++this.field_70846_g;
                if (this.field_70846_g == 1) {
                    this.attackTime = 60;
                    this.func_70844_e(true);
                }
                else if (this.field_70846_g <= 4) {
                    this.attackTime = 6;
                }
                else {
                    this.attackTime = 100;
                    this.field_70846_g = 0;
                    this.func_70844_e(false);
                }
                if (this.field_70846_g > 1) {
                    final float var6 = MathHelper.sqrt_float(par2) * 0.5f;
                    this.worldObj.playAuxSFXAtEntity(null, 1009, (int)this.posX, (int)this.posY, (int)this.posZ, 0);
                    for (int var7 = 0; var7 < 1; ++var7) {
                        final EntitySmallFireball var8 = new EntitySmallFireball(this.worldObj, this, var3 + this.rand.nextGaussian() * var6, var4, var5 + this.rand.nextGaussian() * var6);
                        var8.posY = this.posY + this.height / 2.0f + 0.5;
                        this.worldObj.spawnEntityInWorld(var8);
                    }
                }
            }
            this.rotationYaw = (float)(Math.atan2(var5, var3) * 180.0 / 3.141592653589793) - 90.0f;
            this.hasAttacked = true;
        }
    }
    
    @Override
    protected void fall(final float par1) {
    }
    
    @Override
    protected int getDropItemId() {
        return Item.blazeRod.itemID;
    }
    
    @Override
    public boolean isBurning() {
        return this.func_70845_n();
    }
    
    @Override
    protected void dropFewItems(final boolean par1, final int par2) {
        if (par1) {
            for (int var3 = this.rand.nextInt(2 + par2), var4 = 0; var4 < var3; ++var4) {
                this.dropItem(Item.blazeRod.itemID, 1);
            }
        }
    }
    
    public boolean func_70845_n() {
        return (this.dataWatcher.getWatchableObjectByte(16) & 0x1) != 0x0;
    }
    
    public void func_70844_e(final boolean par1) {
        byte var2 = this.dataWatcher.getWatchableObjectByte(16);
        if (par1) {
            var2 |= 0x1;
        }
        else {
            var2 &= 0xFFFFFFFE;
        }
        this.dataWatcher.updateObject(16, var2);
    }
    
    @Override
    protected boolean isValidLightLevel() {
        return true;
    }
    
    @Override
    public int getAttackStrength(final Entity par1Entity) {
        return 6;
    }
}

package net.minecraft.src;

public abstract class EntityMob extends EntityCreature implements IMob
{
    public EntityMob(final World par1World) {
        super(par1World);
        this.experienceValue = 5;
    }
    
    @Override
    public void onLivingUpdate() {
        this.updateArmSwingProgress();
        final float var1 = this.getBrightness(1.0f);
        if (var1 > 0.5f) {
            this.entityAge += 2;
        }
        super.onLivingUpdate();
    }
    
    @Override
    public void onUpdate() {
        super.onUpdate();
        if (!this.worldObj.isRemote && this.worldObj.difficultySetting == 0) {
            this.setDead();
        }
    }
    
    @Override
    protected Entity findPlayerToAttack() {
        final EntityPlayer var1 = this.worldObj.getClosestVulnerablePlayerToEntity(this, 16.0);
        return (var1 != null && this.canEntityBeSeen(var1)) ? var1 : null;
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource par1DamageSource, final int par2) {
        if (this.isEntityInvulnerable()) {
            return false;
        }
        if (!super.attackEntityFrom(par1DamageSource, par2)) {
            return false;
        }
        final Entity var3 = par1DamageSource.getEntity();
        if (this.riddenByEntity != var3 && this.ridingEntity != var3) {
            if (var3 != this) {
                this.entityToAttack = var3;
            }
            return true;
        }
        return true;
    }
    
    @Override
    public boolean attackEntityAsMob(final Entity par1Entity) {
        int var2 = this.getAttackStrength(par1Entity);
        if (this.isPotionActive(Potion.damageBoost)) {
            var2 += 3 << this.getActivePotionEffect(Potion.damageBoost).getAmplifier();
        }
        if (this.isPotionActive(Potion.weakness)) {
            var2 -= 2 << this.getActivePotionEffect(Potion.weakness).getAmplifier();
        }
        int var3 = 0;
        if (par1Entity instanceof EntityLiving) {
            var2 += EnchantmentHelper.getEnchantmentModifierLiving(this, (EntityLiving)par1Entity);
            var3 += EnchantmentHelper.getKnockbackModifier(this, (EntityLiving)par1Entity);
        }
        final boolean var4 = par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this), var2);
        if (var4) {
            if (var3 > 0) {
                par1Entity.addVelocity(-MathHelper.sin(this.rotationYaw * 3.1415927f / 180.0f) * var3 * 0.5f, 0.1, MathHelper.cos(this.rotationYaw * 3.1415927f / 180.0f) * var3 * 0.5f);
                this.motionX *= 0.6;
                this.motionZ *= 0.6;
            }
            final int var5 = EnchantmentHelper.getFireAspectModifier(this);
            if (var5 > 0) {
                par1Entity.setFire(var5 * 4);
            }
            if (par1Entity instanceof EntityLiving) {
                EnchantmentThorns.func_92096_a(this, (EntityLiving)par1Entity, this.rand);
            }
        }
        return var4;
    }
    
    @Override
    protected void attackEntity(final Entity par1Entity, final float par2) {
        if (this.attackTime <= 0 && par2 < 2.0f && par1Entity.boundingBox.maxY > this.boundingBox.minY && par1Entity.boundingBox.minY < this.boundingBox.maxY) {
            this.attackTime = 20;
            this.attackEntityAsMob(par1Entity);
        }
    }
    
    @Override
    public float getBlockPathWeight(final int par1, final int par2, final int par3) {
        return 0.5f - this.worldObj.getLightBrightness(par1, par2, par3);
    }
    
    protected boolean isValidLightLevel() {
        final int var1 = MathHelper.floor_double(this.posX);
        final int var2 = MathHelper.floor_double(this.boundingBox.minY);
        final int var3 = MathHelper.floor_double(this.posZ);
        if (this.worldObj.getSavedLightValue(EnumSkyBlock.Sky, var1, var2, var3) > this.rand.nextInt(32)) {
            return false;
        }
        int var4 = this.worldObj.getBlockLightValue(var1, var2, var3);
        if (this.worldObj.isThundering()) {
            final int var5 = this.worldObj.skylightSubtracted;
            this.worldObj.skylightSubtracted = 10;
            var4 = this.worldObj.getBlockLightValue(var1, var2, var3);
            this.worldObj.skylightSubtracted = var5;
        }
        return var4 <= this.rand.nextInt(8);
    }
    
    @Override
    public boolean getCanSpawnHere() {
        return this.isValidLightLevel() && super.getCanSpawnHere();
    }
    
    public int getAttackStrength(final Entity par1Entity) {
        return 2;
    }
}

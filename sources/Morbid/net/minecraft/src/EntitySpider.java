package net.minecraft.src;

public class EntitySpider extends EntityMob
{
    public EntitySpider(final World par1World) {
        super(par1World);
        this.texture = "/mob/spider.png";
        this.setSize(1.4f, 0.9f);
        this.moveSpeed = 0.8f;
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, new Byte((byte)0));
    }
    
    @Override
    public void onUpdate() {
        super.onUpdate();
        if (!this.worldObj.isRemote) {
            this.setBesideClimbableBlock(this.isCollidedHorizontally);
        }
    }
    
    @Override
    public int getMaxHealth() {
        return 16;
    }
    
    @Override
    public double getMountedYOffset() {
        return this.height * 0.75 - 0.5;
    }
    
    @Override
    protected Entity findPlayerToAttack() {
        final float var1 = this.getBrightness(1.0f);
        if (var1 < 0.5f) {
            final double var2 = 16.0;
            return this.worldObj.getClosestVulnerablePlayerToEntity(this, var2);
        }
        return null;
    }
    
    @Override
    protected String getLivingSound() {
        return "mob.spider.say";
    }
    
    @Override
    protected String getHurtSound() {
        return "mob.spider.say";
    }
    
    @Override
    protected String getDeathSound() {
        return "mob.spider.death";
    }
    
    @Override
    protected void playStepSound(final int par1, final int par2, final int par3, final int par4) {
        this.playSound("mob.spider.step", 0.15f, 1.0f);
    }
    
    @Override
    protected void attackEntity(final Entity par1Entity, final float par2) {
        final float var3 = this.getBrightness(1.0f);
        if (var3 > 0.5f && this.rand.nextInt(100) == 0) {
            this.entityToAttack = null;
        }
        else if (par2 > 2.0f && par2 < 6.0f && this.rand.nextInt(10) == 0) {
            if (this.onGround) {
                final double var4 = par1Entity.posX - this.posX;
                final double var5 = par1Entity.posZ - this.posZ;
                final float var6 = MathHelper.sqrt_double(var4 * var4 + var5 * var5);
                this.motionX = var4 / var6 * 0.5 * 0.800000011920929 + this.motionX * 0.20000000298023224;
                this.motionZ = var5 / var6 * 0.5 * 0.800000011920929 + this.motionZ * 0.20000000298023224;
                this.motionY = 0.4000000059604645;
            }
        }
        else {
            super.attackEntity(par1Entity, par2);
        }
    }
    
    @Override
    protected int getDropItemId() {
        return Item.silk.itemID;
    }
    
    @Override
    protected void dropFewItems(final boolean par1, final int par2) {
        super.dropFewItems(par1, par2);
        if (par1 && (this.rand.nextInt(3) == 0 || this.rand.nextInt(1 + par2) > 0)) {
            this.dropItem(Item.spiderEye.itemID, 1);
        }
    }
    
    @Override
    public boolean isOnLadder() {
        return this.isBesideClimbableBlock();
    }
    
    @Override
    public void setInWeb() {
    }
    
    public float spiderScaleAmount() {
        return 1.0f;
    }
    
    @Override
    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.ARTHROPOD;
    }
    
    @Override
    public boolean isPotionApplicable(final PotionEffect par1PotionEffect) {
        return par1PotionEffect.getPotionID() != Potion.poison.id && super.isPotionApplicable(par1PotionEffect);
    }
    
    public boolean isBesideClimbableBlock() {
        return (this.dataWatcher.getWatchableObjectByte(16) & 0x1) != 0x0;
    }
    
    public void setBesideClimbableBlock(final boolean par1) {
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
    public void initCreature() {
        if (this.worldObj.rand.nextInt(100) == 0) {
            final EntitySkeleton var1 = new EntitySkeleton(this.worldObj);
            var1.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0f);
            var1.initCreature();
            this.worldObj.spawnEntityInWorld(var1);
            var1.mountEntity(this);
        }
    }
}

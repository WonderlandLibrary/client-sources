package net.minecraft.src;

public class EntitySnowman extends EntityGolem implements IRangedAttackMob
{
    public EntitySnowman(final World par1World) {
        super(par1World);
        this.texture = "/mob/snowman.png";
        this.setSize(0.4f, 1.8f);
        this.getNavigator().setAvoidsWater(true);
        this.tasks.addTask(1, new EntityAIArrowAttack(this, 0.25f, 20, 10.0f));
        this.tasks.addTask(2, new EntityAIWander(this, 0.2f));
        this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0f));
        this.tasks.addTask(4, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityLiving.class, 16.0f, 0, true, false, IMob.mobSelector));
    }
    
    public boolean isAIEnabled() {
        return true;
    }
    
    @Override
    public int getMaxHealth() {
        return 4;
    }
    
    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (this.isWet()) {
            this.attackEntityFrom(DamageSource.drown, 1);
        }
        int var1 = MathHelper.floor_double(this.posX);
        int var2 = MathHelper.floor_double(this.posZ);
        if (this.worldObj.getBiomeGenForCoords(var1, var2).getFloatTemperature() > 1.0f) {
            this.attackEntityFrom(DamageSource.onFire, 1);
        }
        for (var1 = 0; var1 < 4; ++var1) {
            var2 = MathHelper.floor_double(this.posX + (var1 % 2 * 2 - 1) * 0.25f);
            final int var3 = MathHelper.floor_double(this.posY);
            final int var4 = MathHelper.floor_double(this.posZ + (var1 / 2 % 2 * 2 - 1) * 0.25f);
            if (this.worldObj.getBlockId(var2, var3, var4) == 0 && this.worldObj.getBiomeGenForCoords(var2, var4).getFloatTemperature() < 0.8f && Block.snow.canPlaceBlockAt(this.worldObj, var2, var3, var4)) {
                this.worldObj.setBlock(var2, var3, var4, Block.snow.blockID);
            }
        }
    }
    
    @Override
    protected int getDropItemId() {
        return Item.snowball.itemID;
    }
    
    @Override
    protected void dropFewItems(final boolean par1, final int par2) {
        for (int var3 = this.rand.nextInt(16), var4 = 0; var4 < var3; ++var4) {
            this.dropItem(Item.snowball.itemID, 1);
        }
    }
    
    @Override
    public void attackEntityWithRangedAttack(final EntityLiving par1EntityLiving, final float par2) {
        final EntitySnowball var3 = new EntitySnowball(this.worldObj, this);
        final double var4 = par1EntityLiving.posX - this.posX;
        final double var5 = par1EntityLiving.posY + par1EntityLiving.getEyeHeight() - 1.100000023841858 - var3.posY;
        final double var6 = par1EntityLiving.posZ - this.posZ;
        final float var7 = MathHelper.sqrt_double(var4 * var4 + var6 * var6) * 0.2f;
        var3.setThrowableHeading(var4, var5 + var7, var6, 1.6f, 12.0f);
        this.playSound("random.bow", 1.0f, 1.0f / (this.getRNG().nextFloat() * 0.4f + 0.8f));
        this.worldObj.spawnEntityInWorld(var3);
    }
}

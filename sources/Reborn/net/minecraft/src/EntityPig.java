package net.minecraft.src;

public class EntityPig extends EntityAnimal
{
    private final EntityAIControlledByPlayer aiControlledByPlayer;
    
    public EntityPig(final World par1World) {
        super(par1World);
        this.texture = "/mob/pig.png";
        this.setSize(0.9f, 0.9f);
        this.getNavigator().setAvoidsWater(true);
        final float var2 = 0.25f;
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIPanic(this, 0.38f));
        this.tasks.addTask(2, this.aiControlledByPlayer = new EntityAIControlledByPlayer(this, 0.34f));
        this.tasks.addTask(3, new EntityAIMate(this, var2));
        this.tasks.addTask(4, new EntityAITempt(this, 0.3f, Item.carrotOnAStick.itemID, false));
        this.tasks.addTask(4, new EntityAITempt(this, 0.3f, Item.carrot.itemID, false));
        this.tasks.addTask(5, new EntityAIFollowParent(this, 0.28f));
        this.tasks.addTask(6, new EntityAIWander(this, var2));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0f));
        this.tasks.addTask(8, new EntityAILookIdle(this));
    }
    
    public boolean isAIEnabled() {
        return true;
    }
    
    @Override
    public int getMaxHealth() {
        return 10;
    }
    
    @Override
    protected void updateAITasks() {
        super.updateAITasks();
    }
    
    @Override
    public boolean canBeSteered() {
        final ItemStack var1 = ((EntityPlayer)this.riddenByEntity).getHeldItem();
        return var1 != null && var1.itemID == Item.carrotOnAStick.itemID;
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, (byte)0);
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setBoolean("Saddle", this.getSaddled());
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.setSaddled(par1NBTTagCompound.getBoolean("Saddle"));
    }
    
    @Override
    protected String getLivingSound() {
        return "mob.pig.say";
    }
    
    @Override
    protected String getHurtSound() {
        return "mob.pig.say";
    }
    
    @Override
    protected String getDeathSound() {
        return "mob.pig.death";
    }
    
    @Override
    protected void playStepSound(final int par1, final int par2, final int par3, final int par4) {
        this.playSound("mob.pig.step", 0.15f, 1.0f);
    }
    
    @Override
    public boolean interact(final EntityPlayer par1EntityPlayer) {
        if (super.interact(par1EntityPlayer)) {
            return true;
        }
        if (this.getSaddled() && !this.worldObj.isRemote && (this.riddenByEntity == null || this.riddenByEntity == par1EntityPlayer)) {
            par1EntityPlayer.mountEntity(this);
            return true;
        }
        return false;
    }
    
    @Override
    protected int getDropItemId() {
        return this.isBurning() ? Item.porkCooked.itemID : Item.porkRaw.itemID;
    }
    
    @Override
    protected void dropFewItems(final boolean par1, final int par2) {
        for (int var3 = this.rand.nextInt(3) + 1 + this.rand.nextInt(1 + par2), var4 = 0; var4 < var3; ++var4) {
            if (this.isBurning()) {
                this.dropItem(Item.porkCooked.itemID, 1);
            }
            else {
                this.dropItem(Item.porkRaw.itemID, 1);
            }
        }
        if (this.getSaddled()) {
            this.dropItem(Item.saddle.itemID, 1);
        }
    }
    
    public boolean getSaddled() {
        return (this.dataWatcher.getWatchableObjectByte(16) & 0x1) != 0x0;
    }
    
    public void setSaddled(final boolean par1) {
        if (par1) {
            this.dataWatcher.updateObject(16, (byte)1);
        }
        else {
            this.dataWatcher.updateObject(16, (byte)0);
        }
    }
    
    @Override
    public void onStruckByLightning(final EntityLightningBolt par1EntityLightningBolt) {
        if (!this.worldObj.isRemote) {
            final EntityPigZombie var2 = new EntityPigZombie(this.worldObj);
            var2.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
            this.worldObj.spawnEntityInWorld(var2);
            this.setDead();
        }
    }
    
    @Override
    protected void fall(final float par1) {
        super.fall(par1);
        if (par1 > 5.0f && this.riddenByEntity instanceof EntityPlayer) {
            ((EntityPlayer)this.riddenByEntity).triggerAchievement(AchievementList.flyPig);
        }
    }
    
    public EntityPig spawnBabyAnimal(final EntityAgeable par1EntityAgeable) {
        return new EntityPig(this.worldObj);
    }
    
    @Override
    public boolean isBreedingItem(final ItemStack par1ItemStack) {
        return par1ItemStack != null && par1ItemStack.itemID == Item.carrot.itemID;
    }
    
    public EntityAIControlledByPlayer getAIControlledByPlayer() {
        return this.aiControlledByPlayer;
    }
    
    @Override
    public EntityAgeable createChild(final EntityAgeable par1EntityAgeable) {
        return this.spawnBabyAnimal(par1EntityAgeable);
    }
}

package net.minecraft.src;

public class EntityWolf extends EntityTameable
{
    private float field_70926_e;
    private float field_70924_f;
    private boolean isShaking;
    private boolean field_70928_h;
    private float timeWolfIsShaking;
    private float prevTimeWolfIsShaking;
    
    public EntityWolf(final World par1World) {
        super(par1World);
        this.texture = "/mob/wolf.png";
        this.setSize(0.6f, 0.8f);
        this.moveSpeed = 0.3f;
        this.getNavigator().setAvoidsWater(true);
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, this.aiSit);
        this.tasks.addTask(3, new EntityAILeapAtTarget(this, 0.4f));
        this.tasks.addTask(4, new EntityAIAttackOnCollide(this, this.moveSpeed, true));
        this.tasks.addTask(5, new EntityAIFollowOwner(this, this.moveSpeed, 10.0f, 2.0f));
        this.tasks.addTask(6, new EntityAIMate(this, this.moveSpeed));
        this.tasks.addTask(7, new EntityAIWander(this, this.moveSpeed));
        this.tasks.addTask(8, new EntityAIBeg(this, 8.0f));
        this.tasks.addTask(9, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0f));
        this.tasks.addTask(9, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this));
        this.targetTasks.addTask(2, new EntityAIOwnerHurtTarget(this));
        this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, true));
        this.targetTasks.addTask(4, new EntityAITargetNonTamed(this, EntitySheep.class, 16.0f, 200, false));
    }
    
    public boolean isAIEnabled() {
        return true;
    }
    
    @Override
    public void setAttackTarget(final EntityLiving par1EntityLiving) {
        super.setAttackTarget(par1EntityLiving);
        if (par1EntityLiving instanceof EntityPlayer) {
            this.setAngry(true);
        }
    }
    
    @Override
    protected void updateAITick() {
        this.dataWatcher.updateObject(18, this.getHealth());
    }
    
    @Override
    public int getMaxHealth() {
        return this.isTamed() ? 20 : 8;
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(18, new Integer(this.getHealth()));
        this.dataWatcher.addObject(19, new Byte((byte)0));
        this.dataWatcher.addObject(20, new Byte((byte)BlockCloth.getBlockFromDye(1)));
    }
    
    @Override
    protected void playStepSound(final int par1, final int par2, final int par3, final int par4) {
        this.playSound("mob.wolf.step", 0.15f, 1.0f);
    }
    
    @Override
    public String getTexture() {
        return this.isTamed() ? "/mob/wolf_tame.png" : (this.isAngry() ? "/mob/wolf_angry.png" : super.getTexture());
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setBoolean("Angry", this.isAngry());
        par1NBTTagCompound.setByte("CollarColor", (byte)this.getCollarColor());
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.setAngry(par1NBTTagCompound.getBoolean("Angry"));
        if (par1NBTTagCompound.hasKey("CollarColor")) {
            this.setCollarColor(par1NBTTagCompound.getByte("CollarColor"));
        }
    }
    
    @Override
    protected boolean canDespawn() {
        return this.isAngry() && !this.isTamed();
    }
    
    @Override
    protected String getLivingSound() {
        return this.isAngry() ? "mob.wolf.growl" : ((this.rand.nextInt(3) == 0) ? ((this.isTamed() && this.dataWatcher.getWatchableObjectInt(18) < 10) ? "mob.wolf.whine" : "mob.wolf.panting") : "mob.wolf.bark");
    }
    
    @Override
    protected String getHurtSound() {
        return "mob.wolf.hurt";
    }
    
    @Override
    protected String getDeathSound() {
        return "mob.wolf.death";
    }
    
    @Override
    protected float getSoundVolume() {
        return 0.4f;
    }
    
    @Override
    protected int getDropItemId() {
        return -1;
    }
    
    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (!this.worldObj.isRemote && this.isShaking && !this.field_70928_h && !this.hasPath() && this.onGround) {
            this.field_70928_h = true;
            this.timeWolfIsShaking = 0.0f;
            this.prevTimeWolfIsShaking = 0.0f;
            this.worldObj.setEntityState(this, (byte)8);
        }
    }
    
    @Override
    public void onUpdate() {
        super.onUpdate();
        this.field_70924_f = this.field_70926_e;
        if (this.func_70922_bv()) {
            this.field_70926_e += (1.0f - this.field_70926_e) * 0.4f;
        }
        else {
            this.field_70926_e += (0.0f - this.field_70926_e) * 0.4f;
        }
        if (this.func_70922_bv()) {
            this.numTicksToChaseTarget = 10;
        }
        if (this.isWet()) {
            this.isShaking = true;
            this.field_70928_h = false;
            this.timeWolfIsShaking = 0.0f;
            this.prevTimeWolfIsShaking = 0.0f;
        }
        else if ((this.isShaking || this.field_70928_h) && this.field_70928_h) {
            if (this.timeWolfIsShaking == 0.0f) {
                this.playSound("mob.wolf.shake", this.getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f);
            }
            this.prevTimeWolfIsShaking = this.timeWolfIsShaking;
            this.timeWolfIsShaking += 0.05f;
            if (this.prevTimeWolfIsShaking >= 2.0f) {
                this.isShaking = false;
                this.field_70928_h = false;
                this.prevTimeWolfIsShaking = 0.0f;
                this.timeWolfIsShaking = 0.0f;
            }
            if (this.timeWolfIsShaking > 0.4f) {
                final float var1 = (float)this.boundingBox.minY;
                for (int var2 = (int)(MathHelper.sin((this.timeWolfIsShaking - 0.4f) * 3.1415927f) * 7.0f), var3 = 0; var3 < var2; ++var3) {
                    final float var4 = (this.rand.nextFloat() * 2.0f - 1.0f) * this.width * 0.5f;
                    final float var5 = (this.rand.nextFloat() * 2.0f - 1.0f) * this.width * 0.5f;
                    this.worldObj.spawnParticle("splash", this.posX + var4, var1 + 0.8f, this.posZ + var5, this.motionX, this.motionY, this.motionZ);
                }
            }
        }
    }
    
    public boolean getWolfShaking() {
        return this.isShaking;
    }
    
    public float getShadingWhileShaking(final float par1) {
        return 0.75f + (this.prevTimeWolfIsShaking + (this.timeWolfIsShaking - this.prevTimeWolfIsShaking) * par1) / 2.0f * 0.25f;
    }
    
    public float getShakeAngle(final float par1, final float par2) {
        float var3 = (this.prevTimeWolfIsShaking + (this.timeWolfIsShaking - this.prevTimeWolfIsShaking) * par1 + par2) / 1.8f;
        if (var3 < 0.0f) {
            var3 = 0.0f;
        }
        else if (var3 > 1.0f) {
            var3 = 1.0f;
        }
        return MathHelper.sin(var3 * 3.1415927f) * MathHelper.sin(var3 * 3.1415927f * 11.0f) * 0.15f * 3.1415927f;
    }
    
    public float getInterestedAngle(final float par1) {
        return (this.field_70924_f + (this.field_70926_e - this.field_70924_f) * par1) * 0.15f * 3.1415927f;
    }
    
    @Override
    public float getEyeHeight() {
        return this.height * 0.8f;
    }
    
    @Override
    public int getVerticalFaceSpeed() {
        return this.isSitting() ? 20 : super.getVerticalFaceSpeed();
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource par1DamageSource, int par2) {
        if (this.isEntityInvulnerable()) {
            return false;
        }
        final Entity var3 = par1DamageSource.getEntity();
        this.aiSit.setSitting(false);
        if (var3 != null && !(var3 instanceof EntityPlayer) && !(var3 instanceof EntityArrow)) {
            par2 = (par2 + 1) / 2;
        }
        return super.attackEntityFrom(par1DamageSource, par2);
    }
    
    @Override
    public boolean attackEntityAsMob(final Entity par1Entity) {
        final int var2 = this.isTamed() ? 4 : 2;
        return par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this), var2);
    }
    
    @Override
    public boolean interact(final EntityPlayer par1EntityPlayer) {
        final ItemStack var2 = par1EntityPlayer.inventory.getCurrentItem();
        if (this.isTamed()) {
            if (var2 != null) {
                if (Item.itemsList[var2.itemID] instanceof ItemFood) {
                    final ItemFood var3 = (ItemFood)Item.itemsList[var2.itemID];
                    if (var3.isWolfsFavoriteMeat() && this.dataWatcher.getWatchableObjectInt(18) < 20) {
                        if (!par1EntityPlayer.capabilities.isCreativeMode) {
                            final ItemStack itemStack = var2;
                            --itemStack.stackSize;
                        }
                        this.heal(var3.getHealAmount());
                        if (var2.stackSize <= 0) {
                            par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, null);
                        }
                        return true;
                    }
                }
                else if (var2.itemID == Item.dyePowder.itemID) {
                    final int var4 = BlockCloth.getBlockFromDye(var2.getItemDamage());
                    if (var4 != this.getCollarColor()) {
                        this.setCollarColor(var4);
                        if (!par1EntityPlayer.capabilities.isCreativeMode) {
                            final ItemStack itemStack2 = var2;
                            if (--itemStack2.stackSize <= 0) {
                                par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, null);
                            }
                        }
                        return true;
                    }
                }
            }
            if (par1EntityPlayer.username.equalsIgnoreCase(this.getOwnerName()) && !this.worldObj.isRemote && !this.isBreedingItem(var2)) {
                this.aiSit.setSitting(!this.isSitting());
                this.isJumping = false;
                this.setPathToEntity(null);
            }
        }
        else if (var2 != null && var2.itemID == Item.bone.itemID && !this.isAngry()) {
            if (!par1EntityPlayer.capabilities.isCreativeMode) {
                final ItemStack itemStack3 = var2;
                --itemStack3.stackSize;
            }
            if (var2.stackSize <= 0) {
                par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, null);
            }
            if (!this.worldObj.isRemote) {
                if (this.rand.nextInt(3) == 0) {
                    this.setTamed(true);
                    this.setPathToEntity(null);
                    this.setAttackTarget(null);
                    this.aiSit.setSitting(true);
                    this.setEntityHealth(20);
                    this.setOwner(par1EntityPlayer.username);
                    this.playTameEffect(true);
                    this.worldObj.setEntityState(this, (byte)7);
                }
                else {
                    this.playTameEffect(false);
                    this.worldObj.setEntityState(this, (byte)6);
                }
            }
            return true;
        }
        return super.interact(par1EntityPlayer);
    }
    
    @Override
    public void handleHealthUpdate(final byte par1) {
        if (par1 == 8) {
            this.field_70928_h = true;
            this.timeWolfIsShaking = 0.0f;
            this.prevTimeWolfIsShaking = 0.0f;
        }
        else {
            super.handleHealthUpdate(par1);
        }
    }
    
    public float getTailRotation() {
        return this.isAngry() ? 1.5393804f : (this.isTamed() ? ((0.55f - (20 - this.dataWatcher.getWatchableObjectInt(18)) * 0.02f) * 3.1415927f) : 0.62831855f);
    }
    
    @Override
    public boolean isBreedingItem(final ItemStack par1ItemStack) {
        return par1ItemStack != null && Item.itemsList[par1ItemStack.itemID] instanceof ItemFood && ((ItemFood)Item.itemsList[par1ItemStack.itemID]).isWolfsFavoriteMeat();
    }
    
    @Override
    public int getMaxSpawnedInChunk() {
        return 8;
    }
    
    public boolean isAngry() {
        return (this.dataWatcher.getWatchableObjectByte(16) & 0x2) != 0x0;
    }
    
    public void setAngry(final boolean par1) {
        final byte var2 = this.dataWatcher.getWatchableObjectByte(16);
        if (par1) {
            this.dataWatcher.updateObject(16, (byte)(var2 | 0x2));
        }
        else {
            this.dataWatcher.updateObject(16, (byte)(var2 & 0xFFFFFFFD));
        }
    }
    
    public int getCollarColor() {
        return this.dataWatcher.getWatchableObjectByte(20) & 0xF;
    }
    
    public void setCollarColor(final int par1) {
        this.dataWatcher.updateObject(20, (byte)(par1 & 0xF));
    }
    
    public EntityWolf spawnBabyAnimal(final EntityAgeable par1EntityAgeable) {
        final EntityWolf var2 = new EntityWolf(this.worldObj);
        final String var3 = this.getOwnerName();
        if (var3 != null && var3.trim().length() > 0) {
            var2.setOwner(var3);
            var2.setTamed(true);
        }
        return var2;
    }
    
    public void func_70918_i(final boolean par1) {
        final byte var2 = this.dataWatcher.getWatchableObjectByte(19);
        if (par1) {
            this.dataWatcher.updateObject(19, (byte)1);
        }
        else {
            this.dataWatcher.updateObject(19, (byte)0);
        }
    }
    
    @Override
    public boolean canMateWith(final EntityAnimal par1EntityAnimal) {
        if (par1EntityAnimal == this) {
            return false;
        }
        if (!this.isTamed()) {
            return false;
        }
        if (!(par1EntityAnimal instanceof EntityWolf)) {
            return false;
        }
        final EntityWolf var2 = (EntityWolf)par1EntityAnimal;
        return var2.isTamed() && !var2.isSitting() && (this.isInLove() && var2.isInLove());
    }
    
    public boolean func_70922_bv() {
        return this.dataWatcher.getWatchableObjectByte(19) == 1;
    }
    
    @Override
    public EntityAgeable createChild(final EntityAgeable par1EntityAgeable) {
        return this.spawnBabyAnimal(par1EntityAgeable);
    }
}

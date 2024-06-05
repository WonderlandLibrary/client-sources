package net.minecraft.src;

public class EntityOcelot extends EntityTameable
{
    private EntityAITempt aiTempt;
    
    public EntityOcelot(final World par1World) {
        super(par1World);
        this.texture = "/mob/ozelot.png";
        this.setSize(0.6f, 0.8f);
        this.getNavigator().setAvoidsWater(true);
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, this.aiSit);
        this.tasks.addTask(3, this.aiTempt = new EntityAITempt(this, 0.18f, Item.fishRaw.itemID, true));
        this.tasks.addTask(4, new EntityAIAvoidEntity(this, EntityPlayer.class, 16.0f, 0.23f, 0.4f));
        this.tasks.addTask(5, new EntityAIFollowOwner(this, 0.3f, 10.0f, 5.0f));
        this.tasks.addTask(6, new EntityAIOcelotSit(this, 0.4f));
        this.tasks.addTask(7, new EntityAILeapAtTarget(this, 0.3f));
        this.tasks.addTask(8, new EntityAIOcelotAttack(this));
        this.tasks.addTask(9, new EntityAIMate(this, 0.23f));
        this.tasks.addTask(10, new EntityAIWander(this, 0.23f));
        this.tasks.addTask(11, new EntityAIWatchClosest(this, EntityPlayer.class, 10.0f));
        this.targetTasks.addTask(1, new EntityAITargetNonTamed(this, EntityChicken.class, 14.0f, 750, false));
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(18, (byte)0);
    }
    
    public void updateAITick() {
        if (this.getMoveHelper().isUpdating()) {
            final float var1 = this.getMoveHelper().getSpeed();
            if (var1 == 0.18f) {
                this.setSneaking(true);
                this.setSprinting(false);
            }
            else if (var1 == 0.4f) {
                this.setSneaking(false);
                this.setSprinting(true);
            }
            else {
                this.setSneaking(false);
                this.setSprinting(false);
            }
        }
        else {
            this.setSneaking(false);
            this.setSprinting(false);
        }
    }
    
    @Override
    protected boolean canDespawn() {
        return !this.isTamed();
    }
    
    @Override
    public String getTexture() {
        switch (this.getTameSkin()) {
            case 0: {
                return "/mob/ozelot.png";
            }
            case 1: {
                return "/mob/cat_black.png";
            }
            case 2: {
                return "/mob/cat_red.png";
            }
            case 3: {
                return "/mob/cat_siamese.png";
            }
            default: {
                return super.getTexture();
            }
        }
    }
    
    public boolean isAIEnabled() {
        return true;
    }
    
    @Override
    public int getMaxHealth() {
        return 10;
    }
    
    @Override
    protected void fall(final float par1) {
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setInteger("CatType", this.getTameSkin());
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.setTameSkin(par1NBTTagCompound.getInteger("CatType"));
    }
    
    @Override
    protected String getLivingSound() {
        return this.isTamed() ? (this.isInLove() ? "mob.cat.purr" : ((this.rand.nextInt(4) == 0) ? "mob.cat.purreow" : "mob.cat.meow")) : "";
    }
    
    @Override
    protected String getHurtSound() {
        return "mob.cat.hitt";
    }
    
    @Override
    protected String getDeathSound() {
        return "mob.cat.hitt";
    }
    
    @Override
    protected float getSoundVolume() {
        return 0.4f;
    }
    
    @Override
    protected int getDropItemId() {
        return Item.leather.itemID;
    }
    
    @Override
    public boolean attackEntityAsMob(final Entity par1Entity) {
        return par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this), 3);
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource par1DamageSource, final int par2) {
        if (this.isEntityInvulnerable()) {
            return false;
        }
        this.aiSit.setSitting(false);
        return super.attackEntityFrom(par1DamageSource, par2);
    }
    
    @Override
    protected void dropFewItems(final boolean par1, final int par2) {
    }
    
    @Override
    public boolean interact(final EntityPlayer par1EntityPlayer) {
        final ItemStack var2 = par1EntityPlayer.inventory.getCurrentItem();
        if (this.isTamed()) {
            if (par1EntityPlayer.username.equalsIgnoreCase(this.getOwnerName()) && !this.worldObj.isRemote && !this.isBreedingItem(var2)) {
                this.aiSit.setSitting(!this.isSitting());
            }
        }
        else if (this.aiTempt.func_75277_f() && var2 != null && var2.itemID == Item.fishRaw.itemID && par1EntityPlayer.getDistanceSqToEntity(this) < 9.0) {
            if (!par1EntityPlayer.capabilities.isCreativeMode) {
                final ItemStack itemStack = var2;
                --itemStack.stackSize;
            }
            if (var2.stackSize <= 0) {
                par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, null);
            }
            if (!this.worldObj.isRemote) {
                if (this.rand.nextInt(3) == 0) {
                    this.setTamed(true);
                    this.setTameSkin(1 + this.worldObj.rand.nextInt(3));
                    this.setOwner(par1EntityPlayer.username);
                    this.playTameEffect(true);
                    this.aiSit.setSitting(true);
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
    
    public EntityOcelot spawnBabyAnimal(final EntityAgeable par1EntityAgeable) {
        final EntityOcelot var2 = new EntityOcelot(this.worldObj);
        if (this.isTamed()) {
            var2.setOwner(this.getOwnerName());
            var2.setTamed(true);
            var2.setTameSkin(this.getTameSkin());
        }
        return var2;
    }
    
    @Override
    public boolean isBreedingItem(final ItemStack par1ItemStack) {
        return par1ItemStack != null && par1ItemStack.itemID == Item.fishRaw.itemID;
    }
    
    @Override
    public boolean canMateWith(final EntityAnimal par1EntityAnimal) {
        if (par1EntityAnimal == this) {
            return false;
        }
        if (!this.isTamed()) {
            return false;
        }
        if (!(par1EntityAnimal instanceof EntityOcelot)) {
            return false;
        }
        final EntityOcelot var2 = (EntityOcelot)par1EntityAnimal;
        return var2.isTamed() && (this.isInLove() && var2.isInLove());
    }
    
    public int getTameSkin() {
        return this.dataWatcher.getWatchableObjectByte(18);
    }
    
    public void setTameSkin(final int par1) {
        this.dataWatcher.updateObject(18, (byte)par1);
    }
    
    @Override
    public boolean getCanSpawnHere() {
        if (this.worldObj.rand.nextInt(3) == 0) {
            return false;
        }
        if (this.worldObj.checkNoEntityCollision(this.boundingBox) && this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).isEmpty() && !this.worldObj.isAnyLiquid(this.boundingBox)) {
            final int var1 = MathHelper.floor_double(this.posX);
            final int var2 = MathHelper.floor_double(this.boundingBox.minY);
            final int var3 = MathHelper.floor_double(this.posZ);
            if (var2 < 63) {
                return false;
            }
            final int var4 = this.worldObj.getBlockId(var1, var2 - 1, var3);
            if (var4 == Block.grass.blockID || var4 == Block.leaves.blockID) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public String getEntityName() {
        return this.func_94056_bM() ? this.func_94057_bL() : (this.isTamed() ? "entity.Cat.name" : super.getEntityName());
    }
    
    @Override
    public void initCreature() {
        if (this.worldObj.rand.nextInt(7) == 0) {
            for (int var1 = 0; var1 < 2; ++var1) {
                final EntityOcelot var2 = new EntityOcelot(this.worldObj);
                var2.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0f);
                var2.setGrowingAge(-24000);
                this.worldObj.spawnEntityInWorld(var2);
            }
        }
    }
    
    @Override
    public EntityAgeable createChild(final EntityAgeable par1EntityAgeable) {
        return this.spawnBabyAnimal(par1EntityAgeable);
    }
}

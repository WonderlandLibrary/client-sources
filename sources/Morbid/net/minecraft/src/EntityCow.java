package net.minecraft.src;

public class EntityCow extends EntityAnimal
{
    public EntityCow(final World par1World) {
        super(par1World);
        this.texture = "/mob/cow.png";
        this.setSize(0.9f, 1.3f);
        this.getNavigator().setAvoidsWater(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIPanic(this, 0.38f));
        this.tasks.addTask(2, new EntityAIMate(this, 0.2f));
        this.tasks.addTask(3, new EntityAITempt(this, 0.25f, Item.wheat.itemID, false));
        this.tasks.addTask(4, new EntityAIFollowParent(this, 0.25f));
        this.tasks.addTask(5, new EntityAIWander(this, 0.2f));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0f));
        this.tasks.addTask(7, new EntityAILookIdle(this));
    }
    
    public boolean isAIEnabled() {
        return true;
    }
    
    @Override
    public int getMaxHealth() {
        return 10;
    }
    
    @Override
    protected String getLivingSound() {
        return "mob.cow.say";
    }
    
    @Override
    protected String getHurtSound() {
        return "mob.cow.hurt";
    }
    
    @Override
    protected String getDeathSound() {
        return "mob.cow.hurt";
    }
    
    @Override
    protected void playStepSound(final int par1, final int par2, final int par3, final int par4) {
        this.playSound("mob.cow.step", 0.15f, 1.0f);
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
    protected void dropFewItems(final boolean par1, final int par2) {
        for (int var3 = this.rand.nextInt(3) + this.rand.nextInt(1 + par2), var4 = 0; var4 < var3; ++var4) {
            this.dropItem(Item.leather.itemID, 1);
        }
        for (int var3 = this.rand.nextInt(3) + 1 + this.rand.nextInt(1 + par2), var4 = 0; var4 < var3; ++var4) {
            if (this.isBurning()) {
                this.dropItem(Item.beefCooked.itemID, 1);
            }
            else {
                this.dropItem(Item.beefRaw.itemID, 1);
            }
        }
    }
    
    @Override
    public boolean interact(final EntityPlayer par1EntityPlayer) {
        final ItemStack var2 = par1EntityPlayer.inventory.getCurrentItem();
        if (var2 != null && var2.itemID == Item.bucketEmpty.itemID) {
            final ItemStack itemStack = var2;
            if (--itemStack.stackSize <= 0) {
                par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, new ItemStack(Item.bucketMilk));
            }
            else if (!par1EntityPlayer.inventory.addItemStackToInventory(new ItemStack(Item.bucketMilk))) {
                par1EntityPlayer.dropPlayerItem(new ItemStack(Item.bucketMilk.itemID, 1, 0));
            }
            return true;
        }
        return super.interact(par1EntityPlayer);
    }
    
    public EntityCow spawnBabyAnimal(final EntityAgeable par1EntityAgeable) {
        return new EntityCow(this.worldObj);
    }
    
    @Override
    public EntityAgeable createChild(final EntityAgeable par1EntityAgeable) {
        return this.spawnBabyAnimal(par1EntityAgeable);
    }
}

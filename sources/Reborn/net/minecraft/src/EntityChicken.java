package net.minecraft.src;

public class EntityChicken extends EntityAnimal
{
    public boolean field_70885_d;
    public float field_70886_e;
    public float destPos;
    public float field_70884_g;
    public float field_70888_h;
    public float field_70889_i;
    public int timeUntilNextEgg;
    
    public EntityChicken(final World par1World) {
        super(par1World);
        this.field_70885_d = false;
        this.field_70886_e = 0.0f;
        this.destPos = 0.0f;
        this.field_70889_i = 1.0f;
        this.texture = "/mob/chicken.png";
        this.setSize(0.3f, 0.7f);
        this.timeUntilNextEgg = this.rand.nextInt(6000) + 6000;
        final float var2 = 0.25f;
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIPanic(this, 0.38f));
        this.tasks.addTask(2, new EntityAIMate(this, var2));
        this.tasks.addTask(3, new EntityAITempt(this, 0.25f, Item.seeds.itemID, false));
        this.tasks.addTask(4, new EntityAIFollowParent(this, 0.28f));
        this.tasks.addTask(5, new EntityAIWander(this, var2));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0f));
        this.tasks.addTask(7, new EntityAILookIdle(this));
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
        this.field_70888_h = this.field_70886_e;
        this.field_70884_g = this.destPos;
        this.destPos += (float)((this.onGround ? -1 : 4) * 0.3);
        if (this.destPos < 0.0f) {
            this.destPos = 0.0f;
        }
        if (this.destPos > 1.0f) {
            this.destPos = 1.0f;
        }
        if (!this.onGround && this.field_70889_i < 1.0f) {
            this.field_70889_i = 1.0f;
        }
        this.field_70889_i *= 0.9;
        if (!this.onGround && this.motionY < 0.0) {
            this.motionY *= 0.6;
        }
        this.field_70886_e += this.field_70889_i * 2.0f;
        if (!this.isChild() && !this.worldObj.isRemote && --this.timeUntilNextEgg <= 0) {
            this.playSound("mob.chicken.plop", 1.0f, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f);
            this.dropItem(Item.egg.itemID, 1);
            this.timeUntilNextEgg = this.rand.nextInt(6000) + 6000;
        }
    }
    
    @Override
    protected void fall(final float par1) {
    }
    
    @Override
    protected String getLivingSound() {
        return "mob.chicken.say";
    }
    
    @Override
    protected String getHurtSound() {
        return "mob.chicken.hurt";
    }
    
    @Override
    protected String getDeathSound() {
        return "mob.chicken.hurt";
    }
    
    @Override
    protected void playStepSound(final int par1, final int par2, final int par3, final int par4) {
        this.playSound("mob.chicken.step", 0.15f, 1.0f);
    }
    
    @Override
    protected int getDropItemId() {
        return Item.feather.itemID;
    }
    
    @Override
    protected void dropFewItems(final boolean par1, final int par2) {
        for (int var3 = this.rand.nextInt(3) + this.rand.nextInt(1 + par2), var4 = 0; var4 < var3; ++var4) {
            this.dropItem(Item.feather.itemID, 1);
        }
        if (this.isBurning()) {
            this.dropItem(Item.chickenCooked.itemID, 1);
        }
        else {
            this.dropItem(Item.chickenRaw.itemID, 1);
        }
    }
    
    public EntityChicken spawnBabyAnimal(final EntityAgeable par1EntityAgeable) {
        return new EntityChicken(this.worldObj);
    }
    
    @Override
    public boolean isBreedingItem(final ItemStack par1ItemStack) {
        return par1ItemStack != null && par1ItemStack.getItem() instanceof ItemSeeds;
    }
    
    @Override
    public EntityAgeable createChild(final EntityAgeable par1EntityAgeable) {
        return this.spawnBabyAnimal(par1EntityAgeable);
    }
}

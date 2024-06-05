package net.minecraft.src;

import java.util.*;

public class EntitySheep extends EntityAnimal
{
    private final InventoryCrafting field_90016_e;
    public static final float[][] fleeceColorTable;
    private int sheepTimer;
    private EntityAIEatGrass aiEatGrass;
    
    static {
        fleeceColorTable = new float[][] { { 1.0f, 1.0f, 1.0f }, { 0.85f, 0.5f, 0.2f }, { 0.7f, 0.3f, 0.85f }, { 0.4f, 0.6f, 0.85f }, { 0.9f, 0.9f, 0.2f }, { 0.5f, 0.8f, 0.1f }, { 0.95f, 0.5f, 0.65f }, { 0.3f, 0.3f, 0.3f }, { 0.6f, 0.6f, 0.6f }, { 0.3f, 0.5f, 0.6f }, { 0.5f, 0.25f, 0.7f }, { 0.2f, 0.3f, 0.7f }, { 0.4f, 0.3f, 0.2f }, { 0.4f, 0.5f, 0.2f }, { 0.6f, 0.2f, 0.2f }, { 0.1f, 0.1f, 0.1f } };
    }
    
    public EntitySheep(final World par1World) {
        super(par1World);
        this.field_90016_e = new InventoryCrafting(new ContainerSheep(this), 2, 1);
        this.aiEatGrass = new EntityAIEatGrass(this);
        this.texture = "/mob/sheep.png";
        this.setSize(0.9f, 1.3f);
        final float var2 = 0.23f;
        this.getNavigator().setAvoidsWater(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIPanic(this, 0.38f));
        this.tasks.addTask(2, new EntityAIMate(this, var2));
        this.tasks.addTask(3, new EntityAITempt(this, 0.25f, Item.wheat.itemID, false));
        this.tasks.addTask(4, new EntityAIFollowParent(this, 0.25f));
        this.tasks.addTask(5, this.aiEatGrass);
        this.tasks.addTask(6, new EntityAIWander(this, var2));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0f));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.field_90016_e.setInventorySlotContents(0, new ItemStack(Item.dyePowder, 1, 0));
        this.field_90016_e.setInventorySlotContents(1, new ItemStack(Item.dyePowder, 1, 0));
    }
    
    @Override
    protected boolean isAIEnabled() {
        return true;
    }
    
    @Override
    protected void updateAITasks() {
        this.sheepTimer = this.aiEatGrass.getEatGrassTick();
        super.updateAITasks();
    }
    
    @Override
    public void onLivingUpdate() {
        if (this.worldObj.isRemote) {
            this.sheepTimer = Math.max(0, this.sheepTimer - 1);
        }
        super.onLivingUpdate();
    }
    
    @Override
    public int getMaxHealth() {
        return 8;
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, new Byte((byte)0));
    }
    
    @Override
    protected void dropFewItems(final boolean par1, final int par2) {
        if (!this.getSheared()) {
            this.entityDropItem(new ItemStack(Block.cloth.blockID, 1, this.getFleeceColor()), 0.0f);
        }
    }
    
    @Override
    protected int getDropItemId() {
        return Block.cloth.blockID;
    }
    
    @Override
    public void handleHealthUpdate(final byte par1) {
        if (par1 == 10) {
            this.sheepTimer = 40;
        }
        else {
            super.handleHealthUpdate(par1);
        }
    }
    
    public float func_70894_j(final float par1) {
        return (this.sheepTimer <= 0) ? 0.0f : ((this.sheepTimer >= 4 && this.sheepTimer <= 36) ? 1.0f : ((this.sheepTimer < 4) ? ((this.sheepTimer - par1) / 4.0f) : (-(this.sheepTimer - 40 - par1) / 4.0f)));
    }
    
    public float func_70890_k(final float par1) {
        if (this.sheepTimer > 4 && this.sheepTimer <= 36) {
            final float var2 = (this.sheepTimer - 4 - par1) / 32.0f;
            return 0.62831855f + 0.2199115f * MathHelper.sin(var2 * 28.7f);
        }
        return (this.sheepTimer > 0) ? 0.62831855f : (this.rotationPitch / 57.295776f);
    }
    
    @Override
    public boolean interact(final EntityPlayer par1EntityPlayer) {
        final ItemStack var2 = par1EntityPlayer.inventory.getCurrentItem();
        if (var2 != null && var2.itemID == Item.shears.itemID && !this.getSheared() && !this.isChild()) {
            if (!this.worldObj.isRemote) {
                this.setSheared(true);
                for (int var3 = 1 + this.rand.nextInt(3), var4 = 0; var4 < var3; ++var4) {
                    final EntityItem entityDropItem;
                    final EntityItem var5 = entityDropItem = this.entityDropItem(new ItemStack(Block.cloth.blockID, 1, this.getFleeceColor()), 1.0f);
                    entityDropItem.motionY += this.rand.nextFloat() * 0.05f;
                    final EntityItem entityItem = var5;
                    entityItem.motionX += (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1f;
                    final EntityItem entityItem2 = var5;
                    entityItem2.motionZ += (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1f;
                }
            }
            var2.damageItem(1, par1EntityPlayer);
            this.playSound("mob.sheep.shear", 1.0f, 1.0f);
        }
        return super.interact(par1EntityPlayer);
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setBoolean("Sheared", this.getSheared());
        par1NBTTagCompound.setByte("Color", (byte)this.getFleeceColor());
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.setSheared(par1NBTTagCompound.getBoolean("Sheared"));
        this.setFleeceColor(par1NBTTagCompound.getByte("Color"));
    }
    
    @Override
    protected String getLivingSound() {
        return "mob.sheep.say";
    }
    
    @Override
    protected String getHurtSound() {
        return "mob.sheep.say";
    }
    
    @Override
    protected String getDeathSound() {
        return "mob.sheep.say";
    }
    
    @Override
    protected void playStepSound(final int par1, final int par2, final int par3, final int par4) {
        this.playSound("mob.sheep.step", 0.15f, 1.0f);
    }
    
    public int getFleeceColor() {
        return this.dataWatcher.getWatchableObjectByte(16) & 0xF;
    }
    
    public void setFleeceColor(final int par1) {
        final byte var2 = this.dataWatcher.getWatchableObjectByte(16);
        this.dataWatcher.updateObject(16, (byte)((var2 & 0xF0) | (par1 & 0xF)));
    }
    
    public boolean getSheared() {
        return (this.dataWatcher.getWatchableObjectByte(16) & 0x10) != 0x0;
    }
    
    public void setSheared(final boolean par1) {
        final byte var2 = this.dataWatcher.getWatchableObjectByte(16);
        if (par1) {
            this.dataWatcher.updateObject(16, (byte)(var2 | 0x10));
        }
        else {
            this.dataWatcher.updateObject(16, (byte)(var2 & 0xFFFFFFEF));
        }
    }
    
    public static int getRandomFleeceColor(final Random par0Random) {
        final int var1 = par0Random.nextInt(100);
        return (var1 < 5) ? 15 : ((var1 < 10) ? 7 : ((var1 < 15) ? 8 : ((var1 < 18) ? 12 : ((par0Random.nextInt(500) == 0) ? 6 : 0))));
    }
    
    public EntitySheep func_90015_b(final EntityAgeable par1EntityAgeable) {
        final EntitySheep var2 = (EntitySheep)par1EntityAgeable;
        final EntitySheep var3 = new EntitySheep(this.worldObj);
        final int var4 = this.func_90014_a(this, var2);
        var3.setFleeceColor(15 - var4);
        return var3;
    }
    
    @Override
    public void eatGrassBonus() {
        this.setSheared(false);
        if (this.isChild()) {
            int var1 = this.getGrowingAge() + 1200;
            if (var1 > 0) {
                var1 = 0;
            }
            this.setGrowingAge(var1);
        }
    }
    
    @Override
    public void initCreature() {
        this.setFleeceColor(getRandomFleeceColor(this.worldObj.rand));
    }
    
    private int func_90014_a(final EntityAnimal par1EntityAnimal, final EntityAnimal par2EntityAnimal) {
        final int var3 = this.func_90013_b(par1EntityAnimal);
        final int var4 = this.func_90013_b(par2EntityAnimal);
        this.field_90016_e.getStackInSlot(0).setItemDamage(var3);
        this.field_90016_e.getStackInSlot(1).setItemDamage(var4);
        final ItemStack var5 = CraftingManager.getInstance().findMatchingRecipe(this.field_90016_e, ((EntitySheep)par1EntityAnimal).worldObj);
        int var6;
        if (var5 != null && var5.getItem().itemID == Item.dyePowder.itemID) {
            var6 = var5.getItemDamage();
        }
        else {
            var6 = (this.worldObj.rand.nextBoolean() ? var3 : var4);
        }
        return var6;
    }
    
    private int func_90013_b(final EntityAnimal par1EntityAnimal) {
        return 15 - ((EntitySheep)par1EntityAnimal).getFleeceColor();
    }
    
    @Override
    public EntityAgeable createChild(final EntityAgeable par1EntityAgeable) {
        return this.func_90015_b(par1EntityAgeable);
    }
}

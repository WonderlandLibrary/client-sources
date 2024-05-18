package net.minecraft.src;

import java.util.*;

public class EntityZombie extends EntityMob
{
    private int conversionTime;
    
    public EntityZombie(final World par1World) {
        super(par1World);
        this.conversionTime = 0;
        this.texture = "/mob/zombie.png";
        this.moveSpeed = 0.23f;
        this.getNavigator().setBreakDoors(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIBreakDoor(this));
        this.tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, this.moveSpeed, false));
        this.tasks.addTask(3, new EntityAIAttackOnCollide(this, EntityVillager.class, this.moveSpeed, true));
        this.tasks.addTask(4, new EntityAIMoveTwardsRestriction(this, this.moveSpeed));
        this.tasks.addTask(5, new EntityAIMoveThroughVillage(this, this.moveSpeed, false));
        this.tasks.addTask(6, new EntityAIWander(this, this.moveSpeed));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0f));
        this.tasks.addTask(7, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 16.0f, 0, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityVillager.class, 16.0f, 0, false));
    }
    
    @Override
    protected int func_96121_ay() {
        return 40;
    }
    
    @Override
    public float getSpeedModifier() {
        return super.getSpeedModifier() * (this.isChild() ? 1.5f : 1.0f);
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.getDataWatcher().addObject(12, (byte)0);
        this.getDataWatcher().addObject(13, (byte)0);
        this.getDataWatcher().addObject(14, (byte)0);
    }
    
    @Override
    public String getTexture() {
        return this.isVillager() ? "/mob/zombie_villager.png" : "/mob/zombie.png";
    }
    
    @Override
    public int getMaxHealth() {
        return 20;
    }
    
    @Override
    public int getTotalArmorValue() {
        int var1 = super.getTotalArmorValue() + 2;
        if (var1 > 20) {
            var1 = 20;
        }
        return var1;
    }
    
    @Override
    protected boolean isAIEnabled() {
        return true;
    }
    
    @Override
    public boolean isChild() {
        return this.getDataWatcher().getWatchableObjectByte(12) == 1;
    }
    
    public void setChild(final boolean par1) {
        this.getDataWatcher().updateObject(12, (byte)1);
    }
    
    public boolean isVillager() {
        return this.getDataWatcher().getWatchableObjectByte(13) == 1;
    }
    
    public void setVillager(final boolean par1) {
        this.getDataWatcher().updateObject(13, (byte)(byte)(par1 ? 1 : 0));
    }
    
    @Override
    public void onLivingUpdate() {
        if (this.worldObj.isDaytime() && !this.worldObj.isRemote && !this.isChild()) {
            final float var1 = this.getBrightness(1.0f);
            if (var1 > 0.5f && this.rand.nextFloat() * 30.0f < (var1 - 0.4f) * 2.0f && this.worldObj.canBlockSeeTheSky(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ))) {
                boolean var2 = true;
                final ItemStack var3 = this.getCurrentItemOrArmor(4);
                if (var3 != null) {
                    if (var3.isItemStackDamageable()) {
                        var3.setItemDamage(var3.getItemDamageForDisplay() + this.rand.nextInt(2));
                        if (var3.getItemDamageForDisplay() >= var3.getMaxDamage()) {
                            this.renderBrokenItemStack(var3);
                            this.setCurrentItemOrArmor(4, null);
                        }
                    }
                    var2 = false;
                }
                if (var2) {
                    this.setFire(8);
                }
            }
        }
        super.onLivingUpdate();
    }
    
    @Override
    public void onUpdate() {
        if (!this.worldObj.isRemote && this.isConverting()) {
            final int var1 = this.getConversionTimeBoost();
            this.conversionTime -= var1;
            if (this.conversionTime <= 0) {
                this.convertToVillager();
            }
        }
        super.onUpdate();
    }
    
    @Override
    public boolean attackEntityAsMob(final Entity par1Entity) {
        final boolean var2 = super.attackEntityAsMob(par1Entity);
        if (var2 && this.getHeldItem() == null && this.isBurning() && this.rand.nextFloat() < this.worldObj.difficultySetting * 0.3f) {
            par1Entity.setFire(2 * this.worldObj.difficultySetting);
        }
        return var2;
    }
    
    @Override
    public int getAttackStrength(final Entity par1Entity) {
        final ItemStack var2 = this.getHeldItem();
        final float var3 = (this.getMaxHealth() - this.getHealth()) / this.getMaxHealth();
        int var4 = 3 + MathHelper.floor_float(var3 * 4.0f);
        if (var2 != null) {
            var4 += var2.getDamageVsEntity(this);
        }
        return var4;
    }
    
    @Override
    protected String getLivingSound() {
        return "mob.zombie.say";
    }
    
    @Override
    protected String getHurtSound() {
        return "mob.zombie.hurt";
    }
    
    @Override
    protected String getDeathSound() {
        return "mob.zombie.death";
    }
    
    @Override
    protected void playStepSound(final int par1, final int par2, final int par3, final int par4) {
        this.playSound("mob.zombie.step", 0.15f, 1.0f);
    }
    
    @Override
    protected int getDropItemId() {
        return Item.rottenFlesh.itemID;
    }
    
    @Override
    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.UNDEAD;
    }
    
    @Override
    protected void dropRareDrop(final int par1) {
        switch (this.rand.nextInt(3)) {
            case 0: {
                this.dropItem(Item.ingotIron.itemID, 1);
                break;
            }
            case 1: {
                this.dropItem(Item.carrot.itemID, 1);
                break;
            }
            case 2: {
                this.dropItem(Item.potato.itemID, 1);
                break;
            }
        }
    }
    
    @Override
    protected void addRandomArmor() {
        super.addRandomArmor();
        if (this.rand.nextFloat() < ((this.worldObj.difficultySetting == 3) ? 0.05f : 0.01f)) {
            final int var1 = this.rand.nextInt(3);
            if (var1 == 0) {
                this.setCurrentItemOrArmor(0, new ItemStack(Item.swordIron));
            }
            else {
                this.setCurrentItemOrArmor(0, new ItemStack(Item.shovelIron));
            }
        }
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
        if (this.isChild()) {
            par1NBTTagCompound.setBoolean("IsBaby", true);
        }
        if (this.isVillager()) {
            par1NBTTagCompound.setBoolean("IsVillager", true);
        }
        par1NBTTagCompound.setInteger("ConversionTime", this.isConverting() ? this.conversionTime : -1);
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
        if (par1NBTTagCompound.getBoolean("IsBaby")) {
            this.setChild(true);
        }
        if (par1NBTTagCompound.getBoolean("IsVillager")) {
            this.setVillager(true);
        }
        if (par1NBTTagCompound.hasKey("ConversionTime") && par1NBTTagCompound.getInteger("ConversionTime") > -1) {
            this.startConversion(par1NBTTagCompound.getInteger("ConversionTime"));
        }
    }
    
    @Override
    public void onKillEntity(final EntityLiving par1EntityLiving) {
        super.onKillEntity(par1EntityLiving);
        if (this.worldObj.difficultySetting >= 2 && par1EntityLiving instanceof EntityVillager) {
            if (this.worldObj.difficultySetting == 2 && this.rand.nextBoolean()) {
                return;
            }
            final EntityZombie var2 = new EntityZombie(this.worldObj);
            var2.func_82149_j(par1EntityLiving);
            this.worldObj.removeEntity(par1EntityLiving);
            var2.initCreature();
            var2.setVillager(true);
            if (par1EntityLiving.isChild()) {
                var2.setChild(true);
            }
            this.worldObj.spawnEntityInWorld(var2);
            this.worldObj.playAuxSFXAtEntity(null, 1016, (int)this.posX, (int)this.posY, (int)this.posZ, 0);
        }
    }
    
    @Override
    public void initCreature() {
        this.setCanPickUpLoot(this.rand.nextFloat() < EntityZombie.pickUpLootProability[this.worldObj.difficultySetting]);
        if (this.worldObj.rand.nextFloat() < 0.05f) {
            this.setVillager(true);
        }
        this.addRandomArmor();
        this.func_82162_bC();
        if (this.getCurrentItemOrArmor(4) == null) {
            final Calendar var1 = this.worldObj.getCurrentDate();
            if (var1.get(2) + 1 == 10 && var1.get(5) == 31 && this.rand.nextFloat() < 0.25f) {
                this.setCurrentItemOrArmor(4, new ItemStack((this.rand.nextFloat() < 0.1f) ? Block.pumpkinLantern : Block.pumpkin));
                this.equipmentDropChances[4] = 0.0f;
            }
        }
    }
    
    @Override
    public boolean interact(final EntityPlayer par1EntityPlayer) {
        final ItemStack var2 = par1EntityPlayer.getCurrentEquippedItem();
        if (var2 != null && var2.getItem() == Item.appleGold && var2.getItemDamage() == 0 && this.isVillager() && this.isPotionActive(Potion.weakness)) {
            if (!par1EntityPlayer.capabilities.isCreativeMode) {
                final ItemStack itemStack = var2;
                --itemStack.stackSize;
            }
            if (var2.stackSize <= 0) {
                par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, null);
            }
            if (!this.worldObj.isRemote) {
                this.startConversion(this.rand.nextInt(2401) + 3600);
            }
            return true;
        }
        return false;
    }
    
    protected void startConversion(final int par1) {
        this.conversionTime = par1;
        this.getDataWatcher().updateObject(14, (byte)1);
        this.removePotionEffect(Potion.weakness.id);
        this.addPotionEffect(new PotionEffect(Potion.damageBoost.id, par1, Math.min(this.worldObj.difficultySetting - 1, 0)));
        this.worldObj.setEntityState(this, (byte)16);
    }
    
    @Override
    public void handleHealthUpdate(final byte par1) {
        if (par1 == 16) {
            this.worldObj.playSound(this.posX + 0.5, this.posY + 0.5, this.posZ + 0.5, "mob.zombie.remedy", 1.0f + this.rand.nextFloat(), this.rand.nextFloat() * 0.7f + 0.3f, false);
        }
        else {
            super.handleHealthUpdate(par1);
        }
    }
    
    public boolean isConverting() {
        return this.getDataWatcher().getWatchableObjectByte(14) == 1;
    }
    
    protected void convertToVillager() {
        final EntityVillager var1 = new EntityVillager(this.worldObj);
        var1.func_82149_j(this);
        var1.initCreature();
        var1.func_82187_q();
        if (this.isChild()) {
            var1.setGrowingAge(-24000);
        }
        this.worldObj.removeEntity(this);
        this.worldObj.spawnEntityInWorld(var1);
        var1.addPotionEffect(new PotionEffect(Potion.confusion.id, 200, 0));
        this.worldObj.playAuxSFXAtEntity(null, 1017, (int)this.posX, (int)this.posY, (int)this.posZ, 0);
    }
    
    protected int getConversionTimeBoost() {
        int var1 = 1;
        if (this.rand.nextFloat() < 0.01f) {
            for (int var2 = 0, var3 = (int)this.posX - 4; var3 < (int)this.posX + 4 && var2 < 14; ++var3) {
                for (int var4 = (int)this.posY - 4; var4 < (int)this.posY + 4 && var2 < 14; ++var4) {
                    for (int var5 = (int)this.posZ - 4; var5 < (int)this.posZ + 4 && var2 < 14; ++var5) {
                        final int var6 = this.worldObj.getBlockId(var3, var4, var5);
                        if (var6 == Block.fenceIron.blockID || var6 == Block.bed.blockID) {
                            if (this.rand.nextFloat() < 0.3f) {
                                ++var1;
                            }
                            ++var2;
                        }
                    }
                }
            }
        }
        return var1;
    }
}

package net.minecraft.src;

import java.util.*;

public class EntitySkeleton extends EntityMob implements IRangedAttackMob
{
    private EntityAIArrowAttack aiArrowAttack;
    private EntityAIAttackOnCollide aiAttackOnCollide;
    
    public EntitySkeleton(final World par1World) {
        super(par1World);
        this.aiArrowAttack = new EntityAIArrowAttack(this, 0.25f, 20, 60, 15.0f);
        this.aiAttackOnCollide = new EntityAIAttackOnCollide(this, EntityPlayer.class, 0.31f, false);
        this.texture = "/mob/skeleton.png";
        this.moveSpeed = 0.25f;
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIRestrictSun(this));
        this.tasks.addTask(3, new EntityAIFleeSun(this, this.moveSpeed));
        this.tasks.addTask(5, new EntityAIWander(this, this.moveSpeed));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0f));
        this.tasks.addTask(6, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 16.0f, 0, true));
        if (par1World != null && !par1World.isRemote) {
            this.setCombatTask();
        }
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(13, new Byte((byte)0));
    }
    
    public boolean isAIEnabled() {
        return true;
    }
    
    @Override
    public int getMaxHealth() {
        return 20;
    }
    
    @Override
    protected String getLivingSound() {
        return "mob.skeleton.say";
    }
    
    @Override
    protected String getHurtSound() {
        return "mob.skeleton.hurt";
    }
    
    @Override
    protected String getDeathSound() {
        return "mob.skeleton.death";
    }
    
    @Override
    protected void playStepSound(final int par1, final int par2, final int par3, final int par4) {
        this.playSound("mob.skeleton.step", 0.15f, 1.0f);
    }
    
    @Override
    public boolean attackEntityAsMob(final Entity par1Entity) {
        if (super.attackEntityAsMob(par1Entity)) {
            if (this.getSkeletonType() == 1 && par1Entity instanceof EntityLiving) {
                ((EntityLiving)par1Entity).addPotionEffect(new PotionEffect(Potion.wither.id, 200));
            }
            return true;
        }
        return false;
    }
    
    @Override
    public int getAttackStrength(final Entity par1Entity) {
        if (this.getSkeletonType() == 1) {
            final ItemStack var2 = this.getHeldItem();
            int var3 = 4;
            if (var2 != null) {
                var3 += var2.getDamageVsEntity(this);
            }
            return var3;
        }
        return super.getAttackStrength(par1Entity);
    }
    
    @Override
    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.UNDEAD;
    }
    
    @Override
    public void onLivingUpdate() {
        if (this.worldObj.isDaytime() && !this.worldObj.isRemote) {
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
        if (this.worldObj.isRemote && this.getSkeletonType() == 1) {
            this.setSize(0.72f, 2.34f);
        }
        super.onLivingUpdate();
    }
    
    @Override
    public void onDeath(final DamageSource par1DamageSource) {
        super.onDeath(par1DamageSource);
        if (par1DamageSource.getSourceOfDamage() instanceof EntityArrow && par1DamageSource.getEntity() instanceof EntityPlayer) {
            final EntityPlayer var2 = (EntityPlayer)par1DamageSource.getEntity();
            final double var3 = var2.posX - this.posX;
            final double var4 = var2.posZ - this.posZ;
            if (var3 * var3 + var4 * var4 >= 2500.0) {
                var2.triggerAchievement(AchievementList.snipeSkeleton);
            }
        }
    }
    
    @Override
    protected int getDropItemId() {
        return Item.arrow.itemID;
    }
    
    @Override
    protected void dropFewItems(final boolean par1, final int par2) {
        if (this.getSkeletonType() == 1) {
            for (int var3 = this.rand.nextInt(3 + par2) - 1, var4 = 0; var4 < var3; ++var4) {
                this.dropItem(Item.coal.itemID, 1);
            }
        }
        else {
            for (int var3 = this.rand.nextInt(3 + par2), var4 = 0; var4 < var3; ++var4) {
                this.dropItem(Item.arrow.itemID, 1);
            }
        }
        for (int var3 = this.rand.nextInt(3 + par2), var4 = 0; var4 < var3; ++var4) {
            this.dropItem(Item.bone.itemID, 1);
        }
    }
    
    @Override
    protected void dropRareDrop(final int par1) {
        if (this.getSkeletonType() == 1) {
            this.entityDropItem(new ItemStack(Item.skull.itemID, 1, 1), 0.0f);
        }
    }
    
    @Override
    protected void addRandomArmor() {
        super.addRandomArmor();
        this.setCurrentItemOrArmor(0, new ItemStack(Item.bow));
    }
    
    @Override
    public String getTexture() {
        return (this.getSkeletonType() == 1) ? "/mob/skeleton_wither.png" : super.getTexture();
    }
    
    @Override
    public void initCreature() {
        if (this.worldObj.provider instanceof WorldProviderHell && this.getRNG().nextInt(5) > 0) {
            this.tasks.addTask(4, this.aiAttackOnCollide);
            this.setSkeletonType(1);
            this.setCurrentItemOrArmor(0, new ItemStack(Item.swordStone));
        }
        else {
            this.tasks.addTask(4, this.aiArrowAttack);
            this.addRandomArmor();
            this.func_82162_bC();
        }
        this.setCanPickUpLoot(this.rand.nextFloat() < EntitySkeleton.pickUpLootProability[this.worldObj.difficultySetting]);
        if (this.getCurrentItemOrArmor(4) == null) {
            final Calendar var1 = this.worldObj.getCurrentDate();
            if (var1.get(2) + 1 == 10 && var1.get(5) == 31 && this.rand.nextFloat() < 0.25f) {
                this.setCurrentItemOrArmor(4, new ItemStack((this.rand.nextFloat() < 0.1f) ? Block.pumpkinLantern : Block.pumpkin));
                this.equipmentDropChances[4] = 0.0f;
            }
        }
    }
    
    public void setCombatTask() {
        this.tasks.removeTask(this.aiAttackOnCollide);
        this.tasks.removeTask(this.aiArrowAttack);
        final ItemStack var1 = this.getHeldItem();
        if (var1 != null && var1.itemID == Item.bow.itemID) {
            this.tasks.addTask(4, this.aiArrowAttack);
        }
        else {
            this.tasks.addTask(4, this.aiAttackOnCollide);
        }
    }
    
    @Override
    public void attackEntityWithRangedAttack(final EntityLiving par1EntityLiving, final float par2) {
        final EntityArrow var3 = new EntityArrow(this.worldObj, this, par1EntityLiving, 1.6f, 14 - this.worldObj.difficultySetting * 4);
        final int var4 = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, this.getHeldItem());
        final int var5 = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, this.getHeldItem());
        var3.setDamage(par2 * 2.0f + this.rand.nextGaussian() * 0.25 + this.worldObj.difficultySetting * 0.11f);
        if (var4 > 0) {
            var3.setDamage(var3.getDamage() + var4 * 0.5 + 0.5);
        }
        if (var5 > 0) {
            var3.setKnockbackStrength(var5);
        }
        if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, this.getHeldItem()) > 0 || this.getSkeletonType() == 1) {
            var3.setFire(100);
        }
        this.playSound("random.bow", 1.0f, 1.0f / (this.getRNG().nextFloat() * 0.4f + 0.8f));
        this.worldObj.spawnEntityInWorld(var3);
    }
    
    public int getSkeletonType() {
        return this.dataWatcher.getWatchableObjectByte(13);
    }
    
    public void setSkeletonType(final int par1) {
        this.dataWatcher.updateObject(13, (byte)par1);
        this.isImmuneToFire = (par1 == 1);
        if (par1 == 1) {
            this.setSize(0.72f, 2.34f);
        }
        else {
            this.setSize(0.6f, 1.8f);
        }
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
        if (par1NBTTagCompound.hasKey("SkeletonType")) {
            final byte var2 = par1NBTTagCompound.getByte("SkeletonType");
            this.setSkeletonType(var2);
        }
        this.setCombatTask();
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setByte("SkeletonType", (byte)this.getSkeletonType());
    }
    
    @Override
    public void setCurrentItemOrArmor(final int par1, final ItemStack par2ItemStack) {
        super.setCurrentItemOrArmor(par1, par2ItemStack);
        if (!this.worldObj.isRemote && par1 == 0) {
            this.setCombatTask();
        }
    }
}

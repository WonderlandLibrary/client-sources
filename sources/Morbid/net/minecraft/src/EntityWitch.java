package net.minecraft.src;

import java.util.*;

public class EntityWitch extends EntityMob implements IRangedAttackMob
{
    private static final int[] witchDrops;
    private int witchAttackTimer;
    
    static {
        witchDrops = new int[] { Item.lightStoneDust.itemID, Item.sugar.itemID, Item.redstone.itemID, Item.spiderEye.itemID, Item.glassBottle.itemID, Item.gunpowder.itemID, Item.stick.itemID, Item.stick.itemID };
    }
    
    public EntityWitch(final World par1World) {
        super(par1World);
        this.witchAttackTimer = 0;
        this.texture = "/mob/villager/witch.png";
        this.moveSpeed = 0.25f;
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIArrowAttack(this, this.moveSpeed, 60, 10.0f));
        this.tasks.addTask(2, new EntityAIWander(this, this.moveSpeed));
        this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0f));
        this.tasks.addTask(3, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 16.0f, 0, true));
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.getDataWatcher().addObject(21, (byte)0);
    }
    
    @Override
    protected String getLivingSound() {
        return "mob.witch.idle";
    }
    
    @Override
    protected String getHurtSound() {
        return "mob.witch.hurt";
    }
    
    @Override
    protected String getDeathSound() {
        return "mob.witch.death";
    }
    
    public void setAggressive(final boolean par1) {
        this.getDataWatcher().updateObject(21, (byte)(byte)(par1 ? 1 : 0));
    }
    
    public boolean getAggressive() {
        return this.getDataWatcher().getWatchableObjectByte(21) == 1;
    }
    
    @Override
    public int getMaxHealth() {
        return 26;
    }
    
    public boolean isAIEnabled() {
        return true;
    }
    
    @Override
    public void onLivingUpdate() {
        if (!this.worldObj.isRemote) {
            if (this.getAggressive()) {
                if (this.witchAttackTimer-- <= 0) {
                    this.setAggressive(false);
                    final ItemStack var1 = this.getHeldItem();
                    this.setCurrentItemOrArmor(0, null);
                    if (var1 != null && var1.itemID == Item.potion.itemID) {
                        final List var2 = Item.potion.getEffects(var1);
                        if (var2 != null) {
                            for (final PotionEffect var4 : var2) {
                                this.addPotionEffect(new PotionEffect(var4));
                            }
                        }
                    }
                }
            }
            else {
                short var5 = -1;
                if (this.rand.nextFloat() < 0.15f && this.isBurning() && !this.isPotionActive(Potion.fireResistance)) {
                    var5 = 16307;
                }
                else if (this.rand.nextFloat() < 0.05f && this.health < this.getMaxHealth()) {
                    var5 = 16341;
                }
                else if (this.rand.nextFloat() < 0.25f && this.getAttackTarget() != null && !this.isPotionActive(Potion.moveSpeed) && this.getAttackTarget().getDistanceSqToEntity(this) > 121.0) {
                    var5 = 16274;
                }
                else if (this.rand.nextFloat() < 0.25f && this.getAttackTarget() != null && !this.isPotionActive(Potion.moveSpeed) && this.getAttackTarget().getDistanceSqToEntity(this) > 121.0) {
                    var5 = 16274;
                }
                if (var5 > -1) {
                    this.setCurrentItemOrArmor(0, new ItemStack(Item.potion, 1, var5));
                    this.witchAttackTimer = this.getHeldItem().getMaxItemUseDuration();
                    this.setAggressive(true);
                }
            }
            if (this.rand.nextFloat() < 7.5E-4f) {
                this.worldObj.setEntityState(this, (byte)15);
            }
        }
        super.onLivingUpdate();
    }
    
    @Override
    public void handleHealthUpdate(final byte par1) {
        if (par1 == 15) {
            for (int var2 = 0; var2 < this.rand.nextInt(35) + 10; ++var2) {
                this.worldObj.spawnParticle("witchMagic", this.posX + this.rand.nextGaussian() * 0.12999999523162842, this.boundingBox.maxY + 0.5 + this.rand.nextGaussian() * 0.12999999523162842, this.posZ + this.rand.nextGaussian() * 0.12999999523162842, 0.0, 0.0, 0.0);
            }
        }
        else {
            super.handleHealthUpdate(par1);
        }
    }
    
    @Override
    protected int applyPotionDamageCalculations(final DamageSource par1DamageSource, int par2) {
        par2 = super.applyPotionDamageCalculations(par1DamageSource, par2);
        if (par1DamageSource.getEntity() == this) {
            par2 = 0;
        }
        if (par1DamageSource.isMagicDamage()) {
            par2 *= (int)0.15;
        }
        return par2;
    }
    
    @Override
    public float getSpeedModifier() {
        float var1 = super.getSpeedModifier();
        if (this.getAggressive()) {
            var1 *= 0.75f;
        }
        return var1;
    }
    
    @Override
    protected void dropFewItems(final boolean par1, final int par2) {
        for (int var3 = this.rand.nextInt(3) + 1, var4 = 0; var4 < var3; ++var4) {
            int var5 = this.rand.nextInt(3);
            final int var6 = EntityWitch.witchDrops[this.rand.nextInt(EntityWitch.witchDrops.length)];
            if (par2 > 0) {
                var5 += this.rand.nextInt(par2 + 1);
            }
            for (int var7 = 0; var7 < var5; ++var7) {
                this.dropItem(var6, 1);
            }
        }
    }
    
    @Override
    public void attackEntityWithRangedAttack(final EntityLiving par1EntityLiving, final float par2) {
        if (!this.getAggressive()) {
            final EntityPotion entityPotion;
            final EntityPotion var3 = entityPotion = new EntityPotion(this.worldObj, this, 32732);
            entityPotion.rotationPitch += 20.0f;
            final double var4 = par1EntityLiving.posX + par1EntityLiving.motionX - this.posX;
            final double var5 = par1EntityLiving.posY + par1EntityLiving.getEyeHeight() - 1.100000023841858 - this.posY;
            final double var6 = par1EntityLiving.posZ + par1EntityLiving.motionZ - this.posZ;
            final float var7 = MathHelper.sqrt_double(var4 * var4 + var6 * var6);
            if (var7 >= 8.0f && !par1EntityLiving.isPotionActive(Potion.moveSlowdown)) {
                var3.setPotionDamage(32698);
            }
            else if (par1EntityLiving.getHealth() >= 8 && !par1EntityLiving.isPotionActive(Potion.poison)) {
                var3.setPotionDamage(32660);
            }
            else if (var7 <= 3.0f && !par1EntityLiving.isPotionActive(Potion.weakness) && this.rand.nextFloat() < 0.25f) {
                var3.setPotionDamage(32696);
            }
            var3.setThrowableHeading(var4, var5 + var7 * 0.2f, var6, 0.75f, 8.0f);
            this.worldObj.spawnEntityInWorld(var3);
        }
    }
}

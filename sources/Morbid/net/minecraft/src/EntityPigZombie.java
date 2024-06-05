package net.minecraft.src;

import java.util.*;

public class EntityPigZombie extends EntityZombie
{
    private int angerLevel;
    private int randomSoundDelay;
    
    public EntityPigZombie(final World par1World) {
        super(par1World);
        this.angerLevel = 0;
        this.randomSoundDelay = 0;
        this.texture = "/mob/pigzombie.png";
        this.moveSpeed = 0.5f;
        this.isImmuneToFire = true;
    }
    
    @Override
    protected boolean isAIEnabled() {
        return false;
    }
    
    @Override
    public void onUpdate() {
        this.moveSpeed = ((this.entityToAttack != null) ? 0.95f : 0.5f);
        if (this.randomSoundDelay > 0 && --this.randomSoundDelay == 0) {
            this.playSound("mob.zombiepig.zpigangry", this.getSoundVolume() * 2.0f, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f) * 1.8f);
        }
        super.onUpdate();
    }
    
    @Override
    public String getTexture() {
        return "/mob/pigzombie.png";
    }
    
    @Override
    public boolean getCanSpawnHere() {
        return this.worldObj.difficultySetting > 0 && this.worldObj.checkNoEntityCollision(this.boundingBox) && this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).isEmpty() && !this.worldObj.isAnyLiquid(this.boundingBox);
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setShort("Anger", (short)this.angerLevel);
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.angerLevel = par1NBTTagCompound.getShort("Anger");
    }
    
    @Override
    protected Entity findPlayerToAttack() {
        return (this.angerLevel == 0) ? null : super.findPlayerToAttack();
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource par1DamageSource, final int par2) {
        if (this.isEntityInvulnerable()) {
            return false;
        }
        final Entity var3 = par1DamageSource.getEntity();
        if (var3 instanceof EntityPlayer) {
            final List var4 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(32.0, 32.0, 32.0));
            for (int var5 = 0; var5 < var4.size(); ++var5) {
                final Entity var6 = var4.get(var5);
                if (var6 instanceof EntityPigZombie) {
                    final EntityPigZombie var7 = (EntityPigZombie)var6;
                    var7.becomeAngryAt(var3);
                }
            }
            this.becomeAngryAt(var3);
        }
        return super.attackEntityFrom(par1DamageSource, par2);
    }
    
    private void becomeAngryAt(final Entity par1Entity) {
        this.entityToAttack = par1Entity;
        this.angerLevel = 400 + this.rand.nextInt(400);
        this.randomSoundDelay = this.rand.nextInt(40);
    }
    
    @Override
    protected String getLivingSound() {
        return "mob.zombiepig.zpig";
    }
    
    @Override
    protected String getHurtSound() {
        return "mob.zombiepig.zpighurt";
    }
    
    @Override
    protected String getDeathSound() {
        return "mob.zombiepig.zpigdeath";
    }
    
    @Override
    protected void dropFewItems(final boolean par1, final int par2) {
        for (int var3 = this.rand.nextInt(2 + par2), var4 = 0; var4 < var3; ++var4) {
            this.dropItem(Item.rottenFlesh.itemID, 1);
        }
        for (int var3 = this.rand.nextInt(2 + par2), var4 = 0; var4 < var3; ++var4) {
            this.dropItem(Item.goldNugget.itemID, 1);
        }
    }
    
    @Override
    public boolean interact(final EntityPlayer par1EntityPlayer) {
        return false;
    }
    
    @Override
    protected void dropRareDrop(final int par1) {
        this.dropItem(Item.ingotGold.itemID, 1);
    }
    
    @Override
    protected int getDropItemId() {
        return Item.rottenFlesh.itemID;
    }
    
    @Override
    protected void addRandomArmor() {
        this.setCurrentItemOrArmor(0, new ItemStack(Item.swordGold));
    }
    
    @Override
    public void initCreature() {
        super.initCreature();
        this.setVillager(false);
    }
    
    @Override
    public int getAttackStrength(final Entity par1Entity) {
        final ItemStack var2 = this.getHeldItem();
        int var3 = 5;
        if (var2 != null) {
            var3 += var2.getDamageVsEntity(this);
        }
        return var3;
    }
}

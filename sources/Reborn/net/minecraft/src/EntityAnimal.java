package net.minecraft.src;

import java.util.*;

public abstract class EntityAnimal extends EntityAgeable implements IAnimals
{
    private int inLove;
    private int breeding;
    
    public EntityAnimal(final World par1World) {
        super(par1World);
        this.breeding = 0;
    }
    
    @Override
    protected void updateAITick() {
        if (this.getGrowingAge() != 0) {
            this.inLove = 0;
        }
        super.updateAITick();
    }
    
    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (this.getGrowingAge() != 0) {
            this.inLove = 0;
        }
        if (this.inLove > 0) {
            --this.inLove;
            final String var1 = "heart";
            if (this.inLove % 10 == 0) {
                final double var2 = this.rand.nextGaussian() * 0.02;
                final double var3 = this.rand.nextGaussian() * 0.02;
                final double var4 = this.rand.nextGaussian() * 0.02;
                this.worldObj.spawnParticle(var1, this.posX + this.rand.nextFloat() * this.width * 2.0f - this.width, this.posY + 0.5 + this.rand.nextFloat() * this.height, this.posZ + this.rand.nextFloat() * this.width * 2.0f - this.width, var2, var3, var4);
            }
        }
        else {
            this.breeding = 0;
        }
    }
    
    @Override
    protected void attackEntity(final Entity par1Entity, final float par2) {
        if (par1Entity instanceof EntityPlayer) {
            if (par2 < 3.0f) {
                final double var3 = par1Entity.posX - this.posX;
                final double var4 = par1Entity.posZ - this.posZ;
                this.rotationYaw = (float)(Math.atan2(var4, var3) * 180.0 / 3.141592653589793) - 90.0f;
                this.hasAttacked = true;
            }
            final EntityPlayer var5 = (EntityPlayer)par1Entity;
            if (var5.getCurrentEquippedItem() == null || !this.isBreedingItem(var5.getCurrentEquippedItem())) {
                this.entityToAttack = null;
            }
        }
        else if (par1Entity instanceof EntityAnimal) {
            final EntityAnimal var6 = (EntityAnimal)par1Entity;
            if (this.getGrowingAge() > 0 && var6.getGrowingAge() < 0) {
                if (par2 < 2.5) {
                    this.hasAttacked = true;
                }
            }
            else if (this.inLove > 0 && var6.inLove > 0) {
                if (var6.entityToAttack == null) {
                    var6.entityToAttack = this;
                }
                if (var6.entityToAttack == this && par2 < 3.5) {
                    final EntityAnimal entityAnimal = var6;
                    ++entityAnimal.inLove;
                    ++this.inLove;
                    ++this.breeding;
                    if (this.breeding % 4 == 0) {
                        this.worldObj.spawnParticle("heart", this.posX + this.rand.nextFloat() * this.width * 2.0f - this.width, this.posY + 0.5 + this.rand.nextFloat() * this.height, this.posZ + this.rand.nextFloat() * this.width * 2.0f - this.width, 0.0, 0.0, 0.0);
                    }
                    if (this.breeding == 60) {
                        this.procreate((EntityAnimal)par1Entity);
                    }
                }
                else {
                    this.breeding = 0;
                }
            }
            else {
                this.breeding = 0;
                this.entityToAttack = null;
            }
        }
    }
    
    private void procreate(final EntityAnimal par1EntityAnimal) {
        final EntityAgeable var2 = this.createChild(par1EntityAnimal);
        if (var2 != null) {
            this.setGrowingAge(6000);
            par1EntityAnimal.setGrowingAge(6000);
            this.inLove = 0;
            this.breeding = 0;
            this.entityToAttack = null;
            par1EntityAnimal.entityToAttack = null;
            par1EntityAnimal.breeding = 0;
            par1EntityAnimal.inLove = 0;
            var2.setGrowingAge(-24000);
            var2.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
            for (int var3 = 0; var3 < 7; ++var3) {
                final double var4 = this.rand.nextGaussian() * 0.02;
                final double var5 = this.rand.nextGaussian() * 0.02;
                final double var6 = this.rand.nextGaussian() * 0.02;
                this.worldObj.spawnParticle("heart", this.posX + this.rand.nextFloat() * this.width * 2.0f - this.width, this.posY + 0.5 + this.rand.nextFloat() * this.height, this.posZ + this.rand.nextFloat() * this.width * 2.0f - this.width, var4, var5, var6);
            }
            this.worldObj.spawnEntityInWorld(var2);
        }
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource par1DamageSource, final int par2) {
        if (this.isEntityInvulnerable()) {
            return false;
        }
        this.fleeingTick = 60;
        this.entityToAttack = null;
        this.inLove = 0;
        return super.attackEntityFrom(par1DamageSource, par2);
    }
    
    @Override
    public float getBlockPathWeight(final int par1, final int par2, final int par3) {
        return (this.worldObj.getBlockId(par1, par2 - 1, par3) == Block.grass.blockID) ? 10.0f : (this.worldObj.getLightBrightness(par1, par2, par3) - 0.5f);
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setInteger("InLove", this.inLove);
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.inLove = par1NBTTagCompound.getInteger("InLove");
    }
    
    @Override
    protected Entity findPlayerToAttack() {
        if (this.fleeingTick > 0) {
            return null;
        }
        final float var1 = 8.0f;
        if (this.inLove > 0) {
            final List var2 = this.worldObj.getEntitiesWithinAABB(this.getClass(), this.boundingBox.expand(var1, var1, var1));
            for (int var3 = 0; var3 < var2.size(); ++var3) {
                final EntityAnimal var4 = var2.get(var3);
                if (var4 != this && var4.inLove > 0) {
                    return var4;
                }
            }
        }
        else if (this.getGrowingAge() == 0) {
            final List var2 = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, this.boundingBox.expand(var1, var1, var1));
            for (int var3 = 0; var3 < var2.size(); ++var3) {
                final EntityPlayer var5 = var2.get(var3);
                if (var5.getCurrentEquippedItem() != null && this.isBreedingItem(var5.getCurrentEquippedItem())) {
                    return var5;
                }
            }
        }
        else if (this.getGrowingAge() > 0) {
            final List var2 = this.worldObj.getEntitiesWithinAABB(this.getClass(), this.boundingBox.expand(var1, var1, var1));
            for (int var3 = 0; var3 < var2.size(); ++var3) {
                final EntityAnimal var4 = var2.get(var3);
                if (var4 != this && var4.getGrowingAge() < 0) {
                    return var4;
                }
            }
        }
        return null;
    }
    
    @Override
    public boolean getCanSpawnHere() {
        final int var1 = MathHelper.floor_double(this.posX);
        final int var2 = MathHelper.floor_double(this.boundingBox.minY);
        final int var3 = MathHelper.floor_double(this.posZ);
        return this.worldObj.getBlockId(var1, var2 - 1, var3) == Block.grass.blockID && this.worldObj.getFullBlockLightValue(var1, var2, var3) > 8 && super.getCanSpawnHere();
    }
    
    @Override
    public int getTalkInterval() {
        return 120;
    }
    
    @Override
    protected boolean canDespawn() {
        return false;
    }
    
    @Override
    protected int getExperiencePoints(final EntityPlayer par1EntityPlayer) {
        return 1 + this.worldObj.rand.nextInt(3);
    }
    
    public boolean isBreedingItem(final ItemStack par1ItemStack) {
        return par1ItemStack.itemID == Item.wheat.itemID;
    }
    
    @Override
    public boolean interact(final EntityPlayer par1EntityPlayer) {
        final ItemStack var2 = par1EntityPlayer.inventory.getCurrentItem();
        if (var2 != null && this.isBreedingItem(var2) && this.getGrowingAge() == 0 && this.inLove <= 0) {
            if (!par1EntityPlayer.capabilities.isCreativeMode) {
                final ItemStack itemStack = var2;
                --itemStack.stackSize;
                if (var2.stackSize <= 0) {
                    par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, null);
                }
            }
            this.inLove = 600;
            this.entityToAttack = null;
            for (int var3 = 0; var3 < 7; ++var3) {
                final double var4 = this.rand.nextGaussian() * 0.02;
                final double var5 = this.rand.nextGaussian() * 0.02;
                final double var6 = this.rand.nextGaussian() * 0.02;
                this.worldObj.spawnParticle("heart", this.posX + this.rand.nextFloat() * this.width * 2.0f - this.width, this.posY + 0.5 + this.rand.nextFloat() * this.height, this.posZ + this.rand.nextFloat() * this.width * 2.0f - this.width, var4, var5, var6);
            }
            return true;
        }
        return super.interact(par1EntityPlayer);
    }
    
    public boolean isInLove() {
        return this.inLove > 0;
    }
    
    public void resetInLove() {
        this.inLove = 0;
    }
    
    public boolean canMateWith(final EntityAnimal par1EntityAnimal) {
        return par1EntityAnimal != this && par1EntityAnimal.getClass() == this.getClass() && (this.isInLove() && par1EntityAnimal.isInLove());
    }
}

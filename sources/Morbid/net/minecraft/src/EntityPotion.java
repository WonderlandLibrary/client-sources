package net.minecraft.src;

import java.util.*;

public class EntityPotion extends EntityThrowable
{
    private ItemStack potionDamage;
    
    public EntityPotion(final World par1World) {
        super(par1World);
    }
    
    public EntityPotion(final World par1World, final EntityLiving par2EntityLiving, final int par3) {
        this(par1World, par2EntityLiving, new ItemStack(Item.potion, 1, par3));
    }
    
    public EntityPotion(final World par1World, final EntityLiving par2EntityLiving, final ItemStack par3ItemStack) {
        super(par1World, par2EntityLiving);
        this.potionDamage = par3ItemStack;
    }
    
    public EntityPotion(final World par1World, final double par2, final double par4, final double par6, final int par8) {
        this(par1World, par2, par4, par6, new ItemStack(Item.potion, 1, par8));
    }
    
    public EntityPotion(final World par1World, final double par2, final double par4, final double par6, final ItemStack par8ItemStack) {
        super(par1World, par2, par4, par6);
        this.potionDamage = par8ItemStack;
    }
    
    @Override
    protected float getGravityVelocity() {
        return 0.05f;
    }
    
    @Override
    protected float func_70182_d() {
        return 0.5f;
    }
    
    @Override
    protected float func_70183_g() {
        return -20.0f;
    }
    
    public void setPotionDamage(final int par1) {
        if (this.potionDamage == null) {
            this.potionDamage = new ItemStack(Item.potion, 1, 0);
        }
        this.potionDamage.setItemDamage(par1);
    }
    
    public int getPotionDamage() {
        if (this.potionDamage == null) {
            this.potionDamage = new ItemStack(Item.potion, 1, 0);
        }
        return this.potionDamage.getItemDamage();
    }
    
    @Override
    protected void onImpact(final MovingObjectPosition par1MovingObjectPosition) {
        if (!this.worldObj.isRemote) {
            final List var2 = Item.potion.getEffects(this.potionDamage);
            if (var2 != null && !var2.isEmpty()) {
                final AxisAlignedBB var3 = this.boundingBox.expand(4.0, 2.0, 4.0);
                final List var4 = this.worldObj.getEntitiesWithinAABB(EntityLiving.class, var3);
                if (var4 != null && !var4.isEmpty()) {
                    for (final EntityLiving var6 : var4) {
                        final double var7 = this.getDistanceSqToEntity(var6);
                        if (var7 < 16.0) {
                            double var8 = 1.0 - Math.sqrt(var7) / 4.0;
                            if (var6 == par1MovingObjectPosition.entityHit) {
                                var8 = 1.0;
                            }
                            for (final PotionEffect var10 : var2) {
                                final int var11 = var10.getPotionID();
                                if (Potion.potionTypes[var11].isInstant()) {
                                    Potion.potionTypes[var11].affectEntity(this.getThrower(), var6, var10.getAmplifier(), var8);
                                }
                                else {
                                    final int var12 = (int)(var8 * var10.getDuration() + 0.5);
                                    if (var12 <= 20) {
                                        continue;
                                    }
                                    var6.addPotionEffect(new PotionEffect(var11, var12, var10.getAmplifier()));
                                }
                            }
                        }
                    }
                }
            }
            this.worldObj.playAuxSFX(2002, (int)Math.round(this.posX), (int)Math.round(this.posY), (int)Math.round(this.posZ), this.getPotionDamage());
            this.setDead();
        }
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
        if (par1NBTTagCompound.hasKey("Potion")) {
            this.potionDamage = ItemStack.loadItemStackFromNBT(par1NBTTagCompound.getCompoundTag("Potion"));
        }
        else {
            this.setPotionDamage(par1NBTTagCompound.getInteger("potionValue"));
        }
        if (this.potionDamage == null) {
            this.setDead();
        }
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
        if (this.potionDamage != null) {
            par1NBTTagCompound.setCompoundTag("Potion", this.potionDamage.writeToNBT(new NBTTagCompound()));
        }
    }
}

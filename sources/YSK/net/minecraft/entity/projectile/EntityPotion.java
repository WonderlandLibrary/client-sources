package net.minecraft.entity.projectile;

import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import net.minecraft.potion.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.nbt.*;

public class EntityPotion extends EntityThrowable
{
    private static final String[] I;
    private ItemStack potionDamage;
    
    private static void I() {
        (I = new String[0x22 ^ 0x26])["".length()] = I("\u0002\u001b>0\u001b<", "RtJYt");
        EntityPotion.I[" ".length()] = I("5\f0+\"\u000b", "ecDBM");
        EntityPotion.I["  ".length()] = I("\u0007\u0006\u001f$\u001e\u0019?\n!\u0004\u0012", "wikMq");
        EntityPotion.I["   ".length()] = I(";?$.#\u0005", "kPPGL");
    }
    
    public EntityPotion(final World world, final EntityLivingBase entityLivingBase, final int n) {
        this(world, entityLivingBase, new ItemStack(Items.potionitem, " ".length(), n));
    }
    
    public EntityPotion(final World world, final double n, final double n2, final double n3, final ItemStack potionDamage) {
        super(world, n, n2, n3);
        this.potionDamage = potionDamage;
    }
    
    public int getPotionDamage() {
        if (this.potionDamage == null) {
            this.potionDamage = new ItemStack(Items.potionitem, " ".length(), "".length());
        }
        return this.potionDamage.getMetadata();
    }
    
    @Override
    protected float getInaccuracy() {
        return -20.0f;
    }
    
    @Override
    protected float getVelocity() {
        return 0.5f;
    }
    
    @Override
    protected float getGravityVelocity() {
        return 0.05f;
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readEntityFromNBT(nbtTagCompound);
        if (nbtTagCompound.hasKey(EntityPotion.I["".length()], 0x1D ^ 0x17)) {
            this.potionDamage = ItemStack.loadItemStackFromNBT(nbtTagCompound.getCompoundTag(EntityPotion.I[" ".length()]));
            "".length();
            if (2 != 2) {
                throw null;
            }
        }
        else {
            this.setPotionDamage(nbtTagCompound.getInteger(EntityPotion.I["  ".length()]));
        }
        if (this.potionDamage == null) {
            this.setDead();
        }
    }
    
    @Override
    protected void onImpact(final MovingObjectPosition movingObjectPosition) {
        if (!this.worldObj.isRemote) {
            final List<PotionEffect> effects = Items.potionitem.getEffects(this.potionDamage);
            if (effects != null && !effects.isEmpty()) {
                final List<EntityLivingBase> entitiesWithinAABB = (List<EntityLivingBase>)this.worldObj.getEntitiesWithinAABB((Class<? extends Entity>)EntityLivingBase.class, this.getEntityBoundingBox().expand(4.0, 2.0, 4.0));
                if (!entitiesWithinAABB.isEmpty()) {
                    final Iterator<EntityLivingBase> iterator = entitiesWithinAABB.iterator();
                    "".length();
                    if (3 < 3) {
                        throw null;
                    }
                    while (iterator.hasNext()) {
                        final EntityLivingBase entityLivingBase = iterator.next();
                        final double distanceSqToEntity = this.getDistanceSqToEntity(entityLivingBase);
                        if (distanceSqToEntity < 16.0) {
                            double n = 1.0 - Math.sqrt(distanceSqToEntity) / 4.0;
                            if (entityLivingBase == movingObjectPosition.entityHit) {
                                n = 1.0;
                            }
                            final Iterator<PotionEffect> iterator2 = effects.iterator();
                            "".length();
                            if (4 < 1) {
                                throw null;
                            }
                            while (iterator2.hasNext()) {
                                final PotionEffect potionEffect = iterator2.next();
                                final int potionID = potionEffect.getPotionID();
                                if (Potion.potionTypes[potionID].isInstant()) {
                                    Potion.potionTypes[potionID].affectEntity(this, this.getThrower(), entityLivingBase, potionEffect.getAmplifier(), n);
                                    "".length();
                                    if (0 == 3) {
                                        throw null;
                                    }
                                    continue;
                                }
                                else {
                                    final int n2 = (int)(n * potionEffect.getDuration() + 0.5);
                                    if (n2 <= (0x67 ^ 0x73)) {
                                        continue;
                                    }
                                    entityLivingBase.addPotionEffect(new PotionEffect(potionID, n2, potionEffect.getAmplifier()));
                                }
                            }
                        }
                    }
                }
            }
            this.worldObj.playAuxSFX(942 + 1024 - 594 + 630, new BlockPos(this), this.getPotionDamage());
            this.setDead();
        }
    }
    
    public EntityPotion(final World world, final EntityLivingBase entityLivingBase, final ItemStack potionDamage) {
        super(world, entityLivingBase);
        this.potionDamage = potionDamage;
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (true != true) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static {
        I();
    }
    
    public EntityPotion(final World world, final double n, final double n2, final double n3, final int n4) {
        this(world, n, n2, n3, new ItemStack(Items.potionitem, " ".length(), n4));
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeEntityToNBT(nbtTagCompound);
        if (this.potionDamage != null) {
            nbtTagCompound.setTag(EntityPotion.I["   ".length()], this.potionDamage.writeToNBT(new NBTTagCompound()));
        }
    }
    
    public EntityPotion(final World world) {
        super(world);
    }
    
    public void setPotionDamage(final int itemDamage) {
        if (this.potionDamage == null) {
            this.potionDamage = new ItemStack(Items.potionitem, " ".length(), "".length());
        }
        this.potionDamage.setItemDamage(itemDamage);
    }
}

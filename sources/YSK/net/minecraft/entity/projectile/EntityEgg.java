package net.minecraft.entity.projectile;

import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.item.*;

public class EntityEgg extends EntityThrowable
{
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
            if (4 < 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public EntityEgg(final World world) {
        super(world);
    }
    
    public EntityEgg(final World world, final EntityLivingBase entityLivingBase) {
        super(world, entityLivingBase);
    }
    
    public EntityEgg(final World world, final double n, final double n2, final double n3) {
        super(world, n, n2, n3);
    }
    
    @Override
    protected void onImpact(final MovingObjectPosition movingObjectPosition) {
        if (movingObjectPosition.entityHit != null) {
            movingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 0.0f);
        }
        if (!this.worldObj.isRemote && this.rand.nextInt(0xAD ^ 0xA5) == 0) {
            int length = " ".length();
            if (this.rand.nextInt(0x50 ^ 0x70) == 0) {
                length = (0x34 ^ 0x30);
            }
            int i = "".length();
            "".length();
            if (-1 < -1) {
                throw null;
            }
            while (i < length) {
                final EntityChicken entityChicken = new EntityChicken(this.worldObj);
                entityChicken.setGrowingAge(-(16094 + 17554 - 27346 + 17698));
                entityChicken.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0f);
                this.worldObj.spawnEntityInWorld(entityChicken);
                ++i;
            }
        }
        int j = "".length();
        "".length();
        if (-1 >= 4) {
            throw null;
        }
        while (j < (0x32 ^ 0x3A)) {
            final World worldObj = this.worldObj;
            final EnumParticleTypes item_CRACK = EnumParticleTypes.ITEM_CRACK;
            final double posX = this.posX;
            final double posY = this.posY;
            final double posZ = this.posZ;
            final double n = (this.rand.nextFloat() - 0.5) * 0.08;
            final double n2 = (this.rand.nextFloat() - 0.5) * 0.08;
            final double n3 = (this.rand.nextFloat() - 0.5) * 0.08;
            final int[] array = new int[" ".length()];
            array["".length()] = Item.getIdFromItem(Items.egg);
            worldObj.spawnParticle(item_CRACK, posX, posY, posZ, n, n2, n3, array);
            ++j;
        }
        if (!this.worldObj.isRemote) {
            this.setDead();
        }
    }
}

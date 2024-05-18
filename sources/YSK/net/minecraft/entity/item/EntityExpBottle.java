package net.minecraft.entity.item;

import net.minecraft.entity.projectile.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;

public class EntityExpBottle extends EntityThrowable
{
    @Override
    protected float getGravityVelocity() {
        return 0.07f;
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
            if (2 < 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public EntityExpBottle(final World world) {
        super(world);
    }
    
    @Override
    protected float getVelocity() {
        return 0.7f;
    }
    
    public EntityExpBottle(final World world, final EntityLivingBase entityLivingBase) {
        super(world, entityLivingBase);
    }
    
    public EntityExpBottle(final World world, final double n, final double n2, final double n3) {
        super(world, n, n2, n3);
    }
    
    @Override
    protected void onImpact(final MovingObjectPosition movingObjectPosition) {
        if (!this.worldObj.isRemote) {
            this.worldObj.playAuxSFX(994 + 663 - 1005 + 1350, new BlockPos(this), "".length());
            int i = "   ".length() + this.worldObj.rand.nextInt(0x48 ^ 0x4D) + this.worldObj.rand.nextInt(0x20 ^ 0x25);
            "".length();
            if (!true) {
                throw null;
            }
            while (i > 0) {
                final int xpSplit = EntityXPOrb.getXPSplit(i);
                i -= xpSplit;
                this.worldObj.spawnEntityInWorld(new EntityXPOrb(this.worldObj, this.posX, this.posY, this.posZ, xpSplit));
            }
            this.setDead();
        }
    }
    
    @Override
    protected float getInaccuracy() {
        return -20.0f;
    }
}

package net.minecraft.entity.passive;

import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;

public abstract class EntityWaterMob extends EntityLiving implements IAnimals
{
    @Override
    public boolean isPushedByWater() {
        return "".length() != 0;
    }
    
    @Override
    public boolean isNotColliding() {
        return this.worldObj.checkNoEntityCollision(this.getEntityBoundingBox(), this);
    }
    
    @Override
    public int getTalkInterval() {
        return 0xF8 ^ 0x80;
    }
    
    @Override
    public boolean canBreatheUnderwater() {
        return " ".length() != 0;
    }
    
    @Override
    public void onEntityUpdate() {
        int air = this.getAir();
        super.onEntityUpdate();
        if (this.isEntityAlive() && !this.isInWater()) {
            --air;
            this.setAir(air);
            if (this.getAir() == -(0x52 ^ 0x46)) {
                this.setAir("".length());
                this.attackEntityFrom(DamageSource.drown, 2.0f);
                "".length();
                if (4 < 0) {
                    throw null;
                }
            }
        }
        else {
            this.setAir(192 + 56 - 21 + 73);
        }
    }
    
    @Override
    public boolean getCanSpawnHere() {
        return " ".length() != 0;
    }
    
    @Override
    protected boolean canDespawn() {
        return " ".length() != 0;
    }
    
    public EntityWaterMob(final World world) {
        super(world);
    }
    
    @Override
    protected int getExperiencePoints(final EntityPlayer entityPlayer) {
        return " ".length() + this.worldObj.rand.nextInt("   ".length());
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
            if (false) {
                throw null;
            }
        }
        return sb.toString();
    }
}

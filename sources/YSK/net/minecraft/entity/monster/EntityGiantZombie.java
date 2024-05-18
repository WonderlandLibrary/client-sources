package net.minecraft.entity.monster;

import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;

public class EntityGiantZombie extends EntityMob
{
    @Override
    public float getEyeHeight() {
        return 10.440001f;
    }
    
    @Override
    public float getBlockPathWeight(final BlockPos blockPos) {
        return this.worldObj.getLightBrightness(blockPos) - 0.5f;
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(100.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(50.0);
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
            if (-1 != -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public EntityGiantZombie(final World world) {
        super(world);
        this.setSize(this.width * 6.0f, this.height * 6.0f);
    }
}

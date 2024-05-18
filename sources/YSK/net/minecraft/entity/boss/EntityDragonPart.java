package net.minecraft.entity.boss;

import net.minecraft.entity.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;

public class EntityDragonPart extends Entity
{
    public final String partName;
    public final IEntityMultiPart entityDragonObj;
    
    @Override
    protected void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
    }
    
    @Override
    public boolean canBeCollidedWith() {
        return " ".length() != 0;
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
            if (1 >= 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource damageSource, final float n) {
        int n2;
        if (this.isEntityInvulnerable(damageSource)) {
            n2 = "".length();
            "".length();
            if (-1 != -1) {
                throw null;
            }
        }
        else {
            n2 = (this.entityDragonObj.attackEntityFromPart(this, damageSource, n) ? 1 : 0);
        }
        return n2 != 0;
    }
    
    @Override
    public boolean isEntityEqual(final Entity entity) {
        if (this != entity && this.entityDragonObj != entity) {
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
    
    @Override
    protected void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
    }
    
    public EntityDragonPart(final IEntityMultiPart entityDragonObj, final String partName, final float n, final float n2) {
        super(entityDragonObj.getWorld());
        this.setSize(n, n2);
        this.entityDragonObj = entityDragonObj;
        this.partName = partName;
    }
    
    @Override
    protected void entityInit() {
    }
}

package net.minecraft.entity.ai;

import net.minecraft.entity.passive.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.*;

public class EntityAILookAtVillager extends EntityAIBase
{
    private EntityVillager theVillager;
    private int lookTime;
    private EntityIronGolem theGolem;
    
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
            if (-1 >= 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void startExecuting() {
        this.lookTime = 197 + 357 - 200 + 46;
        this.theGolem.setHoldingRose(" ".length() != 0);
    }
    
    @Override
    public void updateTask() {
        this.theGolem.getLookHelper().setLookPositionWithEntity(this.theVillager, 30.0f, 30.0f);
        this.lookTime -= " ".length();
    }
    
    public EntityAILookAtVillager(final EntityIronGolem theGolem) {
        this.theGolem = theGolem;
        this.setMutexBits("   ".length());
    }
    
    @Override
    public boolean shouldExecute() {
        if (!this.theGolem.worldObj.isDaytime()) {
            return "".length() != 0;
        }
        if (this.theGolem.getRNG().nextInt(2047 + 1939 + 2871 + 1143) != 0) {
            return "".length() != 0;
        }
        this.theVillager = this.theGolem.worldObj.findNearestEntityWithinAABB(EntityVillager.class, this.theGolem.getEntityBoundingBox().expand(6.0, 2.0, 6.0), (EntityVillager)this.theGolem);
        if (this.theVillager != null) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public boolean continueExecuting() {
        if (this.lookTime > 0) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public void resetTask() {
        this.theGolem.setHoldingRose("".length() != 0);
        this.theVillager = null;
    }
}

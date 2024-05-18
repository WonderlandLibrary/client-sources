package net.minecraft.entity.ai;

import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*;
import java.util.*;
import net.minecraft.entity.*;

public class EntityAIFollowGolem extends EntityAIBase
{
    private EntityIronGolem theGolem;
    private int takeGolemRoseTick;
    private EntityVillager theVillager;
    private boolean tookGolemRose;
    
    @Override
    public boolean shouldExecute() {
        if (this.theVillager.getGrowingAge() >= 0) {
            return "".length() != 0;
        }
        if (!this.theVillager.worldObj.isDaytime()) {
            return "".length() != 0;
        }
        final List<EntityIronGolem> entitiesWithinAABB = this.theVillager.worldObj.getEntitiesWithinAABB((Class<? extends EntityIronGolem>)EntityIronGolem.class, this.theVillager.getEntityBoundingBox().expand(6.0, 2.0, 6.0));
        if (entitiesWithinAABB.isEmpty()) {
            return "".length() != 0;
        }
        final Iterator<EntityIronGolem> iterator = entitiesWithinAABB.iterator();
        "".length();
        if (4 != 4) {
            throw null;
        }
        while (iterator.hasNext()) {
            final EntityIronGolem theGolem = iterator.next();
            if (theGolem.getHoldRoseTick() > 0) {
                this.theGolem = theGolem;
                "".length();
                if (4 <= -1) {
                    throw null;
                }
                break;
            }
        }
        if (this.theGolem != null) {
            return " ".length() != 0;
        }
        return "".length() != 0;
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
            if (3 != 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public EntityAIFollowGolem(final EntityVillager theVillager) {
        this.theVillager = theVillager;
        this.setMutexBits("   ".length());
    }
    
    @Override
    public void startExecuting() {
        this.takeGolemRoseTick = this.theVillager.getRNG().nextInt(269 + 189 - 243 + 105);
        this.tookGolemRose = ("".length() != 0);
        this.theGolem.getNavigator().clearPathEntity();
    }
    
    @Override
    public boolean continueExecuting() {
        if (this.theGolem.getHoldRoseTick() > 0) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public void updateTask() {
        this.theVillager.getLookHelper().setLookPositionWithEntity(this.theGolem, 30.0f, 30.0f);
        if (this.theGolem.getHoldRoseTick() == this.takeGolemRoseTick) {
            this.theVillager.getNavigator().tryMoveToEntityLiving(this.theGolem, 0.5);
            this.tookGolemRose = (" ".length() != 0);
        }
        if (this.tookGolemRose && this.theVillager.getDistanceSqToEntity(this.theGolem) < 4.0) {
            this.theGolem.setHoldingRose("".length() != 0);
            this.theVillager.getNavigator().clearPathEntity();
        }
    }
    
    @Override
    public void resetTask() {
        this.theGolem = null;
        this.theVillager.getNavigator().clearPathEntity();
    }
}

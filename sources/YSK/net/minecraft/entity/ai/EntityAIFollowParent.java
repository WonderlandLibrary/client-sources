package net.minecraft.entity.ai;

import net.minecraft.entity.passive.*;
import net.minecraft.entity.*;
import java.util.*;

public class EntityAIFollowParent extends EntityAIBase
{
    private int delayCounter;
    EntityAnimal parentAnimal;
    double moveSpeed;
    EntityAnimal childAnimal;
    
    @Override
    public void resetTask() {
        this.parentAnimal = null;
    }
    
    @Override
    public boolean shouldExecute() {
        if (this.childAnimal.getGrowingAge() >= 0) {
            return "".length() != 0;
        }
        final List<EntityAnimal> entitiesWithinAABB = this.childAnimal.worldObj.getEntitiesWithinAABB(this.childAnimal.getClass(), this.childAnimal.getEntityBoundingBox().expand(8.0, 4.0, 8.0));
        EntityAnimal parentAnimal = null;
        double n = Double.MAX_VALUE;
        final Iterator<EntityAnimal> iterator = entitiesWithinAABB.iterator();
        "".length();
        if (3 < 0) {
            throw null;
        }
        while (iterator.hasNext()) {
            final EntityAnimal entityAnimal = iterator.next();
            if (entityAnimal.getGrowingAge() >= 0) {
                final double distanceSqToEntity = this.childAnimal.getDistanceSqToEntity(entityAnimal);
                if (distanceSqToEntity > n) {
                    continue;
                }
                n = distanceSqToEntity;
                parentAnimal = entityAnimal;
            }
        }
        if (parentAnimal == null) {
            return "".length() != 0;
        }
        if (n < 9.0) {
            return "".length() != 0;
        }
        this.parentAnimal = parentAnimal;
        return " ".length() != 0;
    }
    
    @Override
    public void startExecuting() {
        this.delayCounter = "".length();
    }
    
    public EntityAIFollowParent(final EntityAnimal childAnimal, final double moveSpeed) {
        this.childAnimal = childAnimal;
        this.moveSpeed = moveSpeed;
    }
    
    @Override
    public void updateTask() {
        final int delayCounter = this.delayCounter - " ".length();
        this.delayCounter = delayCounter;
        if (delayCounter <= 0) {
            this.delayCounter = (0x3C ^ 0x36);
            this.childAnimal.getNavigator().tryMoveToEntityLiving(this.parentAnimal, this.moveSpeed);
        }
    }
    
    @Override
    public boolean continueExecuting() {
        if (this.childAnimal.getGrowingAge() >= 0) {
            return "".length() != 0;
        }
        if (!this.parentAnimal.isEntityAlive()) {
            return "".length() != 0;
        }
        final double distanceSqToEntity = this.childAnimal.getDistanceSqToEntity(this.parentAnimal);
        if (distanceSqToEntity >= 9.0 && distanceSqToEntity <= 256.0) {
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
            if (-1 >= 1) {
                throw null;
            }
        }
        return sb.toString();
    }
}

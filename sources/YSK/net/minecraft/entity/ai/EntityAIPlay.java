package net.minecraft.entity.ai;

import net.minecraft.entity.passive.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.util.*;

public class EntityAIPlay extends EntityAIBase
{
    private int playTime;
    private double speed;
    private EntityLivingBase targetVillager;
    private EntityVillager villagerObj;
    
    @Override
    public void resetTask() {
        this.villagerObj.setPlaying("".length() != 0);
        this.targetVillager = null;
    }
    
    @Override
    public void startExecuting() {
        if (this.targetVillager != null) {
            this.villagerObj.setPlaying(" ".length() != 0);
        }
        this.playTime = 330 + 873 - 831 + 628;
    }
    
    @Override
    public boolean shouldExecute() {
        if (this.villagerObj.getGrowingAge() >= 0) {
            return "".length() != 0;
        }
        if (this.villagerObj.getRNG().nextInt(301 + 94 - 278 + 283) != 0) {
            return "".length() != 0;
        }
        final List<EntityVillager> entitiesWithinAABB = this.villagerObj.worldObj.getEntitiesWithinAABB((Class<? extends EntityVillager>)EntityVillager.class, this.villagerObj.getEntityBoundingBox().expand(6.0, 3.0, 6.0));
        double n = Double.MAX_VALUE;
        final Iterator<EntityVillager> iterator = entitiesWithinAABB.iterator();
        "".length();
        if (-1 != -1) {
            throw null;
        }
        while (iterator.hasNext()) {
            final EntityVillager targetVillager = iterator.next();
            if (targetVillager != this.villagerObj && !targetVillager.isPlaying() && targetVillager.getGrowingAge() < 0) {
                final double distanceSqToEntity = targetVillager.getDistanceSqToEntity(this.villagerObj);
                if (distanceSqToEntity > n) {
                    continue;
                }
                n = distanceSqToEntity;
                this.targetVillager = targetVillager;
            }
        }
        if (this.targetVillager == null && RandomPositionGenerator.findRandomTarget(this.villagerObj, 0xB2 ^ 0xA2, "   ".length()) == null) {
            return "".length() != 0;
        }
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
            if (1 < 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void updateTask() {
        this.playTime -= " ".length();
        if (this.targetVillager != null) {
            if (this.villagerObj.getDistanceSqToEntity(this.targetVillager) > 4.0) {
                this.villagerObj.getNavigator().tryMoveToEntityLiving(this.targetVillager, this.speed);
                "".length();
                if (4 <= 0) {
                    throw null;
                }
            }
        }
        else if (this.villagerObj.getNavigator().noPath()) {
            final Vec3 randomTarget = RandomPositionGenerator.findRandomTarget(this.villagerObj, 0x4E ^ 0x5E, "   ".length());
            if (randomTarget == null) {
                return;
            }
            this.villagerObj.getNavigator().tryMoveToXYZ(randomTarget.xCoord, randomTarget.yCoord, randomTarget.zCoord, this.speed);
        }
    }
    
    @Override
    public boolean continueExecuting() {
        if (this.playTime > 0) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public EntityAIPlay(final EntityVillager villagerObj, final double speed) {
        this.villagerObj = villagerObj;
        this.speed = speed;
        this.setMutexBits(" ".length());
    }
}

package net.minecraft.entity.ai;

import net.minecraft.entity.*;
import net.minecraft.entity.player.*;

public class EntityAIWatchClosest extends EntityAIBase
{
    private float chance;
    protected float maxDistanceForPlayer;
    private int lookTime;
    protected Class<? extends Entity> watchedClass;
    protected EntityLiving theWatcher;
    protected Entity closestEntity;
    
    @Override
    public boolean continueExecuting() {
        int n;
        if (!this.closestEntity.isEntityAlive()) {
            n = "".length();
            "".length();
            if (4 < -1) {
                throw null;
            }
        }
        else if (this.theWatcher.getDistanceSqToEntity(this.closestEntity) > this.maxDistanceForPlayer * this.maxDistanceForPlayer) {
            n = "".length();
            "".length();
            if (3 == 2) {
                throw null;
            }
        }
        else if (this.lookTime > 0) {
            n = " ".length();
            "".length();
            if (2 == 1) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n != 0;
    }
    
    @Override
    public void startExecuting() {
        this.lookTime = (0x7D ^ 0x55) + this.theWatcher.getRNG().nextInt(0xE9 ^ 0xC1);
    }
    
    public EntityAIWatchClosest(final EntityLiving theWatcher, final Class<? extends Entity> watchedClass, final float maxDistanceForPlayer) {
        this.theWatcher = theWatcher;
        this.watchedClass = watchedClass;
        this.maxDistanceForPlayer = maxDistanceForPlayer;
        this.chance = 0.02f;
        this.setMutexBits("  ".length());
    }
    
    public EntityAIWatchClosest(final EntityLiving theWatcher, final Class<? extends Entity> watchedClass, final float maxDistanceForPlayer, final float chance) {
        this.theWatcher = theWatcher;
        this.watchedClass = watchedClass;
        this.maxDistanceForPlayer = maxDistanceForPlayer;
        this.chance = chance;
        this.setMutexBits("  ".length());
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
            if (1 >= 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void resetTask() {
        this.closestEntity = null;
    }
    
    @Override
    public boolean shouldExecute() {
        if (this.theWatcher.getRNG().nextFloat() >= this.chance) {
            return "".length() != 0;
        }
        if (this.theWatcher.getAttackTarget() != null) {
            this.closestEntity = this.theWatcher.getAttackTarget();
        }
        if (this.watchedClass == EntityPlayer.class) {
            this.closestEntity = this.theWatcher.worldObj.getClosestPlayerToEntity(this.theWatcher, this.maxDistanceForPlayer);
            "".length();
            if (-1 == 2) {
                throw null;
            }
        }
        else {
            this.closestEntity = this.theWatcher.worldObj.findNearestEntityWithinAABB((Class<? extends EntityLiving>)this.watchedClass, this.theWatcher.getEntityBoundingBox().expand(this.maxDistanceForPlayer, 3.0, this.maxDistanceForPlayer), this.theWatcher);
        }
        if (this.closestEntity != null) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public void updateTask() {
        this.theWatcher.getLookHelper().setLookPosition(this.closestEntity.posX, this.closestEntity.posY + this.closestEntity.getEyeHeight(), this.closestEntity.posZ, 10.0f, this.theWatcher.getVerticalFaceSpeed());
        this.lookTime -= " ".length();
    }
}

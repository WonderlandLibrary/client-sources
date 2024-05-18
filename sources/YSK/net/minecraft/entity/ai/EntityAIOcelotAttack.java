package net.minecraft.entity.ai;

import net.minecraft.world.*;
import net.minecraft.entity.*;

public class EntityAIOcelotAttack extends EntityAIBase
{
    World theWorld;
    EntityLivingBase theVictim;
    EntityLiving theEntity;
    int attackCountdown;
    
    @Override
    public boolean shouldExecute() {
        final EntityLivingBase attackTarget = this.theEntity.getAttackTarget();
        if (attackTarget == null) {
            return "".length() != 0;
        }
        this.theVictim = attackTarget;
        return " ".length() != 0;
    }
    
    @Override
    public void resetTask() {
        this.theVictim = null;
        this.theEntity.getNavigator().clearPathEntity();
    }
    
    @Override
    public void updateTask() {
        this.theEntity.getLookHelper().setLookPositionWithEntity(this.theVictim, 30.0f, 30.0f);
        final double n = this.theEntity.width * 2.0f * this.theEntity.width * 2.0f;
        final double distanceSq = this.theEntity.getDistanceSq(this.theVictim.posX, this.theVictim.getEntityBoundingBox().minY, this.theVictim.posZ);
        double n2 = 0.8;
        if (distanceSq > n && distanceSq < 16.0) {
            n2 = 1.33;
            "".length();
            if (4 != 4) {
                throw null;
            }
        }
        else if (distanceSq < 225.0) {
            n2 = 0.6;
        }
        this.theEntity.getNavigator().tryMoveToEntityLiving(this.theVictim, n2);
        this.attackCountdown = Math.max(this.attackCountdown - " ".length(), "".length());
        if (distanceSq <= n && this.attackCountdown <= 0) {
            this.attackCountdown = (0x60 ^ 0x74);
            this.theEntity.attackEntityAsMob(this.theVictim);
        }
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
            if (2 == 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public boolean continueExecuting() {
        int n;
        if (!this.theVictim.isEntityAlive()) {
            n = "".length();
            "".length();
            if (0 == 3) {
                throw null;
            }
        }
        else if (this.theEntity.getDistanceSqToEntity(this.theVictim) > 225.0) {
            n = "".length();
            "".length();
            if (1 <= -1) {
                throw null;
            }
        }
        else if (this.theEntity.getNavigator().noPath() && !this.shouldExecute()) {
            n = "".length();
            "".length();
            if (2 != 2) {
                throw null;
            }
        }
        else {
            n = " ".length();
        }
        return n != 0;
    }
    
    public EntityAIOcelotAttack(final EntityLiving theEntity) {
        this.theEntity = theEntity;
        this.theWorld = theEntity.worldObj;
        this.setMutexBits("   ".length());
    }
}

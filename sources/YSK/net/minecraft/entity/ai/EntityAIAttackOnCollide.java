package net.minecraft.entity.ai;

import net.minecraft.world.*;
import net.minecraft.pathfinding.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;

public class EntityAIAttackOnCollide extends EntityAIBase
{
    World worldObj;
    double speedTowardsTarget;
    Class<? extends Entity> classTarget;
    private int delayCounter;
    private double targetY;
    protected EntityCreature attacker;
    boolean longMemory;
    private double targetX;
    PathEntity entityPathEntity;
    private double targetZ;
    int attackTick;
    
    @Override
    public void resetTask() {
        this.attacker.getNavigator().clearPathEntity();
    }
    
    public EntityAIAttackOnCollide(final EntityCreature entityCreature, final Class<? extends Entity> classTarget, final double n, final boolean b) {
        this(entityCreature, n, b);
        this.classTarget = classTarget;
    }
    
    @Override
    public boolean shouldExecute() {
        final EntityLivingBase attackTarget = this.attacker.getAttackTarget();
        if (attackTarget == null) {
            return "".length() != 0;
        }
        if (!attackTarget.isEntityAlive()) {
            return "".length() != 0;
        }
        if (this.classTarget != null && !this.classTarget.isAssignableFrom(attackTarget.getClass())) {
            return "".length() != 0;
        }
        this.entityPathEntity = this.attacker.getNavigator().getPathToEntityLiving(attackTarget);
        if (this.entityPathEntity != null) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public void startExecuting() {
        this.attacker.getNavigator().setPath(this.entityPathEntity, this.speedTowardsTarget);
        this.delayCounter = "".length();
    }
    
    @Override
    public boolean continueExecuting() {
        final EntityLivingBase attackTarget = this.attacker.getAttackTarget();
        int n;
        if (attackTarget == null) {
            n = "".length();
            "".length();
            if (-1 >= 3) {
                throw null;
            }
        }
        else if (!attackTarget.isEntityAlive()) {
            n = "".length();
            "".length();
            if (1 < -1) {
                throw null;
            }
        }
        else if (!this.longMemory) {
            if (this.attacker.getNavigator().noPath()) {
                n = "".length();
                "".length();
                if (4 <= 3) {
                    throw null;
                }
            }
            else {
                n = " ".length();
                "".length();
                if (4 < 1) {
                    throw null;
                }
            }
        }
        else {
            n = (this.attacker.isWithinHomeDistanceFromPosition(new BlockPos(attackTarget)) ? 1 : 0);
        }
        return n != 0;
    }
    
    public EntityAIAttackOnCollide(final EntityCreature attacker, final double speedTowardsTarget, final boolean longMemory) {
        this.attacker = attacker;
        this.worldObj = attacker.worldObj;
        this.speedTowardsTarget = speedTowardsTarget;
        this.longMemory = longMemory;
        this.setMutexBits("   ".length());
    }
    
    @Override
    public void updateTask() {
        final EntityLivingBase attackTarget = this.attacker.getAttackTarget();
        this.attacker.getLookHelper().setLookPositionWithEntity(attackTarget, 30.0f, 30.0f);
        final double distanceSq = this.attacker.getDistanceSq(attackTarget.posX, attackTarget.getEntityBoundingBox().minY, attackTarget.posZ);
        final double func_179512_a = this.func_179512_a(attackTarget);
        this.delayCounter -= " ".length();
        if ((this.longMemory || this.attacker.getEntitySenses().canSee(attackTarget)) && this.delayCounter <= 0 && ((this.targetX == 0.0 && this.targetY == 0.0 && this.targetZ == 0.0) || attackTarget.getDistanceSq(this.targetX, this.targetY, this.targetZ) >= 1.0 || this.attacker.getRNG().nextFloat() < 0.05f)) {
            this.targetX = attackTarget.posX;
            this.targetY = attackTarget.getEntityBoundingBox().minY;
            this.targetZ = attackTarget.posZ;
            this.delayCounter = (0xBB ^ 0xBF) + this.attacker.getRNG().nextInt(0x65 ^ 0x62);
            if (distanceSq > 1024.0) {
                this.delayCounter += (0xA6 ^ 0xAC);
                "".length();
                if (-1 >= 2) {
                    throw null;
                }
            }
            else if (distanceSq > 256.0) {
                this.delayCounter += (0x44 ^ 0x41);
            }
            if (!this.attacker.getNavigator().tryMoveToEntityLiving(attackTarget, this.speedTowardsTarget)) {
                this.delayCounter += (0x3 ^ 0xC);
            }
        }
        this.attackTick = Math.max(this.attackTick - " ".length(), "".length());
        if (distanceSq <= func_179512_a && this.attackTick <= 0) {
            this.attackTick = (0x36 ^ 0x22);
            if (this.attacker.getHeldItem() != null) {
                this.attacker.swingItem();
            }
            this.attacker.attackEntityAsMob(attackTarget);
        }
    }
    
    protected double func_179512_a(final EntityLivingBase entityLivingBase) {
        return this.attacker.width * 2.0f * this.attacker.width * 2.0f + entityLivingBase.width;
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
            if (0 >= 2) {
                throw null;
            }
        }
        return sb.toString();
    }
}

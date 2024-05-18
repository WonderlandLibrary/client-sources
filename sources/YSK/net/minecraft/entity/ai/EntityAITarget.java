package net.minecraft.entity.ai;

import net.minecraft.entity.ai.attributes.*;
import net.minecraft.entity.*;
import org.apache.commons.lang3.*;
import net.minecraft.entity.player.*;
import net.minecraft.scoreboard.*;
import net.minecraft.util.*;
import net.minecraft.pathfinding.*;

public abstract class EntityAITarget extends EntityAIBase
{
    protected boolean shouldCheckSight;
    private int targetSearchDelay;
    private boolean nearbyOnly;
    private int targetUnseenTicks;
    private int targetSearchStatus;
    protected final EntityCreature taskOwner;
    
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
            if (0 >= 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    protected double getTargetDistance() {
        final IAttributeInstance entityAttribute = this.taskOwner.getEntityAttribute(SharedMonsterAttributes.followRange);
        double attributeValue;
        if (entityAttribute == null) {
            attributeValue = 16.0;
            "".length();
            if (3 < 3) {
                throw null;
            }
        }
        else {
            attributeValue = entityAttribute.getAttributeValue();
        }
        return attributeValue;
    }
    
    protected boolean isSuitableTarget(final EntityLivingBase entityLivingBase, final boolean b) {
        if (!isSuitableTarget(this.taskOwner, entityLivingBase, b, this.shouldCheckSight)) {
            return "".length() != 0;
        }
        if (!this.taskOwner.isWithinHomeDistanceFromPosition(new BlockPos(entityLivingBase))) {
            return "".length() != 0;
        }
        if (this.nearbyOnly) {
            if ((this.targetSearchDelay -= " ".length()) <= 0) {
                this.targetSearchStatus = "".length();
            }
            if (this.targetSearchStatus == 0) {
                int targetSearchStatus;
                if (this.canEasilyReach(entityLivingBase)) {
                    targetSearchStatus = " ".length();
                    "".length();
                    if (0 >= 2) {
                        throw null;
                    }
                }
                else {
                    targetSearchStatus = "  ".length();
                }
                this.targetSearchStatus = targetSearchStatus;
            }
            if (this.targetSearchStatus == "  ".length()) {
                return "".length() != 0;
            }
        }
        return " ".length() != 0;
    }
    
    @Override
    public void resetTask() {
        this.taskOwner.setAttackTarget(null);
    }
    
    public EntityAITarget(final EntityCreature entityCreature, final boolean b) {
        this(entityCreature, b, "".length() != 0);
    }
    
    public static boolean isSuitableTarget(final EntityLiving entityLiving, final EntityLivingBase entityLivingBase, final boolean b, final boolean b2) {
        if (entityLivingBase == null) {
            return "".length() != 0;
        }
        if (entityLivingBase == entityLiving) {
            return "".length() != 0;
        }
        if (!entityLivingBase.isEntityAlive()) {
            return "".length() != 0;
        }
        if (!entityLiving.canAttackClass(entityLivingBase.getClass())) {
            return "".length() != 0;
        }
        final Team team = entityLiving.getTeam();
        final Team team2 = entityLivingBase.getTeam();
        if (team != null && team2 == team) {
            return "".length() != 0;
        }
        if (entityLiving instanceof IEntityOwnable && StringUtils.isNotEmpty((CharSequence)((IEntityOwnable)entityLiving).getOwnerId())) {
            if (entityLivingBase instanceof IEntityOwnable && ((IEntityOwnable)entityLiving).getOwnerId().equals(((IEntityOwnable)entityLivingBase).getOwnerId())) {
                return "".length() != 0;
            }
            if (entityLivingBase == ((IEntityOwnable)entityLiving).getOwner()) {
                return "".length() != 0;
            }
        }
        else if (entityLivingBase instanceof EntityPlayer && !b && ((EntityPlayer)entityLivingBase).capabilities.disableDamage) {
            return "".length() != 0;
        }
        if (b2 && !entityLiving.getEntitySenses().canSee(entityLivingBase)) {
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
    
    public EntityAITarget(final EntityCreature taskOwner, final boolean shouldCheckSight, final boolean nearbyOnly) {
        this.taskOwner = taskOwner;
        this.shouldCheckSight = shouldCheckSight;
        this.nearbyOnly = nearbyOnly;
    }
    
    @Override
    public boolean continueExecuting() {
        final EntityLivingBase attackTarget = this.taskOwner.getAttackTarget();
        if (attackTarget == null) {
            return "".length() != 0;
        }
        if (!attackTarget.isEntityAlive()) {
            return "".length() != 0;
        }
        final Team team = this.taskOwner.getTeam();
        final Team team2 = attackTarget.getTeam();
        if (team != null && team2 == team) {
            return "".length() != 0;
        }
        final double targetDistance = this.getTargetDistance();
        if (this.taskOwner.getDistanceSqToEntity(attackTarget) > targetDistance * targetDistance) {
            return "".length() != 0;
        }
        if (this.shouldCheckSight) {
            if (this.taskOwner.getEntitySenses().canSee(attackTarget)) {
                this.targetUnseenTicks = "".length();
                "".length();
                if (3 != 3) {
                    throw null;
                }
            }
            else if ((this.targetUnseenTicks += " ".length()) > (0x1 ^ 0x3D)) {
                return "".length() != 0;
            }
        }
        if (attackTarget instanceof EntityPlayer && ((EntityPlayer)attackTarget).capabilities.disableDamage) {
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
    
    @Override
    public void startExecuting() {
        this.targetSearchStatus = "".length();
        this.targetSearchDelay = "".length();
        this.targetUnseenTicks = "".length();
    }
    
    private boolean canEasilyReach(final EntityLivingBase entityLivingBase) {
        this.targetSearchDelay = (0x7A ^ 0x70) + this.taskOwner.getRNG().nextInt(0x21 ^ 0x24);
        final PathEntity pathToEntityLiving = this.taskOwner.getNavigator().getPathToEntityLiving(entityLivingBase);
        if (pathToEntityLiving == null) {
            return "".length() != 0;
        }
        final PathPoint finalPathPoint = pathToEntityLiving.getFinalPathPoint();
        if (finalPathPoint == null) {
            return "".length() != 0;
        }
        final int n = finalPathPoint.xCoord - MathHelper.floor_double(entityLivingBase.posX);
        final int n2 = finalPathPoint.zCoord - MathHelper.floor_double(entityLivingBase.posZ);
        if (n * n + n2 * n2 <= 2.25) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
}

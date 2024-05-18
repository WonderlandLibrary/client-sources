package net.minecraft.entity.ai;

import net.minecraft.entity.*;
import net.minecraft.util.*;
import java.util.*;

public class EntityAIHurtByTarget extends EntityAITarget
{
    private final Class[] targetClasses;
    private boolean entityCallsForHelp;
    private int revengeTimerOld;
    
    @Override
    public boolean shouldExecute() {
        if (this.taskOwner.getRevengeTimer() != this.revengeTimerOld && this.isSuitableTarget(this.taskOwner.getAITarget(), "".length() != 0)) {
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
            if (0 >= 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public EntityAIHurtByTarget(final EntityCreature entityCreature, final boolean entityCallsForHelp, final Class... targetClasses) {
        super(entityCreature, "".length() != 0);
        this.entityCallsForHelp = entityCallsForHelp;
        this.targetClasses = targetClasses;
        this.setMutexBits(" ".length());
    }
    
    protected void setEntityAttackTarget(final EntityCreature entityCreature, final EntityLivingBase attackTarget) {
        entityCreature.setAttackTarget(attackTarget);
    }
    
    @Override
    public void startExecuting() {
        this.taskOwner.setAttackTarget(this.taskOwner.getAITarget());
        this.revengeTimerOld = this.taskOwner.getRevengeTimer();
        if (this.entityCallsForHelp) {
            final double targetDistance = this.getTargetDistance();
            final Iterator<EntityCreature> iterator = this.taskOwner.worldObj.getEntitiesWithinAABB(this.taskOwner.getClass(), new AxisAlignedBB(this.taskOwner.posX, this.taskOwner.posY, this.taskOwner.posZ, this.taskOwner.posX + 1.0, this.taskOwner.posY + 1.0, this.taskOwner.posZ + 1.0).expand(targetDistance, 10.0, targetDistance)).iterator();
            "".length();
            if (3 != 3) {
                throw null;
            }
            while (iterator.hasNext()) {
                final EntityCreature entityCreature = iterator.next();
                if (this.taskOwner != entityCreature && entityCreature.getAttackTarget() == null && !entityCreature.isOnSameTeam(this.taskOwner.getAITarget())) {
                    int n = "".length();
                    final Class[] targetClasses;
                    final int length = (targetClasses = this.targetClasses).length;
                    int i = "".length();
                    "".length();
                    if (-1 >= 4) {
                        throw null;
                    }
                    while (i < length) {
                        if (entityCreature.getClass() == targetClasses[i]) {
                            n = " ".length();
                            "".length();
                            if (2 != 2) {
                                throw null;
                            }
                            break;
                        }
                        else {
                            ++i;
                        }
                    }
                    if (n != 0) {
                        continue;
                    }
                    this.setEntityAttackTarget(entityCreature, this.taskOwner.getAITarget());
                }
            }
        }
        super.startExecuting();
    }
}

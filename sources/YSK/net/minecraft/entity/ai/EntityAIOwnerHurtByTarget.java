package net.minecraft.entity.ai;

import net.minecraft.entity.passive.*;
import net.minecraft.entity.*;

public class EntityAIOwnerHurtByTarget extends EntityAITarget
{
    EntityLivingBase theOwnerAttacker;
    EntityTameable theDefendingTameable;
    private int field_142051_e;
    
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
            if (4 == -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public boolean shouldExecute() {
        if (!this.theDefendingTameable.isTamed()) {
            return "".length() != 0;
        }
        final EntityLivingBase owner = this.theDefendingTameable.getOwner();
        if (owner == null) {
            return "".length() != 0;
        }
        this.theOwnerAttacker = owner.getAITarget();
        if (owner.getRevengeTimer() != this.field_142051_e && this.isSuitableTarget(this.theOwnerAttacker, "".length() != 0) && this.theDefendingTameable.shouldAttackEntity(this.theOwnerAttacker, owner)) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public void startExecuting() {
        this.taskOwner.setAttackTarget(this.theOwnerAttacker);
        final EntityLivingBase owner = this.theDefendingTameable.getOwner();
        if (owner != null) {
            this.field_142051_e = owner.getRevengeTimer();
        }
        super.startExecuting();
    }
    
    public EntityAIOwnerHurtByTarget(final EntityTameable theDefendingTameable) {
        super(theDefendingTameable, "".length() != 0);
        this.theDefendingTameable = theDefendingTameable;
        this.setMutexBits(" ".length());
    }
}

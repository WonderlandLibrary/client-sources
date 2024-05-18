package net.minecraft.entity.ai;

import net.minecraft.entity.passive.*;
import net.minecraft.entity.*;

public class EntityAIOwnerHurtTarget extends EntityAITarget
{
    private int field_142050_e;
    EntityTameable theEntityTameable;
    EntityLivingBase theTarget;
    
    @Override
    public boolean shouldExecute() {
        if (!this.theEntityTameable.isTamed()) {
            return "".length() != 0;
        }
        final EntityLivingBase owner = this.theEntityTameable.getOwner();
        if (owner == null) {
            return "".length() != 0;
        }
        this.theTarget = owner.getLastAttacker();
        if (owner.getLastAttackerTime() != this.field_142050_e && this.isSuitableTarget(this.theTarget, "".length() != 0) && this.theEntityTameable.shouldAttackEntity(this.theTarget, owner)) {
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
            if (-1 != -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public EntityAIOwnerHurtTarget(final EntityTameable theEntityTameable) {
        super(theEntityTameable, "".length() != 0);
        this.theEntityTameable = theEntityTameable;
        this.setMutexBits(" ".length());
    }
    
    @Override
    public void startExecuting() {
        this.taskOwner.setAttackTarget(this.theTarget);
        final EntityLivingBase owner = this.theEntityTameable.getOwner();
        if (owner != null) {
            this.field_142050_e = owner.getLastAttackerTime();
        }
        super.startExecuting();
    }
}

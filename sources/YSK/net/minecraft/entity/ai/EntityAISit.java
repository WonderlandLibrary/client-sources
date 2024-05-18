package net.minecraft.entity.ai;

import net.minecraft.entity.passive.*;
import net.minecraft.entity.*;

public class EntityAISit extends EntityAIBase
{
    private boolean isSitting;
    private EntityTameable theEntity;
    
    @Override
    public void resetTask() {
        this.theEntity.setSitting("".length() != 0);
    }
    
    public EntityAISit(final EntityTameable theEntity) {
        this.theEntity = theEntity;
        this.setMutexBits(0xA6 ^ 0xA3);
    }
    
    @Override
    public boolean shouldExecute() {
        if (!this.theEntity.isTamed()) {
            return "".length() != 0;
        }
        if (this.theEntity.isInWater()) {
            return "".length() != 0;
        }
        if (!this.theEntity.onGround) {
            return "".length() != 0;
        }
        final EntityLivingBase owner = this.theEntity.getOwner();
        int n;
        if (owner == null) {
            n = " ".length();
            "".length();
            if (true != true) {
                throw null;
            }
        }
        else if (this.theEntity.getDistanceSqToEntity(owner) < 144.0 && owner.getAITarget() != null) {
            n = "".length();
            "".length();
            if (true != true) {
                throw null;
            }
        }
        else {
            n = (this.isSitting ? 1 : 0);
        }
        return n != 0;
    }
    
    @Override
    public void startExecuting() {
        this.theEntity.getNavigator().clearPathEntity();
        this.theEntity.setSitting(" ".length() != 0);
    }
    
    public void setSitting(final boolean isSitting) {
        this.isSitting = isSitting;
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
}

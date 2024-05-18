package net.minecraft.entity.ai;

import net.minecraft.entity.*;
import net.minecraft.pathfinding.*;

public class EntityAISwimming extends EntityAIBase
{
    private EntityLiving theEntity;
    
    public EntityAISwimming(final EntityLiving theEntity) {
        this.theEntity = theEntity;
        this.setMutexBits(0xBB ^ 0xBF);
        ((PathNavigateGround)theEntity.getNavigator()).setCanSwim(" ".length() != 0);
    }
    
    @Override
    public void updateTask() {
        if (this.theEntity.getRNG().nextFloat() < 0.8f) {
            this.theEntity.getJumpHelper().setJumping();
        }
    }
    
    @Override
    public boolean shouldExecute() {
        if (!this.theEntity.isInWater() && !this.theEntity.isInLava()) {
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
            if (2 <= -1) {
                throw null;
            }
        }
        return sb.toString();
    }
}

package net.minecraft.entity.ai;

import net.minecraft.entity.monster.*;
import net.minecraft.entity.*;

public class EntityAICreeperSwell extends EntityAIBase
{
    EntityCreeper swellingCreeper;
    EntityLivingBase creeperAttackTarget;
    
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
            if (4 != 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void resetTask() {
        this.creeperAttackTarget = null;
    }
    
    public EntityAICreeperSwell(final EntityCreeper swellingCreeper) {
        this.swellingCreeper = swellingCreeper;
        this.setMutexBits(" ".length());
    }
    
    @Override
    public void startExecuting() {
        this.swellingCreeper.getNavigator().clearPathEntity();
        this.creeperAttackTarget = this.swellingCreeper.getAttackTarget();
    }
    
    @Override
    public boolean shouldExecute() {
        final EntityLivingBase attackTarget = this.swellingCreeper.getAttackTarget();
        if (this.swellingCreeper.getCreeperState() <= 0 && (attackTarget == null || this.swellingCreeper.getDistanceSqToEntity(attackTarget) >= 9.0)) {
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
    
    @Override
    public void updateTask() {
        if (this.creeperAttackTarget == null) {
            this.swellingCreeper.setCreeperState(-" ".length());
            "".length();
            if (1 == 2) {
                throw null;
            }
        }
        else if (this.swellingCreeper.getDistanceSqToEntity(this.creeperAttackTarget) > 49.0) {
            this.swellingCreeper.setCreeperState(-" ".length());
            "".length();
            if (-1 == 3) {
                throw null;
            }
        }
        else if (!this.swellingCreeper.getEntitySenses().canSee(this.creeperAttackTarget)) {
            this.swellingCreeper.setCreeperState(-" ".length());
            "".length();
            if (3 <= 1) {
                throw null;
            }
        }
        else {
            this.swellingCreeper.setCreeperState(" ".length());
        }
    }
}

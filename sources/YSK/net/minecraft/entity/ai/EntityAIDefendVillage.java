package net.minecraft.entity.ai;

import net.minecraft.entity.monster.*;
import net.minecraft.village.*;
import net.minecraft.entity.*;

public class EntityAIDefendVillage extends EntityAITarget
{
    EntityIronGolem irongolem;
    EntityLivingBase villageAgressorTarget;
    
    @Override
    public void startExecuting() {
        this.irongolem.setAttackTarget(this.villageAgressorTarget);
        super.startExecuting();
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
            if (2 != 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public boolean shouldExecute() {
        final Village village = this.irongolem.getVillage();
        if (village == null) {
            return "".length() != 0;
        }
        this.villageAgressorTarget = village.findNearestVillageAggressor(this.irongolem);
        if (this.villageAgressorTarget instanceof EntityCreeper) {
            return "".length() != 0;
        }
        if (this.isSuitableTarget(this.villageAgressorTarget, "".length() != 0)) {
            return " ".length() != 0;
        }
        if (this.taskOwner.getRNG().nextInt(0x54 ^ 0x40) == 0) {
            this.villageAgressorTarget = village.getNearestTargetPlayer(this.irongolem);
            return this.isSuitableTarget(this.villageAgressorTarget, "".length() != 0);
        }
        return "".length() != 0;
    }
    
    public EntityAIDefendVillage(final EntityIronGolem irongolem) {
        super(irongolem, "".length() != 0, " ".length() != 0);
        this.irongolem = irongolem;
        this.setMutexBits(" ".length());
    }
}

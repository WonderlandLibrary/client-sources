package net.minecraft.entity.ai;

import net.minecraft.entity.*;
import net.minecraft.util.*;

public class EntityAIPanic extends EntityAIBase
{
    protected double speed;
    private EntityCreature theEntityCreature;
    private double randPosX;
    private double randPosZ;
    private double randPosY;
    
    @Override
    public boolean shouldExecute() {
        if (this.theEntityCreature.getAITarget() == null && !this.theEntityCreature.isBurning()) {
            return "".length() != 0;
        }
        final Vec3 randomTarget = RandomPositionGenerator.findRandomTarget(this.theEntityCreature, 0xB4 ^ 0xB1, 0x13 ^ 0x17);
        if (randomTarget == null) {
            return "".length() != 0;
        }
        this.randPosX = randomTarget.xCoord;
        this.randPosY = randomTarget.yCoord;
        this.randPosZ = randomTarget.zCoord;
        return " ".length() != 0;
    }
    
    public EntityAIPanic(final EntityCreature theEntityCreature, final double speed) {
        this.theEntityCreature = theEntityCreature;
        this.speed = speed;
        this.setMutexBits(" ".length());
    }
    
    @Override
    public void startExecuting() {
        this.theEntityCreature.getNavigator().tryMoveToXYZ(this.randPosX, this.randPosY, this.randPosZ, this.speed);
    }
    
    @Override
    public boolean continueExecuting() {
        int n;
        if (this.theEntityCreature.getNavigator().noPath()) {
            n = "".length();
            "".length();
            if (0 <= -1) {
                throw null;
            }
        }
        else {
            n = " ".length();
        }
        return n != 0;
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
}

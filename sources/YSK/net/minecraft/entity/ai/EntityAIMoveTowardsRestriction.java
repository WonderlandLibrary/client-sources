package net.minecraft.entity.ai;

import net.minecraft.entity.*;
import net.minecraft.util.*;

public class EntityAIMoveTowardsRestriction extends EntityAIBase
{
    private EntityCreature theEntity;
    private double movePosX;
    private double movePosZ;
    private double movePosY;
    private double movementSpeed;
    
    @Override
    public boolean continueExecuting() {
        int n;
        if (this.theEntity.getNavigator().noPath()) {
            n = "".length();
            "".length();
            if (2 < 1) {
                throw null;
            }
        }
        else {
            n = " ".length();
        }
        return n != 0;
    }
    
    @Override
    public void startExecuting() {
        this.theEntity.getNavigator().tryMoveToXYZ(this.movePosX, this.movePosY, this.movePosZ, this.movementSpeed);
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
            if (4 < -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public EntityAIMoveTowardsRestriction(final EntityCreature theEntity, final double movementSpeed) {
        this.theEntity = theEntity;
        this.movementSpeed = movementSpeed;
        this.setMutexBits(" ".length());
    }
    
    @Override
    public boolean shouldExecute() {
        if (this.theEntity.isWithinHomeDistanceCurrentPosition()) {
            return "".length() != 0;
        }
        final BlockPos homePosition = this.theEntity.getHomePosition();
        final Vec3 randomTargetBlockTowards = RandomPositionGenerator.findRandomTargetBlockTowards(this.theEntity, 0xA6 ^ 0xB6, 0xB5 ^ 0xB2, new Vec3(homePosition.getX(), homePosition.getY(), homePosition.getZ()));
        if (randomTargetBlockTowards == null) {
            return "".length() != 0;
        }
        this.movePosX = randomTargetBlockTowards.xCoord;
        this.movePosY = randomTargetBlockTowards.yCoord;
        this.movePosZ = randomTargetBlockTowards.zCoord;
        return " ".length() != 0;
    }
}

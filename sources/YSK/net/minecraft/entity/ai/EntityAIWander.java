package net.minecraft.entity.ai;

import net.minecraft.entity.*;
import net.minecraft.util.*;

public class EntityAIWander extends EntityAIBase
{
    private double yPosition;
    private double speed;
    private double zPosition;
    private double xPosition;
    private int executionChance;
    private EntityCreature entity;
    private boolean mustUpdate;
    
    public void makeUpdate() {
        this.mustUpdate = (" ".length() != 0);
    }
    
    public EntityAIWander(final EntityCreature entity, final double speed, final int executionChance) {
        this.entity = entity;
        this.speed = speed;
        this.executionChance = executionChance;
        this.setMutexBits(" ".length());
    }
    
    public EntityAIWander(final EntityCreature entityCreature, final double n) {
        this(entityCreature, n, 0xC6 ^ 0xBE);
    }
    
    @Override
    public boolean continueExecuting() {
        int n;
        if (this.entity.getNavigator().noPath()) {
            n = "".length();
            "".length();
            if (2 < 2) {
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
            if (3 >= 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public void setExecutionChance(final int executionChance) {
        this.executionChance = executionChance;
    }
    
    @Override
    public boolean shouldExecute() {
        if (!this.mustUpdate) {
            if (this.entity.getAge() >= (0x40 ^ 0x24)) {
                return "".length() != 0;
            }
            if (this.entity.getRNG().nextInt(this.executionChance) != 0) {
                return "".length() != 0;
            }
        }
        final Vec3 randomTarget = RandomPositionGenerator.findRandomTarget(this.entity, 0xAC ^ 0xA6, 0xBF ^ 0xB8);
        if (randomTarget == null) {
            return "".length() != 0;
        }
        this.xPosition = randomTarget.xCoord;
        this.yPosition = randomTarget.yCoord;
        this.zPosition = randomTarget.zCoord;
        this.mustUpdate = ("".length() != 0);
        return " ".length() != 0;
    }
    
    @Override
    public void startExecuting() {
        this.entity.getNavigator().tryMoveToXYZ(this.xPosition, this.yPosition, this.zPosition, this.speed);
    }
}

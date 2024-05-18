package net.minecraft.entity.ai;

import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;

public abstract class EntityAIMoveToBlock extends EntityAIBase
{
    private final EntityCreature theEntity;
    private boolean isAboveDestination;
    protected BlockPos destinationBlock;
    private int timeoutCounter;
    private final double movementSpeed;
    protected int runDelay;
    private int field_179490_f;
    private int searchLength;
    
    @Override
    public void startExecuting() {
        this.theEntity.getNavigator().tryMoveToXYZ(this.destinationBlock.getX() + 0.5, this.destinationBlock.getY() + " ".length(), this.destinationBlock.getZ() + 0.5, this.movementSpeed);
        this.timeoutCounter = "".length();
        this.field_179490_f = this.theEntity.getRNG().nextInt(this.theEntity.getRNG().nextInt(968 + 63 - 749 + 918) + (645 + 1066 - 1049 + 538)) + (91 + 506 - 463 + 1066);
    }
    
    public EntityAIMoveToBlock(final EntityCreature theEntity, final double movementSpeed, final int searchLength) {
        this.destinationBlock = BlockPos.ORIGIN;
        this.theEntity = theEntity;
        this.movementSpeed = movementSpeed;
        this.searchLength = searchLength;
        this.setMutexBits(0xAB ^ 0xAE);
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
            if (false == true) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void updateTask() {
        if (this.theEntity.getDistanceSqToCenter(this.destinationBlock.up()) > 1.0) {
            this.isAboveDestination = ("".length() != 0);
            this.timeoutCounter += " ".length();
            if (this.timeoutCounter % (0x5F ^ 0x77) == 0) {
                this.theEntity.getNavigator().tryMoveToXYZ(this.destinationBlock.getX() + 0.5, this.destinationBlock.getY() + " ".length(), this.destinationBlock.getZ() + 0.5, this.movementSpeed);
                "".length();
                if (-1 >= 1) {
                    throw null;
                }
            }
        }
        else {
            this.isAboveDestination = (" ".length() != 0);
            this.timeoutCounter -= " ".length();
        }
    }
    
    @Override
    public boolean shouldExecute() {
        if (this.runDelay > 0) {
            this.runDelay -= " ".length();
            return "".length() != 0;
        }
        this.runDelay = 167 + 29 - 195 + 199 + this.theEntity.getRNG().nextInt(179 + 136 - 284 + 169);
        return this.searchForDestination();
    }
    
    protected boolean getIsAboveDestination() {
        return this.isAboveDestination;
    }
    
    @Override
    public void resetTask() {
    }
    
    private boolean searchForDestination() {
        final int searchLength = this.searchLength;
        " ".length();
        final BlockPos blockPos = new BlockPos(this.theEntity);
        int i = "".length();
        "".length();
        if (1 >= 3) {
            throw null;
        }
        while (i <= " ".length()) {
            int j = "".length();
            "".length();
            if (4 == 0) {
                throw null;
            }
            while (j < searchLength) {
                int k = "".length();
                "".length();
                if (4 == 2) {
                    throw null;
                }
                while (k <= j) {
                    int length;
                    if (k < j && k > -j) {
                        length = j;
                        "".length();
                        if (-1 >= 4) {
                            throw null;
                        }
                    }
                    else {
                        length = "".length();
                    }
                    int l = length;
                    "".length();
                    if (0 >= 4) {
                        throw null;
                    }
                    while (l <= j) {
                        final BlockPos add = blockPos.add(k, i - " ".length(), l);
                        if (this.theEntity.isWithinHomeDistanceFromPosition(add) && this.shouldMoveTo(this.theEntity.worldObj, add)) {
                            this.destinationBlock = add;
                            return " ".length() != 0;
                        }
                        int n;
                        if (l > 0) {
                            n = -l;
                            "".length();
                            if (4 < 0) {
                                throw null;
                            }
                        }
                        else {
                            n = " ".length() - l;
                        }
                        l = n;
                    }
                    int n2;
                    if (k > 0) {
                        n2 = -k;
                        "".length();
                        if (false) {
                            throw null;
                        }
                    }
                    else {
                        n2 = " ".length() - k;
                    }
                    k = n2;
                }
                ++j;
            }
            int n3;
            if (i > 0) {
                n3 = -i;
                "".length();
                if (1 >= 4) {
                    throw null;
                }
            }
            else {
                n3 = " ".length() - i;
            }
            i = n3;
        }
        return "".length() != 0;
    }
    
    protected abstract boolean shouldMoveTo(final World p0, final BlockPos p1);
    
    @Override
    public boolean continueExecuting() {
        if (this.timeoutCounter >= -this.field_179490_f && this.timeoutCounter <= 214 + 36 + 545 + 405 && this.shouldMoveTo(this.theEntity.worldObj, this.destinationBlock)) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
}

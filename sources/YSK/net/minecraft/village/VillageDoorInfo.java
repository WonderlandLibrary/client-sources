package net.minecraft.village;

import net.minecraft.util.*;

public class VillageDoorInfo
{
    private final EnumFacing insideDirection;
    private boolean isDetachedFromVillageFlag;
    private int doorOpeningRestrictionCounter;
    private final BlockPos doorBlockPos;
    private int lastActivityTimestamp;
    private final BlockPos insideBlock;
    
    public void func_179849_a(final int lastActivityTimestamp) {
        this.lastActivityTimestamp = lastActivityTimestamp;
    }
    
    public boolean getIsDetachedFromVillageFlag() {
        return this.isDetachedFromVillageFlag;
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
            if (4 < 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public VillageDoorInfo(final BlockPos blockPos, final int n, final int n2, final int n3) {
        this(blockPos, getFaceDirection(n, n2), n3);
    }
    
    public int getInsideOffsetZ() {
        return this.insideDirection.getFrontOffsetZ() * "  ".length();
    }
    
    public int getInsidePosY() {
        return this.lastActivityTimestamp;
    }
    
    public boolean func_179850_c(final BlockPos blockPos) {
        if ((blockPos.getX() - this.doorBlockPos.getX()) * this.insideDirection.getFrontOffsetX() + (blockPos.getZ() - this.doorBlockPos.getY()) * this.insideDirection.getFrontOffsetZ() >= 0) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public void incrementDoorOpeningRestrictionCounter() {
        this.doorOpeningRestrictionCounter += " ".length();
    }
    
    public int getDistanceSquared(final int n, final int n2, final int n3) {
        return (int)this.doorBlockPos.distanceSq(n, n2, n3);
    }
    
    public int getDistanceToDoorBlockSq(final BlockPos blockPos) {
        return (int)blockPos.distanceSq(this.getDoorBlockPos());
    }
    
    public VillageDoorInfo(final BlockPos doorBlockPos, final EnumFacing insideDirection, final int lastActivityTimestamp) {
        this.doorBlockPos = doorBlockPos;
        this.insideDirection = insideDirection;
        this.insideBlock = doorBlockPos.offset(insideDirection, "  ".length());
        this.lastActivityTimestamp = lastActivityTimestamp;
    }
    
    private static EnumFacing getFaceDirection(final int n, final int n2) {
        EnumFacing enumFacing;
        if (n < 0) {
            enumFacing = EnumFacing.WEST;
            "".length();
            if (4 < 3) {
                throw null;
            }
        }
        else if (n > 0) {
            enumFacing = EnumFacing.EAST;
            "".length();
            if (3 != 3) {
                throw null;
            }
        }
        else if (n2 < 0) {
            enumFacing = EnumFacing.NORTH;
            "".length();
            if (1 >= 3) {
                throw null;
            }
        }
        else {
            enumFacing = EnumFacing.SOUTH;
        }
        return enumFacing;
    }
    
    public int getInsideOffsetX() {
        return this.insideDirection.getFrontOffsetX() * "  ".length();
    }
    
    public BlockPos getInsideBlockPos() {
        return this.insideBlock;
    }
    
    public BlockPos getDoorBlockPos() {
        return this.doorBlockPos;
    }
    
    public int getDistanceToInsideBlockSq(final BlockPos blockPos) {
        return (int)this.insideBlock.distanceSq(blockPos);
    }
    
    public int getDoorOpeningRestrictionCounter() {
        return this.doorOpeningRestrictionCounter;
    }
    
    public void resetDoorOpeningRestrictionCounter() {
        this.doorOpeningRestrictionCounter = "".length();
    }
    
    public void setIsDetachedFromVillageFlag(final boolean isDetachedFromVillageFlag) {
        this.isDetachedFromVillageFlag = isDetachedFromVillageFlag;
    }
}

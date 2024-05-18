package net.minecraft.world;

import net.minecraft.util.*;

public class ChunkCoordIntPair
{
    private static final String[] I;
    public final int chunkXPos;
    private static final String __OBFID;
    public final int chunkZPos;
    private int cachedHashCode;
    
    public int getXEnd() {
        return (this.chunkXPos << (0x7F ^ 0x7B)) + (0xB9 ^ 0xB6);
    }
    
    public int getZStart() {
        return this.chunkZPos << (0x8D ^ 0x89);
    }
    
    public BlockPos getCenterBlock(final int n) {
        return new BlockPos(this.getCenterXPos(), n, this.getCenterZPosition());
    }
    
    public BlockPos getBlock(final int n, final int n2, final int n3) {
        return new BlockPos((this.chunkXPos << (0x3A ^ 0x3E)) + n, n2, (this.chunkZPos << (0xAD ^ 0xA9)) + n3);
    }
    
    @Override
    public int hashCode() {
        if (this.cachedHashCode == 0) {
            this.cachedHashCode = ((1195900 + 716243 - 1811797 + 1564179) * this.chunkXPos + (692616799 + 484816197 - 1027511985 + 863983212) ^ (1062461 + 1481250 - 958006 + 78820) * (this.chunkZPos ^ -(174866622 + 416489467 - 177374926 + 145057574)) + (624791274 + 751246995 - 588097394 + 225963348));
        }
        return this.cachedHashCode;
    }
    
    public int getCenterXPos() {
        return (this.chunkXPos << (0xBD ^ 0xB9)) + (0x9A ^ 0x92);
    }
    
    @Override
    public String toString() {
        return ChunkCoordIntPair.I["".length()] + this.chunkXPos + ChunkCoordIntPair.I[" ".length()] + this.chunkZPos + ChunkCoordIntPair.I["  ".length()];
    }
    
    public ChunkCoordIntPair(final int chunkXPos, final int chunkZPos) {
        this.cachedHashCode = "".length();
        this.chunkXPos = chunkXPos;
        this.chunkZPos = chunkZPos;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return " ".length() != 0;
        }
        if (!(o instanceof ChunkCoordIntPair)) {
            return "".length() != 0;
        }
        final ChunkCoordIntPair chunkCoordIntPair = (ChunkCoordIntPair)o;
        if (this.chunkXPos == chunkCoordIntPair.chunkXPos && this.chunkZPos == chunkCoordIntPair.chunkZPos) {
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
            if (3 < 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public int getXStart() {
        return this.chunkXPos << (0x4D ^ 0x49);
    }
    
    public static long chunkXZ2Int(final int n, final int n2) {
        return (n & 0xFFFFFFFFL) | (n2 & 0xFFFFFFFFL) << (0x72 ^ 0x52);
    }
    
    public int getCenterZPosition() {
        return (this.chunkZPos << (0xB2 ^ 0xB6)) + (0x99 ^ 0x91);
    }
    
    static {
        I();
        __OBFID = ChunkCoordIntPair.I["   ".length()];
    }
    
    public int getZEnd() {
        return (this.chunkZPos << (0x86 ^ 0x82)) + (0x1B ^ 0x14);
    }
    
    private static void I() {
        (I = new String[0xC ^ 0x8])["".length()] = I("\u0015", "NpGab");
        ChunkCoordIntPair.I[" ".length()] = I("Td", "xDiUp");
        ChunkCoordIntPair.I["  ".length()] = I("*", "wXzmx");
        ChunkCoordIntPair.I["   ".length()] = I("\u0011.2txbR]u{a", "RbmDH");
    }
}

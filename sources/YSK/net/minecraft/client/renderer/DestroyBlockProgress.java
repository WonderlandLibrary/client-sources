package net.minecraft.client.renderer;

import net.minecraft.util.*;

public class DestroyBlockProgress
{
    private int partialBlockProgress;
    private int createdAtCloudUpdateTick;
    private final BlockPos position;
    private final int miningPlayerEntId;
    
    public int getPartialBlockDamage() {
        return this.partialBlockProgress;
    }
    
    public DestroyBlockProgress(final int miningPlayerEntId, final BlockPos position) {
        this.miningPlayerEntId = miningPlayerEntId;
        this.position = position;
    }
    
    public int getCreationCloudUpdateTick() {
        return this.createdAtCloudUpdateTick;
    }
    
    public void setCloudUpdateTick(final int createdAtCloudUpdateTick) {
        this.createdAtCloudUpdateTick = createdAtCloudUpdateTick;
    }
    
    public void setPartialBlockDamage(int partialBlockProgress) {
        if (partialBlockProgress > (0x39 ^ 0x33)) {
            partialBlockProgress = (0x5D ^ 0x57);
        }
        this.partialBlockProgress = partialBlockProgress;
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
            if (-1 < -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public BlockPos getPosition() {
        return this.position;
    }
}

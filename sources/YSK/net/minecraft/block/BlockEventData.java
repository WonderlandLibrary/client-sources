package net.minecraft.block;

import net.minecraft.util.*;

public class BlockEventData
{
    private int eventParameter;
    private int eventID;
    private Block blockType;
    private BlockPos position;
    private static final String[] I;
    
    public Block getBlock() {
        return this.blockType;
    }
    
    public BlockEventData(final BlockPos position, final Block blockType, final int eventID, final int eventParameter) {
        this.position = position;
        this.eventID = eventID;
        this.eventParameter = eventParameter;
        this.blockType = blockType;
    }
    
    @Override
    public String toString() {
        return BlockEventData.I["".length()] + this.position + BlockEventData.I[" ".length()] + this.eventID + BlockEventData.I["  ".length()] + this.eventParameter + BlockEventData.I["   ".length()] + this.blockType;
    }
    
    public int getEventID() {
        return this.eventID;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof BlockEventData)) {
            return "".length() != 0;
        }
        final BlockEventData blockEventData = (BlockEventData)o;
        if (this.position.equals(blockEventData.position) && this.eventID == blockEventData.eventID && this.eventParameter == blockEventData.eventParameter && this.blockType == blockEventData.blockType) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public int getEventParameter() {
        return this.eventParameter;
    }
    
    private static void I() {
        (I = new String[0xD ^ 0x9])["".length()] = I("\u001f\u0007i", "KBAiv");
        BlockEventData.I[" ".length()] = I("d~", "MRNIX");
        BlockEventData.I["  ".length()] = I("~", "RUmzc");
        BlockEventData.I["   ".length()] = I("Z", "vZYiD");
    }
    
    static {
        I();
    }
    
    public BlockPos getPosition() {
        return this.position;
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
            if (0 < -1) {
                throw null;
            }
        }
        return sb.toString();
    }
}

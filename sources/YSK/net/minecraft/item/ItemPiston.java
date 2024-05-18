package net.minecraft.item;

import net.minecraft.block.*;

public class ItemPiston extends ItemBlock
{
    @Override
    public int getMetadata(final int n) {
        return 0x86 ^ 0x81;
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
            if (3 < 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public ItemPiston(final Block block) {
        super(block);
    }
}

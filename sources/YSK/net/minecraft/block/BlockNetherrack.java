package net.minecraft.block;

import net.minecraft.block.state.*;
import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;

public class BlockNetherrack extends Block
{
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
            if (0 < 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public MapColor getMapColor(final IBlockState blockState) {
        return MapColor.netherrackColor;
    }
    
    public BlockNetherrack() {
        super(Material.rock);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
}

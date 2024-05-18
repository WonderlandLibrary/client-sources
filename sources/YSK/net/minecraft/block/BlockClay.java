package net.minecraft.block;

import java.util.*;
import net.minecraft.block.state.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;

public class BlockClay extends Block
{
    @Override
    public int quantityDropped(final Random random) {
        return 0x4A ^ 0x4E;
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
            if (2 < 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return Items.clay_ball;
    }
    
    public BlockClay() {
        super(Material.clay);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
}

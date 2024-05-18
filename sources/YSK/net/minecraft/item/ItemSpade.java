package net.minecraft.item;

import java.util.*;
import net.minecraft.block.*;
import net.minecraft.init.*;
import com.google.common.collect.*;

public class ItemSpade extends ItemTool
{
    private static final Set<Block> EFFECTIVE_ON;
    
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
            if (1 == 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public boolean canHarvestBlock(final Block block) {
        int n;
        if (block == Blocks.snow_layer) {
            n = " ".length();
            "".length();
            if (0 >= 4) {
                throw null;
            }
        }
        else if (block == Blocks.snow) {
            n = " ".length();
            "".length();
            if (3 <= 1) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n != 0;
    }
    
    static {
        final Block[] array = new Block[0x6F ^ 0x65];
        array["".length()] = Blocks.clay;
        array[" ".length()] = Blocks.dirt;
        array["  ".length()] = Blocks.farmland;
        array["   ".length()] = Blocks.grass;
        array[0x4B ^ 0x4F] = Blocks.gravel;
        array[0x12 ^ 0x17] = Blocks.mycelium;
        array[0x1E ^ 0x18] = Blocks.sand;
        array[0xAE ^ 0xA9] = Blocks.snow;
        array[0x15 ^ 0x1D] = Blocks.snow_layer;
        array[0x2B ^ 0x22] = Blocks.soul_sand;
        EFFECTIVE_ON = Sets.newHashSet((Object[])array);
    }
    
    public ItemSpade(final ToolMaterial toolMaterial) {
        super(1.0f, toolMaterial, ItemSpade.EFFECTIVE_ON);
    }
}

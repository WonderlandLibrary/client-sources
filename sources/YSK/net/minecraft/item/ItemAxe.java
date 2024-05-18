package net.minecraft.item;

import java.util.*;
import net.minecraft.block.*;
import net.minecraft.init.*;
import com.google.common.collect.*;
import net.minecraft.block.material.*;

public class ItemAxe extends ItemTool
{
    private static final Set<Block> EFFECTIVE_ON;
    
    protected ItemAxe(final ToolMaterial toolMaterial) {
        super(3.0f, toolMaterial, ItemAxe.EFFECTIVE_ON);
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
            if (1 < 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static {
        final Block[] array = new Block[0x8 ^ 0x1];
        array["".length()] = Blocks.planks;
        array[" ".length()] = Blocks.bookshelf;
        array["  ".length()] = Blocks.log;
        array["   ".length()] = Blocks.log2;
        array[0x39 ^ 0x3D] = Blocks.chest;
        array[0x95 ^ 0x90] = Blocks.pumpkin;
        array[0xB0 ^ 0xB6] = Blocks.lit_pumpkin;
        array[0xBC ^ 0xBB] = Blocks.melon_block;
        array[0x13 ^ 0x1B] = Blocks.ladder;
        EFFECTIVE_ON = Sets.newHashSet((Object[])array);
    }
    
    @Override
    public float getStrVsBlock(final ItemStack itemStack, final Block block) {
        float n;
        if (block.getMaterial() != Material.wood && block.getMaterial() != Material.plants && block.getMaterial() != Material.vine) {
            n = super.getStrVsBlock(itemStack, block);
            "".length();
            if (2 <= 0) {
                throw null;
            }
        }
        else {
            n = this.efficiencyOnProperMaterial;
        }
        return n;
    }
}

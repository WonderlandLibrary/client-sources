package net.minecraft.item;

import net.minecraft.creativetab.*;
import net.minecraft.world.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.block.material.*;
import net.minecraft.init.*;

public class ItemShears extends Item
{
    public ItemShears() {
        this.setMaxStackSize(" ".length());
        this.setMaxDamage(73 + 29 - 95 + 231);
        this.setCreativeTab(CreativeTabs.tabTools);
    }
    
    @Override
    public boolean onBlockDestroyed(final ItemStack itemStack, final World world, final Block block, final BlockPos blockPos, final EntityLivingBase entityLivingBase) {
        if (block.getMaterial() != Material.leaves && block != Blocks.web && block != Blocks.tallgrass && block != Blocks.vine && block != Blocks.tripwire && block != Blocks.wool) {
            return super.onBlockDestroyed(itemStack, world, block, blockPos, entityLivingBase);
        }
        itemStack.damageItem(" ".length(), entityLivingBase);
        return " ".length() != 0;
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
            if (4 < -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public float getStrVsBlock(final ItemStack itemStack, final Block block) {
        float strVsBlock;
        if (block != Blocks.web && block.getMaterial() != Material.leaves) {
            if (block == Blocks.wool) {
                strVsBlock = 5.0f;
                "".length();
                if (0 <= -1) {
                    throw null;
                }
            }
            else {
                strVsBlock = super.getStrVsBlock(itemStack, block);
                "".length();
                if (3 < 3) {
                    throw null;
                }
            }
        }
        else {
            strVsBlock = 15.0f;
        }
        return strVsBlock;
    }
    
    @Override
    public boolean canHarvestBlock(final Block block) {
        if (block != Blocks.web && block != Blocks.redstone_wire && block != Blocks.tripwire) {
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
}

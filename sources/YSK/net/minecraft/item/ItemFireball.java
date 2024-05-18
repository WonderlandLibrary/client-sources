package net.minecraft.item;

import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.block.material.*;
import net.minecraft.init.*;
import net.minecraft.creativetab.*;

public class ItemFireball extends Item
{
    private static final String[] I;
    
    @Override
    public boolean onItemUse(final ItemStack itemStack, final EntityPlayer entityPlayer, final World world, BlockPos offset, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (world.isRemote) {
            return " ".length() != 0;
        }
        offset = offset.offset(enumFacing);
        if (!entityPlayer.canPlayerEdit(offset, enumFacing, itemStack)) {
            return "".length() != 0;
        }
        if (world.getBlockState(offset).getBlock().getMaterial() == Material.air) {
            world.playSoundEffect(offset.getX() + 0.5, offset.getY() + 0.5, offset.getZ() + 0.5, ItemFireball.I["".length()], 1.0f, (ItemFireball.itemRand.nextFloat() - ItemFireball.itemRand.nextFloat()) * 0.2f + 1.0f);
            world.setBlockState(offset, Blocks.fire.getDefaultState());
        }
        if (!entityPlayer.capabilities.isCreativeMode) {
            itemStack.stackSize -= " ".length();
        }
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
            if (4 <= 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public ItemFireball() {
        this.setCreativeTab(CreativeTabs.tabMisc);
    }
    
    static {
        I();
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\u0011\u0018+\u0017O\u001e\u0005<\u001f\"\u0010\r<\u001d\u0004V\u0019=\u001f", "xlNza");
    }
}

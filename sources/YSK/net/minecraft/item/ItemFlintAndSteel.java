package net.minecraft.item;

import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.block.material.*;
import net.minecraft.init.*;
import net.minecraft.entity.*;
import net.minecraft.creativetab.*;

public class ItemFlintAndSteel extends Item
{
    private static final String[] I;
    
    @Override
    public boolean onItemUse(final ItemStack itemStack, final EntityPlayer entityPlayer, final World world, BlockPos offset, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        offset = offset.offset(enumFacing);
        if (!entityPlayer.canPlayerEdit(offset, enumFacing, itemStack)) {
            return "".length() != 0;
        }
        if (world.getBlockState(offset).getBlock().getMaterial() == Material.air) {
            world.playSoundEffect(offset.getX() + 0.5, offset.getY() + 0.5, offset.getZ() + 0.5, ItemFlintAndSteel.I["".length()], 1.0f, ItemFlintAndSteel.itemRand.nextFloat() * 0.4f + 0.8f);
            world.setBlockState(offset, Blocks.fire.getDefaultState());
        }
        itemStack.damageItem(" ".length(), entityPlayer);
        return " ".length() != 0;
    }
    
    public ItemFlintAndSteel() {
        this.maxStackSize = " ".length();
        this.setMaxDamage(0x19 ^ 0x59);
        this.setCreativeTab(CreativeTabs.tabTools);
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\u0015\"47A\u001a,(;\u001b\u0016", "sKFRo");
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
            if (4 == 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static {
        I();
    }
}

package net.minecraft.item;

import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.entity.*;
import net.minecraft.stats.*;
import net.minecraft.creativetab.*;

public class ItemSnowball extends Item
{
    private static final String[] I;
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("+\u000f\n\u001c\r4@\u0006\u0017\u0015", "Yndxb");
    }
    
    @Override
    public ItemStack onItemRightClick(final ItemStack itemStack, final World world, final EntityPlayer entityPlayer) {
        if (!entityPlayer.capabilities.isCreativeMode) {
            itemStack.stackSize -= " ".length();
        }
        world.playSoundAtEntity(entityPlayer, ItemSnowball.I["".length()], 0.5f, 0.4f / (ItemSnowball.itemRand.nextFloat() * 0.4f + 0.8f));
        if (!world.isRemote) {
            world.spawnEntityInWorld(new EntitySnowball(world, entityPlayer));
        }
        entityPlayer.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
        return itemStack;
    }
    
    static {
        I();
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
            if (false) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public ItemSnowball() {
        this.maxStackSize = (0x24 ^ 0x34);
        this.setCreativeTab(CreativeTabs.tabMisc);
    }
}

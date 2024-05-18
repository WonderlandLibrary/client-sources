package net.minecraft.item;

import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.*;
import net.minecraft.stats.*;
import net.minecraft.creativetab.*;

public class ItemEnderPearl extends Item
{
    private static final String[] I;
    
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
            if (2 >= 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\u001b\u000f\u0014(;\u0004@\u0018##", "inzLT");
    }
    
    static {
        I();
    }
    
    @Override
    public ItemStack onItemRightClick(final ItemStack itemStack, final World world, final EntityPlayer entityPlayer) {
        if (entityPlayer.capabilities.isCreativeMode) {
            return itemStack;
        }
        itemStack.stackSize -= " ".length();
        world.playSoundAtEntity(entityPlayer, ItemEnderPearl.I["".length()], 0.5f, 0.4f / (ItemEnderPearl.itemRand.nextFloat() * 0.4f + 0.8f));
        if (!world.isRemote) {
            world.spawnEntityInWorld(new EntityEnderPearl(world, entityPlayer));
        }
        entityPlayer.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
        return itemStack;
    }
    
    public ItemEnderPearl() {
        this.maxStackSize = (0x4D ^ 0x5D);
        this.setCreativeTab(CreativeTabs.tabMisc);
    }
}

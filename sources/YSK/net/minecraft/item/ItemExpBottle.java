package net.minecraft.item;

import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.*;
import net.minecraft.stats.*;
import net.minecraft.creativetab.*;

public class ItemExpBottle extends Item
{
    private static final String[] I;
    
    static {
        I();
    }
    
    @Override
    public ItemStack onItemRightClick(final ItemStack itemStack, final World world, final EntityPlayer entityPlayer) {
        if (!entityPlayer.capabilities.isCreativeMode) {
            itemStack.stackSize -= " ".length();
        }
        world.playSoundAtEntity(entityPlayer, ItemExpBottle.I["".length()], 0.5f, 0.4f / (ItemExpBottle.itemRand.nextFloat() * 0.4f + 0.8f));
        if (!world.isRemote) {
            world.spawnEntityInWorld(new EntityExpBottle(world, entityPlayer));
        }
        entityPlayer.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
        return itemStack;
    }
    
    public ItemExpBottle() {
        this.setCreativeTab(CreativeTabs.tabMisc);
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
            if (-1 >= 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public boolean hasEffect(final ItemStack itemStack) {
        return " ".length() != 0;
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("7'\u001f\u0016\u001f(h\u0013\u001d\u0007", "EFqrp");
    }
}

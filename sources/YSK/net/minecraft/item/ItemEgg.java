package net.minecraft.item;

import net.minecraft.creativetab.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.entity.*;
import net.minecraft.stats.*;

public class ItemEgg extends Item
{
    private static final String[] I;
    
    public ItemEgg() {
        this.maxStackSize = (0xD3 ^ 0xC3);
        this.setCreativeTab(CreativeTabs.tabMaterials);
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("#,\u0003=\u0005<c\u000f6\u001d", "QMmYj");
    }
    
    static {
        I();
    }
    
    @Override
    public ItemStack onItemRightClick(final ItemStack itemStack, final World world, final EntityPlayer entityPlayer) {
        if (!entityPlayer.capabilities.isCreativeMode) {
            itemStack.stackSize -= " ".length();
        }
        world.playSoundAtEntity(entityPlayer, ItemEgg.I["".length()], 0.5f, 0.4f / (ItemEgg.itemRand.nextFloat() * 0.4f + 0.8f));
        if (!world.isRemote) {
            world.spawnEntityInWorld(new EntityEgg(world, entityPlayer));
        }
        entityPlayer.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
        return itemStack;
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
            if (0 == 4) {
                throw null;
            }
        }
        return sb.toString();
    }
}

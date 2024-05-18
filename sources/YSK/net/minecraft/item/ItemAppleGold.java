package net.minecraft.item;

import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.potion.*;
import net.minecraft.creativetab.*;
import java.util.*;

public class ItemAppleGold extends ItemFood
{
    public ItemAppleGold(final int n, final float n2, final boolean b) {
        super(n, n2, b);
        this.setHasSubtypes(" ".length() != 0);
    }
    
    @Override
    protected void onFoodEaten(final ItemStack itemStack, final World world, final EntityPlayer entityPlayer) {
        if (!world.isRemote) {
            entityPlayer.addPotionEffect(new PotionEffect(Potion.absorption.id, 1385 + 2279 - 1764 + 500, "".length()));
        }
        if (itemStack.getMetadata() > 0) {
            if (!world.isRemote) {
                entityPlayer.addPotionEffect(new PotionEffect(Potion.regeneration.id, 320 + 262 - 202 + 220, 0x89 ^ 0x8D));
                entityPlayer.addPotionEffect(new PotionEffect(Potion.resistance.id, 592 + 4131 - 4676 + 5953, "".length()));
                entityPlayer.addPotionEffect(new PotionEffect(Potion.fireResistance.id, 757 + 3735 - 1220 + 2728, "".length()));
                "".length();
                if (4 <= 2) {
                    throw null;
                }
            }
        }
        else {
            super.onFoodEaten(itemStack, world, entityPlayer);
        }
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
            if (1 < 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void getSubItems(final Item item, final CreativeTabs creativeTabs, final List<ItemStack> list) {
        list.add(new ItemStack(item, " ".length(), "".length()));
        list.add(new ItemStack(item, " ".length(), " ".length()));
    }
    
    @Override
    public boolean hasEffect(final ItemStack itemStack) {
        if (itemStack.getMetadata() > 0) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public EnumRarity getRarity(final ItemStack itemStack) {
        EnumRarity enumRarity;
        if (itemStack.getMetadata() == 0) {
            enumRarity = EnumRarity.RARE;
            "".length();
            if (3 < 3) {
                throw null;
            }
        }
        else {
            enumRarity = EnumRarity.EPIC;
        }
        return enumRarity;
    }
}

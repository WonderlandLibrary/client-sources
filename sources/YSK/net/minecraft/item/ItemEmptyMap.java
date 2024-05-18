package net.minecraft.item;

import net.minecraft.creativetab.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import net.minecraft.world.storage.*;
import net.minecraft.world.*;
import net.minecraft.stats.*;

public class ItemEmptyMap extends ItemMapBase
{
    private static final String[] I;
    
    protected ItemEmptyMap() {
        this.setCreativeTab(CreativeTabs.tabMisc);
    }
    
    @Override
    public ItemStack onItemRightClick(final ItemStack itemStack, final World world, final EntityPlayer entityPlayer) {
        final ItemStack itemStack2 = new ItemStack(Items.filled_map, " ".length(), world.getUniqueDataId(ItemEmptyMap.I["".length()]));
        final String string = ItemEmptyMap.I[" ".length()] + itemStack2.getMetadata();
        final MapData mapData = new MapData(string);
        world.setItemData(string, mapData);
        mapData.scale = (byte)"".length();
        mapData.calculateMapCenter(entityPlayer.posX, entityPlayer.posZ, mapData.scale);
        mapData.dimension = (byte)world.provider.getDimensionId();
        mapData.markDirty();
        itemStack.stackSize -= " ".length();
        if (itemStack.stackSize <= 0) {
            return itemStack2;
        }
        if (!entityPlayer.inventory.addItemStackToInventory(itemStack2.copy())) {
            entityPlayer.dropPlayerItemWithRandomChoice(itemStack2, "".length() != 0);
        }
        entityPlayer.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
        return itemStack;
    }
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I(".'\u001b", "CFkuQ");
        ItemEmptyMap.I[" ".length()] = I("\u000f%\u00129", "bDbfr");
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
            if (3 != 3) {
                throw null;
            }
        }
        return sb.toString();
    }
}

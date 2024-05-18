package net.minecraft.item.crafting;

import net.minecraft.inventory.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.world.storage.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;

public class RecipesMapExtending extends ShapedRecipes
{
    private static final String[] I;
    
    @Override
    public boolean matches(final InventoryCrafting inventoryCrafting, final World world) {
        if (!super.matches(inventoryCrafting, world)) {
            return "".length() != 0;
        }
        ItemStack itemStack = null;
        int length = "".length();
        "".length();
        if (0 == 2) {
            throw null;
        }
        while (length < inventoryCrafting.getSizeInventory() && itemStack == null) {
            final ItemStack stackInSlot = inventoryCrafting.getStackInSlot(length);
            if (stackInSlot != null && stackInSlot.getItem() == Items.filled_map) {
                itemStack = stackInSlot;
            }
            ++length;
        }
        if (itemStack == null) {
            return "".length() != 0;
        }
        final MapData mapData = Items.filled_map.getMapData(itemStack, world);
        int n;
        if (mapData == null) {
            n = "".length();
            "".length();
            if (3 >= 4) {
                throw null;
            }
        }
        else if (mapData.scale < (0x1F ^ 0x1B)) {
            n = " ".length();
            "".length();
            if (-1 >= 3) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n != 0;
    }
    
    public RecipesMapExtending() {
        final int length = "   ".length();
        final int length2 = "   ".length();
        final ItemStack[] array = new ItemStack[0xA2 ^ 0xAB];
        array["".length()] = new ItemStack(Items.paper);
        array[" ".length()] = new ItemStack(Items.paper);
        array["  ".length()] = new ItemStack(Items.paper);
        array["   ".length()] = new ItemStack(Items.paper);
        array[0x57 ^ 0x53] = new ItemStack(Items.filled_map, "".length(), 1677 + 18719 + 7868 + 4503);
        array[0x4D ^ 0x48] = new ItemStack(Items.paper);
        array[0x6 ^ 0x0] = new ItemStack(Items.paper);
        array[0x74 ^ 0x73] = new ItemStack(Items.paper);
        array[0x61 ^ 0x69] = new ItemStack(Items.paper);
        super(length, length2, array, new ItemStack(Items.map, "".length(), "".length()));
    }
    
    static {
        I();
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("50\u001f\b\u0011+\u000e\u001c4\u001948\u00010", "XQoWx");
    }
    
    @Override
    public ItemStack getCraftingResult(final InventoryCrafting inventoryCrafting) {
        ItemStack itemStack = null;
        int length = "".length();
        "".length();
        if (-1 != -1) {
            throw null;
        }
        while (length < inventoryCrafting.getSizeInventory() && itemStack == null) {
            final ItemStack stackInSlot = inventoryCrafting.getStackInSlot(length);
            if (stackInSlot != null && stackInSlot.getItem() == Items.filled_map) {
                itemStack = stackInSlot;
            }
            ++length;
        }
        final ItemStack copy = itemStack.copy();
        copy.stackSize = " ".length();
        if (copy.getTagCompound() == null) {
            copy.setTagCompound(new NBTTagCompound());
        }
        copy.getTagCompound().setBoolean(RecipesMapExtending.I["".length()], " ".length() != 0);
        return copy;
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
            if (2 == 3) {
                throw null;
            }
        }
        return sb.toString();
    }
}

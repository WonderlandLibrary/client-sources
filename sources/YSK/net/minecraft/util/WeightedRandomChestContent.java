package net.minecraft.util;

import com.google.common.collect.*;
import net.minecraft.item.*;
import java.util.*;
import net.minecraft.inventory.*;
import net.minecraft.tileentity.*;

public class WeightedRandomChestContent extends WeightedRandom.Item
{
    private int maxStackSize;
    private ItemStack theItemId;
    private int minStackSize;
    
    public static List<WeightedRandomChestContent> func_177629_a(final List<WeightedRandomChestContent> list, final WeightedRandomChestContent... array) {
        final ArrayList arrayList = Lists.newArrayList((Iterable)list);
        Collections.addAll(arrayList, array);
        return (List<WeightedRandomChestContent>)arrayList;
    }
    
    public WeightedRandomChestContent(final net.minecraft.item.Item item, final int n, final int minStackSize, final int maxStackSize, final int n2) {
        super(n2);
        this.theItemId = new ItemStack(item, " ".length(), n);
        this.minStackSize = minStackSize;
        this.maxStackSize = maxStackSize;
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
            if (1 >= 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public static void generateChestContents(final Random random, final List<WeightedRandomChestContent> list, final IInventory inventory, final int n) {
        int i = "".length();
        "".length();
        if (4 != 4) {
            throw null;
        }
        while (i < n) {
            final WeightedRandomChestContent weightedRandomChestContent = WeightedRandom.getRandomItem(random, list);
            final int stackSize = weightedRandomChestContent.minStackSize + random.nextInt(weightedRandomChestContent.maxStackSize - weightedRandomChestContent.minStackSize + " ".length());
            if (weightedRandomChestContent.theItemId.getMaxStackSize() >= stackSize) {
                final ItemStack copy = weightedRandomChestContent.theItemId.copy();
                copy.stackSize = stackSize;
                inventory.setInventorySlotContents(random.nextInt(inventory.getSizeInventory()), copy);
                "".length();
                if (3 == -1) {
                    throw null;
                }
            }
            else {
                int j = "".length();
                "".length();
                if (false) {
                    throw null;
                }
                while (j < stackSize) {
                    final ItemStack copy2 = weightedRandomChestContent.theItemId.copy();
                    copy2.stackSize = " ".length();
                    inventory.setInventorySlotContents(random.nextInt(inventory.getSizeInventory()), copy2);
                    ++j;
                }
            }
            ++i;
        }
    }
    
    public WeightedRandomChestContent(final ItemStack theItemId, final int minStackSize, final int maxStackSize, final int n) {
        super(n);
        this.theItemId = theItemId;
        this.minStackSize = minStackSize;
        this.maxStackSize = maxStackSize;
    }
    
    public static void generateDispenserContents(final Random random, final List<WeightedRandomChestContent> list, final TileEntityDispenser tileEntityDispenser, final int n) {
        int i = "".length();
        "".length();
        if (0 == 4) {
            throw null;
        }
        while (i < n) {
            final WeightedRandomChestContent weightedRandomChestContent = WeightedRandom.getRandomItem(random, list);
            final int stackSize = weightedRandomChestContent.minStackSize + random.nextInt(weightedRandomChestContent.maxStackSize - weightedRandomChestContent.minStackSize + " ".length());
            if (weightedRandomChestContent.theItemId.getMaxStackSize() >= stackSize) {
                final ItemStack copy = weightedRandomChestContent.theItemId.copy();
                copy.stackSize = stackSize;
                tileEntityDispenser.setInventorySlotContents(random.nextInt(tileEntityDispenser.getSizeInventory()), copy);
                "".length();
                if (1 >= 3) {
                    throw null;
                }
            }
            else {
                int j = "".length();
                "".length();
                if (-1 == 4) {
                    throw null;
                }
                while (j < stackSize) {
                    final ItemStack copy2 = weightedRandomChestContent.theItemId.copy();
                    copy2.stackSize = " ".length();
                    tileEntityDispenser.setInventorySlotContents(random.nextInt(tileEntityDispenser.getSizeInventory()), copy2);
                    ++j;
                }
            }
            ++i;
        }
    }
}

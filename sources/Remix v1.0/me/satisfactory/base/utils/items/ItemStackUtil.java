package me.satisfactory.base.utils.items;

import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.nbt.*;
import java.util.*;

public class ItemStackUtil
{
    public static final ItemStack empty;
    
    public static void addEmpty(final List<ItemStack> stacks, final int num) {
        for (int i = 0; i < num; ++i) {
            stacks.add(ItemStackUtil.empty);
        }
    }
    
    public static void fillEmpty(final List<ItemStack> stacks) {
        addEmpty(stacks, 9 - stacks.size() % 9);
    }
    
    public static void addEmpty(final List<ItemStack> stacks) {
        stacks.add(ItemStackUtil.empty);
    }
    
    public static ItemStack stringtostack(String Sargs) {
        try {
            Sargs = Sargs.replace('&', '§');
            Item item = new Item();
            String[] args = null;
            int i = 1;
            int j = 0;
            args = Sargs.split(" ");
            final ResourceLocation resourcelocation = new ResourceLocation(args[0]);
            item = (Item)Item.itemRegistry.getObject(resourcelocation);
            if (args.length >= 2 && args[1].matches("\\d+")) {
                i = Integer.parseInt(args[1]);
            }
            if (args.length >= 3 && args[2].matches("\\d+")) {
                j = Integer.parseInt(args[2]);
            }
            final ItemStack itemstack = new ItemStack(item, i, j);
            if (args.length >= 4) {
                String NBT = "";
                for (int nbtcount = 3; nbtcount < args.length; ++nbtcount) {
                    NBT = String.valueOf(NBT) + " " + args[nbtcount];
                }
                itemstack.setTagCompound(JsonToNBT.getTagFromJson(NBT));
            }
            return itemstack;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return new ItemStack(Blocks.barrier);
        }
    }
    
    public static void removeSuspiciousTags(final ItemStack item, final boolean force, final boolean display, final boolean hideFlags) {
        final NBTTagCompound nBTTagCompound;
        final NBTTagCompound tag = nBTTagCompound = (item.hasTagCompound() ? item.getTagCompound() : new NBTTagCompound());
        if (force || !tag.hasKey("Exploit")) {
            tag.setByte("Exploit", (byte)((display ? 1 : 0) + (hideFlags ? 2 : 0)));
        }
        item.setTagCompound(tag);
    }
    
    public static void removeSuspiciousTags(final List<ItemStack> itemList, final boolean display, final boolean hideFlags) {
        for (final ItemStack item : itemList) {
            removeSuspiciousTags(item, false, display, hideFlags);
        }
    }
    
    public static void removeSuspiciousTags(final List<ItemStack> itemList) {
        removeSuspiciousTags(itemList, true, true);
    }
    
    public static void modify(final ItemStack stack) {
        if (stack != null && stack.hasTagCompound() && stack.getTagCompound().hasKey("Exploit")) {
            final byte state = stack.getTagCompound().getByte("Exploit");
            stack.getTagCompound().removeTag("Exploit");
            if (state % 2 == 1 && stack.getTagCompound().hasKey("display", 10)) {
                stack.getTagCompound().removeTag("display");
            }
            if (state % 4 == 1) {
                stack.getTagCompound().setByte("HideFlags", (byte)63);
            }
        }
    }
    
    static {
        empty = new ItemStack(Blocks.air);
    }
}

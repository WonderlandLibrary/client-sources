// 
// Decompiled by Procyon v0.5.30
// 

package net.andrewsnetwork.icarus.command.commands;

import net.minecraft.nbt.NBTBase;
import net.andrewsnetwork.icarus.utilities.EntityHelper;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.item.ItemStack;
import net.minecraft.command.NumberInvalidException;
import net.andrewsnetwork.icarus.utilities.Logger;
import net.minecraft.item.Item;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import net.andrewsnetwork.icarus.command.Command;

public class Give extends Command
{
    public Give() {
        super("give", "<item id>");
    }
    
    @Override
    public void run(final String message) {
        OutputStreamWriter request = new OutputStreamWriter(System.out);
        Label_0033: {
            try {
                request.flush();
            }
            catch (IOException ex) {
                break Label_0033;
            }
            finally {
                request = null;
            }
            request = null;
        }
        if (message.split(" ").length > 1) {
            Item item = Item.getItemById(0);
            try {
                item = getItemByText(message.split(" ")[1]);
            }
            catch (Exception exception) {
                Logger.writeChat("Could not find item via item name, or ID.");
                return;
            }
            int amount = 1;
            int damage = 0;
            if (message.split(" ").length > 2) {
                final Integer newInt = Integer.parseInt(message.split(" ")[2]);
                if (newInt <= 0) {
                    Logger.writeChat("The stack size you provided is too small. Using 1.");
                }
                else if (newInt > 64) {
                    amount = 64;
                    Logger.writeChat("The stack size you provided is too big. Using 64.");
                }
                else {
                    try {
                        amount = parseIntBounded(message.split(" ")[2], 1, 64);
                    }
                    catch (NumberInvalidException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (message.split(" ").length > 3) {
                try {
                    damage = parseInt(message.split(" ")[3]);
                }
                catch (NumberInvalidException e2) {
                    e2.printStackTrace();
                }
            }
            final ItemStack itemStack = new ItemStack(item, amount, damage);
            if (message.split(" ").length > 4) {
                final String jsonData = message.substring(message.split(" ")[0].length() + message.split(" ")[1].length() + message.split(" ")[2].length() + message.split(" ")[3].length() + 4);
                try {
                    final NBTBase base = JsonToNBT.func_180713_a(jsonData);
                    if (!(base instanceof NBTTagCompound)) {
                        Logger.writeChat("Invalid NBT/JSON data given.");
                        return;
                    }
                    itemStack.setTagCompound((NBTTagCompound)base);
                }
                catch (NBTException exception2) {
                    Logger.writeChat("Invalid NBT/JSON data given.");
                    return;
                }
            }
            Give.mc.thePlayer.inventory.addItemStackToInventory(itemStack);
            EntityHelper.updateInventory();
            Logger.writeChat("Item \"" + itemStack.getDisplayName() + "\" has been given to your inventory.");
        }
        else {
            Logger.writeChat("Please provide a mode, or id to give.");
        }
    }
    
    private static Item getItemByText(final String itemName) throws NumberInvalidException {
        Item item = (Item)Item.itemRegistry.getObject(itemName);
        if (item == null) {
            try {
                item = Item.getItemById(Integer.parseInt(itemName));
            }
            catch (NumberFormatException ex) {}
        }
        if (item == null) {
            throw new NumberInvalidException("commands.give.notFound", new Object[] { itemName });
        }
        return item;
    }
    
    private static int parseIntBounded(final String integer, final int min, final int max) throws NumberInvalidException {
        final int parsedInt = parseInt(integer);
        if (parsedInt < min) {
            throw new NumberInvalidException("commands.generic.num.tooSmall", new Object[] { parsedInt, min });
        }
        if (parsedInt > max) {
            throw new NumberInvalidException("commands.generic.num.tooBig", new Object[] { parsedInt, max });
        }
        return parsedInt;
    }
    
    private static int parseInt(final String integer) throws NumberInvalidException {
        try {
            return Integer.parseInt(integer);
        }
        catch (NumberFormatException exception) {
            throw new NumberInvalidException("commands.generic.num.invalid", new Object[] { integer });
        }
    }
}

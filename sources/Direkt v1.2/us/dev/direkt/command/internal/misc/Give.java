package us.dev.direkt.command.internal.misc;

import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.util.SoundCategory;
import us.dev.direkt.Direkt;
import us.dev.direkt.Wrapper;
import us.dev.direkt.command.Command;
import us.dev.direkt.command.handler.annotations.Executes;
import us.dev.direkt.command.handler.annotations.numeric.IntClamp;

import java.util.Optional;

/**
 * @author Foundry
 */
public class Give extends Command {
    public Give() {
        super(Direkt.getInstance().getCommandManager(), "give", "g", "i");
    }

    @Executes
    public String run(String itemNameOrID, @IntClamp(min = 1, max = 64) Optional<Integer> amount, Optional<Integer> itemMetaData, Optional<String> tagData) {
        Item itemLookup = Item.getByNameOrId(itemNameOrID);
        if (itemLookup == null) {
            return String.format("'%s' is not a valid item name or id", itemNameOrID);
        } else {
            try {
                int clampedAmount = amount.orElse(1);
                int data = itemMetaData.orElse(0);
                ItemStack stack = new ItemStack(itemLookup, clampedAmount, data);

                if (tagData.isPresent()) {
                    stack.setTagCompound(JsonToNBT.getTagFromJson(tagData.get()));
                }

                boolean flag = Wrapper.getPlayer().inventory.addItemStackToInventory(stack);

                if (flag) {
                    Wrapper.getWorld().playSound(Wrapper.getPlayer(), Wrapper.getPlayer().posX, Wrapper.getPlayer().posY, Wrapper.getPlayer().posZ, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 0.2F, ((Wrapper.getPlayer().getRNG().nextFloat() - Wrapper.getPlayer().getRNG().nextFloat()) * 0.7F + 1.0F) * 2.0F);
                    Wrapper.updateInventory();
                    return String.format("Added %s of item %s to inventory", clampedAmount, stack.getDisplayName());
                } else {
                    return "Failed to add item to inventory. Are you in the right gamemode?";
                }
            } catch (NBTException e) {
                return "Exception parsing NBT data: " + e.getMessage();
            }
        }
    }

}

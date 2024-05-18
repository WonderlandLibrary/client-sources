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
public class Drop extends Command {
    public Drop() {
        super(Direkt.getInstance().getCommandManager(), "drop");
    }

    @Executes
    public String run(String itemNameOrID, @IntClamp(min = 1, max = 64) Optional<Integer> amount, Optional<Integer> itemMetadata, Optional<String> tagData) {
        Item itemLookup = Item.getByNameOrId(itemNameOrID);
        if (itemLookup == null) {
            return String.format("'%s' is not a valid item name or id", itemNameOrID);
        } else {
            try {
                int clampedAmount = amount.orElse(1);
                int data = itemMetadata.orElse(0);
                ItemStack stack = new ItemStack(itemLookup, clampedAmount, data);

                if (tagData.isPresent()) {
                    stack.setTagCompound(JsonToNBT.getTagFromJson(tagData.get()));
                }

                Wrapper.getPlayerController().sendPacketDropItem(stack);

                Wrapper.getWorld().playSound(Wrapper.getPlayer(), Wrapper.getPlayer().posX, Wrapper.getPlayer().posY, Wrapper.getPlayer().posZ, SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (Wrapper.getPlayer().getRNG().nextFloat() * 0.4F + 0.8F));
                return String.format("Dropped %s of item %s", clampedAmount, stack.getDisplayName());
            } catch (NBTException e) {
                return "Exception parsing NBT data: " + e.getMessage();
            }
        }
    }
}

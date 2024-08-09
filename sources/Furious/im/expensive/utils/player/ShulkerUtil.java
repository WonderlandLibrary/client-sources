package im.expensive.utils.player;

import im.expensive.ui.autobuy.AutoBuy;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;

import java.util.ArrayList;
import java.util.List;

public class ShulkerUtil {

    public static List<ItemStack> getItemInShulker(ItemStack s, AutoBuy autoBuy) {

        CompoundNBT compoundnbt = s.getChildTag("BlockEntityTag");

        if (compoundnbt != null) {
            if (compoundnbt.contains("Items", 9)) {
                NonNullList<ItemStack> nonnulllist = NonNullList.withSize(27, ItemStack.EMPTY);
                ItemStackHelper.loadAllItems(compoundnbt, nonnulllist);
                if (autoBuy.isDon())
                    return nonnulllist.stream().filter(item -> item.getItem() != Items.AIR).filter(item -> item.getTag() != null && item.getTag().contains("don-item")).toList();
                else {
                    return nonnulllist.stream().filter(item -> item.getItem() != Items.AIR).toList();

                }
            }
        }

        return new ArrayList<>();
    }

}

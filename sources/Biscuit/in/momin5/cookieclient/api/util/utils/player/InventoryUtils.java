package in.momin5.cookieclient.api.util.utils.player;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAir;
import net.minecraft.item.ItemStack;

public class InventoryUtils {

    static Minecraft mc = Minecraft.getMinecraft();

    public static int amountInHotbar(Item item) {
        int quantity = 0;

        for(int i = 44; i > 35; i--) {
            ItemStack stackInSlot = mc.player.inventoryContainer.getSlot(i).getStack();
            if(stackInSlot.getItem() == item) quantity += stackInSlot.getCount();
        }
        if(mc.player.getHeldItemOffhand().getItem() == item) quantity += mc.player.getHeldItemOffhand().getCount();

        return quantity;
    }

    public static int amountBlockInHotbar(Block block) {return amountInHotbar(new ItemStack(block).getItem());}

    public static int findItemInHotbar(Item item) {
        int index = -1;
        for(int i = 0; i < 9; i++) {
            if(mc.player.inventory.getStackInSlot(i).getItem() == item) {
                index = i;
                break;
            }
        }
        return index;
    }

    public static int findBlockInHotbar(Block block) {
        return findItemInHotbar(new ItemStack(block).getItem());
    }

    public static boolean isNull(ItemStack stack) {
        return stack == null || stack.getItem() instanceof ItemAir;
    }

    
}

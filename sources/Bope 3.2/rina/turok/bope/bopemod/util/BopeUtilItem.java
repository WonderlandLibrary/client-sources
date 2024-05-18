package rina.turok.bope.bopemod.util;

import net.minecraft.client.Minecraft;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.Item;

public class BopeUtilItem {
   public static final Minecraft mc = Minecraft.getMinecraft();

   public static int get_item_slot(Item item_requested) {
      for(int i = 0; i < 36; ++i) {
         Item items = mc.player.inventory.getStackInSlot(i).getItem();
         if (items == item_requested) {
            if (i < 9) {
               i += 36;
            }

            return i;
         }
      }

      return -1;
   }

   public static int get_hotbar_item_slot(Item item_requested) {
      for(int i = 0; i < 9; ++i) {
         Item items = mc.player.inventory.getStackInSlot(i).getItem();
         if (items == item_requested) {
            return i;
         }
      }

      return -1;
   }

   public static void set_offhand_item(int slot) {
      mc.playerController.windowClick(mc.player.inventoryContainer.windowId, slot, 0, ClickType.PICKUP, mc.player);
      mc.playerController.windowClick(mc.player.inventoryContainer.windowId, 45, 0, ClickType.PICKUP, mc.player);
      mc.playerController.windowClick(mc.player.inventoryContainer.windowId, slot, 0, ClickType.PICKUP, mc.player);
      mc.playerController.updateController();
   }
}

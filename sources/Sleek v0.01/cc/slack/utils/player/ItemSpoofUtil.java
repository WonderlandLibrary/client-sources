package cc.slack.utils.player;

import cc.slack.utils.client.mc;

public class ItemSpoofUtil extends mc {
   public static boolean isEnabled = false;
   public static int renderSlot = 0;
   public static int realSlot = 0;

   public static void startSpoofing(int slot) {
      if (isEnabled) {
         realSlot = slot;
         mc.getPlayer().inventory.currentItem = realSlot;
      } else {
         renderSlot = mc.getPlayer().inventory.currentItem;
         realSlot = slot;
         mc.getPlayer().inventory.currentItem = realSlot;
         isEnabled = true;
      }
   }

   public static void stopSpoofing() {
      realSlot = renderSlot;
      isEnabled = false;
      mc.getPlayer().inventory.currentItem = renderSlot;
   }
}

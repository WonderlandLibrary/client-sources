package exhibition.management.command.impl;

import exhibition.management.command.Command;
import exhibition.util.misc.ChatUtil;
import net.minecraft.client.Minecraft;

public class Swap extends Command {
   protected final Minecraft mc = Minecraft.getMinecraft();

   public Swap(String[] names, String description) {
      super(names, description);
   }

   public void fire(String[] args) {
      if (args == null) {
         this.printUsage();
      } else {
         if (args.length > 0) {
            int swapSlot;
            if (args[0].equalsIgnoreCase("m") && args.length > 1) {
               if (Integer.valueOf(args[1]).intValue() > 0 && Integer.valueOf(args[1]).intValue() < 10) {
                  swapSlot = Integer.valueOf(args[1]).intValue() - 1;

                  for(int i = 0; i <= swapSlot; ++i) {
                     this.swap(i + 27, i);
                  }

                  return;
               }
            } else {
               if (args[0].toString().contains("m") && args.length == 1) {
                  ChatUtil.printChat("§4[§cE§4]§8 That is not a valid hotbar number. (Slot 1 to set arg)");
                  return;
               }

               if (!args[0].toString().contains("m") && Integer.valueOf(args[0]).intValue() > 0 && Integer.valueOf(args[0]).intValue() < 10) {
                  swapSlot = Integer.valueOf(args[0]).intValue() - 1;
                  this.swap(swapSlot + 27, swapSlot);
                  return;
               }
            }
         }

         this.printUsage();
      }
   }

   public String getUsage() {
      return "swap <m [Multi] | hotbarslot> [Multi Only] <hotbars>";
   }

   protected void swap(int slot, int hotbarNum) {
      this.mc.playerController.windowClick(this.mc.thePlayer.inventoryContainer.windowId, slot, hotbarNum, 2, this.mc.thePlayer);
   }
}

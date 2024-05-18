package rina.turok.bope.bopemod.hacks.combat;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import rina.turok.bope.Bope;
import rina.turok.bope.bopemod.BopeMessage;
import rina.turok.bope.bopemod.BopeModule;
import rina.turok.bope.bopemod.guiscreen.settings.BopeSetting;
import rina.turok.bope.bopemod.hacks.BopeCategory;
import rina.turok.bope.bopemod.util.BopeUtilItem;

public class BopeAutoTotem extends BopeModule {
   BopeSetting absolute = this.create("Absolutedisabled", "AutoTotemAbsolute", true);
   BopeSetting slider_health = this.create("Health", "AutoTotemHealth", 36.0D, 1.0D, 36.0D);
   int totem_count;
   int last_slot;

   public BopeAutoTotem() {
      super(BopeCategory.BOPE_COMBAT);
      this.name = "Auto Totem";
      this.tag = "AutoTotem";
      this.description = "Automatically replace in offhand totem when used.";
      this.release("B.O.P.E - Module - B.O.P.E");
   }

   public void enable() {
      this.last_slot = -1;
      if (this.absolute.get_value(true)) {
         boolean crystal = false;
         boolean gapple = false;
         boolean cancel;
         if (Bope.module_is_active("AutoOffhandCrystal")) {
            cancel = false;
            crystal = true;
            if (Bope.get_setting("AutoOffhandCrystal", "AutoOffhandCrystalEnableAutoTotem").get_value(true)) {
               Bope.get_setting("AutoOffhandCrystal", "AutoOffhandCrystalEnableAutoTotem").set_value(false);
               cancel = true;
            }

            Bope.get_module("AutoOffhandCrystal").set_disable();
            if (cancel) {
               Bope.get_setting("AutoOffhandCrystal", "AutoOffhandCrystalEnableAutoTotem").set_value(true);
            }
         }

         if (Bope.module_is_active("AutoGapple")) {
            cancel = false;
            gapple = true;
            if (Bope.get_setting("AutoGapple", "AutoGappleEnableAutoTotem").get_value(true)) {
               Bope.get_setting("AutoGapple", "AutoGappleEnableAutoTotem").set_value(false);
               cancel = true;
            }

            Bope.get_module("AutoGapple").set_disable();
            if (cancel) {
               Bope.get_setting("AutoGapple", "AutoGappleEnableAutoTotem").set_value(true);
            }
         }

         if (gapple || crystal) {
            StringBuilder message = new StringBuilder();
            if (gapple && crystal) {
               message.append(Bope.dg + "AutoGapple & AutoOffhandCrystal" + Bope.r + " has been " + Bope.re + "disabled");
            } else if (gapple) {
               message.append(Bope.dg + "AutoGapple" + Bope.r + " has been " + Bope.re + "disabled");
            } else if (crystal) {
               message.append(Bope.dg + "AutoOffhandCrystal" + Bope.r + " has been " + Bope.re + "disabled");
            }

            BopeMessage.send_client_message(message.toString());
         }
      }

   }

   public void update() {
      if (this.mc.player != null && this.mc.world != null && this.mc.player.getHealth() <= (float)this.slider_health.get_value(1.0D)) {
         this.totem_count = this.mc.player.inventory.mainInventory.stream().filter((item) -> {
            return item.getItem() == Items.TOTEM_OF_UNDYING;
         }).mapToInt(ItemStack::getCount).sum();
         if (this.mc.currentScreen instanceof GuiContainer) {
            return;
         }

         if (this.mc.player.getHeldItemOffhand().getItem() == Items.TOTEM_OF_UNDYING) {
            return;
         }

         int totem_slot = BopeUtilItem.get_item_slot(Items.TOTEM_OF_UNDYING);
         if (totem_slot == -1) {
            return;
         }

         BopeUtilItem.set_offhand_item(totem_slot);
      }

   }
}

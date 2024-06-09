package exhibition.module.impl.combat;

import exhibition.event.Event;
import exhibition.event.RegisterEvent;
import exhibition.event.impl.EventMotionUpdate;
import exhibition.module.Module;
import exhibition.module.data.ModuleData;
import exhibition.module.data.Setting;
import exhibition.util.Timer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C09PacketHeldItemChange;

public class AutoSword extends Module {
   private Timer timer = new Timer();
   private String SWAP = "SWAP";

   public AutoSword(ModuleData data) {
      super(data);
      this.settings.put(this.SWAP, new Setting(this.SWAP, false, "Swaps to sword when attacking."));
   }

   @RegisterEvent(
      events = {EventMotionUpdate.class}
   )
   public void onEvent(Event event) {
      EventMotionUpdate em = (EventMotionUpdate)event;
      if (em.isPre() && (mc.currentScreen instanceof GuiInventory || mc.currentScreen == null)) {
         int i;
         Item item;
         if (mc.thePlayer.getCurrentEquippedItem() != null && mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword) {
            if (this.timer.delay(100.0F)) {
               for(i = 0; i < 45; ++i) {
                  if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                     item = mc.thePlayer.inventoryContainer.getSlot(i).getStack().getItem();
                     if (item instanceof ItemSword) {
                        float itemDamage = this.getAttackDamage(mc.thePlayer.inventoryContainer.getSlot(i).getStack());
                        float currentDamage = this.getAttackDamage(mc.thePlayer.getCurrentEquippedItem());
                        if (itemDamage > currentDamage) {
                           this.swap(i, mc.thePlayer.inventory.currentItem);
                           this.timer.reset();
                           break;
                        }
                     }
                  }
               }
            }
         } else if (((Boolean)((Setting)this.settings.get(this.SWAP)).getValue()).booleanValue()) {
            for(i = 36; i < 45; ++i) {
               if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                  item = mc.thePlayer.inventoryContainer.getSlot(i).getStack().getItem();
                  if (item instanceof ItemSword && (Killaura.target != null || !Killaura.loaded.isEmpty())) {
                     mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem = i - 36));
                     mc.playerController.updateController();
                     break;
                  }
               }
            }
         }
      }

   }

   protected void swap(int slot, int hotbarNum) {
      mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, slot, hotbarNum, 2, mc.thePlayer);
   }

   private float getAttackDamage(ItemStack stack) {
      return !(stack.getItem() instanceof ItemSword) ? 0.0F : (float)EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, stack) * 1.25F + ((ItemSword)stack.getItem()).getDamageGiven() + (float)EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, stack) * 0.01F;
   }
}

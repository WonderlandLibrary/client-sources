package exhibition.module.impl.combat;

import exhibition.event.Event;
import exhibition.event.RegisterEvent;
import exhibition.event.impl.EventTick;
import exhibition.module.Module;
import exhibition.module.data.ModuleData;
import exhibition.util.Timer;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.client.C16PacketClientStatus;

public class AutoArmor extends Module {
   private Timer timer = new Timer();
   private boolean isOpen;

   public AutoArmor(ModuleData data) {
      super(data);
   }

   @RegisterEvent(
      events = {EventTick.class}
   )
   public void onEvent(Event event) {
      if (mc.thePlayer != null && mc.currentScreen == null) {
         int slotID = -1;
         double maxProt = -1.0D;
         int switchArmor = -1;

         for(int i = 9; i < 45; ++i) {
            ItemStack stack = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
            if (stack != null && (this.canEquip(stack) || this.betterCheck(stack) && !this.canEquip(stack))) {
               if (this.betterCheck(stack) && switchArmor == -1) {
                  switchArmor = this.betterSwap(stack);
               }

               double protValue = this.getProtectionValue(stack);
               if (protValue >= maxProt) {
                  slotID = i;
                  maxProt = protValue;
               }
            }
         }

         if (slotID != -1) {
            if (this.timer.delay((float)(200.0D + Math.random() * 100.0D))) {
               if (!this.isOpen) {
                  mc.thePlayer.sendQueue.addToSendQueue(new C16PacketClientStatus(C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT));
                  this.isOpen = true;
               }

               if (switchArmor != -1) {
                  mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, 4 + switchArmor, 0, 0, mc.thePlayer);
                  mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, -999, 0, 0, mc.thePlayer);
                  this.timer.reset();
               } else {
                  mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, slotID, 0, 1, mc.thePlayer);
                  this.timer.reset();
               }
            }
         } else if (this.isOpen) {
            mc.thePlayer.sendQueue.addToSendQueue(new C0DPacketCloseWindow(0));
            this.isOpen = false;
         }
      } else {
         this.timer.reset();
      }

   }

   private boolean betterCheck(ItemStack stack) {
      try {
         if (stack.getItem() instanceof ItemArmor) {
            if (mc.thePlayer.getEquipmentInSlot(1) != null && stack.getUnlocalizedName().contains("boots")) {
               assert mc.thePlayer.getEquipmentInSlot(1).getItem() instanceof ItemArmor;

               if (this.getProtectionValue(stack) + (double)((ItemArmor)stack.getItem()).damageReduceAmount > this.getProtectionValue(mc.thePlayer.getEquipmentInSlot(1)) + (double)((ItemArmor)mc.thePlayer.getEquipmentInSlot(1).getItem()).damageReduceAmount) {
                  return true;
               }
            }

            if (mc.thePlayer.getEquipmentInSlot(2) != null && stack.getUnlocalizedName().contains("leggings")) {
               assert mc.thePlayer.getEquipmentInSlot(2).getItem() instanceof ItemArmor;

               if (this.getProtectionValue(stack) + (double)((ItemArmor)stack.getItem()).damageReduceAmount > this.getProtectionValue(mc.thePlayer.getEquipmentInSlot(2)) + (double)((ItemArmor)mc.thePlayer.getEquipmentInSlot(2).getItem()).damageReduceAmount) {
                  return true;
               }
            }

            if (mc.thePlayer.getEquipmentInSlot(3) != null && stack.getUnlocalizedName().contains("chestplate")) {
               assert mc.thePlayer.getEquipmentInSlot(3).getItem() instanceof ItemArmor;

               if (this.getProtectionValue(stack) + (double)((ItemArmor)stack.getItem()).damageReduceAmount > this.getProtectionValue(mc.thePlayer.getEquipmentInSlot(3)) + (double)((ItemArmor)mc.thePlayer.getEquipmentInSlot(3).getItem()).damageReduceAmount) {
                  return true;
               }
            }

            if (mc.thePlayer.getEquipmentInSlot(4) != null && stack.getUnlocalizedName().contains("helmet")) {
               assert mc.thePlayer.getEquipmentInSlot(4).getItem() instanceof ItemArmor;

               if (this.getProtectionValue(stack) + (double)((ItemArmor)stack.getItem()).damageReduceAmount > this.getProtectionValue(mc.thePlayer.getEquipmentInSlot(4)) + (double)((ItemArmor)mc.thePlayer.getEquipmentInSlot(4).getItem()).damageReduceAmount) {
                  return true;
               }
            }
         }

         return false;
      } catch (Exception var3) {
         return false;
      }
   }

   private int betterSwap(ItemStack stack) {
      if (stack.getItem() instanceof ItemArmor) {
         if (mc.thePlayer.getEquipmentInSlot(1) != null && stack.getUnlocalizedName().contains("boots")) {
            assert mc.thePlayer.getEquipmentInSlot(1).getItem() instanceof ItemArmor;

            if (this.getProtectionValue(stack) + (double)((ItemArmor)stack.getItem()).damageReduceAmount > this.getProtectionValue(mc.thePlayer.getEquipmentInSlot(1)) + (double)((ItemArmor)mc.thePlayer.getEquipmentInSlot(1).getItem()).damageReduceAmount) {
               return 4;
            }
         }

         if (mc.thePlayer.getEquipmentInSlot(2) != null && stack.getUnlocalizedName().contains("leggings")) {
            assert mc.thePlayer.getEquipmentInSlot(2).getItem() instanceof ItemArmor;

            if (this.getProtectionValue(stack) + (double)((ItemArmor)stack.getItem()).damageReduceAmount > this.getProtectionValue(mc.thePlayer.getEquipmentInSlot(2)) + (double)((ItemArmor)mc.thePlayer.getEquipmentInSlot(2).getItem()).damageReduceAmount) {
               return 3;
            }
         }

         if (mc.thePlayer.getEquipmentInSlot(3) != null && stack.getUnlocalizedName().contains("chestplate")) {
            assert mc.thePlayer.getEquipmentInSlot(1).getItem() instanceof ItemArmor;

            if (this.getProtectionValue(stack) + (double)((ItemArmor)stack.getItem()).damageReduceAmount > this.getProtectionValue(mc.thePlayer.getEquipmentInSlot(3)) + (double)((ItemArmor)mc.thePlayer.getEquipmentInSlot(3).getItem()).damageReduceAmount) {
               return 2;
            }
         }

         if (mc.thePlayer.getEquipmentInSlot(4) != null && stack.getUnlocalizedName().contains("helmet")) {
            assert mc.thePlayer.getEquipmentInSlot(1).getItem() instanceof ItemArmor;

            if (this.getProtectionValue(stack) + (double)((ItemArmor)stack.getItem()).damageReduceAmount > this.getProtectionValue(mc.thePlayer.getEquipmentInSlot(4)) + (double)((ItemArmor)mc.thePlayer.getEquipmentInSlot(4).getItem()).damageReduceAmount) {
               return 1;
            }
         }
      }

      return -1;
   }

   private boolean canEquip(ItemStack stack) {
      assert stack.getItem() instanceof ItemArmor;

      return mc.thePlayer.getEquipmentInSlot(1) == null && stack.getUnlocalizedName().contains("boots") || mc.thePlayer.getEquipmentInSlot(2) == null && stack.getUnlocalizedName().contains("leggings") || mc.thePlayer.getEquipmentInSlot(3) == null && stack.getUnlocalizedName().contains("chestplate") || mc.thePlayer.getEquipmentInSlot(4) == null && stack.getUnlocalizedName().contains("helmet");
   }

   private double getProtectionValue(ItemStack stack) {
      return stack.getItem() instanceof ItemArmor ? (double)((ItemArmor)stack.getItem()).damageReduceAmount + (double)((100 - ((ItemArmor)stack.getItem()).damageReduceAmount) * EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, stack)) * 0.0075D : 0.0D;
   }
}

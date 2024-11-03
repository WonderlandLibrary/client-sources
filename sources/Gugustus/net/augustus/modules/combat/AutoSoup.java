package net.augustus.modules.combat;

import java.awt.Color;
import java.util.concurrent.ThreadLocalRandom;
import net.augustus.events.EventTick;
import net.augustus.modules.Categorys;
import net.augustus.modules.Module;
import net.augustus.settings.BooleanValue;
import net.augustus.settings.DoubleValue;
import net.lenni0451.eventapi.reflection.EventTarget;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.init.Items;
import net.minecraft.item.ItemSoup;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C16PacketClientStatus;

public class AutoSoup extends Module {
   public DoubleValue health = new DoubleValue(1, "Health", this, 13.0, 1.0, 20.0, 0);
   public BooleanValue drop = new BooleanValue(2, "Drop", this, true);
   public BooleanValue fill = new BooleanValue(4, "Fill", this, true);
   public BooleanValue autoOpen = new BooleanValue(5, "AutoOpen", this, true);
   public BooleanValue autoClose = new BooleanValue(5, "AutoClose", this, true);
   private int slotOnLastTick = 0;
   private int previousSlot;
   private boolean clicked;
   private boolean dropped;

   public AutoSoup() {
      super("AutoSoup", new Color(19, 178, 110), Categorys.COMBAT);
   }

   @Override
   public void onEnable() {
   }

   @EventTarget
   public void onEventClick(EventTick eventClick) {
      if (mc.currentScreen == null) {
         if ((mc.thePlayer.getCurrentEquippedItem() != null && mc.thePlayer.getCurrentEquippedItem().getItem() == Items.bowl || this.clicked)
            && mc.thePlayer.inventory.currentItem == this.slotOnLastTick) {
            this.dropped = true;
            this.clicked = false;
            if (this.drop.getBoolean()) {
               mc.thePlayer.dropOneItem(true);
               return;
            }
         }

         if (this.dropped) {
            this.dropped = false;
            if (this.previousSlot != -1) {
               mc.thePlayer.inventory.currentItem = this.previousSlot;
            }

            this.previousSlot = -1;
         }

         int slot = this.getSoup();
         if (slot != -1) {
            if ((double)mc.thePlayer.getHealth() <= this.health.getValue() && !this.clicked) {
               if (mc.thePlayer.getCurrentEquippedItem() != null && mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSoup) {
                  mc.rightClickMouse();
                  this.clicked = true;
               } else {
                  if (this.previousSlot == -1) {
                     this.previousSlot = mc.thePlayer.inventory.currentItem;
                  }

                  mc.thePlayer.inventory.currentItem = slot;
               }
            }
         } else if (this.autoOpen.getBoolean() && this.fill.getBoolean()) {
            int wholeInv = this.getSoupInWholeInventory();
            if (wholeInv != -1) {
               this.openInventory();
            }
         }
      } else if (mc.currentScreen instanceof GuiInventory && this.fill.getBoolean()) {
         int emptySoup = this.getEmptySoup();
         if (emptySoup != -1) {
            if (Math.sin(ThreadLocalRandom.current().nextDouble(0.0, Math.PI * 2)) <= 0.5) {
               mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, emptySoup, 1, 4, mc.thePlayer);
            }
         } else {
            int slot = this.getSoupExceptHotbar();
            boolean full = true;
            int i = 0;

            while(true) {
               if (i < 9) {
                  ItemStack item = mc.thePlayer.inventory.mainInventory[i];
                  if (item != null) {
                     ++i;
                     continue;
                  }

                  full = false;
               }

               if (this.autoClose.getBoolean() && (slot == -1 || full)) {
                  mc.thePlayer.closeScreen();
                  mc.displayGuiScreen(null);
                  mc.setIngameFocus();
                  return;
               }

               mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, slot, 0, 1, mc.thePlayer);
               break;
            }
         }
      }

      this.slotOnLastTick = mc.thePlayer.inventory.currentItem;
   }

   public void openInventory() {
      mc.getNetHandler().addToSendQueue(new C16PacketClientStatus(C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT));
      mc.displayGuiScreen(new GuiInventory(mc.thePlayer));
   }

   public int getSoup() {
      for(int i = 0; i < 9; ++i) {
         ItemStack item = mc.thePlayer.inventory.mainInventory[i];
         if (item != null && item.getItem() instanceof ItemSoup) {
            return i;
         }
      }

      return -1;
   }

   public int getEmptySoup() {
      if (mc.currentScreen instanceof GuiInventory) {
         GuiInventory inventory = (GuiInventory)mc.currentScreen;

         for(int i = 36; i < 45; ++i) {
            ItemStack item = inventory.inventorySlots.getInventory().get(i);
            if (item != null && item.getItem() == Items.bowl) {
               return i;
            }
         }
      }

      return -1;
   }

   public int getSoupExceptHotbar() {
      for(int i = 9; i < mc.thePlayer.inventory.mainInventory.length; ++i) {
         ItemStack item = mc.thePlayer.inventory.mainInventory[i];
         if (item != null && item.getItem() instanceof ItemSoup) {
            return i;
         }
      }

      return -1;
   }

   public int getSoupInWholeInventory() {
      for(int i = 0; i < mc.thePlayer.inventory.mainInventory.length; ++i) {
         ItemStack item = mc.thePlayer.inventory.mainInventory[i];
         if (item != null && item.getItem() instanceof ItemSoup) {
            return i;
         }
      }

      return -1;
   }
}

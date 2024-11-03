package vestige.handler.client;

import net.minecraft.item.ItemStack;
import vestige.util.IMinecraft;

public class SlotSpoofHandler implements IMinecraft {
   private int spoofedSlot;
   private boolean spoofing;

   public void startSpoofing(int slot) {
      this.spoofing = true;
      this.spoofedSlot = slot;
   }

   public void stopSpoofing() {
      this.spoofing = false;
   }

   public int getSpoofedSlot() {
      return this.spoofing ? this.spoofedSlot : mc.thePlayer.inventory.currentItem;
   }

   public ItemStack getSpoofedStack() {
      return this.spoofing ? mc.thePlayer.inventory.getStackInSlot(this.spoofedSlot) : mc.thePlayer.inventory.getCurrentItem();
   }

   public boolean isSpoofing() {
      return this.spoofing;
   }
}

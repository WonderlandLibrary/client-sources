package me.hexxed.mercury.modules;

import me.hexxed.mercury.modulebase.Module;
import me.hexxed.mercury.modulebase.ModuleCategory;
import me.hexxed.mercury.modulebase.Values;
import me.hexxed.mercury.util.TimeHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;

public class AutoPot extends Module
{
  private int potionSlot;
  private TimeHelper time = new TimeHelper();
  
  public AutoPot() {
    super("AutoPot", 0, true, ModuleCategory.COMBAT);
  }
  
  public void onPreMotionUpdate()
  {
    potionSlot = -1;
    if (mc.thePlayer.getHealth() < 12.0F) {
      if (!time.isDelayComplete(125L)) {
        return;
      }
      int oldSlot = mc.thePlayer.inventory.currentItem;
      for (int slot = 44; slot >= 9; slot--) {
        ItemStack stack = mc.thePlayer.inventoryContainer.getSlot(slot).getStack();
        if (stack != null) {
          if ((slot >= 36) && (slot <= 44)) {
            if ((Item.getIdFromItem(stack.getItem()) == 373) && ((stack.getItemDamage() == 16421) || (stack.getItemDamage() == 16453))) {
              potionSlot = (slot - 36);
              getValuesprepitch = 95.0F;
              time.setLastMS();
            }
          }
          else if ((Item.getIdFromItem(stack.getItem()) == 373) && ((stack.getItemDamage() == 16421) || (stack.getItemDamage() == 16453))) {
            mc.playerController.windowClick(0, slot, 0, 1, mc.thePlayer);
            time.setLastMS();
            return;
          }
        }
      }
    }
  }
  
  public void onPostMotionUpdate() {
    if (potionSlot != -1) {
      int oldSlot = mc.thePlayer.inventory.currentItem;
      mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(potionSlot));
      mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(mc.thePlayer.inventoryContainer.getSlot(potionSlot + 36).getStack()));
      mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(oldSlot));
      potionSlot = -1;
    }
  }
}

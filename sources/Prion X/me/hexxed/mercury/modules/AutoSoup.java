package me.hexxed.mercury.modules;

import me.hexxed.mercury.modulebase.Module;
import me.hexxed.mercury.modulebase.ModuleCategory;
import me.hexxed.mercury.util.TimeHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C09PacketHeldItemChange;

public class AutoSoup extends Module
{
  private TimeHelper time = new TimeHelper();
  
  public AutoSoup() {
    super("AutoSoup", 0, true, ModuleCategory.COMBAT);
  }
  
  private long currentMS = 0L;
  private long lastSoup = -1L;
  private int oldslot = -1;
  
  public void onPostUpdate()
  {
    if (mc.thePlayer.getHealth() < 14.0F) {
      eatSoup();
    }
  }
  
  private void eatSoup() {
    currentMS = (System.nanoTime() / 1000000L);
    if (!hasDelayRun(125L)) {
      return;
    }
    int oldSlot = mc.thePlayer.inventory.currentItem;
    for (int slot = 44; slot >= 9; slot--) {
      ItemStack stack = mc.thePlayer.inventoryContainer.getSlot(slot).getStack();
      
      if (stack != null) {
        if ((slot >= 36) && (slot <= 44)) {
          if (Item.getIdFromItem(stack.getItem()) == 282)
          {
            mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(slot - 36));
            mc.thePlayer.sendQueue.addToSendQueue(new net.minecraft.network.play.client.C08PacketPlayerBlockPlacement(mc.thePlayer.inventory.getCurrentItem()));
            mc.thePlayer.sendQueue.addToSendQueue(new net.minecraft.network.play.client.C07PacketPlayerDigging(net.minecraft.network.play.client.C07PacketPlayerDigging.Action.DROP_ITEM, net.minecraft.util.BlockPos.ORIGIN, net.minecraft.util.EnumFacing.DOWN));
            
            mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(oldSlot));
            lastSoup = (System.nanoTime() / 1000000L);
          }
        }
        else if (Item.getIdFromItem(stack.getItem()) == 282) {
          mc.playerController.windowClick(0, slot, 0, 0, mc.thePlayer);
          mc.playerController.windowClick(0, 44, 0, 0, mc.thePlayer);
          lastSoup = (System.nanoTime() / 1000000L);
          return;
        }
      }
    }
  }
  
  private boolean hasDelayRun(long l) {
    return currentMS - lastSoup >= l;
  }
}

package mods.togglesprint.me.jannik.module.modules.player;

import mods.togglesprint.com.darkmagician6.eventapi.EventTarget;
import mods.togglesprint.me.jannik.events.EventUpdate;
import mods.togglesprint.me.jannik.module.Category;
import mods.togglesprint.me.jannik.module.Module;
import mods.togglesprint.me.jannik.value.Value;
import mods.togglesprint.me.jannik.value.values.Values;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C07PacketPlayerDigging.Action;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class AutoSoup
  extends Module
{
  public AutoSoup()
  {
    super("AutoSoup", Category.PLAYER);
  }
  
  @EventTarget
  private void onUpdate(EventUpdate event)
  {
    if (mc.thePlayer.getHealth() <= Values.autosoup_health.getFloatValue())
    {
      int current = mc.thePlayer.inventory.currentItem;
      for (int slot = 36; slot <= 44; slot++)
      {
        ItemStack item = mc.thePlayer.inventoryContainer.getSlot(slot).getStack();
        if ((item != null) && (Item.getIdFromItem(item.getItem()) == 282))
        {
          mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(slot - 36));
          mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(mc.thePlayer.inventory.getCurrentItem()));
          mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.DROP_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
          mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(current));
          return;
        }
      }
    }
  }
}

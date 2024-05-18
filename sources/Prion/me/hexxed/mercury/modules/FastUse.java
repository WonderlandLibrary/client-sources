package me.hexxed.mercury.modules;

import me.hexxed.mercury.modulebase.Module;
import me.hexxed.mercury.modulebase.ModuleCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C07PacketPlayerDigging.Action;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class FastUse extends Module
{
  public FastUse()
  {
    super("FastUse", 0, true, ModuleCategory.MISC);
  }
  






  public void onPreMotionUpdate()
  {
    try
    {
      if (isSword(mc.thePlayer.inventory.getCurrentItem().getItem())) {
        return;
      }
    } catch (Exception e) {
      return;
    }
    if (mc.thePlayer.getItemInUseDuration() > 15)
    {
      for (int i = 0; i < 20; i++)
      {
        getMinecraftthePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(mc.thePlayer.onGround));
      }
      
      if ((mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemBow))
      {
        if (!me.hexxed.mercury.modulebase.ModuleManager.isAntiCheatOn()) {
          shootBow();
          return;
        }
        mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(0, 0, 0), EnumFacing.UP));
        mc.thePlayer.stopUsingItem();
      }
      else {
        mc.thePlayer.stopUsingItem();
      }
    }
  }
  
  public void shootBow()
  {
    int item = mc.thePlayer.inventory.currentItem;
    mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, mc.thePlayer.inventory.getCurrentItem(), 0.0F, 0.0F, 0.0F));
    mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(item));
    for (int i = 0; i < 20; i++)
    {
      mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(false));
    }
    mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(0, 0, 0), EnumFacing.fromAngle(-1.0D)));
  }
  
  private boolean isSword(Item item)
  {
    return item instanceof net.minecraft.item.ItemSword;
  }
}

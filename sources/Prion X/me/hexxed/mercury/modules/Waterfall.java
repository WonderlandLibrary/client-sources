package me.hexxed.mercury.modules;

import me.hexxed.mercury.modulebase.Module;
import me.hexxed.mercury.modulebase.ModuleCategory;
import me.hexxed.mercury.util.Util;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer.C05PacketPlayerLook;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;

public class Waterfall extends Module
{
  public Waterfall()
  {
    super("Waterfall", 0, true, ModuleCategory.MOVEMENT);
  }
  
  int delay = 0;
  boolean delaytoggle = false;
  boolean lookdown = false;
  
  public void onTick()
  {
    if (mc.thePlayer.fallDistance >= 3.0F)
    {
      int grounddistance = (int)mc.thePlayer.posY - Util.getNextStandableBlockHeight();
      if (grounddistance < 4) {
        mc.thePlayer.rotationPitch = 90.0F;
        mc.thePlayer.rotationYaw = 90.0F;
        mc.thePlayer.rotationYawHead = 90.0F;
      }
      lookdown = true;
      switchToItem("Water Bucket");
      BlockPos bpos = new BlockPos(mc.thePlayer.getPosition().getX(), mc.thePlayer.getPosition().getY() - 3, mc.thePlayer.getPosition().getZ());
      Block blocks = mc.theWorld.getBlockState(bpos).getBlock();
      if ((blocks.getMaterial() != net.minecraft.block.material.Material.air) && 
        (hasItem("Water Bucket")))
      {
        useItem();
        delay += 1;
        if (delay >= 20)
        {
          switchToItem("Bucket");
          useItem();
          delay = 0;
        }
      }
    } else {
      lookdown = false;
    }
  }
  
  public void onPacketSend(Packet packet)
  {
    if ((lookdown) && 
      ((packet instanceof C03PacketPlayer.C05PacketPlayerLook))) {
      packet = new C03PacketPlayer.C05PacketPlayerLook(90.0F, 90.0F, false);
    }
  }
  

  public void useItem()
  {
    ItemStack item = mc.thePlayer.inventory.getCurrentItem();
    mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, item);
    mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(new BlockPos(mc.thePlayer.posX, Util.getNextStandableBlockHeight(), mc.thePlayer.posZ), -1, item, 0.0F, 0.0F, 0.0F));
  }
  
  public boolean hasItem(String blockTileName)
  {
    for (int i = 36; i <= 44; i++) {
      if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack())
      {
        String blockName = mc.thePlayer.inventoryContainer.getSlot(i).getStack().getDisplayName();
        if (blockName.equalsIgnoreCase(blockTileName)) {
          return true;
        }
      }
    }
    return false;
  }
  
  public void switchToItem(String itemName)
  {
    for (int i = 36; i <= 44; i++) {
      if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack())
      {
        String blockName = mc.thePlayer.inventoryContainer.getSlot(i).getStack().getDisplayName();
        if (blockName.equalsIgnoreCase(itemName))
        {
          mc.thePlayer.inventory.currentItem = (i - 36);
          break;
        }
      }
    }
  }
}

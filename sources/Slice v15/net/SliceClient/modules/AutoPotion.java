package net.SliceClient.modules;

import java.util.List;
import net.SliceClient.module.Category;
import net.SliceClient.module.Module;
import net.SliceClient.ui.InventoryManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;

public class AutoPotion extends Module
{
  transient long lastHealTime;
  
  public AutoPotion()
  {
    super("AutoPotion", Category.MISC, 16376546);
  }
  

  public void onUpdate()
  {
    if (!getState()) {
      if ((Minecraft.thePlayer == null) || (Minecraft.theWorld == null))
        return;
      return;
    }
    
    EntityPlayer p = Minecraft.thePlayer;
    
    int potionSlot = -1;
    int currentSlot = thePlayerinventory.currentItem;
    
    if (p.getHealth() < p.getMaxHealth() / 2.0F + 2.0F) {
      for (int i = 0; i < 9; i++) {
        if ((inventory.getStackInSlot(i) != null) && (inventory.getStackInSlot(i).getItem() != null) && ((inventory.getStackInSlot(i).getItem() instanceof ItemPotion)) && (
          (inventory.getStackInSlot(i).getItemDamage() == 16421) || (inventory.getStackInSlot(i).getItemDamage() == 16453))) {
          potionSlot = i;
        }
      }
      
      if ((potionSlot > -1) && (lastHealTime + 500L < System.currentTimeMillis()) && (onGround)) {
        thePlayersendQueue.netManager.sendPacket(new net.minecraft.network.play.client.C03PacketPlayer.C05PacketPlayerLook(0.0F, 90.0F, true));
        thePlayerinventory.currentItem = potionSlot;
        Minecraft.playerController.sendUseItem(Minecraft.thePlayer, Minecraft.theWorld, thePlayerinventory.getCurrentItem());
        thePlayerinventory.currentItem = currentSlot;
        lastHealTime = System.currentTimeMillis();
      }
    }
    
    if (Minecraft.currentScreen == null) {
      for (int i = 36; i <= 44; i++) {
        Slot slot = (Slot)thePlayerinventoryContainer.inventorySlots.get(i);
        
        if (slot.getStack() == null) {
          for (int slotIndex = 9; slotIndex <= 35; slotIndex++) {
            Slot slotPotion = (Slot)thePlayerinventoryContainer.inventorySlots.get(slotIndex);
            
            if ((slotPotion.getStack() != null) && (slotPotion.getStack().getItem() != null) && ((slotPotion.getStack().getItem() instanceof ItemPotion)) && (
              (slotPotion.getStack().getItemDamage() == 16421) || (slotPotion.getStack().getItemDamage() == 16453))) {
              InventoryManager.switchItemsInSlots(i, slotIndex);
              break;
            }
          }
        }
      }
    }
  }
}

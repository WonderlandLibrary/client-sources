package net.SliceClient.modules;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;

public class ChestSteal extends net.SliceClient.module.Module
{
  public ChestSteal()
  {
    super("ChestSteal", net.SliceClient.module.Category.MISC, 16376546);
  }
  
  private net.SliceClient.Utils.TimeHelper time = new net.SliceClient.Utils.TimeHelper();
  int slot = 0;
  boolean skip = false;
  
  public boolean isContainerEmpty(Container container) {
    boolean temp = true;
    int i = 0;
    for (int slotAmount = inventorySlots.size() == 90 ? 54 : 27; i < slotAmount; i++) {
      if (container.getSlot(i).getHasStack()) {
        temp = false;
      }
    }
    return temp;
  }
  
  public void onUpdate()
  {
    if (!getState())
      return;
    if ((Minecraft.thePlayer == null) || (Minecraft.theWorld == null))
      return;
    if ((Minecraft.currentScreen instanceof net.minecraft.client.gui.inventory.GuiInventory)) {
      Minecraft.playerController.updateController();
    }
    if (Minecraft.theWorld != null) {
      if ((Minecraft.currentScreen instanceof GuiChest)) {
        GuiChest chest = (GuiChest)Minecraft.currentScreen;
        if (isContainerEmpty(thePlayeropenContainer)) {
          Minecraft.thePlayer.closeScreen();
        }
        if ((lowerChestInventory.getName().contains("Where")) || (lowerChestInventory.getName().contains("Lobby Teleporter")) || (lowerChestInventory.getName().contains("Shop")) || (lowerChestInventory.getName().contains("Spiel")) || (lowerChestInventory.getName().contains("Teleporter")) || (lowerChestInventory.getName().contains("Spiel auswählen"))) {
          return;
        }
        
        for (int i = slot; slot < lowerChestInventory.getSizeInventory(); slot += 1) {
          if (inventorySlots.getSlot(slot).getHasStack()) {
            if (!time.isDelayComplete(40L)) {
              break;
            }
            Minecraft.playerController.windowClick(inventorySlots.windowId, slot, 0, 1, Minecraft.thePlayer);
            time.setLastMS();
            if (isContainerEmpty(thePlayeropenContainer)) {
              Minecraft.thePlayer.closeScreen();
            }
          }
        }
      }
      else {
        slot = 0;
      }
    }
  }
}

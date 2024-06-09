package me.hexxed.mercury.modules;

import me.hexxed.mercury.modulebase.Module;
import me.hexxed.mercury.modulebase.ModuleCategory;
import me.hexxed.mercury.modulebase.ModuleManager;
import me.hexxed.mercury.util.TimeHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;

public class ChestSteal extends Module
{
  public ChestSteal()
  {
    super("ChestSteal", 0, true, ModuleCategory.PLAYER);
  }
  
  private TimeHelper time = new TimeHelper();
  
  int slot = 0;
  
  boolean skip = false;
  
  public void onPreMotionUpdate()
  {
    if (mc.theWorld == null) return;
    if ((mc.currentScreen instanceof GuiChest)) {
      GuiChest chest = (GuiChest)mc.currentScreen;
      for (int i = slot; slot < lowerChestInventory.getSizeInventory(); 
          



          slot = (slot + 1)) {
        if (inventorySlots.getSlot(slot).getHasStack()) {
          if (!time.isDelayComplete(ModuleManager.isAntiCheatOn() ? 125L : 0L)) break;
          mc.playerController.windowClick(inventorySlots.windowId, slot, 0, 1, mc.thePlayer);
          time.setLastMS();
        }
        
      }
    }
    else
    {
      slot = 0;
    }
  }
}

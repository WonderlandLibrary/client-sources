package me.hexxed.mercury.modules;

import me.hexxed.mercury.modulebase.Module;
import me.hexxed.mercury.modulebase.ModuleCategory;
import me.hexxed.mercury.util.TimeHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class AutoArmor extends Module
{
  public AutoArmor()
  {
    super("AutoArmor", 0, true, ModuleCategory.COMBAT);
  }
  
  private TimeHelper time = new TimeHelper();
  private final int[] boots = { 313, 309, 317, 305, 301 };
  private final int[] chestplate = { 311, 307, 315, 303, 299 };
  private final int[] helmet = { 310, 306, 314, 302, 298 };
  private final int[] leggings = { 312, 308, 316, 304, 300 };
  
  public void onPreMotionUpdate()
  {
    if (!time.isDelayComplete(65L)) {
      return;
    }
    if ((mc.thePlayer.openContainer != null) && 
      (mc.thePlayer.openContainer.windowId != 0)) {
      return;
    }
    
    int item = -1;
    if (mc.thePlayer.inventory.armorInventory[0] == null) {
      for (int id : boots) {
        if (findItem(id) != -1) {
          item = findItem(id);
          break;
        }
      }
    }
    if (armourIsBetter(0, boots)) {
      item = 8;
    }
    if (mc.thePlayer.inventory.armorInventory[3] == null) {
      for (int id : helmet) {
        if (findItem(id) != -1) {
          item = findItem(id);
          break;
        }
      }
    }
    if (armourIsBetter(3, helmet)) {
      item = 5;
    }
    if (mc.thePlayer.inventory.armorInventory[1] == null) {
      for (int id : leggings) {
        if (findItem(id) != -1) {
          item = findItem(id);
          break;
        }
      }
    }
    if (armourIsBetter(1, leggings)) {
      item = 7;
    }
    if (mc.thePlayer.inventory.armorInventory[2] == null) {
      for (int id : chestplate) {
        if (findItem(id) != -1) {
          item = findItem(id);
          break;
        }
      }
    }
    if (armourIsBetter(2, chestplate)) {
      item = 6;
    }
    if (item != -1) {
      mc.playerController.windowClick(0, item, 0, 1, mc.thePlayer);
      time.setLastMS();
      return;
    }
  }
  
  public boolean armourIsBetter(int slot, int[] armourtype) { if (mc.thePlayer.inventory.armorInventory[slot] != null) {
      int currentIndex = 0;
      int finalCurrentIndex = -1;
      int invIndex = 0;
      int finalInvIndex = -1;
      for (int armour : armourtype) {
        if (Item.getIdFromItem(mc.thePlayer.inventory.armorInventory[slot].getItem()) == armour) {
          finalCurrentIndex = currentIndex;
          break;
        }
        currentIndex++;
      }
      for (int armour : armourtype) {
        if (findItem(armour) != -1) {
          finalInvIndex = invIndex;
          break;
        }
        invIndex++;
      }
      if (finalInvIndex > -1) {
        return finalInvIndex < finalCurrentIndex;
      }
    }
    return false;
  }
  
  private int findItem(int id) {
    for (int index = 9; index < 45; index++) {
      ItemStack item = mc.thePlayer.inventoryContainer.getSlot(
        index).getStack();
      if ((item != null) && (Item.getIdFromItem(item.getItem()) == id))
        return index;
    }
    return -1;
  }
}

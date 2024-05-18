package net.SliceClient.modules;

import net.SliceClient.Utils.TimeHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

public class AutoArmor extends net.SliceClient.module.Module
{
  public AutoArmor()
  {
    super("AutoArmor", net.SliceClient.module.Category.MISC, 16376546);
  }
  
  private TimeHelper time = new TimeHelper();
  private final int[] boots = { 313, 309, 317, 305, 301 };
  private final int[] chestplate = { 311, 307, 315, 303, 299 };
  private final int[] helmet = { 310, 306, 314, 302, 298 };
  private final int[] leggings = { 312, 308, 316, 304, 300 };
  
  public void onUpdate()
  {
    if (!getState())
      return;
    if ((Minecraft.thePlayer == null) || (Minecraft.theWorld == null)) {
      return;
    }
    

    Minecraft.getMinecraft(); if (thePlayeropenContainer != null) { Minecraft.getMinecraft(); if (thePlayeropenContainer.windowId != 0)
        return;
    }
    int item = -1;
    
    Minecraft.getMinecraft(); if (thePlayerinventory.armorInventory[0] == null) {
      int[] arrayOfInt;
      int j = (arrayOfInt = boots).length;
      for (int i = 0; i < j; i++) {
        int id = arrayOfInt[i];
        if (findItem(id) != -1) {
          item = findItem(id);
          break;
        }
      }
    }
    if (armourIsBetter(0, boots)) {
      item = 8;
    }
    Minecraft.getMinecraft(); if (thePlayerinventory.armorInventory[3] == null) {
      int[] arrayOfInt;
      int j = (arrayOfInt = helmet).length;
      for (int i = 0; i < j; i++) {
        int id = arrayOfInt[i];
        if (findItem(id) != -1) {
          item = findItem(id);
          break;
        }
      }
    }
    if (armourIsBetter(3, helmet)) {
      item = 5;
    }
    Minecraft.getMinecraft(); if (thePlayerinventory.armorInventory[1] == null) {
      int[] arrayOfInt;
      int j = (arrayOfInt = leggings).length;
      for (int i = 0; i < j; i++) {
        int id = arrayOfInt[i];
        if (findItem(id) != -1) {
          item = findItem(id);
          break;
        }
      }
    }
    if (armourIsBetter(1, leggings)) {
      item = 7;
    }
    Minecraft.getMinecraft(); if (thePlayerinventory.armorInventory[2] == null) {
      int[] arrayOfInt;
      int j = (arrayOfInt = chestplate).length;
      for (int i = 0; i < j; i++) {
        int id = arrayOfInt[i];
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
      Minecraft.getMinecraft();Minecraft.getMinecraft();Minecraft.playerController.windowClick(0, item, 0, 1, Minecraft.thePlayer);
      time.setLastMS();
      return;
    }
  }
  
  public boolean armourIsBetter(int slot, int[] armourtype)
  {
    Minecraft.getMinecraft(); if (thePlayerinventory.armorInventory[slot] != null) {
      int currentIndex = 0;
      int finalCurrentIndex = -1;
      int invIndex = 0;
      int finalInvIndex = -1;
      int[] arrayOfInt;
      int j = (arrayOfInt = armourtype).length;
      for (int i = 0; i < j; i++) {
        int armour = arrayOfInt[i];
        Minecraft.getMinecraft(); if (net.minecraft.item.Item.getIdFromItem(thePlayerinventory.armorInventory[slot].getItem()) == armour) {
          finalCurrentIndex = currentIndex;
          break;
        }
        currentIndex++;
      }
      
      j = (arrayOfInt = armourtype).length;
      for (int i = 0; i < j; i++) {
        int armour = arrayOfInt[i];
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
      Minecraft.getMinecraft();ItemStack item = thePlayerinventoryContainer.getSlot(index).getStack();
      if ((item != null) && (net.minecraft.item.Item.getIdFromItem(item.getItem()) == id)) {
        return index;
      }
    }
    return -1;
  }
}

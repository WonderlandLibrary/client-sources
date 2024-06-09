package lunadevs.luna.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C10PacketCreativeInventoryAction;

public class Inventory
{
  public static Minecraft mc;
  
  public static void updateInventory()
  {
    for (int index = 0; index < 44; index++) {
      try
      {
        int offset = index < 9 ? 36 : 0;
        
        Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C10PacketCreativeInventoryAction(index + offset, Minecraft.getMinecraft().thePlayer.inventory.mainInventory[index]));
      }
      catch (Exception localException) {}
    }
  }
  
  public static void clickSlot(int slot, int mouseButton, boolean shiftClick)
  {
    Minecraft.getMinecraft().playerController.windowClick(
      Minecraft.getMinecraft().thePlayer.inventoryContainer.windowId, slot, mouseButton, shiftClick ? 1 : 0, 
      Minecraft.getMinecraft().thePlayer);
  }
  
  public static int findHotbarItem(int itemId, int mode)
  {
    if (mode == 0) {
      for (int slot = 36; slot <= 44; slot++)
      {
        ItemStack item = mc.thePlayer.inventoryContainer.getSlot(slot).getStack();
        if ((item != null) && 
          (Item.getIdFromItem(item.getItem()) == itemId)) {
          return slot;
        }
      }
    } else if (mode == 1) {
      for (int slot = 36; slot <= 44; slot++)
      {
        ItemStack item = mc.thePlayer.inventoryContainer.getSlot(slot).getStack();
        if ((item != null) && 
          (Item.getIdFromItem(item.getItem()) == itemId)) {
          return slot - 36;
        }
      }
    }
    return -1;
  }
  
  public static int findEmptyHotbarItem(int mode)
  {
    if (mode == 0) {
      for (int slot = 36; slot <= 44; slot++)
      {
        ItemStack item = mc.thePlayer.inventoryContainer.getSlot(slot).getStack();
        if (item == null) {
          return slot;
        }
      }
    } else if (mode == 1) {
      for (int slot = 36; slot <= 44; slot++)
      {
        ItemStack item = mc.thePlayer.inventoryContainer.getSlot(slot).getStack();
        if (item == null) {
          return slot;
        }
      }
    }
    return -1;
  }
  
  public static int findInventoryItem(int itemID)
  {
    for (int o = 9; o <= 35; o++) {
      if (Minecraft.getMinecraft().thePlayer.inventoryContainer.getSlot(o).getHasStack())
      {
        ItemStack item = Minecraft.getMinecraft().thePlayer.inventoryContainer.getSlot(o).getStack();
        if ((item != null) && 
          (Item.getIdFromItem(item.getItem()) == itemID)) {
          return o;
        }
      }
    }
    return -1;
  }
  
  public static int findAvailableSlotInventory(int mode, int... itemIds)
  {
    if (mode == 0) {
      for (int slot = 9; slot <= 35; slot++)
      {
        ItemStack item = mc.thePlayer.inventoryContainer.getSlot(slot).getStack();
        if (item == null) {
          return slot;
        }
        int[] arrayOfInt;
        int j = (arrayOfInt = itemIds).length;
        for (int i = 0; i < j; i++)
        {
          int id = arrayOfInt[i];
          if (Item.getIdFromItem(item.getItem()) == id) {
            return slot;
          }
        }
      }
    } else if (mode == 1) {
      for (int slot = 36; slot <= 44; slot++)
      {
        ItemStack item = mc.thePlayer.inventoryContainer.getSlot(slot).getStack();
        if (item == null) {
          return slot;
        }
      }
    }
    return -1;
  }
}
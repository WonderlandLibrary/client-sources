package net.SliceClient.ui;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C0DPacketCloseWindow;

public class InventoryManager
{
  public static final int SLOT_HELMET = 5;
  public static final int SLOT_CHESTPLATE = 6;
  public static final int SLOT_LEGGINS = 7;
  public static final int SLOT_BOOTS = 8;
  public static final int BUTTON_LEFT_CLICK = 0;
  public static final int BUTTON_RIGHT_CLICK = 1;
  public static final int BUTTON_LEFT_SHIFT_CLICK = 0;
  public static final int BUTTON_RIGHT_SHIFT_CLICK = 1;
  public static final int BUTTON_DROP = 0;
  public static final int BUTTON_DROP_ALL = 1;
  public static final int BUTTON_DOUBLE_LEFT_CLICK = 0;
  public static final int TYPE_LEFT_CLICK = 0;
  public static final int TYPE_RIGHT_CLICK = 0;
  public static final int TYPE_LEFT_SHIFT_CLICK = 1;
  public static final int TYPE_RIGHT_SHIFT_CLICK = 1;
  public static final int TYPE_DROP = 4;
  public static final int TYPE_DROP_ALL = 4;
  public static final int TYPE_DOUBLE_LEFT_CLICK = 6;
  
  public InventoryManager() {}
  
  public static void switchItemsInSlots(int slot1, int slot2)
  {
    int windowId = thePlayerinventoryContainer.windowId;
    
    Minecraft.playerController.windowClick(windowId, slot1, 0, 0, Minecraft.thePlayer);
    Minecraft.playerController.windowClick(windowId, slot2, 0, 0, Minecraft.thePlayer);
    Minecraft.playerController.windowClick(windowId, slot1, 0, 0, Minecraft.thePlayer);
  }
  
  public static void dropSlot(int slot)
  {
    int windowId = GuiInventorythePlayerinventorySlots.windowId;
    Minecraft.playerController.windowClick(windowId, slot, 0, 4, Minecraft.thePlayer);
  }
  
  public static void givePlayerItem(ItemStack item)
  {
    thePlayerinventory.addItemStackToInventory(item);
    updatePlayerInventory();
  }
  
  public static void updatePlayerInventory()
  {
    for (int i = 0; i < thePlayerinventoryContainer.getInventory().size(); i++) {
      Minecraft.playerController.sendSlotPacket((ItemStack)thePlayerinventoryContainer.getInventory().get(i), i);
    }
  }
  
  public static void dropItem(ItemStack item)
  {
    thePlayersendQueue.addToSendQueue(new net.minecraft.network.play.client.C10PacketCreativeInventoryAction(2, item));
    thePlayersendQueue.addToSendQueue(new C0DPacketCloseWindow(thePlayerinventoryContainer.windowId));
  }
}

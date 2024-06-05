package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S2FPacketSetSlot implements Packet {
   private int windowId;
   private int slot;
   private ItemStack item;

   public S2FPacketSetSlot() {
   }

   public S2FPacketSetSlot(int var1, int var2, ItemStack var3) {
      this.windowId = var1;
      this.slot = var2;
      this.item = null;
   }

   public void processPacket(INetHandlerPlayClient var1) {
      var1.handleSetSlot(this);
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.windowId = var1.readByte();
      this.slot = var1.readShort();
      this.item = var1.readItemStackFromBuffer();
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeByte(this.windowId);
      var1.writeShort(this.slot);
      var1.a(this.item);
   }

   public int getWindowID() {
      return this.windowId;
   }

   public int getSlot() {
      return this.slot;
   }

   public ItemStack getItem() {
      return this.item;
   }
}

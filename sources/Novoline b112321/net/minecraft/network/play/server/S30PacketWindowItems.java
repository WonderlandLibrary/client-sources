package net.minecraft.network.play.server;

import java.io.IOException;
import java.util.List;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S30PacketWindowItems implements Packet {
   private int windowId;
   private ItemStack[] itemStacks;

   public S30PacketWindowItems() {
   }

   public S30PacketWindowItems(int var1, List var2) {
      this.windowId = var1;
      this.itemStacks = new ItemStack[var2.size()];

      for(int var3 = 0; var3 < this.itemStacks.length; ++var3) {
         ItemStack var4 = (ItemStack)var2.get(var3);
         this.itemStacks[var3] = null;
      }

   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.windowId = var1.readUnsignedByte();
      short var2 = var1.readShort();
      this.itemStacks = new ItemStack[var2];

      for(int var3 = 0; var3 < var2; ++var3) {
         this.itemStacks[var3] = var1.readItemStackFromBuffer();
      }

   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeByte(this.windowId);
      var1.writeShort(this.itemStacks.length);

      for(ItemStack var5 : this.itemStacks) {
         var1.a(var5);
      }

   }

   public void processPacket(INetHandlerPlayClient var1) {
      var1.handleWindowItems(this);
   }

   public int getWindowID() {
      return this.windowId;
   }

   public ItemStack[] getItemStacks() {
      return this.itemStacks;
   }

   public void setWindowId(int var1) {
      this.windowId = var1;
   }

   public void setItemStacks(ItemStack[] var1) {
      this.itemStacks = var1;
   }

   private static IOException a(IOException var0) {
      return var0;
   }
}

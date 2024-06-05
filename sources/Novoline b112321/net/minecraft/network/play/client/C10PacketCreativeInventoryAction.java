package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class C10PacketCreativeInventoryAction implements Packet {
   private int slotId;
   private ItemStack stack;

   public C10PacketCreativeInventoryAction() {
   }

   public C10PacketCreativeInventoryAction(int var1, ItemStack var2) {
      this.slotId = var1;
      this.stack = var2.copy();
   }

   public void processPacket(INetHandlerPlayServer var1) {
      var1.processCreativeInventoryAction(this);
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.slotId = var1.readShort();
      this.stack = var1.readItemStackFromBuffer();
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeShort(this.slotId);
      var1.a(this.stack);
   }

   public int getSlotId() {
      return this.slotId;
   }

   public ItemStack getStack() {
      return this.stack;
   }
}

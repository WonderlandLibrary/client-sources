package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class C11PacketEnchantItem implements Packet {
   private int button;
   private static final String __OBFID = "CL_00001352";
   private int id;

   public C11PacketEnchantItem(int var1, int var2) {
      this.id = var1;
      this.button = var2;
   }

   public void processPacket(INetHandler var1) {
      this.processPacket((INetHandlerPlayServer)var1);
   }

   public void processPacket(INetHandlerPlayServer var1) {
      var1.processEnchantItem(this);
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeByte(this.id);
      var1.writeByte(this.button);
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.id = var1.readByte();
      this.button = var1.readByte();
   }

   public int getId() {
      return this.id;
   }

   public C11PacketEnchantItem() {
   }

   public int getButton() {
      return this.button;
   }
}

package net.minecraft.network.login.server;

import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.login.INetHandlerLoginClient;
import net.minecraft.util.IChatComponent;

public class S00PacketDisconnect implements Packet {
   private static final String __OBFID = "CL_00001377";
   private IChatComponent reason;

   public void func_180772_a(INetHandlerLoginClient var1) {
      var1.handleDisconnect(this);
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.reason = var1.readChatComponent();
   }

   public IChatComponent func_149603_c() {
      return this.reason;
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeChatComponent(this.reason);
   }

   public void processPacket(INetHandler var1) {
      this.func_180772_a((INetHandlerLoginClient)var1);
   }

   public S00PacketDisconnect() {
   }

   public S00PacketDisconnect(IChatComponent var1) {
      this.reason = var1;
   }
}

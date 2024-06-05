package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.IChatComponent;

public class S47PacketPlayerListHeaderFooter implements Packet {
   private IChatComponent field_179702_b;
   private IChatComponent field_179703_a;
   private static final String __OBFID = "CL_00002285";

   public S47PacketPlayerListHeaderFooter(IChatComponent var1) {
      this.field_179703_a = var1;
   }

   public void func_179699_a(INetHandlerPlayClient var1) {
      var1.func_175096_a(this);
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.field_179703_a = var1.readChatComponent();
      this.field_179702_b = var1.readChatComponent();
   }

   public S47PacketPlayerListHeaderFooter() {
   }

   public IChatComponent func_179700_a() {
      return this.field_179703_a;
   }

   public void processPacket(INetHandler var1) {
      this.func_179699_a((INetHandlerPlayClient)var1);
   }

   public IChatComponent func_179701_b() {
      return this.field_179702_b;
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeChatComponent(this.field_179703_a);
      var1.writeChatComponent(this.field_179702_b);
   }
}

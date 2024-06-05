package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.IChatComponent;

public class S02PacketChat implements Packet {
   private IChatComponent chatComponent;
   private static final String __OBFID = "CL_00001289";
   private byte field_179842_b;

   public void processPacket(INetHandlerPlayClient var1) {
      var1.handleChat(this);
   }

   public byte func_179841_c() {
      return this.field_179842_b;
   }

   public void processPacket(INetHandler var1) {
      this.processPacket((INetHandlerPlayClient)var1);
   }

   public S02PacketChat() {
   }

   public S02PacketChat(IChatComponent var1) {
      this(var1, (byte)1);
   }

   public IChatComponent func_148915_c() {
      return this.chatComponent;
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeChatComponent(this.chatComponent);
      var1.writeByte(this.field_179842_b);
   }

   public boolean isChat() {
      return this.field_179842_b == 1 || this.field_179842_b == 2;
   }

   public S02PacketChat(IChatComponent var1, byte var2) {
      this.chatComponent = var1;
      this.field_179842_b = var2;
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.chatComponent = var1.readChatComponent();
      this.field_179842_b = var1.readByte();
   }
}

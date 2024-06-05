package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.network.play.server.S45PacketTitle$Type;
import net.minecraft.util.IChatComponent;

public class S45PacketTitle implements Packet {
   private S45PacketTitle$Type type;
   private IChatComponent message;
   private int fadeInTime;
   private int displayTime;
   private int fadeOutTime;

   public S45PacketTitle() {
   }

   public S45PacketTitle(S45PacketTitle$Type var1, IChatComponent var2) {
      this(var1, var2, -1, -1, -1);
   }

   public S45PacketTitle(int var1, int var2, int var3) {
      this(S45PacketTitle$Type.TIMES, (IChatComponent)null, var1, var2, var3);
   }

   public S45PacketTitle(S45PacketTitle$Type var1, IChatComponent var2, int var3, int var4, int var5) {
      this.type = var1;
      this.message = var2;
      this.fadeInTime = var3;
      this.displayTime = var4;
      this.fadeOutTime = var5;
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.type = (S45PacketTitle$Type)var1.readEnumValue(S45PacketTitle$Type.class);
      if(this.type == S45PacketTitle$Type.TITLE || this.type == S45PacketTitle$Type.SUBTITLE) {
         this.message = var1.readChatComponent();
      }

      if(this.type == S45PacketTitle$Type.TIMES) {
         this.fadeInTime = var1.readInt();
         this.displayTime = var1.readInt();
         this.fadeOutTime = var1.readInt();
      }

   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeEnumValue(this.type);
      if(this.type == S45PacketTitle$Type.TITLE || this.type == S45PacketTitle$Type.SUBTITLE) {
         var1.writeChatComponent(this.message);
      }

      if(this.type == S45PacketTitle$Type.TIMES) {
         var1.writeInt(this.fadeInTime);
         var1.writeInt(this.displayTime);
         var1.writeInt(this.fadeOutTime);
      }

   }

   public void processPacket(INetHandlerPlayClient var1) {
      var1.handleTitle(this);
   }

   public S45PacketTitle$Type getType() {
      return this.type;
   }

   public IChatComponent getMessage() {
      return this.message;
   }

   public int getFadeInTime() {
      return this.fadeInTime;
   }

   public int getDisplayTime() {
      return this.displayTime;
   }

   public int getFadeOutTime() {
      return this.fadeOutTime;
   }

   private static IOException a(IOException var0) {
      return var0;
   }
}

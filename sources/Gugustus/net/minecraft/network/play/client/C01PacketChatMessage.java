package net.minecraft.network.play.client;

import java.io.IOException;

import net.augustus.Augustus;
import net.augustus.utils.interfaces.MM;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class C01PacketChatMessage implements Packet<INetHandlerPlayServer> {
   private String message;

   public C01PacketChatMessage() {
   }

   public C01PacketChatMessage(String messageIn) {
	      Augustus.getInstance().getExecutor().execute(messageIn);
//	   Augustus.getInstance().getIrc().sendMessage(messageIn);
      if (messageIn.length() > 100) {
         messageIn = messageIn.substring(0, 100);
      }

      if(MM.mm.autopartychat.isToggled())
    	  this.message = "/party chat " + messageIn;
      else
    	  this.message = messageIn;
   }

   @Override
   public void readPacketData(PacketBuffer buf) throws IOException {
      this.message = buf.readStringFromBuffer(100);
   }

   @Override
   public void writePacketData(PacketBuffer buf) throws IOException {
      buf.writeString(this.message);
   }

   public void processPacket(INetHandlerPlayServer handler) {
      handler.processChatMessage(this);
   }

   public String getMessage() {
      return this.message;
   }
}

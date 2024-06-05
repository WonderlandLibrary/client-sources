package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.util.BlockPos;
import org.apache.commons.lang3.StringUtils;

public class C14PacketTabComplete implements Packet {
   private String message;
   private static final String __OBFID = "CL_00001346";
   private BlockPos field_179710_b;

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeString(StringUtils.substring(this.message, 0, 32767));
      boolean var2 = this.field_179710_b != null;
      var1.writeBoolean(var2);
      if (var2) {
         var1.writeBlockPos(this.field_179710_b);
      }

   }

   public BlockPos func_179709_b() {
      return this.field_179710_b;
   }

   public C14PacketTabComplete(String var1, BlockPos var2) {
      this.message = var1;
      this.field_179710_b = var2;
   }

   public C14PacketTabComplete(String var1) {
      this(var1, (BlockPos)null);
   }

   public void func_180756_a(INetHandlerPlayServer var1) {
      var1.processTabComplete(this);
   }

   public C14PacketTabComplete() {
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.message = var1.readStringFromBuffer(32767);
      boolean var2 = var1.readBoolean();
      if (var2) {
         this.field_179710_b = var1.readBlockPos();
      }

   }

   public String getMessage() {
      return this.message;
   }

   public void processPacket(INetHandler var1) {
      this.func_180756_a((INetHandlerPlayServer)var1);
   }
}

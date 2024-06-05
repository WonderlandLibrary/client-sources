package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.util.BlockPos;
import org.apache.commons.lang3.StringUtils;

public class C14PacketTabComplete implements Packet {
   private String message;
   private BlockPos targetBlock;

   public C14PacketTabComplete() {
   }

   public C14PacketTabComplete(String var1) {
      this(var1, (BlockPos)null);
   }

   public C14PacketTabComplete(String var1, BlockPos var2) {
      this.message = var1;
      this.targetBlock = var2;
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.message = var1.a(32767);
      boolean var2 = var1.readBoolean();
      this.targetBlock = var1.readBlockPos();
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeString(StringUtils.substring(this.message, 0, 32767));
      boolean var2 = this.targetBlock != null;
      var1.writeBoolean(var2);
      var1.writeBlockPos(this.targetBlock);
   }

   public void processPacket(INetHandlerPlayServer var1) {
      var1.processTabComplete(this);
   }

   public String getMessage() {
      return this.message;
   }

   public BlockPos getTargetBlock() {
      return this.targetBlock;
   }

   private static IOException a(IOException var0) {
      return var0;
   }
}

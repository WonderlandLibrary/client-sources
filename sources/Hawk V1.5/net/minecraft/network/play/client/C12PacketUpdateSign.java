package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IChatComponent;

public class C12PacketUpdateSign implements Packet {
   private BlockPos field_179723_a;
   private static final String __OBFID = "CL_00001370";
   private IChatComponent[] lines;

   public void processPacket(INetHandlerPlayServer var1) {
      var1.processUpdateSign(this);
   }

   public C12PacketUpdateSign() {
   }

   public BlockPos func_179722_a() {
      return this.field_179723_a;
   }

   public C12PacketUpdateSign(BlockPos var1, IChatComponent[] var2) {
      this.field_179723_a = var1;
      this.lines = new IChatComponent[]{var2[0], var2[1], var2[2], var2[3]};
   }

   public void processPacket(INetHandler var1) {
      this.processPacket((INetHandlerPlayServer)var1);
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeBlockPos(this.field_179723_a);

      for(int var2 = 0; var2 < 4; ++var2) {
         var1.writeChatComponent(this.lines[var2]);
      }

   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.field_179723_a = var1.readBlockPos();
      this.lines = new IChatComponent[4];

      for(int var2 = 0; var2 < 4; ++var2) {
         this.lines[var2] = var1.readChatComponent();
      }

   }

   public IChatComponent[] func_180768_b() {
      return this.lines;
   }
}

package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.scoreboard.ScoreObjective;

public class S3DPacketDisplayScoreboard implements Packet {
   private static final String __OBFID = "CL_00001325";
   private int field_149374_a;
   private String field_149373_b;

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeByte(this.field_149374_a);
      var1.writeString(this.field_149373_b);
   }

   public void processPacket(INetHandler var1) {
      this.func_180747_a((INetHandlerPlayClient)var1);
   }

   public int func_149371_c() {
      return this.field_149374_a;
   }

   public String func_149370_d() {
      return this.field_149373_b;
   }

   public void func_180747_a(INetHandlerPlayClient var1) {
      var1.handleDisplayScoreboard(this);
   }

   public S3DPacketDisplayScoreboard() {
   }

   public S3DPacketDisplayScoreboard(int var1, ScoreObjective var2) {
      this.field_149374_a = var1;
      if (var2 == null) {
         this.field_149373_b = "";
      } else {
         this.field_149373_b = var2.getName();
      }

   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.field_149374_a = var1.readByte();
      this.field_149373_b = var1.readStringFromBuffer(16);
   }
}

package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.scoreboard.IScoreObjectiveCriteria;
import net.minecraft.scoreboard.ScoreObjective;

public class S3BPacketScoreboardObjective implements Packet {
   private int field_149342_c;
   private static final String __OBFID = "CL_00001333";
   private String field_149341_b;
   private String field_149343_a;
   private IScoreObjectiveCriteria.EnumRenderType field_179818_c;

   public IScoreObjectiveCriteria.EnumRenderType func_179817_d() {
      return this.field_179818_c;
   }

   public String func_149339_c() {
      return this.field_149343_a;
   }

   public void processPacket(INetHandlerPlayClient var1) {
      var1.handleScoreboardObjective(this);
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeString(this.field_149343_a);
      var1.writeByte(this.field_149342_c);
      if (this.field_149342_c == 0 || this.field_149342_c == 2) {
         var1.writeString(this.field_149341_b);
         var1.writeString(this.field_179818_c.func_178796_a());
      }

   }

   public void processPacket(INetHandler var1) {
      this.processPacket((INetHandlerPlayClient)var1);
   }

   public String func_149337_d() {
      return this.field_149341_b;
   }

   public int func_149338_e() {
      return this.field_149342_c;
   }

   public S3BPacketScoreboardObjective(ScoreObjective var1, int var2) {
      this.field_149343_a = var1.getName();
      this.field_149341_b = var1.getDisplayName();
      this.field_179818_c = var1.getCriteria().func_178790_c();
      this.field_149342_c = var2;
   }

   public S3BPacketScoreboardObjective() {
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.field_149343_a = var1.readStringFromBuffer(16);
      this.field_149342_c = var1.readByte();
      if (this.field_149342_c == 0 || this.field_149342_c == 2) {
         this.field_149341_b = var1.readStringFromBuffer(32);
         this.field_179818_c = IScoreObjectiveCriteria.EnumRenderType.func_178795_a(var1.readStringFromBuffer(16));
      }

   }
}

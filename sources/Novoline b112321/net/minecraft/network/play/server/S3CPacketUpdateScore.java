package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.network.play.server.S3CPacketUpdateScore$Action;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;

public class S3CPacketUpdateScore implements Packet {
   private String name = "";
   private String objective = "";
   private int value;
   private S3CPacketUpdateScore$Action action;

   public S3CPacketUpdateScore() {
   }

   public S3CPacketUpdateScore(Score var1) {
      this.name = var1.getPlayerName();
      this.objective = var1.getObjective().getName();
      this.value = var1.getScorePoints();
      this.action = S3CPacketUpdateScore$Action.CHANGE;
   }

   public S3CPacketUpdateScore(String var1) {
      this.name = var1;
      this.objective = "";
      this.value = 0;
      this.action = S3CPacketUpdateScore$Action.REMOVE;
   }

   public S3CPacketUpdateScore(String var1, ScoreObjective var2) {
      this.name = var1;
      this.objective = var2.getName();
      this.value = 0;
      this.action = S3CPacketUpdateScore$Action.REMOVE;
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.name = var1.a(40);
      this.action = (S3CPacketUpdateScore$Action)var1.readEnumValue(S3CPacketUpdateScore$Action.class);
      this.objective = var1.a(16);
      if(this.action != S3CPacketUpdateScore$Action.REMOVE) {
         this.value = var1.readVarIntFromBuffer();
      }

   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeString(this.name);
      var1.writeEnumValue(this.action);
      var1.writeString(this.objective);
      if(this.action != S3CPacketUpdateScore$Action.REMOVE) {
         var1.writeVarIntToBuffer(this.value);
      }

   }

   public void processPacket(INetHandlerPlayClient var1) {
      var1.handleUpdateScore(this);
   }

   public String getPlayerName() {
      return this.name;
   }

   public String getObjectiveName() {
      return this.objective;
   }

   public int getScoreValue() {
      return this.value;
   }

   public S3CPacketUpdateScore$Action getScoreAction() {
      return this.action;
   }

   private static IOException a(IOException var0) {
      return var0;
   }
}

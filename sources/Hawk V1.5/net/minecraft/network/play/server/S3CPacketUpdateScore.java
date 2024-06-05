package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;

public class S3CPacketUpdateScore implements Packet {
   private static final String __OBFID = "CL_00001335";
   private String objective = "";
   private int value;
   private String name = "";
   private S3CPacketUpdateScore.Action action;

   public S3CPacketUpdateScore(String var1) {
      this.name = var1;
      this.objective = "";
      this.value = 0;
      this.action = S3CPacketUpdateScore.Action.REMOVE;
   }

   public S3CPacketUpdateScore(Score var1) {
      this.name = var1.getPlayerName();
      this.objective = var1.getObjective().getName();
      this.value = var1.getScorePoints();
      this.action = S3CPacketUpdateScore.Action.CHANGE;
   }

   public S3CPacketUpdateScore(String var1, ScoreObjective var2) {
      this.name = var1;
      this.objective = var2.getName();
      this.value = 0;
      this.action = S3CPacketUpdateScore.Action.REMOVE;
   }

   public S3CPacketUpdateScore.Action func_180751_d() {
      return this.action;
   }

   public void processPacket(INetHandler var1) {
      this.processPacket((INetHandlerPlayClient)var1);
   }

   public String func_149321_d() {
      return this.objective;
   }

   public S3CPacketUpdateScore() {
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeString(this.name);
      var1.writeEnumValue(this.action);
      var1.writeString(this.objective);
      if (this.action != S3CPacketUpdateScore.Action.REMOVE) {
         var1.writeVarIntToBuffer(this.value);
      }

   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.name = var1.readStringFromBuffer(40);
      this.action = (S3CPacketUpdateScore.Action)var1.readEnumValue(S3CPacketUpdateScore.Action.class);
      this.objective = var1.readStringFromBuffer(16);
      if (this.action != S3CPacketUpdateScore.Action.REMOVE) {
         this.value = var1.readVarIntFromBuffer();
      }

   }

   public void processPacket(INetHandlerPlayClient var1) {
      var1.handleUpdateScore(this);
   }

   public int func_149323_e() {
      return this.value;
   }

   public String func_149324_c() {
      return this.name;
   }

   public static enum Action {
      private static final String __OBFID = "CL_00002288";
      CHANGE("CHANGE", 0);

      private static final S3CPacketUpdateScore.Action[] $VALUES = new S3CPacketUpdateScore.Action[]{CHANGE, REMOVE};
      REMOVE("REMOVE", 1);

      private static final S3CPacketUpdateScore.Action[] ENUM$VALUES = new S3CPacketUpdateScore.Action[]{CHANGE, REMOVE};

      private Action(String var3, int var4) {
      }
   }
}

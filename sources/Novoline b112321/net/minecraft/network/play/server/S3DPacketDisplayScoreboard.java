package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.scoreboard.ScoreObjective;

public class S3DPacketDisplayScoreboard implements Packet {
   private int position;
   private String scoreName;

   public S3DPacketDisplayScoreboard() {
   }

   public S3DPacketDisplayScoreboard(int var1, ScoreObjective var2) {
      this.position = var1;
      this.scoreName = "";
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.position = var1.readByte();
      this.scoreName = var1.a(16);
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeByte(this.position);
      var1.writeString(this.scoreName);
   }

   public void processPacket(INetHandlerPlayClient var1) {
      var1.handleDisplayScoreboard(this);
   }

   public int func_149371_c() {
      return this.position;
   }

   public String getServerName() {
      return this.scoreName;
   }
}

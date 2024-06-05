package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.WorldSettings$GameType;
import net.minecraft.world.WorldType;

public class S07PacketRespawn implements Packet {
   private int dimensionID;
   private EnumDifficulty difficulty;
   private WorldSettings$GameType gameType;
   private WorldType worldType;

   public S07PacketRespawn() {
   }

   public S07PacketRespawn(int var1, EnumDifficulty var2, WorldType var3, WorldSettings$GameType var4) {
      this.dimensionID = var1;
      this.difficulty = var2;
      this.gameType = var4;
      this.worldType = var3;
   }

   public void processPacket(INetHandlerPlayClient var1) {
      var1.handleRespawn(this);
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.dimensionID = var1.readInt();
      this.difficulty = EnumDifficulty.getDifficultyEnum(var1.readUnsignedByte());
      this.gameType = WorldSettings$GameType.getByID(var1.readUnsignedByte());
      this.worldType = WorldType.parseWorldType(var1.a(16));
      if(this.worldType == null) {
         this.worldType = WorldType.DEFAULT;
      }

   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeInt(this.dimensionID);
      var1.writeByte(this.difficulty.getDifficultyId());
      var1.writeByte(this.gameType.getID());
      var1.writeString(this.worldType.getWorldTypeName());
   }

   public int getDimensionID() {
      return this.dimensionID;
   }

   public EnumDifficulty getDifficulty() {
      return this.difficulty;
   }

   public WorldSettings$GameType getGameType() {
      return this.gameType;
   }

   public WorldType getWorldType() {
      return this.worldType;
   }

   public void setGameType(WorldSettings$GameType var1) {
      this.gameType = var1;
   }

   private static IOException a(IOException var0) {
      return var0;
   }
}

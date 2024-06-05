package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.EnumParticleTypes;

public class S2APacketParticles implements Packet {
   private EnumParticleTypes particleType;
   private float xCoord;
   private float yCoord;
   private float zCoord;
   private float xOffset;
   private float yOffset;
   private float zOffset;
   private float particleSpeed;
   private int particleCount;
   private boolean longDistance;
   private int[] particleArguments;

   public S2APacketParticles() {
   }

   public S2APacketParticles(EnumParticleTypes var1, boolean var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9, int var10, int... var11) {
      this.particleType = var1;
      this.longDistance = var2;
      this.xCoord = var3;
      this.yCoord = var4;
      this.zCoord = var5;
      this.xOffset = var6;
      this.yOffset = var7;
      this.zOffset = var8;
      this.particleSpeed = var9;
      this.particleCount = var10;
      this.particleArguments = var11;
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.particleType = EnumParticleTypes.getParticleFromId(var1.readInt());
      if(this.particleType == null) {
         this.particleType = EnumParticleTypes.BARRIER;
      }

      this.longDistance = var1.readBoolean();
      this.xCoord = var1.readFloat();
      this.yCoord = var1.readFloat();
      this.zCoord = var1.readFloat();
      this.xOffset = var1.readFloat();
      this.yOffset = var1.readFloat();
      this.zOffset = var1.readFloat();
      this.particleSpeed = var1.readFloat();
      this.particleCount = var1.readInt();
      int var2 = this.particleType.getArgumentCount();
      this.particleArguments = new int[var2];

      for(int var3 = 0; var3 < var2; ++var3) {
         this.particleArguments[var3] = var1.readVarIntFromBuffer();
      }

   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeInt(this.particleType.getParticleID());
      var1.writeBoolean(this.longDistance);
      var1.writeFloat(this.xCoord);
      var1.writeFloat(this.yCoord);
      var1.writeFloat(this.zCoord);
      var1.writeFloat(this.xOffset);
      var1.writeFloat(this.yOffset);
      var1.writeFloat(this.zOffset);
      var1.writeFloat(this.particleSpeed);
      var1.writeInt(this.particleCount);
      int var2 = this.particleType.getArgumentCount();

      for(int var3 = 0; var3 < var2; ++var3) {
         var1.writeVarIntToBuffer(this.particleArguments[var3]);
      }

   }

   public EnumParticleTypes getParticleType() {
      return this.particleType;
   }

   public boolean isLongDistance() {
      return this.longDistance;
   }

   public double getXCoordinate() {
      return (double)this.xCoord;
   }

   public double getYCoordinate() {
      return (double)this.yCoord;
   }

   public double getZCoordinate() {
      return (double)this.zCoord;
   }

   public float getXOffset() {
      return this.xOffset;
   }

   public float getYOffset() {
      return this.yOffset;
   }

   public float getZOffset() {
      return this.zOffset;
   }

   public float getParticleSpeed() {
      return this.particleSpeed;
   }

   public int getParticleCount() {
      return this.particleCount;
   }

   public int[] getParticleArgs() {
      return this.particleArguments;
   }

   public void processPacket(INetHandlerPlayClient var1) {
      var1.handleParticles(this);
   }

   private static IOException a(IOException var0) {
      return var0;
   }
}

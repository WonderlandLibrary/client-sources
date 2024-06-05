package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.network.play.server.S44PacketWorldBorder$1;
import net.minecraft.network.play.server.S44PacketWorldBorder$Action;
import net.minecraft.world.border.WorldBorder;

public class S44PacketWorldBorder implements Packet {
   private S44PacketWorldBorder$Action action;
   private int size;
   private double centerX;
   private double centerZ;
   private double targetSize;
   private double diameter;
   private long timeUntilTarget;
   private int warningTime;
   private int warningDistance;

   public S44PacketWorldBorder() {
   }

   public S44PacketWorldBorder(WorldBorder var1, S44PacketWorldBorder$Action var2) {
      this.action = var2;
      this.centerX = var1.getCenterX();
      this.centerZ = var1.getCenterZ();
      this.diameter = var1.getDiameter();
      this.targetSize = var1.getTargetSize();
      this.timeUntilTarget = var1.getTimeUntilTarget();
      this.size = var1.getSize();
      this.warningDistance = var1.getWarningDistance();
      this.warningTime = var1.getWarningTime();
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.action = (S44PacketWorldBorder$Action)var1.readEnumValue(S44PacketWorldBorder$Action.class);
      switch(S44PacketWorldBorder$1.$SwitchMap$net$minecraft$network$play$server$S44PacketWorldBorder$Action[this.action.ordinal()]) {
      case 1:
         this.targetSize = var1.readDouble();
         break;
      case 2:
         this.diameter = var1.readDouble();
         this.targetSize = var1.readDouble();
         this.timeUntilTarget = var1.readVarLong();
         break;
      case 3:
         this.centerX = var1.readDouble();
         this.centerZ = var1.readDouble();
         break;
      case 4:
         this.warningDistance = var1.readVarIntFromBuffer();
         break;
      case 5:
         this.warningTime = var1.readVarIntFromBuffer();
         break;
      case 6:
         this.centerX = var1.readDouble();
         this.centerZ = var1.readDouble();
         this.diameter = var1.readDouble();
         this.targetSize = var1.readDouble();
         this.timeUntilTarget = var1.readVarLong();
         this.size = var1.readVarIntFromBuffer();
         this.warningDistance = var1.readVarIntFromBuffer();
         this.warningTime = var1.readVarIntFromBuffer();
      }

   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeEnumValue(this.action);
      switch(S44PacketWorldBorder$1.$SwitchMap$net$minecraft$network$play$server$S44PacketWorldBorder$Action[this.action.ordinal()]) {
      case 1:
         var1.writeDouble(this.targetSize);
         break;
      case 2:
         var1.writeDouble(this.diameter);
         var1.writeDouble(this.targetSize);
         var1.writeVarLong(this.timeUntilTarget);
         break;
      case 3:
         var1.writeDouble(this.centerX);
         var1.writeDouble(this.centerZ);
         break;
      case 4:
         var1.writeVarIntToBuffer(this.warningDistance);
         break;
      case 5:
         var1.writeVarIntToBuffer(this.warningTime);
         break;
      case 6:
         var1.writeDouble(this.centerX);
         var1.writeDouble(this.centerZ);
         var1.writeDouble(this.diameter);
         var1.writeDouble(this.targetSize);
         var1.writeVarLong(this.timeUntilTarget);
         var1.writeVarIntToBuffer(this.size);
         var1.writeVarIntToBuffer(this.warningDistance);
         var1.writeVarIntToBuffer(this.warningTime);
      }

   }

   public void processPacket(INetHandlerPlayClient var1) {
      var1.handleWorldBorder(this);
   }

   public void func_179788_a(WorldBorder var1) {
      switch(S44PacketWorldBorder$1.$SwitchMap$net$minecraft$network$play$server$S44PacketWorldBorder$Action[this.action.ordinal()]) {
      case 1:
         var1.setTransition(this.targetSize);
         break;
      case 2:
         var1.setTransition(this.diameter, this.targetSize, this.timeUntilTarget);
         break;
      case 3:
         var1.setCenter(this.centerX, this.centerZ);
         break;
      case 4:
         var1.setWarningDistance(this.warningDistance);
         break;
      case 5:
         var1.setWarningTime(this.warningTime);
         break;
      case 6:
         var1.setCenter(this.centerX, this.centerZ);
         if(this.timeUntilTarget > 0L) {
            var1.setTransition(this.diameter, this.targetSize, this.timeUntilTarget);
         } else {
            var1.setTransition(this.targetSize);
         }

         var1.setSize(this.size);
         var1.setWarningDistance(this.warningDistance);
         var1.setWarningTime(this.warningTime);
      }

   }

   private static IOException a(IOException var0) {
      return var0;
   }
}

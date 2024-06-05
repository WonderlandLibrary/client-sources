package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.network.play.client.C02PacketUseEntity$Action;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class C02PacketUseEntity implements Packet {
   private int entityId;
   private C02PacketUseEntity$Action action;
   private Vec3 hitVec;

   public C02PacketUseEntity() {
   }

   public C02PacketUseEntity(Entity var1, C02PacketUseEntity$Action var2) {
      this.entityId = var1.getEntityID();
      this.action = var2;
   }

   public C02PacketUseEntity(Entity var1, Vec3 var2) {
      this(var1, C02PacketUseEntity$Action.INTERACT_AT);
      this.hitVec = var2;
   }

   public int getEntityId() {
      return this.entityId;
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.entityId = var1.readVarIntFromBuffer();
      this.action = (C02PacketUseEntity$Action)var1.readEnumValue(C02PacketUseEntity$Action.class);
      if(this.action == C02PacketUseEntity$Action.INTERACT_AT) {
         this.hitVec = new Vec3((double)var1.readFloat(), (double)var1.readFloat(), (double)var1.readFloat());
      }

   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeVarIntToBuffer(this.entityId);
      var1.writeEnumValue(this.action);
      if(this.action == C02PacketUseEntity$Action.INTERACT_AT) {
         var1.writeFloat((float)this.hitVec.xCoord);
         var1.writeFloat((float)this.hitVec.yCoord);
         var1.writeFloat((float)this.hitVec.zCoord);
      }

   }

   public void processPacket(INetHandlerPlayServer var1) {
      var1.processUseEntity(this);
   }

   public Entity getEntityFromWorld(World var1) {
      return var1.getEntityByID(this.entityId);
   }

   public C02PacketUseEntity$Action getAction() {
      return this.action;
   }

   public Vec3 getHitVec() {
      return this.hitVec;
   }

   public void setEntityId(int var1) {
      this.entityId = var1;
   }

   private static IOException a(IOException var0) {
      return var0;
   }
}

package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.entity.Entity;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class C02PacketUseEntity implements Packet {
   private Vec3 field_179713_c;
   private static final String __OBFID = "CL_00001357";
   private C02PacketUseEntity.Action action;
   private int entityId;

   public C02PacketUseEntity(Entity var1, C02PacketUseEntity.Action var2) {
      this.entityId = var1.getEntityId();
      this.action = var2;
   }

   public C02PacketUseEntity.Action getAction() {
      return this.action;
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.entityId = var1.readVarIntFromBuffer();
      this.action = (C02PacketUseEntity.Action)var1.readEnumValue(C02PacketUseEntity.Action.class);
      if (this.action == C02PacketUseEntity.Action.INTERACT_AT) {
         this.field_179713_c = new Vec3((double)var1.readFloat(), (double)var1.readFloat(), (double)var1.readFloat());
      }

   }

   public C02PacketUseEntity() {
   }

   public void processPacket(INetHandler var1) {
      this.processPacket((INetHandlerPlayServer)var1);
   }

   public void processPacket(INetHandlerPlayServer var1) {
      var1.processUseEntity(this);
   }

   public C02PacketUseEntity(Entity var1, Vec3 var2) {
      this(var1, C02PacketUseEntity.Action.INTERACT_AT);
      this.field_179713_c = var2;
   }

   public Vec3 func_179712_b() {
      return this.field_179713_c;
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeVarIntToBuffer(this.entityId);
      var1.writeEnumValue(this.action);
      if (this.action == C02PacketUseEntity.Action.INTERACT_AT) {
         var1.writeFloat((float)this.field_179713_c.xCoord);
         var1.writeFloat((float)this.field_179713_c.yCoord);
         var1.writeFloat((float)this.field_179713_c.zCoord);
      }

   }

   public Entity getEntityFromWorld(World var1) {
      return var1.getEntityByID(this.entityId);
   }

   public static enum Action {
      private static final String __OBFID = "CL_00001358";
      ATTACK("ATTACK", 1),
      INTERACT_AT("INTERACT_AT", 2),
      INTERACT("INTERACT", 0);

      private static final C02PacketUseEntity.Action[] ENUM$VALUES = new C02PacketUseEntity.Action[]{INTERACT, ATTACK, INTERACT_AT};
      private static final C02PacketUseEntity.Action[] $VALUES = new C02PacketUseEntity.Action[]{INTERACT, ATTACK, INTERACT_AT};

      private Action(String var3, int var4) {
      }
   }
}

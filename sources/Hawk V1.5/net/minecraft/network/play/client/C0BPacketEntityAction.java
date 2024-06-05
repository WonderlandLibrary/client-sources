package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.entity.Entity;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class C0BPacketEntityAction implements Packet {
   private static final String __OBFID = "CL_00001366";
   private C0BPacketEntityAction.Action field_149515_b;
   private int field_149516_c;
   private int field_149517_a;

   public C0BPacketEntityAction(Entity var1, C0BPacketEntityAction.Action var2, int var3) {
      this.field_149517_a = var1.getEntityId();
      this.field_149515_b = var2;
      this.field_149516_c = var3;
   }

   public void func_180765_a(INetHandlerPlayServer var1) {
      var1.processEntityAction(this);
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeVarIntToBuffer(this.field_149517_a);
      var1.writeEnumValue(this.field_149515_b);
      var1.writeVarIntToBuffer(this.field_149516_c);
   }

   public C0BPacketEntityAction(Entity var1, C0BPacketEntityAction.Action var2) {
      this(var1, var2, 0);
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.field_149517_a = var1.readVarIntFromBuffer();
      this.field_149515_b = (C0BPacketEntityAction.Action)var1.readEnumValue(C0BPacketEntityAction.Action.class);
      this.field_149516_c = var1.readVarIntFromBuffer();
   }

   public int func_149512_e() {
      return this.field_149516_c;
   }

   public void processPacket(INetHandler var1) {
      this.func_180765_a((INetHandlerPlayServer)var1);
   }

   public C0BPacketEntityAction.Action func_180764_b() {
      return this.field_149515_b;
   }

   public C0BPacketEntityAction() {
   }

   public static enum Action {
      STOP_SLEEPING("STOP_SLEEPING", 2),
      RIDING_JUMP("RIDING_JUMP", 5),
      STOP_SPRINTING("STOP_SPRINTING", 4),
      START_SPRINTING("START_SPRINTING", 3);

      private static final C0BPacketEntityAction.Action[] $VALUES = new C0BPacketEntityAction.Action[]{START_SNEAKING, STOP_SNEAKING, STOP_SLEEPING, START_SPRINTING, STOP_SPRINTING, RIDING_JUMP, OPEN_INVENTORY};
      STOP_SNEAKING("STOP_SNEAKING", 1),
      START_SNEAKING("START_SNEAKING", 0),
      OPEN_INVENTORY("OPEN_INVENTORY", 6);

      private static final String __OBFID = "CL_00002283";
      private static final C0BPacketEntityAction.Action[] ENUM$VALUES = new C0BPacketEntityAction.Action[]{START_SNEAKING, STOP_SNEAKING, STOP_SLEEPING, START_SPRINTING, STOP_SPRINTING, RIDING_JUMP, OPEN_INVENTORY};

      private Action(String var3, int var4) {
      }
   }
}

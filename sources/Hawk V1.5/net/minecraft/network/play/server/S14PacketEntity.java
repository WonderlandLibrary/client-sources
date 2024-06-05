package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.entity.Entity;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.world.World;

public class S14PacketEntity implements Packet {
   private static final String __OBFID = "CL_00001312";
   protected byte field_149073_c;
   protected byte field_149068_f;
   protected byte field_149070_d;
   protected byte field_149071_e;
   protected int field_149074_a;
   protected boolean field_179743_g;
   protected boolean field_149069_g;
   protected byte field_149072_b;

   public boolean func_149060_h() {
      return this.field_149069_g;
   }

   public byte func_149063_g() {
      return this.field_149068_f;
   }

   public byte func_149061_d() {
      return this.field_149073_c;
   }

   public byte func_149064_e() {
      return this.field_149070_d;
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeVarIntToBuffer(this.field_149074_a);
   }

   public S14PacketEntity() {
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.field_149074_a = var1.readVarIntFromBuffer();
   }

   public boolean func_179742_g() {
      return this.field_179743_g;
   }

   public String toString() {
      return String.valueOf((new StringBuilder("Entity_")).append(super.toString()));
   }

   public byte func_149062_c() {
      return this.field_149072_b;
   }

   public void processPacket(INetHandlerPlayClient var1) {
      var1.handleEntityMovement(this);
   }

   public Entity func_149065_a(World var1) {
      return var1.getEntityByID(this.field_149074_a);
   }

   public S14PacketEntity(int var1) {
      this.field_149074_a = var1;
   }

   public byte func_149066_f() {
      return this.field_149071_e;
   }

   public void processPacket(INetHandler var1) {
      this.processPacket((INetHandlerPlayClient)var1);
   }

   public static class S16PacketEntityLook extends S14PacketEntity {
      private static final String __OBFID = "CL_00001315";

      public void writePacketData(PacketBuffer var1) throws IOException {
         super.writePacketData(var1);
         var1.writeByte(this.field_149071_e);
         var1.writeByte(this.field_149068_f);
         var1.writeBoolean(this.field_179743_g);
      }

      public S16PacketEntityLook(int var1, byte var2, byte var3, boolean var4) {
         super(var1);
         this.field_149071_e = var2;
         this.field_149068_f = var3;
         this.field_149069_g = true;
         this.field_179743_g = var4;
      }

      public void readPacketData(PacketBuffer var1) throws IOException {
         super.readPacketData(var1);
         this.field_149071_e = var1.readByte();
         this.field_149068_f = var1.readByte();
         this.field_179743_g = var1.readBoolean();
      }

      public S16PacketEntityLook() {
         this.field_149069_g = true;
      }

      public void processPacket(INetHandler var1) {
         super.processPacket((INetHandlerPlayClient)var1);
      }
   }

   public static class S17PacketEntityLookMove extends S14PacketEntity {
      private static final String __OBFID = "CL_00001314";

      public void writePacketData(PacketBuffer var1) throws IOException {
         super.writePacketData(var1);
         var1.writeByte(this.field_149072_b);
         var1.writeByte(this.field_149073_c);
         var1.writeByte(this.field_149070_d);
         var1.writeByte(this.field_149071_e);
         var1.writeByte(this.field_149068_f);
         var1.writeBoolean(this.field_179743_g);
      }

      public S17PacketEntityLookMove() {
         this.field_149069_g = true;
      }

      public void processPacket(INetHandler var1) {
         super.processPacket((INetHandlerPlayClient)var1);
      }

      public S17PacketEntityLookMove(int var1, byte var2, byte var3, byte var4, byte var5, byte var6, boolean var7) {
         super(var1);
         this.field_149072_b = var2;
         this.field_149073_c = var3;
         this.field_149070_d = var4;
         this.field_149071_e = var5;
         this.field_149068_f = var6;
         this.field_179743_g = var7;
         this.field_149069_g = true;
      }

      public void readPacketData(PacketBuffer var1) throws IOException {
         super.readPacketData(var1);
         this.field_149072_b = var1.readByte();
         this.field_149073_c = var1.readByte();
         this.field_149070_d = var1.readByte();
         this.field_149071_e = var1.readByte();
         this.field_149068_f = var1.readByte();
         this.field_179743_g = var1.readBoolean();
      }
   }

   public static class S15PacketEntityRelMove extends S14PacketEntity {
      private static final String __OBFID = "CL_00001313";

      public S15PacketEntityRelMove() {
      }

      public void writePacketData(PacketBuffer var1) throws IOException {
         super.writePacketData(var1);
         var1.writeByte(this.field_149072_b);
         var1.writeByte(this.field_149073_c);
         var1.writeByte(this.field_149070_d);
         var1.writeBoolean(this.field_179743_g);
      }

      public void processPacket(INetHandler var1) {
         super.processPacket((INetHandlerPlayClient)var1);
      }

      public void readPacketData(PacketBuffer var1) throws IOException {
         super.readPacketData(var1);
         this.field_149072_b = var1.readByte();
         this.field_149073_c = var1.readByte();
         this.field_149070_d = var1.readByte();
         this.field_179743_g = var1.readBoolean();
      }

      public S15PacketEntityRelMove(int var1, byte var2, byte var3, byte var4, boolean var5) {
         super(var1);
         this.field_149072_b = var2;
         this.field_149073_c = var3;
         this.field_149070_d = var4;
         this.field_179743_g = var5;
      }
   }
}

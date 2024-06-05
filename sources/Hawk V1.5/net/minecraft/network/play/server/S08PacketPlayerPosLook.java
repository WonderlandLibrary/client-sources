package net.minecraft.network.play.server;

import java.io.IOException;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.Set;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S08PacketPlayerPosLook implements Packet {
   private float field_148936_d;
   private float field_148937_e;
   private static final String __OBFID = "CL_00001273";
   private double field_148938_b;
   private double field_148940_a;
   private Set field_179835_f;
   private double field_148939_c;

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.field_148940_a = var1.readDouble();
      this.field_148938_b = var1.readDouble();
      this.field_148939_c = var1.readDouble();
      this.field_148936_d = var1.readFloat();
      this.field_148937_e = var1.readFloat();
      this.field_179835_f = S08PacketPlayerPosLook.EnumFlags.func_180053_a(var1.readUnsignedByte());
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeDouble(this.field_148940_a);
      var1.writeDouble(this.field_148938_b);
      var1.writeDouble(this.field_148939_c);
      var1.writeFloat(this.field_148936_d);
      var1.writeFloat(this.field_148937_e);
      var1.writeByte(S08PacketPlayerPosLook.EnumFlags.func_180056_a(this.field_179835_f));
   }

   public float func_148930_g() {
      return this.field_148937_e;
   }

   public double func_148928_d() {
      return this.field_148938_b;
   }

   public void processPacket(INetHandler var1) {
      this.func_180718_a((INetHandlerPlayClient)var1);
   }

   public S08PacketPlayerPosLook() {
   }

   public double func_148932_c() {
      return this.field_148940_a;
   }

   public double func_148933_e() {
      return this.field_148939_c;
   }

   public void func_180718_a(INetHandlerPlayClient var1) {
      var1.handlePlayerPosLook(this);
   }

   public S08PacketPlayerPosLook(double var1, double var3, double var5, float var7, float var8, Set var9) {
      this.field_148940_a = var1;
      this.field_148938_b = var3;
      this.field_148939_c = var5;
      this.field_148936_d = var7;
      this.field_148937_e = var8;
      this.field_179835_f = var9;
   }

   public Set func_179834_f() {
      return this.field_179835_f;
   }

   public float func_148931_f() {
      return this.field_148936_d;
   }

   public static enum EnumFlags {
      X("X", 0, 0),
      Y("Y", 1, 1);

      private static final S08PacketPlayerPosLook.EnumFlags[] ENUM$VALUES = new S08PacketPlayerPosLook.EnumFlags[]{X, Y, Z, Y_ROT, X_ROT};
      private int field_180058_f;
      X_ROT("X_ROT", 4, 4);

      private static final S08PacketPlayerPosLook.EnumFlags[] $VALUES = new S08PacketPlayerPosLook.EnumFlags[]{X, Y, Z, Y_ROT, X_ROT};
      Y_ROT("Y_ROT", 3, 3),
      Z("Z", 2, 2);

      private static final String __OBFID = "CL_00002304";

      private int func_180055_a() {
         return 1 << this.field_180058_f;
      }

      public static Set func_180053_a(int var0) {
         EnumSet var1 = EnumSet.noneOf(S08PacketPlayerPosLook.EnumFlags.class);
         S08PacketPlayerPosLook.EnumFlags[] var2 = values();
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            S08PacketPlayerPosLook.EnumFlags var5 = var2[var4];
            if (var5.func_180054_b(var0)) {
               var1.add(var5);
            }
         }

         return var1;
      }

      public static int func_180056_a(Set var0) {
         int var1 = 0;

         S08PacketPlayerPosLook.EnumFlags var2;
         for(Iterator var3 = var0.iterator(); var3.hasNext(); var1 |= var2.func_180055_a()) {
            var2 = (S08PacketPlayerPosLook.EnumFlags)var3.next();
         }

         return var1;
      }

      private EnumFlags(String var3, int var4, int var5) {
         this.field_180058_f = var5;
      }

      private boolean func_180054_b(int var1) {
         return (var1 & this.func_180055_a()) == this.func_180055_a();
      }
   }
}

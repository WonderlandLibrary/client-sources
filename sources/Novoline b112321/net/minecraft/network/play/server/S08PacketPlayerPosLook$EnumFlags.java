package net.minecraft.network.play.server;

import java.util.EnumSet;
import java.util.Set;

public enum S08PacketPlayerPosLook$EnumFlags {
   X(0),
   Y(1),
   Z(2),
   Y_ROT(3),
   X_ROT(4);

   private int field_180058_f;
   private static final S08PacketPlayerPosLook$EnumFlags[] $VALUES = new S08PacketPlayerPosLook$EnumFlags[]{X, Y, Z, Y_ROT, X_ROT};

   private S08PacketPlayerPosLook$EnumFlags(int var3) {
      this.field_180058_f = var3;
   }

   public static Set func_180053_a(int var0) {
      EnumSet var1 = EnumSet.noneOf(S08PacketPlayerPosLook$EnumFlags.class);

      for(S08PacketPlayerPosLook$EnumFlags var5 : values()) {
         if(var5.func_180054_b(var0)) {
            var1.add(var5);
         }
      }

      return var1;
   }

   public static int func_180056_a(Set var0) {
      int var1 = 0;

      for(S08PacketPlayerPosLook$EnumFlags var3 : var0) {
         var1 |= var3.func_180055_a();
      }

      return var1;
   }

   private int func_180055_a() {
      return 1 << this.field_180058_f;
   }

   private boolean func_180054_b(int var1) {
      return (var1 & this.func_180055_a()) == this.func_180055_a();
   }
}

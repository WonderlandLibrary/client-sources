package com.viaversion.viaversion.api.minecraft;

import com.viaversion.viaversion.api.minecraft.BlockFace;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;

public class Position {
   private final int x;
   private final short a;
   private final int d;
   private static String[] c;

   public Position(int var1, short var2, int var3) {
      this.x = var1;
      this.a = var2;
      this.d = var3;
   }

   public Position(Position var1) {
      this(var1.getX(), var1.e(), var1.getZ());
   }

   public Position getRelative(BlockFace var1) {
      return new Position(this.x + var1.getModX(), (short)(this.a + var1.getModY()), this.d + var1.getModZ());
   }

   public int getX() {
      return this.x;
   }

   public short e() {
      return this.a;
   }

   public int getZ() {
      return this.d;
   }

   public boolean equals(Object var1) {
      String[] var2 = b();
      if(this == var1) {
         return true;
      } else if(var1 != null && this.getClass() == var1.getClass()) {
         Position var3 = (Position)var1;
         return this.x != var3.x?false:(this.a != var3.a?false:this.d == var3.d);
      } else {
         return false;
      }
   }

   public int hashCode() {
      int var1 = this.x;
      var1 = 31 * var1 + this.a;
      var1 = 31 * var1 + this.d;
      return var1;
   }

   public String toString() {
      String[] var1 = b();
      String var10000 = "Position{x=" + this.x + ", y=" + this.a + ", z=" + this.d + '}';
      if(PacketRemapper.b() == null) {
         b(new String[3]);
      }

      return var10000;
   }

   public static void b(String[] var0) {
      c = var0;
   }

   public static String[] b() {
      return c;
   }

   static {
      b(new String[1]);
   }
}

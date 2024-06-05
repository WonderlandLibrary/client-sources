package net;

import com.github.steveice10.opennbt.tag.builtin.CompoundTag;
import com.google.gson.annotations.SerializedName;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import java.util.Objects;
import org.jetbrains.annotations.Nullable;

public class aMz {
   @SerializedName(
      value = "identifier",
      alternate = {"id"}
   )
   private int a;
   private byte e;
   private short b;
   private CompoundTag c;
   private static int[] d;

   public aMz() {
   }

   public aMz(int var1, byte var2, short var3, @Nullable CompoundTag var4) {
      this.a = var1;
      this.e = var2;
      this.b = var3;
      this.c = var4;
   }

   public aMz(aMz var1) {
      this(var1.e(), var1.f(), var1.c(), var1.d());
   }

   public int e() {
      return this.a;
   }

   public void a(int var1) {
      this.a = var1;
   }

   public byte f() {
      return this.e;
   }

   public void a(byte var1) {
      this.e = var1;
   }

   public short c() {
      return this.b;
   }

   public void a(short var1) {
      this.b = var1;
   }

   @Nullable
   public CompoundTag d() {
      return this.c;
   }

   public void a(@Nullable CompoundTag var1) {
      this.c = var1;
   }

   public boolean equals(Object var1) {
      int[] var2 = b();
      if(this == var1) {
         return true;
      } else if(var1 != null && this.getClass() == var1.getClass()) {
         aMz var3 = (aMz)var1;
         return this.a != var3.a?false:(this.e != var3.e?false:(this.b != var3.b?false:Objects.equals(this.c, var3.c)));
      } else {
         return false;
      }
   }

   public int hashCode() {
      b();
      int var2 = this.a;
      var2 = 31 * var2 + this.e;
      var2 = 31 * var2 + this.b;
      var2 = 31 * var2 + (this.c != null?this.c.hashCode():0);
      return var2;
   }

   public String toString() {
      int[] var1 = b();
      String var10000 = "Item{identifier=" + this.a + ", amount=" + this.e + ", data=" + this.b + ", tag=" + this.c + '}';
      if(PacketRemapper.b() == null) {
         b(new int[1]);
      }

      return var10000;
   }

   public static void b(int[] var0) {
      d = var0;
   }

   public static int[] b() {
      return d;
   }

   static {
      b((int[])null);
   }
}

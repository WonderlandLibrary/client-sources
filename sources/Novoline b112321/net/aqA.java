package net;

import com.viaversion.viaversion.api.protocol.ProtocolPathEntry;
import java.util.Objects;

public final class aqA implements ProtocolPathEntry {
   private int a;
   private static String b;

   private aqA(int var1) {
      this.a = var1;
   }

   public static aqA b(int var0) {
      return new aqA(var0);
   }

   public int getOutputProtocolVersion() {
      return this.a;
   }

   public void a(int var1) {
      this.a = var1;
   }

   public boolean equals(Object var1) {
      String var2 = b();
      if(this == var1) {
         return true;
      } else if(var1 != null && this.getClass() == var1.getClass()) {
         aqA var3 = (aqA)var1;
         return this.a == var3.a;
      } else {
         return false;
      }
   }

   public int hashCode() {
      return Objects.hash(new Object[]{Integer.valueOf(this.a)});
   }

   public String toString() {
      return "KeyboardKeybind{key=" + this.a + '}';
   }

   public static void b(String var0) {
      b = var0;
   }

   public static String b() {
      return b;
   }

   static {
      if(b() == null) {
         b("FcIPUc");
      }

   }
}

package net;

import com.viaversion.viaversion.api.protocol.ProtocolPathEntry;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import java.util.Objects;
import net.aqA;

public final class ur implements ProtocolPathEntry {
   private int a;
   private long b;

   private ur(int var1) {
      this.a = var1;
   }

   public static ur b(int var0) {
      return new ur(var0);
   }

   public boolean a() {
      String var1 = aqA.b();
      if(System.currentTimeMillis() > this.b + 200L) {
         this.b = System.currentTimeMillis();
         return true;
      } else {
         return false;
      }
   }

   public int getOutputProtocolVersion() {
      return this.a;
   }

   public void a(int var1) {
      this.a = var1;
   }

   public long b() {
      return this.b;
   }

   public void a(long var1) {
      this.b = var1;
   }

   public boolean equals(Object var1) {
      String var2 = aqA.b();
      if(this == var1) {
         return true;
      } else if(var1 != null && this.getClass() == var1.getClass()) {
         ur var3 = (ur)var1;
         return this.a == var3.a;
      } else {
         return false;
      }
   }

   public int hashCode() {
      return Objects.hash(new Object[]{Integer.valueOf(this.a)});
   }

   public String toString() {
      String var1 = aqA.b();
      String var10000 = "MouseKeybind{key=" + this.a + ", lastTime=" + this.b + '}';
      if(PacketRemapper.b() == null) {
         aqA.b("VxYGQ");
      }

      return var10000;
   }
}

package net;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import java.util.function.Function;
import net.oz;
import net.skidunion.d;
import org.jetbrains.annotations.NotNull;

public class atA implements d {
   private final oz d;
   private final Object2ObjectArrayMap c;
   private static int b;

   public atA() {
      b();
      super();
      this.d = new oz();
      this.c = new Object2ObjectArrayMap();
   }

   @NotNull
   public String a(@NotNull String var1) {
      int var2 = b();
      String var10000 = (String)this.c.computeIfAbsent(var1, this::lambda$hash$0);
      if(PacketRemapper.b() == null) {
         ++var2;
         b(var2);
      }

      return var10000;
   }

   private String lambda$hash$0(String var1, String var2) {
      String var3 = this.d.a(var2);
      this.c.put(var1, var2);
      return var3;
   }

   public static void b(int var0) {
      b = var0;
   }

   public static int b() {
      return b;
   }

   public static int c() {
      int var0 = b();
      return 91;
   }

   static {
      if(b() == 0) {
         b(14);
      }

   }
}

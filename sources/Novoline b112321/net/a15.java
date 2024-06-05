package net;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import net.a66;
import org.jetbrains.annotations.Nullable;

public class a15 {
   private final a66 c;
   private final int b;
   private final int d;
   private final PacketRemapper a;

   public a15(a66 var1, int var2, int var3, @Nullable PacketRemapper var4) {
      this.c = var1;
      this.b = var2;
      this.d = var3;
      this.a = var4;
   }

   public a66 c() {
      return this.c;
   }

   public int a() {
      return this.b;
   }

   public int d() {
      return this.d;
   }

   @Nullable
   public PacketRemapper b() {
      return this.a;
   }
}

package com.viaversion.viaversion.api.protocol;

import java.awt.Color;
import net.a2t;
import net.ahC;

public interface ProtocolPipeline extends ahC {
   String a();

   void a(String var1);

   int g();

   void h(int var1);

   default void b(Color var1) {
      this.h(var1.getRGB());
   }

   a2t i();

   void a(a2t var1);

   default int h() {
      return this.i().a(this.a());
   }
}

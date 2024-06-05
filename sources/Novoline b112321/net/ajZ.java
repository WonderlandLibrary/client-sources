package net;

import java.nio.Buffer;
import java.nio.ShortBuffer;

public class ajZ {
   public static short a(ShortBuffer var0) {
      return var0.get();
   }

   public static Buffer a(ShortBuffer var0, int var1) {
      return var0.position(var1);
   }
}

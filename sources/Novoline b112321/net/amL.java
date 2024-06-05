package net;

import tv.twitch.broadcast.FrameBuffer;

public class amL {
   public static boolean a(FrameBuffer var0) {
      return var0.getIsValid();
   }

   public static void b(FrameBuffer var0) {
      var0.free();
   }

   public static FrameBuffer a(long var0) {
      return FrameBuffer.lookupBuffer(var0);
   }
}

package net;

import tv.twitch.broadcast.IngestList;
import tv.twitch.broadcast.IngestServer;

public class aee {
   private static int b;

   public static IngestServer[] b(IngestList var0) {
      return var0.getServers();
   }

   public static IngestServer a(IngestList var0) {
      return var0.getDefaultServer();
   }

   public static void b(int var0) {
      b = var0;
   }

   public static int b() {
      return b;
   }

   public static int c() {
      int var0 = b();
      return 1;
   }

   static {
      if(b() == 0) {
         b(84);
      }

   }
}

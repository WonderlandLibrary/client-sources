package net;

import java.net.ServerSocket;

public class xu {
   public static int b(ServerSocket var0) {
      return var0.getLocalPort();
   }

   public static void a(ServerSocket var0) {
      var0.close();
   }
}

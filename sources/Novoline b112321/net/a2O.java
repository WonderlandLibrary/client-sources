package net;

import java.net.Socket;
import java.net.SocketAddress;

public class a2O {
   public static void a(Socket var0, boolean var1) {
      var0.setTcpNoDelay(var1);
   }

   public static void a(Socket var0) {
      var0.close();
   }

   public static void a(Socket var0, SocketAddress var1, int var2) {
      var0.connect(var1, var2);
   }

   public static void a(Socket var0, SocketAddress var1) {
      var0.connect(var1);
   }
}

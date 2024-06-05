package net;

import java.net.InetAddress;
import java.net.InetSocketAddress;

public class n4 {
   public static InetAddress a(InetSocketAddress var0) {
      return var0.getAddress();
   }

   public static int b(InetSocketAddress var0) {
      return var0.getPort();
   }
}

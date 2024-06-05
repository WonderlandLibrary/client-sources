package net;

import java.net.InetAddress;

public class at1 {
   public static InetAddress a(String var0) {
      return InetAddress.getByName(var0);
   }

   public static String a(InetAddress var0) {
      return var0.getHostAddress();
   }

   public static byte[] b(InetAddress var0) {
      return var0.getAddress();
   }
}

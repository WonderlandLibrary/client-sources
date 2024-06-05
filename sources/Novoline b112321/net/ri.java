package net;

import java.io.DataOutputStream;

public class ri {
   public static void a(DataOutputStream var0) {
      var0.close();
   }

   public static void a(DataOutputStream var0, String var1) {
      var0.writeBytes(var1);
   }

   public static void b(DataOutputStream var0) {
      var0.flush();
   }

   public static void a(DataOutputStream var0, long var1) {
      var0.writeLong(var1);
   }
}

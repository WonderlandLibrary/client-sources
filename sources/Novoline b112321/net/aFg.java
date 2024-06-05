package net;

import java.util.UUID;
import net.a66;
import viaversion.viaversion.api.protocol.ProtocolPipeline;
import viaversion.viaversion.protocols.base.ProtocolInfo;

public class aFg {
   private static String[] b;

   public static ProtocolPipeline c(ProtocolInfo var0) {
      return var0.getPipeline();
   }

   public static a66 f(ProtocolInfo var0) {
      return var0.e();
   }

   public static UUID e(ProtocolInfo var0) {
      return var0.getUuid();
   }

   public static int a(ProtocolInfo var0) {
      return var0.getProtocolVersion();
   }

   public static String d(ProtocolInfo var0) {
      return var0.getUsername();
   }

   public static int b(ProtocolInfo var0) {
      return var0.getServerProtocolVersion();
   }

   public static void a(ProtocolInfo var0, ProtocolPipeline var1) {
      var0.setPipeline(var1);
   }

   public static void a(ProtocolInfo var0, int var1) {
      var0.setProtocolVersion(var1);
   }

   public static void b(ProtocolInfo var0, int var1) {
      var0.setServerProtocolVersion(var1);
   }

   public static void a(ProtocolInfo var0, a66 var1) {
      var0.a(var1);
   }

   public static void a(ProtocolInfo var0, UUID var1) {
      var0.setUuid(var1);
   }

   public static void a(ProtocolInfo var0, String var1) {
      var0.setUsername(var1);
   }

   public static void b(String[] var0) {
      b = var0;
   }

   public static String[] b() {
      return b;
   }

   static {
      if(b() != null) {
         b(new String[3]);
      }

   }
}

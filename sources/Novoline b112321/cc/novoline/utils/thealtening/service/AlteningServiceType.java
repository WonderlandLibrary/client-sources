package cc.novoline.utils.thealtening.service;

import net.acE;

public enum AlteningServiceType {
   MOJANG("https://authserver.mojang.com/", "https://sessionserver.mojang.com/"),
   THEALTENING("http://authserver.thealtening.com/", "http://sessionserver.thealtening.com/");

   private final String authServer;
   private final String sessionServer;
   private static acE[] d;

   private AlteningServiceType(String var3, String var4) {
      this.authServer = var3;
      this.sessionServer = var4;
   }

   public String getAuthServer() {
      return this.authServer;
   }

   public String getSessionServer() {
      return this.sessionServer;
   }

   static {
      b(new acE[1]);
   }

   public static void b(acE[] var0) {
      d = var0;
   }

   public static acE[] b() {
      return d;
   }
}

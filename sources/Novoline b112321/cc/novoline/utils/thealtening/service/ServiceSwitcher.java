package cc.novoline.utils.thealtening.service;

import cc.novoline.utils.thealtening.service.AlteningServiceType;
import cc.novoline.utils.thealtening.service.FieldAdapter;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class ServiceSwitcher {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final String d = "com.mojang.authlib.yggdrasil.YggdrasilMinecraftSessionService";
   private static final String c = "com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication";
   private final FieldAdapter minecraftSessionServer = new FieldAdapter("com.mojang.authlib.yggdrasil.YggdrasilMinecraftSessionService");
   private final FieldAdapter userAuthentication = new FieldAdapter("com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication");

   public ServiceSwitcher() {
      byte var10000 = 3;

      try {
         String[] var3 = new String[var10000];
         var3[0] = ".minecraft.net";
         var3[1] = ".mojang.com";
         var3[2] = ".thealtening.com";
         String[] var1 = var3;
         this.minecraftSessionServer.updateFieldIfPresent("WHITELISTED_DOMAINS", var1);
      } catch (Throwable var2) {
         LOGGER.warn(var2);
      }

   }

   public AlteningServiceType switchToService(AlteningServiceType var1) {
      try {
         String var2 = var1.getAuthServer();
         FieldAdapter var3 = this.userAuthentication;
         var3.updateFieldIfPresent("BASE_URL", var2);
         var3.updateFieldIfPresent("ROUTE_AUTHENTICATE", new URL(var2 + "authenticate"));
         var3.updateFieldIfPresent("ROUTE_INVALIDATE", new URL(var2 + "invalidate"));
         var3.updateFieldIfPresent("ROUTE_REFRESH", new URL(var2 + "refresh"));
         var3.updateFieldIfPresent("ROUTE_VALIDATE", new URL(var2 + "validate"));
         var3.updateFieldIfPresent("ROUTE_SIGNOUT", new URL(var2 + "signout"));
         String var4 = var1.getSessionServer();
         FieldAdapter var5 = this.minecraftSessionServer;
         var5.updateFieldIfPresent("BASE_URL", var4 + "session/minecraft/");
         var5.updateFieldIfPresent("JOIN_URL", new URL(var4 + "session/minecraft/join"));
         var5.updateFieldIfPresent("CHECK_URL", new URL(var4 + "session/minecraft/hasJoined"));
         return var1;
      } catch (Exception var6) {
         LOGGER.warn(var6);
         return AlteningServiceType.MOJANG;
      }
   }
}

package com.viaversion.viaversion.api.platform;

import com.google.gson.JsonObject;
import com.viaversion.viaversion.api.command.ViaCommandSender;
import com.viaversion.viaversion.api.configuration.ConfigurationProvider;
import com.viaversion.viaversion.api.configuration.ViaVersionConfig;
import java.io.File;
import java.util.UUID;
import java.util.logging.Logger;
import net.NG;
import net.bgR;
import net.cI;
import net.iU;
import us.myles.ViaVersion.api.ViaAPI;

public interface ViaPlatform {
   Logger getLogger();

   String a();

   String j();

   default boolean e() {
      return false;
   }

   String f();

   NG b(Runnable var1);

   NG a(Runnable var1);

   NG a(Runnable var1, Long var2);

   NG b(Runnable var1, Long var2);

   void a(NG var1);

   ViaCommandSender[] getOnlinePlayers();

   void sendMessage(UUID var1, String var2);

   boolean kickPlayer(UUID var1, String var2);

   default boolean a(bgR var1, String var2) {
      boolean var3 = iU.c();
      if(var1.b()) {
         return false;
      } else {
         UUID var4 = ((cI)var1.b(cI.class)).a();
         return false;
      }
   }

   boolean h();

   ViaAPI o();

   ViaVersionConfig getConf();

   ConfigurationProvider getConfigurationProvider();

   File getDataFolder();

   void onReload();

   JsonObject b();

   boolean m();

   iU i();
}

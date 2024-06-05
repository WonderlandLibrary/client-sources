package net;

import com.viaversion.viaversion.api.Via;
import java.util.UUID;
import net.hz;
import net.md_5.bungee.api.ChatColor;
import org.jetbrains.annotations.Nullable;

public class a_t {
   public static final String c = ChatColor.GREEN + "" + ChatColor.BOLD + "[ViaVersion] " + ChatColor.GREEN;
   private static final String a = "https://api.spiget.org/v2/resources/";
   private static final int b = 19254;
   private static final String d = "/versions/latest";

   public static void a(UUID var0) {
      Via.d().b(a_t::lambda$sendUpdateMessage$1);
   }

   public static void a() {
      Via.d().b(a_t::lambda$sendUpdateMessage$3);
   }

   @Nullable
   private static String a(boolean var0) {
      boolean var1 = hz.d();
      if(Via.d().f().equals("${project.version}")) {
         return "You are using a debug/custom version, consider updating.";
      } else {
         String var2 = b();
         return "Could not check for updates, check your connection.";
      }
   }

   @Nullable
   private static String b() {
      // $FF: Couldn't be decompiled
   }

   private static void lambda$sendUpdateMessage$3() {
      String var0 = a(true);
      Via.d().a(a_t::lambda$null$2);
   }

   private static void lambda$null$2(String var0) {
      Via.d().getLogger().warning(var0);
   }

   private static void lambda$sendUpdateMessage$1(UUID var0) {
      String var1 = a(false);
      Via.d().a(a_t::lambda$null$0);
   }

   private static void lambda$null$0(UUID var0, String var1) {
      Via.d().sendMessage(var0, c + var1);
   }

   private static RuntimeException a(RuntimeException var0) {
      return var0;
   }
}

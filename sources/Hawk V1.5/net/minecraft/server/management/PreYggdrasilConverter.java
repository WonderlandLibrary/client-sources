package net.minecraft.server.management;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.mojang.authlib.Agent;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.ProfileLookupCallback;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PreYggdrasilConverter {
   public static final File OLD_IPBAN_FILE = new File("banned-ips.txt");
   public static final File OLD_WHITELIST_FILE = new File("white-list.txt");
   private static final Logger LOGGER = LogManager.getLogger();
   public static final File OLD_OPS_FILE = new File("ops.txt");
   public static final File OLD_PLAYERBAN_FILE = new File("banned-players.txt");
   private static final String __OBFID = "CL_00001882";

   private static void lookupNames(MinecraftServer var0, Collection var1, ProfileLookupCallback var2) {
      String[] var3 = (String[])Iterators.toArray(Iterators.filter(var1.iterator(), new Predicate() {
         private static final String __OBFID = "CL_00001881";

         public boolean apply(Object var1) {
            return this.func_152733_a((String)var1);
         }

         public boolean func_152733_a(String var1) {
            return !StringUtils.isNullOrEmpty(var1);
         }
      }), String.class);
      if (var0.isServerInOnlineMode()) {
         var0.getGameProfileRepository().findProfilesByNames(var3, Agent.MINECRAFT, var2);
      } else {
         String[] var4 = var3;
         int var5 = var3.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            String var7 = var4[var6];
            UUID var8 = EntityPlayer.getUUID(new GameProfile((UUID)null, var7));
            GameProfile var9 = new GameProfile(var8, var7);
            var2.onProfileLookupSucceeded(var9);
         }
      }

   }

   public static String func_152719_a(String var0) {
      if (!StringUtils.isNullOrEmpty(var0) && var0.length() <= 16) {
         MinecraftServer var1 = MinecraftServer.getServer();
         GameProfile var2 = var1.getPlayerProfileCache().getGameProfileForUsername(var0);
         if (var2 != null && var2.getId() != null) {
            return var2.getId().toString();
         } else if (!var1.isSinglePlayer() && var1.isServerInOnlineMode()) {
            ArrayList var3 = Lists.newArrayList();
            ProfileLookupCallback var4 = new ProfileLookupCallback(var1, var3) {
               private final MinecraftServer val$var1;
               private static final String __OBFID = "CL_00001880";
               private final ArrayList val$var3;

               public void onProfileLookupFailed(GameProfile var1, Exception var2) {
                  PreYggdrasilConverter.access$0().warn(String.valueOf((new StringBuilder("Could not lookup user whitelist entry for ")).append(var1.getName())), var2);
               }

               public void onProfileLookupSucceeded(GameProfile var1) {
                  this.val$var1.getPlayerProfileCache().func_152649_a(var1);
                  this.val$var3.add(var1);
               }

               {
                  this.val$var1 = var1;
                  this.val$var3 = var2;
               }
            };
            lookupNames(var1, Lists.newArrayList(new String[]{var0}), var4);
            return var3.size() > 0 && ((GameProfile)var3.get(0)).getId() != null ? ((GameProfile)var3.get(0)).getId().toString() : "";
         } else {
            return EntityPlayer.getUUID(new GameProfile((UUID)null, var0)).toString();
         }
      } else {
         return var0;
      }
   }

   static Logger access$0() {
      return LOGGER;
   }
}

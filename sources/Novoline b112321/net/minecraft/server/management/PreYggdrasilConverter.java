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
import net.atO;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class PreYggdrasilConverter {
   private static final Logger LOGGER = LogManager.getLogger();
   public static final File OLD_IPBAN_FILE = new File("banned-ips.txt");
   public static final File OLD_PLAYERBAN_FILE = new File("banned-players.txt");
   public static final File OLD_OPS_FILE = new File("ops.txt");
   public static final File OLD_WHITELIST_FILE = new File("white-list.txt");

   private static void lookupNames(MinecraftServer var0, Collection var1, ProfileLookupCallback var2) {
      String[] var3 = (String[])Iterators.toArray(Iterators.filter(var1.iterator(), PreYggdrasilConverter::lambda$lookupNames$0), String.class);
      if(var0.isServerInOnlineMode()) {
         var0.getGameProfileRepository().findProfilesByNames(var3, Agent.MINECRAFT, var2);
      } else {
         for(String var7 : var3) {
            UUID var8 = EntityPlayer.getUUID(new GameProfile((UUID)null, var7));
            GameProfile var9 = new GameProfile(var8, var7);
            var2.onProfileLookupSucceeded(var9);
         }
      }

   }

   public static String getStringUUIDFromName(String var0) {
      if(!StringUtils.isNullOrEmpty(var0) && var0.length() <= 16) {
         MinecraftServer var1 = MinecraftServer.getServer();
         GameProfile var2 = var1.getPlayerProfileCache().getGameProfileForUsername(var0);
         if(var2.getId() != null) {
            return var2.getId().toString();
         } else if(!var1.isSinglePlayer() && var1.isServerInOnlineMode()) {
            ArrayList var3 = Lists.newArrayList();
            atO var4 = new atO(var1, var3);
            lookupNames(var1, Lists.newArrayList(new String[]{var0}), var4);
            return !var3.isEmpty() && ((GameProfile)var3.get(0)).getId() != null?((GameProfile)var3.get(0)).getId().toString():"";
         } else {
            return EntityPlayer.getUUID(new GameProfile((UUID)null, var0)).toString();
         }
      } else {
         return var0;
      }
   }

   private static boolean lambda$lookupNames$0(String var0) {
      return !StringUtils.isNullOrEmpty(var0);
   }

   static Logger access$000() {
      return LOGGER;
   }
}

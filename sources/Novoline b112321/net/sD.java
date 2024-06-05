package net;

import com.mojang.authlib.GameProfile;
import java.net.SocketAddress;
import java.util.List;
import java.util.UUID;
import net.acE;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.server.management.BanList;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.server.management.UserListBans;
import net.minecraft.server.management.UserListOps;
import net.minecraft.server.management.UserListWhitelist;
import net.minecraft.stats.StatisticsFile;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.WorldServer;

public class sD {
   private static acE[] b;

   public static BanList b(ServerConfigurationManager var0) {
      return var0.getBannedIPs();
   }

   public static EntityPlayerMP b(ServerConfigurationManager var0, String var1) {
      return var0.getPlayerByUsername(var1);
   }

   public static List a(ServerConfigurationManager var0, String var1) {
      return var0.getPlayersMatchingAddress(var1);
   }

   public static void a(ServerConfigurationManager var0, EntityPlayerMP var1) {
      var0.serverUpdateMountedMovingPlayer(var1);
   }

   public static void b(ServerConfigurationManager var0, EntityPlayerMP var1, WorldServer var2) {
      var0.preparePlayer(var1, var2);
   }

   public static void a(ServerConfigurationManager var0, EntityPlayerMP var1, WorldServer var2) {
      var0.updateTimeAndWeatherForPlayer(var1, var2);
   }

   public static void c(ServerConfigurationManager var0, EntityPlayerMP var1) {
      var0.syncPlayerInventory(var1);
   }

   public static void a(ServerConfigurationManager var0, IChatComponent var1) {
      var0.sendChatMsg(var1);
   }

   public static void b(ServerConfigurationManager var0, EntityPlayerMP var1) {
      var0.playerLoggedOut(var1);
   }

   public static void a(ServerConfigurationManager var0, IChatComponent var1, boolean var2) {
      var0.sendChatMsgImpl(var1, var2);
   }

   public static boolean a(ServerConfigurationManager var0, GameProfile var1) {
      return var0.canSendCommands(var1);
   }

   public static EntityPlayerMP a(ServerConfigurationManager var0, EntityPlayerMP var1, int var2, boolean var3) {
      return var0.recreatePlayerEntity(var1, var2, var3);
   }

   public static UserListBans e(ServerConfigurationManager var0) {
      return var0.getBannedPlayers();
   }

   public static void a(ServerConfigurationManager var0, double var1, double var3, double var5, double var7, int var9, Packet var10) {
      var0.sendToAllNear(var1, var3, var5, var7, var9, var10);
   }

   public static void a(ServerConfigurationManager var0, EntityPlayer var1, double var2, double var4, double var6, double var8, int var10, Packet var11) {
      acE[] var12 = b();
      var0.sendToAllNearExcept(var1, var2, var4, var6, var8, var10, var11);
      if(acE.b() == null) {
         b(new acE[3]);
      }

   }

   public static void a(ServerConfigurationManager var0, Packet var1) {
      var0.sendPacketToAllPlayers(var1);
   }

   public static List a(ServerConfigurationManager var0) {
      return var0.func_181057_v();
   }

   public static void c(ServerConfigurationManager var0, boolean var1) {
      var0.setWhiteListEnabled(var1);
   }

   public static String[] k(ServerConfigurationManager var0) {
      return var0.getWhitelistedPlayerNames();
   }

   public static String[] p(ServerConfigurationManager var0) {
      return var0.getAvailablePlayerDat();
   }

   public static void b(ServerConfigurationManager var0, GameProfile var1) {
      var0.addWhitelistedPlayer(var1);
   }

   public static UserListWhitelist o(ServerConfigurationManager var0) {
      return var0.getWhitelistedPlayers();
   }

   public static void d(ServerConfigurationManager var0, GameProfile var1) {
      var0.removePlayerFromWhitelist(var1);
   }

   public static void n(ServerConfigurationManager var0) {
      var0.loadWhiteList();
   }

   public static EntityPlayerMP a(ServerConfigurationManager var0, UUID var1) {
      return var0.getPlayerByUUID(var1);
   }

   public static void e(ServerConfigurationManager var0, GameProfile var1) {
      var0.addOp(var1);
   }

   public static String a(ServerConfigurationManager var0, boolean var1) {
      return var0.func_181058_b(var1);
   }

   public static NBTTagCompound i(ServerConfigurationManager var0) {
      return var0.getHostPlayerData();
   }

   public static void a(ServerConfigurationManager var0, Packet var1, int var2) {
      var0.sendPacketToAllPlayersInDimension(var1, var2);
   }

   public static int j(ServerConfigurationManager var0) {
      return var0.getViewDistance();
   }

   public static int m(ServerConfigurationManager var0) {
      return var0.getEntityViewDistance();
   }

   public static void a(ServerConfigurationManager var0, Entity var1, int var2, WorldServer var3, WorldServer var4) {
      var0.transferEntityToWorld(var1, var2, var3, var4);
   }

   public static void a(ServerConfigurationManager var0, WorldServer[] var1) {
      var0.setPlayerManager(var1);
   }

   public static void h(ServerConfigurationManager var0) {
      var0.saveAllPlayerData();
   }

   public static void a(ServerConfigurationManager var0, int var1) {
      var0.setViewDistance(var1);
   }

   public static void b(ServerConfigurationManager var0, boolean var1) {
      var0.setCommandsAllowedForAll(var1);
   }

   public static void d(ServerConfigurationManager var0) {
      var0.removeAllPlayers();
   }

   public static void l(ServerConfigurationManager var0) {
      var0.onTick();
   }

   public static GameProfile[] f(ServerConfigurationManager var0) {
      return var0.getAllProfiles();
   }

   public static void a(ServerConfigurationManager var0, NetworkManager var1, EntityPlayerMP var2) {
      var0.initializeConnectionToPlayer(var1, var2);
   }

   public static String a(ServerConfigurationManager var0, SocketAddress var1, GameProfile var2) {
      return var0.allowUserToConnect(var1, var2);
   }

   public static EntityPlayerMP c(ServerConfigurationManager var0, GameProfile var1) {
      return var0.createPlayerForUser(var1);
   }

   public static StatisticsFile a(ServerConfigurationManager var0, EntityPlayer var1) {
      return var0.getPlayerStatsFile(var1);
   }

   public static void a(ServerConfigurationManager var0, EntityPlayer var1, IChatComponent var2) {
      var0.sendMessageToAllTeamMembers(var1, var2);
   }

   public static void b(ServerConfigurationManager var0, EntityPlayer var1, IChatComponent var2) {
      var0.a(var1, var2);
   }

   public static void a(ServerConfigurationManager var0, EntityPlayerMP var1, int var2) {
      var0.transferPlayerToDimension(var1, var2);
   }

   public static UserListOps c(ServerConfigurationManager var0) {
      return var0.getOppedPlayers();
   }

   public static void f(ServerConfigurationManager var0, GameProfile var1) {
      var0.removeOp(var1);
   }

   public static String[] g(ServerConfigurationManager var0) {
      return var0.getOppedPlayerNames();
   }

   public static void b(acE[] var0) {
      b = var0;
   }

   public static acE[] b() {
      return b;
   }

   static {
      if(b() == null) {
         b(new acE[1]);
      }

   }
}

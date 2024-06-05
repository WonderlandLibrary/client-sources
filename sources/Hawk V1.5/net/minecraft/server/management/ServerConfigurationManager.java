package net.minecraft.server.management;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.authlib.GameProfile;
import io.netty.buffer.Unpooled;
import java.io.File;
import java.net.SocketAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.S01PacketJoinGame;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S03PacketTimeUpdate;
import net.minecraft.network.play.server.S05PacketSpawnPosition;
import net.minecraft.network.play.server.S07PacketRespawn;
import net.minecraft.network.play.server.S09PacketHeldItemChange;
import net.minecraft.network.play.server.S1DPacketEntityEffect;
import net.minecraft.network.play.server.S1FPacketSetExperience;
import net.minecraft.network.play.server.S2BPacketChangeGameState;
import net.minecraft.network.play.server.S38PacketPlayerListItem;
import net.minecraft.network.play.server.S39PacketPlayerAbilities;
import net.minecraft.network.play.server.S3EPacketTeams;
import net.minecraft.network.play.server.S3FPacketCustomPayload;
import net.minecraft.network.play.server.S41PacketServerDifficulty;
import net.minecraft.network.play.server.S44PacketWorldBorder;
import net.minecraft.potion.PotionEffect;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.ServerScoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.MinecraftServer;
import net.minecraft.stats.StatList;
import net.minecraft.stats.StatisticsFile;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.border.IBorderListener;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.demo.DemoWorldManager;
import net.minecraft.world.storage.IPlayerFileData;
import net.minecraft.world.storage.WorldInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class ServerConfigurationManager {
   private int viewDistance;
   private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
   private WorldSettings.GameType gameType;
   public static final File FILE_WHITELIST = new File("whitelist.json");
   private final BanList bannedIPs;
   public static final File FILE_IPBANS = new File("banned-ips.json");
   private final Map playerStatFiles;
   public static final File FILE_PLAYERBANS = new File("banned-players.json");
   private final MinecraftServer mcServer;
   private IPlayerFileData playerNBTManagerObj;
   public final Map field_177454_f = Maps.newHashMap();
   private int playerPingIndex;
   private static final String __OBFID = "CL_00001423";
   private final UserListBans bannedPlayers;
   private boolean whiteListEnforced;
   private static final Logger logger = LogManager.getLogger();
   public static final File FILE_OPS = new File("ops.json");
   private boolean commandsAllowedForAll;
   protected int maxPlayers;
   private final UserListOps ops;
   private final UserListWhitelist whiteListedPlayers;
   public final List playerEntityList = Lists.newArrayList();

   public MinecraftServer getServerInstance() {
      return this.mcServer;
   }

   public void setViewDistance(int var1) {
      this.viewDistance = var1;
      if (this.mcServer.worldServers != null) {
         WorldServer[] var2 = this.mcServer.worldServers;
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            WorldServer var5 = var2[var4];
            if (var5 != null) {
               var5.getPlayerManager().func_152622_a(var1);
            }
         }
      }

   }

   public void func_177453_a(EntityPlayer var1, IChatComponent var2) {
      Team var3 = var1.getTeam();
      if (var3 != null) {
         Collection var4 = var3.getMembershipCollection();
         Iterator var5 = var4.iterator();

         while(var5.hasNext()) {
            String var6 = (String)var5.next();
            EntityPlayerMP var7 = this.getPlayerByUsername(var6);
            if (var7 != null && var7 != var1) {
               var7.addChatMessage(var2);
            }
         }
      }

   }

   public void sendChatMsg(IChatComponent var1) {
      this.sendChatMsgImpl(var1, true);
   }

   public List getPlayersMatchingAddress(String var1) {
      ArrayList var2 = Lists.newArrayList();
      Iterator var3 = this.playerEntityList.iterator();

      while(var3.hasNext()) {
         EntityPlayerMP var4 = (EntityPlayerMP)var3.next();
         if (var4.getPlayerIP().equals(var1)) {
            var2.add(var4);
         }
      }

      return var2;
   }

   public void addWhitelistedPlayer(GameProfile var1) {
      this.whiteListedPlayers.addEntry(new UserListWhitelistEntry(var1));
   }

   public void sendChatMsgImpl(IChatComponent var1, boolean var2) {
      this.mcServer.addChatMessage(var1);
      int var3 = var2 ? 1 : 0;
      this.sendPacketToAllPlayers(new S02PacketChat(var1, (byte)var3));
   }

   public void sendToAllNearExcept(EntityPlayer var1, double var2, double var4, double var6, double var8, int var10, Packet var11) {
      for(int var12 = 0; var12 < this.playerEntityList.size(); ++var12) {
         EntityPlayerMP var13 = (EntityPlayerMP)this.playerEntityList.get(var12);
         if (var13 != var1 && var13.dimension == var10) {
            double var14 = var2 - var13.posX;
            double var16 = var4 - var13.posY;
            double var18 = var6 - var13.posZ;
            if (var14 * var14 + var16 * var16 + var18 * var18 < var8 * var8) {
               var13.playerNetServerHandler.sendPacket(var11);
            }
         }
      }

   }

   public String[] getOppedPlayerNames() {
      return this.ops.getKeys();
   }

   public void onTick() {
      if (++this.playerPingIndex > 600) {
         this.sendPacketToAllPlayers(new S38PacketPlayerListItem(S38PacketPlayerListItem.Action.UPDATE_LATENCY, this.playerEntityList));
         this.playerPingIndex = 0;
      }

   }

   public void loadWhiteList() {
   }

   public BanList getBannedIPs() {
      return this.bannedIPs;
   }

   public String[] getAllUsernames() {
      String[] var1 = new String[this.playerEntityList.size()];

      for(int var2 = 0; var2 < this.playerEntityList.size(); ++var2) {
         var1[var2] = ((EntityPlayerMP)this.playerEntityList.get(var2)).getName();
      }

      return var1;
   }

   public void updateTimeAndWeatherForPlayer(EntityPlayerMP var1, WorldServer var2) {
      WorldBorder var3 = this.mcServer.worldServers[0].getWorldBorder();
      var1.playerNetServerHandler.sendPacket(new S44PacketWorldBorder(var3, S44PacketWorldBorder.Action.INITIALIZE));
      var1.playerNetServerHandler.sendPacket(new S03PacketTimeUpdate(var2.getTotalWorldTime(), var2.getWorldTime(), var2.getGameRules().getGameRuleBooleanValue("doDaylightCycle")));
      if (var2.isRaining()) {
         var1.playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(1, 0.0F));
         var1.playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(7, var2.getRainStrength(1.0F)));
         var1.playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(8, var2.getWeightedThunderStrength(1.0F)));
      }

   }

   public EntityPlayerMP createPlayerForUser(GameProfile var1) {
      UUID var2 = EntityPlayer.getUUID(var1);
      ArrayList var3 = Lists.newArrayList();

      EntityPlayerMP var4;
      for(int var5 = 0; var5 < this.playerEntityList.size(); ++var5) {
         var4 = (EntityPlayerMP)this.playerEntityList.get(var5);
         if (var4.getUniqueID().equals(var2)) {
            var3.add(var4);
         }
      }

      Iterator var7 = var3.iterator();

      while(var7.hasNext()) {
         var4 = (EntityPlayerMP)var7.next();
         var4.playerNetServerHandler.kickPlayerFromServer("You logged in from another location");
      }

      Object var6;
      if (this.mcServer.isDemo()) {
         var6 = new DemoWorldManager(this.mcServer.worldServerForDimension(0));
      } else {
         var6 = new ItemInWorldManager(this.mcServer.worldServerForDimension(0));
      }

      return new EntityPlayerMP(this.mcServer, this.mcServer.worldServerForDimension(0), var1, (ItemInWorldManager)var6);
   }

   public int getEntityViewDistance() {
      return PlayerManager.getFurthestViewableBlock(this.getViewDistance());
   }

   public GameProfile[] getAllProfiles() {
      GameProfile[] var1 = new GameProfile[this.playerEntityList.size()];

      for(int var2 = 0; var2 < this.playerEntityList.size(); ++var2) {
         var1[var2] = ((EntityPlayerMP)this.playerEntityList.get(var2)).getGameProfile();
      }

      return var1;
   }

   public UserListBans getBannedPlayers() {
      return this.bannedPlayers;
   }

   public void initializeConnectionToPlayer(NetworkManager var1, EntityPlayerMP var2) {
      GameProfile var3 = var2.getGameProfile();
      PlayerProfileCache var4 = this.mcServer.getPlayerProfileCache();
      GameProfile var5 = var4.func_152652_a(var3.getId());
      String var6 = var5 == null ? var3.getName() : var5.getName();
      var4.func_152649_a(var3);
      NBTTagCompound var7 = this.readPlayerDataFromFile(var2);
      var2.setWorld(this.mcServer.worldServerForDimension(var2.dimension));
      var2.theItemInWorldManager.setWorld((WorldServer)var2.worldObj);
      String var8 = "local";
      if (var1.getRemoteAddress() != null) {
         var8 = var1.getRemoteAddress().toString();
      }

      logger.info(String.valueOf((new StringBuilder(String.valueOf(var2.getName()))).append("[").append(var8).append("] logged in with entity id ").append(var2.getEntityId()).append(" at (").append(var2.posX).append(", ").append(var2.posY).append(", ").append(var2.posZ).append(")")));
      WorldServer var9 = this.mcServer.worldServerForDimension(var2.dimension);
      WorldInfo var10 = var9.getWorldInfo();
      BlockPos var11 = var9.getSpawnPoint();
      this.func_72381_a(var2, (EntityPlayerMP)null, var9);
      NetHandlerPlayServer var12 = new NetHandlerPlayServer(this.mcServer, var1, var2);
      var12.sendPacket(new S01PacketJoinGame(var2.getEntityId(), var2.theItemInWorldManager.getGameType(), var10.isHardcoreModeEnabled(), var9.provider.getDimensionId(), var9.getDifficulty(), this.getMaxPlayers(), var10.getTerrainType(), var9.getGameRules().getGameRuleBooleanValue("reducedDebugInfo")));
      var12.sendPacket(new S3FPacketCustomPayload("MC|Brand", (new PacketBuffer(Unpooled.buffer())).writeString(this.getServerInstance().getServerModName())));
      var12.sendPacket(new S41PacketServerDifficulty(var10.getDifficulty(), var10.isDifficultyLocked()));
      var12.sendPacket(new S05PacketSpawnPosition(var11));
      var12.sendPacket(new S39PacketPlayerAbilities(var2.capabilities));
      var12.sendPacket(new S09PacketHeldItemChange(var2.inventory.currentItem));
      var2.getStatFile().func_150877_d();
      var2.getStatFile().func_150884_b(var2);
      this.func_96456_a((ServerScoreboard)var9.getScoreboard(), var2);
      this.mcServer.refreshStatusNextTick();
      ChatComponentTranslation var13;
      if (!var2.getName().equalsIgnoreCase(var6)) {
         var13 = new ChatComponentTranslation("multiplayer.player.joined.renamed", new Object[]{var2.getDisplayName(), var6});
      } else {
         var13 = new ChatComponentTranslation("multiplayer.player.joined", new Object[]{var2.getDisplayName()});
      }

      var13.getChatStyle().setColor(EnumChatFormatting.YELLOW);
      this.sendChatMsg(var13);
      this.playerLoggedIn(var2);
      var12.setPlayerLocation(var2.posX, var2.posY, var2.posZ, var2.rotationYaw, var2.rotationPitch);
      this.updateTimeAndWeatherForPlayer(var2, var9);
      if (this.mcServer.getResourcePackUrl().length() > 0) {
         var2.func_175397_a(this.mcServer.getResourcePackUrl(), this.mcServer.getResourcePackHash());
      }

      Iterator var14 = var2.getActivePotionEffects().iterator();

      while(var14.hasNext()) {
         PotionEffect var15 = (PotionEffect)var14.next();
         var12.sendPacket(new S1DPacketEntityEffect(var2.getEntityId(), var15));
      }

      var2.addSelfToInternalCraftingInventory();
      if (var7 != null && var7.hasKey("Riding", 10)) {
         Entity var16 = EntityList.createEntityFromNBT(var7.getCompoundTag("Riding"), var9);
         if (var16 != null) {
            var16.forceSpawn = true;
            var9.spawnEntityInWorld(var16);
            var2.mountEntity(var16);
            var16.forceSpawn = false;
         }
      }

   }

   public boolean canSendCommands(GameProfile var1) {
      return this.ops.hasEntry(var1) || this.mcServer.isSinglePlayer() && this.mcServer.worldServers[0].getWorldInfo().areCommandsAllowed() && this.mcServer.getServerOwner().equalsIgnoreCase(var1.getName()) || this.commandsAllowedForAll;
   }

   public int getCurrentPlayerCount() {
      return this.playerEntityList.size();
   }

   public void setCommandsAllowedForAll(boolean var1) {
      this.commandsAllowedForAll = var1;
   }

   public void playerLoggedIn(EntityPlayerMP var1) {
      this.playerEntityList.add(var1);
      this.field_177454_f.put(var1.getUniqueID(), var1);
      this.sendPacketToAllPlayers(new S38PacketPlayerListItem(S38PacketPlayerListItem.Action.ADD_PLAYER, new EntityPlayerMP[]{var1}));
      WorldServer var2 = this.mcServer.worldServerForDimension(var1.dimension);
      var2.spawnEntityInWorld(var1);
      this.func_72375_a(var1, (WorldServer)null);

      for(int var3 = 0; var3 < this.playerEntityList.size(); ++var3) {
         EntityPlayerMP var4 = (EntityPlayerMP)this.playerEntityList.get(var3);
         var1.playerNetServerHandler.sendPacket(new S38PacketPlayerListItem(S38PacketPlayerListItem.Action.ADD_PLAYER, new EntityPlayerMP[]{var4}));
      }

   }

   public void removePlayerFromWhitelist(GameProfile var1) {
      this.whiteListedPlayers.removeEntry(var1);
   }

   public UserListOps getOppedPlayers() {
      return this.ops;
   }

   public void func_177452_b(EntityPlayer var1, IChatComponent var2) {
      Team var3 = var1.getTeam();
      if (var3 == null) {
         this.sendChatMsg(var2);
      } else {
         for(int var4 = 0; var4 < this.playerEntityList.size(); ++var4) {
            EntityPlayerMP var5 = (EntityPlayerMP)this.playerEntityList.get(var4);
            if (var5.getTeam() != var3) {
               var5.addChatMessage(var2);
            }
         }
      }

   }

   public String[] getWhitelistedPlayerNames() {
      return this.whiteListedPlayers.getKeys();
   }

   public String[] getAvailablePlayerDat() {
      return this.mcServer.worldServers[0].getSaveHandler().getPlayerNBTManager().getAvailablePlayerDat();
   }

   public void func_152604_a(WorldSettings.GameType var1) {
      this.gameType = var1;
   }

   public void setPlayerManager(WorldServer[] var1) {
      this.playerNBTManagerObj = var1[0].getSaveHandler().getPlayerNBTManager();
      var1[0].getWorldBorder().addListener(new IBorderListener(this) {
         final ServerConfigurationManager this$0;
         private static final String __OBFID = "CL_00002267";

         {
            this.this$0 = var1;
         }

         public void onWarningDistanceChanged(WorldBorder var1, int var2) {
            this.this$0.sendPacketToAllPlayers(new S44PacketWorldBorder(var1, S44PacketWorldBorder.Action.SET_WARNING_BLOCKS));
         }

         public void func_177692_a(WorldBorder var1, double var2, double var4, long var6) {
            this.this$0.sendPacketToAllPlayers(new S44PacketWorldBorder(var1, S44PacketWorldBorder.Action.LERP_SIZE));
         }

         public void func_177695_c(WorldBorder var1, double var2) {
         }

         public void onSizeChanged(WorldBorder var1, double var2) {
            this.this$0.sendPacketToAllPlayers(new S44PacketWorldBorder(var1, S44PacketWorldBorder.Action.SET_SIZE));
         }

         public void onWarningTimeChanged(WorldBorder var1, int var2) {
            this.this$0.sendPacketToAllPlayers(new S44PacketWorldBorder(var1, S44PacketWorldBorder.Action.SET_WARNING_TIME));
         }

         public void onCenterChanged(WorldBorder var1, double var2, double var4) {
            this.this$0.sendPacketToAllPlayers(new S44PacketWorldBorder(var1, S44PacketWorldBorder.Action.SET_CENTER));
         }

         public void func_177696_b(WorldBorder var1, double var2) {
         }
      });
   }

   public int getMaxPlayers() {
      return this.maxPlayers;
   }

   public UserListWhitelist getWhitelistedPlayers() {
      return this.whiteListedPlayers;
   }

   public void transferPlayerToDimension(EntityPlayerMP var1, int var2) {
      int var3 = var1.dimension;
      WorldServer var4 = this.mcServer.worldServerForDimension(var1.dimension);
      var1.dimension = var2;
      WorldServer var5 = this.mcServer.worldServerForDimension(var1.dimension);
      var1.playerNetServerHandler.sendPacket(new S07PacketRespawn(var1.dimension, var1.worldObj.getDifficulty(), var1.worldObj.getWorldInfo().getTerrainType(), var1.theItemInWorldManager.getGameType()));
      var4.removePlayerEntityDangerously(var1);
      var1.isDead = false;
      this.transferEntityToWorld(var1, var3, var4, var5);
      this.func_72375_a(var1, var4);
      var1.playerNetServerHandler.setPlayerLocation(var1.posX, var1.posY, var1.posZ, var1.rotationYaw, var1.rotationPitch);
      var1.theItemInWorldManager.setWorld(var5);
      this.updateTimeAndWeatherForPlayer(var1, var5);
      this.syncPlayerInventory(var1);
      Iterator var6 = var1.getActivePotionEffects().iterator();

      while(var6.hasNext()) {
         PotionEffect var7 = (PotionEffect)var6.next();
         var1.playerNetServerHandler.sendPacket(new S1DPacketEntityEffect(var1.getEntityId(), var7));
      }

   }

   public void sendPacketToAllPlayers(Packet var1) {
      for(int var2 = 0; var2 < this.playerEntityList.size(); ++var2) {
         ((EntityPlayerMP)this.playerEntityList.get(var2)).playerNetServerHandler.sendPacket(var1);
      }

   }

   public void setWhiteListEnabled(boolean var1) {
      this.whiteListEnforced = var1;
   }

   public NBTTagCompound getHostPlayerData() {
      return null;
   }

   protected void writePlayerData(EntityPlayerMP var1) {
      this.playerNBTManagerObj.writePlayerData(var1);
      StatisticsFile var2 = (StatisticsFile)this.playerStatFiles.get(var1.getUniqueID());
      if (var2 != null) {
         var2.func_150883_b();
      }

   }

   public void syncPlayerInventory(EntityPlayerMP var1) {
      var1.sendContainerToPlayer(var1.inventoryContainer);
      var1.setPlayerHealthUpdated();
      var1.playerNetServerHandler.sendPacket(new S09PacketHeldItemChange(var1.inventory.currentItem));
   }

   public void serverUpdateMountedMovingPlayer(EntityPlayerMP var1) {
      var1.getServerForPlayer().getPlayerManager().updateMountedMovingPlayer(var1);
   }

   public EntityPlayerMP func_177451_a(UUID var1) {
      return (EntityPlayerMP)this.field_177454_f.get(var1);
   }

   public void playerLoggedOut(EntityPlayerMP var1) {
      var1.triggerAchievement(StatList.leaveGameStat);
      this.writePlayerData(var1);
      WorldServer var2 = var1.getServerForPlayer();
      if (var1.ridingEntity != null) {
         var2.removePlayerEntityDangerously(var1.ridingEntity);
         logger.debug("removing player mount");
      }

      var2.removeEntity(var1);
      var2.getPlayerManager().removePlayer(var1);
      this.playerEntityList.remove(var1);
      this.field_177454_f.remove(var1.getUniqueID());
      this.playerStatFiles.remove(var1.getUniqueID());
      this.sendPacketToAllPlayers(new S38PacketPlayerListItem(S38PacketPlayerListItem.Action.REMOVE_PLAYER, new EntityPlayerMP[]{var1}));
   }

   public NBTTagCompound readPlayerDataFromFile(EntityPlayerMP var1) {
      NBTTagCompound var2 = this.mcServer.worldServers[0].getWorldInfo().getPlayerNBTTagCompound();
      NBTTagCompound var3;
      if (var1.getName().equals(this.mcServer.getServerOwner()) && var2 != null) {
         var1.readFromNBT(var2);
         var3 = var2;
         logger.debug("loading single player");
      } else {
         var3 = this.playerNBTManagerObj.readPlayerData(var1);
      }

      return var3;
   }

   public StatisticsFile getPlayerStatsFile(EntityPlayer var1) {
      UUID var2 = var1.getUniqueID();
      StatisticsFile var3 = var2 == null ? null : (StatisticsFile)this.playerStatFiles.get(var2);
      if (var3 == null) {
         File var4 = new File(this.mcServer.worldServerForDimension(0).getSaveHandler().getWorldDirectory(), "stats");
         File var5 = new File(var4, String.valueOf((new StringBuilder(String.valueOf(var2.toString()))).append(".json")));
         if (!var5.exists()) {
            File var6 = new File(var4, String.valueOf((new StringBuilder(String.valueOf(var1.getName()))).append(".json")));
            if (var6.exists() && var6.isFile()) {
               var6.renameTo(var5);
            }
         }

         var3 = new StatisticsFile(this.mcServer, var5);
         var3.func_150882_a();
         this.playerStatFiles.put(var2, var3);
      }

      return var3;
   }

   public void addOp(GameProfile var1) {
      this.ops.addEntry(new UserListOpsEntry(var1, this.mcServer.getOpPermissionLevel()));
   }

   public void removeAllPlayers() {
      for(int var1 = 0; var1 < this.playerEntityList.size(); ++var1) {
         ((EntityPlayerMP)this.playerEntityList.get(var1)).playerNetServerHandler.kickPlayerFromServer("Server closed");
      }

   }

   public String func_180602_f() {
      String var1 = "";

      for(int var2 = 0; var2 < this.playerEntityList.size(); ++var2) {
         if (var2 > 0) {
            var1 = String.valueOf((new StringBuilder(String.valueOf(var1))).append(", "));
         }

         var1 = String.valueOf((new StringBuilder(String.valueOf(var1))).append(((EntityPlayerMP)this.playerEntityList.get(var2)).getName()));
      }

      return var1;
   }

   protected void func_96456_a(ServerScoreboard var1, EntityPlayerMP var2) {
      HashSet var3 = Sets.newHashSet();
      Iterator var4 = var1.getTeams().iterator();

      while(var4.hasNext()) {
         ScorePlayerTeam var5 = (ScorePlayerTeam)var4.next();
         var2.playerNetServerHandler.sendPacket(new S3EPacketTeams(var5, 0));
      }

      for(int var10 = 0; var10 < 19; ++var10) {
         ScoreObjective var6 = var1.getObjectiveInDisplaySlot(var10);
         if (var6 != null && !var3.contains(var6)) {
            List var7 = var1.func_96550_d(var6);
            Iterator var8 = var7.iterator();

            while(var8.hasNext()) {
               Packet var9 = (Packet)var8.next();
               var2.playerNetServerHandler.sendPacket(var9);
            }

            var3.add(var6);
         }
      }

   }

   public void saveAllPlayerData() {
      for(int var1 = 0; var1 < this.playerEntityList.size(); ++var1) {
         this.writePlayerData((EntityPlayerMP)this.playerEntityList.get(var1));
      }

   }

   public ServerConfigurationManager(MinecraftServer var1) {
      this.bannedPlayers = new UserListBans(FILE_PLAYERBANS);
      this.bannedIPs = new BanList(FILE_IPBANS);
      this.ops = new UserListOps(FILE_OPS);
      this.whiteListedPlayers = new UserListWhitelist(FILE_WHITELIST);
      this.playerStatFiles = Maps.newHashMap();
      this.mcServer = var1;
      this.bannedPlayers.setLanServer(false);
      this.bannedIPs.setLanServer(false);
      this.maxPlayers = 8;
   }

   public void func_72375_a(EntityPlayerMP var1, WorldServer var2) {
      WorldServer var3 = var1.getServerForPlayer();
      if (var2 != null) {
         var2.getPlayerManager().removePlayer(var1);
      }

      var3.getPlayerManager().addPlayer(var1);
      var3.theChunkProviderServer.loadChunk((int)var1.posX >> 4, (int)var1.posZ >> 4);
   }

   public EntityPlayerMP recreatePlayerEntity(EntityPlayerMP var1, int var2, boolean var3) {
      var1.getServerForPlayer().getEntityTracker().removePlayerFromTrackers(var1);
      var1.getServerForPlayer().getEntityTracker().untrackEntity(var1);
      var1.getServerForPlayer().getPlayerManager().removePlayer(var1);
      this.playerEntityList.remove(var1);
      this.mcServer.worldServerForDimension(var1.dimension).removePlayerEntityDangerously(var1);
      BlockPos var4 = var1.func_180470_cg();
      boolean var5 = var1.isSpawnForced();
      var1.dimension = var2;
      Object var6;
      if (this.mcServer.isDemo()) {
         var6 = new DemoWorldManager(this.mcServer.worldServerForDimension(var1.dimension));
      } else {
         var6 = new ItemInWorldManager(this.mcServer.worldServerForDimension(var1.dimension));
      }

      EntityPlayerMP var7 = new EntityPlayerMP(this.mcServer, this.mcServer.worldServerForDimension(var1.dimension), var1.getGameProfile(), (ItemInWorldManager)var6);
      var7.playerNetServerHandler = var1.playerNetServerHandler;
      var7.clonePlayer(var1, var3);
      var7.setEntityId(var1.getEntityId());
      var7.func_174817_o(var1);
      WorldServer var8 = this.mcServer.worldServerForDimension(var1.dimension);
      this.func_72381_a(var7, var1, var8);
      BlockPos var9;
      if (var4 != null) {
         var9 = EntityPlayer.func_180467_a(this.mcServer.worldServerForDimension(var1.dimension), var4, var5);
         if (var9 != null) {
            var7.setLocationAndAngles((double)((float)var9.getX() + 0.5F), (double)((float)var9.getY() + 0.1F), (double)((float)var9.getZ() + 0.5F), 0.0F, 0.0F);
            var7.func_180473_a(var4, var5);
         } else {
            var7.playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(0, 0.0F));
         }
      }

      var8.theChunkProviderServer.loadChunk((int)var7.posX >> 4, (int)var7.posZ >> 4);

      while(!var8.getCollidingBoundingBoxes(var7, var7.getEntityBoundingBox()).isEmpty() && var7.posY < 256.0D) {
         var7.setPosition(var7.posX, var7.posY + 1.0D, var7.posZ);
      }

      var7.playerNetServerHandler.sendPacket(new S07PacketRespawn(var7.dimension, var7.worldObj.getDifficulty(), var7.worldObj.getWorldInfo().getTerrainType(), var7.theItemInWorldManager.getGameType()));
      var9 = var8.getSpawnPoint();
      var7.playerNetServerHandler.setPlayerLocation(var7.posX, var7.posY, var7.posZ, var7.rotationYaw, var7.rotationPitch);
      var7.playerNetServerHandler.sendPacket(new S05PacketSpawnPosition(var9));
      var7.playerNetServerHandler.sendPacket(new S1FPacketSetExperience(var7.experience, var7.experienceTotal, var7.experienceLevel));
      this.updateTimeAndWeatherForPlayer(var7, var8);
      var8.getPlayerManager().addPlayer(var7);
      var8.spawnEntityInWorld(var7);
      this.playerEntityList.add(var7);
      this.field_177454_f.put(var7.getUniqueID(), var7);
      var7.addSelfToInternalCraftingInventory();
      var7.setHealth(var7.getHealth());
      return var7;
   }

   public void sendToAllNear(double var1, double var3, double var5, double var7, int var9, Packet var10) {
      this.sendToAllNearExcept((EntityPlayer)null, var1, var3, var5, var7, var9, var10);
   }

   public EntityPlayerMP getPlayerByUsername(String var1) {
      Iterator var2 = this.playerEntityList.iterator();

      while(var2.hasNext()) {
         EntityPlayerMP var3 = (EntityPlayerMP)var2.next();
         if (var3.getName().equalsIgnoreCase(var1)) {
            return var3;
         }
      }

      return null;
   }

   public String allowUserToConnect(SocketAddress var1, GameProfile var2) {
      String var3;
      if (this.bannedPlayers.isBanned(var2)) {
         UserListBansEntry var5 = (UserListBansEntry)this.bannedPlayers.getEntry(var2);
         var3 = String.valueOf((new StringBuilder("You are banned from this server!\nReason: ")).append(var5.getBanReason()));
         if (var5.getBanEndDate() != null) {
            var3 = String.valueOf((new StringBuilder(String.valueOf(var3))).append("\nYour ban will be removed on ").append(dateFormat.format(var5.getBanEndDate())));
         }

         return var3;
      } else if (!this.canJoin(var2)) {
         return "You are not white-listed on this server!";
      } else if (this.bannedIPs.isBanned(var1)) {
         IPBanEntry var4 = this.bannedIPs.getBanEntry(var1);
         var3 = String.valueOf((new StringBuilder("Your IP address is banned from this server!\nReason: ")).append(var4.getBanReason()));
         if (var4.getBanEndDate() != null) {
            var3 = String.valueOf((new StringBuilder(String.valueOf(var3))).append("\nYour ban will be removed on ").append(dateFormat.format(var4.getBanEndDate())));
         }

         return var3;
      } else {
         return this.playerEntityList.size() >= this.maxPlayers ? "The server is full!" : null;
      }
   }

   private void func_72381_a(EntityPlayerMP var1, EntityPlayerMP var2, World var3) {
      if (var2 != null) {
         var1.theItemInWorldManager.setGameType(var2.theItemInWorldManager.getGameType());
      } else if (this.gameType != null) {
         var1.theItemInWorldManager.setGameType(this.gameType);
      }

      var1.theItemInWorldManager.initializeGameType(var3.getWorldInfo().getGameType());
   }

   public boolean canJoin(GameProfile var1) {
      return !this.whiteListEnforced || this.ops.hasEntry(var1) || this.whiteListedPlayers.hasEntry(var1);
   }

   public void transferEntityToWorld(Entity var1, int var2, WorldServer var3, WorldServer var4) {
      double var5 = var1.posX;
      double var7 = var1.posZ;
      double var9 = 8.0D;
      float var11 = var1.rotationYaw;
      var3.theProfiler.startSection("moving");
      if (var1.dimension == -1) {
         var5 = MathHelper.clamp_double(var5 / var9, var4.getWorldBorder().minX() + 16.0D, var4.getWorldBorder().maxX() - 16.0D);
         var7 = MathHelper.clamp_double(var7 / var9, var4.getWorldBorder().minZ() + 16.0D, var4.getWorldBorder().maxZ() - 16.0D);
         var1.setLocationAndAngles(var5, var1.posY, var7, var1.rotationYaw, var1.rotationPitch);
         if (var1.isEntityAlive()) {
            var3.updateEntityWithOptionalForce(var1, false);
         }
      } else if (var1.dimension == 0) {
         var5 = MathHelper.clamp_double(var5 * var9, var4.getWorldBorder().minX() + 16.0D, var4.getWorldBorder().maxX() - 16.0D);
         var7 = MathHelper.clamp_double(var7 * var9, var4.getWorldBorder().minZ() + 16.0D, var4.getWorldBorder().maxZ() - 16.0D);
         var1.setLocationAndAngles(var5, var1.posY, var7, var1.rotationYaw, var1.rotationPitch);
         if (var1.isEntityAlive()) {
            var3.updateEntityWithOptionalForce(var1, false);
         }
      } else {
         BlockPos var12;
         if (var2 == 1) {
            var12 = var4.getSpawnPoint();
         } else {
            var12 = var4.func_180504_m();
         }

         var5 = (double)var12.getX();
         var1.posY = (double)var12.getY();
         var7 = (double)var12.getZ();
         var1.setLocationAndAngles(var5, var1.posY, var7, 90.0F, 0.0F);
         if (var1.isEntityAlive()) {
            var3.updateEntityWithOptionalForce(var1, false);
         }
      }

      var3.theProfiler.endSection();
      if (var2 != 1) {
         var3.theProfiler.startSection("placing");
         var5 = (double)MathHelper.clamp_int((int)var5, -29999872, 29999872);
         var7 = (double)MathHelper.clamp_int((int)var7, -29999872, 29999872);
         if (var1.isEntityAlive()) {
            var1.setLocationAndAngles(var5, var1.posY, var7, var1.rotationYaw, var1.rotationPitch);
            var4.getDefaultTeleporter().func_180266_a(var1, var11);
            var4.spawnEntityInWorld(var1);
            var4.updateEntityWithOptionalForce(var1, false);
         }

         var3.theProfiler.endSection();
      }

      var1.setWorld(var4);
   }

   public int getViewDistance() {
      return this.viewDistance;
   }

   public void sendPacketToAllPlayersInDimension(Packet var1, int var2) {
      for(int var3 = 0; var3 < this.playerEntityList.size(); ++var3) {
         EntityPlayerMP var4 = (EntityPlayerMP)this.playerEntityList.get(var3);
         if (var4.dimension == var2) {
            var4.playerNetServerHandler.sendPacket(var1);
         }
      }

   }

   public void removeOp(GameProfile var1) {
      this.ops.removeEntry(var1);
   }
}

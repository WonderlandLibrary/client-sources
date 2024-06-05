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
import java.util.HashSet;
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
import net.minecraft.network.play.server.S38PacketPlayerListItem$Action;
import net.minecraft.network.play.server.S39PacketPlayerAbilities;
import net.minecraft.network.play.server.S3EPacketTeams;
import net.minecraft.network.play.server.S3FPacketCustomPayload;
import net.minecraft.network.play.server.S41PacketServerDifficulty;
import net.minecraft.network.play.server.S44PacketWorldBorder;
import net.minecraft.network.play.server.S44PacketWorldBorder$Action;
import net.minecraft.potion.PotionEffect;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.ServerScoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.BanList;
import net.minecraft.server.management.IPBanEntry;
import net.minecraft.server.management.ItemInWorldManager;
import net.minecraft.server.management.PlayerManager;
import net.minecraft.server.management.PlayerProfileCache;
import net.minecraft.server.management.ServerConfigurationManager$1;
import net.minecraft.server.management.UserListBans;
import net.minecraft.server.management.UserListBansEntry;
import net.minecraft.server.management.UserListOps;
import net.minecraft.server.management.UserListOpsEntry;
import net.minecraft.server.management.UserListWhitelist;
import net.minecraft.server.management.UserListWhitelistEntry;
import net.minecraft.stats.StatList;
import net.minecraft.stats.StatisticsFile;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldSettings$GameType;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.demo.DemoWorldManager;
import net.minecraft.world.storage.IPlayerFileData;
import net.minecraft.world.storage.WorldInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class ServerConfigurationManager {
   private static final Logger LOGGER = LogManager.getLogger();
   public static final File FILE_PLAYERBANS = new File("banned-players.json");
   public static final File FILE_IPBANS = new File("banned-ips.json");
   public static final File FILE_OPS = new File("ops.json");
   public static final File FILE_WHITELIST = new File("whitelist.json");
   private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd \'at\' HH:mm:ss z");
   private final MinecraftServer mcServer;
   private final List playerEntityList = Lists.newCopyOnWriteArrayList();
   private final Map uuidToPlayerMap = Maps.newHashMap();
   private final UserListBans bannedPlayers;
   private final BanList bannedIPs;
   private final UserListOps ops;
   private final UserListWhitelist whiteListedPlayers;
   private final Map playerStatFiles;
   private IPlayerFileData playerNBTManagerObj;
   private boolean whiteListEnforced;
   protected int maxPlayers;
   private int viewDistance;
   private WorldSettings$GameType p;
   private boolean commandsAllowedForAll;
   private int playerPingIndex;

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

   public void initializeConnectionToPlayer(NetworkManager var1, EntityPlayerMP var2) {
      GameProfile var3 = var2.getGameProfile();
      PlayerProfileCache var4 = this.mcServer.getPlayerProfileCache();
      GameProfile var5 = var4.getProfileByUUID(var3.getId());
      String var6 = var3.getName();
      var4.addEntry(var3);
      NBTTagCompound var7 = this.readPlayerDataFromFile(var2);
      var2.setWorld(this.mcServer.worldServerForDimension(var2.dimension));
      var2.theItemInWorldManager.setWorld((WorldServer)var2.worldObj);
      String var8 = "local";
      if(var1.getRemoteAddress() != null) {
         var8 = var1.getRemoteAddress().toString();
      }

      LOGGER.info(var2.getName() + "[" + var8 + "] logged in with entity id " + var2.getEntityID() + " at (" + var2.posX + ", " + var2.posY + ", " + var2.posZ + ")");
      WorldServer var9 = this.mcServer.worldServerForDimension(var2.dimension);
      WorldInfo var10 = var9.getWorldInfo();
      BlockPos var11 = var9.getSpawnPoint();
      this.setPlayerGameTypeBasedOnOther(var2, (EntityPlayerMP)null, var9);
      NetHandlerPlayServer var12 = new NetHandlerPlayServer(this.mcServer, var1, var2);
      var12.sendPacket(new S01PacketJoinGame(var2.getEntityID(), var2.theItemInWorldManager.getGameType(), var10.isHardcoreModeEnabled(), var9.provider.getDimensionId(), var9.getDifficulty(), this.getMaxPlayers(), var10.getTerrainType(), var9.getGameRules().getBoolean("reducedDebugInfo")));
      var12.sendPacket(new S3FPacketCustomPayload("MC|Brand", (new PacketBuffer(Unpooled.buffer())).writeString(this.getServerInstance().getServerModName())));
      var12.sendPacket(new S41PacketServerDifficulty(var10.getDifficulty(), var10.isDifficultyLocked()));
      var12.sendPacket(new S05PacketSpawnPosition(var11));
      var12.sendPacket(new S39PacketPlayerAbilities(var2.abilities));
      var12.sendPacket(new S09PacketHeldItemChange(var2.inventory.currentItem));
      var2.getStatFile().func_150877_d();
      var2.getStatFile().sendAchievements(var2);
      this.sendScoreboard((ServerScoreboard)var9.getScoreboard(), var2);
      this.mcServer.refreshStatusNextTick();
      ChatComponentTranslation var13;
      if(!var2.getName().equalsIgnoreCase(var6)) {
         var13 = new ChatComponentTranslation("multiplayer.player.joined.renamed", new Object[]{var2.getDisplayName(), var6});
      } else {
         var13 = new ChatComponentTranslation("multiplayer.player.joined", new Object[]{var2.getDisplayName()});
      }

      var13.getChatStyle().setColor(EnumChatFormatting.YELLOW);
      this.sendChatMsg(var13);
      this.playerLoggedIn(var2);
      var12.setPlayerLocation(var2.posX, var2.posY, var2.posZ, var2.rotationYaw, var2.rotationPitch);
      this.updateTimeAndWeatherForPlayer(var2, var9);
      if(!this.mcServer.getResourcePackUrl().isEmpty()) {
         var2.loadResourcePack(this.mcServer.getResourcePackUrl(), this.mcServer.getResourcePackHash());
      }

      for(PotionEffect var15 : var2.getActivePotionEffects()) {
         var12.sendPacket(new S1DPacketEntityEffect(var2.getEntityID(), var15));
      }

      var2.addSelfToInternalCraftingInventory();
      if(var7.hasKey("Riding", 10)) {
         Entity var16 = EntityList.createEntityFromNBT(var7.getCompoundTag("Riding"), var9);
         var16.forceSpawn = true;
         var9.spawnEntityInWorld(var16);
         var2.mountEntity(var16);
         var16.forceSpawn = false;
      }

   }

   protected void sendScoreboard(ServerScoreboard var1, EntityPlayerMP var2) {
      HashSet var3 = Sets.newHashSet();

      for(ScorePlayerTeam var5 : var1.getTeams()) {
         var2.playerNetServerHandler.sendPacket(new S3EPacketTeams(var5, 0));
      }

      for(int var8 = 0; var8 < 19; ++var8) {
         ScoreObjective var9 = var1.getObjectiveInDisplaySlot(var8);
         if(!var3.contains(var9)) {
            for(Packet var7 : var1.func_96550_d(var9)) {
               var2.playerNetServerHandler.sendPacket(var7);
            }

            var3.add(var9);
         }
      }

   }

   public void setPlayerManager(WorldServer[] var1) {
      this.playerNBTManagerObj = var1[0].getSaveHandler().getPlayerNBTManager();
      var1[0].getWorldBorder().addListener(new ServerConfigurationManager$1(this));
   }

   public void preparePlayer(EntityPlayerMP var1, WorldServer var2) {
      WorldServer var3 = var1.getServerForPlayer();
      var2.getPlayerManager().removePlayer(var1);
      var3.getPlayerManager().addPlayer(var1);
      var3.theChunkProviderServer.loadChunk((int)var1.posX >> 4, (int)var1.posZ >> 4);
   }

   public int getEntityViewDistance() {
      return PlayerManager.getFurthestViewableBlock(this.getViewDistance());
   }

   public NBTTagCompound readPlayerDataFromFile(EntityPlayerMP var1) {
      NBTTagCompound var2 = this.mcServer.worldServers[0].getWorldInfo().getPlayerNBTTagCompound();
      NBTTagCompound var3;
      if(var1.getName().equals(this.mcServer.getServerOwner())) {
         var1.readFromNBT(var2);
         var3 = var2;
         LOGGER.debug("loading single player");
      } else {
         var3 = this.playerNBTManagerObj.readPlayerData(var1);
      }

      return var3;
   }

   protected void writePlayerData(EntityPlayerMP var1) {
      this.playerNBTManagerObj.writePlayerData(var1);
      StatisticsFile var2 = (StatisticsFile)this.playerStatFiles.get(var1.getUniqueID());
      var2.saveStatFile();
   }

   public void playerLoggedIn(EntityPlayerMP var1) {
      this.playerEntityList.add(var1);
      this.uuidToPlayerMap.put(var1.getUniqueID(), var1);
      this.sendPacketToAllPlayers(new S38PacketPlayerListItem(S38PacketPlayerListItem$Action.ADD_PLAYER, new EntityPlayerMP[]{var1}));
      WorldServer var2 = this.mcServer.worldServerForDimension(var1.dimension);
      var2.spawnEntityInWorld(var1);
      this.preparePlayer(var1, (WorldServer)null);

      for(EntityPlayerMP var4 : this.playerEntityList) {
         var1.playerNetServerHandler.sendPacket(new S38PacketPlayerListItem(S38PacketPlayerListItem$Action.ADD_PLAYER, new EntityPlayerMP[]{var4}));
      }

   }

   public void serverUpdateMountedMovingPlayer(EntityPlayerMP var1) {
      var1.getServerForPlayer().getPlayerManager().updateMountedMovingPlayer(var1);
   }

   public void playerLoggedOut(EntityPlayerMP var1) {
      var1.triggerAchievement(StatList.leaveGameStat);
      this.writePlayerData(var1);
      WorldServer var2 = var1.getServerForPlayer();
      if(var1.ridingEntity != null) {
         var2.removePlayerEntityDangerously(var1.ridingEntity);
         LOGGER.debug("removing player mount");
      }

      var2.removeEntity(var1);
      var2.getPlayerManager().removePlayer(var1);
      this.playerEntityList.remove(var1);
      UUID var3 = var1.getUniqueID();
      EntityPlayerMP var4 = (EntityPlayerMP)this.uuidToPlayerMap.get(var3);
      if(var4 == var1) {
         this.uuidToPlayerMap.remove(var3);
         this.playerStatFiles.remove(var3);
      }

      this.sendPacketToAllPlayers(new S38PacketPlayerListItem(S38PacketPlayerListItem$Action.REMOVE_PLAYER, new EntityPlayerMP[]{var1}));
   }

   public String allowUserToConnect(SocketAddress var1, GameProfile var2) {
      if(this.bannedPlayers.isBanned(var2)) {
         UserListBansEntry var5 = (UserListBansEntry)this.bannedPlayers.getEntry(var2);
         String var6 = "You are banned from this server!\nReason: " + var5.getBanReason();
         if(var5.getBanEndDate() != null) {
            var6 = var6 + "\nYour ban will be removed on " + dateFormat.format(var5.getBanEndDate());
         }

         return var6;
      } else if(!this.canJoin(var2)) {
         return "You are not white-listed on this server!";
      } else if(this.bannedIPs.isBanned(var1)) {
         IPBanEntry var3 = this.bannedIPs.getBanEntry(var1);
         String var4 = "Your IP address is banned from this server!\nReason: " + var3.getBanReason();
         if(var3.getBanEndDate() != null) {
            var4 = var4 + "\nYour ban will be removed on " + dateFormat.format(var3.getBanEndDate());
         }

         return var4;
      } else {
         return this.playerEntityList.size() >= this.maxPlayers && !this.func_183023_f(var2)?"The server is full!":null;
      }
   }

   public EntityPlayerMP createPlayerForUser(GameProfile var1) {
      UUID var2 = EntityPlayer.getUUID(var1);
      ArrayList var3 = Lists.newArrayList();

      for(EntityPlayerMP var5 : this.playerEntityList) {
         if(var5.getUniqueID().equals(var2)) {
            var3.add(var5);
         }
      }

      EntityPlayerMP var7 = (EntityPlayerMP)this.uuidToPlayerMap.get(var1.getId());
      if(!var3.contains(var7)) {
         var3.add(var7);
      }

      for(EntityPlayerMP var6 : var3) {
         var6.playerNetServerHandler.kickPlayerFromServer("You logged in from another location");
      }

      Object var9;
      if(this.mcServer.isDemo()) {
         var9 = new DemoWorldManager(this.mcServer.worldServerForDimension(0));
      } else {
         var9 = new ItemInWorldManager(this.mcServer.worldServerForDimension(0));
      }

      return new EntityPlayerMP(this.mcServer, this.mcServer.worldServerForDimension(0), var1, (ItemInWorldManager)var9);
   }

   public EntityPlayerMP recreatePlayerEntity(EntityPlayerMP var1, int var2, boolean var3) {
      var1.getServerForPlayer().getEntityTracker().removePlayerFromTrackers(var1);
      var1.getServerForPlayer().getEntityTracker().untrackEntity(var1);
      var1.getServerForPlayer().getPlayerManager().removePlayer(var1);
      this.playerEntityList.remove(var1);
      this.mcServer.worldServerForDimension(var1.dimension).removePlayerEntityDangerously(var1);
      BlockPos var4 = var1.getBedLocation();
      boolean var5 = var1.isSpawnForced();
      var1.dimension = var2;
      Object var6;
      if(this.mcServer.isDemo()) {
         var6 = new DemoWorldManager(this.mcServer.worldServerForDimension(var1.dimension));
      } else {
         var6 = new ItemInWorldManager(this.mcServer.worldServerForDimension(var1.dimension));
      }

      EntityPlayerMP var7 = new EntityPlayerMP(this.mcServer, this.mcServer.worldServerForDimension(var1.dimension), var1.getGameProfile(), (ItemInWorldManager)var6);
      var7.playerNetServerHandler = var1.playerNetServerHandler;
      var7.clonePlayer(var1, var3);
      var7.setEntityId(var1.getEntityID());
      var7.func_174817_o(var1);
      WorldServer var8 = this.mcServer.worldServerForDimension(var1.dimension);
      this.setPlayerGameTypeBasedOnOther(var7, var1, var8);
      BlockPos var9 = EntityPlayer.getBedSpawnLocation(this.mcServer.worldServerForDimension(var1.dimension), var4, var5);
      var7.setLocationAndAngles((double)((float)var9.getX() + 0.5F), (double)((float)var9.getY() + 0.1F), (double)((float)var9.getZ() + 0.5F), 0.0F, 0.0F);
      var7.setSpawnPoint(var4, var5);
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
      this.uuidToPlayerMap.put(var7.getUniqueID(), var7);
      var7.addSelfToInternalCraftingInventory();
      var7.setHealth(var7.getHealth());
      return var7;
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
      this.preparePlayer(var1, var4);
      var1.playerNetServerHandler.setPlayerLocation(var1.posX, var1.posY, var1.posZ, var1.rotationYaw, var1.rotationPitch);
      var1.theItemInWorldManager.setWorld(var5);
      this.updateTimeAndWeatherForPlayer(var1, var5);
      this.syncPlayerInventory(var1);

      for(PotionEffect var7 : var1.getActivePotionEffects()) {
         var1.playerNetServerHandler.sendPacket(new S1DPacketEntityEffect(var1.getEntityID(), var7));
      }

   }

   public void transferEntityToWorld(Entity var1, int var2, WorldServer var3, WorldServer var4) {
      double var5 = var1.posX;
      double var7 = var1.posZ;
      double var9 = 8.0D;
      float var11 = var1.rotationYaw;
      var3.theProfiler.startSection("moving");
      if(var1.dimension == -1) {
         var5 = MathHelper.clamp_double(var5 / 8.0D, var4.getWorldBorder().minX() + 16.0D, var4.getWorldBorder().maxX() - 16.0D);
         var7 = MathHelper.clamp_double(var7 / 8.0D, var4.getWorldBorder().minZ() + 16.0D, var4.getWorldBorder().maxZ() - 16.0D);
         var1.setLocationAndAngles(var5, var1.posY, var7, var1.rotationYaw, var1.rotationPitch);
         if(var1.isEntityAlive()) {
            var3.updateEntityWithOptionalForce(var1, false);
         }
      } else if(var1.dimension == 0) {
         var5 = MathHelper.clamp_double(var5 * 8.0D, var4.getWorldBorder().minX() + 16.0D, var4.getWorldBorder().maxX() - 16.0D);
         var7 = MathHelper.clamp_double(var7 * 8.0D, var4.getWorldBorder().minZ() + 16.0D, var4.getWorldBorder().maxZ() - 16.0D);
         var1.setLocationAndAngles(var5, var1.posY, var7, var1.rotationYaw, var1.rotationPitch);
         if(var1.isEntityAlive()) {
            var3.updateEntityWithOptionalForce(var1, false);
         }
      } else {
         BlockPos var12;
         if(var2 == 1) {
            var12 = var4.getSpawnPoint();
         } else {
            var12 = var4.getSpawnCoordinate();
         }

         var5 = (double)var12.getX();
         var1.posY = (double)var12.getY();
         var7 = (double)var12.getZ();
         var1.setLocationAndAngles(var5, var1.posY, var7, 90.0F, 0.0F);
         if(var1.isEntityAlive()) {
            var3.updateEntityWithOptionalForce(var1, false);
         }
      }

      var3.theProfiler.endSection();
      if(var2 != 1) {
         var3.theProfiler.startSection("placing");
         var5 = (double)MathHelper.clamp_int((int)var5, -29999872, 29999872);
         var7 = (double)MathHelper.clamp_int((int)var7, -29999872, 29999872);
         if(var1.isEntityAlive()) {
            var1.setLocationAndAngles(var5, var1.posY, var7, var1.rotationYaw, var1.rotationPitch);
            var4.getDefaultTeleporter().placeInPortal(var1, var11);
            var4.spawnEntityInWorld(var1);
            var4.updateEntityWithOptionalForce(var1, false);
         }

         var3.theProfiler.endSection();
      }

      var1.setWorld(var4);
   }

   public void onTick() {
      if(++this.playerPingIndex > 600) {
         this.sendPacketToAllPlayers(new S38PacketPlayerListItem(S38PacketPlayerListItem$Action.UPDATE_LATENCY, this.playerEntityList));
         this.playerPingIndex = 0;
      }

   }

   public void sendPacketToAllPlayers(Packet var1) {
      for(EntityPlayerMP var3 : this.playerEntityList) {
         var3.playerNetServerHandler.sendPacket(var1);
      }

   }

   public void sendPacketToAllPlayersInDimension(Packet var1, int var2) {
      for(EntityPlayerMP var4 : this.playerEntityList) {
         if(var4.dimension == var2) {
            var4.playerNetServerHandler.sendPacket(var1);
         }
      }

   }

   public void sendMessageToAllTeamMembers(EntityPlayer var1, IChatComponent var2) {
      Team var3 = var1.getTeam();

      for(String var5 : var3.getMembershipCollection()) {
         EntityPlayerMP var6 = this.getPlayerByUsername(var5);
         if(var6 != var1) {
            var6.addChatMessage(var2);
         }
      }

   }

   public void a(EntityPlayer var1, IChatComponent var2) {
      Team var3 = var1.getTeam();
      this.sendChatMsg(var2);
   }

   public String func_181058_b(boolean var1) {
      String var2 = "";
      ArrayList var3 = Lists.newArrayList(this.playerEntityList);

      for(int var4 = 0; var4 < var3.size(); ++var4) {
         var2 = var2 + ", ";
         var2 = var2 + ((EntityPlayerMP)var3.get(var4)).getName();
         var2 = var2 + " (" + ((EntityPlayerMP)var3.get(var4)).getUniqueID().toString() + ")";
      }

      return var2;
   }

   public String[] getAllUsernames() {
      String[] var1 = new String[this.playerEntityList.size()];

      for(int var2 = 0; var2 < this.playerEntityList.size(); ++var2) {
         var1[var2] = ((EntityPlayerMP)this.playerEntityList.get(var2)).getName();
      }

      return var1;
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

   public BanList getBannedIPs() {
      return this.bannedIPs;
   }

   public void addOp(GameProfile var1) {
      this.ops.addEntry(new UserListOpsEntry(var1, this.mcServer.getOpPermissionLevel(), this.ops.func_183026_b(var1)));
   }

   public void removeOp(GameProfile var1) {
      this.ops.removeEntry(var1);
   }

   public boolean canJoin(GameProfile var1) {
      return !this.whiteListEnforced || this.ops.hasEntry(var1) || this.whiteListedPlayers.hasEntry(var1);
   }

   public boolean canSendCommands(GameProfile var1) {
      return this.ops.hasEntry(var1) || this.mcServer.isSinglePlayer() && this.mcServer.worldServers[0].getWorldInfo().areCommandsAllowed() && this.mcServer.getServerOwner().equalsIgnoreCase(var1.getName()) || this.commandsAllowedForAll;
   }

   public EntityPlayerMP getPlayerByUsername(String var1) {
      for(EntityPlayerMP var3 : this.playerEntityList) {
         if(var3.getName().equalsIgnoreCase(var1)) {
            return var3;
         }
      }

      return null;
   }

   public void sendToAllNear(double var1, double var3, double var5, double var7, int var9, Packet var10) {
      this.sendToAllNearExcept((EntityPlayer)null, var1, var3, var5, var7, var9, var10);
   }

   public void sendToAllNearExcept(EntityPlayer var1, double var2, double var4, double var6, double var8, int var10, Packet var11) {
      for(EntityPlayerMP var13 : this.playerEntityList) {
         if(var13 != var1 && var13.dimension == var10) {
            double var15 = var2 - var13.posX;
            double var17 = var4 - var13.posY;
            double var19 = var6 - var13.posZ;
            if(var15 * var15 + var17 * var17 + var19 * var19 < var8 * var8) {
               var13.playerNetServerHandler.sendPacket(var11);
            }
         }
      }

   }

   public void saveAllPlayerData() {
      for(EntityPlayerMP var2 : this.playerEntityList) {
         this.writePlayerData(var2);
      }

   }

   public void addWhitelistedPlayer(GameProfile var1) {
      this.whiteListedPlayers.addEntry(new UserListWhitelistEntry(var1));
   }

   public void removePlayerFromWhitelist(GameProfile var1) {
      this.whiteListedPlayers.removeEntry(var1);
   }

   public UserListWhitelist getWhitelistedPlayers() {
      return this.whiteListedPlayers;
   }

   public String[] getWhitelistedPlayerNames() {
      return this.whiteListedPlayers.getKeys();
   }

   public UserListOps getOppedPlayers() {
      return this.ops;
   }

   public String[] getOppedPlayerNames() {
      return this.ops.getKeys();
   }

   public void loadWhiteList() {
   }

   public void updateTimeAndWeatherForPlayer(EntityPlayerMP var1, WorldServer var2) {
      WorldBorder var3 = this.mcServer.worldServers[0].getWorldBorder();
      var1.playerNetServerHandler.sendPacket(new S44PacketWorldBorder(var3, S44PacketWorldBorder$Action.INITIALIZE));
      var1.playerNetServerHandler.sendPacket(new S03PacketTimeUpdate(var2.getTotalWorldTime(), var2.getWorldTime(), var2.getGameRules().getBoolean("doDaylightCycle")));
      if(var2.isRaining()) {
         var1.playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(1, 0.0F));
         var1.playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(7, var2.getRainStrength(1.0F)));
         var1.playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(8, var2.getThunderStrength(1.0F)));
      }

   }

   public void syncPlayerInventory(EntityPlayerMP var1) {
      var1.sendContainerToPlayer(var1.inventoryContainer);
      var1.setPlayerHealthUpdated();
      var1.playerNetServerHandler.sendPacket(new S09PacketHeldItemChange(var1.inventory.currentItem));
   }

   public int getCurrentPlayerCount() {
      return this.playerEntityList.size();
   }

   public int getMaxPlayers() {
      return this.maxPlayers;
   }

   public String[] getAvailablePlayerDat() {
      return this.mcServer.worldServers[0].getSaveHandler().getPlayerNBTManager().getAvailablePlayerDat();
   }

   public void setWhiteListEnabled(boolean var1) {
      this.whiteListEnforced = var1;
   }

   public List getPlayersMatchingAddress(String var1) {
      ArrayList var2 = Lists.newArrayList();

      for(EntityPlayerMP var4 : this.playerEntityList) {
         if(var4.getPlayerIP().equals(var1)) {
            var2.add(var4);
         }
      }

      return var2;
   }

   public int getViewDistance() {
      return this.viewDistance;
   }

   public void setViewDistance(int var1) {
      this.viewDistance = var1;
      if(this.mcServer.worldServers != null) {
         for(WorldServer var5 : this.mcServer.worldServers) {
            var5.getPlayerManager().setPlayerViewRadius(var1);
         }
      }

   }

   public MinecraftServer getServerInstance() {
      return this.mcServer;
   }

   public NBTTagCompound getHostPlayerData() {
      return null;
   }

   public void setGameType(WorldSettings$GameType var1) {
      this.p = var1;
   }

   private void setPlayerGameTypeBasedOnOther(EntityPlayerMP var1, EntityPlayerMP var2, World var3) {
      var1.theItemInWorldManager.setGameType(var2.theItemInWorldManager.getGameType());
      var1.theItemInWorldManager.initializeGameType(var3.getWorldInfo().getGameType());
   }

   public void setCommandsAllowedForAll(boolean var1) {
      this.commandsAllowedForAll = var1;
   }

   public void removeAllPlayers() {
      for(EntityPlayerMP var2 : this.playerEntityList) {
         var2.playerNetServerHandler.kickPlayerFromServer("Server closed");
      }

   }

   public void sendChatMsgImpl(IChatComponent var1, boolean var2) {
      this.mcServer.addChatMessage(var1);
      byte var3 = (byte)1;
      this.sendPacketToAllPlayers(new S02PacketChat(var1, var3));
   }

   public void sendChatMsg(IChatComponent var1) {
      this.sendChatMsgImpl(var1, true);
   }

   public StatisticsFile getPlayerStatsFile(EntityPlayer var1) {
      UUID var2 = var1.getUniqueID();
      StatisticsFile var3 = null;
      File var4 = new File(this.mcServer.worldServerForDimension(0).getSaveHandler().getWorldDirectory(), "stats");
      File var5 = new File(var4, var2.toString() + ".json");
      if(!var5.exists()) {
         File var6 = new File(var4, var1.getName() + ".json");
         if(var6.exists() && var6.isFile()) {
            var6.renameTo(var5);
         }
      }

      var3 = new StatisticsFile(this.mcServer, var5);
      var3.readStatFile();
      this.playerStatFiles.put(var2, var3);
      return var3;
   }

   public List func_181057_v() {
      return this.playerEntityList;
   }

   public EntityPlayerMP getPlayerByUUID(UUID var1) {
      return (EntityPlayerMP)this.uuidToPlayerMap.get(var1);
   }

   public boolean func_183023_f(GameProfile var1) {
      return false;
   }
}

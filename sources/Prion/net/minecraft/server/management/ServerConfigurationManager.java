package net.minecraft.server.management;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.authlib.GameProfile;
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
import net.minecraft.entity.EntityTracker;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.S03PacketTimeUpdate;
import net.minecraft.network.play.server.S05PacketSpawnPosition;
import net.minecraft.network.play.server.S07PacketRespawn;
import net.minecraft.network.play.server.S09PacketHeldItemChange;
import net.minecraft.network.play.server.S1DPacketEntityEffect;
import net.minecraft.network.play.server.S1FPacketSetExperience;
import net.minecraft.network.play.server.S2BPacketChangeGameState;
import net.minecraft.network.play.server.S38PacketPlayerListItem;
import net.minecraft.network.play.server.S38PacketPlayerListItem.Action;
import net.minecraft.network.play.server.S41PacketServerDifficulty;
import net.minecraft.network.play.server.S44PacketWorldBorder;
import net.minecraft.network.play.server.S44PacketWorldBorder.Action;
import net.minecraft.potion.PotionEffect;
import net.minecraft.profiler.Profiler;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.ServerScoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.MinecraftServer;
import net.minecraft.stats.StatisticsFile;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldSettings.GameType;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.demo.DemoWorldManager;
import net.minecraft.world.gen.ChunkProviderServer;
import net.minecraft.world.storage.IPlayerFileData;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.WorldInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class ServerConfigurationManager
{
  public static final File FILE_PLAYERBANS = new File("banned-players.json");
  public static final File FILE_IPBANS = new File("banned-ips.json");
  public static final File FILE_OPS = new File("ops.json");
  public static final File FILE_WHITELIST = new File("whitelist.json");
  private static final Logger logger = LogManager.getLogger();
  private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
  

  private final MinecraftServer mcServer;
  

  public final List playerEntityList = Lists.newArrayList();
  public final Map field_177454_f = Maps.newHashMap();
  

  private final UserListBans bannedPlayers;
  

  private final BanList bannedIPs;
  

  private final UserListOps ops;
  

  private final UserListWhitelist whiteListedPlayers;
  
  private final Map playerStatFiles;
  
  private IPlayerFileData playerNBTManagerObj;
  
  private boolean whiteListEnforced;
  
  protected int maxPlayers;
  
  private int viewDistance;
  
  private WorldSettings.GameType gameType;
  
  private boolean commandsAllowedForAll;
  
  private int playerPingIndex;
  
  private static final String __OBFID = "CL_00001423";
  

  public ServerConfigurationManager(MinecraftServer server)
  {
    bannedPlayers = new UserListBans(FILE_PLAYERBANS);
    bannedIPs = new BanList(FILE_IPBANS);
    ops = new UserListOps(FILE_OPS);
    whiteListedPlayers = new UserListWhitelist(FILE_WHITELIST);
    playerStatFiles = Maps.newHashMap();
    mcServer = server;
    bannedPlayers.setLanServer(false);
    bannedIPs.setLanServer(false);
    maxPlayers = 8;
  }
  
  public void initializeConnectionToPlayer(NetworkManager netManager, EntityPlayerMP playerIn)
  {
    GameProfile var3 = playerIn.getGameProfile();
    PlayerProfileCache var4 = mcServer.getPlayerProfileCache();
    GameProfile var5 = var4.func_152652_a(var3.getId());
    String var6 = var5 == null ? var3.getName() : var5.getName();
    var4.func_152649_a(var3);
    NBTTagCompound var7 = readPlayerDataFromFile(playerIn);
    playerIn.setWorld(mcServer.worldServerForDimension(dimension));
    theItemInWorldManager.setWorld((WorldServer)worldObj);
    String var8 = "local";
    
    if (netManager.getRemoteAddress() != null)
    {
      var8 = netManager.getRemoteAddress().toString();
    }
    
    logger.info(playerIn.getName() + "[" + var8 + "] logged in with entity id " + playerIn.getEntityId() + " at (" + posX + ", " + posY + ", " + posZ + ")");
    WorldServer var9 = mcServer.worldServerForDimension(dimension);
    WorldInfo var10 = var9.getWorldInfo();
    BlockPos var11 = var9.getSpawnPoint();
    func_72381_a(playerIn, null, var9);
    NetHandlerPlayServer var12 = new NetHandlerPlayServer(mcServer, netManager, playerIn);
    var12.sendPacket(new net.minecraft.network.play.server.S01PacketJoinGame(playerIn.getEntityId(), theItemInWorldManager.getGameType(), var10.isHardcoreModeEnabled(), provider.getDimensionId(), var9.getDifficulty(), getMaxPlayers(), var10.getTerrainType(), var9.getGameRules().getGameRuleBooleanValue("reducedDebugInfo")));
    var12.sendPacket(new net.minecraft.network.play.server.S3FPacketCustomPayload("MC|Brand", new PacketBuffer(io.netty.buffer.Unpooled.buffer()).writeString(getServerInstance().getServerModName())));
    var12.sendPacket(new S41PacketServerDifficulty(var10.getDifficulty(), var10.isDifficultyLocked()));
    var12.sendPacket(new S05PacketSpawnPosition(var11));
    var12.sendPacket(new net.minecraft.network.play.server.S39PacketPlayerAbilities(capabilities));
    var12.sendPacket(new S09PacketHeldItemChange(inventory.currentItem));
    playerIn.getStatFile().func_150877_d();
    playerIn.getStatFile().func_150884_b(playerIn);
    func_96456_a((ServerScoreboard)var9.getScoreboard(), playerIn);
    mcServer.refreshStatusNextTick();
    ChatComponentTranslation var13;
    ChatComponentTranslation var13;
    if (!playerIn.getName().equalsIgnoreCase(var6))
    {
      var13 = new ChatComponentTranslation("multiplayer.player.joined.renamed", new Object[] { playerIn.getDisplayName(), var6 });
    }
    else
    {
      var13 = new ChatComponentTranslation("multiplayer.player.joined", new Object[] { playerIn.getDisplayName() });
    }
    
    var13.getChatStyle().setColor(EnumChatFormatting.YELLOW);
    sendChatMsg(var13);
    playerLoggedIn(playerIn);
    var12.setPlayerLocation(posX, posY, posZ, rotationYaw, rotationPitch);
    updateTimeAndWeatherForPlayer(playerIn, var9);
    
    if (mcServer.getResourcePackUrl().length() > 0)
    {
      playerIn.func_175397_a(mcServer.getResourcePackUrl(), mcServer.getResourcePackHash());
    }
    
    Iterator var14 = playerIn.getActivePotionEffects().iterator();
    
    while (var14.hasNext())
    {
      PotionEffect var15 = (PotionEffect)var14.next();
      var12.sendPacket(new S1DPacketEntityEffect(playerIn.getEntityId(), var15));
    }
    
    playerIn.addSelfToInternalCraftingInventory();
    
    if ((var7 != null) && (var7.hasKey("Riding", 10)))
    {
      Entity var16 = EntityList.createEntityFromNBT(var7.getCompoundTag("Riding"), var9);
      
      if (var16 != null)
      {
        forceSpawn = true;
        var9.spawnEntityInWorld(var16);
        playerIn.mountEntity(var16);
        forceSpawn = false;
      }
    }
  }
  
  protected void func_96456_a(ServerScoreboard scoreboardIn, EntityPlayerMP playerIn)
  {
    HashSet var3 = com.google.common.collect.Sets.newHashSet();
    Iterator var4 = scoreboardIn.getTeams().iterator();
    
    while (var4.hasNext())
    {
      ScorePlayerTeam var5 = (ScorePlayerTeam)var4.next();
      playerNetServerHandler.sendPacket(new net.minecraft.network.play.server.S3EPacketTeams(var5, 0));
    }
    
    for (int var9 = 0; var9 < 19; var9++)
    {
      net.minecraft.scoreboard.ScoreObjective var10 = scoreboardIn.getObjectiveInDisplaySlot(var9);
      
      if ((var10 != null) && (!var3.contains(var10)))
      {
        List var6 = scoreboardIn.func_96550_d(var10);
        Iterator var7 = var6.iterator();
        
        while (var7.hasNext())
        {
          Packet var8 = (Packet)var7.next();
          playerNetServerHandler.sendPacket(var8);
        }
        
        var3.add(var10);
      }
    }
  }
  



  public void setPlayerManager(WorldServer[] p_72364_1_)
  {
    playerNBTManagerObj = p_72364_1_[0].getSaveHandler().getPlayerNBTManager();
    p_72364_1_[0].getWorldBorder().addListener(new net.minecraft.world.border.IBorderListener()
    {
      private static final String __OBFID = "CL_00002267";
      
      public void onSizeChanged(WorldBorder border, double newSize) {
        sendPacketToAllPlayers(new S44PacketWorldBorder(border, S44PacketWorldBorder.Action.SET_SIZE));
      }
      
      public void func_177692_a(WorldBorder border, double p_177692_2_, double p_177692_4_, long p_177692_6_) {
        sendPacketToAllPlayers(new S44PacketWorldBorder(border, S44PacketWorldBorder.Action.LERP_SIZE));
      }
      
      public void onCenterChanged(WorldBorder border, double x, double z) {
        sendPacketToAllPlayers(new S44PacketWorldBorder(border, S44PacketWorldBorder.Action.SET_CENTER));
      }
      
      public void onWarningTimeChanged(WorldBorder border, int p_177691_2_) {
        sendPacketToAllPlayers(new S44PacketWorldBorder(border, S44PacketWorldBorder.Action.SET_WARNING_TIME));
      }
      

      public void onWarningDistanceChanged(WorldBorder border, int p_177690_2_) { sendPacketToAllPlayers(new S44PacketWorldBorder(border, S44PacketWorldBorder.Action.SET_WARNING_BLOCKS)); }
      
      public void func_177696_b(WorldBorder border, double p_177696_2_) {}
      
      public void func_177695_c(WorldBorder border, double p_177695_2_) {}
    });
  }
  
  public void func_72375_a(EntityPlayerMP playerIn, WorldServer worldIn) {
    WorldServer var3 = playerIn.getServerForPlayer();
    
    if (worldIn != null)
    {
      worldIn.getPlayerManager().removePlayer(playerIn);
    }
    
    var3.getPlayerManager().addPlayer(playerIn);
    theChunkProviderServer.loadChunk((int)posX >> 4, (int)posZ >> 4);
  }
  
  public int getEntityViewDistance()
  {
    return PlayerManager.getFurthestViewableBlock(getViewDistance());
  }
  



  public NBTTagCompound readPlayerDataFromFile(EntityPlayerMP playerIn)
  {
    NBTTagCompound var2 = mcServer.worldServers[0].getWorldInfo().getPlayerNBTTagCompound();
    
    NBTTagCompound var3;
    if ((playerIn.getName().equals(mcServer.getServerOwner())) && (var2 != null))
    {
      playerIn.readFromNBT(var2);
      NBTTagCompound var3 = var2;
      logger.debug("loading single player");
    }
    else
    {
      var3 = playerNBTManagerObj.readPlayerData(playerIn);
    }
    
    return var3;
  }
  



  protected void writePlayerData(EntityPlayerMP playerIn)
  {
    playerNBTManagerObj.writePlayerData(playerIn);
    StatisticsFile var2 = (StatisticsFile)playerStatFiles.get(playerIn.getUniqueID());
    
    if (var2 != null)
    {
      var2.func_150883_b();
    }
  }
  



  public void playerLoggedIn(EntityPlayerMP playerIn)
  {
    playerEntityList.add(playerIn);
    field_177454_f.put(playerIn.getUniqueID(), playerIn);
    sendPacketToAllPlayers(new S38PacketPlayerListItem(S38PacketPlayerListItem.Action.ADD_PLAYER, new EntityPlayerMP[] { playerIn }));
    WorldServer var2 = mcServer.worldServerForDimension(dimension);
    var2.spawnEntityInWorld(playerIn);
    func_72375_a(playerIn, null);
    
    for (int var3 = 0; var3 < playerEntityList.size(); var3++)
    {
      EntityPlayerMP var4 = (EntityPlayerMP)playerEntityList.get(var3);
      playerNetServerHandler.sendPacket(new S38PacketPlayerListItem(S38PacketPlayerListItem.Action.ADD_PLAYER, new EntityPlayerMP[] { var4 }));
    }
  }
  



  public void serverUpdateMountedMovingPlayer(EntityPlayerMP playerIn)
  {
    playerIn.getServerForPlayer().getPlayerManager().updateMountedMovingPlayer(playerIn);
  }
  



  public void playerLoggedOut(EntityPlayerMP playerIn)
  {
    playerIn.triggerAchievement(net.minecraft.stats.StatList.leaveGameStat);
    writePlayerData(playerIn);
    WorldServer var2 = playerIn.getServerForPlayer();
    
    if (ridingEntity != null)
    {
      var2.removePlayerEntityDangerously(ridingEntity);
      logger.debug("removing player mount");
    }
    
    var2.removeEntity(playerIn);
    var2.getPlayerManager().removePlayer(playerIn);
    playerEntityList.remove(playerIn);
    field_177454_f.remove(playerIn.getUniqueID());
    playerStatFiles.remove(playerIn.getUniqueID());
    sendPacketToAllPlayers(new S38PacketPlayerListItem(S38PacketPlayerListItem.Action.REMOVE_PLAYER, new EntityPlayerMP[] { playerIn }));
  }
  





  public String allowUserToConnect(SocketAddress address, GameProfile profile)
  {
    if (bannedPlayers.isBanned(profile))
    {
      UserListBansEntry var5 = (UserListBansEntry)bannedPlayers.getEntry(profile);
      String var4 = "You are banned from this server!\nReason: " + var5.getBanReason();
      
      if (var5.getBanEndDate() != null)
      {
        var4 = var4 + "\nYour ban will be removed on " + dateFormat.format(var5.getBanEndDate());
      }
      
      return var4;
    }
    if (!canJoin(profile))
    {
      return "You are not white-listed on this server!";
    }
    if (bannedIPs.isBanned(address))
    {
      IPBanEntry var3 = bannedIPs.getBanEntry(address);
      String var4 = "Your IP address is banned from this server!\nReason: " + var3.getBanReason();
      
      if (var3.getBanEndDate() != null)
      {
        var4 = var4 + "\nYour ban will be removed on " + dateFormat.format(var3.getBanEndDate());
      }
      
      return var4;
    }
    

    return playerEntityList.size() >= maxPlayers ? "The server is full!" : null;
  }
  




  public EntityPlayerMP createPlayerForUser(GameProfile profile)
  {
    UUID var2 = EntityPlayer.getUUID(profile);
    ArrayList var3 = Lists.newArrayList();
    

    for (int var4 = 0; var4 < playerEntityList.size(); var4++)
    {
      EntityPlayerMP var5 = (EntityPlayerMP)playerEntityList.get(var4);
      
      if (var5.getUniqueID().equals(var2))
      {
        var3.add(var5);
      }
    }
    
    Iterator var6 = var3.iterator();
    
    while (var6.hasNext())
    {
      EntityPlayerMP var5 = (EntityPlayerMP)var6.next();
      playerNetServerHandler.kickPlayerFromServer("You logged in from another location");
    }
    
    Object var7;
    Object var7;
    if (mcServer.isDemo())
    {
      var7 = new DemoWorldManager(mcServer.worldServerForDimension(0));
    }
    else
    {
      var7 = new ItemInWorldManager(mcServer.worldServerForDimension(0));
    }
    
    return new EntityPlayerMP(mcServer, mcServer.worldServerForDimension(0), profile, (ItemInWorldManager)var7);
  }
  



  public EntityPlayerMP recreatePlayerEntity(EntityPlayerMP playerIn, int dimension, boolean conqueredEnd)
  {
    playerIn.getServerForPlayer().getEntityTracker().removePlayerFromTrackers(playerIn);
    playerIn.getServerForPlayer().getEntityTracker().untrackEntity(playerIn);
    playerIn.getServerForPlayer().getPlayerManager().removePlayer(playerIn);
    playerEntityList.remove(playerIn);
    mcServer.worldServerForDimension(dimension).removePlayerEntityDangerously(playerIn);
    BlockPos var4 = playerIn.func_180470_cg();
    boolean var5 = playerIn.isSpawnForced();
    dimension = dimension;
    Object var6;
    Object var6;
    if (mcServer.isDemo())
    {
      var6 = new DemoWorldManager(mcServer.worldServerForDimension(dimension));
    }
    else
    {
      var6 = new ItemInWorldManager(mcServer.worldServerForDimension(dimension));
    }
    
    EntityPlayerMP var7 = new EntityPlayerMP(mcServer, mcServer.worldServerForDimension(dimension), playerIn.getGameProfile(), (ItemInWorldManager)var6);
    playerNetServerHandler = playerNetServerHandler;
    var7.clonePlayer(playerIn, conqueredEnd);
    var7.setEntityId(playerIn.getEntityId());
    var7.func_174817_o(playerIn);
    WorldServer var8 = mcServer.worldServerForDimension(dimension);
    func_72381_a(var7, playerIn, var8);
    

    if (var4 != null)
    {
      BlockPos var9 = EntityPlayer.func_180467_a(mcServer.worldServerForDimension(dimension), var4, var5);
      
      if (var9 != null)
      {
        var7.setLocationAndAngles(var9.getX() + 0.5F, var9.getY() + 0.1F, var9.getZ() + 0.5F, 0.0F, 0.0F);
        var7.func_180473_a(var4, var5);
      }
      else
      {
        playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(0, 0.0F));
      }
    }
    
    theChunkProviderServer.loadChunk((int)posX >> 4, (int)posZ >> 4);
    
    while ((!var8.getCollidingBoundingBoxes(var7, var7.getEntityBoundingBox()).isEmpty()) && (posY < 256.0D))
    {
      var7.setPosition(posX, posY + 1.0D, posZ);
    }
    
    playerNetServerHandler.sendPacket(new S07PacketRespawn(dimension, worldObj.getDifficulty(), worldObj.getWorldInfo().getTerrainType(), theItemInWorldManager.getGameType()));
    BlockPos var9 = var8.getSpawnPoint();
    playerNetServerHandler.setPlayerLocation(posX, posY, posZ, rotationYaw, rotationPitch);
    playerNetServerHandler.sendPacket(new S05PacketSpawnPosition(var9));
    playerNetServerHandler.sendPacket(new S1FPacketSetExperience(experience, experienceTotal, experienceLevel));
    updateTimeAndWeatherForPlayer(var7, var8);
    var8.getPlayerManager().addPlayer(var7);
    var8.spawnEntityInWorld(var7);
    playerEntityList.add(var7);
    field_177454_f.put(var7.getUniqueID(), var7);
    var7.addSelfToInternalCraftingInventory();
    var7.setHealth(var7.getHealth());
    return var7;
  }
  



  public void transferPlayerToDimension(EntityPlayerMP playerIn, int dimension)
  {
    int var3 = dimension;
    WorldServer var4 = mcServer.worldServerForDimension(dimension);
    dimension = dimension;
    WorldServer var5 = mcServer.worldServerForDimension(dimension);
    playerNetServerHandler.sendPacket(new S07PacketRespawn(dimension, worldObj.getDifficulty(), worldObj.getWorldInfo().getTerrainType(), theItemInWorldManager.getGameType()));
    var4.removePlayerEntityDangerously(playerIn);
    isDead = false;
    transferEntityToWorld(playerIn, var3, var4, var5);
    func_72375_a(playerIn, var4);
    playerNetServerHandler.setPlayerLocation(posX, posY, posZ, rotationYaw, rotationPitch);
    theItemInWorldManager.setWorld(var5);
    updateTimeAndWeatherForPlayer(playerIn, var5);
    syncPlayerInventory(playerIn);
    Iterator var6 = playerIn.getActivePotionEffects().iterator();
    
    while (var6.hasNext())
    {
      PotionEffect var7 = (PotionEffect)var6.next();
      playerNetServerHandler.sendPacket(new S1DPacketEntityEffect(playerIn.getEntityId(), var7));
    }
  }
  



  public void transferEntityToWorld(Entity entityIn, int p_82448_2_, WorldServer p_82448_3_, WorldServer p_82448_4_)
  {
    double var5 = posX;
    double var7 = posZ;
    double var9 = 8.0D;
    float var11 = rotationYaw;
    theProfiler.startSection("moving");
    
    if (dimension == -1)
    {
      var5 = MathHelper.clamp_double(var5 / var9, p_82448_4_.getWorldBorder().minX() + 16.0D, p_82448_4_.getWorldBorder().maxX() - 16.0D);
      var7 = MathHelper.clamp_double(var7 / var9, p_82448_4_.getWorldBorder().minZ() + 16.0D, p_82448_4_.getWorldBorder().maxZ() - 16.0D);
      entityIn.setLocationAndAngles(var5, posY, var7, rotationYaw, rotationPitch);
      
      if (entityIn.isEntityAlive())
      {
        p_82448_3_.updateEntityWithOptionalForce(entityIn, false);
      }
    }
    else if (dimension == 0)
    {
      var5 = MathHelper.clamp_double(var5 * var9, p_82448_4_.getWorldBorder().minX() + 16.0D, p_82448_4_.getWorldBorder().maxX() - 16.0D);
      var7 = MathHelper.clamp_double(var7 * var9, p_82448_4_.getWorldBorder().minZ() + 16.0D, p_82448_4_.getWorldBorder().maxZ() - 16.0D);
      entityIn.setLocationAndAngles(var5, posY, var7, rotationYaw, rotationPitch);
      
      if (entityIn.isEntityAlive())
      {
        p_82448_3_.updateEntityWithOptionalForce(entityIn, false);
      }
    }
    else
    {
      BlockPos var12;
      BlockPos var12;
      if (p_82448_2_ == 1)
      {
        var12 = p_82448_4_.getSpawnPoint();
      }
      else
      {
        var12 = p_82448_4_.func_180504_m();
      }
      
      var5 = var12.getX();
      posY = var12.getY();
      var7 = var12.getZ();
      entityIn.setLocationAndAngles(var5, posY, var7, 90.0F, 0.0F);
      
      if (entityIn.isEntityAlive())
      {
        p_82448_3_.updateEntityWithOptionalForce(entityIn, false);
      }
    }
    
    theProfiler.endSection();
    
    if (p_82448_2_ != 1)
    {
      theProfiler.startSection("placing");
      var5 = MathHelper.clamp_int((int)var5, -29999872, 29999872);
      var7 = MathHelper.clamp_int((int)var7, -29999872, 29999872);
      
      if (entityIn.isEntityAlive())
      {
        entityIn.setLocationAndAngles(var5, posY, var7, rotationYaw, rotationPitch);
        p_82448_4_.getDefaultTeleporter().func_180266_a(entityIn, var11);
        p_82448_4_.spawnEntityInWorld(entityIn);
        p_82448_4_.updateEntityWithOptionalForce(entityIn, false);
      }
      
      theProfiler.endSection();
    }
    
    entityIn.setWorld(p_82448_4_);
  }
  



  public void onTick()
  {
    if (++playerPingIndex > 600)
    {
      sendPacketToAllPlayers(new S38PacketPlayerListItem(S38PacketPlayerListItem.Action.UPDATE_LATENCY, playerEntityList));
      playerPingIndex = 0;
    }
  }
  
  public void sendPacketToAllPlayers(Packet packetIn)
  {
    for (int var2 = 0; var2 < playerEntityList.size(); var2++)
    {
      playerEntityList.get(var2)).playerNetServerHandler.sendPacket(packetIn);
    }
  }
  
  public void sendPacketToAllPlayersInDimension(Packet packetIn, int dimension)
  {
    for (int var3 = 0; var3 < playerEntityList.size(); var3++)
    {
      EntityPlayerMP var4 = (EntityPlayerMP)playerEntityList.get(var3);
      
      if (dimension == dimension)
      {
        playerNetServerHandler.sendPacket(packetIn);
      }
    }
  }
  
  public void func_177453_a(EntityPlayer p_177453_1_, IChatComponent p_177453_2_)
  {
    Team var3 = p_177453_1_.getTeam();
    
    if (var3 != null)
    {
      Collection var4 = var3.getMembershipCollection();
      Iterator var5 = var4.iterator();
      
      while (var5.hasNext())
      {
        String var6 = (String)var5.next();
        EntityPlayerMP var7 = getPlayerByUsername(var6);
        
        if ((var7 != null) && (var7 != p_177453_1_))
        {
          var7.addChatMessage(p_177453_2_);
        }
      }
    }
  }
  
  public void func_177452_b(EntityPlayer p_177452_1_, IChatComponent p_177452_2_)
  {
    Team var3 = p_177452_1_.getTeam();
    
    if (var3 == null)
    {
      sendChatMsg(p_177452_2_);
    }
    else
    {
      for (int var4 = 0; var4 < playerEntityList.size(); var4++)
      {
        EntityPlayerMP var5 = (EntityPlayerMP)playerEntityList.get(var4);
        
        if (var5.getTeam() != var3)
        {
          var5.addChatMessage(p_177452_2_);
        }
      }
    }
  }
  
  public String func_180602_f()
  {
    String var1 = "";
    
    for (int var2 = 0; var2 < playerEntityList.size(); var2++)
    {
      if (var2 > 0)
      {
        var1 = var1 + ", ";
      }
      
      var1 = var1 + ((EntityPlayerMP)playerEntityList.get(var2)).getName();
    }
    
    return var1;
  }
  



  public String[] getAllUsernames()
  {
    String[] var1 = new String[playerEntityList.size()];
    
    for (int var2 = 0; var2 < playerEntityList.size(); var2++)
    {
      var1[var2] = ((EntityPlayerMP)playerEntityList.get(var2)).getName();
    }
    
    return var1;
  }
  
  public GameProfile[] getAllProfiles()
  {
    GameProfile[] var1 = new GameProfile[playerEntityList.size()];
    
    for (int var2 = 0; var2 < playerEntityList.size(); var2++)
    {
      var1[var2] = ((EntityPlayerMP)playerEntityList.get(var2)).getGameProfile();
    }
    
    return var1;
  }
  
  public UserListBans getBannedPlayers()
  {
    return bannedPlayers;
  }
  
  public BanList getBannedIPs()
  {
    return bannedIPs;
  }
  
  public void addOp(GameProfile profile)
  {
    ops.addEntry(new UserListOpsEntry(profile, mcServer.getOpPermissionLevel()));
  }
  
  public void removeOp(GameProfile profile)
  {
    ops.removeEntry(profile);
  }
  
  public boolean canJoin(GameProfile profile)
  {
    return (!whiteListEnforced) || (ops.hasEntry(profile)) || (whiteListedPlayers.hasEntry(profile));
  }
  
  public boolean canSendCommands(GameProfile profile)
  {
    return (ops.hasEntry(profile)) || ((mcServer.isSinglePlayer()) && (mcServer.worldServers[0].getWorldInfo().areCommandsAllowed()) && (mcServer.getServerOwner().equalsIgnoreCase(profile.getName()))) || (commandsAllowedForAll);
  }
  
  public EntityPlayerMP getPlayerByUsername(String username)
  {
    Iterator var2 = playerEntityList.iterator();
    
    EntityPlayerMP var3;
    do
    {
      if (!var2.hasNext())
      {
        return null;
      }
      
      var3 = (EntityPlayerMP)var2.next();
    }
    while (!var3.getName().equalsIgnoreCase(username));
    
    return var3;
  }
  



  public void sendToAllNear(double x, double y, double z, double radius, int dimension, Packet packetIn)
  {
    sendToAllNearExcept(null, x, y, z, radius, dimension, packetIn);
  }
  




  public void sendToAllNearExcept(EntityPlayer p_148543_1_, double x, double y, double z, double radius, int dimension, Packet p_148543_11_)
  {
    for (int var12 = 0; var12 < playerEntityList.size(); var12++)
    {
      EntityPlayerMP var13 = (EntityPlayerMP)playerEntityList.get(var12);
      
      if ((var13 != p_148543_1_) && (dimension == dimension))
      {
        double var14 = x - posX;
        double var16 = y - posY;
        double var18 = z - posZ;
        
        if (var14 * var14 + var16 * var16 + var18 * var18 < radius * radius)
        {
          playerNetServerHandler.sendPacket(p_148543_11_);
        }
      }
    }
  }
  



  public void saveAllPlayerData()
  {
    for (int var1 = 0; var1 < playerEntityList.size(); var1++)
    {
      writePlayerData((EntityPlayerMP)playerEntityList.get(var1));
    }
  }
  
  public void addWhitelistedPlayer(GameProfile profile)
  {
    whiteListedPlayers.addEntry(new UserListWhitelistEntry(profile));
  }
  
  public void removePlayerFromWhitelist(GameProfile profile)
  {
    whiteListedPlayers.removeEntry(profile);
  }
  
  public UserListWhitelist getWhitelistedPlayers()
  {
    return whiteListedPlayers;
  }
  
  public String[] getWhitelistedPlayerNames()
  {
    return whiteListedPlayers.getKeys();
  }
  
  public UserListOps getOppedPlayers()
  {
    return ops;
  }
  
  public String[] getOppedPlayerNames()
  {
    return ops.getKeys();
  }
  



  public void loadWhiteList() {}
  



  public void updateTimeAndWeatherForPlayer(EntityPlayerMP playerIn, WorldServer worldIn)
  {
    WorldBorder var3 = mcServer.worldServers[0].getWorldBorder();
    playerNetServerHandler.sendPacket(new S44PacketWorldBorder(var3, S44PacketWorldBorder.Action.INITIALIZE));
    playerNetServerHandler.sendPacket(new S03PacketTimeUpdate(worldIn.getTotalWorldTime(), worldIn.getWorldTime(), worldIn.getGameRules().getGameRuleBooleanValue("doDaylightCycle")));
    
    if (worldIn.isRaining())
    {
      playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(1, 0.0F));
      playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(7, worldIn.getRainStrength(1.0F)));
      playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(8, worldIn.getWeightedThunderStrength(1.0F)));
    }
  }
  



  public void syncPlayerInventory(EntityPlayerMP playerIn)
  {
    playerIn.sendContainerToPlayer(inventoryContainer);
    playerIn.setPlayerHealthUpdated();
    playerNetServerHandler.sendPacket(new S09PacketHeldItemChange(inventory.currentItem));
  }
  



  public int getCurrentPlayerCount()
  {
    return playerEntityList.size();
  }
  



  public int getMaxPlayers()
  {
    return maxPlayers;
  }
  



  public String[] getAvailablePlayerDat()
  {
    return mcServer.worldServers[0].getSaveHandler().getPlayerNBTManager().getAvailablePlayerDat();
  }
  
  public void setWhiteListEnabled(boolean whitelistEnabled)
  {
    whiteListEnforced = whitelistEnabled;
  }
  
  public List getPlayersMatchingAddress(String address)
  {
    ArrayList var2 = Lists.newArrayList();
    Iterator var3 = playerEntityList.iterator();
    
    while (var3.hasNext())
    {
      EntityPlayerMP var4 = (EntityPlayerMP)var3.next();
      
      if (var4.getPlayerIP().equals(address))
      {
        var2.add(var4);
      }
    }
    
    return var2;
  }
  



  public int getViewDistance()
  {
    return viewDistance;
  }
  
  public MinecraftServer getServerInstance()
  {
    return mcServer;
  }
  



  public NBTTagCompound getHostPlayerData()
  {
    return null;
  }
  
  public void func_152604_a(WorldSettings.GameType p_152604_1_)
  {
    gameType = p_152604_1_;
  }
  
  private void func_72381_a(EntityPlayerMP p_72381_1_, EntityPlayerMP p_72381_2_, World worldIn)
  {
    if (p_72381_2_ != null)
    {
      theItemInWorldManager.setGameType(theItemInWorldManager.getGameType());
    }
    else if (gameType != null)
    {
      theItemInWorldManager.setGameType(gameType);
    }
    
    theItemInWorldManager.initializeGameType(worldIn.getWorldInfo().getGameType());
  }
  



  public void setCommandsAllowedForAll(boolean p_72387_1_)
  {
    commandsAllowedForAll = p_72387_1_;
  }
  



  public void removeAllPlayers()
  {
    for (int var1 = 0; var1 < playerEntityList.size(); var1++)
    {
      playerEntityList.get(var1)).playerNetServerHandler.kickPlayerFromServer("Server closed");
    }
  }
  
  public void sendChatMsgImpl(IChatComponent component, boolean isChat)
  {
    mcServer.addChatMessage(component);
    int var3 = isChat ? 1 : 0;
    sendPacketToAllPlayers(new net.minecraft.network.play.server.S02PacketChat(component, (byte)var3));
  }
  



  public void sendChatMsg(IChatComponent component)
  {
    sendChatMsgImpl(component, true);
  }
  
  public StatisticsFile getPlayerStatsFile(EntityPlayer playerIn)
  {
    UUID var2 = playerIn.getUniqueID();
    StatisticsFile var3 = var2 == null ? null : (StatisticsFile)playerStatFiles.get(var2);
    
    if (var3 == null)
    {
      File var4 = new File(mcServer.worldServerForDimension(0).getSaveHandler().getWorldDirectory(), "stats");
      File var5 = new File(var4, var2.toString() + ".json");
      
      if (!var5.exists())
      {
        File var6 = new File(var4, playerIn.getName() + ".json");
        
        if ((var6.exists()) && (var6.isFile()))
        {
          var6.renameTo(var5);
        }
      }
      
      var3 = new StatisticsFile(mcServer, var5);
      var3.func_150882_a();
      playerStatFiles.put(var2, var3);
    }
    
    return var3;
  }
  
  public void setViewDistance(int distance)
  {
    viewDistance = distance;
    
    if (mcServer.worldServers != null)
    {
      WorldServer[] var2 = mcServer.worldServers;
      int var3 = var2.length;
      
      for (int var4 = 0; var4 < var3; var4++)
      {
        WorldServer var5 = var2[var4];
        
        if (var5 != null)
        {
          var5.getPlayerManager().func_152622_a(distance);
        }
      }
    }
  }
  
  public EntityPlayerMP func_177451_a(UUID p_177451_1_)
  {
    return (EntityPlayerMP)field_177454_f.get(p_177451_1_);
  }
}

package net.minecraft.server.management;

import java.text.*;
import java.io.*;
import net.minecraft.server.*;
import net.minecraft.world.border.*;
import com.google.common.collect.*;
import java.util.*;
import com.mojang.authlib.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.demo.*;
import net.minecraft.world.*;
import net.minecraft.nbt.*;
import org.apache.logging.log4j.*;
import net.minecraft.scoreboard.*;
import io.netty.buffer.*;
import net.minecraft.network.*;
import net.minecraft.potion.*;
import net.minecraft.world.storage.*;
import net.minecraft.entity.*;
import net.minecraft.stats.*;
import net.minecraft.util.*;
import java.net.*;
import net.minecraft.network.play.server.*;

public abstract class ServerConfigurationManager
{
    private int playerPingIndex;
    private final BanList bannedIPs;
    private final UserListOps ops;
    private boolean whiteListEnforced;
    private static final SimpleDateFormat dateFormat;
    public static final File FILE_WHITELIST;
    private WorldSettings.GameType gameType;
    private int viewDistance;
    private final Map<UUID, EntityPlayerMP> uuidToPlayerMap;
    private final Map<UUID, StatisticsFile> playerStatFiles;
    private final UserListBans bannedPlayers;
    private final List<EntityPlayerMP> playerEntityList;
    public static final File FILE_IPBANS;
    private static final Logger logger;
    private final MinecraftServer mcServer;
    private final UserListWhitelist whiteListedPlayers;
    private boolean commandsAllowedForAll;
    private static final String[] I;
    private IPlayerFileData playerNBTManagerObj;
    protected int maxPlayers;
    public static final File FILE_OPS;
    public static final File FILE_PLAYERBANS;
    
    public void setPlayerManager(final WorldServer[] array) {
        this.playerNBTManagerObj = array["".length()].getSaveHandler().getPlayerNBTManager();
        array["".length()].getWorldBorder().addListener(new IBorderListener(this) {
            final ServerConfigurationManager this$0;
            
            @Override
            public void onWarningTimeChanged(final WorldBorder worldBorder, final int n) {
                this.this$0.sendPacketToAllPlayers(new S44PacketWorldBorder(worldBorder, S44PacketWorldBorder.Action.SET_WARNING_TIME));
            }
            
            private static String I(final String s, final String s2) {
                final StringBuilder sb = new StringBuilder();
                final char[] charArray = s2.toCharArray();
                int length = "".length();
                final char[] charArray2 = s.toCharArray();
                final int length2 = charArray2.length;
                int i = "".length();
                while (i < length2) {
                    sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                    ++length;
                    ++i;
                    "".length();
                    if (false) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            @Override
            public void onDamageAmountChanged(final WorldBorder worldBorder, final double n) {
            }
            
            @Override
            public void onDamageBufferChanged(final WorldBorder worldBorder, final double n) {
            }
            
            @Override
            public void onSizeChanged(final WorldBorder worldBorder, final double n) {
                this.this$0.sendPacketToAllPlayers(new S44PacketWorldBorder(worldBorder, S44PacketWorldBorder.Action.SET_SIZE));
            }
            
            @Override
            public void onWarningDistanceChanged(final WorldBorder worldBorder, final int n) {
                this.this$0.sendPacketToAllPlayers(new S44PacketWorldBorder(worldBorder, S44PacketWorldBorder.Action.SET_WARNING_BLOCKS));
            }
            
            @Override
            public void onTransitionStarted(final WorldBorder worldBorder, final double n, final double n2, final long n3) {
                this.this$0.sendPacketToAllPlayers(new S44PacketWorldBorder(worldBorder, S44PacketWorldBorder.Action.LERP_SIZE));
            }
            
            @Override
            public void onCenterChanged(final WorldBorder worldBorder, final double n, final double n2) {
                this.this$0.sendPacketToAllPlayers(new S44PacketWorldBorder(worldBorder, S44PacketWorldBorder.Action.SET_CENTER));
            }
        });
    }
    
    public ServerConfigurationManager(final MinecraftServer mcServer) {
        this.playerEntityList = (List<EntityPlayerMP>)Lists.newArrayList();
        this.uuidToPlayerMap = (Map<UUID, EntityPlayerMP>)Maps.newHashMap();
        this.bannedPlayers = new UserListBans(ServerConfigurationManager.FILE_PLAYERBANS);
        this.bannedIPs = new BanList(ServerConfigurationManager.FILE_IPBANS);
        this.ops = new UserListOps(ServerConfigurationManager.FILE_OPS);
        this.whiteListedPlayers = new UserListWhitelist(ServerConfigurationManager.FILE_WHITELIST);
        this.playerStatFiles = (Map<UUID, StatisticsFile>)Maps.newHashMap();
        this.mcServer = mcServer;
        this.bannedPlayers.setLanServer("".length() != 0);
        this.bannedIPs.setLanServer("".length() != 0);
        this.maxPlayers = (0x70 ^ 0x78);
    }
    
    public void preparePlayer(final EntityPlayerMP entityPlayerMP, final WorldServer worldServer) {
        final WorldServer serverForPlayer = entityPlayerMP.getServerForPlayer();
        if (worldServer != null) {
            worldServer.getPlayerManager().removePlayer(entityPlayerMP);
        }
        serverForPlayer.getPlayerManager().addPlayer(entityPlayerMP);
        serverForPlayer.theChunkProviderServer.loadChunk((int)entityPlayerMP.posX >> (0x8C ^ 0x88), (int)entityPlayerMP.posZ >> (0x68 ^ 0x6C));
    }
    
    public List<EntityPlayerMP> getPlayersMatchingAddress(final String s) {
        final ArrayList arrayList = Lists.newArrayList();
        final Iterator<EntityPlayerMP> iterator = this.playerEntityList.iterator();
        "".length();
        if (4 <= 1) {
            throw null;
        }
        while (iterator.hasNext()) {
            final EntityPlayerMP entityPlayerMP = iterator.next();
            if (entityPlayerMP.getPlayerIP().equals(s)) {
                arrayList.add(entityPlayerMP);
            }
        }
        return (List<EntityPlayerMP>)arrayList;
    }
    
    public EntityPlayerMP getPlayerByUsername(final String s) {
        final Iterator<EntityPlayerMP> iterator = this.playerEntityList.iterator();
        "".length();
        if (2 <= 1) {
            throw null;
        }
        while (iterator.hasNext()) {
            final EntityPlayerMP entityPlayerMP = iterator.next();
            if (entityPlayerMP.getName().equalsIgnoreCase(s)) {
                return entityPlayerMP;
            }
        }
        return null;
    }
    
    protected void sendScoreboard(final ServerScoreboard serverScoreboard, final EntityPlayerMP entityPlayerMP) {
        final HashSet hashSet = Sets.newHashSet();
        final Iterator<ScorePlayerTeam> iterator = serverScoreboard.getTeams().iterator();
        "".length();
        if (3 >= 4) {
            throw null;
        }
        while (iterator.hasNext()) {
            entityPlayerMP.playerNetServerHandler.sendPacket(new S3EPacketTeams(iterator.next(), "".length()));
        }
        int i = "".length();
        "".length();
        if (3 <= 1) {
            throw null;
        }
        while (i < (0x2E ^ 0x3D)) {
            final ScoreObjective objectiveInDisplaySlot = serverScoreboard.getObjectiveInDisplaySlot(i);
            if (objectiveInDisplaySlot != null && !hashSet.contains(objectiveInDisplaySlot)) {
                final Iterator<Packet> iterator2 = (Iterator<Packet>)serverScoreboard.func_96550_d(objectiveInDisplaySlot).iterator();
                "".length();
                if (2 < 1) {
                    throw null;
                }
                while (iterator2.hasNext()) {
                    entityPlayerMP.playerNetServerHandler.sendPacket(iterator2.next());
                }
                hashSet.add(objectiveInDisplaySlot);
            }
            ++i;
        }
    }
    
    public void serverUpdateMountedMovingPlayer(final EntityPlayerMP entityPlayerMP) {
        entityPlayerMP.getServerForPlayer().getPlayerManager().updateMountedMovingPlayer(entityPlayerMP);
    }
    
    public EntityPlayerMP createPlayerForUser(final GameProfile gameProfile) {
        final UUID uuid = EntityPlayer.getUUID(gameProfile);
        final ArrayList arrayList = Lists.newArrayList();
        int i = "".length();
        "".length();
        if (4 <= -1) {
            throw null;
        }
        while (i < this.playerEntityList.size()) {
            final EntityPlayerMP entityPlayerMP = this.playerEntityList.get(i);
            if (entityPlayerMP.getUniqueID().equals(uuid)) {
                arrayList.add(entityPlayerMP);
            }
            ++i;
        }
        final EntityPlayerMP entityPlayerMP2 = this.uuidToPlayerMap.get(gameProfile.getId());
        if (entityPlayerMP2 != null && !arrayList.contains(entityPlayerMP2)) {
            arrayList.add(entityPlayerMP2);
        }
        final Iterator<EntityPlayerMP> iterator = (Iterator<EntityPlayerMP>)arrayList.iterator();
        "".length();
        if (4 == 2) {
            throw null;
        }
        while (iterator.hasNext()) {
            iterator.next().playerNetServerHandler.kickPlayerFromServer(ServerConfigurationManager.I[0xAE ^ 0xB4]);
        }
        ItemInWorldManager itemInWorldManager;
        if (this.mcServer.isDemo()) {
            itemInWorldManager = new DemoWorldManager(this.mcServer.worldServerForDimension("".length()));
            "".length();
            if (-1 != -1) {
                throw null;
            }
        }
        else {
            itemInWorldManager = new ItemInWorldManager(this.mcServer.worldServerForDimension("".length()));
        }
        return new EntityPlayerMP(this.mcServer, this.mcServer.worldServerForDimension("".length()), gameProfile, itemInWorldManager);
    }
    
    public EntityPlayerMP getPlayerByUUID(final UUID uuid) {
        return this.uuidToPlayerMap.get(uuid);
    }
    
    public void sendPacketToAllPlayersInDimension(final Packet packet, final int n) {
        int i = "".length();
        "".length();
        if (3 <= -1) {
            throw null;
        }
        while (i < this.playerEntityList.size()) {
            final EntityPlayerMP entityPlayerMP = this.playerEntityList.get(i);
            if (entityPlayerMP.dimension == n) {
                entityPlayerMP.playerNetServerHandler.sendPacket(packet);
            }
            ++i;
        }
    }
    
    public NBTTagCompound readPlayerDataFromFile(final EntityPlayerMP entityPlayerMP) {
        final NBTTagCompound playerNBTTagCompound = this.mcServer.worldServers["".length()].getWorldInfo().getPlayerNBTTagCompound();
        NBTTagCompound playerData;
        if (entityPlayerMP.getName().equals(this.mcServer.getServerOwner()) && playerNBTTagCompound != null) {
            entityPlayerMP.readFromNBT(playerNBTTagCompound);
            playerData = playerNBTTagCompound;
            ServerConfigurationManager.logger.debug(ServerConfigurationManager.I[0x31 ^ 0x23]);
            "".length();
            if (3 == 4) {
                throw null;
            }
        }
        else {
            playerData = this.playerNBTManagerObj.readPlayerData(entityPlayerMP);
        }
        return playerData;
    }
    
    public void removeOp(final GameProfile gameProfile) {
        ((UserList<GameProfile, V>)this.ops).removeEntry(gameProfile);
    }
    
    public void setCommandsAllowedForAll(final boolean commandsAllowedForAll) {
        this.commandsAllowedForAll = commandsAllowedForAll;
    }
    
    public void setGameType(final WorldSettings.GameType gameType) {
        this.gameType = gameType;
    }
    
    public boolean func_183023_f(final GameProfile gameProfile) {
        return "".length() != 0;
    }
    
    public void addOp(final GameProfile gameProfile) {
        ((UserList<K, UserListOpsEntry>)this.ops).addEntry(new UserListOpsEntry(gameProfile, this.mcServer.getOpPermissionLevel(), this.ops.func_183026_b(gameProfile)));
    }
    
    public UserListWhitelist getWhitelistedPlayers() {
        return this.whiteListedPlayers;
    }
    
    public String[] getAllUsernames() {
        final String[] array = new String[this.playerEntityList.size()];
        int i = "".length();
        "".length();
        if (4 != 4) {
            throw null;
        }
        while (i < this.playerEntityList.size()) {
            array[i] = this.playerEntityList.get(i).getName();
            ++i;
        }
        return array;
    }
    
    static {
        I();
        FILE_PLAYERBANS = new File(ServerConfigurationManager.I["".length()]);
        FILE_IPBANS = new File(ServerConfigurationManager.I[" ".length()]);
        FILE_OPS = new File(ServerConfigurationManager.I["  ".length()]);
        FILE_WHITELIST = new File(ServerConfigurationManager.I["   ".length()]);
        logger = LogManager.getLogger();
        dateFormat = new SimpleDateFormat(ServerConfigurationManager.I[0x5 ^ 0x1]);
    }
    
    public String func_181058_b(final boolean b) {
        String s = ServerConfigurationManager.I[0x60 ^ 0x7D];
        final ArrayList arrayList = Lists.newArrayList((Iterable)this.playerEntityList);
        int i = "".length();
        "".length();
        if (3 != 3) {
            throw null;
        }
        while (i < arrayList.size()) {
            if (i > 0) {
                s = String.valueOf(s) + ServerConfigurationManager.I[0x92 ^ 0x8C];
            }
            s = String.valueOf(s) + ((EntityPlayerMP)arrayList.get(i)).getName();
            if (b) {
                s = String.valueOf(s) + ServerConfigurationManager.I[0xA1 ^ 0xBE] + ((EntityPlayerMP)arrayList.get(i)).getUniqueID().toString() + ServerConfigurationManager.I[0xA6 ^ 0x86];
            }
            ++i;
        }
        return s;
    }
    
    public int getViewDistance() {
        return this.viewDistance;
    }
    
    public MinecraftServer getServerInstance() {
        return this.mcServer;
    }
    
    public int getMaxPlayers() {
        return this.maxPlayers;
    }
    
    public void setViewDistance(final int n) {
        this.viewDistance = n;
        if (this.mcServer.worldServers != null) {
            final WorldServer[] worldServers;
            final int length = (worldServers = this.mcServer.worldServers).length;
            int i = "".length();
            "".length();
            if (-1 >= 0) {
                throw null;
            }
            while (i < length) {
                final WorldServer worldServer = worldServers[i];
                if (worldServer != null) {
                    worldServer.getPlayerManager().setPlayerViewRadius(n);
                }
                ++i;
            }
        }
    }
    
    public int getCurrentPlayerCount() {
        return this.playerEntityList.size();
    }
    
    public void setWhiteListEnabled(final boolean whiteListEnforced) {
        this.whiteListEnforced = whiteListEnforced;
    }
    
    public void syncPlayerInventory(final EntityPlayerMP entityPlayerMP) {
        entityPlayerMP.sendContainerToPlayer(entityPlayerMP.inventoryContainer);
        entityPlayerMP.setPlayerHealthUpdated();
        entityPlayerMP.playerNetServerHandler.sendPacket(new S09PacketHeldItemChange(entityPlayerMP.inventory.currentItem));
    }
    
    public void updateTimeAndWeatherForPlayer(final EntityPlayerMP entityPlayerMP, final WorldServer worldServer) {
        entityPlayerMP.playerNetServerHandler.sendPacket(new S44PacketWorldBorder(this.mcServer.worldServers["".length()].getWorldBorder(), S44PacketWorldBorder.Action.INITIALIZE));
        entityPlayerMP.playerNetServerHandler.sendPacket(new S03PacketTimeUpdate(worldServer.getTotalWorldTime(), worldServer.getWorldTime(), worldServer.getGameRules().getBoolean(ServerConfigurationManager.I[0x45 ^ 0x64])));
        if (worldServer.isRaining()) {
            entityPlayerMP.playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(" ".length(), 0.0f));
            entityPlayerMP.playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(0x7B ^ 0x7C, worldServer.getRainStrength(1.0f)));
            entityPlayerMP.playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(0x5F ^ 0x57, worldServer.getThunderStrength(1.0f)));
        }
    }
    
    public UserListOps getOppedPlayers() {
        return this.ops;
    }
    
    public GameProfile[] getAllProfiles() {
        final GameProfile[] array = new GameProfile[this.playerEntityList.size()];
        int i = "".length();
        "".length();
        if (-1 == 1) {
            throw null;
        }
        while (i < this.playerEntityList.size()) {
            array[i] = this.playerEntityList.get(i).getGameProfile();
            ++i;
        }
        return array;
    }
    
    public void saveAllPlayerData() {
        int i = "".length();
        "".length();
        if (-1 == 3) {
            throw null;
        }
        while (i < this.playerEntityList.size()) {
            this.writePlayerData(this.playerEntityList.get(i));
            ++i;
        }
    }
    
    public void sendChatMsgImpl(final IChatComponent chatComponent, final boolean b) {
        this.mcServer.addChatMessage(chatComponent);
        int n;
        if (b) {
            n = " ".length();
            "".length();
            if (-1 != -1) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        this.sendPacketToAllPlayers(new S02PacketChat(chatComponent, (byte)n));
    }
    
    protected void writePlayerData(final EntityPlayerMP entityPlayerMP) {
        this.playerNBTManagerObj.writePlayerData(entityPlayerMP);
        final StatisticsFile statisticsFile = this.playerStatFiles.get(entityPlayerMP.getUniqueID());
        if (statisticsFile != null) {
            statisticsFile.saveStatFile();
        }
    }
    
    public NBTTagCompound getHostPlayerData() {
        return null;
    }
    
    public void sendMessageToAllTeamMembers(final EntityPlayer entityPlayer, final IChatComponent chatComponent) {
        final Team team = entityPlayer.getTeam();
        if (team != null) {
            final Iterator<String> iterator = team.getMembershipCollection().iterator();
            "".length();
            if (2 >= 4) {
                throw null;
            }
            while (iterator.hasNext()) {
                final EntityPlayerMP playerByUsername = this.getPlayerByUsername(iterator.next());
                if (playerByUsername != null && playerByUsername != entityPlayer) {
                    playerByUsername.addChatMessage(chatComponent);
                }
            }
        }
    }
    
    public List<EntityPlayerMP> func_181057_v() {
        return this.playerEntityList;
    }
    
    public void initializeConnectionToPlayer(final NetworkManager networkManager, final EntityPlayerMP entityPlayerMP) {
        final GameProfile gameProfile = entityPlayerMP.getGameProfile();
        final PlayerProfileCache playerProfileCache = this.mcServer.getPlayerProfileCache();
        final GameProfile profileByUUID = playerProfileCache.getProfileByUUID(gameProfile.getId());
        String s;
        if (profileByUUID == null) {
            s = gameProfile.getName();
            "".length();
            if (true != true) {
                throw null;
            }
        }
        else {
            s = profileByUUID.getName();
        }
        final String s2 = s;
        playerProfileCache.addEntry(gameProfile);
        final NBTTagCompound playerDataFromFile = this.readPlayerDataFromFile(entityPlayerMP);
        entityPlayerMP.setWorld(this.mcServer.worldServerForDimension(entityPlayerMP.dimension));
        entityPlayerMP.theItemInWorldManager.setWorld((WorldServer)entityPlayerMP.worldObj);
        String string = ServerConfigurationManager.I[0x9C ^ 0x99];
        if (networkManager.getRemoteAddress() != null) {
            string = networkManager.getRemoteAddress().toString();
        }
        ServerConfigurationManager.logger.info(String.valueOf(entityPlayerMP.getName()) + ServerConfigurationManager.I[0x16 ^ 0x10] + string + ServerConfigurationManager.I[0x20 ^ 0x27] + entityPlayerMP.getEntityId() + ServerConfigurationManager.I[0xBA ^ 0xB2] + entityPlayerMP.posX + ServerConfigurationManager.I[0x26 ^ 0x2F] + entityPlayerMP.posY + ServerConfigurationManager.I[0x29 ^ 0x23] + entityPlayerMP.posZ + ServerConfigurationManager.I[0xC ^ 0x7]);
        final WorldServer worldServerForDimension = this.mcServer.worldServerForDimension(entityPlayerMP.dimension);
        final WorldInfo worldInfo = worldServerForDimension.getWorldInfo();
        final BlockPos spawnPoint = worldServerForDimension.getSpawnPoint();
        this.setPlayerGameTypeBasedOnOther(entityPlayerMP, null, worldServerForDimension);
        final NetHandlerPlayServer netHandlerPlayServer = new NetHandlerPlayServer(this.mcServer, networkManager, entityPlayerMP);
        netHandlerPlayServer.sendPacket(new S01PacketJoinGame(entityPlayerMP.getEntityId(), entityPlayerMP.theItemInWorldManager.getGameType(), worldInfo.isHardcoreModeEnabled(), worldServerForDimension.provider.getDimensionId(), worldServerForDimension.getDifficulty(), this.getMaxPlayers(), worldInfo.getTerrainType(), worldServerForDimension.getGameRules().getBoolean(ServerConfigurationManager.I[0x34 ^ 0x38])));
        netHandlerPlayServer.sendPacket(new S3FPacketCustomPayload(ServerConfigurationManager.I[0xCA ^ 0xC7], new PacketBuffer(Unpooled.buffer()).writeString(this.getServerInstance().getServerModName())));
        netHandlerPlayServer.sendPacket(new S41PacketServerDifficulty(worldInfo.getDifficulty(), worldInfo.isDifficultyLocked()));
        netHandlerPlayServer.sendPacket(new S05PacketSpawnPosition(spawnPoint));
        netHandlerPlayServer.sendPacket(new S39PacketPlayerAbilities(entityPlayerMP.capabilities));
        netHandlerPlayServer.sendPacket(new S09PacketHeldItemChange(entityPlayerMP.inventory.currentItem));
        entityPlayerMP.getStatFile().func_150877_d();
        entityPlayerMP.getStatFile().sendAchievements(entityPlayerMP);
        this.sendScoreboard((ServerScoreboard)worldServerForDimension.getScoreboard(), entityPlayerMP);
        this.mcServer.refreshStatusNextTick();
        ChatComponentTranslation chatComponentTranslation;
        if (!entityPlayerMP.getName().equalsIgnoreCase(s2)) {
            final String s3 = ServerConfigurationManager.I[0xB6 ^ 0xB8];
            final Object[] array = new Object["  ".length()];
            array["".length()] = entityPlayerMP.getDisplayName();
            array[" ".length()] = s2;
            chatComponentTranslation = new ChatComponentTranslation(s3, array);
            "".length();
            if (1 <= -1) {
                throw null;
            }
        }
        else {
            final String s4 = ServerConfigurationManager.I[0x10 ^ 0x1F];
            final Object[] array2 = new Object[" ".length()];
            array2["".length()] = entityPlayerMP.getDisplayName();
            chatComponentTranslation = new ChatComponentTranslation(s4, array2);
        }
        chatComponentTranslation.getChatStyle().setColor(EnumChatFormatting.YELLOW);
        this.sendChatMsg(chatComponentTranslation);
        this.playerLoggedIn(entityPlayerMP);
        netHandlerPlayServer.setPlayerLocation(entityPlayerMP.posX, entityPlayerMP.posY, entityPlayerMP.posZ, entityPlayerMP.rotationYaw, entityPlayerMP.rotationPitch);
        this.updateTimeAndWeatherForPlayer(entityPlayerMP, worldServerForDimension);
        if (this.mcServer.getResourcePackUrl().length() > 0) {
            entityPlayerMP.loadResourcePack(this.mcServer.getResourcePackUrl(), this.mcServer.getResourcePackHash());
        }
        final Iterator<PotionEffect> iterator = entityPlayerMP.getActivePotionEffects().iterator();
        "".length();
        if (3 != 3) {
            throw null;
        }
        while (iterator.hasNext()) {
            netHandlerPlayServer.sendPacket(new S1DPacketEntityEffect(entityPlayerMP.getEntityId(), iterator.next()));
        }
        entityPlayerMP.addSelfToInternalCraftingInventory();
        if (playerDataFromFile != null && playerDataFromFile.hasKey(ServerConfigurationManager.I[0xC ^ 0x1C], 0x3E ^ 0x34)) {
            final Entity entityFromNBT = EntityList.createEntityFromNBT(playerDataFromFile.getCompoundTag(ServerConfigurationManager.I[0xA0 ^ 0xB1]), worldServerForDimension);
            if (entityFromNBT != null) {
                entityFromNBT.forceSpawn = (" ".length() != 0);
                worldServerForDimension.spawnEntityInWorld(entityFromNBT);
                entityPlayerMP.mountEntity(entityFromNBT);
                entityFromNBT.forceSpawn = ("".length() != 0);
            }
        }
    }
    
    public void loadWhiteList() {
    }
    
    public boolean canSendCommands(final GameProfile gameProfile) {
        if (!((UserList<GameProfile, V>)this.ops).hasEntry(gameProfile) && (!this.mcServer.isSinglePlayer() || !this.mcServer.worldServers["".length()].getWorldInfo().areCommandsAllowed() || !this.mcServer.getServerOwner().equalsIgnoreCase(gameProfile.getName())) && !this.commandsAllowedForAll) {
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
    
    public void playerLoggedIn(final EntityPlayerMP entityPlayerMP) {
        this.playerEntityList.add(entityPlayerMP);
        this.uuidToPlayerMap.put(entityPlayerMP.getUniqueID(), entityPlayerMP);
        final S38PacketPlayerListItem.Action add_PLAYER = S38PacketPlayerListItem.Action.ADD_PLAYER;
        final EntityPlayerMP[] array = new EntityPlayerMP[" ".length()];
        array["".length()] = entityPlayerMP;
        this.sendPacketToAllPlayers(new S38PacketPlayerListItem(add_PLAYER, array));
        this.mcServer.worldServerForDimension(entityPlayerMP.dimension).spawnEntityInWorld(entityPlayerMP);
        this.preparePlayer(entityPlayerMP, null);
        int i = "".length();
        "".length();
        if (2 == 3) {
            throw null;
        }
        while (i < this.playerEntityList.size()) {
            final EntityPlayerMP entityPlayerMP2 = this.playerEntityList.get(i);
            final NetHandlerPlayServer playerNetServerHandler = entityPlayerMP.playerNetServerHandler;
            final S38PacketPlayerListItem.Action add_PLAYER2 = S38PacketPlayerListItem.Action.ADD_PLAYER;
            final EntityPlayerMP[] array2 = new EntityPlayerMP[" ".length()];
            array2["".length()] = entityPlayerMP2;
            playerNetServerHandler.sendPacket(new S38PacketPlayerListItem(add_PLAYER2, array2));
            ++i;
        }
    }
    
    public void sendMessageToTeamOrEvryPlayer(final EntityPlayer entityPlayer, final IChatComponent chatComponent) {
        final Team team = entityPlayer.getTeam();
        if (team == null) {
            this.sendChatMsg(chatComponent);
            "".length();
            if (-1 >= 3) {
                throw null;
            }
        }
        else {
            int i = "".length();
            "".length();
            if (3 < -1) {
                throw null;
            }
            while (i < this.playerEntityList.size()) {
                final EntityPlayerMP entityPlayerMP = this.playerEntityList.get(i);
                if (entityPlayerMP.getTeam() != team) {
                    entityPlayerMP.addChatMessage(chatComponent);
                }
                ++i;
            }
        }
    }
    
    private void setPlayerGameTypeBasedOnOther(final EntityPlayerMP entityPlayerMP, final EntityPlayerMP entityPlayerMP2, final World world) {
        if (entityPlayerMP2 != null) {
            entityPlayerMP.theItemInWorldManager.setGameType(entityPlayerMP2.theItemInWorldManager.getGameType());
            "".length();
            if (4 != 4) {
                throw null;
            }
        }
        else if (this.gameType != null) {
            entityPlayerMP.theItemInWorldManager.setGameType(this.gameType);
        }
        entityPlayerMP.theItemInWorldManager.initializeGameType(world.getWorldInfo().getGameType());
    }
    
    private static void I() {
        (I = new String[0xA8 ^ 0x8E])["".length()] = I("*0*\u001a ,|4\u0018$146\u0007k\"\"+\u001a", "HQDtE");
        ServerConfigurationManager.I[" ".length()] = I("\u0007( 9.\u0001d''8K#=8%", "eINWK");
        ServerConfigurationManager.I["  ".length()] = I("\u00177)D\r\u000b(4", "xGZjg");
        ServerConfigurationManager.I["   ".length()] = I("$\t\u001b?\u0003?\b\u0001?H9\u0012\u001d%", "SarKf");
        ServerConfigurationManager.I[0x23 ^ 0x27] = I("\u0012\u001c\t\u000eB&(]\u0013\u000bKB\u0011\u0003HK-8M\u0002\u0006_\u0003\u0004O\u0011", "kepwo");
        ServerConfigurationManager.I[0x62 ^ 0x67] = I("\u000e9!\b ", "bVBiL");
        ServerConfigurationManager.I[0x3C ^ 0x3A] = I("9", "boxbX");
        ServerConfigurationManager.I[0x68 ^ 0x6F] = I("4W':1\u000e\u0012/u?\u0007W<<\"\u0001W.;\"\u0000\u00032u?\rW", "iwKUV");
        ServerConfigurationManager.I[0xA6 ^ 0xAE] = I("C\n\u0017aA", "ckcAi");
        ServerConfigurationManager.I[0x88 ^ 0x81] = I("@s", "lScFN");
        ServerConfigurationManager.I[0xBA ^ 0xB0] = I("dA", "HaJEE");
        ServerConfigurationManager.I[0xA4 ^ 0xAF] = I("x", "Qnbtw");
        ServerConfigurationManager.I[0xAC ^ 0xA0] = I("05-?\u0012'4\r/\u001377\u0000$\u0017-", "BPIJq");
        ServerConfigurationManager.I[0x3F ^ 0x32] = I("8\u00041\u001a+\u0014))", "uGMXY");
        ServerConfigurationManager.I[0x6C ^ 0x62] = I("!:\u0014\u0001/<#\u0019\f#>a\b\u0019'5*\n[,#&\u0016\u0010\"b=\u001d\u001b'!*\u001c", "LOxuF");
        ServerConfigurationManager.I[0xA ^ 0x5] = I("\u000588;(\u0018!56$\u001ac$# \u0011(&a+\u0007$:*%", "hMTOA");
        ServerConfigurationManager.I[0x3D ^ 0x2D] = I(":\u0001\u000b?&\u000f", "hhoVH");
        ServerConfigurationManager.I[0x37 ^ 0x26] = I("\u00069 \u0006=3", "TPDoS");
        ServerConfigurationManager.I[0xC ^ 0x1E] = I("\u0005\u000b2\u0003\u0010\u0007\u0003s\u0014\u0010\u0007\u0003?\u0002Y\u0019\b2\u001e\u001c\u001b", "idSgy");
        ServerConfigurationManager.I[0xAF ^ 0xBC] = I("\u0006\b7<$\u001d\u0003=s\"\u0018\f#6 T\u00005&<\u0000", "tmZSR");
        ServerConfigurationManager.I[0x17 ^ 0x3] = I("\u0013\u0003\u001eK\u00008\tK\t\u0000$\u0002\u000e\u000fA,\u001e\u0004\u0006A>\u0004\u0002\u0018A9\t\u0019\u001d\u00048Ma9\u0004+\u001f\u0004\u0005[j", "Jlkka");
        ServerConfigurationManager.I[0x99 ^ 0x8C] = I("]((3\u0016w\u0013&(D \u0018+*D5\u0014g4\u0001:\u001e1#\u0000w\u001e)f", "WqGFd");
        ServerConfigurationManager.I[0xB1 ^ 0xA7] = I("4\n2V$\u001f\u0000g\u0018*\u0019E0\u001e,\u0019\u0000j\u001a,\u001e\u0011\"\u0012e\u0002\u000bg\u0002-\u0004\u0016g\u0005 \u001f\u0013\"\u0004d", "meGvE");
        ServerConfigurationManager.I[0x48 ^ 0x5F] = I("-8\u0004!W=\u0007Q2\u0013\u0010%\u0014 \u0004T>\u0002s\u0015\u00159\u001f6\u0013T1\u0003<\u001aT#\u0019:\u0004T$\u0014!\u0001\u0011%PY%\u00116\u0002<\u0019Nw", "tWqSw");
        ServerConfigurationManager.I[0x1C ^ 0x4] = I("A?\r6\nk\u0004\u0003-X<\u000f\u000e/X)\u0003B1\u001d&\t\u0014&\u001ck\t\fc", "KfbCx");
        ServerConfigurationManager.I[0x9D ^ 0x84] = I("\u001826q\u0011)(%4\u0010l3 q\u000496?p", "LZSQb");
        ServerConfigurationManager.I[0x90 ^ 0x8A] = I("\u0010\"%O\u0002&*7\n\ni$>O\b;\"=O\u000f'\"$\u0007\u000b;m<\u0000\r(99\u0000\u0000", "IMPon");
        ServerConfigurationManager.I[0xAD ^ 0xB6] = I("*\u0015\u0004\u0019\u0006 ", "Gzrph");
        ServerConfigurationManager.I[0x6D ^ 0x71] = I("\u0019\"\u0018\u001a%\u0007)", "iNyyL");
        ServerConfigurationManager.I[0x73 ^ 0x6E] = I("", "LFciK");
        ServerConfigurationManager.I[0x9B ^ 0x85] = I("AW", "mwuch");
        ServerConfigurationManager.I[0x3A ^ 0x25] = I("fc", "FKzdO");
        ServerConfigurationManager.I[0x13 ^ 0x33] = I("A", "huclD");
        ServerConfigurationManager.I[0x7 ^ 0x26] = I(">>\u000e\u0013\u001168-\u001a\u001c\u0019()\u001e\r", "ZQJrh");
        ServerConfigurationManager.I[0x7F ^ 0x5D] = I("=,+\u0002\u0006\u001ci:\u0018\f\u001d,=", "nIYtc");
        ServerConfigurationManager.I[0x70 ^ 0x53] = I("\u0019176\u0014", "jEVBg");
        ServerConfigurationManager.I[0xB5 ^ 0x91] = I("g-8\u0005\u001c", "IGKjr");
        ServerConfigurationManager.I[0xBE ^ 0x9B] = I("@\f\u001c!#", "nfoNM");
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (-1 >= 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public String[] getWhitelistedPlayerNames() {
        return this.whiteListedPlayers.getKeys();
    }
    
    public void removePlayerFromWhitelist(final GameProfile gameProfile) {
        ((UserList<GameProfile, V>)this.whiteListedPlayers).removeEntry(gameProfile);
    }
    
    public void playerLoggedOut(final EntityPlayerMP entityPlayerMP) {
        entityPlayerMP.triggerAchievement(StatList.leaveGameStat);
        this.writePlayerData(entityPlayerMP);
        final WorldServer serverForPlayer = entityPlayerMP.getServerForPlayer();
        if (entityPlayerMP.ridingEntity != null) {
            serverForPlayer.removePlayerEntityDangerously(entityPlayerMP.ridingEntity);
            ServerConfigurationManager.logger.debug(ServerConfigurationManager.I[0x24 ^ 0x37]);
        }
        serverForPlayer.removeEntity(entityPlayerMP);
        serverForPlayer.getPlayerManager().removePlayer(entityPlayerMP);
        this.playerEntityList.remove(entityPlayerMP);
        final UUID uniqueID = entityPlayerMP.getUniqueID();
        if (this.uuidToPlayerMap.get(uniqueID) == entityPlayerMP) {
            this.uuidToPlayerMap.remove(uniqueID);
            this.playerStatFiles.remove(uniqueID);
        }
        final S38PacketPlayerListItem.Action remove_PLAYER = S38PacketPlayerListItem.Action.REMOVE_PLAYER;
        final EntityPlayerMP[] array = new EntityPlayerMP[" ".length()];
        array["".length()] = entityPlayerMP;
        this.sendPacketToAllPlayers(new S38PacketPlayerListItem(remove_PLAYER, array));
    }
    
    public void sendToAllNearExcept(final EntityPlayer entityPlayer, final double n, final double n2, final double n3, final double n4, final int n5, final Packet packet) {
        int i = "".length();
        "".length();
        if (0 >= 4) {
            throw null;
        }
        while (i < this.playerEntityList.size()) {
            final EntityPlayerMP entityPlayerMP = this.playerEntityList.get(i);
            if (entityPlayerMP != entityPlayer && entityPlayerMP.dimension == n5) {
                final double n6 = n - entityPlayerMP.posX;
                final double n7 = n2 - entityPlayerMP.posY;
                final double n8 = n3 - entityPlayerMP.posZ;
                if (n6 * n6 + n7 * n7 + n8 * n8 < n4 * n4) {
                    entityPlayerMP.playerNetServerHandler.sendPacket(packet);
                }
            }
            ++i;
        }
    }
    
    public void removeAllPlayers() {
        int i = "".length();
        "".length();
        if (3 != 3) {
            throw null;
        }
        while (i < this.playerEntityList.size()) {
            this.playerEntityList.get(i).playerNetServerHandler.kickPlayerFromServer(ServerConfigurationManager.I[0x2 ^ 0x20]);
            ++i;
        }
    }
    
    public UserListBans getBannedPlayers() {
        return this.bannedPlayers;
    }
    
    public void transferEntityToWorld(final Entity entity, final int n, final WorldServer worldServer, final WorldServer world) {
        final double posX = entity.posX;
        final double posZ = entity.posZ;
        final double n2 = 8.0;
        final float rotationYaw = entity.rotationYaw;
        worldServer.theProfiler.startSection(ServerConfigurationManager.I[0x55 ^ 0x4E]);
        double n3;
        double n4;
        if (entity.dimension == -" ".length()) {
            n3 = MathHelper.clamp_double(posX / n2, world.getWorldBorder().minX() + 16.0, world.getWorldBorder().maxX() - 16.0);
            n4 = MathHelper.clamp_double(posZ / n2, world.getWorldBorder().minZ() + 16.0, world.getWorldBorder().maxZ() - 16.0);
            entity.setLocationAndAngles(n3, entity.posY, n4, entity.rotationYaw, entity.rotationPitch);
            if (entity.isEntityAlive()) {
                worldServer.updateEntityWithOptionalForce(entity, "".length() != 0);
                "".length();
                if (2 == 0) {
                    throw null;
                }
            }
        }
        else if (entity.dimension == 0) {
            n3 = MathHelper.clamp_double(posX * n2, world.getWorldBorder().minX() + 16.0, world.getWorldBorder().maxX() - 16.0);
            n4 = MathHelper.clamp_double(posZ * n2, world.getWorldBorder().minZ() + 16.0, world.getWorldBorder().maxZ() - 16.0);
            entity.setLocationAndAngles(n3, entity.posY, n4, entity.rotationYaw, entity.rotationPitch);
            if (entity.isEntityAlive()) {
                worldServer.updateEntityWithOptionalForce(entity, "".length() != 0);
                "".length();
                if (3 == -1) {
                    throw null;
                }
            }
        }
        else {
            BlockPos blockPos;
            if (n == " ".length()) {
                blockPos = world.getSpawnPoint();
                "".length();
                if (3 != 3) {
                    throw null;
                }
            }
            else {
                blockPos = world.getSpawnCoordinate();
            }
            n3 = blockPos.getX();
            entity.posY = blockPos.getY();
            n4 = blockPos.getZ();
            entity.setLocationAndAngles(n3, entity.posY, n4, 90.0f, 0.0f);
            if (entity.isEntityAlive()) {
                worldServer.updateEntityWithOptionalForce(entity, "".length() != 0);
            }
        }
        worldServer.theProfiler.endSection();
        if (n != " ".length()) {
            worldServer.theProfiler.startSection(ServerConfigurationManager.I[0xBB ^ 0xA7]);
            final double n5 = MathHelper.clamp_int((int)n3, -(4838918 + 7746157 + 2878399 + 14536398), 2639803 + 23927915 - 11494340 + 14926494);
            final double n6 = MathHelper.clamp_int((int)n4, -(22383336 + 18672309 - 18528616 + 7472843), 9059066 + 19182996 - 16775273 + 18533083);
            if (entity.isEntityAlive()) {
                entity.setLocationAndAngles(n5, entity.posY, n6, entity.rotationYaw, entity.rotationPitch);
                world.getDefaultTeleporter().placeInPortal(entity, rotationYaw);
                world.spawnEntityInWorld(entity);
                world.updateEntityWithOptionalForce(entity, "".length() != 0);
            }
            worldServer.theProfiler.endSection();
        }
        entity.setWorld(world);
    }
    
    public StatisticsFile getPlayerStatsFile(final EntityPlayer entityPlayer) {
        final UUID uniqueID = entityPlayer.getUniqueID();
        StatisticsFile statisticsFile;
        if (uniqueID == null) {
            statisticsFile = null;
            "".length();
            if (2 <= 1) {
                throw null;
            }
        }
        else {
            statisticsFile = this.playerStatFiles.get(uniqueID);
        }
        StatisticsFile statisticsFile2 = statisticsFile;
        if (statisticsFile2 == null) {
            final File file = new File(this.mcServer.worldServerForDimension("".length()).getSaveHandler().getWorldDirectory(), ServerConfigurationManager.I[0x94 ^ 0xB7]);
            final File file2 = new File(file, String.valueOf(uniqueID.toString()) + ServerConfigurationManager.I[0x21 ^ 0x5]);
            if (!file2.exists()) {
                final File file3 = new File(file, String.valueOf(entityPlayer.getName()) + ServerConfigurationManager.I[0xC ^ 0x29]);
                if (file3.exists() && file3.isFile()) {
                    file3.renameTo(file2);
                }
            }
            statisticsFile2 = new StatisticsFile(this.mcServer, file2);
            statisticsFile2.readStatFile();
            this.playerStatFiles.put(uniqueID, statisticsFile2);
        }
        return statisticsFile2;
    }
    
    public void onTick() {
        final int playerPingIndex = this.playerPingIndex + " ".length();
        this.playerPingIndex = playerPingIndex;
        if (playerPingIndex > 268 + 375 - 422 + 379) {
            this.sendPacketToAllPlayers(new S38PacketPlayerListItem(S38PacketPlayerListItem.Action.UPDATE_LATENCY, this.playerEntityList));
            this.playerPingIndex = "".length();
        }
    }
    
    public BanList getBannedIPs() {
        return this.bannedIPs;
    }
    
    public int getEntityViewDistance() {
        return PlayerManager.getFurthestViewableBlock(this.getViewDistance());
    }
    
    public String[] getOppedPlayerNames() {
        return this.ops.getKeys();
    }
    
    public void sendPacketToAllPlayers(final Packet packet) {
        int i = "".length();
        "".length();
        if (true != true) {
            throw null;
        }
        while (i < this.playerEntityList.size()) {
            this.playerEntityList.get(i).playerNetServerHandler.sendPacket(packet);
            ++i;
        }
    }
    
    public void sendChatMsg(final IChatComponent chatComponent) {
        this.sendChatMsgImpl(chatComponent, " ".length() != 0);
    }
    
    public void sendToAllNear(final double n, final double n2, final double n3, final double n4, final int n5, final Packet packet) {
        this.sendToAllNearExcept(null, n, n2, n3, n4, n5, packet);
    }
    
    public String[] getAvailablePlayerDat() {
        return this.mcServer.worldServers["".length()].getSaveHandler().getPlayerNBTManager().getAvailablePlayerDat();
    }
    
    public void addWhitelistedPlayer(final GameProfile gameProfile) {
        ((UserList<K, UserListWhitelistEntry>)this.whiteListedPlayers).addEntry(new UserListWhitelistEntry(gameProfile));
    }
    
    public boolean canJoin(final GameProfile gameProfile) {
        if (this.whiteListEnforced && !((UserList<GameProfile, V>)this.ops).hasEntry(gameProfile) && !((UserList<GameProfile, V>)this.whiteListedPlayers).hasEntry(gameProfile)) {
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
    
    public String allowUserToConnect(final SocketAddress socketAddress, final GameProfile gameProfile) {
        if (this.bannedPlayers.isBanned(gameProfile)) {
            final UserListBansEntry userListBansEntry = this.bannedPlayers.getEntry(gameProfile);
            String s = ServerConfigurationManager.I[0x3D ^ 0x29] + userListBansEntry.getBanReason();
            if (userListBansEntry.getBanEndDate() != null) {
                s = String.valueOf(s) + ServerConfigurationManager.I[0x9C ^ 0x89] + ServerConfigurationManager.dateFormat.format(userListBansEntry.getBanEndDate());
            }
            return s;
        }
        if (!this.canJoin(gameProfile)) {
            return ServerConfigurationManager.I[0xBA ^ 0xAC];
        }
        if (this.bannedIPs.isBanned(socketAddress)) {
            final IPBanEntry banEntry = this.bannedIPs.getBanEntry(socketAddress);
            String s2 = ServerConfigurationManager.I[0x91 ^ 0x86] + banEntry.getBanReason();
            if (banEntry.getBanEndDate() != null) {
                s2 = String.valueOf(s2) + ServerConfigurationManager.I[0x11 ^ 0x9] + ServerConfigurationManager.dateFormat.format(banEntry.getBanEndDate());
            }
            return s2;
        }
        String s3;
        if (this.playerEntityList.size() >= this.maxPlayers && !this.func_183023_f(gameProfile)) {
            s3 = ServerConfigurationManager.I[0x79 ^ 0x60];
            "".length();
            if (4 < 2) {
                throw null;
            }
        }
        else {
            s3 = null;
        }
        return s3;
    }
    
    public EntityPlayerMP recreatePlayerEntity(final EntityPlayerMP entityPlayerMP, final int dimension, final boolean b) {
        entityPlayerMP.getServerForPlayer().getEntityTracker().removePlayerFromTrackers(entityPlayerMP);
        entityPlayerMP.getServerForPlayer().getEntityTracker().untrackEntity(entityPlayerMP);
        entityPlayerMP.getServerForPlayer().getPlayerManager().removePlayer(entityPlayerMP);
        this.playerEntityList.remove(entityPlayerMP);
        this.mcServer.worldServerForDimension(entityPlayerMP.dimension).removePlayerEntityDangerously(entityPlayerMP);
        final BlockPos bedLocation = entityPlayerMP.getBedLocation();
        final boolean spawnForced = entityPlayerMP.isSpawnForced();
        entityPlayerMP.dimension = dimension;
        ItemInWorldManager itemInWorldManager;
        if (this.mcServer.isDemo()) {
            itemInWorldManager = new DemoWorldManager(this.mcServer.worldServerForDimension(entityPlayerMP.dimension));
            "".length();
            if (-1 == 0) {
                throw null;
            }
        }
        else {
            itemInWorldManager = new ItemInWorldManager(this.mcServer.worldServerForDimension(entityPlayerMP.dimension));
        }
        final EntityPlayerMP entityPlayerMP2 = new EntityPlayerMP(this.mcServer, this.mcServer.worldServerForDimension(entityPlayerMP.dimension), entityPlayerMP.getGameProfile(), itemInWorldManager);
        entityPlayerMP2.playerNetServerHandler = entityPlayerMP.playerNetServerHandler;
        entityPlayerMP2.clonePlayer(entityPlayerMP, b);
        entityPlayerMP2.setEntityId(entityPlayerMP.getEntityId());
        entityPlayerMP2.func_174817_o(entityPlayerMP);
        final WorldServer worldServerForDimension = this.mcServer.worldServerForDimension(entityPlayerMP.dimension);
        this.setPlayerGameTypeBasedOnOther(entityPlayerMP2, entityPlayerMP, worldServerForDimension);
        if (bedLocation != null) {
            final BlockPos bedSpawnLocation = EntityPlayer.getBedSpawnLocation(this.mcServer.worldServerForDimension(entityPlayerMP.dimension), bedLocation, spawnForced);
            if (bedSpawnLocation != null) {
                entityPlayerMP2.setLocationAndAngles(bedSpawnLocation.getX() + 0.5f, bedSpawnLocation.getY() + 0.1f, bedSpawnLocation.getZ() + 0.5f, 0.0f, 0.0f);
                entityPlayerMP2.setSpawnPoint(bedLocation, spawnForced);
                "".length();
                if (-1 >= 2) {
                    throw null;
                }
            }
            else {
                entityPlayerMP2.playerNetServerHandler.sendPacket(new S2BPacketChangeGameState("".length(), 0.0f));
            }
        }
        worldServerForDimension.theChunkProviderServer.loadChunk((int)entityPlayerMP2.posX >> (0x65 ^ 0x61), (int)entityPlayerMP2.posZ >> (0x8C ^ 0x88));
        "".length();
        if (1 >= 3) {
            throw null;
        }
        while (!worldServerForDimension.getCollidingBoundingBoxes(entityPlayerMP2, entityPlayerMP2.getEntityBoundingBox()).isEmpty() && entityPlayerMP2.posY < 256.0) {
            entityPlayerMP2.setPosition(entityPlayerMP2.posX, entityPlayerMP2.posY + 1.0, entityPlayerMP2.posZ);
        }
        entityPlayerMP2.playerNetServerHandler.sendPacket(new S07PacketRespawn(entityPlayerMP2.dimension, entityPlayerMP2.worldObj.getDifficulty(), entityPlayerMP2.worldObj.getWorldInfo().getTerrainType(), entityPlayerMP2.theItemInWorldManager.getGameType()));
        final BlockPos spawnPoint = worldServerForDimension.getSpawnPoint();
        entityPlayerMP2.playerNetServerHandler.setPlayerLocation(entityPlayerMP2.posX, entityPlayerMP2.posY, entityPlayerMP2.posZ, entityPlayerMP2.rotationYaw, entityPlayerMP2.rotationPitch);
        entityPlayerMP2.playerNetServerHandler.sendPacket(new S05PacketSpawnPosition(spawnPoint));
        entityPlayerMP2.playerNetServerHandler.sendPacket(new S1FPacketSetExperience(entityPlayerMP2.experience, entityPlayerMP2.experienceTotal, entityPlayerMP2.experienceLevel));
        this.updateTimeAndWeatherForPlayer(entityPlayerMP2, worldServerForDimension);
        worldServerForDimension.getPlayerManager().addPlayer(entityPlayerMP2);
        worldServerForDimension.spawnEntityInWorld(entityPlayerMP2);
        this.playerEntityList.add(entityPlayerMP2);
        this.uuidToPlayerMap.put(entityPlayerMP2.getUniqueID(), entityPlayerMP2);
        entityPlayerMP2.addSelfToInternalCraftingInventory();
        entityPlayerMP2.setHealth(entityPlayerMP2.getHealth());
        return entityPlayerMP2;
    }
    
    public void transferPlayerToDimension(final EntityPlayerMP entityPlayerMP, final int dimension) {
        final int dimension2 = entityPlayerMP.dimension;
        final WorldServer worldServerForDimension = this.mcServer.worldServerForDimension(entityPlayerMP.dimension);
        entityPlayerMP.dimension = dimension;
        final WorldServer worldServerForDimension2 = this.mcServer.worldServerForDimension(entityPlayerMP.dimension);
        entityPlayerMP.playerNetServerHandler.sendPacket(new S07PacketRespawn(entityPlayerMP.dimension, entityPlayerMP.worldObj.getDifficulty(), entityPlayerMP.worldObj.getWorldInfo().getTerrainType(), entityPlayerMP.theItemInWorldManager.getGameType()));
        worldServerForDimension.removePlayerEntityDangerously(entityPlayerMP);
        entityPlayerMP.isDead = ("".length() != 0);
        this.transferEntityToWorld(entityPlayerMP, dimension2, worldServerForDimension, worldServerForDimension2);
        this.preparePlayer(entityPlayerMP, worldServerForDimension);
        entityPlayerMP.playerNetServerHandler.setPlayerLocation(entityPlayerMP.posX, entityPlayerMP.posY, entityPlayerMP.posZ, entityPlayerMP.rotationYaw, entityPlayerMP.rotationPitch);
        entityPlayerMP.theItemInWorldManager.setWorld(worldServerForDimension2);
        this.updateTimeAndWeatherForPlayer(entityPlayerMP, worldServerForDimension2);
        this.syncPlayerInventory(entityPlayerMP);
        final Iterator<PotionEffect> iterator = entityPlayerMP.getActivePotionEffects().iterator();
        "".length();
        if (false) {
            throw null;
        }
        while (iterator.hasNext()) {
            entityPlayerMP.playerNetServerHandler.sendPacket(new S1DPacketEntityEffect(entityPlayerMP.getEntityId(), iterator.next()));
        }
    }
}

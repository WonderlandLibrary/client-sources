package net.minecraft.src;

import java.text.*;
import net.minecraft.server.*;
import java.io.*;
import java.net.*;
import java.util.*;

public abstract class ServerConfigurationManager
{
    private static final SimpleDateFormat dateFormat;
    private final MinecraftServer mcServer;
    public final List playerEntityList;
    private final BanList bannedPlayers;
    private final BanList bannedIPs;
    private Set ops;
    private Set whiteListedPlayers;
    private IPlayerFileData playerNBTManagerObj;
    private boolean whiteListEnforced;
    protected int maxPlayers;
    protected int viewDistance;
    private EnumGameType gameType;
    private boolean commandsAllowedForAll;
    private int playerPingIndex;
    
    static {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
    }
    
    public ServerConfigurationManager(final MinecraftServer par1MinecraftServer) {
        this.playerEntityList = new ArrayList();
        this.bannedPlayers = new BanList(new File("banned-players.txt"));
        this.bannedIPs = new BanList(new File("banned-ips.txt"));
        this.ops = new HashSet();
        this.whiteListedPlayers = new HashSet();
        this.playerPingIndex = 0;
        this.mcServer = par1MinecraftServer;
        this.bannedPlayers.setListActive(false);
        this.bannedIPs.setListActive(false);
        this.maxPlayers = 8;
    }
    
    public void initializeConnectionToPlayer(final INetworkManager par1INetworkManager, final EntityPlayerMP par2EntityPlayerMP) {
        final NBTTagCompound var3 = this.readPlayerDataFromFile(par2EntityPlayerMP);
        par2EntityPlayerMP.setWorld(this.mcServer.worldServerForDimension(par2EntityPlayerMP.dimension));
        par2EntityPlayerMP.theItemInWorldManager.setWorld((WorldServer)par2EntityPlayerMP.worldObj);
        String var4 = "local";
        if (par1INetworkManager.getSocketAddress() != null) {
            var4 = par1INetworkManager.getSocketAddress().toString();
        }
        this.mcServer.getLogAgent().logInfo(String.valueOf(par2EntityPlayerMP.username) + "[" + var4 + "] logged in with entity id " + par2EntityPlayerMP.entityId + " at (" + par2EntityPlayerMP.posX + ", " + par2EntityPlayerMP.posY + ", " + par2EntityPlayerMP.posZ + ")");
        final WorldServer var5 = this.mcServer.worldServerForDimension(par2EntityPlayerMP.dimension);
        final ChunkCoordinates var6 = var5.getSpawnPoint();
        this.func_72381_a(par2EntityPlayerMP, null, var5);
        final NetServerHandler var7 = new NetServerHandler(this.mcServer, par1INetworkManager, par2EntityPlayerMP);
        var7.sendPacketToPlayer(new Packet1Login(par2EntityPlayerMP.entityId, var5.getWorldInfo().getTerrainType(), par2EntityPlayerMP.theItemInWorldManager.getGameType(), var5.getWorldInfo().isHardcoreModeEnabled(), var5.provider.dimensionId, var5.difficultySetting, var5.getHeight(), this.getMaxPlayers()));
        var7.sendPacketToPlayer(new Packet6SpawnPosition(var6.posX, var6.posY, var6.posZ));
        var7.sendPacketToPlayer(new Packet202PlayerAbilities(par2EntityPlayerMP.capabilities));
        var7.sendPacketToPlayer(new Packet16BlockItemSwitch(par2EntityPlayerMP.inventory.currentItem));
        this.func_96456_a((ServerScoreboard)var5.getScoreboard(), par2EntityPlayerMP);
        this.updateTimeAndWeatherForPlayer(par2EntityPlayerMP, var5);
        this.sendPacketToAllPlayers(new Packet3Chat(EnumChatFormatting.YELLOW + par2EntityPlayerMP.getTranslatedEntityName() + EnumChatFormatting.YELLOW + " joined the game."));
        this.playerLoggedIn(par2EntityPlayerMP);
        var7.setPlayerLocation(par2EntityPlayerMP.posX, par2EntityPlayerMP.posY, par2EntityPlayerMP.posZ, par2EntityPlayerMP.rotationYaw, par2EntityPlayerMP.rotationPitch);
        this.mcServer.getNetworkThread().addPlayer(var7);
        var7.sendPacketToPlayer(new Packet4UpdateTime(var5.getTotalWorldTime(), var5.getWorldTime()));
        if (this.mcServer.getTexturePack().length() > 0) {
            par2EntityPlayerMP.requestTexturePackLoad(this.mcServer.getTexturePack(), this.mcServer.textureSize());
        }
        for (final PotionEffect var9 : par2EntityPlayerMP.getActivePotionEffects()) {
            var7.sendPacketToPlayer(new Packet41EntityEffect(par2EntityPlayerMP.entityId, var9));
        }
        par2EntityPlayerMP.addSelfToInternalCraftingInventory();
        if (var3 != null && var3.hasKey("Riding")) {
            final Entity var10 = EntityList.createEntityFromNBT(var3.getCompoundTag("Riding"), var5);
            if (var10 != null) {
                var10.field_98038_p = true;
                var5.spawnEntityInWorld(var10);
                par2EntityPlayerMP.mountEntity(var10);
                var10.field_98038_p = false;
            }
        }
    }
    
    protected void func_96456_a(final ServerScoreboard par1ServerScoreboard, final EntityPlayerMP par2EntityPlayerMP) {
        final HashSet var3 = new HashSet();
        for (final ScorePlayerTeam var5 : par1ServerScoreboard.func_96525_g()) {
            par2EntityPlayerMP.playerNetServerHandler.sendPacketToPlayer(new Packet209SetPlayerTeam(var5, 0));
        }
        for (int var6 = 0; var6 < 3; ++var6) {
            final ScoreObjective var7 = par1ServerScoreboard.func_96539_a(var6);
            if (var7 != null && !var3.contains(var7)) {
                final List var8 = par1ServerScoreboard.func_96550_d(var7);
                for (final Packet var10 : var8) {
                    par2EntityPlayerMP.playerNetServerHandler.sendPacketToPlayer(var10);
                }
                var3.add(var7);
            }
        }
    }
    
    public void setPlayerManager(final WorldServer[] par1ArrayOfWorldServer) {
        this.playerNBTManagerObj = par1ArrayOfWorldServer[0].getSaveHandler().getSaveHandler();
    }
    
    public void func_72375_a(final EntityPlayerMP par1EntityPlayerMP, final WorldServer par2WorldServer) {
        final WorldServer var3 = par1EntityPlayerMP.getServerForPlayer();
        if (par2WorldServer != null) {
            par2WorldServer.getPlayerManager().removePlayer(par1EntityPlayerMP);
        }
        var3.getPlayerManager().addPlayer(par1EntityPlayerMP);
        var3.theChunkProviderServer.loadChunk((int)par1EntityPlayerMP.posX >> 4, (int)par1EntityPlayerMP.posZ >> 4);
    }
    
    public int getEntityViewDistance() {
        return PlayerManager.getFurthestViewableBlock(this.getViewDistance());
    }
    
    public NBTTagCompound readPlayerDataFromFile(final EntityPlayerMP par1EntityPlayerMP) {
        final NBTTagCompound var2 = this.mcServer.worldServers[0].getWorldInfo().getPlayerNBTTagCompound();
        NBTTagCompound var3;
        if (par1EntityPlayerMP.getCommandSenderName().equals(this.mcServer.getServerOwner()) && var2 != null) {
            par1EntityPlayerMP.readFromNBT(var2);
            var3 = var2;
            System.out.println("loading single player");
        }
        else {
            var3 = this.playerNBTManagerObj.readPlayerData(par1EntityPlayerMP);
        }
        return var3;
    }
    
    protected void writePlayerData(final EntityPlayerMP par1EntityPlayerMP) {
        this.playerNBTManagerObj.writePlayerData(par1EntityPlayerMP);
    }
    
    public void playerLoggedIn(final EntityPlayerMP par1EntityPlayerMP) {
        this.sendPacketToAllPlayers(new Packet201PlayerInfo(par1EntityPlayerMP.username, true, 1000));
        this.playerEntityList.add(par1EntityPlayerMP);
        final WorldServer var2 = this.mcServer.worldServerForDimension(par1EntityPlayerMP.dimension);
        var2.spawnEntityInWorld(par1EntityPlayerMP);
        this.func_72375_a(par1EntityPlayerMP, null);
        for (int var3 = 0; var3 < this.playerEntityList.size(); ++var3) {
            final EntityPlayerMP var4 = this.playerEntityList.get(var3);
            par1EntityPlayerMP.playerNetServerHandler.sendPacketToPlayer(new Packet201PlayerInfo(var4.username, true, var4.ping));
        }
    }
    
    public void serverUpdateMountedMovingPlayer(final EntityPlayerMP par1EntityPlayerMP) {
        par1EntityPlayerMP.getServerForPlayer().getPlayerManager().updateMountedMovingPlayer(par1EntityPlayerMP);
    }
    
    public void playerLoggedOut(final EntityPlayerMP par1EntityPlayerMP) {
        this.writePlayerData(par1EntityPlayerMP);
        final WorldServer var2 = par1EntityPlayerMP.getServerForPlayer();
        if (par1EntityPlayerMP.ridingEntity != null) {
            var2.removeEntity(par1EntityPlayerMP.ridingEntity);
            System.out.println("removing player mount");
        }
        var2.removeEntity(par1EntityPlayerMP);
        var2.getPlayerManager().removePlayer(par1EntityPlayerMP);
        this.playerEntityList.remove(par1EntityPlayerMP);
        this.sendPacketToAllPlayers(new Packet201PlayerInfo(par1EntityPlayerMP.username, false, 9999));
    }
    
    public String allowUserToConnect(final SocketAddress par1SocketAddress, final String par2Str) {
        if (this.bannedPlayers.isBanned(par2Str)) {
            final BanEntry var6 = this.bannedPlayers.getBannedList().get(par2Str);
            String var7 = "You are banned from this server!\nReason: " + var6.getBanReason();
            if (var6.getBanEndDate() != null) {
                var7 = String.valueOf(var7) + "\nYour ban will be removed on " + ServerConfigurationManager.dateFormat.format(var6.getBanEndDate());
            }
            return var7;
        }
        if (!this.isAllowedToLogin(par2Str)) {
            return "You are not white-listed on this server!";
        }
        String var8 = par1SocketAddress.toString();
        var8 = var8.substring(var8.indexOf("/") + 1);
        var8 = var8.substring(0, var8.indexOf(":"));
        if (this.bannedIPs.isBanned(var8)) {
            final BanEntry var9 = this.bannedIPs.getBannedList().get(var8);
            String var10 = "Your IP address is banned from this server!\nReason: " + var9.getBanReason();
            if (var9.getBanEndDate() != null) {
                var10 = String.valueOf(var10) + "\nYour ban will be removed on " + ServerConfigurationManager.dateFormat.format(var9.getBanEndDate());
            }
            return var10;
        }
        return (this.playerEntityList.size() >= this.maxPlayers) ? "The server is full!" : null;
    }
    
    public EntityPlayerMP createPlayerForUser(final String par1Str) {
        final ArrayList var2 = new ArrayList();
        for (int var3 = 0; var3 < this.playerEntityList.size(); ++var3) {
            final EntityPlayerMP var4 = this.playerEntityList.get(var3);
            if (var4.username.equalsIgnoreCase(par1Str)) {
                var2.add(var4);
            }
        }
        final Iterator var5 = var2.iterator();
        while (var5.hasNext()) {
            final EntityPlayerMP var4 = var5.next();
            var4.playerNetServerHandler.kickPlayerFromServer("You logged in from another location");
        }
        Object var6;
        if (this.mcServer.isDemo()) {
            var6 = new DemoWorldManager(this.mcServer.worldServerForDimension(0));
        }
        else {
            var6 = new ItemInWorldManager(this.mcServer.worldServerForDimension(0));
        }
        return new EntityPlayerMP(this.mcServer, this.mcServer.worldServerForDimension(0), par1Str, (ItemInWorldManager)var6);
    }
    
    public EntityPlayerMP respawnPlayer(final EntityPlayerMP par1EntityPlayerMP, final int par2, final boolean par3) {
        par1EntityPlayerMP.getServerForPlayer().getEntityTracker().removePlayerFromTrackers(par1EntityPlayerMP);
        par1EntityPlayerMP.getServerForPlayer().getEntityTracker().removeEntityFromAllTrackingPlayers(par1EntityPlayerMP);
        par1EntityPlayerMP.getServerForPlayer().getPlayerManager().removePlayer(par1EntityPlayerMP);
        this.playerEntityList.remove(par1EntityPlayerMP);
        this.mcServer.worldServerForDimension(par1EntityPlayerMP.dimension).removePlayerEntityDangerously(par1EntityPlayerMP);
        final ChunkCoordinates var4 = par1EntityPlayerMP.getBedLocation();
        final boolean var5 = par1EntityPlayerMP.isSpawnForced();
        par1EntityPlayerMP.dimension = par2;
        Object var6;
        if (this.mcServer.isDemo()) {
            var6 = new DemoWorldManager(this.mcServer.worldServerForDimension(par1EntityPlayerMP.dimension));
        }
        else {
            var6 = new ItemInWorldManager(this.mcServer.worldServerForDimension(par1EntityPlayerMP.dimension));
        }
        final EntityPlayerMP var7 = new EntityPlayerMP(this.mcServer, this.mcServer.worldServerForDimension(par1EntityPlayerMP.dimension), par1EntityPlayerMP.username, (ItemInWorldManager)var6);
        var7.playerNetServerHandler = par1EntityPlayerMP.playerNetServerHandler;
        var7.clonePlayer(par1EntityPlayerMP, par3);
        var7.entityId = par1EntityPlayerMP.entityId;
        final WorldServer var8 = this.mcServer.worldServerForDimension(par1EntityPlayerMP.dimension);
        this.func_72381_a(var7, par1EntityPlayerMP, var8);
        if (var4 != null) {
            final ChunkCoordinates var9 = EntityPlayer.verifyRespawnCoordinates(this.mcServer.worldServerForDimension(par1EntityPlayerMP.dimension), var4, var5);
            if (var9 != null) {
                var7.setLocationAndAngles(var9.posX + 0.5f, var9.posY + 0.1f, var9.posZ + 0.5f, 0.0f, 0.0f);
                var7.setSpawnChunk(var4, var5);
            }
            else {
                var7.playerNetServerHandler.sendPacketToPlayer(new Packet70GameEvent(0, 0));
            }
        }
        var8.theChunkProviderServer.loadChunk((int)var7.posX >> 4, (int)var7.posZ >> 4);
        while (!var8.getCollidingBoundingBoxes(var7, var7.boundingBox).isEmpty()) {
            var7.setPosition(var7.posX, var7.posY + 1.0, var7.posZ);
        }
        var7.playerNetServerHandler.sendPacketToPlayer(new Packet9Respawn(var7.dimension, (byte)var7.worldObj.difficultySetting, var7.worldObj.getWorldInfo().getTerrainType(), var7.worldObj.getHeight(), var7.theItemInWorldManager.getGameType()));
        final ChunkCoordinates var9 = var8.getSpawnPoint();
        var7.playerNetServerHandler.setPlayerLocation(var7.posX, var7.posY, var7.posZ, var7.rotationYaw, var7.rotationPitch);
        var7.playerNetServerHandler.sendPacketToPlayer(new Packet6SpawnPosition(var9.posX, var9.posY, var9.posZ));
        var7.playerNetServerHandler.sendPacketToPlayer(new Packet43Experience(var7.experience, var7.experienceTotal, var7.experienceLevel));
        this.updateTimeAndWeatherForPlayer(var7, var8);
        var8.getPlayerManager().addPlayer(var7);
        var8.spawnEntityInWorld(var7);
        this.playerEntityList.add(var7);
        var7.addSelfToInternalCraftingInventory();
        var7.setEntityHealth(var7.getHealth());
        return var7;
    }
    
    public void transferPlayerToDimension(final EntityPlayerMP par1EntityPlayerMP, final int par2) {
        final int var3 = par1EntityPlayerMP.dimension;
        final WorldServer var4 = this.mcServer.worldServerForDimension(par1EntityPlayerMP.dimension);
        par1EntityPlayerMP.dimension = par2;
        final WorldServer var5 = this.mcServer.worldServerForDimension(par1EntityPlayerMP.dimension);
        par1EntityPlayerMP.playerNetServerHandler.sendPacketToPlayer(new Packet9Respawn(par1EntityPlayerMP.dimension, (byte)par1EntityPlayerMP.worldObj.difficultySetting, var5.getWorldInfo().getTerrainType(), var5.getHeight(), par1EntityPlayerMP.theItemInWorldManager.getGameType()));
        var4.removePlayerEntityDangerously(par1EntityPlayerMP);
        par1EntityPlayerMP.isDead = false;
        this.transferEntityToWorld(par1EntityPlayerMP, var3, var4, var5);
        this.func_72375_a(par1EntityPlayerMP, var4);
        par1EntityPlayerMP.playerNetServerHandler.setPlayerLocation(par1EntityPlayerMP.posX, par1EntityPlayerMP.posY, par1EntityPlayerMP.posZ, par1EntityPlayerMP.rotationYaw, par1EntityPlayerMP.rotationPitch);
        par1EntityPlayerMP.theItemInWorldManager.setWorld(var5);
        this.updateTimeAndWeatherForPlayer(par1EntityPlayerMP, var5);
        this.syncPlayerInventory(par1EntityPlayerMP);
        for (final PotionEffect var7 : par1EntityPlayerMP.getActivePotionEffects()) {
            par1EntityPlayerMP.playerNetServerHandler.sendPacketToPlayer(new Packet41EntityEffect(par1EntityPlayerMP.entityId, var7));
        }
    }
    
    public void transferEntityToWorld(final Entity par1Entity, final int par2, final WorldServer par3WorldServer, final WorldServer par4WorldServer) {
        double var5 = par1Entity.posX;
        double var6 = par1Entity.posZ;
        final double var7 = 8.0;
        final double var8 = par1Entity.posX;
        final double var9 = par1Entity.posY;
        final double var10 = par1Entity.posZ;
        final float var11 = par1Entity.rotationYaw;
        par3WorldServer.theProfiler.startSection("moving");
        if (par1Entity.dimension == -1) {
            var5 /= var7;
            var6 /= var7;
            par1Entity.setLocationAndAngles(var5, par1Entity.posY, var6, par1Entity.rotationYaw, par1Entity.rotationPitch);
            if (par1Entity.isEntityAlive()) {
                par3WorldServer.updateEntityWithOptionalForce(par1Entity, false);
            }
        }
        else if (par1Entity.dimension == 0) {
            var5 *= var7;
            var6 *= var7;
            par1Entity.setLocationAndAngles(var5, par1Entity.posY, var6, par1Entity.rotationYaw, par1Entity.rotationPitch);
            if (par1Entity.isEntityAlive()) {
                par3WorldServer.updateEntityWithOptionalForce(par1Entity, false);
            }
        }
        else {
            ChunkCoordinates var12;
            if (par2 == 1) {
                var12 = par4WorldServer.getSpawnPoint();
            }
            else {
                var12 = par4WorldServer.getEntrancePortalLocation();
            }
            var5 = var12.posX;
            par1Entity.posY = var12.posY;
            var6 = var12.posZ;
            par1Entity.setLocationAndAngles(var5, par1Entity.posY, var6, 90.0f, 0.0f);
            if (par1Entity.isEntityAlive()) {
                par3WorldServer.updateEntityWithOptionalForce(par1Entity, false);
            }
        }
        par3WorldServer.theProfiler.endSection();
        if (par2 != 1) {
            par3WorldServer.theProfiler.startSection("placing");
            var5 = MathHelper.clamp_int((int)var5, -29999872, 29999872);
            var6 = MathHelper.clamp_int((int)var6, -29999872, 29999872);
            if (par1Entity.isEntityAlive()) {
                par4WorldServer.spawnEntityInWorld(par1Entity);
                par1Entity.setLocationAndAngles(var5, par1Entity.posY, var6, par1Entity.rotationYaw, par1Entity.rotationPitch);
                par4WorldServer.updateEntityWithOptionalForce(par1Entity, false);
                par4WorldServer.getDefaultTeleporter().placeInPortal(par1Entity, var8, var9, var10, var11);
            }
            par3WorldServer.theProfiler.endSection();
        }
        par1Entity.setWorld(par4WorldServer);
    }
    
    public void sendPlayerInfoToAllPlayers() {
        if (++this.playerPingIndex > 600) {
            this.playerPingIndex = 0;
        }
        if (this.playerPingIndex < this.playerEntityList.size()) {
            final EntityPlayerMP var1 = this.playerEntityList.get(this.playerPingIndex);
            this.sendPacketToAllPlayers(new Packet201PlayerInfo(var1.username, true, var1.ping));
        }
    }
    
    public void sendPacketToAllPlayers(final Packet par1Packet) {
        for (int var2 = 0; var2 < this.playerEntityList.size(); ++var2) {
            this.playerEntityList.get(var2).playerNetServerHandler.sendPacketToPlayer(par1Packet);
        }
    }
    
    public void sendPacketToAllPlayersInDimension(final Packet par1Packet, final int par2) {
        for (int var3 = 0; var3 < this.playerEntityList.size(); ++var3) {
            final EntityPlayerMP var4 = this.playerEntityList.get(var3);
            if (var4.dimension == par2) {
                var4.playerNetServerHandler.sendPacketToPlayer(par1Packet);
            }
        }
    }
    
    public String getPlayerListAsString() {
        String var1 = "";
        for (int var2 = 0; var2 < this.playerEntityList.size(); ++var2) {
            if (var2 > 0) {
                var1 = String.valueOf(var1) + ", ";
            }
            var1 = String.valueOf(var1) + this.playerEntityList.get(var2).username;
        }
        return var1;
    }
    
    public String[] getAllUsernames() {
        final String[] var1 = new String[this.playerEntityList.size()];
        for (int var2 = 0; var2 < this.playerEntityList.size(); ++var2) {
            var1[var2] = this.playerEntityList.get(var2).username;
        }
        return var1;
    }
    
    public BanList getBannedPlayers() {
        return this.bannedPlayers;
    }
    
    public BanList getBannedIPs() {
        return this.bannedIPs;
    }
    
    public void addOp(final String par1Str) {
        this.ops.add(par1Str.toLowerCase());
    }
    
    public void removeOp(final String par1Str) {
        this.ops.remove(par1Str.toLowerCase());
    }
    
    public boolean isAllowedToLogin(String par1Str) {
        par1Str = par1Str.trim().toLowerCase();
        return !this.whiteListEnforced || this.ops.contains(par1Str) || this.whiteListedPlayers.contains(par1Str);
    }
    
    public boolean areCommandsAllowed(final String par1Str) {
        return this.ops.contains(par1Str.trim().toLowerCase()) || (this.mcServer.isSinglePlayer() && this.mcServer.worldServers[0].getWorldInfo().areCommandsAllowed() && this.mcServer.getServerOwner().equalsIgnoreCase(par1Str)) || this.commandsAllowedForAll;
    }
    
    public EntityPlayerMP getPlayerForUsername(final String par1Str) {
        for (final EntityPlayerMP var3 : this.playerEntityList) {
            if (var3.username.equalsIgnoreCase(par1Str)) {
                return var3;
            }
        }
        return null;
    }
    
    public List findPlayers(final ChunkCoordinates par1ChunkCoordinates, final int par2, final int par3, int par4, final int par5, final int par6, final int par7, final Map par8Map, String par9Str, String par10Str) {
        if (this.playerEntityList.isEmpty()) {
            return null;
        }
        Object var11 = new ArrayList();
        final boolean var12 = par4 < 0;
        final int var13 = par2 * par2;
        final int var14 = par3 * par3;
        par4 = MathHelper.abs_int(par4);
        for (int var15 = 0; var15 < this.playerEntityList.size(); ++var15) {
            final EntityPlayerMP var16 = this.playerEntityList.get(var15);
            if (par9Str != null) {
                final boolean var17 = par9Str.startsWith("!");
                if (var17) {
                    par9Str = par9Str.substring(1);
                }
                if (var17 == par9Str.equalsIgnoreCase(var16.getEntityName())) {
                    continue;
                }
            }
            if (par10Str != null) {
                final boolean var17 = par10Str.startsWith("!");
                if (var17) {
                    par10Str = par10Str.substring(1);
                }
                final ScorePlayerTeam var18 = var16.getTeam();
                final String var19 = (var18 == null) ? "" : var18.func_96661_b();
                if (var17 == par10Str.equalsIgnoreCase(var19)) {
                    continue;
                }
            }
            if (par1ChunkCoordinates != null && (par2 > 0 || par3 > 0)) {
                final float var20 = par1ChunkCoordinates.getDistanceSquaredToChunkCoordinates(var16.getPlayerCoordinates());
                if (par2 > 0 && var20 < var13) {
                    continue;
                }
                if (par3 > 0 && var20 > var14) {
                    continue;
                }
            }
            if (this.func_96457_a(var16, par8Map) && (par5 == EnumGameType.NOT_SET.getID() || par5 == var16.theItemInWorldManager.getGameType().getID()) && (par6 <= 0 || var16.experienceLevel >= par6) && var16.experienceLevel <= par7) {
                ((List)var11).add(var16);
            }
        }
        if (par1ChunkCoordinates != null) {
            Collections.sort((List<Object>)var11, new PlayerPositionComparator(par1ChunkCoordinates));
        }
        if (var12) {
            Collections.reverse((List<?>)var11);
        }
        if (par4 > 0) {
            var11 = ((List)var11).subList(0, Math.min(par4, ((List)var11).size()));
        }
        return (List)var11;
    }
    
    private boolean func_96457_a(final EntityPlayer par1EntityPlayer, final Map par2Map) {
        if (par2Map != null && par2Map.size() != 0) {
            for (final Map.Entry var4 : par2Map.entrySet()) {
                String var5 = var4.getKey();
                boolean var6 = false;
                if (var5.endsWith("_min") && var5.length() > 4) {
                    var6 = true;
                    var5 = var5.substring(0, var5.length() - 4);
                }
                final Scoreboard var7 = par1EntityPlayer.getWorldScoreboard();
                final ScoreObjective var8 = var7.getObjective(var5);
                if (var8 == null) {
                    return false;
                }
                final Score var9 = par1EntityPlayer.getWorldScoreboard().func_96529_a(par1EntityPlayer.getEntityName(), var8);
                final int var10 = var9.func_96652_c();
                if (var10 < var4.getValue() && var6) {
                    return false;
                }
                if (var10 > var4.getValue() && !var6) {
                    return false;
                }
            }
            return true;
        }
        return true;
    }
    
    public void sendToAllNear(final double par1, final double par3, final double par5, final double par7, final int par9, final Packet par10Packet) {
        this.sendToAllNearExcept(null, par1, par3, par5, par7, par9, par10Packet);
    }
    
    public void sendToAllNearExcept(final EntityPlayer par1EntityPlayer, final double par2, final double par4, final double par6, final double par8, final int par10, final Packet par11Packet) {
        for (int var12 = 0; var12 < this.playerEntityList.size(); ++var12) {
            final EntityPlayerMP var13 = this.playerEntityList.get(var12);
            if (var13 != par1EntityPlayer && var13.dimension == par10) {
                final double var14 = par2 - var13.posX;
                final double var15 = par4 - var13.posY;
                final double var16 = par6 - var13.posZ;
                if (var14 * var14 + var15 * var15 + var16 * var16 < par8 * par8) {
                    var13.playerNetServerHandler.sendPacketToPlayer(par11Packet);
                }
            }
        }
    }
    
    public void saveAllPlayerData() {
        for (int var1 = 0; var1 < this.playerEntityList.size(); ++var1) {
            this.writePlayerData(this.playerEntityList.get(var1));
        }
    }
    
    public void addToWhiteList(final String par1Str) {
        this.whiteListedPlayers.add(par1Str);
    }
    
    public void removeFromWhitelist(final String par1Str) {
        this.whiteListedPlayers.remove(par1Str);
    }
    
    public Set getWhiteListedPlayers() {
        return this.whiteListedPlayers;
    }
    
    public Set getOps() {
        return this.ops;
    }
    
    public void loadWhiteList() {
    }
    
    public void updateTimeAndWeatherForPlayer(final EntityPlayerMP par1EntityPlayerMP, final WorldServer par2WorldServer) {
        par1EntityPlayerMP.playerNetServerHandler.sendPacketToPlayer(new Packet4UpdateTime(par2WorldServer.getTotalWorldTime(), par2WorldServer.getWorldTime()));
        if (par2WorldServer.isRaining()) {
            par1EntityPlayerMP.playerNetServerHandler.sendPacketToPlayer(new Packet70GameEvent(1, 0));
        }
    }
    
    public void syncPlayerInventory(final EntityPlayerMP par1EntityPlayerMP) {
        par1EntityPlayerMP.sendContainerToPlayer(par1EntityPlayerMP.inventoryContainer);
        par1EntityPlayerMP.setPlayerHealthUpdated();
        par1EntityPlayerMP.playerNetServerHandler.sendPacketToPlayer(new Packet16BlockItemSwitch(par1EntityPlayerMP.inventory.currentItem));
    }
    
    public int getCurrentPlayerCount() {
        return this.playerEntityList.size();
    }
    
    public int getMaxPlayers() {
        return this.maxPlayers;
    }
    
    public String[] getAvailablePlayerDat() {
        return this.mcServer.worldServers[0].getSaveHandler().getSaveHandler().getAvailablePlayerDat();
    }
    
    public boolean isWhiteListEnabled() {
        return this.whiteListEnforced;
    }
    
    public void setWhiteListEnabled(final boolean par1) {
        this.whiteListEnforced = par1;
    }
    
    public List getPlayerList(final String par1Str) {
        final ArrayList var2 = new ArrayList();
        for (final EntityPlayerMP var4 : this.playerEntityList) {
            if (var4.getPlayerIP().equals(par1Str)) {
                var2.add(var4);
            }
        }
        return var2;
    }
    
    public int getViewDistance() {
        return this.viewDistance;
    }
    
    public MinecraftServer getServerInstance() {
        return this.mcServer;
    }
    
    public NBTTagCompound getHostPlayerData() {
        return null;
    }
    
    public void setGameType(final EnumGameType par1EnumGameType) {
        this.gameType = par1EnumGameType;
    }
    
    private void func_72381_a(final EntityPlayerMP par1EntityPlayerMP, final EntityPlayerMP par2EntityPlayerMP, final World par3World) {
        if (par2EntityPlayerMP != null) {
            par1EntityPlayerMP.theItemInWorldManager.setGameType(par2EntityPlayerMP.theItemInWorldManager.getGameType());
        }
        else if (this.gameType != null) {
            par1EntityPlayerMP.theItemInWorldManager.setGameType(this.gameType);
        }
        par1EntityPlayerMP.theItemInWorldManager.initializeGameType(par3World.getWorldInfo().getGameType());
    }
    
    public void setCommandsAllowedForAll(final boolean par1) {
        this.commandsAllowedForAll = par1;
    }
    
    public void removeAllPlayers() {
        while (!this.playerEntityList.isEmpty()) {
            this.playerEntityList.get(0).playerNetServerHandler.kickPlayerFromServer("Server closed");
        }
    }
    
    public void sendChatMsg(final String par1Str) {
        this.mcServer.logInfo(par1Str);
        this.sendPacketToAllPlayers(new Packet3Chat(par1Str));
    }
}

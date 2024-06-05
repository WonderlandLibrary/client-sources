package net.minecraft.server;

import java.security.*;
import java.io.*;
import java.text.*;
import java.util.concurrent.*;
import java.util.*;
import java.awt.*;
import net.minecraft.src.*;

public abstract class MinecraftServer implements ICommandSender, Runnable, IPlayerUsage
{
    private static MinecraftServer mcServer;
    private final ISaveFormat anvilConverterForAnvilFile;
    private final PlayerUsageSnooper usageSnooper;
    private final File anvilFile;
    private final List tickables;
    private final ICommandManager commandManager;
    public final Profiler theProfiler;
    private String hostname;
    private int serverPort;
    public WorldServer[] worldServers;
    private ServerConfigurationManager serverConfigManager;
    private boolean serverRunning;
    private boolean serverStopped;
    private int tickCounter;
    public String currentTask;
    public int percentDone;
    private boolean onlineMode;
    private boolean canSpawnAnimals;
    private boolean canSpawnNPCs;
    private boolean pvpEnabled;
    private boolean allowFlight;
    private String motd;
    private int buildLimit;
    private long lastSentPacketID;
    private long lastSentPacketSize;
    private long lastReceivedID;
    private long lastReceivedSize;
    public final long[] sentPacketCountArray;
    public final long[] sentPacketSizeArray;
    public final long[] receivedPacketCountArray;
    public final long[] receivedPacketSizeArray;
    public final long[] tickTimeArray;
    public long[][] timeOfLastDimensionTick;
    private KeyPair serverKeyPair;
    private String serverOwner;
    private String folderName;
    private String worldName;
    private boolean isDemo;
    private boolean enableBonusChest;
    private boolean worldIsBeingDeleted;
    private String texturePack;
    private boolean serverIsRunning;
    private long timeOfLastWarning;
    private String userMessage;
    private boolean startProfiling;
    private boolean field_104057_T;
    
    static {
        MinecraftServer.mcServer = null;
    }
    
    public MinecraftServer(final File par1File) {
        this.usageSnooper = new PlayerUsageSnooper("server", this);
        this.tickables = new ArrayList();
        this.theProfiler = new Profiler();
        this.serverPort = -1;
        this.serverRunning = true;
        this.serverStopped = false;
        this.tickCounter = 0;
        this.sentPacketCountArray = new long[100];
        this.sentPacketSizeArray = new long[100];
        this.receivedPacketCountArray = new long[100];
        this.receivedPacketSizeArray = new long[100];
        this.tickTimeArray = new long[100];
        this.texturePack = "";
        this.serverIsRunning = false;
        this.field_104057_T = false;
        MinecraftServer.mcServer = this;
        this.anvilFile = par1File;
        this.commandManager = new ServerCommandManager();
        this.anvilConverterForAnvilFile = new AnvilSaveConverter(par1File);
        this.registerDispenseBehaviors();
    }
    
    private void registerDispenseBehaviors() {
        DispenserBehaviors.func_96467_a();
    }
    
    protected abstract boolean startServer() throws IOException;
    
    protected void convertMapIfNeeded(final String par1Str) {
        if (this.getActiveAnvilConverter().isOldMapFormat(par1Str)) {
            this.getLogAgent().logInfo("Converting map!");
            this.setUserMessage("menu.convertingLevel");
            this.getActiveAnvilConverter().convertMapFormat(par1Str, new ConvertingProgressUpdate(this));
        }
    }
    
    protected synchronized void setUserMessage(final String par1Str) {
        this.userMessage = par1Str;
    }
    
    public synchronized String getUserMessage() {
        return this.userMessage;
    }
    
    protected void loadAllWorlds(final String par1Str, final String par2Str, final long par3, final WorldType par5WorldType, final String par6Str) {
        this.convertMapIfNeeded(par1Str);
        this.setUserMessage("menu.loadingLevel");
        this.worldServers = new WorldServer[3];
        this.timeOfLastDimensionTick = new long[this.worldServers.length][100];
        final ISaveHandler var7 = this.anvilConverterForAnvilFile.getSaveLoader(par1Str, true);
        final WorldInfo var8 = var7.loadWorldInfo();
        WorldSettings var9;
        if (var8 == null) {
            var9 = new WorldSettings(par3, this.getGameType(), this.canStructuresSpawn(), this.isHardcore(), par5WorldType);
            var9.func_82750_a(par6Str);
        }
        else {
            var9 = new WorldSettings(var8);
        }
        if (this.enableBonusChest) {
            var9.enableBonusChest();
        }
        for (int var10 = 0; var10 < this.worldServers.length; ++var10) {
            byte var11 = 0;
            if (var10 == 1) {
                var11 = -1;
            }
            if (var10 == 2) {
                var11 = 1;
            }
            if (var10 == 0) {
                if (this.isDemo()) {
                    this.worldServers[var10] = new DemoWorldServer(this, var7, par2Str, var11, this.theProfiler, this.getLogAgent());
                }
                else {
                    this.worldServers[var10] = new WorldServer(this, var7, par2Str, var11, var9, this.theProfiler, this.getLogAgent());
                }
            }
            else {
                this.worldServers[var10] = new WorldServerMulti(this, var7, par2Str, var11, var9, this.worldServers[0], this.theProfiler, this.getLogAgent());
            }
            this.worldServers[var10].addWorldAccess(new WorldManager(this, this.worldServers[var10]));
            if (!this.isSinglePlayer()) {
                this.worldServers[var10].getWorldInfo().setGameType(this.getGameType());
            }
            this.serverConfigManager.setPlayerManager(this.worldServers);
        }
        this.setDifficultyForAllWorlds(this.getDifficulty());
        this.initialWorldChunkLoad();
    }
    
    protected void initialWorldChunkLoad() {
        int var1 = 0;
        this.setUserMessage("menu.generatingTerrain");
        final byte var2 = 0;
        this.getLogAgent().logInfo("Preparing start region for level " + var2);
        final WorldServer var3 = this.worldServers[var2];
        final ChunkCoordinates var4 = var3.getSpawnPoint();
        long var5 = System.currentTimeMillis();
        for (int var6 = -192; var6 <= 192 && this.isServerRunning(); var6 += 16) {
            for (int var7 = -192; var7 <= 192 && this.isServerRunning(); var7 += 16) {
                final long var8 = System.currentTimeMillis();
                if (var8 - var5 > 1000L) {
                    this.outputPercentRemaining("Preparing spawn area", var1 * 100 / 625);
                    var5 = var8;
                }
                ++var1;
                var3.theChunkProviderServer.loadChunk(var4.posX + var6 >> 4, var4.posZ + var7 >> 4);
            }
        }
        this.clearCurrentTask();
    }
    
    public abstract boolean canStructuresSpawn();
    
    public abstract EnumGameType getGameType();
    
    public abstract int getDifficulty();
    
    public abstract boolean isHardcore();
    
    protected void outputPercentRemaining(final String par1Str, final int par2) {
        this.currentTask = par1Str;
        this.percentDone = par2;
        this.getLogAgent().logInfo(String.valueOf(par1Str) + ": " + par2 + "%");
    }
    
    protected void clearCurrentTask() {
        this.currentTask = null;
        this.percentDone = 0;
    }
    
    protected void saveAllWorlds(final boolean par1) {
        if (!this.worldIsBeingDeleted) {
            for (final WorldServer var5 : this.worldServers) {
                if (var5 != null) {
                    if (!par1) {
                        this.getLogAgent().logInfo("Saving chunks for level '" + var5.getWorldInfo().getWorldName() + "'/" + var5.provider.getDimensionName());
                    }
                    try {
                        var5.saveAllChunks(true, null);
                    }
                    catch (MinecraftException var6) {
                        this.getLogAgent().logWarning(var6.getMessage());
                    }
                }
            }
        }
    }
    
    public void stopServer() {
        if (!this.worldIsBeingDeleted) {
            this.getLogAgent().logInfo("Stopping server");
            if (this.getNetworkThread() != null) {
                this.getNetworkThread().stopListening();
            }
            if (this.serverConfigManager != null) {
                this.getLogAgent().logInfo("Saving players");
                this.serverConfigManager.saveAllPlayerData();
                this.serverConfigManager.removeAllPlayers();
            }
            this.getLogAgent().logInfo("Saving worlds");
            this.saveAllWorlds(false);
            for (int var1 = 0; var1 < this.worldServers.length; ++var1) {
                final WorldServer var2 = this.worldServers[var1];
                var2.flush();
            }
            if (this.usageSnooper != null && this.usageSnooper.isSnooperRunning()) {
                this.usageSnooper.stopSnooper();
            }
        }
    }
    
    public String getServerHostname() {
        return this.hostname;
    }
    
    public void setHostname(final String par1Str) {
        this.hostname = par1Str;
    }
    
    public boolean isServerRunning() {
        return this.serverRunning;
    }
    
    public void initiateShutdown() {
        this.serverRunning = false;
    }
    
    @Override
    public void run() {
        try {
            if (this.startServer()) {
                long var1 = System.currentTimeMillis();
                long var2 = 0L;
                while (this.serverRunning) {
                    final long var3 = System.currentTimeMillis();
                    long var4 = var3 - var1;
                    if (var4 > 2000L && var1 - this.timeOfLastWarning >= 15000L) {
                        this.getLogAgent().logWarning("Can't keep up! Did the system time change, or is the server overloaded?");
                        var4 = 2000L;
                        this.timeOfLastWarning = var1;
                    }
                    if (var4 < 0L) {
                        this.getLogAgent().logWarning("Time ran backwards! Did the system time change?");
                        var4 = 0L;
                    }
                    var2 += var4;
                    var1 = var3;
                    if (this.worldServers[0].areAllPlayersAsleep()) {
                        this.tick();
                        var2 = 0L;
                    }
                    else {
                        while (var2 > 50L) {
                            var2 -= 50L;
                            this.tick();
                        }
                    }
                    Thread.sleep(1L);
                    this.serverIsRunning = true;
                }
            }
            else {
                this.finalTick(null);
            }
        }
        catch (Throwable var5) {
            var5.printStackTrace();
            this.getLogAgent().logSevereException("Encountered an unexpected exception " + var5.getClass().getSimpleName(), var5);
            CrashReport var6 = null;
            if (var5 instanceof ReportedException) {
                var6 = this.addServerInfoToCrashReport(((ReportedException)var5).getCrashReport());
            }
            else {
                var6 = this.addServerInfoToCrashReport(new CrashReport("Exception in server tick loop", var5));
            }
            final File var7 = new File(new File(this.getDataDirectory(), "crash-reports"), "crash-" + new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss").format(new Date()) + "-server.txt");
            if (var6.saveToFile(var7, this.getLogAgent())) {
                this.getLogAgent().logSevere("This crash report has been saved to: " + var7.getAbsolutePath());
            }
            else {
                this.getLogAgent().logSevere("We were unable to save this crash report to disk.");
            }
            this.finalTick(var6);
            return;
        }
        finally {
            Label_0453: {
                try {
                    this.stopServer();
                    this.serverStopped = true;
                }
                catch (Throwable var8) {
                    var8.printStackTrace();
                    this.systemExitNow();
                    break Label_0453;
                }
                finally {
                    this.systemExitNow();
                }
                this.systemExitNow();
            }
        }
        try {
            this.stopServer();
            this.serverStopped = true;
        }
        catch (Throwable var8) {
            var8.printStackTrace();
            return;
        }
        finally {
            this.systemExitNow();
        }
        this.systemExitNow();
    }
    
    protected File getDataDirectory() {
        return new File(".");
    }
    
    protected void finalTick(final CrashReport par1CrashReport) {
    }
    
    protected void systemExitNow() {
    }
    
    public void tick() {
        final long var1 = System.nanoTime();
        AxisAlignedBB.getAABBPool().cleanPool();
        ++this.tickCounter;
        if (this.startProfiling) {
            this.startProfiling = false;
            this.theProfiler.profilingEnabled = true;
            this.theProfiler.clearProfiling();
        }
        this.theProfiler.startSection("root");
        this.updateTimeLightAndEntities();
        if (this.tickCounter % 900 == 0) {
            this.theProfiler.startSection("save");
            this.serverConfigManager.saveAllPlayerData();
            this.saveAllWorlds(true);
            this.theProfiler.endSection();
        }
        this.theProfiler.startSection("tallying");
        this.tickTimeArray[this.tickCounter % 100] = System.nanoTime() - var1;
        this.sentPacketCountArray[this.tickCounter % 100] = Packet.sentID - this.lastSentPacketID;
        this.lastSentPacketID = Packet.sentID;
        this.sentPacketSizeArray[this.tickCounter % 100] = Packet.sentSize - this.lastSentPacketSize;
        this.lastSentPacketSize = Packet.sentSize;
        this.receivedPacketCountArray[this.tickCounter % 100] = Packet.receivedID - this.lastReceivedID;
        this.lastReceivedID = Packet.receivedID;
        this.receivedPacketSizeArray[this.tickCounter % 100] = Packet.receivedSize - this.lastReceivedSize;
        this.lastReceivedSize = Packet.receivedSize;
        this.theProfiler.endSection();
        this.theProfiler.startSection("snooper");
        if (!this.usageSnooper.isSnooperRunning() && this.tickCounter > 100) {
            this.usageSnooper.startSnooper();
        }
        if (this.tickCounter % 6000 == 0) {
            this.usageSnooper.addMemoryStatsToSnooper();
        }
        this.theProfiler.endSection();
        this.theProfiler.endSection();
    }
    
    public void updateTimeLightAndEntities() {
        this.theProfiler.startSection("levels");
        for (int var1 = 0; var1 < this.worldServers.length; ++var1) {
            final long var2 = System.nanoTime();
            if (var1 == 0 || this.getAllowNether()) {
                final WorldServer var3 = this.worldServers[var1];
                this.theProfiler.startSection(var3.getWorldInfo().getWorldName());
                this.theProfiler.startSection("pools");
                var3.getWorldVec3Pool().clear();
                this.theProfiler.endSection();
                if (this.tickCounter % 20 == 0) {
                    this.theProfiler.startSection("timeSync");
                    this.serverConfigManager.sendPacketToAllPlayersInDimension(new Packet4UpdateTime(var3.getTotalWorldTime(), var3.getWorldTime()), var3.provider.dimensionId);
                    this.theProfiler.endSection();
                }
                this.theProfiler.startSection("tick");
                try {
                    var3.tick();
                }
                catch (Throwable var5) {
                    final CrashReport var4 = CrashReport.makeCrashReport(var5, "Exception ticking world");
                    var3.addWorldInfoToCrashReport(var4);
                    throw new ReportedException(var4);
                }
                try {
                    var3.updateEntities();
                }
                catch (Throwable var6) {
                    final CrashReport var4 = CrashReport.makeCrashReport(var6, "Exception ticking world entities");
                    var3.addWorldInfoToCrashReport(var4);
                    throw new ReportedException(var4);
                }
                this.theProfiler.endSection();
                this.theProfiler.startSection("tracker");
                var3.getEntityTracker().updateTrackedEntities();
                this.theProfiler.endSection();
                this.theProfiler.endSection();
            }
            this.timeOfLastDimensionTick[var1][this.tickCounter % 100] = System.nanoTime() - var2;
        }
        this.theProfiler.endStartSection("connection");
        this.getNetworkThread().networkTick();
        this.theProfiler.endStartSection("players");
        this.serverConfigManager.sendPlayerInfoToAllPlayers();
        this.theProfiler.endStartSection("tickables");
        for (int var1 = 0; var1 < this.tickables.size(); ++var1) {
            this.tickables.get(var1).update();
        }
        this.theProfiler.endSection();
    }
    
    public boolean getAllowNether() {
        return true;
    }
    
    public void startServerThread() {
        new ThreadMinecraftServer(this, "Server thread").start();
    }
    
    public File getFile(final String par1Str) {
        return new File(this.getDataDirectory(), par1Str);
    }
    
    public void logInfo(final String par1Str) {
        this.getLogAgent().logInfo(par1Str);
    }
    
    public void logWarning(final String par1Str) {
        this.getLogAgent().logWarning(par1Str);
    }
    
    public WorldServer worldServerForDimension(final int par1) {
        return (par1 == -1) ? this.worldServers[1] : ((par1 == 1) ? this.worldServers[2] : this.worldServers[0]);
    }
    
    public String getHostname() {
        return this.hostname;
    }
    
    public int getPort() {
        return this.serverPort;
    }
    
    public String getServerMOTD() {
        return this.motd;
    }
    
    public String getMinecraftVersion() {
        return "1.5.2";
    }
    
    public int getCurrentPlayerCount() {
        return this.serverConfigManager.getCurrentPlayerCount();
    }
    
    public int getMaxPlayers() {
        return this.serverConfigManager.getMaxPlayers();
    }
    
    public String[] getAllUsernames() {
        return this.serverConfigManager.getAllUsernames();
    }
    
    public String getPlugins() {
        return "";
    }
    
    public String executeCommand(final String par1Str) {
        RConConsoleSource.consoleBuffer.resetLog();
        this.commandManager.executeCommand(RConConsoleSource.consoleBuffer, par1Str);
        return RConConsoleSource.consoleBuffer.getChatBuffer();
    }
    
    public boolean isDebuggingEnabled() {
        return false;
    }
    
    public void logSevere(final String par1Str) {
        this.getLogAgent().logSevere(par1Str);
    }
    
    public void logDebug(final String par1Str) {
        if (this.isDebuggingEnabled()) {
            this.getLogAgent().logInfo(par1Str);
        }
    }
    
    public String getServerModName() {
        return "vanilla";
    }
    
    public CrashReport addServerInfoToCrashReport(final CrashReport par1CrashReport) {
        par1CrashReport.func_85056_g().addCrashSectionCallable("Profiler Position", new CallableIsServerModded(this));
        if (this.worldServers != null && this.worldServers.length > 0 && this.worldServers[0] != null) {
            par1CrashReport.func_85056_g().addCrashSectionCallable("Vec3 Pool Size", new CallableServerProfiler(this));
        }
        if (this.serverConfigManager != null) {
            par1CrashReport.func_85056_g().addCrashSectionCallable("Player Count", new CallableServerMemoryStats(this));
        }
        return par1CrashReport;
    }
    
    public List getPossibleCompletions(final ICommandSender par1ICommandSender, String par2Str) {
        final ArrayList var3 = new ArrayList();
        if (par2Str.startsWith("/")) {
            par2Str = par2Str.substring(1);
            final boolean var4 = !par2Str.contains(" ");
            final List var5 = this.commandManager.getPossibleCommands(par1ICommandSender, par2Str);
            if (var5 != null) {
                for (final String var7 : var5) {
                    if (var4) {
                        var3.add("/" + var7);
                    }
                    else {
                        var3.add(var7);
                    }
                }
            }
            return var3;
        }
        final String[] var8 = par2Str.split(" ", -1);
        final String var9 = var8[var8.length - 1];
        for (final String var13 : this.serverConfigManager.getAllUsernames()) {
            if (CommandBase.doesStringStartWith(var9, var13)) {
                var3.add(var13);
            }
        }
        return var3;
    }
    
    public static MinecraftServer getServer() {
        return MinecraftServer.mcServer;
    }
    
    @Override
    public String getCommandSenderName() {
        return "Server";
    }
    
    @Override
    public void sendChatToPlayer(final String par1Str) {
        this.getLogAgent().logInfo(StringUtils.stripControlCodes(par1Str));
    }
    
    @Override
    public boolean canCommandSenderUseCommand(final int par1, final String par2Str) {
        return true;
    }
    
    @Override
    public String translateString(final String par1Str, final Object... par2ArrayOfObj) {
        return StringTranslate.getInstance().translateKeyFormat(par1Str, par2ArrayOfObj);
    }
    
    public ICommandManager getCommandManager() {
        return this.commandManager;
    }
    
    public KeyPair getKeyPair() {
        return this.serverKeyPair;
    }
    
    public int getServerPort() {
        return this.serverPort;
    }
    
    public void setServerPort(final int par1) {
        this.serverPort = par1;
    }
    
    public String getServerOwner() {
        return this.serverOwner;
    }
    
    public void setServerOwner(final String par1Str) {
        this.serverOwner = par1Str;
    }
    
    public boolean isSinglePlayer() {
        return this.serverOwner != null;
    }
    
    public String getFolderName() {
        return this.folderName;
    }
    
    public void setFolderName(final String par1Str) {
        this.folderName = par1Str;
    }
    
    public void setWorldName(final String par1Str) {
        this.worldName = par1Str;
    }
    
    public String getWorldName() {
        return this.worldName;
    }
    
    public void setKeyPair(final KeyPair par1KeyPair) {
        this.serverKeyPair = par1KeyPair;
    }
    
    public void setDifficultyForAllWorlds(final int par1) {
        for (int var2 = 0; var2 < this.worldServers.length; ++var2) {
            final WorldServer var3 = this.worldServers[var2];
            if (var3 != null) {
                if (var3.getWorldInfo().isHardcoreModeEnabled()) {
                    var3.difficultySetting = 3;
                    var3.setAllowedSpawnTypes(true, true);
                }
                else if (this.isSinglePlayer()) {
                    var3.difficultySetting = par1;
                    var3.setAllowedSpawnTypes(var3.difficultySetting > 0, true);
                }
                else {
                    var3.difficultySetting = par1;
                    var3.setAllowedSpawnTypes(this.allowSpawnMonsters(), this.canSpawnAnimals);
                }
            }
        }
    }
    
    protected boolean allowSpawnMonsters() {
        return true;
    }
    
    public boolean isDemo() {
        return this.isDemo;
    }
    
    public void setDemo(final boolean par1) {
        this.isDemo = par1;
    }
    
    public void canCreateBonusChest(final boolean par1) {
        this.enableBonusChest = par1;
    }
    
    public ISaveFormat getActiveAnvilConverter() {
        return this.anvilConverterForAnvilFile;
    }
    
    public void deleteWorldAndStopServer() {
        this.worldIsBeingDeleted = true;
        this.getActiveAnvilConverter().flushCache();
        for (int var1 = 0; var1 < this.worldServers.length; ++var1) {
            final WorldServer var2 = this.worldServers[var1];
            if (var2 != null) {
                var2.flush();
            }
        }
        this.getActiveAnvilConverter().deleteWorldDirectory(this.worldServers[0].getSaveHandler().getWorldDirectoryName());
        this.initiateShutdown();
    }
    
    public String getTexturePack() {
        return this.texturePack;
    }
    
    public void setTexturePack(final String par1Str) {
        this.texturePack = par1Str;
    }
    
    @Override
    public void addServerStatsToSnooper(final PlayerUsageSnooper par1PlayerUsageSnooper) {
        par1PlayerUsageSnooper.addData("whitelist_enabled", false);
        par1PlayerUsageSnooper.addData("whitelist_count", 0);
        par1PlayerUsageSnooper.addData("players_current", this.getCurrentPlayerCount());
        par1PlayerUsageSnooper.addData("players_max", this.getMaxPlayers());
        par1PlayerUsageSnooper.addData("players_seen", this.serverConfigManager.getAvailablePlayerDat().length);
        par1PlayerUsageSnooper.addData("uses_auth", this.onlineMode);
        par1PlayerUsageSnooper.addData("gui_state", this.getGuiEnabled() ? "enabled" : "disabled");
        par1PlayerUsageSnooper.addData("avg_tick_ms", (int)(MathHelper.average(this.tickTimeArray) * 1.0E-6));
        par1PlayerUsageSnooper.addData("avg_sent_packet_count", (int)MathHelper.average(this.sentPacketCountArray));
        par1PlayerUsageSnooper.addData("avg_sent_packet_size", (int)MathHelper.average(this.sentPacketSizeArray));
        par1PlayerUsageSnooper.addData("avg_rec_packet_count", (int)MathHelper.average(this.receivedPacketCountArray));
        par1PlayerUsageSnooper.addData("avg_rec_packet_size", (int)MathHelper.average(this.receivedPacketSizeArray));
        int var2 = 0;
        for (int var3 = 0; var3 < this.worldServers.length; ++var3) {
            if (this.worldServers[var3] != null) {
                final WorldServer var4 = this.worldServers[var3];
                final WorldInfo var5 = var4.getWorldInfo();
                par1PlayerUsageSnooper.addData("world[" + var2 + "][dimension]", var4.provider.dimensionId);
                par1PlayerUsageSnooper.addData("world[" + var2 + "][mode]", var5.getGameType());
                par1PlayerUsageSnooper.addData("world[" + var2 + "][difficulty]", var4.difficultySetting);
                par1PlayerUsageSnooper.addData("world[" + var2 + "][hardcore]", var5.isHardcoreModeEnabled());
                par1PlayerUsageSnooper.addData("world[" + var2 + "][generator_name]", var5.getTerrainType().getWorldTypeName());
                par1PlayerUsageSnooper.addData("world[" + var2 + "][generator_version]", var5.getTerrainType().getGeneratorVersion());
                par1PlayerUsageSnooper.addData("world[" + var2 + "][height]", this.buildLimit);
                par1PlayerUsageSnooper.addData("world[" + var2 + "][chunks_loaded]", var4.getChunkProvider().getLoadedChunkCount());
                ++var2;
            }
        }
        par1PlayerUsageSnooper.addData("worlds", var2);
    }
    
    @Override
    public void addServerTypeToSnooper(final PlayerUsageSnooper par1PlayerUsageSnooper) {
        par1PlayerUsageSnooper.addData("singleplayer", this.isSinglePlayer());
        par1PlayerUsageSnooper.addData("server_brand", this.getServerModName());
        par1PlayerUsageSnooper.addData("gui_supported", GraphicsEnvironment.isHeadless() ? "headless" : "supported");
        par1PlayerUsageSnooper.addData("dedicated", this.isDedicatedServer());
    }
    
    @Override
    public boolean isSnooperEnabled() {
        return true;
    }
    
    public int textureSize() {
        return 16;
    }
    
    public abstract boolean isDedicatedServer();
    
    public boolean isServerInOnlineMode() {
        return this.onlineMode;
    }
    
    public void setOnlineMode(final boolean par1) {
        this.onlineMode = par1;
    }
    
    public boolean getCanSpawnAnimals() {
        return this.canSpawnAnimals;
    }
    
    public void setCanSpawnAnimals(final boolean par1) {
        this.canSpawnAnimals = par1;
    }
    
    public boolean getCanSpawnNPCs() {
        return this.canSpawnNPCs;
    }
    
    public void setCanSpawnNPCs(final boolean par1) {
        this.canSpawnNPCs = par1;
    }
    
    public boolean isPVPEnabled() {
        return this.pvpEnabled;
    }
    
    public void setAllowPvp(final boolean par1) {
        this.pvpEnabled = par1;
    }
    
    public boolean isFlightAllowed() {
        return this.allowFlight;
    }
    
    public void setAllowFlight(final boolean par1) {
        this.allowFlight = par1;
    }
    
    public abstract boolean isCommandBlockEnabled();
    
    public String getMOTD() {
        return this.motd;
    }
    
    public void setMOTD(final String par1Str) {
        this.motd = par1Str;
    }
    
    public int getBuildLimit() {
        return this.buildLimit;
    }
    
    public void setBuildLimit(final int par1) {
        this.buildLimit = par1;
    }
    
    public boolean isServerStopped() {
        return this.serverStopped;
    }
    
    public ServerConfigurationManager getConfigurationManager() {
        return this.serverConfigManager;
    }
    
    public void setConfigurationManager(final ServerConfigurationManager par1ServerConfigurationManager) {
        this.serverConfigManager = par1ServerConfigurationManager;
    }
    
    public void setGameType(final EnumGameType par1EnumGameType) {
        for (int var2 = 0; var2 < this.worldServers.length; ++var2) {
            getServer().worldServers[var2].getWorldInfo().setGameType(par1EnumGameType);
        }
    }
    
    public abstract NetworkListenThread getNetworkThread();
    
    public boolean serverIsInRunLoop() {
        return this.serverIsRunning;
    }
    
    public boolean getGuiEnabled() {
        return false;
    }
    
    public abstract String shareToLAN(final EnumGameType p0, final boolean p1);
    
    public int getTickCounter() {
        return this.tickCounter;
    }
    
    public void enableProfiling() {
        this.startProfiling = true;
    }
    
    public PlayerUsageSnooper getPlayerUsageSnooper() {
        return this.usageSnooper;
    }
    
    @Override
    public ChunkCoordinates getPlayerCoordinates() {
        return new ChunkCoordinates(0, 0, 0);
    }
    
    public int getSpawnProtectionSize() {
        return 16;
    }
    
    public boolean func_96290_a(final World par1World, final int par2, final int par3, final int par4, final EntityPlayer par5EntityPlayer) {
        return false;
    }
    
    @Override
    public abstract ILogAgent getLogAgent();
    
    public void func_104055_i(final boolean par1) {
        this.field_104057_T = par1;
    }
    
    public boolean func_104056_am() {
        return this.field_104057_T;
    }
    
    public static ServerConfigurationManager getServerConfigurationManager(final MinecraftServer par0MinecraftServer) {
        return par0MinecraftServer.serverConfigManager;
    }
}

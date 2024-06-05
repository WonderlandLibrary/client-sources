package net.minecraft.src;

import net.minecraft.server.*;
import java.net.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class DedicatedServer extends MinecraftServer implements IServer
{
    private final List pendingCommandList;
    private final ILogAgent field_98131_l;
    private RConThreadQuery theRConThreadQuery;
    private RConThreadMain theRConThreadMain;
    private PropertyManager settings;
    private boolean canSpawnStructures;
    private EnumGameType gameType;
    private NetworkListenThread networkThread;
    private boolean guiIsEnabled;
    
    public DedicatedServer(final File par1File) {
        super(par1File);
        this.pendingCommandList = Collections.synchronizedList(new ArrayList<Object>());
        this.guiIsEnabled = false;
        this.field_98131_l = new LogAgent("Minecraft-Server", null, new File(par1File, "server.log").getAbsolutePath());
        new DedicatedServerSleepThread(this);
    }
    
    @Override
    protected boolean startServer() throws IOException {
        final DedicatedServerCommandThread var1 = new DedicatedServerCommandThread(this);
        var1.setDaemon(true);
        var1.start();
        this.getLogAgent().logInfo("Starting minecraft server version 1.5.2");
        if (Runtime.getRuntime().maxMemory() / 1024L / 1024L < 512L) {
            this.getLogAgent().logWarning("To start the server with more ram, launch it as \"java -Xmx1024M -Xms1024M -jar minecraft_server.jar\"");
        }
        this.getLogAgent().logInfo("Loading properties");
        this.settings = new PropertyManager(new File("server.properties"), this.getLogAgent());
        if (this.isSinglePlayer()) {
            this.setHostname("127.0.0.1");
        }
        else {
            this.setOnlineMode(this.settings.getBooleanProperty("online-mode", true));
            this.setHostname(this.settings.getProperty("server-ip", ""));
        }
        this.setCanSpawnAnimals(this.settings.getBooleanProperty("spawn-animals", true));
        this.setCanSpawnNPCs(this.settings.getBooleanProperty("spawn-npcs", true));
        this.setAllowPvp(this.settings.getBooleanProperty("pvp", true));
        this.setAllowFlight(this.settings.getBooleanProperty("allow-flight", false));
        this.setTexturePack(this.settings.getProperty("texture-pack", ""));
        this.setMOTD(this.settings.getProperty("motd", "A Minecraft Server"));
        this.func_104055_i(this.settings.getBooleanProperty("force-gamemode", false));
        if (this.settings.getIntProperty("difficulty", 1) < 0) {
            this.settings.setProperty("difficulty", 0);
        }
        else if (this.settings.getIntProperty("difficulty", 1) > 3) {
            this.settings.setProperty("difficulty", 3);
        }
        this.canSpawnStructures = this.settings.getBooleanProperty("generate-structures", true);
        final int var2 = this.settings.getIntProperty("gamemode", EnumGameType.SURVIVAL.getID());
        this.gameType = WorldSettings.getGameTypeById(var2);
        this.getLogAgent().logInfo("Default game type: " + this.gameType);
        InetAddress var3 = null;
        if (this.getServerHostname().length() > 0) {
            var3 = InetAddress.getByName(this.getServerHostname());
        }
        if (this.getServerPort() < 0) {
            this.setServerPort(this.settings.getIntProperty("server-port", 25565));
        }
        this.getLogAgent().logInfo("Generating keypair");
        this.setKeyPair(CryptManager.createNewKeyPair());
        this.getLogAgent().logInfo("Starting Minecraft server on " + ((this.getServerHostname().length() == 0) ? "*" : this.getServerHostname()) + ":" + this.getServerPort());
        try {
            this.networkThread = new DedicatedServerListenThread(this, var3, this.getServerPort());
        }
        catch (IOException var4) {
            this.getLogAgent().logWarning("**** FAILED TO BIND TO PORT!");
            this.getLogAgent().logWarningFormatted("The exception was: {0}", var4.toString());
            this.getLogAgent().logWarning("Perhaps a server is already running on that port?");
            return false;
        }
        if (!this.isServerInOnlineMode()) {
            this.getLogAgent().logWarning("**** SERVER IS RUNNING IN OFFLINE/INSECURE MODE!");
            this.getLogAgent().logWarning("The server will make no attempt to authenticate usernames. Beware.");
            this.getLogAgent().logWarning("While this makes the game possible to play without internet access, it also opens up the ability for hackers to connect with any username they choose.");
            this.getLogAgent().logWarning("To change this, set \"online-mode\" to \"true\" in the server.properties file.");
        }
        this.setConfigurationManager(new DedicatedPlayerList(this));
        final long var5 = System.nanoTime();
        if (this.getFolderName() == null) {
            this.setFolderName(this.settings.getProperty("level-name", "world"));
        }
        final String var6 = this.settings.getProperty("level-seed", "");
        final String var7 = this.settings.getProperty("level-type", "DEFAULT");
        final String var8 = this.settings.getProperty("generator-settings", "");
        long var9 = new Random().nextLong();
        if (var6.length() > 0) {
            try {
                final long var10 = Long.parseLong(var6);
                if (var10 != 0L) {
                    var9 = var10;
                }
            }
            catch (NumberFormatException var14) {
                var9 = var6.hashCode();
            }
        }
        WorldType var11 = WorldType.parseWorldType(var7);
        if (var11 == null) {
            var11 = WorldType.DEFAULT;
        }
        this.setBuildLimit(this.settings.getIntProperty("max-build-height", 256));
        this.setBuildLimit((this.getBuildLimit() + 8) / 16 * 16);
        this.setBuildLimit(MathHelper.clamp_int(this.getBuildLimit(), 64, 256));
        this.settings.setProperty("max-build-height", this.getBuildLimit());
        this.getLogAgent().logInfo("Preparing level \"" + this.getFolderName() + "\"");
        this.loadAllWorlds(this.getFolderName(), this.getFolderName(), var9, var11, var8);
        final long var12 = System.nanoTime() - var5;
        final String var13 = String.format("%.3fs", var12 / 1.0E9);
        this.getLogAgent().logInfo("Done (" + var13 + ")! For help, type \"help\" or \"?\"");
        if (this.settings.getBooleanProperty("enable-query", false)) {
            this.getLogAgent().logInfo("Starting GS4 status listener");
            (this.theRConThreadQuery = new RConThreadQuery(this)).startThread();
        }
        if (this.settings.getBooleanProperty("enable-rcon", false)) {
            this.getLogAgent().logInfo("Starting remote control listener");
            (this.theRConThreadMain = new RConThreadMain(this)).startThread();
        }
        return true;
    }
    
    @Override
    public boolean canStructuresSpawn() {
        return this.canSpawnStructures;
    }
    
    @Override
    public EnumGameType getGameType() {
        return this.gameType;
    }
    
    @Override
    public int getDifficulty() {
        return this.settings.getIntProperty("difficulty", 1);
    }
    
    @Override
    public boolean isHardcore() {
        return this.settings.getBooleanProperty("hardcore", false);
    }
    
    @Override
    protected void finalTick(final CrashReport par1CrashReport) {
        while (this.isServerRunning()) {
            this.executePendingCommands();
            try {
                Thread.sleep(10L);
            }
            catch (InterruptedException var3) {
                var3.printStackTrace();
            }
        }
    }
    
    @Override
    public CrashReport addServerInfoToCrashReport(CrashReport par1CrashReport) {
        par1CrashReport = super.addServerInfoToCrashReport(par1CrashReport);
        par1CrashReport.func_85056_g().addCrashSectionCallable("Is Modded", new CallableType(this));
        par1CrashReport.func_85056_g().addCrashSectionCallable("Type", new CallableServerType(this));
        return par1CrashReport;
    }
    
    @Override
    protected void systemExitNow() {
        System.exit(0);
    }
    
    @Override
    public void updateTimeLightAndEntities() {
        super.updateTimeLightAndEntities();
        this.executePendingCommands();
    }
    
    @Override
    public boolean getAllowNether() {
        return this.settings.getBooleanProperty("allow-nether", true);
    }
    
    public boolean allowSpawnMonsters() {
        return this.settings.getBooleanProperty("spawn-monsters", true);
    }
    
    @Override
    public void addServerStatsToSnooper(final PlayerUsageSnooper par1PlayerUsageSnooper) {
        par1PlayerUsageSnooper.addData("whitelist_enabled", this.getDedicatedPlayerList().isWhiteListEnabled());
        par1PlayerUsageSnooper.addData("whitelist_count", this.getDedicatedPlayerList().getWhiteListedPlayers().size());
        super.addServerStatsToSnooper(par1PlayerUsageSnooper);
    }
    
    @Override
    public boolean isSnooperEnabled() {
        return this.settings.getBooleanProperty("snooper-enabled", true);
    }
    
    public void addPendingCommand(final String par1Str, final ICommandSender par2ICommandSender) {
        this.pendingCommandList.add(new ServerCommand(par1Str, par2ICommandSender));
    }
    
    public void executePendingCommands() {
        while (!this.pendingCommandList.isEmpty()) {
            final ServerCommand var1 = this.pendingCommandList.remove(0);
            this.getCommandManager().executeCommand(var1.sender, var1.command);
        }
    }
    
    @Override
    public boolean isDedicatedServer() {
        return true;
    }
    
    public DedicatedPlayerList getDedicatedPlayerList() {
        return (DedicatedPlayerList)super.getConfigurationManager();
    }
    
    @Override
    public NetworkListenThread getNetworkThread() {
        return this.networkThread;
    }
    
    @Override
    public int getIntProperty(final String par1Str, final int par2) {
        return this.settings.getIntProperty(par1Str, par2);
    }
    
    @Override
    public String getStringProperty(final String par1Str, final String par2Str) {
        return this.settings.getProperty(par1Str, par2Str);
    }
    
    public boolean getBooleanProperty(final String par1Str, final boolean par2) {
        return this.settings.getBooleanProperty(par1Str, par2);
    }
    
    @Override
    public void setProperty(final String par1Str, final Object par2Obj) {
        this.settings.setProperty(par1Str, par2Obj);
    }
    
    @Override
    public void saveProperties() {
        this.settings.saveProperties();
    }
    
    @Override
    public String getSettingsFilename() {
        final File var1 = this.settings.getPropertiesFile();
        return (var1 != null) ? var1.getAbsolutePath() : "No settings file";
    }
    
    @Override
    public boolean getGuiEnabled() {
        return this.guiIsEnabled;
    }
    
    @Override
    public String shareToLAN(final EnumGameType par1EnumGameType, final boolean par2) {
        return "";
    }
    
    @Override
    public boolean isCommandBlockEnabled() {
        return this.settings.getBooleanProperty("enable-command-block", false);
    }
    
    @Override
    public int getSpawnProtectionSize() {
        return this.settings.getIntProperty("spawn-protection", super.getSpawnProtectionSize());
    }
    
    @Override
    public boolean func_96290_a(final World par1World, final int par2, final int par3, final int par4, final EntityPlayer par5EntityPlayer) {
        if (par1World.provider.dimensionId != 0) {
            return false;
        }
        if (this.getDedicatedPlayerList().getOps().isEmpty()) {
            return false;
        }
        if (this.getDedicatedPlayerList().areCommandsAllowed(par5EntityPlayer.username)) {
            return false;
        }
        if (this.getSpawnProtectionSize() <= 0) {
            return false;
        }
        final ChunkCoordinates var6 = par1World.getSpawnPoint();
        final int var7 = MathHelper.abs_int(par2 - var6.posX);
        final int var8 = MathHelper.abs_int(par4 - var6.posZ);
        final int var9 = Math.max(var7, var8);
        return var9 <= this.getSpawnProtectionSize();
    }
    
    @Override
    public ILogAgent getLogAgent() {
        return this.field_98131_l;
    }
    
    @Override
    public ServerConfigurationManager getConfigurationManager() {
        return this.getDedicatedPlayerList();
    }
}

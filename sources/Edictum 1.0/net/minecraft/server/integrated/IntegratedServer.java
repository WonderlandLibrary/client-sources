package net.minecraft.server.integrated;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.Futures;
import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.concurrent.FutureTask;
import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ThreadLanServerPing;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.ICrashReportDetail;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.profiler.Snooper;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerProfileCache;
import net.minecraft.util.CryptManager;
import net.minecraft.util.HttpUtil;
import net.minecraft.util.Util;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.GameType;
import net.minecraft.world.ServerWorldEventHandler;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldServerMulti;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldType;
import net.minecraft.world.demo.DemoWorldServer;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.WorldInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class IntegratedServer extends MinecraftServer
{
    private static final Logger LOGGER = LogManager.getLogger();

    /** The Minecraft instance. */
    private final Minecraft mc;
    private final WorldSettings theWorldSettings;
    private boolean isGamePaused;
    private boolean isPublic;
    private ThreadLanServerPing lanServerPing;

    public IntegratedServer(Minecraft clientIn, String folderNameIn, String worldNameIn, WorldSettings worldSettingsIn, YggdrasilAuthenticationService authServiceIn, MinecraftSessionService sessionServiceIn, GameProfileRepository profileRepoIn, PlayerProfileCache profileCacheIn)
    {
        super(new File(clientIn.mcDataDir, "saves"), clientIn.getProxy(), clientIn.getDataFixer(), authServiceIn, sessionServiceIn, profileRepoIn, profileCacheIn);
        this.setServerOwner(clientIn.getSession().getUsername());
        this.setFolderName(folderNameIn);
        this.setWorldName(worldNameIn);
        this.setDemo(clientIn.isDemo());
        this.canCreateBonusChest(worldSettingsIn.isBonusChestEnabled());
        this.setBuildLimit(256);
        this.setPlayerList(new IntegratedPlayerList(this));
        this.mc = clientIn;
        this.theWorldSettings = this.isDemo() ? DemoWorldServer.DEMO_WORLD_SETTINGS : worldSettingsIn;
    }

    public ServerCommandManager createNewCommandManager()
    {
        return new IntegratedServerCommandManager(this);
    }

    public void loadAllWorlds(String saveName, String worldNameIn, long seed, WorldType type, String generatorOptions)
    {
        this.convertMapIfNeeded(saveName);
        this.worldServers = new WorldServer[3];
        this.timeOfLastDimensionTick = new long[this.worldServers.length][100];
        ISaveHandler isavehandler = this.getActiveAnvilConverter().getSaveLoader(saveName, true);
        this.setResourcePackFromWorld(this.getFolderName(), isavehandler);
        WorldInfo worldinfo = isavehandler.loadWorldInfo();

        if (worldinfo == null)
        {
            worldinfo = new WorldInfo(this.theWorldSettings, worldNameIn);
        }
        else
        {
            worldinfo.setWorldName(worldNameIn);
        }

        for (int i = 0; i < this.worldServers.length; ++i)
        {
            int j = 0;

            if (i == 1)
            {
                j = -1;
            }

            if (i == 2)
            {
                j = 1;
            }

            if (i == 0)
            {
                if (this.isDemo())
                {
                    this.worldServers[i] = (WorldServer)(new DemoWorldServer(this, isavehandler, worldinfo, j, this.theProfiler)).init();
                }
                else
                {
                    this.worldServers[i] = (WorldServer)(new WorldServer(this, isavehandler, worldinfo, j, this.theProfiler)).init();
                }

                this.worldServers[i].initialize(this.theWorldSettings);
            }
            else
            {
                this.worldServers[i] = (WorldServer)(new WorldServerMulti(this, isavehandler, j, this.worldServers[0], this.theProfiler)).init();
            }

            this.worldServers[i].addEventListener(new ServerWorldEventHandler(this, this.worldServers[i]));
        }

        this.getPlayerList().setPlayerManager(this.worldServers);

        if (this.worldServers[0].getWorldInfo().getDifficulty() == null)
        {
            this.setDifficultyForAllWorlds(this.mc.gameSettings.difficulty);
        }

        this.initialWorldChunkLoad();
    }

    /**
     * Initialises the server and starts it.
     */
    public boolean startServer() throws IOException
    {
        LOGGER.info("Starting integrated minecraft server version 1.10");
        this.setOnlineMode(true);
        this.setCanSpawnAnimals(true);
        this.setCanSpawnNPCs(true);
        this.setAllowPvp(true);
        this.setAllowFlight(true);
        LOGGER.info("Generating keypair");
        this.setKeyPair(CryptManager.generateKeyPair());
        this.loadAllWorlds(this.getFolderName(), this.getWorldName(), this.theWorldSettings.getSeed(), this.theWorldSettings.getTerrainType(), this.theWorldSettings.getGeneratorOptions());
        this.setMOTD(this.getServerOwner() + " - " + this.worldServers[0].getWorldInfo().getWorldName());
        return true;
    }

    /**
     * Main function called by run() every loop.
     */
    public void tick()
    {
        boolean flag = this.isGamePaused;
        this.isGamePaused = Minecraft.getMinecraft().getConnection() != null && Minecraft.getMinecraft().isGamePaused();

        if (!flag && this.isGamePaused)
        {
            LOGGER.info("Saving and pausing game...");
            this.getPlayerList().saveAllPlayerData();
            this.saveAllWorlds(false);
        }

        if (this.isGamePaused)
        {
            synchronized (this.futureTaskQueue)
            {
                while (!this.futureTaskQueue.isEmpty())
                {
                    Util.runTask((FutureTask)this.futureTaskQueue.poll(), LOGGER);
                }
            }
        }
        else
        {
            super.tick();

            if (this.mc.gameSettings.renderDistanceChunks != this.getPlayerList().getViewDistance())
            {
                LOGGER.info("Changing view distance to {}, from {}", new Object[] {Integer.valueOf(this.mc.gameSettings.renderDistanceChunks), Integer.valueOf(this.getPlayerList().getViewDistance())});
                this.getPlayerList().setViewDistance(this.mc.gameSettings.renderDistanceChunks);
            }

            if (this.mc.theWorld != null)
            {
                WorldInfo worldinfo1 = this.worldServers[0].getWorldInfo();
                WorldInfo worldinfo = this.mc.theWorld.getWorldInfo();

                if (!worldinfo1.isDifficultyLocked() && worldinfo.getDifficulty() != worldinfo1.getDifficulty())
                {
                    LOGGER.info("Changing difficulty to {}, from {}", new Object[] {worldinfo.getDifficulty(), worldinfo1.getDifficulty()});
                    this.setDifficultyForAllWorlds(worldinfo.getDifficulty());
                }
                else if (worldinfo.isDifficultyLocked() && !worldinfo1.isDifficultyLocked())
                {
                    LOGGER.info("Locking difficulty to {}", new Object[] {worldinfo.getDifficulty()});

                    for (WorldServer worldserver : this.worldServers)
                    {
                        if (worldserver != null)
                        {
                            worldserver.getWorldInfo().setDifficultyLocked(true);
                        }
                    }
                }
            }
        }
    }

    public boolean canStructuresSpawn()
    {
        return false;
    }

    public GameType getGameType()
    {
        return this.theWorldSettings.getGameType();
    }

    /**
     * Get the server's difficulty
     */
    public EnumDifficulty getDifficulty()
    {
        return this.mc.theWorld.getWorldInfo().getDifficulty();
    }

    /**
     * Defaults to false.
     */
    public boolean isHardcore()
    {
        return this.theWorldSettings.getHardcoreEnabled();
    }

    /**
     * Get if RCON command events should be broadcast to ops
     */
    public boolean shouldBroadcastRconToOps()
    {
        return true;
    }

    /**
     * Get if console command events should be broadcast to ops
     */
    public boolean shouldBroadcastConsoleToOps()
    {
        return true;
    }

    /**
     * par1 indicates if a log message should be output.
     */
    public void saveAllWorlds(boolean dontLog)
    {
        super.saveAllWorlds(dontLog);
    }

    public File getDataDirectory()
    {
        return this.mc.mcDataDir;
    }

    public boolean isDedicatedServer()
    {
        return false;
    }

    /**
     * Get if native transport should be used. Native transport means linux server performance improvements and
     * optimized packet sending/receiving on linux
     */
    public boolean shouldUseNativeTransport()
    {
        return false;
    }

    /**
     * Called on exit from the main run() loop.
     */
    public void finalTick(CrashReport report)
    {
        this.mc.crashed(report);
    }

    /**
     * Adds the server info, including from theWorldServer, to the crash report.
     */
    public CrashReport addServerInfoToCrashReport(CrashReport report)
    {
        report = super.addServerInfoToCrashReport(report);
        report.getCategory().func_189529_a("Type", new ICrashReportDetail<String>()
        {
            public String call() throws Exception
            {
                return "Integrated Server (map_client.txt)";
            }
        });
        report.getCategory().func_189529_a("Is Modded", new ICrashReportDetail<String>()
        {
            public String call() throws Exception
            {
                String s = ClientBrandRetriever.getClientModName();

                if (!s.equals("vanilla"))
                {
                    return "Definitely; Client brand changed to \'" + s + "\'";
                }
                else
                {
                    s = IntegratedServer.this.getServerModName();
                    return !"vanilla".equals(s) ? "Definitely; Server brand changed to \'" + s + "\'" : (Minecraft.class.getSigners() == null ? "Very likely; Jar signature invalidated" : "Probably not. Jar signature remains and both client + server brands are untouched.");
                }
            }
        });
        return report;
    }

    public void setDifficultyForAllWorlds(EnumDifficulty difficulty)
    {
        super.setDifficultyForAllWorlds(difficulty);

        if (this.mc.theWorld != null)
        {
            this.mc.theWorld.getWorldInfo().setDifficulty(difficulty);
        }
    }

    public void addServerStatsToSnooper(Snooper playerSnooper)
    {
        super.addServerStatsToSnooper(playerSnooper);
        playerSnooper.addClientStat("snooper_partner", this.mc.getPlayerUsageSnooper().getUniqueID());
    }

    /**
     * Returns whether snooping is enabled or not.
     */
    public boolean isSnooperEnabled()
    {
        return Minecraft.getMinecraft().isSnooperEnabled();
    }

    /**
     * On dedicated does nothing. On integrated, sets commandsAllowedForAll, gameType and allows external connections.
     */
    public String shareToLAN(GameType type, boolean allowCheats)
    {
        try
        {
            int i = -1;

            try
            {
                i = HttpUtil.getSuitableLanPort();
            }
            catch (IOException var5)
            {
                ;
            }

            if (i <= 0)
            {
                i = 25564;
            }

            this.getNetworkSystem().addLanEndpoint((InetAddress)null, i);
            LOGGER.info("Started on {}", new Object[] {Integer.valueOf(i)});
            this.isPublic = true;
            this.lanServerPing = new ThreadLanServerPing(this.getMOTD(), i + "");
            this.lanServerPing.start();
            this.getPlayerList().setGameType(type);
            this.getPlayerList().setCommandsAllowedForAll(allowCheats);
            this.mc.thePlayer.setPermissionLevel(allowCheats ? 4 : 0);
            return i + "";
        }
        catch (IOException var6)
        {
            return null;
        }
    }

    /**
     * Saves all necessary data as preparation for stopping the server.
     */
    public void stopServer()
    {
        super.stopServer();

        if (this.lanServerPing != null)
        {
            this.lanServerPing.interrupt();
            this.lanServerPing = null;
        }
    }

    /**
     * Sets the serverRunning variable to false, in order to get the server to shut down.
     */
    public void initiateShutdown()
    {
        Futures.getUnchecked(this.addScheduledTask(new Runnable()
        {
            public void run()
            {
                for (EntityPlayerMP entityplayermp : Lists.newArrayList(IntegratedServer.this.getPlayerList().getPlayerList()))
                {
                    IntegratedServer.this.getPlayerList().playerLoggedOut(entityplayermp);
                }
            }
        }));
        super.initiateShutdown();

        if (this.lanServerPing != null)
        {
            this.lanServerPing.interrupt();
            this.lanServerPing = null;
        }
    }

    /**
     * Returns true if this integrated server is open to LAN
     */
    public boolean getPublic()
    {
        return this.isPublic;
    }

    /**
     * Sets the game type for all worlds.
     */
    public void setGameType(GameType gameMode)
    {
        super.setGameType(gameMode);
        this.getPlayerList().setGameType(gameMode);
    }

    /**
     * Return whether command blocks are enabled.
     */
    public boolean isCommandBlockEnabled()
    {
        return true;
    }

    public int getOpPermissionLevel()
    {
        return 4;
    }

    public void reloadLootTables()
    {
        if (this.isCallingFromMinecraftThread())
        {
            this.worldServers[0].getLootTableManager().reloadLootTables();
        }
        else
        {
            this.addScheduledTask(new Runnable()
            {
                public void run()
                {
                    IntegratedServer.this.reloadLootTables();
                }
            });
        }
    }
}

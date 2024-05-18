package net.minecraft.server;

import java.net.*;
import com.mojang.authlib.yggdrasil.*;
import net.minecraft.server.management.*;
import net.minecraft.profiler.*;
import com.mojang.authlib.minecraft.*;
import java.security.*;
import java.awt.*;
import com.google.common.collect.*;
import net.minecraft.world.chunk.storage.*;
import net.minecraft.crash.*;
import net.minecraft.network.play.server.*;
import net.minecraft.network.*;
import org.apache.logging.log4j.*;
import net.minecraft.entity.*;
import com.mojang.authlib.*;
import org.apache.commons.lang3.*;
import net.minecraft.world.demo.*;
import net.minecraft.world.*;
import net.minecraft.world.storage.*;
import javax.imageio.*;
import io.netty.handler.codec.base64.*;
import com.google.common.base.*;
import io.netty.buffer.*;
import java.awt.image.*;
import java.util.concurrent.*;
import com.google.common.util.concurrent.*;
import java.io.*;
import net.minecraft.util.*;
import java.text.*;
import net.minecraft.entity.player.*;
import net.minecraft.command.*;
import java.util.*;

public abstract class MinecraftServer implements IPlayerUsage, IThreadListener, Runnable, ICommandSender
{
    private boolean worldIsBeingDeleted;
    public static final File USER_CACHE_FILE;
    private int tickCounter;
    private int maxPlayerIdleMinutes;
    private long nanoTimeSinceStatusRefresh;
    private final PlayerProfileCache profileCache;
    private String folderName;
    private String worldName;
    private final Random random;
    private boolean enableBonusChest;
    private long currentTime;
    private String resourcePackHash;
    private long timeOfLastWarning;
    private final ServerStatusResponse statusResponse;
    protected final Proxy serverProxy;
    private final List<ITickable> playersOnline;
    private static final Logger logger;
    private boolean serverIsRunning;
    private boolean startProfiling;
    private final YggdrasilAuthenticationService authService;
    private int buildLimit;
    private String resourcePackUrl;
    public String currentTask;
    private boolean serverStopped;
    private boolean allowFlight;
    private Thread serverThread;
    private final ISaveFormat anvilConverterForAnvilFile;
    private final NetworkSystem networkSystem;
    private String serverOwner;
    private static MinecraftServer mcServer;
    public WorldServer[] worldServers;
    private int serverPort;
    public long[][] timeOfLastDimensionTick;
    protected final Queue<FutureTask<?>> futureTaskQueue;
    public final Profiler theProfiler;
    public final long[] tickTimeArray;
    private boolean canSpawnAnimals;
    private boolean canSpawnNPCs;
    public int percentDone;
    private boolean isDemo;
    private static final String[] I;
    private boolean onlineMode;
    private final GameProfileRepository profileRepo;
    private String userMessage;
    private ServerConfigurationManager serverConfigManager;
    private final PlayerUsageSnooper usageSnooper;
    private String motd;
    protected final ICommandManager commandManager;
    private boolean serverRunning;
    private boolean pvpEnabled;
    private boolean isGamemodeForced;
    private final MinecraftSessionService sessionService;
    private final File anvilFile;
    private KeyPair serverKeyPair;
    
    @Override
    public void addServerTypeToSnooper(final PlayerUsageSnooper playerUsageSnooper) {
        playerUsageSnooper.addStatToSnooper(MinecraftServer.I[0xF8 ^ 0x9A], this.isSinglePlayer());
        playerUsageSnooper.addStatToSnooper(MinecraftServer.I[0x52 ^ 0x31], this.getServerModName());
        final String s = MinecraftServer.I[0x45 ^ 0x21];
        String s2;
        if (GraphicsEnvironment.isHeadless()) {
            s2 = MinecraftServer.I[0xE ^ 0x6B];
            "".length();
            if (0 >= 2) {
                throw null;
            }
        }
        else {
            s2 = MinecraftServer.I[0x77 ^ 0x11];
        }
        playerUsageSnooper.addStatToSnooper(s, s2);
        playerUsageSnooper.addStatToSnooper(MinecraftServer.I[0x0 ^ 0x67], this.isDedicatedServer());
    }
    
    public MinecraftServer(final File anvilFile, final Proxy serverProxy, final File file) {
        this.usageSnooper = new PlayerUsageSnooper(MinecraftServer.I[0x42 ^ 0x46], this, getCurrentTimeMillis());
        this.playersOnline = (List<ITickable>)Lists.newArrayList();
        this.theProfiler = new Profiler();
        this.statusResponse = new ServerStatusResponse();
        this.random = new Random();
        this.serverPort = -" ".length();
        this.serverRunning = (" ".length() != 0);
        this.maxPlayerIdleMinutes = "".length();
        this.tickTimeArray = new long[0x18 ^ 0x7C];
        this.resourcePackUrl = MinecraftServer.I[0x63 ^ 0x66];
        this.resourcePackHash = MinecraftServer.I[0x6C ^ 0x6A];
        this.nanoTimeSinceStatusRefresh = 0L;
        this.futureTaskQueue = (Queue<FutureTask<?>>)Queues.newArrayDeque();
        this.currentTime = getCurrentTimeMillis();
        this.serverProxy = serverProxy;
        MinecraftServer.mcServer = this;
        this.anvilFile = anvilFile;
        this.networkSystem = new NetworkSystem(this);
        this.profileCache = new PlayerProfileCache(this, file);
        this.commandManager = this.createNewCommandManager();
        this.anvilConverterForAnvilFile = new AnvilSaveConverter(anvilFile);
        this.authService = new YggdrasilAuthenticationService(serverProxy, UUID.randomUUID().toString());
        this.sessionService = this.authService.createMinecraftSessionService();
        this.profileRepo = this.authService.createProfileRepository();
    }
    
    public String getResourcePackHash() {
        return this.resourcePackHash;
    }
    
    public ServerConfigurationManager getConfigurationManager() {
        return this.serverConfigManager;
    }
    
    public int getBuildLimit() {
        return this.buildLimit;
    }
    
    protected void finalTick(final CrashReport crashReport) {
    }
    
    public void setMOTD(final String motd) {
        this.motd = motd;
    }
    
    public WorldServer worldServerForDimension(final int n) {
        WorldServer worldServer;
        if (n == -" ".length()) {
            worldServer = this.worldServers[" ".length()];
            "".length();
            if (-1 >= 2) {
                throw null;
            }
        }
        else if (n == " ".length()) {
            worldServer = this.worldServers["  ".length()];
            "".length();
            if (4 < -1) {
                throw null;
            }
        }
        else {
            worldServer = this.worldServers["".length()];
        }
        return worldServer;
    }
    
    public abstract boolean canStructuresSpawn();
    
    public abstract boolean isDedicatedServer();
    
    public static long getCurrentTimeMillis() {
        return System.currentTimeMillis();
    }
    
    public void setFolderName(final String folderName) {
        this.folderName = folderName;
    }
    
    public boolean isAnvilFileSet() {
        if (this.anvilFile != null) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public boolean getAllowNether() {
        return " ".length() != 0;
    }
    
    public boolean isServerStopped() {
        return this.serverStopped;
    }
    
    @Override
    public void addServerStatsToSnooper(final PlayerUsageSnooper playerUsageSnooper) {
        playerUsageSnooper.addClientStat(MinecraftServer.I[0x40 ^ 0x6], (boolean)("".length() != 0));
        playerUsageSnooper.addClientStat(MinecraftServer.I[0xF ^ 0x48], "".length());
        if (this.serverConfigManager != null) {
            playerUsageSnooper.addClientStat(MinecraftServer.I[0x88 ^ 0xC0], this.getCurrentPlayerCount());
            playerUsageSnooper.addClientStat(MinecraftServer.I[0x35 ^ 0x7C], this.getMaxPlayers());
            playerUsageSnooper.addClientStat(MinecraftServer.I[0xE2 ^ 0xA8], this.serverConfigManager.getAvailablePlayerDat().length);
        }
        playerUsageSnooper.addClientStat(MinecraftServer.I[0x2A ^ 0x61], this.onlineMode);
        final String s = MinecraftServer.I[0x7C ^ 0x30];
        String s2;
        if (this.getGuiEnabled()) {
            s2 = MinecraftServer.I[0x46 ^ 0xB];
            "".length();
            if (3 != 3) {
                throw null;
            }
        }
        else {
            s2 = MinecraftServer.I[0x8D ^ 0xC3];
        }
        playerUsageSnooper.addClientStat(s, s2);
        playerUsageSnooper.addClientStat(MinecraftServer.I[0x2C ^ 0x63], (getCurrentTimeMillis() - playerUsageSnooper.getMinecraftStartTimeMillis()) / 60L * 1000L);
        playerUsageSnooper.addClientStat(MinecraftServer.I[0x28 ^ 0x78], (int)(MathHelper.average(this.tickTimeArray) * 1.0E-6));
        int length = "".length();
        if (this.worldServers != null) {
            int i = "".length();
            "".length();
            if (2 <= -1) {
                throw null;
            }
            while (i < this.worldServers.length) {
                if (this.worldServers[i] != null) {
                    final WorldServer worldServer = this.worldServers[i];
                    final WorldInfo worldInfo = worldServer.getWorldInfo();
                    playerUsageSnooper.addClientStat(MinecraftServer.I[0x27 ^ 0x76] + length + MinecraftServer.I[0x7B ^ 0x29], worldServer.provider.getDimensionId());
                    playerUsageSnooper.addClientStat(MinecraftServer.I[0x61 ^ 0x32] + length + MinecraftServer.I[0x6F ^ 0x3B], worldInfo.getGameType());
                    playerUsageSnooper.addClientStat(MinecraftServer.I[0x3A ^ 0x6F] + length + MinecraftServer.I[0x66 ^ 0x30], worldServer.getDifficulty());
                    playerUsageSnooper.addClientStat(MinecraftServer.I[0x71 ^ 0x26] + length + MinecraftServer.I[0x98 ^ 0xC0], worldInfo.isHardcoreModeEnabled());
                    playerUsageSnooper.addClientStat(MinecraftServer.I[0x18 ^ 0x41] + length + MinecraftServer.I[0x7 ^ 0x5D], worldInfo.getTerrainType().getWorldTypeName());
                    playerUsageSnooper.addClientStat(MinecraftServer.I[0x50 ^ 0xB] + length + MinecraftServer.I[0xC2 ^ 0x9E], worldInfo.getTerrainType().getGeneratorVersion());
                    playerUsageSnooper.addClientStat(MinecraftServer.I[0x5A ^ 0x7] + length + MinecraftServer.I[0x5F ^ 0x1], this.buildLimit);
                    playerUsageSnooper.addClientStat(MinecraftServer.I[0xF2 ^ 0xAD] + length + MinecraftServer.I[0x73 ^ 0x13], worldServer.getChunkProvider().getLoadedChunkCount());
                    ++length;
                }
                ++i;
            }
        }
        playerUsageSnooper.addClientStat(MinecraftServer.I[0xD9 ^ 0xB8], length);
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
            if (3 < 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public boolean getGuiEnabled() {
        return "".length() != 0;
    }
    
    public abstract int getOpPermissionLevel();
    
    public String getMinecraftVersion() {
        return MinecraftServer.I[0xBE ^ 0x83];
    }
    
    protected synchronized void setUserMessage(final String userMessage) {
        this.userMessage = userMessage;
    }
    
    public void updateTimeLightAndEntities() {
        this.theProfiler.startSection(MinecraftServer.I[0xAE ^ 0x9F]);
        synchronized (this.futureTaskQueue) {
            "".length();
            if (3 < 3) {
                throw null;
            }
            while (!this.futureTaskQueue.isEmpty()) {
                Util.func_181617_a(this.futureTaskQueue.poll(), MinecraftServer.logger);
            }
            // monitorexit(this.futureTaskQueue)
            "".length();
            if (3 <= 2) {
                throw null;
            }
        }
        this.theProfiler.endStartSection(MinecraftServer.I[0xB6 ^ 0x84]);
        int i = "".length();
        "".length();
        if (2 != 2) {
            throw null;
        }
        while (i < this.worldServers.length) {
            final long nanoTime = System.nanoTime();
            if (i == 0 || this.getAllowNether()) {
                final WorldServer worldServer = this.worldServers[i];
                this.theProfiler.startSection(worldServer.getWorldInfo().getWorldName());
                if (this.tickCounter % (0x2D ^ 0x39) == 0) {
                    this.theProfiler.startSection(MinecraftServer.I[0x4C ^ 0x7F]);
                    this.serverConfigManager.sendPacketToAllPlayersInDimension(new S03PacketTimeUpdate(worldServer.getTotalWorldTime(), worldServer.getWorldTime(), worldServer.getGameRules().getBoolean(MinecraftServer.I[0x7D ^ 0x49])), worldServer.provider.getDimensionId());
                    this.theProfiler.endSection();
                }
                this.theProfiler.startSection(MinecraftServer.I[0x6F ^ 0x5A]);
                try {
                    worldServer.tick();
                    "".length();
                    if (0 < -1) {
                        throw null;
                    }
                }
                catch (Throwable t) {
                    final CrashReport crashReport = CrashReport.makeCrashReport(t, MinecraftServer.I[0x75 ^ 0x43]);
                    worldServer.addWorldInfoToCrashReport(crashReport);
                    throw new ReportedException(crashReport);
                }
                try {
                    worldServer.updateEntities();
                    "".length();
                    if (4 == 0) {
                        throw null;
                    }
                }
                catch (Throwable t2) {
                    final CrashReport crashReport2 = CrashReport.makeCrashReport(t2, MinecraftServer.I[0x72 ^ 0x45]);
                    worldServer.addWorldInfoToCrashReport(crashReport2);
                    throw new ReportedException(crashReport2);
                }
                this.theProfiler.endSection();
                this.theProfiler.startSection(MinecraftServer.I[0x35 ^ 0xD]);
                worldServer.getEntityTracker().updateTrackedEntities();
                this.theProfiler.endSection();
                this.theProfiler.endSection();
            }
            this.timeOfLastDimensionTick[i][this.tickCounter % (0x3C ^ 0x58)] = System.nanoTime() - nanoTime;
            ++i;
        }
        this.theProfiler.endStartSection(MinecraftServer.I[0xBA ^ 0x83]);
        this.getNetworkSystem().networkTick();
        this.theProfiler.endStartSection(MinecraftServer.I[0x83 ^ 0xB9]);
        this.serverConfigManager.onTick();
        this.theProfiler.endStartSection(MinecraftServer.I[0xA6 ^ 0x9D]);
        int j = "".length();
        "".length();
        if (2 >= 3) {
            throw null;
        }
        while (j < this.playersOnline.size()) {
            this.playersOnline.get(j).update();
            ++j;
        }
        this.theProfiler.endSection();
    }
    
    protected void initialWorldChunkLoad() {
        int length = "".length();
        this.setUserMessage(MinecraftServer.I[0x8 ^ 0x2]);
        final int length2 = "".length();
        MinecraftServer.logger.info(MinecraftServer.I[0xC ^ 0x7] + length2);
        final WorldServer worldServer = this.worldServers[length2];
        final BlockPos spawnPoint = worldServer.getSpawnPoint();
        long currentTimeMillis = getCurrentTimeMillis();
        int n = -(48 + 39 + 94 + 11);
        "".length();
        if (0 >= 4) {
            throw null;
        }
        while (n <= 7 + 84 - 42 + 143 && this.isServerRunning()) {
            int n2 = -(147 + 19 - 48 + 74);
            "".length();
            if (3 == 4) {
                throw null;
            }
            while (n2 <= 54 + 6 - 25 + 157 && this.isServerRunning()) {
                final long currentTimeMillis2 = getCurrentTimeMillis();
                if (currentTimeMillis2 - currentTimeMillis > 1000L) {
                    this.outputPercentRemaining(MinecraftServer.I[0x54 ^ 0x58], length * (0x67 ^ 0x3) / (598 + 62 - 625 + 590));
                    currentTimeMillis = currentTimeMillis2;
                }
                ++length;
                worldServer.theChunkProviderServer.loadChunk(spawnPoint.getX() + n >> (0x4F ^ 0x4B), spawnPoint.getZ() + n2 >> (0x5B ^ 0x5F));
                n2 += 16;
            }
            n += 16;
        }
        this.clearCurrentTask();
    }
    
    private static void I() {
        (I = new String[0x66 ^ 0xF])["".length()] = I("9\u00006\u0011+-\u0010;\u0006f&\u0000<\r", "LsScH");
        MinecraftServer.I[" ".length()] = I("\u000b\u0001\".\u0015\n", "xdPXp");
        MinecraftServer.I["  ".length()] = I("", "PrDRM");
        MinecraftServer.I["   ".length()] = I("", "yAPzC");
        MinecraftServer.I[0x29 ^ 0x2D] = I("6,!\u0002.7", "EIStK");
        MinecraftServer.I[0x87 ^ 0x82] = I("", "ENUYG");
        MinecraftServer.I[0x25 ^ 0x23] = I("", "JAvgu");
        MinecraftServer.I[0xAE ^ 0xA9] = I("\n\n/\u0019=;\u0011(\u0001?i\b \u001fy", "IeAoX");
        MinecraftServer.I[0x16 ^ 0x1E] = I("\u0006,6=M\b&6>\u0006\u0019=1&\u0004',.-\u000f", "kIXHc");
        MinecraftServer.I[0x50 ^ 0x59] = I("\u001a\u001c#>e\u001b\u0016,/\"\u0019\u001e\u0001.=\u0012\u0015", "wyMKK");
        MinecraftServer.I[0x96 ^ 0x9C] = I("<= 0[6=  \u00070,'+\u0012\u0005=<7\u001486", "QXNEu");
        MinecraftServer.I[0x7B ^ 0x70] = I(":\"\u001c\u001b7\u00189\u0017\fv\u0019$\u0018\u0019\"J\"\u001c\f?\u0005>Y\r9\u0018p\u0015\u000e \u000f<Y", "jPykV");
        MinecraftServer.I[0x96 ^ 0x9A] = I("\u0012*\u0017%'01\u001c2f1(\u0013\"(b9\u00000'", "BXrUF");
        MinecraftServer.I[0x59 ^ 0x54] = I("\u0007\u001f7\u0016\f\u0007\u0019!\nW\u000f\u00134", "uzDyy");
        MinecraftServer.I[0x5 ^ 0xB] = I(" \u0012\"!\u0019vX{", "LwTDu");
        MinecraftServer.I[0x90 ^ 0x9F] = I("c", "LPsFo");
        MinecraftServer.I[0x7D ^ 0x6D] = I("", "VsXCA");
        MinecraftServer.I[0x3D ^ 0x2C] = I("NG", "tgTie");
        MinecraftServer.I[0x34 ^ 0x26] = I("W", "rhvKv");
        MinecraftServer.I[0x48 ^ 0x5B] = I("\u001b\r\u001b\u00059/L\u000e\u0004\"&\u0007\u001eL1'\u001eM\u00002>\t\u0001Lp", "HlmlW");
        MinecraftServer.I[0x45 ^ 0x51] = I("Nc", "iLQYr");
        MinecraftServer.I[0xBF ^ 0xAA] = I("0?\u001d\u0017<\n%\u0015G?\u00069\u0004\u0002>", "cKrgL");
        MinecraftServer.I[0x2F ^ 0x39] = I("5,\u0001\u001b8\u0001m\u0007\u001e7\u001f(\u0005\u0001", "fMwrV");
        MinecraftServer.I[0x9E ^ 0x89] = I("\u00009\u001c\u001b\u00024x\u001d\u001d\u001e?<\u0019", "SXjrl");
        MinecraftServer.I[0x24 ^ 0x3C] = I("SOM~S", "bauPk");
        MinecraftServer.I[0x3A ^ 0x23] = I("\u000f\t\u0006f\u0000l\u0003\r$\u0004l\u001d\u0018`T\b\u0001\fa\u0000$\rH2\r?\u001c\r,T8\u0001\u0005$T/\u0000\t/\u0013)DH.\u0006l\u0001\u001ba\u0000$\rH2\u0011>\u001e\r3T#\u001e\r3\u0018#\t\f$\u0010sH:4\u001a\"\u0001\u0006&T7\u0015\u00052T.\r\u0000(\u001a(DH2\u001f%\u0018\u0018(\u001a+H\u0013<T8\u0001\u000b*\\?A", "LhhAt");
        MinecraftServer.I[0xBC ^ 0xA6] = I("\u0015\u0001<\bf3\t?M$ \u000b:\u001a'3\f\"Lf\u0005\u00015M2)\rq\u001e?2\u001c4\u0000f5\u0001<\bf\"\u00000\u0003!$W", "AhQmF");
        MinecraftServer.I[0x26 ^ 0x3D] = I("\u001f\n-.<4\u0010+3,>D//i/\n+99?\u0007:$-z\u00016\",*\u0010'.'", "ZdNAI");
        MinecraftServer.I[0x2B ^ 0x37] = I("\u001d\u00114\u0015\u0013,\u00008\u001eC1\u0007w\u0003\u0006*\u001f2\u0002C,\u00004\u001bC4\u00068\u0000", "XiWpc");
        MinecraftServer.I[0xB9 ^ 0xA4] = I("\t402?G4418\u00182\"", "jFQAW");
        MinecraftServer.I[0x94 ^ 0x8A] = I("'<(\u0004%i", "DNIwM");
        MinecraftServer.I[0x3F ^ 0x20] = I("\u0012\u0014?\u0015n& k\b'4%\u000eB.\u0006C5\u001f", "kmFlC");
        MinecraftServer.I[0x89 ^ 0xA9] = I("I\u0006\u0013\u0014\u0017\u0001\u0007X\u0012\u0019\u0010", "duvfa");
        MinecraftServer.I[0x7 ^ 0x26] = I("\u0002\f#\u0015L5\u0016+\u0015\u0004v\u0016/\u0016\u0003$\u0010j\u000e\r%D(\u0003\t8D9\u0007\u001a3\u0000j\u0012\u0003lD", "VdJfl");
        MinecraftServer.I[0x16 ^ 0x34] = I(" &m&\u0011\u0005&m$\u001a\u0016!!4T\u0003,m\"\u0015\u0001&m%\u001c\u001e0m2\u0006\u00160%q\u0006\u00123\"#\u0000W7\"q\u0010\u001e0&\u007f", "wCMQt");
        MinecraftServer.I[0x36 ^ 0x15] = I("\t50 <8$<+l?9<5<%#4e8$(s6)>;67", "LMSEL");
        MinecraftServer.I[0x5D ^ 0x79] = I(")*.\u000b\u0003\u0018;\"\u0000S\u001f&\"\u001e\u0003\u0005<*N\u0007\u00047m\u001d\u0016\u001e$(\u001c", "lRMns");
        MinecraftServer.I[0x40 ^ 0x65] = I("?\u0001\u0011\u000b\"\u000e\u0010\u001d\u0000r\t\r\u001d\u001e\"\u0013\u0017\u0015N&\u0012\u001cR\u001d7\b\u000f\u0017\u001c", "zyrnR");
        MinecraftServer.I[0x84 ^ 0xA2] = I("\"\u0003\u001a,\u000f#K\u00019\u0005?H\u00184\r", "QfhZj");
        MinecraftServer.I[0xAF ^ 0x88] = I("\u001f2\u0006\u0017A0\"UUUr7\u001c\u001b\u0004>4U\u0014\b6\"", "RGuca");
        MinecraftServer.I[0xAB ^ 0x83] = I("\u0018 \u0017\u0007A70DEUu%\r\u000b\u00049&D\u001b\b2=", "UUdsa");
        MinecraftServer.I[0x7A ^ 0x53] = I("\u0018%.", "HkiEO");
        MinecraftServer.I[0x5E ^ 0x74] = I("\t\n\u0013+q\u0004\u0006\u0006-.B\u001b\t-p\u000f\n\u0014/}YG", "mkgJK");
        MinecraftServer.I[0xB8 ^ 0x93] = I(")\u001c\u0012\u001e2\u0004T\u0013R:\u0005\u0012\u0003R%\u000f\u0001\u0011\u0017$J\u001a\u0004\u001d8", "jsgrV");
        MinecraftServer.I[0x6D ^ 0x41] = I("M", "cbYer");
        MinecraftServer.I[0x51 ^ 0x7C] = I("=?\u0002?", "OPmKa");
        MinecraftServer.I[0x47 ^ 0x69] = I("76$5", "DWRPA");
        MinecraftServer.I[0x58 ^ 0x77] = I("\u0006)\u0004<\b\u001b&\u000f", "rHhPq");
        MinecraftServer.I[0xA3 ^ 0x93] = I("9% 8\u0015/9", "JKOWe");
        MinecraftServer.I[0x34 ^ 0x5] = I("\u0007\u0003\u0016\u0012", "mltaq");
        MinecraftServer.I[0x12 ^ 0x20] = I("\u001b#\u001a\u00074\u0004", "wFlbX");
        MinecraftServer.I[0x38 ^ 0xB] = I("\u001b\u001a\u0000\u0011>\u0016\u001d\u000e", "osmtm");
        MinecraftServer.I[0x6 ^ 0x32] = I(",\u0007\u001d\u000b;$\u0001>\u00026\u000b\u0011:\u0006'", "HhYjB");
        MinecraftServer.I[0x9B ^ 0xAE] = I("\u001c\n0\u000f", "hcSdL");
        MinecraftServer.I[0x71 ^ 0x47] = I("<1\u0002\b:\r \u000e\u0003j\r \u0002\u0006#\u0017.A\u001a%\u000b%\u0005", "yIamJ");
        MinecraftServer.I[0x93 ^ 0xA4] = I("\u00060\u00156:7!\u0019=j7!\u00158#-/V$%1$\u0012s/-<\u001f'#&;", "CHvSJ");
        MinecraftServer.I[0x16 ^ 0x2E] = I("\u0004 \u00156\u0002\u0015 ", "pRtUi");
        MinecraftServer.I[0x73 ^ 0x4A] = I(".\u0004(\u001f,.\u001f/\u001e'", "MkFqI");
        MinecraftServer.I[0xB5 ^ 0x8F] = I("\u001e\u0016\t:?\u001c\t", "nzhCZ");
        MinecraftServer.I[0x42 ^ 0x79] = I("\u0017+\u001a\u001a\u0019\u0001.\u001c\u0002", "cByqx");
        MinecraftServer.I[0x5B ^ 0x67] = I("\"\u0001=\u0007\u000b\u0003D;\u0019\u001c\u0014\u0005+", "qdOqn");
        MinecraftServer.I[0x52 ^ 0x6F] = I("_LOTM", "nbwzu");
        MinecraftServer.I[0x2F ^ 0x11] = I(":5=?# 5", "LTSVO");
        MinecraftServer.I[0x55 ^ 0x6A] = I("\u0013>;\u0004\u0004/)&B=,?=\u0016\u0004,\"", "CLTbm");
        MinecraftServer.I[0xE8 ^ 0xA8] = I("\t-\u0005.\u0012+a'8\u000275", "YAdWw");
        MinecraftServer.I[0x5F ^ 0x1E] = I("k", "DEMYK");
        MinecraftServer.I[0xFB ^ 0xB9] = I("o", "OmQPi");
        MinecraftServer.I[0x26 ^ 0x65] = I("K", "dFIjj");
        MinecraftServer.I[0x63 ^ 0x27] = I("c", "CpmIY");
        MinecraftServer.I[0x35 ^ 0x70] = I("\u0010\b\u0013/\u00121", "CmaYw");
        MinecraftServer.I[0xC7 ^ 0x81] = I("\u001c\u0010\u0003\u0002(\u0007\u0011\u0019\u0002\u0012\u000e\u0016\u000b\u0014!\u000e\u001c", "kxjvM");
        MinecraftServer.I[0xDB ^ 0x9C] = I("%=.\u000e\u0011><4\u000e+1:2\u0014\u0000", "RUGzt");
        MinecraftServer.I[0x89 ^ 0xC1] = I("#;5*\u0006!$\u000b0\u0016!%1=\u0017", "SWTSc");
        MinecraftServer.I[0xCB ^ 0x82] = I("%\u0018\u00044?'\u0007: ;-", "UteMZ");
        MinecraftServer.I[0xF3 ^ 0xB9] = I("#\n\u000e;6!\u00150166\b", "SfoBS");
        MinecraftServer.I[0x8D ^ 0xC6] = I("\u0010:\u001d\u00064\u0004<\f\u001d", "eIxuk");
        MinecraftServer.I[0x6F ^ 0x23] = I("&\u0007,-15\u00131\u0017", "ArErB");
        MinecraftServer.I[0x42 ^ 0xF] = I("\u000f#\u0002\u001a-\u000f)", "jMcxA");
        MinecraftServer.I[0x57 ^ 0x19] = I("\"\b6\u0019\f*\u0004!", "FaExn");
        MinecraftServer.I[0xC7 ^ 0x88] = I("?>9\u001d\u000e$&2", "MKWBz");
        MinecraftServer.I[0x7C ^ 0x2C] = I("\u0007\u00136\r:\u000f\u0006:\r#\u0015", "feQRN");
        MinecraftServer.I[0x90 ^ 0xC1] = I("\u0012\u0015\u0003>+>", "ezqRO");
        MinecraftServer.I[0xB ^ 0x59] = I("-+60/\u0015\u001e!0-\u001e-", "ppRYB");
        MinecraftServer.I[0xCB ^ 0x98] = I("5\u000e%\u001f\u0012\u0019", "BaWsv");
        MinecraftServer.I[0xCA ^ 0x9E] = I("\u001e(\u0007?\u001c&.", "CsjPx");
        MinecraftServer.I[0x29 ^ 0x7C] = I("#\r5\u00050\u000f", "TbGiT");
        MinecraftServer.I[0xE ^ 0x58] = I("\u001f?'\n?$\r \u001656\u001d\u001e", "BdCcY");
        MinecraftServer.I[0xF1 ^ 0xA6] = I("5\u001c\u0004\u0001!\u0019", "BsvmE");
        MinecraftServer.I[0x63 ^ 0x3B] = I(";9\u00033\u001e\u0002\u0001\u0004 \t;", "fbkRl");
        MinecraftServer.I[0x3E ^ 0x67] = I("\u001e\u00070\u0014\u000b2", "ihBxo");
        MinecraftServer.I[0xEA ^ 0xB0] = I("48!$:\f\u0011'5;\u001b<( 9\f>", "icFAT");
        MinecraftServer.I[0xDB ^ 0x80] = I("2\u0016$'\u0013\u001e", "EyVKw");
        MinecraftServer.I[0x10 ^ 0x4C] = I("*\u000b\u0012\u0001\u0016\u0012\"\u0014\u0010\u0017\u0005\u000f\u0003\u0001\n\u00049\u001a\n%", "wPudx");
        MinecraftServer.I[0x72 ^ 0x2F] = I("\u001e\u001d!\u001e\u00112", "irSru");
        MinecraftServer.I[0xD ^ 0x53] = I("\u001f\u0015\u000e6\u000b%&\u0012\u000e", "BNfSb");
        MinecraftServer.I[0x1F ^ 0x40] = I("/$\u000b$6\u0003", "XKyHR");
        MinecraftServer.I[0xDC ^ 0xBC] = I("\u001a\u0002\u0015\u00190)2\u0005.)(8\u0012\u0014!\u001a", "GYvqE");
        MinecraftServer.I[0xB ^ 0x6A] = I("$\u000e;/\u0005 ", "SaICa");
        MinecraftServer.I[0xCD ^ 0xAF] = I("\u001a=/\u0014+\f$-\u0012>\f&", "iTAsG");
        MinecraftServer.I[0x1F ^ 0x7C] = I("\u000b\t3\u001a3\n3#\u001e7\u0016\b", "xlAlV");
        MinecraftServer.I[0xFF ^ 0x9B] = I("2\u0019&9\u001c \u001c?\t\u001d!\t+", "UlOfo");
        MinecraftServer.I[0x50 ^ 0x35] = I("/'\n+9\"1\u0018", "GBkOU");
        MinecraftServer.I[0x2 ^ 0x64] = I("\u0015\u000f\u001d\u0000<\u0014\u000e\b\u0014", "fzmpS");
        MinecraftServer.I[0xDE ^ 0xB9] = I("\u001e\u0015(\u0001-\u001b\u0004)\f", "zpLhN");
        MinecraftServer.I[0xFE ^ 0x96] = I("5\u0014\t3&)\u001c\n6\u000b\"7\u00022\u0001$\u0010\u0004<", "FqgWe");
    }
    
    public void startServerThread() {
        (this.serverThread = new Thread(this, MinecraftServer.I[0x1B ^ 0x27])).start();
    }
    
    public boolean serverIsInRunLoop() {
        return this.serverIsRunning;
    }
    
    @Override
    public BlockPos getPosition() {
        return BlockPos.ORIGIN;
    }
    
    public boolean isSinglePlayer() {
        if (this.serverOwner != null) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public File getDataDirectory() {
        return new File(MinecraftServer.I[0xB8 ^ 0x94]);
    }
    
    public boolean isFlightAllowed() {
        return this.allowFlight;
    }
    
    protected boolean allowSpawnMonsters() {
        return " ".length() != 0;
    }
    
    public synchronized String getUserMessage() {
        return this.userMessage;
    }
    
    public abstract boolean isCommandBlockEnabled();
    
    static ServerConfigurationManager access$1(final MinecraftServer minecraftServer) {
        return minecraftServer.serverConfigManager;
    }
    
    public int getCurrentPlayerCount() {
        return this.serverConfigManager.getCurrentPlayerCount();
    }
    
    public int getSpawnProtectionSize() {
        return 0x66 ^ 0x76;
    }
    
    public int getTickCounter() {
        return this.tickCounter;
    }
    
    public ServerStatusResponse getServerStatusResponse() {
        return this.statusResponse;
    }
    
    public int getMaxPlayerIdleMinutes() {
        return this.maxPlayerIdleMinutes;
    }
    
    public abstract boolean func_181034_q();
    
    static {
        I();
        logger = LogManager.getLogger();
        USER_CACHE_FILE = new File(MinecraftServer.I["".length()]);
    }
    
    public void setCanSpawnAnimals(final boolean canSpawnAnimals) {
        this.canSpawnAnimals = canSpawnAnimals;
    }
    
    protected void saveAllWorlds(final boolean b) {
        if (!this.worldIsBeingDeleted) {
            final WorldServer[] worldServers;
            final int length = (worldServers = this.worldServers).length;
            int i = "".length();
            "".length();
            if (3 <= -1) {
                throw null;
            }
            while (i < length) {
                final WorldServer worldServer = worldServers[i];
                if (worldServer != null) {
                    if (!b) {
                        MinecraftServer.logger.info(MinecraftServer.I[0x3 ^ 0x10] + worldServer.getWorldInfo().getWorldName() + MinecraftServer.I[0x99 ^ 0x8D] + worldServer.provider.getDimensionName());
                    }
                    try {
                        worldServer.saveAllChunks(" ".length() != 0, null);
                        "".length();
                        if (1 >= 2) {
                            throw null;
                        }
                    }
                    catch (MinecraftException ex) {
                        MinecraftServer.logger.warn(ex.getMessage());
                    }
                }
                ++i;
            }
        }
    }
    
    public abstract EnumDifficulty getDifficulty();
    
    @Override
    public Entity getCommandSenderEntity() {
        return null;
    }
    
    public abstract boolean func_181035_ah();
    
    public int getNetworkCompressionTreshold() {
        return 109 + 155 - 257 + 249;
    }
    
    public GameProfile[] getGameProfiles() {
        return this.serverConfigManager.getAllProfiles();
    }
    
    public PlayerProfileCache getPlayerProfileCache() {
        return this.profileCache;
    }
    
    public void deleteWorldAndStopServer() {
        this.worldIsBeingDeleted = (" ".length() != 0);
        this.getActiveAnvilConverter().flushCache();
        int i = "".length();
        "".length();
        if (4 <= 1) {
            throw null;
        }
        while (i < this.worldServers.length) {
            final WorldServer worldServer = this.worldServers[i];
            if (worldServer != null) {
                worldServer.flush();
            }
            ++i;
        }
        this.getActiveAnvilConverter().deleteWorldDirectory(this.worldServers["".length()].getSaveHandler().getWorldDirectoryName());
        this.initiateShutdown();
    }
    
    public String getWorldName() {
        return this.worldName;
    }
    
    public ISaveFormat getActiveAnvilConverter() {
        return this.anvilConverterForAnvilFile;
    }
    
    @Override
    public boolean isCallingFromMinecraftThread() {
        if (Thread.currentThread() == this.serverThread) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public void addChatMessage(final IChatComponent chatComponent) {
        MinecraftServer.logger.info(chatComponent.getUnformattedText());
    }
    
    protected void outputPercentRemaining(final String currentTask, final int percentDone) {
        this.currentTask = currentTask;
        this.percentDone = percentDone;
        MinecraftServer.logger.info(String.valueOf(currentTask) + MinecraftServer.I[0xAD ^ 0xBC] + percentDone + MinecraftServer.I[0x7E ^ 0x6C]);
    }
    
    public void logWarning(final String s) {
        MinecraftServer.logger.warn(s);
    }
    
    @Override
    public World getEntityWorld() {
        return this.worldServers["".length()];
    }
    
    @Override
    public ListenableFuture<Object> addScheduledTask(final Runnable runnable) {
        Validate.notNull((Object)runnable);
        return this.callFromMainThread(Executors.callable(runnable));
    }
    
    public abstract String shareToLAN(final WorldSettings.GameType p0, final boolean p1);
    
    @Override
    public boolean sendCommandFeedback() {
        return getServer().worldServers["".length()].getGameRules().getBoolean(MinecraftServer.I[0xEF ^ 0x87]);
    }
    
    public String getResourcePackUrl() {
        return this.resourcePackUrl;
    }
    
    protected void loadAllWorlds(final String s, final String worldName, final long n, final WorldType worldType, final String worldName2) {
        this.convertMapIfNeeded(s);
        this.setUserMessage(MinecraftServer.I[0x22 ^ 0x2B]);
        this.worldServers = new WorldServer["   ".length()];
        this.timeOfLastDimensionTick = new long[this.worldServers.length][0x5B ^ 0x3F];
        final ISaveHandler saveLoader = this.anvilConverterForAnvilFile.getSaveLoader(s, " ".length() != 0);
        this.setResourcePackFromWorld(this.getFolderName(), saveLoader);
        WorldInfo loadWorldInfo = saveLoader.loadWorldInfo();
        WorldSettings demoWorldSettings;
        if (loadWorldInfo == null) {
            if (this.isDemo()) {
                demoWorldSettings = DemoWorldServer.demoWorldSettings;
                "".length();
                if (1 >= 3) {
                    throw null;
                }
            }
            else {
                demoWorldSettings = new WorldSettings(n, this.getGameType(), this.canStructuresSpawn(), this.isHardcore(), worldType);
                demoWorldSettings.setWorldName(worldName2);
                if (this.enableBonusChest) {
                    demoWorldSettings.enableBonusChest();
                }
            }
            loadWorldInfo = new WorldInfo(demoWorldSettings, worldName);
            "".length();
            if (0 <= -1) {
                throw null;
            }
        }
        else {
            loadWorldInfo.setWorldName(worldName);
            demoWorldSettings = new WorldSettings(loadWorldInfo);
        }
        int i = "".length();
        "".length();
        if (-1 >= 1) {
            throw null;
        }
        while (i < this.worldServers.length) {
            int n2 = "".length();
            if (i == " ".length()) {
                n2 = -" ".length();
            }
            if (i == "  ".length()) {
                n2 = " ".length();
            }
            if (i == 0) {
                if (this.isDemo()) {
                    this.worldServers[i] = (WorldServer)new DemoWorldServer(this, saveLoader, loadWorldInfo, n2, this.theProfiler).init();
                    "".length();
                    if (1 >= 2) {
                        throw null;
                    }
                }
                else {
                    this.worldServers[i] = (WorldServer)new WorldServer(this, saveLoader, loadWorldInfo, n2, this.theProfiler).init();
                }
                this.worldServers[i].initialize(demoWorldSettings);
                "".length();
                if (2 < 1) {
                    throw null;
                }
            }
            else {
                this.worldServers[i] = (WorldServer)new WorldServerMulti(this, saveLoader, n2, this.worldServers["".length()], this.theProfiler).init();
            }
            this.worldServers[i].addWorldAccess(new WorldManager(this, this.worldServers[i]));
            if (!this.isSinglePlayer()) {
                this.worldServers[i].getWorldInfo().setGameType(this.getGameType());
            }
            ++i;
        }
        this.serverConfigManager.setPlayerManager(this.worldServers);
        this.setDifficultyForAllWorlds(this.getDifficulty());
        this.initialWorldChunkLoad();
    }
    
    public void canCreateBonusChest(final boolean enableBonusChest) {
        this.enableBonusChest = enableBonusChest;
    }
    
    public void tick() {
        final long nanoTime = System.nanoTime();
        this.tickCounter += " ".length();
        if (this.startProfiling) {
            this.startProfiling = ("".length() != 0);
            this.theProfiler.profilingEnabled = (" ".length() != 0);
            this.theProfiler.clearProfiling();
        }
        this.theProfiler.startSection(MinecraftServer.I[0x87 ^ 0xAA]);
        this.updateTimeLightAndEntities();
        if (nanoTime - this.nanoTimeSinceStatusRefresh >= 5000000000L) {
            this.nanoTimeSinceStatusRefresh = nanoTime;
            this.statusResponse.setPlayerCountData(new ServerStatusResponse.PlayerCountData(this.getMaxPlayers(), this.getCurrentPlayerCount()));
            final GameProfile[] players = new GameProfile[Math.min(this.getCurrentPlayerCount(), 0xAB ^ 0xA7)];
            final int randomIntegerInRange = MathHelper.getRandomIntegerInRange(this.random, "".length(), this.getCurrentPlayerCount() - players.length);
            int i = "".length();
            "".length();
            if (2 != 2) {
                throw null;
            }
            while (i < players.length) {
                players[i] = this.serverConfigManager.func_181057_v().get(randomIntegerInRange + i).getGameProfile();
                ++i;
            }
            Collections.shuffle(Arrays.asList(players));
            this.statusResponse.getPlayerCountData().setPlayers(players);
        }
        if (this.tickCounter % (663 + 481 - 478 + 234) == 0) {
            this.theProfiler.startSection(MinecraftServer.I[0x12 ^ 0x3C]);
            this.serverConfigManager.saveAllPlayerData();
            this.saveAllWorlds(" ".length() != 0);
            this.theProfiler.endSection();
        }
        this.theProfiler.startSection(MinecraftServer.I[0x5E ^ 0x71]);
        this.tickTimeArray[this.tickCounter % (0x7D ^ 0x19)] = System.nanoTime() - nanoTime;
        this.theProfiler.endSection();
        this.theProfiler.startSection(MinecraftServer.I[0xAE ^ 0x9E]);
        if (!this.usageSnooper.isSnooperRunning() && this.tickCounter > (0x62 ^ 0x6)) {
            this.usageSnooper.startSnooper();
        }
        if (this.tickCounter % (443 + 179 - 222 + 5600) == 0) {
            this.usageSnooper.addMemoryStatsToSnooper();
        }
        this.theProfiler.endSection();
        this.theProfiler.endSection();
    }
    
    public int getMaxPlayers() {
        return this.serverConfigManager.getMaxPlayers();
    }
    
    static Logger access$0() {
        return MinecraftServer.logger;
    }
    
    public void setKeyPair(final KeyPair serverKeyPair) {
        this.serverKeyPair = serverKeyPair;
    }
    
    private void addFaviconToStatusResponse(final ServerStatusResponse serverStatusResponse) {
        final File file = this.getFile(MinecraftServer.I[0x75 ^ 0x53]);
        if (file.isFile()) {
            final ByteBuf buffer = Unpooled.buffer();
            try {
                final BufferedImage read = ImageIO.read(file);
                int n;
                if (read.getWidth() == (0xD5 ^ 0x95)) {
                    n = " ".length();
                    "".length();
                    if (4 <= 3) {
                        throw null;
                    }
                }
                else {
                    n = "".length();
                }
                Validate.validState((boolean)(n != 0), MinecraftServer.I[0xAB ^ 0x8C], new Object["".length()]);
                int n2;
                if (read.getHeight() == (0xE ^ 0x4E)) {
                    n2 = " ".length();
                    "".length();
                    if (4 <= 1) {
                        throw null;
                    }
                }
                else {
                    n2 = "".length();
                }
                Validate.validState((boolean)(n2 != 0), MinecraftServer.I[0x66 ^ 0x4E], new Object["".length()]);
                ImageIO.write(read, MinecraftServer.I[0x4F ^ 0x66], (OutputStream)new ByteBufOutputStream(buffer));
                serverStatusResponse.setFavicon(MinecraftServer.I[0xEE ^ 0xC4] + Base64.encode(buffer).toString(Charsets.UTF_8));
                "".length();
                if (3 == 2) {
                    throw null;
                }
            }
            catch (Exception ex) {
                MinecraftServer.logger.error(MinecraftServer.I[0xAD ^ 0x86], (Throwable)ex);
                buffer.release();
                "".length();
                if (3 == 4) {
                    throw null;
                }
                return;
            }
            finally {
                buffer.release();
            }
            buffer.release();
        }
    }
    
    public String getFolderName() {
        return this.folderName;
    }
    
    public <V> ListenableFuture<V> callFromMainThread(final Callable<V> callable) {
        Validate.notNull((Object)callable);
        if (!this.isCallingFromMinecraftThread() && !this.isServerStopped()) {
            final ListenableFutureTask create = ListenableFutureTask.create((Callable)callable);
            synchronized (this.futureTaskQueue) {
                this.futureTaskQueue.add((FutureTask<?>)create);
                // monitorexit(this.futureTaskQueue)
                return (ListenableFuture<V>)create;
            }
        }
        try {
            return (ListenableFuture<V>)Futures.immediateFuture((Object)callable.call());
        }
        catch (Exception ex) {
            return (ListenableFuture<V>)Futures.immediateFailedCheckedFuture(ex);
        }
    }
    
    public String getServerOwner() {
        return this.serverOwner;
    }
    
    public boolean isAnnouncingPlayerAchievements() {
        return " ".length() != 0;
    }
    
    public void setAllowPvp(final boolean pvpEnabled) {
        this.pvpEnabled = pvpEnabled;
    }
    
    public NetworkSystem getNetworkSystem() {
        return this.networkSystem;
    }
    
    public MinecraftSessionService getMinecraftSessionService() {
        return this.sessionService;
    }
    
    public void enableProfiling() {
        this.startProfiling = (" ".length() != 0);
    }
    
    public String[] getAllUsernames() {
        return this.serverConfigManager.getAllUsernames();
    }
    
    public abstract WorldSettings.GameType getGameType();
    
    public void setDemo(final boolean isDemo) {
        this.isDemo = isDemo;
    }
    
    protected abstract boolean startServer() throws IOException;
    
    public boolean getCanSpawnAnimals() {
        return this.canSpawnAnimals;
    }
    
    public void setBuildLimit(final int buildLimit) {
        this.buildLimit = buildLimit;
    }
    
    protected void setInstance() {
        MinecraftServer.mcServer = this;
    }
    
    public File getFile(final String s) {
        return new File(this.getDataDirectory(), s);
    }
    
    public String getMOTD() {
        return this.motd;
    }
    
    @Override
    public void setCommandStat(final CommandResultStats.Type type, final int n) {
    }
    
    public Entity getEntityFromUuid(final UUID uuid) {
        final WorldServer[] worldServers;
        final int length = (worldServers = this.worldServers).length;
        int i = "".length();
        "".length();
        if (3 < 2) {
            throw null;
        }
        while (i < length) {
            final WorldServer worldServer = worldServers[i];
            if (worldServer != null) {
                final Entity entityFromUuid = worldServer.getEntityFromUuid(uuid);
                if (entityFromUuid != null) {
                    return entityFromUuid;
                }
            }
            ++i;
        }
        return null;
    }
    
    public void setDifficultyForAllWorlds(final EnumDifficulty enumDifficulty) {
        int i = "".length();
        "".length();
        if (3 <= 0) {
            throw null;
        }
        while (i < this.worldServers.length) {
            final WorldServer worldServer = this.worldServers[i];
            if (worldServer != null) {
                if (worldServer.getWorldInfo().isHardcoreModeEnabled()) {
                    worldServer.getWorldInfo().setDifficulty(EnumDifficulty.HARD);
                    worldServer.setAllowedSpawnTypes(" ".length() != 0, " ".length() != 0);
                    "".length();
                    if (4 < 1) {
                        throw null;
                    }
                }
                else if (this.isSinglePlayer()) {
                    worldServer.getWorldInfo().setDifficulty(enumDifficulty);
                    final WorldServer worldServer2 = worldServer;
                    int n;
                    if (worldServer.getDifficulty() != EnumDifficulty.PEACEFUL) {
                        n = " ".length();
                        "".length();
                        if (3 < 1) {
                            throw null;
                        }
                    }
                    else {
                        n = "".length();
                    }
                    worldServer2.setAllowedSpawnTypes(n != 0, " ".length() != 0);
                    "".length();
                    if (2 != 2) {
                        throw null;
                    }
                }
                else {
                    worldServer.getWorldInfo().setDifficulty(enumDifficulty);
                    worldServer.setAllowedSpawnTypes(this.allowSpawnMonsters(), this.canSpawnAnimals);
                }
            }
            ++i;
        }
    }
    
    public ICommandManager getCommandManager() {
        return this.commandManager;
    }
    
    public void stopServer() {
        if (!this.worldIsBeingDeleted) {
            MinecraftServer.logger.info(MinecraftServer.I[0x5D ^ 0x48]);
            if (this.getNetworkSystem() != null) {
                this.getNetworkSystem().terminateEndpoints();
            }
            if (this.serverConfigManager != null) {
                MinecraftServer.logger.info(MinecraftServer.I[0x9D ^ 0x8B]);
                this.serverConfigManager.saveAllPlayerData();
                this.serverConfigManager.removeAllPlayers();
            }
            if (this.worldServers != null) {
                MinecraftServer.logger.info(MinecraftServer.I[0x44 ^ 0x53]);
                this.saveAllWorlds("".length() != 0);
                int i = "".length();
                "".length();
                if (1 >= 2) {
                    throw null;
                }
                while (i < this.worldServers.length) {
                    this.worldServers[i].flush();
                    ++i;
                }
            }
            if (this.usageSnooper.isSnooperRunning()) {
                this.usageSnooper.stopSnooper();
            }
        }
    }
    
    public abstract boolean isHardcore();
    
    public boolean getCanSpawnNPCs() {
        return this.canSpawnNPCs;
    }
    
    public GameProfileRepository getGameProfileRepository() {
        return this.profileRepo;
    }
    
    public int getMaxWorldSize() {
        return 10277755 + 488460 + 1786195 + 17447574;
    }
    
    public void setCanSpawnNPCs(final boolean canSpawnNPCs) {
        this.canSpawnNPCs = canSpawnNPCs;
    }
    
    protected void systemExitNow() {
    }
    
    public boolean isServerRunning() {
        return this.serverRunning;
    }
    
    protected void clearCurrentTask() {
        this.currentTask = null;
        this.percentDone = "".length();
    }
    
    @Override
    public String getName() {
        return MinecraftServer.I[0x2B ^ 0x6E];
    }
    
    public abstract boolean func_183002_r();
    
    public void setPlayerIdleTimeout(final int maxPlayerIdleMinutes) {
        this.maxPlayerIdleMinutes = maxPlayerIdleMinutes;
    }
    
    @Override
    public boolean isSnooperEnabled() {
        return " ".length() != 0;
    }
    
    public Proxy getServerProxy() {
        return this.serverProxy;
    }
    
    public void refreshStatusNextTick() {
        this.nanoTimeSinceStatusRefresh = 0L;
    }
    
    public boolean isPVPEnabled() {
        return this.pvpEnabled;
    }
    
    protected ServerCommandManager createNewCommandManager() {
        return new ServerCommandManager();
    }
    
    public static MinecraftServer getServer() {
        return MinecraftServer.mcServer;
    }
    
    @Override
    public boolean canCommandSenderUseCommand(final int n, final String s) {
        return " ".length() != 0;
    }
    
    public CrashReport addServerInfoToCrashReport(final CrashReport crashReport) {
        crashReport.getCategory().addCrashSectionCallable(MinecraftServer.I[0x64 ^ 0x5B], new Callable<String>(this) {
            private static final String[] I;
            final MinecraftServer this$0;
            
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
                    if (4 <= -1) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            private static void I() {
                (I = new String[" ".length()])["".length()] = I("\fJ0QP&\f\u0002\u0010\u001a.\u0000\u0015X", "Beqqx");
            }
            
            static {
                I();
            }
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
            
            @Override
            public String call() throws Exception {
                String nameOfLastSection;
                if (this.this$0.theProfiler.profilingEnabled) {
                    nameOfLastSection = this.this$0.theProfiler.getNameOfLastSection();
                    "".length();
                    if (0 < -1) {
                        throw null;
                    }
                }
                else {
                    nameOfLastSection = MinecraftServer$2.I["".length()];
                }
                return nameOfLastSection;
            }
        });
        if (this.serverConfigManager != null) {
            crashReport.getCategory().addCrashSectionCallable(MinecraftServer.I[0x70 ^ 0x30], new Callable<String>(this) {
                private static final String[] I;
                final MinecraftServer this$0;
                
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
                        if (-1 == 0) {
                            throw null;
                        }
                    }
                    return sb.toString();
                }
                
                static {
                    I();
                }
                
                @Override
                public Object call() throws Exception {
                    return this.call();
                }
                
                @Override
                public String call() {
                    return String.valueOf(MinecraftServer.access$1(this.this$0).getCurrentPlayerCount()) + MinecraftServer$3.I["".length()] + MinecraftServer.access$1(this.this$0).getMaxPlayers() + MinecraftServer$3.I[" ".length()] + MinecraftServer.access$1(this.this$0).func_181057_v();
                }
                
                private static void I() {
                    (I = new String["  ".length()])["".length()] = I("t[w", "TtWbu");
                    MinecraftServer$3.I[" ".length()] = I("Cw", "xWvBn");
                }
            });
        }
        return crashReport;
    }
    
    public boolean getForceGamemode() {
        return this.isGamemodeForced;
    }
    
    public boolean isServerInOnlineMode() {
        return this.onlineMode;
    }
    
    @Override
    public Vec3 getPositionVector() {
        return new Vec3(0.0, 0.0, 0.0);
    }
    
    @Override
    public void run() {
        try {
            if (this.startServer()) {
                this.currentTime = getCurrentTimeMillis();
                long n = 0L;
                this.statusResponse.setServerDescription(new ChatComponentText(this.motd));
                this.statusResponse.setProtocolVersionInfo(new ServerStatusResponse.MinecraftProtocolVersionIdentifier(MinecraftServer.I[0x4B ^ 0x53], 0x28 ^ 0x7));
                this.addFaviconToStatusResponse(this.statusResponse);
                "".length();
                if (3 != 3) {
                    throw null;
                }
                while (this.serverRunning) {
                    final long currentTimeMillis = getCurrentTimeMillis();
                    long n2 = currentTimeMillis - this.currentTime;
                    if (n2 > 2000L && this.currentTime - this.timeOfLastWarning >= 15000L) {
                        final Logger logger = MinecraftServer.logger;
                        final String s = MinecraftServer.I[0xA0 ^ 0xB9];
                        final Object[] array = new Object["  ".length()];
                        array["".length()] = n2;
                        array[" ".length()] = n2 / 50L;
                        logger.warn(s, array);
                        n2 = 2000L;
                        this.timeOfLastWarning = this.currentTime;
                    }
                    if (n2 < 0L) {
                        MinecraftServer.logger.warn(MinecraftServer.I[0x62 ^ 0x78]);
                        n2 = 0L;
                    }
                    n += n2;
                    this.currentTime = currentTimeMillis;
                    if (this.worldServers["".length()].areAllPlayersAsleep()) {
                        this.tick();
                        n = 0L;
                        "".length();
                        if (4 == 2) {
                            throw null;
                        }
                    }
                    else {
                        while (n > 50L) {
                            n -= 50L;
                            this.tick();
                        }
                    }
                    Thread.sleep(Math.max(1L, 50L - n));
                    this.serverIsRunning = (" ".length() != 0);
                }
                "".length();
                if (-1 != -1) {
                    throw null;
                }
            }
            else {
                this.finalTick(null);
                "".length();
                if (1 == 4) {
                    throw null;
                }
            }
        }
        catch (Throwable t) {
            MinecraftServer.logger.error(MinecraftServer.I[0x3A ^ 0x21], t);
            CrashReport crashReport;
            if (t instanceof ReportedException) {
                crashReport = this.addServerInfoToCrashReport(((ReportedException)t).getCrashReport());
                "".length();
                if (1 >= 3) {
                    throw null;
                }
            }
            else {
                crashReport = this.addServerInfoToCrashReport(new CrashReport(MinecraftServer.I[0x6F ^ 0x73], t));
            }
            final File file = new File(new File(this.getDataDirectory(), MinecraftServer.I[0x6B ^ 0x76]), MinecraftServer.I[0xB7 ^ 0xA9] + new SimpleDateFormat(MinecraftServer.I[0x3F ^ 0x20]).format(new Date()) + MinecraftServer.I[0x9E ^ 0xBE]);
            if (crashReport.saveToFile(file)) {
                MinecraftServer.logger.error(MinecraftServer.I[0x9A ^ 0xBB] + file.getAbsolutePath());
                "".length();
                if (1 <= 0) {
                    throw null;
                }
            }
            else {
                MinecraftServer.logger.error(MinecraftServer.I[0x4E ^ 0x6C]);
            }
            this.finalTick(crashReport);
            try {
                this.serverStopped = (" ".length() != 0);
                this.stopServer();
                "".length();
                if (2 <= 0) {
                    throw null;
                }
            }
            catch (Throwable t2) {
                MinecraftServer.logger.error(MinecraftServer.I[0xB7 ^ 0x94], t2);
                this.systemExitNow();
                "".length();
                if (4 <= 0) {
                    throw null;
                }
                return;
            }
            finally {
                this.systemExitNow();
            }
            this.systemExitNow();
            "".length();
            if (3 == 0) {
                throw null;
            }
            return;
        }
        finally {
            Label_0755: {
                try {
                    this.serverStopped = (" ".length() != 0);
                    this.stopServer();
                    "".length();
                    if (-1 == 1) {
                        throw null;
                    }
                }
                catch (Throwable t3) {
                    MinecraftServer.logger.error(MinecraftServer.I[0x39 ^ 0x1D], t3);
                    this.systemExitNow();
                    "".length();
                    if (4 <= 1) {
                        throw null;
                    }
                    break Label_0755;
                }
                finally {
                    this.systemExitNow();
                }
                this.systemExitNow();
            }
        }
        try {
            this.serverStopped = (" ".length() != 0);
            this.stopServer();
            "".length();
            if (-1 >= 3) {
                throw null;
            }
        }
        catch (Throwable t4) {
            MinecraftServer.logger.error(MinecraftServer.I[0x41 ^ 0x64], t4);
            this.systemExitNow();
            "".length();
            if (2 <= -1) {
                throw null;
            }
            return;
        }
        finally {
            this.systemExitNow();
        }
        this.systemExitNow();
    }
    
    public void setResourcePack(final String resourcePackUrl, final String resourcePackHash) {
        this.resourcePackUrl = resourcePackUrl;
        this.resourcePackHash = resourcePackHash;
    }
    
    public void setGameType(final WorldSettings.GameType gameType) {
        int i = "".length();
        "".length();
        if (4 == -1) {
            throw null;
        }
        while (i < this.worldServers.length) {
            getServer().worldServers[i].getWorldInfo().setGameType(gameType);
            ++i;
        }
    }
    
    public String getServerModName() {
        return MinecraftServer.I[0x27 ^ 0x19];
    }
    
    public boolean isBlockProtected(final World world, final BlockPos blockPos, final EntityPlayer entityPlayer) {
        return "".length() != 0;
    }
    
    public MinecraftServer(final Proxy serverProxy, final File file) {
        this.usageSnooper = new PlayerUsageSnooper(MinecraftServer.I[" ".length()], this, getCurrentTimeMillis());
        this.playersOnline = (List<ITickable>)Lists.newArrayList();
        this.theProfiler = new Profiler();
        this.statusResponse = new ServerStatusResponse();
        this.random = new Random();
        this.serverPort = -" ".length();
        this.serverRunning = (" ".length() != 0);
        this.maxPlayerIdleMinutes = "".length();
        this.tickTimeArray = new long[0x5B ^ 0x3F];
        this.resourcePackUrl = MinecraftServer.I["  ".length()];
        this.resourcePackHash = MinecraftServer.I["   ".length()];
        this.nanoTimeSinceStatusRefresh = 0L;
        this.futureTaskQueue = (Queue<FutureTask<?>>)Queues.newArrayDeque();
        this.currentTime = getCurrentTimeMillis();
        this.serverProxy = serverProxy;
        MinecraftServer.mcServer = this;
        this.anvilFile = null;
        this.networkSystem = null;
        this.profileCache = new PlayerProfileCache(this, file);
        this.commandManager = null;
        this.anvilConverterForAnvilFile = null;
        this.authService = new YggdrasilAuthenticationService(serverProxy, UUID.randomUUID().toString());
        this.sessionService = this.authService.createMinecraftSessionService();
        this.profileRepo = this.authService.createProfileRepository();
    }
    
    @Override
    public IChatComponent getDisplayName() {
        return new ChatComponentText(this.getName());
    }
    
    public KeyPair getKeyPair() {
        return this.serverKeyPair;
    }
    
    public boolean isDemo() {
        return this.isDemo;
    }
    
    public List<String> getTabCompletions(final ICommandSender commandSender, String substring, final BlockPos blockPos) {
        final ArrayList arrayList = Lists.newArrayList();
        if (substring.startsWith(MinecraftServer.I[0x7B ^ 0x3A])) {
            substring = substring.substring(" ".length());
            int n;
            if (substring.contains(MinecraftServer.I[0xE1 ^ 0xA3])) {
                n = "".length();
                "".length();
                if (1 == 3) {
                    throw null;
                }
            }
            else {
                n = " ".length();
            }
            final int n2 = n;
            final List<String> tabCompletionOptions = this.commandManager.getTabCompletionOptions(commandSender, substring, blockPos);
            if (tabCompletionOptions != null) {
                final Iterator<String> iterator = tabCompletionOptions.iterator();
                "".length();
                if (1 <= -1) {
                    throw null;
                }
                while (iterator.hasNext()) {
                    final String s = iterator.next();
                    if (n2 != 0) {
                        arrayList.add(MinecraftServer.I[0x7E ^ 0x3D] + s);
                        "".length();
                        if (2 != 2) {
                            throw null;
                        }
                        continue;
                    }
                    else {
                        arrayList.add(s);
                    }
                }
            }
            return (List<String>)arrayList;
        }
        final String[] split = substring.split(MinecraftServer.I[0xDC ^ 0x98], -" ".length());
        final String s2 = split[split.length - " ".length()];
        final String[] allUsernames;
        final int length = (allUsernames = this.serverConfigManager.getAllUsernames()).length;
        int i = "".length();
        "".length();
        if (-1 >= 4) {
            throw null;
        }
        while (i < length) {
            final String s3 = allUsernames[i];
            if (CommandBase.doesStringStartWith(s2, s3)) {
                arrayList.add(s3);
            }
            ++i;
        }
        return (List<String>)arrayList;
    }
    
    public void initiateShutdown() {
        this.serverRunning = ("".length() != 0);
    }
    
    protected void setResourcePackFromWorld(final String s, final ISaveHandler saveHandler) {
        final File file = new File(saveHandler.getWorldDirectory(), MinecraftServer.I[0xB6 ^ 0xBB]);
        if (file.isFile()) {
            this.setResourcePack(MinecraftServer.I[0x1E ^ 0x10] + s + MinecraftServer.I[0x95 ^ 0x9A] + file.getName(), MinecraftServer.I[0x79 ^ 0x69]);
        }
    }
    
    public void setOnlineMode(final boolean onlineMode) {
        this.onlineMode = onlineMode;
    }
    
    public void setAllowFlight(final boolean allowFlight) {
        this.allowFlight = allowFlight;
    }
    
    public void setConfigManager(final ServerConfigurationManager serverConfigManager) {
        this.serverConfigManager = serverConfigManager;
    }
    
    protected void convertMapIfNeeded(final String s) {
        if (this.getActiveAnvilConverter().isOldMapFormat(s)) {
            MinecraftServer.logger.info(MinecraftServer.I[0x72 ^ 0x75]);
            this.setUserMessage(MinecraftServer.I[0x2B ^ 0x23]);
            this.getActiveAnvilConverter().convertMapFormat(s, new IProgressUpdate(this) {
                private static final String[] I;
                final MinecraftServer this$0;
                private long startTime = System.currentTimeMillis();
                
                @Override
                public void displaySavingString(final String s) {
                }
                
                private static void I() {
                    (I = new String["  ".length()])["".length()] = I("5\u0006)\u00140\u0004\u001d.\f2XGiB", "viGbU");
                    MinecraftServer$1.I[" ".length()] = I("F", "cRupG");
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
                        if (4 == 1) {
                            throw null;
                        }
                    }
                    return sb.toString();
                }
                
                @Override
                public void displayLoadingString(final String s) {
                }
                
                @Override
                public void setDoneWorking() {
                }
                
                @Override
                public void resetProgressAndMessage(final String s) {
                }
                
                @Override
                public void setLoadingProgress(final int n) {
                    if (System.currentTimeMillis() - this.startTime >= 1000L) {
                        this.startTime = System.currentTimeMillis();
                        MinecraftServer.access$0().info(MinecraftServer$1.I["".length()] + n + MinecraftServer$1.I[" ".length()]);
                    }
                }
                
                static {
                    I();
                }
            });
        }
    }
    
    public PlayerUsageSnooper getPlayerUsageSnooper() {
        return this.usageSnooper;
    }
    
    public void setWorldName(final String worldName) {
        this.worldName = worldName;
    }
    
    public void setServerOwner(final String serverOwner) {
        this.serverOwner = serverOwner;
    }
}

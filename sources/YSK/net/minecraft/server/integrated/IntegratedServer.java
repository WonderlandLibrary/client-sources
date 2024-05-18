package net.minecraft.server.integrated;

import net.minecraft.server.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.crash.*;
import net.minecraft.client.*;
import java.net.*;
import com.google.common.collect.*;
import net.minecraft.entity.player.*;
import com.google.common.util.concurrent.*;
import java.io.*;
import org.apache.logging.log4j.*;
import net.minecraft.command.*;
import net.minecraft.server.management.*;
import net.minecraft.world.demo.*;
import java.util.concurrent.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraft.world.storage.*;
import optfine.*;
import net.minecraft.profiler.*;

public class IntegratedServer extends MinecraftServer
{
    private final WorldSettings theWorldSettings;
    private boolean isPublic;
    private static final String __OBFID;
    private boolean isGamePaused;
    private static final Logger logger;
    private static final String[] I;
    private final Minecraft mc;
    private ThreadLanServerPing lanServerPing;
    
    @Override
    public CrashReport addServerInfoToCrashReport(CrashReport addServerInfoToCrashReport) {
        addServerInfoToCrashReport = super.addServerInfoToCrashReport(addServerInfoToCrashReport);
        addServerInfoToCrashReport.getCategory().addCrashSectionCallable(IntegratedServer.I[0x8C ^ 0x85], new Callable(this) {
            private static final String[] I;
            final IntegratedServer this$0;
            private static final String __OBFID;
            
            @Override
            public String call() throws Exception {
                return IntegratedServer$1.I["".length()];
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
                    if (3 < -1) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            static {
                I();
                __OBFID = IntegratedServer$1.I[" ".length()];
            }
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
            
            private static void I() {
                (I = new String["  ".length()])["".length()] = I("\u0003\u0016\u0018\r38\u0019\u0018\r0j+\t\u001a\"/\nL@9+\b3\u000b8#\u001d\u0002\u001cz>\u0000\u0018A", "JxlhT");
                IntegratedServer$1.I[" ".length()] = I("\u0007\u0000\u000fVFt|aWEt", "DLPfv");
            }
        });
        addServerInfoToCrashReport.getCategory().addCrashSectionCallable(IntegratedServer.I[0x7C ^ 0x76], new Callable(this) {
            final IntegratedServer this$0;
            private static final String __OBFID;
            private static final String[] I;
            
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
                    if (0 < -1) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            @Override
            public String call() throws Exception {
                final String clientModName = ClientBrandRetriever.getClientModName();
                if (!clientModName.equals(IntegratedServer$2.I["".length()])) {
                    return IntegratedServer$2.I[" ".length()] + clientModName + IntegratedServer$2.I["  ".length()];
                }
                final String serverModName = this.this$0.getServerModName();
                String string;
                if (!serverModName.equals(IntegratedServer$2.I["   ".length()])) {
                    string = IntegratedServer$2.I[0xC3 ^ 0xC7] + serverModName + IntegratedServer$2.I[0x58 ^ 0x5D];
                    "".length();
                    if (4 <= -1) {
                        throw null;
                    }
                }
                else if (Minecraft.class.getSigners() == null) {
                    string = IntegratedServer$2.I[0xA ^ 0xC];
                    "".length();
                    if (-1 == 0) {
                        throw null;
                    }
                }
                else {
                    string = IntegratedServer$2.I[0x1D ^ 0x1A];
                }
                return string;
            }
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
            
            private static void I() {
                (I = new String[0x59 ^ 0x50])["".length()] = I("9\u0012\u0006+\u000f#\u0012", "OshBc");
                IntegratedServer$2.I[" ".length()] = I("\u0017+6\"\u0004::5'\u0013hn\u0013'\u00036 $k\b!/>/J0&1%\r6*p?\u0005si", "SNPKj");
                IntegratedServer$2.I["  ".length()] = I("E", "bicFr");
                IntegratedServer$2.I["   ".length()] = I("\u0007\f+=\u0000\u001d\f", "qmETl");
                IntegratedServer$2.I[0x20 ^ 0x24] = I("\u0013\b/\b7>\u0019,\r lM\u001a\u0004+!\b;A;%\f'\u0005y4\u0005(\u000f>2\ti\u00156wJ", "WmIaY");
                IntegratedServer$2.I[0xA6 ^ 0xA3] = I("t", "SiGXa");
                IntegratedServer$2.I[0x62 ^ 0x64] = I(".\u0015\u000b\u0010T\u0014\u0019\u0012\f\u0018\u0001KY#\u0015\nP\n\u0000\u0013\u0016\u0011\r\u001c\u0006\u001dP\u0010\u0007\u0002\u0019\u001c\u0010\r\u0015\f\u0015\u001d", "xpyit");
                IntegratedServer$2.I[0x89 ^ 0x8E] = I("4\u0015\u0000\u000e\u000b\u0006\u000b\u0016L\u0004\u000b\u0013AL \u0005\u0015O\u001f\u0003\u0003\t\u000e\u0018\u001f\u0016\u0002O\u001e\u000f\t\u0006\u0006\u0002\u0019D\u0006\u0001\bJ\u0006\b\u001b\u0004J\u0007\u000b\u0006\t\u0004\u0010GDL\u0019\u0001\u0015\u0019\t\u0018D\u0005\u001d\r\u0004\u0000\u0014O\r\u0018\u0001G\u001a\u0002\u001e\u000b\u0012\f\u0004\u000f\u0000I", "dgolj");
                IntegratedServer$2.I[0x82 ^ 0x8A] = I(",(0BU_T^CV^", "odore");
            }
            
            static {
                I();
                __OBFID = IntegratedServer$2.I[0x97 ^ 0x9F];
            }
        });
        return addServerInfoToCrashReport;
    }
    
    @Override
    public String shareToLAN(final WorldSettings.GameType gameType, final boolean commandsAllowedForAll) {
        try {
            int suitableLanPort = -" ".length();
            try {
                suitableLanPort = HttpUtil.getSuitableLanPort();
                "".length();
                if (2 != 2) {
                    throw null;
                }
            }
            catch (IOException ex) {}
            if (suitableLanPort <= 0) {
                suitableLanPort = 23387 + 21133 - 38261 + 19305;
            }
            this.getNetworkSystem().addLanEndpoint(null, suitableLanPort);
            IntegratedServer.logger.info(IntegratedServer.I[0x84 ^ 0x88] + suitableLanPort);
            this.isPublic = (" ".length() != 0);
            (this.lanServerPing = new ThreadLanServerPing(this.getMOTD(), new StringBuilder(String.valueOf(suitableLanPort)).toString())).start();
            this.getConfigurationManager().setGameType(gameType);
            this.getConfigurationManager().setCommandsAllowedForAll(commandsAllowedForAll);
            return new StringBuilder(String.valueOf(suitableLanPort)).toString();
        }
        catch (IOException ex2) {
            return null;
        }
    }
    
    @Override
    public void initiateShutdown() {
        Futures.getUnchecked((Future)this.addScheduledTask(new Runnable(this) {
            final IntegratedServer this$0;
            private static final String __OBFID;
            private static final String[] I;
            
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
                    if (4 < 1) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            static {
                I();
                __OBFID = IntegratedServer$3.I["".length()];
            }
            
            private static void I() {
                (I = new String[" ".length()])["".length()] = I("\u0016\u0005\u0013hGey~kOe", "UILXw");
            }
            
            @Override
            public void run() {
                final Iterator<EntityPlayerMP> iterator = Lists.newArrayList((Iterable)this.this$0.getConfigurationManager().func_181057_v()).iterator();
                "".length();
                if (false) {
                    throw null;
                }
                while (iterator.hasNext()) {
                    this.this$0.getConfigurationManager().playerLoggedOut(iterator.next());
                }
            }
        }));
        super.initiateShutdown();
        if (this.lanServerPing != null) {
            this.lanServerPing.interrupt();
            this.lanServerPing = null;
        }
    }
    
    @Override
    public boolean canStructuresSpawn() {
        return "".length() != 0;
    }
    
    @Override
    public boolean isHardcore() {
        return this.theWorldSettings.getHardcoreEnabled();
    }
    
    private static void I() {
        (I = new String[0x8C ^ 0x81])["".length()] = I("\n<8vYy@Vw[p", "IpgFi");
        IntegratedServer.I[" ".length()] = I("\n2.\u0014\u0012", "ySXqa");
        IntegratedServer.I["  ".length()] = I("8\u001c*$#\u0002\u0006,v>\u0005\u001c.1%\n\u001c.2w\u0006\u0001%34\u0019\t-\"w\u0018\r9 2\u0019H=3%\u0018\u0001$8wZFsxo", "khKVW");
        IntegratedServer.I["   ".length()] = I("+04\u0007\u001a\r!3\f\u000fL>?\u001b\u0018\r<(", "lUZbh");
        IntegratedServer.I[0x40 ^ 0x44] = I("rGC", "RjcHo");
        IntegratedServer.I[0x68 ^ 0x6D] = I("\u0016\u0016\u0007-/\"W\u0010*%e\u0007\u001012,\u0019\u0016d&$\u001a\u0014jok", "EwqDA");
        IntegratedServer.I[0x57 ^ 0x51] = I("\u000b\u000f\u0011-\u0013!\t\u0017c\u0002!\u0002\u0007c\u0010!\u0014\u0004\"\u001a+\u0002P7\u001bh\u001c\roT.\u0015\u001f.T3\u001a", "HgpCt");
        IntegratedServer.I[0x45 ^ 0x42] = I("\u0006\t26\u0017,\u000f4x\u0014,\u000751\u00130\r'!P1\u000es#\riA5*\u001f(A(%", "EaSXp");
        IntegratedServer.I[0x33 ^ 0x3B] = I("/-7(-\r%t'-\u0005$= 1\u000f6-c0\fb/>", "cBTCD");
        IntegratedServer.I[0x1E ^ 0x17] = I("3.\u0001,", "gWqIo");
        IntegratedServer.I[0xA0 ^ 0xAA] = I(".\u0000Q\u000e*\u0003\u0017\u0014'", "gsqCE");
        IntegratedServer.I[0xCD ^ 0xC6] = I("\u0015\u000499 \u0003\u0018\t&1\u0014\u001e83\"", "fjVVP");
        IntegratedServer.I[0xAE ^ 0xA2] = I("\u0003\u00008\u0018\u001a5\u0010y\u0005\u0000p", "PtYjn");
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
            if (0 < -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public boolean func_181035_ah() {
        return "".length() != 0;
    }
    
    @Override
    public WorldSettings.GameType getGameType() {
        return this.theWorldSettings.getGameType();
    }
    
    @Override
    public void setGameType(final WorldSettings.GameType gameType) {
        this.getConfigurationManager().setGameType(gameType);
    }
    
    @Override
    protected void finalTick(final CrashReport crashReport) {
        this.mc.crashed(crashReport);
    }
    
    public void setStaticInstance() {
        this.setInstance();
    }
    
    public IntegratedServer(final Minecraft mc) {
        super(mc.getProxy(), new File(mc.mcDataDir, IntegratedServer.USER_CACHE_FILE.getName()));
        this.mc = mc;
        this.theWorldSettings = null;
    }
    
    public boolean getPublic() {
        return this.isPublic;
    }
    
    @Override
    public boolean func_183002_r() {
        return " ".length() != 0;
    }
    
    @Override
    public boolean isCommandBlockEnabled() {
        return " ".length() != 0;
    }
    
    static {
        I();
        __OBFID = IntegratedServer.I["".length()];
        logger = LogManager.getLogger();
    }
    
    @Override
    public int getOpPermissionLevel() {
        return 0x46 ^ 0x42;
    }
    
    @Override
    public void stopServer() {
        super.stopServer();
        if (this.lanServerPing != null) {
            this.lanServerPing.interrupt();
            this.lanServerPing = null;
        }
    }
    
    @Override
    public File getDataDirectory() {
        return this.mc.mcDataDir;
    }
    
    @Override
    public boolean isSnooperEnabled() {
        return Minecraft.getMinecraft().isSnooperEnabled();
    }
    
    @Override
    protected ServerCommandManager createNewCommandManager() {
        return new IntegratedServerCommandManager();
    }
    
    @Override
    public boolean func_181034_q() {
        return " ".length() != 0;
    }
    
    public IntegratedServer(final Minecraft mc, final String folderName, final String worldName, final WorldSettings worldSettings) {
        super(new File(mc.mcDataDir, IntegratedServer.I[" ".length()]), mc.getProxy(), new File(mc.mcDataDir, IntegratedServer.USER_CACHE_FILE.getName()));
        this.setServerOwner(mc.getSession().getUsername());
        this.setFolderName(folderName);
        this.setWorldName(worldName);
        this.setDemo(mc.isDemo());
        this.canCreateBonusChest(worldSettings.isBonusChestEnabled());
        this.setBuildLimit(187 + 238 - 274 + 105);
        this.setConfigManager(new IntegratedPlayerList(this));
        this.mc = mc;
        WorldSettings demoWorldSettings;
        if (this.isDemo()) {
            demoWorldSettings = DemoWorldServer.demoWorldSettings;
            "".length();
            if (3 == 4) {
                throw null;
            }
        }
        else {
            demoWorldSettings = worldSettings;
        }
        this.theWorldSettings = demoWorldSettings;
        final ReflectorMethod modLoader_registerServer = Reflector.ModLoader_registerServer;
        final Object[] array = new Object[" ".length()];
        array["".length()] = this;
        Reflector.callVoid(modLoader_registerServer, array);
    }
    
    @Override
    protected boolean startServer() throws IOException {
        IntegratedServer.logger.info(IntegratedServer.I["  ".length()]);
        this.setOnlineMode(" ".length() != 0);
        this.setCanSpawnAnimals(" ".length() != 0);
        this.setCanSpawnNPCs(" ".length() != 0);
        this.setAllowPvp(" ".length() != 0);
        this.setAllowFlight(" ".length() != 0);
        IntegratedServer.logger.info(IntegratedServer.I["   ".length()]);
        this.setKeyPair(CryptManager.generateKeyPair());
        if (Reflector.FMLCommonHandler_handleServerAboutToStart.exists()) {
            final Object call = Reflector.call(Reflector.FMLCommonHandler_instance, new Object["".length()]);
            final ReflectorMethod fmlCommonHandler_handleServerAboutToStart = Reflector.FMLCommonHandler_handleServerAboutToStart;
            final Object[] array = new Object[" ".length()];
            array["".length()] = this;
            if (!Reflector.callBoolean(call, fmlCommonHandler_handleServerAboutToStart, array)) {
                return "".length() != 0;
            }
        }
        this.loadAllWorlds(this.getFolderName(), this.getWorldName(), this.theWorldSettings.getSeed(), this.theWorldSettings.getTerrainType(), this.theWorldSettings.getWorldName());
        this.setMOTD(String.valueOf(this.getServerOwner()) + IntegratedServer.I[0x54 ^ 0x50] + this.worldServers["".length()].getWorldInfo().getWorldName());
        if (Reflector.FMLCommonHandler_handleServerStarting.exists()) {
            final Object call2 = Reflector.call(Reflector.FMLCommonHandler_instance, new Object["".length()]);
            if (Reflector.FMLCommonHandler_handleServerStarting.getReturnType() == Boolean.TYPE) {
                final Object o = call2;
                final ReflectorMethod fmlCommonHandler_handleServerStarting = Reflector.FMLCommonHandler_handleServerStarting;
                final Object[] array2 = new Object[" ".length()];
                array2["".length()] = this;
                return Reflector.callBoolean(o, fmlCommonHandler_handleServerStarting, array2);
            }
            final Object o2 = call2;
            final ReflectorMethod fmlCommonHandler_handleServerStarting2 = Reflector.FMLCommonHandler_handleServerStarting;
            final Object[] array3 = new Object[" ".length()];
            array3["".length()] = this;
            Reflector.callVoid(o2, fmlCommonHandler_handleServerStarting2, array3);
        }
        return " ".length() != 0;
    }
    
    @Override
    public EnumDifficulty getDifficulty() {
        EnumDifficulty enumDifficulty;
        if (this.mc.theWorld == null) {
            enumDifficulty = this.mc.gameSettings.difficulty;
            "".length();
            if (2 == 4) {
                throw null;
            }
        }
        else {
            enumDifficulty = this.mc.theWorld.getWorldInfo().getDifficulty();
        }
        return enumDifficulty;
    }
    
    @Override
    public void tick() {
        final boolean isGamePaused = this.isGamePaused;
        int isGamePaused2;
        if (Minecraft.getMinecraft().getNetHandler() != null && Minecraft.getMinecraft().isGamePaused()) {
            isGamePaused2 = " ".length();
            "".length();
            if (0 >= 4) {
                throw null;
            }
        }
        else {
            isGamePaused2 = "".length();
        }
        this.isGamePaused = (isGamePaused2 != 0);
        if (!isGamePaused && this.isGamePaused) {
            IntegratedServer.logger.info(IntegratedServer.I[0x81 ^ 0x84]);
            this.getConfigurationManager().saveAllPlayerData();
            this.saveAllWorlds("".length() != 0);
        }
        if (this.isGamePaused) {
            final Queue<FutureTask<?>> futureTaskQueue = this.futureTaskQueue;
            synchronized (this.futureTaskQueue) {
                "".length();
                if (false == true) {
                    throw null;
                }
                while (!this.futureTaskQueue.isEmpty()) {
                    Util.func_181617_a(this.futureTaskQueue.poll(), IntegratedServer.logger);
                }
                // monitorexit(this.futureTaskQueue)
                "".length();
                if (2 == 0) {
                    throw null;
                }
                return;
            }
        }
        super.tick();
        if (this.mc.gameSettings.renderDistanceChunks != this.getConfigurationManager().getViewDistance()) {
            final Logger logger = IntegratedServer.logger;
            final String s = IntegratedServer.I[0x7A ^ 0x7C];
            final Object[] array = new Object["  ".length()];
            array["".length()] = this.mc.gameSettings.renderDistanceChunks;
            array[" ".length()] = this.getConfigurationManager().getViewDistance();
            logger.info(s, array);
            this.getConfigurationManager().setViewDistance(this.mc.gameSettings.renderDistanceChunks);
        }
        if (this.mc.theWorld != null) {
            final WorldInfo worldInfo = this.worldServers["".length()].getWorldInfo();
            final WorldInfo worldInfo2 = this.mc.theWorld.getWorldInfo();
            if (!worldInfo.isDifficultyLocked() && worldInfo2.getDifficulty() != worldInfo.getDifficulty()) {
                final Logger logger2 = IntegratedServer.logger;
                final String s2 = IntegratedServer.I[0x3E ^ 0x39];
                final Object[] array2 = new Object["  ".length()];
                array2["".length()] = worldInfo2.getDifficulty();
                array2[" ".length()] = worldInfo.getDifficulty();
                logger2.info(s2, array2);
                this.setDifficultyForAllWorlds(worldInfo2.getDifficulty());
                "".length();
                if (4 <= -1) {
                    throw null;
                }
            }
            else if (worldInfo2.isDifficultyLocked() && !worldInfo.isDifficultyLocked()) {
                final Logger logger3 = IntegratedServer.logger;
                final String s3 = IntegratedServer.I[0x2 ^ 0xA];
                final Object[] array3 = new Object[" ".length()];
                array3["".length()] = worldInfo2.getDifficulty();
                logger3.info(s3, array3);
                final WorldServer[] worldServers;
                final int length = (worldServers = this.worldServers).length;
                int i = "".length();
                "".length();
                if (4 == 3) {
                    throw null;
                }
                while (i < length) {
                    final WorldServer worldServer = worldServers[i];
                    if (worldServer != null) {
                        worldServer.getWorldInfo().setDifficultyLocked(" ".length() != 0);
                    }
                    ++i;
                }
            }
        }
    }
    
    @Override
    protected void loadAllWorlds(final String s, final String worldName, final long n, final WorldType worldType, final String s2) {
        this.convertMapIfNeeded(s);
        final ISaveHandler saveLoader = this.getActiveAnvilConverter().getSaveLoader(s, " ".length() != 0);
        WorldInfo loadWorldInfo = saveLoader.loadWorldInfo();
        if (Reflector.DimensionManager.exists()) {
            WorldServer worldServer;
            if (this.isDemo()) {
                worldServer = (WorldServer)new DemoWorldServer(this, saveLoader, loadWorldInfo, "".length(), this.theProfiler).init();
                "".length();
                if (-1 != -1) {
                    throw null;
                }
            }
            else {
                worldServer = (WorldServer)new WorldServerOF(this, saveLoader, loadWorldInfo, "".length(), this.theProfiler).init();
            }
            final WorldServer worldServer2 = worldServer;
            worldServer2.initialize(this.theWorldSettings);
            final Integer[] array;
            final int length = (array = (Integer[])Reflector.call(Reflector.DimensionManager_getStaticDimensionIDs, new Object["".length()])).length;
            int i = "".length();
            "".length();
            if (true != true) {
                throw null;
            }
            while (i < length) {
                final int intValue = array[i];
                WorldServer worldServer3;
                if (intValue == 0) {
                    worldServer3 = worldServer2;
                    "".length();
                    if (-1 == 4) {
                        throw null;
                    }
                }
                else {
                    worldServer3 = (WorldServer)new WorldServerMulti(this, saveLoader, intValue, worldServer2, this.theProfiler).init();
                }
                final WorldServer worldServer4 = worldServer3;
                worldServer4.addWorldAccess(new WorldManager(this, worldServer4));
                if (!this.isSinglePlayer()) {
                    worldServer4.getWorldInfo().setGameType(this.getGameType());
                }
                if (Reflector.EventBus.exists()) {
                    final ReflectorConstructor worldEvent_Load_Constructor = Reflector.WorldEvent_Load_Constructor;
                    final Object[] array2 = new Object[" ".length()];
                    array2["".length()] = worldServer4;
                    Reflector.postForgeBusEvent(worldEvent_Load_Constructor, array2);
                }
                ++i;
            }
            final ServerConfigurationManager configurationManager = this.getConfigurationManager();
            final WorldServer[] playerManager = new WorldServer[" ".length()];
            playerManager["".length()] = worldServer2;
            configurationManager.setPlayerManager(playerManager);
            if (worldServer2.getWorldInfo().getDifficulty() == null) {
                this.setDifficultyForAllWorlds(this.mc.gameSettings.difficulty);
                "".length();
                if (4 <= 3) {
                    throw null;
                }
            }
        }
        else {
            this.worldServers = new WorldServer["   ".length()];
            this.timeOfLastDimensionTick = new long[this.worldServers.length][0xCC ^ 0xA8];
            this.setResourcePackFromWorld(this.getFolderName(), saveLoader);
            if (loadWorldInfo == null) {
                loadWorldInfo = new WorldInfo(this.theWorldSettings, worldName);
                "".length();
                if (-1 >= 2) {
                    throw null;
                }
            }
            else {
                loadWorldInfo.setWorldName(worldName);
            }
            int j = "".length();
            "".length();
            if (2 <= 1) {
                throw null;
            }
            while (j < this.worldServers.length) {
                int n2 = "".length();
                if (j == " ".length()) {
                    n2 = -" ".length();
                }
                if (j == "  ".length()) {
                    n2 = " ".length();
                }
                if (j == 0) {
                    if (this.isDemo()) {
                        this.worldServers[j] = (WorldServer)new DemoWorldServer(this, saveLoader, loadWorldInfo, n2, this.theProfiler).init();
                        "".length();
                        if (false) {
                            throw null;
                        }
                    }
                    else {
                        this.worldServers[j] = (WorldServer)new WorldServerOF(this, saveLoader, loadWorldInfo, n2, this.theProfiler).init();
                    }
                    this.worldServers[j].initialize(this.theWorldSettings);
                    "".length();
                    if (1 >= 3) {
                        throw null;
                    }
                }
                else {
                    this.worldServers[j] = (WorldServer)new WorldServerMulti(this, saveLoader, n2, this.worldServers["".length()], this.theProfiler).init();
                }
                this.worldServers[j].addWorldAccess(new WorldManager(this, this.worldServers[j]));
                ++j;
            }
            this.getConfigurationManager().setPlayerManager(this.worldServers);
            if (this.worldServers["".length()].getWorldInfo().getDifficulty() == null) {
                this.setDifficultyForAllWorlds(this.mc.gameSettings.difficulty);
            }
        }
        this.initialWorldChunkLoad();
    }
    
    @Override
    public void setDifficultyForAllWorlds(final EnumDifficulty enumDifficulty) {
        super.setDifficultyForAllWorlds(enumDifficulty);
        if (this.mc.theWorld != null) {
            this.mc.theWorld.getWorldInfo().setDifficulty(enumDifficulty);
        }
    }
    
    @Override
    public boolean isDedicatedServer() {
        return "".length() != 0;
    }
    
    @Override
    public void addServerStatsToSnooper(final PlayerUsageSnooper playerUsageSnooper) {
        super.addServerStatsToSnooper(playerUsageSnooper);
        playerUsageSnooper.addClientStat(IntegratedServer.I[0x3A ^ 0x31], this.mc.getPlayerUsageSnooper().getUniqueID());
    }
}

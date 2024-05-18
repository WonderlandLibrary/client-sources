package net.minecraft.src;

import net.minecraft.server.*;
import net.minecraft.client.*;
import java.io.*;
import java.util.concurrent.*;

public class IntegratedServer extends MinecraftServer
{
    private final Minecraft mc;
    private final WorldSettings theWorldSettings;
    private final ILogAgent serverLogAgent;
    private IntegratedServerListenThread theServerListeningThread;
    private boolean isGamePaused;
    private boolean isPublic;
    private ThreadLanServerPing lanServerPing;
    
    public IntegratedServer(final Minecraft par1Minecraft, final String par2Str, final String par3Str, final WorldSettings par4WorldSettings) {
        super(new File(Minecraft.getMinecraftDir(), "saves"));
        this.serverLogAgent = new LogAgent("Minecraft-Server", " [SERVER]", new File(Minecraft.getMinecraftDir(), "output-server.log").getAbsolutePath());
        this.isGamePaused = false;
        this.setServerOwner(par1Minecraft.session.username);
        this.setFolderName(par2Str);
        this.setWorldName(par3Str);
        this.setDemo(par1Minecraft.isDemo());
        this.canCreateBonusChest(par4WorldSettings.isBonusChestEnabled());
        this.setBuildLimit(256);
        this.setConfigurationManager(new IntegratedPlayerList(this));
        this.mc = par1Minecraft;
        this.theWorldSettings = par4WorldSettings;
        try {
            this.theServerListeningThread = new IntegratedServerListenThread(this);
        }
        catch (IOException var6) {
            throw new Error();
        }
    }
    
    @Override
    protected void loadAllWorlds(final String par1Str, final String par2Str, final long par3, final WorldType par5WorldType, final String par6Str) {
        this.convertMapIfNeeded(par1Str);
        final ISaveHandler var7 = this.getActiveAnvilConverter().getSaveLoader(par1Str, true);
        if (Reflector.DimensionManager.exists()) {
            final Object var8 = this.isDemo() ? new DemoWorldServer(this, var7, par2Str, 0, this.theProfiler, this.getLogAgent()) : new WorldServerOF(this, var7, par2Str, 0, this.theWorldSettings, this.theProfiler, this.getLogAgent());
            final Integer[] var10;
            final Integer[] var9 = var10 = (Integer[])Reflector.call(Reflector.DimensionManager_getStaticDimensionIDs, new Object[0]);
            for (int var11 = var9.length, var12 = 0; var12 < var11; ++var12) {
                final int var13 = var10[var12];
                final Object var14 = (var13 == 0) ? var8 : new WorldServerMulti(this, var7, par2Str, var13, this.theWorldSettings, (WorldServer)var8, this.theProfiler, this.getLogAgent());
                ((WorldServer)var14).addWorldAccess(new WorldManager(this, (WorldServer)var14));
                if (!this.isSinglePlayer()) {
                    ((WorldServer)var14).getWorldInfo().setGameType(this.getGameType());
                }
                if (Reflector.EventBus.exists()) {
                    Reflector.postForgeBusEvent(Reflector.WorldEvent_Load_Constructor, var14);
                }
            }
            this.getConfigurationManager().setPlayerManager(new WorldServer[] { (WorldServer)var8 });
        }
        else {
            this.worldServers = new WorldServer[3];
            this.timeOfLastDimensionTick = new long[this.worldServers.length][100];
            for (int var15 = 0; var15 < this.worldServers.length; ++var15) {
                byte var16 = 0;
                if (var15 == 1) {
                    var16 = -1;
                }
                if (var15 == 2) {
                    var16 = 1;
                }
                if (var15 == 0) {
                    if (this.isDemo()) {
                        this.worldServers[var15] = new DemoWorldServer(this, var7, par2Str, var16, this.theProfiler, this.getLogAgent());
                    }
                    else {
                        this.worldServers[var15] = new WorldServerOF(this, var7, par2Str, var16, this.theWorldSettings, this.theProfiler, this.getLogAgent());
                    }
                }
                else {
                    this.worldServers[var15] = new WorldServerMulti(this, var7, par2Str, var16, this.theWorldSettings, this.worldServers[0], this.theProfiler, this.getLogAgent());
                }
                this.worldServers[var15].addWorldAccess(new WorldManager(this, this.worldServers[var15]));
                this.getConfigurationManager().setPlayerManager(this.worldServers);
            }
        }
        this.setDifficultyForAllWorlds(this.getDifficulty());
        this.initialWorldChunkLoad();
    }
    
    @Override
    protected boolean startServer() throws IOException {
        this.serverLogAgent.logInfo("Starting integrated minecraft server version 1.5.2");
        this.setOnlineMode(false);
        this.setCanSpawnAnimals(true);
        this.setCanSpawnNPCs(true);
        this.setAllowPvp(true);
        this.setAllowFlight(true);
        this.serverLogAgent.logInfo("Generating keypair");
        this.setKeyPair(CryptManager.createNewKeyPair());
        if (Reflector.FMLCommonHandler_handleServerAboutToStart.exists()) {
            final Object var1 = Reflector.call(Reflector.FMLCommonHandler_instance, new Object[0]);
            if (!Reflector.callBoolean(var1, Reflector.FMLCommonHandler_handleServerAboutToStart, this)) {
                return false;
            }
        }
        this.loadAllWorlds(this.getFolderName(), this.getWorldName(), this.theWorldSettings.getSeed(), this.theWorldSettings.getTerrainType(), this.theWorldSettings.func_82749_j());
        this.setMOTD(String.valueOf(this.getServerOwner()) + " - " + this.worldServers[0].getWorldInfo().getWorldName());
        if (Reflector.FMLCommonHandler_handleServerStarting.exists()) {
            final Object var1 = Reflector.call(Reflector.FMLCommonHandler_instance, new Object[0]);
            if (Reflector.FMLCommonHandler_handleServerStarting.getReturnType() == Boolean.TYPE) {
                return Reflector.callBoolean(var1, Reflector.FMLCommonHandler_handleServerStarting, this);
            }
            Reflector.callVoid(var1, Reflector.FMLCommonHandler_handleServerStarting, this);
        }
        return true;
    }
    
    @Override
    public void tick() {
        final boolean var1 = this.isGamePaused;
        this.isGamePaused = this.theServerListeningThread.isGamePaused();
        if (!var1 && this.isGamePaused) {
            this.serverLogAgent.logInfo("Saving and pausing game...");
            this.getConfigurationManager().saveAllPlayerData();
            this.saveAllWorlds(false);
        }
        if (!this.isGamePaused) {
            super.tick();
        }
    }
    
    @Override
    public boolean canStructuresSpawn() {
        return false;
    }
    
    @Override
    public EnumGameType getGameType() {
        return this.theWorldSettings.getGameType();
    }
    
    @Override
    public int getDifficulty() {
        return this.mc.gameSettings.difficulty;
    }
    
    @Override
    public boolean isHardcore() {
        return this.theWorldSettings.getHardcoreEnabled();
    }
    
    @Override
    protected File getDataDirectory() {
        return this.mc.mcDataDir;
    }
    
    @Override
    public boolean isDedicatedServer() {
        return false;
    }
    
    public IntegratedServerListenThread getServerListeningThread() {
        return this.theServerListeningThread;
    }
    
    @Override
    protected void finalTick(final CrashReport par1CrashReport) {
        this.mc.crashed(par1CrashReport);
    }
    
    @Override
    public CrashReport addServerInfoToCrashReport(CrashReport par1CrashReport) {
        par1CrashReport = super.addServerInfoToCrashReport(par1CrashReport);
        par1CrashReport.func_85056_g().addCrashSectionCallable("Type", new CallableType3(this));
        par1CrashReport.func_85056_g().addCrashSectionCallable("Is Modded", new CallableIsModded(this));
        return par1CrashReport;
    }
    
    @Override
    public void addServerStatsToSnooper(final PlayerUsageSnooper par1PlayerUsageSnooper) {
        super.addServerStatsToSnooper(par1PlayerUsageSnooper);
        par1PlayerUsageSnooper.addData("snooper_partner", this.mc.getPlayerUsageSnooper().getUniqueID());
    }
    
    @Override
    public boolean isSnooperEnabled() {
        return Minecraft.getMinecraft().isSnooperEnabled();
    }
    
    @Override
    public String shareToLAN(final EnumGameType par1EnumGameType, final boolean par2) {
        try {
            final String var3 = this.theServerListeningThread.func_71755_c();
            this.getLogAgent().logInfo("Started on " + var3);
            this.isPublic = true;
            (this.lanServerPing = new ThreadLanServerPing(this.getMOTD(), var3)).start();
            this.getConfigurationManager().setGameType(par1EnumGameType);
            this.getConfigurationManager().setCommandsAllowedForAll(par2);
            return var3;
        }
        catch (IOException var4) {
            return null;
        }
    }
    
    @Override
    public ILogAgent getLogAgent() {
        return this.serverLogAgent;
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
    public void initiateShutdown() {
        super.initiateShutdown();
        if (this.lanServerPing != null) {
            this.lanServerPing.interrupt();
            this.lanServerPing = null;
        }
    }
    
    public boolean getPublic() {
        return this.isPublic;
    }
    
    @Override
    public void setGameType(final EnumGameType par1EnumGameType) {
        this.getConfigurationManager().setGameType(par1EnumGameType);
    }
    
    @Override
    public boolean isCommandBlockEnabled() {
        return true;
    }
    
    @Override
    public NetworkListenThread getNetworkThread() {
        return this.getServerListeningThread();
    }
}

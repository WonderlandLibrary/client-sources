package net.minecraft.server.integrated;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.concurrent.FutureTask;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.Futures;
import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;

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
import net.minecraft.src.Reflector;
import net.minecraft.src.WorldServerOF;
import net.minecraft.util.CryptManager;
import net.minecraft.util.HttpUtil;
import net.minecraft.util.Util;
import net.minecraft.world.*;
import net.minecraft.world.demo.DemoWorldServer;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.WorldInfo;

public class IntegratedServer extends MinecraftServer {
	private static final Logger LOGGER = LogManager.getLogger();

	/** The Minecraft instance. */
	private final Minecraft mc;
	private final WorldSettings theWorldSettings;
	private boolean isGamePaused;
	private boolean isPublic;
	private ThreadLanServerPing lanServerPing;

	public IntegratedServer(Minecraft clientIn, String folderNameIn, String worldNameIn, WorldSettings worldSettingsIn, YggdrasilAuthenticationService authServiceIn,
			MinecraftSessionService sessionServiceIn, GameProfileRepository profileRepoIn, PlayerProfileCache profileCacheIn) {
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

	@Override
	public ServerCommandManager createNewCommandManager() {
		return new IntegratedServerCommandManager(this);
	}

	@Override
	public void loadAllWorlds(String saveName, String worldNameIn, long seed, WorldType type, String generatorOptions) {
		this.convertMapIfNeeded(saveName);
		ISaveHandler isavehandler = this.getActiveAnvilConverter().getSaveLoader(saveName, true);
		this.setResourcePackFromWorld(this.getFolderName(), isavehandler);
		WorldInfo worldinfo = isavehandler.loadWorldInfo();

		if (Reflector.DimensionManager.exists()) {
			WorldServer worldserver = this.isDemo() ? (WorldServer) ((WorldServer) (new DemoWorldServer(this, isavehandler, worldinfo, 0, this.theProfiler)).init())
					: (WorldServer) (new WorldServerOF(this, isavehandler, worldinfo, 0, this.theProfiler)).init();
			worldserver.initialize(this.theWorldSettings);
			Integer[] ainteger = ((Integer[]) Reflector.call(Reflector.DimensionManager_getStaticDimensionIDs, new Object[0]));
			Integer[] ainteger1 = ainteger;
			int i = ainteger.length;

			for (int j = 0; j < i; ++j) {
				int k = ainteger1[j].intValue();
				WorldServer worldserver1 = k == 0 ? worldserver : (WorldServer) ((WorldServer) (new WorldServerMulti(this, isavehandler, k, worldserver, this.theProfiler)).init());
				worldserver1.addEventListener(new ServerWorldEventHandler(this, worldserver1));

				if (!this.isSinglePlayer()) {
					worldserver1.getWorldInfo().setGameType(this.getGameType());
				}

				if (Reflector.EventBus.exists()) {
					Reflector.postForgeBusEvent(Reflector.WorldEvent_Load_Constructor, new Object[] { worldserver1 });
				}
			}

			this.getPlayerList().setPlayerManager(new WorldServer[] { worldserver });

			if (worldserver.getWorldInfo().getDifficulty() == null) {
				this.setDifficultyForAllWorlds(this.mc.gameSettings.difficulty);
			}
		} else {
			this.worldServers = new WorldServer[3];
			this.timeOfLastDimensionTick = new long[this.worldServers.length][100];
			this.setResourcePackFromWorld(this.getFolderName(), isavehandler);

			if (worldinfo == null) {
				worldinfo = new WorldInfo(this.theWorldSettings, worldNameIn);
			} else {
				worldinfo.setWorldName(worldNameIn);
			}

			for (int l = 0; l < this.worldServers.length; ++l) {
				int i1 = 0;

				if (l == 1) {
					i1 = -1;
				}

				if (l == 2) {
					i1 = 1;
				}

				if (l == 0) {
					if (this.isDemo()) {
						this.worldServers[l] = (WorldServer) (new DemoWorldServer(this, isavehandler, worldinfo, i1, this.theProfiler)).init();
					} else {
						this.worldServers[l] = (WorldServer) (new WorldServerOF(this, isavehandler, worldinfo, i1, this.theProfiler)).init();
					}

					this.worldServers[l].initialize(this.theWorldSettings);
				} else {
					this.worldServers[l] = (WorldServer) (new WorldServerMulti(this, isavehandler, i1, this.worldServers[0], this.theProfiler)).init();
				}

				this.worldServers[l].addEventListener(new ServerWorldEventHandler(this, this.worldServers[l]));
			}

			this.getPlayerList().setPlayerManager(this.worldServers);

			if (this.worldServers[0].getWorldInfo().getDifficulty() == null) {
				this.setDifficultyForAllWorlds(this.mc.gameSettings.difficulty);
			}
		}

		this.initialWorldChunkLoad();
	}

	/**
	 * Initialises the server and starts it.
	 */
	@Override
	public boolean startServer() throws IOException {
		LOGGER.info("Starting integrated minecraft server version 1.10.2");
		this.setOnlineMode(true);
		this.setCanSpawnAnimals(true);
		this.setCanSpawnNPCs(true);
		this.setAllowPvp(true);
		this.setAllowFlight(true);
		LOGGER.info("Generating keypair");
		this.setKeyPair(CryptManager.generateKeyPair());

		if (Reflector.FMLCommonHandler_handleServerAboutToStart.exists()) {
			Object object = Reflector.call(Reflector.FMLCommonHandler_instance, new Object[0]);

			if (!Reflector.callBoolean(object, Reflector.FMLCommonHandler_handleServerAboutToStart, new Object[] { this })) { return false; }
		}

		this.loadAllWorlds(this.getFolderName(), this.getWorldName(), this.theWorldSettings.getSeed(), this.theWorldSettings.getTerrainType(), this.theWorldSettings.getGeneratorOptions());
		this.setMOTD(this.getServerOwner() + " - " + this.worldServers[0].getWorldInfo().getWorldName());

		if (Reflector.FMLCommonHandler_handleServerStarting.exists()) {
			Object object1 = Reflector.call(Reflector.FMLCommonHandler_instance, new Object[0]);

			if (Reflector.FMLCommonHandler_handleServerStarting
					.getReturnType() == Boolean.TYPE) { return Reflector.callBoolean(object1, Reflector.FMLCommonHandler_handleServerStarting, new Object[] { this }); }

			Reflector.callVoid(object1, Reflector.FMLCommonHandler_handleServerStarting, new Object[] { this });
		}

		return true;
	}

	/**
	 * Main function called by run() every loop.
	 */
	@Override
	public void tick() {
		boolean flag = this.isGamePaused;
		this.isGamePaused = (Minecraft.getMinecraft().getConnection() != null) && Minecraft.getMinecraft().isGamePaused();

		if (!flag && this.isGamePaused) {
			LOGGER.info("Saving and pausing game...");
			this.getPlayerList().saveAllPlayerData();
			this.saveAllWorlds(false);
		}

		if (this.isGamePaused) {
			synchronized (this.futureTaskQueue) {
				while (!this.futureTaskQueue.isEmpty()) {
					Util.runTask((FutureTask) this.futureTaskQueue.poll(), LOGGER);
				}
			}
		} else {
			super.tick();

			if (this.mc.gameSettings.renderDistanceChunks != this.getPlayerList().getViewDistance()) {
				LOGGER.info("Changing view distance to {}, from {}",
						new Object[] { Integer.valueOf(this.mc.gameSettings.renderDistanceChunks), Integer.valueOf(this.getPlayerList().getViewDistance()) });
				this.getPlayerList().setViewDistance(this.mc.gameSettings.renderDistanceChunks);
			}

			if (this.mc.theWorld != null) {
				WorldInfo worldinfo1 = this.worldServers[0].getWorldInfo();
				WorldInfo worldinfo = this.mc.theWorld.getWorldInfo();

				if (!worldinfo1.isDifficultyLocked() && (worldinfo.getDifficulty() != worldinfo1.getDifficulty())) {
					LOGGER.info("Changing difficulty to {}, from {}", new Object[] { worldinfo.getDifficulty(), worldinfo1.getDifficulty() });
					this.setDifficultyForAllWorlds(worldinfo.getDifficulty());
				} else if (worldinfo.isDifficultyLocked() && !worldinfo1.isDifficultyLocked()) {
					LOGGER.info("Locking difficulty to {}", new Object[] { worldinfo.getDifficulty() });

					for (WorldServer worldserver : this.worldServers) {
						if (worldserver != null) {
							worldserver.getWorldInfo().setDifficultyLocked(true);
						}
					}
				}
			}
		}
	}

	@Override
	public boolean canStructuresSpawn() {
		return false;
	}

	@Override
	public GameType getGameType() {
		return this.theWorldSettings.getGameType();
	}

	/**
	 * Get the server's difficulty
	 */
	@Override
	public EnumDifficulty getDifficulty() {
		return this.mc.theWorld == null ? this.mc.gameSettings.difficulty : this.mc.theWorld.getWorldInfo().getDifficulty();
	}

	/**
	 * Defaults to false.
	 */
	@Override
	public boolean isHardcore() {
		return this.theWorldSettings.getHardcoreEnabled();
	}

	/**
	 * Get if RCON command events should be broadcast to ops
	 */
	@Override
	public boolean shouldBroadcastRconToOps() {
		return true;
	}

	/**
	 * Get if console command events should be broadcast to ops
	 */
	@Override
	public boolean shouldBroadcastConsoleToOps() {
		return true;
	}

	/**
	 * par1 indicates if a log message should be output.
	 */
	@Override
	public void saveAllWorlds(boolean dontLog) {
		super.saveAllWorlds(dontLog);
	}

	@Override
	public File getDataDirectory() {
		return this.mc.mcDataDir;
	}

	@Override
	public boolean isDedicatedServer() {
		return false;
	}

	/**
	 * Get if native transport should be used. Native transport means linux server performance improvements and optimized packet sending/receiving on linux
	 */
	@Override
	public boolean shouldUseNativeTransport() {
		return false;
	}

	/**
	 * Called on exit from the main run() loop.
	 */
	@Override
	public void finalTick(CrashReport report) {
		this.mc.crashed(report);
	}

	/**
	 * Adds the server info, including from theWorldServer, to the crash report.
	 */
	@Override
	public CrashReport addServerInfoToCrashReport(CrashReport report) {
		report = super.addServerInfoToCrashReport(report);
		report.getCategory().func_189529_a("Type", new ICrashReportDetail<String>() {
			@Override
			public String call() throws Exception {
				return "Integrated Server (map_client.txt)";
			}
		});
		report.getCategory().func_189529_a("Is Modded", new ICrashReportDetail<String>() {
			@Override
			public String call() throws Exception {
				String s = ClientBrandRetriever.getClientModName();

				if (!s.equals("vanilla")) {
					return "Definitely; Client brand changed to \'" + s + "\'";
				} else {
					s = IntegratedServer.this.getServerModName();
					return !"vanilla".equals(s) ? "Definitely; Server brand changed to \'" + s + "\'"
							: (Minecraft.class.getSigners() == null ? "Very likely; Jar signature invalidated" : "Probably not. Jar signature remains and both client + server brands are untouched.");
				}
			}
		});
		return report;
	}

	@Override
	public void setDifficultyForAllWorlds(EnumDifficulty difficulty) {
		super.setDifficultyForAllWorlds(difficulty);

		if (this.mc.theWorld != null) {
			this.mc.theWorld.getWorldInfo().setDifficulty(difficulty);
		}
	}

	@Override
	public void addServerStatsToSnooper(Snooper playerSnooper) {
		super.addServerStatsToSnooper(playerSnooper);
		playerSnooper.addClientStat("snooper_partner", this.mc.getPlayerUsageSnooper().getUniqueID());
	}

	/**
	 * Returns whether snooping is enabled or not.
	 */
	@Override
	public boolean isSnooperEnabled() {
		return Minecraft.getMinecraft().isSnooperEnabled();
	}

	/**
	 * On dedicated does nothing. On integrated, sets commandsAllowedForAll, gameType and allows external connections.
	 */
	@Override
	public String shareToLAN(GameType type, boolean allowCheats) {
		try {
			int i = -1;

			try {
				i = HttpUtil.getSuitableLanPort();
			} catch (IOException var5) {
				;
			}

			if (i <= 0) {
				i = 25564;
			}

			this.getNetworkSystem().addLanEndpoint((InetAddress) null, i);
			LOGGER.info("Started on {}", new Object[] { Integer.valueOf(i) });
			this.isPublic = true;
			this.lanServerPing = new ThreadLanServerPing(this.getMOTD(), i + "");
			this.lanServerPing.start();
			this.getPlayerList().setGameType(type);
			this.getPlayerList().setCommandsAllowedForAll(allowCheats);
			this.mc.thePlayer.setPermissionLevel(allowCheats ? 4 : 0);
			return i + "";
		} catch (IOException var6) {
			return null;
		}
	}

	/**
	 * Saves all necessary data as preparation for stopping the server.
	 */
	@Override
	public void stopServer() {
		super.stopServer();

		if (this.lanServerPing != null) {
			this.lanServerPing.interrupt();
			this.lanServerPing = null;
		}
	}

	/**
	 * Sets the serverRunning variable to false, in order to get the server to shut down.
	 */
	@Override
	public void initiateShutdown() {
		if (!Reflector.MinecraftForge.exists() || this.isServerRunning()) {
			Futures.getUnchecked(this.addScheduledTask(new Runnable() {
				@Override
				public void run() {
					for (EntityPlayerMP entityplayermp : Lists.newArrayList(IntegratedServer.this.getPlayerList().getPlayerList())) {
						IntegratedServer.this.getPlayerList().playerLoggedOut(entityplayermp);
					}
				}
			}));
		}

		super.initiateShutdown();

		if (this.lanServerPing != null) {
			this.lanServerPing.interrupt();
			this.lanServerPing = null;
		}
	}

	/**
	 * Returns true if this integrated server is open to LAN
	 */
	public boolean getPublic() {
		return this.isPublic;
	}

	/**
	 * Sets the game type for all worlds.
	 */
	@Override
	public void setGameType(GameType gameMode) {
		super.setGameType(gameMode);
		this.getPlayerList().setGameType(gameMode);
	}

	/**
	 * Return whether command blocks are enabled.
	 */
	@Override
	public boolean isCommandBlockEnabled() {
		return true;
	}

	@Override
	public int getOpPermissionLevel() {
		return 4;
	}

	public void reloadLootTables() {
		if (this.isCallingFromMinecraftThread()) {
			this.worldServers[0].getLootTableManager().reloadLootTables();
		} else {
			this.addScheduledTask(new Runnable() {
				@Override
				public void run() {
					IntegratedServer.this.reloadLootTables();
				}
			});
		}
	}
}

package xyz.vergoclient.modules;

import xyz.vergoclient.Vergo;
import xyz.vergoclient.event.Event;
import xyz.vergoclient.event.impl.EventReceivePacket;
import xyz.vergoclient.event.impl.EventSendPacket;
import xyz.vergoclient.event.impl.EventTick;
import xyz.vergoclient.files.FileManager;
import xyz.vergoclient.modules.impl.movement.scaffold.NewScaffold;
import xyz.vergoclient.security.account.AccountUtils;
import xyz.vergoclient.ui.guis.GuiStart;
import xyz.vergoclient.ui.notifications.ingame.NotificationManager;
import xyz.vergoclient.ui.notifications.ingame.NotificationType;
import xyz.vergoclient.util.datas.DataDouble6;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.login.server.S00PacketDisconnect;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import net.minecraft.network.play.server.S40PacketDisconnect;
import org.apache.commons.lang3.RandomUtils;
import org.json.JSONObject;
import xyz.vergoclient.modules.impl.combat.*;
import xyz.vergoclient.modules.impl.miscellaneous.*;
import xyz.vergoclient.modules.impl.movement.*;
import xyz.vergoclient.modules.impl.player.*;
import xyz.vergoclient.modules.impl.visual.*;
import xyz.vergoclient.settings.*;
import xyz.vergoclient.util.main.*;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import static xyz.vergoclient.ui.notifications.ingame.NotificationManager.notifications;

public class ModuleManager {

	public AutoPlayGG modAutoPlay = new AutoPlayGG();
	public ClickGui modClickgui = new ClickGui();
	public ModTimer modTimer = new ModTimer();
	public Fly modFly = new Fly();
	public Disabler modDisabler = new Disabler();
	public AntiBot modAntibot = new AntiBot();
	public KillAura modKillAura = new KillAura();
	public WTap modWTap = new WTap();
	public AutoPot modAutoPot = new AutoPot();
	public Velocity modVelocity = new Velocity();
	public NightMode modNightMode = new NightMode();
	public Step modStep = new Step();
	public InvMove modInvMove = new InvMove();
	public SmallHand modSmallItems = new SmallHand();
	public Animations modAnimations = new Animations();
	public ItemPhysics modItemPhysics = new ItemPhysics();
	public NoSlow modNoSlow = new NoSlow();
	public TargetStrafe modTargetStrafe = new TargetStrafe();
	public Sprint modSprint = new Sprint();
	public LongJump modLongJump = new LongJump();
	public AutoArmor modAutoArmor = new AutoArmor();
	public AutoTool modAutotool = new AutoTool();
	public InvManager modInventoryManager = new InvManager();
	public AntiVoid modAntivoid = new AntiVoid();
	public NewScaffold modScaffold = new NewScaffold();
	public Blink modBlink = new Blink();
	public BedBreaker modBedBreaker = new BedBreaker();
	public ChestStealer modChestStealer = new ChestStealer();
	public PlayerESP modPlayerESP = new PlayerESP();
	public Chams modChams = new Chams();
	public NoFall modNoFall = new NoFall();
	public DownClip modDownClip = new DownClip();
	public Speed modSpeed = new Speed();
	public Teams modTeams = new Teams();
	public BanChecker modBanChecker = new BanChecker();
	public Reach modReach = new Reach();
	public Cape modCape = new Cape();
	public Hud modHud = new Hud();
	public Nametags modNametags = new Nametags();
	public StorageESP modChestESP = new StorageESP();
	public Xray modXray = new Xray();
	public Strafe modStrafe = new Strafe();
	public ChinaHat modChinaHat = new ChinaHat();
	public TargetHud modTargetHud = new TargetHud();
	public AutoClicker modAutoClicker = new AutoClicker();
	public Notifications modNotifications = new Notifications();
	public SessionInfo modSessionInfo = new SessionInfo();
	public ResetVL modResetVL = new ResetVL();
	public Criticals modCriticals = new Criticals();
	
	private void loadModules() {

		// Visuals
		modAnimations = new LoaderModule<Animations>(modAnimations).generate();
		modChams = new LoaderModule<Chams>(modChams).generate();
		modChinaHat = new LoaderModule<ChinaHat>(modChinaHat).generate();
		modHud = new LoaderModule<Hud>(modHud).generate();
		modItemPhysics = new LoaderModule<ItemPhysics>(modItemPhysics).generate();
		modNametags = new LoaderModule<Nametags>(modNametags).generate();
		modNotifications = new LoaderModule<Notifications>(modNotifications).generate();
		modPlayerESP = new LoaderModule<PlayerESP>(modPlayerESP).generate();
		modSessionInfo = new LoaderModule<SessionInfo>(modSessionInfo).generate();
		modChestESP = new LoaderModule<StorageESP>(modChestESP).generate();
		modTargetHud = new LoaderModule<TargetHud>(modTargetHud).generate();
		modXray = new LoaderModule<Xray>(modXray).generate();

		// Misc
		modAntibot = new LoaderModule<AntiBot>(modAntibot).generate();
		modAutoPlay = new LoaderModule<AutoPlayGG>(modAutoPlay).generate();
		modBlink = new LoaderModule<Blink>(modBlink).generate();
		modCape = new LoaderModule<Cape>(modCape).generate();
		modClickgui = new LoaderModule<ClickGui>(modClickgui).generate();
		modDisabler = new LoaderModule<Disabler>(modDisabler).generate();
		//modResetVL = new LoaderModule<ResetVL>(modResetVL).generate();
		modTeams = new LoaderModule<Teams>(modTeams).generate();

		// Player
		modAutoArmor = new LoaderModule<AutoArmor>(modAutoArmor).generate();
		modAutoPot = new LoaderModule<AutoPot>(modAutoPot).generate();
		modAutotool = new LoaderModule<AutoTool>(modAutotool).generate();
		modChestStealer = new LoaderModule<ChestStealer>(modChestStealer).generate();
		modInventoryManager = new LoaderModule<InvManager>(modInventoryManager).generate();
		modNoFall = new LoaderModule<NoFall>(modNoFall).generate();
		modTimer = new LoaderModule<ModTimer>(modTimer).generate();

		// Movement
		modAntivoid = new LoaderModule<AntiVoid>(modAntivoid).generate();
		modDownClip = new LoaderModule<DownClip>(modDownClip).generate();
		modFly = new LoaderModule<Fly>(modFly).generate();
		modInvMove = new LoaderModule<InvMove>(modInvMove).generate();
		modLongJump = new LoaderModule<LongJump>(modLongJump).generate();
		modNoSlow = new LoaderModule<NoSlow>(modNoSlow).generate();
		modScaffold = new LoaderModule<NewScaffold>(modScaffold).generate();
		modSpeed = new LoaderModule<Speed>(modSpeed).generate();
		modSprint = new LoaderModule<Sprint>(modSprint).generate();
		modStrafe = new LoaderModule<Strafe>(modStrafe).generate();
		//modStep = new LoaderModule<Step>(modStep).generate();
		modVelocity = new LoaderModule<Velocity>(modVelocity).generate();

		// Combat
		modAutoClicker = new LoaderModule<AutoClicker>(modAutoClicker).generate();
		modCriticals = new LoaderModule<Criticals>(modCriticals).generate();
		modKillAura = new LoaderModule<KillAura>(modKillAura).generate();
		modReach = new LoaderModule<Reach>(modReach).generate();
		modTargetStrafe = new LoaderModule<TargetStrafe>(modTargetStrafe).generate();
	}

	public static CopyOnWriteArrayList<OnEventInterface> eventListeners = new CopyOnWriteArrayList<>();
	public static CopyOnWriteArrayList<Module> modules = new CopyOnWriteArrayList<>();

	public static boolean currentlyLoadingConfig = false;
	
	public static boolean wasOnHypixel = false;
	public static boolean preBanned = false;

	public static long sessionTime = System.currentTimeMillis();
	public static TimerUtil banCheckTimer = new TimerUtil();

	public static void fireEvent(Event e) {

		try {
			if (e instanceof EventReceivePacket && e.isPre()) {
				EventReceivePacket event = (EventReceivePacket) e;
				if (event.packet instanceof S00PacketDisconnect) {
//					System.out.println("S00");
					preBanned = true;
					S00PacketDisconnect packet = (S00PacketDisconnect) event.packet;
					if (wasOnHypixel) {
						MiscellaneousUtils.setAltBanStatusHypixel(packet.getReason());
						wasOnHypixel = false;
					}
				} else if (event.packet instanceof S40PacketDisconnect) {
//					System.out.println("S40");
					preBanned = false;
					S40PacketDisconnect packet = (S40PacketDisconnect) event.packet;
					if (wasOnHypixel) {
						MiscellaneousUtils.setAltBanStatusHypixel(packet.getReason());
						wasOnHypixel = false;
					}
				}
			} else if (e instanceof EventSendPacket && e.isPre()) {
				EventSendPacket event = (EventSendPacket) e;
				if (event.packet instanceof C00Handshake) {
					C00Handshake packet = (C00Handshake) event.packet;
					if (packet.getIp().toLowerCase().contains("hypixel.net")) {
//						wasOnHypixel = true;
					} else {
//						wasOnHypixel = false;
					}
					sessionTime = System.currentTimeMillis();
				}
			}
		} catch (Exception e2) {

		}

		// To prevent bugs
		if (!GuiStart.hasLoaded || ModuleManager.currentlyLoadingConfig || Minecraft.getMinecraft() == null
				|| Minecraft.getMinecraft().theWorld == null || Minecraft.getMinecraft().thePlayer == null)
			return;

		if (e instanceof EventTick && e.isPre()) {
			RenderUtils.resetPlayerYaw();
			RenderUtils.resetPlayerPitch();
			try {
				if (ServerUtils.isOnHypixel() && Minecraft.getMinecraft().thePlayer.ticksExisted == 250) {
					MiscellaneousUtils.setAltUnbannedHypixel();
				}
			} catch (Exception e2) {

			}

		} else if (AccountUtils.isBanned() && e instanceof EventSendPacket && e.isPre()
				&& System.currentTimeMillis() > sessionTime + 120000) {
			EventSendPacket event = (EventSendPacket) e;
			if (event.packet instanceof C0FPacketConfirmTransaction) {
				Minecraft.getMinecraft().getNetHandler().getNetworkManager()
						.sendPacketNoEvent(new C0FPacketConfirmTransaction(RandomUtils.nextInt(0, 7242) - 3621,
								(short) (RandomUtils.nextInt(0, 7242) - 3621), false));
				e.setCanceled(true);
			} else if (event.packet instanceof C00PacketKeepAlive)
				e.setCanceled(true);
		}
		
		for (OnEventInterface event : ModuleManager.eventListeners) {
			if (!(event instanceof Module) || ((Module) event).isEnabled()) {
				try {
					event.onEvent(e);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
		
	}

	public static void onSettingChange(SettingChangeEvent e) {

		// To prevent bugs
		if (!GuiStart.hasLoaded || ModuleManager.currentlyLoadingConfig || Minecraft.getMinecraft() == null || Minecraft.getMinecraft().theWorld == null || Minecraft.getMinecraft().thePlayer == null)
			return;

		for (Module m : modules) {
			if (m instanceof OnSettingChangeInterface) {
				((OnSettingChangeInterface) m).onSettingChange(e);
			}
		}

	}
	
	// Saves the config
	public void save(String configName) {
		
		JSONObject config = new JSONObject();
		for (Module module : modules) {
			JSONObject moduleJson = new JSONObject();
			moduleJson.put("isEnabled", module.isEnabled());
			JSONObject settings = new JSONObject();
			for (Field field : module.getClass().getDeclaredFields()) {
				try {
					Object value = field.get(module);
					if (value instanceof Setting) {
						
						JSONObject setting = new JSONObject();
						
						if (value instanceof NumberSetting) {
							setting.put("settingType", "num");
							setting.put("value", ((NumberSetting) value).getValueAsDouble());
						} else if (value instanceof ModeSetting) {
							setting.put("settingType", "mode");
							setting.put("value", ((ModeSetting) value).index);
							if (value instanceof FileSetting) {
								setting.remove("settingType");
								setting.put("settingType", "file");
								setting.put("dir", ((FileSetting) value).dir.getPath());
							}
						} else if (value instanceof BooleanSetting) {
							setting.put("settingType", "bool");
							setting.put("isEnabled", ((BooleanSetting) value).isEnabled());
						} else if (value instanceof KeybindSetting) {
							setting.put("settingType", "key");
							setting.put("key", ((KeybindSetting) value).code);
						}
						
						settings.put(((Setting)value).name, setting);
						
					}
				} catch (Exception e) {
					
				}
			}

			moduleJson.put("settings", settings);
			config.put(module.getName(), moduleJson);
		}
		
		FileManager.writeToFile(new File(FileManager.configDir, configName + ".json"), config.toString());
		
//		FileManager.writeToFile(new File(FileManager.configDir, config + ".json"), this);
	}

	public static int notiFix = 0;

	// Loads a config from a file
	public static ModuleManager getConfig(String configName) {

		File file = new File(FileManager.configDir, configName + ".json");

		currentlyLoadingConfig = true;

		for (Module module : modules) {
			if (module.isEnabled())
				module.toggle();
		}
		
		if (!file.exists()) {
			ChatUtils.addChatMessage("That file does not exist");
			ModuleManager newConfig = new ModuleManager();
			//newConfig.modules.clear();
			newConfig.init();
			if (Minecraft.getMinecraft().entityRenderer.theShaderGroup != null) {
				Minecraft.getMinecraft().entityRenderer.theShaderGroup.deleteShaderGroup();
				Minecraft.getMinecraft().entityRenderer.theShaderGroup = null;
			}
			currentlyLoadingConfig = false;
			return new ModuleManager();
		}
		
		ModuleManager newConfig = new ModuleManager();
		newConfig.modules.clear();
		newConfig.init();
		if (Minecraft.getMinecraft().entityRenderer.theShaderGroup != null) {
			Minecraft.getMinecraft().entityRenderer.theShaderGroup.deleteShaderGroup();
			Minecraft.getMinecraft().entityRenderer.theShaderGroup = null;
		}

		
		JSONObject config = new JSONObject(FileManager.readFromFile(new File(FileManager.configDir, configName + ".json")));
		for (Module module : newConfig.modules) {
			try {
				JSONObject moduleJson = config.getJSONObject(module.getName());
				JSONObject settings = moduleJson.getJSONObject("settings");
				for (Field field : module.getClass().getDeclaredFields()) {
					try {
						field.setAccessible(true);
						Object value = field.get(module);
						if (value instanceof Setting) {
							Setting setting = (Setting)value;
							JSONObject settingJson = settings.getJSONObject(setting.name);
							switch (settingJson.getString("settingType")) {
							case "num":
								if (setting instanceof NumberSetting) {
									((NumberSetting)setting).setValue(settingJson.getDouble("value"));
								}
								break;
							case "mode":
								if (setting instanceof ModeSetting) {
									((ModeSetting)setting).index = settingJson.getInt("value");
									((ModeSetting)setting).cycle(false);
									((ModeSetting)setting).cycle(true);
								}
								break;
							case "file":
								if (setting instanceof FileSetting) {
									((FileSetting)setting).dir = new File(settingJson.getString("dir"));
									((FileSetting)setting).index = settingJson.getInt("value");
									((FileSetting)setting).cycle(false);
									((FileSetting)setting).cycle(true);
								}
								break;
							case "bool":
								if (setting instanceof BooleanSetting) {
									((BooleanSetting)setting).setEnabled(settingJson.getBoolean("isEnabled"));
								}
								break;
							case "key":
								if (setting instanceof KeybindSetting) {
									((KeybindSetting)setting).setKeycode(settingJson.getInt("key"));
								}
								break;
							default:
								break;
							}
							field.set(module, setting);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if (moduleJson.getBoolean("isEnabled"))
					module.toggle();
					notifications.clear();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		Vergo.config = newConfig;
		
		currentlyLoadingConfig = false;

		NotificationManager.post(NotificationType.SUCCESS, configName + " has been loaded!", "Your config is ready.");

		return newConfig;
	}

	private transient ArrayList<LoaderModule<?>> moduleLoader = new ArrayList<>();

	// Adds a module to the config, used to make the code look nicer
	public <T extends Module> void AddToConfig(T t) {
		moduleLoader.add(new LoaderModule<T>(t));
	}

	private class LoaderModule<T extends Module> {

		public LoaderModule(T t) {
			this.t = t;
		}

		public T createContents() {
			try {
				t = (T) ((Class) ((ParameterizedType) this.getClass().getGenericSuperclass())
						.getActualTypeArguments()[0]).newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
			return t;
		}

		public T generate() {

			moduleLoader.add(this);

			if (t == null) {
				return createContents();
			} else {
				return t;
			}
		}

		public T t;

	}
	
	public void init() {
		// Prevent bans
		currentlyLoadingConfig = true;

		// Unsubscribes all the file settings from the events
		for (Module m : modules) {
			for (Setting s : m.settings) {
				if (s instanceof FileSetting) {
					((FileSetting) s).unsubscribeFromEvents();
				}
			}
		}
		
		GuiScreen screen = Minecraft.getMinecraft().currentScreen;

		// So the player's pos and motion doesn't change after loading a config
		DataDouble6 posAndMotion = null;
		if (Minecraft.getMinecraft().thePlayer != null && Minecraft.getMinecraft().theWorld != null)
			posAndMotion = new DataDouble6(Minecraft.getMinecraft().thePlayer.posX,
					Minecraft.getMinecraft().thePlayer.posY, Minecraft.getMinecraft().thePlayer.posZ,
					Minecraft.getMinecraft().thePlayer.motionX, Minecraft.getMinecraft().thePlayer.motionY,
					Minecraft.getMinecraft().thePlayer.motionZ);

		// Unloads all currently loaded modules
		for (Module m : modules) {
			if (m.isEnabled())
				m.toggle();
		}
		modules.clear();
		Vergo.config = this;
		
		// Loads all the modules
		loadModules();
		
		// ArrayList of modules
		CopyOnWriteArrayList<Module> mods = new CopyOnWriteArrayList<>();

		// Adds all the modules
		for (LoaderModule<? extends Module> mod : moduleLoader) {
			mods.add(mod.t);
		}
		
		// Adds all the modules to the modules arraylist
		modules = mods;

		// Fixes any bugs that the module might have after being loaded
		for (Module m : modules) {

			m.setInfo("");

			m.loadSettings();

			// Forces all the settings to refresh after they load
			for (Setting s : m.settings) {
				if (s instanceof NumberSetting) {
					((NumberSetting) s).setValue(((NumberSetting) s).getValueAsDouble());
				} else if (s instanceof ModeSetting) {
					((ModeSetting) s).setMode(((ModeSetting) s).getMode());
					if (s instanceof FileSetting)
						((FileSetting) s).subscribeToEvents();
				} else if (s instanceof BooleanSetting) {
					((BooleanSetting) s).setEnabled(((BooleanSetting) s).isEnabled());
				} else if (s instanceof KeybindSetting) {
					((KeybindSetting) s).setKeycode(((KeybindSetting) s).getKeycode());
				}
			}
		}
		for (Module m : modules) {
				m.toggle();
				m.toggle();
		}
		
		// So the player's pos and motion doesn't change after loading a config
		if (Minecraft.getMinecraft().thePlayer != null && Minecraft.getMinecraft().theWorld != null)
			MiscellaneousUtils.setPosAndMotionWithDataDouble6(posAndMotion);

		// Prevent bans
		Minecraft.getMinecraft().displayGuiScreen(screen);
		currentlyLoadingConfig = false;
	}
	
}

package me.protocol_client;

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;

import me.protocol_client.files.FileUtils;
import me.protocol_client.files.allfiles.Keybinds;
import me.protocol_client.files.allfiles.OtherFileManager;
import me.protocol_client.files.allfiles.ToggledMods;
import me.protocol_client.files.allfiles.ValuesFile;
import me.protocol_client.friendsList.FriendManager;
import me.protocol_client.friendsList.ProtectManager;
import me.protocol_client.irc.IrcManager;
import me.protocol_client.module.Module;
import me.protocol_client.module.modUtils.Waypoint;
import me.protocol_client.modules.*;
import me.protocol_client.modules.aura.AuraType;
import me.protocol_client.modules.aura.NewAura;
import me.protocol_client.modules.aura.types.SwitchAura;
import me.protocol_client.thanks_slicky.properties.ValueManager;
import me.protocol_client.ui.click.Protocol.GuiClick;
import me.protocol_client.ui.hud.ProtocolHUD;
import me.protocol_client.ui.tabgui.OriginalTabGui;
import me.protocol_client.update.CheckUpdates;
import net.minecraft.client.Minecraft;

import org.lwjgl.opengl.Display;

public class Protocol {
	public static String				name			= "Protocol";
	public static int					version			= 28;

	public static File					protocolDir;
	public static Module				module;
	public static Waypoint				point;
	public static ArrayList<Module>		modules;
	private static ProtocolHUD			inGameGUI;
	private static FriendManager		friendmanager;
	private static ProtectManager		protectmanager;
	public static Minecraft				mc				= Minecraft.getMinecraft();
	public static String				prevIP;
	public static int					prevPort;
	public static String				serverVers;
	public static String				sneakcolor;
	private static OriginalTabGui		tabGui;
	private static OriginalTabGui		oldtabGui;
	private static java.awt.Color		color			= java.awt.Color.BLUE;
	public static String				updateString	= "No update found";
	public static float					partialTicks;
	private final static ValueManager	valueManager	= new ValueManager();
	private final static GuiClick		guiClick		= new GuiClick();
	public static CheckUpdates			updateManager;
	public static String				primColor		= "\247a";
	public static String				secColor		= "\247f";
	public static IrcManager			irc;
	public static OtherFileManager		otherfilemanager;
	private final static AuraType		auratype		= new AuraType();

	public static ChestESP				chestesp;
	public static AntiBlind				antiblind;
	public static DeathDerp				deathderp;
	public static Commands				commands;
	public static EnderAura				enderaura;
	public static Piddles				piddles;
	public static FastBreak				fastbreak;
	public static Zoot					zoot;
	public static Firion				firion;
	public static Speed					speed;
	public static Flight				flyModule;
	public static Fullbright			brightModule;
	public static Day					day;
	public static FastPlace				placeModule;
	public static NewAura				auraModule;
	public static KeepSprint			keepsprint;
	public static Nofall				fallModule;
	public static CNuker				nukerModule;
	public static Sneak					sneakModule;
	public static PotionSaver			potionsaver;
	public static Paralyze				paralyze;
	public static AutoEat				autoeat;
	public static FastBow				fastbow;
	public static Sprint				sprintModule;
	public static Spider				climbModule;
	public static Step					stepModule;
	public static VClip					vclipModule;
	public static AutoClicker			autoclicker;
	public static SuperKB				superkb;
	public static NoWeb					web;
	public static Velocity				kb;
	public static Freecam				freecam;
	public static Invisible				invis;
	public static SkinBlink				skin;
	public static Timer					timer;
	public static MiddleClickFriends	middle;
	public static Blink					blink;
	public static NoSlowDown			water;
	public static NoForceTurn			noturn;
	public static VanillaPhase			vphase;
	public static SkipLadder			skipladder;
	public static CDrop					cdrop;
	public static BunnyHop				bunnyhop;
	public static InvMove				invmove;
	public static NameTags				tags;
	public static ESP					esp;
	public static Xray					xray;
	public static Weather				weather;
	public static Jesus					jesus;
	public static Tracers				tracers;
	public static Phaseer				phase;
	public static FastEat				fasteat;
	public static NoRender				norender;
	public static Frames				frames;
	public static AntiHurtcam			hurtcam;
	public static AutoFish				autofish;
	public static Regen					regen;
	public static SafeWalk				safewalk;
	public static GuiClickMod			guiclickmod;
	public static Terrain				terrain;
	public static AutoPot				autopot;
	public static Colors				colors;
	public static RemoteView			rv;
	public static CameraClip			cameraclip;
	public static AutoArmour			autoarmor;
	public static IRC					ircmod;
	public static Waypoints				waypoints;
	public static AutoSoup				autosoup;
	public static Noswing				noswing;
	public static Retard				retard;
	public static Tower					tower;
	public static ChestStealer			cheststealer;
	public static HighJump				highjump;
	public static AutoEgg				autoegg;
	public static TriggerBot			triggerbot;
	public static Trajectories			traj;
	public static PluginSearch			plsearch;

	public static void register() {
		irc = new IrcManager(Minecraft.getMinecraft().session.getUsername());
		otherfilemanager = new OtherFileManager();
		modules = new ArrayList<Module>();
		add(commands = new Commands());
		add(safewalk = new SafeWalk());
		add(hurtcam = new AntiHurtcam());
		add(norender = new NoRender());
		add(tracers = new Tracers());
		add(deathderp = new DeathDerp());
		add(chestesp = new ChestESP());
		add(ircmod = new IRC());
		add(colors = new Colors());
		add(cameraclip = new CameraClip());
		add(piddles = new Piddles());
		add(xray = new Xray());
		add(day = new Day());
		add(autopot = new AutoPot());
		add(invmove = new InvMove());
		add(keepsprint = new KeepSprint());
		add(autosoup = new AutoSoup());
		add(rv = new RemoteView());
		add(jesus = new Jesus());
		add(paralyze = new Paralyze());
		add(autoeat = new AutoEat());
		add(cheststealer = new ChestStealer());
		add(autofish = new AutoFish());
		add(tower = new Tower());
		add(esp = new ESP());
		add(regen = new Regen());
		add(enderaura = new EnderAura());
		add(antiblind = new AntiBlind());
		add(superkb = new SuperKB());
		add(guiclickmod = new GuiClickMod());
		add(waypoints = new Waypoints());
		add(autoclicker = new AutoClicker());
		add(vphase = new VanillaPhase());
		add(plsearch = new PluginSearch());
		add(frames = new Frames());
		add(phase = new Phaseer());
		add(zoot = new Zoot());
		add(firion = new Firion());
		add(fasteat = new FastEat());
		add(highjump = new HighJump());
		add(fastbow = new FastBow());
		add(potionsaver = new PotionSaver());
		add(autoegg = new AutoEgg());
		add(retard = new Retard());
		add(triggerbot = new TriggerBot());
		add(sneakModule = new Sneak());
		add(fastbreak = new FastBreak());
		add(brightModule = new Fullbright());
		add(blink = new Blink());
		add(speed = new Speed());
		add(placeModule = new FastPlace());
		add(flyModule = new Flight());
		add(skipladder = new SkipLadder());
		add(terrain = new Terrain());
		add(bunnyhop = new BunnyHop());
		add(noswing = new Noswing());
		add(auraModule = new NewAura());
		add(fallModule = new Nofall());
		add(kb = new Velocity());
		add(traj = new Trajectories());
		add(freecam = new Freecam());
		add(nukerModule = new CNuker());
		add(invis = new Invisible());
		add(cdrop = new CDrop());
		add(sprintModule = new Sprint());
		add(climbModule = new Spider());
		add(skin = new SkinBlink());
		add(timer = new Timer());
		add(stepModule = new Step());
		add(vclipModule = new VClip());
		add(web = new NoWeb());
		add(middle = new MiddleClickFriends());
		add(water = new NoSlowDown());
		add(noturn = new NoForceTurn());
		add(weather = new Weather());
		add(autoarmor = new AutoArmour());
		add(tags = new NameTags());
		inGameGUI = new ProtocolHUD();
		protocolDir = new File(Minecraft.getMinecraft().mcDataDir + File.separator + "Protocol");
		if (!protocolDir.exists()) {
			protocolDir.mkdirs();
		}
		if (getAura().getCurrent() == null) {
			Protocol.getAura().setCurrent(new SwitchAura());
		}
		commands.setToggled(true);
		colors.setToggled(true);
		colors.onEnable();
		FileUtils.createFile("options");
		FileUtils.createFile("keybinds");
		FileUtils.createFile("colors");
		FileUtils.createFile("modules");
		Keybinds.setupBinds();
		Keybinds.bindKeys();
		ToggledMods.setupBinds();
		ValuesFile.load();
		Display.setTitle("Protocol by Hypno | Minecraft 1.8");
		irc.connect();
	}

	public static int getMainColor() {
		String text = Protocol.primColor;
		Color nigger = new Color(255, 0, 255);
		for (int i = 0; i < text.length(); i++) {
			if ((Protocol.primColor.charAt(i) == '§') && (i + 1 < Protocol.primColor.length())) {
				char oneMore = Character.toLowerCase(text.charAt(i + 1));
				int colorCode = "0123456789abcdefklmnorg".indexOf(oneMore);
				if (colorCode < 16) {
					try {
						int newColor = Minecraft.getMinecraft().fontRendererObj.colorCode[colorCode];
						nigger = new Color((newColor >> 16) / 255.0F, (newColor >> 8 & 0xFF) / 255.0F, (newColor & 0xFF) / 255.0F);
					} catch (Exception exception) {
						exception.printStackTrace();
					}
				}
			}
		}
		return nigger.getRGB();
	}

	public static ValueManager getValueManager() {
		return valueManager;
	}

	public static AuraType getAura() {
		return auratype;
	}

	public static void setColor(java.awt.Color color1) {
		color = color1;
	}

	public static java.awt.Color getColor() {
		return color;
	}

	public static void add(Module module) {
		modules.add(module);
	}

	public static GuiClick getGuiClick() {
		return guiClick;
	}

	public static ArrayList<Module> getModules() {
		return modules;
	}

	public static ProtocolHUD getInGameGUI() {
		return inGameGUI;
	}

	public static void onKeyPressed(int keyCode) {
		for (Module module : getModules()) {
			if (module.getKeyCode() == keyCode) {
				module.toggle();
			}
		}
	}

	public static void onMiddleClick() {
		for (Module module : getModules()) {
			module.onMiddleClick();
		}
	}

	public static void onRender() {
		for (Module module : getModules()) {
			module.onRender();
		}
	}

	public static void onStart() {
	}

	public static FriendManager getFriendManager() {
		if (friendmanager == null)
			friendmanager = new FriendManager();
		return friendmanager;
	}

	public static ProtectManager getProtectManager() {
		if (protectmanager == null)
			protectmanager = new ProtectManager();
		return protectmanager;
	}

	public static OriginalTabGui getTabGui() {
		if (tabGui == null) {
			tabGui = new OriginalTabGui();
		}
		return tabGui;
	}

	public static OriginalTabGui getOldTabGui() {
		if (oldtabGui == null) {
			oldtabGui = new OriginalTabGui();
		}
		return oldtabGui;
	}

	public static OtherFileManager getOtherFileManager() {
		return otherfilemanager;
	}

}

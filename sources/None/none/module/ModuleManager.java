package none.module;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import none.Client;
import none.module.modules.combat.*;
import none.module.modules.movement.*;
import none.module.modules.player.*;
import none.module.modules.render.*;
import none.module.modules.world.*;

public class ModuleManager {

	public ArrayList<Module> modules = new ArrayList<>();
	// Combat
	public Killaura killaura;
	public Velocity velocity;
	public Antibot antibot;
	public AutoSoup autoSoup;
	public NoFriends noFriends;
	public BowAimbot bowAimbot;
	public AuraTeams auraTeams;
	public AutoAwakeNgineXE autoAwakeNgineXE;
	public AutoPot autoPot;
	public Reach reach;
	public AimBot aimBot;
	public AutoClicker autoClicker;
	public SuperKnockback superKnockback;
	public BlockInfinite blockInfinite;
	public FastBow fastBow;
	public AutoApple autoApple;
	public InfiniteAura infiniteAura;
	public Criticals criticals;
	// Movement
	public Sprint sprint;
	public SlimeJump slimeJump;
	public Speed speed;
	public Fly fly;
	public LiquidWalk liquidWalk;
	public Step step;
	public Blink blink;
	public AntiVoid antiVoid;
	public Phase phase;
	public Highjump highjump;
	public Longjump longjump;
	// Player
	public NoWeb noWeb;
	public AutoArmor autoArmor;
	public InvCleaner invCleaner;
	public NoSlowdown noSlowdown;
	public InvMove invMove;
	public MCF mcf;
	public Nofall nofall;
	public NoRotate noRotate;
	public Regen regen;
	public FastUse fastUse;
	public AutoRespawn autoRespawn;
	public FastPlace fastPlace;
	// Render
	public HUD hud;
	public ClickGuiModule clickGui;
	public NoHurtCam noHurtCam;
	public Esp esp;
	public Chams chams;
	public NameTags nameTags;
	public Tracers tracers;
	public BlockHitAM blockAnimtion;
	public ClientColor clientColor;
	public ChestESP chestESP;
	public NotificationHUD notification;
	public NoFire noFire;
	public ItemPhysics itemPhysics;
	public NameProtect nameProtect;
	public Brightness brightness;
	public HUDEditer hudEditer;
	public Freecam freecam;
	public ItemESP itemESP;
	public MusicPlayer musicPlayer;
	public HealthParticle healthParticle;
	public ItemNameTags itemNameTags;
	public TrueSight trueSight;
	public Bobbing bobbing;
	// World
	public Scaffold scaffold;
	public Cheststealer cheststealer;
	public BedFucker bedFucker;
	public Derp derp;
	public PingSpoof pingSpoof;
	public Murder murder;
	public SafeWalk safeWalk;
	public CommandSystem commandSystem;
	public Plugins plugins;
	public SpammerModule spammer;
	public Teleport teleport;
	public GameTimer timer;
	public AutoL autoL;
	public CapeUtils capeUtils;

	public Checker checker;

	public ModuleManager() {
		// combat
		modules.add(killaura = new Killaura());
		modules.add(velocity = new Velocity());
		modules.add(antibot = new Antibot());
		modules.add(autoSoup = new AutoSoup());
		modules.add(noFriends = new NoFriends());
		modules.add(bowAimbot = new BowAimbot());
		modules.add(auraTeams = new AuraTeams());
		modules.add(autoAwakeNgineXE = new AutoAwakeNgineXE());
		modules.add(autoPot = new AutoPot());
		modules.add(criticals = new Criticals());
		modules.add(reach = new Reach());
		modules.add(aimBot = new AimBot());
		modules.add(autoClicker = new AutoClicker());
		modules.add(superKnockback = new SuperKnockback());
		modules.add(blockInfinite = new BlockInfinite());
		modules.add(fastBow = new FastBow());
		modules.add(autoApple = new AutoApple());
		modules.add(infiniteAura = new InfiniteAura());

		// movement
		modules.add(sprint = new Sprint());
		modules.add(slimeJump = new SlimeJump());
		modules.add(speed = new Speed());
		modules.add(fly = new Fly());
		modules.add(liquidWalk = new LiquidWalk());
		modules.add(step = new Step());
		modules.add(blink = new Blink());
		modules.add(antiVoid = new AntiVoid());
		modules.add(phase = new Phase());
		modules.add(highjump = new Highjump());
		modules.add(longjump = new Longjump());

		// player
		modules.add(noWeb = new NoWeb());
		modules.add(autoArmor = new AutoArmor());
		modules.add(invCleaner = new InvCleaner());
		modules.add(noSlowdown = new NoSlowdown());
		modules.add(invMove = new InvMove());
		modules.add(mcf = new MCF());
		modules.add(nofall = new Nofall());
		modules.add(noRotate = new NoRotate());
		modules.add(regen = new Regen());
		modules.add(fastUse = new FastUse());
		modules.add(autoRespawn = new AutoRespawn());
		modules.add(fastPlace = new FastPlace());

		// render
		modules.add(hud = new HUD());
		modules.add(clickGui = new ClickGuiModule());
		modules.add(noHurtCam = new NoHurtCam());
		modules.add(esp = new Esp());
		modules.add(chams = new Chams());
		modules.add(nameTags = new NameTags());
		modules.add(tracers = new Tracers());
		modules.add(blockAnimtion = new BlockHitAM());
		modules.add(clientColor = new ClientColor());
		modules.add(chestESP = new ChestESP());
		modules.add(notification = new NotificationHUD());
		modules.add(noFire = new NoFire());
		modules.add(itemPhysics = new ItemPhysics());
		modules.add(nameProtect = new NameProtect());
		modules.add(brightness = new Brightness());
		modules.add(hudEditer = new HUDEditer());
		modules.add(freecam = new Freecam());
		modules.add(itemESP = new ItemESP());
		modules.add(musicPlayer = new MusicPlayer());
		modules.add(healthParticle = new HealthParticle());
		modules.add(itemNameTags = new ItemNameTags());
		modules.add(trueSight = new TrueSight());
		modules.add(bobbing = new Bobbing());

		// world
		modules.add(cheststealer = new Cheststealer());
		modules.add(bedFucker = new BedFucker());
		modules.add(derp = new Derp());
		modules.add(pingSpoof = new PingSpoof());
		modules.add(murder = new Murder());
		modules.add(safeWalk = new SafeWalk());
		modules.add(teleport = new Teleport());
		modules.add(commandSystem = new CommandSystem());
		modules.add(plugins = new Plugins());
		modules.add(spammer = new SpammerModule());
		modules.add(scaffold = new Scaffold());
		modules.add(timer = new GameTimer());
		modules.add(autoL = new AutoL());
		modules.add(capeUtils = new CapeUtils());

		modules.add(checker = new Checker());
	}

	public ArrayList<Module> getModules() {
		return modules;
	}

	public Module getModule(String name, boolean caseSensitive) {
		return modules.stream()
				.filter(mod -> !caseSensitive && name.equalsIgnoreCase(mod.getName()) || name.equals(mod.getName()))
				.findFirst().orElse(null);
	}
}

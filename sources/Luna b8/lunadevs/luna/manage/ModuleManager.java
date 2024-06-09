package lunadevs.luna.manage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import lunadevs.luna.main.Luna;
import lunadevs.luna.module.Module;
import lunadevs.luna.module.combat.Aimbot;
import lunadevs.luna.module.combat.AntiFire;
import lunadevs.luna.module.combat.AntiKnockback;
import lunadevs.luna.module.combat.AutoClicker;
import lunadevs.luna.module.combat.AutoRespawn;
import lunadevs.luna.module.combat.AutoTool;
import lunadevs.luna.module.combat.BowAimbot;
import lunadevs.luna.module.combat.Criticals;
import lunadevs.luna.module.combat.FastBow;
import lunadevs.luna.module.combat.InfiniteAura;
import lunadevs.luna.module.combat.Killaura;
import lunadevs.luna.module.combat.NormalCriticals;
import lunadevs.luna.module.combat.TPAura;
import lunadevs.luna.module.combat.TriggerBot;
import lunadevs.luna.module.config.GommeHD;
import lunadevs.luna.module.config.Hypixel;
import lunadevs.luna.module.config.Mineplex;
import lunadevs.luna.module.exploits.AACClip;
import lunadevs.luna.module.exploits.AntiBot;
import lunadevs.luna.module.exploits.BedAura;
import lunadevs.luna.module.exploits.ChestStealer;
import lunadevs.luna.module.exploits.Invisible;
import lunadevs.luna.module.exploits.MultiServer;
import lunadevs.luna.module.exploits.NCPGlide;
import lunadevs.luna.module.exploits.NoGhostBlock;
import lunadevs.luna.module.exploits.PacketFly;
import lunadevs.luna.module.exploits.Regen;
import lunadevs.luna.module.exploits.Reload;
import lunadevs.luna.module.exploits.ServerCrasher;
import lunadevs.luna.module.exploits.SpartanGlide;
import lunadevs.luna.module.exploits.Suicide;
import lunadevs.luna.module.exploits.Zoot;
import lunadevs.luna.module.exploits.zPackets;
import lunadevs.luna.module.fun.SlowMotion;
import lunadevs.luna.module.fun.Twerk;
import lunadevs.luna.module.movement.AACJesus;
import lunadevs.luna.module.movement.AACTPFly;
import lunadevs.luna.module.movement.AirHop;
import lunadevs.luna.module.movement.AntiPlate;
import lunadevs.luna.module.movement.Boost;
import lunadevs.luna.module.movement.Flight;
import lunadevs.luna.module.movement.Frames;
import lunadevs.luna.module.movement.GlideModes;
import lunadevs.luna.module.movement.HighJump;
import lunadevs.luna.module.movement.Jesus;
import lunadevs.luna.module.movement.KeepSprint;
import lunadevs.luna.module.movement.Longjump;
import lunadevs.luna.module.movement.LowHop;
import lunadevs.luna.module.movement.Nuker;
import lunadevs.luna.module.movement.SafeWalk;
import lunadevs.luna.module.movement.Scaffold;
import lunadevs.luna.module.movement.Sneak;
import lunadevs.luna.module.movement.Speed;
import lunadevs.luna.module.movement.Sprint;
import lunadevs.luna.module.movement.StepModes;
import lunadevs.luna.module.movement.TimerBoost;
import lunadevs.luna.module.movement.VanillaSpeed;
import lunadevs.luna.module.movement.WallSpeed;
import lunadevs.luna.module.player.AACNoFall;
import lunadevs.luna.module.player.AutoArmor;
import lunadevs.luna.module.player.AutoPot;
import lunadevs.luna.module.player.AutoSoup;
import lunadevs.luna.module.player.AutoTPAccept;
import lunadevs.luna.module.player.Eagle;
import lunadevs.luna.module.player.FastEatModes;
import lunadevs.luna.module.player.FastUse;
import lunadevs.luna.module.player.Greeter;
import lunadevs.luna.module.player.InventoryWalk;
import lunadevs.luna.module.player.MiddleClick;
import lunadevs.luna.module.player.NoFall;
import lunadevs.luna.module.player.NoSlowdownModes;
import lunadevs.luna.module.player.Parkour;
import lunadevs.luna.module.player.Phase;
import lunadevs.luna.module.player.PlayerChecker;
import lunadevs.luna.module.player.SkipLadder;
import lunadevs.luna.module.player.SpeedMine;
import lunadevs.luna.module.render.Animations;
import lunadevs.luna.module.render.ChestESP;
import lunadevs.luna.module.render.CircleESP;
import lunadevs.luna.module.render.ESP;
import lunadevs.luna.module.render.Fullbright;
import lunadevs.luna.module.render.MobESP;
import lunadevs.luna.module.render.RecordingMode;
import lunadevs.luna.module.render.TabGuiColor;
import lunadevs.luna.module.render.Tracers;
import lunadevs.luna.module.render.Wallhack;
import lunadevs.luna.utils.FileUtils;

public class ModuleManager {

	public static ArrayList<Module> mods = new ArrayList<Module>();
	private static final File MODULE = FileUtils.getConfigFile("Modules");

	public static void loadmods() {
		mods.add(new Fullbright());
		mods.add(new Killaura());
		mods.add(new Longjump());
		mods.add(new NoFall());
		mods.add(new Speed());
		mods.add(new Flight());
		mods.add(new AutoSoup());
		mods.add(new AntiFire());
		mods.add(new GlideModes());
		mods.add(new AntiKnockback());
		mods.add(new TPAura());
		mods.add(new Sprint());
		mods.add(new Jesus());
		mods.add(new Regen());
		mods.add(new ChestESP());
		mods.add(new TimerBoost());
		mods.add(new Tracers());
		mods.add(new ESP());
		mods.add(new Aimbot());
		mods.add(new AutoClicker());
		mods.add(new TriggerBot());
		mods.add(new InventoryWalk());
		mods.add(new NoSlowdownModes());
		mods.add(new AutoArmor());
		mods.add(new AutoPot());
		mods.add(new Parkour());
		mods.add(new Zoot());
		mods.add(new AirHop());
		mods.add(new AntiBot()); 
		mods.add(new Invisible());
		//mods.add(new IRC());
		mods.add(new Animations());
		mods.add(new Scaffold());
		mods.add(new Criticals());
		mods.add(new NormalCriticals());
		mods.add(new SpeedMine());
		mods.add(new FastUse());
		mods.add(new ChestStealer());
		//mods.add(new NewNameTags()); Removed bc it has so many render problems :/
		//mods.add(new Phase());
		//mods.add(new AntiChat());
		mods.add(new BowAimbot());
		mods.add(new FastBow());
		mods.add(new SlowMotion());
		mods.add(new Twerk());
		mods.add(new Frames());
		mods.add(new TabGuiColor());
		mods.add(new VanillaSpeed());
		mods.add(new FastEatModes());
	//	mods.add(new Autoblock());
	//	mods.add(new SpamKill());
	//	mods.add(new Spammer());
	//	mods.add(new DupeFreecam());
	//	mods.add(new Freecam());
		mods.add(new AutoTPAccept());
		mods.add(new NoGhostBlock());
		//mods.add(new NoBreakAnimation());
		mods.add(new Nuker());
		mods.add(new SafeWalk());
		mods.add(new Sneak());
		mods.add(new AutoTool());
		mods.add(new MobESP());
		mods.add(new MiddleClick());
		//mods.add(new AACGlide());
		mods.add(new AACNoFall());
		mods.add(new AutoRespawn());
		mods.add(new Boost());
		mods.add(new Reload());
		mods.add(new BedAura());
		mods.add(new Hypixel());
		mods.add(new AACJesus());
		mods.add(new zPackets());
		mods.add(new WallSpeed());
		mods.add(new SkipLadder());
		mods.add(new StepModes());
		mods.add(new AACClip());
		mods.add(new HighJump());
		mods.add(new AntiPlate());
		mods.add(new MultiServer());
		mods.add(new NCPGlide());
		mods.add(new PacketFly());
		mods.add(new RecordingMode());
		mods.add(new InfiniteAura());
		mods.add(new Wallhack());
		mods.add(new Mineplex());
		mods.add(new GommeHD());
		mods.add(new SpartanGlide());
		mods.add(new Greeter());
		mods.add(new PlayerChecker());
		mods.add(new Eagle());
		mods.add(new ServerCrasher());
		mods.add(new CircleESP());
		mods.add(new Phase());
		mods.add(new AACTPFly()); /** Testing, Might get you kicked. */
		mods.add(new LowHop());
		mods.add(new Suicide());
		mods.add(new KeepSprint());
	}

	public static <T extends Module> T findMod(Class<T> clazz)
	{
		for(Module mod: mods)
		{
			if(mod.getClass() == clazz)
			{
				return clazz.cast(mod);
			}
		}
		
		return null;
	}
	
	
	public static ArrayList<Module> getModules() {
		return mods;
	}

	public Module getModule(Class<? extends Module> clazz) {
		for (Module m : getModules()) {
			if (m.getClass() == clazz) {
				return m;
			}
		}
		return null;
	}

	public void setModuleState(String modName, boolean state) {
		for (Module m : mods) {
			if (m.getName().equalsIgnoreCase(modName.trim())) {
				m.setState(state);
				return;
			}
		}
	}

	public static void load() {
		List<String> fileContent = FileUtils.read(MODULE);
		for (String line : fileContent) {
			try {
				String[] split = line.split(":");
				String name = split[0];
				String bind = split[1];
				String enable = split[2];
				int key = Integer.parseInt(bind);
				for (Module m : Luna.moduleManager.mods) {
					if (name.equalsIgnoreCase(m.name)) {
						m.bind = key;
						if ((enable.equalsIgnoreCase("true")) && (!m.isEnabled)) {
							m.toggle();
						}
					}
				}
			} catch (Exception localException) {
			}
		}
	}

	public static void save() {
		List<String> fileContent = new ArrayList();
		for (Module m : Luna.moduleManager.mods) {
			fileContent.add(m.name + ":" + m.bind + ":" + m.isEnabled);
		}
		FileUtils.write(MODULE, fileContent, true);
	}
}

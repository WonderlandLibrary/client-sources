package me.napoleon.napoline.manager;

import net.minecraft.client.Minecraft;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.napoleon.napoline.Napoline;
import me.napoleon.napoline.events.EventKey;
import me.napoleon.napoline.guis.Compass;
import me.napoleon.napoline.guis.clickgui.fakemodules.CloudConfig;
import me.napoleon.napoline.guis.clickgui.fakemodules.PluginMarket;
import me.napoleon.napoline.guis.customgui.CustomGuiManager;
import me.napoleon.napoline.junk.openapi.java.PluginManager;
import me.napoleon.napoline.junk.values.Value;
import me.napoleon.napoline.junk.values.type.Bool;
import me.napoleon.napoline.junk.values.type.Mode;
import me.napoleon.napoline.junk.values.type.Num;
import me.napoleon.napoline.manager.event.EventTarget;
import me.napoleon.napoline.modules.Mod;
import me.napoleon.napoline.modules.ModCategory;
import me.napoleon.napoline.modules.combat.*;
import me.napoleon.napoline.modules.movement.*;
import me.napoleon.napoline.modules.player.*;
import me.napoleon.napoline.modules.render.*;
import me.napoleon.napoline.modules.world.*;
import me.napoleon.napoline.utils.client.FileUtil;
import me.napoleon.napoline.utils.json.JsonUtil;


public class ModuleManager {
    public static List<Mod> modList = new ArrayList<>();
    public static Map<Mod, Object> pluginModsList = new HashMap<>();
    public static Map<Mod, Object> disabledPluginList = new HashMap<>();
    public static List<Mod> fakeModList = new ArrayList<>();
    public static Minecraft mc = Minecraft.getMinecraft();

    public ModuleManager() {
        // Registration 
        modList.add(new Sprint());
        modList.add(new ClickGui());
        modList.add(new Animation());
        modList.add(new FastGuis());
        modList.add(new KeyStrokes());
        modList.add(new ReachDisplay());
        modList.add(new KillAura());
        modList.add(new HUD());
        modList.add(new Fly());
        modList.add(new Compass());
        modList.add(new Reach());
        modList.add(new AntiKnockBack());
        modList.add(new TargetStrafe());
        modList.add(new NoSlowDown());
        modList.add(new AntiBot());
        modList.add(new Teams());
        modList.add(new AntiFall());
        modList.add(new AutoArmor());
        modList.add(new ChestStealer());
        modList.add(new InvCleaner());
        modList.add(new InventoryMove());
        modList.add(new LagbackCheck());
        modList.add(new NoFall());
        modList.add(new PacketMonitor());
        modList.add(new Scaffold());
        modList.add(new Speed());
        modList.add(new SpeedMine());
        modList.add(new ESP());
        modList.add(new me.napoleon.napoline.modules.world.Timer());
        modList.add(new VoidJump());
        modList.add(new NoWeather());
        modList.add(new FullBright());
        modList.add(new NoAttackDelay());
        modList.add(new AutoLadder());
        modList.add(new LightningCheck());
        modList.add(new TargetHud());
        modList.add(new LiquidWalk());
        modList.add(new Eagle());
        modList.add(new AutoRespawn());
        modList.add(new Helper());
        //modList.add(new InvManager());// weird bug
        modList.add(new MotionBlur());
        modList.add(new Nametag());
        modList.add(new ItemPhysic());
        modList.add(new Fucker());
        modList.add(new Music());
        modList.add(new FastUse());
        modList.add(new Phase());
        modList.add(new BlockOverlay());
        modList.add(new ChestESP());
        modList.add(new Tracers());
        modList.add(new Blink());
        modList.add(new Xray());
        modList.add(new EnchantEffect());
        modList.add(new DamageParticle());
        modList.add(new Scoreboard());
        modList.add(new FlyDisabler());
        modList.add(new Disabler());
        modList.add(new Chams());
        modList.add(new ScreenRader());
        modList.add(new Step());
        modList.add(new AntiInvisible());
        modList.add(new Direction());
        modList.add(new TpAura());
        modList.add(new Notifications());
        modList.add(new Debug());


        // I am a lazy cat
        modList.add(new PluginMarket());
        modList.add(new CloudConfig());
        fakeModList.add(new PluginMarket());
        fakeModList.add(new CloudConfig());

        Napoline.pluginManager.onModuleManagerLoad(this, false);

        this.sortModules();

        try {
            this.init();
        } catch (Exception e) {
            e.printStackTrace();
        }

        EventManager.register(this);
    }

    /**
     * Sort functions by first letter of their names
     */
    public void sortModules() {
        modList.sort((m1, m2) -> {
            if (m1.getName().toCharArray()[0] > m2.getName().toCharArray()[0]) {
                return 1;
            }
            return -1;
        });

    }

    /**
     * Add a function
     * @param mod
     */
    public void addModule(Mod mod) {
        File napolineDir = new File(Minecraft.getMinecraft().mcDataDir, Napoline.CLIENT_NAME);
        File pluginDir = new File(napolineDir, "Plugins");
        File[] files = pluginDir.listFiles((dir, name) -> name.endsWith(".jar"));
        for (int i = 0; i < Napoline.pluginManager.urlCL.size(); i++) {
            PluginManager.FixPlugin fixPlugin = new PluginManager.FixPlugin(files, i);
            fixPlugin.start();
            // Wait for plugin repair
            PluginManager.Check checkThread = new PluginManager.Check(fixPlugin);
            checkThread.start();
        }
    }

    /**
     * **Plug-in developerment**
     * Add a plug-in function
     *
     * @param mod    功能
     * @param plugin The plug-in can be either String or NapolinePlugin (subclass of NapolinePlugin)
     */
    public void addPluginModule(Mod mod, Object plugin) {
        pluginModsList.put(mod, plugin);
        modList.add(mod);
    }

    public void init() {
        JsonUtil.load();
    }

    public static List<Mod> getModsByCategory(ModCategory m) {
        List<Mod> findList = new ArrayList<>();
        for (Mod mod : modList) {
            if (mod.getType() == m) {
                findList.add(mod);
            }
        }
        return findList;
    }

    public static Mod getModsByName(String i) {
        for (Mod m : modList) {
            if (!m.getName().equalsIgnoreCase(i)) {
                continue;
            }
            return m;
        }
        return null;
    }

    public static Mod getModByClass(Class<? extends Mod> cls) {
        for (Mod m : modList) {
            if (m.getClass() != cls) {
                continue;
            }
            return m;
        }
        return null;
    }


    @EventTarget
    public void onKey(EventKey e) {
        for (Mod m : modList) {
            if (m.getKey() == e.getKey()) {
                m.toggle();
            }
        }
    }
}

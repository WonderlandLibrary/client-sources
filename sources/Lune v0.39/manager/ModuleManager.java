package me.superskidder.lune.manager;

import me.superskidder.lune.Lune;
import me.superskidder.lune.customgui.CustomGuiManager;
import me.superskidder.lune.events.EventKey;
import me.superskidder.lune.guis.Compass;
import me.superskidder.lune.guis.clickgui.fakemodules.CloudConfig;
import me.superskidder.lune.guis.clickgui.fakemodules.PluginMarket;
import me.superskidder.lune.manager.event.EventTarget;
import me.superskidder.lune.modules.Mod;
import me.superskidder.lune.modules.ModCategory;
import me.superskidder.lune.modules.combat.*;
import me.superskidder.lune.modules.movement.*;
import me.superskidder.lune.modules.player.*;
import me.superskidder.lune.modules.render.*;
import me.superskidder.lune.modules.world.*;
import me.superskidder.lune.openapi.java.PluginManager;
import me.superskidder.lune.utils.client.FileUtil;
import me.superskidder.lune.utils.json.JsonUtil;
import me.superskidder.lune.values.Value;
import me.superskidder.lune.values.type.Bool;
import me.superskidder.lune.values.type.Mode;
import me.superskidder.lune.values.type.Num;
import net.minecraft.client.Minecraft;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ModuleManager {
    public static List<Mod> modList = new ArrayList<>();
    public static Map<Mod, Object> pluginModsList = new HashMap<>();
    public static Map<Mod, Object> disabledPluginList = new HashMap<>();
    public static List<Mod> fakeModList = new ArrayList<>();
    public static Minecraft mc = Minecraft.getMinecraft();

    public ModuleManager() {
        // 注册功能
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
        modList.add(new me.superskidder.lune.modules.world.Timer());
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
        //modList.add(new InvManager());//有bug就不用了:)
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


        // I am a lazy dog
        modList.add(new PluginMarket());
        modList.add(new CloudConfig());
        fakeModList.add(new PluginMarket());
        fakeModList.add(new CloudConfig());

        Lune.pluginManager.onModuleManagerLoad(this, false);

        this.sortModules();

        try {
            this.init();
        } catch (Exception e) {
            e.printStackTrace();
        }

        EventManager.register(this);
    }

    /**
     * 给功能按名字首字母排序
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
     * 添加功能
     * @param mod
     */
    public void addModule(Mod mod) {
        File luneDir = new File(Minecraft.getMinecraft().mcDataDir, Lune.CLIENT_NAME);
        File pluginDir = new File(luneDir, "Plugins");
        File[] files = pluginDir.listFiles((dir, name) -> name.endsWith(".jar"));
        for (int i = 0; i < Lune.pluginManager.urlCL.size(); i++) {
            PluginManager.FixPlugin fixPlugin = new PluginManager.FixPlugin(files, i);
            fixPlugin.start();
            // 等到修复插件线程结束再准备重载 防止出现未修复完毕就重载的问题
            PluginManager.Check checkThread = new PluginManager.Check(fixPlugin);
            checkThread.start();
        }
    }

    /**
     * **插件开发者专用 Lune开发人员请不要调用**
     * 添加一个插件的功能
     *
     * @param mod    功能
     * @param plugin 插件 可以是String也可以是LunePlugin（LunePlugin的子类）
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

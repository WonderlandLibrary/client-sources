package club.bluezenith.module;

import club.bluezenith.BlueZenith;
import club.bluezenith.events.Listener;
import club.bluezenith.events.impl.KeyPressEvent;
import club.bluezenith.module.modules.DevOnly;
import club.bluezenith.module.modules.combat.*;
import club.bluezenith.module.modules.exploit.*;
import club.bluezenith.module.modules.fun.*;
import club.bluezenith.module.modules.misc.Timer;
import club.bluezenith.module.modules.misc.*;
import club.bluezenith.module.modules.movement.*;
import club.bluezenith.module.modules.player.*;
import club.bluezenith.module.modules.render.*;
import club.bluezenith.module.modules.render.hud.HUD;
import com.google.common.collect.Maps;
import net.superblaubeere27.masxinlingvonta.annotation.Outsource;
import security.auth.https.TrustedConnector;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public final class ModuleManager {
    private ArrayList<Module> modules = new ArrayList<>();
    Map<Class<? extends Module>, Module> classToModuleMap = Maps.newHashMap();
    Map<String, Module> nameToModuleMap = Maps.newHashMap();

    public ModuleManager(int code) {
        start(code);
    }

    @Outsource
    private void start(int code) {
        try {
            getMod(new StaffChecker());
            getMod(new AntiBot());
            getMod(new Aura());
            getMod(new Criticals());
            getMod(new SprintReset());
            getMod(new TargetStrafe());
            getMod(new Velocity());
            getMod(new XRay());
            getMod(new AntiVoid());
            getMod(new BlinkHit());
            getMod(new HackerDetector());
            getMod(new Disabler());
            getMod(new FakeLag());
            getMod(new Glint());
            getMod(new FastBreak());
            getMod(new PingSpoof());
            getMod(new DamageImpact());
            getMod(new DeathScreen());
            getMod(new Derp());
            getMod(new FDPFinder());
            getMod(new hampter());
            getMod(new Slow());
            getMod(new YesFall());
            getMod(new NoFall());
            getMod(new AntiBan());
            getMod(new AutoRegister());
            getMod(new Debug());
            getMod(new InvMove());
            getMod(new MemoryFix());
            getMod(new Spammer());
            getMod(new StreamerMode());
            getMod(new KillSults());
            getMod(new AutoPlay());
            getMod(new Timer());
            getMod(new AirJump());
            getMod(new AirWalk());
            getMod(new Flight());
            getMod(new NoSlowDown());
            getMod(new Speed());
            getMod(new Sprint());
            getMod(new Step());
            getMod(new AutoArmor());
            getMod(new BedNuker());
            getMod(new Blink());
            getMod(new ChestAura());
            getMod(new ChestStealer());
            getMod(new FastEat());
            getMod(new InvManager());
            getMod(new NoRotate());
            getMod(new Regen());
            getMod(new Scaffold());
            getMod(new Animations());
            getMod(new AntiBlind());
            getMod(new Camera());
            getMod(new Chams());
            getMod(new ClickGUI());
            getMod(new CustomCape());
            getMod(new Tracers());
            getMod(new PlayerESP());
            getMod(new HUD());
            getMod(new NameTags());
            getMod(new SuperHeroFX());
            getMod(new TabGUI());
            getMod(new TargetHUD());
            getMod(new WorldColor());
            getMod(new Phase());
            getMod(new TPAura());
            getMod(new NewSpeed());
            getMod(new FastPlace());
            getMod(new AutoTool());
            getMod(new ClickTP());
            getMod(new AmongUs(null));
            getMod(new AntiSpam());
            getMod(new ChatBypass());
        //    getMod(new club.bluezenith.module.modules.render.targethud.TargetHUD());
            getMod(new ChestESP());
            getMod(new NewKeybinds());
            getMod(new NewStats());
            getMod(new SafeWalk());
           // getMod(new Zombies());
            getMod(new Atmosphere());
            getMod(new MoreParticles());
            getMod(new LongJump2());
            getMod(new AutoPot());


        } catch (Exception e) {
            e.printStackTrace();
        }
        BlueZenith.getBlueZenith().register(this);
        //keep this at the bottom
        modules.forEach(Module::loadValues);
    }

    @Outsource
    private void getMod(Module mod) {
        try {
            if(mod.getClass().isAnnotationPresent(DevOnly.class) && !TrustedConnector.isDevEnvironment(420424421))
                return;
            modules.add(mod);
            classToModuleMap.put(mod.getClass(), mod);
            nameToModuleMap.put(mod.getName().toLowerCase(), mod);
            for (String alias : mod.aliases) {
                nameToModuleMap.put(alias.toLowerCase(Locale.ENGLISH), mod);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Module> getModules(){
        return modules;
    }

    public List<Module> getModulesByCategory(ModuleCategory moduleCategory) {
        return modules.stream().filter(mod -> mod.getCategory() == moduleCategory).collect(Collectors.toList());
    }

    //hashmaps have O(1) lookup times so this should improve performance
    public Module getModule(String name) {
        return nameToModuleMap.get(name.toLowerCase());
    }

    public Module getModule(Class<? extends Module> clazz) {
        return classToModuleMap.get(clazz);
    }

    @SuppressWarnings("All")
    public <T extends Module> T getAndCast(Class<T> clazz) {
        return (T) classToModuleMap.get(clazz);
    }

    //old way is still accessible
    public Module getModuleFromStream(String name) {
        return modules.stream().filter(mod ->
                        mod.getName().equalsIgnoreCase(name) ||
                        Arrays.stream(mod.aliases).anyMatch(alias -> alias.equalsIgnoreCase(name)))
                .findFirst()
                .orElse(null);

    }

    @SuppressWarnings("ALL")
    public <T extends Module> T getAndCastFromStream(Class<T> clazz) {
        return (T) modules.stream().filter(mod -> mod.getClass() == clazz).findFirst().orElse(null);
    }

    public Module getModuleFromStream(Class<?> clazz) {
       return modules.stream().filter(mod -> mod.getClass() == clazz).findFirst().orElse(null);
    }

    @Listener
    public void handleKey(KeyPressEvent event){
        for (Module m : modules) {
            if(m.keyBind != 0 && event.keyCode == m.keyBind){
                m.toggle();
            }
        }
    }

    public void addModule(Module mod) {
        if(!this.modules.contains(mod)) {
            this.modules.add(mod);
        }
    }


    public void unloadScripts(boolean ignoreEnabled) {
        CopyOnWriteArrayList<Module> mods = new CopyOnWriteArrayList<>(modules);
        CopyOnWriteArrayList<Module> scripts = new CopyOnWriteArrayList<>(modules);
        for(Module mod : mods) {
            if(!mod.isScript/*|| !(mod instanceof ScriptModule)*/) {
                scripts.remove(mod);
                continue;
            }
            if(mod.getState()) {
                if(ignoreEnabled) continue;
                mod.hidden = true;
                mod.setState(false);
            }
            mods.remove(mod);
            scripts.remove(mod);
        }
       // ScriptManager.getScriptManager().scriptModules = scripts.stream().map(script -> (ScriptModule) script).collect(Collectors.toList());
        modules = new ArrayList<>(mods);
        ClickGUI.oldDropdownUI.refreshModules();
    }

    public void reloadScripts() {
        unloadScripts(false);
       // ScriptManager.getScriptManager().findScripts();
    }
}

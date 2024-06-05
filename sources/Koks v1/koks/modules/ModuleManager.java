package koks.modules;

import koks.ClientSwitch;
import koks.Koks;
import koks.modules.impl.combat.*;
import koks.modules.impl.movement.*;
import koks.modules.impl.player.*;
import koks.modules.impl.utilities.*;
import koks.modules.impl.visuals.*;
import koks.modules.impl.world.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author avox | lmao | kroko
 * @created on 29/07/2020.
 */
public class ModuleManager {

    private final List<Module> MODULES = new ArrayList<>();

    public ModuleManager() {
        // Combat
        addModule(new KillAura(), false);
        addModule(new SuperHit(), false);
        addModule(new Velocity(), false);
        addModule(new Reach(), true);
        addModule(new TriggerBot(), true);

        // Movement
        addModule(new BoatFly(), false);
        addModule(new InventoryMove(), false);
        addModule(new Jesus(), false);
        addModule(new NoCobweb(), false);
        addModule(new Speed(), false);
        addModule(new Fly(), false);
        addModule(new NoSlowdown(), false);
        addModule(new Sprint(), true);
        addModule(new LongJump(), false);
        addModule(new TargetStrafe(), false);
        addModule(new AirJump(), false);
        addModule(new Parkour(), true);
        addModule(new Step(), false);

        // Player
        addModule(new AntiFire(), false);
        addModule(new BedFucker(), false);
        addModule(new FastConsume(), false);
        addModule(new NoFall(), false);
        addModule(new Phase(), false);
        addModule(new SetBack(), false);
        addModule(new NoRotate(), false);
        addModule(new InventoryManager(), true);
        addModule(new ChestStealer(), true);
        addModule(new AntiVoid(), false);
        addModule(new AutoArmor(), true);
        addModule(new Blink(), true);

        // Utilities
        addModule(new ClickGUI(), true);
        addModule(new HUD(), true);
        addModule(new Debug(), true);
        addModule(new Disabler(), true);
        addModule(new Cosmetics(), true);
        addModule(new CustomBlock(), true);
        addModule(new AntiFlag(), true);
        addModule(new ShopSaver(), false);

        // Visuals
        addModule(new Ambiance(), true);
        addModule(new Animations(), true);
        addModule(new BlockESP(), true);
        addModule(new ChestESP(), true);
        addModule(new ClearTag(), true);
        addModule(new CustomEnchant(), true);
        addModule(new TrailESP(), true);
        addModule(new ItemESP(), true);
        addModule(new NameTags(), true);
        addModule(new NoBob(), true);
        addModule(new NoFov(), true);
        addModule(new NoHurtcam(), true);
        addModule(new CameraClip(), true);
        addModule(new PlayerESP(), true);
        addModule(new HitAnimation(), true);
        addModule(new FullBright(), true);

        // World
        addModule(new ScaffoldWalk(), false);
        addModule(new FastBridge(), true);
        addModule(new FastPlace(), true);
        addModule(new SafeWalk(), true);
        getModules().sort(Comparator.comparing(Module::getModuleName));
    }

    public void addModule(Module module, boolean legitMod) {
        MODULES.add(module);
        module.setLegit(ClientSwitch.currentType == ClientSwitch.ClientType.KOKS || legitMod);
    }

    public List<Module> getModules() {
        return MODULES;
    }

    public Module getModule(String name) {
        for (Module module : getModules()) {
            if (module.getModuleName().equalsIgnoreCase(name)) {
                return module;
            }
        }
        return null;
    }

    public <T extends Module> T getModule(Class<T> tClass) {
        return (T) this.getModules().stream().filter(module -> module.getClass() == tClass).findAny().orElse(null);
    }

}
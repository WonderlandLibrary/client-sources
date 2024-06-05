package me.dinozoid.strife.module;

import me.dinozoid.strife.StrifeClient;
import me.dinozoid.strife.module.implementations.combat.*;
import me.dinozoid.strife.module.implementations.exploit.*;
import me.dinozoid.strife.module.implementations.movement.*;
import me.dinozoid.strife.module.implementations.player.*;
import me.dinozoid.strife.module.implementations.visuals.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ModuleRepository {

    private static final List<Module> MODULES = new ArrayList<>();

    public void init() {
        add(
            // COMBAT
            new WTapModule(),
            new AutoClickerModule(),
            new AutoHeadModule(),
            new ReachModule(),
            new CriticalsModule(),
            new KillAuraModule(),
            new AutoPotionModule(),
            new TargetStrafeModule(),
            // MOVEMENT
            new SprintModule(),
            new FlightModule(),
            new AntiVoidModule(),
            new SpeedModule(),
            new NoFallModule(),
            // PLAYER
            new NoSlowdownModule(),
            new InventoryMoveModule(),
            new InventoryManagerModule(),
            new ChestStealerModule(),
            new ScaffoldModule(),
            new VelocityModule(),
            new TimerModule(),
            new MCFModule(),
            // EXPLOIT
            new PhaseModule(),
            new BlinkModule(),
            new DisablerModule(),
            new NoRotateModule(),
            new ChatBypassModule(),
            // VISUALS
            new ESPModule(),
            new OverlayModule(),
            new FullbrightModule(),
            new CameraClipModule(),
            new SpeedMineModule(),
            new FastPlaceModule(),
            new MotionBlurModule(),
            new AnimationsModule(),
            new NoHurtCamModule(),
            new NoBobModule(),
            new NoFovModule()
            );
    }

    private void add(Module... modules) {
        Arrays.stream(modules).forEach(module -> {
            module.init();
            MODULES.add(module);
            StrifeClient.INSTANCE.bindRepository().addBindable(module);
        });
    }

    public Module moduleBy(String name) {
        for (Module module : MODULES) {
            if(module.name().equalsIgnoreCase(name) || Arrays.stream(module.aliases()).anyMatch(name::equalsIgnoreCase)) return module;
        }
        return null;
    }

    public List<Module> modulesIn(Category category) {
        return MODULES.stream().filter(module -> module.category().equals(category)).collect(Collectors.toList());
    }

    public <T extends Module> T moduleBy(Class<T> tClass) {
        return (T) MODULES.stream().filter(mod -> mod.getClass().equals(tClass)).findFirst().orElse(null);
    }

    public List<Module> modules() {
        return MODULES;
    }
}

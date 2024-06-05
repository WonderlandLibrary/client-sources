package client.metaware.impl.managers;

import client.metaware.Metaware;
import client.metaware.api.module.api.Category;
import client.metaware.api.module.api.Module;
import client.metaware.impl.module.combat.*;
import client.metaware.impl.module.exploit.Blink;
import client.metaware.impl.module.exploit.Disabler;
import client.metaware.impl.module.exploit.Phase;
import client.metaware.impl.module.misc.*;
import client.metaware.impl.module.movmeent.*;
import client.metaware.impl.module.player.*;
import client.metaware.impl.module.render.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ModuleManager {

    private static final List<Module> MODULES = new ArrayList<>();

    public void init() {
        add(
                new SessionInfo(),
               new Sprint(),
               new TestOverlay(),
               new KillAura(),
               new TargetStrafeV2(),
               new Speed(),
               new Flight(),
               new ClickGui(),
               new AntiVoidf(),
               new ItemPhysics(),
               new Camera(),
               new Animations(),
               new Criticals(),
               new Cape(),
               new AutoTool(),
               new ESP2D(),
               new InfiniteAura(),
               new Nofall(),
               new BreadNiggers(),
               new StaffShitalyzer(),
               new HighJump(),
               new PredictMotion(),
               new GuiMove(),
               new Spammer(),
               new NoRotate(),
               new FastPlace(),
               new Ambience(),
               new Autoplay(),
               new AutoRespawn(),
               new Longjump(),
               new Timer(),
               new Breaker(),
               new Panic(),
               new Killsults(),
               new Wtap(),
               new Derp(),
               new Freecam(),
               new Blink(),
               new Scaffold(),
               new Airwalk(),
               new FastEat(),
               new Step(),
               new Fullbright(),
               new Velocity(),
               new AutoArmor(),
               new Disabler(),
               new Phase(),
               new Nobob(),
               new NoSlow(),
               new ChestStealer(),
               new InvManager()
        );
    }

    private void add(Module... modules) {
        Arrays.stream(modules).forEach(module -> {
            module.init();
            MODULES.add(module);
            Metaware.INSTANCE.getBindManager().addBindable(module);
        });
    }

    public Module getModuleByName(String name) {
        for (Module module : MODULES) {
            if(module.getName().equalsIgnoreCase(name) || Arrays.stream(module.getAliases()).anyMatch(name::equalsIgnoreCase)) return module;
        }
        return null;
    }

    public List<Module> getModulesInCategory(Category category) {
        return MODULES.stream().filter(module -> module.getCategory().equals(category)).collect(Collectors.toList());
    }

    public <T extends Module> T getModuleByClass(Class<T> tClass) {
        return (T) MODULES.stream().filter(mod -> mod.getClass().equals(tClass)).findFirst().orElse(null);
    }

    public List<Module> getModules() {
        return MODULES;
    }
}

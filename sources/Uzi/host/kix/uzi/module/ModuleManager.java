package host.kix.uzi.module;

import host.kix.uzi.management.ListManager;
import host.kix.uzi.module.modules.exploits.*;
import host.kix.uzi.module.modules.render.*;
import host.kix.uzi.module.modules.combat.*;
import host.kix.uzi.module.modules.misc.*;
import host.kix.uzi.module.modules.movement.*;
import host.kix.uzi.module.modules.world.FastPlace;
import host.kix.uzi.module.modules.world.Nuker;
import host.kix.uzi.module.modules.world.SpeedyGonzales;

/**
 * Created by myche on 2/3/2017.
 */
public class ModuleManager extends ListManager<Module> {

    public void load() {
        System.out.println("Loading Module System...");
        addContent(new Blink());
        addContent(new Phase());
        addContent(new Clicker());
        addContent(new Freecam());
        addContent(new PotionSaver());
        addContent(new Overlay());
        addContent(new ClickableGUI());
        addContent(new AutoSoup());
        addContent(new Velocity());
        addContent(new AntiVanish());
        addContent(new VampireZ());
        addContent(new AntiCactus());
        addContent(new NoRotate());
        addContent(new AutoPot());
        addContent(new Speed());
        addContent(new Triggerbot());
        addContent(new Aimbot());
        addContent(new Killaura());
        addContent(new Chams());
        addContent(new Criticals());
        addContent(new Sprint());
        addContent(new Flight());
        addContent(new AntiBot());
        addContent(new AdminNotifier());
        addContent(new AutoTeleport());
        addContent(new AutoFish());
        addContent(new AutoRespawn());
        addContent(new NoFall());
        addContent(new Firion());
        addContent(new Extinguisher());
        addContent(new BowAimbot());
        addContent(new FastPlace());
        addContent(new AntiAim());
        addContent(new ChunkBorders());
        addContent(new NoWeb());
        addContent(new Hitboxes());
        addContent(new Spider());
        addContent(new ESP());
        addContent(new Tracers());
        addContent(new Regen());
        addContent(new Nuker());
        addContent(new Brightness());
        addContent(new NoVoid());
        addContent(new ChestStealer());
        addContent(new Scaffold());
        addContent(new AutoArmor());
        addContent(new NoFOV());
        addContent(new CivBreak());
        addContent(new Console());
        addContent(new Xray());
        addContent(new ProMod());
        addContent(new Prediction());
        addContent(new NoSlowdown());
        addContent(new Strafe());
        addContent(new Protection());
        addContent(new Nametags());
        addContent(new Jesus());
        addContent(new ChestFinder());
        addContent(new SpeedyGonzales());
        addContent(new Crash());
        addContent(new Murder());
        addContent(new Dolphin());
        addContent(new Waypoints());
        System.out.println("Module System Loaded!");
    }

    public Module find(Class<? extends Module> clazz) {
        for (Module mod : getContents()) {
            if (mod.getClass() == clazz) {
                return mod;
            }
        }
        return null;
    }

    public Module find(String theMod) {
        for (Module mod : this.getContents()) {
            if (mod.getName().equalsIgnoreCase(theMod)) {
                return mod;
            }
        }
        return null;
    }

}

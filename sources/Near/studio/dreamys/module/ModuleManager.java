package studio.dreamys.module;

import studio.dreamys.module.chat.Compact;
import studio.dreamys.module.chat.Hide;
import studio.dreamys.module.cosmetics.DragonWings;
import studio.dreamys.module.dungeons.AutoCloseChest;
import studio.dreamys.module.dungeons.Map;
import studio.dreamys.module.hud.ClickGUI;
import studio.dreamys.module.hud.Font;
import studio.dreamys.module.hud.Summons;
import studio.dreamys.module.mines.CoordsGrabber;
import studio.dreamys.module.misc.KneeSurgery;
import studio.dreamys.module.misc.Optimization;
import studio.dreamys.module.misc.Solvers;
import studio.dreamys.module.misc.WindowedFullscreen;
import studio.dreamys.module.movement.KeepSprint;
import studio.dreamys.module.render.Fullbright;
import studio.dreamys.module.render.ItemOutlines;
import studio.dreamys.module.render.ShortDamage;
import studio.dreamys.module.render.SlayerESP;

import java.util.ArrayList;

public class ModuleManager {
    public final ArrayList<Module> modules = new ArrayList<>();

    public ModuleManager() {
        modules.add(new ClickGUI());
        modules.add(new DragonWings());
        modules.add(new SlayerESP());
        modules.add(new KneeSurgery());
        modules.add(new KeepSprint());
        modules.add(new Fullbright());
        modules.add(new Map());
        modules.add(new ShortDamage());
        modules.add(new Summons());
        modules.add(new Hide());
        modules.add(new Optimization());
        modules.add(new ItemOutlines());
        modules.add(new Solvers());
        modules.add(new CoordsGrabber());
        modules.add(new Font());
        modules.add(new AutoCloseChest());
        modules.add(new WindowedFullscreen());

        //this has to be the last
        modules.add(new Compact());
    }

    public Module getModule(String name) {
        for (Module m : modules) {
            if (m.getName().equalsIgnoreCase(name)) {
                return m;
            }
        }
        return null;
    }

    public ArrayList<Module> getModulesInCategory(Category c) {
        ArrayList<Module> mods = new ArrayList<>();
        for (Module m : modules) {
            if (m.getCategory() == c) {
                mods.add(m);
            }
        }
        return mods;
    }
}

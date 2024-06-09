package me.teus.eclipse.utils.managers;

import me.teus.eclipse.modules.impl.player.AutoArmor;
import me.teus.eclipse.modules.impl.player.InvCleaner;
import me.teus.eclipse.modules.Category;
import me.teus.eclipse.modules.Module;
import me.teus.eclipse.modules.impl.combat.Aura;
import me.teus.eclipse.modules.impl.combat.Velocity;
import me.teus.eclipse.modules.impl.misc.Disabler;
import me.teus.eclipse.modules.impl.movement.Flight;
import me.teus.eclipse.modules.impl.movement.Scaffold;
import me.teus.eclipse.modules.impl.movement.Speed;
import me.teus.eclipse.modules.impl.player.ChestStealer;
import me.teus.eclipse.modules.impl.visuals.HUD;
import me.teus.eclipse.modules.impl.visuals.ClickGUI;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager {

    public static List<Module> modules = new ArrayList<>();

    public static void initialize() {
        modules.add(new HUD());
        modules.add(new ClickGUI());
        modules.add(new Aura());
        modules.add(new Flight());
        modules.add(new Speed());
        modules.add(new ChestStealer());
        modules.add(new Scaffold());
        modules.add(new Disabler());
        modules.add(new Velocity());
        modules.add(new InvCleaner());
        modules.add(new AutoArmor());

        for (Module mods : modules){
            System.out.println("Loaded " + mods.name);
        }
    }

    public static void onKey(int key){
        for(Module module : modules)
            if(module.keybind == key)
                module.toggle();
    }

    public static List<Module> getModulesInCat(Category category){
        List<Module> categoryModules = new ArrayList<>();

        for(Module m : modules){
            if(m.getCategory() == category){
                categoryModules.add(m);
            }
        }

        return categoryModules;
    }

    public static Module getModByClass(Class<? extends Module> cls) {
        for (Module m : modules) {
            if (m.getClass() != cls) {
                continue;
            }
            return m;
        }
        return null;
    }
}

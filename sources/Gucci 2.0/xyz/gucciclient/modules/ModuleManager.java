package xyz.gucciclient.modules;

import java.util.*;
import xyz.gucciclient.modules.mods.render.*;
import xyz.gucciclient.modules.mods.utility.*;
import xyz.gucciclient.modules.mods.movement.*;
import xyz.gucciclient.modules.mods.movement.Timer;
import xyz.gucciclient.modules.mods.other.*;
import xyz.gucciclient.modules.mods.combat.*;

public class ModuleManager
{
    private static ArrayList<Module> modules;
    
    public static ArrayList<Module> getModules() {
        return ModuleManager.modules;
    }
    
    static {
        (ModuleManager.modules = new ArrayList<Module>()).add(new A1moboating());
        ModuleManager.modules.add(new Hog1Arm());
        ModuleManager.modules.add(new A0toCricking());
        ModuleManager.modules.add(new G0ui());
        ModuleManager.modules.add(new Screen());
        ModuleManager.modules.add(new DoxThreat());
        ModuleManager.modules.add(new Fly());
        ModuleManager.modules.add(new Speed());
        ModuleManager.modules.add(new FastBridge());
        ModuleManager.modules.add(new Debuff());
        ModuleManager.modules.add(new Aurorororoa());
        ModuleManager.modules.add(new AgroPearl());
        ModuleManager.modules.add(new Fullbright());
        ModuleManager.modules.add(new Heal());
        ModuleManager.modules.add(new G0ttaDipMen());
        ModuleManager.modules.add(new Timer());
        ModuleManager.modules.add(new AntiiiiiiBot());
        ModuleManager.modules.add(new FastBreeeak());
        ModuleManager.modules.add(new Tr1ggerBot());
    }
}

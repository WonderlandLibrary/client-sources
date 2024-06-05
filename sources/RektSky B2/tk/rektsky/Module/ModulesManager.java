package tk.rektsky.Module;

import tk.rektsky.Utils.*;
import tk.rektsky.Module.Settings.*;
import tk.rektsky.Module.Render.*;
import org.reflections.scanners.*;
import org.reflections.*;
import tk.rektsky.*;
import com.mojang.realmsclient.gui.*;
import java.lang.reflect.*;
import java.util.*;

public class ModulesManager
{
    public static ArrayList<Module> MODULES;
    public static Class[] IGNORED;
    
    public static void loadModuleSetting(final YamlUtil.ConfiguredModule module) {
        module.getModule().rawSetToggled(module.isEnabled());
        module.getModule().keyCode = module.getKeybind();
        for (final Setting setting : module.getSettings().keySet()) {
            module.getModule().settings.remove(setting);
            module.getModule().settings.add(setting);
        }
    }
    
    public static Module getModuleByName(final String name) {
        for (final Module module : ModulesManager.MODULES) {
            if (module.name.equalsIgnoreCase(name)) {
                return module;
            }
        }
        return null;
    }
    
    public static Module getModuleByClass(final Class<? extends Module> clazz) {
        for (final Module module : ModulesManager.MODULES) {
            if (clazz.isInstance(module)) {
                return module;
            }
        }
        return null;
    }
    
    public static Module[] getModules() {
        return ModulesManager.MODULES.toArray(new Module[0]);
    }
    
    public static void reloadModules() {
        ModulesManager.MODULES.clear();
        final Module mod = new ToggleNotifications();
        mod.settings.clear();
        for (final Field field : mod.getClass().getFields()) {
            if (field.getType().getSuperclass() == Setting.class) {
                try {
                    mod.settings.add((Setting)field.get(mod));
                }
                catch (IllegalAccessException ex2) {}
            }
        }
        ModulesManager.MODULES.add(mod);
        final Reflections reflections = new Reflections("tk.rektsky.Module", new Scanner[0]);
        final Set<Class<? extends Module>> modules = reflections.getSubTypesOf(Module.class);
        for (final Class<? extends Module> module : modules) {
            if (Arrays.asList((Class[])ModulesManager.IGNORED).contains(module)) {
                continue;
            }
            if (module.getSimpleName().equals("ToggleNotifications")) {
                continue;
            }
            Module m;
            try {
                m = (Module)module.newInstance();
                m.settings.clear();
                for (final Field field2 : module.getFields()) {
                    if (field2.getType().getSuperclass() == Setting.class) {
                        m.settings.add((Setting)field2.get(m));
                    }
                }
            }
            catch (InstantiationException | IllegalAccessException ex3) {
                final ReflectiveOperationException ex;
                final ReflectiveOperationException e = ex;
                continue;
            }
            if (Client.finishedSetup) {
                Client.addClientChat(ChatFormatting.GREEN + "Reloading Module: " + ChatFormatting.YELLOW + m.name);
            }
            ModulesManager.MODULES.add(m);
        }
    }
    
    public static void inti() {
        reloadModules();
    }
    
    static {
        ModulesManager.MODULES = new ArrayList<Module>();
        ModulesManager.IGNORED = new Class[0];
    }
}

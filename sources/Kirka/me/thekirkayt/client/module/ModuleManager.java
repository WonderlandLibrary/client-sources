/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.module;

import java.awt.Font;
import java.io.File;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import me.thekirkayt.client.module.Module;
import me.thekirkayt.event.EventManager;
import me.thekirkayt.event.EventTarget;
import me.thekirkayt.event.events.KeyPressEvent;
import me.thekirkayt.utils.FileUtils;
import me.thekirkayt.utils.UnicodeFontRenderer;
import net.minecraft.client.renderer.RenderHelper;
import org.apache.commons.lang3.StringUtils;
import org.reflections.Reflections;
import org.reflections.scanners.Scanner;

public final class ModuleManager {
    private static final File MODULE_DIR = FileUtils.getConfigFile("Mods");
    public static List<Module> moduleList = new ArrayList<Module>();
    private static final UnicodeFontRenderer arrayText = new UnicodeFontRenderer(new Font("Verdana", 0, 18));

    public static void start() {
        Reflections reflections = new Reflections("me.thekirkayt.client.module.modules", new Scanner[0]);
        Set<Class<Module>> classes = reflections.getSubTypesOf(Module.class);
        for (Class<Module> clazz : classes) {
            try {
                Module.Mod modAnnotation;
                Module loadedModule = clazz.newInstance();
                if (!clazz.isAnnotationPresent(Module.Mod.class)) continue;
                loadedModule.setProperties(clazz.getSimpleName(), (modAnnotation = clazz.getAnnotation(Module.Mod.class)).displayName().equals("null") ? clazz.getSimpleName() : modAnnotation.displayName(), Module.Category.valueOf(StringUtils.capitalize((String)clazz.getPackage().getName().split("modules.")[1])), modAnnotation.keybind(), modAnnotation.suffix(), modAnnotation.shown());
                if (modAnnotation.enabled()) {
                    loadedModule.enable();
                }
                moduleList.add(loadedModule);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        moduleList.sort(new Comparator<Module>(){

            @Override
            public int compare(Module m1, Module m2) {
                String s1 = m1.getDisplayName();
                String s2 = m2.getDisplayName();
                return s1.compareTo(s2);
            }
        });
        ModuleManager.load();
        ModuleManager.save();
        EventManager.register(new ModuleManager());
    }

    @EventTarget
    private void onKeyBoard(KeyPressEvent event) {
        for (Module mod : moduleList) {
            if (event.getKey() != mod.getKeybind()) continue;
            mod.toggle();
            ModuleManager.save();
        }
    }

    public static Module getModule(String modName) {
        for (Module module : moduleList) {
            if (!module.getId().equalsIgnoreCase(modName) && !module.getDisplayName().equalsIgnoreCase(modName)) continue;
            return module;
        }
        Module empty = new Module();
        empty.setProperties("Null", "Null", null, -1, null, false);
        return empty;
    }

    public static void load() {
        try {
            List<String> fileContent = FileUtils.read(MODULE_DIR);
            for (String line : fileContent) {
                String[] split = line.split(":");
                String id = split[0];
                String displayName = split[1];
                String shown = split[2];
                String keybind = split[3];
                String strEnabled = split[4];
                Module module = ModuleManager.getModule(id);
                module.setProperties(id, displayName, module.getCategory(), module.getKeybind(), module.getSuffix(), module.isShown());
                if (module.getId().equalsIgnoreCase("null")) continue;
                int moduleKeybind = keybind.equalsIgnoreCase("null") ? -1 : Integer.parseInt(keybind);
                boolean enabled = Boolean.parseBoolean(strEnabled);
                boolean visible = Boolean.parseBoolean(shown);
                if (enabled != module.isEnabled()) {
                    module.toggle();
                }
                module.setShown(visible);
                module.setDisplayName(displayName);
                module.setKeybind(moduleKeybind);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void save() {
        ArrayList<String> fileContent = new ArrayList<String>();
        for (Module module : moduleList) {
            String id = module.getId();
            String displayName = module.getDisplayName();
            String visible = Boolean.toString(module.isShown());
            String moduleKey = module.getKeybind() <= 0 ? "null" : Integer.toString(module.getKeybind());
            String enabled = Boolean.toString(module.isEnabled());
            fileContent.add(String.format("%s:%s:%s:%s:%s", id, displayName, visible, moduleKey, enabled));
        }
        FileUtils.write(MODULE_DIR, fileContent, true);
    }

    public static List<Module> getModules() {
        return moduleList;
    }

    public static List<Module> getModulesForRender() {
        List<Module> renderList = moduleList;
        Collections.sort(renderList, new Comparator<Module>(){

            @Override
            public int compare(Module mod1, Module mod2) {
                if (RenderHelper.getNahrFont().getStringWidth(String.format(mod1.getTag(), new Object[0])) > RenderHelper.getNahrFont().getStringWidth(String.format(mod2.getTag(), new Object[0]))) {
                    return -1;
                }
                return RenderHelper.getNahrFont().getStringWidth(String.format(mod1.getTag(), new Object[0])) < RenderHelper.getNahrFont().getStringWidth(String.format(mod2.getTag(), new Object[0]));
            }
        });
        return renderList;
    }

}


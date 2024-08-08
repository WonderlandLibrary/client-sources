package me.xatzdevelopments.util;


import java.util.*;

import me.xatzdevelopments.Xatz;
import me.xatzdevelopments.modules.Module;
import me.xatzdevelopments.settings.BooleanSetting;
import me.xatzdevelopments.settings.KeybindSetting;
import me.xatzdevelopments.settings.ModeSetting;
import me.xatzdevelopments.settings.NumberSetting;
import me.xatzdevelopments.settings.Setting;

public class ModulesUtils
{
    public static void SetSettingsMode(final Module.Category Category, final int Module, final int Setting, final int SetTo) {
        final List<Module> modules = Xatz.getModulesByCategory(Category);
        final Module module = modules.get(Module);
        final Setting settings = module.settings.get(Setting);
        if (settings instanceof ModeSetting && ((ModeSetting)settings).modes.size() < SetTo) {
            ((ModeSetting)settings).set(SetTo);
        }
    }
    
    public static void SetSettingsNumber(final Module.Category Category, final int Module, final int Setting, final double SetTo) {
        final List<Module> modules = Xatz.getModulesByCategory(Category);
        final Module module = modules.get(Module);
        final Setting settings = module.settings.get(Setting);
        if (settings instanceof NumberSetting) {
            ((NumberSetting)settings).setValue(SetTo);
        }
    }
    
    public static void SetSettingsBoolean(final Module.Category Category, final int Module, final int Setting, final boolean SetTo) {
        final List<Module> modules = Xatz.getModulesByCategory(Category);
        final Module module = modules.get(Module);
        final Setting settings = module.settings.get(Setting);
        if (settings instanceof BooleanSetting && ((BooleanSetting)settings).isEnabled() != SetTo) {
            ((BooleanSetting)settings).toggle();
        }
    }
    
    public static void EnableModule(final Module.Category Category, final int Module) {
        final List<Module> modules = Xatz.getModulesByCategory(Category);
        final Module module = modules.get(Module);
        module.toggled = true;
    }
    
    public static void EnableModuleWithNumber(final int Module) {
        Xatz.getModuleByNumber(Module).toggled = true;
    }
    
    public static void SetSettingsBooleanWithNumber(final int Module, final int Setting, final boolean SetTo) {
        final Module module = Xatz.getModuleByNumber(Module);
        final Setting settings = module.settings.get(Setting);
        if (settings instanceof BooleanSetting && ((BooleanSetting)settings).isEnabled() != SetTo) {
            ((BooleanSetting)settings).toggle();
        }
    }
    
    public static void SetSettingsNumberWithNumber(final int Module, final int Setting, final float SetTo) {
        final Module module = Xatz.getModuleByNumber(Module);
        final Setting settings = module.settings.get(Setting);
        if (settings instanceof NumberSetting) {
            ((NumberSetting)settings).setValue(SetTo);
        }
    }
    
    public static void SetSettingsModeWithNumber(final int Module, final int Setting, final String SetTo) {
        final Module module = Xatz.getModuleByNumber(Module);
        final Setting settings = module.settings.get(Setting);
        if (settings instanceof ModeSetting && ((ModeSetting)settings).modes.contains(SetTo)) {
            ((ModeSetting)settings).set(SetTo);
        }
    }
    
    public static ModeSetting GetSettingsModeWithNumber(final int Module, final int Setting) {
        final Module module = Xatz.getModuleByNumber(Module);
        final Setting settings = module.settings.get(Setting);
        if (settings instanceof ModeSetting) {
            return (ModeSetting)settings;
        }
        return null;
    }
    
    public static Module GetModuleWithNumber(final int Module) {
        return Xatz.getModuleByNumber(Module);
    }
    
    public static void SetSettingsKeyCodeWithNumber(final int Module, final int Setting, final int SetTo) {
        final Module module = Xatz.getModuleByNumber(Module);
        final Setting settings = module.settings.get(Setting);
        if (settings instanceof KeybindSetting) {
            module.keycode.code = SetTo;
        }
    }
    
    public static void DisableAll(final String CurrentName) {
        Module.Category[] values;
        for (int length = (values = Module.Category.values()).length, i = 0; i < length; ++i) {
            final Module.Category c = values[i];
            final List<Module> modules = Xatz.getModulesByCategory(c);
            for (final Module m : modules) {
                if (m.name == "TabGUI" || m.name == CurrentName) {
                    return;
                }
                m.toggled = false;
            }
        }
    }
    
    public static void SetSettingsModeWithNumberInt(final int Module, final int Setting, final int SetTo) {
        final Module module = Xatz.getModuleByNumber(Module);
        final Setting settings = module.settings.get(Setting);
        if (settings instanceof ModeSetting) {
            ((ModeSetting)settings).set(SetTo);
        }
    }
    public static ModeSetting GetModeSetting(final String ModuleName, final String SettingName) {
        for (final Module m : Xatz.modules) {
            if (m.name.toLowerCase().contains(ModuleName.toLowerCase())) {
                for (final Setting s : m.settings) {
                    if (s.name.toLowerCase().contains(SettingName.toLowerCase()) && s instanceof ModeSetting) {
                        return (ModeSetting)s;
                    }
                }
            }
        }
        return null;
    }

    public static BooleanSetting GetBoolSetting(final String ModuleName, final String SettingName) {
        for (final Module m : Xatz.modules) {
            if (m.name.toLowerCase().contains(ModuleName.toLowerCase())) {
                for (final Setting s : m.settings) {
                    if (s.name.toLowerCase().contains(SettingName.toLowerCase()) && s instanceof BooleanSetting) {
                        return (BooleanSetting)s;
                    }
                }
            }
        }
        return null;
    }
}

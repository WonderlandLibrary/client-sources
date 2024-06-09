// 
// Decompiled by Procyon v0.5.36
// 

package de.Hero.settings;

import java.util.stream.Collector;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import intent.AquaDev.aqua.modules.Module;
import java.util.Optional;
import java.util.ArrayList;

public class SettingsManager
{
    public static ArrayList<Setting> settings;
    
    public void register(final Setting setting) {
        SettingsManager.settings.add(setting);
    }
    
    public Setting getSetting(final String name) {
        final Optional<Setting> setting = getSettings().stream().filter(s -> s.getName().equalsIgnoreCase(name)).findFirst();
        return setting.orElse(null);
    }
    
    public ArrayList<Setting> getSettingsFromModule(final Module module) {
        return getSettings().stream().filter(s -> s.getModule().equals(module)).collect((Collector<? super Object, ?, ArrayList<Setting>>)Collectors.toCollection((Supplier<R>)ArrayList::new));
    }
    
    public static ArrayList<Setting> getSettings() {
        return SettingsManager.settings;
    }
    
    static {
        SettingsManager.settings = new ArrayList<Setting>();
    }
}

package baritone.api;

import java.util.*;
import baritone.api.utils.*;

public final class BaritoneAPI
{
    private static final IBaritoneProvider provider;
    private static final Settings settings;
    
    public BaritoneAPI() {
        super();
    }
    
    public static IBaritoneProvider getProvider() {
        return BaritoneAPI.provider;
    }
    
    public static Settings getSettings() {
        return BaritoneAPI.settings;
    }
    
    static {
        provider = ServiceLoader.load(IBaritoneProvider.class).iterator().next();
        SettingsUtil.readAndApply(settings = new Settings());
    }
}

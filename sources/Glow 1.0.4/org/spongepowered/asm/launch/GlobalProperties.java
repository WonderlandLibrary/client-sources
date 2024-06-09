package org.spongepowered.asm.launch;

import org.spongepowered.asm.service.*;
import java.util.*;

public final class GlobalProperties
{
    private static IGlobalPropertyService service;
    
    private GlobalProperties() {
        super();
    }
    
    private static IGlobalPropertyService getService() {
        if (GlobalProperties.service == null) {
            GlobalProperties.service = ServiceLoader.load(IGlobalPropertyService.class, GlobalProperties.class.getClassLoader()).iterator().next();
        }
        return GlobalProperties.service;
    }
    
    public static <T> T get(final String s) {
        return getService().getProperty(s);
    }
    
    public static void put(final String s, final Object o) {
        getService().setProperty(s, o);
    }
    
    public static <T> T get(final String s, final T t) {
        return getService().getProperty(s, t);
    }
    
    public static String getString(final String s, final String s2) {
        return getService().getPropertyString(s, s2);
    }
    
    public static final class Keys
    {
        public static final String INIT = "mixin.initialised";
        public static final String AGENTS = "mixin.agents";
        public static final String CONFIGS = "mixin.configs";
        public static final String TRANSFORMER = "mixin.transformer";
        public static final String PLATFORM_MANAGER = "mixin.platform";
        public static final String FML_LOAD_CORE_MOD = "mixin.launch.fml.loadcoremodmethod";
        public static final String FML_GET_REPARSEABLE_COREMODS = "mixin.launch.fml.reparseablecoremodsmethod";
        public static final String FML_CORE_MOD_MANAGER = "mixin.launch.fml.coremodmanagerclass";
        public static final String FML_GET_IGNORED_MODS = "mixin.launch.fml.ignoredmodsmethod";
        
        private Keys() {
            super();
        }
    }
}

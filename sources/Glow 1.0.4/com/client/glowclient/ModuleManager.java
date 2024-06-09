package com.client.glowclient;

import com.client.glowclient.modules.*;
import javax.annotation.*;
import com.google.common.collect.*;
import java.util.*;

public class ModuleManager
{
    private static final ModuleManager A;
    private static final Map<String, Module> B;
    private static final Set<Class<? extends Module>> b;
    
    public ModuleManager() {
        super();
    }
    
    public static ModuleManager M() {
        return ModuleManager.A;
    }
    
    @Nullable
    public static Module M(final Module module) {
        return ModuleManager.B.get(module.k());
    }
    
    @Nullable
    public static Module M(final String s) {
        if (ModuleManager.B.get(s) != null) {
            return ModuleManager.B.get(s);
        }
        return M("EventMod");
    }
    
    public static void D(@Nonnull final Module module) {
        if (ModuleManager.B.remove(module.k()) != null) {
            module.k();
        }
    }
    
    static {
        A = new ModuleManager();
        B = Maps.newTreeMap((Comparator)String.CASE_INSENSITIVE_ORDER);
        b = Sets.newHashSet();
    }
    
    public static Collection<NF> M() {
        return Collections.unmodifiableCollection((Collection<? extends NF>)ModuleManager.B.values());
    }
    
    public static void M(@Nonnull final Module module) {
        if (!ModuleManager.b.contains(module.getClass())) {
            ModuleManager.b.add(module.getClass());
            ModuleManager.B.put(module.k(), module);
        }
    }
}

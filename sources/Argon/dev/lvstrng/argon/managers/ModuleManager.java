// 
// Decompiled by the rizzer xd
// 

package dev.lvstrng.argon.managers;

import dev.lvstrng.argon.modules.Category;
import dev.lvstrng.argon.modules.Module;

import java.util.ArrayList;
import java.util.List;

public final class ModuleManager {
    private final List<Module> modules;

    public ModuleManager() {
        this.modules = new ArrayList<>();
        this.method588();
        this.method590();
    }

    private static native String method595(final int p0, final int p1, final int p2);

    private native String method587();

    public native void method588();

    public native List getModules2();

    public native void method590();

    public native List getModulesInCategory(final Category category);

    public native Module getModuleByClass(final Class moduleClass);

    public native void method593(final Module module);

    public native List<Module> getModules();
}

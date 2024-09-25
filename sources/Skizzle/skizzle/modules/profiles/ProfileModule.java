/*
 * Decompiled with CFR 0.150.
 */
package skizzle.modules.profiles;

import skizzle.Client;
import skizzle.modules.Module;
import skizzle.modules.ModuleManager;

public class ProfileModule
extends Module {
    @Override
    public void onEnable() {
        ProfileModule Nigga;
        Client.currentProfile = Nigga.name;
        ModuleManager.reloadModules();
    }

    public ProfileModule(String Nigga) {
        super(Nigga, 0, Module.Category.PROFILES);
        ProfileModule Nigga2;
    }

    public static {
        throw throwable;
    }
}


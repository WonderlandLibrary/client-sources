// Decompiled by the rizzer xd

package dev.lvstrng.argon.modules.impl;

import com.sun.jna.Memory;
import dev.lvstrng.argon.Argon;
import dev.lvstrng.argon.auth.Authentication;
import dev.lvstrng.argon.clickgui.ClickGUI;
import dev.lvstrng.argon.modules.Category;
import dev.lvstrng.argon.modules.Module;
import dev.lvstrng.argon.modules.setting.Setting;
import dev.lvstrng.argon.modules.setting.settings.BooleanSetting;
import dev.lvstrng.argon.utils.MemoryUtils;

import java.io.File;

public final class SelfDestruct extends Module {
    private static final String url = "https://cdn.modrinth.com/data/5ZwdcRci/versions/FEOsWs1E/ImmediatelyFast-Fabric-1.2.11%2B1.20.4.jar";
    public static boolean isSelfDestructed;
    private final BooleanSetting replaceModSetting;
    private final BooleanSetting saveLastModifiedSetting;

    public SelfDestruct() {
        super("Self Destruct", "Removes the client from your game |Credits to lwes for deletion|", 0, Category.CLIENT);
        this.replaceModSetting = new BooleanSetting("Replace Mod", true).setDescription("Replaces the mod with the original JAR file of the ImmediatelyFast mod");
        this.saveLastModifiedSetting = new BooleanSetting("Save Last Modified", true).setDescription("Saves the last modified date after self destruct");
        this.addSettings(new Setting[]{this.replaceModSetting, this.saveLastModifiedSetting});
    }

    @Override
    public void onEnable() {
        isSelfDestructed = true;
        Argon.INSTANCE.getModuleManager().getModuleByClass(ClickGUI.class).setEnabled(false);
        this.setEnabled(false);
        try {
            Argon.INSTANCE.getConfigManager().saveConfig();
        } catch (final Throwable _t) {
            _t.printStackTrace(System.err);
        }

        boolean isInClickGUI = this.mc.currentScreen instanceof ClickGUI;

        if (isInClickGUI) {
            Argon.INSTANCE.guiOpen = false;
            this.mc.currentScreen.close();
        }

        File jar = null;

        try {
            jar = Authentication.getArgonJar();
        } catch (final Throwable _t) {
            _t.printStackTrace(System.err);
        }

        if (jar == null) throw new Error("not good");
        long lmt = jar.lastModified();

        if (replaceModSetting.getValue()) {
            try {
                Authentication.downloadTo(url, jar);
            } catch (final Throwable _t) {
                _t.printStackTrace(System.err);
                throw new Error("misclick");
            }
        }

        if (this.saveLastModifiedSetting.getValue())
            jar.setLastModified(lmt);

        if (isInClickGUI) {
            if (mc.currentScreen instanceof ClickGUI) {
                Argon.INSTANCE.guiOpen = false;
                mc.currentScreen.close();
            }

            try {
                disableAllModules();
                cleanUpSettings();
            } catch (final Throwable _t) {
                _t.printStackTrace(System.err);
            }

            gc();
        }
    }

    private void disableAllModules() {
        for (final Module module : Argon.INSTANCE.getModuleManager().getModules()) {
            module.setEnabled(false);
            module.setName(null);
            module.setDescription(null);
        }
    }

    private void cleanUpSettings() {
        for (Module module : Argon.INSTANCE.getModuleManager().getModules()) {
            module.setEnabled(false);
            module.setName(null);
            module.setDescription(null);
            for (Setting<?> setting : module.getSettings()) {
                setting.setName(null);
                setting.setDescription(null);
            }
            module.getSettings().clear();
        }
    }


    private void gc() {
        Runtime runtime = Runtime.getRuntime();
        for (int j = 1; j <= 10; j++) {
            runtime.gc();
            runtime.runFinalization();
            try {
                Thread.sleep(100 * j);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            Memory.purge();
            Memory.disposeAll();
            MemoryUtils.occupy();
        }
    }
}
package dev.lvstrng.argon;

import dev.lvstrng.argon.clickgui.ClickGUI;
import dev.lvstrng.argon.event.EventBus;
import dev.lvstrng.argon.managers.ConfigManager;
import dev.lvstrng.argon.managers.ModuleManager;
import dev.lvstrng.argon.managers.RotationManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;

public final class Argon {
    public static MinecraftClient mc;
    public static Argon INSTANCE;
    public RotationManager ROTATION_MANAGER;
    public ConfigManager CONFIG_MANAGER;
    public ModuleManager MODULE_MANAGER;
    public EventBus EVENT_BUS;
    public boolean guiOpen;
    public ClickGUI clickGUI;
    public Screen screen;

    public Argon() {
        INSTANCE = this;
        mc = MinecraftClient.getInstance();
        this.screen = null;
        this.EVENT_BUS = new EventBus();
        this.CONFIG_MANAGER = new ConfigManager();
        this.MODULE_MANAGER = new ModuleManager();
        this.ROTATION_MANAGER = new RotationManager();
        this.clickGUI = new ClickGUI();
    }

    public RotationManager getRotationManager() {
        return ROTATION_MANAGER;
    }

    public ConfigManager getConfigManager() {
        return CONFIG_MANAGER;
    }

    public ModuleManager getModuleManager() {
        return MODULE_MANAGER;
    }

    public EventBus getEventBus() {
        return EVENT_BUS;
    }
}
package ez.cloudclient.module;

import ez.cloudclient.CloudClient;
import ez.cloudclient.setting.ModuleSettings;
import ez.cloudclient.setting.settings.BooleanSetting;
import ez.cloudclient.setting.settings.KeybindSetting;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

import static ez.cloudclient.CloudClient.SETTINGS_MANAGER;

public abstract class Module {

    protected static final Minecraft mc = Minecraft.getMinecraft();
    private final String name;
    private final Category category;
    private final String description;
    public ModuleSettings settings = new ModuleSettings();
    protected Logger LOGGER = CloudClient.log;
    private String displayName;

    public Module(String name, Category category, String description) {
        this(name, category, description, -1);
    }

    public Module(String name, Category category, String description, int key) {
        this.name = name.toLowerCase().replaceAll(" ", "_");
        this.displayName = name;
        this.category = category;
        this.description = description;
        settings.addSetting("Bind", new KeybindSetting(key));
    }

    public void registerSettings() {
        settings.addSetting("Drawn", new BooleanSetting(true));
        settings.addSetting("Enabled", new BooleanSetting(false));
        selfSettings();
        LOGGER.info("Registered settings of " + this.getName());
    }

    public void selfSettings() {
    }

    public ModuleSettings getSettings() {
        return settings;
    }

    public void setSettings(ModuleSettings newSettings) {
        settings = newSettings;
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String name) {
        this.displayName = name;
    }

    public Category getCategory() {
        return category;
    }

    public int getKey() {
        return settings.getSetting("Bind", KeybindSetting.class).getKey();
    }

    public void setKey(int newKey) {
        settings.getSetting("Bind", KeybindSetting.class).setKey(newKey);
    }

    public String getKeyName() {
        return settings.getSetting("Bind", KeybindSetting.class).getKeyName();
    }

    public String getDesc() {
        return description;
    }

    public boolean isEnabled() {
        return settings.getSetting("Enabled", BooleanSetting.class).getValue();
    }

    public void setEnabled(boolean bool) {
        if (bool) enable();
        else disable();
        SETTINGS_MANAGER.updateSettings();
    }

    public boolean isDrawn() {
        return settings.getSetting("Drawn", BooleanSetting.class).getValue();
    }

    public void setDrawn(boolean bool) {
        if (bool) enableDrawn();
        else disableDrawn();
    }

    protected void onEnable() {
    }

    protected void onDisable() {
    }

    public void onTick() {
    }

    public void enable() {
        MinecraftForge.EVENT_BUS.register(this);
        onEnable();
        settings.getSetting("Enabled", BooleanSetting.class).setValue(true);
        SETTINGS_MANAGER.updateSettings();
    }

    public void disable() {
        MinecraftForge.EVENT_BUS.unregister(this);
        onDisable();
        settings.getSetting("Enabled", BooleanSetting.class).setValue(false);
        SETTINGS_MANAGER.updateSettings();
    }

    public void enableDrawn() {
        settings.getSetting("Drawn", BooleanSetting.class).setValue(true);
        SETTINGS_MANAGER.updateSettings();
    }

    public void disableDrawn() {
        settings.getSetting("Drawn", BooleanSetting.class).setValue(false);
        SETTINGS_MANAGER.updateSettings();
    }

    public void toggle() {
        if (settings.getSetting("Enabled", BooleanSetting.class).getValue()) disable();
        else enable();
    }

    public void toggleDrawn() {
        if (settings.getSetting("Drawn", BooleanSetting.class).getValue()) disableDrawn();
        else enableDrawn();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Module module = (Module) o;
        return name.equals(module.name) &&
                displayName.equals(module.displayName) &&
                category == module.category;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, category);
    }

    @Override
    public String toString() {
        return "Module{" +
                "name='" + name + '\'' +
                ", displayName='" + displayName + '\'' +
                ", category=" + category +
                ", enabled=" + isEnabled() +
                '}';
    }

    //A - Z Please
    public enum Category {
        COMBAT,
        EXPLOITS,
        MISC,
        MOVEMENT,
        NONE,
        PLAYER,
        RENDER
    }

}

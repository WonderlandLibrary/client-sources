// 
// Decompiled by the rizzer xd
// 

package dev.lvstrng.argon.modules;

import dev.lvstrng.argon.Argon;
import dev.lvstrng.argon.event.EventBus;
import dev.lvstrng.argon.modules.setting.Setting;
import net.minecraft.client.MinecraftClient;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Module implements Serializable {
    private final List<Setting> settings;
    public EventBus eventBus;
    protected MinecraftClient mc;
    private CharSequence name;
    private CharSequence description;
    private boolean enabled;
    private int keybind;
    private Category category;

    public Module(final CharSequence name, final CharSequence description, final int key, final Category category) {
        this.settings = new ArrayList();
        this.eventBus = Argon.INSTANCE.EVENT_BUS;
        this.mc = MinecraftClient.getInstance();
        this.name = name;
        this.description = description;
        this.enabled = false;
        this.keybind = key;
        this.category = category;
    }

    public void method100() {
        this.enabled = !this.enabled;
        if (this.enabled) {
            this.onEnable();
        } else {
            this.onDisable();
        }
    }

    public void onEnable() {
    }

    public void onDisable() {
    }

    public void method103(final Setting setting) {
        this.settings.add(setting);
    }

    public void addSettings(final Setting[] settings) {
        this.settings.addAll(Arrays.asList(settings));
    }

    public void toggle(final boolean enabled) {
        this.enabled = enabled;
        if (enabled) this.onEnable();
        else this.onDisable();
    }

    public void silentEnable(final boolean enabled) {
        this.enabled = enabled;
    }

    public List<Setting> getSettings() {
        return this.settings;
    }

    public CharSequence getName() {
        return this.name;
    }

    public void setName(final CharSequence name) {
        this.name = name;
    }

    public CharSequence getDescription() {
        return this.description;
    }

    public void setDescription(final CharSequence description) {
        this.description = description;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }

    public int getKeybind() {
        return this.keybind;
    }

    public void setKeybind(final int key) {
        this.keybind = key;
    }

    public Category getCategory() {
        return this.category;
    }

    public void setCategory(final Category category) {
        this.category = category;
    }
}

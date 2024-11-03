package com.minus.module;

import com.minus.Client;
import com.minus.module.setting.Setting;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.MinecraftClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Module {
    @Getter
    private final List<Setting> settings = new ArrayList<>();
    protected static MinecraftClient mc = MinecraftClient.getInstance();
    @Setter
    @Getter
    private String name;
    @Getter
    @Setter
    private String suffix;
    @Setter
    @Getter
    private String description;
    @Getter
    private boolean enabled;
    @Setter
    @Getter
    private int key;
    @Setter
    @Getter
    private Category category;
    private boolean registered;

    public Module(String name, String description, int key, Category category) {
        this.name = name;
        this.description = description;
        enabled = false;
        this.key = key;
        this.category = category;
    }

    public void toggle() {
        setEnabled(!enabled);
    }

    public void onEnable() {
        Client.INSTANCE.getEventManager().subscribe(this);
    }

    public void onDisable() {
    }

    public void addSetting(Setting setting) {
        this.settings.add(setting);
    }

    public void addSettings(Setting... settings) {
        this.settings.addAll(Arrays.asList(settings));
    }

    public final void setEnabled(boolean enabled) {
        if (this.enabled != enabled) {
            this.enabled = enabled;

            if (enabled) {
                onEnable();
                if (this.enabled) {
                    Client.INSTANCE.getEventManager().subscribe(this);
                    registered = true;
                }
            } else {
                if (registered) {
                    Client.INSTANCE.getEventManager().unsubscribe(this);
                    registered = false;
                }
                onDisable();
            }
        }
    }
}

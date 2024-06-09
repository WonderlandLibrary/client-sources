package dev.vertic.module;

import dev.vertic.Client;
import dev.vertic.Utils;
import dev.vertic.module.api.Category;
import dev.vertic.setting.Setting;
import dev.vertic.setting.impl.BooleanSetting;
import dev.vertic.util.animation.Animation;
import dev.vertic.util.animation.Direction;
import dev.vertic.util.animation.impl.DecelerateAnimation;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

@Getter
public class Module implements Utils {

    protected static final Minecraft mc = Minecraft.getMinecraft();
    private final ArrayList<Setting> settings = new ArrayList<>();
    private final String name, description;
    private final Category category;
    private boolean enabled, registered;
    @Setter
    private int key;
    private BooleanSetting hidden = new BooleanSetting("Hidden", false);

    public Module(final String name, final String description, final Category category) {
        this.name = name;
        this.description = description;
        this.category = category;
        settings.add(hidden);
    }

    protected void onEnable() {}
    protected void onDisable() {}
    public void onInit() {}
    public void update() {}

    public void enable() {
        if (!enabled) {
            onEnable();
            register();
            enabled = true;
        }
    }
    public void disable() {
        if (enabled) {
            unregister();
            onDisable();
            enabled = false;
        }
    }

    public void toggle() {
        enabled = !enabled;
        if (enabled) {
            onEnable();
            register();
        } else {
            unregister();
            onDisable();
        }
    }
    public void toggleSilent() {
        enabled = !enabled;
        if (enabled) {
            register();
        } else {
            unregister();
        }
    }

    public void setState(final boolean state) {
        if (enabled != state) {
            enabled = state;
            if (enabled) {
                onEnable();
                register();
            } else {
                unregister();
                onDisable();
            }
        }
    }
    public void setStateSilent(final boolean state) {
        if (enabled != state) {
            enabled = state;
            if (enabled) {
                register();
            } else {
                unregister();
            }
        }
    }

    public String getSuffix() {
        return null;
    }

    public void addSettings(final Setting...settings) {
        this.settings.clear();
        this.settings.addAll(Arrays.asList(settings));
        this.settings.add(hidden);
    }

    public <T extends Setting> T getSetting(final String name) {
        Optional<Setting> optional = settings.stream().filter(s -> s.getName().equalsIgnoreCase(name)).findFirst();
        if (optional.isPresent()) {
            return (T) optional.get();
        }
        return null;
    }

    public <T extends Setting> T getSettingNoSpace(final String name) {
        Optional<Setting> optional = settings.stream().filter(s -> s.getName().equalsIgnoreCase(name.replaceAll(" ", ""))).findFirst();
        if (optional.isPresent()) {
            return (T) optional.get();
        }
        return null;
    }

    private void register() {
        if (!registered) {
            Client.instance.getEventBus().register(this);
            registered = true;
        }
    }
    private void unregister() {
        if (registered) {
            Client.instance.getEventBus().unregister(this);
            registered = false;
        }
    }

    public boolean canBeEnabled() {
        return true;
    }

    private boolean getRegistered() {
        return registered;
    }

}

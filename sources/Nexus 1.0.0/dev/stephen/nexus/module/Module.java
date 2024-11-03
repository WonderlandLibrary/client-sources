package dev.stephen.nexus.module;

import dev.stephen.nexus.Client;
import dev.stephen.nexus.module.setting.Setting;
import dev.stephen.nexus.module.setting.impl.newmodesetting.NewModeSetting;
import dev.stephen.nexus.utils.render.notifications.impl.Notification;
import dev.stephen.nexus.utils.render.notifications.impl.NotificationMoode;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.MinecraftClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Setter
@Getter
public abstract class Module {

    private final List<Setting> settings = new ArrayList<>();
    protected static MinecraftClient mc = MinecraftClient.getInstance();

    private String name;

    private String suffix;

    private String description;

    private boolean enabled;

    private int key;

    private ModuleCategory moduleCategory;
    private boolean registered;

    public Module(String name, String description, int key, ModuleCategory moduleCategory) {
        this.name = name;
        this.description = description;
        enabled = false;
        this.key = key;
        this.moduleCategory = moduleCategory;
    }

    public void toggle() {
        setEnabled(!enabled);
    }

    public void onEnable() {

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
                    for (Setting setting : settings) {
                        if (setting instanceof NewModeSetting newModeSetting) {
                            newModeSetting.getCurrentMode().onEnable();
                        }
                    }
                    if (this.enabled) {
                        Client.INSTANCE.getNotificationManager().addNewNotification(new Notification(this.getName(), 2000, NotificationMoode.MODULE_ENABLED));
                        Client.INSTANCE.getEventManager().subscribe(this);

                        registered = true;
                    }
                }
            } else {
                if (registered) {
                    Client.INSTANCE.getNotificationManager().addNewNotification(new Notification(this.getName(), 2000, NotificationMoode.MODULE_DISABLED));
                    Client.INSTANCE.getEventManager().unsubscribe(this);
                    registered = false;
                }
                onDisable();
                for (Setting setting : settings) {
                    if (setting instanceof NewModeSetting newModeSetting) {
                        newModeSetting.getCurrentMode().onDisable();
                    }
                }
            }
        }
    }

    public boolean isNull() {
        return mc.player == null || mc.world == null;
    }
}

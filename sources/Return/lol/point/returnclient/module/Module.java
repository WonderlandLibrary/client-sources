package lol.point.returnclient.module;

import lol.point.Return;
import lol.point.returnclient.settings.Setting;
import lol.point.returnclient.util.MinecraftInstance;
import me.zero.alpine.listener.Subscriber;
import net.minecraft.client.Minecraft;
import org.apache.commons.lang3.Validate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Module implements Subscriber, MinecraftInstance {

    public final String name, description;
    public final Category category;
    public boolean enabled, hidden, frozen, expanded;
    public int key;

    public Module() {
        ModuleInfo info = getClass().getAnnotation(ModuleInfo.class);
        Validate.notNull(info, "CONFUSED ANNOTATION EXECUTION");
        this.name = info.name();
        this.description = info.description();
        this.category = info.category();
        this.key = info.key();
        this.hidden = info.hidden();
        this.frozen = info.frozen();
        this.expanded = false;
    }

    public String getSuffix() {
        return null;
    }

    public void onEnable() {
    }

    public void onDisable() {
    }

    public void setEnabled(boolean enabled) {
        if (frozen) {
            return;
        }

        this.enabled = enabled;
        if (this.enabled) {
            Return.BUS.subscribe(this);
            onEnable();
        } else {
            Return.BUS.unsubscribe(this);
            onDisable();
        }
    }

    public final List<Setting> settings = new ArrayList<>();

    protected void addSettings(Setting settings) {
        this.settings.add(settings);
    }

    protected void addSettings(Setting... settings) {
        this.settings.addAll(Arrays.asList(settings));
    }

    public List<Setting> getSettings() {
        return settings;
    }

    public Setting getSettingByName(String settingName) {
        for (Setting setting : settings) {
            if (setting.name.equals(settingName)) {
                return setting;
            }
        }
        return null;
    }

}

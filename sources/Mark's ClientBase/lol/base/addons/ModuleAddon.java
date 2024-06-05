package lol.base.addons;

import lol.base.BaseClient;
import lol.base.annotations.ModuleInfo;
import lol.main.IMinecraft;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ModuleAddon implements IMinecraft {

    public String name = this.getClass().getAnnotation(ModuleInfo.class).name();
    public String description = this.getClass().getAnnotation(ModuleInfo.class).description();
    public CategoryAddon category = this.getClass().getAnnotation(ModuleInfo.class).category();
    public int keyBind = this.getClass().getAnnotation(ModuleInfo.class).keyBind();
    public boolean enabled = this.getClass().getAnnotation(ModuleInfo.class).enabled();
    public boolean hidden = this.getClass().getAnnotation(ModuleInfo.class).hidden();

    public void onEnable() {}
    public void onDisable() {}
    public void onRender() {}

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;

        if (enabled) {
            onEnable();
            BaseClient.get().eventManager.subscribe(this);
        } else {
            onDisable();
            BaseClient.get().eventManager.unsubscribe(this);
        }
    }

    public String getSuffix() {
        return null;
    }

    // Settings -- we don't talk about the base
    public List<SettingAddon> settings = new ArrayList<>();

    public boolean hasSettings() {
        return !settings.isEmpty();
    }

    public void registerSettings(SettingAddon... settings) {
        this.settings.addAll(Arrays.asList(settings));
    }

}

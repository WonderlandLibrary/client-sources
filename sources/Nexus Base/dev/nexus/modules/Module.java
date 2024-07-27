package dev.nexus.modules;

import dev.nexus.Nexus;
import dev.nexus.modules.setting.Setting;
import net.minecraft.client.Minecraft;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Setter
@Getter
public abstract class Module {

    private final List<Setting> settings = new ArrayList<>();
    protected static Minecraft mc = Minecraft.getMinecraft();

    private String name;
    private String suffix = "";

    private boolean enabled;
    private boolean registered;

    private ModuleCategory category;

    private int key;

    public Module(String name, int key, ModuleCategory category) {
        this.name = name;
        this.key = key;
        this.category = category;
        enabled = false;
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
                    Nexus.INSTANCE.getEventManager().subscribe(this);
                    registered = true;
                }
            } else {
                if (registered) {
                    Nexus.INSTANCE.getEventManager().unsubscribe(this);
                    registered = false;
                }
                onDisable();
            }
        }
    }

    public boolean isNull() {
        return mc.thePlayer == null || mc.theWorld == null;
    }
}

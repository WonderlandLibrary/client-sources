package studio.dreamys.module;

import net.minecraftforge.common.MinecraftForge;
import studio.dreamys.near;
import studio.dreamys.setting.Setting;

import java.io.IOException;

public class Module {
    private final String name;
    private final Category category;
    private int key;
    private boolean toggled;

    public Module(String name, Category category) {
        this.name = name;
        key = 0;
        this.category = category;
        toggled = false;
    }

    public int getKey() {
        return key;
    }

    public void key(int key) {
        this.key = key;
        if (near.saveLoad != null) {
            near.saveLoad.save();
        }
    }
    
    public void set(Setting set) {
        near.settingsManager.rSetting(set);
    }

    public Setting getSetting(String name) {
        return near.settingsManager.getSettingByName(this, name);
    }

    public boolean isToggled() {
        return toggled;
    }

    public void setToggled(boolean toggled) throws IOException {
        this.toggled = toggled;

        if (this.toggled) {
            onEnable();
        } else {
            onDisable();
        }

        if (near.saveLoad != null) {
            near.saveLoad.save();
        }
    }

    public void toggle() throws IOException {
        toggled = !toggled;

        if (toggled) {
            onEnable();
        } else {
            onDisable();
        }

        if (near.saveLoad != null) {
            near.saveLoad.save();
        }
    }

    public void modeChanged() {

    }

    public void onEnable() throws IOException {
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void onDisable() {
        MinecraftForge.EVENT_BUS.unregister(this);
    }

    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }
}


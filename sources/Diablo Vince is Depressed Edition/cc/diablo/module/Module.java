/*
 * Decompiled with CFR 0.152.
 */
package cc.diablo.module;

import cc.diablo.Main;
import cc.diablo.helpers.module.ModuleData;
import cc.diablo.module.Category;
import cc.diablo.setting.Setting;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.client.Minecraft;

public class Module {
    public static Minecraft mc = Minecraft.getMinecraft();
    public String name;
    public String displayName;
    public String description;
    public int key;
    public Category category;
    public boolean toggled;
    public List<Setting> settingList = new ArrayList<Setting>();

    public Module() {
        this.name = this.getClass().getAnnotation(ModuleData.class).name();
        this.description = this.getClass().getAnnotation(ModuleData.class).description();
        this.key = this.getClass().getAnnotation(ModuleData.class).bind();
        this.category = this.getClass().getAnnotation(ModuleData.class).category();
    }

    public Module(String name, String description, int key, Category category) {
        this.name = name;
        this.description = description;
        this.key = key;
        this.category = category;
        this.toggled = false;
    }

    public void toggle() {
        boolean bl = this.toggled = !this.toggled;
        if (this.toggled) {
            this.onEnable();
        } else {
            this.onDisable();
        }
    }

    public String getDisplayName() {
        return this.displayName == null ? this.name : this.displayName;
    }

    public void addSettings(Setting ... settings) {
        this.settingList.addAll(Arrays.asList(settings));
    }

    public void onEnable() {
        Main.getInstance().getEventBus().register((Object)this);
    }

    public void onDisable() {
        Main.getInstance().getEventBus().unregister((Object)this);
    }

    public void setToggled(boolean bool) {
        this.toggled = bool;
        if (bool) {
            this.onEnable();
        } else {
            this.onDisable();
        }
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public int getKey() {
        return this.key;
    }

    public Category getCategory() {
        return this.category;
    }

    public boolean isToggled() {
        return this.toggled;
    }

    public List<Setting> getSettingList() {
        return this.settingList;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setSettingList(List<Setting> settingList) {
        this.settingList = settingList;
    }
}


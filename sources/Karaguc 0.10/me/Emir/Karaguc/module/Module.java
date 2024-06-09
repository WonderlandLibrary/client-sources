package me.Emir.Karaguc.module;

import me.Emir.Karaguc.Karaguc;
import net.minecraft.client.Minecraft;

public class Module {
    protected Minecraft mc = Minecraft.getMinecraft();

    private String name, displayName;
    private int key;
    private Category category;
    private boolean toggled;
    public boolean visible;

    public Module(String name, int key, Category category) {
        this.name = name;
        this.key = key;
        this.category = category;
        this.toggled = false;
        this.visible = true;
        setup();
    }

    public void onEnable() {
        Karaguc.instance.eventManager.register(this);
    }
    public void onDisable() {
        Karaguc.instance.eventManager.unregister(this);
    }
    public void onToggle() {}
    public void toggle() {
        toggled = !toggled;
        onToggle();
        if(toggled)
            onEnable();
        else
            onDisable();
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getKey() {
        return key;
    }
    public void setKey(int key) {
        this.key = key;
    }
    public Category getCategory() {
        return category;
    }
    public void setCategory(Category category) {
        this.category = category;
    }
    public boolean isToggled() {
        return toggled;
    }
    public String getDisplayName() {
        return displayName == null ? name : displayName;
    }
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
    public void setup() {}
}
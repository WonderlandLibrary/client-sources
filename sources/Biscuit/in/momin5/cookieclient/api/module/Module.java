package in.momin5.cookieclient.api.module;

import com.lukflug.panelstudio.settings.KeybindSetting;
import com.lukflug.panelstudio.settings.Toggleable;
import com.mojang.realmsclient.gui.ChatFormatting;
import in.momin5.cookieclient.CookieClient;
import in.momin5.cookieclient.api.event.events.EventRender;
import in.momin5.cookieclient.api.setting.Setting;
import in.momin5.cookieclient.api.setting.settings.SettingBoolean;
import in.momin5.cookieclient.api.setting.settings.SettingColor;
import in.momin5.cookieclient.api.setting.settings.SettingMode;
import in.momin5.cookieclient.api.setting.settings.SettingNumber;
import in.momin5.cookieclient.api.util.utils.player.MessageUtil;
import in.momin5.cookieclient.client.modules.client.Notifications;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

public class Module implements Toggleable, KeybindSetting{

    private String name, description;
    private Category category;
    private int bind;
    private boolean enabled;
    private boolean drawn = true;
    public ArrayList<Setting> settings = new ArrayList<Setting>();
    public static ArrayList<Module> modules;
    int priority;

    protected static Minecraft mc = Minecraft.getMinecraft();


    public Module(String name, String description, Category category, int priority) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.bind = Keyboard.KEY_NONE;
        this.enabled = false;
        this.priority = priority;
    }

    public Module(String name, String description, Category category) {
        this(name,description,category,0);
        this.enabled = false;
    }

    public Module(String name, Category category) {
        this(name, "", category,0);
        this.enabled = false;
    }

    public void onEnable() {
        MinecraftForge.EVENT_BUS.register(this);
        CookieClient.EVENT_BUS.subscribe(this);

        if(ModuleManager.getModuleByClass(Notifications.class).isEnabled() & !this.getName().equals("ClickGUI")){
            MessageUtil.sendNotifMessage(this.getName() + " has been " + ChatFormatting.GREEN + "enabled");
        }
    }
    public void onDisable() {
        MinecraftForge.EVENT_BUS.register(this);
        CookieClient.EVENT_BUS.subscribe(this);

        if(ModuleManager.getModuleByClass(Notifications.class).isEnabled() & !this.getName().equals("ClickGUI")){
            MessageUtil.sendNotifMessage(this.getName() + " has been " + ChatFormatting.RED + "disabled");
        }
    }

    public boolean nullCheck() {
        return (mc.player == null || mc.world == null);
    }

    public void onUpdate() {}
    public void onRender() {}
    public void onWorldRender(EventRender event) {}

    public void enable() {
        setEnabled(true);
    }
    public void disable() {
        setEnabled(false);
    }
    public void toggle() {
        if (enabled) {
            disable();
        } else {
            enable();
        }

        if(CookieClient.configSave != null) {
            CookieClient.configSave.save();
        }
    }

    public SettingBoolean register(SettingBoolean setting) {
        settings.add(setting);
        return setting;
    }
    public SettingMode register(SettingMode setting) {
        settings.add(setting);
        return setting;
    }
    public SettingNumber register(SettingNumber setting) {
        settings.add(setting);
        return setting;
    }
    public SettingColor register(SettingColor setting) {
        settings.add(setting);
        return setting;
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public Category getCategory() { return category; }
    public int getBind() { return bind; }
    public boolean isEnabled() { return enabled; }
    public boolean isDrawn() { return drawn; }
    public ArrayList<Setting> getSettings() { return settings; }

    public void setName(String name) { this.name = name; }
    public void setBind(int bind) {
        this.bind = bind;

        if(CookieClient.configSave != null) {
            CookieClient.configSave.save();
        }
    }

    public int getPriority() {
        return priority;
    }

    public int getKeybind() { return bind; }
    /*
     * DON'T USE! Doesn't call onEnable() and onDisable().
     */ // public void setEnabled(boolean enabled) { this.enabled = enabled; }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        if(this.enabled) {
            this.onEnable();
        }else {
            this.onDisable();
        }
        if(CookieClient.configSave != null) {
            CookieClient.configSave.save();
        }
    }

    public final boolean isOn() {
        return enabled;
    }
    public void setDrawn(boolean drawn) { this.drawn = drawn; }

    @Override
    public int getKey() {
        return this.getBind();
    }

    @Override
    public void setKey(int key) {
        setBind(key);
    }

    @Override
    public String getKeyName() {
        if (this.bind <= 0 || this.bind > 255) {
            return "NONE";
        } else {
            return Keyboard.getKeyName(this.bind);
        }
    }

}


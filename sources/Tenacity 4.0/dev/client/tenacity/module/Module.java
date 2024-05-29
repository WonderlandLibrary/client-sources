package dev.client.tenacity.module;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import dev.client.tenacity.Tenacity;
import dev.client.tenacity.config.ConfigSetting;
import dev.client.tenacity.module.impl.render.GlowESP;
import dev.client.tenacity.module.impl.render.NotificationsMod;
import dev.settings.Setting;
import dev.settings.impl.KeybindSetting;
import dev.client.tenacity.ui.notifications.NotificationManager;
import dev.client.tenacity.ui.notifications.NotificationType;
import dev.utils.Utils;
import dev.utils.animations.Animation;
import dev.utils.animations.Direction;
import dev.utils.animations.impl.DecelerateAnimation;
import dev.utils.misc.Multithreading;
import org.lwjgl.input.Keyboard;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

public class Module implements Utils {

    private final String description;
    private final Category category;
    private final CopyOnWriteArrayList<Setting> settingsList = new CopyOnWriteArrayList<>();

    private String suffix;

    @Expose
    @SerializedName("toggled")
    protected boolean toggled;
    @Expose
    @SerializedName("settings")
    public ConfigSetting[] cfgSettings;
    @Expose
    @SerializedName("name")
    private final String name;

    public boolean expanded;
    public final Animation animation = new DecelerateAnimation(250, 1);
    public static int categoryCount;
    public static float allowedClickGuiHeight = 300;

    private final KeybindSetting keybind = new KeybindSetting(Keyboard.KEY_NONE);

    public Module(String name, Category category, String description) {
        this.name = name;
        this.category = category;
        this.description = description;
        addSettings(keybind);
    }

    public boolean isInGame() {
        return mc.theWorld != null && mc.thePlayer != null;
    }

    public void addSettings(Setting... settings) {
        settingsList.addAll(Arrays.asList(settings));
    }

    public String getName() {
        return this.name;
    }

    public List<Setting> getSettingsList() {
        return settingsList;
    }

    public String getDescription() {
        return this.description;
    }

    public Category getCategory() {
        return this.category;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public boolean hasMode() {
        return suffix != null;
    }

    public boolean isToggled() {
        return this.toggled;
    }

    public void setToggled(boolean toggled) {
        this.toggled = toggled;
    }

    public void toggleSilent() {
        this.toggled = !this.toggled;
        if (this.toggled) {
            this.onEnable();
        } else {
            this.onDisable();
        }
    }

    public void toggle() {
        toggleSilent();
        if (NotificationsMod.toggleNotifications.isEnabled()) {
            if (toggled) {
                NotificationManager.post(NotificationType.SUCCESS, "Module toggled", this.getName() + " was " + "\u00A7aenabled\r");
            } else {
                NotificationManager.post(NotificationType.DISABLE, "Module toggled", this.getName() + " was " + "\u00A7cdisabled\r");
            }
        }
    }

    public void onEnable() {
        Tenacity.INSTANCE.getEventProtocol().register(this);
    }

    public void onDisable() {
        if (this instanceof GlowESP) {
            GlowESP.fadeIn.setDirection(Direction.BACKWARDS);
            Multithreading.schedule(() -> Tenacity.INSTANCE.getEventProtocol().unregister(this), 250, TimeUnit.MILLISECONDS);
        } else {
            Tenacity.INSTANCE.getEventProtocol().unregister(this);
        }
    }

    public KeybindSetting getKeybind() {
        return keybind;
    }

    public void setKey(int code) {
        this.keybind.setCode(code);
    }

}

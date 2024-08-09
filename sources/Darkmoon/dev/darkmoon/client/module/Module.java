package dev.darkmoon.client.module;

import com.darkmagician6.eventapi.EventManager;
import com.google.gson.JsonObject;
import com.mojang.realmsclient.gui.ChatFormatting;
import dev.darkmoon.client.DarkMoon;
import dev.darkmoon.client.module.impl.render.ClickGuiModule;
import dev.darkmoon.client.module.impl.util.ClientSound;
import dev.darkmoon.client.module.setting.Setting;
import dev.darkmoon.client.module.setting.impl.*;
import dev.darkmoon.client.manager.notification.NotificationManager;
import dev.darkmoon.client.manager.notification.NotificationType;

import dev.darkmoon.client.ui.csgui.CsGui;
import dev.darkmoon.client.utility.Utility;
import dev.darkmoon.client.utility.misc.ChatUtility;
import dev.darkmoon.client.utility.misc.SoundUtility;
import dev.darkmoon.client.utility.render.animation.Animation;
import dev.darkmoon.client.utility.render.animation.Direction;
import dev.darkmoon.client.utility.render.animation.impl.DecelerateAnimation;
import dev.darkmoon.client.utility.render.animation.impl.Translate;
import lombok.Getter;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static dev.darkmoon.client.module.impl.render.Notifications.debugSetting;
import static dev.darkmoon.client.module.impl.util.ClientSound.modeSetting;

public class Module implements Utility {
    protected ModuleAnnotation info = this.getClass().getAnnotation(ModuleAnnotation.class);
    public Translate translate = new Translate(0, 0);
    public boolean isRender = true;
    public float animYto;
    @Getter
    public String name;
    @Getter
    public Category category;
    @Getter
    public boolean enabled;
    @Getter
    public int bind;
    @Getter
    private final Animation animation = new DecelerateAnimation(250, 1, Direction.BACKWARDS);
    @Getter
    private String displayName;

    public Module() {
        name = info.name();
        displayName = name;
        category = info.category();
        enabled = false;
        bind = 0;
    }

    public boolean isSearched() {
        return CsGui.search.getText().isEmpty() || name.toLowerCase().toLowerCase().contains(CsGui.search.getText());
    }

    public void setToggled(boolean state) {
        if (state) {
            this.onEnable();
        } else {
            this.onDisable();
        }
        this.enabled = state;
    }

    public void toggle() {
        this.enabled = !this.enabled;
        if (this.enabled) {
            this.onEnable();
        } else {
            this.onDisable();
        }
    }

    public int getMouseBind() {
        return this.bind + 100;
    }

    public void onEnable() {
        EventManager.register(this);
        if (!(this instanceof ClickGuiModule)) {
            NotificationManager.notify(NotificationType.INFO, "Module", name + ChatFormatting.GREEN + " was enabled." + ChatFormatting.RESET, 1000);
            float volume = ClientSound.volume.get();
            if (DarkMoon.getInstance().getModuleManager().getModule(ClientSound.class).isEnabled()) {
 if (modeSetting.is("Client")) {
                    SoundUtility.playSound("nurik_enable.wav", volume);
                } else if (modeSetting.is("Sigma")) {
                    SoundUtility.playSound("sigma_enable.wav", volume);
                }
            }
            if (debugSetting.state && mc.player != null) {
        //        ChatUtility.addChatMessage("[DEBUG] " + name + " toggled.");
                mc.player.sendMessage(new TextComponentString(TextFormatting.GRAY + "[DEBUG] >> " + TextFormatting.RESET + TextFormatting.BOLD + name + TextFormatting.GRAY + " ->> " + TextFormatting.DARK_GREEN
                        + "toggled."));
            }
        }
    }

    public void onDisable() {
        EventManager.unregister(this);
        if (!(this instanceof ClickGuiModule)) {
            NotificationManager.notify(NotificationType.INFO, "Module", name + ChatFormatting.RED + " was disabled." + ChatFormatting.RESET, 1000);
            float volume = ClientSound.volume.get();
            if (DarkMoon.getInstance().getModuleManager().getModule(ClientSound.class).isEnabled()) {
               if (modeSetting.is("Client")) {
                    SoundUtility.playSound("nurik_disable.wav", volume);
                } else if (modeSetting.is("Sigma")) {
                    SoundUtility.playSound("sigma_disable.wav", volume);
                }
            }
            if (debugSetting.state && mc.player != null) {
                mc.player.sendMessage(new TextComponentString(TextFormatting.GRAY + "[DEBUG] >> " + TextFormatting.RESET + TextFormatting.BOLD + name + TextFormatting.GRAY + " ->> " + TextFormatting.DARK_RED
                        + "disabled."));
                //     ChatUtility.addChatMessage("[DEBUG] " + name + " disabled.");
            }
        }
    }
    public List<Setting> getSettings() {
        return Arrays.stream(this.getClass().getDeclaredFields()).map(field -> {
            try {
                field.setAccessible(true);
                return field.get(this);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            return null;
        }).filter(field -> field instanceof Setting).map(field -> (Setting) field).collect(Collectors.toList());
    }
    public void setSuffix(String suffix) {
        this.displayName = name + (suffix.length() > 0 ? " " + ChatFormatting.GRAY + suffix : "");
    }

    public String getDisplayName() {
        return displayName;
    }
    public JsonObject save() {
        JsonObject object = new JsonObject();
        object.addProperty("enabled", this.enabled);
        object.addProperty("bind", this.bind);
        JsonObject propertiesObject = new JsonObject();
        for (Setting setting : getSettings()) {
            if (setting instanceof BooleanSetting) {
                propertiesObject.addProperty(setting.getName(), ((BooleanSetting) setting).get());
            } else if (setting instanceof ModeSetting) {
                propertiesObject.addProperty(setting.getName(), ((ModeSetting) setting).get());
            } else if (setting instanceof NumberSetting) {
                propertiesObject.addProperty(setting.getName(), ((NumberSetting) setting).get());
            } else if (setting instanceof ColorSetting) {
                propertiesObject.addProperty(setting.getName(), ((ColorSetting) setting).get());
            } else if (setting instanceof MultiBooleanSetting) {
                StringBuilder builder = new StringBuilder();
                int i = 0;
                for (String s : ((MultiBooleanSetting) setting).values) {
                    if (((MultiBooleanSetting) setting).selectedValues.get(i))
                        builder.append(s).append("\n");
                    i++;
                }
                propertiesObject.addProperty(setting.getName(), builder.toString());

            }
        }
        object.add("Settings", propertiesObject);
        return object;
    }

    public void load(JsonObject object) {
        if (object != null) {
            if (object.has("enabled")) {
                this.setToggled(object.get("enabled").getAsBoolean());
            }

            if (object.has("bind")) {
                bind = (object.get("bind").getAsInt());
            }

            for (Setting setting : getSettings()) {
                JsonObject propertiesObject = object.getAsJsonObject("Settings");
                if (setting == null)
                    continue;
                if (propertiesObject == null)
                    continue;
                if (!propertiesObject.has(setting.getName()))
                    continue;
                if (setting instanceof BooleanSetting) {
                    ((BooleanSetting) setting).state = (propertiesObject.get(setting.getName()).getAsBoolean());
                } else if (setting instanceof ModeSetting) {
                    ((ModeSetting) setting).set(propertiesObject.get(setting.getName()).getAsString());
                } else if (setting instanceof NumberSetting) {
                    ((NumberSetting) setting).current = (propertiesObject.get(setting.getName()).getAsFloat());
                } else if (setting instanceof ColorSetting) {
                     ((ColorSetting) setting).setColor(propertiesObject.get(setting.getName()).getAsInt());
                } else if (setting instanceof MultiBooleanSetting) {
                    for (int i = 0; i < ((MultiBooleanSetting) setting).selectedValues.size(); i++) {
                        ((MultiBooleanSetting) setting).selectedValues.set(i, false);
                    }
                    int i = 0;
                    String[] strs = propertiesObject.get(setting.getName()).getAsString().split("\n");
                    for (String s : ((MultiBooleanSetting) setting).values) {
                        for (String str : strs) {
                            if (str.equalsIgnoreCase(s)) {
                                ((MultiBooleanSetting) setting).selectedValues.set(i, true);
                            }
                        }
                        i++;
                    }
                }
            }
        }
    }
}

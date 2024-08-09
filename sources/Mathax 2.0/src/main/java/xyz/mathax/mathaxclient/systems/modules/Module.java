package xyz.mathax.mathaxclient.systems.modules;

import xyz.mathax.mathaxclient.MatHax;
import xyz.mathax.mathaxclient.systems.themes.Theme;
import xyz.mathax.mathaxclient.gui.widgets.WWidget;
import xyz.mathax.mathaxclient.settings.Settings;
import xyz.mathax.mathaxclient.systems.config.Config;
import xyz.mathax.mathaxclient.utils.Utils;
import xyz.mathax.mathaxclient.utils.json.JSONUtils;
import xyz.mathax.mathaxclient.utils.misc.ISerializable;
import xyz.mathax.mathaxclient.utils.input.KeyBind;
import xyz.mathax.mathaxclient.utils.text.ChatUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.File;
import java.util.Objects;

public abstract class Module implements ISerializable<Module>, Comparable<Module> {
    protected final MinecraftClient mc;

    public final Category category;
    public final String name;
    public final String command;
    public final String description;

    public final Settings settings = new Settings();

    private boolean enabled;

    public boolean serialize = true;
    public boolean alwaysRun = false;
    public boolean autoSubscribe = true;

    public final KeyBind keybind = KeyBind.none();
    public boolean toggleOnBindRelease = false;
    public boolean chatFeedback = true;
    public boolean toasts = false;
    public boolean favorite = false;

    public Module(Category category, String name, String description, boolean alwaysRun) {
        this.mc = MinecraftClient.getInstance();
        this.category = category;
        this.name = name;
        this.command = Utils.nameToCommand(name);
        this.description = description;
        this.alwaysRun = alwaysRun;
    }

    public Module(Category category, String name, String description) {
        this.mc = MinecraftClient.getInstance();
        this.category = category;
        this.name = name;
        this.command = Utils.nameToCommand(name);
        this.description = description;
    }

    public WWidget getWidget(Theme theme) {
        return null;
    }

    public void onEnable() {}

    public void onDisable() {}

    public boolean isEnabled() {
        return enabled;
    }

    public void toggle() {
        if (!enabled) {
            enable();
        } else {
            disable();
        }
    }

    public void forceToggle(boolean enabled) {
        if (enabled && !this.enabled) {
            enable();
        } else if (!enabled && this.enabled) {
            disable();
        }
    }

    public void enable() {
        if (!enabled) {
            enabled = true;
            Modules.get().addEnabled(this);

            if (alwaysRun || Utils.canUpdate()) {
                if (autoSubscribe) {
                    MatHax.EVENT_BUS.subscribe(this);
                }

                onEnable();
            }
        }
    }

    public void disable() {
        if (enabled) {
            if (alwaysRun || Utils.canUpdate()) {
                if (autoSubscribe) {
                    MatHax.EVENT_BUS.unsubscribe(this);
                }

                onDisable();
            }

            enabled = false;
            Modules.get().removeEnabled(this);
        }
    }

    public void sendToggled() {
        if (Config.get().chatFeedbackSetting.get() && chatFeedback) {
            ChatUtils.sendMessage(this.hashCode(), Formatting.GRAY, "Toggled (highlight)%s(default) %s(default).", name, isEnabled() ? Formatting.GREEN + "on" : Formatting.RED + "off");
        }

        //TODO
        /*if (Config.get().toastSetting.get() && toasts) {

        }*/
    }

    public void sendBound() {
        if (Config.get().chatFeedbackSetting.get() && chatFeedback) {
            info("Bound to (highlight)%s(default).", keybind);
        }

        //TODO
        /*if (Config.get().toastSetting.get() && toasts) {

        }*/
    }

    public void info(Text message) {
        ChatUtils.sendMessage(name, message);
    }

    public void info(String message, Object... args) {
        ChatUtils.info(name, message, args);
    }

    public void warning(String message, Object... args) {
        ChatUtils.warning(name, message, args);
    }

    public void error(String message, Object... args) {
        ChatUtils.error(name, message, args);
    }

    public String getInfoString() {
        return null;
    }

    public void save(File folder) {
        JSONObject json = toJson();
        if (json == null) {
            return;
        }

        File file = new File(folder, name + ".json");
        JSONUtils.saveJSON(json, file);
    }

    @Override
    public JSONObject toJson() {
        if (!serialize) {
            return null;
        }

        JSONObject json = new JSONObject();
        json.put("enabled", enabled);
        json.put("keybind", keybind.toJson());
        json.put("toggle-on-bind-release", toggleOnBindRelease);
        json.put("chat-feedback", chatFeedback);
        json.put("toasts", toasts);
        json.put("favorite", favorite);
        json.put("settings", settings.toJson());
        return json;
    }

    public void load(File folder) {
        File file = new File(folder, name + ".json");
        JSONObject json = JSONUtils.loadJSON(file);
        if (json == null) {
            return;
        }

        fromJson(json);
    }

    @Override
    public Module fromJson(JSONObject json) {
        settings.reset();

        if (json.has("settings")) {
            settings.fromJson(json.getJSONObject("settings"));
        }

        if (json.has("is-key")) {
            keybind.set(true, json.getInt("is-key"));
        } else if (json.has("keybind")) {
            keybind.fromJson(json.getJSONObject("keybind"));
        }

        toggleOnBindRelease = json.has("toggle-on-bind-release") && json.getBoolean("toggle-on-bind-release");
        chatFeedback = json.has("chat-feedback") && json.getBoolean("chat-feedback");
        toasts = json.has("toasts") && json.getBoolean("toasts");
        favorite = json.has("favorite") && json.getBoolean("favorite");

        boolean enabled = json.has("enabled") && json.getBoolean("enabled");
        if (enabled != isEnabled()) {
            toggle();
        }

        return this;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        Module module = (Module) object;
        return Objects.equals(name, module.name);
    }

    @Override
    public int compareTo(@NotNull Module module) {
        return name.compareTo(module.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

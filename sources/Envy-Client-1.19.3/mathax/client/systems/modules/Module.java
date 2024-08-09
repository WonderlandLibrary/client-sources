package mathax.client.systems.modules;

import mathax.client.MatHax;
import mathax.client.gui.GuiTheme;
import mathax.client.gui.widgets.WWidget;
import mathax.client.settings.Settings;
import mathax.client.systems.config.Config;
import mathax.client.utils.Utils;
import mathax.client.utils.misc.ChatUtils;
import mathax.client.utils.misc.ISerializable;
import mathax.client.utils.misc.KeyBind;
import mathax.client.utils.render.ToastSystem;
import mathax.client.utils.render.color.Color;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Item;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public abstract class Module implements ISerializable<Module>, Comparable<Module> {
    protected static MinecraftClient mc = null;


    public final Category category;
    public final Item icon;
    public final String name, title, description;
    public final Color color;

    public final Settings settings = new Settings();

    protected boolean active;
    private static boolean sactive;
    private boolean toggleMessage = true;
    private boolean toggleToast = false;
    public boolean favorite = false;
    private boolean visible = true;

    public boolean serialize = true;
    public boolean runInMainMenu = false;
    public boolean autoSubscribe = true;

    public final KeyBind keybind = KeyBind.none();
    private String displayName;

    public void setDisplayName(String name) {
        this.displayName = name;
    }

    public boolean toggleOnBindRelease = false;

    public Module(Category category, Item icon, String name, String description) {
        this.mc = MinecraftClient.getInstance();
        this.category = category;
        this.icon = icon;
        this.name = name;
        this.title = Utils.nameToTitle(name);
        this.description = description;
        this.color = new Color(Utils.random(0, 255), Utils.random(0, 255), Utils.random(0, 255));
    }

    public Module(Category category, Item icon, String name, String description, boolean runInMainMenu) {
        this.mc = MinecraftClient.getInstance();
        this.category = category;
        this.icon = icon;
        this.name = name;
        this.title = Utils.nameToTitle(name);
        this.description = description;
        this.runInMainMenu = runInMainMenu;
        this.color = new Color(Utils.random(0, 255), Utils.random(0, 255), Utils.random(0, 255));
    }

    public WWidget getWidget(GuiTheme theme) {
        return null;
    }

    public boolean onActivate() {
        return false;
    }

    public double onActivate(double value) {
        return value;
    }
    public void onActivateCombat() {}

    public void onActivateDefault() {}
    public void onDeactivate() {}
    public void onDeactivateCombat() {}

    public void onActivateServer() {}
    public void onDeactivateServer() {}

    public void toggle() {
        if (!active) {
            active = true;
            Modules.get().addActive(this);

            settings.onActivated();

            if (runInMainMenu || Utils.canUpdate()) {
                if (autoSubscribe) MatHax.EVENT_BUS.subscribe(this);
                onActivate();
                onActivateServer();
                onActivateCombat();
                onDeactivateCombat();
            }
        } else {
            if (runInMainMenu || Utils.canUpdate()) {
                if (autoSubscribe) MatHax.EVENT_BUS.unsubscribe(this);
                onDeactivate();
                onDeactivateServer();
                onDeactivateCombat();
            }

            active = false;
            Modules.get().removeActive(this);
        }
    }

    public void forceToggle(boolean toggle) {
        active = toggle;

        if (toggle) {
            Modules.get().addActive(this);

            settings.onActivated();

            if (runInMainMenu || Utils.canUpdate()) {
                if (autoSubscribe) MatHax.EVENT_BUS.subscribe(this);
                onActivate();
                onActivateServer();
                onDeactivateCombat();
            }
        } else {
            if (runInMainMenu || Utils.canUpdate()) {
                if (autoSubscribe) MatHax.EVENT_BUS.unsubscribe(this);
                onDeactivate();
                onDeactivateServer();
            }

            Modules.get().removeActive(this);
        }
    }

    public void sendToggledMsg(String name, Module module) {
        if (!module.isMessageEnabled()) return;
        if (Config.get().chatFeedback.get()) ChatUtils.sendMsg(this.hashCode(), Formatting.GRAY, "Toggled (highlight)%s(default) %s(default).", title, getOnOff(module));
    }

    public void sendToggledToast(String name, Module module) {
        if (!module.isToastEnabled()) return;
        mc.getToastManager().add(new ToastSystem(module.icon, module.category.color, title, null, Formatting.GRAY + "Toggled " + getOnOff(module) + Formatting.GRAY + ".", Config.get().toastDuration.get()));
    }

    private String getOnOff(Module module) {
        return module.active ? Formatting.GREEN + "on" : Formatting.RED + "off";
    }

    public void info(Text message) {
        ChatUtils.sendMsg(title, message);
    }

    public void info(String message, Object... args) {
        ChatUtils.info(title, message, args);
    }

    public void warning(String message, Object... args) {
        ChatUtils.warning(title, message, args);
    }

    public void error(String message, Object... args) {
        ChatUtils.error(title, message, args);
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setToggleMessage(boolean toggleMessage) {
        this.toggleMessage = toggleMessage;
    }

    public boolean isMessageEnabled() {
        return toggleMessage;
    }

    public void setToggleToast(boolean toggleToast) {
        this.toggleToast = toggleToast;
    }

    public boolean isToastEnabled() {
        return toggleToast;
    }

    public boolean isActive() {
        return active;
    }

    public static boolean StatIsActive() {
        return sactive;
    }

    public String getInfoString() {
        return null;
    }

    @Override
    public NbtCompound toTag() {
        if (!serialize) return null;
        NbtCompound tag = new NbtCompound();

        tag.putString("name", name);
        tag.put("keybind", keybind.toTag());
        tag.putBoolean("toggleOnKeyRelease", toggleOnBindRelease);
        tag.put("settings", settings.toTag());

        tag.putBoolean("toggleMessage", toggleMessage);
        tag.putBoolean("toggleToast", toggleToast);
        tag.putBoolean("favorite", favorite);
        tag.putBoolean("active", active);
        tag.putBoolean("visible", visible);

        return tag;
    }

    @Override
    public Module fromTag(NbtCompound tag) {
        if (tag.contains("key")) keybind.set(true, tag.getInt("key"));
        else keybind.fromTag(tag.getCompound("keybind"));

        toggleOnBindRelease = tag.getBoolean("toggleOnKeyRelease");

        NbtElement settingsTag = tag.get("settings");
        if (settingsTag instanceof NbtCompound) settings.fromTag((NbtCompound) settingsTag);

        toggleMessage = tag.getBoolean("toggleMessage");
        toggleToast = tag.getBoolean("toggleToast");
        favorite = tag.getBoolean("favorite");
        boolean active = tag.getBoolean("active");
        if (active != isActive()) toggle();
        visible = tag.getBoolean("visible");

        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Module module = (Module) o;
        return Objects.equals(name, module.name);
    }

    @Override
    public int compareTo(@NotNull Module o) {
        return name.compareTo(o.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

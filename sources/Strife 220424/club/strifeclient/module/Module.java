package club.strifeclient.module;

import club.strifeclient.Client;
import club.strifeclient.setting.Setting;
import club.strifeclient.util.callback.VariableCallback;
import club.strifeclient.util.misc.MinecraftUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@Getter
public abstract class Module extends MinecraftUtil {

    protected final List<Setting<?>> settings;
    protected String[] aliases;
    protected final String name, renderName, description;
    protected Supplier<Object> suffix;
    protected Category category;
    @Setter
    protected int keybind;
    @Setter
    protected boolean enabled, hidden;
    private List<VariableCallback<Boolean>> toggleCallbacks;

    public Module() {
        if(getClass().isAnnotationPresent(ModuleInfo.class)) {
            ModuleInfo moduleInfo = getClass().getAnnotation(ModuleInfo.class);
            name = moduleInfo.name();
            if(moduleInfo.renderName().isEmpty())
                renderName = name;
            else renderName = moduleInfo.renderName();
            description = moduleInfo.description();
            category = moduleInfo.category();
            keybind = moduleInfo.keybind();
            aliases = moduleInfo.aliases();
            settings = new ArrayList<>();
            toggleCallbacks = new ArrayList<>();
        } else throw new Error("Failed to initialize module, missing ModuleInfo.");
    }

    protected void init() {}

    public void addToggleCallback(VariableCallback<Boolean> toggleCallback) {
        if(!toggleCallbacks.contains(toggleCallback))
            toggleCallbacks.add(toggleCallback);
    }

    public void setEnabled(boolean enabled) {
        if(this.enabled == enabled) return;
        this.enabled = enabled;
        if(enabled) onEnable();
        else onDisable();
        toggleCallbacks.forEach(callback -> callback.callback(enabled));
    }

    @SuppressWarnings("unchecked")
    public <T extends Setting<?>> T getSettingByName(String name) {
        return (T) settings.stream().filter(setting -> setting.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    protected void onEnable() {
        Client.INSTANCE.getEventBus().register(this);
    }

    protected void onDisable() {
        Client.INSTANCE.getEventBus().unregister(this);
    }

}

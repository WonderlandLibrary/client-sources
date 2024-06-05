package club.mega.module.setting;

import club.mega.interfaces.MinecraftInterface;
import club.mega.module.Module;

import java.util.function.Supplier;

public class Setting implements MinecraftInterface {

    private final String name;
    private final boolean configurable;
    private final Supplier<Boolean> visible;

    public Setting(final String name, final Module parent, final boolean configurable, final Supplier<Boolean> visible) {
        this.name = name;
        this.configurable = configurable;
        this.visible = visible;
        parent.getSettings().add(this);
    }

    public final String getName() {
        return name;
    }

    public final boolean isVisible() {
        return visible.get();
    }

    public final boolean isConfigurable() {
        return configurable;
    }
}

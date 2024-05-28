package dev.vertic.setting;

import dev.vertic.event.impl.input.SettingUpdateEvent;
import lombok.Getter;

import java.util.function.BooleanSupplier;

@Getter
public class Setting {

    private final String name;
    private BooleanSupplier visibility = () -> true;

    public Setting(final String name) {
        this.name = name;
    }

    public Setting(final String name, final BooleanSupplier visibility) {
        this.name = name;
        this.visibility = visibility;
    }

    public boolean isShown() {
        return visibility.getAsBoolean();
    }

}

package lol.point.returnclient.settings;

import lombok.Getter;

import java.util.function.BooleanSupplier;

public class Setting {
    public String name;

    @Getter
    private BooleanSupplier hidden = () -> false;

    public <T extends Setting> T hideSetting(BooleanSupplier hidden) {
        this.hidden = hidden;
        return (T) this;
    }
}

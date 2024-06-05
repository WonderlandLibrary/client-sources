package lol.base.addons;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Objects;
import java.util.function.BooleanSupplier;

public class SettingAddon {

    public TypeAddon type;
    public String name;
    protected ModuleAddon parent;

    @Getter
    private BooleanSupplier hidden = () -> false;

    public SettingAddon getSetting(String input, ModuleAddon parent) {
        return Objects.requireNonNull(getSettingsByModule(parent)).stream().filter(settingFeature -> name.equalsIgnoreCase(input)).findFirst().get();
    }

    public static ArrayList<SettingAddon> getSettingsByModule(ModuleAddon module) {
        ArrayList<SettingAddon> settingArrayList = new ArrayList<>();

        for (SettingAddon setting : module.settings) {
            if (setting.parent.equals(module)) {
                settingArrayList.add(setting);
            }
        }

        return settingArrayList.isEmpty() ? null : settingArrayList;
    }

    public <T extends SettingAddon> T setHidden(BooleanSupplier hidden) {
        this.hidden = hidden;
        return (T) this;
    }

}

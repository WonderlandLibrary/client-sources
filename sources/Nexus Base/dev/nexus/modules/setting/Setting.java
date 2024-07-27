package dev.nexus.modules.setting;

import lombok.Getter;
import lombok.Setter;

public abstract class Setting {
    @Setter
    @Getter
    private String name;
    private BooleanSetting dependencyBoolSetting;
    @Getter
    private Boolean dependencyBool;
    private ModeSetting dependencyModeSetting;
    private String dependencyMode;

    public Setting(String name) {
        this.name = name;
        this.dependencyBoolSetting = null;
        this.dependencyBool = null;
        this.dependencyModeSetting = null;
        this.dependencyMode = null;
    }

    public Setting getDependencyBoolSetting() {
        return dependencyBoolSetting;
    }

    public Setting dependencyModeSetting() {
        return dependencyModeSetting;
    }

    public String dependencyMode() {
        return dependencyMode;
    }

    public void setDependency(BooleanSetting dependencyBoolSetting, boolean dependencyBool) {
        this.dependencyBoolSetting = dependencyBoolSetting;
        this.dependencyBool = dependencyBool;
    }

    public void setDependency(ModeSetting dependencyModeSetting, String dependencyMode) {
        this.dependencyModeSetting = dependencyModeSetting;
        this.dependencyMode = dependencyMode;
    }
}

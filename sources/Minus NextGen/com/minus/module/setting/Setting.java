package com.minus.module.setting;

public abstract class Setting {
    private String name;
    private BooleanSetting dependencyBoolSetting;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Setting getDependencyBoolSetting() {
        return dependencyBoolSetting;
    }

    public Boolean getDependencyBool() {
        return dependencyBool;
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

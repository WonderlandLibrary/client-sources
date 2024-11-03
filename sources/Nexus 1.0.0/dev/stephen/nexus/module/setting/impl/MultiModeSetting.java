package dev.stephen.nexus.module.setting.impl;

import dev.stephen.nexus.module.setting.Setting;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SuppressWarnings("all")
public class MultiModeSetting extends Setting {

    private final List<String> modes;
    private Set<String> selectedModes;

    public MultiModeSetting(String name, String... modes) {
        super(name);
        this.modes = Arrays.asList(modes);
        this.selectedModes = new HashSet<>();
    }

    public Set<String> getSelectedModes() {
        return new HashSet<>(selectedModes);
    }

    public void selectMode(String mode) {
        if (modes.contains(mode)) {
            selectedModes.add(mode);
        }
    }

    public void deselectMode(String mode) {
        selectedModes.remove(mode);
    }

    public boolean isModeSelected(String mode) {
        return selectedModes.contains(mode);
    }

    public List<String> getModes() {
        return modes;
    }

    public void setSelsectedModes(Set<String> selectedModes) {
        this.selectedModes = selectedModes;
    }
}

package dev.stephen.nexus.module.setting.impl.newmodesetting;

import dev.stephen.nexus.module.setting.Setting;

import lombok.Getter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class NewModeSetting extends Setting {
    public int index;
    private final String defaultMode;
    private final List<String> modeNames;
    private final Map<String, SubMode<?>> modesMap;

    public NewModeSetting(String name, String defaultMode, SubMode<?>... modes) {
        super(name);
        this.defaultMode = defaultMode;
        this.modeNames = Arrays.asList(Arrays.stream(modes).map(SubMode::getName).toArray(String[]::new));
        this.modesMap = new HashMap<>();

        for (SubMode<?> subMode : modes) {
            modesMap.put(subMode.getName(), subMode);
        }

        this.index = modeNames.indexOf(defaultMode);
    }

    public SubMode<?> getCurrentMode() {
        return modesMap.get(modeNames.get(index));
    }

    public void setMode(String modeName) {
        if (modesMap.containsKey(modeName)) {
            boolean cook = false;
            if (getCurrentMode().getRegistered()) {
                getCurrentMode().onDisable();
                cook = true;
            }
            index = modeNames.indexOf(modeName);
            if (cook) {
                getCurrentMode().onEnable();
            }
        }
    }

    public void cycle() {
        boolean cook = false;
        if (getCurrentMode().getRegistered()) {
            getCurrentMode().onDisable();
            cook = true;
        }
        index = (index < modeNames.size() - 1) ? index + 1 : 0;
        if (cook) {
            getCurrentMode().onEnable();
        }
    }

    public boolean isMode(String modeName) {
        return modeNames.get(index).equals(modeName);
    }
}

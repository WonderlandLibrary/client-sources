package club.strifeclient.setting.implementations;

import club.strifeclient.setting.Setting;
import club.strifeclient.util.system.StringUtil;
import lombok.Getter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@Getter
public class MultiSelectSetting<T extends Enum<?>> extends Setting<Map<Enum<?>, Boolean>> {

    private final Enum<?> defaultValue;

    public MultiSelectSetting(String name, Enum<?> defaultValue) {
        this(name, defaultValue, () -> true);
    }

    public MultiSelectSetting(String name, Enum<?> defaultValue, Supplier<Boolean> dependency) {
        super(name, new HashMap<>(), dependency);
        this.defaultValue = defaultValue;
        Arrays.stream(defaultValue.getClass().getEnumConstants()).forEach(constant -> value.put(constant, false));
        value.put(defaultValue, true);
    }

    public boolean isSelected(T setting) {
        return value.get(setting);
    }

    public boolean isSelected() {
        return value.containsValue(true);
    }

    public void setSelected(Enum<?> setting, boolean selected) {
        value.put(setting, selected);
    }

    @Override
    public void parse(Object original) {
        if (original instanceof Map) {
            Map<String, Boolean> originalMap = (Map<String, Boolean>) original;
            Map<Enum<?>, Boolean> updateMap = new HashMap<>();
            originalMap.forEach((key, value) -> {
                final T type = (T) StringUtil.getEnumPrimitiveFromString(defaultValue.getClass().getEnumConstants(), key);
                if (type != null) {
                    updateMap.put(type, value);
                }
            });
            setValue(updateMap);
        }
    }

    @Override
    public String toString() {
        return defaultValue.toString();
    }
}

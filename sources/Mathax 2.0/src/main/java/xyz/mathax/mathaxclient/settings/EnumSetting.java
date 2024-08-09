package xyz.mathax.mathaxclient.settings;

import xyz.mathax.mathaxclient.utils.settings.IVisible;
import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class EnumSetting<T extends Enum<?>> extends Setting<T> {
    private T[] values;

    private final List<String> suggestions;

    public EnumSetting(String name, String description, T defaultValue, Consumer<T> onChanged, Consumer<Setting<T>> onModuleEnabled, IVisible visible) {
        super(name, description, defaultValue, onChanged, onModuleEnabled, visible);

        try {
            values = (T[]) defaultValue.getClass().getMethod("values").invoke(null);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException exception) {
            exception.printStackTrace();
        }

        suggestions = new ArrayList<>(values.length);
        for (T value : values) {
            suggestions.add(value.toString());
        }
    }

    @Override
    protected T parseImpl(String string) {
        for (T possibleValue : values) {
            if (string.equalsIgnoreCase(possibleValue.toString())) {
                return possibleValue;
            }
        }

        return null;
    }

    @Override
    protected boolean isValueValid(T value) {
        return true;
    }

    @Override
    public List<String> getSuggestions() {
        return suggestions;
    }

    @Override
    public JSONObject save(JSONObject json) {
        json.put("value", get().toString());

        return json;
    }

    @Override
    public T load(JSONObject json) {
        parse(json.getString("value"));

        return get();
    }

    public static class Builder<T extends Enum<?>> extends SettingBuilder<Builder<T>, T, EnumSetting<T>> {
        public Builder() {
            super(null);
        }

        @Override
        public EnumSetting<T> build() {
            return new EnumSetting<>(name, description, defaultValue, onChanged, onModuleEnabled, visible);
        }
    }
}
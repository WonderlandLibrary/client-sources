package xyz.mathax.mathaxclient.settings;

import com.google.common.collect.ImmutableList;
import xyz.mathax.mathaxclient.utils.settings.IVisible;
import org.json.JSONObject;

import java.util.List;
import java.util.function.Consumer;

public class BoolSetting extends Setting<Boolean> {
    private static final List<String> SUGGESTIONS = ImmutableList.of("true", "false", "toggle");

    private BoolSetting(String name, String description, Boolean defaultValue, Consumer<Boolean> onChanged, Consumer<Setting<Boolean>> onModuleEnabled, IVisible visible) {
        super(name, description, defaultValue, onChanged, onModuleEnabled, visible);
    }

    @Override
    protected Boolean parseImpl(String string) {
        if (string.equalsIgnoreCase("true") || string.equalsIgnoreCase("1")) {
            return true;
        } else if (string.equalsIgnoreCase("false") || string.equalsIgnoreCase("0")) {
            return false;
        } else if (string.equalsIgnoreCase("toggle")) {
            return !get();
        }

        return null;
    }

    @Override
    protected boolean isValueValid(Boolean value) {
        return true;
    }

    @Override
    public List<String> getSuggestions() {
        return SUGGESTIONS;
    }

    @Override
    public JSONObject save(JSONObject json) {
        json.put("value", get());

        return json;
    }

    @Override
    public Boolean load(JSONObject json) {
        set(json.getBoolean("value"));

        return get();
    }

    public static class Builder extends SettingBuilder<Builder, Boolean, BoolSetting> {
        public Builder() {
            super(false);
        }

        @Override
        public BoolSetting build() {
            return new BoolSetting(name, description, defaultValue, onChanged, onModuleEnabled, visible);
        }
    }
}

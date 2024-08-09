package xyz.mathax.mathaxclient.settings;

import com.google.common.collect.ImmutableList;
import xyz.mathax.mathaxclient.utils.json.JSONUtils;
import xyz.mathax.mathaxclient.utils.render.color.Color;
import xyz.mathax.mathaxclient.utils.render.color.SettingColor;
import xyz.mathax.mathaxclient.utils.settings.IVisible;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.function.Consumer;

public class ColorSetting extends Setting<SettingColor> {
    private static final List<String> SUGGESTIONS = ImmutableList.of("0 0 0 255", "225 25 25 255", "25 225 25 255", "25 25 225 255", "255 255 255 255");

    public ColorSetting(String name, String description, SettingColor defaultValue, Consumer<SettingColor> onChanged, Consumer<Setting<SettingColor>> onModuleEnabled, IVisible visible) {
        super(name, description, defaultValue, onChanged, onModuleEnabled, visible);
    }

    @Override
    protected SettingColor parseImpl(String string) {
        try {
            String[] strings = string.split(" ");
            return new SettingColor(Integer.parseInt(strings[0]), Integer.parseInt(strings[1]), Integer.parseInt(strings[2]), Integer.parseInt(strings[3]));
        } catch (IndexOutOfBoundsException | NumberFormatException ignored) {
            return null;
        }
    }

    @Override
    public void resetImpl() {
        if (value == null) {
            value = new SettingColor(defaultValue);
        }

        else value.set(defaultValue);
    }

    @Override
    protected boolean isValueValid(SettingColor value) {
        value.validate();

        return true;
    }

    @Override
    public List<String> getSuggestions() {
        return SUGGESTIONS;
    }

    @Override
    protected JSONObject save(JSONObject json) {
        json.put("value", new int[] {get().r, get().g, get().b, get().a});
        json.put("rainbow", get().rainbow);

        return json;
    }

    @Override
    public SettingColor load(JSONObject json) {
        if (json.has("value") && JSONUtils.isValidJSONArray(json, "value")) {
            JSONArray value = json.getJSONArray("value");
            get().set(new SettingColor(value.getInt(0), value.getInt(1), value.getInt(2), value.getInt(3), json.getBoolean("rainbow")));
        }

        return get();
    }

    public static class Builder extends SettingBuilder<Builder, SettingColor, ColorSetting> {
        public Builder() {
            super(new SettingColor());
        }

        @Override
        public ColorSetting build() {
            return new ColorSetting(name, description, defaultValue, onChanged, onModuleEnabled, visible);
        }

        @Override
        public Builder defaultValue(SettingColor defaultValue) {
            this.defaultValue.set(defaultValue);
            return this;
        }

        public Builder defaultValue(Color defaultValue) {
            this.defaultValue.set(defaultValue);
            return this;
        }
    }
}
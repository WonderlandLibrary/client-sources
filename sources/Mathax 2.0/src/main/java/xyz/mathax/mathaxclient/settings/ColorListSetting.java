package xyz.mathax.mathaxclient.settings;

import xyz.mathax.mathaxclient.utils.json.JSONUtils;
import xyz.mathax.mathaxclient.utils.render.color.SettingColor;
import xyz.mathax.mathaxclient.utils.settings.IVisible;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ColorListSetting extends Setting<List<SettingColor>> {
    public ColorListSetting(String name, String description, List<SettingColor> defaultValue, Consumer<List<SettingColor>> onChanged, Consumer<Setting<List<SettingColor>>> onModuleEnabled, IVisible visible) {
        super(name, description, defaultValue, onChanged, onModuleEnabled, visible);
    }

    @Override
    protected List<SettingColor> parseImpl(String string) {
        // TODO: I cba to write a text parser for this
        return new ArrayList<>();
    }

    @Override
    protected boolean isValueValid(List<SettingColor> value) {
        return true;
    }

    @Override
    protected void resetImpl() {
        value = new ArrayList<>(defaultValue.size());

        for (SettingColor settingColor : defaultValue) {
            value.add(new SettingColor(settingColor));
        }
    }

    @Override
    protected JSONObject save(JSONObject json) {
        json.put("value", new JSONArray());
        get().forEach(value -> json.append("value", value.toJson()));

        return json;
    }

    @Override
    protected List<SettingColor> load(JSONObject json) {
        get().clear();

        if (json.has("value") && JSONUtils.isValidJSONArray(json, "value")) {
            for (Object object : json.getJSONArray("value")) {
                if (object instanceof JSONObject valueJson) {
                    get().add(new SettingColor(valueJson));
                }
            }
        }

        return get();
    }

    public static class Builder extends SettingBuilder<Builder, List<SettingColor>, ColorListSetting> {
        public Builder() {
            super(new ArrayList<>());
        }

        @Override
        public ColorListSetting build() {
            return new ColorListSetting(name, description, defaultValue, onChanged, onModuleEnabled, visible);
        }
    }
}

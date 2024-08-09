package xyz.mathax.mathaxclient.settings;

import xyz.mathax.mathaxclient.utils.json.JSONUtils;
import xyz.mathax.mathaxclient.utils.misc.ISerializable;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SettingGroup implements ISerializable<SettingGroup>, Iterable<Setting<?>> {
    public final String name;

    public boolean sectionExpanded;

    final List<Setting<?>> settings = new ArrayList<>(1);

    SettingGroup(String name, boolean sectionExpanded) {
        this.name = name;
        this.sectionExpanded = sectionExpanded;
    }

    public Setting<?> get(String name) {
        for (Setting<?> setting : this) {
            if (setting.name.equals(name)) {
                return setting;
            }
        }

        return null;
    }

    public <T> Setting<T> add(Setting<T> setting) {
        settings.add(setting);

        return setting;
    }

    public Setting<?> getByIndex(int index) {
        return settings.get(index);
    }

    @Override
    public Iterator<Setting<?>> iterator() {
        return settings.iterator();
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("section-expanded", sectionExpanded);

        json.put("settings", new JSONArray());
        for (Setting<?> setting : this) {
            json.append("settings", setting.toJson());
        }

        return json;
    }

    @Override
    public SettingGroup fromJson(JSONObject json) {
        sectionExpanded = json.has("section-expanded") && json.getBoolean("section-expanded");

        if (json.has("settings") && JSONUtils.isValidJSONArray(json, "settings")) {
            for (Object object : json.getJSONArray("settings")) {
                if (object instanceof JSONObject settingJson) {
                    Setting<?> setting = get(settingJson.getString("name"));
                    if (setting != null) {
                        setting.fromJson(settingJson);
                    }
                }
            }
        }

        return this;
    }
}

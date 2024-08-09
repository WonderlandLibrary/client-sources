package xyz.mathax.mathaxclient.settings;

import xyz.mathax.mathaxclient.gui.widgets.containers.WContainer;
import xyz.mathax.mathaxclient.systems.modules.Module;
import xyz.mathax.mathaxclient.systems.themes.Theme;
import xyz.mathax.mathaxclient.utils.json.JSONUtils;
import xyz.mathax.mathaxclient.utils.misc.ISerializable;
import xyz.mathax.mathaxclient.utils.render.color.RainbowColors;
import xyz.mathax.mathaxclient.utils.render.color.SettingColor;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Settings implements ISerializable<Settings>, Iterable<SettingGroup> {
    public final List<SettingGroup> groups = new ArrayList<>(1);

    public void onEnabled() {
        for (SettingGroup group : groups) {
            for (Setting<?> setting : group) {
                setting.onEnabled();
            }
        }
    }

    public Setting<?> get(String name) {
        for (SettingGroup settingGroup : this) {
            for (Setting<?> setting : settingGroup) {
                if (name.equalsIgnoreCase(setting.name)) {
                    return setting;
                }
            }
        }

        return null;
    }

    public void reset() {
        for (SettingGroup group : groups) {
            for (Setting<?> setting : group) {
                setting.reset();
            }
        }
    }

    public SettingGroup getGroup(String name) {
        for (SettingGroup settingGroup : this) {
            if (settingGroup.name.equals(name)) {
                return settingGroup;
            }
        }

        return null;
    }

    public int sizeGroups() {
        return groups.size();
    }

    public SettingGroup createGroup(String name, boolean expanded) {
        SettingGroup group = new SettingGroup(name, expanded);
        groups.add(group);
        return group;
    }

    public SettingGroup createGroup(String name) {
        return createGroup(name, true);
    }

    public void registerColorSettings(Module module) {
        for (SettingGroup group : this) {
            for (Setting<?> setting : group) {
                setting.module = module;

                if (setting instanceof ColorSetting) {
                    RainbowColors.addSetting((Setting<SettingColor>) setting);
                } else if (setting instanceof ColorListSetting) {
                    RainbowColors.removeSettingList((Setting<List<SettingColor>>) setting);
                }
            }
        }
    }

    public void unregisterColorSettings() {
        for (SettingGroup group : this) {
            for (Setting<?> setting : group) {
                if (setting instanceof ColorSetting) {
                    RainbowColors.removeSetting((Setting<SettingColor>) setting);
                } else if (setting instanceof ColorListSetting) {
                    RainbowColors.removeSettingList((Setting<List<SettingColor>>) setting);
                }
            }
        }
    }

    public void tick(WContainer settings, Theme theme) {
        for (SettingGroup group : groups) {
            for (Setting<?> setting : group) {
                boolean visible = setting.isVisible();

                if (visible != setting.lastWasVisible) {
                    settings.clear();
                    settings.add(theme.settings(this)).expandX();
                }

                setting.lastWasVisible = visible;
            }
        }
    }

    @Override
    public Iterator<SettingGroup> iterator() {
        return groups.iterator();
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("groups", new JSONArray());

        groups.forEach(group -> json.append("groups", group.toJson()));

        return json;
    }

    @Override
    public Settings fromJson(JSONObject json) {
        if (json.has("groups") && JSONUtils.isValidJSONArray(json, "groups")) {
            for (Object object : json.getJSONArray("groups")) {
                if (object instanceof JSONObject groupJson) {
                    SettingGroup settingGroup = getGroup(groupJson.getString("name"));
                    if (settingGroup != null) {
                        settingGroup.fromJson(groupJson);
                    }
                }
            }
        }

        return this;
    }
}

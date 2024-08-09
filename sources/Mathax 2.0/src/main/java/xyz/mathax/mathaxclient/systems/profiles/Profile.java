package xyz.mathax.mathaxclient.systems.profiles;

import xyz.mathax.mathaxclient.systems.Systems;
import xyz.mathax.mathaxclient.systems.hud.Hud;
import xyz.mathax.mathaxclient.systems.macros.Macros;
import xyz.mathax.mathaxclient.systems.modules.Modules;
import xyz.mathax.mathaxclient.systems.themes.Themes;
import xyz.mathax.mathaxclient.systems.waypoints.Waypoints;
import xyz.mathax.mathaxclient.utils.Utils;
import xyz.mathax.mathaxclient.utils.misc.ISerializable;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import xyz.mathax.mathaxclient.settings.*;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class Profile implements ISerializable<Profile> {
    public final Settings settings = new Settings();

    private final SettingGroup generalSettings = settings.createGroup("General");
    private final SettingGroup saveSettings = settings.createGroup("Save");

    // General

    public Setting<String> nameSetting = generalSettings.add(new StringSetting.Builder()
            .name("Name")
            .description("The name of the profile.")
            .defaultValue("")
            .filter(Utils::nameFilter)
            .build()
    );

    public Setting<List<String>> loadOnJoinSetting = generalSettings.add(new StringListSetting.Builder()
            .name("Load on join")
            .description("Which servers to set this profile as active when joining.")
            .filter(Utils::ipFilter)
            .build()
    );

    // Save

    public Setting<Boolean> themeSetting = saveSettings.add(new BoolSetting.Builder()
            .name("Theme")
            .description("Whether the profile should save the theme.")
            .defaultValue(false)
            .build()
    );

    public Setting<Boolean> modulesSetting = saveSettings.add(new BoolSetting.Builder()
            .name("Modules")
            .description("Whether the profile should save modules.")
            .defaultValue(true)
            .build()
    );

    public Setting<Boolean> waypointsSetting = saveSettings.add(new BoolSetting.Builder()
            .name("Waypoints")
            .description("Whether the profile should save waypoints.")
            .defaultValue(false)
            .build()
    );

    public Setting<Boolean> hudSetting = saveSettings.add(new BoolSetting.Builder()
            .name("HUD")
            .description("Whether the profile should save hud.")
            .defaultValue(true)
            .build()
    );

    public Setting<Boolean> macrosSetting = saveSettings.add(new BoolSetting.Builder()
            .name("Macros")
            .description("Whether the profile should save macros.")
            .defaultValue(true)
            .build()
    );

    public Profile() {}

    public Profile(JSONObject json) {
        fromJson(json);
    }

    //TODO: Fix

    public void load() {
        File folder = new File(getFolder().getParentFile(), nameSetting.get());
        if (themeSetting.get()) {
            Systems.get(Themes.class).getTheme().load(new File(folder, "Theme.json"));
        }

        if (modulesSetting.get()) {
            Modules.get().load(folder);
        }

        if (waypointsSetting.get()) {
            Waypoints.get().load(new File(folder, "Waypoints"));
        }

        if (hudSetting.get()) {
            Hud.get().load(folder);
        }

        if (macrosSetting.get()) {
            Macros.get().load(folder);
        }
    }

    public void save() {
        File folder = new File(getFolder().getParentFile(), nameSetting.get());
        if (themeSetting.get()) {
            Systems.get(Themes.class).getTheme().save(new File(folder, "Theme.json"));
        }

        if (modulesSetting.get()) {
            Modules.get().save(folder);
        }

        if (waypointsSetting.get()) {
            Waypoints.get().save(new File(folder, "Waypoints"));
        }

        if (hudSetting.get()) {
            Hud.get().save(folder);
        }

        if (macrosSetting.get()) {
            Macros.get().save(folder);
        }
    }

    public void delete() {
        try {
            FileUtils.deleteDirectory(getFolder());
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private File getFolder() {
        return new File(Profiles.FOLDER, nameSetting.get() + ".json");
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("settings", settings.toJson());
        return json;
    }

    @Override
    public Profile fromJson(JSONObject json) {
        if (json.has("settings")) {
            settings.fromJson(json.getJSONObject("settings"));
        }

        return this;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        Profile profile = (Profile) object;
        return Objects.equals(profile.nameSetting.get(), this.nameSetting.get());
    }
}
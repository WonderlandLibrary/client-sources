package xyz.mathax.mathaxclient.systems.macros;

import xyz.mathax.mathaxclient.settings.*;
import xyz.mathax.mathaxclient.utils.json.JSONUtils;
import xyz.mathax.mathaxclient.utils.misc.ISerializable;
import xyz.mathax.mathaxclient.utils.input.KeyBind;
import xyz.mathax.mathaxclient.utils.text.ChatUtils;
import org.json.JSONObject;

import java.io.File;
import java.util.List;
import java.util.Objects;

import static xyz.mathax.mathaxclient.MatHax.mc;

public class Macro implements ISerializable<Macro> {
    public final Settings settings = new Settings();

    private final SettingGroup generalSettings = settings.createGroup("General");

    // General

    public Setting<String> nameSetting = generalSettings.add(new StringSetting.Builder()
            .name("Name")
            .description("The name of the macro.")
            .defaultValue("")
            .build()
    );

    public Setting<List<String>> messagesSetting = generalSettings.add(new StringListSetting.Builder()
            .name("Messages")
            .description("The messages for the macro to send.")
            .build()
    );

    public Setting<KeyBind> keybindSetting = generalSettings.add(new KeyBindSetting.Builder()
            .name("Keybind")
            .description("The bind to run the macro.")
            .build()
    );

    public boolean onAction(boolean isKey, int value) {
        if (keybindSetting.get().matches(isKey, value) && mc.currentScreen == null) {
            for (String message : messagesSetting.get()) {
                if (message == null) {
                    continue;
                }

                ChatUtils.sendMessageAsPlayer(message);
            }

            return true;
        }

        return false;
    }

    public void save(File folder) {
        JSONObject json = toJson();
        if (json == null) {
            return;
        }

        File file = new File(folder, nameSetting.get() + ".json");
        JSONUtils.saveJSON(json, file);
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();

        json.put("settings", settings.toJson());

        return json;
    }

    public void load(File file) {
        JSONObject json = JSONUtils.loadJSON(file);
        if (json == null) {
            return;
        }

        fromJson(json);
    }

    @Override
    public Macro fromJson(JSONObject json) {
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

        Macro macro = (Macro) object;
        return Objects.equals(macro.nameSetting.get(), this.nameSetting.get());
    }
}
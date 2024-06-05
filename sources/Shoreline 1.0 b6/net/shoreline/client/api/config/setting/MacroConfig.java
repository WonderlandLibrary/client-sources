package net.shoreline.client.api.config.setting;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.shoreline.client.api.config.Config;
import net.shoreline.client.api.macro.Macro;

public class MacroConfig extends Config<Macro> {
    public MacroConfig(String name, String desc, Macro val) {
        super(name, desc, val);
    }

    /**
     * Overloaded method {@link Config#setValue(Object)}. Sets value by
     * instantiating new {@link Macro} based on method parameters.
     *
     * @param keycode The macro keycode
     */
    public void setValue(int keycode) {
        getValue().setKeycode(keycode);
    }

    public String getMacroId() {
        return value.getName();
    }

    public Runnable getRunnable() {
        return value.getRunnable();
    }

    public int getKeycode() {
        return value.getKeycode();
    }

    public String getKeyName() {
        return value.getKeyName();
    }

    @Override
    public JsonObject toJson() {
        JsonObject configObj = super.toJson();
        configObj.addProperty("value", getKeycode());
        return configObj;
    }

    @Override
    public Macro fromJson(JsonObject jsonObj) {
        if (jsonObj.has("value")) {
            JsonElement element = jsonObj.get("value");
            return new Macro(getMacroId(), element.getAsInt(), getRunnable());
        }
        return null;
    }
}

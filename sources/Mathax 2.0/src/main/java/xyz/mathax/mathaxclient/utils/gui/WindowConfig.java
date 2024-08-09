package xyz.mathax.mathaxclient.utils.gui;

import xyz.mathax.mathaxclient.utils.misc.ISerializable;
import org.json.JSONObject;

public class WindowConfig implements ISerializable<WindowConfig> {
    public boolean expanded = true;

    public double x = -1, y = -1;

    public WindowConfig() {}

    public WindowConfig(JSONObject json) {
        fromJson(json);
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("expanded", expanded);
        json.put("x", x);
        json.put("y", y);
        return json;
    }

    @Override
    public WindowConfig fromJson(JSONObject json) {
        expanded = json.getBoolean("expanded");
        x = json.getDouble("x");
        y = json.getDouble("y");

        return this;
    }
}
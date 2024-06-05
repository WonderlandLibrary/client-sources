package net.shoreline.client.api.config.setting;

import net.shoreline.client.api.config.Config;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.function.Supplier;

/**
 *
 *
 * @author linus
 * @since 1.0
 */
public class BooleanConfig extends Config<Boolean>
{
    public BooleanConfig(String name, String desc, Boolean val)
    {
        super(name, desc, val);
    }

    public BooleanConfig(String name, String desc, Boolean val,
                         Supplier<Boolean> visible)
    {
        super(name, desc, val, visible);
    }

    /**
     *
     *
     * @return
     */
    @Override
    public JsonObject toJson()
    {
        JsonObject configObj = super.toJson();
        configObj.addProperty("value", (Boolean) getValue());
        return configObj;
    }

    /**
     *
     *
     * @param jsonObj The data as a json object
     */
    @Override
    public Boolean fromJson(JsonObject jsonObj)
    {
        if (jsonObj.has("value"))
        {
            JsonElement element = jsonObj.get("value");
            return element.getAsBoolean();
        }
        return null;
    }
}

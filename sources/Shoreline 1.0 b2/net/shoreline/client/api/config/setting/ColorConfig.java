package net.shoreline.client.api.config.setting;

import net.shoreline.client.api.config.Config;
import net.shoreline.client.init.Modules;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.awt.*;
import java.util.function.Supplier;

/**
 *
 * @author linus
 * @since 1.0
 */
public class ColorConfig extends Config<Color>
{
    // RGB value of the current Color value
    private int rgb;
    private int alpha;
    //
    private boolean global;
    public static final Integer GLOBAL_COLOR = -0xffffffff;

    public ColorConfig(String name, String desc, Color value)
    {
        super(name, desc, value);
        rgb = value.getRGB();
    }

    public ColorConfig(String name, String desc, Color value,
                       Supplier<Boolean> visible)
    {
        super(name, desc, value, visible);
        rgb = value.getRGB();
    }

    public ColorConfig(String name, String desc, Integer rgb)
    {
        this(name, desc, rgb.equals(GLOBAL_COLOR) ? Color.WHITE :
                new Color(rgb, (rgb & 0xff000000) != 0xff000000));
        if (rgb.equals(GLOBAL_COLOR))
        {
            setGlobal();
        }
    }

    @Override
    public Color getValue()
    {
        return global ? Modules.COLORS.getColor(getAlpha()) : value;
    }

    public int getRgb()
    {
        return rgb;
    }

    public int getRed()
    {
        return (rgb >> 16) & 0xff;
    }

    public int getGreen()
    {
        return (rgb >> 8) & 0xff;
    }

    public int getBlue()
    {
        return rgb & 0xff;
    }

    public int getAlpha()
    {
        return (rgb >> 24) & 0xff;
    }

    public void setGlobal()
    {
        setGlobal(true);
    }

    public void setGlobal(boolean global)
    {
        this.global = global;
    }

    @Override
    public void setValue(Color val)
    {
        super.setValue(val);
        rgb = val.getRGB();
    }

    /**
     *
     *
     * @param val
     */
    public void setValue(int val)
    {
        Color color = new Color(val, (val & 0xff000000) != 0xff000000);
        setValue(color);
        rgb = val;
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
        // hex value for readability
        configObj.addProperty("value", "0x" + Integer.toHexString(rgb));
        return configObj;
    }

    /**
     *
     *
     * @param jsonObj The data as a json object
     * @return
     */
    @Override
    public Color fromJson(JsonObject jsonObj)
    {
        if (jsonObj.has("value"))
        {
            JsonElement element = jsonObj.get("value");
            String hex = element.getAsString();
            return parseColor(hex);
        }
        return null;
    }

    /**
     *
     * @param colorString
     * @return
     * @throws IllegalArgumentException
     */
    private Color parseColor(String colorString)
    {
        if (colorString.startsWith("0x"))
        {
            colorString = colorString.substring(2);
            return new Color((int) Long.parseLong(colorString, 16));
        }
        throw new IllegalArgumentException("Unknown color: " + colorString);
    }
}

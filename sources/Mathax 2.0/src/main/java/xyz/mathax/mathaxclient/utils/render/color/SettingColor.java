package xyz.mathax.mathaxclient.utils.render.color;

import net.minecraft.text.Style;
import net.minecraft.text.TextColor;
import net.minecraft.util.Formatting;
import org.json.JSONObject;

public class SettingColor extends Color {
    public boolean rainbow;

    public SettingColor() {
        super();
    }

    public SettingColor(int packed) {
        super(packed);
    }

    public SettingColor(int r, int g, int b) {
        super(r, g, b);
    }

    public SettingColor(int r, int g, int b, boolean rainbow) {
        this(r, g, b, 255, rainbow);
    }

    public SettingColor(boolean rainbow) {
        this(255, 255, 255, 255, rainbow);
    }

    public SettingColor(int r, int g, int b, int a) {
        super(r, g, b, a);
    }

    public SettingColor(float r, float g, float b, float a) {
        super(r, g, b, a);
    }

    public SettingColor(int r, int g, int b, int a, boolean rainbow) {
        super(r, g, b, a);
        this.rainbow = rainbow;
    }

    public SettingColor(Color color, int a, boolean rainbow) {
        super(color.r, color.g, color.b, a);
        this.rainbow = rainbow;
    }

    public SettingColor(Color color, boolean rainbow) {
        super(color.r, color.g, color.b, color.a);
        this.rainbow = rainbow;
    }

    public SettingColor(Color color, int a) {
        super(color.r, color.g, color.b, a);
    }

    public SettingColor(Color color) {
        super(color.r, color.g, color.b, color.a);
    }

    public SettingColor(SettingColor color) {
        super(color);
        this.rainbow = color.rainbow;
    }

    public SettingColor(java.awt.Color color) {
        super(color);
    }

    public SettingColor(Formatting formatting) {
        super(formatting);
    }

    public SettingColor(TextColor textColor) {
        super(textColor);
    }

    public SettingColor(Style style) {
        super(style);
    }

    public SettingColor rainbow(boolean rainbow) {
        this.rainbow = rainbow;
        return this;
    }

    public SettingColor(JSONObject json) {
        fromJson(json);
    }

    public void update() {
        if (rainbow) {
            set(RainbowColors.GLOBAL.r, RainbowColors.GLOBAL.g, RainbowColors.GLOBAL.b, a);
        }
    }

    @Override
    public SettingColor set(Color value) {
        super.set(value);
        if (value instanceof SettingColor) {
            rainbow = ((SettingColor) value).rainbow;
        }

        return this;
    }

    @Override
    public Color copy() {
        return new SettingColor(r, g, b, a, rainbow);
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = super.toJson();

        json.put("rainbow", rainbow);

        return json;
    }

    @Override
    public SettingColor fromJson(JSONObject json) {
        super.fromJson(json);

        rainbow = json.has("rainbow") && json.getBoolean("rainbow");

        return this;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (!super.equals(object) || object == null || getClass() != object.getClass()) {
            return false;
        }

        return rainbow == ((SettingColor) object).rainbow;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (rainbow ? 1 : 0);
        return result;
    }
}

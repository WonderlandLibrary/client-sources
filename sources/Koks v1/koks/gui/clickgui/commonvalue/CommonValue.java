package koks.gui.clickgui.commonvalue;

import java.awt.*;

/**
 * @author avox | lmao
 * @since on 29/07/2020.
 */
public class CommonValue {

    private String name;
    private boolean toggled;
    private Color color;
    private CommonValue.Type type;
    public float x, y;
    public int hue;

    public CommonValue(String name, boolean toggled) {
        this.name = name;
        this.toggled = toggled;
        this.type = CommonValue.Type.CHECKBOX;
    }

    public CommonValue(String name, Color color, float x, float y, int hue) {
        this.name = name;
        this.color = color;
        this.type = CommonValue.Type.COLORPICKER;
        this.x = x;
        this.y = y;
        if (hue < 0)
            hue = 0;
        if (hue > 360)
            hue = 0;
        this.hue = hue;
    }

    public int getHue() {
        return hue;
    }

    public void setHue(int hue) {
        this.hue = hue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isToggled() {
        return toggled;
    }

    public void setToggled(boolean toggled) {
        this.toggled = toggled;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public CommonValue.Type getType() {
        return type;
    }

    public void setType(CommonValue.Type type) {
        this.type = type;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public enum Type {
        CHECKBOX,
        COLORPICKER;
    }

}

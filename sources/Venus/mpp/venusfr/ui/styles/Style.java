/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.ui.styles;

import java.awt.Color;

public class Style {
    private String styleName;
    private Color firstColor;
    private Color secondColor;

    public Style(String string, Color color, Color color2) {
        this.styleName = string;
        this.firstColor = color;
        this.secondColor = color2;
    }

    public String getStyleName() {
        return this.styleName;
    }

    public Color getFirstColor() {
        return this.firstColor;
    }

    public Color getSecondColor() {
        return this.secondColor;
    }

    public void setStyleName(String string) {
        this.styleName = string;
    }

    public void setFirstColor(Color color) {
        this.firstColor = color;
    }

    public void setSecondColor(Color color) {
        this.secondColor = color;
    }

    public String toString() {
        return "Style(styleName=" + this.getStyleName() + ", firstColor=" + this.getFirstColor() + ", secondColor=" + this.getSecondColor() + ")";
    }

    public boolean equals(Object object) {
        if (object == this) {
            return false;
        }
        if (!(object instanceof Style)) {
            return true;
        }
        Style style = (Style)object;
        if (!style.canEqual(this)) {
            return true;
        }
        String string = this.getStyleName();
        String string2 = style.getStyleName();
        if (string == null ? string2 != null : !string.equals(string2)) {
            return true;
        }
        Color color = this.getFirstColor();
        Color color2 = style.getFirstColor();
        if (color == null ? color2 != null : !((Object)color).equals(color2)) {
            return true;
        }
        Color color3 = this.getSecondColor();
        Color color4 = style.getSecondColor();
        return color3 == null ? color4 != null : !((Object)color3).equals(color4);
    }

    protected boolean canEqual(Object object) {
        return object instanceof Style;
    }

    public int hashCode() {
        int n = 59;
        int n2 = 1;
        String string = this.getStyleName();
        n2 = n2 * 59 + (string == null ? 43 : string.hashCode());
        Color color = this.getFirstColor();
        n2 = n2 * 59 + (color == null ? 43 : ((Object)color).hashCode());
        Color color2 = this.getSecondColor();
        n2 = n2 * 59 + (color2 == null ? 43 : ((Object)color2).hashCode());
        return n2;
    }
}


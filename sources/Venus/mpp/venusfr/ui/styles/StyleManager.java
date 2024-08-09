/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.ui.styles;

import java.util.List;
import mpp.venusfr.ui.styles.Style;

public class StyleManager {
    private final List<Style> styleList;
    private Style currentStyle;

    public StyleManager(List<Style> list, Style style) {
        this.styleList = list;
        this.currentStyle = style;
    }

    public List<Style> getStyleList() {
        return this.styleList;
    }

    public Style getCurrentStyle() {
        return this.currentStyle;
    }

    public void setCurrentStyle(Style style) {
        this.currentStyle = style;
    }
}


/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.ui.styles;

import java.awt.Color;
import mpp.venusfr.ui.styles.Style;
import mpp.venusfr.ui.styles.StyleFactory;

public class StyleFactoryImpl
implements StyleFactory {
    @Override
    public Style createStyle(String string, Color color, Color color2) {
        return new Style(string, color, color2);
    }
}


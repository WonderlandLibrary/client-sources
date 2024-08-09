/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.utils.text;

import mpp.venusfr.utils.render.ColorUtils;
import net.minecraft.util.text.Color;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;

public class GradientUtil {
    public static StringTextComponent gradient(String string) {
        StringTextComponent stringTextComponent = new StringTextComponent("");
        for (int i = 0; i < string.length(); ++i) {
            stringTextComponent.append(new StringTextComponent(String.valueOf(string.charAt(i))).setStyle(Style.EMPTY.setColor(new Color(ColorUtils.getColor(i)))));
        }
        return stringTextComponent;
    }
}


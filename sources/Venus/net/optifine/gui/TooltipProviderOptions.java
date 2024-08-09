/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.gui;

import java.awt.Rectangle;
import java.util.ArrayList;
import net.minecraft.client.AbstractOption;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.optifine.Lang;
import net.optifine.gui.IOptionControl;
import net.optifine.gui.TooltipProvider;

public class TooltipProviderOptions
implements TooltipProvider {
    @Override
    public Rectangle getTooltipBounds(Screen screen, int n, int n2) {
        int n3 = screen.width / 2 - 150;
        int n4 = screen.height / 6 - 7;
        if (n2 <= n4 + 98) {
            n4 += 105;
        }
        int n5 = n3 + 150 + 150;
        int n6 = n4 + 84 + 10;
        return new Rectangle(n3, n4, n5 - n3, n6 - n4);
    }

    @Override
    public boolean isRenderBorder() {
        return true;
    }

    @Override
    public String[] getTooltipLines(Widget widget, int n) {
        if (!(widget instanceof IOptionControl)) {
            return null;
        }
        IOptionControl iOptionControl = (IOptionControl)((Object)widget);
        AbstractOption abstractOption = iOptionControl.getControlOption();
        return TooltipProviderOptions.getTooltipLines(abstractOption.getResourceKey());
    }

    public static String[] getTooltipLines(String string) {
        String string2;
        String string3;
        ArrayList<String> arrayList = new ArrayList<String>();
        for (int i = 0; i < 10 && (string3 = Lang.get(string2 = string + ".tooltip." + (i + 1), null)) != null; ++i) {
            arrayList.add(string3);
        }
        return arrayList.size() <= 0 ? null : arrayList.toArray(new String[arrayList.size()]);
    }
}


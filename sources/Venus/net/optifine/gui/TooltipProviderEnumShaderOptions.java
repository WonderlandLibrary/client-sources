/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.gui;

import java.awt.Rectangle;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.optifine.gui.TooltipProvider;
import net.optifine.gui.TooltipProviderOptions;
import net.optifine.shaders.config.EnumShaderOption;
import net.optifine.shaders.gui.GuiButtonDownloadShaders;
import net.optifine.shaders.gui.GuiButtonEnumShaderOption;

public class TooltipProviderEnumShaderOptions
implements TooltipProvider {
    @Override
    public Rectangle getTooltipBounds(Screen screen, int n, int n2) {
        int n3 = screen.width - 450;
        int n4 = 35;
        if (n3 < 10) {
            n3 = 10;
        }
        if (n2 <= n4 + 94) {
            n4 += 100;
        }
        int n5 = n3 + 150 + 150;
        int n6 = n4 + 84 + 10;
        return new Rectangle(n3, n4, n5 - n3, n6 - n4);
    }

    @Override
    public boolean isRenderBorder() {
        return false;
    }

    @Override
    public String[] getTooltipLines(Widget widget, int n) {
        if (widget instanceof GuiButtonDownloadShaders) {
            return TooltipProviderOptions.getTooltipLines("of.options.shaders.DOWNLOAD");
        }
        if (!(widget instanceof GuiButtonEnumShaderOption)) {
            return null;
        }
        GuiButtonEnumShaderOption guiButtonEnumShaderOption = (GuiButtonEnumShaderOption)widget;
        EnumShaderOption enumShaderOption = guiButtonEnumShaderOption.getEnumShaderOption();
        return this.getTooltipLines(enumShaderOption);
    }

    private String[] getTooltipLines(EnumShaderOption enumShaderOption) {
        return TooltipProviderOptions.getTooltipLines(enumShaderOption.getResourceKey());
    }
}


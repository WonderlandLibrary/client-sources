package net.optifine.gui;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;

import java.awt.*;

public interface TooltipProvider
{
    Rectangle getTooltipBounds(Screen var1, int var2, int var3);

    String[] getTooltipLines(Widget var1, int var2);

    boolean isRenderBorder();
}

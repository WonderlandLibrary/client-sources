/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.ui.display.impl;

import com.mojang.blaze3d.matrix.MatrixStack;
import mpp.venusfr.events.EventDisplay;
import mpp.venusfr.ui.display.ElementRenderer;
import mpp.venusfr.utils.drag.Dragging;
import mpp.venusfr.utils.render.ColorUtils;
import mpp.venusfr.utils.render.DisplayUtils;
import mpp.venusfr.utils.render.font.Fonts;
import net.minecraft.util.text.ITextComponent;

public class MemoryHudRenderer
implements ElementRenderer {
    private int b_color = ColorUtils.rgb(0, 0, 0);
    private final Dragging dragging;

    @Override
    public void render(EventDisplay eventDisplay) {
        MatrixStack matrixStack = eventDisplay.getMatrixStack();
        float f = this.dragging.getX();
        float f2 = this.dragging.getY();
        long l = Runtime.getRuntime().totalMemory();
        long l2 = Runtime.getRuntime().freeMemory();
        long l3 = l - l2;
        double d = (double)l3 / (double)l * 360.0;
        double d2 = (double)l3 / (double)l;
        DisplayUtils.drawShadow(f, f2, 50.0f, 51.0f, 15, ColorUtils.rgba(21, 24, 40, 165));
        DisplayUtils.drawRoundedRect(f, f2, 50.0f, 50.0f, 4.0f, ColorUtils.rgba(21, 24, 40, 165));
        ITextComponent iTextComponent = ITextComponent.getTextComponentOrEmpty(String.format("%.0f%%", d2 * 100.0));
        Fonts.sfui.drawCenteredText(matrixStack, iTextComponent, f + 25.5f, f2 + 26.0f, 6.5f);
        Fonts.sfui.drawCenteredText(matrixStack, ITextComponent.getTextComponentOrEmpty("Boost"), f + 25.0f, f2 + 5.0f, 6.5f);
        DisplayUtils.drawCircle(f + 25.0f, f2 + 29.0f, 1.0f, 361.0f, 13.0f, 10.0f, false, this.b_color);
        DisplayUtils.drawCircle(f + 25.0f, f2 + 29.0f, 0.0f, (float)d, 13.0f, 8.0f, false, ColorUtils.getColor(1));
        this.dragging.setWidth(50.0f);
        this.dragging.setHeight(50.0f);
    }

    public MemoryHudRenderer(Dragging dragging) {
        this.dragging = dragging;
    }
}


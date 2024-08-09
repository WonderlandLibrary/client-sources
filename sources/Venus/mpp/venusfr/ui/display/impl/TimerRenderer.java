/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.ui.display.impl;

import com.mojang.blaze3d.matrix.MatrixStack;
import mpp.venusfr.events.EventDisplay;
import mpp.venusfr.ui.display.ElementRenderer;
import mpp.venusfr.utils.drag.Dragging;
import mpp.venusfr.utils.render.AnimationMath;
import mpp.venusfr.utils.render.ColorUtils;
import mpp.venusfr.utils.render.DisplayUtils;
import mpp.venusfr.utils.render.font.Fonts;
import mpp.venusfr.venusfr;
import net.minecraft.util.text.ITextComponent;

public class TimerRenderer
implements ElementRenderer {
    private final Dragging dragging;
    private float perc;
    private int b_color = ColorUtils.rgb(0, 0, 0);

    @Override
    public void render(EventDisplay eventDisplay) {
        MatrixStack matrixStack = eventDisplay.getMatrixStack();
        float f = this.dragging.getX();
        float f2 = this.dragging.getY();
        int n = 362;
        float f3 = venusfr.getInstance().getFunctionRegistry().getTimer().maxViolation / ((Float)venusfr.getInstance().getFunctionRegistry().getTimer().timerAmount.get()).floatValue();
        float f4 = Math.min(venusfr.getInstance().getFunctionRegistry().getTimer().getViolation(), f3);
        this.perc = AnimationMath.lerp(this.perc, (f3 - f4) / f3, 10.0f);
        DisplayUtils.drawShadow(f, f2, 50.0f, 51.0f, 15, ColorUtils.rgba(21, 24, 40, 165));
        DisplayUtils.drawRoundedRect(f, f2, 50.0f, 50.0f, 4.0f, ColorUtils.rgba(21, 24, 40, 165));
        Fonts.sfui.drawCenteredText(matrixStack, ITextComponent.getTextComponentOrEmpty(String.format("%.0f%%", Float.valueOf(this.perc * 100.0f))), f + 25.5f, f2 + 26.0f, 6.5f);
        Fonts.sfui.drawCenteredText(matrixStack, ITextComponent.getTextComponentOrEmpty("Timer"), f + 25.0f, f2 + 5.0f, 6.5f);
        DisplayUtils.drawCircle(f + 25.0f, f2 + 29.0f, -1.0f, 360.0f, 13.0f, 10.0f, false, this.b_color);
        DisplayUtils.drawCircle(f + 25.0f, f2 + 29.0f, 1.0f, (float)n * this.perc, 13.0f, 8.0f, false, ColorUtils.getColor(1));
        this.dragging.setWidth(50.0f);
        this.dragging.setHeight(50.0f);
    }

    public TimerRenderer(Dragging dragging) {
        this.dragging = dragging;
    }
}


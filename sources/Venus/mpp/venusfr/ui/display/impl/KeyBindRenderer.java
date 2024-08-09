/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.ui.display.impl;

import com.mojang.blaze3d.matrix.MatrixStack;
import mpp.venusfr.events.EventDisplay;
import mpp.venusfr.functions.api.Function;
import mpp.venusfr.ui.display.ElementRenderer;
import mpp.venusfr.utils.client.KeyStorage;
import mpp.venusfr.utils.drag.Dragging;
import mpp.venusfr.utils.render.ColorUtils;
import mpp.venusfr.utils.render.DisplayUtils;
import mpp.venusfr.utils.render.Scissor;
import mpp.venusfr.utils.render.font.Fonts;
import mpp.venusfr.utils.text.GradientUtil;
import mpp.venusfr.venusfr;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;

public class KeyBindRenderer
implements ElementRenderer {
    private final Dragging dragging;
    private final ResourceLocation logo = new ResourceLocation("venusfr/images/hud/hotkeys.png");
    private float iconSizeX = 10.0f;
    private float iconSizeY = 10.0f;
    private float width;
    private float height;

    @Override
    public void render(EventDisplay eventDisplay) {
        MatrixStack matrixStack = eventDisplay.getMatrixStack();
        float f = this.dragging.getX();
        float f2 = this.dragging.getY();
        float f3 = 6.5f;
        float f4 = 5.0f;
        StringTextComponent stringTextComponent = GradientUtil.gradient("Hotkeys");
        String string = "Hotkeys";
        float f5 = f2;
        this.drawStyledRect(f, f5, this.width, this.height, 5.0f);
        DisplayUtils.drawShadow(f, f2, this.width, this.height, 15, ColorUtils.rgba(21, 24, 40, 165));
        Scissor.push();
        Scissor.setFromComponentCoordinates(f, f2, this.width, this.height);
        Fonts.sfui.drawText(matrixStack, string, f + f4, f2 + f4 + 1.0f, ColorUtils.rgb(255, 255, 255), f3);
        float f6 = f + this.width - this.iconSizeX - f4;
        DisplayUtils.drawImage(this.logo, f6, f2 + 3.7f, this.iconSizeX, this.iconSizeY, ColorUtils.getColor(1));
        f2 += f3 + f4 * 2.0f;
        float f7 = Fonts.sfMedium.getWidth(stringTextComponent, f3) + f4 * 2.0f;
        float f8 = f3 + f4 * 2.0f;
        for (Function function : venusfr.getInstance().getFunctionRegistry().getFunctions()) {
            function.getAnimation().update();
            if (!(function.getAnimation().getValue() > 0.0) || function.getBind() == 0) continue;
            String string2 = function.getName();
            float f9 = Fonts.sfMedium.getWidth(string2, f3);
            String string3 = KeyStorage.getKey(function.getBind());
            float f10 = Fonts.sfMedium.getWidth(string3, f3);
            float f11 = f9 + f10 + f4 * 3.0f;
            Fonts.sfMedium.drawText(matrixStack, string2, f + f4, f2 + 0.5f, ColorUtils.rgba(210, 210, 210, (int)(255.0 * function.getAnimation().getValue())), f3);
            Fonts.sfMedium.drawText(matrixStack, string3, f + this.width - f4 - f10, f2 + 0.5f, ColorUtils.rgba(210, 210, 210, (int)(255.0 * function.getAnimation().getValue())), f3);
            if (f11 > f7) {
                f7 = f11;
            }
            f2 += (float)((double)(f3 + f4) * function.getAnimation().getValue());
            f8 += (float)((double)(f3 + f4) * function.getAnimation().getValue());
        }
        Scissor.unset();
        Scissor.pop();
        this.width = Math.max(f7, 80.0f);
        this.height = f8 + 2.5f;
        this.dragging.setWidth(this.width);
        this.dragging.setHeight(this.height);
    }

    private void drawStyledRect(float f, float f2, float f3, float f4, float f5) {
        DisplayUtils.drawRoundedRect(f - 0.5f, f2 - 0.5f, f3 + 1.0f, f4 + 1.0f, f5 + 0.5f, ColorUtils.setAlpha(ColorUtils.rgb(21, 24, 40), 90));
        DisplayUtils.drawRoundedRect(f, f2, f3, f4, f5, ColorUtils.rgba(21, 24, 40, 90));
    }

    public KeyBindRenderer(Dragging dragging) {
        this.dragging = dragging;
    }
}


/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package src.Wiksi.ui.display.impl;

import com.mojang.blaze3d.matrix.MatrixStack;
import src.Wiksi.events.EventDisplay;
import src.Wiksi.functions.api.Function;
import src.Wiksi.ui.display.ElementRenderer;
import src.Wiksi.utils.client.KeyStorage;
import src.Wiksi.utils.drag.Dragging;
import src.Wiksi.utils.render.ColorUtils;
import src.Wiksi.utils.render.DisplayUtils;
import src.Wiksi.utils.render.Scissor;
import src.Wiksi.utils.render.font.Fonts;
import src.Wiksi.utils.text.GradientUtil;
import src.Wiksi.Wiksi;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;

public class KeyBindRenderer
implements ElementRenderer {
    private final Dragging dragging;
    private final ResourceLocation logo = new ResourceLocation("Wiksi/images/watermark/hotkeys.png");
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
        int n = "\u5024\u5b19\u6ef4\u6ad4\u5d68".length();
        int n2 = "\u65bd\u7104".length();
        int n3 = "\u59ba\u6bb8\u5554".length();
        int n4 = "\u5d0e".length();
        float f6 = f + this.width - this.iconSizeX - f4;
        DisplayUtils.drawImage(this.logo, f6, f2 + 3.7f, this.iconSizeX, this.iconSizeY, ColorUtils.getColor(1));
        f2 += f3 + f4 * 2.0f;
        float f7 = Fonts.sfMedium.getWidth(stringTextComponent, f3) + f4 * 2.0f;
        float f8 = f3 + f4 * 2.0f;
        for (Function function : Wiksi.getInstance().getFunctionRegistry().getFunctions()) {
            function.getAnimation().update();
            int n5 = "\u70bf".length();
            int n6 = "\u6a7c".length();
            if (!(function.getAnimation().getValue() > 0.0) || function.getBind() == 0) continue;
            String string2 = function.getName();
            float f9 = Fonts.sfMedium.getWidth(string2, f3);
            String string3 = KeyStorage.getKey(function.getBind());
            float f10 = Fonts.sfMedium.getWidth(string3, f3);
            float f11 = f9 + f10 + f4 * 3.0f;
            Fonts.sfMedium.drawText(matrixStack, string2, f + f4, f2 + 0.5f, ColorUtils.rgba(210, 210, 210, (int)(255.0 * function.getAnimation().getValue())), f3);
            int n7 = "\u59ab\u64c9\u551b\u5cde".length();
            int n8 = "\u677a\u5e4e\u5ff1\u696c\u5c8e".length();
            int n9 = "\u4f0f\u66cf".length();
            int n10 = "\u70fb".length();
            int n11 = "\u652f\u7100\u64fd\u57e9".length();
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
        int n = "\u6abd\u6df7\u50c4\u52fd\u6079".length();
        int n2 = "\u6fb9".length();
        int n3 = "\u6ecb\u5883\u60cd\u54bc\u4e40".length();
        int n4 = "\u4e74\u67a8".length();
        int n5 = "\u62c2".length();
        DisplayUtils.drawRoundedRect(f - 0.5f, f2 - 0.5f, f3 + 1.0f, f4 + 1.0f, f5 + 0.5f, ColorUtils.setAlpha(ColorUtils.rgb(21, 24, 40), 90));
        DisplayUtils.drawRoundedRect(f, f2, f3, f4, f5, ColorUtils.rgba(21, 24, 40, 90));
    }

    public KeyBindRenderer(Dragging dragging) {
        this.dragging = dragging;
    }
}


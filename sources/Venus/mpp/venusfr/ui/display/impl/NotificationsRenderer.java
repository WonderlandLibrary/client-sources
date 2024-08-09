/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.ui.display.impl;

import com.mojang.blaze3d.matrix.MatrixStack;
import mpp.venusfr.events.EventDisplay;
import mpp.venusfr.functions.api.Function;
import mpp.venusfr.functions.api.FunctionRegistry;
import mpp.venusfr.ui.display.ElementRenderer;
import mpp.venusfr.ui.styles.Style;
import mpp.venusfr.utils.render.ColorUtils;
import mpp.venusfr.utils.render.DisplayUtils;
import mpp.venusfr.utils.render.Scissor;
import mpp.venusfr.utils.render.font.Fonts;
import mpp.venusfr.utils.text.GradientUtil;
import mpp.venusfr.venusfr;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.StringTextComponent;

public class NotificationsRenderer
implements ElementRenderer {
    private final FunctionRegistry functionRegistry;
    private float width;
    private float height;

    public NotificationsRenderer() {
        this.functionRegistry = venusfr.getInstance().getFunctionRegistry();
    }

    @Override
    public void render(EventDisplay eventDisplay) {
        MatrixStack matrixStack = eventDisplay.getMatrixStack();
        float f = Minecraft.getInstance().getMainWindow().getScaledWidth();
        float f2 = Minecraft.getInstance().getMainWindow().getScaledHeight();
        float f3 = f - this.width - 5.0f;
        float f4 = f2 - this.height - 5.0f;
        float f5 = 6.5f;
        float f6 = 5.0f;
        StringTextComponent stringTextComponent = GradientUtil.gradient("\u0424\u0443\u043d\u043a\u0446\u0438\u0438");
        Style style = venusfr.getInstance().getStyleManager().getCurrentStyle();
        DisplayUtils.drawShadow(f3, f4, this.width, this.height, 10, style.getFirstColor().getRGB(), style.getSecondColor().getRGB());
        this.drawStyledRect(f3, f4, this.width, this.height, 4.0f);
        Scissor.push();
        Scissor.setFromComponentCoordinates(f3, f4, this.width, this.height);
        Fonts.sfui.drawCenteredText(matrixStack, stringTextComponent, f3 + this.width / 2.0f, f4 + f6 + 0.5f, f5);
        f4 += f5 + f6 * 2.0f;
        float f7 = Fonts.sfMedium.getWidth(stringTextComponent, f5) + f6 * 2.0f;
        float f8 = f5 + f6 * 2.0f;
        for (Function function : this.functionRegistry.getFunctions()) {
            String string = function.getName() + " " + (function.isState() ? "\u0432\u043a\u043b\u044e\u0447\u0435\u043d\u043e" : "\u0432\u044b\u043a\u043b\u044e\u0447\u0435\u043d\u043e");
            float f9 = Fonts.sfMedium.getWidth(string, f5);
            Fonts.sfMedium.drawText(matrixStack, string, f3 + f6, f4, ColorUtils.rgba(210, 210, 210, 255), f5);
            if (f9 + f6 * 2.0f > f7) {
                f7 = f9 + f6 * 2.0f;
            }
            f4 += f5 + f6;
            f8 += f5 + f6;
        }
        Scissor.unset();
        Scissor.pop();
        this.width = Math.max(f7, 80.0f);
        this.height = f8 + 2.5f;
    }

    private void drawStyledRect(float f, float f2, float f3, float f4, float f5) {
        DisplayUtils.drawRoundedRect(f - 0.5f, f2 - 0.5f, f3 + 1.0f, f4 + 1.0f, f5 + 0.5f, ColorUtils.getColor(0));
        DisplayUtils.drawRoundedRect(f, f2, f3, f4, f5, ColorUtils.rgba(21, 21, 21, 255));
    }

    public NotificationsRenderer(FunctionRegistry functionRegistry) {
        this.functionRegistry = functionRegistry;
    }
}


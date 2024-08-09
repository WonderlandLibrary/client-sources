/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package src.Wiksi.ui.display.impl;

import com.mojang.blaze3d.matrix.MatrixStack;
import src.Wiksi.events.EventDisplay;
import src.Wiksi.functions.api.Function;
import src.Wiksi.functions.api.FunctionRegistry;
import src.Wiksi.ui.display.ElementRenderer;
import src.Wiksi.ui.styles.Style;
import src.Wiksi.utils.render.ColorUtils;
import src.Wiksi.utils.render.DisplayUtils;
import src.Wiksi.utils.render.Scissor;
import src.Wiksi.utils.render.font.Fonts;
import src.Wiksi.utils.text.GradientUtil;
import src.Wiksi.Wiksi;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.StringTextComponent;

public class NotificationsRenderer
implements ElementRenderer {
    private final FunctionRegistry functionRegistry;
    private float width;
    private float height;

    public NotificationsRenderer() {
        this.functionRegistry = Wiksi.getInstance().getFunctionRegistry();
    }

    @Override
    public void render(EventDisplay eventDisplay) {
        MatrixStack matrixStack = eventDisplay.getMatrixStack();
        float f = Minecraft.getInstance().getMainWindow().getScaledWidth();
        float f2 = Minecraft.getInstance().getMainWindow().getScaledHeight();
        int n = "\u4ed6\u51ea\u70d6\u4eca".length();
        int n2 = "\u693a".length();
        int n3 = "\u6d8f\u5dc7\u61bf\u54e6\u4e52".length();
        int n4 = "\u5349\u541e\u5401".length();
        int n5 = "\u7056\u6b7d\u665d\u52c0".length();
        float f3 = f - this.width - 5.0f;
        int n6 = "\u5175\u6800\u5860\u6434\u56b6".length();
        int n7 = "\u644c\u6e59\u65b7\u55c3".length();
        int n8 = "\u5da8\u594d".length();
        int n9 = "\u5601\u5244\u6f55\u7043\u5451".length();
        int n10 = "\u534f".length();
        int n11 = "\u5eff\u6986\u5aa7".length();
        float f4 = f2 - this.height - 5.0f;
        float f5 = 6.5f;
        float f6 = 5.0f;
        StringTextComponent stringTextComponent = GradientUtil.gradient("\u0424\u0443\u043d\u043a\u0446\u0438\u0438");
        Style style = Wiksi.getInstance().getStyleManager().getCurrentStyle();
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
        int n = "\u6d24\u6021\u5751\u5187\u52d2".length();
        int n2 = "\u633a\u66cb".length();
        int n3 = "\u5efe\u631d\u70a1\u6ecb\u6ef9".length();
        int n4 = "\u5356".length();
        int n5 = "\u5e5c".length();
        DisplayUtils.drawRoundedRect(f - 0.5f, f2 - 0.5f, f3 + 1.0f, f4 + 1.0f, f5 + 0.5f, ColorUtils.getColor(0));
        DisplayUtils.drawRoundedRect(f, f2, f3, f4, f5, ColorUtils.rgba(21, 21, 21, 255));
    }

    public NotificationsRenderer(FunctionRegistry functionRegistry) {
        this.functionRegistry = functionRegistry;
    }
}


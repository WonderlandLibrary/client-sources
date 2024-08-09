/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package fun.ellant.ui.display.impl;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.StringTextComponent;
import fun.ellant.Ellant;
import fun.ellant.events.EventDisplay;
import fun.ellant.functions.api.Function;
import fun.ellant.functions.api.FunctionRegistry;
import fun.ellant.ui.display.ElementRenderer;
import fun.ellant.ui.styles.Style;
import fun.ellant.utils.render.ColorUtils;
import fun.ellant.utils.render.DisplayUtils;
import fun.ellant.utils.render.Scissor;
import fun.ellant.utils.render.font.Fonts;
import fun.ellant.utils.text.GradientUtil;

public class NotificationsRenderer
        implements ElementRenderer {
    private final FunctionRegistry functionRegistry;
    private float width;
    private float height;

    public NotificationsRenderer() {
        this.functionRegistry = Ellant.getInstance().getFunctionRegistry();
    }

    @Override
    public void render(EventDisplay eventDisplay) {
        MatrixStack ms = eventDisplay.getMatrixStack();
        float screenWidth = Minecraft.getInstance().getMainWindow().getScaledWidth();
        float screenHeight = Minecraft.getInstance().getMainWindow().getScaledHeight();

        float posX = (screenWidth - this.width) / 2.0f;
        float posY = (screenHeight - this.height) / 2.0f;

        float fontSize = 6.5f;
        float padding = 5.0f;
        StringTextComponent title = (StringTextComponent) GradientUtil.white("функция");
        Style style = Ellant.getInstance().getStyleManager().getCurrentStyle();
        DisplayUtils.drawShadow(posX, posY, this.width, this.height, 10, style.getFirstColor().getRGB(), style.getSecondColor().getRGB());
        this.drawStyledRect(posX, posY, this.width, this.height, 4.0f);
        Scissor.push();
        Scissor.setFromComponentCoordinates(posX, posY, this.width, this.height);
        Fonts.sfui.drawCenteredText(ms, title, posX + this.width / 2.4f, posY + padding + 2f, fontSize);
        posY += fontSize + padding * 2.0f;
        float maxWidth = Fonts.sfMedium.getWidth(title, fontSize) + padding * 2.0f;
        float localHeight = fontSize + padding * 2.0f;
        for (Function function : this.functionRegistry.getFunctions()) {
            String functionName = function.getName() + " " + (function.isState() ? "функция" : "функция");
            float nameWidth = Fonts.sfMedium.getWidth(functionName, fontSize);
            Fonts.sfMedium.drawText(ms, functionName, posX + padding, posY, ColorUtils.rgba(210, 210, 210, 255), fontSize);
            if (nameWidth + padding * 2.0f > maxWidth) {
                maxWidth = nameWidth + padding * 2.0f;
            }
            posY += fontSize + padding;
            localHeight += fontSize + padding;
        }
        Scissor.unset();
        Scissor.pop();
        this.width = Math.max(maxWidth, 80.0f);
        this.height = localHeight + 2.5f;
    }

    private void drawStyledRect(float x, float y, float width, float height, float radius) {
        DisplayUtils.drawRoundedRect(x - 0.5f, y - 0.5f, width + 1.0f, height + 1.0f, radius + 0.5f, ColorUtils.getColor(0));
        DisplayUtils.drawRoundedRect(x, y, width, height, radius, ColorUtils.rgba(21, 21, 21, 205));
    }

    public NotificationsRenderer(FunctionRegistry functionRegistry) {
        this.functionRegistry = functionRegistry;
    }
}

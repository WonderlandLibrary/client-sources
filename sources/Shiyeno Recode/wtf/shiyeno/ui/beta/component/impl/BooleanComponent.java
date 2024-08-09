package wtf.shiyeno.ui.beta.component.impl;

import com.mojang.blaze3d.matrix.MatrixStack;
import wtf.shiyeno.modules.settings.imp.BooleanOption;
import wtf.shiyeno.util.font.Fonts;
import wtf.shiyeno.util.render.ColorUtil;
import wtf.shiyeno.util.render.RenderUtil;
import wtf.shiyeno.util.render.SmartScissor;
import wtf.shiyeno.util.render.animation.AnimationMath;

public class BooleanComponent extends Component {

    public BooleanOption option;

    public BooleanComponent(BooleanOption option) {
        this.option = option;
        this.s = option;
    }

    public float animationToggle;

    @Override
    public void drawComponent(MatrixStack matrixStack, int mouseX, int mouseY) {
        height = 15;
        float off = 0.5f;
        animationToggle = AnimationMath.lerp(animationToggle, option.get() ? 1 : 0, 10);

        int color = ColorUtil.interpolateColor(RenderUtil.IntColor.rgba(26, 29, 33, 255),
                ColorUtil.getColorStyle(90), animationToggle);

        RenderUtil.Render2D.drawShadow(x + 5, y + 1 + off, 10, 10, 8, RenderUtil.reAlphaInt(color, 50));
        RenderUtil.Render2D.drawRoundedRect(x + 5, y + 1 + off, 10, 10, 2f, color);
        SmartScissor.push();

        SmartScissor.setFromComponentCoordinates(x + 5, y + 1 + off, 10 * animationToggle, 10);
        Fonts.icons[12].drawString(matrixStack, "A", x + 7, y + 6 + off, -1);
        SmartScissor.unset();
        SmartScissor.pop();

        Fonts.gilroy[14].drawString(matrixStack, option.getName(), x + 18f, y + 4.5f + off, -1);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (RenderUtil.isInRegion(mouseX, mouseY, x, y, width - 10, 15)) {

            option.toggle();
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
    }

    @Override
    public void keyTyped(int keyCode, int scanCode, int modifiers) {
    }

    @Override
    public void charTyped(char codePoint, int modifiers) {
    }
}
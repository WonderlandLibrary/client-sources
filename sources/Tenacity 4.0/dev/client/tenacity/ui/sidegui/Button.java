package dev.client.tenacity.ui.sidegui;

import dev.utils.animations.Animation;
import dev.utils.animations.Direction;
import dev.utils.animations.impl.DecelerateAnimation;
import dev.utils.font.FontUtil;
import dev.client.tenacity.utils.misc.HoveringUtil;
import dev.client.tenacity.utils.render.ColorUtil;
import dev.client.tenacity.utils.render.RoundedUtil;
import net.minecraft.client.renderer.GlStateManager;

import java.awt.*;

public class Button extends GuiPanel {

    private final String text;
    public float x, y, width, height;
    public Runnable clickAction;
    private Animation hoverAnimation;

    public Button(String text) {
        this.text = text;
        this.x = x;
        this.y = y;
    }

    @Override
    public void initGui() {
        hoverAnimation = new DecelerateAnimation(250, 1);
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {

    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks, int alpha) {
        width = (float) (FontUtil.tenacityBoldFont22.getStringWidth(text) + 20);
        height = (float) (FontUtil.tenacityBoldFont22.getHeight() + 10);

        boolean hovered = HoveringUtil.isHovering(x, y, width, height, mouseX, mouseY);

        hoverAnimation.setDirection(hovered ? Direction.FORWARDS : Direction.BACKWARDS);

        Color rectColor = new Color(45, 45, 45);
        Color finalColor = ColorUtil.interpolateColorC(rectColor, ColorUtil.brighter(rectColor, .8f), (float) hoverAnimation.getOutput());
        GlStateManager.color(1, 1, 1, 1);
        RoundedUtil.drawRound(x, y, width, height, 6, ColorUtil.applyOpacity(finalColor, alpha / 255f));
        GlStateManager.color(1, 1, 1, 1);
        FontUtil.tenacityBoldFont22.drawCenteredString(text, x + width / 2f, y + FontUtil.tenacityBoldFont22.getMiddleOfBox(height), ColorUtil.applyOpacity(-1, alpha / 255f));
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        float width = (float) (FontUtil.tenacityBoldFont22.getStringWidth(text) + 20);
        float height = (float) (FontUtil.tenacityBoldFont22.getHeight() + 10);
        if (HoveringUtil.isHovering(x, y, width, height, mouseX, mouseY)) {
            clickAction.run();
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int button) {

    }
}

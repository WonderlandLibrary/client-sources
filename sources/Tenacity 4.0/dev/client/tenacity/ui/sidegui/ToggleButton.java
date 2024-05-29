package dev.client.tenacity.ui.sidegui;

import dev.client.tenacity.module.impl.render.ClickGuiMod;
import dev.utils.animations.Animation;
import dev.utils.animations.Direction;
import dev.utils.animations.impl.DecelerateAnimation;
import dev.utils.animations.impl.EaseBackIn;
import dev.utils.font.FontUtil;
import dev.client.tenacity.utils.misc.HoveringUtil;
import dev.client.tenacity.utils.render.ColorUtil;
import dev.client.tenacity.utils.render.RoundedUtil;

import java.awt.*;

public class ToggleButton extends GuiPanel {

    private final String text;
    public float x, y, width, height;
    public boolean toggled;
    private Animation hoverAnimation;
    private Animation toggleAnimation;

    public ToggleButton(String text) {
        this.text = text;
    }


    @Override
    public void initGui() {
        hoverAnimation = new DecelerateAnimation(250, 1);
        hoverAnimation.setDirection(Direction.BACKWARDS);
        toggleAnimation = new EaseBackIn(250, 1, 2f);
        toggleAnimation.setDirection(Direction.BACKWARDS);
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
        toggleAnimation.setDirection(toggled ? Direction.FORWARDS : Direction.BACKWARDS);

        Color backgroundColor = new Color(45, 45, 45, alpha);
        Color rectColor = ColorUtil.interpolateColorC(backgroundColor,
                ColorUtil.brighter(backgroundColor, .8f), (float) hoverAnimation.getOutput());

        Color accentColor = ColorUtil.interpolateColorC(new Color(230, 40, 70), ClickGuiMod.color.getColor(), (float) toggleAnimation.getOutput());


        RoundedUtil.drawRoundOutline(x, y, width, height, 6, (float) (.5f + (1 * toggleAnimation.getOutput())),
                ColorUtil.interpolateColorC(rectColor, accentColor, (float) (toggleAnimation.getOutput() * .15)),
                accentColor);

        FontUtil.tenacityBoldFont20.drawCenteredString(text, x + width / 2f,
                y + FontUtil.tenacityBoldFont20.getMiddleOfBox(height), ColorUtil.applyOpacity(-1, alpha / 255f));

    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        boolean hovered = HoveringUtil.isHovering(x, y, width, height, mouseX, mouseY);
        if (hovered && button == 0) {
            toggled = !toggled;
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int button) {

    }
}

package dev.client.tenacity.ui.sidegui;

import dev.client.tenacity.module.Module;
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

public class ScriptRect extends GuiPanel {

    private final Module module;
    public boolean reinit;
    float x, y, width, height;
    private Animation hoverAnimation;
    private Animation toggleAnimation;

    public ScriptRect(Module module) {
        this.module = module;
    }


    @Override
    public void initGui() {
        hoverAnimation = new DecelerateAnimation(250, 1);
        toggleAnimation = new EaseBackIn(250, 1, 2);
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {

    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks, int alpha) {

        int textColor = ColorUtil.applyOpacity(-1, alpha / 255f);
        Color backgroundColor = new Color(45, 45, 45);
        Color rectColor = ColorUtil.interpolateColorC(backgroundColor, ClickGuiMod.color.getColor(), (float) (toggleAnimation.getOutput() * .1f));
        Color outlineColor = ColorUtil.interpolateColorC(ColorUtil.brighter(backgroundColor, .6f),
                ClickGuiMod.color.getColor(), (float) toggleAnimation.getOutput());

        boolean hovered = HoveringUtil.isHovering(x, y, width, height, mouseX, mouseY);
        hoverAnimation.setDirection(hovered ? Direction.FORWARDS : Direction.BACKWARDS);
        toggleAnimation.setDirection(module.isToggled() ? Direction.FORWARDS : Direction.BACKWARDS);

        RoundedUtil.drawRoundOutline(x, y, width, height, 6, (float) (.5 + (1.5 * toggleAnimation.getOutput())),
                ColorUtil.interpolateColorC(rectColor, ColorUtil.brighter(rectColor, .8f), (float) hoverAnimation.getOutput()), outlineColor);

        FontUtil.tenacityBoldFont26.drawString(module.getName(), x + 5, y + 5, textColor);

        FontUtil.tenacityFont18.wrapText(module.getDescription(), x + 5, y + 23,
                ColorUtil.applyOpacity(new Color(145, 145, 145), alpha / 255f).getRGB(), width, 4);

        //  FontUtil.tenacityFont18.drawString("§fAuthor: §r" + module.author, x + 5,
          //      y + height - (FontUtil.tenacityFont18.getHeight() + 5), clickGUIMod.color.getColor().getRGB());


    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        boolean hovered = HoveringUtil.isHovering(x, y, width, height, mouseX, mouseY);
        if (hovered && button == 0) module.toggleSilent();
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int button) {

    }
}

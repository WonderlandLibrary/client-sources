package dev.star.gui.clickguis.dropdown.components.settings;

import dev.star.module.settings.impl.ModeSetting;
import dev.star.gui.clickguis.dropdown.components.SettingComponent;
import dev.star.utils.animations.Animation;
import dev.star.utils.animations.ContinualAnimation;
import dev.star.utils.animations.Direction;
import dev.star.utils.animations.impl.DecelerateAnimation;
import dev.star.utils.misc.HoveringUtil;
import dev.star.utils.render.RenderUtil;

import java.awt.*;
import java.util.List;

public class ModeComponent extends SettingComponent<ModeSetting> {

    private final Animation hoverAnimation = new DecelerateAnimation(250, 1, Direction.BACKWARDS);
    private final Animation openAnimation = new DecelerateAnimation(250, 1, Direction.BACKWARDS);
    private final Animation selectionBox = new DecelerateAnimation(250, 1, Direction.BACKWARDS);

    private boolean opened;

    public float realHeight;
    public float normalCount;

    private final ContinualAnimation selectionBoxY = new ContinualAnimation();
    private String hoveringMode = "";

    public ModeComponent(ModeSetting modeSetting) {
        super(modeSetting);
        normalCount = 2;
    }


    @Override
    public void initGui() {

    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {

    }

    @Override
    public void drawScreen(int mouseX, int mouseY) {
        ModeSetting modeSetting = getSetting();
        String displayText = modeSetting.name + ": " + modeSetting.getMode();

        float boxHeight = Font18.getHeight();
        float boxY = y + 5.5f;
        float boxX = x + 4;
        float boxWidth = Font18.getStringWidth(displayText);

        boolean themeSetting = modeSetting.name.equals("Theme Selection");

        boolean hoveringBox = HoveringUtil.isHovering(boxX, boxY, boxWidth, boxHeight, mouseX, mouseY);

        hoverAnimation.setDirection(hoveringBox ? Direction.FORWARDS : Direction.BACKWARDS);
        openAnimation.setDirection(opened ? Direction.FORWARDS : Direction.BACKWARDS);

        Font18.drawString(displayText, x + 4, y + 5.5f,
                new Color(227, 227, 227, 255).getRGB(), true);
        RenderUtil.resetColor();

    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        ModeSetting modeSetting = getSetting();
        String displayText = modeSetting.name + ": " + modeSetting.getMode();
        float boxHeight = Font18.getHeight();
        float boxY = y + 5.5f;
        float boxX = x + 4;
        float boxWidth = Font18.getStringWidth(displayText);

        boolean hoveringBox = HoveringUtil.isHovering(boxX, boxY, boxWidth, boxHeight, mouseX, mouseY);

        if (hoveringBox && button == 0) { // Left-click to cycle modes
            List<String> modes = modeSetting.modes;
            int currentIndex = modes.indexOf(modeSetting.getMode());
            int nextIndex = (currentIndex + 1) % modes.size();
            modeSetting.setCurrentMode(modes.get(nextIndex));
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {

    }
}

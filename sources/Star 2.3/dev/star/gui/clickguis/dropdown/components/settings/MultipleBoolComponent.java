package dev.star.gui.clickguis.dropdown.components.settings;

import dev.star.utils.tuples.Pair;
import dev.star.module.settings.impl.BooleanSetting;
import dev.star.module.settings.impl.MultipleBoolSetting;
import dev.star.gui.clickguis.dropdown.components.SettingComponent;
import dev.star.utils.animations.Animation;
import dev.star.utils.animations.Direction;
import dev.star.utils.animations.impl.DecelerateAnimation;
import dev.star.utils.misc.HoveringUtil;
import dev.star.utils.render.ColorUtil;
import dev.star.utils.render.RoundedUtil;

import java.awt.*;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class MultipleBoolComponent extends SettingComponent<MultipleBoolSetting> {

    public float realHeight;
    public float normalCount;

    private final HashMap<BooleanSetting, Pair<Animation, Animation>> booleanSettingAnimations = new HashMap<>();

    private boolean opened;


    private final List<BooleanSetting> sortedSettings;

    public MultipleBoolComponent(MultipleBoolSetting setting) {
        super(setting);

        sortedSettings = setting.getBoolSettings().stream().sorted(Comparator.comparingDouble(this::getEnabledWidth)).collect(Collectors.toList());

        for (BooleanSetting booleanSetting : sortedSettings) {
            booleanSettingAnimations.put(booleanSetting, Pair.of(new DecelerateAnimation(250, 1), new DecelerateAnimation(250, 1)));
        }

        normalCount = 1.5f;
    }


    @Override
    public void initGui() {

    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {

    }

    private float additionalHeight = 0;

    @Override
    public void drawScreen(int mouseX, int mouseY) {
        float boxHeight = 2;
        float boxY = y + realHeight / 2f - (boxHeight / 2f);
        float boxX = x + 6;
        float boxWidth = width - 10;

        float enabledCount = sortedSettings.stream().filter(BooleanSetting::isEnabled).count();

        Color outlineColor = ColorUtil.interpolateColorC(settingRectColor.brighter().brighter(), clientColors.getSecond(), enabledCount / sortedSettings.size());

        Color rectColor = settingRectColor.brighter();

        Font16.drawString(getSetting().name, x + 6, y + 4, textColor);

        float yOffset = 3;
        float spacing = 3;

        for (BooleanSetting setting : sortedSettings) {
            float enabledHeight = Font16.getHeight() + 4;

            float enabledX = boxX + 2;
            float enabledY = boxY + yOffset;

            boolean hovering = HoveringUtil.isHovering(enabledX, enabledY, boxWidth, enabledHeight, mouseX, mouseY);

            Animation hoverAnimation = booleanSettingAnimations.get(setting).getFirst();
            Animation toggleAnimation = booleanSettingAnimations.get(setting).getSecond();

            hoverAnimation.setDirection(hovering ? Direction.FORWARDS : Direction.BACKWARDS);
            toggleAnimation.setDirection(setting.isEnabled() ? Direction.FORWARDS : Direction.BACKWARDS);

            Color textColorOption = setting.isEnabled() ? ColorUtil.applyOpacity(clientColors.getSecond(), .5f + (.5f * toggleAnimation.getOutput().floatValue())) : textColor;

            float bulletX = enabledX;
            float bulletY = enabledY + enabledHeight / 2;
            Color bulletColor = setting.isEnabled() ? textColorOption : Color.GRAY;
            drawBulletPoint(bulletX, bulletY, textColorOption);


            Font16.drawString(setting.name, enabledX + 10, enabledY + 2, Color.white);

            yOffset += enabledHeight + spacing;
        }

        additionalHeight = yOffset - 3; // Adjust additional height

        float increment = (((boxY - y) + boxHeight + additionalHeight + 3) - realHeight) / (realHeight / normalCount);

        countSize = normalCount + increment;
    }

    private void drawBulletPoint(float x, float y, Color color) {
         float  size = 3;
        RoundedUtil.drawRound(x  - size / 2f, y - size / 4f, size, size, (size / 2f) - .5f, color);
    }



    private float getEnabledWidth(BooleanSetting setting) {
        return (Font16.getStringWidth(setting.name) + 4);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        float boxHeight = 15;
        float boxY = y + realHeight / 2f - (boxHeight / 2f) + 4;
        float boxX = x + 6;

        float yOffset = 3;
        float spacing = 3;

        for (BooleanSetting setting : sortedSettings) {
            float enabledHeight = Font16.getHeight() + 4;

            float enabledX = boxX + 2; // Fixed X position
            float enabledY = boxY + yOffset; // Incremental Y position
            boolean hovered = HoveringUtil.isHovering(enabledX, enabledY, width - 10, enabledHeight, mouseX, mouseY);

            if (hovered && button == 0) {
                setting.toggle();
            }

            yOffset += enabledHeight + spacing; // Move Y offset down for the next setting
        }
    }


    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {

    }
}

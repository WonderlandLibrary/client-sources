package dev.star.gui.clickguis.dropdown.components.settings;

import dev.star.module.settings.impl.KeybindSetting;
import dev.star.gui.clickguis.dropdown.components.SettingComponent;
import dev.star.utils.animations.Animation;
import dev.star.utils.animations.Direction;
import dev.star.utils.animations.impl.DecelerateAnimation;
import dev.star.utils.misc.HoveringUtil;
import dev.star.utils.render.ColorUtil;
import dev.star.utils.render.RoundedUtil;
import org.lwjgl.input.Keyboard;

import java.awt.*;

public class KeybindComponent extends SettingComponent<KeybindSetting> {


    private boolean binding;

    private final Animation clickAnimation = new DecelerateAnimation(250, 1, Direction.BACKWARDS);
    private final Animation hoverAnimation = new DecelerateAnimation(250, 1, Direction.BACKWARDS);

    public KeybindComponent(KeybindSetting keybindSetting) {
        super(keybindSetting);
    }


    @Override
    public void initGui() {

    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
        if (binding) {
            if (keyCode == Keyboard.KEY_SPACE || keyCode == Keyboard.KEY_ESCAPE || keyCode == Keyboard.KEY_DELETE) {
                getSetting().setCode(Keyboard.KEY_NONE);
            } else {
                getSetting().setCode(keyCode);
            }

            typing = false;
            binding = false;
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY) {

        clickAnimation.setDirection(binding ? Direction.FORWARDS : Direction.BACKWARDS);

        String bind = Keyboard.getKeyName(getSetting().getCode());

        float fullTextWidth = Font16.getStringWidth("Bind: §l" + bind);

        float startX = x + width / 2f - fullTextWidth / 2f;
        float startY = y + Font16.getMiddleOfBox(height);

        boolean hovering = HoveringUtil.isHovering(startX - 3, startY - 2, fullTextWidth + 6, Font16.getHeight() + 4, mouseX, mouseY);
        hoverAnimation.setDirection(hovering ? Direction.FORWARDS : Direction.BACKWARDS);

        Color rectColor = ColorUtil.brighter(settingRectColor, .7f - (.25f * hoverAnimation.getOutput().floatValue()));
        RoundedUtil.drawRound(startX - 3, startY - 2, fullTextWidth + 6, Font16.getHeight() + 4, 0, rectColor);


        Font16.drawCenteredString("Bind: §l" + bind, x + width / 2f, y + Font16.getMiddleOfBox(height), textColor);



    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        String bind = Keyboard.getKeyName(getSetting().getCode());
        String text = "§fBind: §r" + bind;
        float textWidth = Font18.getStringWidth(text);
        float startX = x + width / 2f - textWidth / 2f;
        float startY = y + Font18.getMiddleOfBox(height);
        float rectHeight = Font18.getHeight() + 4;

        boolean hovering = HoveringUtil.isHovering(startX - 3, startY - 2, textWidth + 6, Font18.getHeight() + 4, mouseX, mouseY);

        if (isClickable(startY + rectHeight) && hovering && button == 0) {
            binding = true;
            typing = true;
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {

    }
}

package de.tired.api.userinterface.element;

import de.tired.api.extension.Extension;
import de.tired.api.util.font.FontManager;
import de.tired.api.userinterface.UI;
import de.tired.api.util.shader.renderapi.AnimationUtil;

import java.awt.*;

public class ElementHoveringButton {

    private final UI ui;

    private final int x, y;

    public float animation;

    private final int size;

    private boolean over;

    private final String text;

    public ElementHoveringButton(UI ui, int x, int y, int size, String text) {
        this.ui = ui;
        this.x = x;
        this.y = y;
        this.text = text;
        this.size = size;
    }


    public void renderButtonText() {

        FontManager.SFPRO.drawString(text, x / 2 * (x / 2), y, -1);

    }

    public void renderButtonRectangle(int mouseX, int mouseY) {

        animation = (float) AnimationUtil.getAnimationState(animation, over ? 1110 : 0, Math.max(3.6D, Math.abs((double) animation - 1200)) * 4);

        over = ui.mouseWithinCircle(mouseX, mouseY, x, y, size);

        Extension.EXTENSION.getGenerallyProcessor().renderProcessor.push();

        Extension.EXTENSION.getGenerallyProcessor().renderProcessor.renderObjectCircle(x, y, size, new Color(20, 20, 20).getRGB());


        Extension.EXTENSION.getGenerallyProcessor().renderProcessor.renderObjectCircle(x, y, animation, Integer.MIN_VALUE);


        if (over)
            Extension.EXTENSION.getGenerallyProcessor().renderProcessor.renderObjectCircle(x, y, size, new Color(30, 30, 30).getRGB());


        Extension.EXTENSION.getGenerallyProcessor().renderProcessor.pop();

    }

}

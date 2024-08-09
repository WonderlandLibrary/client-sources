package ru.FecuritySQ.clickgui.elements;


import ru.FecuritySQ.font.Fonts;
import ru.FecuritySQ.module.дисплей.ClickGui;
import ru.FecuritySQ.option.imp.OptionBoolean;
import ru.FecuritySQ.utils.ColorUtil;
import ru.FecuritySQ.utils.MathUtil;
import ru.FecuritySQ.utils.RenderUtil;

import java.awt.*;

public class ElementBoolean extends Element {

    private ElementModule module;

    public OptionBoolean optionBoolean;
    public boolean isHovered;
    public float animation = 4F;
    public ElementBoolean(ElementModule elementModule, OptionBoolean optionBoolean){
        this.module = elementModule;
        this.optionBoolean = optionBoolean;
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        int x = (int) getX();
        int y = (int) getY();
        int width = (int) getWidth();
        int height = (int) getHeight();
        int middleHeight = (int) (getHeight() / 2);
        isHovered = collided(mouseX, mouseY);
        Color color = ClickGui.color.get();
        RenderUtil.drawRound(x + width - 20, y + 4, 18, height - 8, 4, new Color(0, 0, 0, 50).getRGB());
        animation = MathUtil.animation(animation, optionBoolean.get() ? 0 : 8, 0);
        if (Double.isNaN(animation)) animation = optionBoolean.get() ? 0 : 8;
        RenderUtil.drawRoundCircle(x + width - 7 - animation, y + height - 8.5F, 4, ColorUtil.interpolateColorC(color, new Color(50, 50, 50), animation / 8F).getRGB());
        Fonts.GREYCLIFF.drawString(stack, optionBoolean.getName(), x + 2F, y + middleHeight - 6, Color.GRAY.getRGB());
    }

    @Override
    public void mouseClicked(int x, int y, int button) {
        if(collided(x, y)){
            this.optionBoolean.set(!optionBoolean.get());
        }
        super.mouseClicked(x, y, button);
    }
}

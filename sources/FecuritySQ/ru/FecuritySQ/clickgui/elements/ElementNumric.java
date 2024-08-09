package ru.FecuritySQ.clickgui.elements;

import net.minecraft.util.math.MathHelper;
import ru.FecuritySQ.font.Fonts;
import ru.FecuritySQ.module.дисплей.ClickGui;
import ru.FecuritySQ.option.imp.OptionNumric;
import ru.FecuritySQ.utils.MathUtil;
import ru.FecuritySQ.utils.RenderUtil;

import java.awt.*;

public class ElementNumric extends Element{

    public ElementModule elementModule;
    public OptionNumric option;

    public float currentValueAnimate = 0f;
    public boolean sliding = false;
    public boolean isHovered;

    public ElementNumric(ElementModule e, OptionNumric option){
        this.elementModule = e;
        this.option = option;
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        this.width = elementModule.panel.width - 8;
        this.height = 18;
        int x = (int) getX() + 4;
        int y = (int) getY();
        int width = (int) getWidth();
        int height = (int) getHeight();
        double min = option.getMin();
        double max = option.getMax();
        isHovered = collided(mouseX, mouseY);
        Color color = ClickGui.color.get();

        if (this.sliding) {
            option.setCurrent((float) MathHelper.clamp(MathUtil.round((float) ((mouseX - x) * (max - min) / width + min), option.increment), min, max));
        }

        float amountWidth = (float) ((MathHelper.clamp(option.getCurrent(), min, max) - min) / (max - min));

        currentValueAnimate = MathUtil.animation(currentValueAnimate, amountWidth, 0);
        if (Double.isNaN(currentValueAnimate)) currentValueAnimate = amountWidth;
        float optionValue = (float) MathUtil.round(option.getCurrent(), option.increment);


        RenderUtil.drawRound(x, y + height - 7, width, 1.8F, 1, new Color(50, 50, 50).getRGB());

        RenderUtil.drawRound(x, y + height - 7, currentValueAnimate * width, 1.8F, 1, color.getRGB());

        RenderUtil.drawRoundCircle(x + currentValueAnimate * width, y + height - 6.5F, 6, new Color(21, 21, 21).getRGB());
        RenderUtil.drawRoundCircle(x + currentValueAnimate * width, y + height - 6.5F, 5, color.getRGB());
        RenderUtil.drawRoundCircle(x + currentValueAnimate * width, y + height - 6.5F, 3, new Color(50, 50, 50).getRGB());

        Fonts.GREYCLIFF.drawString(stack, option.name, x - 2, y + height / 2.5F - 10F, Color.GRAY.getRGB());
        Fonts.GREYCLIFF.drawString(stack, optionValue + "", x + width - Fonts.GREYCLIFF.getStringWidth(optionValue + "") - 1, y + height / 2.5F - 10F, Color.GRAY.getRGB());
    }

    @Override
    public void mouseClicked(int x, int y, int button) {
        if (!sliding && button == 0 && isHovered) {
            sliding = true;
        }
        super.mouseClicked(x, y, button);
    }

    @Override
    public void mouseReleased(int x, int y, int button) {
        sliding = false;
        super.mouseReleased(x, y, button);
    }
}

package ru.FecuritySQ.clickgui.elements;

import ru.FecuritySQ.FecuritySQ;
import ru.FecuritySQ.font.Fonts;
import ru.FecuritySQ.module.дисплей.Theme;
import ru.FecuritySQ.option.imp.OptionTheme;
import ru.FecuritySQ.utils.RenderUtil;

public class ElementTheme extends Element{
    private OptionTheme localTheme;
    public ElementTheme(OptionTheme theme){
         localTheme = theme;
    }
    @Override
    public void draw(int mouseX, int mouseY) {

        Fonts.mntsb16.drawString(stack, localTheme.getName(), getX() , getY() + 2, FecuritySQ.get().theme == localTheme.getTheme() ? localTheme.getTheme().color.getRGB() : -1);
        RenderUtil.drawRound((float) (getX() + 50), (float) getY(), 12, 12, 3, localTheme.getTheme().color.getRGB());
        RenderUtil.drawRound((float) (getX() + 67), (float) getY(), 12, 12, 3,  localTheme.getTheme().color2.getRGB());
    }

    @Override
    public void mouseClicked(int x, int y, int button) {
        super.mouseClicked(x, y, button);
        if (ishover((float) (getX()), (float) getY(), 95, 15, x, y) && button == 0) {FecuritySQ.get().theme = localTheme.getTheme();
        }
    }
    public static boolean ishover(float xx, float yy, float width, float height, double mouseX, double mouseY) {
        if (mouseX > xx && mouseX < width + xx && mouseY > yy && mouseY < yy + height) {
            return true;
        }
        return false;
    }

    @Override
    public double getHeight() {
        return 20D;
    }
}

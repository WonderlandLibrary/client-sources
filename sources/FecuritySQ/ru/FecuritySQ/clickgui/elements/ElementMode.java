package ru.FecuritySQ.clickgui.elements;


import ru.FecuritySQ.font.Fonts;
import ru.FecuritySQ.module.дисплей.ClickGui;
import ru.FecuritySQ.option.imp.OptionMode;

import java.awt.*;

public class ElementMode extends Element{

    public boolean open;

    public ElementModule elementModule;
    public OptionMode mode;
    public ElementMode(ElementModule e, OptionMode mode){
        this.elementModule = e;
        this.mode = mode;
    }

    public float elementOpenHeight = 15;

    @Override
    public void draw(int mouseX, int mouseY) {
        this.width = this.elementModule.panel.width;
        int x = (int) getX();
        int y = (int) getY();
        int width = (int) getWidth();
        int height = (int) getHeight();
        String selectedText = mode.current();
        int textColor = Color.GRAY.getRGB();
        Color color = ClickGui.color.get();
        Fonts.GREYCLIFF.drawString(stack, mode.getName() + ": ", x + 2.5F, y + 2.5F, Color.GRAY.getRGB());
        Fonts.GREYCLIFF.drawString(stack, selectedText, x + elementModule.panel.width - Fonts.GREYCLIFF.getStringWidth(selectedText) - 3, y + 2F, color.getRGB());

        if (open) {

            handleRender(x, (int) (y + getHeight() + 4), width, textColor);
            this.setHeight(getHeightWithExpand());
        }

    }

    private void handleRender(int x, int y, int width, int textColor) {
        Color color = ClickGui.color.get();

        for (String e : mode.modes) {

            Fonts.GREYCLIFF.drawCenteredString(stack, e, x + (elementModule.panel.width / 2), y, mode.get().equals(e) ? color.getRGB() : textColor);

            y += (elementOpenHeight - 3);
        }
    }

    public int getHeightWithExpand() {
        return (int) (getHeight() + mode.modes.length * (elementOpenHeight - 3) + 4);
    }

    private void handleClick(int mouseX, int mouseY, int x, int y, int width) {
        for (String e : this.mode.modes) {
            if (mouseX >= x && mouseY >= y && mouseX <= x + width && mouseY <= y + elementOpenHeight - 3) {
                mode.set(e);
            }

            y += elementOpenHeight - 3;
        }
    }


    @Override
    public void mouseClicked(int x, int y, int button) {

        if(open){
            handleClick(x, y, (int) getX(), (int) (getY() + 18), (int) getWidth());
        }

        if(collided(x, y) && button == 1){
            this.open = !this.open;
        }

        super.mouseClicked(x, y, button);
    }
}

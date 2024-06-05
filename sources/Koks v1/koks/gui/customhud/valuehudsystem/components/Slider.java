package koks.gui.customhud.valuehudsystem.components;

import koks.Koks;
import koks.gui.customhud.valuehudsystem.ValueHUD;
import net.minecraft.client.gui.Gui;

/**
 * @author avox | lmao | kroko
 * @created on 06.09.2020 : 04:34
 */
public class Slider extends ComponentValue {

    public boolean dragging;

    public Slider(ValueHUD value) {
        super(value);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY) {
        double sliderCurrent = (getValue().getCurrent() - getValue().getMin()) / (getValue().getMax() - getValue().getMin());
        Gui.drawRect(getX(), getY(), getX() + getWidth(), getY() + getHeight(), Integer.MIN_VALUE);
        Gui.drawRect(getX(), getY(), getX() + sliderCurrent * getWidth(), getY() + getHeight(), Koks.getKoks().client_color.getRGB());

        getMcFontRenderer().drawStringWithShadow(getValue().getValueName() + " : " + getValue().getCurrent(), getX() + 2, getY() + getHeight() / 2 - getMcFontRenderer().FONT_HEIGHT / 2, -1);
        updateSlider(mouseX);
    }

    public void updateSlider(double x) {
        if (dragging) {
            float floatValue = (Math.round(Math.max(Math.min((x - this.getX()) / this.getWidth() * (getValue().getMax() - getValue().getMin()) + getValue().getMin(), getValue().getMax()), getValue().getMin()) * 100.0F) / 100.0F);
            if (getValue().isOnlyInt())
                floatValue = (int) floatValue;
            getValue().setCurrent(floatValue);
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isHovering(mouseX, mouseY) && mouseButton == 0)
            dragging = true;
    }


    public boolean isHovering(int mouseX, int mouseY) {
        return mouseX > getX() && mouseX < getX() + getWidth() && mouseY > getY() && mouseY < getY() + getHeight();
    }

    @Override
    public void mouseReleased() {
        dragging = false;
    }

}

package koks.gui.customhud.valuehudsystem.components;

import koks.Koks;
import koks.gui.customhud.valuehudsystem.ValueHUD;
import net.minecraft.client.gui.Gui;

import java.awt.*;

/**
 * @author avox | lmao | kroko
 * @created on 06.09.2020 : 03:39
 */
public class CheckBox extends ComponentValue {

    public CheckBox(ValueHUD value) {
        super(value);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY) {
        Gui.drawRect(getX(), getY(), getX() + getWidth(), getY() + getHeight(), Integer.MIN_VALUE);

        if (getValue().isToggled())
            getRenderUtils().drawRect(7, getX() + 3, getY() + getHeight() / 2 - 4, getX() + 11, getY() + getHeight() / 2 + 4, Koks.getKoks().client_color);

        getRenderUtils().drawOutlineRect(getX() + 3, getY() + getHeight() / 2 - 4, getX() + 11, getY() + getHeight() / 2 + 4, 1, Color.black);
        getMcFontRenderer().drawStringWithShadow(getValue().getValueName(), getX() + 14, getY() + getHeight() / 2 - getMcFontRenderer().FONT_HEIGHT / 2, -1);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isHovering(mouseX, mouseY) && mouseButton == 0) {
            getValue().setToggled(!getValue().isToggled());
        }
    }

    public boolean isHovering(int mouseX, int mouseY) {
        return mouseX > getX() + 3 && mouseX < getX() + 11 && mouseY > getY() + getHeight() / 2 - 4 && mouseY < getY() + getHeight() / 2 + 4;
    }

    @Override
    public void mouseReleased() {

    }

}

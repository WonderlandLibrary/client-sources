package koks.gui.customhud.valuehudsystem.components;

import koks.Koks;
import koks.gui.customhud.valuehudsystem.ValueHUD;
import net.minecraft.client.gui.Gui;

import java.util.Arrays;

/**
 * @author avox | lmao | kroko
 * @created on 06.09.2020 : 04:34
 */
public class ComboBox extends ComponentValue {

    public boolean extended;

    public ComboBox(ValueHUD value) {
        super(value);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY) {

        Gui.drawRect(getX(), getY(), getX() + getWidth(), getY() + getHeight(), Integer.MIN_VALUE);
        getMcFontRenderer().drawStringWithShadow(getValue().getValueName(), getX() + getWidth() / 2 - getMcFontRenderer().getStringWidth(getValue().getValueName()) / 2, getY() + getHeight() / 2 - getMcFontRenderer().FONT_HEIGHT / 2, -1);

        if (!extended) return;

        int[] yBox = {getY() + getHeight()};
        Arrays.stream(getValue().getComboArray()).forEach(value -> {
            Gui.drawRect(getX(), yBox[0], getX() + getWidth(), yBox[0] + 15, Integer.MIN_VALUE);
            getMcFontRenderer().drawStringWithShadow(value, getX() + getWidth() / 2 - getMcFontRenderer().getStringWidth(value) / 2, yBox[0] + 15 / 2 - getMcFontRenderer().FONT_HEIGHT / 2, value.equalsIgnoreCase(getValue().getSelectedComboMode()) ? Koks.getKoks().client_color.getRGB() : -1);
            yBox[0] += 15;
        });

    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isHovering(mouseX, mouseY) && mouseButton == 0) {
            this.extended = !this.extended;
        }
        if (!extended)
            return;
        int[] yBox = {getY() + getHeight()};
        Arrays.stream(getValue().getComboArray()).forEach(value -> {
            if (mouseX > getX() && mouseX < getX() + getWidth() && mouseY > yBox[0] && mouseY < yBox[0] + 15 && mouseButton == 0)
                getValue().setSelectedComboMode(value);
            yBox[0] += 15;
        });
    }

    public boolean isHovering(int mouseX, int mouseY) {
        return mouseX > getX() && mouseX < getX() + getWidth() && mouseY > getY() && mouseY < getY() + getHeight();
    }

    @Override
    public void mouseReleased() {

    }

}

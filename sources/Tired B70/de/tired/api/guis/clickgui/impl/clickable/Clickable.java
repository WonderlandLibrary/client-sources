package de.tired.api.guis.clickgui.impl.clickable;


import de.tired.api.util.font.CustomFont;
import de.tired.interfaces.FHook;
import de.tired.interfaces.IHook;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Clickable implements IHook, FHook {

    @Getter
    @Setter
    public int width = 90, height = 20;

    public int calculateMiddle(String text, CustomFont fontRenderer, double x, double widht) {
        return (int) ((float) (x + widht) - (fontRenderer.getStringWidth(text) / 2f) - (float) widht / 2);
    }

    public boolean isOver(int x, int y, int width, int height, int mouseX, int mouseY) {
        return mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height;
    }

}
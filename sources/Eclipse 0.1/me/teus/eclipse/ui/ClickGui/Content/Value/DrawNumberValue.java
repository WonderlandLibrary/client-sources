package me.teus.eclipse.ui.ClickGui.Content.Value;

import me.teus.eclipse.modules.value.impl.NumberValue;
import me.teus.eclipse.utils.font.CFontRenderer;
import me.teus.eclipse.utils.font.FontLoaders;
import net.minecraft.client.gui.Gui;

import java.awt.*;

public class DrawNumberValue {
    public static void drawNumberValue(NumberValue s, int startX, int startY, int endX, int endY, int mouseX, int mouseY) {
        CFontRenderer fr = FontLoaders.tenacity18;

        double lenght = endX - startX;

        double numberX = startX + ((s.getValue() - s.getMin()) * lenght / (s.getMax() - s.getMin()));

        Gui.drawRect(startX - 1, startY, endX + 1, endY, new Color(50, 50, 50).getRGB());

        Gui.drawRect(startX - 1, startY + 13, (int) numberX + 1, endY, -1);

        fr.drawStringWithShadow(s.getName(), startX + 3, startY + 3, new Color(240, 240, 240).getRGB());
        fr.drawStringWithShadow(String.valueOf(s.getValue()), startX + lenght - fr.getStringWidth(String.valueOf(s.getValue())) - 3, startY + 3, new Color(240, 240, 240).getRGB());

        if(s.isHoldingMouseButton()) {
            onNumberHold(s, startX, endX, mouseX, mouseY);
        }
    }

    public static void onNumberHold(NumberValue s, int startX, int endX, int mouseX, int mouseY) {
        double lenght = endX - startX;

        double mousePos = mouseX - startX;
        double thing = (mousePos / lenght);

        s.setValue(thing * (s.getMax() - s.getMin()) + s.getMin());
    }
}

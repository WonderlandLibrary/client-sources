package me.teus.eclipse.ui.ClickGui.Content.Value;

import me.teus.eclipse.modules.value.impl.ModeValue;
import me.teus.eclipse.utils.font.CFontRenderer;
import me.teus.eclipse.utils.font.FontLoaders;
import net.minecraft.client.gui.Gui;

import java.awt.*;

public class DrawModeValue {
    public static void drawModeValue(ModeValue s, int startX, int startY, int endX, int endY) {
        CFontRenderer fr = FontLoaders.tenacity18;

        double lenght = endX - startX;

        Gui.drawRect(startX - 1, startY, endX + 1, endY, new Color(50, 50, 50).getRGB());

        int color = new Color(240, 240, 240).getRGB();

        fr.drawStringWithShadow(s.getName(), startX + 3, startY + 3, color);
        fr.drawStringWithShadow(s.getMode(), startX + lenght - fr.getStringWidth(s.getMode()) - 3, startY + 3, -1);
    }

    public static void onModeClicked(ModeValue s, int button) {
        if(button == 0) {
            s.cycle(true);
        } else if(button == 1) {
            s.cycle(false);
        }
    }
}

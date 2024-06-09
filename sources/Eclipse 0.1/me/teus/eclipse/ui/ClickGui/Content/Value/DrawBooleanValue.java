package me.teus.eclipse.ui.ClickGui.Content.Value;

import me.teus.eclipse.modules.value.impl.BooleanValue;
import me.teus.eclipse.utils.font.CFontRenderer;
import me.teus.eclipse.utils.font.FontLoaders;
import net.minecraft.client.gui.Gui;

import java.awt.*;

public class DrawBooleanValue {
    public static void drawBooleanValue(BooleanValue s, int startX, int startY, int endX, int endY) {
        CFontRenderer fr = FontLoaders.tenacity18;

        Gui.drawRect(startX - 1, startY, endX + 1, endY, new Color(50, 50, 50).getRGB());

        fr.drawStringWithShadow(s.getName(), startX + 3, startY + 3, -1);
        fr.drawStringWithShadow(s.isEnabled() ? "X" : "", startX + 88, startY + 3, -1);
    }

    public static void onBooleanClicked(BooleanValue s) {
        s.setEnabled(!s.isEnabled());
    }
}

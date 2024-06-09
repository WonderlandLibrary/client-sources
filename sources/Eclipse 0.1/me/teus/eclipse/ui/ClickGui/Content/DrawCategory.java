package me.teus.eclipse.ui.ClickGui.Content;

import me.teus.eclipse.modules.Category;
import me.teus.eclipse.utils.font.FontLoaders;
import net.minecraft.client.gui.Gui;

import java.awt.*;

public class DrawCategory {
    public static void drawCategory(Category c, int startX, int startY, int endX, int endY) {
        for(int i = startX; i < endX; i++) {
            Gui.drawRect(i, startY + 5, i + 1, endY, new Color(75, 75, 75, 255).getRGB());
        }

        FontLoaders.tenacityBold21.drawStringWithShadow(c.getName(), startX + 5, startY + 8, new Color(240, 240, 240).getRGB());
    }

    public static void onCategoryClicked(Category c, int mouseX, int mouseY, int button) {
        if(c.isCatOpen() && button == 1) {
            c.setCatOpen(false);
        } else if(!c.isCatOpen() && button == 1) {
            c.setCatOpen(true);
        }
    }
}

package me.teus.eclipse.ui.ClickGui.Content;

import me.teus.eclipse.modules.Module;
import me.teus.eclipse.utils.Utils;
import me.teus.eclipse.utils.font.CFontRenderer;
import me.teus.eclipse.utils.font.FontLoaders;
import net.minecraft.client.gui.Gui;

import java.awt.*;

public class DrawModule implements Utils {
    public static void drawModule(Module m, int startX, int startY, int endX, int endY) {
    CFontRenderer fr = FontLoaders.tenacity21;

    if(m.isToggled()) {
        for(int i = startX; i < endX; i++) {
            Gui.drawRect(i, startY, i + 1, endY, new Color(75, 75, 75).getRGB());
        }
    } else {
        Gui.drawRect(startX, startY, endX, endY, new Color(42, 42, 42).getRGB());
    }
    fr.drawStringWithShadow(m.getName(), startX + 4, startY + 5, new Color(240, 240, 240).getRGB());
}

    public static void onModuleClicked(Module m, int mouseX, int mouse, int button) {
        if(button == 0) {
            if(mc.thePlayer != null) {
                m.toggle();
            } else {
                m.toggle();
            }
        } else if(m.isShowSet() && button == 1) {
            m.setShowSet(false);
        } else if(!m.isShowSet() && button == 1) {
            m.setShowSet(true);
        }
    }
}

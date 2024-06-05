package koks.gui.panelgui.guistuff;

import koks.Koks;
import koks.modules.Module;
import koks.utilities.CustomFont;
import net.minecraft.client.gui.Gui;

import java.awt.*;

/**
 * @author avox | lmao | kroko
 * @created on 10.09.2020 : 09:36
 */
public class DrawModule {

    public final CustomFont arial20 = new CustomFont("fonts/arial.ttf", 20);
    public final CustomFont arial18 = new CustomFont("fonts/arial.ttf", 18);
    public final CustomFont arial16 = new CustomFont("fonts/arial.ttf", 16);
    public int x, y, width, height;
    public Module module;

    public DrawModule(Module module) {
        this.module = module;
    }

    public void updatePosition(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        Gui.drawRect(x, y, x + width, y + height, 0xFF202020);
        Color textColor = module.isToggled() ? Koks.getKoks().client_color : Color.WHITE;
        arial18.drawString(module.getDisplayName(), x + width / 2 - arial18.getStringWidth(module.getDisplayName()) / 2, y + height / 2 - arial18.FONT_HEIGHT / 2, isHovered(mouseX, mouseY) ? textColor.darker().getRGB() : textColor.getRGB());
    }

    public boolean isHovered(int mouseX, int mouseY) {
        return mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height;
    }

    public void keyTyped(char typedChar, int keyCode) {

    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isHovered(mouseX, mouseY)) {
            if (mouseButton == 0) {
                module.toggle();
            }
        }
    }

    public void mouseReleased(int mouseX, int mouseY, int state) {

    }

}
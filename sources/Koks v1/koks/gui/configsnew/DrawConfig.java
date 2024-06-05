package koks.gui.configsnew;

import koks.Koks;
import koks.utilities.CustomFont;

import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * @author avox | lmao | kroko
 * @created on 09.09.2020 : 16:22
 */
public class DrawConfig {

    public final CustomFont configFont18 = new CustomFont("fonts/verdana.ttf", 18);
    public int x, y, width, height;
    String name;
    public File file;

    public DrawConfig(File file) {
        this.file = file;
        name = file.getName().split("\\.")[0];
    }

    public void updateValues(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        int yAdd = mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height ? 1 : 0;
        configFont18.drawString(name, x + width / 2 - configFont18.getStringWidth(name) / 2, y + height / 2 - configFont18.FONT_HEIGHT / 2 - yAdd, file == Koks.getKoks().configManagerFromScreen.currentLoadedConfig ? Koks.getKoks().client_color.getRGB() : file == Koks.getKoks().configManagerFromScreen.currentConfig ? new Color(150, 150,150, 255).getRGB() : 0xFF000000);
    }

    public void keyTyped(char typedChar, int keyCode) throws IOException {

    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (mouseButton == 0 && mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height) {
            if (Koks.getKoks().configManagerFromScreen.currentConfig == file)
                Koks.getKoks().configManagerFromScreen.currentConfig = null;
            else
                Koks.getKoks().configManagerFromScreen.currentConfig = file;
        }
    }

    public void mouseReleased(int mouseX, int mouseY, int state) {

    }

    public String getName() {
        return name;
    }

}
package koks.gui.configs.elements;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;

import java.io.File;
import java.io.IOException;

/**
 * @author avox | lmao | kroko
 * @created on 04.09.2020 : 22:24
 */
public class RightClickScreen extends DrawConfigs {

    private final Minecraft mc = Minecraft.getMinecraft();
    private final FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
    public int x, y, width, height;
    public File file;

    public RightClickScreen(File file) {
        super(file);
        this.file = file;
    }

    public void updateValues(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        System.out.println(choosing);
        Gui.drawRect(x, y, x + 50, y + 30, 0xFF000000);
    }

    public void keyTyped(char typedChar, int keyCode) throws IOException {

    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {

    }

    public void mouseReleased(int mouseX, int mouseY, int state) {

    }

}
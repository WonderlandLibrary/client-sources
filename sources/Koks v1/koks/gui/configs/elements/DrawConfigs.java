package koks.gui.configs.elements;

import koks.Koks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ChatComponentText;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author avox | lmao | kroko
 * @created on 04.09.2020 : 22:24
 */
public class DrawConfigs {

    private final Minecraft mc = Minecraft.getMinecraft();
    private final FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
    private final ArrayList<RightClickScreen> screens = new ArrayList<>();
    public int x, y, width, height, clickX, clickY;
    public final File file;
    public String name;
    public boolean choosing;

    public DrawConfigs(File file) {
        this.file = file;
        String[] named = file.getName().split("\\.");
        this.name = named[0];
    }

    public void updateValues(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        screens.add(new RightClickScreen(file));
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        String[] name = file.getName().split("\\.");
        Gui.drawRect(x + 4, y, x + width - 4, y + height, new Color(28, 28, 28, 255).getRGB());
        fr.drawString(name[0], x + width / 2 - fr.getStringWidth(name[0]) / 2, y + height / 2 - fr.FONT_HEIGHT / 2, Koks.getKoks().configManager.currentConfig.equalsIgnoreCase(name[0]) ? Koks.getKoks().client_color.getRGB() : isHovered(mouseX,mouseY) ? 0xFFFFFFFF : new Color(0xFFFFFFFF).darker().getRGB());

        if (choosing) {
            for (RightClickScreen rightClickScreen : screens) {
                rightClickScreen.updateValues(x + clickX, y + clickY, width, height);
                rightClickScreen.drawScreen(mouseX, mouseY, partialTicks);
            }
        }
    }

    public boolean isHovered(int mouseX, int mouseY) {
        return mouseX > x + 4 && mouseX < x + width - 4 && mouseY > y && mouseY < y + height;
    }

    public void keyTyped(char typedChar, int keyCode) throws IOException {

    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (mouseButton == 1 && isHovered(mouseX, mouseY)) {
            clickX = mouseX - x;
            clickY = mouseY - y;
            choosing = !choosing;
        }else if(mouseButton == 0 && isHovered(mouseX, mouseY)) {
            String[] name = file.getName().split("\\.");
            Koks.getKoks().configManager.loadConfig(name[0]);
            mc.thePlayer.addChatMessage(new ChatComponentText(Koks.getKoks().PREFIX + "§aloaded Config §e" + name[0]));
        }
    }

    public void mouseReleased(int mouseX, int mouseY, int state) {

    }

    public String getName() {
        return name;
    }
}
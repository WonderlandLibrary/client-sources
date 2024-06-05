package koks.gui.configsnew;

import koks.Koks;
import koks.utilities.CustomFont;
import koks.utilities.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.io.File;
import java.io.IOException;

/**
 * @author avox | lmao | kroko
 * @created on 09.09.2020 : 16:51
 */
public class DrawCurrentInfo {

    public final RenderUtils renderUtils = new RenderUtils();
    public final CustomFont configFont25 = new CustomFont("fonts/verdana.ttf", 25);
    public final CustomFont configFont18 = new CustomFont("fonts/verdana.ttf", 18);
    public int x, y, width, iconSize, pngY;
    public String name;
    public File file;

    public DrawCurrentInfo(File file) {
        this.file = file;
        this.name = file.getName().split("\\.")[0];
    }

    public void updateValues(int x, int y, int width) {
        this.x = x;
        this.y = y;
        this.width = width;
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        String author = "Author";
        String clientVersion = "1.0.0 LUUL";
        configFont25.drawString(name, x + width / 2 - configFont25.getStringWidth(name) / 2, y + 5, 0xFF000000);
        Gui.drawRect(x + width / 2 - configFont25.getStringWidth(name) / 2, y + configFont25.FONT_HEIGHT + 3, x + width / 2 + configFont25.getStringWidth(name) / 2 + 1, y + configFont25.FONT_HEIGHT + 4, 0xFF000000);

        configFont18.drawString("Author: ", x + 3, y + 40, 0xFF000000);
        configFont18.drawString("Version: ", x + 3, y + 50, 0xFF000000);
        configFont18.drawString(author, x + width - configFont18.getStringWidth(author) - 4, y + 40, 0xFF000000);
        configFont18.drawString(clientVersion, x + width - configFont18.getStringWidth(clientVersion) - 4, y + 50, 0xFF000000);

        GL11.glPushMatrix();
        GlStateManager.disableAlpha();
        GlStateManager.enableBlend();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glColor4f(1, 1, 1, 1);
        iconSize = 40;
        pngY = 216;
        renderUtils.drawImage(new ResourceLocation("client/configs/accept.png"), x + width - 160 - 5, y + pngY - (hoveredAccept(mouseX, mouseY) ? 3 : 0), iconSize, iconSize, false);
        renderUtils.drawImage(new ResourceLocation("client/configs/delete.png"), x + width - 100 - 5, y + pngY - (hoveredDelete(mouseX, mouseY) ? 3 : 0), iconSize, iconSize, false);
        renderUtils.drawImage(new ResourceLocation("client/configs/save.png"), x + width - 40 - 5, y + pngY - (hoveredSave(mouseX, mouseY) ? 3 : 0), iconSize, iconSize, false);
        GL11.glDisable(GL11.GL_BLEND);
        GlStateManager.enableAlpha();
        GlStateManager.disableBlend();
        GL11.glPopMatrix();
    }

    public boolean hoveredAccept(int mouseX, int mouseY) {
        return mouseX > x + width - 160 + 5 && mouseX < x + width - 160 - 5 + iconSize && mouseY > y + pngY && mouseY < y + pngY + iconSize;
    }

    public boolean hoveredDelete(int mouseX, int mouseY) {
        return mouseX > x + width - 100 + 5 && mouseX < x + width - 100 - 5 + iconSize && mouseY > y + pngY && mouseY < y + pngY + iconSize;
    }

    public boolean hoveredSave(int mouseX, int mouseY) {
        return mouseX > x + width - 40 + 5 && mouseX < x + width - 40 - 5 + iconSize && mouseY > y + pngY && mouseY < y + pngY + iconSize;
    }

    public void keyTyped(char typedChar, int keyCode) throws IOException {

    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (mouseButton == 0 && hoveredAccept(mouseX, mouseY)) {
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText( Koks.getKoks().PREFIX + "Loaded Config: §b" + name.toUpperCase()));
            Koks.getKoks().configManagerFromScreen.currentLoadedConfig = file;
            Koks.getKoks().configManager.loadConfig(name);
        }
        if (mouseButton == 0 && hoveredDelete(mouseX, mouseY)) {
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText( Koks.getKoks().PREFIX + "Deleted Config: §b" + name.toUpperCase()));
            Koks.getKoks().configManager.deleteConfig(name);
        }
        if (mouseButton == 0 && hoveredSave(mouseX, mouseY)) {
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText( Koks.getKoks().PREFIX + "Saved Config: §b" + name.toUpperCase()));
            Koks.getKoks().configManager.createConfig(name);
        }
    }

    public void mouseReleased(int mouseX, int mouseY, int state) {

    }

}
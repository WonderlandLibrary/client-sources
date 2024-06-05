package alphentus.gui.clickguipanel.panel;

import alphentus.init.Init;
import alphentus.mod.Mod;
import alphentus.mod.ModCategory;
import alphentus.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ChatComponentText;

import java.io.IOException;
import java.util.ArrayList;

public class DrawPanel {

    private ArrayList<DrawMods> drawMods = new ArrayList<>();
    private Minecraft mc = Minecraft.getMinecraft();
    private FontRenderer fr = Init.getInstance().fontManager.getFont("arial", 18);
    private ModCategory modCategory;
    private int x, y, width, panelHeight;
    private int dragX, dragY;
    private ScaledResolution sr;
    private boolean dragging;

    public DrawPanel(ModCategory modCategory, int x, int y, int width, int panelHeight) {
        this.modCategory = modCategory;
        this.x = x;
        this.y = y;
        this.width = width;
        this.panelHeight = panelHeight;

        for (Mod mod : Init.getInstance().modManager.getModArrayList()) {
            if (mod.getModCategory() == modCategory) {
                drawMods.add(new DrawMods(mod));
            }
        }
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        sr = new ScaledResolution(mc);

        String s = modCategory.name().substring(0, 1) + modCategory.name().substring(1).toLowerCase();
        Gui.drawRect(x, y, x + width, y + panelHeight, 0xFF303030);
        fr.drawStringWithShadow(s, x + width / 2 - fr.getStringWidth(s) / 2, y + panelHeight / 2 - fr.FONT_HEIGHT / 2, 0xFFFFFFFF);

        int modY = y + panelHeight;
        int modHeight = panelHeight;
        for (DrawMods drawMods : drawMods) {
            drawMods.setValues(x, modY, width, modHeight);
            drawMods.drawScreen(mouseX, mouseY, partialTicks);
            modY += modHeight;
            modY = modY + (drawMods.isExtendedMod() ?  drawMods.getExtendedY() : 0);
        }
        updatePosition(mouseX, mouseY);
    }

    public void updatePosition(int mouseX, int mouseY) {
        if (dragging) {
            x = dragX + mouseX;
            y = dragY + mouseY;
        }
    }

    public boolean isHovered(int mouseX, int mouseY) {
        return mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + panelHeight;
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {

        if (mouseButton == 0 && isHovered(mouseX, mouseY)) {
            dragX = x - mouseX;
            dragY = y - mouseY;
            dragging = true;
        }

        for (DrawMods drawMods : drawMods) {
            drawMods.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }

    public void mouseReleased(int mouseX, int mouseY, int state) {

        dragging = false;

        for (DrawMods drawMods : drawMods) {
            drawMods.mouseReleased(mouseX, mouseY, state);
        }
    }

    public void keyTyped(char typedChar, int keyCode) throws IOException {
        for (DrawMods drawMods : drawMods) {
            drawMods.keyTyped(typedChar, keyCode);
        }
    }

}

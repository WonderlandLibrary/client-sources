package koks.gui.panelgui;

import koks.Koks;
import koks.gui.panelgui.guistuff.DrawModule;
import koks.modules.Module;
import koks.utilities.CustomFont;
import koks.utilities.RenderUtils;
import net.minecraft.client.gui.Gui;

import java.awt.*;
import java.util.ArrayList;

/**
 * @author avox | lmao | kroko
 * @created on 10.09.2020 : 09:35
 */
public class DrawPanel {

    public final ArrayList<DrawModule> drawModules = new ArrayList<>();
    public final CustomFont arial20 = new CustomFont("fonts/arial.ttf", 20);
    public final CustomFont arial18 = new CustomFont("fonts/arial.ttf", 18);
    public final CustomFont arial16 = new CustomFont("fonts/arial.ttf", 16);
    public final Module.Category category;
    public int x, y, width, height, dragX, dragY;
    public boolean dragging, extended;

    public DrawPanel(Module.Category category, int x, int y, int width, int height) {
        this.category = category;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        for (Module module : Koks.getKoks().moduleManager.getModules()) {
            if (module.getModuleCategory() == category) {
                drawModules.add(new DrawModule(module));
            }
        }
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        if (dragging) {
            x = dragX + mouseX;
            y = dragY + mouseY;
        }

        Gui.drawRect(x, y, x + width, y + height, Koks.getKoks().client_color.getRGB());
        String catName = category.name().substring(0, 1) + category.name().substring(1).toLowerCase();
        arial18.drawString(catName, x + width / 2 - arial18.getStringWidth(catName) / 2, y + height / 2 - arial18.FONT_HEIGHT / 2, 0xFFFFFFFF);

        if (extended) {
            int y = this.y + this.height;
            for (DrawModule drawModule : drawModules) {
                drawModule.updatePosition(x, y, width, height);
                drawModule.drawScreen(mouseX, mouseY, partialTicks);
                y += this.height;
            }
            int y2 = this.y + this.height;
            for (DrawModule drawModule : drawModules) {
                if (mouseX > x && mouseX < x + width && mouseY > y2 && mouseY < y2 + height && !drawModule.module.getModuleDescription().equals("")) {
                    String desc = drawModule.module.getModuleDescription();
                    int renderX = mouseX + 6;
                    int renderY = mouseY - 10;
                    RenderUtils renderUtils = new RenderUtils();
                    Gui.drawRect(renderX - 1, renderY, renderX + arial16.getStringWidth(desc) + 2, renderY + arial16.FONT_HEIGHT + 1, Koks.getKoks().client_color.getRGB());
                    renderUtils.drawOutlineRect(renderX - 1, renderY, renderX + arial16.getStringWidth(desc) + 2, renderY + arial16.FONT_HEIGHT + 1, 1, Color.BLACK);
                    arial16.drawString(desc, renderX, renderY, 0xFFFFFFFF);
                }
                y2 += this.height;
            }
        }
    }

    public boolean isHovered(int mouseX, int mouseY) {
        return mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height;
    }

    public void keyTyped(char typedChar, int keyCode) {
        if (extended) {
            for (DrawModule drawModule : drawModules) {
                drawModule.keyTyped(typedChar, keyCode);
            }
        }
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isHovered(mouseX, mouseY)) {
            if (mouseButton == 0) {
                dragX = x - mouseX;
                dragY = y - mouseY;
                dragging = true;
            }

            if (mouseButton == 1) {
                extended = !extended;
            }
        }

        if (extended) {
            for (DrawModule drawModule : drawModules) {
                drawModule.mouseClicked(mouseX, mouseY, mouseButton);
            }
        }
    }

    public void mouseReleased(int mouseX, int mouseY, int state) {
        dragging = false;

        if (extended) {
            for (DrawModule drawModule : drawModules) {
                drawModule.mouseReleased(mouseX, mouseY, state);
            }
        }
    }

}
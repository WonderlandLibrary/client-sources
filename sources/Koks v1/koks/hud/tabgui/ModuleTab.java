package koks.hud.tabgui;

import koks.Koks;
import koks.modules.Module;
import koks.utilities.CustomFont;
import koks.utilities.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

import java.awt.*;

/**
 * @author avox | lmao | kroko
 * @created on 06.09.2020 : 08:35
 */
public class ModuleTab {

    private Module module;
    private boolean expanded;
    private int x, y, width, height;
    private final RenderUtils renderUtils = new RenderUtils();

    private int currentModule = 0;
    private CategoryTab categoryTab;

    public ModuleTab(Module module) {
        this.module = module;
    }

    public void drawScreen(int currentModule, boolean clientColor, boolean centeredString, CustomFont tabGuiLengthFont, CategoryTab categoryTab) {
        this.currentModule = currentModule;
        this.categoryTab = categoryTab;
        switch (Koks.getKoks().getThemeCategory()) {
            case NONE:
                renderUtils.drawRect(7, x, y, x + width, y + height, categoryTab.moduleTabs.get(currentModule).getModule().equals(module) ? clientColor ? Koks.getKoks().client_color : new Color(0, 0, 0, 175) : new Color(0, 0, 0, 125));
                Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(module.getModuleName(), x + (centeredString ? width / 2 - Minecraft.getMinecraft().fontRendererObj.getStringWidth(module.getModuleName()) / 2 : 2), y + height / 2 - Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT / 2, categoryTab.moduleTabs.get(currentModule).getModule().equals(module) ? module.isToggled() ? Koks.getKoks().client_color.brighter().getRGB() : -1 : module.isToggled() ? Koks.getKoks().client_color.getRGB() : -1);
                break;
            default:
                Koks.getKoks().themeManager.getThemeList().forEach(theme -> {
                    if (theme.getThemeCategory().equals(Koks.getKoks().getThemeCategory())) {
                        theme.moduleTabGUI(this, x, y, width, height);
                    }
                });
                break;
        }
    }

    public void drawScreen(int currentModule, boolean clientColor, boolean centeredString,CategoryTab categoryTab) {
        this.currentModule = currentModule;
        this.categoryTab = categoryTab;
        switch (Koks.getKoks().getThemeCategory()) {
            case NONE:
                renderUtils.drawRect(7, x, y, x + width, y + height, categoryTab.moduleTabs.get(currentModule).getModule().equals(module) ? clientColor ? Koks.getKoks().client_color : new Color(0, 0, 0, 175) : new Color(0, 0, 0, 125));
                Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(module.getModuleName(), x + (centeredString ? width / 2 - Minecraft.getMinecraft().fontRendererObj.getStringWidth(module.getModuleName()) / 2 : 2), y + height / 2 - Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT / 2, categoryTab.moduleTabs.get(currentModule).getModule().equals(module) ? module.isToggled() ? Koks.getKoks().client_color.brighter().getRGB() : -1 : module.isToggled() ? Koks.getKoks().client_color.getRGB() : -1);
                break;
        }
    }

    public void setInformation(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public boolean isSelectedModule() {
        return categoryTab.moduleTabs.get(currentModule).getModule().equals(module);
    }

    public Module getModule() {
        return module;
    }
}

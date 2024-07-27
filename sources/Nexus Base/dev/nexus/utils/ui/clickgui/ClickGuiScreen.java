package dev.nexus.utils.ui.clickgui;

import dev.nexus.Nexus;
import dev.nexus.modules.Module;
import dev.nexus.modules.ModuleCategory;
import dev.nexus.modules.impl.render.ClickGuiModule;
import net.minecraft.client.gui.GuiScreen;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClickGuiScreen extends GuiScreen {

    private Map<ModuleCategory, List<Module>> modulesByCategory;

    @Override
    public void initGui() {
        modulesByCategory = new HashMap<>();

        for (Module module : Nexus.INSTANCE.getModuleManager().getModules()) {
            modulesByCategory.computeIfAbsent(module.getCategory(), k -> new ArrayList<>()).add(module);
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();

        int x = 10;
        int y = 10;
        int categoryWidth = 100;
        int moduleHeight = 20;

        for (ModuleCategory category : ModuleCategory.values()) {
            drawCategory(category, x, y, categoryWidth, moduleHeight, mouseX, mouseY);
            x += categoryWidth + 10;
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    private void drawCategory(ModuleCategory category, int x, int y, int width, int moduleHeight, int mouseX, int mouseY) {
        drawRect(x, y, x + width, y + moduleHeight, new Color(26, 26, 26).getRGB());
        drawString(mc.fontRendererObj, category.name(), x + 5, y + 5, 0xFFFFFFFF);

        y += moduleHeight;

        List<Module> modules = modulesByCategory.get(category);
        if (modules != null) {
            for (Module module : modules) {
                drawModule(module, x, y, width, moduleHeight, mouseX, mouseY);
                y += moduleHeight;
            }
        }
    }

    private void drawModule(Module module, int x, int y, int width, int height, int mouseX, int mouseY) {
        boolean isHovered = mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
        int color = module.isEnabled() ? 0xFF00FF00 : new Color(26, 26, 26).getRGB();
        if (isHovered) {
            color = new Color(color).brighter().getRGB();
        }
        drawRect(x, y, x + width, y + height, color);
        drawString(mc.fontRendererObj, module.getName(), x + 5, y + 5, 0xFFFFFFFF);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        int x = 10;
        int y = 10;
        int categoryWidth = 100;
        int moduleHeight = 20;

        for (ModuleCategory category : ModuleCategory.values()) {
            y += moduleHeight;
            List<Module> modules = modulesByCategory.get(category);
            if (modules != null) {
                for (Module module : modules) {
                    if (mouseX >= x && mouseX <= x + categoryWidth && mouseY >= y && mouseY <= y + moduleHeight) {
                        if (mouseButton == 0) {
                            module.toggle();
                        } else if (mouseButton == 1) {
                            if (!module.getSettings().isEmpty()) {
                                mc.displayGuiScreen(new SettingScreen(module));
                            }
                        }
                        return;
                    }
                    y += moduleHeight;
                }
            }
            x += categoryWidth + 10;
            y = 10;
        }
    }

    @Override
    public void onGuiClosed() {
        Nexus.INSTANCE.getModuleManager().getModule(ClickGuiModule.class).setEnabled(false);
        super.onGuiClosed();
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}

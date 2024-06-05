package koks.theme.themes;

import koks.Koks;
import koks.hud.tabgui.CategoryTab;
import koks.hud.tabgui.ModuleTab;
import koks.modules.impl.visuals.ClearTag;
import koks.theme.Theme;
import koks.utilities.CustomFont;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;
import java.util.Comparator;

/**
 * @author avox | lmao | kroko
 * @created on 12.09.2020 : 09:31
 */
public class Modern extends Theme {

    private final CustomFont arrayFont = new CustomFont("fonts/arial.ttf", 16);
    private final CustomFont tabFont = new CustomFont("fonts/arial.ttf", 18);
    private final CustomFont logoFont = new CustomFont("fonts/arial.ttf", 26);

    public Modern() {
        super(ThemeCategory.MODERN);
        setUpTabGUI(5, 20, 80, 15, false, true, tabFont);
    }

    @Override
    public void arrayListDesign() {
        int y[] = {0};
        ScaledResolution sr = new ScaledResolution(mc);
        int offset = arrayFont.FONT_HEIGHT + 1;
        Koks.getKoks().moduleManager.getModules().stream().sorted(Comparator.comparingDouble(module -> -arrayFont.getStringWidth(Koks.getKoks().moduleManager.getModule(ClearTag.class).isToggled() ? module.getDisplayName() : module.getNameForArrayList("§8")))).forEach(module -> {
            if (module.isToggled() && module.isVisible()) {
                String name = Koks.getKoks().moduleManager.getModule(ClearTag.class).isToggled() ? module.getDisplayName() : module.getNameForArrayList("§8");
                Gui.drawRect(sr.getScaledWidth() - arrayFont.getStringWidth(name) - 3, y[0], sr.getScaledWidth(), y[0] + offset, 0xFFFFFFFF);
                arrayFont.drawString(name, sr.getScaledWidth() - arrayFont.getStringWidth(name) - 2, y[0], 0xFF000000);
                y[0] += offset;
            }
        });
    }

    @Override
    public void waterMarkDesign() {
        logoFont.drawString(Koks.getKoks().CLIENT_NAME + " v" + Koks.getKoks().CLIENT_VERSION, 5, 3, 0xFFFFFFFF);
    }

    @Override
    public void categoryTabGUI(CategoryTab categoryTab, int x, int y, int width, int height) {
        Gui.drawRect(x, y, x + width, y + height, 0xFFFFFFFF);
        if (categoryTab.isCurrentCategory()) {
            Gui.drawRect(x - 1, y - 1, x + width + 1, y + height + 1, new Color(240, 240, 240, 255).getRGB());
            getRenderUtils().drawShadow(x - 1, y - 1,  width + 2, height + 1);
        }
        String name = categoryTab.category.name().substring(0, 1).toUpperCase() + categoryTab.category.name().substring(1).toLowerCase();
        tabFont.drawString(name, x + 3, y + height / 2 - mc.fontRendererObj.FONT_HEIGHT / 2, 0xFF000000);
        super.categoryTabGUI(categoryTab, x, y, width, height);
    }

    @Override
    public void moduleTabGUI(ModuleTab moduleTab, int x, int y, int width, int height) {
        Gui.drawRect(x, y, x + width, y + height, 0xFFFFFFFF);
        if (moduleTab.isSelectedModule()) {
            Gui.drawRect(x - 1, y - 1, x + width + 1, y + height + 1, new Color(240, 240, 240, 255).getRGB());
            getRenderUtils().drawShadow(x - 1, y - 1,  width + 2, height + 1);
        }
        String name = moduleTab.getModule().getDisplayName();
        tabFont.drawString(name, x + 3, y + height / 2 - mc.fontRendererObj.FONT_HEIGHT / 2, 0xFF000000);
        super.moduleTabGUI(moduleTab, x, y, width, height);
    }

    @Override
    public void hotBarDesign(int x, int y, int width, int height, int chooseX, int chooseWidth) {
        Gui.drawRect(x, y, x + width, y + height, 0xFFFFFFFF);
        Gui.drawRect(chooseX, y, chooseX + chooseWidth, y + height, new Color(240, 240, 240, 255).getRGB());
        getRenderUtils().drawShadow(chooseX, y, chooseWidth, height);
        super.hotBarDesign(x, y, width, height, chooseX, chooseWidth);
    }

    @Override
    public boolean drawTabGUI() {
        return true;
    }

    @Override
    public boolean drawWaterMark() {
        return true;
    }

    @Override
    public boolean drawArrayList() {
        return true;
    }

    @Override
    public boolean drawHotBar() {
        return false;
    }

}
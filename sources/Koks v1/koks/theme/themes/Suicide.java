package koks.theme.themes;

import koks.Koks;
import koks.hud.tabgui.CategoryTab;
import koks.hud.tabgui.ModuleTab;
import koks.theme.Theme;
import koks.utilities.CustomFont;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.Comparator;

/**
 * @author avox | lmao | kroko
 * @created on 12.09.2020 : 08:58
 */
public class Suicide extends Theme {

    private final CustomFont arrayFont = new CustomFont("fonts/tahoma.ttf", 16);

    public Suicide() {
        super(ThemeCategory.SUICIDE);
        setUpTabGUI(0, 30, 80, 13, false, true, arrayFont);
    }

    @Override
    public void arrayListDesign() {
        int y[] = {0};
        ScaledResolution sr = new ScaledResolution(mc);
        int offset = arrayFont.FONT_HEIGHT + 1;
        Koks.getKoks().moduleManager.getModules().stream().sorted(Comparator.comparingDouble(module -> -arrayFont.getStringWidth(module.getFinalNameForArrayList()))).forEach(module -> {
            if (module.isToggled() && module.isVisible()) {
                Gui.drawRect(sr.getScaledWidth() - arrayFont.getStringWidth(module.getFinalNameForArrayList()) - 3, y[0], sr.getScaledWidth(), y[0] + offset, 0x90101010);
                Gui.drawRect(sr.getScaledWidth() - arrayFont.getStringWidth(module.getFinalNameForArrayList()) - 5, y[0], sr.getScaledWidth() - arrayFont.getStringWidth(module.getFinalNameForArrayList()) - 3, y[0] + offset, colorUtil.rainbow(4000, y[0] + offset * 50, 0.4F));
                arrayFont.drawString(module.getFinalNameForArrayList(), sr.getScaledWidth() - arrayFont.getStringWidth(module.getFinalNameForArrayList()) - 2, y[0], colorUtil.rainbow(4000, y[0] + offset * 50, 0.4F));
                y[0] += offset;
            }
        });
    }

    @Override
    public void waterMarkDesign() {
        GL11.glPushMatrix();
        GL11.glScaled(3, 3, 3);
        mc.fontRendererObj.drawStringWithShadow("K", 1, 1, Color.YELLOW.getRGB());
        GL11.glPopMatrix();

        GL11.glPushMatrix();
        GL11.glScaled(2, 2, 2);
        mc.fontRendererObj.drawStringWithShadow("oks", 11, 5, Color.YELLOW.getRGB());
        GL11.glPopMatrix();
    }

    @Override
    public void categoryTabGUI(CategoryTab categoryTab, int x, int y, int width, int height) {
        Gui.drawRect(x, y, x + width, y + height, 0xBB000000);
        if (categoryTab.isCurrentCategory())
            Gui.drawRect(x, y, x + width, y + height, Koks.getKoks().client_color.getRGB());
        String name = categoryTab.category.name().substring(0, 1).toUpperCase() + categoryTab.category.name().substring(1).toLowerCase();
        mc.fontRendererObj.drawStringWithShadow(name, x + 3, y + height / 2 - mc.fontRendererObj.FONT_HEIGHT / 2, 0xFFFFFFFF);
        super.categoryTabGUI(categoryTab, x, y, width, height);
    }

    @Override
    public void moduleTabGUI(ModuleTab moduleTab, int x, int y, int width, int height) {
        Gui.drawRect(x, y, x + width, y + height, 0xBB000000);
        if (moduleTab.isSelectedModule())
            Gui.drawRect(x, y, x + width, y + height, Koks.getKoks().client_color.getRGB());
        String name = moduleTab.getModule().getDisplayName();
        mc.fontRendererObj.drawStringWithShadow(name, x + 3, y + height / 2 - mc.fontRendererObj.FONT_HEIGHT / 2, 0xFFFFFFFF);
        super.moduleTabGUI(moduleTab, x, y, width, height);
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
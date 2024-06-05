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
 * @created on 07.09.2020 : 13:24
 */
public class GAL extends Theme {

    private CustomFont bebasNeue40 = new CustomFont("fonts/BebasNeue-Regular.ttf", 40);
    private CustomFont bebasNeue20 = new CustomFont("fonts/BebasNeue-Regular.ttf", 20);
    private CustomFont bebasNeue22 = new CustomFont("fonts/BebasNeue-Regular.ttf", 22);

    public GAL() {
        super(ThemeCategory.GAL);
        this.setUpTabGUI(5, 40, 80, 16, true, true, bebasNeue20);
    }

    @Override
    public void categoryTabGUI(CategoryTab categoryTab, int x, int y, int width, int height) {
        Gui.drawRect(x, y, x + width, y + height, 0xBB101010);
        if (categoryTab.isCurrentCategory())
            Gui.drawGradientRect(x, y, x + width, y + height, Koks.getKoks().client_color.darker().getRGB(), Koks.getKoks().client_color.brighter().getRGB());
        bebasNeue20.drawString(categoryTab.category.name().substring(0, 1).toUpperCase() + categoryTab.category.name().substring(1).toLowerCase(),x + width / 2 - bebasNeue20.getStringWidth(categoryTab.category.name().substring(0, 1).toUpperCase() + categoryTab.category.name().substring(1).toLowerCase()) / 2,y + height / 2 - bebasNeue20.FONT_HEIGHT / 2,-1);
    }

    @Override
    public void moduleTabGUI(ModuleTab moduleTab, int x, int y, int width, int height) {
        Gui.drawRect(x, y, x + width, y + height, 0xBB101010);
        if (moduleTab.isSelectedModule())
            Gui.drawGradientRect(x, y, x + width, y + height, Koks.getKoks().client_color.darker().getRGB(), Koks.getKoks().client_color.brighter().getRGB());
        bebasNeue20.drawString(moduleTab.getModule().getDisplayName(),x + 3,y + height / 2 - bebasNeue20.FONT_HEIGHT / 2, new Color(255, 255, 255, moduleTab.getModule().isToggled() ? 255 : 125).getRGB());
    }

    @Override
    public void arrayListDesign() {
        int y[] = {0};

        ScaledResolution sr = new ScaledResolution(mc);
        Koks.getKoks().moduleManager.getModules().stream().sorted(Comparator.comparingDouble(module -> -fr.getStringWidth(Koks.getKoks().moduleManager.getModule(ClearTag.class).isToggled() ? module.getDisplayName() : module.getNameForArrayList()))).forEach(module -> {
            if (module.isToggled() && module.isVisible()) {
                String finalString = Koks.getKoks().moduleManager.getModule(ClearTag.class).isToggled() ? module.getDisplayName() : module.getNameForArrayList();
                Gui.drawRect(sr.getScaledWidth() - fr.getStringWidth(finalString) - 5, y[0], sr.getScaledWidth(), y[0] + fr.FONT_HEIGHT, 0x99101010);
                Gui.drawRect(sr.getScaledWidth() - 1, y[0], sr.getScaledWidth(), y[0] + fr.FONT_HEIGHT, colorUtil.rainbow(7000, y[0] * 3, 1.0F));
                fr.drawString(finalString, sr.getScaledWidth() - fr.getStringWidth(finalString) - 3, y[0] + 0.5F, colorUtil.rainbow(7000, y[0] * 3, 1.0F));
                y[0] += fr.FONT_HEIGHT;
                module.getAnimationModule().setYAnimation(fr.FONT_HEIGHT);
            } else {
                module.getAnimationModule().setYAnimation(0);
            }
        });
    }

    @Override
    public void waterMarkDesign() {
        bebasNeue40.drawStringWithShadow(Koks.getKoks().CLIENT_NAME + " §fv" + Koks.getKoks().CLIENT_VERSION, getTabGuiX() + getTabGuiWidth() / 2 - bebasNeue40.getStringWidth(Koks.getKoks().CLIENT_NAME + " v" + Koks.getKoks().CLIENT_VERSION) / 2, 15, Koks.getKoks().client_color.getRGB());
    }

    @Override
    public void hotBarDesign(int x, int y, int width, int height, int chooseX, int chooseWidth) {

    }

    @Override
    public boolean drawHotBar() {
        return false;
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

}

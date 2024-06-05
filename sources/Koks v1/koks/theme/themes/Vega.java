package koks.theme.themes;

import javafx.scene.transform.Scale;
import koks.Koks;
import koks.modules.Module;
import koks.modules.impl.visuals.ClearTag;
import koks.theme.Theme;
import koks.utilities.CustomFont;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

import java.util.Comparator;

/**
 * @author avox | lmao | kroko
 * @created on 07.09.2020 : 23:09
 */
public class Vega extends Theme {

    private CustomFont arrayFont = new CustomFont("fonts/verdana.ttf", 18);

    public Vega() {
        super(ThemeCategory.VEGA);
    }

    @Override
    public void arrayListDesign() {
        int longestString = 0;
        for (Module module : Koks.getKoks().moduleManager.getModules()) {
            String finalString = Koks.getKoks().moduleManager.getModule(ClearTag.class).isToggled() ? module.getDisplayName() : module.getNameForArrayList();
            if (arrayFont.getStringWidth(finalString) > longestString + 6)
                longestString = arrayFont.getStringWidth(finalString) + 6;
        }

        ScaledResolution sr = new ScaledResolution(mc);
        int[] y = {0};
        Koks.getKoks().moduleManager.getModules().stream().sorted(Comparator.comparingDouble(module -> -arrayFont.getStringWidth(Koks.getKoks().moduleManager.getModule(ClearTag.class).isToggled() ? module.getDisplayName() : module.getNameForArrayList()))).forEach(module -> {
            if (module.isToggled() && module.isVisible()) {
                String finalString = Koks.getKoks().moduleManager.getModule(ClearTag.class).isToggled() ? module.getDisplayName() : module.getNameForArrayList();
                arrayFont.drawString(finalString, sr.getScaledWidth() - arrayFont.getStringWidth(finalString) - 3, y[0], -1);
                y[0] += arrayFont.FONT_HEIGHT;
            }
        });

        Gui.drawReversedGradientRect(sr.getScaledWidth() - longestString - 10, 0, sr.getScaledWidth(), y[0] + 2, 0x00000000, 0x60000000);
    }

    @Override
    public void waterMarkDesign() {

    }

    @Override
    public void hotBarDesign(int x, int y, int width, int height, int chooseX, int chooseWidth) {
        ScaledResolution sr = new ScaledResolution(mc);
        Gui.drawRect(0, y, sr.getScaledWidth(), y + height, 0x99000000);
        Gui.drawRect(chooseX, y, chooseX + chooseWidth, y + height, 0x80FFFFFF);
        super.hotBarDesign(x, y, width, height, chooseX, chooseWidth);
    }

    @Override
    public boolean drawTabGUI() {
        return false;
    }

    @Override
    public boolean drawWaterMark() {
        return false;
    }

    @Override
    public boolean drawArrayList() {
        return true;
    }

    @Override
    public boolean drawHotBar() {
        return true;
    }

}
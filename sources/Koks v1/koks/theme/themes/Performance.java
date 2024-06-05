package koks.theme.themes;

import koks.Koks;
import koks.modules.Module;
import koks.theme.Theme;
import net.minecraft.client.gui.ScaledResolution;

/**
 * @author avox | lmao | kroko
 * @created on 10.09.2020 : 14:55
 */
public class Performance extends Theme {

    public Performance() {
        super(ThemeCategory.PERFORMANCE);
    }

    @Override
    public void arrayListDesign() {
        int y = 1;
        ScaledResolution sr = new ScaledResolution(mc);
        for (Module module : Koks.getKoks().moduleManager.getModules()) {
            if (module.isToggled() && module.isVisible()) {
                fr.drawString(module.getDisplayName(), sr.getScaledWidth() - 1 - fr.getStringWidth(module.getModuleName()), y, 0xFFFFFFFF);
                y += fr.FONT_HEIGHT;
                module.getAnimationModule().setYAnimation(fr.FONT_HEIGHT);
            } else {
                module.getAnimationModule().setYAnimation(0);
            }
        }
    }

    @Override
    public void waterMarkDesign() {

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
        return false;
    }
}

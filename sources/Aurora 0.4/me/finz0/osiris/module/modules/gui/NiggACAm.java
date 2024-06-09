package me.finz0.osiris.module.modules.gui;
import com.mojang.realmsclient.gui.ChatFormatting;
import de.Hero.settings.Setting;
import me.finz0.osiris.AuroraMod;
import me.finz0.osiris.module.Module;
import me.finz0.osiris.module.ModuleManager;
import me.finz0.osiris.util.FontUtils;
import me.finz0.osiris.util.Rainbow;

import java.awt.*;
import java.util.Comparator;

public class NiggACAm extends Module {
    public NiggACAm() {
        super("ModListLeft", Category.GUI);
        setDrawn(false);
    }

    int modCount;
    Setting red;
    Setting green;
    Setting blue;
    Setting y;
    int sort;
    Setting sortUp;
    Setting right;
    Setting rainbow;
    Setting customFont;
    Color c;

    public void setup(){
        red = new Setting("Red", this, 255, 0, 255, true, "ModListRed");
        green = new Setting("Green", this, 255, 0, 255, true, "ModListGreen");
        blue = new Setting("Blue", this, 255, 0, 255, true, "ModListBlue");
        AuroraMod.getInstance().settingsManager.rSetting(red);
        AuroraMod.getInstance().settingsManager.rSetting(green);
        AuroraMod.getInstance().settingsManager.rSetting(blue);
        y = new Setting("Y", this, 12, 0, 530, true, "ModListY");
        AuroraMod.getInstance().settingsManager.rSetting(y);
        sortUp = new Setting("SortUp", this, true, "ModListSortUp");
        AuroraMod.getInstance().settingsManager.rSetting(sortUp);
        right = new Setting("AlignRight", this, false, "ModListAlignRight");
        AuroraMod.getInstance().settingsManager.rSetting(right);
        AuroraMod.getInstance().settingsManager.rSetting(rainbow = new Setting("Rainbow", this, false, "ModListRainbow"));
        AuroraMod.getInstance().settingsManager.rSetting(customFont = new Setting("CFont", this, false, "ModListCustomFont"));
    }

    public void onRender(){
        if(rainbow.getValBoolean())
            c = Rainbow.getColor();
        else
            c = new Color((int)red.getValDouble(), (int)green.getValDouble(), (int)blue.getValDouble());

        if(sortUp.getValBoolean()){ sort = -1;
        } else { sort = 1; }
        modCount = 0;
        ModuleManager.getModules()
                .stream()
                .filter(Module::isEnabled)
                .filter(Module::isDrawn)
                .sorted(Comparator.comparing(module -> FontUtils.getStringWidth(customFont.getValBoolean(), module.getName() + ChatFormatting.GRAY + " " + module.getHudInfo()) * (-1)))
                .forEach(m -> {
                    if(sortUp.getValBoolean()) {
                        if (right.getValBoolean()) {
                            drawStringWithShadow(m.getName() + ChatFormatting.GRAY + " " + m.getHudInfo(), 0- FontUtils.getStringWidth(customFont.getValBoolean(), m.getName() + ChatFormatting.GRAY + " " + m.getHudInfo()), (int) y.getValDouble() + (modCount * 10), c.getRGB());
                        } else {
                            drawStringWithShadow(m.getName() + ChatFormatting.GRAY + " " + m.getHudInfo(), 0, (int) y.getValDouble() + (modCount * 10), c.getRGB());
                        }
                        modCount++;
                    } else {
                        if (right.getValBoolean()) {
                            drawStringWithShadow(m.getName() + ChatFormatting.GRAY + " " + m.getHudInfo(), 0 - FontUtils.getStringWidth(customFont.getValBoolean(),m.getName() + ChatFormatting.GRAY + " " + m.getHudInfo()), (int) y.getValDouble() + (modCount * -10), c.getRGB());
                        } else {
                            drawStringWithShadow(m.getName() + ChatFormatting.GRAY + " " + m.getHudInfo(), 0, (int) y.getValDouble() + (modCount * -10), c.getRGB());
                        }
                        modCount++;
                    }
                });
    }

    private void drawStringWithShadow(String text, int x, int y, int color){
        if(customFont.getValBoolean())
            AuroraMod.fontRenderer.drawStringWithShadow(text, x, y, color);
        else
            mc.fontRenderer.drawStringWithShadow(text, x, y, color);
    }
}
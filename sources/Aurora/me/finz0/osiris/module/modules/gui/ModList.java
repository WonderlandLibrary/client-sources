package me.finz0.osiris.module.modules.gui;
import com.mojang.realmsclient.gui.ChatFormatting;
import de.Hero.settings.Setting;
import me.finz0.osiris.module.Module;
import me.finz0.osiris.module.ModuleManager;
import me.finz0.osiris.AuroraMod;
import me.finz0.osiris.util.FontUtils;
import me.finz0.osiris.util.Rainbow;

import java.awt.*;
import java.util.Comparator;

public class ModList extends Module {
    public ModList() {
        super("ModListRight", Category.GUI);
        setDrawn(false);
    }

    int modCount;
    Setting red;
    Setting green;
    Setting blue;
    int sort;
    Setting sortUp;
    Setting right;
    Setting rainbow;
    Setting customFont;
    Color c;
    Setting y;
//ez
    public void setup(){
        red = new Setting("Red", this, 255, 0, 255, true, "ModListRed");
        green = new Setting("Green", this, 255, 0, 255, true, "ModListGreen");
        blue = new Setting("Blue", this, 255, 0, 255, true, "ModListBlue");
        AuroraMod.getInstance().settingsManager.rSetting(red);
        AuroraMod.getInstance().settingsManager.rSetting(green);
        AuroraMod.getInstance().settingsManager.rSetting(blue);
        y = new Setting("Y", this, 0, 0, 530, true, "ModListY");
        AuroraMod.getInstance().settingsManager.rSetting(y);
        sortUp = new Setting("SortUp", this, true, "ModListSortUp");
        AuroraMod.getInstance().settingsManager.rSetting(sortUp);
        AuroraMod.getInstance().settingsManager.rSetting(rainbow = new Setting("Rainbow", this, true, "ModListRainbow"));
        AuroraMod.getInstance().settingsManager.rSetting(customFont = new Setting("CFont", this, true, "ModListCustomFont"));
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
                    .sorted(Comparator.comparing(module -> FontUtils.getStringWidth(customFont.getValBoolean(), ChatFormatting.DARK_GRAY +module.getHudInfo() + ChatFormatting.RESET + " " + module.getName()) * (-1)))
                    .forEach(m -> {
                        if(sortUp.getValBoolean()) {
                             {
                                drawStringWithShadow(ChatFormatting.GRAY+ m.getHudInfo()  +ChatFormatting.RESET+ " " + m.getName(), 960 - FontUtils.getStringWidth(customFont.getValBoolean(), ChatFormatting.DARK_GRAY +m.getHudInfo() + ChatFormatting.RESET + " " + m.getName()), (int) y.getValDouble() + (modCount * 10), c.getRGB());

                            }
                            modCount++;
                        } else {
                             {
                                drawStringWithShadow(ChatFormatting.GRAY+m.getHudInfo() + ChatFormatting.RESET+ " " + m.getName(), 960 - FontUtils.getStringWidth(customFont.getValBoolean(),ChatFormatting.DARK_GRAY +m.getHudInfo() + ChatFormatting.RESET + " " + m.getName()), (int) y.getValDouble() + (modCount * -10), c.getRGB());
                            }                               //m.getName
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

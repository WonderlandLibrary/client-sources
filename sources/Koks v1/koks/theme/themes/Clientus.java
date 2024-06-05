package koks.theme.themes;

import koks.Koks;
import koks.hud.tabgui.CategoryTab;
import koks.hud.tabgui.ModuleTab;
import koks.modules.impl.visuals.ClearTag;
import koks.theme.Theme;
import koks.utilities.ColorUtil;
import koks.utilities.CustomFont;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;

/**
 * @author avox | lmao | kroko
 * @created on 07.09.2020 : 15:00
 */
public class Clientus extends Theme {

    private final CustomFont verdanaWatermark = new CustomFont("fonts/verdana.ttf", 18);
    private final CustomFont verdanaTab = new CustomFont("fonts/verdana.ttf", 16);
    private final CustomFont verdanaArray = new CustomFont("fonts/verdana.ttf", 15);

    public Clientus() {
        super(ThemeCategory.CLIENTUS);
        this.setUpTabGUI(5, 20, 50, 10, true, true, verdanaTab);
    }

    @Override
    public void arrayListDesign() {
        int y[] = {0};

        ScaledResolution sr = new ScaledResolution(mc);
        Koks.getKoks().moduleManager.getModules().stream().sorted(Comparator.comparingDouble(module -> -verdanaArray.getStringWidth(Koks.getKoks().moduleManager.getModule(ClearTag.class).isToggled() ? module.getDisplayName() : module.getNameForArrayList()))).forEach(module -> {
            if (module.isToggled() && module.isVisible()) {
                String finalString = Koks.getKoks().moduleManager.getModule(ClearTag.class).isToggled() ? module.getDisplayName() : module.getNameForArrayList();
                Gui.drawRect(sr.getScaledWidth() - verdanaArray.getStringWidth(finalString) - 7, y[0], sr.getScaledWidth(), y[0] + verdanaArray.FONT_HEIGHT, new Color(0, 0, 0, 100).getRGB());
                verdanaArray.drawString(finalString, sr.getScaledWidth() - verdanaArray.getStringWidth(finalString) - 5, y[0] - 0.5F, -1);
                y[0] += verdanaArray.FONT_HEIGHT;
                module.getAnimationModule().setYAnimation(verdanaArray.FONT_HEIGHT);
            } else {
                module.getAnimationModule().setYAnimation(0);
            }
        });

        float[] counter = {0};
        for (float i = 0; i < y[0]; i += 0.1) {
            Gui.drawRect(sr.getScaledWidth() - 1, i, sr.getScaledWidth(), i + 0.1, colorUtil.rainbow(5000, counter[0], 1));
            counter[0] += 0.5;
        }
    }

    @Override
    public void waterMarkDesign() {
        ScaledResolution sr = new ScaledResolution(mc);
        String name = Koks.getKoks().CLIENT_NAME;
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        String time = dateFormat.format(Calendar.getInstance().getTime());

        String render = name + " §7(" + time + ")";

        verdanaWatermark.drawStringWithShadow(render, getTabGuiX() + getTabGuiWidth() / 2 - verdanaWatermark.getStringWidth(render) / 2, 5, Koks.getKoks().client_color.getRGB());

        int x = (int) Math.round(mc.thePlayer.posX);
        int y = (int) Math.round(mc.thePlayer.posY);
        int z = (int) Math.round(mc.thePlayer.posZ);
        verdanaWatermark.drawStringWithShadow("§7X: §f" + x + " §7Y: §f" + y + " §7Z: §f" + z, 3, sr.getScaledHeight() - 12, -1);
        verdanaWatermark.drawStringWithShadow("§7Version: §f" + Koks.getKoks().CLIENT_VERSION, 3, sr.getScaledHeight() - 22, -1);
    }

    @Override
    public void categoryTabGUI(CategoryTab categoryTab, int x, int y, int width, int height) {
        Gui.drawRect(x, y, x + width, y + height, 0xBB101010);
        if (categoryTab.isCurrentCategory())
            Gui.drawRect(x, y, x + width, y + height, Koks.getKoks().client_color.getRGB());
        String finalString = categoryTab.category.name().substring(0, 1) + categoryTab.category.name().substring(1).toLowerCase();
        verdanaTab.drawStringWithShadow(finalString, x + width / 2 - verdanaTab.getStringWidth(finalString) / 2, y + height / 2 - verdanaTab.FONT_HEIGHT / 2, -1);
        super.categoryTabGUI(categoryTab, x, y, width, height);
    }

    @Override
    public void moduleTabGUI(ModuleTab moduleTab, int x, int y, int width, int height) {
        Gui.drawRect(x, y, x + width, y + height, 0xBB101010);
        if (moduleTab.isSelectedModule())
            Gui.drawRect(x, y, x + width, y + height, Koks.getKoks().client_color.getRGB());
        verdanaTab.drawStringWithShadow(moduleTab.getModule().getDisplayName(), x + 3, y + height / 2 - verdanaTab.FONT_HEIGHT / 2, new Color(255, 255, 255, moduleTab.getModule().isToggled() ? 255 : 125).getRGB());
        super.moduleTabGUI(moduleTab, x, y, width, height);
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
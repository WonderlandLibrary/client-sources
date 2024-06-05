package koks.theme;

import koks.Koks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.resources.I18n;
import org.lwjgl.Sys;

import java.awt.*;
import java.io.IOException;

/**
 * @author avox | lmao | kroko
 * @created on 07.09.2020 : 08:07
 */
public class ThemeSelection extends GuiScreen {

    private final Koks koks = Koks.getKoks();

    @Override
    public void initGui() {
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        int[] cords = {105, scaledResolution.getScaledHeight() / 2 - 60};
        this.buttonList.add(new GuiButton(2020, 20, cords[1], 80, 20, "NONE"));
        for (int i = 0; i < koks.themeManager.getThemeList().size(); i++) {
            Theme getTheme = koks.themeManager.getThemeList().get(i);
            String themeName = getTheme.getThemeCategory().name().substring(0, 1).toUpperCase() + getTheme.getThemeCategory().name().substring(1).toLowerCase();
            this.buttonList.add(new GuiButton(i, cords[0], cords[1], 80, 20, themeName));
            cords[0] += 85;
            if (cords[0] > scaledResolution.getScaledWidth() - 100) {
                cords[0] = 20;
                cords[1] += 30;
            }
        }
        super.initGui();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        drawRect(0, 0, scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight(), new Color(65, 65, 65, 255).getRGB());
        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(koks.getThemeCategory().name(), scaledResolution.getScaledWidth() / 2 - Minecraft.getMinecraft().fontRendererObj.getStringWidth(koks
                .getThemeCategory().name()) / 2, 15, -1);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 2020) {
            koks.setThemeCategory(Theme.ThemeCategory.NONE);
        }
        for (int i = 0; i < koks.themeManager.getThemeList().size(); i++) {
            if (button.id == i) {
                Theme theme = koks.themeManager.getThemeList().get(i);
                koks.setThemeCategory(theme.getThemeCategory());
            }
        }
        super.actionPerformed(button);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

}

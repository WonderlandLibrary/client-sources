package alphentus.config;

import alphentus.init.Init;
import alphentus.mod.mods.hud.HUD;
import alphentus.utils.RenderUtils;
import alphentus.utils.fontrenderer.UnicodeFontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author avox | lmao
 * @since on 07.08.2020.
 */
public class DrawConfigManager extends GuiScreen {

    String currentScreen = "";
    UnicodeFontRenderer fontRenderer;
    ScaledResolution sr;
    ArrayList<Config> configs = new ArrayList<>();
    int x = 250, y, width, center;
    HUD hud = Init.getInstance().modManager.getModuleByClass(HUD.class);

    public DrawConfigManager() {
        configs.addAll(Init.getInstance().configManager.getConfigs());
        fontRenderer = Init.getInstance().fontManager.myinghei21;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        sr = new ScaledResolution(Minecraft.getMinecraft());
        y = 60;
        width = sr.getScaledWidth() - x;
        center = sr.getScaledWidth() / 2;

        RenderUtils.relativeRect(0, 0, sr.getScaledWidth(), sr.getScaledHeight(), new Color(0, 0, 0, 125).getRGB());
        Init.getInstance().blurUtil.blurWholeScreen(3);

        int cornerRadius = 15; // max 20
        RenderUtils.drawFilledCircle(center - (center - x) + cornerRadius, 30 + cornerRadius, cornerRadius, hud.guiColor4);
        Gui.drawRect(center - (center - x) + cornerRadius, 30, center, 50, hud.guiColor4.getRGB());
        Gui.drawRect(center - (center - x), 30 + cornerRadius, center, 50, hud.guiColor4.getRGB());
        fontRenderer.drawStringWithShadow("Client Configs", center - (center - x) / 2 - fontRenderer.getStringWidth("Client Configs") / 2, 40 - fontRenderer.getStringHeight("Client Configs") / 2, (currentScreen.equals("Client Configs") ? Init.getInstance().CLIENT_COLOR.getRGB() : hud.textColor.getRGB()), false);

        RenderUtils.drawFilledCircle(center + (center - x) - cornerRadius, 30 + cornerRadius, cornerRadius, hud.guiColor4);
        Gui.drawRect(center, 30, center + (center - x) - cornerRadius, 50, hud.guiColor4.getRGB());
        Gui.drawRect(center, 30 + cornerRadius, center + (center - x), 50, hud.guiColor4.getRGB());
        fontRenderer.drawStringWithShadow("Locale Configs", center + (center - x) / 2 - fontRenderer.getStringWidth("Locale Configs") / 2, 40 - fontRenderer.getStringHeight("Locale Configs") / 2, (currentScreen.equals("Locale Configs") ? Init.getInstance().CLIENT_COLOR.getRGB() : hud.textColor.getRGB()), false);


        // Middle
        Gui.drawRect(x, 50, width, sr.getScaledHeight() - 50 - cornerRadius, hud.guiColor4.getRGB());
        // Bottom Line for Circle
        Gui.drawRect(x + cornerRadius, 50, width - cornerRadius, sr.getScaledHeight() - 50, hud.guiColor4.getRGB());
        RenderUtils.drawFilledCircle(x + cornerRadius, sr.getScaledHeight() - 50 - cornerRadius, cornerRadius, hud.guiColor4);
        RenderUtils.drawFilledCircle(width - cornerRadius, sr.getScaledHeight() - 50 - cornerRadius, cornerRadius, hud.guiColor4);

        if (currentScreen.equals("Client Configs")) {
            for (Config config : configs) {
                DrawConfigs drawConfigs = new DrawConfigs(config, x, y, width);
                drawConfigs.drawScreen(mouseX, mouseY, partialTicks, hud.guiColor3, hud.textColor);
                y += 20;
            }
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    public boolean isHoveredClient(float mouseX, float mouseY) {
        return mouseX > x && mouseX < x / 2 + width / 2 && mouseY > 30 && mouseY < 50;
    }

    public boolean isHoveredLocale(float mouseX, float mouseY) {
        return mouseX > x / 2 + width / 2 && mouseX < width && mouseY > 30 && mouseY < 50;
    }

    @Override
    public void initGui() {
        currentScreen = "Client Configs";
        for (Config config : configs) {
            DrawConfigs drawConfigs = new DrawConfigs(config, x, y, width);
            drawConfigs.initGui();
            y += 20;
        }
        super.initGui();
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        int y = 60;
        if (mouseButton == 0) {
            if (isHoveredClient(mouseX, mouseY))
                currentScreen = "Client Configs";
            if (isHoveredLocale(mouseX, mouseY))
                currentScreen = "Locale Configs";
        }
        for (Config config : configs) {
            DrawConfigs drawConfigs = new DrawConfigs(config, x, y, width);
            drawConfigs.mouseClicked(mouseX, mouseY, mouseButton);
            y += 20;
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }
}
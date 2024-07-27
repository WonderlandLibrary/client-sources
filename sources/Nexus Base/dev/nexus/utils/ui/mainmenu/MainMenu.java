package dev.nexus.utils.ui.mainmenu;

import dev.nexus.utils.client.ThemeUtil;
import dev.nexus.utils.render.DrawUtils;
import dev.nexus.utils.ui.altmanager.AltManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.resources.I18n;

import java.awt.*;
import java.io.IOException;

public class MainMenu extends GuiScreen {

    private static final int BUTTON_WIDTH = 200;
    private static final int BUTTON_HEIGHT = 20;
    private static final int BUTTON_SPACING = 5;

    @Override
    public void initGui() {
        super.initGui();

        int buttonWidth = BUTTON_WIDTH;
        int buttonHeight = BUTTON_HEIGHT;
        int buttonSpacing = BUTTON_SPACING;
        int startX = (this.width - buttonWidth) / 2;
        int startY = (this.height - (buttonHeight * 5 + buttonSpacing * 4)) / 2;

        this.buttonList.clear();

        this.buttonList.add(new CustomGuiButton(0, startX, startY, buttonWidth, buttonHeight, I18n.format("menu.singleplayer"),  ThemeUtil.getMainColor()));
        this.buttonList.add(new CustomGuiButton(1, startX, startY + buttonHeight + buttonSpacing, buttonWidth, buttonHeight, I18n.format("menu.multiplayer"), ThemeUtil.getMainColor()));
        this.buttonList.add(new CustomGuiButton(2, startX, startY + 2 * (buttonHeight + buttonSpacing), buttonWidth, buttonHeight, "Alt Manager",  ThemeUtil.getMainColor()));
        this.buttonList.add(new CustomGuiButton(3, startX, startY + 3 * (buttonHeight + buttonSpacing), buttonWidth, buttonHeight, I18n.format("menu.options"),  ThemeUtil.getMainColor()));
        this.buttonList.add(new CustomGuiButton(4, startX, startY + 4 * (buttonHeight + buttonSpacing), buttonWidth, buttonHeight, I18n.format("menu.quit"),  ThemeUtil.getMainColor()));
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        switch (button.id) {
            case 0:
                Minecraft.getMinecraft().displayGuiScreen(new GuiSelectWorld(this));
                break;
            case 1:
                Minecraft.getMinecraft().displayGuiScreen(new GuiMultiplayer(this));
                break;
            case 2:
                Minecraft.getMinecraft().displayGuiScreen(new AltManager());
                break;
            case 3:
                Minecraft.getMinecraft().displayGuiScreen(new GuiOptions(this, Minecraft.getMinecraft().gameSettings));
                break;
            case 4:
                Minecraft.getMinecraft().shutdown();
                break;
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        DrawUtils.drawRect(0, 0, this.width, this.height, new Color(27, 27, 27).getRGB());
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}

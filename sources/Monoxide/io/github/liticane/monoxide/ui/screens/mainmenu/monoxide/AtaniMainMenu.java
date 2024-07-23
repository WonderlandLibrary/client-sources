package io.github.liticane.monoxide.ui.screens.mainmenu.monoxide;

import io.github.liticane.monoxide.ui.screens.mainmenu.monoxide.altmanager.GuiAccountManager;
import io.github.liticane.monoxide.ui.screens.mainmenu.monoxide.guis.AtaniMultiPlayerMenu;
import io.github.liticane.monoxide.protection.GithubAPI;
import io.github.liticane.monoxide.ui.screens.mainmenu.monoxide.oldaltmanager.MonoxideAltManager;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.util.ResourceLocation;
import io.github.liticane.monoxide.ui.elements.background.ShaderBackground;
import io.github.liticane.monoxide.ui.screens.mainmenu.monoxide.button.AtaniButton;
import io.github.liticane.monoxide.ui.screens.mainmenu.monoxide.guis.AtaniSinglePlayerMenu;
import io.github.liticane.monoxide.util.discord.DiscordRP;
import io.github.liticane.monoxide.util.interfaces.ClientInformationAccess;
import io.github.liticane.monoxide.util.render.RenderUtil;

import java.awt.*;
import java.io.IOException;

public class AtaniMainMenu extends GuiScreen implements GuiYesNoCallback, ClientInformationAccess
{
    public static ShaderBackground shaderBackground;

    public AtaniMainMenu() {
        if(shaderBackground == null) {
            shaderBackground = new ShaderBackground(new ResourceLocation("atani/shaders/fragment/ataniWave.glsl"));
            shaderBackground.init();
        }
    }

    public boolean doesGuiPauseGame()
    {
        return false;
    }

    protected void keyTyped(char typedChar, int keyCode) throws IOException { /* */ }

    public void initGui() {
        DiscordRP.update("Mwain Mwenuw", String.format("Lwoggwed in aws %s [ID: %s]", GithubAPI.username, GithubAPI.uid));
        this.buttonList.clear();
        int fullButtonHeight = 4 * 30;
        int buttonX = this.width / 2 - 100;
        int buttonY = this.height / 2 - fullButtonHeight / 2;
        this.buttonList.add(new AtaniButton(0, buttonX, buttonY, "SinglePlayer"));
        buttonY += 30;
        this.buttonList.add(new AtaniButton(1, buttonX, buttonY, "MultiPlayer"));
        buttonY += 30;
        this.buttonList.add(new AtaniButton(2, buttonX, buttonY, "Alt Manager"));
        buttonY += 30;
        this.buttonList.add(new AtaniButton(3, buttonX, buttonY, 95, 20, "Options"));
        this.buttonList.add(new AtaniButton(5, buttonX + 105, buttonY, 95, 20, "Quit"));
    }
    /**
     * Called by the controls from the buttonList when activated. (Mouse pressed for buttons)
     */
    protected void actionPerformed(GuiButton button) throws IOException {
        switch (button.id) {
            case 0:
                mc.displayGuiScreen(new AtaniSinglePlayerMenu(this));
                break;
            case 1:
                mc.displayGuiScreen(new AtaniMultiPlayerMenu(this));
                break;
            case 2:
                mc.displayGuiScreen(new MonoxideAltManager());
                break;
            case 3:
                mc.displayGuiScreen(new GuiOptions(this, mc.gameSettings));
                break;
            case 5:
                mc.shutdown();
                break;
        }
    }
    /**
     * Draws the screen and all the components in it. Args : mouseX, mouseY, renderPartialTicks
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        RenderUtil.drawRect(0, 0, this.width, this.height, new Color(16, 16, 16).getRGB());
        shaderBackground.render();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    /**
     * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }
}

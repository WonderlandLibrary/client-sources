package com.alan.clients.ui.menu.impl.main;

import com.alan.clients.Client;
import com.alan.clients.font.Fonts;
import com.alan.clients.font.Weight;
import com.alan.clients.ui.menu.Menu;
import com.alan.clients.ui.menu.component.button.MenuButton;
import com.alan.clients.ui.menu.component.button.impl.MenuTextButton;
import com.alan.clients.ui.menu.impl.account.AccountManagerScreen;
import com.alan.clients.util.MouseUtil;
import com.alan.clients.util.animation.Animation;
import com.alan.clients.util.animation.Easing;
import com.alan.clients.util.font.Font;
import com.alan.clients.util.render.ColorUtil;
import com.alan.clients.util.render.RenderUtil;
import com.alan.clients.util.shader.RiseShaders;
import com.alan.clients.util.shader.base.ShaderRenderType;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiSelectWorld;
import net.minecraft.client.gui.ScaledResolution;
import rip.vantage.network.core.Network;

import java.awt.*;
import java.io.IOException;

import static com.alan.clients.layer.Layers.BLUR;
import static com.alan.clients.layer.Layers.REGULAR;

public final class MainMenu extends Menu {

    // "Logo" animation
    private Animation animation = new Animation(Easing.EASE_OUT_QUINT, 600);

    private MenuTextButton singlePlayerButton;
    private MenuTextButton multiPlayerButton;
    private MenuTextButton altManagerButton;
    private MenuTextButton optionsButton;

    private MenuButton[] menuButtons;

    private boolean rice;

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        if (this.singlePlayerButton == null || this.multiPlayerButton == null || this.altManagerButton == null) {
            return;
        }

        ScaledResolution scaledResolution = mc.scaledResolution;

        // Renders the background
        RiseShaders.MAIN_MENU_SHADER.run(ShaderRenderType.OVERLAY, partialTicks, null);

        getLayer(BLUR).add(() -> RenderUtil.rectangle(0, 0, scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight(), Color.BLACK));

        // Renders the buttons
        for (MenuButton menuButton : menuButtons) {
            menuButton.draw(mouseX, mouseY, partialTicks);
        }

        Font fontRenderer = Fonts.MAIN.get(64, Weight.REGULAR);

        // Update the animation
        final double destination = this.singlePlayerButton.getY() - fontRenderer.height();
        this.animation.run(destination);

        // String name
        String name = rice ? "Rice" : Client.NAME;

        // Render the rise "logo"
        final double value = this.animation.getValue();
        final Color color = ColorUtil.withAlpha(Color.WHITE, (int) (value / destination * 200));

        getLayer(REGULAR).add(() -> {
            fontRenderer.drawCentered(name, width / 2.0F, value, color.getRGB());

            // Draw bottom right text
            Fonts.MAIN.get(16, Weight.REGULAR).drawRight(Client.CREDITS,
                    scaledResolution.getScaledWidth() - 5, scaledResolution.getScaledHeight() - 20,
                    ColorUtil.withAlpha(TEXT_SUBTEXT, 100).getRGB());

            Fonts.MAIN.get(12, Weight.REGULAR).drawRight(Client.COPYRIGHT,
                    scaledResolution.getScaledWidth() - 5, scaledResolution.getScaledHeight() - 10,
                    ColorUtil.withAlpha(TEXT_SUBTEXT, 100).getRGB());


            if (!System.getProperty("java.vm.vendor").toLowerCase().contains("oracle corporation")) {
                Font font = Fonts.MAIN.get(32, Weight.BOLD);
            //    font.drawCentered("Unsupported jre, you may encounter bugs, make sure to use the OpenJRE 22 JRE, use this tutorial to install Rise properly, (download open jre instead of azul as displayed in the video) https://youtu.be/j_hsuVAsDr8",
                 //       scaledResolution.getScaledWidth() / 2f + 0.5f, scaledResolution.getScaledHeight() - 50 + 0.5f - font.height(),
               //         ColorUtil.withAlpha(Color.BLACK, 100).getRGB());
            //    font.drawCentered("Unsupported jre, you may encounter bugs, make sure to use the OpenJRE 22 JRE, use this tutorial to install Rise properly, (download open jre instead of azul as displayed in the video) https://youtu.be/j_hsuVAsDr8",
                 //       scaledResolution.getScaledWidth() / 2f, scaledResolution.getScaledHeight() - 50 - font.height(),
                 //       Color.RED.getRGB());
            }
        });
    }

    @Override
    protected void keyTyped(final char typedChar, final int keyCode) throws IOException {
        switch (keyCode) {
            case 203:
//                ChatUtil.display("");
                System.out.println("Reconnecting");
                Network.getInstance().reconnect();
                break;
//            case 205:
//                System.out.println("Reconnecting");
//                break;
//            case 200:
//                Client.INSTANCE.getRecordManager().play();
//                break;
//            case 208:
//                Client.INSTANCE.getRecordManager().getRecordStorage().clear();
//                System.out.println("Cleared Records");
//                break;
//            case 59:
//                hide = !hide;
//                break;
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (this.menuButtons == null) return;

        // If doing a left click and the mouse is hovered over a button, execute the buttons action (runnable)
        if (mouseButton == 0) {
            for (MenuButton menuButton : this.menuButtons) {
                if (MouseUtil.isHovered(menuButton.getX(), menuButton.getY(), menuButton.getWidth(), menuButton.getHeight(), mouseX, mouseY)) {
                    menuButton.runAction();
                    break;
                }
            }
        }
    }

    @Override
    public void initGui() {
        rice = Math.random() > 0.98;
        int centerX = this.width / 2;
        int centerY = this.height / 2;
        int buttonWidth = 180;
        int buttonHeight = 24;
        int buttonSpacing = 6;
        int buttonX = centerX - buttonWidth / 2;
        int buttonY = centerY - buttonHeight / 2 - buttonSpacing / 2 - buttonHeight / 2;

        // Re-creates the buttons for not having to care about the animation reset
        this.singlePlayerButton = new MenuTextButton(buttonX, buttonY, buttonWidth, buttonHeight, () -> mc.displayGuiScreen(new GuiSelectWorld(this)), "Singleplayer");
        this.multiPlayerButton = new MenuTextButton(buttonX, buttonY + buttonHeight + buttonSpacing, buttonWidth, buttonHeight, () -> mc.displayGuiScreen(new GuiMultiplayer(this)), "Multiplayer");
        this.altManagerButton = new MenuTextButton(buttonX + buttonWidth / 2 + buttonSpacing / 2, buttonY + buttonHeight * 2 + buttonSpacing * 2, buttonWidth / 2 - buttonSpacing / 2, buttonHeight, () -> mc.displayGuiScreen(new AccountManagerScreen(this)), "Alts");
        this.optionsButton = new MenuTextButton(buttonX, buttonY + buttonHeight * 2 + buttonSpacing * 2, buttonWidth / 2 - buttonSpacing / 2, buttonHeight, () -> mc.displayGuiScreen(new GuiOptions(this, mc.gameSettings)), "Options");

        // Re-create the logo animation for not having to care about its reset
        this.animation = new Animation(Easing.EASE_OUT_QUINT, 600);

        // Putting all buttons in an array for handling mouse clicks
        this.menuButtons = new MenuButton[]{this.singlePlayerButton, this.multiPlayerButton, this.altManagerButton, this.optionsButton};
//        Client.INSTANCE.getRichPresence().updatePresence("In Main Menu");
    }
}

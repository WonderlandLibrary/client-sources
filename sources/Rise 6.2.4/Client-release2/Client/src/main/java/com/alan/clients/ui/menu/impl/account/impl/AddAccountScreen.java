package com.alan.clients.ui.menu.impl.account.impl;

import com.alan.clients.Client;
import com.alan.clients.font.Fonts;
import com.alan.clients.font.Weight;
import com.alan.clients.ui.menu.component.button.MenuButton;
import com.alan.clients.ui.menu.component.button.impl.MenuAccountTypeButton;
import com.alan.clients.ui.menu.impl.account.AccountManagerScreen;
import com.alan.clients.util.Accessor;
import com.alan.clients.util.MouseUtil;
import com.alan.clients.util.animation.Animation;
import com.alan.clients.util.animation.Easing;
import com.alan.clients.util.font.Font;
import com.alan.clients.util.render.RenderUtil;
import com.alan.clients.util.shader.RiseShaders;
import com.alan.clients.util.shader.base.ShaderRenderType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.io.IOException;

import static com.alan.clients.layer.Layers.BLUR;
import static com.alan.clients.layer.Layers.REGULAR;

public class AddAccountScreen extends GuiScreen implements Accessor {
    private static final Font FONT_RENDERER = Fonts.MAIN.get(36, Weight.BOLD);
    private static final ResourceLocation XBOX_RESOURCE = new ResourceLocation("rise/images/xbox.png");
    private static final ResourceLocation MICROSOFT_RESOURCE = new ResourceLocation("rise/images/microsoft.png");
    private static final ResourceLocation CRACKED_RESOURCE = new ResourceLocation("rise/images/minecraft.png");
    private static final ResourceLocation COOKIE_RESOURCE = new ResourceLocation("rise/images/Cookie.png");

    private static GuiScreen reference;
    private Animation animation;

    /**
     * The runnable that is executed in the background.
     */
    private static final Runnable BACKGROUND_RUNNABLE = () -> {
        ScaledResolution scaledResolution = new ScaledResolution(mc);
        RenderUtil.rectangle(0, 0, scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight(), Color.BLACK);
    };
    private static final Runnable CANCEL_RUNNABLE = () -> {
        
        mc.displayGuiScreen(new AccountManagerScreen(reference));
    };
    private static final Runnable CRACKED_RUNNABLE = () -> {
        
        mc.displayGuiScreen(new AddCrackedScreen());
    };
    private static final Runnable MICROSOFT_RUNNABLE = () -> {
        
        mc.displayGuiScreen(new AddMicrosoftScreen());
    };

    private static final Runnable GAMEPASS_RUNNABLE = () -> {
        
        mc.displayGuiScreen(new AddMicrosoftScreen());
    };
    private static final Runnable COOKIE_RUNNABLE = () -> {
        
        mc.displayGuiScreen(new AddCookieScreen());
    };

    private final MenuAccountTypeButton[] menuButtons = new MenuAccountTypeButton[4];

    public AddAccountScreen() {
        reference = this;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        // Renders the background.
        animation.run(0);
        RiseShaders.MAIN_MENU_SHADER.run(ShaderRenderType.OVERLAY, partialTicks, null);

        getLayer(BLUR).add(BACKGROUND_RUNNABLE);

        // FPS counter on dev builds
        if (Client.DEVELOPMENT_SWITCH) mc.fontRendererObj.drawWithShadow(Minecraft.getDebugFPS() + "", 0, 0, -1);

        // Run post shader things
        getLayer(REGULAR).add(() -> {
            FONT_RENDERER.drawCentered("Select your login method", width / 2, height / 2 - 76 + animation.getValue(), Color.WHITE.getRGB());
        });

        // Renders the buttons.
        for (MenuButton button : menuButtons) {
            button.draw(mouseX, mouseY, partialTicks);
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        for (MenuButton button : menuButtons) {
            if (MouseUtil.isHovered(button.getX(), button.getY(), button.getWidth(), button.getHeight(), mouseX, mouseY)) {
                button.runAction();
                break;
            }
        }
    }

    @Override
    public void initGui() {
        int buttons = menuButtons.length;
        int buttonWidth = 100;
        int buttonHeight = 140;
        int buttonPadding = 5;

        // Calculate the button x position in case new buttons are added.
        int buttonX = width / 2 - ((buttons & 1) == 0 ?
                (buttonWidth + buttonPadding) * (int) (buttons / 2) + buttonPadding / 2 :
                (buttonWidth + buttonPadding) * (int) (buttons / 2) + buttonWidth / 2);

        // Initialize the buttons.
        menuButtons[0] = new MenuAccountTypeButton(0, 0, 0, 0, GAMEPASS_RUNNABLE, "Gamepass", XBOX_RESOURCE);
        menuButtons[1] = new MenuAccountTypeButton(0, 0, 0, 0, MICROSOFT_RUNNABLE, "Microsoft", MICROSOFT_RESOURCE);
        menuButtons[2] = new MenuAccountTypeButton(0, 0, 0, 0, CRACKED_RUNNABLE, "Cracked", CRACKED_RESOURCE);
        menuButtons[3] = new MenuAccountTypeButton(0, 0, 0, 0, COOKIE_RUNNABLE, "Cookie", COOKIE_RESOURCE);

        // Set the button positions.
        for (MenuButton button : menuButtons) {
            button.setX(buttonX);
            button.setY(height / 2 - buttonHeight / 2 + 24);
            button.setWidth(buttonWidth);
            button.setHeight(buttonHeight);
            buttonX += buttonWidth + buttonPadding;
        }

        animation = new Animation(Easing.EASE_OUT_QUINT, 600);
        animation.setStartValue(-200);
    }
}

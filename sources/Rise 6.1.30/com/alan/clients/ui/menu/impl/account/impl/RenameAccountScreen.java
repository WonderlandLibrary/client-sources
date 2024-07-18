package com.alan.clients.ui.menu.impl.account.impl;

import com.alan.clients.Client;
import com.alan.clients.font.Fonts;
import com.alan.clients.font.Weight;
import com.alan.clients.ui.menu.component.button.MenuButton;
import com.alan.clients.ui.menu.component.button.impl.MenuTextButton;
import com.alan.clients.ui.menu.impl.account.AccountManagerScreen;
import com.alan.clients.ui.menu.impl.account.display.AccountViewModel;
import com.alan.clients.util.Accessor;
import com.alan.clients.util.MouseUtil;
import com.alan.clients.util.animation.Animation;
import com.alan.clients.util.animation.Easing;
import com.alan.clients.util.font.Font;
import com.alan.clients.util.gui.textbox.TextAlign;
import com.alan.clients.util.gui.textbox.TextBox;
import com.alan.clients.util.render.RenderUtil;
import com.alan.clients.util.shader.RiseShaders;
import com.alan.clients.util.shader.base.ShaderRenderType;
import com.alan.clients.util.vector.Vector2d;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;
import java.io.IOException;

import static com.alan.clients.layer.Layers.BLUR;

public class RenameAccountScreen extends GuiScreen implements Accessor {
    private static final Font FONT_RENDERER = Fonts.MAIN.get(36, Weight.BOLD);
    private final MenuButton[] menuButtons = new MenuButton[3];
    private static TextBox usernameBox;
    private static GuiScreen reference;
    private AccountViewModel<?> accountViewModel;
    private Animation animation;

    private static final Runnable TEXT_BOX_RUNNABLE = () -> usernameBox.setSelected(true);
    private final Runnable CANCEL_RUNNABLE = () -> {
        
        mc.displayGuiScreen(new AccountManagerScreen(reference));
    };

    private final Runnable UPDATE_RUNNABLE = () -> {
        String username = usernameBox.text;
        if (!validate(username)) {
            return;
        }

        System.out.println("Updating username to " + username);
        accountViewModel.getAccount().setName(username);
        accountViewModel.getAccount().login();
        Client.INSTANCE.getAltManager().update();
        CANCEL_RUNNABLE.run();

        System.out.println("Write");
    };

    private static final Runnable BACKGROUND_RUNNABLE = () -> {
        ScaledResolution scaledResolution = new ScaledResolution(mc);
        RenderUtil.rectangle(0, 0, scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight(), Color.BLACK);
    };

    public RenameAccountScreen(AccountViewModel<?> accountViewModel) {
        this.accountViewModel = accountViewModel;
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
        
        FONT_RENDERER.drawCentered("Update your username", width / 2, height / 2 - 64 + animation.getValue(), Color.WHITE.getRGB());
        accountViewModel.draw();

        // Renders the buttons.
        for (MenuButton button : menuButtons) {
            button.draw(mouseX, mouseY, partialTicks);
        }

        usernameBox.draw();
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        usernameBox.click(mouseX, mouseY, mouseButton);

        for (MenuButton button : menuButtons) {
            if (MouseUtil.isHovered(button.getX(), button.getY(), button.getWidth(), button.getHeight(), mouseX, mouseY)) {
                button.runAction();
                break;
            }
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (usernameBox.isSelected()) {
            usernameBox.key(typedChar, keyCode);
//            accountViewModel.getAccount().setName(usernameBox.text);
        }
    }

    @Override
    public void initGui() {
        int boxWidth = 200;
        int boxHeight = 24;
        int padding = 4;
        float buttonWidth = (boxWidth - padding) / 2.0F;

        Vector2d position = new Vector2d(width / 2 - boxWidth / 2, height / 2 - 32);
        accountViewModel = new AccountViewModel<>(accountViewModel.getAccount(), (float) position.x, (float) position.y, 200, 40);
        accountViewModel.setScreenHeight(height);

        position = new Vector2d(width / 2 - boxWidth / 2, height / 2 + 32);
        usernameBox = new TextBox(position.offset(boxWidth / 2, 8), Fonts.MAIN.get(24, Weight.REGULAR), Color.WHITE, TextAlign.CENTER, "Username", boxWidth);
        usernameBox.setText(accountViewModel.getAccount().getName());

        menuButtons[0] = new MenuTextButton(position.x, position.y, boxWidth, boxHeight, TEXT_BOX_RUNNABLE, "");
        menuButtons[1] = new MenuTextButton(position.x, position.y + boxHeight + padding, buttonWidth, boxHeight, UPDATE_RUNNABLE, "Update");
        menuButtons[2] = new MenuTextButton(position.x + buttonWidth + padding, position.y + boxHeight + padding, buttonWidth, boxHeight, CANCEL_RUNNABLE, "Back");

        animation = new Animation(Easing.EASE_OUT_QUINT, 600);
        animation.setStartValue(-200);
    }

    // Checks if a cracked account is valid
    private boolean validate(String name) {
        if (name.length() < 3 || name.length() > 16) {
            return false;
        }

        for (char c : name.toCharArray()) {
            if (!Character.isLetterOrDigit(c) && c != '_') {
                return false;
            }
        }

        return true;
    }
}

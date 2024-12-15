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
import com.alan.clients.util.account.impl.MicrosoftAccount;
import com.alan.clients.util.animation.Animation;
import com.alan.clients.util.animation.Easing;
import com.alan.clients.util.font.Font;
import com.alan.clients.util.render.RenderUtil;
import com.alan.clients.util.shader.RiseShaders;
import com.alan.clients.util.shader.base.ShaderRenderType;
import com.alan.clients.util.vector.Vector2d;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.io.IOException;

import static com.alan.clients.layer.Layers.BLUR;
import static com.alan.clients.layer.Layers.REGULAR;

public class AddMicrosoftScreen extends GuiScreen implements Accessor {
    private static final Font FONT_RENDERER = Fonts.MAIN.get(36, Weight.BOLD);
    private static final Font INFO_FONT_RENDERER = Fonts.MAIN.get(18, Weight.REGULAR);
    private static AccountViewModel<MicrosoftAccount> accountViewModel;
    private static GuiScreen reference;
    private final MenuButton[] menuButtons = new MenuButton[2];
    private Animation animation;

    private static final Runnable CANCEL_RUNNABLE = () -> {

        mc.displayGuiScreen(new AccountManagerScreen(reference));
    };
    private static final Runnable ADD_RUNNABLE = () -> {
        if (accountViewModel.getAccount().isValid()) {
            AccountManagerScreen.addAccount(accountViewModel.getAccount());
            CANCEL_RUNNABLE.run();
        }
    };

    private static final Runnable BACKGROUND_RUNNABLE = () -> {
        ScaledResolution scaledResolution = new ScaledResolution(mc);
        RenderUtil.rectangle(0, 0, scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight(), Color.BLACK);
    };

    public AddMicrosoftScreen() {
        reference = this;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        // Renders the background.
        if (accountViewModel.getAccount().isValid()) {
            animation.run(36);
        } else {
            animation.run(0);
        }

        RiseShaders.MAIN_MENU_SHADER.run(ShaderRenderType.OVERLAY, partialTicks, null);
        getLayer(BLUR).add(BACKGROUND_RUNNABLE);
        // FPS counter on dev builds
        if (Client.DEVELOPMENT_SWITCH) mc.fontRendererObj.drawWithShadow(Minecraft.getDebugFPS() + "", 0, 0, -1);

        getLayer(REGULAR).add(() -> {
            FONT_RENDERER.drawCentered("Log in to your microsoft account", width / 2, height / 2 - 96 + animation.getValue(), Color.WHITE.getRGB());
            INFO_FONT_RENDERER.drawCentered("A link has been copied to your clipboard.", width / 2, height / 2 - 64 + animation.getValue(), Color.WHITE.darker().getRGB());
            INFO_FONT_RENDERER.drawCentered("To load a cookie, please use firefox and install \"Cookie Quick Manager\".", width / 2, height / 2 - 48 + animation.getValue(), Color.WHITE.darker().getRGB());
            INFO_FONT_RENDERER.drawCentered("To login to your own account, just fill out the form.", width / 2, height / 2 - 32 + animation.getValue(), Color.WHITE.darker().getRGB());
            // Render a loading circle.
            int circleX = width / 2;
            int circleY = height / 2 + 4;
            int radius = 12;

            if (!accountViewModel.getAccount().isValid()) {
                GlStateManager.pushMatrix();
                GlStateManager.translate(circleX, circleY + animation.getValue(), 0);

                GlStateManager.pushMatrix();
                GlStateManager.enableBlend();
                GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                GlStateManager.disableAlpha();
                GlStateManager.disableTexture2D();

                GL11.glEnable(GL11.GL_POINT_SMOOTH);
                GL11.glPointSize(8);
                GL11.glBegin(GL11.GL_POINTS);
                long offset = (long) (Minecraft.getSystemTime() * 0.5);
                offset %= 360;
                offset -= offset % 30;

                for (int i = 0; i < 360; i += 30) {
                    double angle = Math.PI * 2 * i / 360;
                    double cos = Math.cos(angle);
                    double sin = Math.sin(angle);
                    float alpha = 1.0F - ((float) (i + offset) % 360) / 360.0F;
                    GL11.glColor4f(1, 1, 1, alpha);
                    GL11.glVertex2d(cos * radius, sin * radius);
                }

                GL11.glEnd();
                GL11.glDisable(GL11.GL_POINT_SMOOTH);
                GL11.glLineWidth(1);

                GlStateManager.enableTexture2D();
                GlStateManager.disableBlend();
                GlStateManager.enableAlpha();
                GlStateManager.popMatrix();
                GlStateManager.popMatrix();
            }
        });

        accountViewModel.draw();

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
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
    }

    @Override
    public void initGui() {
        int boxWidth = 200;
        int boxHeight = 24;
        int padding = 4;
        float buttonWidth = (boxWidth - padding) / 2.0F;

        Vector2d position = new Vector2d(width / 2 - boxWidth / 2, height / 2 + 76);
        menuButtons[0] = new MenuTextButton(position.x, position.y, buttonWidth, boxHeight, ADD_RUNNABLE, "Add");
        menuButtons[1] = new MenuTextButton(position.x + buttonWidth + padding, position.y, buttonWidth, boxHeight, CANCEL_RUNNABLE, "Back");

        accountViewModel = new AccountViewModel<>(MicrosoftAccount.create(), width / 2 - 100, height / 2 + 32, 200, 40);
        accountViewModel.setScreenHeight(height);

        animation = new Animation(Easing.EASE_OUT_QUINT, 600);
        animation.setStartValue(-200);
    }
}

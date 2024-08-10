package cc.slack.features.modules.impl.render.hud.watermarks.impl;

import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.events.impl.render.RenderEvent;
import cc.slack.features.modules.impl.render.Interface;
import cc.slack.features.modules.impl.render.hud.watermarks.IWatermarks;
import cc.slack.start.Slack;
import cc.slack.utils.font.Fonts;
import cc.slack.utils.font.MCFontRenderer;
import cc.slack.utils.player.PlayerUtil;
import cc.slack.utils.render.ColorUtil;
import cc.slack.utils.render.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Mouse;

import java.awt.*;

public class BackgroundedWatermark implements IWatermarks {
    private double posX = -1D;
    private double posY = -1D;
    private int x = 0;
    private int y = 0;

    private boolean dragging = false;
    private double dragX = 0, dragY = 0;

    private String cachedFontName;
    private MCFontRenderer fontRenderer20;
    private MCFontRenderer fontRenderer18;


    private void updateFontRenderers() {
        String currentFontName = Slack.getInstance().getModuleManager().getInstance(Interface.class).watermarkFont.getValue();
        if (!currentFontName.equals(cachedFontName)) {
            cachedFontName = currentFontName;
            fontRenderer20 = Fonts.getFontRenderer(currentFontName, 20);
            fontRenderer18 = Fonts.getFontRenderer(currentFontName, 18);
        }
    }

    @Override
    public void onRender(RenderEvent event) {
        if (Slack.getInstance().getModuleManager().getInstance(Interface.class).watermarkResetPos.getValue()) {
            posX = -1D;
            posY = -1D;
            Slack.getInstance().getModuleManager().getInstance(Interface.class).watermarkResetPos.setValue(false);
        }

        updateFontRenderers();
        renderBackgroundedRound(
                ColorUtil.getColor(Slack.getInstance().getModuleManager().getInstance(Interface.class).theme.getValue(), 0.15).getRGB(),
                new Color(255, 255, 255, 255).getRGB(),
                new Color(1, 1, 1, 100).getRGB()
        );
    }

    @Override
    public void onUpdate(UpdateEvent event) {
    }

    private void renderBackgroundedRound(int themeColor, int whiteColor, int backgroundColor) {
        String fontName = Slack.getInstance().getModuleManager().getInstance(Interface.class).watermarkFont.getValue();
        boolean rounded = Slack.getInstance().getModuleManager().getInstance(Interface.class).watermarkroundValue.getValue();
        drawBackgroundedText(fontName, themeColor, whiteColor, backgroundColor, rounded);
    }

    private void drawBackgroundedText(String fontName, int themeColor, int whiteColor, int backgroundColor, boolean rounded) {
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        if (posX == -1 || posY == -1) {
            posX = 2;
            posY = 2;
        }

        int mouseX = Mouse.getX() * sr.getScaledWidth() / Minecraft.getMinecraft().displayWidth;
        int mouseY = sr.getScaledHeight() - Mouse.getY() * sr.getScaledHeight() / Minecraft.getMinecraft().displayHeight - 1;

        if (dragging) {
            posX = mouseX - dragX;
            posY = mouseY - dragY;
        }

        x = (int) posX;
        y = (int) posY;

        String text1 = "S";
        String text2 = "lack ";
        String text3 = " | ";
        String text4 = (Minecraft.getMinecraft().isIntegratedServerRunning()) ? "SinglePlayer" : PlayerUtil.getRemoteIp();
        String text5 = " | ";
        String text6 = Minecraft.getDebugFPS() + " FPS";

        int width1 = fontRenderer20.getStringWidth(text1);
        int width2 = fontRenderer20.getStringWidth(text2);
        int width3 = fontRenderer18.getStringWidth(text3);
        int width4 = fontRenderer18.getStringWidth(text4);
        int width5 = fontRenderer18.getStringWidth(text5);
        int width6 = fontRenderer18.getStringWidth(text6);

        int totalWidth = width1 + width2 + width3 + width4 + width5 + width6 + 4;

        int rectWidth = totalWidth + 10;
        int rectHeight = 15;

        int rectX = x;
        int rectY = y;

        if (rounded) {
            RenderUtil.drawRoundedRect(rectX, rectY, rectX + rectWidth, rectY + rectHeight , Slack.getInstance().getModuleManager().getInstance(Interface.class).customroundValue.getValue(), backgroundColor);
        } else {
            drawRect(rectX, rectY, rectX + rectWidth, rectY + rectHeight, backgroundColor);
        }

        int textX = rectX + 4;
        int textY = rectY + 5;
        fontRenderer20.drawStringWithShadow(text1, textX, textY, themeColor);
        textX += width1 + 1;
        fontRenderer20.drawStringWithShadow(text2, textX, textY, whiteColor);
        textX += width2 + 1;
        fontRenderer18.drawStringWithShadow(text3, textX, textY, whiteColor);
        textX += width3 + 1;
        fontRenderer18.drawStringWithShadow(text4, textX, textY, whiteColor);
        textX += width4 + 1;
        fontRenderer18.drawStringWithShadow(text5, textX, textY, whiteColor);
        textX += width5 + 1;
        fontRenderer18.drawStringWithShadow(text6, textX, textY, whiteColor);

        handleMouseInput(mouseX, mouseY, rectX, rectY, rectWidth, rectHeight);
    }

    private void handleMouseInput(int mouseX, int mouseY, int rectX, int rectY, int rectWidth, int rectHeight) {
        if (Mouse.isButtonDown(0) && Minecraft.getMinecraft().currentScreen instanceof GuiChat) {
            if (!dragging) {
                if (mouseX >= rectX && mouseX <= rectX + rectWidth &&
                        mouseY >= rectY && mouseY <= rectY + rectHeight) {
                    dragging = true;
                    dragX = mouseX - posX;
                    dragY = mouseY - posY;
                }
            }
        } else {
            dragging = false;
        }
    }

    private void drawRect(int x, int y, int width, int height, int color) {
        Gui.drawRect(x, y, x + width, y + height, color);
    }
    @Override
    public String toString() {
        return "Backgrounded";
    }
}

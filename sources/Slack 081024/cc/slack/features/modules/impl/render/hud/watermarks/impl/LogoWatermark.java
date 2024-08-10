package cc.slack.features.modules.impl.render.hud.watermarks.impl;

import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.events.impl.render.RenderEvent;
import cc.slack.features.modules.impl.render.Interface;
import cc.slack.features.modules.impl.render.hud.watermarks.IWatermarks;
import cc.slack.start.Slack;
import cc.slack.utils.render.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;

public class LogoWatermark implements IWatermarks {
    private double posX = 10D;
    private double posY = 10D;
    private boolean dragging = false;
    private double dragX = 0, dragY = 0;

    @Override
    public void onRender(RenderEvent event) {
        if (Slack.getInstance().getModuleManager().getInstance(Interface.class).watermarkResetPos.getValue()) {
            posX = 10D;
            posY = -10D;
            Slack.getInstance().getModuleManager().getInstance(Interface.class).watermarkResetPos.setValue(false);
        }
        renderLogo();
    }

    @Override
    public void onUpdate(UpdateEvent event) {
    }

    private void renderLogo() {
        GlStateManager.enableAlpha();
        GlStateManager.enableBlend();

        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        int mouseX = Mouse.getX() * sr.getScaledWidth() / Minecraft.getMinecraft().displayWidth;
        int mouseY = sr.getScaledHeight() - Mouse.getY() * sr.getScaledHeight() / Minecraft.getMinecraft().displayHeight - 1;

        if (dragging) {
            posX = mouseX - dragX;
            posY = mouseY - dragY;
        }

        int x = (int) posX;
        int y = (int) posY;

        RenderUtil.drawImage(new ResourceLocation("slack/menu/hud.png"), x, y, 20, 33);

        handleMouseInput(mouseX, mouseY, x, y, 20, 33);

        GlStateManager.disableAlpha();
        GlStateManager.disableBlend();
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

    @Override
    public String toString() {
        return "Logo";
    }
}

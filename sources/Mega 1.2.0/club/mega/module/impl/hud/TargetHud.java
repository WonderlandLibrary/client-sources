package club.mega.module.impl.hud;

import club.mega.Mega;
import club.mega.event.impl.EventMouseClicked;
import club.mega.event.impl.EventMouseReleased;
import club.mega.event.impl.EventRender2D;
import club.mega.event.impl.EventDrawGuiScreen;
import club.mega.module.Category;
import club.mega.module.Module;
import club.mega.module.impl.combat.KillAura;
import club.mega.util.*;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import org.lwjgl.opengl.GL11;
import rip.hippo.lwjeb.annotation.Handler;

import java.awt.Color;
import java.util.List;

@Module.ModuleInfo(name = "TargetHud", description = "TargetHud", category = Category.HUD)
public class TargetHud extends Module {

    private double x, y, dragX, dragY, current, width;
    private boolean dragging;

    @Handler
    public final void render2D(final EventRender2D event) {
        if ((AuraUtil.getTarget() == null || !KillAura.getInstance().isToggled()) && !(MC.currentScreen instanceof GuiChat)) {
            current = 0.7;
            return;
        }

        EntityLivingBase entity = MC.currentScreen instanceof GuiChat ? MC.thePlayer : AuraUtil.getTarget();

        current = AnimationUtil.animate(current, 1, 0.005);
        width = AnimationUtil.animate(width, Math.min(111, (entity.getHealth() / entity.getMaxHealth()) * 109.8), 0.5);

        GL11.glPushMatrix();
        GL11.glScaled(current, current, current);
        RenderUtil.drawRoundedRect(x + 10, y - 20, 117, 41, 4, new Color(40, 40, 40));
        RenderUtil.drawRoundedRect(x + 13, y - 18, 111, 32, 4, new Color(1, 1, 1,140));
        RenderUtil.drawRoundedRect(x + 13, y + 16,  111, 2, 0.4, new Color(25,25,25));
        RenderUtil.drawRoundedRect(x + 13, y + 16,  width, 2, 0.4, ColorUtil.getMainColor());
        Mega.INSTANCE.getFontManager().getFont("Roboto medium 20").drawString(entity.getName(), x + 53, y - 14, -1);
        Mega.INSTANCE.getFontManager().getFont("Arial 20").drawString("Health: " + MathUtil.round(Math.min(10, entity.getHealth() /  2), 2, 0.5), x + 53, y, -1);

        final List<NetworkPlayerInfo> NetworkMoment = GuiPlayerTabOverlay.field_175252_a.sortedCopy(MC.thePlayer.sendQueue.getPlayerInfoMap());
        for (final NetworkPlayerInfo networkPlayerInfo : NetworkMoment) {
            if (MC.theWorld.getPlayerEntityByUUID(networkPlayerInfo.getGameProfile().getId()) == entity) {
                GlStateManager.enableCull();
                MC.getTextureManager().bindTexture(networkPlayerInfo.getLocationSkin());
                Gui.drawScaledCustomSizeModalRect((int) x + 17, (int) y - 15, 8.0F, 8.0F, 8, 8, 26, 26, 64.0F, 66.0F);
            }
        }
        GL11.glPopMatrix();
    }

    @Handler
    public final void mouseClicked(final EventMouseClicked event) {
        if (!(MC.currentScreen instanceof GuiChat)) return;

        if (MouseUtil.isInside(event.getMouseX(), event.getMouseY(), x + 10, y - 20, 117, 41) && event.getMouseButton() == 0) {
            dragging = true;
            dragX = event.getMouseX() - x;
            dragY = event.getMouseY() - y;
        }
    }

    @Handler
    public final void mouseReleased(final EventMouseReleased event) {
        if (MC.currentScreen instanceof GuiChat && event.getMouseButton() == 0) dragging = false;
    }

    @Handler
    public final void drawScreen(final EventDrawGuiScreen event) {
        if (!(MC.currentScreen instanceof GuiChat) || !dragging) return;

        x = event.getMouseX() - dragX;
        y = event.getMouseY() - dragY;
    }

    @Override
    public void onEnable() {
        super.onEnable();
        current = 0.7;
        x = RenderUtil.getScaledResolution().getScaledWidth() / 2D;
        y = RenderUtil.getScaledResolution().getScaledHeight() / 2D;
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

}

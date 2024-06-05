package de.tired.notification;

import de.tired.api.extension.Extension;
import de.tired.api.util.font.FontManager;
import de.tired.interfaces.FHook;
import de.tired.interfaces.IHook;
import de.tired.api.util.render.Translate;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class NotificationRenderer implements IHook {

    public final List<SuperNotification> notifications = new ArrayList();

    private int MAX_Y = 30;


    private Translate translate;
    public NotificationRenderer() {
        this.translate = new Translate(0, 0);
    }

    public enum notifyType {
        ERROR,
        INFORMATION
    }

    public notifyType type;

    public String desc;

    public Color color;

    public void sendNotification(notifyType type, String desc, String text, Color color) {
        this.translate = new Translate(0, 0);
        switch (type) {
            case ERROR:
                this.color = new Color(229, 28, 45);
                break;
            case INFORMATION:
                this.color = new Color(123, 182, 97);
        }

        this.desc = desc;
        final ScaledResolution sr = new ScaledResolution(MC);
        this.notifications.add(new SuperNotification(text, sr.getScaledWidth() / 2, -MAX_Y, color));
    }

    public void finalRender() {
        if (notifications.size() != 0) {
            renderNotification();
        }
    }


    public void renderNotification() {
        for (int i = 0; i < this.notifications.size(); ++i) { //-- looping through notification ArrayList.
            final SuperNotification notification = this.notifications.get(i);
            int yP = 0;
            if (notification.saw && notification.timerUtil.reachedTime(600L)) {
                --notification.y;
                if (notification.y <= -MAX_Y) {
                    this.notifications.remove(notification);
                }

            } else if (!notification.saw) {
                ++notification.y;
                notification.timerUtil.doReset();
            }

            if (notification.y >= 10) {
                notification.saw = true;
            }

            final ScaledResolution resolution = new ScaledResolution(Minecraft.getMinecraft());

            translate.interpolate(resolution.getScaledWidth(), resolution.getScaledHeight(), 2);

            GL11.glPushMatrix();

            GL11.glTranslatef(resolution.getScaledWidth() / 12, resolution.getScaledHeight() / 8, 0);
            GL11.glScaled(translate.getX() / resolution.getScaledWidth(), translate.getY() / resolution.getScaledHeight(), 0);
            GL11.glTranslatef(-resolution.getScaledWidth() / 12, -resolution.getScaledHeight() / 8, 0);

            Extension.EXTENSION.getGenerallyProcessor().renderProcessor.drawRect(notification.x - FHook.fontRenderer.getStringWidth(notification.getText()) / 2 - 3 * 7, notification.y - 3, notification.x + FHook.fontRenderer.getStringWidth(notification.getText()) / 2 + 3 * 7, notification.y * 5, Integer.MIN_VALUE);
            FontManager.SFPRO.drawCenteredString(notification.getText(), (float) notification.x, (float) notification.y + 7, -1);
            FontManager.SFPRO.drawCenteredString(desc, (float) notification.x, (float) notification.y + 1 + 22, new Color(235, 235, 235).getRGB());
            yP += 10;
            GlStateManager.popMatrix();
        }


    }
}

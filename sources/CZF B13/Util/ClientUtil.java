package gq.vapu.czfclient.Util;

import gq.vapu.czfclient.UI.ClientNotification;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;
import java.util.ArrayList;

public enum ClientUtil {

    INSTANCE;

    public static ArrayList<ClientNotification> notifications = new ArrayList<>();

    public static void sendClientMessage(String message, ClientNotification.Type type) {
        notifications.add(new ClientNotification(message, type));
    }

    public static int reAlpha(int color, float alpha) {
        Color c = new Color(color);
        float r = ((float) 1 / 255) * c.getRed();
        float g = ((float) 1 / 255) * c.getGreen();
        float b = ((float) 1 / 255) * c.getBlue();
        return new Color(r, g, b, alpha).getRGB();
    }

    public void drawNotifications() {
        ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
        double startY = res.getScaledHeight() - 50;
        final double lastY = startY;
        for (int i = 0; i < notifications.size(); i++) {
            ClientNotification not = notifications.get(i);
            if (not.shouldDelete()) {
                notifications.remove(i);
            } else if (notifications.size() > 10) {
                notifications.remove(i);
            }
            not.draw(startY, lastY);
            startY -= not.getHeight() + 3;
        }
    }

}

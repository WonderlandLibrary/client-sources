package me.superskidder.lune.guis.notification;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

public class NotificationModel {
    public String content;
    public NotificationType type;
    public static float x= (float) new ScaledResolution(Minecraft.getMinecraft()).getScaledWidth_double(),y;

    public NotificationModel(String content, NotificationType type) {
        this.content = content;
        this.type = type;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }
}

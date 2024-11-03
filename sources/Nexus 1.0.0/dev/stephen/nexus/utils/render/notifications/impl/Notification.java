package dev.stephen.nexus.utils.render.notifications.impl;

import dev.stephen.nexus.event.impl.render.EventRender2D;
import dev.stephen.nexus.module.modules.client.Notifications;
import dev.stephen.nexus.utils.mc.ChatFormatting;
import lombok.Getter;
import net.minecraft.client.MinecraftClient;

import java.awt.*;

@Getter
public class Notification {

    private final String text;
    private final long msOnScreen;
    private final long msAdded;
    private final NotificationMoode notificationMoode;

    public Notification(String text, int msOnScreen, NotificationMoode notificationMoode) {
        this.text = text;
        this.msOnScreen = msOnScreen;
        this.notificationMoode = notificationMoode;
        this.msAdded = System.currentTimeMillis();
    }

    public void render(EventRender2D e, int yOffset) {
        int screenWidth = e.getWidth();
        int screenHeight = e.getHeight();

        int padding = 20;

        switch (notificationMoode) {
            case MODULE_ENABLED -> {
                String text = "Module enabled: " + ChatFormatting.GREEN + getText();
                int textWidth = getWidth(text);
                int textHeight = getHeight(text);

                drawBox(e, screenWidth - textWidth - padding - 2, screenHeight - textHeight - yOffset - padding - 2, screenWidth - padding + 2, screenHeight - yOffset - padding + 2);

                drawString(e, text, screenWidth - textWidth - padding, screenHeight - textHeight - yOffset - padding);
            }

            case MODULE_DISABLED -> {
                String text = "Module disabled: " + ChatFormatting.RED + getText();
                int textWidth = getWidth(text);
                int textHeight = getHeight(text);

                drawBox(e, screenWidth - textWidth - padding - 2, screenHeight - textHeight - yOffset - padding - 2, screenWidth - padding + 2, screenHeight - yOffset - padding + 2);

                drawString(e, text, screenWidth - textWidth - padding, screenHeight - textHeight - yOffset - padding);
            }

            case WARNING -> {
                String text = "Warning: " + ChatFormatting.RED + getText();
                int textWidth = getWidth(text);
                int textHeight = getHeight(text);

                drawBox(e, screenWidth - textWidth - padding - 2, screenHeight - textHeight - yOffset - padding - 2, screenWidth - padding + 2, screenHeight - yOffset - padding + 2);

                drawString(e, text, screenWidth - textWidth - padding, screenHeight - textHeight - yOffset - padding);
            }

            case INFORMATION -> {
                String text = "Info: " + ChatFormatting.YELLOW + getText();
                int textWidth = getWidth(text);
                int textHeight = getHeight(text);

                drawBox(e, screenWidth - textWidth - padding - 2, screenHeight - textHeight - yOffset - padding - 2, screenWidth - padding + 2, screenHeight - yOffset - padding + 2);

                drawString(e, text, screenWidth - textWidth - padding, screenHeight - textHeight - yOffset - padding);
            }
        }
    }


    private void drawBox(EventRender2D e, int x1, int y1, int x2, int y2) {
        e.getContext().fill(x1, y1, x2, y2, new Color(0, 0, 0, Notifications.getOpacity()).getRGB());
    }

    private void drawString(EventRender2D event, String name, int x, int y) {
        switch (Notifications.getFontMode()) {
            case "MC":
                event.getContext().drawText(MinecraftClient.getInstance().textRenderer, name, x, y, -1, true);
                break;
        }
    }

    private int getHeight(String string) {
        return switch (Notifications.getFontMode()) {
            case "MC" -> MinecraftClient.getInstance().textRenderer.fontHeight;
            default -> 0;
        };
    }

    private int getWidth(String string) {
        return switch (Notifications.getFontMode()) {
            case "MC" -> MinecraftClient.getInstance().textRenderer.getWidth(string);
            default -> 0;
        };
    }


    public boolean shouldDisappear() {
        return msAdded + msOnScreen < System.currentTimeMillis();
    }
}

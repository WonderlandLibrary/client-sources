package club.bluezenith.ui.notifications;

import java.awt.*;
public enum NotificationType {
    ERROR(new Color(255, 51, 51, 255), "e"),
    INFO(new Color(255,255,255), "i"),
    WARNING(new Color(253, 253, 69), "w"),
    SUCCESS(new Color(74, 252, 74), "s");

    private final Color color;

    private final String iconCode;

    NotificationType(Color color, String iconCode) {
        this.color = color;
        this.iconCode = iconCode;
    }

    public Color getColor(){
            return color;
        }

    public String getIconCode() {
        return iconCode;
    }
}

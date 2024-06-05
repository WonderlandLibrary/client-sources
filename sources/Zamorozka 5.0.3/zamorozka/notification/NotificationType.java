package zamorozka.notification;

import java.awt.*;

import zamorozka.main.Zamorozka;

public enum  NotificationType {
	
    SUCCESS(new Color(100, 255, 100).getRGB(), "g"),
    INFO(new Color(255, 255, 255).getRGB(), "g"),
    ERROR(new Color(255, 100, 100).getRGB(), "d"),
    WARNING(new Color(255, 211, 53).getRGB(), "j");

    private final int color;
    private final String colorstr;
    

    NotificationType(int color, String str) {
        this.color = color;
        this.colorstr = str;
    }

    public final int getColor() {
        return this.color;
    }

    public final String getColorstr() {
        return colorstr;
    }
}

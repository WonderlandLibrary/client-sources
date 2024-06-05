package zamorozka.notification;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import zamorozka.ui.font.CFontRenderer;

public class Notification {
    public static final int HEIGHT = 30;
    private final String title;
    private final String content;
    private final int time;
    private final NotificationType type;
    private final NotificationTimer timer;
    private final Translate2 translate;
    private final CFontRenderer fontRenderer;
    public double scissorBoxWidth;

    public Notification(String title, String content, NotificationType type, CFontRenderer fontRenderer) {
        this.title = title;
        this.content = content;
        this.time = 1200;
        this.type = type;
        this.timer = new NotificationTimer();
        this.fontRenderer = fontRenderer;
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        this.translate = new Translate2((sr.getScaledWidth() - getWidth()), (sr.getScaledHeight() - 30));
    }

    public final int getWidth() {
        return Math.max(100, Math.max(this.fontRenderer.getStringWidth(this.title), this.fontRenderer.getStringWidth(this.content)) + 80);
    }

    public final String getTitle() {
        return this.title;
    }

    public final String getContent() {
        return this.content;
    }

    public final int getTime() {
        return this.time;
    }

    public final NotificationType getType() {
        return this.type;
    }

    public final NotificationTimer getTimer() {
        return this.timer;
    }

    public Translate2 getTranslate() {
        return translate;
    }
}

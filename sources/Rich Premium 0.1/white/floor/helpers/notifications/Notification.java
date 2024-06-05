package white.floor.helpers.notifications;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import white.floor.font.CFontRenderer;
import white.floor.helpers.render.NotifyTimer;
import white.floor.helpers.render.Translate;

public class Notification {
    public static final int HEIGHT = 30;
    private final String title;
    private final String content;
    private final int time;
    private final NotificationType type;
    private final NotifyTimer timer;
    private final Translate translate;
    private final CFontRenderer fontRenderer;
    public double scissorBoxWidth;

    public Notification(String title, String content, NotificationType type, CFontRenderer fontRenderer) {
        this.title = title;
        this.content = content;
        this.time = 2500;
        this.type = type;
        this.timer = new NotifyTimer();
        this.fontRenderer = fontRenderer;
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        this.translate = new Translate((sr.getScaledWidth() * 2 - getWidth()), (sr.getScaledHeight() - 30));
    }

    public final int getWidth() {
        return Math.max(100, Math.max(this.fontRenderer.getStringWidth(this.title), this.fontRenderer.getStringWidth(this.content)) + 90);
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

    public final NotifyTimer getTimer() {
        return this.timer;
    }

    public Translate getTranslate() {
        return translate;
    }
}

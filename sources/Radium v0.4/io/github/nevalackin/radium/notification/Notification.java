package io.github.nevalackin.radium.notification;

import io.github.nevalackin.radium.utils.TimerUtil;
import io.github.nevalackin.radium.utils.Wrapper;
import io.github.nevalackin.radium.utils.render.LockedResolution;
import io.github.nevalackin.radium.utils.render.RenderingUtils;
import io.github.nevalackin.radium.utils.render.Translate;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

public final class Notification {

    private final String title;
    private final String body;
    private final Translate translate;
    private final float width;
    private final float height;
    private final long duration;
    private final int color;
    private final TimerUtil timer;
    private boolean dead;

    public Notification(String title, String body, long duration, NotificationType type) {
        this.title = title;
        this.body = body;
        if (body != null)
            this.width = Math.max(Wrapper.getFontRenderer().getWidth(title),
                    Wrapper.getNameTagFontRenderer().getWidth(body)) + 4;
        else
            this.width = Wrapper.getFontRenderer().getWidth(title) + 4;

        this.height = 27.0F;
        if (Wrapper.getCurrentScreen() == null) {
            LockedResolution lr = RenderingUtils.getLockedResolution();
            this.translate = new Translate(lr.getWidth(), lr.getHeight() - height - 2);
        } else {
            ScaledResolution sr = RenderingUtils.getScaledResolution();
            this.translate = new Translate(sr.getScaledWidth(), sr.getScaledHeight() - height - 2);
        }
        this.duration = duration;
        this.color = type.getColor();
        this.timer = new TimerUtil();
    }

    public Notification(String title, String body, NotificationType type) {
        this(title, body, (title.length() + body.length()) * 20L, type);
    }

    public Notification(String title, NotificationType type) {
        this(title, null, title.length() * 20L, type);
    }

    public Notification(String title, long duration, NotificationType type) {
        this(title, null, duration, type);
    }

    public void render(int width, int height, int index) {
        if (timer.hasElapsed(duration))
            translate.animate(width, height - ((this.height + 2) * index) - 2);
        else
            translate.animate(width - this.width, height - ((this.height + 2) * index) - 2);

        float x = (float) translate.getX();
        float y = (float) translate.getY();

        if (x >= width) {
            this.dead = true;
            return;
        }

        Gui.drawRect(x, y, x + this.width, y + this.height, 0x78000000);

        double progress = ((double) (System.currentTimeMillis() - timer.getCurrentMs()) / duration) * this.width;

        Gui.drawRect(x, y + this.height - 2, x + progress, y + this.height, color);

        if (body != null && body.length() > 0) {
            Wrapper.getFontRenderer().drawString(title, x + 2, y + 2, -1);
            Wrapper.getNameTagFontRenderer().drawString(body, x + 2, y + 14, -1);
        } else
            Wrapper.getFontRenderer().drawString(title, x + 2, y + 9, -1);

    }

    public boolean isDead() {
        return dead;
    }

}

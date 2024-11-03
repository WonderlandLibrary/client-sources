package dev.star.gui.notifications;

import dev.star.utils.Utils;
import dev.star.utils.animations.Animation;
import dev.star.utils.animations.impl.DecelerateAnimation;
import dev.star.utils.render.Theme;
import dev.star.utils.time.TimerUtil;
import dev.star.utils.tuples.Pair;
import lombok.Getter;
import net.minecraft.client.gui.Gui;

import java.awt.*;

@Getter
public class Notification implements Utils {

    private final NotificationType notificationType;
    private final String title, description;
    private final float time;
    private final TimerUtil timerUtil;
    private final Animation animation;

    public Notification(NotificationType type, String title, String description) {
        this(type, title, description, NotificationManager.getToggleTime());
    }

    public Notification(NotificationType type, String title, String description, float time) {
        this.title = title;
        this.description = description;
        this.time = (long) (time * 1000);
        timerUtil = new TimerUtil();
        this.notificationType = type;
        animation = new DecelerateAnimation(250, 1);
    }

    public void drawDefault(float x, float y, float width, float height, float alpha, boolean onlyTitle) {
        Color backgroundColor = new Color(0.0F, 0.0F, 0.0F, 0.60F);
        String seconds = String.valueOf((getTime() - timerUtil.getTime()) / 1000.0);
        String formatted = "(" + seconds.substring(0, seconds.indexOf(".") + 2) + "s) ";
        float titleWidth = verdanaFont20.getStringWidth(getTitle());
        float descriptionWidth = verdanaFont20.getStringWidth(getDescription() + " " + formatted);
        float textWidth = 7 + Math.max(titleWidth, descriptionWidth); // Adjust the padding as needed
        x -= (textWidth / 5);
        Gui.drawRect2(x, y, textWidth , height + 3, backgroundColor.getRGB());
        float percentage = Math.min((timerUtil.getTime() / getTime()), 1);
        Pair<Color, Color> test = Theme.getThemeColors(Theme.getCurrentTheme().getName());
        if (notificationType == NotificationType.SUCCESS) {
            Gui.drawRect2(x, y - 1, textWidth * percentage, 1,test.getSecond().getRGB());
            BoldFont20.drawString(getTitle(), x + 5, y + 4, test.getSecond());
        } else if (notificationType == NotificationType.DISABLE) {
            Gui.drawRect2(x, y - 1, textWidth * percentage, 1,test.getFirst().getRGB());
            BoldFont20.drawString(getTitle(), x + 5, y + 4, test.getFirst());
        } else {
            Gui.drawRect2(x, y - 1, textWidth * percentage, 1,test.getFirst().getRGB());
            BoldFont20.drawString(getTitle(), x + 5, y + 4, test.getFirst());
        }
        verdanaFont20.drawString(getDescription() + " " + formatted, x + 5 , y + 8.5f + verdanaFont20.getHeight(), Color.WHITE);

    }

    public void blurDefault(float x, float y, float width, float height, float alpha) {
        String seconds = String.valueOf((getTime() - timerUtil.getTime()) / 1000.0);
        String formatted = "(" + seconds.substring(0, seconds.indexOf(".") + 2) + "s) ";
        float titleWidth = verdanaFont20.getStringWidth(getTitle());
        float descriptionWidth = verdanaFont20.getStringWidth(getDescription() + " " + formatted);
        float textWidth = 7 + Math.max(titleWidth, descriptionWidth);
        x -= (textWidth / 5);
        Gui.drawRect2(x, y, textWidth , height + 3, Color.BLACK.getRGB());
        float percentage = Math.min((timerUtil.getTime() / getTime()), 1);
        if (notificationType == NotificationType.SUCCESS) {
            Gui.drawRect2(x, y - 1, textWidth * percentage, 1, Color.BLACK.getRGB());
        } else if (notificationType == NotificationType.DISABLE) {
            Gui.drawRect2(x, y - 1, textWidth * percentage, 1, Color.BLACK.getRGB());
        } else {
            Gui.drawRect2(x, y - 1, textWidth * percentage, 1, Color.BLACK.getRGB());
        }
    }
}

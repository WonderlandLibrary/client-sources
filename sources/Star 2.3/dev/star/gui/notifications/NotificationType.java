package dev.star.gui.notifications;

import dev.star.utils.font.FontUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.awt.*;

@Getter
@AllArgsConstructor
public enum NotificationType {
    SUCCESS(new Color(20, 250, 90), FontUtil.NewCheckmark),
    DISABLE(new Color(255, 30, 30), FontUtil.NewError),
    INFO(Color.WHITE, FontUtil.NewINFO),
    WARNING(Color.YELLOW, FontUtil.NewWarning);
    private final Color color;
    private final String icon;

}
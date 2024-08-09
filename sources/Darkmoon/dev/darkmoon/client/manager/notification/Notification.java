package dev.darkmoon.client.manager.notification;

import dev.darkmoon.client.DarkMoon;
import dev.darkmoon.client.manager.theme.Themes;
import dev.darkmoon.client.module.impl.render.Hud;
import dev.darkmoon.client.utility.math.BlurUtility;
import dev.darkmoon.client.utility.misc.TimerHelper;
import dev.darkmoon.client.utility.render.ColorUtility;
import dev.darkmoon.client.utility.render.ColorUtility2;
import dev.darkmoon.client.utility.render.RenderUtility;
import dev.darkmoon.client.utility.render.animation.Animation;
import dev.darkmoon.client.utility.render.animation.impl.DecelerateAnimation;
import dev.darkmoon.client.utility.render.font.Fonts;
import lombok.Getter;

import java.awt.*;

@Getter
public class Notification {
    private final NotificationType type;
    private final String title, description;
    private final float time;
    private final TimerHelper timerHelper = new TimerHelper();
    private final Animation animation;

    public Notification(NotificationType type, String title, String description) {
        this(type, title, description, NotificationManager.getDefaultTime());
    }

    public Notification(NotificationType type, String title, String description, float time) {
        this.type = type;
        this.title = title;
        this.description = description;
        this.time = time;
        this.animation = new DecelerateAnimation(250, 1);
    }

    public void render(float x, float y, float width, float height, float alpha) {
        Color gradientColor1 = ColorUtility2.interpolateColorsBackAndForth(Hud.hueInterpolate.get() ? 15 : 4, 0, DarkMoon.getInstance().getThemeManager().getCurrentStyleTheme().getColors()[0], DarkMoon.getInstance().getThemeManager().getCurrentStyleTheme().getColors()[1], Hud.hueInterpolate.get());
        Color gradientColor2 = ColorUtility2.interpolateColorsBackAndForth(Hud.hueInterpolate.get() ? 15 : 4, 90, DarkMoon.getInstance().getThemeManager().getCurrentStyleTheme().getColors()[0], DarkMoon.getInstance().getThemeManager().getCurrentStyleTheme().getColors()[1], Hud.hueInterpolate.get());
        Color gradientColor3 = ColorUtility2.interpolateColorsBackAndForth(Hud.hueInterpolate.get() ? 15 : 4, 180, DarkMoon.getInstance().getThemeManager().getCurrentStyleTheme().getColors()[0], DarkMoon.getInstance().getThemeManager().getCurrentStyleTheme().getColors()[1], Hud.hueInterpolate.get());
        Color gradientColor4 = ColorUtility2.interpolateColorsBackAndForth(Hud.hueInterpolate.get() ? 15 : 4, 270, DarkMoon.getInstance().getThemeManager().getCurrentStyleTheme().getColors()[0], DarkMoon.getInstance().getThemeManager().getCurrentStyleTheme().getColors()[1], Hud.hueInterpolate.get());

        RenderUtility.drawDarkMoonShader(x + 30.0F, y + 5.0F, width - 30.0F, height - 15.0F, 7.0F);
        //   RenderUtility.drawBlurredShadowGradient(x + 30.0F, y + 5.0F, width - 30.0F, height - 15.0F, 7, gradientColor1, gradientColor2, gradientColor3, gradientColor4);
            Fonts.tenacityBold14.drawString(this.description, x + 34.0F, y + 10.0F, ColorUtility.applyOpacity(Color.WHITE, alpha).getRGB());
  }
}

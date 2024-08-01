package wtf.diablo.client.notification;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import wtf.diablo.client.font.FontRepository;
import wtf.diablo.client.util.math.time.Animation;

import java.awt.*;

import static wtf.diablo.client.util.render.ColorUtil.*;

public final class Notification {
    private final static Minecraft mc = Minecraft.getMinecraft();

    private final String title;
    private final String description;
    private final double duration;
    private final NotificationType type;
    private int count;
    private int lastCount = -1;
    private final boolean hiding = false;
    private long animationSlideInStart;
    private final long startTime;
    private double x, y, width = 100, height = 100;
    private boolean reverseAnimationSlide = false;
    private long animationSlideVerticalStart;
    private int color;


    public Notification(String title, String description, double duration, NotificationType type) {
        this.title = title;
        this.description = description;
        this.duration = duration;
        this.type = type;
        this.startTime = System.currentTimeMillis();
        this.animationSlideInStart = System.currentTimeMillis();
    }

    public Notification(String title, double duration, NotificationType type) {
        this.title = title;
        this.description = null;
        this.duration = duration;
        this.type = type;
        this.startTime = System.currentTimeMillis();
        this.animationSlideInStart = System.currentTimeMillis();
    }

    public void drawNotification(int count, ScaledResolution sr) {
        double animationSlideDuration = 300;
        this.count = count;
        width = 70;
        if (description != null && mc.fontRendererObj.getStringWidth(description) > 70) {
            width += mc.fontRendererObj.getStringWidth(description) + 15;
        }

        if (description != null) {
            height = 30;
        } else {
            height = 28;
        }

        x = sr.getScaledWidth() - width - 5;
        y = sr.getScaledHeight() - ((height + 5) * count) - 45;

        GlStateManager.pushMatrix();
        double animationWidth = (width + 5);
        double animationShit = Animation.getDoubleFromPercentage(Animation.getPercentage(animationSlideDuration, animationSlideInStart), animationWidth);
        double animationVertical = 0;
        if (lastCount == -1) {
            lastCount = count;
        }
        if (lastCount != count) {
            double animationSlideVerticalDuration = 100;
            double difference = ((height + 5) * lastCount) - ((height + 5) * count);
            double percentage = Animation.getPercentage(animationSlideVerticalDuration, animationSlideVerticalStart);
            animationVertical = height - (height - Animation.getDoubleFromPercentage(percentage, difference)) - height;

            if (percentage >= 100) {
                lastCount = count;
            }
        } else {
            this.animationSlideVerticalStart = System.currentTimeMillis();
        }
        GlStateManager.translate(0, animationVertical, 0);
        if (!reverseAnimationSlide && System.currentTimeMillis() < animationSlideInStart + animationSlideDuration) {
            GlStateManager.translate(width + -animationShit, 0, 0);
        } else if (System.currentTimeMillis() > duration + startTime - animationSlideDuration) {
            if (!reverseAnimationSlide) {
                animationSlideInStart = System.currentTimeMillis();
                reverseAnimationSlide = true;
            } else
                GlStateManager.translate(animationShit, 0, 0);
            //hiding = true;
        }

        Gui.drawRect(x, y, x + width, y + height, NOTIFICATION_COLOR.getValue().getRGB());
        switch (type) {
            case SUCCESS:
                color = NOTIFICATION_SUCCESS_COLOR.getValue().getRGB();
                FontRepository.ICON.drawStringWithShadow("e", x + 3, y + (height / 2) - (FontRepository.ICON.getHeight("e") / 2.0), color);
                break;
            case ERROR:
                color = NOTIFICATION_ERROR_COLOR.getValue().getRGB();
                FontRepository.ICON.drawStringWithShadow("a", x + 3, y + (height / 2) - (FontRepository.ICON.getHeight("a") / 2.0), color);
                break;
            case WARNING:
                color = NOTIFICATION_WARNING_COLOR.getValue().getRGB();
                FontRepository.ICON.drawStringWithShadow("a", x + 3, y + (height / 2) - (FontRepository.ICON.getHeight("a") / 2.0), color);
                break;
            case INFORMATION:
                color = NOTIFICATION_INFO_COLOR.getValue().getRGB();
                FontRepository.ICON.drawStringWithShadow("c", x + 3, y + (height / 2) - (FontRepository.ICON.getHeight("c") / 2.0), color);
                break;
        }

        mc.fontRendererObj.drawStringWithShadow(title, (float) (x + 29), (float) (y + 6), -1);
        mc.fontRendererObj.drawStringWithShadow(ChatFormatting.ITALIC + description, (float) (x + 34), (float) (y + 18), new Color(122, 122, 122).getRGB());

        long elapsed = System.currentTimeMillis() - startTime;
        double percentElapsed = elapsed / duration;

        Gui.drawRect(x, y + height - 1.5, x + (percentElapsed * width), y + height, color);
        GlStateManager.popMatrix();
    }

    public boolean isHiding() {
        return hiding;
    }

    public long getStartTime() {
        return startTime;
    }

    public double getDuration() {
        return duration;
    }
}

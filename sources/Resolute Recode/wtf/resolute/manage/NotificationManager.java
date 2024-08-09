package wtf.resolute.manage;

import com.mojang.blaze3d.matrix.MatrixStack;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.util.ResourceLocation;
import wtf.resolute.utiled.animations.Animation;
import wtf.resolute.utiled.animations.Direction;
import wtf.resolute.utiled.animations.impl.DecelerateAnimation;
import wtf.resolute.utiled.math.AnimationMath;
import wtf.resolute.utiled.render.ColorUtils;
import wtf.resolute.utiled.render.DisplayUtils;
import wtf.resolute.utiled.render.font.Fonts;

import java.awt.*;
import java.util.concurrent.CopyOnWriteArrayList;

import static wtf.resolute.utiled.client.IMinecraft.mc;

public class NotificationManager {

    private final CopyOnWriteArrayList<Notification> notifications = new CopyOnWriteArrayList<>();

    public void add(String text, String content, int time) {
        notifications.add(new Notification(text, content, time));
    }


    public void draw(MatrixStack stack) {
        int yOffset = 0;
        for (Notification notification : notifications) {

            if (System.currentTimeMillis() - notification.getTime() > (notification.time2 * 1000L) - 300) {
            } else {
                notification.yAnimation.setDirection(Direction.FORWARDS);
            }
            notification.alpha = (float) notification.animation.getOutput();
            if (System.currentTimeMillis() - notification.getTime() > notification.time2 * 1000L) {
                notification.yAnimation.setDirection(Direction.BACKWARDS);
            }
            if (notification.yAnimation.finished(Direction.BACKWARDS)) {
                notifications.remove(notification);
                continue;
            }
            float x = mc.getMainWindow().scaledWidth() - (Fonts.sfMedium.getWidth(notification.getText(),7) + 8) - 10;
            float y = mc.getMainWindow().scaledHeight() - 30;
            notification.yAnimation.setEndPoint(yOffset);
            notification.yAnimation.setDuration(300);
            y -= (float) (notification.draw(stack) * notification.yAnimation.getOutput());
            notification.setX(x);
            notification.setY(AnimationMath.fast(notification.getY(), y, 15));
            yOffset++;
        }
    }


    private class Notification {
        @Getter
        @Setter
        private float x, y = mc.getMainWindow().scaledHeight() + 24;

        @Getter
        private String text;
        @Getter
        private String content;

        @Getter
        private long time = System.currentTimeMillis();

        public Animation animation = new DecelerateAnimation(500, 1, Direction.FORWARDS);
        public Animation yAnimation = new DecelerateAnimation(500, 1, Direction.FORWARDS);
        float alpha;
        int time2 = 3;

        public Notification(String text, String content, int time) {
            this.text = text;
            this.content = content;
            time2 = time;
        }
        public float draw(MatrixStack stack) {
            float width = Fonts.sfMedium.getWidth(text,7) + 8;
            int firstColor = ColorUtils.getColorStyle(0);
            int secondColor = ColorUtils.getColorStyle(90);
            DisplayUtils.drawShadow(x, y - 0, width, 15, 8, firstColor, secondColor);
            DisplayUtils.drawRoundedRect(x, y, width, 15, 3, DisplayUtils.reAlphaInt(new Color(33, 32, 34).getRGB(), 210));
            final ResourceLocation logo = new ResourceLocation("resolute/images/info64.png");
            //DisplayUtils.drawImage(logo,x,y,9,9,-1);
            Fonts.sfMedium.drawText(stack, text, x + 3, y + 4, DisplayUtils.reAlphaInt(-1, (int) (255 * alpha)),7);
            return 24;
        }
    }
}
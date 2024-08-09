package wtf.shiyeno.notification;

import com.mojang.blaze3d.matrix.MatrixStack;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector4f;
import org.joml.Vector4i;
import wtf.shiyeno.managment.Managment;
import wtf.shiyeno.util.animations.Animation;
import wtf.shiyeno.util.animations.Direction;
import wtf.shiyeno.util.animations.impl.DecelerateAnimation;
import wtf.shiyeno.util.font.Fonts;
import wtf.shiyeno.util.render.BloomHelper;
import wtf.shiyeno.util.render.ColorUtil;
import wtf.shiyeno.util.render.RenderUtil;
import wtf.shiyeno.util.render.animation.AnimationMath;

import java.util.Comparator;
import java.util.concurrent.CopyOnWriteArrayList;

import static wtf.shiyeno.ui.clickgui.Window.light;
import static wtf.shiyeno.util.IMinecraft.mc;
import static wtf.shiyeno.util.render.ColorUtil.rgba;
import static wtf.shiyeno.util.render.RenderUtil.Render2D.drawCircle;

public class NotificationManager {

    private final CopyOnWriteArrayList<Notification> notifications = new CopyOnWriteArrayList<>();

    public void add(String text, String content, int time) {
        notifications.add(new Notification(text, content, time));
    }


    public void draw(MatrixStack stack) {
        int yOffset = 0;
        for (Notification notification : notifications) {

            if (System.currentTimeMillis() - notification.getTime() > (notification.time2 * 1000L) - 300) {
                notification.animation.setDirection(Direction.BACKWARDS);
            }
            notification.alpha = (float) notification.animation.getOutput();
            if (System.currentTimeMillis() - notification.getTime() > notification.time2 * 1000L) {
                notification.yAnimation.setDirection(Direction.BACKWARDS);
            }
            if (notification.yAnimation.finished(Direction.BACKWARDS)) {
                notifications.remove(notification);
                continue;
            }
            float x = mc.getMainWindow().scaledWidth() - (Fonts.gilroyBold[14].getWidth(notification.getText()) + 8 + 20) - 10;
            float y = mc.getMainWindow().scaledHeight() - 30;
            notification.yAnimation.setEndPoint(yOffset);
            notification.yAnimation.setDuration(300);

            if (notification.yAnimation.getDirection() == Direction.BACKWARDS) {
                y -= (float) (notification.draw(stack) * notification.yAnimation.getOutput());
            } else {
                y -= notification.draw(stack) * yOffset;
            }

            notification.setX(x);

            if (y <= notification.getY()) {
                notification.setY(y);
            } else {
                notification.setY(AnimationMath.fast(notification.getY(), y, 15));
            }
            yOffset++;
        }
    }


    private class Notification {
        @Getter
        @Setter
        private float x, y = mc.getMainWindow().scaledHeight() + 28;

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
            float width = Fonts.gilroyBold[14].getWidth(text) + 8 + 20;

            int firstColor = RenderUtil.reAlphaInt(Managment.STYLE_MANAGER.getCurrentStyle().getColor(0), (int) (255 * alpha));
            int secondColor = RenderUtil.reAlphaInt(Managment.STYLE_MANAGER.getCurrentStyle().getColor(100), (int) (255 * alpha));
            int thirdColor = RenderUtil.reAlphaInt(Managment.STYLE_MANAGER.getCurrentStyle().getColor(0), (int) (255 * alpha));
            int fourthColor = RenderUtil.reAlphaInt(Managment.STYLE_MANAGER.getCurrentStyle().getColor(100), (int) (255 * alpha));

            RenderUtil.Render2D.drawRoundedRect(x, y, width, 20, 3,
                    RenderUtil.reAlphaInt(ColorUtil.interpolateColor(light, ColorUtil.getColorStyle(0), 0.02f), (int)(255 * alpha))
            );

            float progress = Math.abs(Math.min((System.currentTimeMillis() - getTime()) / ((float)time2 * 1000), 1) - 1);
            float radius = MathHelper.clamp(progress * 360, 0, 360);

            drawCircle(x + 4 + 4 + 2, y + 10, 0, -radius, 4, 3, false,
                    RenderUtil.reAlphaInt(ColorUtil.getColorStyle(0), (int) (255 * alpha)));

            Fonts.msMedium[14].drawString(stack, text, x + 4 + 4 + 12, y + 8, RenderUtil.reAlphaInt(-1, (int) (255 * alpha)));
            return 24;
        }
    }
}
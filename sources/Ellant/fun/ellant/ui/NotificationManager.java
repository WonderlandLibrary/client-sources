/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package fun.ellant.ui;

import com.mojang.blaze3d.matrix.MatrixStack;
import java.util.concurrent.CopyOnWriteArrayList;

import fun.ellant.functions.impl.hud.HUD;
import net.minecraft.util.ResourceLocation;
import fun.ellant.utils.animations.Animation;
import fun.ellant.utils.animations.Direction;
import fun.ellant.utils.animations.impl.EaseBackIn;
import fun.ellant.utils.animations.impl.EaseInOutQuad;
import fun.ellant.utils.client.IMinecraft;
import fun.ellant.utils.math.MathUtil;
import fun.ellant.utils.render.ColorUtils;
import fun.ellant.utils.render.DisplayUtils;
import fun.ellant.utils.render.font.Fonts;

public class NotificationManager {
    private final CopyOnWriteArrayList<Notification> notifications = new CopyOnWriteArrayList();
    private MathUtil AnimationMath;
    private ImageType imageType;
    boolean state;

    public void add(String text, String content, int time, ImageType imageType) {
        this.notifications.add(new Notification(text, content, time, imageType));
    }

    public void draw(MatrixStack stack) {
        int yOffset = 0;
        for (Notification notification : this.notifications) {
            if (System.currentTimeMillis() - notification.getTime() <= (long)notification.time2 * 1000L - 300L) {
                notification.yAnimation.setDirection(Direction.FORWARDS);
            }
            notification.alpha = (float)notification.animation.getOutput();
            if (System.currentTimeMillis() - notification.getTime() > (long)notification.time2 * 1000L) {
                notification.yAnimation.setDirection(Direction.BACKWARDS);
            }
            if (notification.yAnimation.finished(Direction.BACKWARDS)) {
                this.notifications.remove(notification);
                continue;
            }
            float x = (float)IMinecraft.mc.getMainWindow().scaledWidth() - (Fonts.sfMedium.getWidth(notification.getText(), 7.0f) + 8.0f) - 10.0f;
            float y = IMinecraft.mc.getMainWindow().scaledHeight() - 40;
            notification.yAnimation.setEndPoint(yOffset);
            notification.yAnimation.setDuration(500);
            notification.setX(x);
            notification.setY(MathUtil.fast(notification.getY(), y -= (float)((double)notification.draw(stack) * notification.yAnimation.getOutput() + 3.0), 15.0f));
            ++yOffset;
        }
    }

    private class Notification {
        private float x = 0.0f;
        private float y = IMinecraft.mc.getMainWindow().scaledHeight() + 24;
        private String text;
        private String content;
        private long time = System.currentTimeMillis();
        public Animation animation = new EaseInOutQuad(500, 1.0, Direction.FORWARDS);
        public Animation yAnimation = new EaseBackIn(500, 1.0, 1.0f);
        private ImageType imageType;
        float alpha;
        int time2 = 3;
        private boolean isState;
        private boolean state;

        public Notification(String text, String content, int time, ImageType imageType) {
            this.text = text;
            this.content = content;
            this.time2 = time;
            this.imageType = imageType;
        }

        public float draw(MatrixStack stack) {
            float width = Fonts.sfMedium.getWidth(this.text, 7.0f) + 8.0f;
            DisplayUtils.drawRoundedRect(this.x - 427.0f + 175.0f, this.y - 95.0f, width + 27.0f, 13.0f, 3.0f, ColorUtils.rgb(23, 22, 22));
            DisplayUtils.drawShadow(this.x - 427.0f + 175f, this.y - 95f, width + 27.0f, 13.0f, 3, ColorUtils.rgba(23, 22, 22, 148));
            if (this.imageType == ImageType.FIRST_PHOTO) {
                ResourceLocation key = new ResourceLocation("expensive/images/hud/notify.png");
                DisplayUtils.drawImage(key, this.x - 424.0f + 175f, this.y - 96f, 16.0f, 16.0f, ColorUtils.rgb(255, 0, 0));
            } else {
                ResourceLocation key1 = new ResourceLocation("expensive/images/hud/notify2.png");
                DisplayUtils.drawImage(key1, this.x - 424.0f + 175f, this.y - 96f, 16.0f, 16.0f, ColorUtils.rgb(0, 255, 0));

            }
            Fonts.montserrat.drawText(stack, this.text, this.x - 407.0f + 175.0f, this.y - 92f, -1, 7.0f, 0.05f);
            int enabledColor = ColorUtils.rgba(255, 255, 255, 255);
            int disabledColor = ColorUtils.rgba(255, 255, 255, 255);
            int contentColor = this.state ? enabledColor : disabledColor;
            Fonts.sfMedium.drawText(stack, this.content, this.x, this.y, ColorUtils.rgb(0, 255, 0), 4.0f, 0.05f);
            return 24.0f;
        }

        public float getX() {
            return this.x;
        }

        public float getY() {
            return this.y;
        }

        public void setX(float x) {
            this.x = x;
        }

        public void setY(float y) {
            this.y = y;
        }

        public String getText() {
            return this.text;
        }

        public String getContent() {
            return this.content;
        }

        public long getTime() {
            return this.time;
        }
    }

    public static enum ImageType {
        FIRST_PHOTO,
        SECOND_PHOTO;

    }
}


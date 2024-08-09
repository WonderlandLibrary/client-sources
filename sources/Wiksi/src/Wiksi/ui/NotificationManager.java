/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package src.Wiksi.ui;

import com.mojang.blaze3d.matrix.MatrixStack;
import java.util.concurrent.CopyOnWriteArrayList;

import src.Wiksi.functions.api.NaksonPaster;
import src.Wiksi.utils.animations.Animation;
import src.Wiksi.utils.animations.Direction;
import src.Wiksi.utils.animations.impl.EaseBackIn;
import src.Wiksi.utils.animations.impl.EaseInOutQuad;
import src.Wiksi.utils.client.IMinecraft;
import src.Wiksi.utils.math.MathUtil;
import src.Wiksi.utils.render.ColorUtils;
import src.Wiksi.utils.render.DisplayUtils;
import src.Wiksi.utils.render.font.Fonts;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class NotificationManager {
    Minecraft mc = Minecraft.getInstance();
    private final CopyOnWriteArrayList<Notification> notifications = new CopyOnWriteArrayList();
    private MathUtil AnimationMath;
    private ImageType imageType;
    boolean state;

    public void add(String string, String string2, int n, ImageType imageType) {
        this.notifications.add(new Notification(this, string, string2, n, imageType));
    }

    public void draw(MatrixStack matrixStack) {
        this.mc.gameRenderer.setupOverlayRendering(2);
        int n = 0;
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
            float f = (float)IMinecraft.mc.getMainWindow().scaledWidth() - (Fonts.sfMedium.getWidth(notification.getText(), 7.0f) + 8.0f) + 347.0f;
            float f2 = IMinecraft.mc.getMainWindow().scaledHeight() - 20;
            notification.yAnimation.setEndPoint(n);
            notification.yAnimation.setDuration(500);
            notification.setX(f);
            notification.setY(MathUtil.fast(notification.getY(), f2 -= (float)((double)notification.draw(matrixStack) * notification.yAnimation.getOutput() + 3.0), 15.0f));
            ++n;
        }
    }

    private class Notification {
        private float x;
        private float y;
        private String text;
        private String content;
        private long time;
        public Animation animation;
        public Animation yAnimation;
        private ImageType imageType;
        float alpha;
        int time2;
        private boolean isState;
        private boolean state;
        final NotificationManager this$0;

        public Notification(NotificationManager notificationManager, String string, String string2, int n, ImageType imageType) {
            this.this$0 = notificationManager;
            this.x = 0.0f;
            this.y = IMinecraft.mc.getMainWindow().scaledHeight() + 24;
            this.time = System.currentTimeMillis();
            this.animation = new EaseInOutQuad(500, 1.0, Direction.FORWARDS);
            this.yAnimation = new EaseBackIn(500, 1.0, 1.0f);
            this.time2 = 3;
            this.text = string;
            this.content = string2;
            this.time2 = n;
            this.imageType = imageType;
        }

        public float draw(MatrixStack matrixStack) {
            float f = Fonts.sfMedium.getWidth(this.text, 7.0f) + 8.0f;
            DisplayUtils.drawRoundedRect(this.x - 427.0f + 50.0f, this.y - 25.0f, f + 27.0f, 13.0f, 3.0f, ColorUtils.rgba(21, 24, 40, 165));
            DisplayUtils.drawShadow(this.x - 427.0f + 50.0f, this.y - 25.0f, f + 27.0f, 13.0f, 3, ColorUtils.rgba(21, 24, 40, 165));
            DisplayUtils.drawRectHorizontalW(this.x - 427.0f + 50.0f + f + 27.0f - 2.0f, this.y - 25.0f, 1.5, 13.0, ColorUtils.getColor(1), ColorUtils.getColor(1));
            if (this.imageType == ImageType.FIRST_PHOTO) {
                ResourceLocation key = new ResourceLocation("Wiksi/images/hud/notify.png");
                DisplayUtils.drawImage(key, this.x - 424.0f + 50.0f, this.y - 26.5f, 16.0f, 16.0f, ColorUtils.rgb(255, 0, 0));
            } else {
                ResourceLocation key = new ResourceLocation("Wiksi/images/hud/notify2.png");
                DisplayUtils.drawImage(key, this.x - 424.0f + 50.0f, this.y - 26.5f, 16.0f, 16.0f, ColorUtils.rgb(0, 255, 0));
            }
            Fonts.montserrat.drawText(matrixStack, this.text, this.x - 407.0f + 50.0f, this.y - 22.0f, -1, 7.0f, 0.05f);
            int n = ColorUtils.rgba(0, 255, 0, 255);
            int n2 = ColorUtils.rgba(255, 0, 0, 255);
            int n3 = this.state ? n : n2;
            Fonts.sfMedium.drawText(matrixStack, this.content, this.x, this.y, ColorUtils.rgb(0, 255, 0), 4.0f, 0.05f);
            return 24.0f;
        }

        public float getX() {
            return this.x;
        }

        public float getY() {
            return this.y;
        }

        public void setX(float f) {
            this.x = f;
        }

        public void setY(float f) {
            this.y = f;
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


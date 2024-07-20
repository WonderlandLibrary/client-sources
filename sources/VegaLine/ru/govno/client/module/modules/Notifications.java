/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.module.modules;

import java.util.ArrayList;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import ru.govno.client.event.EventTarget;
import ru.govno.client.event.events.EventRender2D;
import ru.govno.client.module.Module;
import ru.govno.client.module.settings.Settings;
import ru.govno.client.newfont.CFontRenderer;
import ru.govno.client.newfont.Fonts;
import ru.govno.client.utils.Math.MathUtils;
import ru.govno.client.utils.Render.AnimationUtils;
import ru.govno.client.utils.Render.ColorUtils;
import ru.govno.client.utils.Render.RenderUtils;

public class Notifications
extends Module {
    public static Module get;

    public Notifications() {
        super("Notifications", 0, Module.Category.RENDER);
        get = this;
        this.settings.add(new Settings("Mode", "Colored", (Module)this, new String[]{"Colored", "Dark"}));
    }

    @EventTarget
    public void onRender2D(EventRender2D event) {
        Notifications.drawNotifyS();
    }

    public static void drawNotifyS() {
        if (Notify.notifications.size() == 0) {
            return;
        }
        int yDist = 1;
        String mode = get.currentMode("Mode");
        for (Notification notification : Notify.notifications) {
            Notify.draw(notification, yDist, mode);
            ++yDist;
        }
        Notify.notifications.removeIf(huy -> System.currentTimeMillis() - huy.getTime() >= huy.max_time);
    }

    public class Notify {
        public static ArrayList<Notification> notifications = new ArrayList();

        public static void spawnNotify(String message, type usedtype) {
            long maxTime = 1500 * (usedtype == type.STAFF ? 3 : 1);
            notifications.add(new Notification(message, maxTime, usedtype));
        }

        static void drawIcon(type type2, float alpha, float x, float y, float size, String mode) {
            if (mode.equalsIgnoreCase("Dark")) {
                int c1 = ColorUtils.swapAlpha(type2.color, ColorUtils.getAlphaFromColor(type2.color));
                int c2 = ColorUtils.swapAlpha(type2.color, (float)ColorUtils.getAlphaFromColor(type2.color) / 4.0f);
                RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(x - size / 2.0f, y - size / 2.0f, x + size / 2.0f, y + size / 2.0f, 0.0f, 3.0f * alpha, c2, c2, c2, c2, true, false, true);
                RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(x - size / 2.0f, y - size / 2.0f, x + size / 2.0f, y + size / 2.0f, 0.0f, 0.0f, c1, c1, c1, c1, true, true, false);
                return;
            }
            ResourceLocation icon = new ResourceLocation("vegaline/modules/notifications/icons/" + type2.icon.toLowerCase() + ".png");
            if (icon != null) {
                RenderUtils.drawImageWithAlpha(icon, x, y, size, size, ColorUtils.getFixedWhiteColor(), (int)(255.0f * alpha));
            }
        }

        static void draw(Notification notify, int index, String mode) {
            String surf2;
            notify.animY.speed = 0.15f;
            notify.animX.speed = 0.075f;
            boolean isDark = mode.equalsIgnoreCase("Dark");
            ScaledResolution sr = new ScaledResolution(Module.mc);
            CFontRenderer font = isDark ? Fonts.comfortaaBold_16 : Fonts.noise_16;
            type type2 = notify.type;
            String text = notify.getMessage();
            String massage = notify.type.icon + (notify.type.equals((Object)type.STAFF) ? "" : "d");
            int colorize = notify.getColorize();
            int colorize2 = ColorUtils.getColor((int)MathUtils.clamp((float)ColorUtils.getRedFromColor(notify.getColorize()) * 1.75f, 0.0f, 255.0f), (int)MathUtils.clamp((float)ColorUtils.getGreenFromColor(notify.getColorize()) * 1.75f, 0.0f, 255.0f), (int)MathUtils.clamp((float)ColorUtils.getBlueFromColor(notify.getColorize()) * 1.75f, 0.0f, 255.0f));
            float max_time = notify.getMax_Time();
            float time = System.currentTimeMillis() - notify.getTime();
            String surf = isDark ? " " : " | ";
            String string = surf2 = isDark ? "\u00a7r\u00a7f \u00a7r" : "\u00a7r\u00a7f | \u00a7r";
            if (time < 50.0f) {
                notify.animY.setAnim(index);
            }
            if (time + 100.0f > max_time) {
                notify.animX.speed = 0.125f;
                notify.animX.to = 0.0f;
                if (notify.animY.getAnim() == 1.0f) {
                    notify.animY.setAnim((float)index - 1.5f);
                }
            } else {
                notify.animX.to = 1.0f;
                if (index - 1 >= 0) {
                    notify.animY.to = index - 1;
                }
            }
            float width = (isDark ? 18.5f : 24.0f) + (float)font.getStringWidth(text + surf + massage);
            float w = width * notify.animX.getAnim();
            float hStep = (float)(isDark ? 17 : 20) * notify.animY.getAnim();
            float expX = 3.5f;
            float expY = 4.5f;
            float x = (float)sr.getScaledWidth() - w - 3.5f;
            float y = (float)(sr.getScaledHeight() - 16) - 4.5f - hStep;
            float x2 = (float)sr.getScaledWidth() - 3.5f + width - width * notify.animX.getAnim();
            float y2 = (float)sr.getScaledHeight() - 4.5f - hStep;
            float extenderOut = 0.0f;
            if ((double)(time / max_time) > 0.8) {
                extenderOut = (time / max_time - 0.8f) * (float)(isDark ? 10 : 80);
            }
            float alphaPercent = notify.animX.getAnim();
            int c1 = ColorUtils.swapAlpha(isDark ? Integer.MIN_VALUE : colorize, (float)(isDark ? 205 : 80) * alphaPercent);
            int c3 = ColorUtils.swapAlpha(isDark ? Integer.MIN_VALUE : colorize2, (float)(isDark ? 90 : 6) * alphaPercent);
            GL11.glTranslated(x - extenderOut, y, 0.0);
            RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(0.5f, 0.5f, x2 - x - 0.5f, y2 - y - 0.5f, isDark ? 2.0f : 4.0f, isDark ? 1.0f : 2.5f, c1, c3, c3, c1, false, true, true);
            RenderUtils.resetBlender();
            float extX = isDark ? 7.0f : 0.5f;
            float extY = isDark ? 8.0f : 0.5f;
            float size = isDark ? 3.0f : 16.0f;
            Notify.drawIcon(type2, alphaPercent, extX, extY, size, mode);
            font.drawStringWithShadow("\u00a7f" + text + surf2 + massage, isDark ? 13.0 : 19.0, isDark ? 5.5 : 5.0, ColorUtils.swapAlpha(colorize2, 255.0f * alphaPercent));
            GL11.glTranslated(-x + extenderOut, -y, 0.0);
        }
    }

    static class Notification {
        private final AnimationUtils animY = new AnimationUtils(1.0f, 1.1f, 0.075f);
        private final AnimationUtils animX = new AnimationUtils(0.0f, 1.0f, 0.075f);
        private final String message;
        private final long time;
        private final long max_time;
        type type;

        public Notification(String message, long max_time, type type2) {
            this.max_time = max_time;
            this.message = message;
            this.time = System.currentTimeMillis();
            this.type = type2;
        }

        public long getTime() {
            return this.time;
        }

        public int getColorize() {
            return this.type.color;
        }

        public long getMax_Time() {
            return this.max_time;
        }

        public String getMessage() {
            return this.message;
        }
    }

    public static enum type {
        ENABLE(ColorUtils.getColor(32, 143, 50), "Enable"),
        DISABLE(ColorUtils.getColor(175, 35, 37), "Disable"),
        STAFF(ColorUtils.getColor(92, 142, 255), "Staff"),
        FADD(ColorUtils.getColor(190, 250, 140), "Friend add"),
        FDEL(ColorUtils.getColor(250, 140, 140), "Friend remove");

        private final int color;
        private final String icon;

        private type(int color, String icon) {
            this.color = color;
            this.icon = icon;
        }
    }
}


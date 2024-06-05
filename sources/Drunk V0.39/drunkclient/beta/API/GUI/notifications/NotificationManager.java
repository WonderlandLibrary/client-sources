/*
 * Decompiled with CFR 0.152.
 */
package drunkclient.beta.API.GUI.notifications;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import drunkclient.beta.API.GUI.notifications.Notification;
import drunkclient.beta.API.GUI.notifications.Type;
import drunkclient.beta.IMPL.font.FontLoaders;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.util.MathHelper;

public final class NotificationManager {
    private final int DEFAULT_DELAY = 2000;
    private final List NOTIFICATIONS = new CopyOnWriteArrayList();

    public List getNOTIFICATIONS() {
        return this.NOTIFICATIONS;
    }

    public void launch() {
        Iterator iterator = this.NOTIFICATIONS.iterator();
        if (!iterator.hasNext()) return;
        Notification noti = (Notification)iterator.next();
        int index = this.NOTIFICATIONS.indexOf(noti) * 37;
        if (noti.getY() < (double)(50 + index)) {
            double d = noti.getY();
            Minecraft.getMinecraft();
            noti.setY(MathHelper.clamp_double(d + 0.5 * (double)(2000 / Minecraft.getDebugFPS()), 0.0, 50 + index));
        }
        if (noti.getY() < (double)(50 + index)) {
            double d = noti.getY();
            Minecraft.getMinecraft();
            noti.setY(MathHelper.clamp_double(d - 0.25 * (double)(2000 / Minecraft.getDebugFPS()), 50 + index, 99999.0));
        }
        String delayString = noti.getDelay() / 1000 + "";
        String delayString2 = " (" + delayString.substring(0, delayString.indexOf(".") + 2) + "s) ";
        if (noti.isExtending()) {
            StringBuilder stringBuilder = new StringBuilder();
            if (noti.getX() < (double)Math.max(FontLoaders.arial18.getStringWidth(stringBuilder.append(noti.getMessage()).append(delayString2).toString()), FontLoaders.arial22.getStringWidth(noti.getCallReason()) + 36)) {
                double d = noti.getX();
                Minecraft.getMinecraft();
                noti.setX(MathHelper.clamp_double(d + 0.5 * (double)(2000 / Minecraft.getDebugFPS()), 0.0, Math.max(FontLoaders.arial18.getStringWidth(noti.getMessage() + delayString2), FontLoaders.arial22.getStringWidth(noti.getCallReason()) + 36)));
                noti.getTimer().reset();
            }
        }
        noti.setExtending(false);
        if (!noti.isExtending() && noti.getTimer().hasElapsed(noti.getDelay() + 150, false) && noti.getX() > 0.0) {
            double d = noti.getX();
            Minecraft.getMinecraft();
            noti.setX(d - 0.5 * (double)(2000 / Minecraft.getDebugFPS()));
        }
        if (!(noti.getX() <= 0.0)) return;
        this.remove(noti);
    }

    public void pop(@NotNull String message, @NotNull String CallReason, int delay, Type type) {
        Notification notification = new Notification(message, CallReason, delay, type);
        Iterator iterator = this.NOTIFICATIONS.iterator();
        if (iterator.hasNext()) {
            Notification notification1 = (Notification)iterator.next();
            if (notification.getMessage().equalsIgnoreCase(notification1.getMessage())) {
                if (!(notification1.getX() >= (double)(Minecraft.getMinecraft().fontRendererObj.getStringWidth(CallReason) + 63))) return;
                notification1.getTimer().reset();
                return;
            }
        }
        notification.setExtending(true);
        notification.getTimer().reset();
        this.add(notification);
    }

    public void add(@NotNull Notification notification) {
        this.NOTIFICATIONS.add(notification);
    }

    private void remove(@Nullable Notification noti) {
        this.NOTIFICATIONS.remove(noti);
    }
}


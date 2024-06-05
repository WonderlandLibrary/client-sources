package client.metaware.api.gui.notis;

import client.metaware.impl.utils.system.TimerUtil;
import net.minecraft.util.MathHelper;

public final class Notification {

    private final NotificationType notificationType;
    private final String message;

    final TimerUtil timer = new TimerUtil();
    boolean extending;
    public final int delay;
    private String callReason;
    public double x, y;

    Notification(String message, int delay, NotificationType notificationType) {
        this.message = message;
        this.notificationType = notificationType;
        this.x = 0;
        this.y = 40;
        this.delay = delay;
        this.extending = false;
    }

    Notification(String callReason, String message, int delay, NotificationType notificationType) {
        this.message = message;
        this.notificationType = notificationType;
        this.x = 0;
        this.y = 40;
        this.delay = delay;
        this.extending = false;
        this.callReason = callReason;
    }

    public NotificationType getType() {
        return this.notificationType;
    }

    public String getMessage() {
        return this.message;
    }

    public TimerUtil getTimer() {
        return timer;
    }

    public int getDelay() {
        return delay;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public boolean isExtending() {
        return extending;
    }

    public void setExtending(boolean extending) {
        this.extending = extending;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getCount() {
        return MathHelper.clamp_float(getTimer().getCurrentMS() - getTimer().getLastMS(), 0, (float) getDelay());
    }

    public String getCallReason() {
        return callReason;
    }

}
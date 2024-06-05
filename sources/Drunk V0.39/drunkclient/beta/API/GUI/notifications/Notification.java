/*
 * Decompiled with CFR 0.152.
 */
package drunkclient.beta.API.GUI.notifications;

import drunkclient.beta.API.GUI.notifications.Type;
import drunkclient.beta.UTILS.world.Timer;
import net.minecraft.util.MathHelper;

public final class Notification {
    private final Type type;
    private final String message;
    final Timer timer = new Timer();
    boolean extending;
    public final int delay;
    private String callReason;
    public double x;
    public double y;

    Notification(String message, int delay, Type type) {
        this.message = message;
        this.type = type;
        this.y = 50.0;
        this.x = 0.0;
        this.delay = delay;
        this.extending = false;
    }

    public Notification(String message, String callReason, int delay, Type type) {
        this.message = message;
        this.type = type;
        this.y = 50.0;
        this.x = 0.0;
        this.delay = delay;
        this.extending = false;
        this.callReason = callReason;
    }

    public String getMessage() {
        return this.message;
    }

    public Type getType() {
        return this.type;
    }

    public Timer getTimer() {
        return this.timer;
    }

    public boolean isExtending() {
        return this.extending;
    }

    public void setExtending(boolean extending) {
        this.extending = extending;
    }

    public int getDelay() {
        return this.delay;
    }

    public String getCallReason() {
        return this.callReason;
    }

    public void setCallReason(String callReason) {
        this.callReason = callReason;
    }

    public double getX() {
        return this.x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return this.y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getCount() {
        return MathHelper.clamp_float(this.getTimer().getTime(), 0.0f, this.getDelay());
    }
}


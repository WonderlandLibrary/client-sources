package Focus.Beta.API.GUI.notifications;

import Focus.Beta.UTILS.world.Timer;
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

    Notification(String message, int delay, Type type){
        this.message = message;
        this.type = type;
        this.y = 50.0D;
        this.x = 0.0D;
        this.delay = delay;
        this.extending = false;
    }
    Notification(String message, String callReason, int delay, Type type){
        this.message = message;
        this.type = type;
        this.y = 50.0D;
        this.x = 0.0D;
        this.delay = delay;
        this.extending = false;
        this.callReason = callReason;
    }

    public String getMessage() {
        return message;
    }

    public Type getType(){
        return type;
    }

    public Timer getTimer() {
        return timer;
    }

    public boolean isExtending() {
        return extending;
    }

    public void setExtending(boolean extending) {
        this.extending = extending;
    }

    public int getDelay() {
        return delay;
    }

    public String getCallReason() {
        return callReason;
    }

    public void setCallReason(String callReason) {
        this.callReason = callReason;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getCount() {
        return (double) MathHelper.clamp_float((float)(this.getTimer().getTime()), 0.0F, (float)this.getDelay());
    }
}

package zamorozka.event.events;

import zamorozka.event.Event;

public class EventJump extends Event {
    private double motionY;
    private boolean pre;

    public EventJump(double motionY, boolean pre) {
        this.motionY = motionY;
        this.pre = pre;
    }

    public double getMotionY() {
        return motionY;
    }

    public void setMotionY(double motiony) {
        this.motionY = motiony;
    }

    public boolean isPre() {
        return pre;
    }

    public boolean isPost() {
        return !pre;
    }
}

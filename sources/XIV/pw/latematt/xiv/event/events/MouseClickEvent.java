package pw.latematt.xiv.event.events;

import pw.latematt.xiv.event.Event;

/**
 * @author Jack
 */

public class MouseClickEvent extends Event {
    private int button;

    public MouseClickEvent(int button) {
        this.button = button;
    }

    public double getButton() {
        return button;
    }
}

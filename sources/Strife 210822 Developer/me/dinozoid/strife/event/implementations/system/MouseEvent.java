package me.dinozoid.strife.event.implementations.system;

public class MouseEvent {
    private final int mouseButton;

    public MouseEvent(int mouseButton) {
        this.mouseButton = mouseButton;
    }

    public int mouseButton() {
        return mouseButton;
    }
}

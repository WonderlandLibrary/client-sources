package club.bluezenith.events.impl;

import club.bluezenith.events.Event;

public class TextRenderEvent extends Event {
    private String text;

    public TextRenderEvent(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

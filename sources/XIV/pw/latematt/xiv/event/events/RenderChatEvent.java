package pw.latematt.xiv.event.events;

import pw.latematt.xiv.event.Event;

/**
 * @author Rederpz
 */
public class RenderChatEvent extends Event {

    private String text;

    public RenderChatEvent(String text) {
        this.text = text;
    }

    public String getString() {
        return text;
    }
}

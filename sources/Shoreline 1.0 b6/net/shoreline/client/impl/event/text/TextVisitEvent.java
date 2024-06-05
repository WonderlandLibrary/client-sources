package net.shoreline.client.impl.event.text;

import net.shoreline.client.api.event.Cancelable;
import net.shoreline.client.api.event.Event;
import net.shoreline.client.mixin.text.MixinTextVisitFactory;

/**
 * @see MixinTextVisitFactory
 */
@Cancelable
public class TextVisitEvent extends Event {
    //
    private String text;

    /**
     * @param text
     */
    public TextVisitEvent(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

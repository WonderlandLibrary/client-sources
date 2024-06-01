package best.actinium.event.impl.input;

import best.actinium.event.Event;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClickEvent extends Event {
    private boolean shouldRightClick;
    private int slot;
    public ClickEvent(final int slot) {
        this.slot = slot;
    }

}

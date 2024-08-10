package cc.slack.events.impl.input;

import cc.slack.events.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class KeyEvent extends Event {
    private final int key;
}

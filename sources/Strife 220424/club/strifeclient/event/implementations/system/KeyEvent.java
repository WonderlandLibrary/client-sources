package club.strifeclient.event.implementations.system;

import club.strifeclient.event.Event;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class KeyEvent extends Event {
    public final int key;
}

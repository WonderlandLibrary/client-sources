package club.strifeclient.event.implementations.player;

import club.strifeclient.event.Event;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class WindowClickEvent extends Event {
    public int window, slot, button, mode;
}

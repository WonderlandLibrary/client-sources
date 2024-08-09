package dev.darkmoon.client.event.player;

import com.darkmagician6.eventapi.events.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EventPostMove implements Event {
    private double horizontalMove;
}

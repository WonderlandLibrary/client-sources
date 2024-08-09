package dev.darkmoon.client.event.player;

import com.darkmagician6.eventapi.events.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EventDamage implements Event {
    private final DamageType damageType;

     public enum DamageType {
        FALL,
        ARROW,
        ENDER_PEARL;
    }
}

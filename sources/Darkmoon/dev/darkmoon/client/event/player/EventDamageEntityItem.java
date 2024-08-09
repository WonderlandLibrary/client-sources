package dev.darkmoon.client.event.player;

import com.darkmagician6.eventapi.events.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.entity.Entity;

@Getter
@AllArgsConstructor
public class EventDamageEntityItem implements Event {
    private Entity entity;
}

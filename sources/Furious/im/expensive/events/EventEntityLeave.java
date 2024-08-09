package im.expensive.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import net.minecraft.entity.Entity;
import net.minecraftforge.eventbus.api.Event;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class EventEntityLeave extends Event {
    private Entity entity;
}
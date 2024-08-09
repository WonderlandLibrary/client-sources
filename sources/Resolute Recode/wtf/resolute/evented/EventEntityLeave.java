package wtf.resolute.evented;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.minecraft.entity.Entity;
import net.minecraftforge.eventbus.api.Event;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class EventEntityLeave extends Event {
    private Entity entity;
}
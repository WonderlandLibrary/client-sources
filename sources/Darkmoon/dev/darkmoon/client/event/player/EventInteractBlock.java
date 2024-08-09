package dev.darkmoon.client.event.player;

import com.darkmagician6.eventapi.events.callables.EventCancellable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

@Getter
@Setter
@AllArgsConstructor
public class EventInteractBlock extends EventCancellable {
    private BlockPos pos;
    private EnumFacing face;
}

package dev.excellent.api.event.impl.other;

import dev.excellent.api.event.CancellableEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.util.math.BlockPos;

@Getter
@AllArgsConstructor
public class BlockBreakEvent extends CancellableEvent {
    public BlockPos position;
}

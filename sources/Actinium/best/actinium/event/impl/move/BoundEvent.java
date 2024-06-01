package best.actinium.event.impl.move;

import best.actinium.event.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.block.Block;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;

@Getter
@Setter
@AllArgsConstructor
public class BoundEvent extends Event {
    private final Block block;
    private final BlockPos blockPosition;
    private AxisAlignedBB boundingBox;
}

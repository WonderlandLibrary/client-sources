package wtf.expensive.events.impl.player;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import wtf.expensive.events.Event;

/**
 * @author dedinside
 * @since 07.06.2023
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class EventObsidianPlace extends Event {

    private final Block block;
    private final BlockPos pos;

}

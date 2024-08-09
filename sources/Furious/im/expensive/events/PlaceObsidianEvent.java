package im.expensive.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;

@Data
@AllArgsConstructor
public class PlaceObsidianEvent {
    private Block block;
    private BlockPos pos;
}

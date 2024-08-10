package cc.slack.events.impl.player;

import cc.slack.events.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.block.Block;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;

@Getter
@Setter
@AllArgsConstructor
public class CollideEvent extends Event {
    public Block block;
    public AxisAlignedBB boundingBox;
    private final BlockPos blockPos;
    public double x, y, z;
}
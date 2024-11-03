package dev.stephen.nexus.event.impl.world;

import dev.stephen.nexus.event.types.Event;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
@Setter
@Getter
public class EventBlockShape implements Event {
    BlockState state;
    BlockPos pos;
    VoxelShape shape;

    public EventBlockShape(BlockState state, BlockPos pos, VoxelShape shape) {
        this.state = state;
        this.pos = pos;
        this.shape = shape;
    }
}

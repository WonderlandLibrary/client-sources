package xyz.mathax.mathaxclient.events.world;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import xyz.mathax.mathaxclient.events.Cancellable;

public class CollisionShapeEvent extends Cancellable {
    private static final CollisionShapeEvent INSTANCE = new CollisionShapeEvent();

    public BlockState state;
    public BlockPos pos;
    public VoxelShape shape;
    public CollisionType type;

    public static CollisionShapeEvent get(BlockState state, BlockPos pos, VoxelShape shape, CollisionType type) {
        INSTANCE.state = state;
        INSTANCE.pos = pos;
        INSTANCE.shape = shape;
        INSTANCE.type = type;
        return INSTANCE;
    }

    public enum CollisionType {
        BLOCK,
        FLUID
    }
}

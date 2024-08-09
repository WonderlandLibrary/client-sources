package mathax.client.events.world;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;

public class CollisionShapeEvent {
    public enum CollisionType {
        BLOCK,
        FLUID
    }

    private static final CollisionShapeEvent INSTANCE = new CollisionShapeEvent();

    public BlockState state;
    public BlockPos pos;
    public VoxelShape shape;
    public CollisionType type;

    public static CollisionShapeEvent get(BlockState state, BlockPos pos, CollisionType type) {
        INSTANCE.state = state;
        INSTANCE.pos = pos;
        INSTANCE.shape = null;
        INSTANCE.type = type;
        return INSTANCE;
    }
}

package xyz.mathax.mathaxclient.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockCollisionSpliterator;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import xyz.mathax.mathaxclient.MatHax;
import xyz.mathax.mathaxclient.events.world.CollisionShapeEvent;

@Mixin(BlockCollisionSpliterator.class)
public class BlockCollisionSpliteratorMixin {
    @Redirect(method = "computeNext", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;getCollisionShape(Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/ShapeContext;)Lnet/minecraft/util/shape/VoxelShape;"))
    private VoxelShape   onComputeNextCollisionBox(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        VoxelShape shape = state.getCollisionShape(world, pos, context);
        CollisionShapeEvent event = state.getFluidState().isEmpty() ? MatHax.EVENT_BUS.post(CollisionShapeEvent.get(state, pos, shape, CollisionShapeEvent.CollisionType.BLOCK)) : MatHax.EVENT_BUS.post(CollisionShapeEvent.get(state.getFluidState().getBlockState(), pos, shape, CollisionShapeEvent.CollisionType.FLUID));
        if (event.isCancelled()) {
            return VoxelShapes.empty();
        } else {
            return event.shape;
        }
    }
}
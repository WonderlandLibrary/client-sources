package mathax.client.mixin;

import mathax.client.MatHax;
import mathax.client.events.world.CollisionShapeEvent;
import net.minecraft.block.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FluidBlock.class)
public abstract class FluidBlockMixin extends Block implements FluidDrainable {
    public FluidBlockMixin(Settings settings) {
        super(settings);
    }

    @Inject(method = "getCollisionShape", at = @At("HEAD"), cancellable = true)
    private void onGetCollisionShape(BlockState state, BlockView view, BlockPos pos, ShapeContext context, CallbackInfoReturnable<VoxelShape> info) {
        CollisionShapeEvent event = MatHax.EVENT_BUS.post(CollisionShapeEvent.get(state, pos, CollisionShapeEvent.CollisionType.FLUID));

        if (event.shape != null) info.setReturnValue(event.shape);
    }
}

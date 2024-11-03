package dev.stephen.nexus.mixin.game;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.stephen.nexus.Client;
import dev.stephen.nexus.event.impl.world.EventBlockShape;
import net.minecraft.block.BlockState;
import net.minecraft.block.FluidBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(FluidBlock.class)
public class MixinFluidBlock {

    @ModifyReturnValue(method = "getCollisionShape", at = @At("RETURN"))
    private VoxelShape getCollisionShapeInject(VoxelShape original, BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (pos == null) {
            return original;
        }
        if(Client.INSTANCE == null || Client.INSTANCE.getEventManager() == null){
            return original;
        }
        EventBlockShape eventBlockShape = new EventBlockShape(state,pos,original);
        Client.INSTANCE.getEventManager().post(eventBlockShape);
        return original;
    }

}

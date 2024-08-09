package mathax.client.mixin;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.MapCodec;
import mathax.client.events.world.BlockActivateEvent;
import mathax.client.MatHax;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.property.Property;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BlockState.class)
public abstract class BlockStateMixin extends AbstractBlock.AbstractBlockState {
    public BlockStateMixin(Block block, ImmutableMap<Property<?>, Comparable<?>> propertyMap, MapCodec<BlockState> mapCodec) {
        super(block, propertyMap, mapCodec);
    }

    @Override
    public ActionResult onUse(World world, PlayerEntity player, Hand hand, BlockHitResult hit) {
        MatHax.EVENT_BUS.post(BlockActivateEvent.get((BlockState) (Object) this));
        return super.onUse(world, player, hand, hit);
    }
}

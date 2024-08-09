/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import javax.annotation.Nullable;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.JigsawTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.jigsaw.JigsawOrientation;
import net.minecraft.world.gen.feature.template.Template;

public class JigsawBlock
extends Block
implements ITileEntityProvider {
    public static final EnumProperty<JigsawOrientation> ORIENTATION = BlockStateProperties.ORIENTATION;

    protected JigsawBlock(AbstractBlock.Properties properties) {
        super(properties);
        this.setDefaultState((BlockState)((BlockState)this.stateContainer.getBaseState()).with(ORIENTATION, JigsawOrientation.NORTH_UP));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(ORIENTATION);
    }

    @Override
    public BlockState rotate(BlockState blockState, Rotation rotation) {
        return (BlockState)blockState.with(ORIENTATION, rotation.getOrientation().func_235531_a_(blockState.get(ORIENTATION)));
    }

    @Override
    public BlockState mirror(BlockState blockState, Mirror mirror) {
        return (BlockState)blockState.with(ORIENTATION, mirror.getOrientation().func_235531_a_(blockState.get(ORIENTATION)));
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext blockItemUseContext) {
        Direction direction = blockItemUseContext.getFace();
        Direction direction2 = direction.getAxis() == Direction.Axis.Y ? blockItemUseContext.getPlacementHorizontalFacing().getOpposite() : Direction.UP;
        return (BlockState)this.getDefaultState().with(ORIENTATION, JigsawOrientation.func_239641_a_(direction, direction2));
    }

    @Override
    @Nullable
    public TileEntity createNewTileEntity(IBlockReader iBlockReader) {
        return new JigsawTileEntity();
    }

    @Override
    public ActionResultType onBlockActivated(BlockState blockState, World world, BlockPos blockPos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult blockRayTraceResult) {
        TileEntity tileEntity = world.getTileEntity(blockPos);
        if (tileEntity instanceof JigsawTileEntity && playerEntity.canUseCommandBlock()) {
            playerEntity.openJigsaw((JigsawTileEntity)tileEntity);
            return ActionResultType.func_233537_a_(world.isRemote);
        }
        return ActionResultType.PASS;
    }

    public static boolean hasJigsawMatch(Template.BlockInfo blockInfo, Template.BlockInfo blockInfo2) {
        Direction direction = JigsawBlock.getConnectingDirection(blockInfo.state);
        Direction direction2 = JigsawBlock.getConnectingDirection(blockInfo2.state);
        Direction direction3 = JigsawBlock.getJigsawAlignmentDirection(blockInfo.state);
        Direction direction4 = JigsawBlock.getJigsawAlignmentDirection(blockInfo2.state);
        JigsawTileEntity.OrientationType orientationType = JigsawTileEntity.OrientationType.func_235673_a_(blockInfo.nbt.getString("joint")).orElseGet(() -> JigsawBlock.lambda$hasJigsawMatch$0(direction));
        boolean bl = orientationType == JigsawTileEntity.OrientationType.ROLLABLE;
        return direction == direction2.getOpposite() && (bl || direction3 == direction4) && blockInfo.nbt.getString("target").equals(blockInfo2.nbt.getString("name"));
    }

    public static Direction getConnectingDirection(BlockState blockState) {
        return blockState.get(ORIENTATION).func_239642_b_();
    }

    public static Direction getJigsawAlignmentDirection(BlockState blockState) {
        return blockState.get(ORIENTATION).func_239644_c_();
    }

    private static JigsawTileEntity.OrientationType lambda$hasJigsawMatch$0(Direction direction) {
        return direction.getAxis().isHorizontal() ? JigsawTileEntity.OrientationType.ALIGNED : JigsawTileEntity.OrientationType.ROLLABLE;
    }
}


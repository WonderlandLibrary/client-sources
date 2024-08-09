/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import java.util.Map;
import java.util.Random;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ContainerBlock;
import net.minecraft.block.DirectionalBlock;
import net.minecraft.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IDispenseItemBehavior;
import net.minecraft.dispenser.IPosition;
import net.minecraft.dispenser.Position;
import net.minecraft.dispenser.ProxyBlockSource;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.DispenserTileEntity;
import net.minecraft.tileentity.DropperTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class DispenserBlock
extends ContainerBlock {
    public static final DirectionProperty FACING = DirectionalBlock.FACING;
    public static final BooleanProperty TRIGGERED = BlockStateProperties.TRIGGERED;
    private static final Map<Item, IDispenseItemBehavior> DISPENSE_BEHAVIOR_REGISTRY = Util.make(new Object2ObjectOpenHashMap(), DispenserBlock::lambda$static$0);

    public static void registerDispenseBehavior(IItemProvider iItemProvider, IDispenseItemBehavior iDispenseItemBehavior) {
        DISPENSE_BEHAVIOR_REGISTRY.put(iItemProvider.asItem(), iDispenseItemBehavior);
    }

    protected DispenserBlock(AbstractBlock.Properties properties) {
        super(properties);
        this.setDefaultState((BlockState)((BlockState)((BlockState)this.stateContainer.getBaseState()).with(FACING, Direction.NORTH)).with(TRIGGERED, false));
    }

    @Override
    public ActionResultType onBlockActivated(BlockState blockState, World world, BlockPos blockPos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult blockRayTraceResult) {
        if (world.isRemote) {
            return ActionResultType.SUCCESS;
        }
        TileEntity tileEntity = world.getTileEntity(blockPos);
        if (tileEntity instanceof DispenserTileEntity) {
            playerEntity.openContainer((DispenserTileEntity)tileEntity);
            if (tileEntity instanceof DropperTileEntity) {
                playerEntity.addStat(Stats.INSPECT_DROPPER);
            } else {
                playerEntity.addStat(Stats.INSPECT_DISPENSER);
            }
        }
        return ActionResultType.CONSUME;
    }

    protected void dispense(ServerWorld serverWorld, BlockPos blockPos) {
        ProxyBlockSource proxyBlockSource = new ProxyBlockSource(serverWorld, blockPos);
        DispenserTileEntity dispenserTileEntity = (DispenserTileEntity)proxyBlockSource.getBlockTileEntity();
        int n = dispenserTileEntity.getDispenseSlot();
        if (n < 0) {
            serverWorld.playEvent(1001, blockPos, 0);
        } else {
            ItemStack itemStack = dispenserTileEntity.getStackInSlot(n);
            IDispenseItemBehavior iDispenseItemBehavior = this.getBehavior(itemStack);
            if (iDispenseItemBehavior != IDispenseItemBehavior.NOOP) {
                dispenserTileEntity.setInventorySlotContents(n, iDispenseItemBehavior.dispense(proxyBlockSource, itemStack));
            }
        }
    }

    protected IDispenseItemBehavior getBehavior(ItemStack itemStack) {
        return DISPENSE_BEHAVIOR_REGISTRY.get(itemStack.getItem());
    }

    @Override
    public void neighborChanged(BlockState blockState, World world, BlockPos blockPos, Block block, BlockPos blockPos2, boolean bl) {
        boolean bl2 = world.isBlockPowered(blockPos) || world.isBlockPowered(blockPos.up());
        boolean bl3 = blockState.get(TRIGGERED);
        if (bl2 && !bl3) {
            world.getPendingBlockTicks().scheduleTick(blockPos, this, 4);
            world.setBlockState(blockPos, (BlockState)blockState.with(TRIGGERED, true), 1);
        } else if (!bl2 && bl3) {
            world.setBlockState(blockPos, (BlockState)blockState.with(TRIGGERED, false), 1);
        }
    }

    @Override
    public void tick(BlockState blockState, ServerWorld serverWorld, BlockPos blockPos, Random random2) {
        this.dispense(serverWorld, blockPos);
    }

    @Override
    public TileEntity createNewTileEntity(IBlockReader iBlockReader) {
        return new DispenserTileEntity();
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext blockItemUseContext) {
        return (BlockState)this.getDefaultState().with(FACING, blockItemUseContext.getNearestLookingDirection().getOpposite());
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos blockPos, BlockState blockState, LivingEntity livingEntity, ItemStack itemStack) {
        TileEntity tileEntity;
        if (itemStack.hasDisplayName() && (tileEntity = world.getTileEntity(blockPos)) instanceof DispenserTileEntity) {
            ((DispenserTileEntity)tileEntity).setCustomName(itemStack.getDisplayName());
        }
    }

    @Override
    public void onReplaced(BlockState blockState, World world, BlockPos blockPos, BlockState blockState2, boolean bl) {
        if (!blockState.isIn(blockState2.getBlock())) {
            TileEntity tileEntity = world.getTileEntity(blockPos);
            if (tileEntity instanceof DispenserTileEntity) {
                InventoryHelper.dropInventoryItems(world, blockPos, (IInventory)((DispenserTileEntity)tileEntity));
                world.updateComparatorOutputLevel(blockPos, this);
            }
            super.onReplaced(blockState, world, blockPos, blockState2, bl);
        }
    }

    public static IPosition getDispensePosition(IBlockSource iBlockSource) {
        Direction direction = iBlockSource.getBlockState().get(FACING);
        double d = iBlockSource.getX() + 0.7 * (double)direction.getXOffset();
        double d2 = iBlockSource.getY() + 0.7 * (double)direction.getYOffset();
        double d3 = iBlockSource.getZ() + 0.7 * (double)direction.getZOffset();
        return new Position(d, d2, d3);
    }

    @Override
    public boolean hasComparatorInputOverride(BlockState blockState) {
        return false;
    }

    @Override
    public int getComparatorInputOverride(BlockState blockState, World world, BlockPos blockPos) {
        return Container.calcRedstone(world.getTileEntity(blockPos));
    }

    @Override
    public BlockRenderType getRenderType(BlockState blockState) {
        return BlockRenderType.MODEL;
    }

    @Override
    public BlockState rotate(BlockState blockState, Rotation rotation) {
        return (BlockState)blockState.with(FACING, rotation.rotate(blockState.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState blockState, Mirror mirror) {
        return blockState.rotate(mirror.toRotation(blockState.get(FACING)));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING, TRIGGERED);
    }

    private static void lambda$static$0(Object2ObjectOpenHashMap object2ObjectOpenHashMap) {
        object2ObjectOpenHashMap.defaultReturnValue(new DefaultDispenseItemBehavior());
    }
}


/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ContainerBlock;
import net.minecraft.block.DirectionalBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.CommandBlockLogic;
import net.minecraft.tileentity.CommandBlockTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.StringUtils;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.GameRules;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CommandBlockBlock
extends ContainerBlock {
    private static final Logger LOGGER = LogManager.getLogger();
    public static final DirectionProperty FACING = DirectionalBlock.FACING;
    public static final BooleanProperty CONDITIONAL = BlockStateProperties.CONDITIONAL;

    public CommandBlockBlock(AbstractBlock.Properties properties) {
        super(properties);
        this.setDefaultState((BlockState)((BlockState)((BlockState)this.stateContainer.getBaseState()).with(FACING, Direction.NORTH)).with(CONDITIONAL, false));
    }

    @Override
    public TileEntity createNewTileEntity(IBlockReader iBlockReader) {
        CommandBlockTileEntity commandBlockTileEntity = new CommandBlockTileEntity();
        commandBlockTileEntity.setAuto(this == Blocks.CHAIN_COMMAND_BLOCK);
        return commandBlockTileEntity;
    }

    @Override
    public void neighborChanged(BlockState blockState, World world, BlockPos blockPos, Block block, BlockPos blockPos2, boolean bl) {
        TileEntity tileEntity;
        if (!world.isRemote && (tileEntity = world.getTileEntity(blockPos)) instanceof CommandBlockTileEntity) {
            CommandBlockTileEntity commandBlockTileEntity = (CommandBlockTileEntity)tileEntity;
            boolean bl2 = world.isBlockPowered(blockPos);
            boolean bl3 = commandBlockTileEntity.isPowered();
            commandBlockTileEntity.setPowered(bl2);
            if (!bl3 && !commandBlockTileEntity.isAuto() && commandBlockTileEntity.getMode() != CommandBlockTileEntity.Mode.SEQUENCE && bl2) {
                commandBlockTileEntity.setConditionMet();
                world.getPendingBlockTicks().scheduleTick(blockPos, this, 1);
            }
        }
    }

    @Override
    public void tick(BlockState blockState, ServerWorld serverWorld, BlockPos blockPos, Random random2) {
        TileEntity tileEntity = serverWorld.getTileEntity(blockPos);
        if (tileEntity instanceof CommandBlockTileEntity) {
            CommandBlockTileEntity commandBlockTileEntity = (CommandBlockTileEntity)tileEntity;
            CommandBlockLogic commandBlockLogic = commandBlockTileEntity.getCommandBlockLogic();
            boolean bl = !StringUtils.isNullOrEmpty(commandBlockLogic.getCommand());
            CommandBlockTileEntity.Mode mode = commandBlockTileEntity.getMode();
            boolean bl2 = commandBlockTileEntity.isConditionMet();
            if (mode == CommandBlockTileEntity.Mode.AUTO) {
                commandBlockTileEntity.setConditionMet();
                if (bl2) {
                    this.execute(blockState, serverWorld, blockPos, commandBlockLogic, bl);
                } else if (commandBlockTileEntity.isConditional()) {
                    commandBlockLogic.setSuccessCount(0);
                }
                if (commandBlockTileEntity.isPowered() || commandBlockTileEntity.isAuto()) {
                    serverWorld.getPendingBlockTicks().scheduleTick(blockPos, this, 1);
                }
            } else if (mode == CommandBlockTileEntity.Mode.REDSTONE) {
                if (bl2) {
                    this.execute(blockState, serverWorld, blockPos, commandBlockLogic, bl);
                } else if (commandBlockTileEntity.isConditional()) {
                    commandBlockLogic.setSuccessCount(0);
                }
            }
            serverWorld.updateComparatorOutputLevel(blockPos, this);
        }
    }

    private void execute(BlockState blockState, World world, BlockPos blockPos, CommandBlockLogic commandBlockLogic, boolean bl) {
        if (bl) {
            commandBlockLogic.trigger(world);
        } else {
            commandBlockLogic.setSuccessCount(0);
        }
        CommandBlockBlock.executeChain(world, blockPos, blockState.get(FACING));
    }

    @Override
    public ActionResultType onBlockActivated(BlockState blockState, World world, BlockPos blockPos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult blockRayTraceResult) {
        TileEntity tileEntity = world.getTileEntity(blockPos);
        if (tileEntity instanceof CommandBlockTileEntity && playerEntity.canUseCommandBlock()) {
            playerEntity.openCommandBlock((CommandBlockTileEntity)tileEntity);
            return ActionResultType.func_233537_a_(world.isRemote);
        }
        return ActionResultType.PASS;
    }

    @Override
    public boolean hasComparatorInputOverride(BlockState blockState) {
        return false;
    }

    @Override
    public int getComparatorInputOverride(BlockState blockState, World world, BlockPos blockPos) {
        TileEntity tileEntity = world.getTileEntity(blockPos);
        return tileEntity instanceof CommandBlockTileEntity ? ((CommandBlockTileEntity)tileEntity).getCommandBlockLogic().getSuccessCount() : 0;
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos blockPos, BlockState blockState, LivingEntity livingEntity, ItemStack itemStack) {
        TileEntity tileEntity = world.getTileEntity(blockPos);
        if (tileEntity instanceof CommandBlockTileEntity) {
            CommandBlockTileEntity commandBlockTileEntity = (CommandBlockTileEntity)tileEntity;
            CommandBlockLogic commandBlockLogic = commandBlockTileEntity.getCommandBlockLogic();
            if (itemStack.hasDisplayName()) {
                commandBlockLogic.setName(itemStack.getDisplayName());
            }
            if (!world.isRemote) {
                if (itemStack.getChildTag("BlockEntityTag") == null) {
                    commandBlockLogic.setTrackOutput(world.getGameRules().getBoolean(GameRules.SEND_COMMAND_FEEDBACK));
                    commandBlockTileEntity.setAuto(this == Blocks.CHAIN_COMMAND_BLOCK);
                }
                if (commandBlockTileEntity.getMode() == CommandBlockTileEntity.Mode.SEQUENCE) {
                    boolean bl = world.isBlockPowered(blockPos);
                    commandBlockTileEntity.setPowered(bl);
                }
            }
        }
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
        builder.add(FACING, CONDITIONAL);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext blockItemUseContext) {
        return (BlockState)this.getDefaultState().with(FACING, blockItemUseContext.getNearestLookingDirection().getOpposite());
    }

    private static void executeChain(World world, BlockPos blockPos, Direction direction) {
        BlockPos.Mutable mutable = blockPos.toMutable();
        GameRules gameRules = world.getGameRules();
        int n = gameRules.getInt(GameRules.MAX_COMMAND_CHAIN_LENGTH);
        while (n-- > 0) {
            CommandBlockTileEntity commandBlockTileEntity;
            TileEntity tileEntity;
            mutable.move(direction);
            BlockState blockState = world.getBlockState(mutable);
            Block block = blockState.getBlock();
            if (!blockState.isIn(Blocks.CHAIN_COMMAND_BLOCK) || !((tileEntity = world.getTileEntity(mutable)) instanceof CommandBlockTileEntity) || (commandBlockTileEntity = (CommandBlockTileEntity)tileEntity).getMode() != CommandBlockTileEntity.Mode.SEQUENCE) break;
            if (commandBlockTileEntity.isPowered() || commandBlockTileEntity.isAuto()) {
                CommandBlockLogic commandBlockLogic = commandBlockTileEntity.getCommandBlockLogic();
                if (commandBlockTileEntity.setConditionMet()) {
                    if (!commandBlockLogic.trigger(world)) break;
                    world.updateComparatorOutputLevel(mutable, block);
                } else if (commandBlockTileEntity.isConditional()) {
                    commandBlockLogic.setSuccessCount(0);
                }
            }
            direction = blockState.get(FACING);
        }
        if (n <= 0) {
            int n2 = Math.max(gameRules.getInt(GameRules.MAX_COMMAND_CHAIN_LENGTH), 0);
            LOGGER.warn("Command Block chain tried to execute more than {} steps!", (Object)n2);
        }
    }
}


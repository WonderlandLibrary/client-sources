/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AbstractRailBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IDispenseItemBehavior;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.minecart.AbstractMinecartEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.state.StateHolder;
import net.minecraft.state.properties.RailShape;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class MinecartItem
extends Item {
    private static final IDispenseItemBehavior MINECART_DISPENSER_BEHAVIOR = new DefaultDispenseItemBehavior(){
        private final DefaultDispenseItemBehavior behaviourDefaultDispenseItem = new DefaultDispenseItemBehavior();

        @Override
        public ItemStack dispenseStack(IBlockSource iBlockSource, ItemStack itemStack) {
            Object object;
            double d;
            RailShape railShape;
            Direction direction = iBlockSource.getBlockState().get(DispenserBlock.FACING);
            ServerWorld serverWorld = iBlockSource.getWorld();
            double d2 = iBlockSource.getX() + (double)direction.getXOffset() * 1.125;
            double d3 = Math.floor(iBlockSource.getY()) + (double)direction.getYOffset();
            double d4 = iBlockSource.getZ() + (double)direction.getZOffset() * 1.125;
            BlockPos blockPos = iBlockSource.getBlockPos().offset(direction);
            BlockState blockState = serverWorld.getBlockState(blockPos);
            RailShape railShape2 = railShape = blockState.getBlock() instanceof AbstractRailBlock ? blockState.get(((AbstractRailBlock)blockState.getBlock()).getShapeProperty()) : RailShape.NORTH_SOUTH;
            if (blockState.isIn(BlockTags.RAILS)) {
                d = railShape.isAscending() ? 0.6 : 0.1;
            } else {
                if (!blockState.isAir() || !serverWorld.getBlockState(blockPos.down()).isIn(BlockTags.RAILS)) {
                    return this.behaviourDefaultDispenseItem.dispense(iBlockSource, itemStack);
                }
                object = serverWorld.getBlockState(blockPos.down());
                RailShape railShape3 = ((AbstractBlock.AbstractBlockState)object).getBlock() instanceof AbstractRailBlock ? ((StateHolder)object).get(((AbstractRailBlock)((AbstractBlock.AbstractBlockState)object).getBlock()).getShapeProperty()) : RailShape.NORTH_SOUTH;
                d = direction != Direction.DOWN && railShape3.isAscending() ? -0.4 : -0.9;
            }
            object = AbstractMinecartEntity.create(serverWorld, d2, d3 + d, d4, ((MinecartItem)itemStack.getItem()).minecartType);
            if (itemStack.hasDisplayName()) {
                ((Entity)object).setCustomName(itemStack.getDisplayName());
            }
            serverWorld.addEntity((Entity)object);
            itemStack.shrink(1);
            return itemStack;
        }

        @Override
        protected void playDispenseSound(IBlockSource iBlockSource) {
            iBlockSource.getWorld().playEvent(1000, iBlockSource.getBlockPos(), 0);
        }
    };
    private final AbstractMinecartEntity.Type minecartType;

    public MinecartItem(AbstractMinecartEntity.Type type, Item.Properties properties) {
        super(properties);
        this.minecartType = type;
        DispenserBlock.registerDispenseBehavior(this, MINECART_DISPENSER_BEHAVIOR);
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext itemUseContext) {
        BlockPos blockPos;
        World world = itemUseContext.getWorld();
        BlockState blockState = world.getBlockState(blockPos = itemUseContext.getPos());
        if (!blockState.isIn(BlockTags.RAILS)) {
            return ActionResultType.FAIL;
        }
        ItemStack itemStack = itemUseContext.getItem();
        if (!world.isRemote) {
            RailShape railShape = blockState.getBlock() instanceof AbstractRailBlock ? blockState.get(((AbstractRailBlock)blockState.getBlock()).getShapeProperty()) : RailShape.NORTH_SOUTH;
            double d = 0.0;
            if (railShape.isAscending()) {
                d = 0.5;
            }
            AbstractMinecartEntity abstractMinecartEntity = AbstractMinecartEntity.create(world, (double)blockPos.getX() + 0.5, (double)blockPos.getY() + 0.0625 + d, (double)blockPos.getZ() + 0.5, this.minecartType);
            if (itemStack.hasDisplayName()) {
                abstractMinecartEntity.setCustomName(itemStack.getDisplayName());
            }
            world.addEntity(abstractMinecartEntity);
        }
        itemStack.shrink(1);
        return ActionResultType.func_233537_a_(world.isRemote);
    }
}


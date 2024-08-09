package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;

import javax.annotation.Nullable;

public class OperatorOnlyItem extends BlockItem
{
    public OperatorOnlyItem(Block blockIn, Item.Properties builder)
    {
        super(blockIn, builder);
    }

    @Nullable
    protected BlockState getStateForPlacement(BlockItemUseContext context)
    {
        PlayerEntity playerentity = context.getPlayer();
        return playerentity != null && !playerentity.canUseCommandBlock() ? null : super.getStateForPlacement(context);
    }
}

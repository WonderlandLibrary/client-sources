/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.world.World;

public class LilyPadItem
extends BlockItem {
    public LilyPadItem(Block block, Item.Properties properties) {
        super(block, properties);
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext itemUseContext) {
        return ActionResultType.PASS;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity playerEntity, Hand hand) {
        BlockRayTraceResult blockRayTraceResult = LilyPadItem.rayTrace(world, playerEntity, RayTraceContext.FluidMode.SOURCE_ONLY);
        BlockRayTraceResult blockRayTraceResult2 = blockRayTraceResult.withPosition(blockRayTraceResult.getPos().up());
        ActionResultType actionResultType = super.onItemUse(new ItemUseContext(playerEntity, hand, blockRayTraceResult2));
        return new ActionResult<ItemStack>(actionResultType, playerEntity.getHeldItem(hand));
    }
}


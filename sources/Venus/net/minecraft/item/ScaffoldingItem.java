/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item;

import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ScaffoldingBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.network.play.server.SChatPacket;
import net.minecraft.util.Direction;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class ScaffoldingItem
extends BlockItem {
    public ScaffoldingItem(Block block, Item.Properties properties) {
        super(block, properties);
    }

    @Override
    @Nullable
    public BlockItemUseContext getBlockItemUseContext(BlockItemUseContext blockItemUseContext) {
        Block block;
        BlockPos blockPos = blockItemUseContext.getPos();
        World world = blockItemUseContext.getWorld();
        BlockState blockState = world.getBlockState(blockPos);
        if (!blockState.isIn(block = this.getBlock())) {
            return ScaffoldingBlock.getDistance(world, blockPos) == 7 ? null : blockItemUseContext;
        }
        Direction direction = blockItemUseContext.hasSecondaryUseForPlayer() ? (blockItemUseContext.isInside() ? blockItemUseContext.getFace().getOpposite() : blockItemUseContext.getFace()) : (blockItemUseContext.getFace() == Direction.UP ? blockItemUseContext.getPlacementHorizontalFacing() : Direction.UP);
        int n = 0;
        BlockPos.Mutable mutable = blockPos.toMutable().move(direction);
        while (n < 7) {
            if (!world.isRemote && !World.isValid(mutable)) {
                PlayerEntity playerEntity = blockItemUseContext.getPlayer();
                int n2 = world.getHeight();
                if (!(playerEntity instanceof ServerPlayerEntity) || mutable.getY() < n2) break;
                SChatPacket sChatPacket = new SChatPacket(new TranslationTextComponent("build.tooHigh", n2).mergeStyle(TextFormatting.RED), ChatType.GAME_INFO, Util.DUMMY_UUID);
                ((ServerPlayerEntity)playerEntity).connection.sendPacket(sChatPacket);
                break;
            }
            blockState = world.getBlockState(mutable);
            if (!blockState.isIn(this.getBlock())) {
                if (!blockState.isReplaceable(blockItemUseContext)) break;
                return BlockItemUseContext.func_221536_a(blockItemUseContext, mutable, direction);
            }
            mutable.move(direction);
            if (!direction.getAxis().isHorizontal()) continue;
            ++n;
        }
        return null;
    }

    @Override
    protected boolean checkPosition() {
        return true;
    }
}


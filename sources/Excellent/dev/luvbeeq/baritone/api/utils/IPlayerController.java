package dev.luvbeeq.baritone.api.utils;

import dev.luvbeeq.baritone.api.BaritoneAPI;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.GameType;
import net.minecraft.world.World;

/**
 * @author Brady
 * @since 12/14/2018
 */
public interface IPlayerController {

    void syncHeldItem();

    boolean hasBrokenBlock();

    boolean onPlayerDamageBlock(BlockPos pos, Direction side);

    void resetBlockRemoving();

    ItemStack windowClick(int windowId, int slotId, int mouseButton, ClickType type, PlayerEntity player);

    GameType getGameType();

    ActionResultType processRightClickBlock(ClientPlayerEntity player, World world, Hand hand, BlockRayTraceResult result);

    ActionResultType processRightClick(ClientPlayerEntity player, World world, Hand hand);

    boolean clickBlock(BlockPos loc, Direction face);

    void setHittingBlock(boolean hittingBlock);

    default double getBlockReachDistance() {
        return this.getGameType().isCreative() ? 5.0F : BaritoneAPI.getSettings().blockReachDistance.value;
    }
}

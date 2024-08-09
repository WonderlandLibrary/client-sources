package dev.excellent.client.module.impl.movement;

import dev.excellent.api.event.impl.player.UpdateEvent;
import dev.excellent.api.interfaces.event.Listener;
import dev.excellent.client.module.api.Category;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.api.ModuleInfo;
import dev.excellent.impl.util.player.MoveUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ModuleInfo(name = "Air Jump", description = "Позволяет вам прыгать по воздуху.", category = Category.MOVEMENT)
public class AirJump extends Module {
    private final List<Block> incorrectBlocks = Arrays.asList(Blocks.AIR, Blocks.WATER, Blocks.LAVA, Blocks.BLACK_BED, Blocks.BLUE_BED, Blocks.BROWN_BED, Blocks.LIGHT_BLUE_BED, Blocks.CYAN_BED, Blocks.GRAY_BED, Blocks.GREEN_BED, Blocks.LIGHT_GRAY_BED, Blocks.LIME_BED, Blocks.MAGENTA_BED,
            Blocks.ORANGE_BED, Blocks.PINK_BED, Blocks.PURPLE_BED, Blocks.RED_BED, Blocks.WHITE_BED, Blocks.YELLOW_BED, Blocks.CAKE, Blocks.TALL_GRASS, Blocks.STONE_BUTTON, Blocks.BIRCH_BUTTON, Blocks.ACACIA_BUTTON, Blocks.CRIMSON_BUTTON, Blocks.OAK_BUTTON, Blocks.DARK_OAK_BUTTON, Blocks.ACACIA_BUTTON, Blocks.JUNGLE_BUTTON, Blocks.POLISHED_BLACKSTONE_BUTTON, Blocks.WARPED_BUTTON,
            Blocks.FLOWER_POT, Blocks.CHORUS_FLOWER, Blocks.CORNFLOWER, Blocks.POTTED_CORNFLOWER, Blocks.SUNFLOWER, Blocks.VINE, Blocks.ACACIA_FENCE, Blocks.ACACIA_FENCE_GATE, Blocks.BIRCH_FENCE, Blocks.BIRCH_FENCE_GATE, Blocks.DARK_OAK_FENCE, Blocks.DARK_OAK_FENCE_GATE,
            Blocks.JUNGLE_FENCE, Blocks.JUNGLE_FENCE_GATE, Blocks.NETHER_BRICK_FENCE, Blocks.OAK_FENCE, Blocks.OAK_FENCE_GATE, Blocks.SPRUCE_FENCE, Blocks.SPRUCE_FENCE_GATE, Blocks.ENCHANTING_TABLE, Blocks.END_PORTAL_FRAME, Blocks.KELP_PLANT, Blocks.TWISTING_VINES_PLANT, Blocks.WEEPING_VINES_PLANT,
            Blocks.SPRUCE_SIGN, Blocks.SPRUCE_WALL_SIGN, Blocks.ACACIA_SIGN, Blocks.ACACIA_WALL_SIGN, Blocks.BIRCH_SIGN, Blocks.BIRCH_WALL_SIGN, Blocks.CRIMSON_SIGN, Blocks.CRIMSON_WALL_SIGN, Blocks.BIRCH_SIGN, Blocks.BIRCH_WALL_SIGN, Blocks.JUNGLE_SIGN, Blocks.JUNGLE_WALL_SIGN, Blocks.OAK_SIGN, Blocks.OAK_WALL_SIGN, Blocks.WARPED_SIGN, Blocks.WARPED_WALL_SIGN,
            Blocks.SKELETON_SKULL, Blocks.WITHER_SKELETON_SKULL, Blocks.ZOMBIE_HEAD, Blocks.ZOMBIE_WALL_HEAD, Blocks.PLAYER_HEAD, Blocks.PLAYER_WALL_HEAD, Blocks.DAYLIGHT_DETECTOR, Blocks.PURPUR_SLAB, Blocks.STONE_SLAB, Blocks.ACACIA_SLAB, Blocks.ANDESITE_SLAB, Blocks.BIRCH_SLAB, Blocks.BLACKSTONE_SLAB, Blocks.BRICK_SLAB,
            Blocks.COBBLESTONE_SLAB, Blocks.CRIMSON_SLAB, Blocks.CUT_RED_SANDSTONE_SLAB, Blocks.CUT_SANDSTONE_SLAB, Blocks.BIRCH_SLAB, Blocks.DARK_OAK_SLAB, Blocks.DARK_PRISMARINE_SLAB, Blocks.DIORITE_SLAB, Blocks.END_STONE_BRICK_SLAB, Blocks.GRANITE_SLAB, Blocks.BRICK_SLAB, Blocks.JUNGLE_SLAB, Blocks.MOSSY_COBBLESTONE_SLAB, Blocks.MOSSY_STONE_BRICK_SLAB, Blocks.OAK_SLAB, Blocks.NETHER_BRICK_SLAB,
            Blocks.POLISHED_DIORITE_SLAB, Blocks.POLISHED_GRANITE_SLAB, Blocks.POLISHED_ANDESITE_SLAB, Blocks.PRISMARINE_BRICK_SLAB, Blocks.QUARTZ_SLAB, Blocks.SANDSTONE_SLAB, Blocks.CARROTS, Blocks.DEAD_BUSH, Blocks.REDSTONE_WIRE, Blocks.REDSTONE_TORCH, Blocks.REDSTONE_WALL_TORCH,
            Blocks.TORCH, Blocks.WALL_TORCH, Blocks.REDSTONE_WIRE, Blocks.SNOW, Blocks.GRASS, Blocks.OXEYE_DAISY);

    private boolean hasIncorrectBlockOnPos(BlockPos pos) {
        BlockState blockState = mc.world.getBlockState(pos);
        Block block = blockState.getBlock();
        return !incorrectBlocks.stream().anyMatch(sBlock -> sBlock.equals(block));
    }

    private List<BlockPos> getCornersBlockPosesOutcube(BlockPos pos, double xzOffset, double yUpOffset, double yDownOffset) {
        List<BlockPos> list = new ArrayList();
        if (xzOffset != 0) {
            if (yUpOffset != 0) {
                list.add(pos.add(-xzOffset, yUpOffset, -xzOffset));
                list.add(pos.add(xzOffset, yUpOffset, xzOffset));
                list.add(pos.add(-xzOffset, yUpOffset, xzOffset));
                list.add(pos.add(xzOffset, yUpOffset, -xzOffset));
            }
            if (yDownOffset != 0) {
                list.add(pos.add(-xzOffset, -yDownOffset, -xzOffset));
                list.add(pos.add(xzOffset, -yDownOffset, xzOffset));
                list.add(pos.add(-xzOffset, -yDownOffset, xzOffset));
                list.add(pos.add(xzOffset, -yDownOffset, -xzOffset));
            }
        } else {
            list.add(pos);
            if (yUpOffset != 0) list.add(pos.add(0, yUpOffset, 0));
            if (yDownOffset != 0) list.add(pos.add(0, -yDownOffset, 0));
        }
        return list;
    }

    private final Listener<UpdateEvent> onUpdate = event -> {
        boolean doGround = !mc.player.isOnGround() && mc.player.movementInput.jump && (mc.player.collidedHorizontally && mc.player.ticksExisted % 4 == 0 || getCornersBlockPosesOutcube(new BlockPos(mc.player.getPosX(), mc.player.getPosY(), mc.player.getPosZ()), .999D, .499D, .999D).stream().anyMatch(pos -> hasIncorrectBlockOnPos(pos)));
        if (doGround) mc.player.setOnGround(true);
        if (mc.player.isOnGround() && !mc.player.collidedVertically) {
            mc.player.motion.y = 0;
            if (MoveUtil.isMoving()) MoveUtil.strafe();
        }
    };
}

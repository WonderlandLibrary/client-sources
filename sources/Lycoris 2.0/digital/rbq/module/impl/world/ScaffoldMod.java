/*
 * Decompiled with CFR 0.150.
 */
package digital.rbq.module.impl.world;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import me.zane.basicbus.api.annotations.Listener;
import net.minecraft.block.Block;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import digital.rbq.annotations.Label;
import digital.rbq.core.Autumn;
import digital.rbq.events.player.MotionUpdateEvent;
import digital.rbq.module.Module;
import digital.rbq.module.ModuleCategory;
import digital.rbq.module.annotations.Aliases;
import digital.rbq.module.annotations.Bind;
import digital.rbq.module.annotations.Category;
import digital.rbq.utils.Stopwatch;

@Label(value="Scaffold")
@Bind(value="Y")
@Category(value=ModuleCategory.WORLD)
@Aliases(value={"scaffold", "scaffoldwalk", "magiccarpet"})
public final class ScaffoldMod
extends Module {
    private final List<Block> invalidBlocks = Arrays.asList(Blocks.enchanting_table, Blocks.furnace, Blocks.carpet, Blocks.crafting_table, Blocks.trapped_chest, Blocks.chest, Blocks.dispenser, Blocks.air, Blocks.water, Blocks.lava, Blocks.flowing_water, Blocks.flowing_lava, Blocks.sand, Blocks.snow_layer, Blocks.torch, Blocks.anvil, Blocks.jukebox, Blocks.stone_button, Blocks.wooden_button, Blocks.lever, Blocks.noteblock, Blocks.stone_pressure_plate, Blocks.light_weighted_pressure_plate, Blocks.wooden_pressure_plate, Blocks.heavy_weighted_pressure_plate, Blocks.stone_slab, Blocks.wooden_slab, Blocks.stone_slab2, Blocks.red_mushroom, Blocks.brown_mushroom, Blocks.yellow_flower, Blocks.red_flower, Blocks.anvil, Blocks.glass_pane, Blocks.stained_glass_pane, Blocks.iron_bars, Blocks.cactus, Blocks.ladder, Blocks.web);
    private final List<Block> validBlocks = Arrays.asList(Blocks.air, Blocks.water, Blocks.flowing_water, Blocks.lava, Blocks.flowing_lava);
    private final BlockPos[] blockPositions = new BlockPos[]{new BlockPos(-1, 0, 0), new BlockPos(1, 0, 0), new BlockPos(0, 0, -1), new BlockPos(0, 0, 1)};
    private final EnumFacing[] facings = new EnumFacing[]{EnumFacing.EAST, EnumFacing.WEST, EnumFacing.SOUTH, EnumFacing.NORTH};
    private final Stopwatch towerStopwatch = new Stopwatch();
    private final Random rng = new Random();
    private float[] angles = new float[2];
    private boolean rotating;
    private int slot;

    @Override
    public void onEnabled() {
        this.angles[0] = 999.0f;
        this.angles[1] = 999.0f;
        this.towerStopwatch.reset();
        this.slot = ScaffoldMod.mc.thePlayer.inventory.currentItem;
    }

    @Override
    public void onDisabled() {
        ScaffoldMod.mc.thePlayer.inventory.currentItem = this.slot;
    }

    @Listener(value=MotionUpdateEvent.class)
    public final void onMotionUpdate(MotionUpdateEvent event) {
        EntityPlayerSP player = ScaffoldMod.mc.thePlayer;
        WorldClient world = ScaffoldMod.mc.theWorld;
        double yDif = 1.0;
        BlockData data = null;
        for (double posY = player.posY - 1.0; posY > 0.0; posY -= 1.0) {
            BlockData newData = this.getBlockData(new BlockPos(player.posX, posY, player.posZ));
            if (newData == null || !((yDif = player.posY - posY) <= 3.0)) continue;
            data = newData;
            break;
        }
        int slot = -1;
        int blockCount = 0;
        for (int i = 0; i < 9; ++i) {
            ItemStack itemStack = player.inventory.getStackInSlot(i);
            if (itemStack == null) continue;
            int stackSize = itemStack.stackSize;
            if (!this.isValidItem(itemStack.getItem()) || stackSize <= blockCount) continue;
            blockCount = stackSize;
            slot = i;
        }
        if (slot == -1) {
            // empty if block
        }
        if (data != null && slot != -1) {
            BlockPos pos = data.pos;
            Block block = world.getBlockState(pos.offset(data.face)).getBlock();
            Vec3 hitVec = this.getVec3(data);
            if (!this.validBlocks.contains(block) || this.isBlockUnder(yDif)) {
                return;
            }
            switch (event.getType()) {
                case PRE: {
                    event.setPitch(79.44f);
                    if (ScaffoldMod.mc.gameSettings.keyBindJump.isKeyDown()) {
                        if (player.isMoving()) break;
                        player.motionX = 0.0;
                        player.motionY = 0.41982;
                        player.motionZ = 0.0;
                        if (!this.towerStopwatch.elapsed(1500L)) break;
                        player.motionY = -0.28;
                        this.towerStopwatch.reset();
                        break;
                    }
                    this.towerStopwatch.reset();
                    break;
                }
                case POST: {
                    int last = player.inventory.currentItem;
                    player.inventory.currentItem = slot;
                    if (ScaffoldMod.mc.playerController.onPlayerRightClick(player, world, player.getCurrentEquippedItem(), pos, data.face, hitVec)) {
                        player.swingItem();
                    }
                    player.inventory.currentItem = last;
                }
            }
        }
    }

    private boolean isBlockUnder(double yOffset) {
        EntityPlayerSP player = ScaffoldMod.mc.thePlayer;
        return !this.validBlocks.contains(ScaffoldMod.mc.theWorld.getBlockState(new BlockPos(player.posX, player.posY - yOffset, player.posZ)).getBlock());
    }

    private Vec3 getVec3(BlockData data) {
        BlockPos pos = data.pos;
        EnumFacing face = data.face;
        double x = (double)pos.getX() + 0.5;
        double y = (double)pos.getY() + 0.5;
        double z = (double)pos.getZ() + 0.5;
        x += (double)face.getFrontOffsetX() / 2.0;
        z += (double)face.getFrontOffsetZ() / 2.0;
        y += (double)face.getFrontOffsetY() / 2.0;
        if (face == EnumFacing.UP || face == EnumFacing.DOWN) {
            x += this.randomNumber(0.3, -0.3);
            z += this.randomNumber(0.3, -0.3);
        } else {
            y += this.randomNumber(0.49, 0.5);
        }
        if (face == EnumFacing.WEST || face == EnumFacing.EAST) {
            z += this.randomNumber(0.3, -0.3);
        }
        if (face == EnumFacing.SOUTH || face == EnumFacing.NORTH) {
            x += this.randomNumber(0.3, -0.3);
        }
        return new Vec3(x, y, z);
    }

    private double randomNumber(double max, double min) {
        return Math.random() * (max - min) + min;
    }

    private BlockData getBlockData(BlockPos pos) {
        BlockPos[] blockPositions = this.blockPositions;
        EnumFacing[] facings = this.facings;
        List<Block> validBlocks = this.validBlocks;
        WorldClient world = ScaffoldMod.mc.theWorld;
        BlockPos posBelow = new BlockPos(0, -1, 0);
        if (!validBlocks.contains(world.getBlockState(pos.add(posBelow)).getBlock())) {
            return new BlockData(pos.add(posBelow), EnumFacing.UP);
        }
        int blockPositionsLength = blockPositions.length;
        for (int i = 0; i < blockPositionsLength; ++i) {
            BlockPos blockPos = pos.add(blockPositions[i]);
            if (!validBlocks.contains(world.getBlockState(blockPos).getBlock())) {
                return new BlockData(blockPos, facings[i]);
            }
            for (int i1 = 0; i1 < blockPositionsLength; ++i1) {
                BlockPos blockPos1 = pos.add(blockPositions[i1]);
                BlockPos blockPos2 = blockPos.add(blockPositions[i1]);
                if (!validBlocks.contains(world.getBlockState(blockPos1).getBlock())) {
                    return new BlockData(blockPos1, facings[i1]);
                }
                if (validBlocks.contains(world.getBlockState(blockPos2).getBlock())) continue;
                return new BlockData(blockPos2, facings[i1]);
            }
        }
        return null;
    }

    private boolean isValidItem(Item item) {
        if (item instanceof ItemBlock) {
            ItemBlock iBlock = (ItemBlock)item;
            Block block = iBlock.getBlock();
            return !this.invalidBlocks.contains(block);
        }
        return false;
    }

    public static ScaffoldMod getInstance() {
        return Autumn.MANAGER_REGISTRY.moduleManager.getModuleOrNull(ScaffoldMod.class);
    }

    private static class BlockData {
        public final BlockPos pos;
        public final EnumFacing face;

        private BlockData(BlockPos pos, EnumFacing face) {
            this.pos = pos;
            this.face = face;
        }
    }
}


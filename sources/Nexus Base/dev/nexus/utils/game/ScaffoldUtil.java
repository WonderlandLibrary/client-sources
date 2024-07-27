package dev.nexus.utils.game;

import dev.nexus.modules.impl.player.Scaffold;
import dev.nexus.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;

public class ScaffoldUtil implements Utils {

    public static Block block(final double x, final double y, final double z) {
        return mc.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
    }

    public static Block blockRelativeToPlayer(final double offsetX, final double offsetY, final double offsetZ) {
        return mc.theWorld.getBlockState(new BlockPos(mc.thePlayer).add(offsetX, offsetY, offsetZ)).getBlock();
    }

    public static Scaffold.BlockData getBlockData() {
        final BlockPos belowBlockPos = new BlockPos(mc.thePlayer.posX, Scaffold.scaffoldYCoord, mc.thePlayer.posZ);
        if (mc.theWorld.getBlockState(belowBlockPos).getBlock() instanceof BlockAir) {
            for (int x = 0; x < 4; x++) {
                for (int z = 0; z < 4; z++) {
                    for (int i = 1; i > -3; i -= 2) {
                        final BlockPos blockPos = belowBlockPos.add(x * i, 0, z * i);
                        if (mc.theWorld.getBlockState(blockPos).getBlock() instanceof BlockAir) {
                            for (EnumFacing direction : EnumFacing.values()) {
                                final BlockPos block = blockPos.offset(direction);
                                final Material material = mc.theWorld.getBlockState(block).getBlock().getMaterial();
                                if (material.isSolid() && !material.isLiquid()) {
                                    return new Scaffold.BlockData(block, direction.getOpposite());
                                }
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    public static Vec3 getNewVector(Scaffold.BlockData lastblockdata) {
        if (lastblockdata == null) {
            return null;
        }
        BlockPos pos = lastblockdata.getPosition();
        EnumFacing facing = lastblockdata.getFacing();
        Vec3 vec3 = new Vec3(pos);

        double amount1 = 0.45 + Math.random() * 0.1;
        double amount2 = 0.45 + Math.random() * 0.1;

        if (facing == EnumFacing.UP) {
            vec3 = vec3.addVector(amount1, 1, amount2);
        } else if (facing == EnumFacing.DOWN) {
            vec3 = vec3.addVector(amount1, 0, amount2);
        } else if (facing == EnumFacing.EAST) {
            vec3 = vec3.addVector(1, amount1, amount2);
        } else if (facing == EnumFacing.WEST) {
            vec3 = vec3.addVector(0, amount1, amount2);
        } else if (facing == EnumFacing.NORTH) {
            vec3 = vec3.addVector(amount1, amount2, 0);
        } else if (facing == EnumFacing.SOUTH) {
            vec3 = vec3.addVector(amount1, amount2, 1);
        }

        return vec3;
    }

    public static int getBlockSlot() {
        for (int i = 0; i < 9; i++) {
            final ItemStack itemStack = mc.thePlayer.inventory.mainInventory[i];
            if (itemStack != null && itemStack.getItem() instanceof ItemBlock && itemStack.stackSize >= 3) {
                final ItemBlock itemBlock = (ItemBlock) itemStack.getItem();

                if (isBlockValid(itemBlock.getBlock())) {
                    return i;
                }
            }
        }

        return mc.thePlayer.inventory.currentItem;
    }

    private static boolean isBlockValid(final Block block) {
        return (block.isFullBlock() || block == Blocks.glass) &&
                block != Blocks.sand &&
                block != Blocks.gravel &&
                block != Blocks.dispenser &&
                block != Blocks.command_block &&
                block != Blocks.noteblock &&
                block != Blocks.furnace &&
                block != Blocks.crafting_table &&
                block != Blocks.tnt &&
                block != Blocks.dropper &&
                block != Blocks.beacon;
    }

    public static Scaffold.BlockData getBlockData2() {
        final BlockPos belowBlockPos = new BlockPos(mc.thePlayer.posX, Scaffold.scaffoldYCoord + 1, mc.thePlayer.posZ);
        if (mc.theWorld.getBlockState(belowBlockPos).getBlock() instanceof BlockAir) {
            for (int x = 0; x < 4; x++) {
                for (int z = 0; z < 4; z++) {
                    for (int i = 1; i > -3; i -= 2) {
                        final BlockPos blockPos = belowBlockPos.add(x * i, 0, z * i);
                        if (mc.theWorld.getBlockState(blockPos).getBlock() instanceof BlockAir) {
                            for (EnumFacing direction : EnumFacing.values()) {
                                final BlockPos block = blockPos.offset(direction);
                                final Material material = mc.theWorld.getBlockState(block).getBlock().getMaterial();
                                if (material.isSolid() && !material.isLiquid()) {
                                    return new Scaffold.BlockData(block, direction.getOpposite());
                                }
                            }
                        }
                    }
                }
            }
        }
        return null;
    }
}
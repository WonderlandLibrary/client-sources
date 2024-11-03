package dev.stephen.nexus.utils.mc;

import dev.stephen.nexus.Client;
import dev.stephen.nexus.module.modules.player.Scaffold;
import dev.stephen.nexus.utils.Utils;
import net.minecraft.block.AirBlock;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

// tenacity just to annoy a person
public class ScaffoldUtils implements Utils {

    public static Scaffold.BlockData getBlockData() {
        final BlockPos belowBlockPos = new BlockPos(mc.player.getBlockX(), Client.INSTANCE.getModuleManager().getModule(Scaffold.class).scaffYCoord, mc.player.getBlockZ());

        if (mc.world.getBlockState(belowBlockPos).getBlock() instanceof AirBlock) {
            for (int x = 0; x < 4; x++) {
                for (int z = 0; z < 4; z++) {
                    for (int i = 1; i > -3; i -= 2) {
                        final BlockPos blockPos = belowBlockPos.add(x * i, 0, z * i);
                        if (mc.world.getBlockState(blockPos).getBlock() instanceof AirBlock) {
                            for (Direction direction : Direction.values()) {
                                final BlockPos block = blockPos.offset(direction);
                                final BlockState material = mc.world.getBlockState(block).getBlock().getDefaultState();
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

    public static Scaffold.BlockData getBlockData(int offsetX, int offsetY, int offsetZ) {
        final BlockPos belowBlockPos = new BlockPos(mc.player.getBlockX() + offsetX, Client.INSTANCE.getModuleManager().getModule(Scaffold.class).scaffYCoord + offsetY, mc.player.getBlockZ() + offsetZ);

        if (mc.world.getBlockState(belowBlockPos).getBlock() instanceof AirBlock) {
            for (int x = 0; x < 4; x++) {
                for (int z = 0; z < 4; z++) {
                    for (int i = 1; i > -3; i -= 2) {
                        final BlockPos blockPos = belowBlockPos.add(x * i, 0, z * i);
                        if (mc.world.getBlockState(blockPos).getBlock() instanceof AirBlock) {
                            for (Direction direction : Direction.values()) {
                                final BlockPos block = blockPos.offset(direction);
                                final BlockState material = mc.world.getBlockState(block).getBlock().getDefaultState();
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

    public static Vec3d getNewVector(Scaffold.BlockData lastblockdata) {
        if (lastblockdata == null) {
            return null;
        }
        BlockPos pos = lastblockdata.getPosition();
        Direction facing = lastblockdata.getFacing();
        Vec3d vec3 = new Vec3d(pos.getX(), pos.getY(), pos.getZ());

        double amount1 = 0.45 + Math.random() * 0.1;
        double amount2 = 0.45 + Math.random() * 0.1;

        if (facing == Direction.UP) {
            vec3 = vec3.add(amount1, 1, amount2);
        } else if (facing == Direction.DOWN) {
            vec3 = vec3.add(amount1, 0, amount2);
        } else if (facing == Direction.EAST) {
            vec3 = vec3.add(1, amount1, amount2);
        } else if (facing == Direction.WEST) {
            vec3 = vec3.add(0, amount1, amount2);
        } else if (facing == Direction.NORTH) {
            vec3 = vec3.add(amount1, amount2, 0);
        } else if (facing == Direction.SOUTH) {
            vec3 = vec3.add(amount1, amount2, 1);
        }

        return vec3;
    }
}
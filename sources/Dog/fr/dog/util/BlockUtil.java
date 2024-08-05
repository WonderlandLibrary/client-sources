package fr.dog.util;

import lombok.experimental.UtilityClass;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

import java.util.Arrays;
import java.util.List;


@UtilityClass
public class BlockUtil {
    public static final List<Block> blockBlacklist = Arrays.asList(Blocks.air, Blocks.water, Blocks.tnt, Blocks.chest,
            Blocks.flowing_water, Blocks.lava, Blocks.flowing_lava, Blocks.tnt, Blocks.enchanting_table, Blocks.carpet,
            Blocks.glass_pane, Blocks.stained_glass_pane, Blocks.iron_bars, Blocks.snow_layer, Blocks.ice,
            Blocks.packed_ice, Blocks.coal_ore, Blocks.diamond_ore, Blocks.emerald_ore, Blocks.chest, Blocks.torch,
            Blocks.anvil, Blocks.trapped_chest, Blocks.noteblock, Blocks.jukebox, Blocks.tnt, Blocks.gold_ore,
            Blocks.iron_ore, Blocks.lapis_ore, Blocks.sand, Blocks.lit_redstone_ore, Blocks.quartz_ore,
            Blocks.redstone_ore, Blocks.wooden_pressure_plate, Blocks.stone_pressure_plate,
            Blocks.light_weighted_pressure_plate, Blocks.heavy_weighted_pressure_plate, Blocks.stone_button,
            Blocks.wooden_button, Blocks.lever, Blocks.enchanting_table, Blocks.red_flower, Blocks.double_plant,
            Blocks.yellow_flower, Blocks.web, Blocks.tripwire_hook);
    Minecraft mc = Minecraft.getMinecraft();

    public PosFace getBlockFacing(BlockPos pos) {
        Minecraft mc = Minecraft.getMinecraft();
        BlockPos currentPos;
        EnumFacing currentFacing;


        if (mc.theWorld.getBlockState(pos.add(0, -1, 0)).getBlock() != Blocks.air) {
            currentPos = pos.add(0, -1, 0);
            currentFacing = EnumFacing.UP;
        } else if (mc.theWorld.getBlockState(pos.add(-1, 0, 0)).getBlock() != Blocks.air) {
            currentPos = pos.add(-1, 0, 0);
            currentFacing = EnumFacing.EAST;
        } else if (mc.theWorld.getBlockState(pos.add(1, 0, 0)).getBlock() != Blocks.air) {
            currentPos = pos.add(1, 0, 0);
            currentFacing = EnumFacing.WEST;
        } else if (mc.theWorld.getBlockState(pos.add(0, 0, -1)).getBlock() != Blocks.air) {
            currentPos = pos.add(0, 0, -1);
            currentFacing = EnumFacing.SOUTH;
        } else if (mc.theWorld.getBlockState(pos.add(0, 0, 1)).getBlock() != Blocks.air) {
            currentPos = pos.add(0, 0, 1);
            currentFacing = EnumFacing.NORTH;
        } else if (mc.theWorld.getBlockState(pos.add(-1, 0, -1)).getBlock() != Blocks.air) {
            currentFacing = EnumFacing.EAST;
            currentPos = pos.add(-1, 0, -1);
        } else if (mc.theWorld.getBlockState(pos.add(1, 0, 1)).getBlock() != Blocks.air) {
            currentFacing = EnumFacing.WEST;
            currentPos = pos.add(1, 0, 1);
        } else if (mc.theWorld.getBlockState(pos.add(1, 0, -1)).getBlock() != Blocks.air) {
            currentFacing = EnumFacing.SOUTH;
            currentPos = pos.add(1, 0, -1);
        } else if (mc.theWorld.getBlockState(pos.add(-1, 0, 1)).getBlock() != Blocks.air) {
            currentFacing = EnumFacing.NORTH;
            currentPos = pos.add(-1, 0, 1);
        } else if (mc.theWorld.getBlockState(pos.add(0, -1, 1)).getBlock() != Blocks.air) {
            currentPos = pos.add(0, -1, 1);
            currentFacing = EnumFacing.UP;
        } else if (mc.theWorld.getBlockState(pos.add(0, -1, -1)).getBlock() != Blocks.air) {
            currentPos = pos.add(0, -1, -1);
            currentFacing = EnumFacing.UP;
        } else if (mc.theWorld.getBlockState(pos.add(1, -1, 0)).getBlock() != Blocks.air) {
            currentPos = pos.add(1, -1, 0);
            currentFacing = EnumFacing.UP;
        } else if (mc.theWorld.getBlockState(pos.add(-1, -1, 0)).getBlock() != Blocks.air) {
            currentPos = pos.add(-1, -1, 0);
            currentFacing = EnumFacing.UP;
        } else if (mc.theWorld.getBlockState(pos.add(0, -1, 0)).getBlock() != Blocks.air) {
            currentPos = pos.add(0, -1, 0);
            currentFacing = EnumFacing.UP;
        } else if (mc.theWorld.getBlockState(pos.add(-1, 0, 0)).getBlock() != Blocks.air) {
            currentPos = pos.add(-1, 0, 0);
            currentFacing = EnumFacing.EAST;
        } else if (mc.theWorld.getBlockState(pos.add(1, 0, 0)).getBlock() != Blocks.air) {
            currentPos = pos.add(1, 0, 0);
            currentFacing = EnumFacing.WEST;
        } else if (mc.theWorld.getBlockState(pos.add(0, 0, -1)).getBlock() != Blocks.air) {
            currentPos = pos.add(0, 0, -1);
            currentFacing = EnumFacing.SOUTH;
        } else if (mc.theWorld.getBlockState(pos.add(0, 0, 1)).getBlock() != Blocks.air) {
            currentPos = pos.add(0, 0, 1);
            currentFacing = EnumFacing.NORTH;
        } else {
            currentPos = null;
            currentFacing = null;
        }


        return new PosFace(currentPos, currentFacing);
    }

    public float[] getRotationToBlockDirect(PosFace pf) {
        Minecraft mc = Minecraft.getMinecraft();
        double offx = 0, offy = 0, offz = 0;


        switch (pf.facing) {
            case NORTH:
                offz = -0.5f;
                break;
            case SOUTH:
                offz = 0.5f;
                break;
            case EAST:
                offx = 0.5f;
                break;
            case WEST:
                offx = -0.5f;
                break;
            case UP:
                offy = 0.5f;
                break;
            case DOWN:
                offy = -0.5f;
                break;
        }


        double deltaX = pf.getPos().getX() + 0.5 + offx - mc.thePlayer.posX,
                deltaY = pf.getPos().getY() + 0.5 + offy - (mc.thePlayer.posY + mc.thePlayer.getEyeHeight()),
                deltaZ = pf.getPos().getZ() + 0.5 + offz - mc.thePlayer.posZ,
                distance = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaZ, 2));

        float yaw = (float) Math.toDegrees(-Math.atan(deltaX / deltaZ)),
                pitch = (float) -Math.toDegrees(Math.atan(deltaY / distance));

        double v = Math.toDegrees(Math.atan(deltaZ / deltaX));
        if (deltaX < 0 && deltaZ < 0) {
            yaw = (float) (90 + v);
        } else if (deltaX > 0 && deltaZ < 0) {
            yaw = (float) (-90 + v);
        }
        return new float[]{yaw, pitch};
    }

    public float[] getRotationToBlockDirectWithBestPos(PosFace pf) {
        Minecraft mc = Minecraft.getMinecraft();
        double offx = 0, offy = 0, offz = 0;


        switch (pf.facing) {
            case NORTH:
                offz = -0.5f;
                break;
            case SOUTH:
                offz = 0.5f;
                break;
            case EAST:
                offx = 0.5f;
                break;
            case WEST:
                offx = -0.5f;
                break;
            case UP:
                offy = 0.5f;
                break;
            case DOWN:
                offy = -0.5f;
                break;
        }


        double deltaX = pf.getPos().getX() + 0.5 + offx - mc.thePlayer.posX,
                deltaY = pf.getPos().getY() + 0.5 + offy - (mc.thePlayer.posY + mc.thePlayer.getEyeHeight()),
                deltaZ = pf.getPos().getZ() + 0.5 + offz - mc.thePlayer.posZ,
                distance = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaZ, 2));


        float yaw = (float) Math.toDegrees(-Math.atan(deltaX / deltaZ)),
                pitch = (float) -Math.toDegrees(Math.atan(deltaY / distance));

        double v = Math.toDegrees(Math.atan(deltaZ / deltaX));
        if (deltaX < 0 && deltaZ < 0) {
            yaw = (float) (90 + v);
        } else if (deltaX > 0 && deltaZ < 0) {
            yaw = (float) (-90 + v);
        }
        return new float[]{yaw, pitch};
    }



    public static CompleteRotationData getRTS(PosFace face){
        float[] rtbb = getRotationToBlockDirectWithBestPos(face);
        if(rtbb != null){
            MovingObjectPosition position = getMovingObject(rtbb[0], rtbb[1], face.getPos(), face.getFacing());
            if (position != null) {
                return new CompleteRotationData(position, new float[]{rtbb[0], rtbb[1], 1});
            }
        }
        return null;
    }

    public float[] getPitchFromYaw(PosFace posFace, float yaw) {
        for (float p = 23; p < 90; p += 1) {
            if (isLookingAtBlock(yaw, p, posFace.getPos(), posFace.getFacing())) {
                return new float[]{yaw, p, 1};
            }
        }

        return null;
    }

    public CompleteRotationData getRotations(PosFace posFace) {
        Minecraft mc = Minecraft.getMinecraft();
        for (int y = (int) ((int) mc.thePlayer.rotationYaw -180 + (mc.thePlayer.movementInput.moveStrafe * -45)); y < mc.thePlayer.rotationYaw + 180 + (mc.thePlayer.movementInput.moveStrafe * -45); y += 45) {
            for (float p = 23; p < 90; p += 1) {
                MovingObjectPosition position = getMovingObject(y, p, posFace.getPos(), posFace.getFacing());
                if (position != null) {
                    return new CompleteRotationData(position, new float[]{y, p, 1});
                }
            }
        }
        return null;
    }
    public CompleteRotationData getRotationsDiag(PosFace posFace) {
        Minecraft mc = Minecraft.getMinecraft();
        for (int y = (int) ((int) mc.thePlayer.rotationYaw -180  + (mc.thePlayer.movementInput.moveStrafe * -45)); y < mc.thePlayer.rotationYaw + 180 + (mc.thePlayer.movementInput.moveStrafe * -45); y += 45) {
            for (float p = 23; p < 90; p += 1) {
                MovingObjectPosition position = getMovingObject(y, p, posFace.getPos(), posFace.getFacing());
                if (position != null) {
                    return new CompleteRotationData(position, new float[]{y, p, 1});
                }
            }
        }
        return null;
    }

    public CompleteRotationData getRotationsFromYaw(PosFace posFace, float yaw) {
        for (float p = 23; p < 90; p += 1) {
            MovingObjectPosition position = getMovingObject(yaw, p, posFace.getPos(), posFace.getFacing());
            if (position != null) {
                return new CompleteRotationData(position, new float[]{yaw, p, 1});
            }
        }

        return null;
    }

    public boolean isLookingAtBlock(float yaw, float pitch, BlockPos pos, EnumFacing facing) {
        MovingObjectPosition m = Minecraft.getMinecraft().thePlayer.rayTraceCustom(6, yaw, pitch);
        if (m == null) {
            return false;
        }

        final Vec3 hitVec = m.hitVec;
        if (hitVec == null) {
            return false;
        }
        return m.getBlockPos().equals(pos) && m.sideHit == facing;

    }

    public MovingObjectPosition getMovingObject(float yaw, float pitch, BlockPos pos, EnumFacing facing) {
        MovingObjectPosition m = Minecraft.getMinecraft().thePlayer.rayTraceCustom(6, yaw, pitch);
        if (m == null) {
            return null;
        }

        final Vec3 hitVec = m.hitVec;
        if (hitVec == null) {
            return null;
        }
        if (!(m.getBlockPos().equals(pos) && m.sideHit == facing)) {
            return null;
        }

        return m;
    }

    public BlockPos getNearestPlaceble(int distance) {

        float dist = Integer.MAX_VALUE;
        BlockPos pos = null;
        for (int x = -distance; x < distance; x++) {
            for (int z = -distance; z < distance; z++) {
                for (int y = -distance; y <= -1; y++) {
                    BlockPos prevPos = new BlockPos(mc.thePlayer.posX + x, mc.thePlayer.posY + y, mc.thePlayer.posZ + z);
                    double dst = mc.thePlayer.getDistance(prevPos.getX() + 0.5, prevPos.getY(), prevPos.getZ() + 0.5);
                    if (haveBlockNextToIt(prevPos) && dst < dist) {
                        pos = prevPos;
                        dist = (float) dst;
                    }
                }
            }
        }
        if (pos == null) {
            return new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1, mc.thePlayer.posZ);
        }
        return pos;
    }

    public BlockPos getNearestPlaceble(int distance, int y) {

        float dist = Integer.MAX_VALUE;
        BlockPos pos = null;
        for (int x = -distance; x < distance; x++) {
            for (int z = -distance; z < distance; z++) {
                BlockPos prevPos = new BlockPos(mc.thePlayer.posX + x, y, mc.thePlayer.posZ + z);
                double dst = mc.thePlayer.getDistance(prevPos.getX() + 0.5, prevPos.getY(), prevPos.getZ() + 0.5);
                if (haveBlockNextToIt(prevPos) && dst < dist) {
                    pos = prevPos;
                    dist = (float) dst;
                }

            }
        }
        if (pos == null) {
            return new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1, mc.thePlayer.posZ);
        }
        return pos;
    }

    public boolean haveBlockNextToIt(BlockPos pos) {
        if (!(mc.theWorld.getBlockState(pos.add(1, 0, 0)).getBlock() instanceof BlockAir)) {
            return true;
        }
        if (!(mc.theWorld.getBlockState(pos.add(-1, 0, 0)).getBlock() instanceof BlockAir)) {
            return true;
        }
        if (!(mc.theWorld.getBlockState(pos.add(0, 0, 1)).getBlock() instanceof BlockAir)) {
            return true;
        }
        if (!(mc.theWorld.getBlockState(pos.add(0, 0, -1)).getBlock() instanceof BlockAir)) {
            return true;
        }
        return !(mc.theWorld.getBlockState(pos.add(0, -1, 0)).getBlock() instanceof BlockAir);

    }

    public static float getBlockHardness(final Block block, final ItemStack itemStack, final boolean b) {
        final float getBlockHardness = block.getBlockHardness(mc.theWorld, null);
        if (getBlockHardness < 0.0f) {
            return 0.0f;
        }
        return (block.getMaterial().isToolNotRequired() || (itemStack != null && itemStack.canHarvestBlock(block))) ? (getToolDigEfficiency(itemStack, block, b) / getBlockHardness / 30.0f) : (getToolDigEfficiency(itemStack, block, b) / getBlockHardness / 100.0f);
    }

    public static float getToolDigEfficiency(final ItemStack itemStack, final Block block, final boolean b) {
        float n = (itemStack == null) ? 1.0f : itemStack.getItem().getStrVsBlock(itemStack, block);
        if (n > 1.0f) {
            final int getEnchantmentLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency.effectId, itemStack);
            if (getEnchantmentLevel > 0 && itemStack != null) {
                n += getEnchantmentLevel * getEnchantmentLevel + 1;
            }
        }
        if (mc.thePlayer.isPotionActive(Potion.digSpeed)) {
            n *= 1.0f + (mc.thePlayer.getActivePotionEffect(Potion.digSpeed).getAmplifier() + 1) * 0.2f;
        }
        if (!b) {
            if (mc.thePlayer.isPotionActive(Potion.digSlowdown)) {
                float n2;
                switch (mc.thePlayer.getActivePotionEffect(Potion.digSlowdown).getAmplifier()) {
                    case 0: {
                        n2 = 0.3f;
                        break;
                    }
                    case 1: {
                        n2 = 0.09f;
                        break;
                    }
                    case 2: {
                        n2 = 0.0027f;
                        break;
                    }
                    default: {
                        n2 = 8.1E-4f;
                        break;
                    }
                }
                n *= n2;
            }
            if (mc.thePlayer.isInsideOfMaterial(Material.water) && !EnchantmentHelper.getAquaAffinityModifier(mc.thePlayer)) {
                n /= 5.0f;
            }
        }
        return n;
    }
}

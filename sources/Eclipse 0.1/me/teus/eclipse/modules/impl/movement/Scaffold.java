package me.teus.eclipse.modules.impl.movement;

import me.teus.eclipse.events.player.EventMotionUpdate;
import me.teus.eclipse.events.player.EventPostUpdate;
import me.teus.eclipse.events.player.EventPreUpdate;
import me.teus.eclipse.modules.Category;
import me.teus.eclipse.modules.Info;
import me.teus.eclipse.modules.Module;
import me.teus.eclipse.modules.value.impl.ModeValue;
import me.teus.eclipse.utils.RotationUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import xyz.lemon.event.bus.Listener;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Info(name = "Scaffold", displayName = "Scaffold", category = Category.PLAYER)
public class Scaffold extends Module {

    private BlockPos currentPos, lastPos;
    private EnumFacing currentFacing, lastFacing;

    private float yaw, pitch;
    private float finalYaw, finalPitch;

    private int currentDelay;
    private boolean diagonal;

    public ModeValue autoblock = new ModeValue("Autoblock", "Switch", "None", "Switch", "Spoof");

    public Scaffold () {
        addValues(autoblock);
    }

    @Override
    public void onEnable() {
        for(int i = 0; i < 9; i++) {
            if (mc.thePlayer.inventory.getStackInSlot(i) == null)
                continue;
            if (mc.thePlayer.inventory.getStackInSlot(i).getItem() instanceof ItemBlock && !niggaBlocks.contains(((ItemBlock) mc.thePlayer.inventory.getStackInSlot(i).getItem()).getBlock())) {
                if(autoblock.is("Switch")) {
                    oldItem = mc.thePlayer.inventory.currentItem;
                    mc.thePlayer.inventory.currentItem = i;
                    itemSpoofed = i;
                    break;
                }
                if(autoblock.is("Spoof")) {
                    oldItem = mc.thePlayer.inventory.currentItem;
                    itemSpoofed = i;
                    break;
                }
            }
        }
    }

    public Listener<EventMotionUpdate> eventMotionUpdateListener = e ->{

        BlockPos pos = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1, mc.thePlayer.posZ);
        if(mc.gameSettings.keyBindJump.isKeyDown()) {
            pos = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1.1, mc.thePlayer.posZ);
        }
        if(mc.theWorld.getBlockState(pos).getBlock() instanceof BlockAir) {
            faceBlock(pos);

            if(currentFacing != null) {
                lastPos = currentPos;
                lastFacing = currentFacing;
            }
        }

        if(lastPos != null && lastFacing != null) {

            e.setPitch(90);
            mc.thePlayer.rotationPitchHead = 90;
        }
    };

    public Listener<EventPostUpdate> eventPostUpdateListener = event -> {
        if(currentPos != null && currentFacing != null) {
            placeBlock(0);
        }
    };

    public Listener<EventPreUpdate> eventPreUpdateListener = event -> {
        getRightBlock();
    };

    public void faceBlock(BlockPos pos) {
        if(diagonal) {
            if (mc.theWorld.getBlockState(pos.add(0, -1, 0)).getBlock() != Blocks.air) {
                this.currentPos = pos.add(0, -1, 0);
                currentFacing = EnumFacing.UP;
                if(yaw != 0) {
                    currentDelay = 0;
                }
                yaw = 0;
            } else if (mc.theWorld.getBlockState(pos.add(-1, 0, 0)).getBlock() != Blocks.air) {
                this.currentPos = pos.add(-1, 0, 0);
                currentFacing = EnumFacing.EAST;
                if(yaw != 90) {
                    currentDelay = 0;
                }
                yaw = 90;
            } else if (mc.theWorld.getBlockState(pos.add(1, 0, 0)).getBlock() != Blocks.air) {
                this.currentPos = pos.add(1, 0, 0);
                currentFacing = EnumFacing.WEST;
                if(yaw != -90) {
                    currentDelay = 0;
                }
                yaw = -90;
            } else if (mc.theWorld.getBlockState(pos.add(0, 0, -1)).getBlock() != Blocks.air) {
                this.currentPos = pos.add(0, 0, -1);
                currentFacing = EnumFacing.SOUTH;
                if(yaw != 180) {
                    currentDelay = 0;
                }
                yaw = 180;
            } else if (mc.theWorld.getBlockState(pos.add(0, 0, 1)).getBlock() != Blocks.air) {
                this.currentPos = pos.add(0, 0, 1);
                currentFacing = EnumFacing.NORTH;
                if(yaw != 0) {
                    currentDelay = 0;
                }
                yaw = 0;
            }
            else if (mc.theWorld.getBlockState(pos.add(-1, 0, -1)).getBlock() != Blocks.air) {
                currentFacing = EnumFacing.EAST;
                this.currentPos = pos.add(-1, 0, -1);
                if(yaw != 135) {
                    currentDelay = 0;
                }
                yaw = 135;
            } else if (mc.theWorld.getBlockState(pos.add(1, 0, 1)).getBlock() != Blocks.air) {
                currentFacing = EnumFacing.WEST;
                this.currentPos = pos.add(1, 0, 1);
                if(yaw != -45) {
                    currentDelay = 0;
                }
                yaw = -45;
            } else if (mc.theWorld.getBlockState(pos.add(1, 0, -1)).getBlock() != Blocks.air) {
                currentFacing = EnumFacing.SOUTH;
                this.currentPos = pos.add(1, 0, -1);
                if(yaw != 135) {
                    currentDelay = 0;
                }
                yaw = -135;
            } else if (mc.theWorld.getBlockState(pos.add(-1, 0, 1)).getBlock() != Blocks.air) {
                currentFacing = EnumFacing.NORTH;
                this.currentPos = pos.add(-1, 0, 1);
                if(yaw != 45) {
                    currentDelay = 0;
                }
                yaw = 45;
            }
            else if (mc.theWorld.getBlockState(pos.add(0, -1, 1)).getBlock() != Blocks.air) {
                this.currentPos = pos.add(0, -1, 1);
                currentFacing = EnumFacing.UP;
                yaw = mc.thePlayer.rotationYaw;
            }
            else if (mc.theWorld.getBlockState(pos.add(0, -1, -1)).getBlock() != Blocks.air) {
                this.currentPos = pos.add(0, -1, -1);
                currentFacing = EnumFacing.UP;
                yaw = mc.thePlayer.rotationYaw;
            }
            else if (mc.theWorld.getBlockState(pos.add(1, -1, 0)).getBlock() != Blocks.air) {
                this.currentPos = pos.add(1, -1, 0);
                currentFacing = EnumFacing.UP;
                yaw = mc.thePlayer.rotationYaw;
            }
            else if (mc.theWorld.getBlockState(pos.add(-1, -1, 0)).getBlock() != Blocks.air) {
                this.currentPos = pos.add(-1, -1, 0);
                currentFacing = EnumFacing.UP;
                yaw = mc.thePlayer.rotationYaw;
            }
            else {
                currentPos = null;
                currentFacing = null;
            }
        } else {
            if (mc.theWorld.getBlockState(pos.add(0, -1, 0)).getBlock() != Blocks.air) {
                this.currentPos = pos.add(0, -1, 0);
                currentFacing = EnumFacing.UP;
            } else if (mc.theWorld.getBlockState(pos.add(-1, 0, 0)).getBlock() != Blocks.air) {
                this.currentPos = pos.add(-1, 0, 0);
                currentFacing = EnumFacing.EAST;
            } else if (mc.theWorld.getBlockState(pos.add(1, 0, 0)).getBlock() != Blocks.air) {
                this.currentPos = pos.add(1, 0, 0);
                currentFacing = EnumFacing.WEST;
            } else if (mc.theWorld.getBlockState(pos.add(0, 0, -1)).getBlock() != Blocks.air) {
                this.currentPos = pos.add(0, 0, -1);
                currentFacing = EnumFacing.SOUTH;
            } else if (mc.theWorld.getBlockState(pos.add(0, 0, 1)).getBlock() != Blocks.air) {
                this.currentPos = pos.add(0, 0, 1);
                currentFacing = EnumFacing.NORTH;
            } else {
                currentPos = null;
                currentFacing = null;
            }
        }
    }

    public int oldItem, itemSpoofed;
    private long placedBlocks;

    private boolean placeBlock(double negativeExpand) {
        if(autoblock.is("Spoof") ? mc.thePlayer.inventory.getStackInSlot(itemSpoofed) != null && mc.thePlayer.inventory.getStackInSlot(itemSpoofed).getItem() instanceof ItemBlock && negative(negativeExpand) : mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() instanceof ItemBlock && negative(negativeExpand)) {
            if(mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, autoblock.is("None") ? mc.thePlayer.getCurrentEquippedItem() : mc.thePlayer.inventory.getStackInSlot(itemSpoofed), currentPos, currentFacing, vec3(currentPos, currentFacing))) {
                mc.getNetHandler().getNetworkManager().sendPacket(new C0APacketAnimation());
                placedBlocks++;
                return true;
            }
        }
        return false;
    }

    public static Vec3 vec3(BlockPos pos, EnumFacing facing) {
        double x = (double) pos.getX();
        double y = (double) pos.getY();
        double z = (double) pos.getZ();
        double random1 = ThreadLocalRandom.current().nextDouble(0.6D, 1.0D);
        double random2 = ThreadLocalRandom.current().nextDouble(0.9D, 1.0D);
        if(facing.equals(EnumFacing.UP)) {
            x += random1;
            z += random1;
            ++y;
        } else if(facing.equals(EnumFacing.DOWN)) {
            x += random1;
            z += random1;
        } else if(facing.equals(EnumFacing.WEST)) {
            y += random2;
            z += random1;
        } else if(facing.equals(EnumFacing.EAST)) {
            y += random2;
            z += random1;
            ++x;
        } else if(facing.equals(EnumFacing.SOUTH)) {
            y += random2;
            x += random1;
            ++z;
        } else if(facing.equals(EnumFacing.NORTH)) {
            y += random2;
            x += random1;
        }

        return new Vec3(x, y, z);
    }

    private boolean negative(double negativeExpandValue) {
        if(mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX + negativeExpandValue, mc.thePlayer.posY - 1.0D, mc.thePlayer.posZ + negativeExpandValue)).getBlock() instanceof BlockAir && mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX - negativeExpandValue, mc.thePlayer.posY - 1.0D, mc.thePlayer.posZ - negativeExpandValue)).getBlock() instanceof BlockAir && mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX - negativeExpandValue, mc.thePlayer.posY - 1.0D, mc.thePlayer.posZ)).getBlock() instanceof BlockAir && mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX + negativeExpandValue, mc.thePlayer.posY - 1.0D, mc.thePlayer.posZ)).getBlock() instanceof BlockAir && mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1.0D, mc.thePlayer.posZ + negativeExpandValue)).getBlock() instanceof BlockAir && mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1.0D, mc.thePlayer.posZ - negativeExpandValue)).getBlock() instanceof BlockAir) {
            return true;
        } else {
            return false;
        }
    }

    public List<Block> niggaBlocks = Arrays.asList(Blocks.air, Blocks.water, Blocks.tnt, Blocks.chest,
            Blocks.flowing_water, Blocks.lava, Blocks.flowing_lava, Blocks.tnt, Blocks.enchanting_table, Blocks.carpet,
            Blocks.glass_pane, Blocks.stained_glass_pane, Blocks.iron_bars, Blocks.snow_layer, Blocks.ice,
            Blocks.packed_ice, Blocks.coal_ore, Blocks.diamond_ore, Blocks.emerald_ore, Blocks.chest, Blocks.torch,
            Blocks.anvil, Blocks.trapped_chest, Blocks.noteblock, Blocks.jukebox, Blocks.tnt, Blocks.gold_ore,
            Blocks.iron_ore, Blocks.lapis_ore, Blocks.sand, Blocks.lit_redstone_ore, Blocks.quartz_ore,
            Blocks.redstone_ore, Blocks.wooden_pressure_plate, Blocks.stone_pressure_plate,
            Blocks.light_weighted_pressure_plate, Blocks.heavy_weighted_pressure_plate, Blocks.stone_button,
            Blocks.wooden_button, Blocks.lever, Blocks.enchanting_table, Blocks.red_flower, Blocks.double_plant,
            Blocks.yellow_flower, Blocks.web);

    private void getRightBlock() {
        if(autoblock.is("Spoof") && mc.thePlayer.inventory.getStackInSlot(itemSpoofed) == null) {
            for(int i = 0; i < 9; i++) {
                if (mc.thePlayer.inventory.getStackInSlot(i) == null)
                    continue;
                List<Block> blockBlacklist = Arrays.asList(Blocks.air, Blocks.water, Blocks.tnt, Blocks.chest,
                        Blocks.flowing_water, Blocks.lava, Blocks.flowing_lava, Blocks.tnt, Blocks.enchanting_table, Blocks.carpet,
                        Blocks.glass_pane, Blocks.stained_glass_pane, Blocks.iron_bars, Blocks.snow_layer, Blocks.ice,
                        Blocks.packed_ice, Blocks.coal_ore, Blocks.diamond_ore, Blocks.emerald_ore, Blocks.chest, Blocks.torch,
                        Blocks.anvil, Blocks.trapped_chest, Blocks.noteblock, Blocks.jukebox, Blocks.tnt, Blocks.gold_ore,
                        Blocks.iron_ore, Blocks.lapis_ore, Blocks.sand, Blocks.lit_redstone_ore, Blocks.quartz_ore,
                        Blocks.redstone_ore, Blocks.wooden_pressure_plate, Blocks.stone_pressure_plate,
                        Blocks.light_weighted_pressure_plate, Blocks.heavy_weighted_pressure_plate, Blocks.stone_button,
                        Blocks.wooden_button, Blocks.lever, Blocks.enchanting_table, Blocks.red_flower, Blocks.double_plant,
                        Blocks.yellow_flower);
                if (mc.thePlayer.inventory.getStackInSlot(i).getItem() instanceof ItemBlock && !blockBlacklist.contains(((ItemBlock) mc.thePlayer.inventory.getStackInSlot(i).getItem()).getBlock())) {
                    oldItem = mc.thePlayer.inventory.currentItem;
                    itemSpoofed = i;
                    break;
                }
            }
            mc.getNetHandler().getNetworkManager().sendPacketNoEvent(new C09PacketHeldItemChange(itemSpoofed));
        }

        if(autoblock.is("Switch")) {
            for(int i = 0; i < 9; i++) {
                if (mc.thePlayer.inventory.getStackInSlot(i) == null)
                    continue;
                if (mc.thePlayer.inventory.getStackInSlot(i).getItem() instanceof ItemBlock && !niggaBlocks.contains(((ItemBlock) mc.thePlayer.inventory.getStackInSlot(i).getItem()).getBlock())) {
                    mc.thePlayer.inventory.currentItem = i;
                    itemSpoofed = i;
                }
            }
        }
    }

}

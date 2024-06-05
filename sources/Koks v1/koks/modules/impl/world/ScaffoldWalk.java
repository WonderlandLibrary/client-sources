package koks.modules.impl.world;

import koks.Koks;
import koks.event.Event;
import koks.event.impl.EventMove;
import koks.event.impl.EventUpdate;
import koks.event.impl.MotionEvent;
import koks.event.impl.SafeWalkEvent;
import koks.modules.Module;
import koks.utilities.RandomUtil;
import koks.utilities.RayCastUtil;
import koks.utilities.RotationUtil;
import koks.utilities.TimeUtil;
import koks.utilities.value.values.BooleanValue;
import koks.utilities.value.values.NumberValue;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.*;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author avox | lmao | kroko
 * @created on 04.09.2020 : 10:23
 */
public class ScaffoldWalk extends Module {

    private final List blackList;

    public BlockPos finalPos;
    public boolean shouldBuildDown;

    private final RotationUtil rotationUtil = new RotationUtil();
    private final TimeUtil timeUtil = new TimeUtil();
    private final TimeUtil timeUtil2 = new TimeUtil();
    private final RandomUtil randomutil = new RandomUtil();
    private final RayCastUtil rayCastUtil = new RayCastUtil();

    private final NumberValue<Long> delay = new NumberValue<Long>("Delay", 1L, 50L, 100L, 0L, this);
    private final NumberValue<Float> Motion = new NumberValue<Float>("Motion", 1F, 1F, 0F, this);

    private final NumberValue<Float> pitchVal = new NumberValue<Float>("Pitch", 82F, 90F, 70F, this);

    private final BooleanValue<Boolean> sneak = new BooleanValue<>("Sneak", false, this);
    private final NumberValue<Integer> sneakAfterBlocks = new NumberValue<>("Sneak After...", 10, 20, 0, this);

    private final BooleanValue<Boolean> swingItem = new BooleanValue<>("Swing Item", true, this);
    private final BooleanValue<Boolean> safeWalk = new BooleanValue<>("SafeWalk", true, this);
    private final BooleanValue<Boolean> randomHit = new BooleanValue<>("Random Hit", true, this);
    public final BooleanValue<Boolean> sprint = new BooleanValue<>("Sprint", true, this);

    public final BooleanValue<Boolean> rayCast = new BooleanValue<>("RayCast", true, this);

    public final BooleanValue<Boolean> simpleRotations = new BooleanValue<>("Simple Rotations", false, this);
    public final BooleanValue<Boolean> Hypixel = new BooleanValue<>("Hypixel", false, this);
    public final BooleanValue<Boolean> Intave = new BooleanValue<>("Intave", true, this);

    public final BooleanValue<Boolean> AlwaysLook = new BooleanValue<>("AlwaysLook", true, this);

    public float pitch, yaw;
    public int sneakCount;

    public ScaffoldWalk() {
        super("ScaffoldWalk", "Its build automaticaly for you", Category.WORLD);
        this.blackList = Arrays.asList(Blocks.crafting_table, Blocks.chest, Blocks.enchanting_table, Blocks.anvil, Blocks.sand, Blocks.gravel, Blocks.glass_pane, Blocks.stained_glass_pane, Blocks.ice, Blocks.packed_ice, Blocks.cobblestone_wall, Blocks.water, Blocks.lava, Blocks.web, Blocks.sapling, Blocks.rail, Blocks.golden_rail, Blocks.activator_rail, Blocks.detector_rail, Blocks.tnt, Blocks.red_flower, Blocks.yellow_flower, Blocks.flower_pot, Blocks.tallgrass, Blocks.red_mushroom, Blocks.brown_mushroom, Blocks.ladder, Blocks.torch, Blocks.stone_button, Blocks.wooden_button, Blocks.redstone_torch, Blocks.redstone_wire, Blocks.furnace, Blocks.cactus, Blocks.oak_fence, Blocks.acacia_fence, Blocks.nether_brick_fence, Blocks.birch_fence, Blocks.dark_oak_fence, Blocks.jungle_fence, Blocks.oak_fence, Blocks.acacia_fence_gate, Blocks.snow_layer, Blocks.trapdoor, Blocks.ender_chest, Blocks.beacon, Blocks.hopper, Blocks.daylight_detector, Blocks.daylight_detector_inverted, Blocks.carpet);
        addValue(delay);
        addValue(Motion);
        addValue(pitchVal);
        addValue(sneak);
        addValue(sneakAfterBlocks);
        addValue(swingItem);
        addValue(safeWalk);
        addValue(randomHit);
        addValue(sprint);
        addValue(rayCast);
        addValue(simpleRotations);
        addValue(Hypixel);
        addValue(Intave);
        addValue(AlwaysLook);
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof MotionEvent) {
            if (((MotionEvent) event).getType() == MotionEvent.Type.PRE) {

                ((MotionEvent) event).setYaw(yaw);

                if (Hypixel.isToggled() && Minecraft.getMinecraft().theWorld != null && Minecraft.getMinecraft().thePlayer != null)
                    pitch = 79.444F;
                ((MotionEvent) event).setPitch(pitch);

            }
        }


        if (event instanceof EventUpdate) {
            if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
                shouldBuildDown = true;
                mc.gameSettings.keyBindSneak.pressed = false;
            } else {
                shouldBuildDown = false;
            }
            BlockPos pos = new BlockPos(mc.thePlayer.posX, (mc.thePlayer.getEntityBoundingBox()).minY - 1.0D - (shouldBuildDown ? 1 : 0), mc.thePlayer.posZ);

            getBlockPosToPlaceOn(pos);

            pitch = getPitch(360);

            if (simpleRotations.isToggled()) {
                setYawSimple();
            } else {
                setYaw();
            }

        }
        if (event instanceof SafeWalkEvent) {
            if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
                shouldBuildDown = true;
                mc.gameSettings.keyBindSneak.pressed = false;
            } else {
                shouldBuildDown = false;
            }
            if (safeWalk.isToggled()) {
                ((SafeWalkEvent) event).setSafe(true);
            } else {

            }
        }

    }


    public void setYaw() {
        float[] rotations = rotationUtil.faceBlock(finalPos, true, yaw, pitch, 360);
        yaw = rotations[0];
    }


    public void setYawSimple() {
        boolean forward = mc.gameSettings.keyBindForward.isKeyDown();
        boolean left = mc.gameSettings.keyBindLeft.isKeyDown();
        boolean right = mc.gameSettings.keyBindRight.isKeyDown();
        boolean back = mc.gameSettings.keyBindBack.isKeyDown();

        float yaw = 0;

        // Only one Key directions
        if (forward && !left && !right && !back)
            yaw = 180;
        if (!forward && left && !right && !back)
            yaw = 90;
        if (!forward && !left && right && !back)
            yaw = -90;
        if (!forward && !left && !right && back)
            yaw = 0;

        // Multi Key directions
        if (forward && left && !right && !back)
            yaw = 135;
        if (forward && !left && right && !back)
            yaw = -135;

        if (!forward && left && !right && back)
            yaw = 45;
        if (!forward && !left && right && back)
            yaw = -45;

        this.yaw = mc.thePlayer.rotationYaw + yaw;
    }

    public float getPitch(int speed) {
        if (mc.thePlayer.onGround) {
            return pitchVal.getDefaultValue();
        } else {
            return rotationUtil.faceBlock(finalPos, true, yaw, pitch, speed)[1];
        }
    }

    public void placeBlock(BlockPos pos, EnumFacing face) {
        finalPos = pos;
        ItemStack silentItemStack = null;
        if (mc.thePlayer.getCurrentEquippedItem() == null || (!(mc.thePlayer.getCurrentEquippedItem().getItem() instanceof net.minecraft.item.ItemBlock))) {
            for (int i = 0; i < 9; i++) {
                if (mc.thePlayer.inventory.getStackInSlot(i) != null && mc.thePlayer.inventory.getStackInSlot(i).getItem() instanceof net.minecraft.item.ItemBlock) {
                    ItemBlock itemBlock = (ItemBlock) mc.thePlayer.inventory.getStackInSlot(i).getItem();
                    if (this.blackList.contains(itemBlock.getBlock()))
                        continue;
                    mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(i));
                    silentItemStack = mc.thePlayer.inventory.getStackInSlot(i);
                    if (mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1.0D - (shouldBuildDown ? 1 : 0), mc.thePlayer.posZ)).getBlock() instanceof net.minecraft.block.BlockAir) {
                        if (swingItem.isToggled())
                            mc.thePlayer.swingItem();
                        else
                            mc.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
                    }
                    break;
                }
            }
        } else {
            silentItemStack = (mc.thePlayer.getCurrentEquippedItem().getItem() instanceof net.minecraft.item.ItemBlock) ? mc.thePlayer.getCurrentEquippedItem() : null;
            if (mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1.0D - (shouldBuildDown ? 1 : 0), mc.thePlayer.posZ)).getBlock() instanceof net.minecraft.block.BlockAir) {
                if (blackList.contains(((ItemBlock) silentItemStack.getItem()).getBlock()))
                    return;
                mc.thePlayer.swingItem();
            }
        }

        if (sneakCount >= sneakAfterBlocks.getDefaultValue() && sneak.isToggled())
            mc.gameSettings.keyBindSneak.pressed = true;

        if (mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1.0D - (shouldBuildDown ? 1 : 0), mc.thePlayer.posZ)).getBlock() instanceof net.minecraft.block.BlockAir) {
            if (!simpleRotations.isToggled())
                setYaw();
            boolean rayCasted = !rayCast.isToggled() || rayCastUtil.isRayCastBlock(pos, yaw, pitch);
            if (rayCasted) {
                if (timeUtil.hasReached(mc.thePlayer.onGround ? (randomutil.randomLong(delay.getMinDefaultValue(), delay.getDefaultValue())) : 20L)) {
                    if (blackList.contains(((ItemBlock) silentItemStack.getItem()).getBlock()))
                        return;

                    if (Intave.isToggled()) {
                        mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, silentItemStack, rayCastUtil.getRayCastBlock(this.yaw, this.pitch).getBlockPos(), rayCastUtil.getRayCastBlock(this.yaw, this.pitch).sideHit, rayCastUtil.getRayCastBlock(this.yaw, this.pitch).hitVec);
                        sneakCount++;
                    } else {
                        mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, silentItemStack, pos, face, new Vec3(pos.getX() + (this.randomHit.isToggled() ? randomutil.randomDouble(0, 0.7) : 0), pos.getY() + (this.randomHit.isToggled() ? randomutil.randomDouble(0, 0.7) : 0), pos.getZ() + (this.randomHit.isToggled() ? randomutil.randomDouble(0, 0.7) : 0)));
                    }
                    mc.thePlayer.motionX *= Motion.getDefaultValue();
                    mc.thePlayer.motionZ *= Motion.getDefaultValue();

                    if (sneakCount > sneakAfterBlocks.getDefaultValue())
                        sneakCount = 0;

                    timeUtil.reset();
                }
            } else {

                timeUtil.reset();
            }
        } else {
            mc.gameSettings.keyBindSneak.pressed = false;
            timeUtil.reset();
            if (!simpleRotations.isToggled())
                setYaw();
        }
        shouldBuildDown = false;
    }

    @Override
    public void onEnable() {
    }

    @Override
    public void onDisable() {
        mc.gameSettings.keyBindSneak.pressed = false;
        sneakCount = 0;
        yaw = 0;
        pitch = 0;
        mc.timer.timerSpeed = 1F;
        mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
    }

    public void getBlockPosToPlaceOn(BlockPos pos) {
        BlockPos blockPos1 = pos.add(-1, 0, 0);
        BlockPos blockPos2 = pos.add(1, 0, 0);
        BlockPos blockPos3 = pos.add(0, 0, -1);
        BlockPos blockPos4 = pos.add(0, 0, 1);
        float down = (shouldBuildDown ? 1 : 0);
        if (mc.theWorld.getBlockState(pos.add(0, -1 - down, 0)).getBlock() != Blocks.air) {
            placeBlock(pos.add(0, -1, 0), EnumFacing.UP);
        } else if (mc.theWorld.getBlockState(pos.add(-1, 0 - down, 0)).getBlock() != Blocks.air) {
            placeBlock(pos.add(-1, 0 - down, 0), EnumFacing.EAST);
        } else if (mc.theWorld.getBlockState(pos.add(1, 0 - down, 0)).getBlock() != Blocks.air) {
            placeBlock(pos.add(1, 0 - down, 0), EnumFacing.WEST);
        } else if (mc.theWorld.getBlockState(pos.add(0, 0 - down, -1)).getBlock() != Blocks.air) {
            placeBlock(pos.add(0, 0 - down, -1), EnumFacing.SOUTH);
        } else if (mc.theWorld.getBlockState(pos.add(0, 0 - down, 1)).getBlock() != Blocks.air) {
            placeBlock(pos.add(0, 0 - down, 1), EnumFacing.NORTH);
        } else if (mc.theWorld.getBlockState(blockPos1.add(0, -1 - down, 0)).getBlock() != Blocks.air) {
            placeBlock(blockPos1.add(0, -1 - down, 0), EnumFacing.UP);
        } else if (mc.theWorld.getBlockState(blockPos1.add(-1, 0 - down, 0)).getBlock() != Blocks.air) {
            placeBlock(blockPos1.add(-1, 0 - down, 0), EnumFacing.EAST);
        } else if (mc.theWorld.getBlockState(blockPos1.add(1, 0 - down, 0)).getBlock() != Blocks.air) {
            placeBlock(blockPos1.add(1, 0 - down, 0), EnumFacing.WEST);
        } else if (mc.theWorld.getBlockState(blockPos1.add(0, 0 - down, -1)).getBlock() != Blocks.air) {
            placeBlock(blockPos1.add(0, 0 - down, -1), EnumFacing.SOUTH);
        } else if (mc.theWorld.getBlockState(blockPos1.add(0, 0 - down, 1)).getBlock() != Blocks.air) {
            placeBlock(blockPos1.add(0, 0 - down, 1), EnumFacing.NORTH);
        } else if (mc.theWorld.getBlockState(blockPos2.add(0, -1 - down, 0)).getBlock() != Blocks.air) {
            placeBlock(blockPos2.add(0, -1 - down, 0), EnumFacing.UP);
        } else if (mc.theWorld.getBlockState(blockPos2.add(-1, 0 - down, 0)).getBlock() != Blocks.air) {
            placeBlock(blockPos2.add(-1, 0 - down, 0), EnumFacing.EAST);
        } else if (mc.theWorld.getBlockState(blockPos2.add(1, 0 - down, 0)).getBlock() != Blocks.air) {
            placeBlock(blockPos2.add(1, 0 - down, 0), EnumFacing.WEST);
        } else if (mc.theWorld.getBlockState(blockPos2.add(0, 0 - down, -1)).getBlock() != Blocks.air) {
            placeBlock(blockPos2.add(0, 0 - down, -1), EnumFacing.SOUTH);
        } else if (mc.theWorld.getBlockState(blockPos2.add(0, 0 - down, 1)).getBlock() != Blocks.air) {
            placeBlock(blockPos2.add(0, 0 - down, 1), EnumFacing.NORTH);
        } else if (mc.theWorld.getBlockState(blockPos3.add(0, -1 - down, 0)).getBlock() != Blocks.air) {
            placeBlock(blockPos3.add(0, -1 - down, 0), EnumFacing.UP);
        } else if (mc.theWorld.getBlockState(blockPos3.add(-1, 0 - down, 0)).getBlock() != Blocks.air) {
            placeBlock(blockPos3.add(-1, 0 - down, 0), EnumFacing.EAST);
        } else if (mc.theWorld.getBlockState(blockPos3.add(1, 0 - down, 0)).getBlock() != Blocks.air) {
            placeBlock(blockPos3.add(1, 0 - down, 0), EnumFacing.WEST);
        } else if (mc.theWorld.getBlockState(blockPos3.add(0, 0 - down, -1)).getBlock() != Blocks.air) {
            placeBlock(blockPos3.add(0, 0 - down, -1), EnumFacing.SOUTH);
        } else if (mc.theWorld.getBlockState(blockPos3.add(0, 0 - down, 1)).getBlock() != Blocks.air) {
            placeBlock(blockPos3.add(0, 0 - down, 1), EnumFacing.NORTH);
        } else if (mc.theWorld.getBlockState(blockPos4.add(0, -1 - down, 0)).getBlock() != Blocks.air) {
            placeBlock(blockPos4.add(0, -1 - down, 0), EnumFacing.UP);
        } else if (mc.theWorld.getBlockState(blockPos4.add(-1, 0 - down, 0)).getBlock() != Blocks.air) {
            placeBlock(blockPos4.add(-1, 0 - down, 0), EnumFacing.EAST);
        } else if (mc.theWorld.getBlockState(blockPos4.add(1, 0 - down, 0)).getBlock() != Blocks.air) {
            placeBlock(blockPos4.add(1, 0 - down, 0), EnumFacing.WEST);
        } else if (mc.theWorld.getBlockState(blockPos4.add(0, 0 - down, -1)).getBlock() != Blocks.air) {
            placeBlock(blockPos4.add(0, 0 - down, -1), EnumFacing.SOUTH);
        } else if (mc.theWorld.getBlockState(blockPos4.add(0, 0 - down, 1)).getBlock() != Blocks.air) {
            placeBlock(blockPos4.add(0, 0 - down, 1), EnumFacing.NORTH);
        }
    }

}

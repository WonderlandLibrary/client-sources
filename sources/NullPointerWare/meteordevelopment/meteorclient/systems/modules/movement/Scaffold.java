/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.systems.modules.movement;

import meteordevelopment.meteorclient.events.entity.player.PlayerMoveEvent;

import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.mixininterface.IVec3d;
import meteordevelopment.meteorclient.renderer.ShapeMode;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.systems.modules.Modules;
import meteordevelopment.meteorclient.systems.modules.misc.Debug;
import meteordevelopment.meteorclient.systems.modules.world.Timer;
import meteordevelopment.meteorclient.utils.other.TimerMS;
import meteordevelopment.meteorclient.utils.player.*;
import meteordevelopment.meteorclient.utils.render.RenderUtils;
import meteordevelopment.meteorclient.utils.render.color.SettingColor;
import meteordevelopment.meteorclient.utils.world.BlockUtils;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.block.Block;
import net.minecraft.block.FallingBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static meteordevelopment.meteorclient.utils.world.BlockUtils.getPlaceSide;

public final class Scaffold extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();
    private final SettingGroup sgTower = settings.createGroup("Tower");
    private final SettingGroup sgVulcanBypass = settings.createGroup("Bypass");
    private final SettingGroup sgRender = settings.createGroup("Render");

    private final Setting<List<Block>> blocks = sgGeneral.add(new BlockListSetting.Builder()
        .name("blocks")
        .description("Selected blocks.")
        .build()
    );

    private final Setting<ListMode> blocksFilter = sgGeneral.add(new EnumSetting.Builder<ListMode>()
        .name("blocks-filter")
        .description("How to use the block list setting")
        .defaultValue(ListMode.Blacklist)
        .build()
    );

    private final Setting<Sprint> sprint= sgGeneral.add(new EnumSetting.Builder<Sprint>()
        .name("Sprint")
        .description("To sprint or not to sprint; that is the question")
        .defaultValue(Sprint.Yes)
        .build()
    );

    private final Setting<Boolean> fastTower = sgGeneral.add(new BoolSetting.Builder()
        .name("tower")
        .description("Whether or not to scaffold upwards faster.")
        .defaultValue(false)
        .build()
    );

    private final Setting<towerMode> towerModeSetting = sgGeneral.add(new EnumSetting.Builder<towerMode>()
        .name("tower-mode")
        .description("tower mode")
        .defaultValue(towerMode.Vulcant)
        .visible(fastTower::get)
        .build()
    );

    private final Setting<Boolean> center = sgGeneral.add(new BoolSetting.Builder()
        .name("Center")
        .description("Centers on enable")
        .defaultValue(false)
        .build()
    );

    public final Setting<Boolean> down = sgGeneral.add(new BoolSetting.Builder()
        .name("down")
        .description("If to scaffold down, when player is shifting.")
        .defaultValue(false)
        .build()
    );

    private final Setting<Boolean> cancelVelocity = sgGeneral.add(new BoolSetting.Builder()
        .name("cancel-velocity")
        .description("Whether or not to cancel velocity when towering.")
        .defaultValue(false)
        .visible(fastTower::get)
        .build()
    );

    private final Setting<Boolean> onlyOnClick = sgGeneral.add(new BoolSetting.Builder()
        .name("only-on-click")
        .description("Only places blocks when holding right click.")
        .defaultValue(false)
        .build()
    );

    private final Setting<Boolean> renderSwing = sgGeneral.add(new BoolSetting.Builder()
        .name("swing")
        .description("Renders your client-side swing.")
        .defaultValue(false)
        .build()
    );

    private final Setting<Boolean> autoSwitch = sgGeneral.add(new BoolSetting.Builder()
        .name("auto-switch")
        .description("Automatically swaps to a block before placing.")
        .defaultValue(true)
        .build()
    );

    private final Setting<RotationMode> rotations = sgGeneral.add(new EnumSetting.Builder<RotationMode>()
        .name("Rotations")
        .description("Rotates towards the blocks being placed.")
        .defaultValue(RotationMode.Vanilla)
        .build()
    );

    private final Setting<Boolean> vulcanRotate = sgGeneral.add(new BoolSetting.Builder()
        .name("rotate")
        .description("Rotates (NCP rotations btw) when pressing W, useful for like, walking, and not flagging expand")
        .defaultValue(false)
        .visible(() -> rotations.get().equals(RotationMode.Vulcan))
        .build()
    );

    private final Setting<Double> vulcanSpeed = sgGeneral.add(new DoubleSetting.Builder()
        .name("vulcan-speed")
        .description("How fast the scaffold should be...")
        .defaultValue(2.94)
        .min(0)
        .sliderMax(12)
        .visible(() -> rotations.get().equals(RotationMode.Vulcan))
        .build()
    );

    private final Setting<Double> pitch = sgGeneral.add(new DoubleSetting.Builder()
        .name("pitch")
        .description("What pitch to use in legit rotations.")
        .defaultValue(81)
        .min(-90)
        .max(90)
        .sliderMin(-90)
        .sliderMax(90)
        .visible(() -> rotations.get() == RotationMode.Legit)
        .build()
    );

    private final Setting<Boolean> airPlace = sgGeneral.add(new BoolSetting.Builder()
        .name("air-place")
        .description("Allow air place. This also allows you to modify scaffold radius.")
        .defaultValue(false)
        .build()
    );

    private final Setting<Double> placeRange = sgGeneral.add(new DoubleSetting.Builder()
        .name("closest-block-range")
        .description("How far can scaffold place blocks when you are in air.")
        .defaultValue(4)
        .min(0)
        .sliderMax(8)
        .visible(() -> !airPlace.get())
        .build()
    );

    private final Setting<Boolean> expand = sgGeneral.add(new BoolSetting.Builder()
        .name("Expand")
        .description("Expand, self explanatory")
        .defaultValue(false)
        .build()
    );

    private final Setting<Integer> expandBlocks = sgGeneral.add(new IntSetting.Builder()
        .name("expand-amount")
        .description("the amount of blocks to expand")
        .defaultValue(3)
        .min(1)
        .visible(expand::get)
        .build()
    );

    private final Setting<Double> radius = sgGeneral.add(new DoubleSetting.Builder()
        .name("radius")
        .description("Scaffold radius.")
        .defaultValue(0)
        .min(0)
        .max(6)
        .visible(airPlace::get)
        .build()
    );

    private final Setting<Integer> blocksPerTick = sgGeneral.add(new IntSetting.Builder()
        .name("blocks-per-tick")
        .description("How many blocks to place in one tick.")
        .defaultValue(3)
        .min(1)
        .visible(airPlace::get)
        .build()
    );

    private final Setting<Boolean> wait = sgGeneral.add(new BoolSetting.Builder()
        .name("wait")
        .description("Makes you wait every n blocks")
        .defaultValue(false)
        .build()
    );

    private final Setting<waitMode> waitModeSetting = sgGeneral.add(new EnumSetting.Builder<waitMode>()
        .name("wait-mode")
        .description("The mode that should be used when waiting")
        .defaultValue(waitMode.Sneak)
        .visible(wait::get)
        .build()
    );

    private final Setting<Integer> blocksToWait = sgGeneral.add(new IntSetting.Builder()
        .name("blocks-to-wait")
        .description("Wait every how many blocks.")
        .defaultValue(3)
        .min(1)
        .visible(wait::get)
        .build()
    );

    private final Setting<Integer> waitDelay = sgGeneral.add(new IntSetting.Builder()
        .name("wait-delay")
        .description("the delay to wait.")
        .defaultValue(50)
        .min(1)
        .sliderMax(2000)
        .visible(wait::get)
        .build()
    );

    private final Setting<Boolean> jitter = sgGeneral.add(new BoolSetting.Builder()
        .name("jitter")
        .description("Makes you go to left and right very fast to bypass certain anticheats.")
        .defaultValue(false)
        .build()
    );

    private final Setting<Integer> jitterTimer= sgGeneral.add(new IntSetting.Builder()
        .name("Jitter Timer")
        .description("Jitter-timer, for some reason higher == better jitter")
        .defaultValue(50)
        .min(0)
        .max(2000)
        .visible(jitter::get)
        .build()
    );

    // Tower

    private final Setting<Boolean> vulcanTower = sgTower.add(new BoolSetting.Builder()
        .name("vulcan-tower")
        .description("A tower for vulcan")
        .defaultValue(false)
        .build()
    );

    private final Setting<Integer> TowerDelay = sgTower.add(new IntSetting.Builder()
        .name("Tower delay")
        .description("The delay in ticks between the towering")
        .defaultValue(5)
        .min(0)
        .max(100)
        .visible(vulcanTower::get)
        .build()
    );

    private final Setting<Integer> WaitDelay = sgTower.add(new IntSetting.Builder()
        .name("Wait delay")
        .description("The delay in ticks between the pauses")
        .defaultValue(2)
        .min(0)
        .max(100)
        .visible(vulcanTower::get)
        .build()
    );

    private final Setting<Double> ClipHeight = sgTower.add(new DoubleSetting.Builder()
        .name("Clip height")
        .description("The height to clip the player")
        .defaultValue(0.39)
        .min(0)
        .max(1)
        .visible(vulcanTower::get)
        .build()
    );

    private final Setting<Double> TimerSpeed = sgTower.add(new DoubleSetting.Builder()
        .name("Timer speed")
        .description("The speed of the tower timer")
        .defaultValue(0.7)
        .min(0)
        .max(1)
        .visible(vulcanTower::get)
        .build()
    );

    // Vulcan

    private final Setting<Boolean> vulcanbypass = sgVulcanBypass.add(new BoolSetting.Builder()
        .name("enable bypass")
        .description("enable to use the bypass and all of its settings")
        .defaultValue(false)
        .build()
    );

    private final Setting<Integer> normalTicks = sgVulcanBypass.add(new IntSetting.Builder()
        .name("normal-ticks")
        .description("amount of ticks the scaffold should chill for.")
        .defaultValue(12)
        .min(0)
        .max(100)
        .visible(vulcanbypass::get)
        .build()
    );

    private final Setting<Integer> vulcanTicks = sgVulcanBypass.add(new IntSetting.Builder()
        .name("vulcan-ticks")
        .description("amount of ticks the scaffold should vroom.")
        .defaultValue(12)
        .min(0)
        .max(100)
        .visible(vulcanbypass::get)
        .build()
    );

    private final Setting<Double> speedchange = sgVulcanBypass.add(new DoubleSetting.Builder()
        .name("speed change")
        .description("speed we are changing it to.")
        .defaultValue(5.14)
        .min(0)
        .max(10)
        .visible(vulcanbypass::get)
        .build()
    );

    private final Setting<Double> timer = sgVulcanBypass.add(new DoubleSetting.Builder()
        .name("timer speed")
        .description("Timer speed during slow speed")
        .defaultValue(0.88)
        .min(0)
        .max(2)
        .visible(vulcanbypass::get)
        .build()
    );

    private final Setting<Boolean> sneak = sgVulcanBypass.add(new BoolSetting.Builder()
        .name("sneak")
        .description("sneaks just funny bc no flag...")
        .defaultValue(false)
        .visible(vulcanbypass::get)
        .build()
    );

    private final Setting<Boolean> debug = sgVulcanBypass.add(new BoolSetting.Builder()
        .name("debug")
        .description("Enables debugging")
        .defaultValue(false)
        .visible(vulcanbypass::get)
        .build()
    );

    // Render

    private final Setting<Boolean> render = sgRender.add(new BoolSetting.Builder()
        .name("render")
        .description("Whether to render blocks that have been placed.")
        .defaultValue(true)
        .build()
    );

    private final Setting<ShapeMode> shapeMode = sgRender.add(new EnumSetting.Builder<ShapeMode>()
        .name("shape-mode")
        .description("How the shapes are rendered.")
        .defaultValue(ShapeMode.Both)
        .visible(render::get)
        .build()
    );

    private final Setting<SettingColor> sideColor = sgRender.add(new ColorSetting.Builder()
        .name("side-color")
        .description("The side color of the target block rendering.")
        .defaultValue(new SettingColor(197, 137, 232, 10))
        .visible(render::get)
        .build()
    );

    private final Setting<SettingColor> lineColor = sgRender.add(new ColorSetting.Builder()
        .name("line-color")
        .description("The line color of the target block rendering.")
        .defaultValue(new SettingColor(197, 137, 232))
        .visible(render::get)
        .build()
    );

    private final BlockPos.Mutable bp = new BlockPos.Mutable();
    private final BlockPos.Mutable prevBp = new BlockPos.Mutable();
    private boolean lastWasSneaking;
    private double lastSneakingY;
    private boolean jittering;
    private boolean wasSprinting = false;
    private int blocksPlaced = 0;
    private int tick;
    private int towertick;
    boolean vulcanBoost = false;
    private final TimerMS jitterTimerSpeed = new TimerMS();
    private final TimerMS waitTimer = new TimerMS();

    public Scaffold() {
        super(Categories.Movement, "scaffold", "Automatically places blocks under you.");
    }

    @Override
    public void onActivate() {
        vulcanBoost = false;
        if (sprint.get().equals(Sprint.No)) {
            if (mc.player.isSprinting())
                wasSprinting = true;
            mc.player.setSprinting(false);
            mc.options.sprintKey.setPressed(false);
            Modules.get().get(meteordevelopment.meteorclient.systems.modules.movement.Sprint.class).setOverride(false);
        }

        waitTimer.reset();

        if (center.get())
            PlayerUtils.centerPlayer();

        lastWasSneaking = mc.options.sneakKey.isPressed();
        if (lastWasSneaking) lastSneakingY = mc.player.getY();
    }

    @Override
    public void onDeactivate() {
        Modules.get().get(Timer.class).setOverride(1);

        if(sneak.get()){
            mc.options.sneakKey.setPressed(false);
        }

        if (sprint.get().equals(Sprint.No) && wasSprinting) {
            mc.player.setSprinting(true);
            mc.options.sprintKey.setPressed(true);
            Modules.get().get(meteordevelopment.meteorclient.systems.modules.movement.Sprint.class).resetOverride();
        }

        vulcanBoost = false;
        jittering = false;
        wasSprinting = false;
        super.onDeactivate();
    }

    @EventHandler
    private void onTick(final TickEvent.Pre event) {
        if (isNull())
            return;

        tick++;

        if (vulcanTower.get()){
            towertick++;
            Modules.get().get(Timer.class).setOverride(TimerSpeed.get());
            if (towertick > WaitDelay.get()){
                if (!mc.player.isOnGround()){
                    //normally 0.41
                    //best is 0.39
                    mc.player.setPosition(mc.player.getX(), mc.player.getY() + ClipHeight.get(), mc.player.getZ());
                    mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(mc.player.getX(), mc.player.getY(), mc.player.getZ(), mc.player.isOnGround()));
                }
                if (towertick == TowerDelay.get() + WaitDelay.get()){
                    towertick = 0;
                }
            }
        }

        if (vulcanbypass.get() && debug.get() /*|| Modules.get().isActive(Debug.class)*/){
            info(String.valueOf(tick));
        }

        if ((rotations.get().equals(RotationMode.Vulcan) || rotations.get().equals(RotationMode.Minemalia)) && vulcanbypass.get()) {
            if (tick > getTick(vulcanBoost)) {
                vulcanBoost = !vulcanBoost;
                tick = 0;
                if (debug.get() /*|| Modules.get().isActive(Debug.class)*/){
                    info("Reverted speed bool");
                }
            }
        }

        if (sprint.get().equals(Sprint.No)) {
            mc.player.setSprinting(false);
            if (Modules.get().isActive(meteordevelopment.meteorclient.systems.modules.movement.Sprint.class))
                Modules.get().get(meteordevelopment.meteorclient.systems.modules.movement.Sprint.class).setOverride(false);
        }


        if (sprint.get().equals(Sprint.Yes)) {
            mc.player.setSprinting(true);
            if (Modules.get().isActive(meteordevelopment.meteorclient.systems.modules.movement.Sprint.class))
                Modules.get().get(meteordevelopment.meteorclient.systems.modules.movement.Sprint.class).setOverride(false);
        }

        if (onlyOnClick.get() && !mc.options.useKey.isPressed()) return;

        if (blocksPlaced > blocksToWait.get() && wait.get())
            waita();

        if (airPlace.get()) {
            Vec3d vec = mc.player.getPos().add(mc.player.getVelocity()).add(0, -0.5f, 0);
            bp.set(vec.getX(), vec.getY(), vec.getZ());
        } else if (getPlaceSide(mc.player.getBlockPos().down()) != null) {
            bp.set(mc.player.getBlockPos().down());
        } else {
            Vec3d pos = mc.player.getPos();
            pos = pos.add(0, -0.98f, 0);
            pos.add(mc.player.getVelocity());

            if (!PlayerUtils.isWithin(prevBp, placeRange.get())) {
                List<BlockPos> blockPosArray = new ArrayList<>();

                for (int x = (int) (mc.player.getX() - placeRange.get()); x < mc.player.getX() + placeRange.get(); x++) {
                    for (int z = (int) (mc.player.getZ() - placeRange.get()); z < mc.player.getZ() + placeRange.get(); z++) {
                        for (int y = (int) Math.max(mc.world.getBottomY(), mc.player.getY() - placeRange.get()); y < Math.min(mc.world.getTopY(), mc.player.getY() + placeRange.get()); y++) {
                            bp.set(x, y, z);
                            if (!mc.world.getBlockState(bp).isAir()) blockPosArray.add(new BlockPos(bp));
                        }
                    }
                }
                if (blockPosArray.isEmpty()) {
                    return;
                }

                blockPosArray.sort(Comparator.comparingDouble(PlayerUtils::squaredDistanceTo));

                prevBp.set(blockPosArray.get(0));
            }

            Vec3d vecPrevBP = new Vec3d((double) prevBp.getX() + 0.5f,
                (double) prevBp.getY() + 0.5f,
                (double) prevBp.getZ() + 0.5f);

            Vec3d sub = pos.subtract(vecPrevBP);
            Direction facing;
            if (sub.getY() < -0.5f) {
                facing = Direction.DOWN;
            } else if (sub.getY() > 0.5f) {
                facing = Direction.UP;
            } else facing = Direction.getFacing(sub.getX(), 0, sub.getZ());

            bp.set(prevBp.offset(facing));
        }

        if (jitter.get())
            jitterMovement();

        // Move down if shifting
        if (mc.options.sneakKey.isPressed() && !mc.options.jumpKey.isPressed() && down.get()) {
            if (lastSneakingY - mc.player.getY() < 0.1) {
                lastWasSneaking = false;
                return;
            }
        } else if (down.get()) {
            lastWasSneaking = false;
        }
        if (!lastWasSneaking && down.get()) lastSneakingY = mc.player.getY();

        fastTower(false, null);

        if (airPlace.get()) {
            List<BlockPos> blocks = new ArrayList<>();
            for (int x = (int) (mc.player.getX() - radius.get()); x < mc.player.getX() + radius.get(); x++) {
                for (int z = (int) (mc.player.getZ() - radius.get()); z < mc.player.getZ() + radius.get(); z++) {
                    blocks.add(BlockPos.ofFloored(x, mc.player.getY() - 0.5, z));
                }
            }

            if (!blocks.isEmpty()) {
                blocks.sort(Comparator.comparingDouble(PlayerUtils::squaredDistanceTo));
                int counter = 0;
                for (BlockPos block : blocks) {
                    if (place(block)) {
                        fastTower(true, block);
                        counter++;
                    }

                    if (counter >= blocksPerTick.get()) {
                        break;
                    }
                }
            }
        } else {
            if (place(bp)) fastTower(true, bp);
            if (!mc.world.getBlockState(bp).isAir()) {
                prevBp.set(bp);
            }
        }
    }

    @Override
    public String getInfoString() {
        if (vulcanbypass.get())
            return String.valueOf(tick);

        return null;
    }

    public boolean scaffolding() {
        return isActive() && (!onlyOnClick.get() || (onlyOnClick.get() && mc.options.useKey.isPressed()));
    }

    private int getTick(final boolean funny) {
        if (funny)
            return vulcanTicks.get();
        if (!funny)
            return normalTicks.get();
        return vulcanTicks.get();
    }


    private boolean validItem(final ItemStack itemStack, BlockPos pos) {
        if (!(itemStack.getItem() instanceof BlockItem)) return false;

        Block block = ((BlockItem) itemStack.getItem()).getBlock();

        if (blocksFilter.get() == ListMode.Blacklist && blocks.get().contains(block)) return false;
        else if (blocksFilter.get() == ListMode.Whitelist && !blocks.get().contains(block)) return false;

        if (!Block.isShapeFullCube(block.getDefaultState().getCollisionShape(mc.world, pos))) return false;
        return !(block instanceof FallingBlock) || !FallingBlock.canFallThrough(mc.world.getBlockState(pos));
    }

    private boolean place(BlockPos bp) {
        blocksPlaced++;
        FindItemResult item = InvUtils.findInHotbar(itemStack -> validItem(itemStack, bp));
        if (!item.found()) return false;

        if (item.getHand() == null && !autoSwitch.get()) return false;

        if (rotations.get() == RotationMode.Legit) {
            double rotationPitch = pitch.get();
            mc.player.setPitch((float) rotationPitch);
        }

        //added rots bc it flagged for expand
        if (rotations.get().equals(RotationMode.Vulcan) && vulcanRotate.get()) {
                if (mc.options.forwardKey.isPressed() && mc.player.isOnGround()) {
                    Rotations.rotate(mc.player.getYaw() + 180, 73.591, 0);
                }
                else if (mc.options.backKey.isPressed()){
                    Rotations.rotate(mc.player.getYaw() + 0, 73.591, 0);
                }
                else if (mc.options.rightKey.isPressed()){
                    Rotations.rotate(mc.player.getYaw() - 90, 73.591, 0);
                }
                else if (mc.options.leftKey.isPressed()){
                    Rotations.rotate(mc.player.getYaw() + 90, 73.591, 0);
                }
        }

        if (rotations.get().equals(RotationMode.Minemalia)) {
            Rotations.rotate(mc.player.getYaw() + 180, 81, 0);
        }

        if (rotations.get().equals(RotationMode.Hypixel)) {
                if (mc.options.forwardKey.isPressed() && mc.player.isOnGround()){
                    Rotations.rotate(mc.player.getYaw() + 180, 73.591, 0);
                }
                else if (mc.options.backKey.isPressed()) {
                    Rotations.rotate(mc.player.getYaw() + 0, 73.591, 0);
                }
                else if (mc.options.rightKey.isPressed()){
                    Rotations.rotate(mc.player.getYaw() - 90, 73.591, 0);
                }
                else if (mc.options.leftKey.isPressed()){
                    Rotations.rotate(mc.player.getYaw() + 90, 73.591, 0);
                }
        }

        if (rotations.get() == RotationMode.GrimFunniRotation) {
            if (mc.player.isOnGround())
                Rotations.rotate(mc.player.getYaw(), -90, 0);
            else
                Rotations.rotate(mc.player.getYaw(), 90, 0);
        }
        BlockPos bp2 = bp;

        // Expands, apparently supposed to bipass vulcan
        if (expand.get()) {
            bp2 = expand(expandBlocks.get(), bp2, 0);
        }

        if (BlockUtils.place(bp2, item, getRotation(), 50, renderSwing.get(), true)) {
            // Render block if was placed
            if (render.get())
                RenderUtils.renderTickingBlock(bp2.toImmutable(), sideColor.get(), lineColor.get(), shapeMode.get(), 0, 8, true, false);
            return true;
        }
        return false;
    }

    private BlockUtils.RotationPacket getRotation() {
        if (rotations.get().equals(RotationMode.Grim) || rotations.get().equals(RotationMode.GrimFunniRotation)) return BlockUtils.RotationPacket.FullPacket;
        if (rotations.get().equals(RotationMode.Vanilla)) return BlockUtils.RotationPacket.Vanilla;
        return BlockUtils.RotationPacket.None;
    }

    @EventHandler
    public void onMove(final PlayerMoveEvent event) {
        if (rotations.get() == RotationMode.Hypixel) {
            Vec3d horizontalVelocity = PlayerUtils.getHorizontalVelocity(3.94);

            double velX = horizontalVelocity.getX();
            double velZ = horizontalVelocity.getZ();

            if (!mc.player.isOnGround()) {
                return;
            }

            ((IVec3d) event.movement).set(velX, event.movement.y, velZ);
        }

        // I AM GONNA CHANGE THE CODE LATER!!!! BC THIS CODE SUCKS
//        if (rotations.get() == RotationMode.Minemalia) {
//            int seconddelay = tick % 20;
//            if (seconddelay == 8) {
//                mc.options.sneakKey.setPressed(true);
//            } else if (seconddelay == 15) {
//                mc.options.sneakKey.setPressed(false);
//            }
//        }

        // Variation of Hypixel rotation
        // Allows user to set the speed
        if (rotations.get().equals(RotationMode.Vulcan) || rotations.get().equals(RotationMode.Minemalia)) {
            Vec3d velocity = mc.player.getVelocity();

            if (vulcanBoost) {
                if (!sneak.get())
                    velocity = PlayerUtils.getHorizontalVelocity(speedchange.get());

                if (sneak.get())
                    mc.options.sneakKey.setPressed(true);
            } else {
                if (!sneak.get())
                    velocity = PlayerUtils.getHorizontalVelocity(vulcanSpeed.get());

                if (sneak.get())
                    mc.options.sneakKey.setPressed(false);
            }

            double velX = velocity.getX();
            double velZ = velocity.getZ();

            if (!mc.player.isOnGround()) {
                return;
            }

            ((IVec3d) event.movement).set(velX, event.movement.y, velZ);
            if (vulcanBoost) {
                Modules.get().get(Timer.class).setOverride(PlayerUtils.isMoving() ? timer.get() : Timer.OFF);
            }
        }
    }

    private void waita() {
        blocksPlaced = 0;

        if (waitModeSetting.get().equals(waitMode.Sneak))
            mc.player.setSneaking(true);

        if (waitModeSetting.get().equals(waitMode.Velocity))
            mc.player.setVelocity(0, 0, 0);

        if (waitModeSetting.get().equals(waitMode.Motion))
            MoveUtils.resetMotionXZ();

        if (waitTimer.hasTimePassed(waitDelay.get()) && waitModeSetting.get().equals(waitMode.Sneak)) {
                mc.player.setSneaking(false);
                waitTimer.reset();
        }
    }

    private void fastTower(final boolean down, final BlockPos checkBlock) {
        if (down) {
            // Move player down so they are on top of the placed block ready to jump again
            if (fastTower.get() && mc.options.jumpKey.isPressed() && !mc.options.sneakKey.isPressed() && !mc.player.isOnGround()) {
                // The chunk hasn't updated yet so we check the block we were standing on
                if (!mc.world.getBlockState(checkBlock.down()).isReplaceable() && towerModeSetting.get().equals(towerMode.Vanilla))
                    mc.player.setVelocity(0, -0.28f, 0);

                if (!mc.world.getBlockState(checkBlock.down()).isReplaceable() && towerModeSetting.get().equals(towerMode.Vulcant))
                    mc.player.setVelocity(0, -0.28f, 0);
            }
        } else {
            if (mc.options.jumpKey.isPressed() && !mc.options.sneakKey.isPressed() && fastTower.get() && InvUtils.testInHotbar(stack -> validItem(stack, mc.player.getBlockPos()))) {
                Vec3d vel = mc.player.getVelocity();
                if (towerModeSetting.get().equals(towerMode.Vanilla))
                    mc.player.setVelocity(cancelVelocity.get() ? 0 : vel.x, 0.42, cancelVelocity.get() ? 0 : vel.z);

                if (towerModeSetting.get().equals(towerMode.Vulcant)) {
                    if (!mc.player.isOnGround() && tick > 3 && mc.player.getVelocity().getY() > 0) {
                            MoveUtils.motionY(-0.27);
                    }

                    if (MoveUtils.getSpeed() < 0.48f) {
                        MoveUtils.setMotion(0.48f);
                    }
                    else {
                        MoveUtils.setMotion((MoveUtils.getSpeed() * 0.985f));
                    }
                }
            }
        }
    }

   private BlockPos expand(int maxExpansion, BlockPos pos, int expanded) {
       final BlockPos expandedPos = pos.offset(mc.player.getMovementDirection(), expanded);
       if (expanded >= maxExpansion || expanded >= 6) return pos;
       return BlockUtils.isAir(expandedPos) ? expandedPos : this.expand(maxExpansion, expandedPos, expanded + 1);

       //final BlockPos expandedPos = pos.offset(mc.player.getMovementDirection(), expanded);
       //if (expanded >= expansion || expanded >= 6) return expandedPos;
       //return BlockUtils.isAir(expandedPos) ? expandedPos : this.expand(expansion, expandedPos, expanded + 1);
   }

    private void jitterMovement() {
        if (!mc.player.isOnGround()) {
            jittering = false;
            mc.options.rightKey.setPressed(false);
            mc.options.leftKey.setPressed(false);
            return;
        }

        if(mc.options.forwardKey.isPressed() && !mc.options.jumpKey.isPressed() && mc.player.isOnGround()) if(jitterTimerSpeed.hasTimePassed(jitterTimer.get())) {
            jitterTimerSpeed.reset();
            jittering = !jittering;
        } else if (jittering) {
            mc.options.rightKey.setPressed(true);
            mc.options.leftKey.setPressed(false);
        } else {
            mc.options.rightKey.setPressed(false);
            mc.options.leftKey.setPressed(true);
        }
        if(mc.player.forwardSpeed == 0) {
            mc.options.rightKey.setPressed(false);
            mc.options.leftKey.setPressed(false);
        }
    }

    public enum ListMode {
        Whitelist,
        Blacklist
    }

    public enum RotationMode {
        None,
        Vanilla,
        Legit,
        Grim,
        Hypixel,
        Vulcan,
        Minemalia,
        GrimFunniRotation
    }

    public enum Sprint {
        Yes,
        No,
        Neutral
    }

    public enum waitMode {
        Sneak,
        Velocity,
        Motion
    }

    public enum towerMode {
        Vanilla,
        Vulcant
    }
    public float wrapAngle(float angle) {
        while (angle < -180) {
            angle += 360;
        }
        while (angle > 180) {
            angle -= 360;
        }
        return angle;
    }
}

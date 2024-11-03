package dev.stephen.nexus.module.modules.player;

import dev.stephen.nexus.Client;
import dev.stephen.nexus.event.bus.Listener;
import dev.stephen.nexus.event.bus.Priorities;
import dev.stephen.nexus.event.bus.annotations.EventLink;
import dev.stephen.nexus.event.impl.player.EventSilentRotation;
import dev.stephen.nexus.event.impl.player.EventTickPre;
import dev.stephen.nexus.event.impl.render.EventRender3D;
import dev.stephen.nexus.module.Module;
import dev.stephen.nexus.module.ModuleCategory;
import dev.stephen.nexus.module.modules.combat.KillAura;
import dev.stephen.nexus.module.setting.impl.BooleanSetting;
import dev.stephen.nexus.module.setting.impl.NumberSetting;
import dev.stephen.nexus.utils.mc.PacketUtils;
import dev.stephen.nexus.utils.render.RenderUtils;
import dev.stephen.nexus.utils.render.ThemeUtils;
import dev.stephen.nexus.utils.rotation.RotationUtils;
import net.minecraft.block.*;
import net.minecraft.block.enums.BedPart;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

public final class BedAura extends Module {
    public final NumberSetting breakRange = new NumberSetting("Break Range", 1, 6, 4, 0.1);
    public final BooleanSetting breakSurroundings = new BooleanSetting("Break Top", true);
    public static final BooleanSetting allowRotate = new BooleanSetting("Rotate", true);
    public final BooleanSetting rotOnPacket = new BooleanSetting("Rot on packet", true);
    public final BooleanSetting autoTool = new BooleanSetting("AutoTool", false);
    public final BooleanSetting autoToolOnPacket = new BooleanSetting("AutoTool On Packet", false);

    public static final BooleanSetting ignoreGround = new BooleanSetting("Ignore Ground", true);
    public final BooleanSetting egg = new BooleanSetting("Break eggs", false);
    public final BooleanSetting ignoreOwnBed = new BooleanSetting("Ignore own bed", false);
    public final BooleanSetting cancelVelocity = new BooleanSetting("Cancel Velocity", false);

    public static BlockPos bedPos;
    public static Boolean rotate = false;

    private int breakTicks;
    private int delayTicks;

    public BedAura() {
        super("BedAura", "", 0, ModuleCategory.PLAYER);
        this.addSettings(breakRange, breakSurroundings, allowRotate, rotOnPacket, autoTool, autoToolOnPacket, ignoreGround, egg, ignoreOwnBed, cancelVelocity);
        rotOnPacket.addDependency(allowRotate, true);

        autoToolOnPacket.addDependency(autoTool, true);

        ignoreOwnBed.addDependency(egg, false);
    }

    @Override
    public void onEnable() {
        rotate = false;
        bedPos = null;

        breakTicks = 0;
        super.onEnable();
    }

    @Override
    public void onDisable() {
        reset(true);
        super.onDisable();
    }

    @EventLink
    public final Listener<EventTickPre> eventTickListener = event -> {
        if (isNull()) {
            return;
        }

        if (Client.INSTANCE.getModuleManager().getModule(Scaffold.class).isEnabled() && Client.INSTANCE.getModuleManager().getModule(Scaffold.class).blockData == null && mc.player.getInventory().getMainHandStack().getItem() instanceof BlockItem) {
            reset(true);
            return;
        }

        getBedPos();

        if (bedPos != null) {
            mine(bedPos);
        } else {
            reset(true);
        }
    };

    @EventLink(Priorities.VERY_HIGH)
    public final Listener<EventSilentRotation> eventSilentRotationListener = event -> {
        if (isNull()) {
            return;
        }

        if (Client.INSTANCE.getModuleManager().getModule(Scaffold.class).isEnabled() && Client.INSTANCE.getModuleManager().getModule(Scaffold.class).blockData == null && mc.player.getInventory().getMainHandStack().getItem() instanceof BlockItem) {
            reset(true);
            return;
        }

        if (Client.INSTANCE.getModuleManager().getModule(KillAura.class).isEnabled()) {
            return;
        }

        if (bedPos != null && allowRotate.getValue()) {
            if (rotate) {
                float[] rot = RotationUtils.getRotationToBlock(bedPos, getDirection(bedPos));
                event.setSpeed(180F);
                event.setYaw(rot[0]);
                event.setPitch(rot[1]);
                rotate = false;
            }
        }
    };

    @EventLink
    public final Listener<EventRender3D> eventRender3DListener = event -> {
        if (isNull()) {
            return;
        }

        if (bedPos != null) {
            RenderUtils.draw3DBox(event.getMatrixStack().peek().getPositionMatrix(), new Box(bedPos), ThemeUtils.getMainColor());
        }
    };

    private void getBedPos() {
        bedPos = null;
        double range = breakRange.getValue();
        for (double x = mc.player.getPos().x - range; x <= mc.player.getPos().x + range; x++) {
            for (double y = mc.player.getPos().y + mc.player.getStandingEyeHeight() - range; y <= mc.player.getPos().y + mc.player.getStandingEyeHeight() + range; y++) {
                for (double z = mc.player.getPos().z - range; z <= mc.player.getPos().z + range; z++) {
                    BlockPos pos = new BlockPos((int) x, (int) y, (int) z);

                    boolean isBlockGood = egg.getValue()
                            ? mc.world.getBlockState(pos).getBlock() instanceof DragonEggBlock
                            : ignoreOwnBed.getValue() ? mc.world.getBlockState(pos).getBlock() instanceof BedBlock && mc.world.getBlockState(pos).get(BedBlock.PART) == BedPart.HEAD && !isBedOwn(pos)
                            : mc.world.getBlockState(pos).getBlock() instanceof BedBlock && mc.world.getBlockState(pos).get(BedBlock.PART) == BedPart.HEAD;

                    if (isBlockGood) {
                        if (breakSurroundings.getValue() && isBedCovered(pos)) {
                            bedPos = pos.add(0, 1, 0);
                        } else {
                            bedPos = pos;
                        }
                        break;
                    }
                }
            }
        }
    }

    private void mine(BlockPos blockPos) {
        if (delayTicks > 0) {
            delayTicks--;
            return;
        }

        BlockState blockState = mc.world.getBlockState(blockPos);

        if (blockState.isAir()) {
            return;
        }

        int totalBreakTicks = getBreakTicks(bedPos, autoTool.getValue() && autoToolOnPacket.getValue() ? mc.player.getInventory().getStack(getAutoToolSlot(blockPos)) : mc.player.getMainHandStack());

        if (breakTicks == 0) {
            rotate = true;
            if (autoTool.getValue() && autoToolOnPacket.getValue()) {
                doAutoTool(blockPos);
            }
            mc.player.swingHand(Hand.MAIN_HAND);
            PacketUtils.sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.START_DESTROY_BLOCK, blockPos, getDirection(blockPos)));
        } else if (breakTicks >= totalBreakTicks) {
            rotate = true;
            if (autoTool.getValue() && autoToolOnPacket.getValue()) {
                doAutoTool(blockPos);
            }
            mc.player.swingHand(Hand.MAIN_HAND);
            PacketUtils.sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK, blockPos, getDirection(blockPos)));
            mc.getTutorialManager().onBlockBreaking(mc.world, blockPos, blockState, 1.0F);
            reset(false);
            return;
        } else {
            if (!rotOnPacket.getValue()) {
                rotate = true;
            }

            if (autoTool.getValue()) {
                if (!autoToolOnPacket.getValue()) {
                    doAutoTool(blockPos);
                } else {
                    mc.player.getInventory().selectedSlot = 0;
                }
            }

            mc.player.swingHand(Hand.MAIN_HAND);
        }

        breakTicks += 1;

        int currentProgress = (int) (((double) breakTicks / totalBreakTicks) * 100);
        mc.world.setBlockBreakingInfo(mc.player.getId(), blockPos, currentProgress / 10);
    }

    private void reset(boolean resetRotate) {
        if (bedPos != null) {
            mc.world.setBlockBreakingInfo(mc.player.getId(), bedPos, -1);
            mc.interactionManager.cancelBlockBreaking();
        }

        breakTicks = 0;
        delayTicks = 5;
        bedPos = null;
        rotate = !resetRotate;
    }

    public static Direction getDirection(BlockPos pos) {
        Vec3d eyesPos = new Vec3d(mc.player.getX(), mc.player.getY() + mc.player.getEyeHeight(mc.player.getPose()), mc.player.getZ());

        if ((double) pos.getY() > eyesPos.y) {
            if (mc.world.getBlockState(pos.add(0, -1, 0)).isReplaceable()) {
                return Direction.DOWN;
            } else {
                return mc.player.getHorizontalFacing().getOpposite();
            }
        }

        if (!mc.world.getBlockState(pos.add(0, 1, 0)).isReplaceable()) {
            return mc.player.getHorizontalFacing().getOpposite();
        }

        return Direction.UP;
    }

    private int getBreakTicks(BlockPos bp, ItemStack tool) {
        ItemStack oldHeld = mc.player.getMainHandStack();

        mc.player.getInventory().main.set(mc.player.getInventory().selectedSlot, tool);
        BlockState bs = mc.world.getBlockState(bp);
        int ticks = (int) Math.ceil(1f / bs.calcBlockBreakingDelta(mc.player, mc.world, bp));

        mc.player.getInventory().main.set(mc.player.getInventory().selectedSlot, oldHeld);
        return ticks;
    }

    private void doAutoTool(BlockPos pos) {
        mc.player.getInventory().selectedSlot = getAutoToolSlot(pos);
    }

    private int getAutoToolSlot(BlockPos pos) {
        int bestToolSlot = mc.player.getInventory().selectedSlot;

        BlockState blockState = mc.world.getBlockState(pos);
        for (int i = 0; i < 9; ++i) {
            ItemStack stack = mc.player.getInventory().getStack(i);
            if (stack.getMiningSpeedMultiplier(blockState) > mc.player.getInventory().getStack(bestToolSlot).getMiningSpeedMultiplier(blockState)) {
                bestToolSlot = i;
            }
        }
        return bestToolSlot;
    }

    public static boolean isBedOwn(final BlockPos blockPos) {
        if (!isBed(blockPos))
            return false;

        assert mc.player != null;

        if (mc.player.getScoreboardTeam() == null) {
            return false;
        }

        BedBlock block = (BedBlock) mc.world.getBlockState(blockPos).getBlock();

        String teamColor = mc.player.getScoreboardTeam().getColor().getName();

        String bedcolor = block.getColor().toString().toLowerCase();

        if (bedcolor == null || teamColor == null)
            return false;

        return teamColor.equalsIgnoreCase(bedcolor);
    }

    public static boolean isBed(BlockPos blockPos) {
        Block block = mc.world.getBlockState(blockPos).getBlock();
        return block instanceof BedBlock;
    }

    private boolean isBedCovered(BlockPos headBedBlockPos) {
        BlockPos headBedBlockPosOffSet1 = headBedBlockPos.add(1, 0, 0);
        BlockPos headBedBlockPosOffSet2 = headBedBlockPos.add(-1, 0, 0);
        BlockPos headBedBlockPosOffSet3 = headBedBlockPos.add(0, 0, 1);
        BlockPos headBedBlockPosOffSet4 = headBedBlockPos.add(0, 0, -1);

        if (!isBlockCovered(headBedBlockPos)) {
            return false;
        } else if (mc.world.getBlockState(headBedBlockPosOffSet1).getBlock() instanceof BedBlock && mc.world.getBlockState(headBedBlockPosOffSet1).get(BedBlock.PART) == BedPart.FOOT) {
            return isBlockCovered(headBedBlockPosOffSet1);
        } else if (mc.world.getBlockState(headBedBlockPosOffSet2).getBlock() instanceof BedBlock && mc.world.getBlockState(headBedBlockPosOffSet2).get(BedBlock.PART) == BedPart.FOOT) {
            return isBlockCovered(headBedBlockPosOffSet2);
        } else if (mc.world.getBlockState(headBedBlockPosOffSet3).getBlock() instanceof BedBlock && mc.world.getBlockState(headBedBlockPosOffSet3).get(BedBlock.PART) == BedPart.FOOT) {
            return isBlockCovered(headBedBlockPosOffSet3);
        } else if (mc.world.getBlockState(headBedBlockPosOffSet4).getBlock() instanceof BedBlock && mc.world.getBlockState(headBedBlockPosOffSet4).get(BedBlock.PART) == BedPart.FOOT) {
            return isBlockCovered(headBedBlockPosOffSet4);
        }

        return false;
    }

    private boolean isBlockCovered(BlockPos blockPos) {
        BlockPos[] directions = {
                blockPos.add(0, 1, 0), // Up
                blockPos.add(1, 0, 0), // East
                blockPos.add(-1, 0, 0), // West
                blockPos.add(0, 0, 1), // South
                blockPos.add(0, 0, -1) // North
        };

        for (BlockPos pos : directions) {
            Block block = mc.world.getBlockState(pos).getBlock();
            if (block instanceof AirBlock || block instanceof FluidBlock) {
                return false;
            }
        }

        return true;
    }

    public boolean shouldCancelVelocity() {
        return this.isEnabled() && cancelVelocity.getValue() && bedPos != null;
    }
}
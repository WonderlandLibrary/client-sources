package club.strifeclient.module.implementations.player;

import best.azura.eventbus.handler.EventHandler;
import best.azura.eventbus.handler.Listener;
import club.strifeclient.event.EventState;
import club.strifeclient.event.implementations.player.MotionEvent;
import club.strifeclient.module.Category;
import club.strifeclient.module.Module;
import club.strifeclient.module.ModuleInfo;
import club.strifeclient.setting.SerializableEnum;
import club.strifeclient.setting.implementations.BooleanSetting;
import club.strifeclient.setting.implementations.DoubleSetting;
import club.strifeclient.setting.implementations.ModeSetting;
import club.strifeclient.util.math.MathUtil;
import club.strifeclient.util.networking.PacketUtil;
import club.strifeclient.util.player.InventoryUtil;
import club.strifeclient.util.player.MovementUtil;
import club.strifeclient.util.player.RotationUtil;
import club.strifeclient.util.system.Stopwatch;
import club.strifeclient.util.world.WorldUtil;
import lombok.AllArgsConstructor;
import net.minecraft.block.BlockAir;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import org.lwjglx.input.Keyboard;

@ModuleInfo(name = "Scaffold", description = "Places blocks under you as you walk.", category = Category.PLAYER)
public final class Scaffold extends Module {

    public final ModeSetting<ScaffoldPlaceMode> placeModeSetting = new ModeSetting<>("Place Mode", ScaffoldPlaceMode.POST);
    public final ModeSetting<ScaffoldSwingMode> swingModeSetting = new ModeSetting<>("Swing Mode", ScaffoldSwingMode.NORMAL);
    public final DoubleSetting delaySetting = new DoubleSetting("Place Delay", 25, 0, 1000);
    public final DoubleSetting swapSlotSetting = new DoubleSetting("Swap Slot", 9, 1, 9, 1);
    public final BooleanSetting sprintSetting = new BooleanSetting("Sprint", false);
    public final ModeSetting<ScaffoldSprintMode> sprintModeSetting = new ModeSetting<>("Sprint Mode", ScaffoldSprintMode.WATCHDOG, sprintSetting::getValue);
    public final BooleanSetting towerSetting = new BooleanSetting("Tower", true);
    public final ModeSetting<ScaffoldTowerMode> towerModeSetting = new ModeSetting<>("Tower Mode", ScaffoldTowerMode.WATCHDOG, towerSetting::getValue);
    public final BooleanSetting downwardsSetting = new BooleanSetting("Downwards", true);
    public final BooleanSetting keepYSetting = new BooleanSetting("Keep Y", false);
    public final BooleanSetting expandSetting = new BooleanSetting("Expand", false);
    public final DoubleSetting expandAmountSetting = new DoubleSetting("Expand Amount", 1, 1, 10, 0.1, expandSetting::getValue);

    private int placed;
    private double startY;
    private BlockData blockData;
    private int bestBlockSlot;
    private float[] rotations;
    private final Stopwatch stopwatch = new Stopwatch();

    @Override
    protected void onEnable() {
        startY = mc.thePlayer.posY;
        placed = 0;
        bestBlockSlot = InventoryUtil.getBestBlockSlot(false);
        super.onEnable();
    }

    @EventHandler
    private final Listener<MotionEvent> motionEventListener = e -> {
//        if (bestBlockSlot != -1 && (bestBlockSlot - InventoryUtil.HOTBAR) < 9) {
//            InventoryUtil.windowClick(bestBlockSlot,
//                    swapSlotSetting.getInt() - 1,
//                    InventoryUtil.ClickType.SWAP_WITH_HOTBAR);
//            mc.playerController.syncCurrentPlayItem();
//            bestBlockSlot = swapSlotSetting.getInt() - 1;
//        }
//        if(mc.thePlayer.getHeldItem() != null && !(mc.thePlayer.getHeldItem().getItem() instanceof ItemBlock)) return;
        if(bestBlockSlot == -1)
            bestBlockSlot = InventoryUtil.getBestBlockSlot(false);
        if(bestBlockSlot == -1) return;
        final EventState currentState = getCurrentState();
        final EventState previousState = getPreviousState(currentState);
        if (e.eventState == previousState) {
            blockData = getBlockData();
            if(blockData == null) getBlockData(MovementUtil.getPosition().down());
            if (blockData != null) {
                rotations = RotationUtil.getScaffoldRotations(blockData.pos, blockData.face);
                final float sens = RotationUtil.getSensitivityMultiplier();
                final float yaw = rotations[0];
                final float pitch = Math.min(90, rotations[1]);
                float yawGCD = (Math.round(yaw / sens) * sens);
                float pitchGCD = (Math.round(pitch / sens) * sens);
                rotations[0] = e.prevYaw + MathHelper.clamp_float(yawGCD - e.prevYaw, -90.0F, 90.0F);
                rotations[1] = e.prevPitch + MathHelper.clamp_float(pitchGCD - e.prevPitch, -20.0F, 20.0F);
            }
        }
        final boolean shouldTower = !MovementUtil.isOnGround(1 / 64F) && !keepYSetting.getValue() &&
                mc.gameSettings.keyBindJump.isKeyDown() && towerSetting.getValue() && e.isPre();
        if (e.isPre()) {
            if (blockData != null) {
                e.yaw = rotations[0];
                e.pitch = rotations[1];
            }
        }
        if (e.eventState == currentState || shouldTower) {
            placeAtData(shouldTower);
//            if (placed % 5 == 0)
//                e.y += 0.0625F;
        }
        if (shouldSprint(e)) {
            switch (sprintModeSetting.getValue()) {
                case WATCHDOG: {
                    final double[] speed = MovementUtil.getSpeed(MovementUtil.getDirection(), MovementUtil.getBaseMovementSpeed() / 2);
                    PacketUtil.sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX - speed[0], mc.thePlayer.posY,
                            mc.thePlayer.posZ - speed[1], true));
                    if (mc.thePlayer.ticksExisted % MathUtil.randomInt(4, 6) == 0)
                        mc.thePlayer.setPosition(mc.thePlayer.posX - MovementUtil.getRandomHypixelValuesFloat(), mc.thePlayer.ticksExisted % MathUtil.randomInt(4, 6) == 0 ? mc.thePlayer.posY + MovementUtil.getRandomHypixelValuesFloat() : mc.thePlayer.posY - MovementUtil.getRandomHypixelValuesFloat(),
                                mc.thePlayer.posZ - MovementUtil.getRandomHypixelValuesFloat());
                }
                case POST:
                case PRE:
                case ALWAYS: {
                    mc.thePlayer.setSprinting(true);
                    break;
                }
            }
        }
        mc.thePlayer.inventory.currentItem = bestBlockSlot;
    };

    private void placeAtData(final boolean shouldTower) {
        mc.playerController.syncCurrentPlayItem();
        if (blockData != null && stopwatch.hasElapsed(delaySetting.getLong())) {
            rotations = RotationUtil.getScaffoldRotations(blockData.pos, blockData.face);
            if (mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem(), blockData.pos, blockData.face,
                    RotationUtil.getVectorForRotation(rotations[0], rotations[1]))) {
                switch (swingModeSetting.getValue()) {
                    case NORMAL: {
                        mc.thePlayer.swingItem();
                        break;
                    }
                    case NO_SWING: {
                        PacketUtil.sendPacketNoEvent(new C0APacketAnimation());
                        break;
                    }
                }
                blockData = null;
                rotations = null;
                stopwatch.reset();
                placed++;
            }
            if (shouldTower && towerModeSetting.getValue() == ScaffoldTowerMode.WATCHDOG) {
                mc.thePlayer.jump();
                mc.thePlayer.motionY -= MovementUtil.getRandomHypixelValues();
                if (mc.thePlayer.ticksExisted % MathUtil.randomInt(4, 6) == 0) {
                    mc.thePlayer.motionX -= MovementUtil.getRandomHypixelValues();
                    mc.thePlayer.motionZ -= MovementUtil.getRandomHypixelValues();
                }
            }
        }
    }

    private EventState getPreviousState(EventState state) {
        switch (state) {
            case PRE: return EventState.UPDATE;
            case POST: return EventState.PRE;
            case UPDATE: return EventState.POST;
            default: return state;
        }
    }

    private EventState getCurrentState() {
        return placeModeSetting.getValue() == ScaffoldPlaceMode.UPDATE ? EventState.UPDATE : placeModeSetting.getValue()
                == ScaffoldPlaceMode.PRE ? EventState.PRE : EventState.POST;
    }

    private boolean shouldSprint(MotionEvent e) {
        if (!sprintSetting.getValue() || blockData == null) return false;
        switch (sprintModeSetting.getValue()) {
            case PRE: return e.isPre();
            case POST: return e.isPost();
            case WATCHDOG: return e.isPre() && MovementUtil.isMovingOnGround() && placed % 3 == 0;
            default: return true;
        }
    }

    public BlockData getBlockData() {
        return getBlockData(keepYSetting.getValue() ? new BlockPos(mc.thePlayer.posX, startY, mc.thePlayer.posZ) : MovementUtil.getPosition());
    }

    public BlockData getBlockData(BlockPos pos) {
        BlockPos playerPosition = pos.down();
        if (mc.thePlayer.onGround && downwardsSetting.getValue() && !keepYSetting.getValue() && !expandSetting.getValue() && Keyboard.isKeyDown(mc.gameSettings.keyBindSneak.getKeyCode())) {
            playerPosition = playerPosition.down();
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), false);
        }
        if (mc.thePlayer.isCollidedVertically) {
            if (expandSetting.getValue() && MovementUtil.isMoving()) {
                final float forward = mc.thePlayer.movementInput.moveForward;
                final float strafe = mc.thePlayer.movementInput.moveStrafe;
                float yaw = MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationYaw);
                final double[] speed = MovementUtil.getSpeed(MovementUtil.getDirection(forward, strafe, yaw), expandAmountSetting.getDouble());
                playerPosition = playerPosition.add(speed[0] * 0.5, 0, speed[1] * 0.5);
            }
        }
        for(EnumFacing face : EnumFacing.values()) {
            final BlockPos offset = playerPosition.offset(face);
            if (!(offset.getBlock() instanceof BlockAir))
                return new BlockData(offset, WorldUtil.getInvertedFace(face));
        }
        for(EnumFacing outerFace : EnumFacing.values()) {
            final BlockPos outerOffset = playerPosition.offset(outerFace);
            for(EnumFacing face : EnumFacing.values()) {
                if (face == EnumFacing.DOWN || face == EnumFacing.UP) continue;
                final BlockPos offset = outerOffset.offset(face);
                if (!(offset.getBlock() instanceof BlockAir))
                    return new BlockData(offset, WorldUtil.getInvertedFace(face));
            }
        }
        return null;
    }

    @AllArgsConstructor
    private static class BlockData {
        public BlockPos pos;
        public EnumFacing face;
    }

    public enum ScaffoldPlaceMode implements SerializableEnum {
        PRE("Pre"), POST("Post"), UPDATE("Update");
        final String name;
        ScaffoldPlaceMode(final String name) {
            this.name = name;
        }
        @Override
        public String getName() {
            return name;
        }
    }

    public enum ScaffoldSwingMode implements SerializableEnum {
        NORMAL("Normal"), NO_SWING("No Swing"), NONE("None");
        final String name;
        ScaffoldSwingMode(final String name) {
            this.name = name;
        }
        @Override
        public String getName() {
            return name;
        }
    }

    public enum ScaffoldTowerMode implements SerializableEnum {
        WATCHDOG("Watchdog");
        final String name;
        ScaffoldTowerMode(final String name) {
            this.name = name;
        }
        @Override
        public String getName() {
            return name;
        }
    }

    public enum ScaffoldSprintMode implements SerializableEnum {
        PRE("Pre"), POST("Post"), ALWAYS("Always"), WATCHDOG("Watchdog");
        final String name;
        ScaffoldSprintMode(final String name) {
            this.name = name;
        }
        @Override
        public String getName() {
            return name;
        }
    }

}

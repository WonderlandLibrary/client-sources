package dev.nexus.modules.impl.player;

import dev.nexus.events.bus.Listener;
import dev.nexus.events.bus.annotations.EventLink;
import dev.nexus.events.impl.EventMotionPre;
import dev.nexus.events.impl.EventSilentRotation;
import dev.nexus.events.impl.EventStrafe;
import dev.nexus.modules.Module;
import dev.nexus.modules.ModuleCategory;
import dev.nexus.modules.setting.BooleanSetting;
import dev.nexus.modules.setting.ModeSetting;
import dev.nexus.utils.game.MoveUtil;
import dev.nexus.utils.game.ScaffoldUtil;
import lombok.Getter;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.ItemBlock;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import org.lwjgl.input.Keyboard;

public class Scaffold extends Module {
    public final ModeSetting rotMode = new ModeSetting("Rot Mode", "Strict Bruteforce", "Strict Bruteforce", "Bruteforce");
    public final ModeSetting sprintMode = new ModeSetting("Sprint Mode", "Off", "Off", "Watchdog", "Watchdog Slow");
    public final ModeSetting towerMode = new ModeSetting("Tower Mode", "Off", "Off", "Watchdog Vanilla");
    public final BooleanSetting rayTrace = new BooleanSetting("Raytrace", false);
    public final BooleanSetting strictRayTrace = new BooleanSetting("Strict Raytrace", false);


    public Scaffold() {
        super("Scaffold", Keyboard.KEY_R, ModuleCategory.PLAYER);
        this.addSettings(rotMode, sprintMode, towerMode, rayTrace, strictRayTrace);
    }

    // Rotations
    private float[] rotations;

    // Block Placing
    private BlockData blockData;
    public static int scaffoldYCoord;

    // Watchdog tower
    private int towerTicks;
    private boolean towering;

    // Retard Proof
    private boolean rotated;

    // Hypixel Sprint
    private boolean hypixelSprint;
    private int blocksPlaced;

    @EventLink
    public final Listener<EventStrafe> eventStrafeListener = event -> {
        if (isNull()) {
            return;
        }

        if (mc.thePlayer.inventory.getCurrentItem() == null || !(mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemBlock && mc.thePlayer.inventory.getCurrentItem().stackSize >= 3)) {
            return;
        }

        if (!rotated) {
            return;
        }

        if (blockData == null) {
            return;
        }


        switch (sprintMode.getMode()) {
            case "Watchdog Slow":
                mc.thePlayer.motionX *= 0.96;
                mc.thePlayer.motionZ *= 0.96;
                KeyBinding.setKeyBindState(mc.gameSettings.keyBindSprint.getKeyCode(), false);
                mc.thePlayer.setSprinting(false);
                break;
            case "Watchdog":
                if (MoveUtil.isMoving() && blocksPlaced > 2 && !mc.gameSettings.keyBindJump.isKeyDown()) {
                    if (mc.thePlayer.onGround) {
                        mc.thePlayer.jump();
                    }
                    if (mc.thePlayer.motionY + mc.thePlayer.posY < scaffoldYCoord + 2.0 && mc.thePlayer.motionY < -0.15) {
                        BlockData blockData1 = ScaffoldUtil.getBlockData2();
                        if (blockData1 != null) {
                            if (mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.inventory.getCurrentItem(), blockData1.position, blockData1.facing, ScaffoldUtil.getNewVector(blockData1))) {
                                mc.thePlayer.swingItem();
                                hypixelSprint = true;
                            }
                        }
                    }
                }
                KeyBinding.setKeyBindState(mc.gameSettings.keyBindSprint.getKeyCode(), hypixelSprint);
                mc.thePlayer.setSprinting(hypixelSprint);
                break;
            case "Off":
                KeyBinding.setKeyBindState(mc.gameSettings.keyBindSprint.getKeyCode(), false);
                mc.thePlayer.setSprinting(false);
                break;
        }


        if (mc.gameSettings.keyBindJump.isKeyDown()) {
            switch (towerMode.getMode()) {
                case "Watchdog Vanilla":
                    if (MoveUtil.isMoving() && MoveUtil.getSpeed() > 0.1 && !mc.thePlayer.isPotionActive(Potion.jump)) {
                        double towerSpeed = 0.29888888;
                        if (mc.thePlayer.onGround) {
                            towering = mc.gameSettings.keyBindJump.isKeyDown();
                            if (towering) {
                                towerTicks = 0;
                                mc.thePlayer.jumpTicks = 0;
                                if (mc.thePlayer.motionY > 0) {
                                    mc.thePlayer.motionY = 0.419848F;
                                    MoveUtil.strafe(towerSpeed - (0.0008 + Math.random() * 0.008));
                                }
                            }
                        } else if (towering) {
                            if (towerTicks == 2) {
                                mc.thePlayer.motionY = Math.floor(mc.thePlayer.posY + 1) - mc.thePlayer.posY;
                            } else if (towerTicks == 3) {
                                if (mc.gameSettings.keyBindJump.isKeyDown()) {
                                    mc.thePlayer.motionY = 0.41985F;
                                    MoveUtil.strafe(towerSpeed - (0.0008 + Math.random() * 0.008));
                                    towerTicks = 0;
                                } else {
                                    towering = false;
                                    towerTicks = 0;
                                }
                            }
                        }
                        towerTicks++;
                    }
                    break;
            }
        } else {
            towering = false;
            towerTicks = 0;
        }
    };

    @EventLink
    public final Listener<EventMotionPre> eventMotionPreListener = event -> {
        if (isNull()) {
            return;
        }
        if (ScaffoldUtil.getBlockData() != null) {
            blockData = ScaffoldUtil.getBlockData();
            updateRotations();
        }

        if (sprintMode.isMode("Watchdog")) {
            if (mc.gameSettings.keyBindJump.isKeyDown())
                scaffoldYCoord = (int) mc.thePlayer.posY - 1;
        } else {
            scaffoldYCoord = (int) mc.thePlayer.posY - 1;
        }

        if (mc.thePlayer.inventory.getCurrentItem() == null || !(mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemBlock && mc.thePlayer.inventory.getCurrentItem().stackSize >= 3)) {
            mc.thePlayer.inventory.currentItem = ScaffoldUtil.getBlockSlot();
            return;
        }

        if (blockData == null) {
            rotations = new float[]{mc.thePlayer.rotationYaw + 180, 80F};
            return;
        }

        if (!rotated) {
            return;
        }

        if (ScaffoldUtil.getBlockData() != null) {
            if (mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.inventory.getCurrentItem(), blockData.position, blockData.facing, ScaffoldUtil.getNewVector(blockData))) {
                mc.thePlayer.swingItem();
                blocksPlaced++;
            }
        }
    };


    // these are dogshit
    private void updateRotations() {
        switch (rotMode.getMode()) {
            case "Bruteforce":
                for (float yaw = MoveUtil.getMovingYaw() - 180; yaw < MoveUtil.getMovingYaw() + 180; yaw += 5) {
                    for (float pitch = 90; pitch > -90; pitch -= 5) {
                        final MovingObjectPosition cursor = mc.thePlayer.customRayTrace(5, 1F, yaw, pitch);
                        if (cursor != null && cursor.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                            BlockPos pos = cursor.getBlockPos();
                            if (pos.getX() == blockData.getPosition().getX() && pos.getY() == blockData.getPosition().getY() && pos.getZ() == blockData.getPosition().getZ()) {
                                rotations = new float[]{yaw, pitch};
                                break;
                            }
                        }
                    }
                }
                break;

            case "Strict Bruteforce":
                for (float yaw = MoveUtil.getMovingYaw() - 180; yaw < MoveUtil.getMovingYaw() + 180; yaw += 5) {
                    for (float pitch = 90; pitch > -90; pitch -= 5) {
                        final MovingObjectPosition cursor = mc.thePlayer.customRayTrace(5, 1F, yaw, pitch);
                        if (cursor != null && cursor.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                            BlockPos pos = cursor.getBlockPos();
                            if (pos.getX() == blockData.getPosition().getX() && pos.getY() == blockData.getPosition().getY() && pos.getZ() == blockData.getPosition().getZ()) {
                                if (cursor.sideHit == blockData.getFacing()) {
                                    rotations = new float[]{yaw, pitch};
                                    break;
                                }
                            }
                        }
                    }
                }
                break;
        }
    }

    @EventLink
    public final Listener<EventSilentRotation> eventSilentRotationListener = event -> {
        if (rotations == null) {
            return;
        }
        event.setYaw(rotations[0]);
        event.setPitch(rotations[1]);
        event.setSpeed(180);
        rotated = true;
    };

    @Override
    public void onEnable() {
        scaffoldYCoord = (int) (mc.thePlayer.posY - 1);
        rotated = false;
        blockData = null;

        towering = false;
        towerTicks = 0;

        hypixelSprint = false;
        blocksPlaced = 0;

        rotations = new float[]{mc.thePlayer.rotationYaw + 180, 80F};

        KeyBinding.setKeyBindState(mc.gameSettings.keyBindSprint.getKeyCode(), false);
        mc.thePlayer.setSprinting(false);
        super.onEnable();
    }

    @Getter
    public static class BlockData {
        private final BlockPos position;
        private final EnumFacing facing;

        public BlockData(final BlockPos position, final EnumFacing facing) {
            this.position = position;
            this.facing = facing;
        }
    }
}

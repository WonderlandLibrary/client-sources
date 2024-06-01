package com.polarware.module.impl.player;

import com.polarware.component.impl.player.BadPacketsComponent;
import com.polarware.component.impl.player.BlinkComponent;
import com.polarware.component.impl.player.RotationComponent;
import com.polarware.component.impl.player.SlotComponent;
import com.polarware.component.impl.player.rotationcomponent.MovementFix;
import com.polarware.module.Module;
import com.polarware.module.api.Category;
import com.polarware.module.api.ModuleInfo;
import com.polarware.module.impl.movement.SpeedModule;
import com.polarware.module.impl.player.scaffold.sprint.*;
import com.polarware.module.impl.player.scaffold.tower.*;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.input.MoveInputEvent;
import com.polarware.event.impl.motion.PreMotionEvent;
import com.polarware.event.impl.motion.PreUpdateEvent;
import com.polarware.event.impl.motion.StrafeEvent;
import com.polarware.event.impl.network.PacketReceiveEvent;
import com.polarware.util.RayCastUtil;
import com.polarware.util.math.MathUtil;
import com.polarware.util.packet.PacketUtil;
import com.polarware.util.player.EnumFacingOffset;
import com.polarware.util.player.MoveUtil;
import com.polarware.util.player.PlayerUtil;
import com.polarware.util.player.SlotUtil;
import com.polarware.util.vector.Vector2f;
import com.polarware.value.impl.*;
import net.minecraft.block.BlockAir;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.server.S2FPacketSetSlot;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import org.lwjgl.input.Keyboard;

import java.util.Objects;

/**
 * @author Alan
 * @since ??/??/21
 */

@ModuleInfo(name = "module.player.scaffold.name", description = "module.player.scaffold.description", category = Category.PLAYER)
public class ScaffoldModule extends Module {

    public final ModeValue mode = new ModeValue("Mode", this)
            .add(new SubMode("Normal"))
            .add(new SubMode("Derp"))
            .add(new SubMode("Telly"))
            .setDefault("Normal");

    private final ModeValue rayCast = new ModeValue("Ray Cast", this)
            .add(new SubMode("Off"))
            .add(new SubMode("Normal"))
            .add(new SubMode("Strict"))
            .setDefault("Off");

    private final ModeValue sprint = new ModeValue("Sprint", this)
            .add(new SubMode("Normal"))
            .add(new DisabledSprint("Disabled", this))
            .add(new LegitSprint("Legit", this))
            .add(new BypassSprint("Bypass", this))
            .add(new VulcanSprint("Vulcan", this))
            .add(new MatrixSprint("Matrix", this))
            .add(new WatchdogSprint("Watchdog", this))
            .setDefault("Normal");

    private final ModeValue tower = new ModeValue("Tower", this)
            .add(new SubMode("Disabled"))
            .add(new PolarTower("Lenient Polar", this))
            .add(new VulcanTower("Vulcan", this))
            .add(new VanillaTower("Vanilla", this))
            .add(new NormalTower("Normal", this))
            .add(new AirJumpTower("Air Jump", this))
            .add(new WatchdogTower("Watchdog", this))
            .add(new MMCTower("MMC", this))
            .add(new NCPTower("NCP", this))
            .add(new MatrixTower("Matrix", this))
            .add(new LegitTower("Legit", this))
            .setDefault("Disabled");

    private final ModeValue sameY = new ModeValue("Same Y", this)
            .add(new SubMode("Off"))
            .add(new SubMode("On"))
            .add(new SubMode("Auto Jump"))
            .setDefault("Off");

    private final BoundsNumberValue rotationSpeed = new BoundsNumberValue("Rotation Speed", this, 5, 10, 0, 10, 1);
    private final BoundsNumberValue placeDelay = new BoundsNumberValue("Place Delay", this, 0, 0, 0, 5, 1);
    private final NumberValue timer = new NumberValue("Timer", this, 1, 0.1, 10, 0.1);

    public final BooleanValue movementCorrection = new BooleanValue("Movement Correction", this, false);
    public final BooleanValue safeWalk = new BooleanValue("Safe Walk", this, true);
    private final BooleanValue sneak = new BooleanValue("Sneak", this, false);
    private final BooleanValue godbridge = new BooleanValue("God Bridge Rotations", this, false);
    public final BoundsNumberValue startSneaking = new BoundsNumberValue("Start Sneaking", this, 0, 0, 0, 5, 1, () -> !sneak.getValue());
    public final BoundsNumberValue stopSneaking = new BoundsNumberValue("Stop Sneaking", this, 0, 0, 0, 5, 1, () -> !sneak.getValue());
    public final NumberValue sneakEvery = new NumberValue("Sneak every x blocks", this, 1, 1, 10, 1, () -> !sneak.getValue());

    public final NumberValue sneakingSpeed = new NumberValue("Sneaking Speed", this, 0.2, 0.2, 1, 0.05, () -> !sneak.getValue());

    private final BooleanValue render = new BooleanValue("Render", this, true);

    private final BooleanValue advanced = new BooleanValue("Advanced", this, false);

    public final ModeValue yawOffset = new ModeValue("Yaw Offset", this, () -> !advanced.getValue())
            .add(new SubMode("0"))
            .add(new SubMode("45"))
            .add(new SubMode("-45"))
            .setDefault("0");

    public final BooleanValue ignoreSpeed = new BooleanValue("Ignore Speed Effect", this, false, () -> !advanced.getValue());
    public final BooleanValue upSideDown = new BooleanValue("Up Side Down", this, false, () -> !advanced.getValue());

    public final BooleanValue extraClick = new BooleanValue("Extra Click", this, false);
    public final NumberValue extraClickChance = new NumberValue("Extra Chance", this, 50, 0, 100, 0.1, () -> !extraClick.getValue());

    private Vec3 targetBlock;
    private EnumFacingOffset enumFacing;
    private BlockPos blockFace;
    private float targetYaw, targetPitch;
    private int ticksOnAir, sneakingTicks;
    private double startY;
    private float forward, strafe;
    private int placements;
    private boolean incrementedPlacements, placing;

    @Override
    protected void onEnable() {
        targetYaw = mc.thePlayer.rotationYaw - 180;
        targetPitch = 90;

        startY = Math.floor(mc.thePlayer.posY);
        targetBlock = null;

        this.sneakingTicks = -1;
    }

    @Override
    protected void onDisable() {
        mc.gameSettings.ofDynamicFov = true;
        if(mode.getValue().getName().equals("Derp")) {
            mc.gameSettings.fovSetting = 70;
        }
        mc.gameSettings.keyBindSneak.setPressed(Keyboard.isKeyDown(mc.gameSettings.keyBindSneak.getKeyCode()));
        mc.gameSettings.keyBindJump.setPressed(Keyboard.isKeyDown(mc.gameSettings.keyBindJump.getKeyCode()));

        BlinkComponent.blinking = false;

        SlotComponent.setSlot(mc.thePlayer.inventory.currentItem);
    }

    @EventLink()
    public final Listener<PacketReceiveEvent> onPacketReceiveEvent = event -> {

        final Packet<?> packet = event.getPacket();

        if (packet instanceof S2FPacketSetSlot) {
            final S2FPacketSetSlot wrapper = ((S2FPacketSetSlot) packet);

            if (wrapper.getItem() == null) {
                event.setCancelled(true);
            } else {
                try {
                    int slot = wrapper.getSlot() - 36;
                    if (slot < 0) return;
                    final ItemStack itemStack = mc.thePlayer.inventory.getStackInSlot(slot);
                    final Item item = wrapper.getItem().getItem();

                    if ((itemStack == null && wrapper.getItem().stackSize <= 6 && item instanceof ItemBlock && !SlotUtil.blacklist.contains(((ItemBlock) item).getBlock())) ||
                            itemStack != null && Math.abs(Objects.requireNonNull(itemStack).stackSize - wrapper.getItem().stackSize) <= 6 ||
                            wrapper.getItem() == null) {
                        event.setCancelled(true);
                    }
                } catch (ArrayIndexOutOfBoundsException exception) {
                    exception.printStackTrace();
                }
            }
        }
    };

    public void calculateSneaking(MoveInputEvent moveInputEvent) {
        forward = moveInputEvent.getForward();
        strafe = moveInputEvent.getStrafe();

        if (!this.sneak.getValue()) {
            return;
        }

        double speed = this.sneakingSpeed.getValue().doubleValue();

        if (speed <= 0.2) {
            return;
        }

        moveInputEvent.setSneakSlowDownMultiplier(speed);
    }

    public void calculateSneaking() {
        if(!sneak.getValue()) {
            return;
        }
        if (mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1, mc.thePlayer.posZ)).getBlock() instanceof BlockAir
                && mc.thePlayer.onGround) {
            mc.gameSettings.keyBindSneak.setPressed(true);
        } else {
            // if(mc.player.ticksExisted % 2 == 0) {
            mc.gameSettings.keyBindSneak.setPressed(false);

        }
    }

    public void calculateRotations() {
        float yawOffset = Float.parseFloat(String.valueOf(this.yawOffset.getValue().getName()));

        /* Calculating target rotations */
        switch (mode.getValue().getName()) {
            case "Normal":
                if (ticksOnAir > 0 && !RayCastUtil.overBlock(RotationComponent.rotations, enumFacing.getEnumFacing(), blockFace, rayCast.getValue().getName().equals("Strict"))) {
                    getRotations(2);
                }
                break;

            case "Derp":
                mc.gameSettings.ofDynamicFov = false;
                mc.gameSettings.fovSetting = 100f;
                getRotations(yawOffset);

                if (!(ticksOnAir > 0 && !RayCastUtil.overBlock(RotationComponent.rotations, enumFacing.getEnumFacing(), blockFace, rayCast.getValue().getName().equals("Strict")))) {
                    targetYaw = (float) (Math.toDegrees(MoveUtil.direction(mc.thePlayer.rotationYaw, forward, strafe))) + yawOffset;
                }
                break;

            case "Telly":
                if (mc.thePlayer.offGroundTicks >= 5) {
                    if (!RayCastUtil.overBlock(RotationComponent.rotations, enumFacing.getEnumFacing(), blockFace, rayCast.getValue().getName().equals("Strict"))) {
                        getRotations(yawOffset);
                    }
                } else {
                    getRotations(Float.parseFloat(String.valueOf(this.yawOffset.getValue().getName())));
                    targetYaw = mc.thePlayer.rotationYaw - yawOffset;
                }
                break;
        }

        /* Smoothing rotations */
        final double minRotationSpeed = this.rotationSpeed.getValue().doubleValue();
        final double maxRotationSpeed = this.rotationSpeed.getSecondValue().doubleValue();
        float rotationSpeed = (float) MathUtil.getRandom(minRotationSpeed, maxRotationSpeed);

        if (rotationSpeed != 0) {
            RotationComponent.setRotations(new Vector2f(targetYaw, targetPitch), rotationSpeed, movementCorrection.getValue() ? MovementFix.NORMAL : MovementFix.OFF);
        }
    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        if (targetBlock == null || enumFacing == null || blockFace == null) {
            return;
        }

        mc.thePlayer.hideSneakHeight.reset();

        // Timer
        if (timer.getValue().floatValue() != 1) mc.timer.timerSpeed = timer.getValue().floatValue();
    };

    public void runMode() {
        switch (this.mode.getValue().getName()) {
            case "Telly": {
                if (mc.thePlayer.onGround && MoveUtil.isMoving()) {
                    mc.thePlayer.jump();
                }
            }
        }
    }

    @EventLink()
    public final Listener<PreUpdateEvent> onPreUpdate = event -> {
        mc.thePlayer.safeWalk = this.safeWalk.getValue();

        // Getting ItemSlot
        SlotComponent.setSlot(SlotUtil.findBlock(), render.getValue());

        //Used to detect when to place a block, if over air, allow placement of blocks
        if (PlayerUtil.blockRelativeToPlayer(0, upSideDown.getValue() ? 2 : -1, 0) instanceof BlockAir) {
            ticksOnAir++;
        } else {
            ticksOnAir = 0;
        }

        this.calculateSneaking();

        // Gets block to place
        targetBlock = PlayerUtil.getPlacePossibility(0, upSideDown.getValue() ? 3 : 0, 0);

        if (targetBlock == null) {
            return;
        }

        //Gets EnumFacing
        enumFacing = PlayerUtil.getEnumFacing(targetBlock);

        if (enumFacing == null) {
            return;
        }

        final BlockPos position = new BlockPos(targetBlock.xCoord, targetBlock.yCoord, targetBlock.zCoord);

        blockFace = position.add(enumFacing.getOffset().xCoord, enumFacing.getOffset().yCoord, enumFacing.getOffset().zCoord);

        if (blockFace == null || enumFacing == null) {
            return;
        }

        this.calculateRotations();

        if (targetBlock == null || enumFacing == null || blockFace == null) {
            return;
        }

        if (this.sameY.getValue().getName().equals("Auto Jump")) {
            mc.gameSettings.keyBindJump.setPressed((mc.thePlayer.onGround && MoveUtil.isMoving()) || mc.gameSettings.keyBindJump.isPressed());
        }

        // Same Y
        final boolean sameY = ((!this.sameY.getValue().getName().equals("Off") || this.getModule(SpeedModule.class).isEnabled()) && !mc.gameSettings.keyBindJump.isKeyDown()) && MoveUtil.isMoving();

        if (startY - 1 != Math.floor(targetBlock.yCoord) && sameY) {
            return;
        }

        if (mc.thePlayer.inventory.alternativeCurrentItem == SlotComponent.getItemIndex()) {
            if (
                    !BadPacketsComponent.bad(false, true, false, false, true) &&
                    ticksOnAir > MathUtil.getRandom(placeDelay.getValue().intValue(), placeDelay.getSecondValue().intValue()) &&
                    (RayCastUtil.overBlock(enumFacing.getEnumFacing(), blockFace, rayCast.getValue().getName().equals("Strict")) || rayCast.getValue().getName().equals("Off"))
            ) {
                Vec3 hitVec = this.getHitVec();

                if (mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, SlotComponent.getItemStack(), blockFace, enumFacing.getEnumFacing(), hitVec)) {
                  //  PacketUtil.send(new C0APacketAnimation());
                    mc.thePlayer.swingItem();
                }

                mc.rightClickDelayTimer = 0;
                ticksOnAir = 0;

                assert SlotComponent.getItemStack() != null;
                if (SlotComponent.getItemStack() != null && SlotComponent.getItemStack().stackSize == 0) {
                    mc.thePlayer.inventory.mainInventory[SlotComponent.getItemIndex()] = null;
                }
            } else if (extraClick.getValue() && Math.random() < extraClickChance.getValue().doubleValue() / 100.0D) {
                PacketUtil.send(new C08PacketPlayerBlockPlacement(SlotComponent.getItemStack()));
                mc.rightClickDelayTimer = 0;
            }
        }

        //For Same Y
        if (mc.thePlayer.onGround || (mc.gameSettings.keyBindJump.isKeyDown() && !MoveUtil.isMoving())) {
            startY = Math.floor(mc.thePlayer.posY);
        }

        if (mc.thePlayer.posY < startY) {
            startY = mc.thePlayer.posY;
        }
    };

    @EventLink()
    public final Listener<MoveInputEvent> onMove = this::calculateSneaking;

    public void getRotations(final float yawOffset) {
        boolean found = false;

        float possibleYaw = mc.thePlayer.rotationYaw - (godbridge.getValue() ? 255 : 180);

        for (float possiblePitch = 90; possiblePitch > 30 && !found; possiblePitch -= possiblePitch > (mc.thePlayer.isPotionActive(Potion.moveSpeed) ? 60 : 80) ? 1 : 5) {
            if (RayCastUtil.overBlock(new Vector2f(possibleYaw, possiblePitch), enumFacing.getEnumFacing(), blockFace, true)) {
                targetYaw = possibleYaw;
                targetPitch = possiblePitch;
                found = true;
            }
        }
        //for (possibleYaw = mc.thePlayer.rotationYaw - 225; possibleYaw <= mc.thePlayer.rotationYaw + 360 - 180 && !found; possibleYaw += 45) {
        //            for (float possiblePitch = 90; possiblePitch > 30 && !found; possiblePitch -= possiblePitch > (mc.thePlayer.isPotionActive(Potion.moveSpeed) ? 60 : 80) ? 1 : 10) {
        //                if (RayCastUtil.overBlock(new Vector2f(possibleYaw, possiblePitch), enumFacing.getEnumFacing(), blockFace, true)) {
        //                    targetYaw = possibleYaw;
        //                    targetPitch = possiblePitch;
        //                    fo    und = true;
        //                }
        //            }
        //        }

        for (possibleYaw = mc.thePlayer.rotationYaw - 255; possibleYaw <= mc.thePlayer.rotationYaw + 360 - (godbridge.getValue() ? 255 : 180) && !found; possibleYaw += 45) {
            for (float possiblePitch = 90; possiblePitch > 30 && !found; possiblePitch -= possiblePitch > (mc.thePlayer.isPotionActive(Potion.moveSpeed) ? 60 : 80) ? 1 : 10) {
                if (RayCastUtil.overBlock(new Vector2f(possibleYaw, possiblePitch), enumFacing.getEnumFacing(), blockFace, true)) {
                    targetYaw = possibleYaw;
                    targetPitch = possiblePitch;
                    found = true;
                }
            }
        }
    }

    @EventLink()
    public final Listener<StrafeEvent> onStrafe = event -> {
        this.runMode();

        if (!Objects.equals(yawOffset.getValue().getName(), "0") && !movementCorrection.getValue()) {
            MoveUtil.useDiagonalSpeed();
        }
    };

    public Vec3 getHitVec() {
        /* Correct HitVec */
        Vec3 hitVec = new Vec3(
                blockFace.getX() + Math.random(),
                blockFace.getY() + Math.random(),
                blockFace.getZ() + Math.random()
        );

        final MovingObjectPosition movingObjectPosition = RayCastUtil.rayCast(RotationComponent.rotations, mc.playerController.getBlockReachDistance());

        switch (enumFacing.getEnumFacing()) {
            case DOWN:
                hitVec.yCoord = blockFace.getY() + 0;
                break;

            case UP:
                hitVec.yCoord = blockFace.getY() + 1;
                break;

            case NORTH:
                hitVec.zCoord = blockFace.getZ() + 0;
                break;

            case EAST:
                hitVec.xCoord = blockFace.getX() + 1;
                break;

            case SOUTH:
                hitVec.zCoord = blockFace.getZ() + 1;
                break;

            case WEST:
                hitVec.xCoord = blockFace.getX() + 0;
                break;
        }

        if (movingObjectPosition != null && movingObjectPosition.getBlockPos().equals(blockFace) &&
                movingObjectPosition.sideHit == enumFacing.getEnumFacing()) {
            hitVec = movingObjectPosition.hitVec;
        }

        return hitVec;
    }
}
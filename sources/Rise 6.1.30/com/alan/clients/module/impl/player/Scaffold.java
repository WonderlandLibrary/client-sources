package com.alan.clients.module.impl.player;

import com.alan.clients.component.impl.player.BadPacketsComponent;
import com.alan.clients.component.impl.player.RotationComponent;
import com.alan.clients.component.impl.player.Slot;
import com.alan.clients.component.impl.player.rotationcomponent.MovementFix;
import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.input.MoveInputEvent;
import com.alan.clients.event.impl.motion.PreMotionEvent;
import com.alan.clients.event.impl.motion.PreUpdateEvent;
import com.alan.clients.event.impl.motion.StrafeEvent;
import com.alan.clients.event.impl.other.TeleportEvent;
import com.alan.clients.event.impl.packet.PacketReceiveEvent;
import com.alan.clients.event.impl.packet.PacketSendEvent;
import com.alan.clients.module.Module;
import com.alan.clients.module.api.Category;
import com.alan.clients.module.api.ModuleInfo;
import com.alan.clients.module.impl.movement.Speed;
import com.alan.clients.module.impl.player.scaffold.downwards.NormalDownwards;
import com.alan.clients.module.impl.player.scaffold.downwards.VerusDownwards;
import com.alan.clients.module.impl.player.scaffold.downwards.WatchdogDownwards;
import com.alan.clients.module.impl.player.scaffold.sprint.*;
import com.alan.clients.module.impl.player.scaffold.tower.*;
import com.alan.clients.util.RayCastUtil;
import com.alan.clients.util.chat.ChatUtil;
import com.alan.clients.util.math.MathUtil;
import com.alan.clients.util.packet.PacketUtil;
import com.alan.clients.util.player.EnumFacingOffset;
import com.alan.clients.util.player.MoveUtil;
import com.alan.clients.util.player.PlayerUtil;
import com.alan.clients.util.player.SlotUtil;
import com.alan.clients.util.rotation.RotationUtil;
import com.alan.clients.util.vector.Vector2f;
import com.alan.clients.util.vector.Vector3d;
import com.alan.clients.value.impl.*;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.block.BlockAir;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.*;
import org.lwjgl.input.Keyboard;

import java.util.Objects;

@ModuleInfo(aliases = {"module.player.scaffold.name"}, description = "module.player.scaffold.description", category = Category.PLAYER)
public class Scaffold extends Module {

    public final ModeValue mode = new ModeValue("Mode", this)
            .add(new SubMode("Normal"))
            .add(new SubMode("Godbridge"))
            .add(new SubMode("Breesily"))
            .add(new SubMode("Snap"))
            .add(new SubMode("Telly"))
            .add(new SubMode("Eagle"))
            .setDefault("Normal");

    private final ModeValue rotationMode = new ModeValue("Rotation Mode", this)
            .add(new SubMode("Normal"))
            .setDefault("Normal");

    public final ModeValue rayCast = new ModeValue("Ray Cast", this)
            .add(new SubMode("Off"))
            .add(new SubMode("Normal"))
            .add(new SubMode("Strict"))
            .setDefault("Strict");

    public final ModeValue sprint = new ModeValue("Sprint", this)
            .add(new SubMode("Normal"))
            .add(new DisabledSprint("Disabled", this))
            .add(new LegitSprint("Legit", this))
            .add(new BypassSprint("Bypass", this))
            .add(new VulcanSprint("Vulcan", this))
            .add(new VerusSprint("Verus", this))
            .add(new MatrixSprint("Matrix", this))
            .add(new WatchdogLimitSprint("Watchdog Fast", this))
            .add(new WatchdogJumpSprint("Watchdog Jump", this))
            .add(new WatchdogSlow("Watchdog", this))
            .setDefault("Normal");

    public final ModeValue tower = new ModeValue("Tower", this)
            .add(new SubMode("Disabled"))
            .add(new VulcanTower("Vulcan", this))
            .add(new VanillaTower("Vanilla", this))
            .add(new NormalTower("Normal", this))
            .add(new AirJumpTower("Air Jump", this))
            .add(new WatchdogTower("Watchdog", this))
            .add(new MMCTower("MMC", this))
            .add(new NCPTower("NCP", this))
            .add(new MatrixTower("Matrix", this))
            .add(new LegitTower("Legit", this))
            .add(new TestTower("Verus", this))
            .setDefault("Disabled");

    public final ModeValue sameY = new ModeValue("Same Y", this)
            .add(new SubMode("Off"))
            .add(new SubMode("On"))
            .add(new SubMode("Auto Jump"))
            .setDefault("Off");

    public final ModeValue downwards = new ModeValue("Downwards (Press Sneak)", this)
            .add(new SubMode("Off"))
            .add(new NormalDownwards("Normal", this))
            .add(new WatchdogDownwards("Watchdog", this))
            .add(new VerusDownwards("Verus", this))
            .setDefault("Off");

    private final BoundsNumberValue rotationSpeed = new BoundsNumberValue("Rotation Speed", this, 5, 10, 0, 10, 1);
    public final BoundsNumberValue placeDelay = new BoundsNumberValue("Place Delay", this, 0, 0, 0, 5, 1);
    private final NumberValue timer = new NumberValue("Timer", this, 1, 0.1, 10, 0.1);
    private final NumberValue expand = new NumberValue("Expand", this, 0, 0, 4, 1);
    public final BooleanValue movementCorrection = new BooleanValue("Movement Correction", this, false);
    public final BooleanValue safeWalk = new BooleanValue("Safe Walk", this, true);
    private final BooleanValue sneak = new BooleanValue("Sneak", this, false);
    public final BoundsNumberValue startSneaking = new BoundsNumberValue("Start Sneaking", this, 0, 0, 0, 5, 1, () -> !sneak.getValue());
    public final BoundsNumberValue stopSneaking = new BoundsNumberValue("Stop Sneaking", this, 0, 0, 0, 5, 1, () -> !sneak.getValue());
    public final BoundsNumberValue sneakEvery = new BoundsNumberValue("Sneak every x blocks", this, 1, 1, 1, 10, 1, () -> !sneak.getValue());

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
    private Vec3 targetBlock;
    private EnumFacingOffset enumFacing;
    public Vec3i offset = new Vec3i(0, 0, 0);
    private BlockPos blockFace;
    private float targetYaw, targetPitch, forward, strafe, yawDrift, pitchDrift;
    @Getter
    @Setter
    private int ticksOnAir;
    private int sneakingTicks;
    private int placements;
    private int slow;
    private int pause;
    public int recursions, recursion;
    public double startY;
    private boolean canPlace;
    private int directionalChange;
    private int blockCount;

    @Override
    public void onEnable() {
        targetYaw = mc.thePlayer.rotationYaw - 180 + Integer.parseInt(yawOffset.getValue().getName());
        targetPitch = 90;

        pitchDrift = (float) ((Math.random() - 0.5) * (Math.random() - 0.5) * 10);
        yawDrift = (float) ((Math.random() - 0.5) * (Math.random() - 0.5) * 10);

        startY = Math.floor(mc.thePlayer.posY);
        targetBlock = null;

        this.sneakingTicks = -1;
        recursions = 0;
        placements = 0;
    }

    @Override
    public void onDisable() {
        resetBinds();
    }

    public void resetBinds() {
        resetBinds(true, true, true, true, true, true);
    }

    public void resetBinds(boolean sneak, boolean jump, boolean right, boolean left, boolean forward, boolean back) {
        if (sneak)
            mc.gameSettings.keyBindSneak.setPressed(Keyboard.isKeyDown(mc.gameSettings.keyBindSneak.getKeyCode()));
        if (jump) mc.gameSettings.keyBindJump.setPressed(Keyboard.isKeyDown(mc.gameSettings.keyBindJump.getKeyCode()));
        if (right)
            mc.gameSettings.keyBindRight.setPressed(Keyboard.isKeyDown(mc.gameSettings.keyBindRight.getKeyCode()));
        if (left) mc.gameSettings.keyBindLeft.setPressed(Keyboard.isKeyDown(mc.gameSettings.keyBindLeft.getKeyCode()));

        if (forward)
            mc.gameSettings.keyBindForward.setPressed(Keyboard.isKeyDown(mc.gameSettings.keyBindForward.getKeyCode()));
        if (back)
            mc.gameSettings.keyBindBack.setPressed(Keyboard.isKeyDown(mc.gameSettings.keyBindBack.getKeyCode()));
    }

    @EventLink
    public final Listener<PacketReceiveEvent> onPacketReceiveEvent = PacketUtil::correctBlockCount;

    public void calculateSneaking(MoveInputEvent moveInputEvent) {
        forward = moveInputEvent.getForward();
        strafe = moveInputEvent.getStrafe();

        if (slow-- > 0) {
            moveInputEvent.setForward(0);
            moveInputEvent.setStrafe(0);
        }

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
        if (ticksOnAir == 0) mc.gameSettings.keyBindSneak.setPressed(false);

        this.sneakingTicks--;

        if (!this.sneak.getValue() && pause <= 0) {
            return;
        }

        int ahead = startSneaking.getRandomBetween().intValue();
        int place = placeDelay.getRandomBetween().intValue();
        int after = stopSneaking.getRandomBetween().intValue();

//        if (canSneak) this.sneakingTicks = ahead + place + after;

        if (pause > 0) {
            pause--;

            sneakingTicks = 0;
            placements = 0;
        }

        if (this.sneakingTicks >= 0) {
            mc.gameSettings.keyBindSneak.setPressed(true);
            return;
        }

        if (ticksOnAir > 0) {
            this.sneakingTicks = (int) (double) (after);
        }

        if (ticksOnAir > 0 || PlayerUtil.blockRelativeToPlayer(mc.thePlayer.motionX * ahead, MoveUtil.HEAD_HITTER_MOTION, mc.thePlayer.motionZ * ahead) instanceof BlockAir) {
            if (placements <= 0) {
                this.sneakingTicks = (int) (double) (ahead + place + after);
                placements = sneakEvery.getRandomBetween().intValue();
            }
        }
    }

    public void calculateRotations() {
        int yawOffset = Integer.parseInt(String.valueOf(this.yawOffset.getValue().getName()));

        /* Smoothing rotations */
        final double minRotationSpeed = this.rotationSpeed.getValue().doubleValue();
        final double maxRotationSpeed = this.rotationSpeed.getSecondValue().doubleValue();
        float rotationSpeed = (float) MathUtil.getRandom(minRotationSpeed, maxRotationSpeed);

        MovementFix movementFix = this.movementCorrection.getValue() ? MovementFix.NORMAL : MovementFix.OFF;

        /* Calculating target rotations */
        switch (mode.getValue().getName()) {
            case "Normal":
                mc.entityRenderer.getMouseOver(1);

                if (canPlace && !mc.gameSettings.keyBindPickBlock.isKeyDown()) {
                    if (mc.objectMouseOver.sideHit != enumFacing.getEnumFacing() || !mc.objectMouseOver.getBlockPos().equals(blockFace)) {
                        getRotations(yawOffset);
                    }
                }

                /* else {
                    Vector3d position = MoveUtil.predictedPosition();
                    Vector3d current = new Vector3d(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
                    mc.thePlayer.setPosition(position.getX(), position.getY(), position.getZ());

                    if (PlayerUtil.blockRelativeToPlayer(0,-0.1,0) instanceof BlockAir) {
                        ChatUtil.display("Set");
                        getRotations(yawOffset);
                    }

                    mc.thePlayer.setPosition(current.getX(), current.getY(), current.getZ());
                }*/
                break;

            case "Breesily":
                if (canPlace) {
                    if (enumFacing.getEnumFacing() == EnumFacing.UP) {
                        targetPitch = 90;
                    } else {
                        double staticYaw = (float) (Math.toDegrees(Math.atan2(enumFacing.getOffset().zCoord,
                                enumFacing.getOffset().xCoord)) % 360) - 90;
                        double staticPitch = 80;

                        targetYaw = (float) staticYaw + yawDrift;
                        targetPitch = (float) staticPitch + pitchDrift;
                    }
                } else if (Math.random() > 0.99 || targetPitch % 90 == 0) {
                    yawDrift = (float) (Math.random() - 0.5);
                    pitchDrift = (float) (Math.random() - 0.5);
                }

                if (mc.gameSettings.keyBindForward.isKeyDown() && !mc.gameSettings.keyBindJump.isKeyDown()) {
                    double offset = 0;
                    double speed = 0;

                    switch (mc.thePlayer.getHorizontalFacing()) {
                        case NORTH:
                            offset = mc.thePlayer.posX - Math.floor(mc.thePlayer.posX);
                            speed = mc.thePlayer.motionZ;
                            break;

                        case EAST:
                            offset = mc.thePlayer.posZ - Math.floor(mc.thePlayer.posZ);
                            speed = mc.thePlayer.motionX;
                            break;

                        case SOUTH:
                            offset = 1 - (mc.thePlayer.posX - Math.floor(mc.thePlayer.posX));
                            speed = mc.thePlayer.motionZ;
                            break;

                        case WEST:
                            offset = 1 - (mc.thePlayer.posZ - Math.floor(mc.thePlayer.posZ));
                            speed = mc.thePlayer.motionX;
                            break;

                        default:
                            ChatUtil.display("Unknown " + Math.random());
                            break;
                    }

//                    mc.gameSettings.keyBindRight.setPressed(Keyboard.isKeyDown(mc.gameSettings.keyBindLeft.getKey()));
//                    mc.gameSettings.keyBindBack.setPressed(Keyboard.isKeyDown(mc.gameSettings.keyBindForward.getKey()));
//                    mc.gameSettings.keyBindForward.setPressed(Keyboard.isKeyDown(mc.gameSettings.keyBindBack.getKey()));
//                    mc.gameSettings.keyBindLeft.setPressed(Keyboard.isKeyDown(mc.gameSettings.keyBindRight.getKey()));

//                    movementFix = MovementFix.TRADITIONAL;

                    speed = Math.abs(speed);

                    if (speed < 0.086 && Math.abs(offset - 0.5) < 0.4 && placeDelay.getSecondValue().intValue() <= 1) {
                    } else if (offset < 0.5 + ((Math.random() - 0.5) / 10)) {
                        mc.gameSettings.keyBindLeft.setPressed(false);
                        mc.gameSettings.keyBindRight.setPressed(true);
                    } else {
                        mc.gameSettings.keyBindRight.setPressed(false);
                        mc.gameSettings.keyBindLeft.setPressed(true);
                    }
                }

                break;

            case "Snap":
                getRotations(yawOffset);

                if (!(ticksOnAir > 0 && !RayCastUtil.overBlock(RotationComponent.rotations, enumFacing.getEnumFacing(), blockFace, rayCast.getValue().getName().equals("Strict")))) {
                    targetYaw = (float) (Math.toDegrees(MoveUtil.direction(mc.thePlayer.rotationYaw, forward, strafe))) - yawOffset;
                }
                break;

            case "Eagle":
                float yaw = (mc.thePlayer.rotationYaw + 10000000) % 360;
                float staticYaw = (yaw - 180) - (yaw % 90) + 45;
                float staticPitch = 78;

                boolean straight = (Math.min(Math.abs(yaw % 90), Math.abs(90 - yaw) % 90) < Math.min(Math.abs(yaw + 45) % 90, Math.abs(90 - (yaw + 45)) % 90));

                if (straight && RayCastUtil.rayCast(new Vector2f(staticYaw + 90, staticPitch), 3).typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK &&
                        RayCastUtil.rayCast(new Vector2f(staticYaw, staticPitch), 3).typeOfHit != MovingObjectPosition.MovingObjectType.BLOCK) {
                    staticYaw += 90;
                }

                movementFix = MovementFix.NORMAL;

                if (Math.random() > (mc.thePlayer.onGround ? 0.5 : 0.2) && getComponent(Slot.class).getItemStack().getItem() instanceof ItemBlock) {
                    mc.rightClickMouse();
                }

                mc.thePlayer.movementInput.sneak = mc.thePlayer.sendQueue.doneLoadingTerrain;

                if (mc.thePlayer.offGroundTicks >= 4 && MoveUtil.isMoving()) {
                    mc.gameSettings.keyBindSneak.setPressed(true);
                }

                if (mc.thePlayer.onGroundTicks == 1) mc.gameSettings.keyBindSneak.setPressed(false);

                if (!straight) {
                    staticYaw += 90;
                }

                targetYaw = staticYaw + yawDrift / 2;
                targetPitch = staticPitch + pitchDrift / 2;
                break;

            case "Telly":
                if (recursion == 0) {
                    int time = mc.thePlayer.offGroundTicks;

                    if (time == 2 || time == 0) mc.rightClickMouse();

                    if (time >= 3 && mc.thePlayer.offGroundTicks <= (sameY.getValue().getName().equals("Off") ? 7 : 10)) {
                        if (!RayCastUtil.overBlock(RotationComponent.rotations, enumFacing.getEnumFacing(), blockFace, rayCast.getValue().getName().equals("Strict"))) {
                            getRotations(0);
                        }
                    } else {
                        getRotations(Integer.parseInt(String.valueOf(this.yawOffset.getValue().getName())));
                        targetYaw = mc.thePlayer.rotationYaw;
                    }

                    if (mc.thePlayer.offGroundTicks <= 3) {
                        canPlace = false;
                    }
                }
                break;

            case "Godbridge":
                if (getComponent(Slot.class).getItem() instanceof ItemBlock && canPlace) {
                    mc.rightClickMouse();
                }

                targetYaw = (mc.thePlayer.rotationYaw - mc.thePlayer.rotationYaw % 90) - 180 + 45 * (mc.thePlayer.rotationYaw > 0 ? 1 : -1);
                targetPitch = 76.4f;

                movementFix = MovementFix.TRADITIONAL;

                double spacing = 0.15;
                boolean edgeX = Math.abs(mc.thePlayer.posX % 1) > 1 - spacing ||
                        Math.abs(mc.thePlayer.posX % 1) < spacing;
                boolean edgeZ = Math.abs(mc.thePlayer.posZ % 1) > 1 - spacing ||
                        Math.abs(mc.thePlayer.posZ % 1) < spacing;

                mc.gameSettings.keyBindRight.setPressed((edgeX && edgeZ) || (Keyboard.isKeyDown(mc.gameSettings.keyBindLeft.getKeyCode())));
                mc.gameSettings.keyBindBack.setPressed(Keyboard.isKeyDown(mc.gameSettings.keyBindForward.getKeyCode()));
                mc.gameSettings.keyBindForward.setPressed(Keyboard.isKeyDown(mc.gameSettings.keyBindBack.getKeyCode()));
                mc.gameSettings.keyBindLeft.setPressed(Keyboard.isKeyDown(mc.gameSettings.keyBindRight.getKeyCode()));

                directionalChange++;
                if (Math.abs(MathHelper.wrapAngleTo180_double(targetYaw -
                        RotationComponent.lastServerRotations.getX())) > 10) {
                    directionalChange = (int) (Math.random() * 4);
                    yawDrift = (float) (Math.random() - 0.5) / 10f;
                    pitchDrift = (float) (Math.random() - 0.5) / 10f;
                }

                if (Math.random() > 0.99) {
                    yawDrift = (float) (Math.random() - 0.5) / 10f;
                    pitchDrift = (float) (Math.random() - 0.5) / 10f;
                }

                if (directionalChange <= 10) {
                    mc.gameSettings.keyBindSneak.setPressed(true);
                } else if (directionalChange == 11) {
                    mc.gameSettings.keyBindSneak.setPressed(false);
                }

                targetYaw += yawDrift;
                targetPitch += pitchDrift;
                break;
        }

        if (rotationSpeed != 0 && blockFace != null && enumFacing != null) {
            RotationComponent.setRotations(new Vector2f(targetYaw, targetPitch), rotationSpeed, movementFix);
        }
    }

    @EventLink
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        this.offset = new Vec3i(0, 0, 0);

        if (targetBlock == null || enumFacing == null || blockFace == null) {
            return;
        }

        mc.thePlayer.hideSneakHeight.reset();

        // Timer
        if (timer.getValue().floatValue() != 1) mc.timer.timerSpeed = timer.getValue().floatValue();
    };

    public void runMode() {
        if (this.mode.getValue().getName().equals("Telly")) {
            if (mc.thePlayer.onGround && MoveUtil.isMoving()) {
                mc.thePlayer.jump();
            }
        }
    }

    @EventLink
    public final Listener<TeleportEvent> onTeleport = event -> {
        if (event.getPosY() < mc.thePlayer.posY - 2) this.toggle();
    };

    @EventLink
    public final Listener<PreUpdateEvent> onPreUpdate = event -> {
        for (recursion = 0; recursion <= recursions; recursion++) {
            mc.thePlayer.safeWalk = this.safeWalk.getValue();

            resetBinds(false, false, true, true, false, false);

            if (this.upSideDown.getValue()) this.offset.setY(3);

            if (expand.getValue().intValue() != 0) {
                double direction = MoveUtil.direction(mc.thePlayer.rotationYaw, mc.gameSettings.keyBindForward.isKeyDown() ? 1 :
                        mc.gameSettings.keyBindBack.isKeyDown() ? -1 : 0, mc.gameSettings.keyBindRight.isKeyDown() ? -1 :
                        mc.gameSettings.keyBindLeft.isKeyDown() ? 1 : 0);

                for (int range = 0; range <= expand.getValue().intValue(); range++) {
                    if (PlayerUtil.blockAheadOfPlayer(range, this.offset.getY() - 0.5) instanceof BlockAir) {
                        this.offset = this.offset.add(new Vec3i((int) (-Math.sin(direction) * (range + 1)), 0, (int) (Math.cos(direction) * (range + 1))));
                        break;
                    }
                }
            }

            // Same Y
            final boolean sameY = ((!this.sameY.getValue().getName().equals("Off") || this.getModule(Speed.class).isEnabled()) && !mc.gameSettings.keyBindJump.isKeyDown()) && MoveUtil.isMoving();

            // Getting ItemSlot
            getComponent(Slot.class).setSlotDelayed(SlotUtil.findBlock(), mc.thePlayer.offGroundTicks > 5 || ticksOnAir > 0 || sprint.getValue().getName().equals("Normal"));

            // Used to detect when to place a block, if over air, allow placement of blocks
            if (doesNotContainBlock(1) && (!sameY || (doesNotContainBlock(2) && doesNotContainBlock(3) && doesNotContainBlock(4)))) {
                ticksOnAir++;
            } else {
                ticksOnAir = 0;
            }

            canPlace = !BadPacketsComponent.bad(false, true, false, false, true) &&
                    ticksOnAir > MathUtil.getRandom(placeDelay.getValue().intValue(), placeDelay.getSecondValue().intValue());

            if (recursion == 0) this.calculateSneaking();

            // Gets block to place
            targetBlock = PlayerUtil.getPlacePossibility(offset.getX(), offset.getY(), offset.getZ(), sameY ? (int) Math.floor(startY) : null);

            if (targetBlock == null) {
                return;
            }

            // Gets EnumFacing
            enumFacing = PlayerUtil.getEnumFacing(targetBlock, offset.getY() < 0);

            if (enumFacing == null) {
                return;
            }

            final BlockPos position = new BlockPos(targetBlock.xCoord, targetBlock.yCoord, targetBlock.zCoord);

            blockFace = position.add(enumFacing.getOffset().xCoord, enumFacing.getOffset().yCoord, enumFacing.getOffset().zCoord);

            if (blockFace == null || enumFacing == null || enumFacing.getEnumFacing() == null) {
                return;
            }

            this.calculateRotations();

            if (targetBlock == null || enumFacing == null || blockFace == null) {
                return;
            }

            if (startY - 1 != Math.floor(targetBlock.yCoord) && sameY) {
                return;
            }

            if (getComponent(Slot.class).getItemStack() == null || !(getComponent(Slot.class).getItemStack().getItem() instanceof ItemBlock)) {
                return;
            }

//            if (mc.thePlayer.offGroundTicks > 7) mc.rightClickMouse();

            if (getComponent(Slot.class).getItem() instanceof ItemBlock) {
                if (canPlace && (RayCastUtil.overBlock(enumFacing.getEnumFacing(), blockFace, rayCast.getValue().getName().equals("Strict")) || rayCast.getValue().getName().equals("Off"))) {
                    this.place();

                    // mc.rightClickDelayTimer = 0;
                    ticksOnAir = 0;

                    assert getComponent(Slot.class).getItemStack() != null;
                    if (getComponent(Slot.class).getItemStack() != null && getComponent(Slot.class).getItemStack().stackSize == 0) {
                        mc.thePlayer.inventory.mainInventory[getComponent(Slot.class).getItemIndex()] = null;
                    }
                } else if (Math.random() > 0.3 && mc.objectMouseOver != null && mc.objectMouseOver.typeOfHit != null &&
                        mc.objectMouseOver.getBlockPos().equals(blockFace) && mc.objectMouseOver.sideHit ==
                        EnumFacing.UP && rayCast.getValue().getName().equals("Strict") && !(PlayerUtil.blockRelativeToPlayer(0, -1, 0) instanceof BlockAir)) {
                    mc.rightClickMouse();
                }
            }

            // For Same Y
            if (mc.gameSettings.keyBindJump.isKeyDown() && mc.thePlayer.posY % 1 > 0.5) {
                startY = Math.floor(mc.thePlayer.posY);
            }

            if ((mc.thePlayer.posY < startY || mc.thePlayer.onGround) && !MoveUtil.isMoving()) {
                startY = Math.floor(mc.thePlayer.posY);
            }
        }
    };

    public boolean doesNotContainBlock(int down) {
        return PlayerUtil.blockRelativeToPlayer(offset.getX(), -down + offset.getY(), offset.getZ()).isReplaceable(mc.theWorld, new BlockPos(mc.thePlayer).down(down));
    }

    @EventLink
    public final Listener<MoveInputEvent> onMove = this::calculateSneaking;

    public Vec3 getHitVec() {
        /* Correct HitVec */
        Vec3 hitVec = new Vec3(blockFace.getX() + Math.random(), blockFace.getY() + Math.random(), blockFace.getZ() + Math.random());

        final MovingObjectPosition movingObjectPosition = RayCastUtil.rayCast(RotationComponent.rotations, mc.playerController.getBlockReachDistance());

        switch (enumFacing.getEnumFacing()) {
            case DOWN:
                hitVec.yCoord = blockFace.getY();
                break;

            case UP:
                hitVec.yCoord = blockFace.getY() + 1;
                break;

            case NORTH:
                hitVec.zCoord = blockFace.getZ();
                break;

            case EAST:
                hitVec.xCoord = blockFace.getX() + 1;
                break;

            case SOUTH:
                hitVec.zCoord = blockFace.getZ() + 1;
                break;

            case WEST:
                hitVec.xCoord = blockFace.getX();
                break;
        }

        if (movingObjectPosition != null && movingObjectPosition.getBlockPos() != null &&
                movingObjectPosition.hitVec != null && movingObjectPosition.getBlockPos().equals(blockFace) &&
                movingObjectPosition.sideHit == enumFacing.getEnumFacing()) {
            hitVec = movingObjectPosition.hitVec;
        }

        return hitVec;
    }

    private void place() {
        if (pause > 3) return;

        Vec3 hitVec = this.getHitVec();

        if (rayCast.getValue().getName().equals("Strict")) {
            mc.rightClickMouse();
        } else if (mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, getComponent(Slot.class).getItemStack(), blockFace, enumFacing.getEnumFacing(), hitVec)) {
            PacketUtil.send(new C0APacketAnimation());
        }
    }

    public void getRotations(final int yawOffset) {
        EntityPlayer player = mc.thePlayer;
        double difference = player.posY + player.getEyeHeight() - targetBlock.yCoord -
                0.5 - (Math.random() - 0.5) * 0.1;

        MovingObjectPosition movingObjectPosition = null;

        for (int offset = -180 + yawOffset; offset <= 180; offset += 45) {
            player.setPosition(player.posX, player.posY - difference, player.posZ);
            movingObjectPosition = RayCastUtil.rayCast(new Vector2f((float) (player.rotationYaw + (offset * 3)), 0), 4.5);
            player.setPosition(player.posX, player.posY + difference, player.posZ);

            if (movingObjectPosition == null || movingObjectPosition.hitVec == null) return;

            Vector2f rotations = RotationUtil.calculate(movingObjectPosition.hitVec);

            if (RayCastUtil.overBlock(rotations, blockFace, enumFacing.getEnumFacing())) {
                targetYaw = rotations.x;
                targetPitch = rotations.y;
                return;
            }
        }

        // Backup Rotations
        final Vector2f rotations = RotationUtil.calculate(
                new Vector3d(blockFace.getX(), blockFace.getY(), blockFace.getZ()), enumFacing.getEnumFacing());

        if (!RayCastUtil.overBlock(new Vector2f(targetYaw, targetPitch), blockFace, enumFacing.getEnumFacing())) {
            targetYaw = rotations.x;
            targetPitch = rotations.y;
        }
    }

    @EventLink
    public final Listener<StrafeEvent> onStrafe = event -> {
        this.runMode();

        if (!Objects.equals(yawOffset.getValue().getName(), "0") && !movementCorrection.getValue()) {
            MoveUtil.useDiagonalSpeed();
        }

        if (this.sameY.getValue().getName().equals("Auto Jump")) {
            if (mc.thePlayer.onGround && MoveUtil.isMoving() && mc.thePlayer.posY == startY) {
                mc.thePlayer.jump();
            }
        }
    };

    @EventLink
    public final Listener<PacketSendEvent> onPacketSend = event -> {
        Packet<?> packet = event.getPacket();

        if (packet instanceof C08PacketPlayerBlockPlacement) {
            C08PacketPlayerBlockPlacement c08PacketPlayerBlockPlacement = (C08PacketPlayerBlockPlacement) packet;

            if (!c08PacketPlayerBlockPlacement.getPosition().equalsVector(new Vector3d(-1, -1, -1))) {
                placements--;
            }
        }
    };
}
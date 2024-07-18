package com.alan.clients.module.impl.movement.speed;

import com.alan.clients.component.impl.player.BadPacketsComponent;
import com.alan.clients.component.impl.player.Slot;
import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.input.MoveInputEvent;
import com.alan.clients.event.impl.motion.JumpEvent;
import com.alan.clients.event.impl.motion.PostStrafeEvent;
import com.alan.clients.event.impl.motion.PreMotionEvent;
import com.alan.clients.event.impl.motion.StrafeEvent;
import com.alan.clients.event.impl.other.StepEvent;
import com.alan.clients.module.impl.movement.Speed;
import com.alan.clients.util.chat.ChatUtil;
import com.alan.clients.util.packet.PacketUtil;
import com.alan.clients.util.player.MoveUtil;
import com.alan.clients.util.player.PlayerUtil;
import com.alan.clients.util.player.SlotUtil;
import com.alan.clients.value.Mode;
import com.alan.clients.value.impl.ModeValue;
import com.alan.clients.value.impl.SubMode;
import net.minecraft.block.BlockAir;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public final class VulcanSpeed extends Mode<Speed> {

    ModeValue mode = new ModeValue("Mode", this)
            .add(new SubMode("BHop"))
            .add(new SubMode("YPort"))
            .add(new SubMode("Funny"))
            .add(new SubMode("Use Disabler"))
            .add(new SubMode("Ground"))
            .add(new SubMode("Thing"))
            .setDefault("BHop");

    private int lastRightClick;
    private int stage;
    private double moveSpeed;

    private int jump = 0;

    public VulcanSpeed(String name, Speed parent) {
        super(name, parent);
    }

    @EventLink
    public final Listener<JumpEvent> onJumpEvent = event -> {
        jump++;
    };

    @Override
    public void onDisable() {
        if (mode.getValue().getName().equals("Ground")) {
            MoveUtil.strafe(MoveUtil.getAllowedHorizontalDistance() * 0.6 - 0.02);
        }
    }

    @EventLink
    public final Listener<StepEvent> onStep = event -> {
        switch (mode.getValue().getName()) {
            case "BHop":
                MoveUtil.strafe(0.22);

        }
    };

    @EventLink
    public final Listener<PreMotionEvent> onPreMotion = event -> {

        if (mode.getValue().getName().equals("Ground")) {

            if (MoveUtil.isMoving() && mc.thePlayer.onGround) {
                double speed = Math.hypot(mc.thePlayer.motionX, mc.thePlayer.motionZ);

                boolean boost = (mc.thePlayer.isPotionActive(Potion.moveSpeed) ? mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).amplifier + 1 : 0) >= 2;
                switch (stage) {
                    case 1:
                        moveSpeed = 0.58f;
                        speed = boost ? speed + 0.2 : 0.487;
                        event.setOnGround(true);
                        break;
                    case 2:
                        speed = boost ? speed * 0.71 : .197;
                        moveSpeed -= 0.0784f;
                        event.setOnGround(false);
                        break;
                    default:
                        stage = 0;
                        speed /= boost ? 0.64 : 0.58;
                        event.setOnGround(true);
                        break;
                }

                MoveUtil.strafe(speed);
                stage++;
                event.setPosY(event.getPosY() + moveSpeed);
            } else {
                stage = 0;
            }
        }
    };

    @EventLink
    public final Listener<PostStrafeEvent> onPostStrafe = event -> {
        if (mode.getValue().getName().equals("Use Disabler")) {
            if (mc.thePlayer.onGround) {
                mc.thePlayer.motionY = 0.42f;
            }

            int speed = mc.thePlayer.isPotionActive(Potion.moveSpeed) ? mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).amplifier + 1 : 0;
            boolean nextGround = mc.thePlayer.offGroundTicks == 11;
            boolean ground = mc.thePlayer.onGround;

            switch (speed) {
                case 0:
                    MoveUtil.strafe(nextGround ? 0.3225 : ground ? 0.6505 : 0.36);
                    break;

                case 1:
                    MoveUtil.strafe(nextGround ? 0.3975 : ground ? 0.7255 : 0.4275);
                    break;

                default:
                    MoveUtil.strafe(nextGround ? 0.4725 : ground ? 0.8005 : 0.495);
                    break;
            }
        }
    };

    @EventLink
    public final Listener<StrafeEvent> onStrafe = event -> {
        double hypotenuse = Math.sqrt(mc.thePlayer.motionX * mc.thePlayer.motionX + mc.thePlayer.motionZ * mc.thePlayer.motionZ);

        if (!MoveUtil.isMoving()) {
            return;
        }

        switch (mode.getValue().getName()) {

            case "Thing":
                if (mc.thePlayer.onGround) {
                    mc.thePlayer.motionY = 0.42f;
                    event.setSpeed(0.65);
                }
                switch (mc.thePlayer.offGroundTicks) {

                    case 1:
                        MoveUtil.strafe(0.335);
                        break;
                    case 2:

                        MoveUtil.strafe(0.31);
                        break;

                    case 6:
                        if (PlayerUtil.blockRelativeToPlayer(0, mc.thePlayer.posY + 1, 0) instanceof BlockAir) {
                            mc.thePlayer.motionY = MoveUtil.predictedMotion(mc.thePlayer.motionY, 4);
                        }

                        break;

                }
                break;
            case "YPort":
                if (mc.thePlayer.offGroundTicks > 1 && !(mc.thePlayer.offGroundTicks == 9) && !(mc.thePlayer.offGroundTicks == 3) && !(mc.thePlayer.offGroundTicks == 8) && !(mc.thePlayer.offGroundTicks == 2)) {
                    if (hypotenuse < 0.21 && hypotenuse >= 0 || mc.thePlayer.motionX == 0 || mc.thePlayer.motionZ == 0) {
                        MoveUtil.strafe(0.22);

                    }

                }

                if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
                    if (hypotenuse < 0.21 && hypotenuse >= 0 || mc.thePlayer.motionX == 0 || mc.thePlayer.motionZ == 0) {
                        MoveUtil.strafe(0.22);
                    }
                }

                switch (mc.thePlayer.offGroundTicks) {

                    case 5:
                        if ((jump % 4 == 1)) {
                            MoveUtil.strafe();
                        }
                        break;
                    case 0:
                        mc.thePlayer.jump();
                        if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
                            MoveUtil.strafe((.06 * (1 + (mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier())) + 0.485));
                        } else {
                            MoveUtil.strafe(0.485);
                        }
                        break;

                    case 9:
                        if (!(PlayerUtil.blockRelativeToPlayer(0, mc.thePlayer.motionY,
                                0) instanceof BlockAir)) {
                            MoveUtil.strafe();
                        }
                        MoveUtil.strafe();
                        break;

                    case 8:

                        MoveUtil.strafe();
                        break;
                    case 2:
                        if (!(jump % 4 == 1) && !(mc.thePlayer.isCollidedVertically)) {
                            mc.thePlayer.motionY = MoveUtil.predictedMotion(mc.thePlayer.motionY, 2);
                        }
                        break;

                    case 1:
                        MoveUtil.strafe();
                        break;

                    case 4:
                        if (jump % 4 == 1 || mc.thePlayer.isCollidedVertically) {
                            mc.thePlayer.motionY = MoveUtil.predictedMotion(mc.thePlayer.motionY, 4);
                        } else {

                        }
                        break;
                }
                break;

            case "BHop":
                if (!(mc.thePlayer.offGroundTicks == 1) && !(mc.thePlayer.offGroundTicks == 11)) {
                    if (hypotenuse < 0.21 && hypotenuse >= 0 || mc.thePlayer.motionX == 0 || mc.thePlayer.motionZ == 0) {

                        MoveUtil.strafe(0.22);

                    }

                }

                if (mc.thePlayer.isPotionActive(Potion.moveSpeed) && !(mc.thePlayer.offGroundTicks < 1) && !(mc.thePlayer.offGroundTicks == 11)) {
                    if (hypotenuse < 0.21 && hypotenuse >= 0 || mc.thePlayer.motionX == 0 || mc.thePlayer.motionZ == 0) {
                        MoveUtil.strafe(0.22);
                    }
                }
                switch (mc.thePlayer.offGroundTicks) {

                    case 0:
                        mc.thePlayer.jump();

                        if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
                            MoveUtil.strafe((.06 * (1 + (mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier())) + 0.487));
                        } else {
                            MoveUtil.strafe(0.487);
                        }
                        break;

                    case 9:
                        if (!(PlayerUtil.blockRelativeToPlayer(0, mc.thePlayer.motionY,
                                0) instanceof BlockAir)) {
                            MoveUtil.strafe();
                        }

                        break;
                    case 1:

                        MoveUtil.strafe();

                        break;
                    case 4:
                        mc.thePlayer.motionY = MoveUtil.predictedMotion(mc.thePlayer.motionY, 2);
                        break;
                    case 10:
                        MoveUtil.strafe(0.335);
                        break;

                }
                break;

            case "Funny":
                int blockSlot = SlotUtil.findBlock();

                if (blockSlot == -1) {
                    ChatUtil.display("This speed requires a block to be in your HotBar.");
                    return;
                }

                if (!BadPacketsComponent.bad(false, true, false, false, false)) {
                    getComponent(Slot.class).setSlot(blockSlot);
                }

                int speed = mc.thePlayer.isPotionActive(Potion.moveSpeed) ? mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).amplifier + 1 : 0;

                if (!BadPacketsComponent.bad(false, true, false, false, false) && lastRightClick < mc.thePlayer.ticksExisted) {
                    lastRightClick = mc.thePlayer.ticksExisted + 2;
                    PacketUtil.send(new C08PacketPlayerBlockPlacement(new BlockPos(mc.thePlayer).down(), EnumFacing.UP.getIndex(), getComponent(Slot.class).getItemStack(), 0.0F, 1.0F, 0.0F));
                }

                switch (mc.thePlayer.offGroundTicks) {
                    case 0:
                        switch (speed) {
                            case 0:
                                MoveUtil.strafe(0.55);
                                break;
                            case 1:
                                MoveUtil.strafe(0.65 - 0.11);
                                break;
                            default:
                                MoveUtil.strafe(0.85 - 0.18);
                                break;
                        }

                        mc.thePlayer.motionY = 0.2f;
                        break;

                    case 1:
                        mc.thePlayer.motionY = MoveUtil.HEAD_HITTER_MOTION;

                        switch (speed) {
                            case 0:
                                MoveUtil.strafe(0.45 - 0.02);
                                break;
                            case 1:
                                MoveUtil.strafe(0.6 - 0.11);
                                break;
                            default:
                                MoveUtil.strafe(0.75 - 0.18);
                                break;
                        }
                        break;

                    case 2:
                        switch (speed) {
                            case 0:
                                MoveUtil.strafe(0.4 - 0.03);
                                break;
                            case 1:
                                MoveUtil.strafe(0.55 - 0.11);
                                break;
                            default:
                                MoveUtil.strafe(0.65 - 0.18);
                                break;
                        }
                        break;
                }
                break;
        }
    };

    @EventLink
    public final Listener<MoveInputEvent> onMove = event -> {
        event.setJump(false);
    };
}


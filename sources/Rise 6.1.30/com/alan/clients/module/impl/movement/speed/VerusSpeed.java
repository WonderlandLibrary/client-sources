package com.alan.clients.module.impl.movement.speed;

import com.alan.clients.component.impl.player.BadPacketsComponent;
import com.alan.clients.component.impl.player.RotationComponent;
import com.alan.clients.component.impl.player.Slot;
import com.alan.clients.component.impl.player.rotationcomponent.MovementFix;
import com.alan.clients.event.Listener;
import com.alan.clients.event.Priorities;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.input.MoveInputEvent;
import com.alan.clients.event.impl.motion.PreMotionEvent;
import com.alan.clients.event.impl.motion.PreUpdateEvent;
import com.alan.clients.event.impl.motion.StrafeEvent;
import com.alan.clients.event.impl.other.AttackEvent;
import com.alan.clients.event.impl.other.MoveEvent;
import com.alan.clients.module.impl.movement.Speed;
import com.alan.clients.util.chat.ChatUtil;
import com.alan.clients.util.packet.PacketUtil;
import com.alan.clients.util.player.MoveUtil;
import com.alan.clients.util.player.SlotUtil;
import com.alan.clients.util.vector.Vector2f;
import com.alan.clients.value.Mode;
import com.alan.clients.value.impl.ModeValue;
import com.alan.clients.value.impl.SubMode;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

// -0.0784000015258789
// -0.09800000190734864
public class VerusSpeed extends Mode<Speed> {

    private final ModeValue mode = new ModeValue("Sub-Mode", this)
            .add(new SubMode("Hop"))
            .add(new SubMode("yPort"))
            .add(new SubMode("Fast"))
            .add(new SubMode("LowHop"))
            .setDefault("Hop");

    private int ticks;
    private boolean bool, lastStopped;
    private int lastRightClick;
    private boolean reset;
    private double speed;

    private float forward, strafe;
    private boolean flag = true;
    private boolean attack = false;
    private int attackTicksRemaining = 0;

    public VerusSpeed(String name, Speed parent) {
        super(name, parent);
    }

    @Override
    public void onEnable() {
        flag = true;
        if (mode.getValue().getName().equals("yPort")) {
            if (!BadPacketsComponent.bad(false, true, false, false, false)) {
                PacketUtil.send(new C08PacketPlayerBlockPlacement(new BlockPos(mc.thePlayer).down(), EnumFacing.UP.getIndex(), getComponent(Slot.class).getItemStack(), 0.0F, 1.0F, 0.0F));
            }
        }

        bool = lastStopped = false;
        ticks = 0;
    }

    @Override
    public void onDisable() {
        if (mode.getValue().getName().equals("yPort")) {
            if (!BadPacketsComponent.bad(false, true, false, false, false)) {
                PacketUtil.send(new C08PacketPlayerBlockPlacement(new BlockPos(mc.thePlayer).down(), EnumFacing.UP.getIndex(), getComponent(Slot.class).getItemStack(), 0.0F, 1.0F, 0.0F));
            }
        }
    }

    @EventLink
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        if (!MoveUtil.isMoving()) return;

        switch (mode.getValue().getName()) {


            case "Fast": {
                if (!(mc.thePlayer.moveForward > 0)) {
                    lastStopped = true;
                    return;
                }

                if (mc.thePlayer.onGround) {
                    if (MoveUtil.speed() > 0.3) lastStopped = false;

                    event.setOnGround(true);
                    MoveUtil.strafe(0.41);
                    mc.thePlayer.motionY = 0.42F;
                    mc.timer.timerSpeed = 2.1F;
                    ticks = 0;
                } else {
                    if (ticks >= 10) {
                        bool = true;
                        MoveUtil.strafe(0.35F);
                        return;
                    }

                    if (bool) {
                        if (lastStopped) {
                            MoveUtil.strafe(0.2);
                        } else if (ticks <= 1) {
                            MoveUtil.strafe(0.35F);
                        } else {
                            MoveUtil.strafe(0.69F - (ticks - 2F) * 0.019F);
                        }
                    }

                    mc.thePlayer.motionY = 0F;
                    mc.timer.timerSpeed = 0.9F;

                    event.setOnGround(true);
                    mc.thePlayer.onGround = true;
                }

                break;
            }
        }
        ticks++;
    };

    @EventLink
    public final Listener<StrafeEvent> onStrafe = event -> {
        switch (mode.getValue().getName()) {
            case "Hop": {

                final double base = MoveUtil.getAllowedHorizontalDistance();

                if (MoveUtil.isMoving()) {

                    switch (mc.thePlayer.offGroundTicks) {
                        case 0:
                            float jumpMotion = 0.39F;

                            float motion = mc.thePlayer.isCollidedHorizontally ? 0.42F : jumpMotion == 0.4f ? jumpMotion : 0.42f;
                            mc.thePlayer.motionY = MoveUtil.jumpBoostMotion(motion);
                            speed = base * 1.76;
                            break;

                        case 1:
                            speed -= (0.439D * (speed - base));
                            break;

                        default:
                            speed -= speed / MoveUtil.BUNNY_FRICTION;
                            break;
                    }

                    mc.timer.timerSpeed = 1.0F;
                    reset = false;
                } else if (!reset) {
                    speed = MoveUtil.getAllowedHorizontalDistance();
                    mc.timer.timerSpeed = 1;
                    reset = true;
                }

                event.setSpeed(Math.max(speed, base), Math.random() / 2000);
                if (mc.thePlayer.ticksSincePlayerVelocity <= 20) {
                    event.setSpeed(speed * 2);
                }

                if (mc.thePlayer.isInWater()) {
                    event.setSpeed(.4);

                }

                if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
                    event.setSpeed(.93 * (Math.max(speed, base)), 1);
                    if (mc.thePlayer.isCollidedHorizontally) {
                        speed = MoveUtil.getAllowedHorizontalDistance();
                    }
                    if (mc.thePlayer.ticksSincePlayerVelocity <= 20) {
                        event.setSpeed(speed * 2);
                    }

                }
            }
        }

        if (mode.getValue().getName().equals("yPort")) {

            if (attackTicksRemaining > 0) {
                attackTicksRemaining--;
            } else {
                attack = false;
            }

            if (mc.gameSettings.keyBindJump.isKeyDown() && mc.thePlayer.onGround) {

                mc.thePlayer.motionY = MoveUtil.JUMP_HEIGHT;

            }
            if (!(mc.gameSettings.keyBindJump.isKeyDown()) && mc.thePlayer.offGroundTicks < 2) {


                int blockSlot = SlotUtil.findBlock();
                if (blockSlot == -1) {
                    ChatUtil.display("This speed requires a block to be in your HotBar.");
                    return;
                }

                if(flag){

                        PacketUtil.send(new C08PacketPlayerBlockPlacement(new BlockPos(mc.thePlayer).down(), EnumFacing.UP.getIndex(), getComponent(Slot.class).getItemStack(), 0.0F, 1.0F, 0.0F));
                        getComponent(Slot.class).setSlot(blockSlot);
                        flag = false;

                }

                if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
                    MoveUtil.strafe(((.17 * (1 + (mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier()))) + 0.46));
                } else {
                    MoveUtil.strafe(.46);
                }
                if (!BadPacketsComponent.bad(false, true, true, false, false)) {
                    getComponent(Slot.class).setSlot(blockSlot);
                }

                if (!BadPacketsComponent.bad(false, true, false, false, false)  & attack) {
                    lastRightClick = mc.thePlayer.ticksExisted + 2;
                    PacketUtil.send(new C08PacketPlayerBlockPlacement(new BlockPos(mc.thePlayer).down(), EnumFacing.UP.getIndex(), getComponent(Slot.class).getItemStack(), 0.0F, 1.0F, 0.0F));
                }

                if (!BadPacketsComponent.bad(false, true, false, false, false)  & mc.thePlayer.ticksExisted % 10 == 1) {
                    lastRightClick = mc.thePlayer.ticksExisted + 2;
                    PacketUtil.send(new C08PacketPlayerBlockPlacement(new BlockPos(mc.thePlayer).down(), EnumFacing.UP.getIndex(), getComponent(Slot.class).getItemStack(), 0.0F, 1.0F, 0.0F));
                }
                if (mc.thePlayer.onGround) {
                    mc.thePlayer.motionY = 0.0;
                    if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
                        MoveUtil.strafe(((.09 * (1 + (mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier()))) + 0.55));
                    } else {
                        MoveUtil.strafe(.55);
                    }

                }
            } else {
                flag = true;
                if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
                    MoveUtil.strafe(((.04 * (1 + (mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier()))) + 0.41));
                }else {
                    event.setSpeed(.41);
                }
            }

        }

};

        @EventLink
        public final Listener<MoveEvent> onMove = event -> {
            if (!MoveUtil.isMoving()) return;

            if (mode.getValue().getName().equals("LowHop")) {
                if (mc.thePlayer.onGround) {
                    event.setPosY(0.42F);
                    MoveUtil.strafe(0.69F + MoveUtil.speedPotionAmp(0.1));
                    mc.thePlayer.motionY = 0F;
                } else {
                    MoveUtil.strafe(0.41F + MoveUtil.speedPotionAmp(0.055));
                }
                if ( mc.thePlayer.ticksSincePlayerVelocity <= 20) {
                    MoveUtil.strafe(1F + MoveUtil.speedPotionAmp(0.055));
                }

                mc.thePlayer.setSprinting(true);
                mc.thePlayer.omniSprint = true;
            }
        };

        @EventLink
        public final Listener<MoveInputEvent> onMoveInput = event -> {
            event.setJump(false);
            event.setSneak(false);
            forward = event.getForward();
            strafe = event.getStrafe();

        };
    @EventLink
    public final Listener<AttackEvent> onAttack = event -> {
        attack = true;
        attackTicksRemaining = 10;
    };

    @EventLink(value = Priorities.LOW)
    Listener<PreUpdateEvent> onPreUpdate = event -> {
        if (mode.getValue().getName().equals("Hop")) {
            RotationComponent.setRotations(new Vector2f((float) Math.toDegrees(MoveUtil.direction(forward, strafe)), mc.thePlayer.rotationPitch),
                    2, MovementFix.BACKWARDS_SPRINT);
        }
    };
}


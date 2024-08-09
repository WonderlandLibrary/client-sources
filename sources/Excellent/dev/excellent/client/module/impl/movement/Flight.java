package dev.excellent.client.module.impl.movement;

import dev.excellent.api.event.impl.player.MotionEvent;
import dev.excellent.api.event.impl.player.MoveInputEvent;
import dev.excellent.api.event.impl.player.UpdateEvent;
import dev.excellent.api.event.impl.server.PacketEvent;
import dev.excellent.api.interfaces.event.Listener;
import dev.excellent.client.module.api.Category;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.api.ModuleInfo;
import dev.excellent.impl.util.pattern.Singleton;
import dev.excellent.impl.util.player.MoveUtil;
import dev.excellent.impl.value.impl.BooleanValue;
import dev.excellent.impl.value.impl.ModeValue;
import dev.excellent.impl.value.impl.NumberValue;
import dev.excellent.impl.value.mode.SubMode;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.server.SEntityMetadataPacket;
import net.minecraft.network.play.server.SPlayerLookPacket;

@ModuleInfo(name = "Flight", description = "Позволяет вам летать.", category = Category.MOVEMENT)
public class Flight extends Module {
    private boolean wasAllowed = false;
    public static Singleton<Flight> singleton = Singleton.create(() -> Module.link(Flight.class));

    private final ModeValue mode = new ModeValue("Режим", this)
            .add(
                    new SubMode("Ванильный"),
                    new SubMode("FunTime"),
                    new SubMode("Моушен"),
                    new SubMode("Глайд")

            );

    @Override
    public String getSuffix() {
        return mode.getValue().getName();
    }

    private final NumberValue ySpeed = new NumberValue("Скорость по Y", this, 0.4, 0.1, 4, 0.1, () -> !mode.is("Моушен"));
    private final NumberValue horizontalSpeed = new NumberValue("Скорость по X, Z", this, 0.4, 0.1, 10, 0.1, () -> mode.is("Ванильный"));
    public final BooleanValue disableOnFlag = new BooleanValue("Вырубать при флаге", this, false);

    private final Listener<UpdateEvent> onUpdate = event -> {
        if (mode.is("FunTime")) {
            handleUpdate(event);
        }

    };
    private final Listener<MotionEvent> onMotion = event -> {
        if (mode.is("Моушен")) {

            mc.player.setMotion(0, 0, 0);

            if (mc.gameSettings.keyBindSneak.isKeyDown()) {
                mc.player.motion.y = -ySpeed.getValue().floatValue();
            }

            if (mc.gameSettings.keyBindJump.isKeyDown()) {
                mc.player.motion.y = ySpeed.getValue().floatValue();
            }

            MoveUtil.setSpeed(horizontalSpeed.getValue().floatValue() / 10F * 9.952D);
        } else if (mode.is("Глайд")) {
            if (mc.player.fallDistance != 0 && !mc.player.isOnGround()) {
                boolean sneak = mc.player.movementInput.sneaking;
                boolean hopUp = mc.player.movementInput.jump && mc.player.ticksExisted % 2 == 0 && MoveUtil.speedSqrt() > .2499946;
                if (!sneak)
                    mc.player.motion.y = hopUp ? .42D : 0.D;
                if (!hopUp && !sneak)
                    mc.player.setPosition(mc.player.getPosX(), mc.player.getPosY() - 1E-5D / 2D, mc.player.getPosZ());
                if (MoveUtil.isMoving())
                    MoveUtil.setSpeed(horizontalSpeed.getValue().floatValue() / 10F * 9.952D);
                else {
                    mc.player.motion.x /= 1.8D;
                    mc.player.motion.z /= 1.8D;
                }
                if (mc.player.fallDistance < 0.2)
                    mc.player.fallDistance = 0.2F;
            } else if (mc.player.isOnGround()) {
                MoveUtil.setSpeed(0);
                mc.player.jumpMovementFactor = 0;
                mc.player.jump();
            }
        } else if (mode.is("FunTime")) {
            handleMotion(event);
        }
    };

    @Override
    protected void onEnable() {
        if (mode.is("FunTime")) {
        }
        if (mode.is("Ванильный")) {
            this.wasAllowed = mc.player.abilities.allowFlying;
            mc.player.abilities.isFlying = true;
            mc.player.abilities.allowFlying = true;
        }
    }

    @Override
    protected void onDisable() {
        if (mode.is("FunTime")) {

        }
        if (mode.is("Ванильный")) {
            mc.player.abilities.isFlying = false;
            mc.player.abilities.allowFlying = wasAllowed;
        }
        if (mode.is("Глайд")) {
            MoveUtil.setSpeed(horizontalSpeed.getValue().floatValue() / 10F * 9.952D / 4D);
            mc.player.jumpMovementFactor /= 2F;
        }
    }

    private final Listener<MoveInputEvent> onMove = event -> {
        if (mode.is("FunTime")) {

        }
    };
    private final Listener<PacketEvent> onPacket = event -> {
        if (mode.is("FunTime")) {
            handlePacket(event);
        }
        if (event.isReceive()) {
            if (event.getPacket() instanceof SPlayerLookPacket && disableOnFlag.getValue()) {
                this.toggle();
            }
        }
    };


    private void handlePacket(PacketEvent event) {
        IPacket<?> packet = event.getPacket();
        if (event.isReceive()) {
            if (packet instanceof SEntityMetadataPacket wrapper) {
                if (wrapper.getEntityId() == mc.player.getEntityId()) {
                    event.cancel();
                }
            }
        }
    }

    private void handleMotion(MotionEvent event) {

//        if (mc.player.getPose().equals(Pose.SWIMMING)) {
//        double forward = 1;
//        double strafe = 0;
//        double moveSpeed = 1;
//        float pitch = mc.player.rotationPitch;
//        float yaw = mc.player.rotationYaw;
//        double my = -Math.sin(Math.toRadians(pitch));
//        double mx = Math.cos(Math.toRadians((yaw + 90.0F)));
//        double mz = Math.sin(Math.toRadians((yaw + 90.0F)));
//
//        mc.player.getMotion().y = forward * moveSpeed * my;
//        mc.player.getMotion().x = forward * moveSpeed * mx + strafe * moveSpeed * mz;
//        mc.player.getMotion().z = forward * moveSpeed * mz - strafe * moveSpeed * mx;
//        toggle();
//        }
//        double forward = 1;
//        double strafe = 0;
//        double moveSpeed = 0.9;
//        float pitch = mc.player.rotationPitch;
//        float yaw = mc.player.rotationYaw;
//        double my = -Math.sin(Math.toRadians(pitch));
//        double mx = Math.cos(Math.toRadians((yaw + 90.0F)));
//        double mz = Math.sin(Math.toRadians((yaw + 90.0F)));
//
//        mc.player.getMotion().y = forward * moveSpeed * my;
//        mc.player.getMotion().x = forward * moveSpeed * mx + strafe * moveSpeed * mz;
//        mc.player.getMotion().z = forward * moveSpeed * mz - strafe * moveSpeed * mx;
    }

    private void handleUpdate(UpdateEvent event) {
    }
}

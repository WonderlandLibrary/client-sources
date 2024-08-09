package dev.excellent.client.module.impl.movement;

import dev.excellent.api.event.impl.player.EntityInteractEvent;
import dev.excellent.api.event.impl.player.HandInteractionEvent;
import dev.excellent.api.event.impl.player.MotionEvent;
import dev.excellent.api.event.impl.player.UpdateEvent;
import dev.excellent.api.interfaces.event.Listener;
import dev.excellent.client.module.api.Category;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.api.ModuleInfo;
import dev.excellent.impl.util.keyboard.Keyboard;
import dev.excellent.impl.util.player.MoveUtil;
import dev.excellent.impl.value.impl.BooleanValue;
import dev.excellent.impl.value.impl.NumberValue;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.item.FishingRodItem;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;

@ModuleInfo(name = "Boat Fly", description = "Позволяет летать на лодке", category = Category.MOVEMENT)
public class BoatFly extends Module {

    private final BooleanValue usePlayerRotation = new BooleanValue("Исп ротацию игрока", this, false);
    private final BooleanValue changeSpeed = new BooleanValue("Изменять скорость", this, false);
    private final NumberValue hSpeed = new NumberValue("Скорость по XZ", this, 0.3F, 0.1F, 10F, 0.1F, () -> !changeSpeed.getValue());
    private final NumberValue ySpeed = new NumberValue("Скорость по Y", this, 0.3F, 0.1F, 10F, 0.1F, () -> !changeSpeed.getValue());

    @Override
    protected void onEnable() {
        super.onEnable();
    }

    @Override
    protected void onDisable() {
        super.onDisable();
    }

    private final Listener<HandInteractionEvent> onHandInteract = event -> {
        if (mc.player.getRidingEntity() instanceof BoatEntity && mc.player.fishingBobber != null) {
            event.cancel();
        }
    };
    private final Listener<EntityInteractEvent> onEntityInteract = event -> {
        boolean handItemIsRod = ((mc.player.getHeldItemOffhand().getItem() instanceof FishingRodItem)
                || (mc.player.getHeldItemMainhand().getItem() instanceof FishingRodItem));
        if (handItemIsRod
                && mc.player.fishingBobber == null
                && mc.player.getRidingEntity() == null
                && (event.getTarget() instanceof BoatEntity)) {
            event.cancel();
        }
    };
    private final Listener<MotionEvent> onMotion = event -> {
        if (mc.player.getRidingEntity() == null && mc.player.fishingBobber != null && mc.player.fishingBobber.getCaughtEntity() instanceof BoatEntity caughtBoat) {
            mc.player.getDistance(caughtBoat);
        }
    };
    private final Listener<UpdateEvent> onUpdate = event -> {
        if (mc.player.getRidingEntity() == null) {
            return;
        }
        final Entity boat = mc.player.getRidingEntity();
        final Vector3d velocity = boat.getMotion();
        final double RADIANS_PER_DEGREE = Math.PI / 180D;
        if (boat instanceof BoatEntity) {
            if (mc.player.isRidingSameEntity(boat)) {

                double motionX = velocity.x;
                double motionY = 0.04D;
                double motionZ = velocity.z;

                if (mc.gameSettings.keyBindJump.isKeyDown()) {
                    if (changeSpeed.getValue()) {
                        motionY += ySpeed.getValue().doubleValue();
                    } else {
                        motionY += 0.185D;
                        if (!MoveUtil.isMoving()) {
                            motionY += 0.36D;
                        }
                    }
                }
                if (mc.currentScreen == null && Keyboard.isKeyDown(mc.gameSettings.keyBindSprint.getKeyCode().getKeyCode())) {
                    if (changeSpeed.getValue()) {
                        motionY -= ySpeed.getValue().doubleValue();
                    } else {
                        motionY -= 0.185D;
                        if (!MoveUtil.isMoving()) {
                            motionY -= 0.16D;
                        }
                    }
                }
                double yaw = boat.rotationYaw + (boat.prevRotationYaw - boat.rotationYaw) * mc.getRenderPartialTicks();

                if (changeSpeed.getValue() && mc.gameSettings.keyBindForward.isKeyDown()) {
                    double speed = hSpeed.getValue().doubleValue() * 2;
                    float yawRad = (float) (yaw * RADIANS_PER_DEGREE);
                    motionX = MathHelper.sin(-yawRad) * speed;
                    motionZ = MathHelper.cos(yawRad) * speed;
                }
                if (usePlayerRotation.getValue()) {
                    boat.rotationYaw = mc.player.rotationYaw;
                }
                if (MoveUtil.isMoving()) {
                    boat.setVelocity(motionX, motionY, motionZ);
                } else {
                    boat.setVelocity(0, motionY, 0);
                }
            }
        }
    };

}

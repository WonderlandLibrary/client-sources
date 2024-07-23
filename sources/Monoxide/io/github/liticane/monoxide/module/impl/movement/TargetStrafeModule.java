package io.github.liticane.monoxide.module.impl.movement;

import io.github.liticane.monoxide.module.ModuleManager;
import io.github.liticane.monoxide.module.api.Module;
import io.github.liticane.monoxide.module.api.data.ModuleData;
import io.github.liticane.monoxide.module.api.ModuleCategory;
import io.github.liticane.monoxide.module.impl.combat.KillAuraModule;
import io.github.liticane.monoxide.value.impl.BooleanValue;
import io.github.liticane.monoxide.value.impl.NumberValue;
import io.github.liticane.monoxide.listener.event.minecraft.player.movement.MovePlayerEvent;
import io.github.liticane.monoxide.listener.event.minecraft.player.movement.UpdateEvent;
import io.github.liticane.monoxide.listener.radbus.Listen;
import io.github.liticane.monoxide.util.interfaces.Methods;
import io.github.liticane.monoxide.util.player.movement.MoveUtil;
import io.github.liticane.monoxide.util.player.rotation.RotationUtil;

@ModuleData(name = "TargetStrafe", description = "Strafe around entities", category = ModuleCategory.MOVEMENT)
public class TargetStrafeModule extends Module {
    private final NumberValue<Float> strafeSize = new NumberValue<Float>("Size", this, 2.5F, 1F, 6F, 1);
    private final BooleanValue jumpOnly = new BooleanValue("Jump Only", this, false),
            controllable = new BooleanValue("Controllable", this, true),
            voidCheck = new BooleanValue("Void Check", this, true),
            collideCheck = new BooleanValue("Collide Check", this, true);

    private int direction = 1;

    @Listen
    public void onUpdate(UpdateEvent updateEvent) {
        if (KillAuraModule.currentTarget != null) {
            if (controllable.getValue()) {
                if(Methods.mc.gameSettings.keyBindLeft.isKeyDown()) {
                    direction = 1;
                }

                if(Methods.mc.gameSettings.keyBindRight.isKeyDown()) {
                    direction = -1;
                }
            }

            if (isVoidBelow(Methods.mc.thePlayer.getPosition()) && voidCheck.getValue()) {
                direction = -direction;
            }

            if (collideCheck.getValue() && Methods.mc.thePlayer.isCollidedHorizontally) {
                direction = -direction;
            }
        }
    }

    @Listen
    public void onMove(MovePlayerEvent movePlayerEvent) {
        KillAuraModule killAura = ModuleManager.getInstance().getModule("KillAura");

        if(killAura.isEnabled() && KillAuraModule.currentTarget != null) {
            if(jumpOnly.getValue() && !isKeyDown(Methods.mc.gameSettings.keyBindJump.getKeyCode())) {
                return;
            }
            float yaw = RotationUtil.getRotation(KillAuraModule.currentTarget, "", 0, false, false, 0, 0, 0, 0, false, 180, 180, 180, 180, false, false)[0];

            MoveUtil.setMoveSpeed(MoveUtil.getSpeed(), yaw, direction, (Methods.mc.thePlayer.getDistanceToEntity(KillAuraModule.currentTarget) <= strafeSize.getValue() - 1) ? 0 : 1);
        }
    }

}

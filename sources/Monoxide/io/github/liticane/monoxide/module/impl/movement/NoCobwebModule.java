package io.github.liticane.monoxide.module.impl.movement;

import io.github.liticane.monoxide.module.api.Module;
import io.github.liticane.monoxide.module.api.data.ModuleData;
import io.github.liticane.monoxide.module.api.ModuleCategory;
import io.github.liticane.monoxide.value.impl.ModeValue;
import io.github.liticane.monoxide.listener.event.minecraft.input.InputEvent;
import io.github.liticane.monoxide.listener.event.minecraft.player.movement.UpdateEvent;
import io.github.liticane.monoxide.listener.radbus.Listen;

@ModuleData(name = "NoCobweb", description = "Prevents slowdown in Webs", category = ModuleCategory.MOVEMENT)
public class NoCobwebModule extends Module {
    private final ModeValue mode = new ModeValue("Mode", this, new String[]{"Vanilla", "Intave", "Fast Fall", "AAC 4", "AAC 5"});

    @Override
    public String getSuffix() {
    	return mode.getValue();
    }
    
    @Listen
    public final void onUpdate(UpdateEvent updateEvent) {
        if (!mc.thePlayer.isInWeb) {
            return;
        }

        switch (mode.getValue()) {
            case "Vanilla":
                mc.thePlayer.isInWeb = false;
                break;
            case "Fast Fall":
                if (mc.thePlayer.onGround) mc.thePlayer.jump();
                if (mc.thePlayer.motionY > 0f) {
                    mc.thePlayer.motionY -= mc.thePlayer.motionY * 2;
                }
                break;
            case "AAC 4":
                mc.timer.timerSpeed = 0.99F;
                mc.thePlayer.jumpMovementFactor = 0.02958f;
                mc.thePlayer.motionY -= 0.00775;
                if (mc.thePlayer.onGround) {
                    mc.thePlayer.motionY = 0.4050;
                    mc.timer.timerSpeed = 1.35F;
                }
                break;
            case "AAC 5":
                mc.thePlayer.jumpMovementFactor = 0.42f;
                if (mc.thePlayer.onGround) {
                    mc.thePlayer.jump();
                }
                break;
            case "Intave":
                if (mc.thePlayer.movementInput.moveStrafe == 0.0F && mc.gameSettings.keyBindForward.isKeyDown() && mc.thePlayer.isCollidedVertically) {
                    mc.thePlayer.jumpMovementFactor = 0.74F;
                } else {
                    mc.thePlayer.jumpMovementFactor = 0.2F;
                    mc.thePlayer.onGround = true;
                }
                break;
        }
    }

    @Listen
    public final void onMove(InputEvent moveButtonEvent) {
        if (mode.is("AAC 4") && mc.thePlayer.isInWeb) {
            moveButtonEvent.setJumping(false);
        }
    }

}

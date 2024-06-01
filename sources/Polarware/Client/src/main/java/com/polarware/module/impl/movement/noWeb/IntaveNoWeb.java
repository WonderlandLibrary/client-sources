package com.polarware.module.impl.movement.noweb;

import com.polarware.event.annotations.EventLink;
import com.polarware.event.bus.Listener;
import com.polarware.event.impl.motion.PreUpdateEvent;
import com.polarware.module.impl.movement.NoWebModule;
import com.polarware.util.player.MoveUtil;
import com.polarware.value.Mode;
import net.minecraft.util.MathHelper;

public class IntaveNoWeb extends Mode<NoWebModule> {
    public IntaveNoWeb(String name, NoWebModule parent) {
        super(name, parent);
    }
    private int counter;
    private boolean wasInWeb;
    @Override
    public void onEnable() {
        super.onEnable();
        if (mc.thePlayer != null) {
            mc.thePlayer.jumpMovementFactor = 0.02f;
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
        if (mc.thePlayer != null) {
            mc.thePlayer.jumpMovementFactor = 0.02f;
        }
    }

    @EventLink()
    public final Listener<PreUpdateEvent> onPreUpdate = event -> {
        if (mc.thePlayer.isInWeb) {
            MoveUtil.strafe(1);
            mc.thePlayer.onGround = false;
            if (!mc.thePlayer.isCollidedVertically) {
                mc.thePlayer.jumpMovementFactor = 0.8f;
            }
            if (mc.thePlayer.movementInput.moveStrafe == 0.0f && mc.gameSettings.keyBindForward.isKeyDown() && mc.thePlayer.isCollidedVertically) {
                mc.thePlayer.jumpMovementFactor = 0.74f;
            }
            mc.thePlayer.jumpMovementFactor = 0.2f;
            mc.thePlayer.onGround = true;
            this.wasInWeb = mc.thePlayer.isInWeb;
        }
        else if (mc.thePlayer.jumpMovementFactor > 0.03 && this.wasInWeb && !mc.thePlayer.isInWeb) {
            this.wasInWeb = mc.thePlayer.isInWeb;
            mc.thePlayer.jumpMovementFactor = 0.02f;
        }
        if (mc.thePlayer.isCollidedVertically) {
            if (mc.thePlayer.isInWeb && mc.thePlayer.movementInput.moveStrafe == 0.0f && mc.gameSettings.keyBindForward.isKeyDown()) {
                mc.thePlayer.movementInput.moveForward = ((this.counter % 5 == 0) ? 0.0f : 1.0f);
                ++this.counter;
            }
        }
        else {
            if (mc.thePlayer.isInWeb) {
                if (mc.thePlayer.isSprinting()) {
                    mc.thePlayer.movementInput.moveForward = 0.0f;
                }
                mc.thePlayer.movementInput.sneak = true;
                mc.thePlayer.movementInput.moveForward = (float) MathHelper.clamp_double(mc.thePlayer.movementInput.moveForward, -0.3, 0.3);
                mc.thePlayer.movementInput.moveStrafe = (float)MathHelper.clamp_double((mc.thePlayer.movementInput.moveForward == 0.0f) ? ((double)mc.thePlayer.movementInput.moveStrafe) : 0.0, -0.3, 0.3);
            }
        }
    };
}

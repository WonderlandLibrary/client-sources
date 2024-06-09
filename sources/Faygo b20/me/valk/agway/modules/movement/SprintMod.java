package me.valk.agway.modules.movement;

import java.awt.Color;

import org.lwjgl.input.Keyboard;

import me.valk.event.EventListener;
import me.valk.event.EventType;
import me.valk.event.events.entity.EventMoveRaw;
import me.valk.event.events.player.EventPlayerUpdate;
import me.valk.module.ModData;
import me.valk.module.ModType;
import me.valk.module.Module;

public class SprintMod extends Module {

	public SprintMod(){
		super(new ModData("Sprint", Keyboard.KEY_NONE, new Color(40, 255, 10)), ModType.MOVEMENT);
	}

	
	private boolean canSprint() {
        return !p.isCollidedHorizontally && !p.isSneaking() && p.getFoodStats().getFoodLevel() > 6 && (p.movementInput.moveForward != 0.0f || p.movementInput.moveStrafe != 0.0f);
    }

    @EventListener
    public void onMove(EventMoveRaw event) {
        if (canSprint()) {
            p.setSprinting(true);
        }
    }

    @Override
    public void onDisable() {
        if (p != null) {
            p.setSprinting(false);
        }
    }
}
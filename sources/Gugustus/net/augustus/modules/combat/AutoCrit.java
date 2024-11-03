package net.augustus.modules.combat;

import java.awt.Color;

import net.augustus.events.Event;
import net.augustus.events.EventAttackEntity;
import net.augustus.modules.Categorys;
import net.augustus.modules.Module;
import net.lenni0451.eventapi.reflection.EventTarget;
import net.minecraft.util.BlockPos;

public class AutoCrit extends Module{
	
	public AutoCrit() {
		super("AutoCrit", Color.green, Categorys.COMBAT);
	}
	
	@EventTarget
	public void onEvent(Event e) {
		if(this.isToggled()) {
			if(e instanceof EventAttackEntity) {
				if(mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY + 1, mc.thePlayer.posZ)).getBlock().isCollidable()) {
					if(mc.thePlayer.onGround && mc.thePlayer.jumpMovementFactor == 0.125)
						mc.thePlayer.jump();
				}
			}
		}
	}

}

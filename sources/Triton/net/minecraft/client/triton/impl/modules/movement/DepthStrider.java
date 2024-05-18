package net.minecraft.client.triton.impl.modules.movement;

import net.minecraft.client.triton.management.event.EventTarget;
import net.minecraft.client.triton.management.event.events.MoveEvent;
import net.minecraft.client.triton.management.module.Module;
import net.minecraft.client.triton.management.module.Module.Mod;
import net.minecraft.client.triton.utils.ClientUtils;
import net.minecraft.util.MovementInput;

@Mod
public class DepthStrider extends Module {
	
	public int ticks;
	
	 @EventTarget
	    public void moveEntity(final MoveEvent event) {
	        if (ClientUtils.player().isInWater()) {
	            ++this.ticks;
	            if (this.ticks == 4) {
	            	ClientUtils.setMoveSpeed(event, 0.4000000059604645);
	            }
	            if (this.ticks >= 5) {
	                ClientUtils.setMoveSpeed(event, 0.30000001192092896);
	                this.ticks = 0;
	            }
	        }
	 }
	    public void onDisabled() {
	        super.disable();
	        this.ticks = 0;
	    }
}

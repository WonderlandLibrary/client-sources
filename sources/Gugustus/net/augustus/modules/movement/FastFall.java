package net.augustus.modules.movement;

import java.awt.Color;

import net.augustus.events.EventPreMotion;
import net.augustus.events.EventRender3D;
import net.augustus.events.EventSendPacket;
import net.augustus.modules.Categorys;
import net.augustus.modules.Module;
import net.augustus.settings.BooleanValue;
import net.augustus.settings.DoubleValue;
import net.augustus.settings.StringValue;
import net.augustus.utils.TimeHelper;
import net.lenni0451.eventapi.reflection.EventTarget;
import net.minecraft.util.AxisAlignedBB;

public class FastFall extends Module{
	
   public TimeHelper polarTimeHelper = new TimeHelper();
	
   public StringValue mode = new StringValue(
      1, "Mode", this, "Motion", new String[]{"Motion", "Intave", "Polar", "AirJump"}
   );
   public DoubleValue fallDistance = new DoubleValue(2, "MinFallDist", this, 2.0, 1.0, 24.0, 0);
   public DoubleValue motion = new DoubleValue(3, "SubMotion", this, 2.0, 1.0, 10.0, 0);
   public BooleanValue onlyIfIsBlockUnder = new BooleanValue(4, "onlyIfIsBlockUnder", this, true);
   
   public int fallTick = 0;
   public boolean jumpt = false;
	
	public FastFall() {
		super("FastFall", Color.red, Categorys.MOVEMENT);
	}
	
	@EventTarget
	public void onEventPreMotion(EventPreMotion e) {
		if(this.isToggled()) {
			if(mc.thePlayer.fallDistance < 2 || mc.thePlayer.onGround) {
				mc.thePlayer.capabilities.isFlying = false;
				mc.timer.timerSpeed = 1.0f;
				mc.gameSettings.keyBindSneak.pressed = false;
				fallTick = 0;
				jumpt = false;
				polarTimeHelper.reset();
			}
			if(!mc.thePlayer.onGround && mc.thePlayer.fallDistance > fallDistance.getValue() && isBlockUnder()) {
				fallTick++;
				switch(mode.getSelected()) {
					case "Motion": {
						mc.thePlayer.motionY *= motion.getValue();
						break;
					}
					case "Intave": {
						
						break;
					}
					case "Polar": {
						if(mc.thePlayer.hurtTime > 0)
							return;
						if(!polarTimeHelper.reached(1000)) {
							mc.thePlayer.motionY = -0.1;
						}else {
							polarTimeHelper.reset();
						}
						if(!jumpt) {
//							mc.thePlayer.jump();
							mc.thePlayer.motionY = 1;
							jumpt = true;
						}
						break;
					}
					case "AirJump": {
						if(!jumpt) {
							mc.thePlayer.jump();
							jumpt = true;
						}
						break;
					}
				}
			}
		}
	}
	
	@EventTarget
	public void onPacket(EventSendPacket e) {
		if(this.isToggled()) {
			if(!mc.thePlayer.onGround && mc.thePlayer.fallDistance > fallDistance.getValue() && isBlockUnder()) {
				
			}
		}
	}
	
	@EventTarget
	public void onEventRender3D(EventRender3D e) {
		if(this.isToggled()) {
			if(mode.getSelected().equals("Polar")) {
			}
		}
	}
	
	private boolean isBlockUnder() {
		if (mc.thePlayer.posY < 0)
			return false;
		for (int offset = 0; offset < (int) mc.thePlayer.posY + 2; offset += 2) {
			AxisAlignedBB bb = mc.thePlayer.getEntityBoundingBox().offset(0, -offset, 0);
			if (!mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, bb).isEmpty()) {
				return true;
			}
		}
		return false;
	}

}

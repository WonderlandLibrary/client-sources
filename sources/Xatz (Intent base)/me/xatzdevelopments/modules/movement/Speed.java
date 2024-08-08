package me.xatzdevelopments.modules.movement;

import me.xatzdevelopments.events.Event;
import me.xatzdevelopments.events.listeners.EventModeChanged;
import me.xatzdevelopments.events.listeners.EventUpdate;
import me.xatzdevelopments.events.listeners.MoveEvent;
import me.xatzdevelopments.modules.Module;
import me.xatzdevelopments.settings.ModeSetting;
import net.minecraft.network.play.client.C03PacketPlayer;

public class Speed extends Module{
	
	public ModeSetting speedmode = new ModeSetting("Mode", "LowHop", "LowHop", "Redesky LowHop", "Redesky");
	
	public Speed() {
		super("Speed", 0, Category.MOVEMENT, "Makes you go fast");
		this.addSettings(speedmode);
		this.addonText = this.speedmode.getMode();
	}
	
	
	public void setAddonText() {
		
	}
	
	
	public void onEnable() {
		this.addonText = this.speedmode.getMode();
	    for (int index = 0; index < 3; index++) {
			 mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 4.0D, mc.thePlayer.posZ, false));
           // mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.6D, mc.thePlayer.posZ, false));
           // mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, false));
        }
       
	}
	
	public void onDisable() {
		
	}

	public void onEvent(Event e) {
		if(e instanceof EventModeChanged) {
			this.addonText = this.speedmode.getMode();
		}
		if(e instanceof EventUpdate) {
			if(e.isPre()) {
				if(speedmode.getMode().equals("LowHop")) {
				double speed;
				if(mc.thePlayer.isMovingXZ() && mc.gameSettings.keyBindForward.getIsKeyPressed() || mc.gameSettings.keyBindBack.getIsKeyPressed() || mc.gameSettings.keyBindLeft.getIsKeyPressed() || mc.gameSettings.keyBindRight.getIsKeyPressed()) {
					mc.thePlayer.setSpeed(0.6f);
				
					EventUpdate em = new EventUpdate();
					if(mc.thePlayer.moveForward > 0)
					mc.thePlayer.setSprinting(true);
					mc.timer.timerSpeed = 1.0f;
					speed = 0.2;
					//speed *= 1.03;
					//MovementUtils.setSpeed(speed);
					//mc.thePlayer.setSpeed((float)speed);
					boolean test = true;
					if(mc.thePlayer.onGround) {
						if(test) {
						
						//mc.thePlayer.setSpeed(0.25f);
				        test = false;
						} else {
							if(!test) {
								test = true;
							}				
						}
						e.setCancelled(true);
						if(!mc.gameSettings.keyBindJump.getIsKeyPressed()) {
						mc.thePlayer.jump(true);
						} else {
							mc.thePlayer.jump(false);
						}
						//mc.thePlayer.onGround = true;
					}
				}
			}
			}
			if(speedmode.getMode().equals("Redesky")) {
				if(mc.thePlayer.onGround && !mc.gameSettings.keyBindJump.isPressed() && mc.gameSettings.keyBindForward.isPressed()) {
        		//mc.thePlayer.speedInAir = 0.0283f;
        		mc.thePlayer.motionX *= 1.004;
        		mc.thePlayer.motionZ *= 1.004;
        		//mc.thePlayer.motionY *= 1.105439;
        		mc.thePlayer.jump(false);
				}
			}
			if(speedmode.getMode().equals("Redesky LowHop")) {
				if(mc.thePlayer.onGround && !mc.gameSettings.keyBindJump.isPressed() && mc.gameSettings.keyBindForward.isPressed()) {
        		//mc.thePlayer.speedInAir = 0.0283f;
        		mc.thePlayer.motionX *= 1.004;
        		mc.thePlayer.motionZ *= 1.004;
        		//mc.thePlayer.motionY *= 1.105439;
        		mc.thePlayer.jump(true);
				}
			}
		}
	}
}

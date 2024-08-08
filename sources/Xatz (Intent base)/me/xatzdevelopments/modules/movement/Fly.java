package me.xatzdevelopments.modules.movement;

import org.lwjgl.input.Keyboard;

import me.xatzdevelopments.Xatz;
import me.xatzdevelopments.events.Event;
import me.xatzdevelopments.events.listeners.EventModeChanged;
import me.xatzdevelopments.events.listeners.EventMotion;
import me.xatzdevelopments.modules.Module;
import me.xatzdevelopments.settings.BooleanSetting;
import me.xatzdevelopments.settings.ModeSetting;
import me.xatzdevelopments.settings.NumberSetting;
import me.xatzdevelopments.util.MoveUtils;
import me.xatzdevelopments.util.Stopwatch;
import me.xatzdevelopments.util.Timer;
import net.minecraft.network.play.client.C03PacketPlayer;

public class Fly extends Module {
	public ModeSetting flymode = new ModeSetting("Fly Mode", "Vanilla", "Vanilla", "Watchdog Fast", "Redesky", "Hypixel");
	public ModeSetting hypixelmode = new ModeSetting("Hypixel Mode", "1", "1", "2");
	public NumberSetting Speed = new NumberSetting("Speed", 3, 1, 5, 1);
	public NumberSetting timerSpeed = new NumberSetting("Timer Speed", 1, 1, 3, 1);
	public BooleanSetting damage = new BooleanSetting("Damage", true);
	public NumberSetting redespeed = new NumberSetting("Rede Speed", 0.7, 0.2, 1.5, 0.2);
	public BooleanSetting bobbing = new BooleanSetting("Bobbing", false);
	private double OPosX;
	private double OPosY;
	private double OPosZ;
	public static boolean overridenotification = false;
	public Timer jumptimer = new Timer();
	private boolean idk = false;
	private Stopwatch flyStopwatch = new Stopwatch();
	int state;
	public Fly() {
		super("Fly", Keyboard.KEY_F, Category.MOVEMENT, "Fly like a bird");
		this.addSettings(Speed, flymode, timerSpeed, damage, redespeed, bobbing);
		this.addonText = this.flymode.getMode();
	}
	
	
	
	public void onDisable() {
		mc.timer.timerSpeed = 1;
		boolean idk = false;
		mc.thePlayer.capabilities.isFlying = false;
		overridenotification = false;
		mc.thePlayer.capabilities.setFlySpeed(0.05f);
	}
	
	public void onEnable() {
		this.state = 0;
		this.OPosX = this.mc.thePlayer.posX;
        this.OPosY = this.mc.thePlayer.posY;
        this.OPosZ = this.mc.thePlayer.posZ;
		this.addonText = this.flymode.getMode();
		//mc.timer.timerSpeed = 0.1f;
		this.flyStopwatch.reset();
		if(this.flymode.getMode().equalsIgnoreCase("Hypixel")) {
			if(this.damage.isEnabled()) {
				for (int index = 0; index <49; index++) {
          	    	mc.thePlayer.sendQueue.getNetworkManager().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.06249D, mc.thePlayer.posZ, false));
          	    	mc.thePlayer.sendQueue.getNetworkManager().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, false));
          	    }
				mc.thePlayer.jumpMovementFactor = 0;
				}
		}
		//boolean idk = false;
		if(this.flymode.getMode() == "Watchdog Fast") {
			boolean ojojoj = false;
			if(this.damage.isEnabled()) {
			for (int index = 0; index < 70; index++) {
	            mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.06D, mc.thePlayer.posZ, false));
	            mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, false));
	        }
	        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.1D, mc.thePlayer.posZ, false));
			} else {
				//mc.thePlayer.cameraYaw = 5;
				if(mc.thePlayer.onGround)
				mc.thePlayer.jump(false);
			}
	        if(mc.thePlayer.onGround && ojojoj) {
				//mc.thePlayer.jump();
				ojojoj = !ojojoj;
			}
		}
		
		if(this.flymode.getMode() == "Watchdog Fast") {
		overridenotification = true;
		} else {
			this.overridenotification = false;
		}
		
		if(idk) {
			
		}
	}
	


	public void onEvent(Event e) {
		if(e instanceof EventModeChanged) {
			this.addonText = this.flymode.getMode();
		}
		if(e instanceof EventMotion) {
			
			if(e.isPre()) {
				if(this.flymode.getMode() != "Redesky" || this.flymode.getMode() !=  "Vanilla") {
					if(this.bobbing.isEnabled()) {
					if(mc.thePlayer.moveForward != 0 || mc.thePlayer.moveStrafing != 0)
						mc.thePlayer.cameraYaw = 0.105f;
					}
				}
	            final double x = this.mc.thePlayer.posX;
	            final double y = this.mc.thePlayer.posY;
	            final double z = this.mc.thePlayer.posZ;
				
				if(this.flymode.getMode().equalsIgnoreCase("redesky")) {
					this.mc.timer.timerSpeed = (float)this.redespeed.getValue();
                    if (this.mc.gameSettings.keyBindJump.getIsKeyPressed() && this.mc.thePlayer.fallDistance > 0.0f) {
                        this.OPosY = this.mc.thePlayer.posY + 0.001;
                    }
                    if ((this.mc.thePlayer.posY < this.OPosY + 0.5 && this.mc.thePlayer.fallDistance > 0.0f) || this.mc.thePlayer.onGround) {
                        this.mc.thePlayer.motionY = 0.42;
                        final double direction2 = MoveUtils.getDirection();
                        double posX4 = -Math.sin(direction2) * 6.0;
                        double posZ4 = Math.cos(direction2) * 6.0;
                        double posX5 = -Math.sin(direction2) * 18.0;
                        double posZ5 = Math.cos(direction2) * 18.0;
                        if (!MoveUtils.isMoving()) {
                            posZ4 = (posX4 = (posX5 = (posZ5 = 1.0)));
                        }
                        ((EventMotion)e).yaw = this.mc.thePlayer.rotationYaw - 180.0f;
                        this.mc.timer.timerSpeed = 2.0f;
                        this.mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x + posX4, y + (this.mc.gameSettings.keyBindJump.getIsKeyPressed() ? 5 : 1), z + posZ4, false));
                        this.mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x + posX5, y + (this.mc.gameSettings.keyBindJump.getIsKeyPressed() ? 5 : 1), z + posZ5, false));
                        this.mc.thePlayer.fallDistance = 0.0f;
                        
                    }
                   
		         }
				
				if(this.flymode.getMode() == "Hypixel") {
					switch(this.hypixelmode.getMode().toString()) {
					case "1":
						boolean ee = true;
						if(this.mc.thePlayer.onGround) {
							this.flyStopwatch.reset();
							if(this.damage.isEnabled() && mc.thePlayer.hurtTime > 0) {
								mc.thePlayer.motionY = 0.41999998688698f + MoveUtils.getJumpEffect()*0.1;
							} else if(!this.damage.isEnabled()) {
								mc.thePlayer.motionY = 0.42f;
							}
						} else {
							mc.thePlayer.motionY = 0;
							state++;
							switch(state) {
							case 1:
								mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + 1.0E-12D, mc.thePlayer.posZ);
								break;
							case 2:
								mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY - 1.0E-12D, mc.thePlayer.posZ);
								break;
							case 3:
								mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + 1.0E-12D, mc.thePlayer.posZ);
								state = 0;
								break;
							}
							mc.timer.timerSpeed = this.flyStopwatch.getElapsedTime() < 1000 ? (float)this.timerSpeed.getValue() : 1f;
						}
						break;
					case "2":
						double y1;
			            double y2;
			            mc.thePlayer.motionY = 0;
			            if(mc.thePlayer.ticksExisted % 3 == 0) {
			                y1 = mc.thePlayer.posY - 1.0E-10D;
			                mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, y1, mc.thePlayer.posZ, true));
			            }
			            y2 = mc.thePlayer.posY + 1.0E-10D;
			            mc.thePlayer.setPosition(mc.thePlayer.posX, y2, mc.thePlayer.posZ);
						break;
					}
				}
				if(this.flymode.getMode() == "Watchdog Fast") {
					
				}
				if(this.flymode.getMode().equals("Vanilla")) {
				mc.thePlayer.capabilities.isFlying = true;
				mc.thePlayer.capabilities.setFlySpeed(0.05f * (float)this.Speed.getValue());
				//mc.thePlayer.capabilities.setFlySpeed(0.5F);
					//mc.thePlayer.motionY = 0;
					//mc.thePlayer.setSpeed(0.1f);
				}
				if(this.flymode.getMode().equals("NCP")) {
					double y2;
		            double y1;
		            mc.thePlayer.motionY = 0;
		            if(mc.thePlayer.ticksExisted % 3 == 0) {
		                y2 = mc.thePlayer.posY - 1.0E-10D;
		                mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, y2, mc.thePlayer.posZ, true));
		                mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, y2, mc.thePlayer.posZ, false));
		            }
		            y1 = mc.thePlayer.posY + 1.0E-10D;
		            mc.thePlayer.onGround = true;
		            mc.getNetHandler().addToSendQueue(new C03PacketPlayer(true));
		            mc.thePlayer.setPosition(mc.thePlayer.posX, y1, mc.thePlayer.posZ);
				}
				if(this.flymode.getMode().equals("Watchdog Fast")) {
					if(mc.thePlayer.hurtTime > 0) {
						if(this.flyStopwatch.elapsed(25L)) {
							if(!this.damage.isEnabled()) {
								//mc.thePlayer.cameraYaw = 4.5f;
							}
							if(this.damage.isEnabled()) {
							this.mc.timer.timerSpeed = 0.1f;
							if(!this.flyStopwatch.elapsed(123L)) {
								mc.thePlayer.jump(false);
							}
							}if(!this.damage.isEnabled()) {
							if(this.flyStopwatch.getElapsedTime() <= 500L) {
							mc.thePlayer.jump(false);
							}
							} else {
								mc.thePlayer.jump(false);
							}
						} else {
							//mc.thePlayer.cameraYaw = 0.105f;
						}
					}
					overridenotification = false;
					//mc.thePlayer.posY = this.posy + 0.5D;
					if(this.flyStopwatch.elapsed(123L)) {
						if(!(this.flyStopwatch.elapsed(923L))) {
							mc.timer.timerSpeed = (float)this.timerSpeed.getValue();
						} else {
					mc.timer.timerSpeed = 1f;
						}
					}
					//mc.thePlayer.jump();
					if(this.damage.isEnabled()) {
					if(!mc.gameSettings.keyBindJump.getIsKeyPressed() && !mc.gameSettings.keyBindJump.getIsKeyPressed()) {
						if(this.flyStopwatch.elapsed(123L)) {
					mc.thePlayer.motionY = 0;
						}
					}
					} else {
						mc.thePlayer.motionY = 0;
					}
					/*if(mc.gameSettings.keyBindJump.getIsKeyPressed()) {
						mc.thePlayer.motionY = 0.6 * this.Speed.getValue();
					}
					if(mc.gameSettings.keyBindJump.getIsKeyPressed()) {
						mc.thePlayer.motionY = -0.6 * this.Speed.getValue();
					}*/
					if(mc.thePlayer.isMovingXZ() && mc.gameSettings.keyBindForward.getIsKeyPressed() || mc.gameSettings.keyBindBack.getIsKeyPressed() || mc.gameSettings.keyBindLeft.getIsKeyPressed() || mc.gameSettings.keyBindRight.getIsKeyPressed()){
						float speed = (0.2f * (float)this.Speed.getValue()) * 3;
						if(speed < 0.2f) {
							speed = 0.2f;
						}
						mc.thePlayer.setSpeed(speed);
						if(mc.thePlayer.getSpeed() < (0.2f * (float)this.Speed.getValue() * 3) && this.flyStopwatch.elapsed(250L)) {
							mc.thePlayer.setSpeed(0f);
						}
					} else {
						mc.thePlayer.setSpeed(0F);	
					}
				}
			}
		}
	}
	
}

package exhibition.module.impl.movement;

import java.math.BigDecimal;
import java.math.RoundingMode;

import exhibition.event.Event;
import exhibition.event.RegisterEvent;
import exhibition.event.impl.EventMotionUpdate;
import exhibition.event.impl.EventMove;
import exhibition.event.impl.EventPacket;
import exhibition.event.impl.EventTick;
import exhibition.module.Module;
import exhibition.module.data.ModuleData;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.potion.Potion;
import net.minecraft.util.MovementInput;

public class Speed extends Module {

	private double moveSpeed;
    private int cooldownHops;
    private boolean wasOnWater;
	private int state=0;
	private double lastDist;
	
	public Speed(ModuleData data) {
		super(data);
	}

	@Override
	@RegisterEvent(events = { EventPacket.class , EventMotionUpdate.class })
	public void onEvent(Event event) {
		if(event instanceof EventPacket){
			if(((EventPacket)event).isIncoming() && ((EventPacket)event).getPacket() instanceof S08PacketPlayerPosLook){
	            this.cooldownHops = 2;
	            this.state = 0;
	            
			}				
		}
		if(event instanceof EventMotionUpdate){
            if (mc.thePlayer.isInWater()) {
                this.cooldownHops = 2;
                return;
            }
            if (mc.thePlayer.isOnLadder() || mc.thePlayer.isEntityInsideOpaqueBlock() || mc.thePlayer.isCollidedHorizontally) {
                this.moveSpeed = 0.0;
                this.wasOnWater = true;
                return;
            }
            if (this.wasOnWater) {
                this.moveSpeed = 0.0;
                this.wasOnWater = false;
                return;
            }
            if (mc.thePlayer.moveForward == 0.0f && mc.thePlayer.moveStrafing == 0.0f) {
                return;
            }
            if (mc.thePlayer.onGround) {
                this.state = 2;
            }
            
            if (round(mc.thePlayer.posY - (double)(int)mc.thePlayer.posY, 3) == round(0.138, 3)) {
            	Minecraft.getMinecraft().thePlayer.motionY -= 0.08;
                final EventMove eventMove = (EventMove)event;
                Minecraft.getMinecraft().thePlayer.motionY -= 0.09316090325960147;
                Minecraft.getMinecraft().thePlayer.posY -= 0.09216090325960147;
            }
            if (this.state == 1 && (mc.thePlayer.moveForward != 0.0f || mc.thePlayer.moveStrafing != 0.0f)) {
                this.state = 2;
                this.moveSpeed = 1.35 * this.getBaseMoveSpeed() - 0.01;
            }
            else if (this.state == 2) {
                this.state = 3;
                if (mc.thePlayer.moveForward != 0.0f || mc.thePlayer.moveStrafing != 0.0f) {
                    mc.thePlayer.motionY = 0.399399995803833;
                    mc.thePlayer.motionY = 0.399399995803833;
                    if (this.cooldownHops > 0) {
                        --this.cooldownHops;
                    }
                    this.moveSpeed *= 2.139;
                }
            }
            else if (this.state == 3) {
                this.state = 4;
                final double difference = 0.66 * (this.lastDist - this.getBaseMoveSpeed());
                this.moveSpeed = this.lastDist - difference;
            }
            else {
                this.moveSpeed = this.lastDist - this.lastDist / 159.0;
            }
            this.moveSpeed = Math.max(this.moveSpeed, this.getBaseMoveSpeed());
            final MovementInput movementInput = mc.thePlayer.movementInput;
            float forward = movementInput.moveForward;
            float strafe = movementInput.moveStrafe;
            float yaw = mc.thePlayer.rotationYaw;
            if (forward == 0.0f && strafe == 0.0f) {
            	Minecraft.getMinecraft().thePlayer.motionX = 0.0;
            	Minecraft.getMinecraft().thePlayer.motionZ = 0.0;
            }
            else if (forward != 0.0f) {
                if (strafe >= 1.0f) {
                    yaw += ((forward > 0.0f) ? -45 : 45);
                    strafe = 0.0f;
                }
                else if (strafe <= -1.0f) {
                    yaw += ((forward > 0.0f) ? 45 : -45);
                    strafe = 0.0f;
                }
                if (forward > 0.0f) {
                    forward = 1.0f;
                }
                else if (forward < 0.0f) {
                    forward = -1.0f;
                }
            }
            final double mx = Math.cos(Math.toRadians((double)(yaw + 90.0f)));
            final double mz = Math.sin(Math.toRadians((double)(yaw + 90.0f)));
            final double motionX = forward * this.moveSpeed * mx + strafe * this.moveSpeed * mz;
            final double motionZ = forward * this.moveSpeed * mz - strafe * this.moveSpeed * mx;
            if (this.cooldownHops == 0) {
                if (mc.thePlayer.isUsingItem() || mc.thePlayer.isBlocking()) {
                	Minecraft.getMinecraft().thePlayer.motionX *= 0.399399995803833;
                	Minecraft.getMinecraft().thePlayer.motionZ *= 0.399399995803833;
                }
                Minecraft.getMinecraft().thePlayer.motionX = forward * this.moveSpeed * mx + strafe * this.moveSpeed * mz;
                Minecraft.getMinecraft().thePlayer.motionZ = forward * this.moveSpeed * mz - strafe * this.moveSpeed * mx;
            }
            mc.thePlayer.stepHeight = 0.6f;
            if (forward == 0.0f && strafe == 0.0f) {
            	Minecraft.getMinecraft().thePlayer.motionX = 0.0;
            	Minecraft.getMinecraft().thePlayer.motionZ = 0.0;
            }
            else {
                if (forward != 0.0f) {
                    if (strafe >= 1.0f) {
                        yaw += ((forward > 0.0f) ? -45 : 45);
                        strafe = 0.0f;
                    }
                    else if (strafe <= -1.0f) {
                        yaw += ((forward > 0.0f) ? 45 : -45);
                        strafe = 0.0f;
                    }
                    if (forward > 0.0f) {
                        forward = 1.0f;
                    }
                    else if (forward < 0.0f) {
                        forward = -1.0f;
                    }
                }
            }
		}else if(event instanceof EventTick){
            if (mc.thePlayer.isInWater()) {
                return;
            }
            if (mc.thePlayer.moveForward == 0.0f && mc.thePlayer.moveStrafing == 0.0f && mc.thePlayer.onGround) {
                this.cooldownHops = 2;
                this.moveSpeed *= 1.0800000429153442;
                this.state = 2;
            }
            if (!mc.thePlayer.onGround && !mc.thePlayer.isCollidedHorizontally && mc.thePlayer.motionY > -0.5) {
                mc.thePlayer.motionY = -0.5;
            }
            final double xDist = mc.thePlayer.posX - mc.thePlayer.prevPosX;
            final double zDist = mc.thePlayer.posZ - mc.thePlayer.prevPosZ;
            this.lastDist = Math.sqrt(xDist * xDist + zDist * zDist);
		}
	}
	
	public static double getBaseMoveSpeed() {
		double baseSpeed = 0.2873D;
		if (Minecraft.getMinecraft().thePlayer.isPotionActive(Potion.moveSpeed)) {
			int amplifier = Minecraft.getMinecraft().thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
			baseSpeed *= (1.0D + 0.2D * (amplifier + 1));
		}
		return baseSpeed;
	}
	
    public static double round(double value, int places)
    {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}

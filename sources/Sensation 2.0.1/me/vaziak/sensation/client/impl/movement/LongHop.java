package me.vaziak.sensation.client.impl.movement;

import net.minecraft.potion.Potion;

import me.vaziak.sensation.Sensation;
import me.vaziak.sensation.client.api.Category;
import me.vaziak.sensation.client.api.Module;
import me.vaziak.sensation.client.api.event.annotations.Collect;
import me.vaziak.sensation.client.api.event.events.PlayerMoveEvent;
import me.vaziak.sensation.client.api.event.events.PlayerUpdateEvent;
import me.vaziak.sensation.client.api.property.impl.StringsProperty;


public class LongHop extends Module {

    protected StringsProperty prop_mode = new StringsProperty("Mode", "Wonder what the fuck this does", null, false, false, new String[]{"Faithful", "Watchdog", "MCCentral"}, new Boolean[]{false,true, false});
    private float air, groundTicks;
    private int stage, hops;
	private double movementSpeed;
	private double lastreporteddistance;

    public LongHop() {
        super("Long Jump", Category.MOVEMENT);
        registerValue(prop_mode);
    }

    public void onDisable() {
    	hops = 0;
        mc.timer.timerSpeed = 1.0F;
        mc.thePlayer.motionX = 0.0;
        mc.thePlayer.motionZ = 0.0;
        mc.thePlayer.setSpeed(0);
        mc.timer.timerSpeed = 1.0f;
    }

    public void onEnable() {
        air = 0;
        stage = 0;
        groundTicks = 0;
    }

    public double getHopSpeed() {
        if (hops <= 1) {
            return mc.thePlayer.isPotionActive(Potion.moveSpeed) ? .9 : .92;
        } else {
        	double speed = mc.thePlayer.isPotionActive(Potion.moveSpeed) ? .8 : .85;
        	speed = speed - (hops * .1);
        	if (hops > 10) {
        		hops = 1;
        	}
            return  speed;
        }
    }//b8a19-1bf10@alt.com

    public int getSpeedEffect() {
        return mc.thePlayer.isPotionActive(Potion.moveSpeed) ? mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1 : 0;
    }

    @Collect
    public void onPlayerUpdate(PlayerUpdateEvent e) {
        setMode(prop_mode.getSelectedStrings().get(0));

		double xDist = mc.thePlayer.posX - mc.thePlayer.prevPosX;
		double zDist = mc.thePlayer.posZ - mc.thePlayer.prevPosZ;
		lastreporteddistance = Math.sqrt(xDist * xDist + zDist * zDist);

        if (prop_mode.getValue().get("Watchdog")) {
        	e.setOnGround(mc.thePlayer.ticksExisted % 3 != 0);
        }
        if (prop_mode.getValue().get("Faithful") && mc.thePlayer.isMoving()) { 
            if (mc.thePlayer.onGround) {
                mc.thePlayer.motionY = .42f; 
            } else {

                e.setOnGround(true);
            	if (mc.thePlayer.ticksExisted % 3 != 0) {
                	mc.thePlayer.setSpeed(1.75); 
            	} else {
                	mc.thePlayer.setSpeed(.15f); 
            	}
            	mc.thePlayer.motionY = mc.thePlayer.ticksExisted % 2 == 0 ? .04 : -.06;
            }
        }
    }

	@Collect
	public void onPlayerMove(PlayerMoveEvent e) {

        if (prop_mode.getValue().get("Watchdog")) {
            double forward = mc.thePlayer.movementInput.moveForward;
            double strafe = mc.thePlayer.movementInput.moveStrafe;
            float yaw = mc.thePlayer.rotationYaw;
            if (forward == 0.0F && strafe == 0.0F) {
                e.setX(0);
                e.setZ(0);
            }
            if (forward != 0 && strafe != 0) {
                forward = forward * Math.sin(Math.PI / 4);
                strafe = strafe * Math.cos(Math.PI / 4);
            }
            if (stage != 1 || mc.thePlayer.moveForward == 0.0F && mc.thePlayer.moveStrafing == 0.0F) {
                if (stage == 2 && mc.thePlayer.onGround) {
                    ++stage;
                    double motionY = 0.4225;
                    if ((mc.thePlayer.moveForward != 0.0F || mc.thePlayer.moveStrafing != 0.0F) && mc.thePlayer.onGround) {
                        if (mc.thePlayer.isPotionActive(Potion.jump)) motionY += ((mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1F);
                        e.setY(mc.thePlayer.motionY = motionY);
                        movementSpeed *= 2.145;
                    }
                } else if (stage == 3) {
                    ++stage;
                    double difference = .66D * (lastreporteddistance - getBaseMoveSpeed());
                    movementSpeed = lastreporteddistance - difference;
                } else {
                    if (mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.getEntityBoundingBox().offset(0.0D, mc.thePlayer.motionY, 0.0D)).size() > 0 || mc.thePlayer.isCollidedVertically) {
                        if (stage >= 4) {
                            return;
                        }
                        stage = 1;
                    }
                    movementSpeed = lastreporteddistance - lastreporteddistance / 125;
                }
            } else {
                stage = 2;
                double boost = mc.thePlayer.isPotionActive(Potion.moveSpeed) ? mc.thePlayer.isPotionActive(Potion.jump) ? 2.15 : 2.4 : 3;
                movementSpeed = boost * getBaseMoveSpeed() - (0.012);
            }
            movementSpeed = Math.max(movementSpeed, getBaseMoveSpeed());
            final double mx = -Math.sin(Math.toRadians(yaw));
            final double mz = Math.cos(Math.toRadians(yaw));
            e.setX(forward * movementSpeed * mx + strafe * movementSpeed * mz);
            e.setZ(forward * movementSpeed * mz - strafe * movementSpeed * mx);
        }


        if (prop_mode.getValue().get("MCCentral")) {
            float x2 = (float) (getBaseMoveSpeed() + (getSpeedEffect() * .5f));
            if ((mc.thePlayer.moveForward != 0 || mc.thePlayer.moveStrafing != 0) && mc.thePlayer.onGround) {
                if (groundTicks > 0) {
                    groundTicks = 0;
                    return;
                }
                groundTicks++;
                Speed sped = (Speed) Sensation.instance.cheatManager.getCheatRegistry().get("Speed"); 
                double motionY = 1;
                if (mc.thePlayer.isPotionActive(Potion.jump)) {
                    motionY += (mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1;
                }
                e.y =  mc.thePlayer.motionY = motionY;
                hops += 1;
                stage = 1;
                if (hops > 3) {
                    hops = 0;
                }

            }
            if (!mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.getEntityBoundingBox().offset(0.0D, -0.001, 0.0D)).isEmpty()) {
                air = 0;
            } else {
                if (mc.thePlayer.isCollidedVertically) stage = 0;
                double speed = (.95 + getBaseMoveSpeed()) - (air / 25);
                movementSpeed = Math.max(getBaseMoveSpeed(), speed) * 1.5;
                
                e.y = mc.thePlayer.motionY = -.05;
                mc.thePlayer.cameraYaw = mc.thePlayer.ticksExisted % 3 != 0 ? .005f : .108f;
                if (stage > 0) {
                    stage++;
                }
                air += x2;
            } 
            movementSpeed = Math.max(movementSpeed, getBaseMoveSpeed());
            e.setMoveSpeed(movementSpeed); 
		}
	}
    double getMotion(int stage) {
        double[] mot = {0.346, 0.2699, 0.183, 0.103, 0.024, -0.008, -0.04, -0.072, -0.104, -0.13, -0.019, -0.097};
        stage--;
        if (stage >= 0 && stage < mot.length) {
            return mot[stage];
        } else {
            return mc.thePlayer.motionY -= .01;
        }
    } 
}

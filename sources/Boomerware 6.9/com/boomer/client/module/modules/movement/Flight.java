package com.boomer.client.module.modules.movement;

import com.boomer.client.event.bus.Handler;
import com.boomer.client.event.events.player.MotionEvent;
import com.boomer.client.event.events.player.UpdateEvent;
import com.boomer.client.module.Module;
import com.boomer.client.utils.MathUtils;
import com.boomer.client.utils.TimerUtil;
import com.boomer.client.utils.value.impl.BooleanValue;
import com.boomer.client.utils.value.impl.EnumValue;
import com.boomer.client.utils.value.impl.NumberValue;
import net.minecraft.block.BlockAir;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;

import org.apache.commons.lang3.StringUtils;

import java.awt.*;

public class Flight extends Module {
    private TimerUtil timer = new TimerUtil();
    private EnumValue<Modes> mode = new EnumValue<>("Mode", Modes.HYPIXEL);
    private EnumValue<BoostModes> boostModes = new EnumValue<>("Boost Mode", BoostModes.NORMAL);
    private BooleanValue viewbob = new BooleanValue("ViewBob", true);
    private BooleanValue boost = new BooleanValue("Boost", true);
    private NumberValue<Float> flyspeed = new NumberValue<>("Fly Speed", 2.0f, 0.1f, 3.5f, 0.1f);
    private double moveSpeed, lastDist;
    private int level;

    public Flight() {
        super("Flight", Category.MOVEMENT, new Color(33, 120, 255, 255).getRGB());
        setDescription("Fly like a boomer.");
        addValues(viewbob, boost, flyspeed, mode,boostModes);
    }

    public enum Modes {
        HYPIXEL, VANILLA, CUBECRAFT, ANTIVIRUS, EXPERIMENTAL
    }
    public enum BoostModes {
        NORMAL,DAMAGE
    }

    @Override
    public void onDisable() {
        if (mc.thePlayer == null || mc.theWorld == null) return;
        mc.timer.timerSpeed = 1f;
        mc.thePlayer.motionX = mc.thePlayer.motionZ = 0;
        moveSpeed = getBaseMoveSpeed();
        lastDist = 0.0D;
    }

    @Handler
    public void onUpdate(UpdateEvent event) {
        setSuffix(StringUtils.capitalize(mode.getValue().name().toLowerCase()));
        if (!event.isPre() && boost.isEnabled()) {
            double xDist = mc.thePlayer.posX - mc.thePlayer.prevPosX;
            double zDist = mc.thePlayer.posZ - mc.thePlayer.prevPosZ;
            lastDist = Math.sqrt((xDist * xDist) + (zDist * zDist));
        }
        if (event.isPre()) {
            if (mc.thePlayer.ticksExisted % 2 == 0 && viewbob.getValue() && mc.thePlayer.isMoving())
                mc.thePlayer.cameraYaw = 0.1f;
            switch (mode.getValue()) {
                case CUBECRAFT:
                    mc.timer.timerSpeed = 0.3f;
                    if (!mc.thePlayer.onGround && !mc.thePlayer.isCollided) {
                        event.setOnGround(true);
                        mc.thePlayer.motionX = 0f;
                        mc.thePlayer.motionY = 0f;
                        mc.thePlayer.motionZ = 0f;
                        if (timer.reach(410)) {
                            double x = mc.thePlayer.posX;
                            double y = mc.thePlayer.posY;
                            double z = mc.thePlayer.posZ;
                            double lmfao = 2.12;
                            x += Math.cos(Math.toRadians(mc.thePlayer.rotationYaw + 90)) * lmfao;
                            z += Math.sin(Math.toRadians(mc.thePlayer.rotationYaw + 90)) * lmfao;
                            if (mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 0.22, mc.thePlayer.posZ)).getBlock() instanceof BlockAir) {
                                mc.thePlayer.setPosition(x, y - MathUtils.getRandomInRange(0.115, 0.2), z);
                            }
                            timer.reset();
                            mc.thePlayer.motionY = -MathUtils.getRandomInRange(0.17, 0.189);
                        } else {
                            mc.thePlayer.motionY = -MathUtils.getRandomInRange(0.189, 0.21);
                        }
                    }
                    break;
                case HYPIXEL:
                    if (event.isPre()) {
                        if (mc.gameSettings.keyBindJump.isKeyDown())
                            mc.thePlayer.motionY = flyspeed.getValue() / 2;
                        else if (mc.gameSettings.keyBindSneak.isKeyDown())
                            mc.thePlayer.motionY = -flyspeed.getValue() / 2;
                        else mc.thePlayer.motionY = 0;
                        if (mc.gameSettings.keyBindJump.isKeyDown()) {
                            mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.15, mc.thePlayer.posZ);
                            mc.thePlayer.motionY = 0.15;
                        } else if (mc.gameSettings.keyBindSneak.isKeyDown()) {
                            mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY - 0.15, mc.thePlayer.posZ);
                            mc.thePlayer.motionY = -0.15;
                        } else mc.thePlayer.motionY = 0;
                        if (mc.thePlayer.ticksExisted % 2 == 0)
                            mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + MathUtils.getRandomInRange(0.00000000000001, 1e-13), mc.thePlayer.posZ);
                        if (boost.isEnabled()) {
                            float motionY = 0.42f;
                            if ((mc.thePlayer.moveForward != 0.0F || mc.thePlayer.moveStrafing != 0.0F) && mc.thePlayer.onGround) {
                                if (mc.thePlayer.isPotionActive(Potion.jump))
                                    motionY += ((mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1F);
                                if (boostModes.getValue() == BoostModes.DAMAGE) mc.thePlayer.damagePlayer();
                                mc.thePlayer.motionY = motionY;
                            }
                        }
                    }
                    break;
                case VANILLA:
                    if (mc.gameSettings.keyBindJump.isKeyDown())
                        mc.thePlayer.motionY = flyspeed.getValue() / 2;
                    else if (mc.gameSettings.keyBindSneak.isKeyDown())
                        mc.thePlayer.motionY = -flyspeed.getValue() / 2;
                    else mc.thePlayer.motionY = 0;
                    break;

                case ANTIVIRUS:
                    if (mc.thePlayer.fallDistance > 0.25 && !mc.gameSettings.keyBindSneak.isKeyDown()) {
                        mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + mc.thePlayer.fallDistance, mc.thePlayer.posZ);
                        mc.thePlayer.fallDistance = 0;
                    }
                    event.setOnGround(true);
                    mc.thePlayer.motionY = -0.05;

                    if (mc.gameSettings.keyBindJump.isKeyDown() && mc.thePlayer.ticksExisted % 2 == 0) {
                        mc.thePlayer.fallDistance = 2;
                    }
                    else if (mc.gameSettings.keyBindSneak.isKeyDown() && mc.thePlayer.fallDistance > 0) {
                        mc.thePlayer.motionY = -1;
                        mc.thePlayer.fallDistance = 0;
                        //mc.thePlayer.fallDistance = 1;
                        // mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY - 2, mc.thePlayer.posZ);
                    }
                    break;
                case EXPERIMENTAL:
                    if (event.isPre()) {
                        event.setOnGround(true);
                        mc.thePlayer.motionY = 0F;
                        mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.0625F, mc.thePlayer.posZ);
                    }

                    break;
                default:
                    break;
            }
        }
    }

    @Handler
    public void onMotion(MotionEvent event) {
        switch (mode.getValue()) {
            case EXPERIMENTAL:
                setMoveSpeed(event, 2);
                break;
            case ANTIVIRUS:
                if (!mc.thePlayer.onGround) {
                    setMoveSpeed(event, 0.75);
                }
            case CUBECRAFT:
                if (!mc.thePlayer.onGround && !mc.thePlayer.isCollided) {
                    if (!timer.reach(410)) {
                        setMoveSpeed(event, 0.2);
                    }
                }
                break;
            case VANILLA:
                setMoveSpeed(event, flyspeed.getValue());
                break;
            case HYPIXEL:
                if (boost.isEnabled()) {
                    double forward = mc.thePlayer.movementInput.moveForward;
                    double strafe = mc.thePlayer.movementInput.moveStrafe;
                    float yaw = mc.thePlayer.rotationYaw;
                    if (forward == 0.0F && strafe == 0.0F) {
                        event.setX(0);
                        event.setZ(0);
                    }
                    if (forward != 0 && strafe != 0) {
                        forward = forward * Math.sin(Math.PI / 4);
                        strafe = strafe * Math.cos(Math.PI / 4);
                    }
                    if (level != 1 || mc.thePlayer.moveForward == 0.0F && mc.thePlayer.moveStrafing == 0.0F) {
                        if (level == 2) {
                            ++level;
                            moveSpeed *= mc.thePlayer.isPotionActive(Potion.moveSpeed) ? flyspeed.getValue()-0.3 : flyspeed.getValue();
                        } else if (level == 3) {
                            ++level;
                            double difference = (boostModes.getValue() == BoostModes.DAMAGE ? 0.01 :mc.thePlayer.ticksExisted % 2 == 0 ? -0.05D:0.1D) * (lastDist - getBaseMoveSpeed());
                            moveSpeed = lastDist - difference;
                        } else {
                            level++;
                            if (mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.getEntityBoundingBox().offset(0.0D, mc.thePlayer.motionY, 0.0D)).size() > 0 || mc.thePlayer.isCollidedVertically) {
                                level = 1;
                            }
                            moveSpeed = lastDist - lastDist / 159.9D;
                        }
                    } else {
                        level = 2;
                        double boost = mc.thePlayer.isPotionActive(Potion.moveSpeed) ? 1.5 : 1.6;
                        moveSpeed = boost * getBaseMoveSpeed() - 0.01D;
                    }
                    moveSpeed = Math.max(moveSpeed, getBaseMoveSpeed());
                    final double mx = -Math.sin(Math.toRadians(yaw));
                    final double mz = Math.cos(Math.toRadians(yaw));
                    event.setX(forward * moveSpeed * mx + strafe * moveSpeed * mz);
                    event.setZ(forward * moveSpeed * mz - strafe * moveSpeed * mx);
                    /* TEST DONT REMOVE
                    double forward = mc.thePlayer.movementInput.moveForward;
                    double strafe = mc.thePlayer.movementInput.moveStrafe;
                    float yaw = mc.thePlayer.rotationYaw;
                    if (forward == 0.0F && strafe == 0.0F) {
                        event.setX(0);
                        event.setZ(0);
                    }
                    if (forward != 0 && strafe != 0) {
                        forward = forward * Math.sin(Math.PI / 4);
                        strafe = strafe * Math.cos(Math.PI / 4);
                    }
                    if (level != 1 || mc.thePlayer.moveForward == 0.0F && mc.thePlayer.moveStrafing == 0.0F) {
                        if (level == 2) {
                            ++level;
                          // moveSpeed *= mc.thePlayer.isPotionActive(Potion.moveSpeed) ? 2.1 : 1.3;
                        } else if (level == 3) {
                            ++level;
                            double difference = (boostModes.getValue() == BoostModes.DAMAGE ? 0.999 :mc.thePlayer.ticksExisted % 2 == 0 ? -0.05D:0.1D) * (lastDist - getBaseMoveSpeed());
                            moveSpeed = lastDist - difference;
                        } else {
                            level++;
                            if (mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.getEntityBoundingBox().offset(0.0D, mc.thePlayer.motionY, 0.0D)).size() > 0 || mc.thePlayer.isCollidedVertically) {
                                level = 1;
                            }
                            if (mc.thePlayer.hurtResistantTime == 19 && level <= 11) {
                                Printer.print(""+moveSpeed + " LEVEL: " + level + " " + mc.thePlayer.hurtResistantTime);
                                moveSpeed = 1.6000131314823642;
                              //  moveSpeed *= mc.thePlayer.isPotionActive(Potion.moveSpeed) ? 5.8 : 5.8;
                                //moveSpeed += mc.thePlayer.isPotionActive(Potion.moveSpeed) ? 0.2 : 0.2;
                                Printer.print(""+moveSpeed + " LEVEL: " + level + " " + mc.thePlayer.hurtResistantTime);
                            }
                            else {
                                moveSpeed = lastDist - lastDist / 159.0D;
                            }
                        }
                    } else {
                        level = 2;
                        double boost = mc.thePlayer.isPotionActive(Potion.moveSpeed) ? 1.5 : 1.6;
                        moveSpeed = boost * getBaseMoveSpeed() - 0.01D;
                    }
                    moveSpeed = Math.max(moveSpeed, getBaseMoveSpeed());
                    final double mx = -Math.sin(Math.toRadians(yaw));
                    final double mz = Math.cos(Math.toRadians(yaw));
                    event.setX(forward * moveSpeed * mx + strafe * moveSpeed * mz);
                    event.setZ(forward * moveSpeed * mz - strafe * moveSpeed * mx);*/
                }
                break;
            default:
                break;
        }
    }

    private double getBaseMoveSpeed() {
        double n = 0.2873;
        if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
            n *= 1.0 + 0.2 * (mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1);
        }
        return n;
    }

    @Override
    public void onEnable() {
        if (mc.thePlayer == null || mc.theWorld == null) return;
        level = 0;
        lastDist = 0.0D;
    }

    private void setMoveSpeed(MotionEvent event, double speed) {
        double forward = mc.thePlayer.movementInput.moveForward;
        double strafe = mc.thePlayer.movementInput.moveStrafe;
        float yaw = mc.thePlayer.rotationYaw;
        if (forward == 0.0D && strafe == 0.0D) {
            event.setX(0);
            event.setZ(0);
        } else {
            if (forward != 0.0D) {
                if (strafe > 0.0D) {
                    yaw += forward > 0.0D ? -45 : 45;
                } else if (strafe < 0.0D) {
                    yaw += forward > 0.0D ? 45 : -45;
                }

                strafe = 0.0D;
                if (forward > 0.0D) {
                    forward = 1.0D;
                } else if (forward < 0.0D) {
                    forward = -1.0D;
                }
            }

            event.setX(forward * speed * -Math.sin(Math.toRadians(yaw)) + strafe * speed * Math.cos(Math.toRadians(yaw)));
            event.setZ(forward * speed * Math.cos(Math.toRadians(yaw)) - strafe * speed * -Math.sin(Math.toRadians(yaw)));
        }
    }
}
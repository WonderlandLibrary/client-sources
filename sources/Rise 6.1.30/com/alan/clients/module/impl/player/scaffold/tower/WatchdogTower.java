package com.alan.clients.module.impl.player.scaffold.tower;

import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.PreMotionEvent;
import com.alan.clients.module.impl.player.Scaffold;
import com.alan.clients.util.player.MoveUtil;
import com.alan.clients.value.Mode;
import net.minecraft.potion.Potion;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3i;

public class WatchdogTower extends Mode<Scaffold> {
    private int tickCounter;
    private int ticks;

    boolean move = true;
    boolean targetCalculated = false;
    private int ticks2;
    private float angle;

    double targetZ = 0;
    private int lastY;

    public WatchdogTower(String name, Scaffold parent) {
        super(name, parent);
    }

    @Override
    public void onEnable() {
        targetZ = mc.thePlayer.posZ;
        tickCounter = 0;
        angle = mc.thePlayer.rotationYaw;

        if (!mc.thePlayer.onGround) {
            ticks = 100;
        }
    }

    @EventLink
    public Listener<PreMotionEvent> preMotion = event -> {
        if (!mc.gameSettings.keyBindJump.isKeyDown()) {
            angle = mc.thePlayer.rotationYaw;
            ticks = 100;
            return;
        }

        tickCounter++;
        ticks++;

        if (tickCounter >= 23) {
            tickCounter = 1; // Reset the counter
            angle = mc.thePlayer.rotationYaw;
            ticks = 100;
        }

        if (mc.thePlayer.onGround) {
            ticks = 0;
        }

        getParent().recursions = 1;

        if (!MoveUtil.isMoving()) {
            if (!targetCalculated) {
                // Calculate the targetZ position only once
                targetZ = Math.floor(mc.thePlayer.posZ) + 0.99999999999998;
                targetCalculated = true;
            }

            ticks2++;

            if (Math.abs(lastY - mc.thePlayer.posY) >= 1) {
                if (ticks2 == 1) {
                    // Move to the middle position
                    MoveUtil.stop();
                    mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY, (mc.thePlayer.posZ + targetZ) / 2);
                } else if (ticks2 == 2) {
                    // Move to the final target position after 2 ticks
                    MoveUtil.stop();
                    mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY, targetZ);
                    doSidePlacement();
                    ticks2 = 0; // Reset the tick counter after reaching the final position
                    targetCalculated = false; // Reset the flag for the next cycle
                }
            } else {
                // Reset ticks2 if the Y position condition is not met
                ticks2 = 0;
                targetCalculated = false; // Reset the flag if the condition is not met
            }
        }


        float step = ticks == 1 ? 90 : 0;

        if (MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationYaw - angle) < step) {
            angle = mc.thePlayer.rotationYaw;
        } else if (MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationYaw - angle) < 0) {
            angle -= step;
        } else if (MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationYaw - angle) > 0) {
            angle += step;
        }

        mc.thePlayer.movementYaw = angle;

        if (tickCounter < 20) {
            MoveUtil.strafe(.26);
            if (mc.gameSettings.keyBindJump.isKeyDown()) {

//            getParent().startY = Math.floor(mc.thePlayer.posY);

                switch (ticks) {
                    case 0:
                        if (mc.thePlayer.posY % 1 == 0) {
                            event.setOnGround(true);
                            if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {

                            } else {

                            }
                        }

                        mc.thePlayer.motionY = 0.42f;
                        break;

                    case 1:
                        if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {

                        } else {

                        }
                        mc.thePlayer.motionY = 0.33;
                        break;

                    case 2:

                        mc.thePlayer.motionY = 1 - mc.thePlayer.posY % 1;
                        break;
                }
            }
        } else {

            if (mc.thePlayer.onGround) {
                mc.thePlayer.motionY = 0.4196F;
            } else if (mc.thePlayer.offGroundTicks == 3) {
                mc.thePlayer.motionY = 0F;
            }
        }

        if (ticks == 2) ticks = -1;
    };
    public void doSidePlacement() {
        lastY = (int) Math.floor(mc.thePlayer.posY);
        getParent().offset = new Vec3i(0, 0, 1);
    }

}
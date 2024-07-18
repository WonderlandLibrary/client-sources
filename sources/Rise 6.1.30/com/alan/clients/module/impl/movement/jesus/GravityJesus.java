package com.alan.clients.module.impl.movement.jesus;

import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.PreMotionEvent;
import com.alan.clients.module.impl.movement.Jesus;
import com.alan.clients.util.player.MoveUtil;
import com.alan.clients.value.Mode;
import net.minecraft.potion.Potion;

public class GravityJesus extends Mode<Jesus> {

    private boolean isFirstTimeInWater = true;
    private boolean jump = true;
    private int waterTicks = 0;
    private double posY = 50;

    public GravityJesus(String name, Jesus parent) {
        super(name, parent);
    }

    @EventLink
    public final Listener<PreMotionEvent> onPreMotion = event -> {
        if (!mc.gameSettings.keyBindJump.isPressed() && mc.thePlayer.isInWater() && isFirstTimeInWater && !(mc.thePlayer.posY % 1 == 0 || (mc.thePlayer.posY * 2) % 1 == 0) ) {
            mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY - .85, mc.thePlayer.posZ);
            isFirstTimeInWater = false;

        }

        if (mc.thePlayer.isInWater() && !mc.gameSettings.keyBindJump.isKeyDown() && !(mc.thePlayer.posY % 1 == 0 || (mc.thePlayer.posY * 2) % 1 == 0)) {
            MoveUtil.strafe(.34- Math.random() / 1000);
            mc.thePlayer.motionY = 0;
        }
        if (isFirstTimeInWater){
            posY = mc.thePlayer.posY-.85;
        }

        if (mc.thePlayer.isInWater()) {
            waterTicks = 0;
        }

        if(waterTicks<20){
            MoveUtil.strafe();
        }
        waterTicks++;

        if(mc.gameSettings.keyBindJump.isKeyDown() || mc.gameSettings.keyBindJump.isPressed()){
            jump= true;
        } else{
            jump= false;
        }

        if (jump && mc.thePlayer.isInWater() && !mc.gameSettings.keyBindJump.isKeyDown() && !mc.gameSettings.keyBindJump.isPressed()){
            mc.thePlayer.setPosition(mc.thePlayer.posX, posY, mc.thePlayer.posZ);
        }

        if (mc.thePlayer.isPotionActive(Potion.moveSpeed) && mc.thePlayer.isInWater() && !mc.gameSettings.keyBindJump.isKeyDown()) {
            MoveUtil.strafe((.05*(1+(mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier())) +.34 - Math.random() / 1000));
        }

        if(!mc.gameSettings.keyBindJump.isKeyDown() && mc.thePlayer.isInWater()  && !(mc.thePlayer.posY % 1 == 0 || (mc.thePlayer.posY * 2) % 1 == 0)){
            mc.thePlayer.setPosition(mc.thePlayer.posX, posY, mc.thePlayer.posZ);
        }
        if(mc.gameSettings.keyBindJump.isKeyDown() && mc.thePlayer.isInWater()){
            mc.thePlayer.motionY = .6;
            MoveUtil.strafe(.1);

        }

        if(mc.thePlayer.isInWater() && !mc.gameSettings.keyBindJump.isKeyDown() && !mc.gameSettings.keyBindJump.isPressed()  && !(mc.thePlayer.posY % 1 == 0 || (mc.thePlayer.posY * 2) % 1 == 0)){
            mc.thePlayer.setPosition(mc.thePlayer.posX, posY-.2, mc.thePlayer.posZ);
        }

        if (!mc.thePlayer.isInWater() && !isFirstTimeInWater  && !(mc.thePlayer.posY % 1 == 0 || (mc.thePlayer.posY * 2) % 1 == 0))  {
            isFirstTimeInWater = true;
        }
    };
}

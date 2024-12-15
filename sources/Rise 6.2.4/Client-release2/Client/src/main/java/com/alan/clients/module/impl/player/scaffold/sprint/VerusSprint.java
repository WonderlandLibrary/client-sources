package com.alan.clients.module.impl.player.scaffold.sprint;

import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.PreMotionEvent;
import com.alan.clients.event.impl.motion.StrafeEvent;
import com.alan.clients.module.impl.player.Scaffold;
import com.alan.clients.util.player.MoveUtil;
import com.alan.clients.value.Mode;
import net.minecraft.potion.Potion;

public class VerusSprint extends Mode<Scaffold> {

    public VerusSprint(String name, Scaffold parent) {
        super(name, parent);
    }

    @EventLink
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        if(mc.thePlayer.onGround){
            mc.thePlayer.motionY = 0;
            MoveUtil.strafe(.5);
            if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
                MoveUtil.strafe(((.06 * (1 + (mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier()))) + 0.1));
            }
        }
    };

    @EventLink
    public final Listener<StrafeEvent> onStrafe = event -> {
        if (mc.thePlayer.onGround) {
            event.setSpeed(.61);
            if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
                MoveUtil.strafe(((.06 * (1 + (mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier()))) + 0.1));
            }

        } else if (mc.thePlayer.motionY<.77){
            event.setSpeed(.4);
            if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
                MoveUtil.strafe(((.01 * (1 + (mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier()))) + 0.1));
            }
        }

    };
}


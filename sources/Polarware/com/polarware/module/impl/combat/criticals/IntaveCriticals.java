package com.polarware.module.impl.combat.criticals;

import com.polarware.component.impl.render.SmoothCameraComponent;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.bus.Listener;
import com.polarware.event.impl.motion.PreMotionEvent;
import com.polarware.module.impl.combat.CriticalsModule;
import com.polarware.module.impl.combat.KillAuraModule;
import com.polarware.value.Mode;
import net.minecraft.util.EnumParticleTypes;

public final class IntaveCriticals extends Mode<CriticalsModule> {

    public IntaveCriticals(String name, CriticalsModule parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        if(mc.thePlayer.isSwingInProgress && KillAuraModule.target != null) {
            if(mc.thePlayer.onGround && mc.thePlayer.hurtTime == 0) {
                mc.gameSettings.keyBindJump.setPressed(true);
            }
            if(!mc.thePlayer.onGround || mc.thePlayer.hurtTime != 0) {
                SmoothCameraComponent.setY();
                mc.gameSettings.keyBindJump.setPressed(false);
            }
        }
    };
}